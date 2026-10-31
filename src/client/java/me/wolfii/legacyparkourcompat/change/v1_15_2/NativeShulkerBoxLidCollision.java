package me.wolfii.legacyparkourcompat.change.v1_15_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockCollisionShapeHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.Optional;

/** Keeps native dynamic lid collision for profiles below the accepted 1.16 range. */
@MovementChange(emulates = ParkourVersion.V1_15_2)
public final class NativeShulkerBoxLidCollision implements BlockCollisionShapeHook {
    private static final List<String> BLOCK_IDS = List.of(
        "minecraft:shulker_box", "minecraft:black_shulker_box", "minecraft:blue_shulker_box",
        "minecraft:brown_shulker_box", "minecraft:cyan_shulker_box", "minecraft:gray_shulker_box",
        "minecraft:green_shulker_box", "minecraft:light_blue_shulker_box", "minecraft:light_gray_shulker_box",
        "minecraft:lime_shulker_box", "minecraft:magenta_shulker_box", "minecraft:orange_shulker_box",
        "minecraft:pink_shulker_box", "minecraft:purple_shulker_box", "minecraft:red_shulker_box",
        "minecraft:white_shulker_box", "minecraft:yellow_shulker_box"
    );

    private final String blockId;

    private NativeShulkerBoxLidCollision(String blockId) {
        this.blockId = blockId;
    }

    @MovementChange.Factory
    public static List<NativeShulkerBoxLidCollision> createVariants() {
        return BLOCK_IDS.stream().map(NativeShulkerBoxLidCollision::new).toList();
    }

    @Override
    public String blockId() {
        return this.blockId;
    }

    @Override
    public Optional<VoxelShape> collisionShape(BlockState state, BlockGetter level, BlockPos pos,
                                                CollisionContext context) {
        return Optional.empty();
    }
}
