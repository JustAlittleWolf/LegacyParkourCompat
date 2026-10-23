package me.wolfii.legacyparkourcompat.change.v1_11_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockCollisionShapeHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.Optional;

/**
 * Retains the native hopper collision shape before the accepted 1.12 boundary.
 * This resolver fence does not claim that earlier hopper geometry has been
 * verified; the ongoing source review may replace it with a historical change.
 */
@MovementChange(emulates = ParkourVersion.V1_11_2)
public final class HopperCollisionShapeNativeFence implements BlockCollisionShapeHook {
    @MovementChange.Factory
    public static List<HopperCollisionShapeNativeFence> createVariants() {
        return List.of(new HopperCollisionShapeNativeFence());
    }

    @Override
    public String blockId() {
        return "minecraft:hopper";
    }

    @Override
    public Optional<VoxelShape> collisionShape(
        BlockState state, BlockGetter level, BlockPos pos, CollisionContext context
    ) {
        return Optional.empty();
    }
}
