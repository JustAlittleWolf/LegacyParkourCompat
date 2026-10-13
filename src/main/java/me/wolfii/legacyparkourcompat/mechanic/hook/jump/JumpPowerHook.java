package me.wolfii.legacyparkourcompat.mechanic.hook.jump;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.LivingEntity;
/** Exact precision of the ground jump power operand. */
@FunctionalInterface
@MechanicType("player.jump.power")
public interface JumpPowerHook extends VersionedMechanic {
    double jumpPower(LivingEntity entity, double vanilla);
}
