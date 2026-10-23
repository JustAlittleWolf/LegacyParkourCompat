package me.wolfii.legacyparkourcompat.change.v1_11_2;

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

/**
 * Keeps the accepted 1.12.2-to-1.13 anvil comparison bounded. The exact
 * Ornithe Feather sources are AnvilBlock#getShape and Block#getCollisionShape
 * in both releases: 1.12.2 selects a full-height cuboid, while 1.13 first
 * selects the component union (retained by 1.13.2). Earlier profiles retain
 * their existing native fallback because this finding does not establish their
 * anvil geometry.
 */
@MovementChange(emulates = ParkourVersion.V1_11_2)
public final class AnvilCollisionShapeNativeFence implements BlockCollisionShapeHook {
    private static final List<String> ANVIL_BLOCKS = List.of(
        "minecraft:anvil",
        "minecraft:chipped_anvil",
        "minecraft:damaged_anvil"
    );

    private final String blockId;

    private AnvilCollisionShapeNativeFence(String blockId) {
        this.blockId = blockId;
    }

    @MovementChange.Factory
    public static List<AnvilCollisionShapeNativeFence> createVariants() {
        return ANVIL_BLOCKS.stream().map(AnvilCollisionShapeNativeFence::new).toList();
    }

    @Override
    public String blockId() {
        return this.blockId;
    }

    @Override
    public Optional<VoxelShape> collisionShape(
        BlockState state, BlockGetter level, BlockPos pos, CollisionContext context
    ) {
        return Optional.empty();
    }
}
