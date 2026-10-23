package me.wolfii.legacyparkourcompat.change.v1_16_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.MovementPositionReconstructionHook;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

/**
 * Ornithe Feather 1.13.2 / 1.14 Entity.move / setPositionFromShape and Mojmap 1.16 /
 * 1.16.2, 1.16.3, 1.16.4 and 1.16.5 Entity.move / setLocationFromBoundingbox translate
 * each AABB endpoint before computing (minX + maxX) / 2.0, minY and
 * (minZ + maxZ) / 2.0, then setPosRaw without rebuilding the translated box.
 * Mojmap 1.17 / 1.17.1 Entity.move instead directly add displacement to position and
 * rebuilds the box through setPos, retained by 26.2. Preserve the old operation
 * order in both collision and noPhysics movement. Resolve this pure result for
 * both the 26.2 movement record endpoint and the actual write. The caller independently
 * resolves whether a physical movement is large enough to write at all.
 * A step resolver can supply its already translated box: 1.13.2 preserves the
 * rise and settle translations separately from its logical downward Y result.
 * Modern entity scaling retains native fallback.
 */
@MovementChange(emulates = ParkourVersion.V1_16_2)
public final class MovementPositionReconstruction implements MovementPositionReconstructionHook {
    @Override
    public Optional<Position> resolve(Player player, ParkourVersion selected, Vec3 movement, @Nullable AABB resolvedBox) {
        if (player.getAttributeValue(Attributes.SCALE) != 1.0) {
            return Optional.empty();
        }
        AABB box = resolvedBox != null ? resolvedBox : player.getBoundingBox().move(movement);
        Vec3 position = new Vec3((box.minX + box.maxX) / 2.0, box.minY, (box.minZ + box.maxZ) / 2.0);
        return Optional.of(new Position(position, box));
    }
}
