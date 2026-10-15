package me.wolfii.legacyparkourcompat.mechanic.hook.travel;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.level.block.state.BlockState;

/** Jump-held operand in the post-movement climb impulse gate. */
@MechanicType("player.climb.held_jump_impulse")
public interface ClimbHeldJumpImpulseHook extends VersionedMechanic {
    boolean heldJump(BlockState state, boolean jumping);
}
