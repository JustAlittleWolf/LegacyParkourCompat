package me.wolfii.legacyparkourcompat.change.v1_19;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.SprintDoubleTapGroundHook;
import net.minecraft.world.entity.player.Player;

/**
 * Mojmap 1.19, 1.19.1 and 1.19.2 LocalPlayer#aiStep requires player ground
 * or underwater state to arm or complete the forward double tap. Mojmap
 * 1.19.3 adds a grounded-vehicle alternative. The held-key route is separate.
 */
@MovementChange(emulates = ParkourVersion.V1_19)
public final class DoubleTapSprintGround implements SprintDoubleTapGroundHook {
    @Override
    public boolean admits(Player player) {
        return player.onGround() || player.isUnderWater();
    }
}
