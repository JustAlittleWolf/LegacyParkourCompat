package me.wolfii.legacyparkourcompat.mixin.accessor;

import net.minecraft.world.entity.monster.Shulker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

/** Reads the Shulker's authoritative closed-state byte without changing it. */
@Mixin(Shulker.class)
public interface ShulkerAccessor {
    @Invoker("getRawPeekAmount")
    int legacyparkourcompat$rawPeekAmount();
}
