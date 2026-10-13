package me.wolfii.legacyparkourcompat.change.v1_10;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockCollisionShapeHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

/**
 * Farmland used a full-block collision shape through Minecraft 1.10.
 *
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.10.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java:
 *   public Box getShape(BlockState state, WorldView world, BlockPos pos).
 *   public void onFallenOn(World world, BlockPos pos, Entity entity, float fallDistance).
 * 1.9.4/ornithe-feather/net/minecraft/block/FarmlandBlock.java:
 *   public Box getShape(BlockState state, WorldView world, BlockPos pos).
 *   public Box getCollisionShape(BlockState state, World world, BlockPos pos).
 *   public void onFallenOn(World world, BlockPos pos, Entity entity, float fallDistance).
 *   public Box getShape(BlockState state, WorldView world, BlockPos pos).
 *   public Box getCollisionShape(BlockState state, World world, BlockPos pos).
 *   public void onFallenOn(World world, BlockPos pos, Entity entity, float fallDistance).
 * Remaining source/applicability gates:
 *   Later applicability gate: exact introduction of campaign-F001-farmland-collision-height is unknown within (1.9.4, 1.10.2]
 *   Exact independent source verdict/snapshot identity for F003-farmland-collision
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_10)
public final class FarmlandFullCollision implements BlockCollisionShapeHook {
    @Override
    public String blockId() {
        return "minecraft:farmland";
    }

    @Override
    public Optional<VoxelShape> collisionShape(
        BlockState state,
        BlockGetter level,
        BlockPos pos,
        CollisionContext context
    ) {
        return Optional.of(Shapes.block());
    }
}
