package me.wolfii.legacyparkourcompat.mechanic.hook.player;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

@MechanicType("player.feet_block_state")
public interface FeetBlockStateHook extends VersionedMechanic {
    Optional<BlockState> getFeetBlockState(Entity entity);
}
