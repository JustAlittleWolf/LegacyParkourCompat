package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.ResolvedMovementCutoffHook;
import net.minecraft.world.entity.MoverType;

/**
 * Restores the pre-1.14 rule that nonzero resolved SELF displacement updates position.
 *
 * <p>In 1.13.2 (Ornithe Feather), {@code net.minecraft.entity.Entity.move} calls
 * {@code setPositionFromShape()} unconditionally after resolving SELF movement
 * (Entity.java, Entity.move). In 1.14 (Ornithe Feather),
 * {@code net.minecraft.entity.Entity.move} updates the box and position only when
 * the resolved vector's squared length is greater than {@code 1.0E-7} (Entity.java,
 * Entity.move). In 26.2 (Mojmap), {@code net.minecraft.world.entity.Entity.move}
 * applies that squared-length position cutoff after collision resolution (Entity.java,
 * Entity.move); the targeted constant is the movement-length comparison, not the
 * earlier stuck-speed or piston-specific checks.
 */
@MovementChange(emulates = ParkourVersion.V1_13_1)
public final class ResolvedMovementCutoff implements ResolvedMovementCutoffHook {
    public ResolvedMovementCutoff() {
    }

    @Override
    public double squaredCutoff(MoverType moverType, double vanillaCutoff) {
        return moverType == MoverType.SELF ? 0.0 : vanillaCutoff;
    }
}
