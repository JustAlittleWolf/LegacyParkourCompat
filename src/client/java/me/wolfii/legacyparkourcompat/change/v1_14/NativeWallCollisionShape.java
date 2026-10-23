package me.wolfii.legacyparkourcompat.change.v1_14;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.WallCollisionShapeHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

/**
 * Keeps native collision shapes from 1.14 onward. Feather 1.13.2
 * {@code WallBlock#shouldConnectTo} rejects glass through
 * {@code Block#isExceptionForAttachment}; Mojmap 1.14.4
 * {@code WallBlock#connectsTo} accepts a sturdy glass face because
 * {@code Block#isExceptionForConnection} no longer includes glass.
 */
@MovementChange(emulates = ParkourVersion.V1_14)
public final class NativeWallCollisionShape implements WallCollisionShapeHook {
    private final String blockId;

    private NativeWallCollisionShape(String blockId) {
        this.blockId = blockId;
    }

    @MovementChange.Factory
    public static List<NativeWallCollisionShape> variants() {
        return List.of(
            new NativeWallCollisionShape("minecraft:cobblestone_wall"),
            new NativeWallCollisionShape("minecraft:mossy_cobblestone_wall")
        );
    }

    @Override
    public String blockId() {
        return this.blockId;
    }

    @Override
    public Optional<VoxelShape> horizontalCollisionShape(
        BlockState state,
        BlockGetter level,
        BlockPos pos,
        VoxelShape vanillaShape,
        OptionalDouble historicalMaxY
    ) {
        return Optional.empty();
    }
}
