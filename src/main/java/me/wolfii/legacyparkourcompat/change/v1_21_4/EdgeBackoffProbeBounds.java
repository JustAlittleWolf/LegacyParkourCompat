package me.wolfii.legacyparkourcompat.change.v1_21_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.SneakEdgeProbeBehavior;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

@MovementChange(emulates = ParkourVersion.V1_21_4)
public final class EdgeBackoffProbeBounds implements SneakEdgeProbeBehavior {
    @Override
    public boolean canFallAtLeast(Player player, double deltaX, double deltaZ, double minHeight) {
        AABB boundingBox = player.getBoundingBox();
        AABB probe = new AABB(
            boundingBox.minX + deltaX,
            boundingBox.minY - (float) minHeight - 1.0E-5F,
            boundingBox.minZ + deltaZ,
            boundingBox.maxX + deltaX,
            boundingBox.minY,
            boundingBox.maxZ + deltaZ
        );
        return player.level().noCollision(player, probe);
    }
}
