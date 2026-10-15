package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.ClimbHeldJumpImpulseHook;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Ornithe Feather 1.8.9 and 1.13.2 LivingEntity.moveRelative require
 * collidingHorizontally && isClimbing before the post-move Y=0.2 write.
 * Ornithe Feather 1.14 LivingEntity.moveRelative adds jumping as an OR
 * operand; 1.14.4 retains it. 26.2 unobfuscated
 * LivingEntity.handleRelativeFrictionAndCalculateMovement still has that
 * operand. Remove it only for the era-existing ladder/vine states; native
 * onClimbable and horizontal-collision admission remain intact. Trapdoor
 * applicability and modern scaffolding/powder-snow climbing remain native.
 */
@MovementChange(emulates = ParkourVersion.V1_13)
public final class ClimbHeldJumpImpulse implements ClimbHeldJumpImpulseHook {
    @Override
    public boolean heldJump(BlockState state, boolean jumping) {
        return !state.is(Blocks.LADDER) && !state.is(Blocks.VINE) && jumping;
    }
}
