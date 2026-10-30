package me.wolfii.legacyparkourcompat.change.v1_15_2.fluid;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.fluid.PlayerEyeWaterSampler;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.PlayerEyeWaterQueryHook;
import net.minecraft.world.entity.player.Player;

import java.util.Optional;

/**
 * Restores the direct current-eye water query retained through 1.15.2; from 1.16, the player reads
 * the eye-fluid cache instead. In 1.15.2 Mojmap, {@code Player#tick/updateIsUnderwater} calls
 * {@code Entity#isUnderLiquid} before {@code Entity#tick}; the shared sampler preserves that point
 * query and its native fallback for unsupported modern vehicle subtypes.
 */
@MovementChange(emulates = ParkourVersion.V1_15_2)
public final class PlayerEyeWaterQuery implements PlayerEyeWaterQueryHook {
    public PlayerEyeWaterQuery() {
    }

    @Override
    public Optional<Boolean> queryEyeWater(Player player) {
        return PlayerEyeWaterSampler.query(player);
    }
}
