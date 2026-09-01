package legacyparkourcompat.minecraft;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.fabricmc.mappingio.format.proguard.ProGuardFileReader;
import net.fabricmc.mappingio.tree.MemoryMappingTree;
import net.fabricmc.tinyremapper.OutputConsumerPath;
import net.fabricmc.tinyremapper.TinyRemapper;
import net.fabricmc.tinyremapper.TinyUtils;
import org.gradle.api.GradleException;
import org.gradle.api.logging.Logger;
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
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HexFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Stream;

final class MinecraftDecompileEngine {
    private static final String VERSION_MANIFEST_URL =
            "https://piston-meta.mojang.com/mc/game/version_manifest_v2.json";
    private static final String FABRIC_YARN_META = "https://meta.fabricmc.net/v2/versions/yarn/";
    private static final String LEGACY_YARN_META = "https://meta.legacyfabric.net/v2/versions/yarn/";
    private static final String FABRIC_MAVEN = "https://maven.fabricmc.net/";
    private static final String LEGACY_FABRIC_MAVEN = "https://maven.legacyfabric.net/";

    private final Logger logger;
    private final Path cacheDir;
    private final Path outputRoot;
    private final Gson gson = new GsonBuilder().create();
    private final HttpClient http = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(30))
            .build();

    MinecraftDecompileEngine(Logger logger, Path cacheDir, Path outputRoot) {
        this.logger = logger;
        this.cacheDir = cacheDir;
        this.outputRoot = outputRoot;
    }

    void decompile(List<String> versionSpecs) {
        try {
            Files.createDirectories(cacheDir);
            Files.createDirectories(outputRoot);

            Path manifestPath = download(
                    VERSION_MANIFEST_URL,
                    cacheDir.resolve("version_manifest_v2.json"),
                    null
            );
            MojangMeta.VersionManifest manifest = gson.fromJson(
                    Files.readString(manifestPath),
                    MojangMeta.VersionManifest.class
            );
            if (manifest == null || manifest.versions == null || manifest.latest == null) {
                throw new GradleException("Could not parse the Minecraft version manifest.");
            }

            for (String spec : versionSpecs) {
                MojangMeta.VersionRef resolved = resolveVersion(spec, manifest);
                logger.lifecycle("Decompiling Minecraft {} (requested '{}')", resolved.id, spec);
                decompileVersion(resolved);
            }
        } catch (GradleException e) {
            throw e;
        } catch (Exception e) {
            throw new GradleException("Failed to decompile Minecraft: " + e.getMessage(), e);
        }
    }

    private void decompileVersion(MojangMeta.VersionRef versionRef) throws Exception {
        Path versionCache = cacheDir.resolve(versionRef.id);
        Files.createDirectories(versionCache);

        Path versionJsonPath = download(versionRef.url, versionCache.resolve("version.json"), null);
        MojangMeta.VersionJson version = gson.fromJson(
                Files.readString(versionJsonPath),
                MojangMeta.VersionJson.class
        );
        if (version == null || version.downloads == null || version.downloads.client == null) {
            throw new GradleException("Version " + versionRef.id + " does not publish a client jar.");
        }

        Path clientJar = download(
                version.downloads.client.url,
                versionCache.resolve("client.jar"),
                version.downloads.client.sha1
        );
        List<Path> libraries = downloadLibraries(version, versionCache.resolve("libraries"));

        Path jarToDecompile = clientJar;
        String mappingSource = "none (unobfuscated or unavailable)";

        if (version.downloads.clientMappings != null && version.downloads.clientMappings.url != null) {
            Path mappings = download(
                    version.downloads.clientMappings.url,
                    versionCache.resolve("client_mappings.txt"),
                    version.downloads.clientMappings.sha1
            );
            Path mappedJar = versionCache.resolve("client-mojmap.jar");
            logger.lifecycle("  Applying official Mojang mappings");
            remap(clientJar, mappedJar, mojmapProvider(mappings), libraries);
            jarToDecompile = mappedJar;
            mappingSource = "mojmap";
        } else {
            YarnMapping yarn = findYarn(versionRef.id);
            if (yarn != null) {
                Path mappedJar = versionCache.resolve("client-yarn.jar");
                logger.lifecycle("  Official Mojang mappings unavailable; applying {} ({})", yarn.label, yarn.version);
                remap(clientJar, mappedJar, TinyUtils.createTinyMappingProvider(yarn.tinyFile, "official", "named"), libraries);
                jarToDecompile = mappedJar;
                mappingSource = yarn.label;
            } else {
                logger.lifecycle("  No mappings available; decompiling the client jar as published");
            }
        }

        Path outputDir = outputRoot.resolve(versionRef.id);
        if (Files.exists(outputDir)) {
            deleteRecursively(outputDir);
        }
        Files.createDirectories(outputDir);

        logger.lifecycle("  Decompiling with Vineflower into {}", outputDir);
        decompileJar(jarToDecompile, libraries, outputDir);
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
        IFernflowerLogger vineflowerLogger = new IFernflowerLogger() {
            @Override
            public void writeMessage(String message, Severity severity) {
                switch (severity) {
                    case ERROR -> logger.error("[Vineflower] {}", message);
                    case WARN -> logger.warn("[Vineflower] {}", message);
                    default -> logger.info("[Vineflower] {}", message);
                }
            }

            @Override
            public void writeMessage(String message, Severity severity, Throwable t) {
                writeMessage(message, severity);
                logger.info("[Vineflower] {}", t.toString());
            }
        };
        vineflowerLogger.setSeverity(IFernflowerLogger.Severity.WARN);

        Decompiler.Builder builder = Decompiler.builder()
                .inputs(jar.toFile())
                .output(new JavaDirectorySaver(outputDir))
                .logger(vineflowerLogger)
                .option(IFernflowerPreferences.DECOMPILE_GENERIC_SIGNATURES, true)
                .option(IFernflowerPreferences.ASCII_STRING_CHARACTERS, true)
                .option(IFernflowerPreferences.REMOVE_SYNTHETIC, true)
                .option(IFernflowerPreferences.INDENT_STRING, "    ")
                .option(IFernflowerPreferences.THREADS, Integer.toString(Math.max(1, Runtime.getRuntime().availableProcessors())))
                .option(IFernflowerPreferences.WARN_INCONSISTENT_INNER_CLASSES, false)
                .allowedPrefixes("net/minecraft", "com/mojang");

        List<Path> existingLibraries = libraries.stream().filter(Files::isRegularFile).toList();
        if (!existingLibraries.isEmpty()) {
            builder.libraries(existingLibraries.stream().map(Path::toFile).toArray(java.io.File[]::new));
        }

        builder.build().decompile();
    }

    private List<Path> downloadLibraries(MojangMeta.VersionJson version, Path libraryRoot) {
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
            try {
                result.add(download(artifact.url, dest, artifact.sha1));
            } catch (Exception e) {
                logger.warn("  Skipping library {}: {}", library.name, e.getMessage());
            }
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

    private YarnMapping findYarn(String minecraftVersion) throws Exception {
        YarnMapping legacy = yarnFromMeta(LEGACY_YARN_META + minecraftVersion, LEGACY_FABRIC_MAVEN, "legacy yarn");
        if (legacy != null) {
            return legacy;
        }
        return yarnFromMeta(FABRIC_YARN_META + minecraftVersion, FABRIC_MAVEN, "yarn");
    }

    private YarnMapping yarnFromMeta(String metaUrl, String mavenRoot, String label) throws Exception {
        String body;
        try {
            body = httpGetString(metaUrl);
        } catch (Exception e) {
            logger.info("  No {} metadata at {}: {}", label, metaUrl, e.getMessage());
            return null;
        }
        List<MojangMeta.YarnBuild> builds = gson.fromJson(body, new TypeToken<List<MojangMeta.YarnBuild>>() {}.getType());
        if (builds == null || builds.isEmpty()) {
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
        String relative = parts[0].replace('.', '/') + '/' + parts[1] + '/' + parts[2]
                + '/' + parts[1] + '-' + parts[2] + "-mergedv2.jar";
        Path yarnJar = cacheDir.resolve("yarn").resolve(parts[1] + '-' + parts[2] + "-mergedv2.jar");
        try {
            download(mavenRoot + relative, yarnJar, null);
        } catch (Exception e) {
            logger.warn("  Failed to download {} {}: {}", label, chosen.version, e.getMessage());
            return null;
        }

        Path tiny = cacheDir.resolve("yarn").resolve(parts[1] + '-' + parts[2] + ".tiny");
        extractNamedFile(yarnJar, "mappings/mappings.tiny", tiny);
        return new YarnMapping(label, chosen.version, tiny);
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

    private MojangMeta.VersionRef resolveVersion(String spec, MojangMeta.VersionManifest manifest) {
        String requested = spec.trim();
        if (requested.isEmpty()) {
            throw new GradleException("Empty Minecraft version spec.");
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

        List<MojangMeta.VersionRef> matches = manifest.versions.stream()
                .filter(version -> version.id.startsWith(requested + ".") || version.id.startsWith(requested + "-"))
                .toList();
        if (matches.isEmpty()) {
            throw new GradleException("Unknown Minecraft version '" + requested + "'.");
        }

        List<MojangMeta.VersionRef> releases = matches.stream()
                .filter(version -> "release".equals(version.type))
                .toList();
        List<MojangMeta.VersionRef> candidates = releases.isEmpty() ? matches : releases;
        return candidates.stream()
                .max(Comparator.comparing(version -> parseTime(version.releaseTime)))
                .orElseThrow(() -> new GradleException("Unknown Minecraft version '" + requested + "'."));
    }

    private static MojangMeta.VersionRef requireVersion(MojangMeta.VersionManifest manifest, String id) {
        return manifest.versions.stream()
                .filter(version -> Objects.equals(id, version.id))
                .findFirst()
                .orElseThrow(() -> new GradleException("Manifest is missing version '" + id + "'."));
    }

    private static Instant parseTime(String value) {
        if (value == null || value.isBlank()) {
            return Instant.EPOCH;
        }
        try {
            return Instant.parse(value);
        } catch (Exception e) {
            return Instant.EPOCH;
        }
    }

    private Path download(String url, Path dest, String expectedSha1) throws Exception {
        if (Files.isRegularFile(dest) && (expectedSha1 == null || expectedSha1.equalsIgnoreCase(sha1(dest)))) {
            return dest;
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
                if (response.statusCode() / 100 != 2) {
                    throw new IOException("HTTP " + response.statusCode() + " for " + url);
                }
                try (InputStream in = response.body()) {
                    Files.copy(in, temp, StandardCopyOption.REPLACE_EXISTING);
                }
                if (expectedSha1 != null && !expectedSha1.equalsIgnoreCase(sha1(temp))) {
                    throw new IOException("SHA-1 mismatch for " + dest.getFileName());
                }
                Files.move(temp, dest, StandardCopyOption.REPLACE_EXISTING);
                return dest;
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

    private String httpGetString(String url) throws Exception {
        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .timeout(Duration.ofSeconds(30))
                .header("User-Agent", "LegacyParkourCompat-decompileMinecraft")
                .GET()
                .build();
        HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        if (response.statusCode() / 100 != 2) {
            throw new IOException("HTTP " + response.statusCode() + " for " + url);
        }
        return response.body();
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

    private record YarnMapping(String label, String version, Path tinyFile) {
    }
}
