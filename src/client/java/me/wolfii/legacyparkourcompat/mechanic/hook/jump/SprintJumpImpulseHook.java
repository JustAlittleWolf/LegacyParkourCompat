package me.wolfii.legacyparkourcompat.mechanic.hook.jump;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
@FunctionalInterface
@MechanicType("player.jump.sprint_impulse")
public interface SprintJumpImpulseHook extends VersionedMechanic {
    Vec3 impulse(LivingEntity entity, Vec3 vanilla);
}
