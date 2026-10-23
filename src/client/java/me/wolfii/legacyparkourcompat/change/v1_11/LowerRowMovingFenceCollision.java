package me.wolfii.legacyparkourcompat.change.v1_11;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.LowerRowCollisionCandidateHook;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.piston.PistonMovingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

/**
 * 1.11, ornithe-feather gen2 build.2, World.getCollisions(Entity, Box) and
 * getCollisions(Box): exactly one X/Z perimeter excludes both padded Y rows.
 * 1.11.1 (unchanged in 1.11.2), same mappings, World.getCollisions helper:
 * that perimeter excludes only the upper row. Both use floor(min)-1 and
 * ceil(max)+1 exclusive bounds, looping X/Z/Y.
 *
 * This deliberately implements only the proved non-source moving era-fence
 * subset. In 1.11 MovingBlockEntity.addCollisions forwards a translated query
 * to FenceBlock.addCollisions, which emits the 1.5-high center post. The
 * 1.11.1 provider retains that post. 26.2, unobfuscated, BlockCollisions.computeNext
 * admits moving pistons at cursor edges and invokes CollisionContext.getCollisionShape;
 * PistonMovingBlockEntity.getCollisionShape supplies the translated moved state.
 * Filtering that existing call leaves native scan bounds, iteration order,
 * chunk access, border handling, other providers and entity additions intact.
 * It does not reproduce all historical scan eligibility or fence connections.
 */
@MovementChange(emulates = ParkourVersion.V1_11)
public final class LowerRowMovingFenceCollision implements LowerRowCollisionCandidateHook {
    @Override
    public boolean excludeCandidate(BlockState state, CollisionGetter level, BlockPos pos, AABB query) {
        if (!state.is(Blocks.MOVING_PISTON) || pos.getY() != Mth.floor(query.minY) - 1) {
            return false;
        }
        int minX = Mth.floor(query.minX) - 1;
        int maxX = Mth.ceil(query.maxX);
        int minZ = Mth.floor(query.minZ) - 1;
        int maxZ = Mth.ceil(query.maxZ);
        if (pos.getX() < minX || pos.getX() > maxX || pos.getZ() < minZ || pos.getZ() > maxZ) {
            return false;
        }
        boolean xPerimeter = pos.getX() == minX || pos.getX() == maxX;
        boolean zPerimeter = pos.getZ() == minZ || pos.getZ() == maxZ;
        if (xPerimeter == zPerimeter
            || !(level.getBlockEntity(pos) instanceof PistonMovingBlockEntity moving)
            || moving.isSourcePiston()) {
            return false;
        }
        BlockState moved = moving.getMovedState();
        if (!moved.getFluidState().isEmpty()) {
            return false;
        }
        return moved.is(Blocks.OAK_FENCE) || moved.is(Blocks.SPRUCE_FENCE)
            || moved.is(Blocks.BIRCH_FENCE) || moved.is(Blocks.JUNGLE_FENCE)
            || moved.is(Blocks.ACACIA_FENCE) || moved.is(Blocks.DARK_OAK_FENCE)
            || moved.is(Blocks.NETHER_BRICK_FENCE);
    }
}
