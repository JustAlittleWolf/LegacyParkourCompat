package me.wolfii.legacyparkourcompat.change.v1_18;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.ElytraLiftForceHook;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

/**
 * Uses the float lookup cosine and float-rounded coefficient from 1.18.
 *
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.17.1/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   travel.
 *   travel.
 * 1.18.2/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   travel.
 *   travel.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_18)
public final class ElytraLiftForce implements ElytraLiftForceHook {
    @Override
    public double liftForce(Player player, ParkourVersion selected, Vec3 lookAngle, double vanilla) {
        if (selected.olderThan(ParkourVersion.V1_9)) {
            return vanilla;
        }

        float leanAngle = player.getXRot() * (float) (Math.PI / 180.0);
        float coefficient = me.wolfii.legacyparkourcompat.change.common.LegacyTrig.cos(leanAngle);
        coefficient = (float) (coefficient * (coefficient * Math.min(1.0, lookAngle.length() / 0.4)));
        return coefficient;
    }
}
