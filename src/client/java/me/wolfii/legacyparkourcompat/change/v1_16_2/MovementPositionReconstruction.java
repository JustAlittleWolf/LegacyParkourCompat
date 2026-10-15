package me.wolfii.legacyparkourcompat.change.v1_16_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.MovementPositionReconstructionHook;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

/**
 * Ornithe Feather 1.14 Entity.move / setPositionFromShape and Mojmap 1.16 /
 * 1.16.5 Entity.move / setLocationFromBoundingbox translate
 * each AABB endpoint before computing (minX + maxX) / 2.0, minY and
 * (minZ + maxZ) / 2.0, then setPosRaw without rebuilding the translated box.
 * Mojmap 1.17 Entity.move instead directly adds displacement to position and
 * rebuilds the box through setPos, retained by 26.2. Preserve the old operation
 * order in both collision and noPhysics movement. The caller independently
 * resolves whether a physical movement is large enough to write at all.
 * Pre-1.14 scope and modern entity scaling retain native fallback.
 */
@MovementChange(emulates = ParkourVersion.V1_16_2)
public final class MovementPositionReconstruction implements MovementPositionReconstructionHook {
    @Override
    public Optional<Position> resolve(Player player, ParkourVersion selected, Vec3 movement) {
        if (selected.olderThan(ParkourVersion.V1_14) || player.getAttributeValue(Attributes.SCALE) != 1.0) {
            return Optional.empty();
        }
        AABB box = player.getBoundingBox().move(movement);
        Vec3 position = new Vec3((box.minX + box.maxX) / 2.0, box.minY, (box.minZ + box.maxZ) / 2.0);
        return Optional.of(new Position(position, box));
    }
}
