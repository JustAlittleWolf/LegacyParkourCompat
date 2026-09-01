package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.BlockChanges;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeRegistry;
import me.wolfii.legacyparkourcompat.mechanic.hook.BlockCollisionShape;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

/**
 * 1.9–1.12 anvils are a 0.125-inset cuboid per axis. 1.13 replaced that with
 * the stepped model (0.75 base, 0.625-tall top). 1.8 keeps the shared mutating
 * box in {@link me.wolfii.legacyparkourcompat.change.v1_8.AnvilCollision}.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class SimpleAnvilCollision implements BlockCollisionShape {
    private static final VoxelShape AXIS_X = Shapes.box(0.0F, 0.0F, 0.125F, 1.0F, 1.0F, 0.875F);
    private static final VoxelShape AXIS_Z = Shapes.box(0.125F, 0.0F, 0.0F, 0.875F, 1.0F, 1.0F);

    private final String blockId;

    public SimpleAnvilCollision(String blockId) {
        this.blockId = blockId;
    }

    public static void register(MovementChangeRegistry registry) {
        BlockChanges.registerEach(registry, SimpleAnvilCollision::isVanillaAnvil, SimpleAnvilCollision::new);
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
        Direction facing = state.getValue(AnvilBlock.FACING);
        return Optional.of(facing.getAxis() == Direction.Axis.X ? AXIS_X : AXIS_Z);
    }
}
