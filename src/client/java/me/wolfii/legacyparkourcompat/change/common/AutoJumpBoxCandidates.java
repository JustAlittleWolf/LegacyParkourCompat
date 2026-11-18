package me.wolfii.legacyparkourcompat.change.common;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockCollisionShapeHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.AutoJumpCollisionOrderHook.ProbeSegments;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.border.BorderStatus;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/** Shared source-box traversal for admitted pre-1.13 AutoJump queries. */
public final class AutoJumpBoxCandidates {
    private static final AABB FENCE_POST = new AABB(0.375, 0.0, 0.375, 0.625, 1.5, 0.625);
    private static final AABB BOTTOM_SLAB = new AABB(0.0, 0.0, 0.0, 1.0, 0.5, 1.0);
    private static final AABB BREWING_STAND_STEM =
        new AABB(7.0 / 16.0, 0.0, 7.0 / 16.0, 9.0 / 16.0, 14.0 / 16.0, 9.0 / 16.0);
    private static final AABB BREWING_STAND_PLATE = new AABB(0.0, 0.0, 0.0, 1.0, 2.0 / 16.0, 1.0);

    private AutoJumpBoxCandidates() {}

    public enum ScanRules {
        SKIP_BOTH_CAPS_FENCE_SLAB(true, false, false),
        SKIP_BOTH_CAPS_WITH_BREWING_STAND(true, true, false),
        SKIP_TOP_CAP_INTERIOR(false, true, false),
        SKIP_TOP_CAP_WITH_BORDER(false, true, true);

        private final boolean skipBottomEdgeCap;
        private final boolean brewingStand;
        private final boolean border;

        ScanRules(boolean skipBottomEdgeCap, boolean brewingStand, boolean border) {
            this.skipBottomEdgeCap = skipBottomEdgeCap;
            this.brewingStand = brewingStand;
            this.border = border;
        }
    }

    private record Cell(BlockPos pos, BlockState state) {}

    private record BorderProbe(Direction face, double edge, int floorY, int row) {
        private int outwardStep() {
            return face.getAxisDirection().getStep();
        }

        private Direction.Axis tangent() {
            return face.getAxis() == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
        }

        private double outward(Vec3 point) {
            return outwardStep() * point.get(face.getAxis());
        }

        private double outwardCell(int x, int z) {
            int coordinate = face.getAxis() == Direction.Axis.X ? x : z;
            return outwardStep() > 0 ? coordinate : -coordinate - 1.0;
        }

        private boolean isFloor(int x, int y, int z) {
            int tangent = face.getAxis() == Direction.Axis.X ? z : x;
            double normal = outwardCell(x, z);
            return y == floorY && tangent == row && (normal == edge - 2.0 || normal == edge - 1.0);
        }

        private BlockPos clearanceSample() {
            int outside = face.getAxisDirection() == Direction.AxisDirection.POSITIVE ? (int) edge : -(int) edge - 1;
            return face.getAxis() == Direction.Axis.X ? new BlockPos(outside, floorY + 2, row)
                : new BlockPos(row, floorY + 2, outside);
        }
    }

    public static Iterable<VoxelShape> collect(Player player, CollisionGetter level, AABB query,
        ParkourVersion selected, VanillaFn<Iterable<VoxelShape>> vanilla, ScanRules rules) {
        return collect(player, level, query, selected, vanilla, rules, null);
    }

    public static Iterable<VoxelShape> collect(Player player, CollisionGetter level, AABB query,
        ParkourVersion selected, VanillaFn<Iterable<VoxelShape>> vanilla, ScanRules rules,
        @Nullable ProbeSegments probes) {
        boolean early = !rules.border;
        if (early && !admitEarlyQuery(player, level, query)) {
            return AutoJumpCollisionCandidates.blocksBeforeEntities(player, level, query, selected, vanilla);
        }
        int x0 = Mth.floor(query.minX) - 1;
        int x1 = Mth.ceil(query.maxX) + 1;
        int y0 = Mth.floor(query.minY) - 1;
        int y1 = Mth.ceil(query.maxY) + 1;
        int z0 = Mth.floor(query.minZ) - 1;
        int z1 = Mth.ceil(query.maxZ) + 1;
        var border = level.getWorldBorder();
        // Include native epsilon-expanded cells in admission, even if the old scan skips them.
        int minX = Math.min(x0, Mth.floor(query.minX - 1.0E-7) - 1);
        int maxX = Math.max(x1 - 1, Mth.floor(query.maxX + 1.0E-7) + 1);
        int minY = Math.min(y0, Mth.floor(query.minY - 1.0E-7) - 1);
        int maxY = Math.max(y1 - 1, Mth.floor(query.maxY + 1.0E-7) + 1);
        int minZ = Math.min(z0, Mth.floor(query.minZ - 1.0E-7) - 1);
        int maxZ = Math.max(z1 - 1, Mth.floor(query.maxZ + 1.0E-7) + 1);
        boolean west = minX < border.getMinX();
        boolean east = maxX + 1.0 > border.getMaxX();
        boolean north = minZ < border.getMinZ();
        boolean south = maxZ + 1.0 > border.getMaxZ();
        int crossedFaces = (west ? 1 : 0) + (east ? 1 : 0) + (north ? 1 : 0) + (south ? 1 : 0);
        if (crossedFaces > 1) {
            return AutoJumpCollisionCandidates.blocksBeforeEntities(player, level, query, selected, vanilla);
        }
        BorderProbe borderProbe = null;
        if (crossedFaces == 1) {
            Direction face = west ? Direction.WEST : east ? Direction.EAST : north ? Direction.NORTH : Direction.SOUTH;
            borderProbe = admitBorder(player, level, query, selected, border, face, minY, maxY, early);
            if (borderProbe == null || (early && !admitsEarlyBorder(player, borderProbe, probes,
                minX, maxX, minY, maxY, minZ, maxZ))) {
                return AutoJumpCollisionCandidates.blocksBeforeEntities(player, level, query, selected, vanilla);
            }
        }
        boolean earlyInterior = early && borderProbe == null;
        int stoneSupportCells = 0;
        List<Cell> cells = new ArrayList<>();
        BlockPos brewingStand = null;
        boolean brewingStandSurroundings = true;
        boolean fenceCandidate = false;
        boolean slabSupportsPlayer = false;
        AABB playerBox = player.getBoundingBox();
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        CollisionContext context = CollisionContext.of(player);
        for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {
                var chunk = level.getChunkForCollisions(SectionPos.blockToSectionCoord(x), SectionPos.blockToSectionCoord(z));
                if (chunk == null) {
                    return AutoJumpCollisionCandidates.blocksBeforeEntities(player, level, query, selected, vanilla);
                }
                for (int y = minY; y <= maxY; y++) {
                    pos.set(x, y, z);
                    BlockState state = chunk.getBlockState(pos);
                    boolean edgeX = x == x0 || x == x1 - 1;
                    boolean edgeZ = z == z0 || z == z1 - 1;
                    boolean cap = y == y1 - 1 || (rules.skipBottomEdgeCap && y == y0);
                    boolean visited = x >= x0 && x < x1 && y >= y0 && y < y1 && z >= z0 && z < z1
                        && !(edgeX && edgeZ) && (!(edgeX || edgeZ) || !cap);
                    if (borderProbe != null) {
                        // Exactly the two full-cube floor cells support the whole footprint.
                        if (borderProbe.isFloor(x, y, z)) {
                            if (!state.is(Blocks.STONE) || context.getCollisionShape(state, level, pos) != Shapes.block()) {
                                return AutoJumpCollisionCandidates.blocksBeforeEntities(player, level, query, selected, vanilla);
                            }
                            stoneSupportCells++;
                        } else if (!state.is(Blocks.AIR)) {
                            return AutoJumpCollisionCandidates.blocksBeforeEntities(player, level, query, selected, vanilla);
                        }
                        if (visited && borderProbe.outwardCell(x, z) >= borderProbe.edge()) {
                            cells.add(new Cell(pos.immutable(), state));
                        }
                        continue;
                    }
                    if (state.is(Blocks.BREWING_STAND)) {
                        if (!rules.brewingStand || brewingStand != null || !visited) {
                            return AutoJumpCollisionCandidates.blocksBeforeEntities(player, level, query, selected, vanilla);
                        }
                        brewingStand = pos.immutable();
                    } else {
                        if (!supported(state, level, pos)) {
                            return AutoJumpCollisionCandidates.blocksBeforeEntities(player, level, query, selected, vanilla);
                        }
                        if (earlyInterior && !state.isAir()) {
                            boolean slab = isDryBottomSlab(state);
                            AABB expected = slab ? BOTTOM_SLAB : FENCE_POST;
                            if (y < 0 || y >= 256
                                || !context.getCollisionShape(state, level, pos).toAabbs().equals(List.of(expected))) {
                                return AutoJumpCollisionCandidates.blocksBeforeEntities(player, level, query, selected, vanilla);
                            }
                            fenceCandidate |= !slab && visited && query.intersects(expected.move(pos));
                            slabSupportsPlayer |= slab && y + 0.5 == playerBox.minY
                                && x + 1.0 > playerBox.minX && x < playerBox.maxX
                                && z + 1.0 > playerBox.minZ && z < playerBox.maxZ;
                        }
                        brewingStandSurroundings &= state.is(Blocks.AIR)
                            || isDryBottomSlab(state) && y + 0.5 <= query.minY;
                    }
                    if (!state.isAir() && visited) {
                        cells.add(new Cell(pos.immutable(), state));
                    }
                }
            }
        }
        if (borderProbe != null) {
            BlockPos head = BlockPos.containing(player.getX(), player.getBoundingBox().maxY, player.getZ());
            if (stoneSupportCells != 2 || !loadedState(level, head).is(Blocks.AIR)
                || !loadedState(level, head.above()).is(Blocks.AIR)
                || !loadedState(level, borderProbe.clearanceSample()).is(Blocks.AIR)) {
                return AutoJumpCollisionCandidates.blocksBeforeEntities(player, level, query, selected, vanilla);
            }
            return () -> cells.stream().map(cell -> new AABB(cell.pos()))
                .filter(query::intersects).map(Shapes::create).iterator();
        }
        if (earlyInterior && (!slabSupportsPlayer || (brewingStand == null && !fenceCandidate))) {
            return AutoJumpCollisionCandidates.blocksBeforeEntities(player, level, query, selected, vanilla);
        }
        List<VoxelShape> standParts = brewingStand == null || !brewingStandSurroundings ? List.of()
            : brewingStandParts(player, level, query, context, brewingStand, selected, earlyInterior);
        if (brewingStand != null && standParts.isEmpty()) {
            return AutoJumpCollisionCandidates.blocksBeforeEntities(player, level, query, selected, vanilla);
        }

        Iterable<VoxelShape> blocks = () -> cells.stream().flatMap(cell -> {
            if (cell.state().is(Blocks.BREWING_STAND)) {
                return standParts.stream().map(shape -> shape.move(cell.pos()).bounds());
            }
            List<AABB> parts = context.getCollisionShape(cell.state(), level, cell.pos()).move(cell.pos()).toAabbs();
            if (parts.size() != 1) {
                throw new IllegalStateException("Admitted auto-jump provider must supply one collision box: " + cell);
            }
            return Stream.of(parts.getFirst());
        })
            .filter(query::intersects).map(Shapes::create).iterator();
        return earlyInterior ? blocks
            : com.google.common.collect.Iterables.concat(blocks, level.getEntityCollisions(player, query));
    }

    private static boolean admitEarlyQuery(Player player, CollisionGetter level, AABB query) {
        float width = player.getBbWidth();
        float height = player.getBbHeight();
        double limit = WorldBorder.MAX_CENTER_COORDINATE;
        return player instanceof LocalPlayer localPlayer && Minecraft.getInstance().player == localPlayer
            && level instanceof Level world && world == player.level() && world.isClientSide()
            && player.getPose() == Pose.STANDING && player.onGround() && !player.noPhysics
            && !player.isSpectator() && !player.isShiftKeyDown() && !player.isPassenger()
            && !player.isFallFlying() && !player.getAbilities().flying && !player.onClimbable()
            && !player.isInWater() && !player.isInLava() && width == 0.6F && height == 1.8F
            && !player.hasEffect(MobEffects.JUMP_BOOST)
            && query.minX > -limit && query.maxX < limit && query.minZ > -limit && query.maxZ < limit
            && query.getXsize() > 0.0 && query.getXsize() <= 16.0
            && query.getZsize() > 0.0 && query.getZsize() <= 16.0
            && query.minY == player.getY() && query.maxY == player.getY() + height
            && query.minY >= 0.0 && query.maxY <= 256.0
            && query.minX <= player.getX() - width && query.maxX >= player.getX() + width
            && query.minZ <= player.getZ() - width && query.maxZ >= player.getZ() + width
            && world.getEntities(player, query.inflate(0.25), entity -> true).isEmpty();
    }

    private static BorderProbe admitBorder(Player player, CollisionGetter level, AABB query, ParkourVersion selected,
        WorldBorder border, Direction face, int minY, int maxY, boolean early) {
        double west = border.getMinX();
        double east = border.getMaxX();
        double north = border.getMinZ();
        double south = border.getMaxZ();
        if ((!early && selected != ParkourVersion.V1_12) || !(player instanceof LocalPlayer)
            || !(level instanceof Level clientLevel) || clientLevel != player.level() || !clientLevel.isClientSide()
            || border.getStatus() != BorderStatus.STATIONARY || east != Math.rint(east) || west != Math.rint(west)
            || north != Math.rint(north) || south != Math.rint(south) || minY < level.getMinY() || maxY > level.getMaxY()) {
            return null;
        }

        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();
        double width = player.getBbWidth();
        AABB box = player.getBoundingBox();
        Direction.Axis axis = face.getAxis();
        Direction.Axis tangent = axis == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
        boolean positive = face.getAxisDirection() == Direction.AxisDirection.POSITIVE;
        double edge = switch (face) {
            case EAST -> east;
            case WEST -> -west;
            case NORTH -> -north;
            case SOUTH -> south;
            default -> throw new IllegalArgumentException("AutoJump border face must be horizontal");
        };
        double normalCenter = (positive ? 1.0 : -1.0) * (axis == Direction.Axis.X ? x : z);
        double tangentCenter = tangent == Direction.Axis.X ? x : z;
        int row = Mth.floor(tangentCenter);
        double probeMin = positive ? query.min(axis) : -query.max(axis);
        double end = (positive ? query.max(axis) : -query.min(axis)) - width;
        double bodyMin = positive ? box.min(axis) : -box.max(axis);
        double bodyMax = positive ? box.max(axis) : -box.min(axis);
        var speed = player.getEffect(MobEffects.SPEED);
        double tangentTolerance = early && face != Direction.EAST ? 1.0E-7 : 0.0;
        if (x > west + 1.0 && x < east - 1.0 && z > north + 1.0 && z < south - 1.0
            && width == 0.6F && player.getBbHeight() == 1.8F
            && bodyMin >= edge - 2.0 && bodyMax < edge && box.min(tangent) >= row && box.max(tangent) <= row + 1.0
            && y == Math.rint(y) && query.minY == y && query.maxY == y + player.getBbHeight()
            && probeMin == normalCenter - width && end > edge && end < edge + 1.0
            && Math.abs(query.min(tangent) - (tangentCenter - width)) <= tangentTolerance
            && Math.abs(query.max(tangent) - (tangentCenter + width)) <= tangentTolerance
            && speed != null && speed.getAmplifier() == 1 && !player.hasEffect(MobEffects.JUMP_BOOST)
            && clientLevel.getEntities(player, query.inflate(0.25), entity -> true).isEmpty()) {
            return new BorderProbe(face, edge, Mth.floor(y) - 1, row);
        }
        return null;
    }

    /**
     * 1.10 through 1.11.2 Feather Gen2 build.2 World.getCollisions inserts
     * Stone at the first exterior cell of the approached face. Its lower
     * side cap changes at 1.11.1 but only touches this query's feet plane.
     * The LocalClientPlayerEntity.autoJump side rays hit the next cell up.
     */
    private static boolean admitsEarlyBorder(Player player, BorderProbe probe, @Nullable ProbeSegments segments,
                                             int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
        if (segments == null || segments.groundedTravelStart() == null) return false;
        Vec3 start = segments.groundedTravelStart();
        double edge = probe.edge();
        double feet = probe.floorY() + 1.0;
        int row = probe.row();
        double halfWidth = player.getBbWidth() * 0.5F;
        Direction.Axis axis = probe.face().getAxis();
        Direction.Axis tangent = probe.tangent();
        boolean alongX = axis == Direction.Axis.X;
        boolean positive = probe.outwardStep() > 0;
        int rawMin = alongX ? minX : minZ;
        int rawMax = alongX ? maxX : maxZ;
        // A mirrored unit cell at raw coordinate r begins at -r - 1.
        int normalMin = positive ? rawMin : -rawMax - 1;
        int normalMax = positive ? rawMax : -rawMin - 1;
        int tangentMin = alongX ? minZ : minX;
        int tangentMax = alongX ? maxZ : maxX;
        AABB box = player.getBoundingBox();
        double bodyMin = positive ? box.min(axis) : -box.max(axis);
        double bodyMax = positive ? box.max(axis) : -box.min(axis);
        double normalStart = probe.outward(start);
        double tangentStart = start.get(tangent);
        double tolerance = probe.face() == Direction.EAST ? 0.0 : 1.0E-7;
        if (normalStart - halfWidth < edge - 2.0 || normalStart + halfWidth >= edge
            || normalStart >= probe.outward(player.position()) || start.y != feet
            || Math.abs(tangentStart - player.position().get(tangent)) > tolerance
            || (probe.face() != Direction.EAST
                && (tangentStart - halfWidth < row || tangentStart + halfWidth > row + 1.0))
            || normalMin != edge - 3.0 || normalMax != edge + 1.0 || minY != feet - 2.0 || maxY != feet + 2.0
            || tangentMin != row - 2 || tangentMax != row + 2
            || bodyMin >= edge - 1.0 || bodyMax <= edge - 1.0) {
            return false;
        }
        return crossesExteriorCell(segments.leftStart(), segments.leftEnd(), probe, feet, tolerance)
            && crossesExteriorCell(segments.rightStart(), segments.rightEnd(), probe, feet, tolerance);
    }

    private static boolean crossesExteriorCell(Vec3 from, Vec3 to, BorderProbe probe, double feet, double tolerance) {
        double edge = probe.edge();
        double outer = probe.outward(to);
        double fromTangent = from.get(probe.tangent());
        double toTangent = to.get(probe.tangent());
        int row = probe.row();
        return probe.outward(from) < edge && outer > edge && outer < edge + 1.0
            && from.y == to.y && from.y > feet && from.y < feet + 1.0
            && Math.abs(toTangent - fromTangent) <= tolerance
            && fromTangent > row && fromTangent < row + 1.0 && toTangent > row && toTangent < row + 1.0;
    }

    private static List<VoxelShape> brewingStandParts(Player player, CollisionGetter level, AABB query,
        CollisionContext context, BlockPos stand, ParkourVersion selected, boolean earlyInterior) {
        float width = player.getBbWidth();
        float height = player.getBbHeight();
        BlockPos playerSupport = BlockPos.containing(player.getX(), player.getY() - 0.5, player.getZ());
        if (!(player instanceof LocalPlayer)
            || !(level instanceof Level clientLevel) || clientLevel != player.level() || !clientLevel.isClientSide()
            || player.getPose() != Pose.STANDING || !player.onGround() || player.getAbilities().flying
            || player.isPassenger() || player.isFallFlying() || player.isInWater() || player.isInLava()
            || player.hasEffect(MobEffects.JUMP_BOOST) || width != 0.6F || height != 1.8F
            || query.minY != player.getY() || query.minY != stand.getY() - 0.5
            || query.maxY != player.getY() + height || query.minY < 0.0 || query.maxY > 256.0
            || !(query.minX <= player.getX() - width && query.maxX >= player.getX() + width
                && query.minZ <= player.getZ() - width && query.maxZ >= player.getZ() + width)
            || !Double.isFinite(query.getSize())
            || playerSupport.getY() != stand.getY() - 1
            || !query.intersects(new AABB(stand))
            || (earlyInterior && (!query.intersects(BREWING_STAND_STEM.move(stand))
                || !query.intersects(BREWING_STAND_PLATE.move(stand))))
            || !isDryBottomSlab(loadedState(level, stand.below()))
            || !isDryBottomSlab(loadedState(level, playerSupport))
            || !loadedState(level, stand.above()).is(Blocks.AIR)
            || !clientLevel.getEntities(player, query.inflate(0.25), entity -> true).isEmpty()) {
            return List.of();
        }

        BlockState state = loadedState(level, stand);
        if (!state.is(Blocks.BREWING_STAND) || !state.getFluidState().isEmpty()) {
            return List.of();
        }
        List<VoxelShape> parts = MovementRuntime.find(BlockCollisionShapeHook.class, state.getBlock(), player)
            .filter(hook -> hook.supports(selected))
            .flatMap(hook -> hook.collisionShapes(state, level, stand, context))
            .orElseGet(List::of);
        if (parts.size() != 2 || !parts.get(0).toAabbs().equals(List.of(BREWING_STAND_STEM))
            || !parts.get(1).toAabbs().equals(List.of(BREWING_STAND_PLATE))) {
            return List.of();
        }
        VoxelShape occupied = Shapes.or(parts.get(0), parts.get(1));
        VoxelShape playerShape = context.getCollisionShape(state, level, stand);
        return Shapes.joinIsNotEmpty(occupied, playerShape, BooleanOp.NOT_SAME) ? List.of() : parts;
    }

    private static boolean supported(BlockState state, CollisionGetter level, BlockPos pos) {
        if (state.isAir()) {
            return true;
        }
        if (state.is(Blocks.SMOOTH_STONE_SLAB)) {
            return isDryBottomSlab(state);
        }
        if (!(state.is(Blocks.OAK_FENCE) || state.is(Blocks.SPRUCE_FENCE) || state.is(Blocks.BIRCH_FENCE)
            || state.is(Blocks.JUNGLE_FENCE) || state.is(Blocks.ACACIA_FENCE) || state.is(Blocks.DARK_OAK_FENCE)
            || state.is(Blocks.NETHER_BRICK_FENCE))) {
            return false;
        }
        // Old fences resolve virtual connections from neighbors; stored modern flags alone
        // are insufficient. Air neighbors close that dependency without a new connection rule.
        return !state.getValue(FenceBlock.NORTH) && !state.getValue(FenceBlock.EAST)
            && !state.getValue(FenceBlock.SOUTH) && !state.getValue(FenceBlock.WEST)
            && !state.getValue(FenceBlock.WATERLOGGED)
            && loadedAir(level, pos.north()) && loadedAir(level, pos.east())
            && loadedAir(level, pos.south()) && loadedAir(level, pos.west());
    }

    private static boolean isDryBottomSlab(BlockState state) {
        return state.is(Blocks.SMOOTH_STONE_SLAB) && state.getValue(SlabBlock.TYPE) == SlabType.BOTTOM
            && !state.getValue(SlabBlock.WATERLOGGED);
    }

    private static BlockState loadedState(CollisionGetter level, BlockPos pos) {
        var chunk = level.getChunkForCollisions(SectionPos.blockToSectionCoord(pos.getX()),
            SectionPos.blockToSectionCoord(pos.getZ()));
        return chunk == null ? Blocks.VOID_AIR.defaultBlockState() : chunk.getBlockState(pos);
    }

    private static boolean loadedAir(CollisionGetter level, BlockPos pos) {
        var chunk = level.getChunkForCollisions(SectionPos.blockToSectionCoord(pos.getX()),
            SectionPos.blockToSectionCoord(pos.getZ()));
        return chunk != null && chunk.getBlockState(pos).isAir();
    }
}
