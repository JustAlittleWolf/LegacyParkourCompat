package me.wolfii.legacyparkourcompat.mechanic.hook;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
@MechanicType("player.jump.sprint_impulse")
public interface SprintJumpImpulseBehavior extends VersionedMechanic {
    Vec3 impulse(LivingEntity entity, Vec3 vanilla);
}
