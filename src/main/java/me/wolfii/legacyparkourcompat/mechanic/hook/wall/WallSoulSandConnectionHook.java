package me.wolfii.legacyparkourcompat.mechanic.hook.wall;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.level.block.state.BlockState;

/** Filters the 1.16 support-shape wall connection to Soul Sand from the 1.15.2 player collision shape. */
@MechanicType("block.wall.soul-sand-connection")
public interface WallSoulSandConnectionHook extends VersionedMechanic {
    boolean suppressConnection(BlockState wall, BlockState neighbor, ParkourVersion selected);
}
