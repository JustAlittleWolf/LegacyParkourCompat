package me.wolfii.legacyparkourcompat.change.v1_8_9;

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
 * Preserves the existing native fallback through the 1.8.9 profile. Ornithe
 * Feather 1.8.9 AnvilBlock#updateShape writes shared mutable bounds, while
 * Block#addCollisions and Block#getCollisionShape read those bounds without
 * refreshing them on World#getCollisions' player movement path. Other Anvil
 * queries can write the bounds first, so this source does not establish a
 * closed per-state result. In 1.9, AnvilBlock#getShape selects the queried
 * state's box and Block#getCollisionShape delegates to it; 1.9.4 through 1.12.2
 * retain those per-state axis boxes. Keep those accepted profiles on the V1_12
 * cuboid provider while leaving the unresolved 1.8.9 caller context native.
 */
@MovementChange(emulates = ParkourVersion.V1_8_9)
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
