package me.wolfii.legacyparkourcompat.mechanic.hook.block;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.level.block.state.BlockState;

@MechanicType("block.bubbleColumn.aboveContact")
public interface BubbleColumnAboveContactHook extends VersionedMechanic {
    boolean isAboveContact(BlockState stateAbove, boolean vanillaAboveContact);
}
