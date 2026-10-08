package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.monster.Monster;

import java.util.function.BooleanSupplier;

/** Historical player sleep-safety predicate. */
@FunctionalInterface
@MechanicType("player.rest-safety")
public interface PlayerRestSafetyBehavior extends VersionedMechanic {
    boolean preventsRest(Monster monster, ParkourVersion selected, BooleanSupplier vanilla);
}
