package me.wolfii.legacyparkourcompat.change.v1_10_1;

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
 * 1.10, 1.10.1 and 1.10.2 Ornithe Feather Gen2 build.2 World.getCollisions
 * visits X/Z/Y and skips both caps on a single horizontal edge.
 * LocalClientPlayerEntity.autoJump consumes the first ray-intersecting box.
 * The source-backed interior fixture uses unconnected FenceBlock center
 * posts and bottom SlabBlock boxes. Exact 1.11 has the same cap rule.
 * In the dry grounded stand fixture, BrewingStandBlock.addCollisions
 * (70–72) emits the stem before the plate in all three 1.10 releases and
 * the 1.11 anchors. Entity.move in 1.10.2 (514–624) clips the approach
 * against the plate, while LocalClientPlayerEntity.autoJump (823 onward)
 * consumes the stem first and rejects its rise from the bottom slab.
 * The independent dry east-border fixture uses the same per-cell collector.
 * A center inside the eroded bounds passes World.isWithinBorder for either
 * prior flag. World.getCollisions adds
 * the loaded exterior cell as Stone, and autoJump reads the physical air cap.
 */
@MovementChange(emulates = ParkourVersion.V1_10_1)
public final class AutoJumpCollisionOrder implements AutoJumpCollisionOrderHook {
    @Override
    public boolean observesGroundedTravel() {
        return true;
    }

    @Override
    public Iterable<VoxelShape> autoJumpCollisions(Player player, CollisionGetter level, AABB query,
        ParkourVersion selected, VanillaFn<Iterable<VoxelShape>> vanilla) {
        return AutoJumpBoxCandidates.collect(player, level, query, selected, vanilla, ScanRules.SKIP_BOTH_CAPS_WITH_BREWING_STAND);
    }

    @Override
    public Iterable<VoxelShape> autoJumpCollisions(Player player, CollisionGetter level, AABB query,
        ParkourVersion selected, ProbeSegments probes, VanillaFn<Iterable<VoxelShape>> vanilla) {
        return AutoJumpBoxCandidates.collect(player, level, query, selected, vanilla, ScanRules.SKIP_BOTH_CAPS_WITH_BREWING_STAND, probes);
    }
}
