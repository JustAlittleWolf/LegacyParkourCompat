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
import java.util.ArrayList;
import java.util.List;

/**
 * Downloads, remaps, and decompiles Minecraft client jars.
 *
 * <p>By default this produces {@code latest} (currently 26.2), {@code 1.8.9},
 * {@code 1.12.2}, and {@code 1.14.4}. Pass other version ids with
 * {@code --versions} or {@code -Pversions=} / {@code -PminecraftVersions=}.
 * Exact ids such as {@code 1.8} are used as-is when they exist in the Mojang manifest.
 *
 * <p>Official Mojang mappings are used when published. Unobfuscated versions
 * are decompiled as-is. Versions through 1.12 prefer Ornithe Feather;
 * 1.13 and later prefer Fabric Yarn when Mojang mappings are unavailable.
 * (needed for versions such as 1.9 / 1.9.2 that Yarn does not cover).
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
                    + "Default: latest,1.8.9,1.12.2,1.14.4"
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

    @TaskAction
    public void run() {
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
}
