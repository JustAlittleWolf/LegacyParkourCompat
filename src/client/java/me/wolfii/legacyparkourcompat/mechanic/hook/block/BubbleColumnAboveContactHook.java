package me.wolfii.legacyparkourcompat.mechanic.hook.block;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import net.minecraft.world.level.block.state.BlockState;

@MechanicType("block.bubbleColumn.aboveContact")
public interface BubbleColumnAboveContactHook extends VersionedMechanic {
    boolean isAboveContact(ParkourVersion selected, BlockState stateAbove, boolean vanillaAboveContact);
}
