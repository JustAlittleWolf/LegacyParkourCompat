package me.wolfii.legacyparkourcompat.change.v1_19;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.InsideBlockContactBehavior;
import net.minecraft.world.entity.player.Player;

@MovementChange(emulates = ParkourVersion.V1_19)
public final class InsideBlockContact_1_19 implements InsideBlockContactBehavior {
    @Override
    public double inset(Player player, double vanilla) {
        return 1.0E-7;
    }
}
