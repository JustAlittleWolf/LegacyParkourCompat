package me.wolfii.legacyparkourcompat.change.v1_10_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.SneakEdgeBorderCollision;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.SneakEdgeCollisionQueryHook;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

/**
 * 1.10.2 Ornithe Feather Gen2 build 2 World.getCollisions(Entity, Box) queries
 * non-spectators in candidate.expand(0.25), skips the same vehicle and adds
 * ShulkerEntity.getCollisionShape only when it strictly intersects candidate.
 * ShulkerEntity.getCollisionShape returns its bounding box only while alive;
 * the player's inherited Entity.getCollisionAgainstShape returns null.
 * 1.11 Ornithe Feather Gen2 build 2 Entity.move(MoverType, double, double,
 * double) excludes SHULKER from the grounded sneaking-player edge guard,
 * unlike the untyped 1.10.2 Entity.move(double, double, double).
 *
 * 26.2 unobfuscated EntityGetter.getEntityCollisions instead selects entities
 * against candidate.inflate(1.0E-7) and adds their boxes without rechecking
 * strict overlap. A shell merely touching a probe face must not supply edge
 * support. Only the shulker entity policy changes here: other entity policies,
 * block queries and the existing border approximation remain unchanged.
 * This does not establish historical parity for those retained policies.
 */
@MovementChange(emulates = ParkourVersion.V1_10_1)
public final class SneakEdgeCollisionQuery implements SneakEdgeCollisionQueryHook {
    @Override
    public boolean noCollision(Player player, AABB candidate) {
        if (SneakEdgeBorderCollision.hasCollision(player)
            || !player.level().noBlockCollision(player, candidate)) {
            return false;
        }

        AABB nativeBounds = candidate.inflate(1.0E-7D);
        return player.level().getEntities(player, candidate.inflate(0.25D), EntitySelector.NO_SPECTATORS.and(other -> {
            if (other instanceof Shulker) {
                return other.isAlive()
                    && !player.isPassengerOfSameVehicle(other)
                    && other.getBoundingBox().intersects(candidate);
            }
            // Retain EntityGetter's size gate, selection bounds and collision
            // predicate for all entities outside the accepted shell response.
            return !(candidate.getSize() < 1.0E-7D)
                && other.getBoundingBox().intersects(nativeBounds)
                && player.canCollideWith(other);
        })).isEmpty();
    }
}
