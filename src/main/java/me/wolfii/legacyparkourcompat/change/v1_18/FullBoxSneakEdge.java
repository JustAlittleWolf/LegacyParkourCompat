package me.wolfii.legacyparkourcompat.change.v1_18;

import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.SneakEdgeBehavior;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

/** Pre-1.20.5 sneak-edge probes move the whole player box below the feet. */
public abstract class FullBoxSneakEdge implements SneakEdgeBehavior {
    protected abstract boolean allowsPositiveY();

    @Override
    public final Vec3 maybeBackOffFromEdge(Player player, Vec3 delta, MoverType moverType, VanillaFn<Vec3> vanilla) {
        if (player.getAbilities().flying
            || (!allowsPositiveY() && delta.y > 0.0)
            || (moverType != MoverType.SELF && moverType != MoverType.PLAYER)
            || !player.isShiftKeyDown()
            || !isAboveGround(player, player.maxUpStep(), false)) {
            return delta;
        }

        double x = delta.x;
        double z = delta.z;
        float step = player.maxUpStep();
        while (x != 0.0 && player.level().noCollision(player, player.getBoundingBox().move(x, -step, 0.0))) {
            x = reduce(x);
        }
        while (z != 0.0 && player.level().noCollision(player, player.getBoundingBox().move(0.0, -step, z))) {
            z = reduce(z);
        }
        while (x != 0.0 && z != 0.0 && player.level().noCollision(player, player.getBoundingBox().move(x, -step, z))) {
            x = reduce(x);
            z = reduce(z);
        }
        return new Vec3(x, delta.y, z);
    }

    private static double reduce(double component) {
        if (component < 0.05 && component >= -0.05) {
            return 0.0;
        }
        return component > 0.0 ? component - 0.05 : component + 0.05;
    }

    @Override
    public final boolean isAboveGround(Player player, float maxDownStep, boolean vanilla) {
        return player.onGround()
            || player.fallDistance < maxDownStep
                && !player.level().noCollision(
                    player,
                    player.getBoundingBox().move(0.0, player.fallDistance - maxDownStep, 0.0)
                );
    }
}
