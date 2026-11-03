package me.wolfii.legacyparkourcompat.change.v1_19_3;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.piston.StickyHoneyCarryCandidateHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;

/**
 * Keeps the native sticky-honey predicate for profiles through 1.19.3, before the
 * 1.19.4 predicate change in Mojmap PistonMovingBlockEntity#matchesStickyCritera.
 */
@MovementChange(emulates = ParkourVersion.V1_19_3)
public final class StickyHoneyCarryCandidateNative implements StickyHoneyCarryCandidateHook {
    @Override
    public boolean isCarryCandidate(boolean nativeCandidate, AABB nativeScanBounds, Entity entity, BlockPos pistonPos) {
        return nativeCandidate;
    }
}
