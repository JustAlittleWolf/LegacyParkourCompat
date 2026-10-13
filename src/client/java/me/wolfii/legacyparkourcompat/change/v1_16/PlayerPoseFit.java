package me.wolfii.legacyparkourcompat.change.v1_16;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.PoseFitHook;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;

/**
 * Emulates through 1.21.4. Since 1.16, pose-fit collision uses the pose box inset by 1.0E-7 before
 * testing noCollision. In 1.20.2 Player.canPlayerFitWithinBlocksAndEntitiesWhen
 * becomes the provider and retains that inset; earlier Entity.canEnterPose is
 * the provider. (1.16 Entity.canEnterPose; 1.20.2 Player provider, Mojmap.)
 */
@MovementChange(emulates = ParkourVersion.V1_21_4)
public final class PlayerPoseFit implements PoseFitHook {
    @Override
    public boolean canFit(Player player, Pose pose) {
        return player.level().noCollision(
            player,
            player.getDimensions(pose).makeBoundingBox(player.position()).deflate(1.0E-7)
        );
    }
}
