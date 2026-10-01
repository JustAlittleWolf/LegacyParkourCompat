package me.wolfii.legacyparkourcompat.mixin.accessor;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Entity.class)
public interface EntityInvoker {
    @Invoker("getBlockPosBelowThatAffectsMyMovement")
    BlockPos legacyparkourcompat$invokeGetBlockPosBelowThatAffectsMyMovement();

    @Invoker("getBlockSpeedFactor")
    float legacyparkourcompat$invokeGetBlockSpeedFactor();
}
