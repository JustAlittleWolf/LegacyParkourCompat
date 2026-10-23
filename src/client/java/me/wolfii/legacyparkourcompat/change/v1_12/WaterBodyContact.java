package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.water.WaterBodyContactHook;
import net.minecraft.world.phys.AABB;
import java.util.Optional;

/**
 * 1.12/1.12.1/1.12.2 Feather: Entity.checkWaterCollisions,
 * World.applyLiquidDrag and LiquidBlock.getHeightLoss. Replaced in 1.13 Feather:
 * Entity.checkWaterState/m_69693160; 26.2 unobfuscated: EntityFluidInteraction.update.
 * Preserve float widening before the vertical shrink and integer-ceiling contact.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class WaterBodyContact implements WaterBodyContactHook {
    public Optional<AABB> probe(AABB body) {
        return Optional.of(new AABB(body.minX, body.minY - -0.4F, body.minZ,
            body.maxX, body.maxY + -0.4F, body.maxZ).deflate(0.001));
    }

    public boolean includes(int exclusiveMaxY, int cellY, int liquidLevel) {
        int level = liquidLevel >= 8 ? 0 : liquidLevel;
        double surface = cellY + 1 - (level + 1) / 9.0F;
        return exclusiveMaxY >= surface;
    }
}
