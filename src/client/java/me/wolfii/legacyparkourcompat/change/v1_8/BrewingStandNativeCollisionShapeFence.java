package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockCollisionShapeHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

/** Keeps the native provider for 1.8 profiles, before the earliest source-confirmed range (1.8.9). */
@MovementChange(emulates = ParkourVersion.V1_8_5)
public final class BrewingStandNativeCollisionShapeFence implements BlockCollisionShapeHook {
    @Override
    public String blockId() {
        return "minecraft:brewing_stand";
    }

    @Override
    public Optional<VoxelShape> collisionShape(BlockState state, BlockGetter level, BlockPos pos,
                                               CollisionContext context) {
        return Optional.empty();
    }
}
