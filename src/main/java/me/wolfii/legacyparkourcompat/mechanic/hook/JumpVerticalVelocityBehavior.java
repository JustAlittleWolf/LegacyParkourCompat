package me.wolfii.legacyparkourcompat.mechanic.hook;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.LivingEntity;
@FunctionalInterface
@MechanicType("player.jump.vertical_velocity")
public interface JumpVerticalVelocityBehavior extends VersionedMechanic {
    double jumpVerticalVelocity(LivingEntity entity, double jumpPower, double currentVerticalVelocity, double vanilla);
}
