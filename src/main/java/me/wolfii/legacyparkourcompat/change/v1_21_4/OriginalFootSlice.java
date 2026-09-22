package me.wolfii.legacyparkourcompat.change.v1_21_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.SneakEdgeProbeBehavior;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

/** 1.20.5–1.21.4 use an uninset foot slice with a 1e-5F vertical pad. */
@MovementChange(emulates = ParkourVersion.V1_21_4)
public final class OriginalFootSlice implements SneakEdgeProbeBehavior {
    @Override
    public boolean appliesTo(Player player) {
        return MovementRuntime.profile(player).target().newerThanOrEqual(ParkourVersion.V1_20_5);
    }

    @Override
    public boolean canFallAtLeast(Player player, double deltaX, double deltaZ, double minHeight) {
        AABB box = player.getBoundingBox();
        float historicalHeight = (float) minHeight;
        return player.level().noCollision(player, new AABB(
            box.minX + deltaX,
            box.minY - historicalHeight - 1.0E-5F,
            box.minZ + deltaZ,
            box.maxX + deltaX,
            box.minY,
            box.maxZ + deltaZ
        ));
    }
}
