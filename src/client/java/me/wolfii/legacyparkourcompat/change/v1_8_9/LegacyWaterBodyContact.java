package me.wolfii.legacyparkourcompat.change.v1_8_9;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.water.WaterBodyContactHook;
import net.minecraft.world.phys.AABB;

import java.util.Optional;

/**
 * Raw 1.8.0/1.8.1 clients mapped with Feather Gen2 build 2 Tiny and the exact
 * Ornithe Feather sources from 1.8.2 through 1.8.9 establish the same coupled
 * contact/current operation. Entity.checkWaterCollisions submits the player
 * box grown by (0,-0.4F,0), then contracted by 0.001 on every axis. Raw
 * 1.8/1.8.1 AABB bytecode yields minY + 0.4010000059604645 and maxY -
 * 0.4010000059604645; this narrows the box rather than translating it. See
 * Entity.checkWaterCollisions()Z and World.applyLiquidDrag(AABB,Material,Entity)Z
 * in the raw clients; the matching 1.8.8 source lines are Entity.java:746-747
 * and World.java:1486-1510.
 * LiquidBlock.getHeightLoss(I)F treats levels >= 8 as zero height loss; the
 * matching source member is LiquidBlock.java:41-47. This bounded provider is
 * selected through 1.8.9; the V1_9 native contact change closes the range.
 */
@MovementChange(emulates = ParkourVersion.V1_8_9)
public final class LegacyWaterBodyContact implements WaterBodyContactHook {
    public LegacyWaterBodyContact() {
    }

    @Override
    public Optional<AABB> probe(AABB body) {
        return Optional.of(new AABB(body.minX, body.minY - -0.4F, body.minZ,
            body.maxX, body.maxY + -0.4F, body.maxZ).deflate(0.001));
    }

    @Override
    public boolean includes(int exclusiveMaxY, int cellY, int liquidLevel) {
        // Equivalent to p + 1 - getHeightLoss(level), then l >= d, with the
        // shared 1.8.0-1.8.9 exclusive maxY supplied by the separate hook.
        int level = liquidLevel >= 8 ? 0 : liquidLevel;
        double surface = cellY + 1 - (level + 1) / 9.0F;
        return exclusiveMaxY >= surface;
    }
}
