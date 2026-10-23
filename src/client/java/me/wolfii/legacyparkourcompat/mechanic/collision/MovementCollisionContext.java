package me.wolfii.legacyparkourcompat.mechanic.collision;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

/** A movement-query context retaining the original context for all vanilla predicates. */
public final class MovementCollisionContext extends EntityCollisionContext {
    private final CollisionContext delegate;
    private final AABB query;

    public MovementCollisionContext(CollisionContext delegate, Entity player, AABB query) {
        super(player, delegate.alwaysCollideWithFluid(), delegate.isPlacement());
        this.delegate = delegate;
        this.query = query;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, CollisionGetter getter, BlockPos pos) {
        return delegate.getCollisionShape(state, getter, pos);
    }

    public CollisionContext delegate() { return delegate; }
    public AABB query() { return query; }

    @Override public boolean isDescending() { return delegate.isDescending(); }
    @Override public boolean isAbove(VoxelShape shape, BlockPos pos, boolean fallback) { return delegate.isAbove(shape, pos, fallback); }
    @Override public boolean isHoldingItem(Item item) { return delegate.isHoldingItem(item); }
    @Override public boolean alwaysCollideWithFluid() { return delegate.alwaysCollideWithFluid(); }
    @Override public boolean canStandOnFluid(FluidState above, FluidState fluid) { return delegate.canStandOnFluid(above, fluid); }
    @Override public boolean isPlacement() { return delegate.isPlacement(); }
}
