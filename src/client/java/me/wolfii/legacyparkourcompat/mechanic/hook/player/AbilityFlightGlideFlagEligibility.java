package me.wolfii.legacyparkourcompat.mechanic.hook.player;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;

/** Shared eligibility for the source-confirmed ability-flight flag-clear groups. */
public final class AbilityFlightGlideFlagEligibility {
    private AbilityFlightGlideFlagEligibility() {
    }

    public static boolean shouldClearAfterTravel(Player player) {
        return player instanceof LocalPlayer
            && player.level().isClientSide()
            && player.getAbilities().flying
            && player.isFallFlying()
            && !player.isPassenger()
            && !player.isInWater()
            && !player.isInLava()
            && player.getItemBySlot(EquipmentSlot.CHEST).is(Items.ELYTRA);
    }
}
