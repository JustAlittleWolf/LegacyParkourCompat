package me.wolfii.legacyparkourcompat.change.v1_15_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.change.common.HistoricalElytra;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.FallFlyingStartHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

/**
 * 1.15.2 mojmap, net.minecraft.world.entity.player.Player.tryToStartFallFlying:
 * airborne, not already gliding, outside water, then usable chest Elytra starts
 * gliding without checking Levitation. ElytraItem.isFlyEnabled compares damage
 * against max damage minus one.
 * 1.16 mojmap, Player.tryToStartFallFlying adds !hasEffect(LEVITATION);
 * net.minecraft.client.player.LocalPlayer.aiStep calls it from jump input and
 * sends START_FALL_FLYING only after a successful local start. The corresponding
 * 1.15.2 mojmap LocalPlayer.aiStep also excludes creative flight and passengers.
 * 1.16.5 mojmap, Player.tryToStartFallFlying retains the new effect gate.
 * 26.2 unobfuscated, LocalPlayer.aiStep calls Player.tryToStartFallFlying, which
 * delegates eligibility to Player.canGlide and LivingEntity.canGlide. The latter
 * contains the effect gate and is also called by server updateFallFlying.
 */
@MovementChange(emulates = ParkourVersion.V1_15_2)
public final class LevitationGlideStart implements FallFlyingStartHook {
    @Override
    public boolean tryToStartFallFlying(LocalPlayer player, ParkourVersion selected, VanillaFn<Boolean> vanilla) {
        // Elytra and Levitation did not exist before 1.9. Other modern gliders
        // and modified Elytra component eligibility keep their native behavior.
        if (!HistoricalElytra.applies(player, selected) || !player.hasEffect(MobEffects.LEVITATION)) {
            return vanilla.get();
        }
        ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
        if (!player.getAbilities().flying && !player.isPassenger()
            && !player.onGround() && !player.isFallFlying() && !player.isInWater()) {
            if (chest.getDamageValue() < chest.getMaxDamage() - 1) {
                player.startFallFlying();
                return true;
            }
        }
        return false;
    }
}
