package legacyparkourcompat.minecraft;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.fabricmc.mappingio.format.proguard.ProGuardFileReader;
import net.fabricmc.mappingio.tree.MemoryMappingTree;
import net.fabricmc.tinyremapper.OutputConsumerPath;
import net.fabricmc.tinyremapper.TinyRemapper;
import net.fabricmc.tinyremapper.TinyUtils;
import org.jetbrains.java.decompiler.api.Decompiler;
import org.jetbrains.java.decompiler.main.extern.IFernflowerLogger;
import org.jetbrains.java.decompiler.main.extern.IFernflowerPreferences;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HexFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipFile;

final class MinecraftDecompileEngine {
    private static final String FAILED_METHOD_MARKER = "// $VF: Couldn't be decompiled";
    private static final String VERSION_MANIFEST_URL =
            "https://piston-meta.mojang.com/mc/game/version_manifest_v2.json";
    private static final String FABRIC_YARN_META = "https://meta.fabricmc.net/v2/versions/yarn/";
    private static final String LEGACY_YARN_META = "https://meta.legacyfabric.net/v2/versions/yarn/";
    private static final String ORNITHE_FEATHER_META = "https://meta.ornithemc.net/v3/versions/feather/";
    private static final String FABRIC_MAVEN = "https://maven.fabricmc.net/";
    private static final String LEGACY_FABRIC_MAVEN = "https://maven.legacyfabric.net/";
    private static final String ORNITHE_MAVEN = "https://maven.ornithemc.net/releases/";
    private static final List<MappingCatalog> COMMUNITY_MAPPING_CATALOGS = List.of(
            new MappingCatalog("ornithe feather", ORNITHE_FEATHER_META, ORNITHE_MAVEN),
            new MappingCatalog("legacy yarn", LEGACY_YARN_META, LEGACY_FABRIC_MAVEN),
            new MappingCatalog("yarn", FABRIC_YARN_META, FABRIC_MAVEN)
    );

    private final DecompileLogger logger;
    private final Path cacheDir;
    private final Path outputRoot;
    private final List<String> mappingSpecs;
    private final boolean force;
    private final java.util.Map<String, String> inputs = new java.util.TreeMap<>();
    private java.util.Map<String, String> tools;
    private java.util.Map<String, String> versionInputs;
    private final Gson gson = new GsonBuilder().create();
    private final HttpClient http = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(30))
            .build();

    MinecraftDecompileEngine(DecompileLogger logger, Path cacheDir, Path outputRoot, List<String> mappingSpecs, boolean force) {
        this.logger = logger;
        this.cacheDir = cacheDir;
        this.outputRoot = outputRoot;
        this.mappingSpecs = List.copyOf(mappingSpecs);
        this.force = force;
    }

    void decompile(List<String> versionSpecs) {
        try {
            Files.createDirectories(cacheDir);
            Files.createDirectories(outputRoot);
            try (var sourceWriter = new DecompileStore.Writer(outputRoot);
                 var cacheWriter = new DecompileStore.Writer(cacheDir)) {
                tools = DecompileStore.tools();
                boolean catalogRefreshed = !Files.isRegularFile(cacheDir.resolve("version_manifest_v2.json"))
                        || versionSpecs.stream().anyMatch(MinecraftDecompileEngine::dynamicVersion);
                MojangMeta.VersionManifest manifest = releaseCatalog(catalogRefreshed);

                for (String spec : versionSpecs) {
                    if (!dynamicVersion(spec) && !catalogRefreshed
                            && manifest.versions.stream().noneMatch(version -> spec.trim().equals(version.id))) {
                        // A release added after the local catalog was saved must remain discoverable.
                        manifest = releaseCatalog(true);
                        catalogRefreshed = true;
                    }
                    MojangMeta.VersionRef resolved = resolveVersion(spec, manifest);
                    logger.lifecycle("Decompiling Minecraft {} (requested '{}')", resolved.id, spec);
                    decompileVersion(resolved);
                }
            }
        } catch (IllegalStateException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalStateException("Failed to decompile Minecraft: " + e.getMessage(), e);
        }
    }

    private static boolean dynamicVersion(String spec) {
        return "latest".equalsIgnoreCase(spec.trim()) || "latest-snapshot".equalsIgnoreCase(spec.trim());
    }

    private MojangMeta.VersionManifest releaseCatalog(boolean refresh) throws Exception {
        // This discovery index is mutable. Exact per-version inputs are pinned separately.
        Path catalog = download(VERSION_MANIFEST_URL, cacheDir.resolve("version_manifest_v2.json"),
                null, false, refresh);
        MojangMeta.VersionManifest manifest = gson.fromJson(Files.readString(catalog), MojangMeta.VersionManifest.class);
        if (manifest == null || manifest.versions == null || manifest.latest == null) {
            throw new IllegalStateException("Could not parse the Minecraft release catalog.");
        }
        return manifest;
    }

    private void decompileVersion(MojangMeta.VersionRef versionRef) throws Exception {
        Path versionCache = cacheDir.resolve(versionRef.id);
        Files.createDirectories(versionCache);
        inputs.clear();

        Path versionJsonPath = download(versionRef.url, versionCache.resolve("version.json"), versionRef.sha1);
        MojangMeta.VersionJson version = gson.fromJson(
                Files.readString(versionJsonPath),
                MojangMeta.VersionJson.class
        );
        if (version == null || version.downloads == null || version.downloads.client == null) {
            throw new IllegalStateException("Version " + versionRef.id + " does not publish a client jar.");
        }

        int requiredJava = version.javaVersion != null ? version.javaVersion.majorVersion : 8;
        int currentJava = Runtime.version().feature();
        logger.lifecycle("  Target bytecode Java {}, decompiler JVM {}", requiredJava, currentJava);
        if (currentJava < requiredJava) {
            throw new IllegalStateException(
                    "Minecraft " + versionRef.id + " requires Java " + requiredJava
                            + " to decompile (this JVM is " + currentJava + "). "
                            + "The decompileMinecraft task uses a Java 25 toolchain; check that it was provisioned."
            );
        }

        Path clientJar = download(
                version.downloads.client.url,
                versionCache.resolve("client.jar"),
                version.downloads.client.sha1
        );
        List<Path> libraries = downloadLibraries(version, versionCache.resolve("libraries"));
        versionInputs = new java.util.TreeMap<>(inputs);

        for (String requestedMapping : mappingSpecs) {
            String mapping = requestedMapping.toLowerCase(Locale.ROOT);
            if (!List.of("auto", "mojmap", "legacy-yarn", "yarn", "feather", "unobfuscated").contains(mapping)) {
                throw new IllegalStateException("Unknown mapping set '" + requestedMapping + "'.");
            }
            if ("auto".equals(mapping) && "1.13.2".equals(versionRef.id)) {
                decompileMappedVersion(versionRef, version, versionCache, clientJar, libraries, "feather");
                decompileMappedVersion(versionRef, version, versionCache, clientJar, libraries, "legacy-yarn");
                continue;
            }
            if ("auto".equals(mapping) && "1.14.4".equals(versionRef.id)) {
                decompileMappedVersion(versionRef, version, versionCache, clientJar, libraries, "mojmap");
                decompileMappedVersion(versionRef, version, versionCache, clientJar, libraries, "yarn");
                continue;
            }
            decompileMappedVersion(versionRef, version, versionCache, clientJar, libraries, mapping);
        }
    }

    private void decompileMappedVersion(MojangMeta.VersionRef versionRef, MojangMeta.VersionJson version,
                                        Path versionCache, Path clientJar, List<Path> libraries, String requestedMapping)
            throws Exception {
        // Provenance for one mapping must not depend on the order of other requested mappings.
        inputs.clear();
        inputs.putAll(versionInputs);
        Path jarToDecompile = clientJar;
        List<Remapping> remaps = new ArrayList<>();
        String mappingSource;
        boolean repair181Helpers = false;
        boolean hasMojmap = version.downloads.clientMappings != null
                && version.downloads.clientMappings.url != null;
        if ("mojmap".equals(requestedMapping) || ("auto".equals(requestedMapping) && hasMojmap)) {
            if (!hasMojmap) {
                throw new IllegalStateException("Minecraft " + versionRef.id + " has no official Mojang mappings.");
            }
            Path mappings = download(version.downloads.clientMappings.url,
                    versionCache.resolve("client_mappings.txt"), version.downloads.clientMappings.sha1);
            Path mappedJar = versionCache.resolve("client-mojmap.jar");
            logger.lifecycle("  Applying official Mojang mappings");
            remaps.add(() -> remap(clientJar, mappedJar, mojmapProvider(mappings), libraries));
            jarToDecompile = mappedJar;
            mappingSource = "mojmap";
        } else if ("unobfuscated".equals(requestedMapping)) {
            if (jarHasDefaultPackageClasses(clientJar)) {
                throw new IllegalStateException("Minecraft " + versionRef.id + " is obfuscated; choose a mapping set.");
            }
            mappingSource = "unobfuscated";
        } else {
            boolean obfuscated = jarHasDefaultPackageClasses(clientJar);
            YarnMapping yarn = "auto".equals(requestedMapping) && !obfuscated ? null
                    : "auto".equals(requestedMapping)
                    ? findCommunityMappings(versionRef.id)
                    : findCommunityMappings(versionRef.id, requestedMapping);
            if (yarn == null) {
                if (!"auto".equals(requestedMapping)) {
                    throw new IllegalStateException("No " + requestedMapping + " mappings for Minecraft " + versionRef.id);
                }
                if (obfuscated) {
                    throw new IllegalStateException("No supported mappings for obfuscated Minecraft " + versionRef.id);
                }
                mappingSource = "unobfuscated";
            } else {
                mappingSource = yarn.label.replace(' ', '-');
                boolean order18Helpers = "1.8".equals(versionRef.id) && "ornithe-feather".equals(mappingSource);
                if (order18Helpers) EnumSwitchHelperRepair.verify18PinnedInputs(clientJar,
                        yarn.tinyFile, yarn.mappingJar);
                repair181Helpers = "1.8.1".equals(versionRef.id) && "ornithe-feather".equals(mappingSource);
                if (repair181Helpers) EnumSwitchHelperRepair.verifyPinnedInputs(clientJar,
                        yarn.tinyFile, yarn.mappingJar);
                Path mappedJar = versionCache.resolve(repair181Helpers
                        ? "client-ornithe-feather-181-enum-helper-order-v1.jar" : "client-" + mappingSource + ".jar");
                logger.lifecycle("  Applying {} ({})", yarn.label, yarn.version);
                String mappingHeader;
                try (BufferedReader reader = Files.newBufferedReader(yarn.tinyFile, StandardCharsets.UTF_8)) {
                    mappingHeader = reader.readLine();
                }
                if (mappingHeader != null && mappingHeader.contains("\tofficial\t")) {
                    if (repair181Helpers) {
                        Path baselineJar = versionCache.resolve("client-ornithe-feather-181-enum-helper-baseline-v1.jar");
                        inputs.put("pipeline:181-enum-helper-order", EnumSwitchHelperRepair.PIPELINE_ID);
                        remaps.add(() -> {
                            remap(clientJar, baselineJar, TinyUtils.createTinyMappingProvider(yarn.tinyFile, "official", "named"), libraries);
                            EnumSwitchHelperRepair.verifyOrdinaryRemap(baselineJar);
                            EnumSwitchHelperRepair.verifyDiagnosticBindings(baselineJar);
                            EnumSwitchHelperRepair.verifyAuditMappings(yarn.tinyFile, "official", "named",
                                    clientJar, yarn.mappingJar);
                            EnumSwitchHelperRepair.applyHelperOrder(baselineJar, mappedJar);
                        });
                    } else {
                        remaps.add(() -> remap(clientJar, mappedJar,
                                TinyUtils.createTinyMappingProvider(yarn.tinyFile, "official", "named"), libraries));
                    }
                } else if (mappingHeader != null && mappingHeader.endsWith("\tintermediary\tnamed")) {
                    Path intermediaryJar = downloadYarnJar(FABRIC_MAVEN, "net.fabricmc", "intermediary", versionRef.id);
                    if (intermediaryJar == null) {
                        throw new IllegalStateException("No exact intermediary bridge for Minecraft " + versionRef.id);
                    }
                    Path intermediaryTiny = cacheDir.resolve("intermediary").resolve(versionRef.id + ".tiny");
                    extractNamedFile(intermediaryJar, "mappings/mappings.tiny", intermediaryTiny);
                    Path intermediaryClient = versionCache.resolve("client-intermediary.jar");
                    logger.lifecycle("  Bridging official -> intermediary -> named for {}", versionRef.id);
                    remaps.add(() -> remap(clientJar, intermediaryClient,
                            TinyUtils.createTinyMappingProvider(intermediaryTiny, "official", "intermediary"), libraries));
                    boolean reversedColumns = hasReversedIntermediaryClassColumns(yarn.tinyFile);
                    String sourceNamespace = reversedColumns ? "named" : "intermediary";
                    String targetNamespace = reversedColumns ? "intermediary" : "named";
                    if (repair181Helpers) {
                        Path baselineJar = versionCache.resolve("client-ornithe-feather-181-enum-helper-baseline-v1.jar");
                        inputs.put("pipeline:181-enum-helper-order", EnumSwitchHelperRepair.PIPELINE_ID);
                        remaps.add(() -> {
                            var ordinaryProvider = TinyUtils.createTinyMappingProvider(yarn.tinyFile, sourceNamespace, targetNamespace);
                            remap(intermediaryClient, baselineJar, ordinaryProvider, libraries);
                            EnumSwitchHelperRepair.verifyOrdinaryRemap(baselineJar);
                            EnumSwitchHelperRepair.verifyDiagnosticBindings(baselineJar);
                            EnumSwitchHelperRepair.verifyAuditMappings(yarn.tinyFile, sourceNamespace,
                                    targetNamespace, intermediaryClient, yarn.mappingJar);
                            EnumSwitchHelperRepair.applyHelperOrder(baselineJar, mappedJar);
                        });
                    } else {
                        remaps.add(() -> remap(intermediaryClient, mappedJar,
                                TinyUtils.createTinyMappingProvider(yarn.tinyFile, sourceNamespace, targetNamespace), libraries));
                    }
                } else {
                    throw new IllegalStateException("Unsupported mapping namespaces for Minecraft " + versionRef.id
                            + ": " + mappingHeader);
                }
                jarToDecompile = mappedJar;
                if (order18Helpers) {
                    Path orderedJar = versionCache.resolve("client-ornithe-feather-18-enum-helper-order-v1.jar");
                    inputs.put("pipeline:18-enum-helper-order", EnumSwitchHelperRepair.PIPELINE_18_ID);
                    remaps.add(() -> EnumSwitchHelperRepair.applyHelperOrder(mappedJar, orderedJar,
                            EnumSwitchHelperRepair.MAPPED_18_CONTENT_SHA256));
                    jarToDecompile = orderedJar;
                }
            }
        }

        Path outputDir = outputRoot.resolve(versionRef.id).resolve(mappingSource);
        if ((repair181Helpers || ("1.8".equals(versionRef.id) && "ornithe-feather".equals(mappingSource)))
                && Files.exists(outputDir) && !force
                && !Files.isRegularFile(outputDir.resolve(DecompileStore.MANIFEST))) {
            throw new IllegalStateException("Refusing unverified legacy " + versionRef.id + " Feather sources at " + outputDir
                    + "; use --force to publish the audited enum-helper ordering through the strict gates.");
        }
        if (Files.exists(outputDir) && !force) {
            ensureNoFailedMethodOutput(outputDir);
            ensureMovementSources(outputDir);
            if (!Files.isRegularFile(outputDir.resolve(DecompileStore.MANIFEST))) {
                logger.warn("  Reusing legacy sources at {}; provenance and reproducibility are unverified. "
                        + "Use --force to publish pinned sources while preserving this tree.", outputDir);
            } else {
                DecompileStore.Manifest recorded = DecompileStore.read(outputDir);
                DecompileStore.Manifest current = DecompileStore.provenance(inputs, tools, outputDir);
                if (!recorded.equals(current)) {
                    throw new IllegalStateException("Source provenance differs at " + outputDir
                            + "; inspect the inputs/tools/source hashes and use --force for deliberate regeneration.");
                }
                logger.lifecycle("  Reusing verified sources at {}", outputDir);
            }
            return;
        }
        Files.createDirectories(outputDir.getParent());
        Path staging = Files.createTempDirectory(outputDir.getParent(), "." + mappingSource + ".staging-");

        logger.lifecycle("  Decompiling with Vineflower into {}", staging);
        try {
            for (Remapping remapping : remaps) remapping.run();
            decompileJar(jarToDecompile, libraries, staging);
            ensureNoFailedMethodOutput(staging);
            ensureMovementSources(staging);
            DecompileStore.Manifest generated = DecompileStore.provenance(inputs, tools, staging);
            if (Files.isRegularFile(outputDir.resolve(DecompileStore.MANIFEST))) {
                DecompileStore.Manifest previous = DecompileStore.read(outputDir);
                if (previous.pipeline().equals(generated.pipeline())
                        && previous.runtime().equals(generated.runtime())
                        && previous.tools().equals(generated.tools())
                        && previous.inputs().equals(generated.inputs())
                        && !previous.sources().equals(generated.sources())) {
                    throw new IllegalStateException("Identical pinned inputs produced different source hashes; "
                            + "preserving previous sources at " + outputDir);
                }
            }
            DecompileStore.write(staging, generated);
            DecompileStore.publish(staging, outputDir);
        } catch (Exception error) {
            try {
                deleteRecursively(staging);
            } catch (IOException cleanupError) {
                error.addSuppressed(cleanupError);
            }
            throw error;
        }
        logger.lifecycle("  Finished {} using {}", versionRef.id, mappingSource);
    }

    private void remap(Path input, Path output, net.fabricmc.tinyremapper.IMappingProvider mappings, List<Path> classpath)
            throws IOException {
        Files.deleteIfExists(output);
        TinyRemapper remapper = TinyRemapper.newRemapper()
                .withMappings(mappings)
                .ignoreConflicts(true)
                .renameInvalidLocals(true)
                .rebuildSourceFilenames(true)
                .fixPackageAccess(true)
                .threads(1)
                .build();
        try {
            if (!classpath.isEmpty()) {
                remapper.readClassPath(classpath.toArray(Path[]::new));
            }
            remapper.readInputs(input);
            try (OutputConsumerPath consumer = new OutputConsumerPath.Builder(output).assumeArchive(true).build()) {
                remapper.apply(consumer);
            }
        } finally {
            remapper.finish();
        }
    }

    private static net.fabricmc.tinyremapper.IMappingProvider mojmapProvider(Path mappings) throws IOException {
        MemoryMappingTree tree = new MemoryMappingTree();
        try (BufferedReader reader = Files.newBufferedReader(mappings, StandardCharsets.UTF_8)) {
            ProGuardFileReader.read(reader, "named", "official", tree);
        }
        return TinyUtils.createMappingProvider(tree, "official", "named");
    }

    private void decompileJar(Path jar, List<Path> libraries, Path outputDir) {
        AtomicInteger decompilerErrors = new AtomicInteger();
        IFernflowerLogger vineflowerLogger = new IFernflowerLogger() {
            @Override
            public void writeMessage(String message, Severity severity) {
                switch (severity) {
                    case ERROR -> {
                        decompilerErrors.incrementAndGet();
                        logger.error("[Vineflower] {}", message);
                    }
                    case WARN -> logger.warn("[Vineflower] {}", message);
                    default -> { /* Vineflower reports every class load at INFO. */ }
                }
            }

            @Override
            public void writeMessage(String message, Severity severity, Throwable t) {
                writeMessage(message, severity);
                logger.info("[Vineflower] {}", t.toString());
            }
        };
        vineflowerLogger.setSeverity(IFernflowerLogger.Severity.WARN);

        // Remapped (or unobfuscated) game classes live under these packages.
        // Pre-Mojmap clients keep nearly everything in the default package until remapped,
        // so skipping mappings would emit only a handful of already-named classes.
        Decompiler.Builder builder = Decompiler.builder()
                .inputs(jar.toFile())
                .output(new JavaDirectorySaver(outputDir))
                .logger(vineflowerLogger)
                .option(IFernflowerPreferences.DECOMPILE_GENERIC_SIGNATURES, true)
                .option(IFernflowerPreferences.ASCII_STRING_CHARACTERS, true)
                .option(IFernflowerPreferences.REMOVE_SYNTHETIC, true)
                .option(IFernflowerPreferences.INDENT_STRING, "    ")
                .option(IFernflowerPreferences.THREADS, "1")
                .option(IFernflowerPreferences.WARN_INCONSISTENT_INNER_CLASSES, false)
                .option(IFernflowerPreferences.INCLUDE_JAVA_RUNTIME, false)
                .allowedPrefixes("net/minecraft", "com/mojang");

        List<Path> existingLibraries = libraries.stream().filter(Files::isRegularFile).toList();
        if (!existingLibraries.isEmpty()) {
            builder.libraries(existingLibraries.stream().map(Path::toFile).toArray(java.io.File[]::new));
        }

        builder.build().decompile();
        if (decompilerErrors.get() > 0) {
            throw new IllegalStateException("Vineflower reported " + decompilerErrors.get()
                    + " errors; incomplete source output will not be published.");
        }
    }

    private static void ensureMovementSources(Path outputDir) throws IOException {
        Set<String> names;
        try (Stream<Path> files = Files.walk(outputDir)) {
            names = files.filter(path -> Files.isRegularFile(path) && path.toString().endsWith(".java"))
                    .map(path -> path.getFileName().toString())
                    .collect(Collectors.toSet());
        }
        for (String required : List.of("Block.java", "Entity.java", "LivingEntity.java")) {
            if (!names.contains(required)) {
                throw new IllegalStateException("Missing movement source " + required + " in " + outputDir);
            }
        }
        if (!names.contains("Player.java") && !names.contains("PlayerEntity.java")) {
            throw new IllegalStateException("Missing player movement source in " + outputDir);
        }
        if (!names.contains("LocalPlayer.java") && !names.contains("ClientPlayerEntity.java")
                && !names.contains("LocalClientPlayerEntity.java")) {
            throw new IllegalStateException("Missing local-player movement source in " + outputDir);
        }
    }

    private static void ensureNoFailedMethodOutput(Path outputDir) throws IOException {
        try (Stream<Path> files = Files.walk(outputDir)) {
            for (Path source : files
                    .filter(path -> Files.isRegularFile(path) && path.toString().endsWith(".java"))
                    .toList()) {
                try (var reader = Files.newBufferedReader(source, StandardCharsets.UTF_8)) {
                    String line;
                    int lineNumber = 0;
                    while ((line = reader.readLine()) != null) {
                        lineNumber++;
                        if (line.contains(FAILED_METHOD_MARKER)) {
                            throw new IllegalStateException("Vineflower emitted a failed-method marker at "
                                    + outputDir.relativize(source) + ":" + lineNumber
                                    + "; incomplete source output cannot be reused or published.");
                        }
                    }
                }
            }
        }
    }

    private List<Path> downloadLibraries(MojangMeta.VersionJson version, Path libraryRoot) throws Exception {
        List<Path> result = new ArrayList<>();
        if (version.libraries == null) {
            return result;
        }
        String os = currentOsName();
        for (MojangMeta.Library library : version.libraries) {
            if (!libraryAllowed(library, os) || library.downloads == null || library.downloads.artifact == null) {
                continue;
            }
            MojangMeta.Artifact artifact = library.downloads.artifact;
            if (artifact.url == null || artifact.url.isBlank()) {
                continue;
            }
            String relative = artifact.path != null && !artifact.path.isBlank()
                    ? artifact.path
                    : library.name.replace(':', '/');
            Path dest = libraryRoot.resolve(relative);
            result.add(download(artifact.url, dest, artifact.sha1));
        }
        return result;
    }

    private static boolean libraryAllowed(MojangMeta.Library library, String os) {
        if (library.rules == null || library.rules.isEmpty()) {
            return true;
        }
        boolean allowed = false;
        for (MojangMeta.Rule rule : library.rules) {
            if (ruleMatches(rule, os)) {
                allowed = "allow".equalsIgnoreCase(rule.action);
            }
        }
        return allowed;
    }

    private static boolean ruleMatches(MojangMeta.Rule rule, String os) {
        if (rule.os == null || rule.os.name == null || rule.os.name.isBlank()) {
            return true;
        }
        return os.equalsIgnoreCase(rule.os.name);
    }

    private static String currentOsName() {
        String name = System.getProperty("os.name", "").toLowerCase(Locale.ROOT);
        if (name.contains("win")) {
            return "windows";
        }
        if (name.contains("mac") || name.contains("darwin")) {
            return "osx";
        }
        return "linux";
    }

    private YarnMapping findCommunityMappings(String minecraftVersion) throws Exception {
        List<MappingCatalog> candidates = minecraftVersion.matches("1\\.(8|9|10|11|12)(\\..*)?")
                ? COMMUNITY_MAPPING_CATALOGS
                : List.of(COMMUNITY_MAPPING_CATALOGS.get(2), COMMUNITY_MAPPING_CATALOGS.get(0),
                        COMMUNITY_MAPPING_CATALOGS.get(1));
        for (MappingCatalog catalog : candidates) {
            YarnMapping mapping = yarnFromMeta(
                    catalog.metaUrlPrefix() + minecraftVersion,
                    catalog.mavenRoot(),
                    catalog.label()
            );
            if (mapping != null) {
                return mapping;
            }
        }
        return null;
    }

    private YarnMapping findCommunityMappings(String minecraftVersion, String requestedMapping) throws Exception {
        String label = switch (requestedMapping) {
            case "legacy-yarn" -> "legacy yarn";
            case "yarn" -> "yarn";
            case "feather" -> "ornithe feather";
            default -> throw new IllegalArgumentException("Unknown community mappings: " + requestedMapping);
        };
        MappingCatalog catalog = COMMUNITY_MAPPING_CATALOGS.stream()
                .filter(candidate -> candidate.label().equals(label))
                .findFirst().orElseThrow();
        return yarnFromMeta(catalog.metaUrlPrefix() + minecraftVersion, catalog.mavenRoot(), catalog.label());
    }

    private YarnMapping yarnFromMeta(String metaUrl, String mavenRoot, String label) throws Exception {
        String body;
        try {
            Path metadata = cacheDir.resolve("mapping-metadata")
                    .resolve(label.replace(' ', '-') + "-" + metaUrl.substring(metaUrl.lastIndexOf('/') + 1) + ".json");
            body = Files.readString(download(metaUrl, metadata, null));
        } catch (Exception e) {
            if (e.getMessage() == null || !e.getMessage().startsWith("HTTP 404")) throw e;
            logger.info("  No {} metadata at {}: {}", label, metaUrl, e.getMessage());
            return null;
        }
        List<MojangMeta.YarnBuild> builds = gson.fromJson(body, new TypeToken<List<MojangMeta.YarnBuild>>() {}.getType());
        if (builds == null || builds.isEmpty()) {
            logger.info("  {} has no builds for this version", label);
            return null;
        }
        MojangMeta.YarnBuild chosen = builds.stream()
                .filter(build -> build.stable)
                .max(Comparator.comparingInt(build -> build.build))
                .orElseGet(() -> builds.stream().max(Comparator.comparingInt(build -> build.build)).orElse(null));
        if (chosen == null || chosen.maven == null) {
            return null;
        }

        String[] parts = chosen.maven.split(":");
        if (parts.length != 3) {
            return null;
        }
        Path yarnJar = downloadYarnJar(mavenRoot, parts[0], parts[1], parts[2]);
        if (yarnJar == null) {
            logger.warn("  Failed to download {} {}", label, chosen.version);
            return null;
        }

        Path tiny = cacheDir.resolve("yarn").resolve(parts[1] + '-' + parts[2] + ".tiny");
        extractNamedFile(yarnJar, "mappings/mappings.tiny", tiny);
        return new YarnMapping(label, chosen.version, tiny, yarnJar);
    }

    private Path downloadYarnJar(String mavenRoot, String group, String artifact, String version) throws Exception {
        String base = mavenRoot + group.replace('.', '/') + '/' + artifact + '/' + version + '/' + artifact + '-' + version;
        List<String> suffixes = List.of("-mergedv2.jar", "-v2.jar", ".jar");
        for (String suffix : suffixes) {
            Path dest = cacheDir.resolve("yarn").resolve(artifact + '-' + version + suffix);
            try {
                return download(base + suffix, dest, null);
            } catch (Exception e) {
                if (e.getMessage() == null || !e.getMessage().startsWith("HTTP 404")) throw e;
                logger.info("  Yarn artifact {}{} not found: {}", artifact + '-' + version, suffix, e.getMessage());
            }
        }
        return null;
    }

    private static void extractNamedFile(Path jar, String entry, Path dest) throws IOException {
        Files.createDirectories(dest.getParent());
        try (FileSystem fs = FileSystems.newFileSystem(jar)) {
            Path source = fs.getPath(entry);
            if (!Files.exists(source)) {
                throw new IOException(entry + " not found in " + jar.getFileName());
            }
            Files.copy(source, dest, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private static boolean hasReversedIntermediaryClassColumns(Path tiny) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(tiny, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith("c\t")) {
                    continue;
                }
                String[] columns = line.split("\t", -1);
                if (columns.length >= 3) {
                    boolean firstIntermediary = columns[1].matches(".*[/]class_[0-9]+.*");
                    boolean secondIntermediary = columns[2].matches(".*[/]class_[0-9]+.*");
                    if (firstIntermediary != secondIntermediary) {
                        return secondIntermediary;
                    }
                }
            }
        }
        throw new IOException("Could not identify intermediary class column in " + tiny);
    }

    private MojangMeta.VersionRef resolveVersion(String spec, MojangMeta.VersionManifest manifest) {
        String requested = spec.trim();
        if (requested.isEmpty()) {
            throw new IllegalStateException("Empty Minecraft version spec.");
        }
        if ("latest".equalsIgnoreCase(requested)) {
            return requireVersion(manifest, manifest.latest.release);
        }
        if ("latest-snapshot".equalsIgnoreCase(requested)) {
            return requireVersion(manifest, manifest.latest.snapshot);
        }

        for (MojangMeta.VersionRef version : manifest.versions) {
            if (requested.equals(version.id)) {
                return version;
            }
        }

        throw new IllegalStateException("Unknown exact Minecraft version '" + requested + "'.");
    }

    private static MojangMeta.VersionRef requireVersion(MojangMeta.VersionManifest manifest, String id) {
        return manifest.versions.stream()
                .filter(version -> Objects.equals(id, version.id))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Manifest is missing version '" + id + "'."));
    }

    private Path download(String url, Path dest, String expectedSha1) throws Exception {
        return download(url, dest, expectedSha1, true, false);
    }

    private Path download(String url, Path dest, String expectedSha1, boolean pinned, boolean refresh) throws Exception {
        if (!refresh && Files.isRegularFile(dest) && (expectedSha1 == null || expectedSha1.equalsIgnoreCase(sha1(dest)))) {
            if (pinned) DecompileStore.pin(dest, url, inputs);
            return dest;
        }
        if (pinned && Files.exists(dest.resolveSibling(dest.getFileName() + ".pin.json"))) {
            throw new IOException("Pinned input missing or corrupt: " + dest + "; restore the pinned artifact.");
        }
        Files.createDirectories(dest.getParent());
        Path temp = dest.resolveSibling(dest.getFileName() + ".part");
        Exception last = null;
        for (int attempt = 1; attempt <= 4; attempt++) {
            try {
                logger.info("  Downloading {} -> {}", url, dest.getFileName());
                HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                        .timeout(Duration.ofMinutes(5))
                        .header("User-Agent", "LegacyParkourCompat-decompileMinecraft")
                        .GET()
                        .build();
                HttpResponse<InputStream> response = http.send(request, HttpResponse.BodyHandlers.ofInputStream());
                int status = response.statusCode();
                if (status / 100 == 4) {
                    throw new IOException("HTTP " + status + " for " + url);
                }
                if (status / 100 != 2) {
                    throw new IOException("HTTP " + status + " for " + url);
                }
                try (InputStream in = response.body()) {
                    Files.copy(in, temp, StandardCopyOption.REPLACE_EXISTING);
                }
                if (expectedSha1 != null && !expectedSha1.equalsIgnoreCase(sha1(temp))) {
                    throw new IOException("SHA-1 mismatch for " + dest.getFileName());
                }
                Files.move(temp, dest, StandardCopyOption.REPLACE_EXISTING);
                if (pinned) DecompileStore.pin(dest, url, inputs);
                return dest;
            } catch (IOException e) {
                last = e;
                Files.deleteIfExists(temp);
                if (e.getMessage() != null && e.getMessage().startsWith("HTTP 4")) {
                    throw e;
                }
                logger.warn("  Download attempt {} failed for {}: {}", attempt, dest.getFileName(), e.getMessage());
                if (attempt < 4) {
                    Thread.sleep(1000L * (1L << (attempt - 1)));
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw e;
            } catch (Exception e) {
                last = e;
                logger.warn("  Download attempt {} failed for {}: {}", attempt, dest.getFileName(), e.getMessage());
                Files.deleteIfExists(temp);
                if (attempt < 4) {
                    Thread.sleep(1000L * (1L << (attempt - 1)));
                }
            }
        }
        throw last;
    }

    private static String sha1(Path file) throws IOException {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            try (InputStream in = Files.newInputStream(file)) {
                byte[] buffer = new byte[8192];
                int read;
                while ((read = in.read(buffer)) != -1) {
                    digest.update(buffer, 0, read);
                }
            }
            return HexFormat.of().formatHex(digest.digest());
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    private static void deleteRecursively(Path root) throws IOException {
        if (!Files.exists(root)) {
            return;
        }
        try (Stream<Path> walk = Files.walk(root)) {
            List<Path> paths = walk.sorted(Comparator.reverseOrder()).toList();
            for (Path path : paths) {
                Files.deleteIfExists(path);
            }
        }
    }

    private static boolean jarHasDefaultPackageClasses(Path jar) throws IOException {
        try (ZipFile zip = new ZipFile(jar.toFile())) {
            return zip.stream().anyMatch(entry -> {
                String name = entry.getName();
                return name.endsWith(".class") && name.indexOf('/') < 0 && name.indexOf('\\') < 0;
            });
        }
    }

    private record MappingCatalog(String label, String metaUrlPrefix, String mavenRoot) {
    }

    @FunctionalInterface
    private interface Remapping {
        void run() throws Exception;
    }

    private record YarnMapping(String label, String version, Path tinyFile, Path mappingJar) {
    }
}
