package me.wolfii.legacyparkourcompat.change.v1_16_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.collision.RawArrayVoxelShape;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockCollisionShapeHook;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.Optional;

/**
 * Restores the reachable residual lid strip lost by current shape encoding.
 * 1.16.5 Mojmap ShulkerBoxBlock.getShape and ShulkerBoxBlockEntity.getBoundingBox
 * pass the expanded AABB to Shapes.create, whose ArrayVoxelShape fallback keeps
 * the negative endpoint. 1.17.1 Mojmap uses Shulker.getProgressAabb and the
 * round-based Shapes.findBits path, which rounds the same residual to the full
 * cell. This changes only the player movement collision provider; block state
 * and entity interaction remain vanilla.
 */
@MovementChange(emulates = ParkourVersion.V1_16_2)
public final class ShulkerBoxLidCollision implements BlockCollisionShapeHook {
    private static final List<String> BLOCK_IDS = List.of(
        "minecraft:shulker_box", "minecraft:black_shulker_box", "minecraft:blue_shulker_box",
        "minecraft:brown_shulker_box", "minecraft:cyan_shulker_box", "minecraft:gray_shulker_box",
        "minecraft:green_shulker_box", "minecraft:light_blue_shulker_box", "minecraft:light_gray_shulker_box",
        "minecraft:lime_shulker_box", "minecraft:magenta_shulker_box", "minecraft:orange_shulker_box",
        "minecraft:pink_shulker_box", "minecraft:purple_shulker_box", "minecraft:red_shulker_box",
        "minecraft:white_shulker_box", "minecraft:yellow_shulker_box"
    );
    private static final float RESIDUAL_PROGRESS = 1.4901161193847656E-8F;

    private final String blockId;

    private ShulkerBoxLidCollision(String blockId) {
        this.blockId = blockId;
    }

    @MovementChange.Factory
    public static List<ShulkerBoxLidCollision> createVariants() {
        return BLOCK_IDS.stream().map(ShulkerBoxLidCollision::new).toList();
    }

    @Override
    public String blockId() {
        return this.blockId;
    }

    @Override
    public Optional<VoxelShape> collisionShape(BlockState state, BlockGetter level, BlockPos pos,
                                                CollisionContext context) {
        if (!(level.getBlockEntity(pos) instanceof ShulkerBoxBlockEntity shulkerBox)) {
            return Optional.empty();
        }
        Direction facing = state.getValue(ShulkerBoxBlock.FACING);
        float progress = shulkerBox.getProgress(1.0F);
        if (progress != RESIDUAL_PROGRESS
            || (facing != Direction.WEST && facing != Direction.DOWN && facing != Direction.NORTH)) {
            return Optional.empty();
        }

        AABB providerBounds = shulkerBox.getBoundingBox(state);
        return Optional.of(RawArrayVoxelShape.oneCell(providerBounds));
    }
}
