package me.wolfii.legacyparkourcompat.change.v1_21;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.HoneySlideDeltaYHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.HoneySlideVelocityHook;

/**
 * Restores HoneyBlock's direct delta-Y gates and -0.05 vertical write from
 * 1.15 through 1.21.1. Mojmap 1.15 and 1.21.1
 * net/minecraft/world/level/block/HoneyBlock.java entityInside,
 * isSlidingDown and doSlideMovement use entity.getDeltaMovement().y directly
 * and write -0.05 after a successful slide. Mojmap 1.21.2
 * net/minecraft/world/level/block/HoneyBlock.java getOldDeltaY and
 * getNewDeltaY add the newer reconstruction; that behavior remains native for
 * 1.21.2 and later profiles.
 */
@MovementChange(emulates = ParkourVersion.V1_21)
public final class HoneyBlockSlide implements HoneySlideDeltaYHook, HoneySlideVelocityHook {
    @Override
    public double oldDeltaY(double actualDeltaY) {
        return actualDeltaY;
    }

    @Override
    public double slideVerticalVelocity() {
        return -0.05;
    }
}
