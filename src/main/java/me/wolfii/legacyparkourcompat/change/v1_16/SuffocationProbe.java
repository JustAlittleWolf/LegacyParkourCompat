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
public final class SuffocationProbe implements ClientUnstuckBehavior {
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
}
