package me.wolfii.legacyparkourcompat.mechanic.hook.collision;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

/**
 * Adds the historical empty-height collision plane from one-layer snow to a
 * player's ordinary descending SELF movement Y clip.
 *
 * <p>{@code nativeY} is the paired result of the ordinary full collision scan
 * with {@code startBox} and {@code requestedY}. Keep that result outside the
 * supported loaded, dry, interior witness.
 */
@MechanicType("player.collision.snow_layer_one_y")
public interface SnowLayerOneCollisionPlaneHook extends VersionedMechanic {
    double clipDescendingY(Player player, AABB startBox, double requestedY, double nativeY);
}
