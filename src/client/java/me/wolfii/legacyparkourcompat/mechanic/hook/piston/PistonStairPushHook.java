package me.wolfii.legacyparkourcompat.mechanic.hook.piston;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.piston.PistonMovingBlockEntity;

import java.util.OptionalDouble;

/** Resolves the final distance requested by the moving-block piston response. */
@MechanicType("piston.stair-push")
public interface PistonStairPushHook extends VersionedMechanic {
    /** Empty retains the native request; a present value replaces only its distance. */
    OptionalDouble requestedDistance(PistonMovingBlockEntity mover, BlockPos pos, float newProgress,
                                     Entity recipient, Direction movementDirection);
}
