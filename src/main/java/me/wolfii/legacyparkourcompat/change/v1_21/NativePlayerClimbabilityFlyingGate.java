package me.wolfii.legacyparkourcompat.change.v1_21;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.PlayerClimbabilityFlyingGateHook;
import net.minecraft.world.entity.player.Player;

/**
 * Preserves the native gate for profiles older than the accepted cutover.
 *
 * <p>The bounded Mojmap comparison is 1.21.3 to 1.21.4: only 1.21.4 adds the
 * early flying return to {@code Player.onClimbable}, while 1.21.3 inherits
 * {@code LivingEntity.onClimbable}. This barrier does not infer that later
 * behavior for earlier 1.21 profiles that were not part of the comparison.
 */
@MovementChange(emulates = ParkourVersion.V1_21)
public final class NativePlayerClimbabilityFlyingGate implements PlayerClimbabilityFlyingGateHook {
    @Override
    public boolean shouldApplyFlyingVeto(Player player, boolean vanillaFlying) {
        return vanillaFlying;
    }
}
