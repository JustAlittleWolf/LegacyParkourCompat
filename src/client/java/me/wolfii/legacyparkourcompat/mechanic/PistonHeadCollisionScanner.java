package me.wolfii.legacyparkourcompat.mechanic;

import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockCollisionShapeHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.BlockCollisionScan;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.function.BiFunction;

/**
 * Bounded reconciliation of the source-confirmed piston provider, not a replacement world scan.
 * Admission requires loaded, air-or-piston-only surroundings across both collectors' bounds.
 * Thus every block contribution is a piston part in historical order; no other block contribution
 * can be reordered across a piston. Mixed surroundings retain the native collector.
 */
public final class PistonHeadCollisionScanner<T> {
    private final BlockCollisionScan scan;
    private final CollisionContext context;
    private final AABB box;
    private final CollisionGetter level;
    private final BlockPos.MutableBlockPos pos;
    private final BiFunction<BlockPos.MutableBlockPos, VoxelShape, T> resultProvider;
    private final BiFunction<Integer, Integer, BlockGetter> chunks;
    private final BlockCollisionShapeHook shapeHook;
    private List<VoxelShape> parts = List.of();
    private int partIndex;
    private int x;
    private int y;
    private int z;
    private T result;

    /**
     * Enforces the empty-surroundings witness rather than assuming that shape order commutes.
     * 26.2/unobfuscated BlockCollisions constructor supplies the inclusive native bounds below.
     * Shapes.collide normalizes tiny distances at every shape, so even an unrelated floor must
     * force fallback. Reject non-air blocks conservatively, without querying their shape providers.
     */
    public static boolean hasOnlyPistonHeads(BlockCollisionScan scan, AABB box,
        BiFunction<Integer, Integer, BlockGetter> chunks) {
        int minX = Math.min(scan.minX(), Mth.floor(box.minX - 1.0E-7) - 1);
        int maxX = Math.max(scan.maxXExclusive() - 1, Mth.floor(box.maxX + 1.0E-7) + 1);
        int minY = Math.min(scan.minY(), Mth.floor(box.minY - 1.0E-7) - 1);
        int maxY = Math.max(scan.maxYExclusive() - 1, Mth.floor(box.maxY + 1.0E-7) + 1);
        int minZ = Math.min(scan.minZ(), Mth.floor(box.minZ - 1.0E-7) - 1);
        int maxZ = Math.max(scan.maxZExclusive() - 1, Mth.floor(box.maxZ + 1.0E-7) + 1);
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {
                BlockGetter chunk = chunks.apply(x, z);
                if (chunk == null) {
                    return false;
                }
                for (int y = minY; y <= maxY; y++) {
                    var state = chunk.getBlockState(pos.set(x, y, z));
                    if (!state.isAir() && !state.is(Blocks.PISTON_HEAD)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public PistonHeadCollisionScanner(BlockCollisionScan scan, CollisionContext context, AABB box,
        CollisionGetter level, BiFunction<BlockPos.MutableBlockPos, VoxelShape, T> resultProvider,
        BiFunction<Integer, Integer, BlockGetter> chunks, Entity player) {
        this.scan = scan;
        this.context = context;
        this.box = box;
        this.level = level;
        this.pos = new BlockPos.MutableBlockPos();
        this.resultProvider = resultProvider;
        this.chunks = chunks;
        this.shapeHook = MovementRuntime.find(BlockCollisionShapeHook.class, Blocks.PISTON_HEAD, player)
            .orElseThrow(() -> new IllegalStateException("Historical piston scan requires its shape provider"));
        this.x = scan.minX();
        this.y = scan.minY();
        this.z = scan.minZ();
    }

    public boolean advance() {
        while (true) {
            while (this.partIndex < this.parts.size()) {
                VoxelShape part = this.parts.get(this.partIndex++).move(this.pos);
                // Historical Block.addCollisions uses strict Box.intersects per head/arm AABB.
                if (this.box.intersects(part.bounds())) {
                    this.result = this.resultProvider.apply(this.pos, part);
                    return true;
                }
            }
            if (this.y >= this.scan.maxYExclusive()) {
                this.y = this.scan.minY();
                if (++this.z >= this.scan.maxZExclusive()) {
                    this.z = this.scan.minZ();
                    this.x++;
                }
            }
            if (this.x >= this.scan.maxXExclusive()) {
                return false;
            }
            int cellY = this.y++;
            this.parts = List.of();
            this.partIndex = 0;
            if (!this.scan.includes(this.x, cellY, this.z)) {
                continue;
            }
            BlockGetter chunk = this.chunks.apply(this.x, this.z);
            if (chunk == null) {
                continue;
            }
            this.pos.set(this.x, cellY, this.z);
            var state = chunk.getBlockState(this.pos);
            if (state.is(Blocks.PISTON_HEAD)) {
                this.parts = this.shapeHook.collisionShapes(state, this.level, this.pos, this.context)
                    .orElseThrow(() -> new IllegalStateException("Historical piston provider omitted its parts"));
            }
        }
    }

    public T result() {
        return this.result;
    }
}
