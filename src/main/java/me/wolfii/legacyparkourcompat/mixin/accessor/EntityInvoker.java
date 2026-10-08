package me.wolfii.legacyparkourcompat.mixin.accessor;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Entity.class)
public interface EntityInvoker {
    @Invoker("getBlockPosBelowThatAffectsMyMovement")
    BlockPos legacyparkourcompat$invokeGetBlockPosBelowThatAffectsMyMovement();

    @Invoker("getBlockSpeedFactor")
    float legacyparkourcompat$invokeGetBlockSpeedFactor();

    @Invoker("setSharedFlag")
    void legacyparkourcompat$setSharedFlag(int flag, boolean value);

    @Invoker("restituteMovementAfterCollisions")
    void legacyparkourcompat$invokeRestituteMovementAfterCollisions(
        BlockState effectState,
        boolean xCollision,
        boolean zCollision,
        Vec3 movement
    );
}
