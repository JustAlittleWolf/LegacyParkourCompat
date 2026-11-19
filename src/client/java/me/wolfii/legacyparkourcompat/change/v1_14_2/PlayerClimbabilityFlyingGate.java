package me.wolfii.legacyparkourcompat.change.v1_14_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.PlayerClimbabilityFlyingGateHook;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;

/**
 * In 1.14–1.14.3 Yarn, {@code LivingEntity#isClimbing} admits scaffolding,
 * ladders, and vines for non-spectators. Ability-flying still reaches the
 * ordinary-air climb clamp in {@code LivingEntity#travel/method_18801}
 * before the self move. 1.14.4 Feather Gen2 retains both operations in
 * {@code LivingEntity#isClimbing/travel/m_19439509}.
 * In 26.2 unobfuscated, {@code Player#onClimbable} vetoes ability-flying
 * before {@code LivingEntity#onClimbable} can admit those blocks for
 * {@code LivingEntity#handleOnClimbable}.
 * The 1.13.2 provider preserves the earlier ladder and vine behavior.
 */
@MovementChange(emulates = ParkourVersion.V1_14_2)
public final class PlayerClimbabilityFlyingGate implements PlayerClimbabilityFlyingGateHook {
    @Override
    public boolean shouldApplyFlyingVeto(Player player, boolean vanillaFlying) {
        if (!vanillaFlying || !player.isLocalPlayer() || player.isSpectator()) {
            return vanillaFlying;
        }

        var block = player.getInBlockState().getBlock();
        return block != Blocks.LADDER && block != Blocks.VINE && block != Blocks.SCAFFOLDING;
    }
}
