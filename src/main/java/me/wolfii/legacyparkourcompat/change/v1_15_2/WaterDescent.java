package me.wolfii.legacyparkourcompat.change.v1_15_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.WaterDescentBehavior;
import net.minecraft.world.entity.player.Player;

@MovementChange(emulates = ParkourVersion.V1_15_2)
public final class WaterDescent implements WaterDescentBehavior {
    @Override
    public boolean mayDescend(Player player, boolean vanillaAffectedByFluids) {
        return true;
    }
}
