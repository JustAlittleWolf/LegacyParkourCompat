package me.wolfii.legacyparkourcompat.change.v1_10;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.FarmlandEntityPushBehavior;
import net.minecraft.world.entity.player.Player;

@MovementChange(emulates = ParkourVersion.V1_10_1)
public final class FarmlandConversionV1_10 implements FarmlandEntityPushBehavior {
    @Override
    public boolean suppressPlayerPushUp(Player player) {
        return true;
    }
}
