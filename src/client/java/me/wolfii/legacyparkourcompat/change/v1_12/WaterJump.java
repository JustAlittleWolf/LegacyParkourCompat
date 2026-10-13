package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.GroundJumpGateHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.LiquidJumpGateHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.WaterJumpHook;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.Fluid;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java:
 *   public void mobTick().
 * 1.13.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java:
 *   public void mobTick().
 * No exact independently accepted source binding is established for this operation.
 * Remaining source/applicability gates:
 *   Later applicability gate: exact introduction of campaign-F-WATER-JUMP is unknown within (1.12.2, 1.13.2]
 *   Exact independent source verdict/snapshot identity for F-WATER-JUMP
 *   Exact independent source verdict/snapshot identity for water-jump-height
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class WaterJump implements WaterJumpHook, GroundJumpGateHook, LiquidJumpGateHook {
    @Override
    public void jumpInWater(Player player) {
        if (!player.isSwimming() && player.isInWater()) {
            player.setDeltaMovement(player.getDeltaMovement().add(0.0, 0.04F, 0.0));
        }
    }

    @Override
    public boolean shouldJumpFromGround(Player player, boolean vanilla) {
        return (player.isSwimming() || !player.isInWater()) && vanilla;
    }

    @Override
    public boolean shouldJumpInLiquid(Player player, TagKey<Fluid> fluid, boolean vanilla) {
        return (player.isSwimming() || !(fluid == FluidTags.WATER && player.isInWater())) && vanilla;
    }

}
