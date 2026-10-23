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

/** Leaves the native collision shape in place before the accepted 1.12 boundary. */
@MovementChange(emulates = ParkourVersion.V1_11_2)
public final class CauldronCollisionNativeFence implements BlockCollisionShapeHook {
    private final String blockId;

    private CauldronCollisionNativeFence(String blockId) {
        this.blockId = blockId;
    }

    @MovementChange.Factory
    public static List<CauldronCollisionNativeFence> variants() {
        return List.of(
            new CauldronCollisionNativeFence("minecraft:cauldron"),
            new CauldronCollisionNativeFence("minecraft:water_cauldron")
        );
    }

    @Override
    public String blockId() {
        return this.blockId;
    }

    @Override
    public Optional<VoxelShape> collisionShape(
        BlockState state, BlockGetter level, BlockPos pos, CollisionContext context
    ) {
        return Optional.empty();
    }
}
