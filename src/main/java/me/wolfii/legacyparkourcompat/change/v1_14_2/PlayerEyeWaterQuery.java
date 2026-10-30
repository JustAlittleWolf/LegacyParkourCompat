package me.wolfii.legacyparkourcompat.change.v1_14_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.fluid.PlayerEyeWaterMath;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.PlayerEyeWaterQueryHook;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.minecraft.world.entity.vehicle.boat.Boat;

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
        var vehicle = player.getVehicle();
        if (vehicle instanceof AbstractBoat && !(vehicle instanceof Boat)) {
            return Optional.empty();
        }
        if (vehicle instanceof Boat) {
            return Optional.of(false);
        }

        double eyeY = player.getY() + player.getEyeHeight();
        BlockPos eyePos = BlockPos.containing(player.getX(), eyeY, player.getZ());
        // Preserve virtual client-world dispatch, rather than replacing it with a cache residency test.
        if (!player.level().hasChunkAt(eyePos)) {
            return Optional.of(false);
        }
        var fluid = player.level().getFluidState(eyePos);
        return Optional.of(fluid.is(FluidTags.WATER)
            && PlayerEyeWaterMath.isSubmerged(eyeY, eyePos.getY(), fluid.getHeight(player.level(), eyePos)));
    }
}
