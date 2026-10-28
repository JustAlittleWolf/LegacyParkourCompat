package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.WaterClimbImpulseHook;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Ornithe Feather 1.13.2 LivingEntity.moveRelative water branch has no
 * post-move climb impulse. Ornithe Feather 1.14 adds horizontal collision
 * && isClimbing -> Y=0.2 before drag; 1.14.4 and 26.2 travelInWater retain
 * it. Omit both the predicate call and impulse for ladder/vine water contact.
 * Newer climbing blocks and pre-1.13 modern swimming retain native behavior;
 * other old climbing-state applicability remains unbound.
 */
@MovementChange(emulates = ParkourVersion.V1_13_1)
public final class WaterClimbImpulse implements WaterClimbImpulseHook {
    @Override
    public boolean onClimbable(Player player, ParkourVersion selected, VanillaFn<Boolean> nativeCheck) {
        if (selected.olderThan(ParkourVersion.V1_13) && player.isSwimming()) {
            return nativeCheck.get();
        }
        BlockState state = player.getInBlockState();
        return !state.is(Blocks.LADDER) && !state.is(Blocks.VINE) && nativeCheck.get();
    }
}
