package legacyparkourcompat.minecraft;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Forked entry point so Vineflower runs on the Java 25 toolchain JVM.
 */
public final class MinecraftDecompileMain {
    private MinecraftDecompileMain() {
    }

    public static void main(String[] args) {
        if (args.length < 4) {
            System.err.println("Usage: MinecraftDecompileMain <cacheDir> <outputRoot> --protected-root=<path>... "
                    + "--mappings=<auto|mojmap|legacy-yarn|yarn|feather|unobfuscated> <version> [<version>...]");
            System.exit(2);
        }

        System.out.println("Decompiler JVM " + Runtime.version());
        Path cacheDir = Path.of(args[0]);
        Path outputRoot = Path.of(args[1]);
        List<String> versions = new ArrayList<>();
        List<String> mappings = new ArrayList<>();
        List<Path> protectedRoots = new ArrayList<>();
        for (int i = 2; i < args.length; i++) {
            if (args[i].startsWith("--mappings=")) {
                mappings.addAll(splitVersions(args[i].substring("--mappings=".length())));
            } else if (args[i].startsWith("--protected-root=")) {
                protectedRoots.add(Path.of(args[i].substring("--protected-root=".length())));
            } else {
                versions.addAll(splitVersions(args[i]));
            }
        }
        if (mappings.isEmpty() || protectedRoots.isEmpty() || versions.isEmpty()) {
            System.err.println("Minecraft decompilation requires mappings, protected publication roots, and versions.");
            System.exit(2);
        }
        try {
            new MinecraftDecompileEngine(new StdDecompileLogger(), cacheDir, outputRoot, mappings, protectedRoots)
                    .decompile(versions);
        } catch (RuntimeException e) {
            e.printStackTrace(System.err);
            System.exit(1);
        }
    }

    static List<String> splitVersions(String value) {
        return Arrays.stream(value.split("[,\\s]+"))
                .map(String::trim)
                .filter(part -> !part.isEmpty())
                .toList();
    }
}
