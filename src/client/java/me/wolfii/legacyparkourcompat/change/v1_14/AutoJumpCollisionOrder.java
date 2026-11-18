package me.wolfii.legacyparkourcompat.change.v1_14;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.AutoJumpFullCubeCandidates;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.AutoJumpCollisionOrderHook;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * Reproduces the old AutoJump full-cube grid aggregate only when its complete
 * query envelope is loaded, entity-free, and contains only supported cells.
 * Most cases are interior and use only ordinary full-cube blocks or air.
 * Cells where the 1.13 and 1.13.1 pruning rules disagree must be air.
 * Other queries retain the native iterable and object identities.
 *
 * <p>For the supported block providers, exact 1.14 Ornithe Feather Gen2 build 2,
 * {@code net.minecraft.block.Block.getShape} returns the shared
 * {@code VoxelShapes.block()} singleton and {@code Block.getCollisionShape}
 * returns that shape for collidable blocks. {@code Blocks} registers the
 * supported providers as ordinary full-cube blocks. Exact 1.14.1
 * Yarn build 10, {@code Block.getOutlineShape} and {@code Block.getCollisionShape}
 * use the shared {@code VoxelShapes.fullCube()} singleton, and
 * {@code Blocks} registers the same providers. Sandstone, stone bricks,
 * obsidian, iron, gold and diamond blocks, white wool and white concrete
 * also have these default providers in the exact 1.13 through 1.13.2
 * Ornithe Feather Gen2 build 2 {@code Block} registrations and
 * {@code Block.getCollisionShape}. Runtime checks require the canonical
 * {@code Shapes.block()} identity. Exact 1.13,
 * 1.13.1 and 1.13.2 Ornithe Feather Gen2 build 2,
 * {@code net.minecraft.world.WorldView.getBlockCollisions}, and exact 1.14
 * Ornithe Feather Gen2 build 2, {@code net.minecraft.world.WorldView.getCollisions};
 * each accumulates full cubes on the padded integer grid and emits an
 * {@code IntVoxelShape}. The matching {@code VoxelSet.forEachBox} implementation
 * defines the X/Y/Z scan and merge order. Exact 1.14.1 Yarn build 10,
 * {@code ViewableWorld.getCollisionShapes} and {@code CuboidBlockIterator.step},
 * emits translated per-block shapes instead. The checked cutover is 1.14.1.
 *
 * <p>For the bounded border cases, Ornithe Feather 1.13, 1.13.1 and 1.13.2
 * {@code WorldView.getBlockCollisions} put full Stone support and synthetic
 * border cells in the same grid before {@code VoxelSet.forEachBox}.
 * Original 1.13 skips X/Z corners and skips the high-Y row only at an
 * X or Z edge. 1.13.1 and 1.13.2 instead skip cells on two scan edges.
 * The supporting floor touches the probe's lower face but changes which
 * box the AutoJump ray encounters first.
 */
@MovementChange(emulates = ParkourVersion.V1_14)
public final class AutoJumpCollisionOrder implements AutoJumpCollisionOrderHook {
    @Override
    public Iterable<VoxelShape> autoJumpCollisions(Player player, CollisionGetter level, AABB query,
        ParkourVersion selected, VanillaFn<Iterable<VoxelShape>> vanilla) {
        return AutoJumpFullCubeCandidates.collect(player, level, query, selected, vanilla);
    }

    @Override
    public Iterable<VoxelShape> autoJumpCollisions(Player player, CollisionGetter level, AABB query,
        ParkourVersion selected, ProbeSegments probes, VanillaFn<Iterable<VoxelShape>> vanilla) {
        return AutoJumpFullCubeCandidates.collect(player, level, query, selected, vanilla, probes);
    }
}
