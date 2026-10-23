package me.wolfii.legacyparkourcompat.mechanic.hook.travel;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

@MechanicType("player.travel.unloaded_chunk_gravity")
public interface UnloadedChunkGravityHook extends VersionedMechanic {
    boolean chunkIsLoaded(LivingEntity entity, Level level, BlockPos vanillaPosition, boolean movementChunkLoaded);

    int gravityFallbackMinimumY(LivingEntity entity, int nativeMinimumY);
}
