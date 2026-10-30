package me.wolfii.legacyparkourcompat.change.v1_8_9.piston;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.piston.PistonStairPushHook;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.piston.PistonMovingBlockEntity;

import java.util.OptionalDouble;

/** Keeps native piston stair responses through the independently unimplemented 1.8.9 rule. */
@MovementChange(emulates = ParkourVersion.V1_8_9)
public final class NativeCobblestoneStairPush implements PistonStairPushHook {
    @Override
    public String variant() {
        return "minecraft:cobblestone_stairs";
    }

    @Override
    public OptionalDouble requestedDistance(PistonMovingBlockEntity mover, BlockPos pos, float newProgress,
                                             Entity recipient, Direction movementDirection) {
        return OptionalDouble.empty();
    }
}
