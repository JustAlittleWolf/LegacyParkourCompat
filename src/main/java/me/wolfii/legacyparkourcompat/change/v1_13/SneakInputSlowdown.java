package me.wolfii.legacyparkourcompat.change.v1_13;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.SneakInputSlowdownBehavior;
import net.minecraft.world.entity.player.Player;
/** KeyboardInput.tick uses the direct sneak key before the 1.14 pose gate. */
@MovementChange(emulates = ParkourVersion.V1_13)
public final class SneakInputSlowdown implements SneakInputSlowdownBehavior {
    @Override public boolean shouldSlowDown(Player player) { return player.isShiftKeyDown(); }
}
