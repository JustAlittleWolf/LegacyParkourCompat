package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

/** Client-side chunk lookup used by the player air-travel fallback. */
@FunctionalInterface
@MechanicType("player.movement_chunk_lookup")
public interface MovementChunkLookupBehavior extends VersionedMechanic {
    boolean hasChunkAt(LivingEntity entity, Level level, BlockPos vanillaPosition, VanillaFn<Boolean> vanilla);
}
