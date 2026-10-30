package legacyparkourcompat.minecraft;

import net.fabricmc.tinyremapper.IMappingProvider;
import net.fabricmc.tinyremapper.TinyUtils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InnerClassNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.commons.ClassRemapper;
import org.objectweb.asm.commons.Remapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

/**
 * Exact 1.8.1 Feather enum-switch helper correction from
 * {@code .native-source/20261030/181-enum-helper-allowlist/evidence.md},
 * with the null-inner-name/ACC_STATIC|ACC_SYNTHETIC contract in the paired audit report.
 */
final class EnumSwitchHelperRepair {
    static final String MAPPING_SHA256 = "dac40cbd25c4d76d99d4263fd9754ab0195efd658756b6828fd06f0c4a8002f7";
    static final String RAW_CLIENT_SHA256 = "66a749ad95adcb929439155c0341309e89e437444867e108dcb36fd16493b0bd";
    static final String MAPPED_CLIENT_CONTENT_SHA256 = "c7527d0279c8a24e70b5b4103c0606dc883ff840b5d5afb827edbe9bda4abaf7";
    static final String TINY_SHA256 = "f26e3fea2c580cf9eb2d5c47cd32a071f72177ba5e2c208c39196e669c8a383d";
    static final String PIPELINE_ID = "lpc-181-enum-helper-repair-v1;mapping-sha256=" + MAPPING_SHA256
            + ";raw-client-sha256=" + RAW_CLIENT_SHA256 + ";mapped-client-content-sha256=" + MAPPED_CLIENT_CONTENT_SHA256
            + ";tiny-sha256=" + TINY_SHA256;

    private static final List<Pair> PAIRS = List.of(
            p("32351573", "net/minecraft/block/BannerBlock", "asz", "asy"), p("81094094", "net/minecraft/block/ButtonBlock", "aub", "aua"),
            p("33107511", "net/minecraft/block/CocoaBlock", "auj", "aui"), p("37645622", "net/minecraft/block/entity/MovingBlockEntity", "bdu", "bdt"),
            p("10723286", "net/minecraft/block/FlowerPotBlock", "awb", "awa"), p("49464893", "net/minecraft/block/InfestedBlock", "axq", "axp"),
            p("59546086", "net/minecraft/block/LadderBlock", "axd", "axc"), p("77791932", "net/minecraft/block/LeverBlock", "axh", "axg"),
            p("28866629", "net/minecraft/block/LogBlock", "ayq", "ayo"), p("62405337", "net/minecraft/block/MushroomBlock", "awx", "aww"),
            p("58206234", "net/minecraft/block/QuartzBlock", "azk", "azj"), p("09110683", "net/minecraft/block/SkullBlock", "baj", "bah"),
            p("01367223", "net/minecraft/block/TorchBlock", "bbl", "bbj"), p("66929955", "net/minecraft/block/TripwireHookBlock", "bbs", "bbr"),
            p("15502813", "net/minecraft/client/gui/screen/menu/twitch/StreamUnavailableScreen", "cak", "caj"),
            p("33495983", "net/minecraft/client/network/PlayerInfo", "cet", "cer"),
            p("29788386", "net/minecraft/client/render/block/BlockModelRenderer", "clo", "cln"),
            p("83556887", "net/minecraft/client/render/ItemInHandRenderer", "ckj", "cki"),
            p("59463020", "net/minecraft/client/render/model/block/MiningQuad", "cmo", "cmn"),
            p("48070952", "net/minecraft/client/render/platform/GlStateManager", "cjn", "cjm"),
            p("84289903", "net/minecraft/client/render/vertex/BufferBuilder", "civ", "ciu"),
            p("14447104", "net/minecraft/client/render/vertex/BufferUploader", "ciy", "cix"),
            p("53981484", "net/minecraft/client/render/vertex/VertexFormat", "cut", "cus"),
            p("14330863", "net/minecraft/client/render/world/ChunkOcclusionGraph", "cot", "cos"),
            p("63976075", "net/minecraft/client/render/world/WorldRenderer", "ckp", "ckn"),
            p("85922567", "net/minecraft/client/resource/model/BlockElement", "clv", "clu"),
            p("67682539", "net/minecraft/client/resource/model/BlockModelShaper", "clk", "clc"),
            p("86087158", "net/minecraft/client/sound/system/SoundManager", "czk", "czh"),
            p("86014756", "net/minecraft/client/twitch/BroadcastController", "czx", "czu"),
            p("28691489", "net/minecraft/entity/living/player/PlayerEntity", "ahb", "aha"),
            p("65200581", "net/minecraft/entity/vehicle/MinecartEntity", "adz", "ady"), p("22169369", "net/minecraft/item/HoeItem", "alm", "all"),
            p("77500029", "net/minecraft/network/packet/s2c/play/PlayerCombatS2CPacket", "kg", "kf"),
            p("25347352", "net/minecraft/server/network/handler/RemoteHandshakeNetworkHandler", "rr", "rq"),
            p("41582934", "net/minecraft/world/biome/layer/AddEdgeLayer", "bok", "boj"),
            p("45169790", "net/minecraft/world/gen/structure/MineshaftPieces", "bjd", "bjc"),
            p("25820342", "net/minecraft/world/gen/structure/OceanMonumentPieces", "bkh", "bkg"),
            p("21168123", "net/minecraft/world/gen/structure/StrongholdPieces", "bls", "blp"),
            p("89926849", "net/minecraft/world/gen/structure/StructureBox", "bja", "biz"),
            p("04227101", "net/minecraft/world/gen/structure/StructurePiece", "bmr", "bmq"),
            p("64165767", "net/minecraft/world/gen/structure/VillagePieces", "bmx", "bmw")
    );

    // One row per accepted Vineflower log line, in order from 30514 through 30572.
    private static final List<Diagnostic> DIAGNOSTICS = List.of(
            d("client/resource/model/BlockElement", "getTextureCoords", "85922567"),
            d("client/render/vertex/BufferBuilder", "vertex", "84289903"),
            d("client/render/block/BlockModelRenderer", "computeFaceShape", "29788386"),
            d("world/gen/structure/MineshaftPieces__Corridor", "findSize", "45169790"),
            d("world/gen/structure/MineshaftPieces__Corridor", "addChildren", "45169790"),
            d("block/MushroomBlock", "getMapColor", "62405337"),
            d("client/sound/system/SoundManager", "register", "86087158"),
            d("client/render/model/block/MiningQuad", "calculateTextureCoords", "59463020"),
            d("block/BannerBlock__Wall", "updateShape", "32351573"),
            d("client/render/world/ChunkOcclusionGraph", "offset", "14330863"),
            d("block/FlowerPotBlock", "resolveVirtualProperties", "10723286"),
            d("block/FlowerPotBlock", "resolveVirtualProperties", "10723286"),
            d("block/QuartzBlock", "getPlacementState", "58206234"),
            d("block/LadderBlock", "updateShape", "59546086"),
            d("world/gen/structure/VillagePieces__Well", "<init>", "64165767"),
            d("world/biome/layer/AddEdgeLayer", "getArea", "41582934"),
            d("world/gen/structure/OceanMonumentPieces__OceanMonument", "<init>", "25820342"),
            d("world/gen/structure/StructureBox", "orient", "89926849"),
            d("block/CocoaBlock", "updateShape", "33107511"),
            d("client/network/PlayerInfo__71417623", "textureAvailable", "33495983"),
            d("block/ButtonBlock", "updateShape", "81094094"),
            d("block/ButtonBlock", "getMetadataFromState", "81094094"),
            d("entity/living/player/PlayerEntity", "trySleep", "28691489"),
            d("entity/living/player/PlayerEntity", "setSleepingCameraOffset", "28691489"),
            d("entity/living/player/PlayerEntity", "getSleepingCameraAngle", "28691489"),
            d("block/SkullBlock", "updateShape", "09110683"),
            d("world/gen/structure/StrongholdPieces__EndPortalRoom", "postProcess", "21168123"),
            d("client/render/world/WorldRenderer", "renderLastChunks", "63976075"),
            d("client/render/ItemInHandRenderer", "renderInFirstPerson", "83556887"),
            d("world/gen/structure/StructurePiece", "transformX", "04227101"),
            d("world/gen/structure/StructurePiece", "transformZ", "04227101"),
            d("client/render/vertex/VertexFormat", "add", "53981484"),
            d("client/resource/model/BlockModelShaper__50530242", "getModelLocation", "67682539"),
            d("block/entity/MovingBlockEntity", "moveEntities", "37645622"),
            d("client/gui/screen/menu/twitch/StreamUnavailableScreen", "buttonClicked", "15502813"),
            d("client/gui/screen/menu/twitch/StreamUnavailableScreen", "m_90754652", "15502813"),
            d("client/gui/screen/menu/twitch/StreamUnavailableScreen", "m_90754652", "15502813"),
            d("world/gen/structure/MineshaftPieces__Crossing", "findSize", "45169790"),
            d("world/gen/structure/MineshaftPieces__Crossing", "addChildren", "45169790"),
            d("entity/vehicle/MinecartEntity", "create", "65200581"),
            d("entity/vehicle/MinecartEntity", "moveOnRail", "65200581"),
            d("block/TripwireHookBlock", "updateShape", "66929955"),
            d("world/gen/structure/MineshaftPieces__Stairs", "findSize", "45169790"),
            d("world/gen/structure/MineshaftPieces__Stairs", "addChildren", "45169790"),
            d("block/LeverBlock__Facing", "byAttachmentAndFacing", "77791932"),
            d("block/LeverBlock__Facing", "byAttachmentAndFacing", "77791932"),
            d("block/LeverBlock__Facing", "byAttachmentAndFacing", "77791932"),
            d("client/render/vertex/BufferUploader", "end", "14447104"),
            d("client/render/vertex/BufferUploader", "end", "14447104"),
            d("server/network/handler/RemoteHandshakeNetworkHandler", "handleHandshake", "25347352"),
            d("block/LogBlock", "getMapColor", "28866629"),
            d("block/LogBlock", "getMapColor", "28866629"),
            d("block/LogBlock", "getMetadataFromState", "28866629"),
            d("network/packet/s2c/play/PlayerCombatS2CPacket", "<init>", "77500029"),
            d("client/twitch/BroadcastController", "update", "86014756"),
            d("client/render/platform/GlStateManager", "getTexGenCoord", "48070952"),
            d("item/HoeItem", "useOn", "22169369"),
            d("block/InfestedBlock", "getSilkTouchDrop", "49464893"),
            d("block/TorchBlock", "getMetadataFromState", "01367223")
    );

    private EnumSwitchHelperRepair() { }

    private static Pair p(String suffix, String owner, String rawHelper, String rawOwner) {
        return new Pair("net/minecraft/unmapped/C_" + suffix, rawHelper, rawOwner,
                owner + "__" + suffix, owner + "$" + suffix, owner);
    }

    private static Diagnostic d(String ownerTail, String method, String helperSuffix) {
        return new Diagnostic("net/minecraft/" + ownerTail, method,
                PAIRS.stream().filter(pair -> pair.canonical.endsWith("$" + helperSuffix)).findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Unknown helper " + helperSuffix)));
    }

    static IMappingProvider provider(Path tiny, String sourceNamespace, String targetNamespace, Path inputJar,
                                     Path mappingJar) throws IOException {
        verifyMappingArtifact(mappingJar);
        IMappingProvider delegate = TinyUtils.createTinyMappingProvider(tiny, sourceNamespace, targetNamespace);
        Map<String, Pair> bySource = new HashMap<>();
        for (Pair pair : PAIRS) { bySource.put(pair.intermediary, pair); bySource.put(pair.rawHelper, pair); }
        Map<String, String> observed = new HashMap<>();
        Map<String, String> sourceByNamedClass = new HashMap<>();
        return acceptor -> {
            delegate.load(new IMappingProvider.MappingAcceptor() {
                @Override public void acceptClass(String srcName, String dstName) {
                    sourceByNamedClass.put(dstName, srcName);
                    Pair pair = bySource.get(srcName);
                    if (pair != null) {
                        if (!pair.flat.equals(dstName)) throw new IllegalStateException("Audited Tiny row changed for " + srcName);
                        if (observed.put(srcName, dstName) != null) throw new IllegalStateException("Duplicate audited Tiny row " + srcName);
                        acceptor.acceptClass(srcName, pair.canonical);
                    } else acceptor.acceptClass(srcName, dstName);
                }
                @Override public void acceptMethod(IMappingProvider.Member method, String dstName) { acceptor.acceptMethod(method, dstName); }
                @Override public void acceptMethodArg(IMappingProvider.Member method, int lvIndex, String dstName) { acceptor.acceptMethodArg(method, lvIndex, dstName); }
                @Override public void acceptMethodVar(IMappingProvider.Member method, int lvIndex, int startOpIdx, int asmIndex, String dstName) {
                    acceptor.acceptMethodVar(method, lvIndex, startOpIdx, asmIndex, dstName);
                }
                @Override public void acceptField(IMappingProvider.Member field, String dstName) { acceptor.acceptField(field, dstName); }
            });
            if (observed.size() != PAIRS.size()) throw new IllegalStateException("Expected all 41 audited Tiny helper rows, found " + observed.size());
            try { verifyIntermediaryJar(inputJar, sourceByNamedClass); }
            catch (IOException error) { throw new IllegalStateException("1.8.1 enum-helper input gate failed", error); }
        };
    }

    static void verifyMappingArtifact(Path mappingJar) throws IOException {
        String actualHash = DecompileStore.hash(mappingJar);
        if (!MAPPING_SHA256.equals(actualHash)) {
            throw new IOException("1.8.1 enum-helper repair requires the audited Feather mapping artifact; found " + actualHash);
        }
    }

    static void verifyPinnedInputs(Path rawClientJar, Path tinyMappings, Path mappingJar) throws IOException {
        verifyMappingArtifact(mappingJar);
        String rawHash = DecompileStore.hash(rawClientJar);
        if (!RAW_CLIENT_SHA256.equals(rawHash)) throw new IOException("1.8.1 enum-helper repair requires the audited client.jar; found " + rawHash);
        String tinyHash = DecompileStore.hash(tinyMappings);
        if (!TINY_SHA256.equals(tinyHash)) throw new IOException("1.8.1 enum-helper repair requires the audited Feather Tiny mapping; found " + tinyHash);
        verifyRawSourceBeforeRemap(rawClientJar);
    }

    static void verifyOrdinaryRemap(Path ordinaryMappedJar) throws IOException {
        String actualHash = archiveContentHash(ordinaryMappedJar);
        if (!MAPPED_CLIENT_CONTENT_SHA256.equals(actualHash))
            throw new IOException("Ordinary TinyRemapper archive contents differ from the audited mapped jar; found " + actualHash);
    }

    /** Hashes sorted ZIP paths and uncompressed contents, excluding container metadata such as timestamps. */
    private static String archiveContentHash(Path jar) throws IOException {
        Map<String, byte[]> entries = new TreeMap<>();
        try (JarFile archive = new JarFile(jar.toFile())) {
            var enumeration = archive.entries();
            while (enumeration.hasMoreElements()) {
                JarEntry entry = enumeration.nextElement();
                if (entries.containsKey(entry.getName())) throw new IOException("Duplicate archive entry " + entry.getName());
                try (var stream = archive.getInputStream(entry)) { entries.put(entry.getName(), stream.readAllBytes()); }
            }
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update("LPC-JAR-CONTENT-v1\0".getBytes(StandardCharsets.UTF_8));
            for (var entry : entries.entrySet()) {
                byte[] name = entry.getKey().getBytes(StandardCharsets.UTF_8);
                updateInt(digest, name.length);
                digest.update(name);
                updateLong(digest, entry.getValue().length);
                digest.update(entry.getValue());
            }
            return java.util.HexFormat.of().formatHex(digest.digest());
        } catch (NoSuchAlgorithmException impossible) {
            throw new IllegalStateException("SHA-256 is unavailable", impossible);
        }
    }

    private static void updateInt(MessageDigest digest, int value) {
        digest.update((byte) (value >>> 24)); digest.update((byte) (value >>> 16));
        digest.update((byte) (value >>> 8)); digest.update((byte) value);
    }

    private static void updateLong(MessageDigest digest, long value) {
        for (int shift = 56; shift >= 0; shift -= 8) digest.update((byte) (value >>> shift));
    }

    private static void verifyRawSourceBeforeRemap(Path rawClientJar) throws IOException {
        Map<String, String> rawSourcesByNamedClass = new HashMap<>();
        for (Pair pair : PAIRS) {
            rawSourcesByNamedClass.put(pair.flat, pair.rawHelper);
            rawSourcesByNamedClass.put(pair.owner, pair.rawOwner);
        }
        verifyIntermediaryJar(rawClientJar, rawSourcesByNamedClass);
    }

    static void addComparatorMetadata(Path baselineJar, Path repairedJar) throws IOException {
        Set<String> expectedChanged = new HashSet<>();
        for (Pair pair : PAIRS) {
            expectedChanged.add(pair.canonical + ".class");
            expectedChanged.add(pair.owner + ".class");
        }
        Path staged = repairedJar.resolveSibling(repairedJar.getFileName() + ".metadata.tmp");
        try (JarFile input = new JarFile(repairedJar.toFile()); JarOutputStream output = new JarOutputStream(Files.newOutputStream(staged))) {
            var entries = input.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                JarEntry copy = new JarEntry(entry.getName());
                if (entry.getTime() >= 0) copy.setTime(entry.getTime());
                if (entry.getComment() != null) copy.setComment(entry.getComment());
                if (entry.getExtra() != null) copy.setExtra(entry.getExtra());
                copy.setMethod(entry.getMethod());
                if (entry.getMethod() == JarEntry.STORED) {
                    copy.setSize(entry.getSize()); copy.setCompressedSize(entry.getCompressedSize()); copy.setCrc(entry.getCrc());
                }
                output.putNextEntry(copy);
                byte[] bytes;
                try (var stream = input.getInputStream(entry)) { bytes = stream.readAllBytes(); }
                if (entry.getName().endsWith(".class") && expectedChanged.contains(entry.getName())) {
                    bytes = metadata(bytes, entry.getName());
                }
                output.write(bytes);
                output.closeEntry();
            }
        }
        Files.move(staged, repairedJar, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        verifyArchiveDelta(baselineJar, repairedJar);
    }

    private static byte[] metadata(byte[] bytes, String entry) throws IOException {
        ClassNode node = new ClassNode();
        new ClassReader(bytes).accept(node, 0);
        if ((node.attrs != null && !node.attrs.isEmpty())
                || node.fields.stream().anyMatch(field -> field.attrs != null && !field.attrs.isEmpty())
                || node.methods.stream().anyMatch(method -> method.attrs != null && !method.attrs.isEmpty())) {
            throw new IOException("Unsupported custom attributes in enum-helper metadata repair: " + entry);
        }
        Pair pair = PAIRS.stream().filter(candidate -> (candidate.canonical + ".class").equals(entry)
                || (candidate.owner + ".class").equals(entry)).findFirst().orElseThrow();
        if (node.name.equals(pair.canonical) && (node.access & Opcodes.ACC_SYNTHETIC) == 0)
            throw new IOException("Expected synthetic enum helper " + entry);
        if (node.innerClasses.stream().anyMatch(inner -> pair.canonical.equals(inner.name))) {
            throw new IOException("Conflicting pre-existing nesting metadata in " + entry);
        }
        node.innerClasses.add(new InnerClassNode(pair.canonical, pair.owner, null,
                Opcodes.ACC_STATIC | Opcodes.ACC_SYNTHETIC));
        if (node.name.equals(pair.canonical)) {
            if (node.outerClass != null || node.outerMethod != null || node.outerMethodDesc != null)
                throw new IOException("Conflicting EnclosingMethod metadata in " + entry);
            node.outerClass = pair.owner;
            node.outerMethod = null;
            node.outerMethodDesc = null;
        }
        ClassWriter writer = new ClassWriter(0);
        node.accept(writer);
        return writer.toByteArray();
    }

    private static void verifyIntermediaryJar(Path jar, Map<String, String> sourceByNamedClass) throws IOException {
        try (JarFile archive = new JarFile(jar.toFile())) {
            for (Pair pair : PAIRS) {
                if (archive.getJarEntry(pair.canonical + ".class") != null)
                    throw new IOException("Canonical helper destination already exists before remapping: " + pair.canonical);
                String helperName = sourceByNamedClass.getOrDefault(pair.flat, pair.intermediary);
                String ownerName = sourceByNamedClass.getOrDefault(pair.owner, pair.rawOwner);
                ClassNode helper = read(archive, helperName);
                if ((helper.access & Opcodes.ACC_SYNTHETIC) == 0 || helper.innerClasses.size() != 0
                        || helper.outerClass != null || helper.outerMethod != null || helper.outerMethodDesc != null)
                    throw new IOException("Unexpected helper shape " + helperName);
                if (helper.fields.stream().filter(field -> field.desc.equals("[I")).anyMatch(field -> !switchField(field)))
                    throw new IOException("Unexpected non-synthetic [I field in " + helperName);
                if (helper.fields.stream().noneMatch(EnumSwitchHelperRepair::switchField)) throw new IOException("Missing synthetic [I switch field " + pair.intermediary);
                MethodNode clinit = helper.methods.stream().filter(method -> method.name.equals("<clinit>")).findFirst()
                        .orElseThrow(() -> new IOException("Missing enum switch initializer " + pair.intermediary));
                boolean ordinal = false, values = false;
                for (var instruction : clinit.instructions) {
                    if (instruction instanceof org.objectweb.asm.tree.MethodInsnNode call) {
                        ordinal |= call.name.equals("ordinal") && call.desc.equals("()I");
                        values |= call.name.equals("values") && call.desc.startsWith("()[L");
                    }
                }
                boolean missingEnumCase = clinit.tryCatchBlocks.stream().anyMatch(block -> "java/lang/NoSuchFieldError".equals(block.type));
                if (!ordinal || !values || !missingEnumCase) throw new IOException("Non-enum synthetic helper shape " + helperName);
                ClassNode owner = read(archive, ownerName);
                if (owner.innerClasses.stream().anyMatch(inner -> pair.canonical.equals(inner.name)))
                    throw new IOException("Owner already has target InnerClasses record " + pair.owner);
                Set<String> helperFields = new HashSet<>();
                for (FieldNode field : helper.fields) {
                    if (field.desc.equals("[I")) helperFields.add(field.name);
                }
                boolean referencedByClient = false;
                var entries = archive.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    if (entry.isDirectory() || !entry.getName().endsWith(".class")
                            || entry.getName().equals(helperName + ".class")) continue;
                    ClassNode consumer = read(archive, entry.getName().substring(0, entry.getName().length() - 6));
                    for (MethodNode method : consumer.methods) {
                        for (var instruction : method.instructions) {
                            if (instruction instanceof org.objectweb.asm.tree.FieldInsnNode field
                                    && field.getOpcode() == Opcodes.GETSTATIC && field.owner.equals(helperName)) {
                                if (!field.desc.equals("[I") || !helperFields.contains(field.name))
                                    throw new IOException("Unexpected enum-helper field binding in " + consumer.name
                                            + " -> " + helperName + "." + field.name + field.desc);
                                referencedByClient = true;
                            }
                        }
                    }
                }
                if (!referencedByClient) throw new IOException("No client class references the audited enum-switch helper " + pair.flat);
            }
        }
    }

    static void verifyDiagnosticBindings(Path ordinarilyMappedJar) throws IOException {
        if (DIAGNOSTICS.size() != 59) throw new IOException("Expected all 59 audited Vineflower diagnostic bindings");
        try (JarFile archive = new JarFile(ordinarilyMappedJar.toFile())) {
            for (Diagnostic diagnostic : DIAGNOSTICS) {
                Pair helper = diagnostic.helper;
                if (archive.getJarEntry(helper.canonical + ".class") != null)
                    throw new IOException("Canonical helper collision after ordinary mapping: " + helper.canonical);
                ClassNode owner = read(archive, diagnostic.owner);
                ClassNode helperClass = read(archive, helper.flat);
                Set<String> declaredHelperFields = new HashSet<>();
                for (FieldNode field : helperClass.fields) {
                    if (field.desc.equals("[I")) declaredHelperFields.add(field.name);
                }
                if (declaredHelperFields.isEmpty()) throw new IOException("Missing helper switch fields: " + helper.flat);
                List<MethodNode> namedMethods = owner.methods.stream().filter(method -> method.name.equals(diagnostic.method)).toList();
                List<MethodNode> matches = namedMethods.stream()
                        .filter(method -> referencesExpectedSwitchHelper(method, helper.flat)).toList();
                if (matches.size() != 1) throw new IOException("Diagnostic method is absent or helper binding is ambiguous: "
                        + diagnostic.owner + " " + diagnostic.method + " (named methods=" + namedMethods.size()
                        + ", matching bindings=" + matches.size() + ")");
                Set<String> switchOwners = new HashSet<>();
                Set<String> referencedFields = new HashSet<>();
                for (var instruction : matches.get(0).instructions) {
                    if (instruction instanceof org.objectweb.asm.tree.FieldInsnNode field
                            && field.getOpcode() == Opcodes.GETSTATIC && field.desc.equals("[I")) {
                        if (isSyntheticSwitchHelper(archive, field.owner)) switchOwners.add(field.owner);
                        if (field.owner.equals(helper.flat)) referencedFields.add(field.name);
                    }
                }
                if (!switchOwners.equals(Set.of(helper.flat)) || referencedFields.isEmpty()
                        || !declaredHelperFields.containsAll(referencedFields))
                    throw new IOException("Diagnostic method does not bind exclusively to the audited helper arrays: "
                            + diagnostic.owner + " " + diagnostic.method + " -> " + helper.flat);
            }
        }
    }

    private static boolean referencesExpectedSwitchHelper(MethodNode method, String helperName) {
        for (var instruction : method.instructions) {
            if (instruction instanceof org.objectweb.asm.tree.FieldInsnNode field
                    && field.getOpcode() == Opcodes.GETSTATIC && field.desc.equals("[I")
                    && field.owner.equals(helperName)) return true;
        }
        return false;
    }

    private static boolean isSyntheticSwitchHelper(JarFile archive, String className) throws IOException {
        JarEntry entry = archive.getJarEntry(className + ".class");
        if (entry == null) return false;
        ClassNode node = read(archive, className);
        return (node.access & Opcodes.ACC_SYNTHETIC) != 0
                && node.fields.stream().anyMatch(EnumSwitchHelperRepair::switchField);
    }

    private static boolean switchField(FieldNode field) {
        int required = Opcodes.ACC_STATIC | Opcodes.ACC_FINAL | Opcodes.ACC_SYNTHETIC;
        return field.desc.equals("[I") && (field.access & required) == required;
    }

    private static ClassNode read(JarFile jar, String name) throws IOException {
        JarEntry entry = jar.getJarEntry(name + ".class");
        if (entry == null) throw new IOException("Missing audited class " + name);
        ClassNode node = new ClassNode();
        try (var stream = jar.getInputStream(entry)) { new ClassReader(stream).accept(node, 0); }
        if (!node.name.equals(name)) throw new IOException("Archive/class identity mismatch for " + name);
        return node;
    }

    private static void verifyArchiveDelta(Path baseline, Path repaired) throws IOException {
        try (JarFile before = new JarFile(baseline.toFile()); JarFile after = new JarFile(repaired.toFile())) {
            Set<String> expectedPathDelta = new HashSet<>();
            for (Pair pair : PAIRS) {
                if (before.getJarEntry(pair.flat + ".class") == null || after.getJarEntry(pair.flat + ".class") != null
                        || before.getJarEntry(pair.canonical + ".class") != null || after.getJarEntry(pair.canonical + ".class") == null)
                    throw new IOException("Unexpected helper identity/path delta for " + pair.flat);
                if (!java.util.Arrays.equals(structuralBytes(before, pair.flat + ".class", false),
                        structuralBytes(after, pair.canonical + ".class", true)))
                    throw new IOException("Helper code/fields/attributes changed beyond the approved nested identity: " + pair.flat);
                ClassNode helper = read(after, pair.canonical);
                ClassNode owner = read(after, pair.owner);
                if ((helper.access & Opcodes.ACC_SYNTHETIC) == 0 || helper.innerClasses.stream().filter(inner -> pair.canonical.equals(inner.name)
                        && pair.owner.equals(inner.outerName) && inner.innerName == null
                        && inner.access == (Opcodes.ACC_STATIC | Opcodes.ACC_SYNTHETIC)).count() != 1
                        || !pair.owner.equals(helper.outerClass) || helper.outerMethod != null || helper.outerMethodDesc != null
                        || owner.innerClasses.stream().filter(inner -> pair.canonical.equals(inner.name)
                        && pair.owner.equals(inner.outerName) && inner.innerName == null
                        && inner.access == (Opcodes.ACC_STATIC | Opcodes.ACC_SYNTHETIC)).count() != 1)
                    throw new IOException("Comparator-matched nesting metadata missing or conflicting for " + pair.canonical);
                expectedPathDelta.add(pair.flat + ".class");
                expectedPathDelta.add(pair.canonical + ".class");
            }
            Set<String> names = new HashSet<>();
            before.stream().forEach(entry -> names.add(entry.getName()));
            after.stream().forEach(entry -> names.add(entry.getName()));
            for (String name : names) {
                var left = before.getJarEntry(name); var right = after.getJarEntry(name);
                if (name.endsWith(".class")) {
                    if ((left == null || right == null) && !expectedPathDelta.contains(name))
                        throw new IOException("Unexpected class archive path delta " + name);
                    if (left != null && right != null && !java.util.Arrays.equals(
                            structuralBytes(before, name, false), structuralBytes(after, name, true)))
                        throw new IOException("Class structure changed beyond helper identity references/approved nesting metadata: " + name);
                    if (right != null) try (var stream = after.getInputStream(right)) {
                        ClassReader reader = new ClassReader(stream);
                        Set<String> flatNames = new HashSet<>();
                        for (Pair pair : PAIRS) flatNames.add(pair.flat);
                        Remapper rejectFlatNames = new Remapper() {
                            @Override public String map(String internalName) {
                                if (flatNames.contains(internalName)) throw new IllegalStateException("Lingering flat helper reference " + internalName);
                                return internalName;
                            }
                        };
                        try { reader.accept(new ClassRemapper(new ClassVisitor(Opcodes.ASM9) { }, rejectFlatNames), 0); }
                        catch (IllegalStateException error) { throw new IOException("Unrewritten helper reference in " + name, error); }
                    }
                    continue;
                }
                if (left == null || right == null) throw new IOException("Unexpected non-class archive entry delta " + name);
                try (var a = before.getInputStream(left); var b = after.getInputStream(right)) {
                    if (!java.util.Arrays.equals(a.readAllBytes(), b.readAllBytes())) throw new IOException("Unexpected non-class content delta " + name);
                }
            }
            Set<String> missing = new HashSet<>(); Set<String> added = new HashSet<>();
            before.stream().map(JarEntry::getName).filter(name -> !after.stream().anyMatch(entry -> entry.getName().equals(name))).forEach(missing::add);
            after.stream().map(JarEntry::getName).filter(name -> !before.stream().anyMatch(entry -> entry.getName().equals(name))).forEach(added::add);
            Set<String> expectedMissing = new HashSet<>(); Set<String> expectedAdded = new HashSet<>();
            for (Pair pair : PAIRS) { expectedMissing.add(pair.flat + ".class"); expectedAdded.add(pair.canonical + ".class"); }
            if (!missing.equals(expectedMissing) || !added.equals(expectedAdded)) throw new IOException("Unexpected archive path set changes");
        }
    }

    private static byte[] structuralBytes(JarFile archive, String entryName, boolean repaired) throws IOException {
        String entryClassName = entryName.substring(0, entryName.length() - ".class".length());
        String className = entryClassName;
        Pair moved = repaired ? PAIRS.stream().filter(pair -> pair.canonical.equals(entryClassName)).findFirst().orElse(null) : null;
        if (moved != null) className = moved.flat;
        ClassNode parsed = new ClassNode();
        try (var stream = archive.getInputStream(archive.getJarEntry(entryName))) { new ClassReader(stream).accept(parsed, 0); }
        ClassNode normalized;
        if (repaired) {
            Map<String, String> reverse = new HashMap<>();
            for (Pair pair : PAIRS) reverse.put(pair.canonical, pair.flat);
            normalized = new ClassNode();
            parsed.accept(new ClassRemapper(normalized, new Remapper() {
                @Override public String map(String internalName) { return reverse.getOrDefault(internalName, internalName); }
            }));
            for (Pair pair : PAIRS) {
                normalized.innerClasses.removeIf(inner -> (pair.canonical.equals(inner.name) || pair.flat.equals(inner.name))
                        && pair.owner.equals(inner.outerName) && inner.innerName == null
                        && inner.access == (Opcodes.ACC_STATIC | Opcodes.ACC_SYNTHETIC));
                if (pair.canonical.equals(parsed.name)) {
                    normalized.outerClass = null; normalized.outerMethod = null; normalized.outerMethodDesc = null;
                }
            }
        } else normalized = parsed;
        if (!normalized.name.equals(className)) throw new IOException("Class identity mismatch while verifying " + entryName);
        ClassWriter writer = new ClassWriter(0);
        normalized.accept(writer);
        return writer.toByteArray();
    }

    private record Pair(String intermediary, String rawHelper, String rawOwner, String flat, String canonical, String owner) { }
    private record Diagnostic(String owner, String method, Pair helper) { }
}
