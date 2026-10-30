package me.wolfii.legacyparkourcompat.change.v1_15_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.LadderSlideSuppressionHook;
import net.minecraft.client.player.LocalPlayer;

/**
 * Restores the shift-only result in Mojmap 1.15, 1.15.1 and 1.15.2:
 * {@code LocalPlayer} has no {@code isSuppressingSlidingDownLadder} override
 * in those releases and inherits {@code LivingEntity.isSuppressingSlidingDownLadder},
 * which returns {@code isShiftKeyDown()}. In Mojmap 1.16,
 * {@code LocalPlayer.isSuppressingSlidingDownLadder} gates that inherited
 * predicate with {@code !abilities.flying}.
 *
 * <p>The exact comparison is Mojmap 1.15.2 {@code LivingEntity} lines 2686–2688
 * and Mojmap 1.16 {@code LocalPlayer} lines 347–349; exact Mojmap 1.15 and
 * 1.15.1 {@code LivingEntity} sources confirm the same inherited predicate and
 * no LocalPlayer override. Current Mojmap 26.2
 * {@code LocalPlayer.isSuppressingSlidingDownLadder} uses the same flying-gated
 * form, so this change replaces only its result for resolved historical profiles.
 */
@MovementChange(emulates = ParkourVersion.V1_15_2)
public final class LadderSlideSuppression implements LadderSlideSuppressionHook {
    @Override
    public boolean isSuppressingSlidingDownLadder(LocalPlayer player, boolean nativeValue) {
        return player.isShiftKeyDown();
    }
}
