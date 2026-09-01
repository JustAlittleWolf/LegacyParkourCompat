package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.LivingEntity;

/**
 * Historical jump ({@code LivingEntity#jumpFromGround}).
 */
@MechanicType("player.jump")
public interface JumpBehavior extends VersionedMechanic {
    void jumpFromGround(LivingEntity entity, VanillaCall vanilla);

    default float jumpPower(LivingEntity entity, float vanilla) {
        return vanilla;
    }
}
