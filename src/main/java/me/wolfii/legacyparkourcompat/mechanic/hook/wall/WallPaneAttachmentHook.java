package me.wolfii.legacyparkourcompat.mechanic.hook.wall;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.level.block.state.BlockState;

/** Filters the 1.16 wall connection to pane/bar neighbors from the 1.15.2 player collision shape. */
@MechanicType("block.wall.pane-attachment")
public interface WallPaneAttachmentHook extends VersionedMechanic {
    boolean suppressAttachment(BlockState neighbor, ParkourVersion selected);
}
