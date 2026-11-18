package me.wolfii.legacyparkourcompat.change.v1_21;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.HistoricalElytra;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.hook.gliding.GlideFallDistanceLimiterStageHook;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

/**
 * Mojmap 1.21 {@code net.minecraft.world.entity.LivingEntity.travel(Vec3)} invokes
 * {@code Entity.checkSlowFallDistance()} only in its dry fall-flying travel branch; paired Mojmap
 * 1.21.2 {@code LivingEntity.updateFallFlying()} invokes that limiter before travel dispatch from
 * {@code LivingEntity.aiStep()}. In 26.2 unobfuscated
 * {@code LivingEntity.updateFallFlying()} calls {@code Entity.checkFallDistanceAccumulation()} before
 * travel and {@code LivingEntity.travelFallFlying(Vec3)} follows fluid dispatch. This operation
 * moves only the native call between its existing callbacks. Accepted Mojmap
 * {@code net.minecraft.world.entity.LivingEntity.travel(Vec3)} comparisons for releases 1.15,
 * 1.15.1, 1.15.2, 1.16–1.16.5, 1.17, 1.17.1, 1.18, 1.18.1, and 1.18.2 show the same already-
 * active dry Elytra assignment without a climbable gate. Mojmap 1.15.2
 * {@code net.minecraft.client.player.LocalPlayer.tick()} adds a separate {@code !onLadder}
 * startup condition, which remains unchanged. The accepted 1.19/1.19.1/1.19.2 aliases continue
 * that travel behavior. For selected V1_15 through V1_19, the existing stage therefore stays at
 * travel for the already-active climbable witness; earlier profiles retain the prior gate.
 */
@MovementChange(emulates = ParkourVersion.V1_21)
public final class GlideFallDistanceLimiterStage implements GlideFallDistanceLimiterStageHook {
    @Override
    public void atStage(LivingEntity entity, ParkourVersion selectedVersion, Stage stage, VanillaCall nativeLimiter) {
        Stage limiterStage = eligible(entity, selectedVersion) ? Stage.TRAVEL_FALL_FLYING : Stage.UPDATE_FALL_FLYING;
        if (stage == limiterStage) {
            nativeLimiter.run();
        }
    }

    private boolean eligible(LivingEntity entity, ParkourVersion selectedVersion) {
        // The accepted witness uses an airborne local player with vanilla chest Elytra.
        // Preserve native behavior before Elytra's introduction and for modern gliders.
        if (selectedVersion.olderThan(ParkourVersion.V1_9)
            || selectedVersion.newerThan(ParkourVersion.V1_21)
            || !(entity instanceof Player player)
            || !player.level().isClientSide()
            || !player.isLocalPlayer()
            || !player.isFallFlying()
            || player.onGround()
            || player.getAbilities().flying
            || player.isPassenger()
            || (!usesHistoricalClimbableTravel(selectedVersion) && player.onClimbable())) {
            return false;
        }
        if (!HistoricalElytra.applies(player, selectedVersion)) {
            return false;
        }
        for (EquipmentSlot slot : EquipmentSlot.VALUES) {
            if (slot != EquipmentSlot.CHEST && LivingEntity.canGlideUsing(player.getItemBySlot(slot), slot)) {
                return false;
            }
        }
        return true;
    }

    private static boolean usesHistoricalClimbableTravel(ParkourVersion selectedVersion) {
        return selectedVersion.newerThanOrEqual(ParkourVersion.V1_15)
            && selectedVersion.olderThanOrEqual(ParkourVersion.V1_19);
    }
}
