package me.wolfii.legacyparkourcompat.mixin.accessor;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.piston.PistonMovingBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/** Reads native collision-query state without interpolation or mutation. */
@Mixin(PistonMovingBlockEntity.class)
public interface PistonMovingBlockEntityAccessor {
    @Accessor("progress")
    float legacyparkourcompat$progress();

    @Accessor("progressO")
    float legacyparkourcompat$previousProgress();

    @Accessor("NOCLIP")
    static ThreadLocal<Direction> legacyparkourcompat$noClip() {
        throw new AssertionError("PistonMovingBlockEntity accessor was not applied");
    }
}
