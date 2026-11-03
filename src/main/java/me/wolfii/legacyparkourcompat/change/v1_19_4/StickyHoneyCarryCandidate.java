package me.wolfii.legacyparkourcompat.change.v1_19_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.piston.StickyHoneyCarryCandidateHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;

/**
 * Restores the 1.19.4 honey-piston candidate scan for the local player. Mojmap
 * 1.19.4 PistonMovingBlockEntity#moveStuckEntities builds its local box through
 * 1.5000000999999998, then matches normally pushable, on-ground entities whose
 * center X/Z lies in that box. Mojmap 1.20 PistonMovingBlockEntity#moveStuckEntities
 * raises the local upper bound to 1.5000010000000001, and #matchesStickyCritera
 * also admits entities supported by the moving block; both bodies persist through
 * 1.20.4. In unobfuscated 26.2, PistonMovingBlockEntity#moveStuckEntities and
 * #matchesStickyCritera retain that broader query and support-position predicate,
 * so the native query is a superset that this change filters to the 1.19.4 result.
 */
@MovementChange(emulates = ParkourVersion.V1_19_4)
public final class StickyHoneyCarryCandidate implements StickyHoneyCarryCandidateHook {
    private static final double LEGACY_SCAN_MAX_Y = 1.5000000999999998;

    @Override
    public boolean isCarryCandidate(boolean nativeCandidate, AABB nativeScanBounds, Entity entity, BlockPos pistonPos) {
        AABB legacyScanBounds = new AABB(
            nativeScanBounds.minX,
            nativeScanBounds.minY,
            nativeScanBounds.minZ,
            nativeScanBounds.maxX,
            pistonPos.getY() + LEGACY_SCAN_MAX_Y,
            nativeScanBounds.maxZ
        );
        return entity.getPistonPushReaction() == PushReaction.NORMAL
            && entity.onGround()
            && entity.getBoundingBox().intersects(legacyScanBounds)
            && entity.getX() >= legacyScanBounds.minX
            && entity.getX() <= legacyScanBounds.maxX
            && entity.getZ() >= legacyScanBounds.minZ
            && entity.getZ() <= legacyScanBounds.maxZ;
    }
}
