package me.wolfii.legacyparkourcompat.change.v1_15_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.PoseClearanceHook;
import net.minecraft.world.entity.Pose;

/**
 * Mojmap 1.14.4 and 1.15.2 Entity.canEnterPose submit the full pose box to
 * noCollision; 1.16 adds deflate(1.0E-7) at that operation. Current Player pose
 * enums added poses absent in these releases, so those retain native clearance.
 */
@MovementChange(emulates = ParkourVersion.V1_15_2)
public final class PlayerPoseClearance implements PoseClearanceHook {
    @Override
    public boolean usesFullBox(ParkourVersion selected, Pose pose) {
        if (!selected.newerThanOrEqual(ParkourVersion.V1_14)
            || !selected.olderThanOrEqual(ParkourVersion.V1_15_2)) {
            return false;
        }
        return switch (pose) {
            case STANDING, FALL_FLYING, SLEEPING, SWIMMING, SPIN_ATTACK, CROUCHING, DYING -> true;
            default -> false;
        };
    }
}
