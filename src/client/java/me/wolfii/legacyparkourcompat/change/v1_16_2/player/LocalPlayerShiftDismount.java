package me.wolfii.legacyparkourcompat.change.v1_16_2.player;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.LocalPlayerShiftDismountHook;

/**
 * In 1.15.2 Mojmap,
 * {@code net.minecraft.world.entity.player.Player.rideTick()} runs the
 * shift-to-dismount branch only when {@code !level.isClientSide};
 * {@code Player.wantsToStopRiding()} supplies its shift gate, and
 * {@code net.minecraft.client.player.LocalPlayer.rideTick()} delegates to
 * {@code super.rideTick()}. In 1.16.0 and 1.16.5 Mojmap, the same
 * {@code Player.rideTick()} branch also runs client-side and calls
 * {@code Player.stopRiding()} and {@code Player.setShiftKeyDown(false)};
 * {@code LocalPlayer.rideTick()} still delegates to the player gate. Suppress
 * that gate for the local client player to retain the earlier
 * server-authoritative dismount behavior. In 1.16.0,
 * {@code net.minecraft.client.player.LocalPlayer.isShiftKeyDown()} reads
 * {@code input.shiftKeyDown}; {@code Player.setShiftKeyDown(false)} changes
 * the entity's shared shift flag and does not clear that input state. The same
 * ride gate and local-player delegation are byte-identical in 1.16.1 and in
 * 1.16.2 through 1.16.5, so the later verified alias serves both 1.16 profile
 * groups through closest-later resolution.
 */
@MovementChange(emulates = ParkourVersion.V1_16_2)
public final class LocalPlayerShiftDismount implements LocalPlayerShiftDismountHook {
    public LocalPlayerShiftDismount() {
    }

    @Override
    public boolean shouldStopRiding(boolean vanillaResult) {
        return false;
    }
}
