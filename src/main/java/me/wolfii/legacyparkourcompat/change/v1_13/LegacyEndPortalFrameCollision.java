package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeRegistry;
import me.wolfii.legacyparkourcompat.mechanic.hook.BlockCollisionShape;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.EndPortalFrameBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

/**
 * Pre-1.13 filled end-portal frames: the eye is 0.375×0.375. 1.13 widened it
 * to 0.5×0.5.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class LegacyEndPortalFrameCollision implements BlockCollisionShape {
    private static final VoxelShape FRAME = Shapes.box(0.0, 0.0, 0.0, 1.0, 0.8125, 1.0);
    private static final VoxelShape EYE = Shapes.box(0.3125, 0.8125, 0.3125, 0.6875, 1.0, 0.6875);
    private static final VoxelShape FILLED = Shapes.or(FRAME, EYE);

    @Override
    public String blockId() {
        return "minecraft:end_portal_frame";
    }

    public static void register(MovementChangeRegistry registry) {
        registry.register(new LegacyEndPortalFrameCollision());
    }

    @Override
    public Optional<VoxelShape> collisionShape(
        BlockState state,
        BlockGetter level,
        BlockPos pos,
        CollisionContext context
    ) {
        return Optional.of(state.getValue(EndPortalFrameBlock.HAS_EYE) ? FILLED : FRAME);
    }
}
