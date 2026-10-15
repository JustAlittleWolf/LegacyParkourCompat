package me.wolfii.legacyparkourcompat.mechanic.hook.fluid;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** The paired water drag/acceleration interpolation before movement. */
@MechanicType("player.water.enchantment_factors")
public interface WaterEnchantmentFactorsHook extends VersionedMechanic {
    record Factors(float drag, float acceleration) {}
    Factors factors(Player player, ParkourVersion selected, float vanillaDrag, float vanillaAcceleration);
}
