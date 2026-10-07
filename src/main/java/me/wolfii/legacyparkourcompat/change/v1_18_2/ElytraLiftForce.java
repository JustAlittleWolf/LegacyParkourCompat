package me.wolfii.legacyparkourcompat.change.v1_18_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.ElytraLiftForceBehavior;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

/** Uses the double cosine coefficient introduced for 1.18.2. */
@MovementChange(emulates = ParkourVersion.V1_18_2)
public final class ElytraLiftForce implements ElytraLiftForceBehavior {
    @Override
    public double liftForce(Player player, Vec3 lookAngle, double vanilla) {
        float leanAngle = player.getXRot() * (float) (Math.PI / 180.0);
        double coefficient = Math.cos(leanAngle);
        coefficient = coefficient * coefficient * Math.min(1.0, lookAngle.length() / 0.4);
        return coefficient;
    }
}
