package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.UnloadedChunkGravityHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/**
 * Ornithe Feather 1.13.2
 * (1.13.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java#travel)
 * checks WorldView#isChunkLoaded and WorldChunk#isLoaded at (x, 0, z) before
 * normal gravity; when absent it assigns -0.1 only for Y > 0.0, otherwise 0.0.
 * Mojmap 1.14.4
 * (1.14.4/mojmap/net/minecraft/world/entity/LivingEntity.java#travel) sees
 * MultiPlayerLevel#hasChunk, which always returns true. In unobfuscated 26.2
 * (26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java#travelInAir),
 * Level#hasChunkAt gates gravity and its absent-chunk branch compares Y with
 * Level#getMinY. The client Level#isLoaded path reaches ChunkSource#hasChunk;
 * ClientChunkCache#getChunk(..., false) returns null when the chunk is absent.
 * The existing LivingEntity#travelInAir chunk-query redirect applies this predicate at the
 * old Y=0 lookup coordinate, while the fallback threshold replacement changes
 * only the minY operand in that same missing-chunk branch. Player tick and
 * registration gates stay unchanged: for an already registered player whose
 * inChunk flag remains true while entering a missing neighbor, 1.13.2
 * World#tickEntity still reaches travel without removing that registration.
 */
@MovementChange(emulates = ParkourVersion.V1_13_1)
public final class UnloadedChunkGravity implements UnloadedChunkGravityHook {
    @Override
    public boolean chunkIsLoaded(LivingEntity entity, Level level, BlockPos vanillaPosition, boolean movementChunkLoaded) {
        if (!level.isClientSide() || !(entity instanceof Player)) {
            return movementChunkLoaded;
        }
        BlockPos historicalChunkPos = BlockPos.containing(entity.getX(), 0.0, entity.getZ());
        return movementChunkLoaded && level.isLoaded(historicalChunkPos);
    }

    @Override
    public int gravityFallbackMinimumY(LivingEntity entity, int nativeMinimumY) {
        return entity instanceof Player && entity.level().isClientSide() ? 0 : nativeMinimumY;
    }
}
