package me.wolfii.legacyparkourcompat.change.v1_14;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.FallFlyingStartHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.FallFlyingClimbableGateHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.FallFlyingFlightToggleGateHook;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.change.common.HistoricalElytra;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;

/**
 * The descending-Y jump request, without a local glide-bit write, climbable gate
 * or same-tick creative-flight-toggle veto, persists through 1.14.4.
 * 1.14 ornithe-feather LocalClientPlayerEntity#mobTick and 1.14.1, 1.14.2,
 * 1.14.3 yarn ClientPlayerEntity#tickMovement retain these three operations.
 * The 1.14.2 sprint-input boundary does not change their applicability.
 *
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.14.4/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   aiStep.
 *   aiStep.
 *   aiStep.
 * 1.15.2/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   aiStep.
 *   aiStep.
 *   aiStep.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_14_2)
public final class ElytraJumpStart implements FallFlyingStartHook, FallFlyingClimbableGateHook, FallFlyingFlightToggleGateHook {
    @Override
    public boolean tryToStartFallFlying(LocalPlayer player, ParkourVersion selected, VanillaFn<Boolean> vanilla) {
        var chestItem = player.getItemBySlot(EquipmentSlot.CHEST);
        if (!HistoricalElytra.applies(player, selected)) {
            return vanilla.get();
        }
        if (player.onGround()
            || !(player.getDeltaMovement().y < 0.0)
            || player.isFallFlying()
            || player.getAbilities().flying) {
            return false;
        }

        return chestItem.is(Items.ELYTRA)
            && chestItem.getDamageValue() < chestItem.getMaxDamage() - 1;
    }

    @Override
    public boolean isOnClimbableForFallFlyingStart(Player player, ParkourVersion selected, boolean vanilla) {
        return HistoricalElytra.applies(player, selected) ? false : vanilla;
    }

    @Override
    public boolean justToggledCreativeFlightForStart(Player player, ParkourVersion selected, boolean vanilla) {
        return HistoricalElytra.applies(player, selected) ? false : vanilla;
    }
}
