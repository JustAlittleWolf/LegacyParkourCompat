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
 * Minecraft 1.21, Mojmap, LivingEntity.travel(Vec3) invokes
 * Entity.checkSlowFallDistance() only in the fall-flying branch after water and lava.
 * Minecraft 1.21.2, Mojmap, LivingEntity.updateFallFlying() invokes that same limiter
 * before travel dispatch from LivingEntity.aiStep(). The strict vertical-velocity
 * and fall-distance thresholds in Entity.checkSlowFallDistance() are unchanged.
 * Minecraft 26.2, unobfuscated, Entity.checkFallDistanceAccumulation() retains the
 * limiter; LivingEntity.updateFallFlying() invokes it before travel(Vec3), while
 * travelFallFlying(Vec3) follows fluid dispatch. This change moves only that call.
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
            || player.onClimbable()) {
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
}
