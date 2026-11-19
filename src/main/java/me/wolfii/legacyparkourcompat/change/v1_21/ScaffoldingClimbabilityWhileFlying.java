package me.wolfii.legacyparkourcompat.change.v1_21;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.PlayerClimbabilityFlyingGateHook;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;

/**
 * Keeps scaffolding climbable for a flying player from 1.15 through 1.21.1.
 *
 * <p>In Mojmap 1.15 through 1.15.2, {@code LivingEntity.onLadder} directly
 * accepts scaffolding for non-spectators. From Mojmap 1.16 through 1.21.3,
 * {@code LivingEntity.onClimbable} accepts scaffolding through
 * {@code BlockTags.CLIMBABLE}. Players inherit these predicates while flying
 * until the veto added in Mojmap 1.21.4 {@code Player.onClimbable}, which
 * 26.2 retains. Defer the inherited check to the native predicate.
 */
@MovementChange(emulates = ParkourVersion.V1_21)
public final class ScaffoldingClimbabilityWhileFlying implements PlayerClimbabilityFlyingGateHook {
    @Override
    public boolean shouldApplyFlyingVeto(Player player, boolean vanillaFlying) {
        return vanillaFlying && (player.isSpectator() || !player.getInBlockState().is(Blocks.SCAFFOLDING));
    }
}
