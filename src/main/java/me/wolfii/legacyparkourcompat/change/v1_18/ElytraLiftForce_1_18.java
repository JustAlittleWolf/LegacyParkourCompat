package me.wolfii.legacyparkourcompat.change.v1_18;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.ElytraLiftForceBehavior;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

/** Uses the float lookup cosine and float-rounded coefficient from 1.18. */
@MovementChange(emulates = ParkourVersion.V1_18)
public final class ElytraLiftForce_1_18 implements ElytraLiftForceBehavior {
    @Override
    public double liftForce(Player player, Vec3 lookAngle, double vanilla) {
        float leanAngle = player.getXRot() * (float) (Math.PI / 180.0);
        float coefficient = me.wolfii.legacyparkourcompat.change.common.LegacyTrig.cos(leanAngle);
        coefficient = (float) (coefficient * (coefficient * Math.min(1.0, lookAngle.length() / 0.4)));
        return coefficient;
    }
}
