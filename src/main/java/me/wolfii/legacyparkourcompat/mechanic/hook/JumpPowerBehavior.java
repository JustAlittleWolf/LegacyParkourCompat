package me.wolfii.legacyparkourcompat.mechanic.hook;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.LivingEntity;
/** Exact precision of the ground jump power operand. */
@FunctionalInterface
@MechanicType("player.jump.power")
public interface JumpPowerBehavior extends VersionedMechanic {
    double jumpPower(LivingEntity entity, double vanilla);
}
