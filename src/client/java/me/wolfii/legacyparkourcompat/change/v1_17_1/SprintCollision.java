package me.wolfii.legacyparkourcompat.change.v1_17_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.SprintCollisionHook;
import net.minecraft.world.entity.player.Player;

/**
 * 1.17.1 Mojmap LocalPlayer.aiStep stops non-swimming sprint for every
 * horizontalCollision. 1.18 and 1.18.1 Mojmap LocalPlayer.aiStep introduce
 * the minorHorizontalCollision exception, retained by 1.18.2. The native
 * 26.2 unobfuscated LocalPlayer.shouldStopRunSprinting reads that exception;
 * adding any horizontal collision to its result restores the older rule.
 */
@MovementChange(emulates = ParkourVersion.V1_17_1)
public final class SprintCollision implements SprintCollisionHook {
    @Override
    public boolean shouldStopRunSprinting(Player player, boolean vanilla) {
        return vanilla || player.horizontalCollision;
    }
}
