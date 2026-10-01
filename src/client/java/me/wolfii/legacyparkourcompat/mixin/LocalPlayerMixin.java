package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.SprintingBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.WaterSneakBehavior;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LocalPlayer.class)
abstract class LocalPlayerMixin {
    @Invoker("goDownInWater")
    protected abstract void legacyparkourcompat$invokeGoDownInWater();

    @ModifyArg(
        method = "canStartSprinting",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isSprintingPossible(Z)Z"),
        index = 0
    )
    private boolean legacyparkourcompat$allowWaterSprintStart(boolean vanilla) {
        Player player = (Player)(Object)this;
        return MovementRuntime.find(SprintingBehavior.class, player)
            .map(behavior -> behavior.allowShallowWaterSprint(player, vanilla))
            .orElse(vanilla);
    }

    @ModifyArg(
        method = "shouldStopRunSprinting",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isSprintingPossible(Z)Z"),
        index = 0
    )
    private boolean legacyparkourcompat$allowWaterSprintContinue(boolean vanilla) {
        Player player = (Player)(Object)this;
        return MovementRuntime.find(SprintingBehavior.class, player)
            .map(behavior -> behavior.allowShallowWaterSprint(player, vanilla))
            .orElse(vanilla);
    }

    @Redirect(
        method = "aiStep",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;goDownInWater()V")
    )
    private void legacyparkourcompat$waterSneakDescent(LocalPlayer player) {
        boolean apply = MovementRuntime.find(WaterSneakBehavior.class, player)
            .map(behavior -> behavior.shouldApplyDownwardImpulse(player, true))
            .orElse(true);
        if (apply) {
            ((LocalPlayerMixin)(Object)player).legacyparkourcompat$invokeGoDownInWater();
        }
    }
}
