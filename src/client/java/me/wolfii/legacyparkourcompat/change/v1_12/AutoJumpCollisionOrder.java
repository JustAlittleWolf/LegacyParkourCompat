package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.AutoJumpBoxCandidates;
import me.wolfii.legacyparkourcompat.change.common.AutoJumpBoxCandidates.ScanRules;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.AutoJumpCollisionOrderHook;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * Feather 1.12.2 World.getCollisions visits X/Z/Y, skips horizontal corners and
 * the top cell of edge columns, then calls each state's addCollisions. Its
 * LocalClientPlayerEntity.autoJump stops at the first intersecting box.
 * Feather 1.13 WorldView.getBlockCollisions unions shapes; VoxelShape.getBoundingBoxes /
 * VoxelSet.forEachBox changes this first-hit order. First new release: 1.13.
 *
 * The unconnected-fence witness admits air and bottom smooth-stone slabs
 * inside the border with loaded surroundings.
 * Feather 1.12.2 FenceBlock.addCollisions emits one post for an unconnected fence;
 * SlabBlock.getShape supplies the bottom half box. Unobfuscated 26.2 FenceBlock /
 * CrossCollisionBlock and SlabBlock preserve those single-box geometries. Query
 * each actual state with the player CollisionContext.
 *
 * The separate single-Brewing-Stand fixture needs the ordered components of
 * Ornithe Feather 1.12, 1.12.1 and 1.12.2 BrewingStandBlock.addCollisions
 * calls Block.addCollision for the stem and then the full plate, appending
 * each translated box on strict intersection. The Feather 1.13 and 1.13.2
 * BrewingStandBlock.SHAPE union and VoxelSet.forEachBox expose the plate first.
 * The active 26.2 historical union retains the occupied volume but likewise
 * flattens plate first. Reuse the ordered components for the isolated dry
 * slab-floor probe, where Feather 1.12.2 Entity.move cannot step onto the plate
 * before LocalClientPlayerEntity.move calls autoJump.
 *
 * For the separate cardinal border fixtures, Feather 1.12–1.12.2
 * World.isWithinBorder is true for either prior outside flag when the
 * player's center is inside the eroded border. World.getCollisions and
 * WorldBorder.contains append outside cells in place. Feather 1.13.2
 * WorldView.getBlockCollisions accumulates them in an IntVoxelShape instead.
 * Include only individual outside cells on the one admitted face for a
 * supported Stone-floor probe. The visit order stays X/Z/Y on every face.
 * Other providers, entities' historical geometry and runtime parity remain open.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class AutoJumpCollisionOrder implements AutoJumpCollisionOrderHook {
    @Override
    public Iterable<VoxelShape> autoJumpCollisions(Player player, CollisionGetter level, AABB query,
        ParkourVersion selected, VanillaFn<Iterable<VoxelShape>> vanilla) {
        return AutoJumpBoxCandidates.collect(player, level, query, selected, vanilla, ScanRules.SKIP_TOP_CAP_WITH_BORDER);
    }
}
