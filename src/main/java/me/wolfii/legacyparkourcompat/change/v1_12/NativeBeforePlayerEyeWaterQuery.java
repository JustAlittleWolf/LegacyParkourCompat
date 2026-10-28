package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.PlayerEyeWaterQueryHook;
import net.minecraft.world.entity.player.Player;

import java.util.Optional;

/** Leaves the cache producer native before the 1.13 point-query boundary. */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class NativeBeforePlayerEyeWaterQuery implements PlayerEyeWaterQueryHook {
    public NativeBeforePlayerEyeWaterQuery() {
    }

    @Override
    public Optional<Boolean> queryEyeWater(Player player) {
        return Optional.empty();
    }
}
