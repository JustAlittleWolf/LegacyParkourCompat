package me.wolfii.legacyparkourcompat.change.v1_20_5.entity;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.EntityBlockContactTraversalHook;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Consumer;

/** Keeps native 1.20.5-1.20.6 contact traversal. */
@MovementChange(emulates = ParkourVersion.V1_20_5)
public final class NativeEntityBlockContactTraversal implements EntityBlockContactTraversalHook {
    @Override
    public void afterMove(Entity entity, Consumer<BlockState> onInsideBlock) {
    }

    @Override
    public boolean suppressPostTravel(Entity entity) {
        return false;
    }
}
