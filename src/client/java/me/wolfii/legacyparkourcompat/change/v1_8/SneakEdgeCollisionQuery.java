package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.SneakEdgeCollisionSupport;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.SneakEdgeCollisionQueryHook;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

/**
 * Preserve the existing block, border and native entity query policy before
 * shulkers existed. The 1.10.2 Ornithe Feather Gen2 build 2
 * World.getCollisions / ShulkerEntity.getCollisionShape correction, compared
 * with 26.2 unobfuscated EntityGetter.getEntityCollisions, concerns the shell
 * response only. A shulker is a modern-only feature for the 1.8 profile.
 * This nearer resolver boundary prevents that later correction from changing
 * the established 1.8 query policy; it does not claim historical query parity.
 */
@MovementChange(emulates = ParkourVersion.V1_8)
public final class SneakEdgeCollisionQuery implements SneakEdgeCollisionQueryHook {
    @Override
    public boolean noCollision(Player player, AABB candidate) {
        return SneakEdgeCollisionSupport.noCollisionWithNativeEntities(player, candidate);
    }
}
