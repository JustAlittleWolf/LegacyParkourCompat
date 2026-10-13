package legacyparkourcompat.minecraft;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

/** Small local protocol: exclusive writers, content manifests, staged publication. */
final class DecompileStore {
    static final String MANIFEST = ".decompile-provenance.json";
    static final String PIPELINE = "lpc-decompile-v1;threads=1;generic=true;ascii=true;synthetic=remove;"
            + "indent=4;inner-warnings=false;runtime=false;prefix=net/minecraft,com/mojang;"
            + "remap=ignoreConflicts,renameInvalidLocals,rebuildSourceFilenames,fixPackageAccess";
    private static final Gson JSON = new Gson();

    record Manifest(String pipeline, String runtime, Map<String, String> tools,
                    Map<String, String> inputs, Map<String, String> sources) { }
    record Pin(String url, String sha256) { }

    static final class Writer implements AutoCloseable {
        private final FileChannel channel;
        private final FileLock lock;
        Writer(Path root) throws IOException {
            Files.createDirectories(root);
            channel = FileChannel.open(root.resolve(".decompile-writer.lock"),
                    StandardOpenOption.CREATE, StandardOpenOption.WRITE);
            FileLock acquired;
            try {
                acquired = channel.tryLock();
                if (acquired == null) throw new IOException("Another decompile writer owns " + root);
            } catch (IOException | RuntimeException error) {
                channel.close();
                throw error;
            }
            lock = acquired;
        }
        public void close() throws IOException {
            try { lock.release(); } finally { channel.close(); }
        }
    }

    static String hash(Path file) throws IOException {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            try (InputStream input = Files.newInputStream(file)) {
                byte[] buffer = new byte[8192];
                int count;
                while ((count = input.read(buffer)) != -1) digest.update(buffer, 0, count);
            }
            return HexFormat.of().formatHex(digest.digest());
        } catch (java.security.NoSuchAlgorithmException error) {
            throw new IllegalStateException(error);
        }
    }

    static Map<String, String> tree(Path root, String suffix) throws IOException {
        Map<String, String> hashes = new TreeMap<>();
        try (var paths = Files.walk(root)) {
            for (Path path : paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(suffix)).sorted().toList()) {
                hashes.put(root.relativize(path).toString().replace('\\', '/'), hash(path));
            }
        }
        return hashes;
    }

    static Map<String, String> tools() throws IOException {
        Map<String, String> hashes = new TreeMap<>();
        for (String entry : System.getProperty("java.class.path").split(java.io.File.pathSeparator)) {
            Path path = Path.of(entry);
            if (Files.isDirectory(path)) {
                tree(path, ".class").forEach((name, hash) -> hashes.put("class:" + name, hash));
            } else if (Files.isRegularFile(path)) {
                hashes.put("jar:" + path.getFileName(), hash(path));
            } else throw new IOException("Missing decompiler classpath entry " + path);
        }
        return hashes;
    }

    static Manifest provenance(Map<String, String> inputs, Map<String, String> tools, Path sources)
            throws IOException {
        return new Manifest(PIPELINE, System.getProperty("java.vendor") + ":" + Runtime.version()
                + ":" + System.getProperty("os.name") + ":" + System.getProperty("os.arch"),
                tools, new TreeMap<>(inputs), tree(sources, ".java"));
    }

    static Manifest read(Path output) throws IOException {
        try {
            Manifest manifest = JSON.fromJson(Files.readString(output.resolve(MANIFEST)), Manifest.class);
            if (manifest == null || manifest.pipeline == null || manifest.runtime == null
                    || manifest.tools == null || manifest.inputs == null || manifest.sources == null
                    || manifest.sources.isEmpty()) throw new IOException("Incomplete provenance in " + output);
            return manifest;
        } catch (RuntimeException error) {
            throw new IOException("Invalid provenance in " + output, error);
        }
    }

    static void write(Path staging, Manifest manifest) throws IOException {
        Files.writeString(staging.resolve(MANIFEST), JSON.toJson(manifest));
    }

    static void publish(Path staging, Path output) throws IOException {
        Path previous = output.resolveSibling("." + output.getFileName() + ".previous-" + UUID.randomUUID());
        boolean existed = Files.exists(output);
        if (existed) Files.move(output, previous, StandardCopyOption.ATOMIC_MOVE);
        try {
            Files.move(staging, output, StandardCopyOption.ATOMIC_MOVE);
        } catch (IOException error) {
            if (existed) {
                try { Files.move(previous, output, StandardCopyOption.ATOMIC_MOVE); }
                catch (IOException rollback) { error.addSuppressed(rollback); }
            }
            throw error;
        }
        // Retain the former tree for evidence and crash recovery, including legacy output.
    }

    static void pin(Path file, String url, Map<String, String> inputs) throws IOException {
        Path marker = file.resolveSibling(file.getFileName() + ".pin.json");
        Pin actual = new Pin(url, hash(file));
        if (Files.exists(marker)) {
            Pin pinned;
            try { pinned = JSON.fromJson(Files.readString(marker), Pin.class); }
            catch (RuntimeException error) { throw new IOException("Invalid cache pin " + marker, error); }
            if (!actual.equals(pinned)) throw new IOException("Pinned input changed: " + file);
        } else {
            Files.writeString(marker, JSON.toJson(actual));
        }
        inputs.put(url, actual.sha256);
    }
}
