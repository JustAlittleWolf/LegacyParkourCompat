package me.wolfii.legacyparkourcompat.change.v1_8_5;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.BlockComponentClipHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.BlockMovementCollisionHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

import java.util.List;

/** Preserves the ordinary dry cauldron union before the exact 1.8.9 movement witness. */
@MovementChange(emulates = ParkourVersion.V1_8_5)
public final class CauldronMovementNative implements BlockMovementCollisionHook {
    @Override public String variant() { return "minecraft:cauldron"; }

    @Override
    public VoxelShape collect(Entity player, BlockPos pos, AABB query, VoxelShape nativeShape,
                              List<VoxelShape> providerParts, @Nullable BlockComponentClipHook componentClip) {
        return nativeShape;
    }
}
