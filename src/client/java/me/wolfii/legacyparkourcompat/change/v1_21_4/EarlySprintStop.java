package me.wolfii.legacyparkourcompat.change.v1_21_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.sprint.EarlySprintStopHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.item.component.UseEffects;

/**
 * Minecraft 1.21.4, Mojmap, net.minecraft.client.player.LocalPlayer.aiStep:
 * immediately after Tutorial.onInput, shouldStopSprinting calls setSprinting(false).
 * LocalPlayer.shouldStopSprinting begins with isFallFlying(), without a water gate
 * or an isSprinting gate. Its remaining ordered reasons are blindness, slow
 * movement, passenger state except Camel, and non-passenger item use outside
 * underwater state. Restore the single write at that stage rather than
 * recomputing the later sprint-start/stop state machine. Modern custom item
 * use effects retain native item-use behavior.
 * Minecraft 1.21.5, Mojmap, the same aiStep has no early stop after Tutorial.onInput;
 * shouldStopRunSprinting and shouldStopSwimSprinting omit these early reasons.
 */
@MovementChange(emulates = ParkourVersion.V1_21_4)
public final class EarlySprintStop implements EarlySprintStopHook {
    @Override
    public void afterTutorialInput(LocalPlayer player) {
        if (player.isFallFlying()
            || player.hasEffect(MobEffects.BLINDNESS)
            || player.isMovingSlowly()
            || player.isPassenger() && (player.getVehicle() == null || player.getVehicle().getType() != EntityTypes.CAMEL)
            || player.isUsingItem() && !player.isPassenger() && !player.isUnderWater() && hasOrdinaryUseEffects(player)) {
            player.setSprinting(false);
        }
    }

    private static boolean hasOrdinaryUseEffects(LocalPlayer player) {
        UseEffects effects = player.getUseItem().getOrDefault(DataComponents.USE_EFFECTS, UseEffects.DEFAULT);
        return effects.speedMultiplier() == UseEffects.DEFAULT.speedMultiplier()
            && effects.canSprint() == UseEffects.DEFAULT.canSprint();
    }
}
