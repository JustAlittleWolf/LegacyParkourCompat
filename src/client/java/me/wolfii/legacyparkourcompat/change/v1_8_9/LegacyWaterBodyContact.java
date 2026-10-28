package me.wolfii.legacyparkourcompat.change.v1_8_9;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.water.WaterBodyContactHook;
import net.minecraft.world.phys.AABB;

import java.util.Optional;

/**
 * 1.8.9 Ornithe Feather / Feather Gen2 build 2: Entity.checkWaterCollisions
 * (Entity.java:746-747), World.applyLiquidDrag (World.java:1486-1510),
 * Box.grown/contract (Box.java:59-63, 176-184) and LiquidBlock.getHeightLoss
 * (LiquidBlock.java:41-46). The 1.9 Feather Gen2 build 2
 * Entity.checkWaterCollisions (Entity.java:845-849) retains the query;
 * World.applyLiquidDrag (World.java:1554-1577) retains the Material.WATER and
 * surface checks while changing the upper bound. Existing 1.12 WaterBodyContact
 * uses the same query shape and surface arithmetic.
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
        // 1.8.9 exclusive maxY supplied by the separate contact-bound hook.
        int level = liquidLevel >= 8 ? 0 : liquidLevel;
        double surface = cellY + 1 - (level + 1) / 9.0F;
        return exclusiveMaxY >= surface;
    }
}
