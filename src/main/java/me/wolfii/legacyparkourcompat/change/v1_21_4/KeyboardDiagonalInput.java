package me.wolfii.legacyparkourcompat.change.v1_21_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.KeyboardDiagonalInputBehavior;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec2;

@MovementChange(emulates = ParkourVersion.V1_21_4)
public final class KeyboardDiagonalInput implements KeyboardDiagonalInputBehavior {
    @Override
    public Vec2 diagonalInput(
        Player player,
        Vec2 rawImpulses,
        Vec2 vanilla,
        boolean keyboard,
        boolean diagonal,
        boolean movingSlowly,
        boolean usingItem
    ) {
        if (keyboard && diagonal && !movingSlowly && !usingItem) {
            return rawImpulses.scale(0.98F);
        }
        return vanilla;
    }
}
