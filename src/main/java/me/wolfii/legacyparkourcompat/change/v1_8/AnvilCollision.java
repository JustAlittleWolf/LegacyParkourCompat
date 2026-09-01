package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.BlockChanges;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeRegistry;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.BlockCollisionShape;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 1.8 anvils share one AABB for every facing and damage variant. Looking at an
 * anvil mutates that box; later collision uses whatever was last set. 1.9 gave
 * each facing a fixed box. The 1.8 shape is the simple 0.125 inset cuboid.
 */
@MovementChange(emulates = ParkourVersion.V1_8)
public final class AnvilCollision implements BlockCollisionShape {
    private static final VoxelShape DEFAULT = Shapes.block();
    private static final VoxelShape AXIS_X = Shapes.box(0.0F, 0.0F, 0.125F, 1.0F, 1.0F, 0.875F);
    private static final VoxelShape AXIS_Z = Shapes.box(0.125F, 0.0F, 0.0F, 0.875F, 1.0F, 1.0F);
    private static final ConcurrentHashMap<UUID, VoxelShape> SHARED = new ConcurrentHashMap<>();

    private final String blockId;

    public AnvilCollision(String blockId) {
        this.blockId = blockId;
    }

    public static void register(MovementChangeRegistry registry) {
        BlockChanges.registerEach(registry, AnvilCollision::isVanillaAnvil, AnvilCollision::new);
    }

    private static boolean isVanillaAnvil(Block block) {
        if (!(block instanceof AnvilBlock)) {
            return false;
        }
        Identifier id = BuiltInRegistries.BLOCK.getKey(block);
        if (id == null) {
            return false;
        }
        String key = id.toString();
        return "minecraft:anvil".equals(key)
            || "minecraft:chipped_anvil".equals(key)
            || "minecraft:damaged_anvil".equals(key);
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
            return Optional.of(DEFAULT);
        }
        return Optional.of(SHARED.getOrDefault(playerId, DEFAULT));
    }

    @Override
    public Optional<VoxelShape> outlineShape(
        BlockState state,
        BlockGetter level,
        BlockPos pos,
        CollisionContext context
    ) {
        VoxelShape shape = trueShape(state);
        UUID playerId = playerId(context);
        if (playerId != null) {
            SHARED.put(playerId, shape);
        }
        return Optional.of(shape);
    }

    private static VoxelShape trueShape(BlockState state) {
        Direction facing = state.getValue(AnvilBlock.FACING);
        return facing.getAxis() == Direction.Axis.X ? AXIS_X : AXIS_Z;
    }

    private static UUID playerId(CollisionContext context) {
        Entity player = MovementRuntime.playerFrom(context);
        return player == null ? null : player.getUUID();
    }
}
