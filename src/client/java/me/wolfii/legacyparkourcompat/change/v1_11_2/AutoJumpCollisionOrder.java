package me.wolfii.legacyparkourcompat.change.v1_11_2;

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
 * 1.11.1 and 1.11.2 Feather Gen2 build.2 World.getCollisions visits X/Z/Y
 * and skips only the top cap on a single horizontal edge, as in 1.12.
 * FenceBlock and the isolated BrewingStandBlock append their boxes through
 * addCollisions with forceShape=false to LocalClientPlayerEntity.autoJump.
 * The independent dry east-border fixture uses the same per-cell collector.
 * A center inside the eroded bounds passes World.isWithinBorder for either
 * prior flag. World.getCollisions adds
 * the loaded exterior cell as Stone, and autoJump reads the physical air cap.
 */
@MovementChange(emulates = ParkourVersion.V1_11_2)
public final class AutoJumpCollisionOrder implements AutoJumpCollisionOrderHook {
    @Override
    public boolean observesGroundedTravel() {
        return true;
    }

    @Override
    public Iterable<VoxelShape> autoJumpCollisions(Player player, CollisionGetter level, AABB query,
        ParkourVersion selected, VanillaFn<Iterable<VoxelShape>> vanilla) {
        return AutoJumpBoxCandidates.collect(player, level, query, selected, vanilla, ScanRules.SKIP_TOP_CAP_INTERIOR);
    }

    @Override
    public Iterable<VoxelShape> autoJumpCollisions(Player player, CollisionGetter level, AABB query,
        ParkourVersion selected, ProbeSegments probes, VanillaFn<Iterable<VoxelShape>> vanilla) {
        return AutoJumpBoxCandidates.collect(player, level, query, selected, vanilla, ScanRules.SKIP_TOP_CAP_INTERIOR, probes);
    }
}
