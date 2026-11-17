package me.wolfii.legacyparkourcompat.change.v1_21_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.EntityInsideShapeHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

/**
 * Mojmap 1.21.4 NetherPortalBlock inherits BlockBehaviour#getEntityInsideCollisionShape,
 * which returns the full-cell sentinel. Mojmap 1.21.5 NetherPortalBlock overrides it
 * with the portal outline. Keep that override from resolving for older profiles.
 */
@MovementChange(emulates = ParkourVersion.V1_21_4)
public final class NativeNetherPortalEntityInsideShape implements EntityInsideShapeHook {
    @Override
    public String variant() {
        return "minecraft:nether_portal";
    }

    @Override
    public Optional<VoxelShape> entityInsideShape(
        BlockBehaviour.BlockStateBase state, BlockGetter level, BlockPos pos, Entity entity
    ) {
        return Optional.empty();
    }
}
