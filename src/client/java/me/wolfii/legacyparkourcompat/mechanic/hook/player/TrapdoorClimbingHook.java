package me.wolfii.legacyparkourcompat.mechanic.hook.player;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.level.block.state.BlockState;

@MechanicType("player.trapdoor_climbing")
public interface TrapdoorClimbingHook extends VersionedMechanic {
    boolean blocksClimbing(BlockState state);
}
