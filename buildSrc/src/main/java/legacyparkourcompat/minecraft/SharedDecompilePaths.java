package legacyparkourcompat.minecraft;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/** Resolves the primary checkout without depending on the invoking worktree's location. */
public final class SharedDecompilePaths {
    private SharedDecompilePaths() { }

    public static Path primaryCheckout(Path projectDirectory) {
        try {
            Path project = projectDirectory.toAbsolutePath().normalize();
            Path git = project.resolve(".git");
            if (Files.isRegularFile(git)) {
                String pointer = Files.readString(git).trim();
                if (!pointer.startsWith("gitdir: ")) {
                    throw new IOException("Invalid Git worktree pointer: " + git);
                }
                git = project.resolve(pointer.substring(8)).normalize();
            }
            Path commonPointer = git.resolve("commondir");
            Path common = Files.isRegularFile(commonPointer)
                    ? git.resolve(Files.readString(commonPointer).trim()).normalize() : git;
            if (!Files.isDirectory(common) || !common.getFileName().toString().equals(".git")) {
                throw new IOException("Cannot infer primary checkout from " + common
                        + "; specify --output-root and --cache-directory explicitly.");
            }
            return common.toRealPath().getParent();
        } catch (IOException e) {
            throw new IllegalStateException("Cannot resolve shared Minecraft source location", e);
        }
    }
}
