package legacyparkourcompat.minecraft;

import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.options.Option;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

/**
 * Downloads, remaps, and decompiles Minecraft client jars.
 *
 * <p>By default this produces {@code latest} (currently 26.2), {@code 1.8.9},
 * {@code 1.12.2}, and {@code 1.14.4}. Pass other versions with
 * {@code --versions} or {@code -Pversions=} / {@code -PminecraftVersions=}.
 *
 * <p>Official Mojang mappings are used from 1.14.4 through 1.21.x. Minecraft
 * 26.1+ ships unobfuscated, so those versions are decompiled as-is. Older
 * versions fall back to Legacy Yarn when Mojang mappings are unavailable.
 */
public abstract class DecompileMinecraftTask extends DefaultTask {
    @Input
    public abstract ListProperty<String> getVersions();

    @OutputDirectory
    public abstract DirectoryProperty getOutputRoot();

    @Internal
    public abstract DirectoryProperty getCacheDirectory();

    @Option(
            option = "versions",
            description = "Comma-separated Minecraft versions to decompile. "
                    + "Accepts exact ids (1.20.1), major versions (1.16), or 'latest'. "
                    + "Default: latest,1.8.9,1.12.2,1.14.4"
    )
    public void setVersionsFromCli(String value) {
        getVersions().set(splitVersions(value));
    }

    @TaskAction
    public void run() {
        List<String> specs = getVersions().get().stream()
                .flatMap(value -> splitVersions(value).stream())
                .toList();
        if (specs.isEmpty()) {
            throw new GradleException("No Minecraft versions specified.");
        }

        Path outputRoot = getOutputRoot().get().getAsFile().toPath();
        Path cacheDir = getCacheDirectory().get().getAsFile().toPath();
        new MinecraftDecompileEngine(getLogger(), cacheDir, outputRoot).decompile(specs);
    }

    static List<String> splitVersions(String value) {
        return Arrays.stream(value.split("[,\\s]+"))
                .map(String::trim)
                .filter(part -> !part.isEmpty())
                .toList();
    }
}
