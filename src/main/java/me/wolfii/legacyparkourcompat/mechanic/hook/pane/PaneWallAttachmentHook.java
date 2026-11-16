package me.wolfii.legacyparkourcompat.mechanic.hook.pane;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.level.block.state.BlockState;

/** Filters the 1.16 pane/bar attachment to walls from the 1.15.2 player collision shape. */
@MechanicType("block.pane.wall-attachment")
public interface PaneWallAttachmentHook extends VersionedMechanic {
    boolean suppressAttachment(BlockState neighbor, ParkourVersion selected);
}
