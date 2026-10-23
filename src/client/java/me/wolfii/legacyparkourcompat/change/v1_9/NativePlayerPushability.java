package me.wolfii.legacyparkourcompat.change.v1_9;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.push.PlayerPushabilityHook;
import net.minecraft.world.entity.player.Player;

/**
 * Applicability fence: the accepted client push path is verified for 1.10-1.10.2
 * (ornithe-feather RemoteClientPlayerEntity.mobTick, EntityFilter.canBePushedBy,
 * LivingEntity.isPushable). The 1.8/1.9 range has no accepted equivalent path.
 * Preserve 26.2 unobfuscated LivingEntity.isPushable there instead of extending
 * the later finding into unverified profiles through closest-later resolution.
 */
@MovementChange(emulates = ParkourVersion.V1_9)
public final class NativePlayerPushability implements PlayerPushabilityHook {
    @Override
    public boolean isPushable(Player player, boolean vanilla) {
        return vanilla;
    }
}
