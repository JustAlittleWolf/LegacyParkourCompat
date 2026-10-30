package me.wolfii.legacyparkourcompat.mixin.accessor;

import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Entity.class)
public interface EntityCallbackFinalizerAccessor {
    @Accessor("wasOnFire")
    boolean legacyparkourcompat$wasOnFire();
}
