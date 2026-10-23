package me.wolfii.legacyparkourcompat.change.v1_11_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.BlockMovementCollisionHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.BlockComponentClipHook;
import org.jspecify.annotations.Nullable;
import java.util.List;

/** Resolver fence: the accepted 1.12.2 evidence does not prove earlier profiles. */
@MovementChange(emulates = ParkourVersion.V1_11_2)
public final class UnprovedBrewingStandCollision implements BlockMovementCollisionHook {
    @Override public String variant() { return "minecraft:brewing_stand"; }
    @Override
    public VoxelShape collect(Entity player, BlockPos pos, AABB query, VoxelShape nativeShape,
                              List<VoxelShape> providerParts, @Nullable BlockComponentClipHook componentClip) {
        return nativeShape;
    }
}
