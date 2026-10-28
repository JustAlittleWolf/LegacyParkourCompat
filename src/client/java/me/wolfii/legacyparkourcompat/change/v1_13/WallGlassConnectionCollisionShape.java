package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.WallCollisionShapeHook;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StainedGlassBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

/**
 * Restores the 1.13.2 glass disconnection in cobblestone wall collision while
 * preserving native connection decisions for every other neighbor.
 *
 * <p>In Ornithe Feather 1.13.2, {@code WallBlock#shouldConnectTo} rejects glass
 * through {@code Block#isExceptionForAttachment} before accepting a solid face.
 * Mojmap 1.14.4 {@code WallBlock#connectsTo} uses the narrower
 * {@code Block#isExceptionForConnection} list, so a sturdy glass face connects.
 * Feather 1.13.2 {@code PaneBlock#makeShapes} and
 * {@code WallBlock#getCollisionShape} use the component union reproduced here.
 * WallBlock's UP=false fallback calls PaneBlock#getCollisionShape, which uses
 * PaneBlock's outline array built with a 24/16 arm height. UP=true uses the
 * wall collision array and the independently resolved height.
 * This change removes only connected glass sides from the native wall state;
 * its other side flags remain the source for the old component union.
 */
@MovementChange(emulates = ParkourVersion.V1_13_1)
public final class WallGlassConnectionCollisionShape implements WallCollisionShapeHook {
    private final String blockId;

    private WallGlassConnectionCollisionShape(String blockId) {
        this.blockId = blockId;
    }

    @MovementChange.Factory
    public static List<WallGlassConnectionCollisionShape> variants() {
        return List.of(
            new WallGlassConnectionCollisionShape("minecraft:cobblestone_wall"),
            new WallGlassConnectionCollisionShape("minecraft:mossy_cobblestone_wall")
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
        if (!(state.getBlock() instanceof WallBlock) || vanillaShape.isEmpty()) {
            return Optional.empty();
        }
        if (historicalMaxY.isEmpty()) {
            throw new IllegalStateException("Wall height is unresolved for a nonempty historical wall shape");
        }

        boolean north = connected(state, WallBlock.NORTH) && !isLegacyGlass(level, pos, Direction.NORTH);
        boolean east = connected(state, WallBlock.EAST) && !isLegacyGlass(level, pos, Direction.EAST);
        boolean south = connected(state, WallBlock.SOUTH) && !isLegacyGlass(level, pos, Direction.SOUTH);
        boolean west = connected(state, WallBlock.WEST) && !isLegacyGlass(level, pos, Direction.WEST);
        boolean post = ((!north || east || !south || west) && (north || !east || south || !west))
            || !level.getBlockState(pos.above()).isAir();

        double maxY = historicalMaxY.getAsDouble() * 16.0; // Block.box accepts sixteenth-based coordinates.
        double armMaxY = post ? maxY : 24.0;
        VoxelShape shape = post ? Block.box(4.0, 0.0, 4.0, 12.0, maxY, 12.0) : Shapes.empty();
        if (north) {
            shape = Shapes.or(shape, Block.box(5.0, 0.0, 0.0, 11.0, armMaxY, 11.0));
        }
        if (east) {
            shape = Shapes.or(shape, Block.box(5.0, 0.0, 5.0, 16.0, armMaxY, 11.0));
        }
        if (south) {
            shape = Shapes.or(shape, Block.box(5.0, 0.0, 5.0, 11.0, armMaxY, 16.0));
        }
        if (west) {
            shape = Shapes.or(shape, Block.box(0.0, 0.0, 5.0, 11.0, armMaxY, 11.0));
        }
        return Optional.of(shape);
    }

    private static boolean connected(BlockState state, EnumProperty<WallSide> side) {
        return state.getValue(side) != WallSide.NONE;
    }

    private static boolean isLegacyGlass(BlockGetter level, BlockPos pos, Direction direction) {
        Block block = level.getBlockState(pos.relative(direction)).getBlock();
        return block == Blocks.GLASS || block instanceof StainedGlassBlock;
    }
}
