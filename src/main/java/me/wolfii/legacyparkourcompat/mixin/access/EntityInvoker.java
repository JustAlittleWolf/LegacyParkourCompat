package me.wolfii.legacyparkourcompat.mixin.access;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(Entity.class)
public interface EntityInvoker {
    @Invoker("collideWithShapes")
    static Vec3 lpc$collideWithShapes(Vec3 movement, AABB boundingBox, List<VoxelShape> shapes) {
        throw new AssertionError();
    }

    @Invoker("collide")
    Vec3 lpc$collide(Vec3 movement);

    @Invoker("getOnPos")
    BlockPos lpc$getOnPos(float offset);

    @Invoker("restituteMovementAfterCollisions")
    void lpc$restituteMovementAfterCollisions(
        net.minecraft.world.level.block.state.BlockState effectState,
        boolean xCollision,
        boolean zCollision,
        Vec3 movement
    );
}
