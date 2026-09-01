package me.wolfii.legacyparkourcompat.change.v1_16_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.SneakEdgeBehavior;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

/**
 * Until 1.16.2 (MC-2404), sneak-edge only applied while {@code onGround}.
 * 1.16.2 also holds the player when they are up to {@code maxUpStep} above
 * the ground (stepping down a slab/stair).
 */
@MovementChange(emulates = ParkourVersion.V1_16)
public final class GroundOnlySneakEdge implements SneakEdgeBehavior {
    @Override
    public Vec3 maybeBackOffFromEdge(Player player, Vec3 delta, MoverType moverType, VanillaFn<Vec3> vanilla) {
        return vanilla.get();
    }

    @Override
    public boolean isAboveGround(Player player, float maxDownStep, boolean vanilla) {
        return player.onGround();
    }
}
