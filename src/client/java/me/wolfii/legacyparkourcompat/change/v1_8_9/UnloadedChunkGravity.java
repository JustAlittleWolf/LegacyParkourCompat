package me.wolfii.legacyparkourcompat.change.v1_8_9;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.UnloadedChunkGravityHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/**
 * Restores 1.8's post-move chunk sample and unloaded-gravity height boundary.
 *
 * <p>1.8.9 (Ornithe Feather Gen2 build.2),
 * {@code net.minecraft.entity.living.LivingEntity#moveRelative(float,float)},
 * samples {@code (int)x, 0, (int)z}. The stable 1.9 source's same method
 * uses a mutable BlockPos whose double setter floors coordinates
 * ({@code net.minecraft.util.math.BlockPos.PooledMutable#set(double,double,double)}).
 * The stable 1.8.0–1.8.8 sources retain the explicit casts. The 1.8 client
 * fallback uses a strict Y > 0 comparison, so the minimum boundary is zero.
 */
@MovementChange(emulates = ParkourVersion.V1_8_9)
public final class UnloadedChunkGravity implements UnloadedChunkGravityHook {
    @Override
    public boolean chunkIsLoaded(
        LivingEntity entity,
        Level level,
        BlockPos vanillaPosition,
        boolean movementChunkLoaded
    ) {
        if (!(entity instanceof Player) || !level.isClientSide()) {
            return movementChunkLoaded;
        }

        // Keep the existing first predicate as a gate, then use the old cast coordinates.
        BlockPos historicalPosition = new BlockPos((int) entity.getX(), 0, (int) entity.getZ());
        return movementChunkLoaded && level.isLoaded(historicalPosition);
    }

    @Override
    public int gravityFallbackMinimumY(LivingEntity entity, int nativeMinimumY) {
        if (!(entity instanceof Player) || !entity.level().isClientSide()) {
            return nativeMinimumY;
        }
        return 0;
    }
}
