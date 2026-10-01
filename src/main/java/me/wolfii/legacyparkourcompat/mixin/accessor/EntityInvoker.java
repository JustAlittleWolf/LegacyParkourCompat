package me.wolfii.legacyparkourcompat.mixin.accessor;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Entity.class)
public interface EntityInvoker {
    @Invoker("restituteMovementAfterCollisions")
    void legacyparkourcompat$invokeRestituteMovementAfterCollisions(
        BlockState effectState,
        boolean xCollision,
        boolean zCollision,
        Vec3 movement
    );
}
