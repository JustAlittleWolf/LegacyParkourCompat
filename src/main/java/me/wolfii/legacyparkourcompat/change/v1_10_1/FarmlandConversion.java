package me.wolfii.legacyparkourcompat.change.v1_10_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.FarmlandEntityPushHook;
import net.minecraft.world.entity.player.Player;

@MovementChange(emulates = ParkourVersion.V1_10_1)
public final class FarmlandConversion implements FarmlandEntityPushHook {
    @Override
    public boolean suppressPlayerPushUp(Player player) {
        return true;
    }
}
