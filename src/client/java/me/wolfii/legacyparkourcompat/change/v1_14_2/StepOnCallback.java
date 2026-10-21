package me.wolfii.legacyparkourcompat.change.v1_14_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.StepOnCallbackHook;
import net.minecraft.world.entity.player.Player;

/**
 * In 1.14.4 Mojmap net.minecraft.world.entity.Entity.move, stepOn is called
 * only inside makeStepSound, non-sneaking-player and non-passenger gates;
 * 1.14.4 Mojmap net.minecraft.world.entity.player.Player.makeStepSound returns
 * false while flying. Starting in 1.15, Mojmap
 * net.minecraft.world.entity.Entity.move places stepOn in a separate grounded
 * and not-stepping-carefully gate. The same gate appears in 1.15.2 Mojmap
 * net.minecraft.world.entity.Entity.move, and 1.15.2 Mojmap
 * net.minecraft.world.level.block.SlimeBlock.stepOn applies horizontal damping
 * when that callback runs. Reinstating the 1.14.4 player gates preserves the
 * earlier slime-step result. The keyed variant limits this accepted operation
 * to slime blocks; other callbacks retain their native behavior.
 */
@MovementChange(emulates = ParkourVersion.V1_14_2)
public final class StepOnCallback implements StepOnCallbackHook {
    public StepOnCallback() {
    }

    @Override
    public String variant() {
        return "minecraft:slime_block";
    }

    @Override
    public boolean shouldInvoke(Player player) {
        return !player.getAbilities().flying && !player.isShiftKeyDown() && !player.isPassenger();
    }
}
