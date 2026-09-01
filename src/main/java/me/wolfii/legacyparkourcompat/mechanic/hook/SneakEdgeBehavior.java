package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

/**
 * Historical sneak-edge algorithm ({@code Player#maybeBackOffFromEdge}).
 * The prevented drop distance is {@link SneakEdgeDistanceBehavior}.
 *
 * <p>Until 1.16.2, sneak-edge only applied while {@code onGround}. 1.16.2 also
 * holds the player when they are up to {@code maxUpStep} above the ground
 * (MC-2404). {@link #isAboveGround} restores the older check.
 */
@MechanicType("player.sneak.edge")
public interface SneakEdgeBehavior extends VersionedMechanic {
    Vec3 maybeBackOffFromEdge(Player player, Vec3 delta, MoverType moverType, VanillaFn<Vec3> vanilla);

    default boolean isAboveGround(Player player, float maxDownStep, boolean vanilla) {
        return vanilla;
    }
}
