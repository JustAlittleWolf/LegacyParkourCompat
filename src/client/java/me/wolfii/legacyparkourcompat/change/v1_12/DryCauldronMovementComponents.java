package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.StrictAxisClipping;
import me.wolfii.legacyparkourcompat.mechanic.CollisionAxisOrderContext;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.collision.ComponentCollisionShape;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.BlockComponentClipHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.BlockMovementCollisionHook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Exact 1.8.9 Ornithe Feather CauldronBlock.addCollisions appends floor, west,
 * north, east and south in that order. Box.intersectZ uses strict X/Y overlap,
 * so the south wall clips a positive sweep with a sub-epsilon overlap across
 * the east face. Exact 26.2 VoxelShape.collideX instead offsets its orthogonal
 * sample by 1e-7, missing that overlap in the same occupied union.
 *
 * The loaded, slab-supported fixture has a top-slab canopy that keeps both
 * Entity.move step alternatives below the wall top. Collection preserves the
 * component faces in both the primary and raised sweeps. Authorized placement
 * can create the starting overlap; command history is not needed to check it.
 * Exact Feather 1.8 through 1.12.2 preserve this bounded result.
 * 1.8 requests a full-height descent in the rejected step candidate.
 * Later aliases descend by the clipped ascent. Both routes tie the
 * ordinary horizontal clip under the canopy, so Entity.move keeps it. From
 * 1.11.1 the ordinary World collector passes forceShape=false. Cauldron
 * ignores that flag and offers the same five ordered components.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class DryCauldronMovementComponents implements BlockMovementCollisionHook, BlockComponentClipHook {
    private static final double EPSILON = 1.0E-7;

    @Override public String variant() { return "minecraft:cauldron"; }

    @Override
    public VoxelShape collect(Entity entity, BlockPos pos, AABB query, VoxelShape nativeShape,
                              List<VoxelShape> providerParts, @Nullable BlockComponentClipHook componentClip) {
        if (!(entity instanceof LocalPlayer player) || Minecraft.getInstance().player != player
            || !admits(player, pos, query)) {
            return nativeShape;
        }
        if (componentClip == null || providerParts.size() != 5) {
            throw new IllegalStateException("Dry cauldron clipping requires its five ordered provider parts");
        }
        List<AABB> components = new ArrayList<>();
        for (VoxelShape part : providerParts) {
            List<AABB> boxes = part.toAabbs();
            if (boxes.size() != 1) {
                throw new IllegalStateException("Dry cauldron provider must supply individual component boxes");
            }
            AABB box = boxes.getFirst();
            if (box.move(pos).intersects(query)) {
                components.add(box);
            }
        }
        if (components.isEmpty()) {
            throw new IllegalStateException("Supported dry cauldron sweep must intersect its east and south walls");
        }
        return new ComponentCollisionShape(components, componentClip);
    }

    private static boolean admits(LocalPlayer player, BlockPos pos, AABB query) {
        var movement = CollisionAxisOrderContext.movementQuery();
        if (movement == null || movement.entity() != player || movement.moverType() != MoverType.SELF
            || !movement.unadjustedPrimaryMovement() || CollisionAxisOrderContext.currentEntity() != player
            || !player.onGround() || player.noPhysics || player.isPassenger() || player.isShiftKeyDown()
            || player.getPose() != Pose.STANDING || player.getBbWidth() != 0.6F || player.getBbHeight() != 1.8F
            || player.maxUpStep() != 0.6F) {
            return false;
        }

        AABB box = movement.startingBox();
        var requested = movement.requestedMovement();
        double eastOverlap = pos.getX() + 1.0 - box.minX;
        if (!box.equals(player.getBoundingBox()) || requested.x != 0.0 || requested.y != 0.0
            || !(Math.abs(requested.z - 0.2) <= EPSILON)
            || !(eastOverlap > 0.0 && eastOverlap < EPSILON) || box.minY != pos.getY() + 0.5
            || !(Math.abs(box.minZ - (pos.getZ() + 0.2)) <= EPSILON)) {
            return false;
        }

        AABB stepSweep = box.expandTowards(0.0, player.maxUpStep(), requested.z);
        if (!query.equals(movement.sweptBox()) && !query.equals(stepSweep)) {
            return false;
        }
        Level level = player.level();
        // Include the padded block neighborhood and the old entity-query margin for both sweeps.
        AABB neighborhood = stepSweep.inflate(1.0);
        var border = level.getWorldBorder();
        return neighborhood.minX > border.getMinX() && neighborhood.maxX < border.getMaxX()
            && neighborhood.minZ > border.getMinZ() && neighborhood.maxZ < border.getMaxZ()
            && level.getEntities(player, stepSweep.inflate(0.25), entity -> entity != player).isEmpty()
            && matchesLoadedTerrain(level, pos, neighborhood);
    }

    private static boolean matchesLoadedTerrain(Level level, BlockPos cauldron, AABB neighborhood) {
        int minY = Mth.floor(neighborhood.minY);
        int maxY = Mth.floor(neighborhood.maxY);
        if (minY < level.getMinY() || maxY > level.getMaxY()) {
            return false;
        }
        BlockPos support = cauldron.east();
        BlockPos canopy = support.above(2);
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        for (int x = Mth.floor(neighborhood.minX); x <= Mth.floor(neighborhood.maxX); x++) {
            for (int z = Mth.floor(neighborhood.minZ); z <= Mth.floor(neighborhood.maxZ); z++) {
                var chunk = level.getChunkForCollisions(x >> 4, z >> 4);
                if (chunk == null) {
                    return false;
                }
                for (int y = minY; y <= maxY; y++) {
                    BlockState state = chunk.getBlockState(pos.set(x, y, z));
                    boolean expected = pos.equals(cauldron) ? state.is(Blocks.CAULDRON)
                        : pos.equals(support) ? isDrySlab(state, SlabType.BOTTOM)
                        : pos.equals(canopy) ? isDrySlab(state, SlabType.TOP)
                        : state.is(Blocks.AIR);
                    if (!expected) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static boolean isDrySlab(BlockState state, SlabType type) {
        return state.is(Blocks.SMOOTH_STONE_SLAB) && state.getValue(SlabBlock.TYPE) == type
            && !state.getValue(SlabBlock.WATERLOGGED);
    }

    @Override
    public double clip(AABB component, Direction.Axis axis, AABB moving, double distance) {
        return StrictAxisClipping.clipBox(component, axis, moving, distance);
    }
}
