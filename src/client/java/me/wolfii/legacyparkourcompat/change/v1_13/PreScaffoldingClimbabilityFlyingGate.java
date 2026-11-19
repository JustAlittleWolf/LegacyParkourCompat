package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.PlayerClimbabilityFlyingGateHook;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;

/**
 * In 1.13.2 Feather Gen2, {@code LivingEntity#isClimbing} admits ladders and
 * vines while ability-flying. Scaffolding first appears in 1.14 Yarn
 * {@code Blocks#field_16492} and joins that version's climbability predicate.
 * Retain the 26.2 unobfuscated {@code Player#onClimbable} flying gate for
 * scaffolding on profiles from before its introduction.
 */
@MovementChange(emulates = ParkourVersion.V1_13_1)
public final class PreScaffoldingClimbabilityFlyingGate implements PlayerClimbabilityFlyingGateHook {
    @Override
    public boolean shouldApplyFlyingVeto(Player player, boolean vanillaFlying) {
        if (!vanillaFlying || !player.isLocalPlayer() || player.isSpectator()) {
            return vanillaFlying;
        }

        var block = player.getInBlockState().getBlock();
        return block != Blocks.LADDER && block != Blocks.VINE;
    }
}
