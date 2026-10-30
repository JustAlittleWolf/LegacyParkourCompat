package me.wolfii.legacyparkourcompat.mechanic.fluid;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.minecraft.world.entity.vehicle.boat.Boat;

import java.util.Optional;

/** Shared direct point query for releases with the same player eye-water sampling body. */
public final class PlayerEyeWaterSampler {
    private PlayerEyeWaterSampler() {
    }

    /**
     * 1.14.2 Yarn/Feather {@code PlayerEntity#tick/updateWaterSubmersion} and 1.15.2 Mojmap
     * {@code Player#tick/updateIsUnderwater} both query water at the current eye point before the
     * inherited entity tick, reject boats, require a loaded point, and compare contextual fluid height
     * using a strict double comparison after float epsilon addition. The corresponding 1.14.2
     * {@code Entity#isSubmergedIn} and 1.15.2 {@code Entity#isUnderLiquid} are the source operations.
     * Unsupported modern boat subtypes keep the native result because they had no historical counterpart.
     */
    public static Optional<Boolean> query(Player player) {
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
