package legacyparkourcompat.minecraft;

import org.jetbrains.java.decompiler.main.extern.IResultSaver;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.jar.Manifest;

/**
 * Writes only decompiled {@code .java} files, skipping jar resources such as assets.
 */
final class JavaDirectorySaver implements IResultSaver {
    private final Path root;

    JavaDirectorySaver(Path root) {
        this.root = root;
    }

    @Override
    public void saveFolder(String path) {
        createDirectories(resolve(path));
    }

    @Override
    public void copyFile(String source, String path, String entryName) {
        // Minecraft client jars include assets; keep the output as sources only.
    }

    @Override
    public void saveClassFile(String path, String qualifiedName, String entryName, String content, int[] mapping) {
        write(resolve(path).resolve(entryName), content);
    }

    @Override
    public void createArchive(String path, String archiveName, Manifest manifest) {
    }

    @Override
    public void saveDirEntry(String path, String archiveName, String entryName) {
        createDirectories(resolve(entryName));
    }

    @Override
    public void copyEntry(String source, String path, String archiveName, String entry) {
    }

    @Override
    public void saveClassEntry(String path, String archiveName, String qualifiedName, String entryName, String content) {
        write(resolve(entryName), content);
    }

    @Override
    public void closeArchive(String path, String archiveName) {
    }

    private Path resolve(String relative) {
        String normalized = relative.replace('\\', '/');
        while (normalized.startsWith("/")) {
            normalized = normalized.substring(1);
        }
        if (normalized.isEmpty() || ".".equals(normalized)) {
            return root;
        }
        return root.resolve(normalized);
    }

    private static void createDirectories(Path path) {
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create " + path, e);
        }
    }

    private static void write(Path file, String content) {
        if (content == null) {
            return;
        }
        try {
            Path parent = file.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
            Files.writeString(file, content, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write " + file, e);
        }
    }
}
