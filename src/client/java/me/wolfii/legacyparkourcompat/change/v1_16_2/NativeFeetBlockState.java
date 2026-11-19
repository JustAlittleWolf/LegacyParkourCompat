package me.wolfii.legacyparkourcompat.change.v1_16_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.FeetBlockStateHook;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

/** Bounds the live Entity feet lookup to its 1.17–1.17.1 powder-snow and climb callers. */
@MovementChange(emulates = ParkourVersion.V1_16_2)
public final class NativeFeetBlockState implements FeetBlockStateHook {
    @Override
    public Optional<BlockState> getFeetBlockState(Entity entity) {
        return Optional.empty();
    }
}
