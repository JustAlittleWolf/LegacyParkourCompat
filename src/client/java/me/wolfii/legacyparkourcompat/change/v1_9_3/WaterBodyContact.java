package me.wolfii.legacyparkourcompat.change.v1_9_3;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.OrdinaryPlayerContext;
import me.wolfii.legacyparkourcompat.mechanic.hook.water.WaterBodyContactHook;
import me.wolfii.legacyparkourcompat.mechanic.water.LegacyWaterProbe;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.phys.AABB;

import java.util.Optional;

/**
 * Ornithe Feather 1.9 through 1.9.4: Entity.checkWaterCollisions passes the
 * vertically shrunk and contracted box to World.applyLiquidDrag. Stable 1.9
 * switches the exclusive maximum from 1.8.9's floor(max + 1.0) to ceil(max).
 * 26.2 unobfuscated EntityFluidInteraction.update uses the unshrunk body box.
 * Restore the old contact result for the supported loaded body scan.
 */
@MovementChange(emulates = ParkourVersion.V1_9_3)
public final class WaterBodyContact implements WaterBodyContactHook {
    @Override
    public Optional<AABB> probe(AABB body) {
        return Optional.of(LegacyWaterProbe.bounds(body));
    }

    @Override
    public boolean supports(Player player, AABB query) {
        if (!(player instanceof LocalPlayer) || !OrdinaryPlayerContext.matches(player)
            || player.getAbilities().flying || Mth.floor(query.minY) < 0 || Mth.ceil(query.maxY) > 256) {
            return false;
        }
        int minChunkX = SectionPos.blockToSectionCoord(Mth.floor(query.minX));
        int maxChunkX = SectionPos.blockToSectionCoord(Mth.ceil(query.maxX) - 1);
        int minChunkZ = SectionPos.blockToSectionCoord(Mth.floor(query.minZ));
        int maxChunkZ = SectionPos.blockToSectionCoord(Mth.ceil(query.maxZ) - 1);
        for (int x = minChunkX; x <= maxChunkX; x++) {
            for (int z = minChunkZ; z <= maxChunkZ; z++) {
                if (player.level().getChunk(x, z, ChunkStatus.FULL, false) == null) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean affectsCurrentAdmission() {
        return false;
    }

    @Override
    public boolean includes(int exclusiveMaxY, int cellY, int liquidLevel) {
        return LegacyWaterProbe.includes(exclusiveMaxY, cellY, liquidLevel);
    }
}
