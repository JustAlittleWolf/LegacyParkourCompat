package me.wolfii.legacyparkourcompat.mechanic;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;
import java.util.IdentityHashMap;
import java.util.function.Supplier;

/** Retains actual block-provider provenance for shapes collected during one collision resolution. */
public final class CollisionShapeSourceContext {
    private static final ThreadLocal<IdentityHashMap<VoxelShape, Source>> SOURCES = new ThreadLocal<>();

    private CollisionShapeSourceContext() {}

    public record Source(BlockState state, BlockPos pos) {}

    public static void record(VoxelShape shape, BlockState state, BlockPos pos) {
        var sources = SOURCES.get();
        if (sources != null) {
            sources.put(shape, new Source(state, pos.immutable()));
        }
    }

    public static @Nullable Source source(VoxelShape shape) {
        var sources = SOURCES.get();
        return sources == null ? null : sources.get(shape);
    }

    public static <T> T withFrame(Supplier<T> action) {
        var previous = SOURCES.get();
        SOURCES.set(new IdentityHashMap<>());
        try {
            return action.get();
        } finally {
            if (previous == null) {
                SOURCES.remove();
            } else {
                SOURCES.set(previous);
            }
        }
    }
}
