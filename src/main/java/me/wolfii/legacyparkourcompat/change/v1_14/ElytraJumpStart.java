package me.wolfii.legacyparkourcompat.change.v1_14;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.FallFlyingStartBehavior;
import me.wolfii.legacyparkourcompat.mixin.accessor.EntityInvoker;
import net.minecraft.world.entity.player.Player;

@MovementChange(emulates = ParkourVersion.V1_14)
public final class ElytraJumpStart implements FallFlyingStartBehavior {
    @Override
    public boolean tryStartFallFlying(Player player, VanillaFn<Boolean> vanilla) {
        if (player.getDeltaMovement().y >= 0.0) {
            return false;
        }

        boolean started = vanilla.get();
        if (started) {
            ((EntityInvoker)player).legacyparkourcompat$setSharedFlag(7, false);
        }
        return started;
    }
}
