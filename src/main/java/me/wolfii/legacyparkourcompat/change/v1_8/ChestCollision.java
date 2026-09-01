package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.BlockChanges;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeRegistry;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.BlockCollisionShape;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 1.8 chests share one AABB per chest block (chest and trapped chest are
 * independent). Looking at a chest mutates that box; 1.9 gave each connection
 * a fixed box.
 */
@MovementChange(emulates = ParkourVersion.V1_8)
public final class ChestCollision implements BlockCollisionShape {
    private static final VoxelShape SINGLE = Shapes.box(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
    private static final VoxelShape NORTH = Shapes.box(0.0625F, 0.0F, 0.0F, 0.9375F, 0.875F, 0.9375F);
    private static final VoxelShape SOUTH = Shapes.box(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 1.0F);
    private static final VoxelShape WEST = Shapes.box(0.0F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
    private static final VoxelShape EAST = Shapes.box(0.0625F, 0.0F, 0.0625F, 1.0F, 0.875F, 0.9375F);

    private final String blockId;
    private final ConcurrentHashMap<UUID, VoxelShape> shared = new ConcurrentHashMap<>();

    public ChestCollision(String blockId) {
        this.blockId = blockId;
    }

    public static void register(MovementChangeRegistry registry) {
        BlockChanges.registerEach(registry, ChestCollision::isVanillaChest, ChestCollision::new);
    }

    private static boolean isVanillaChest(Block block) {
        Identifier id = BuiltInRegistries.BLOCK.getKey(block);
        if (id == null) {
            return false;
        }
        String key = id.toString();
        return "minecraft:chest".equals(key) || "minecraft:trapped_chest".equals(key);
    }

    @Override
    public String blockId() {
        return this.blockId;
    }

    @Override
    public Optional<VoxelShape> collisionShape(
        BlockState state,
        BlockGetter level,
        BlockPos pos,
        CollisionContext context
    ) {
        UUID playerId = playerId(context);
        if (playerId == null) {
            return Optional.of(SINGLE);
        }
        return Optional.of(this.shared.getOrDefault(playerId, SINGLE));
    }

    @Override
    public Optional<VoxelShape> outlineShape(
        BlockState state,
        BlockGetter level,
        BlockPos pos,
        CollisionContext context
    ) {
        VoxelShape shape = trueShape(state.getBlock(), level, pos);
        UUID playerId = playerId(context);
        if (playerId != null) {
            this.shared.put(playerId, shape);
        }
        return Optional.of(shape);
    }

    private static VoxelShape trueShape(Block block, BlockGetter level, BlockPos pos) {
        if (level.getBlockState(pos.north()).is(block)) {
            return NORTH;
        }
        if (level.getBlockState(pos.south()).is(block)) {
            return SOUTH;
        }
        if (level.getBlockState(pos.west()).is(block)) {
            return WEST;
        }
        if (level.getBlockState(pos.east()).is(block)) {
            return EAST;
        }
        return SINGLE;
    }

    private static UUID playerId(CollisionContext context) {
        Entity player = MovementRuntime.playerFrom(context);
        return player == null ? null : player.getUUID();
    }
}
