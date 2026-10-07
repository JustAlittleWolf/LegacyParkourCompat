package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.FlightSneakInputBehavior;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec2;

@MovementChange(emulates = ParkourVersion.V1_8)
public final class CreativeFlightSneakInput implements FlightSneakInputBehavior {
    @Override
    public Vec2 modify(Player player, Vec2 input, boolean slowedBySneaking) {
        boolean flyingSneak = player.getAbilities().flying && player.isShiftKeyDown() && !slowedBySneaking;
        return flyingSneak ? input.scale(0.3F) : input;
    }
}
