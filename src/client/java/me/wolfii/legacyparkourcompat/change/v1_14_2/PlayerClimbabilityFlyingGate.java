package me.wolfii.legacyparkourcompat.change.v1_14_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.PlayerClimbabilityFlyingGateHook;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;

/**
 * Preserves 1.14.4's inherited ladder/vine climbing response while flying.
 * Its {@code LivingEntity.isClimbing} has no player flying veto; 26.2
 * {@code Player.onClimbable} returns false before the inherited predicate when
 * flying. Suppress only that newer veto for local, non-spectator ladder/vine
 * contact; other climbable blocks retain the native result.
 * Sources: 1.14.4 Ornithe Feather Gen2
 * {@code net/minecraft/entity/LivingEntity#isClimbing}; 26.2 unobfuscated
 * {@code net/minecraft/world/entity/player/Player#onClimbable} and
 * {@code net/minecraft/world/entity/LivingEntity#onClimbable}.
 *
 * <p>The checked 1.14.0–1.14.4 sources retain this gate behavior. The audit
 * does not identify the exact patch introduction or establish runtime reachability.
 */
@MovementChange(emulates = ParkourVersion.V1_14_2)
public final class PlayerClimbabilityFlyingGate implements PlayerClimbabilityFlyingGateHook {
    @Override
    public boolean shouldApplyFlyingVeto(Player player, boolean vanillaFlying) {
        if (!vanillaFlying || !player.isLocalPlayer() || player.isSpectator()) {
            return vanillaFlying;
        }

        var block = player.getInBlockState().getBlock();
        return block != Blocks.LADDER && block != Blocks.VINE;
    }
}
