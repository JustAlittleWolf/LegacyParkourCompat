package me.wolfii.legacyparkourcompat.change.v1_21_10;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.EyeFluidCacheHook;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.material.Fluid;

import java.util.Set;

/**
 * Keeps native eye-cache behavior through 1.21.10. The accepted source comparison establishes the
 * sampled-producer difference between 1.21.11 Mojmap Entity#updateFluidOnEyes and 26.1
 * unobfuscated EntityFluidInteraction#update, but does not establish an earlier cutover.
 */
@MovementChange(emulates = ParkourVersion.V1_21_10)
public final class EyeFluidCacheFence implements EyeFluidCacheHook {
    @Override
    public boolean overridesVanillaCache() {
        return false;
    }

    @Override
    public void refresh(Entity entity, Set<TagKey<Fluid>> cache) {
    }
}
