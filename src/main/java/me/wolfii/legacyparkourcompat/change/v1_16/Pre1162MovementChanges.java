package me.wolfii.legacyparkourcompat.change.v1_16;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.ClientUnstuckBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SneakEdgeBehavior;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

@MovementChange(emulates = ParkourVersion.V1_16)
public final class Pre1162MovementChanges implements ClientUnstuckBehavior, SneakEdgeBehavior {
    @Override
    public void moveTowardsClosestSpace(Player player, double x, double z, VanillaCall vanilla) {
        vanilla.run();
    }

    @Override
    public boolean suffocatesAt(Player player, BlockPos pos, VanillaFn<Boolean> vanilla) {
        int minY = Mth.floor(player.getBoundingBox().minY);
        int maxY = Mth.ceil(player.getBoundingBox().maxY);
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos(pos.getX(), minY, pos.getZ());
        for (int y = minY; y < maxY; y++) {
            cursor.setY(y);
            if (player.level().getBlockState(cursor).isSuffocating(player.level(), cursor)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Vec3 maybeBackOffFromEdge(
        Player player,
        Vec3 delta,
        MoverType moverType,
        boolean stayingOnGroundSurface
    ) {
        if ((moverType != MoverType.SELF && moverType != MoverType.PLAYER)
            || !player.onGround()
            || !stayingOnGroundSurface) {
            return delta;
        }

        double x = delta.x;
        double z = delta.z;
        double downStep = -player.maxUpStep();
        while (x != 0.0D && player.level().noCollision(player, player.getBoundingBox().move(x, downStep, 0.0D))) {
            x = reduceTowardZero(x);
        }
        while (z != 0.0D && player.level().noCollision(player, player.getBoundingBox().move(0.0D, downStep, z))) {
            z = reduceTowardZero(z);
        }
        while (x != 0.0D && z != 0.0D
            && player.level().noCollision(player, player.getBoundingBox().move(x, downStep, z))) {
            x = reduceTowardZero(x);
            z = reduceTowardZero(z);
        }
        return new Vec3(x, delta.y, z);
    }

    private static double reduceTowardZero(double value) {
        if (value < 0.05D && value >= -0.05D) {
            return 0.0D;
        }
        return value > 0.0D ? value - 0.05D : value + 0.05D;
    }
}
