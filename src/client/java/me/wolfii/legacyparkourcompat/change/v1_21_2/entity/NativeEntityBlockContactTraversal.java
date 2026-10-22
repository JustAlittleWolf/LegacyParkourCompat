package me.wolfii.legacyparkourcompat.change.v1_21_2.entity;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.EntityBlockContactTraversalHook;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Consumer;

/** Restores native traversal from the 1.21.2 contact migration onward. */
@MovementChange(emulates = ParkourVersion.V1_21_2)
public final class NativeEntityBlockContactTraversal implements EntityBlockContactTraversalHook {
    @Override
    public void afterMove(Entity entity, Consumer<BlockState> onInsideBlock) {
    }

    @Override
    public boolean suppressPostTravel(Entity entity) {
        return false;
    }
}
