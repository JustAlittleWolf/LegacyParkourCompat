package me.wolfii.legacyparkourcompat.mixin;


@Mixin(LocalPlayer.class)

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.ClientInputBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.RideableJumpBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SprintDurationBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SprintingBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.WaterSneakBehavior;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec2;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

abstract class LocalPlayerMixin {
    @Inject(method = "aiStep", at = @At("TAIL"))
    private void legacyParkourCompat$tickSprintDuration(CallbackInfo ci) {
        LocalPlayer player = (LocalPlayer) (Object) this;
        MovementRuntime.find(SprintDurationBehavior.class, player)
            .ifPresent(behavior -> behavior.tick(player));
    }

    @ModifyVariable(method = "modifyInput", at = @At("HEAD"), argsOnly = true)
    private Vec2 legacyParkourCompat$modifyInput(Vec2 input) {
        LocalPlayer player = (LocalPlayer) (Object) this;
        return MovementRuntime.find(ClientInputBehavior.class, player).map(behavior -> behavior.modify(
                input,
                player.isUsingItem(),
                player.isMovingSlowly(),
                (float) player.getAttributeValue(Attributes.SNEAKING_SPEED),
                player.getAbilities().flying && player.isMovingSlowly()
            )).orElse(input);
    }

    @Redirect(
        method = "aiStep",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/PlayerRideableJumping;onPlayerJump(I)V")
    )
    private void legacyParkourCompat$applyRideableJumpCharge(PlayerRideableJumping mount, int strength) {
        LocalPlayer player = (LocalPlayer) (Object) this;
        MovementRuntime.find(RideableJumpBehavior.class, player)
            .ifPresentOrElse(
                behavior -> behavior.onPlayerJump(player, mount, strength, () -> mount.onPlayerJump(strength)),
                () -> mount.onPlayerJump(strength)
            );
    }



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
