package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;

/** Client-side chunk lookup used by the player air-travel fallback. */
@MechanicType("player.movement_chunk_lookup")
public interface MovementChunkLookupBehavior extends VersionedMechanic {
    boolean hasChunkAt(LivingEntity entity, Level level, BlockPos vanillaPosition, VanillaFn<Boolean> vanilla);
}
