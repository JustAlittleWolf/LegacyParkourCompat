package me.wolfii.legacyparkourcompat.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/** Writable dimensions bridge for the 26.2 entity representation. */
@Mixin(Entity.class)
public interface EntityPoseDimensionsAccessor {
    @Accessor("dimensions")
    EntityDimensions legacyparkourcompat$getDimensions();

    @Accessor("dimensions")
    void legacyparkourcompat$setDimensions(EntityDimensions dimensions);

    @Accessor("eyeHeight")
    void legacyparkourcompat$setEyeHeight(float eyeHeight);
}
