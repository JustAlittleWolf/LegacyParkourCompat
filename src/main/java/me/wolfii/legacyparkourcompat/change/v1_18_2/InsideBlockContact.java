package me.wolfii.legacyparkourcompat.change.v1_18_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.InsideBlockContactHook;
import net.minecraft.world.entity.player.Player;

@MovementChange(emulates = ParkourVersion.V1_18_2)
public final class InsideBlockContact implements InsideBlockContactHook {
    @Override
    public double inset(Player player, double vanilla) {
        return 0.001;
    }
}
