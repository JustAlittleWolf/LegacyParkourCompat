package me.wolfii.legacyparkourcompat.change.v1_17_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.FeetBlockStateHook;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

/**
 * In 1.17 and 1.17.1 Mojmap, Entity#getFeetBlockState reads the world on every call.
 * In 1.18 Mojmap, Entity#getFeetBlockState caches that read until Entity#baseTick
 * or a change of block position in Entity#setPosRaw. The live read lets the
 * powder-snow movement callback see a newly updated block at the same feet position.
 */
@MovementChange(emulates = ParkourVersion.V1_17_1)
public final class FeetBlockState implements FeetBlockStateHook {
    @Override
    public Optional<BlockState> getFeetBlockState(Entity entity) {
        return Optional.of(entity.level().getBlockState(entity.blockPosition()));
    }
}
