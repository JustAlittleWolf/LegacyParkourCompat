package me.wolfii.legacyparkourcompat.change.v1_16_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.FluidScanLoadedChunksHook;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

import java.util.Optional;

/**
 * Mojmap 1.16 and 1.16.5 Entity.updateFluidHeightAndDoFluidPushing check
 * LevelReader.hasChunksAt against floor(min)/ceil(max) of the box deflated
 * by 0.001. Mojmap 1.17 adds touchingUnloadedChunk's full-box one-block
 * margin. 26.2 EntityFluidInteraction.update / hasFluidAndLoaded likewise
 * checks an expanded horizontal range. Replace only scan admission with
 * the old inclusive XZ chunk range; sampling/accumulation remain native.
 * The checked 1.16.5 LevelReader Y gate is [0,256); restrict the delta to
 * boxes within that old build range. Passengers, scaling, earlier profiles
 * and modern-height-only regions retain native admission.
 */
@MovementChange(emulates = ParkourVersion.V1_16_2)
public final class FluidScanLoadedChunks implements FluidScanLoadedChunksHook {
    @Override
    public Optional<Boolean> scanAllowed(Player player, ParkourVersion selected) {
        if (selected.olderThan(ParkourVersion.V1_16) || player.isPassenger()
            || player.getAttributeValue(Attributes.SCALE) != 1.0) {
            return Optional.empty();
        }
        AABB box = player.getBoundingBox().deflate(0.001);
        int y0 = Mth.floor(box.minY);
        int y1 = Mth.ceil(box.maxY);
        if (y0 < 0 || y1 > 256) {
            return Optional.empty();
        }
        return Optional.of(player.level().hasChunksAt(
            Mth.floor(box.minX), Mth.floor(box.minZ), Mth.ceil(box.maxX), Mth.ceil(box.maxZ)));
    }
}
