package me.wolfii.legacyparkourcompat.change.v1_19_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.SprintStartBehavior;
import net.minecraft.world.entity.player.Player;


@MovementChange(emulates = ParkourVersion.V1_19_4)
public final class SprintEligibility_1_19_4 implements SprintStartBehavior {
    @Override
    public boolean canStartSprinting(Player player, boolean vanilla) {
        return vanilla && !player.isFallFlying();
    }
}
