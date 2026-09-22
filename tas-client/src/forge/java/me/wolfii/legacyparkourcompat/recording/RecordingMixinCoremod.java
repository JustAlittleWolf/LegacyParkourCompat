package me.wolfii.legacyparkourcompat.recording;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import java.util.Map;

/**
 * Forge 1.8–1.12 does not bundle Mixin. Unimined passes {@code --mixin} but
 * LaunchWrapper never loads MixinTweaker, so the recording mixins never apply
 * unless this coremod bootstraps them.
 */
@IFMLLoadingPlugin.Name("legacyparkourrecording")
@IFMLLoadingPlugin.SortingIndex(Integer.MIN_VALUE)
@IFMLLoadingPlugin.TransformerExclusions("org.spongepowered.")
public final class RecordingMixinCoremod implements IFMLLoadingPlugin {
    public RecordingMixinCoremod() {
        MixinBootstrap.init();
        Mixins.addConfiguration("legacyparkourrecording.mixins.json");
        MixinEnvironment.getDefaultEnvironment().setSide(MixinEnvironment.Side.CLIENT);
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[0];
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
