package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockCollisionCollectionHook;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;
import java.util.List;

/**
 * Lower native fence: closest-later resolution would otherwise select the
 * V1_13 collector for V1_12 and earlier profiles. Empty keeps native behavior.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class NativeBlockCollisionCollection implements BlockCollisionCollectionHook {
    @Override
    public StreamOrder streamOrder() {
        return StreamOrder.NATIVE;
    }

    @Override
    public Optional<List<VoxelShape>> collect(CollectorQuery query, BlockCollisionProvider provider) {
        return Optional.empty();
    }
}
