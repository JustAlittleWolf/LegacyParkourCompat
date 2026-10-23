package me.wolfii.legacyparkourcompat.change.v1_8;

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
 * Keeps native hopper geometry for 1.8.0 through 1.8.8. The blind source
 * comparison starts at 1.8.9, where the ordered five-box provider matches the
 * inspected 1.9.4, 1.10.2, 1.11.2 and 1.12.2 providers. This resolver fence
 * makes no claim about those uninspected earlier releases.
 */
@MovementChange(emulates = ParkourVersion.V1_8)
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
