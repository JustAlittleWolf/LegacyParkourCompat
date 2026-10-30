package me.wolfii.legacyparkourcompat.change.v1_14_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.fluid.PlayerEyeWaterSampler;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.PlayerEyeWaterQueryHook;
import net.minecraft.world.entity.player.Player;

import java.util.Optional;

/**
 * Yarn 1.14 through 1.14.2 PlayerEntity.tick/updateInWater call Entity.isInFluid
 * before inherited ticking; the helper is named isSubmergedIn in Yarn 1.14.3.
 * Feather 1.14.4 PlayerEntity.tick/updateWaterSubmersion
 * retain that order and call Entity.m_69330161 with the loaded-check argument true.
 * Unlike Feather 1.13.2 Entity.isSubmergedIn, these helpers use contextual fluid
 * height: BaseFluid.getHeight (1.14 Yarn) / FlowingFluid.getHeight (1.14.4 Feather)
 * returns full height when the same fluid is above. Preserve the double eye point,
 * strict comparison and float epsilon addition. In 26.2 unobfuscated,
 * Player.updateIsUnderwater instead reads the tracker before Entity.baseTick
 * refreshes it; this change replaces only that cached-water producer.
 */
@MovementChange(emulates = ParkourVersion.V1_14_2)
public final class PlayerEyeWaterQuery implements PlayerEyeWaterQueryHook {
    @Override
    public Optional<Boolean> queryEyeWater(Player player) {
        return PlayerEyeWaterSampler.query(player);
    }
}
