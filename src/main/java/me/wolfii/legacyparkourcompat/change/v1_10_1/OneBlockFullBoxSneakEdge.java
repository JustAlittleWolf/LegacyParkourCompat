package me.wolfii.legacyparkourcompat.change.v1_10_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.v1_18.FullBoxSneakEdge;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.SneakEdgeBehavior;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

/** Through 1.10.2, the full player box probes a fixed block below the feet. */
@MovementChange(emulates = ParkourVersion.V1_10_1)
public final class OneBlockFullBoxSneakEdge implements SneakEdgeBehavior {
    @Override
    public Vec3 maybeBackOffFromEdge(Player player, Vec3 delta, MoverType moverType, VanillaFn<Vec3> vanilla) {
        if (!player.onGround() || !player.isShiftKeyDown()) {
            return delta;
        }
        return FullBoxSneakEdge.backOff(player, delta, 1.0);
    }

    @Override
    public boolean isAboveGround(Player player, float maxDownStep, boolean vanilla) {
        return player.onGround();
    }
}
