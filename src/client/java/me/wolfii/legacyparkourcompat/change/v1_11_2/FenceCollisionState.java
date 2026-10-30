package me.wolfii.legacyparkourcompat.change.v1_11_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.FenceCollisionStateHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.CrossCollisionBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;
import java.util.Set;

/**
 * Recomputes the complete supported legacy fence-connection mask at query time.
 *
 * <p>Ornithe Feather 1.11.2 {@code block/FenceBlock.java},
 * {@code addCollisions}, {@code shouldConnectTo} and {@code resolveVirtualProperties}
 * use the material/full-cube test; {@code block/StairsBlock.java},
 * {@code isCube}, rejects stairs. Ornithe Feather 1.12.2
 * {@code block/FenceBlock.java}, {@code shouldConnectTo} and
 * {@code block/StairsBlock.java}, {@code getFaceShape}, add the directional-face
 * connection for stairs. Current geometry is evaluated by the 26.2 unobfuscated
 * {@code block/CrossCollisionBlock.java}, {@code getCollisionShape}.
 */
@MovementChange(emulates = ParkourVersion.V1_11_2)
public final class FenceCollisionState implements FenceCollisionStateHook {
    private static final String AIR_ID = "minecraft:air";
    // 1.11.2 Ornithe Feather block/Blocks.java lists FENCE and the five wood variants;
    // current 26.2 unobfuscated block/Blocks.java exposes the corresponding OAK_FENCE and variants.
    private static final Set<String> LEGACY_FENCE_IDS = Set.of(
        "minecraft:oak_fence",
        "minecraft:spruce_fence",
        "minecraft:birch_fence",
        "minecraft:jungle_fence",
        "minecraft:acacia_fence",
        "minecraft:dark_oak_fence"
    );
    private static final Set<String> LEGACY_STAIR_IDS = Set.of(
        "minecraft:oak_stairs",
        // 1.11.2 Ornithe Feather block/Blocks.java STONE_STAIRS and
        // crafting/CraftingManager.registerShaped show that it is crafted from COBBLESTONE.
        // 26.2 unobfuscated util/datafix/fixes/BlockStateData.register maps legacy stone_stairs
        // to cobblestone_stairs; world/level/block/Blocks.java has a distinct STONE_STAIRS.
        "minecraft:cobblestone_stairs",
        "minecraft:brick_stairs",
        "minecraft:stone_brick_stairs",
        "minecraft:nether_brick_stairs",
        "minecraft:sandstone_stairs",
        "minecraft:spruce_stairs",
        "minecraft:birch_stairs",
        "minecraft:jungle_stairs",
        "minecraft:quartz_stairs",
        "minecraft:acacia_stairs",
        "minecraft:dark_oak_stairs",
        "minecraft:red_sandstone_stairs",
        "minecraft:purpur_stairs"
    );

    @Override
    public Optional<BlockState> collisionState(BlockState state, BlockGetter level, BlockPos pos) {
        if (!isLegacyFence(state) || isWaterlogged(state)) {
            return Optional.empty();
        }

        Connection north = connection(level.getBlockState(pos.north()));
        Connection east = connection(level.getBlockState(pos.east()));
        Connection south = connection(level.getBlockState(pos.south()));
        Connection west = connection(level.getBlockState(pos.west()));
        if (north == null || east == null || south == null || west == null) {
            return Optional.empty();
        }

        return Optional.of(state
            .setValue(CrossCollisionBlock.NORTH, north.connects())
            .setValue(CrossCollisionBlock.EAST, east.connects())
            .setValue(CrossCollisionBlock.SOUTH, south.connects())
            .setValue(CrossCollisionBlock.WEST, west.connects()));
    }

    private static Connection connection(BlockState neighbor) {
        String id = MovementRuntime.blockId(neighbor.getBlock());
        if (id.equals(AIR_ID)) {
            return Connection.DISCONNECTED;
        }
        if (LEGACY_FENCE_IDS.contains(id)) {
            if (!(neighbor.getBlock() instanceof FenceBlock) || isWaterlogged(neighbor)) {
                return null;
            }
            return Connection.CONNECTED;
        }
        if (LEGACY_STAIR_IDS.contains(id)) {
            if (!(neighbor.getBlock() instanceof StairBlock) || isWaterlogged(neighbor)) {
                return null;
            }
            return Connection.DISCONNECTED;
        }
        return null;
    }

    private static boolean isLegacyFence(BlockState state) {
        return state.getBlock() instanceof FenceBlock
            && LEGACY_FENCE_IDS.contains(MovementRuntime.blockId(state.getBlock()));
    }

    private static boolean isWaterlogged(BlockState state) {
        return state.hasProperty(CrossCollisionBlock.WATERLOGGED)
            && Boolean.TRUE.equals(state.getValue(CrossCollisionBlock.WATERLOGGED));
    }

    private enum Connection {
        CONNECTED(true),
        DISCONNECTED(false);

        private final boolean connects;

        Connection(boolean connects) {
            this.connects = connects;
        }

        boolean connects() {
            return this.connects;
        }
    }
}
