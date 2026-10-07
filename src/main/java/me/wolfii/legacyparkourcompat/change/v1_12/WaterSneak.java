package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.WaterSneakBehavior;
import net.minecraft.world.entity.player.Player;

@MovementChange(emulates = ParkourVersion.V1_12)
public final class WaterSneak implements WaterSneakBehavior {
    @Override
    public boolean shouldApplyDownwardImpulse(Player player, boolean vanilla) {
        return false;
    }
}
