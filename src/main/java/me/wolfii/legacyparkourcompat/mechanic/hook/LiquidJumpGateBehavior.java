package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.Fluid;

/** Whether the liquid-jump call selected by aiStep should run. */
@FunctionalInterface
@MechanicType("player.jump.liquid_gate")
public interface LiquidJumpGateBehavior extends VersionedMechanic {
    boolean shouldJumpInLiquid(Player player, TagKey<Fluid> fluid, boolean vanilla);
}
