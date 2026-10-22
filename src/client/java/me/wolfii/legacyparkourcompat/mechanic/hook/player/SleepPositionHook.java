package me.wolfii.legacyparkourcompat.mechanic.hook.player;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

/** Historical position assigned after a player successfully enters a bed. */
@MechanicType("player.sleep_position")
public interface SleepPositionHook extends VersionedMechanic {
    Vec3 position(LivingEntity entity, BlockPos bedPosition);
}
