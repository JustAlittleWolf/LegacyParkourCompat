package me.wolfii.legacyparkourcompat.mechanic.hook.fluid;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.material.Fluid;

import java.util.Set;

/** Resolves the historical cached eye-fluid sampling operation. */
@MechanicType("fluid.eye-cache")
public interface EyeFluidCacheHook extends VersionedMechanic {
    /** Whether this resolved version replaces the native eye-fluid cache query. */
    boolean overridesVanillaCache();

    /** Refreshes the entity-owned cache at the baseTick fluid-update phase. */
    void refresh(Entity entity, Set<TagKey<Fluid>> cache);
}
