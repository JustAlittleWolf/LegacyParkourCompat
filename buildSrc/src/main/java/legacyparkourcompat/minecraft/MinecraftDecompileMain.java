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
        if (args.length < 3) {
            System.err.println("Usage: MinecraftDecompileMain <cacheDir> <outputRoot> <version> [<version>...]");
            System.exit(2);
        }

        System.out.println("Decompiler JVM " + Runtime.version());
        Path cacheDir = Path.of(args[0]);
        Path outputRoot = Path.of(args[1]);
        List<String> versions = new ArrayList<>();
        for (int i = 2; i < args.length; i++) {
            versions.addAll(splitVersions(args[i]));
        }
        try {
            new MinecraftDecompileEngine(new StdDecompileLogger(), cacheDir, outputRoot).decompile(versions);
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
