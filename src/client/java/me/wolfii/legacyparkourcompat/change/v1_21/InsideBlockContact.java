package me.wolfii.legacyparkourcompat.change.v1_21;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.InsideBlockContactHook;
import net.minecraft.world.entity.player.Player;

/**
 * The 1.21.1 move-time final-box scan deflates by 1.0E-7. From 1.21.2,
 * Entity.checkInsideBlocks uses 1.0E-5F and the native inset is retained by
 * the 1.21.2 change.
 *
 * Sources (paths relative to decompiled_minecraft/):
 * 1.21.1/mojmap/net/minecraft/world/entity/Entity.java: checkInsideBlocks.
 * 1.21.2/mojmap/net/minecraft/world/entity/Entity.java: checkInsideBlocks.
 */
@MovementChange(emulates = ParkourVersion.V1_21)
public final class InsideBlockContact implements InsideBlockContactHook {
    @Override
    public double inset(Player player, double vanilla) {
        return 1.0E-7;
    }
}
