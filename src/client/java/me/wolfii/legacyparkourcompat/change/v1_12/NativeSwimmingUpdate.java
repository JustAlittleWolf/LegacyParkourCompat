package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.SwimmingUpdateHook;
import net.minecraft.world.entity.player.Player;

/** Native delegation fence for profiles before the swimming-state writer. */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class NativeSwimmingUpdate implements SwimmingUpdateHook {
    @Override
    public boolean shouldDelegate(Player player) {
        return true;
    }
}
