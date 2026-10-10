package legacyparkourcompat.minecraft;

import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Classpath;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.Nested;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.options.Option;
import org.gradle.jvm.toolchain.JavaLauncher;
import org.gradle.process.ExecOperations;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.List;

/**
 * Downloads, remaps, and decompiles Minecraft client jars.
 *
 * <p>By default this produces {@code latest}, {@code 1.8.9},
 * {@code 1.12.2}, {@code 1.13.2}, and {@code 1.14.4}. Pass other version ids with
 * {@code --versions} or {@code -Pversions=} / {@code -PminecraftVersions=}.
 * Exact ids such as {@code 1.8} are used as-is when they exist in the Mojang manifest.
 *
 * <p>Official Mojang mappings are used when published. Unobfuscated versions
 * are decompiled as-is. Versions through 1.12 prefer Ornithe Feather;
 * 1.13 and later prefer Fabric Yarn when Mojang mappings are unavailable.
 * The 1.13.2 auto profile emits both Ornithe Feather and Legacy Yarn;
 * the 1.14.4 auto profile emits both Mojang mappings and Fabric Yarn.
 * Without mappings the published client stays obfuscated in the default
 * package, and Vineflower's {@code net/minecraft} / {@code com/mojang}
 * filter emits only the few already-named classes.
 *
 * <p>The decompiler is forked onto a Java 25 toolchain so Minecraft 26.x
 * class files can be processed.
 */
public abstract class DecompileMinecraftTask extends DefaultTask {
    @Input
    public abstract ListProperty<String> getVersions();

    @Input
    public abstract ListProperty<String> getMappings();

    @Input
    public abstract Property<String> getDecompilerHeap();

    @OutputDirectory
    public abstract DirectoryProperty getOutputRoot();

    @Internal
    public abstract DirectoryProperty getCacheDirectory();

    @Nested
    public abstract Property<JavaLauncher> getJavaLauncher();

    @Classpath
    public abstract ConfigurableFileCollection getDecompilerClasspath();

    @Inject
    public abstract ExecOperations getExecOperations();

    @Option(
            option = "versions",
            description = "Comma-separated Minecraft versions to decompile. "
                    + "Accepts exact version ids (1.8, 1.20.1) or 'latest'. "
                    + "Default: latest,1.8.9,1.12.2,1.13.2,1.14.4"
    )
    public void setVersionsFromCli(String value) {
        getVersions().set(MinecraftDecompileMain.splitVersions(value));
    }

    @Option(option = "mappings", description = "Mapping sets: auto, mojmap, legacy-yarn, yarn, feather, unobfuscated (comma-separated).")
    public void setMappingsFromCli(String value) {
        getMappings().set(MinecraftDecompileMain.splitVersions(value));
    }

    @Option(option = "decompiler-heap", description = "Maximum heap for the forked decompiler JVM (default 4G).")
    public void setDecompilerHeapFromCli(String value) {
        getDecompilerHeap().set(value);
    }

    @Option(option = "output-root", description = "Shared or local directory for decompiled version/mapping source trees.")
    public void setOutputRootFromCli(String value) {
        getOutputRoot().set(getProject().file(value));
    }

    @Option(option = "cache-directory", description = "Shared or local directory for decompilation artifacts and downloads.")
    public void setCacheDirectoryFromCli(String value) {
        getCacheDirectory().set(getProject().file(value));
    }

    @TaskAction
    public void run() {
        if (!getOutputRoot().isPresent() || !getCacheDirectory().isPresent()) {
            throw new GradleException("decompileMinecraft requires explicit --output-root and --cache-directory paths. "
                    + "Use an isolated staging pair for preparation; source consumers must use the published "
                    + "readiness records and must not run this task.");
        }
        validateWritableRoots();

        List<String> specs = getVersions().get().stream()
                .flatMap(value -> MinecraftDecompileMain.splitVersions(value).stream())
                .toList();
        if (specs.isEmpty()) {
            throw new GradleException("No Minecraft versions specified.");
        }

        JavaLauncher launcher = getJavaLauncher().get();
        String heap = getDecompilerHeap().get().trim();
        if (!heap.matches("(?i)[1-9][0-9]*[mg]")) {
            throw new GradleException("Invalid decompiler heap '" + heap + "'; use a value such as 2G or 2048M.");
        }
        getLogger().lifecycle(
                "Forking decompiler onto {}",
                launcher.getMetadata().getInstallationPath().getAsFile()
        );

        List<String> args = new ArrayList<>();
        args.add(getCacheDirectory().get().getAsFile().getAbsolutePath());
        args.add(getOutputRoot().get().getAsFile().getAbsolutePath());
        args.add("--mappings=" + String.join(",", getMappings().get()));
        args.addAll(specs);

        var result = getExecOperations().javaexec(spec -> {
            spec.setExecutable(launcher.getExecutablePath().getAsFile().getAbsolutePath());
            spec.setClasspath(getDecompilerClasspath());
            spec.getMainClass().set("legacyparkourcompat.minecraft.MinecraftDecompileMain");
            spec.setArgs(args);
            spec.jvmArgs("-Xmx" + heap);
        });
        if (result.getExitValue() != 0) {
            throw new GradleException("Minecraft decompilation failed with exit code " + result.getExitValue());
        }
    }

    private void validateWritableRoots() {
        Path projectRoot = getProject().getRootDir().toPath().toAbsolutePath().normalize();
        Path repositoryRoot = findRepositoryRoot(projectRoot);
        List<Path> protectedRoots = List.of(
                projectRoot.resolve("decompiled_minecraft"),
                projectRoot.resolve("build/movement-campaign-2026-10-07/ready"),
                repositoryRoot.resolve("decompiled_minecraft"),
                repositoryRoot.resolve("build/movement-campaign-2026-10-07/ready")
        );
        Path outputRoot = canonicalize(getOutputRoot().get().getAsFile().toPath());
        Path cacheDirectory = canonicalize(getCacheDirectory().get().getAsFile().toPath());
        for (Path root : protectedRoots) {
            Path canonicalProtectedRoot = canonicalize(root);
            if (overlaps(outputRoot, canonicalProtectedRoot)) {
                throw new GradleException("Refusing to decompile into the published read-only Minecraft source tree: "
                        + getOutputRoot().get().getAsFile() + " resolves to or overlaps " + canonicalProtectedRoot
                        + ". Use an isolated staging output root.");
            }
            if (overlaps(cacheDirectory, canonicalProtectedRoot)) {
                throw new GradleException("Refusing to use the published read-only Minecraft source tree as a "
                        + "decompiler cache: " + getCacheDirectory().get().getAsFile() + " resolves to or overlaps "
                        + canonicalProtectedRoot + ". Use a separate cache directory.");
            }
        }
    }

    private static Path findRepositoryRoot(Path projectRoot) {
        Path gitEntry = projectRoot.resolve(".git");
        if (Files.isDirectory(gitEntry)) {
            return projectRoot;
        }
        if (Files.isRegularFile(gitEntry)) {
            try {
                String gitFile = Files.readString(gitEntry).trim();
                if (!gitFile.startsWith("gitdir:")) {
                    throw new GradleException("Cannot resolve shared source protection: invalid Git worktree file "
                            + gitEntry);
                }
                Path worktreeGitDirectory = Path.of(gitFile.substring("gitdir:".length()).trim());
                if (!worktreeGitDirectory.isAbsolute()) {
                    worktreeGitDirectory = projectRoot.resolve(worktreeGitDirectory);
                }
                worktreeGitDirectory = worktreeGitDirectory.toAbsolutePath().normalize();
                Path commonDirectoryFile = worktreeGitDirectory.resolve("commondir");
                if (!Files.isRegularFile(commonDirectoryFile)) {
                    throw new GradleException("Cannot resolve shared source protection: missing Git common-dir file "
                            + commonDirectoryFile);
                }
                Path commonGitDirectory = Path.of(Files.readString(commonDirectoryFile).trim());
                if (!commonGitDirectory.isAbsolute()) {
                    commonGitDirectory = worktreeGitDirectory.resolve(commonGitDirectory);
                }
                commonGitDirectory = commonGitDirectory.toAbsolutePath().normalize();
                if (!Files.isDirectory(commonGitDirectory) || commonGitDirectory.getParent() == null) {
                    throw new GradleException("Cannot resolve shared source protection from Git metadata at "
                            + commonGitDirectory);
                }
                return commonGitDirectory.getParent();
            } catch (IOException | InvalidPathException error) {
                throw new GradleException("Cannot resolve shared source protection from Git worktree metadata at "
                        + gitEntry, error);
            }
        }
        return projectRoot;
    }

    private static Path canonicalize(Path path) {
        Path absolute = path.toAbsolutePath().normalize();
        Path existing = absolute;
        List<Path> missingParts = new ArrayList<>();
        while (existing != null && !Files.exists(existing)) {
            if (Files.isSymbolicLink(existing)) {
                throw new GradleException("Cannot safely resolve output/cache path through dangling link " + existing);
            }
            Path name = existing.getFileName();
            if (name == null) {
                break;
            }
            missingParts.add(name);
            existing = existing.getParent();
        }
        if (existing == null) {
            throw new GradleException("Cannot resolve output/cache path " + absolute);
        }
        try {
            Path resolved = existing.toRealPath();
            for (int index = missingParts.size() - 1; index >= 0; index--) {
                resolved = resolved.resolve(missingParts.get(index));
            }
            return resolved.normalize();
        } catch (IOException error) {
            throw new GradleException("Cannot canonicalize output/cache path " + absolute, error);
        }
    }

    private static boolean overlaps(Path first, Path second) {
        return first.startsWith(second) || second.startsWith(first);
    }
}
