package me.wolfii.legacyparkourcompat.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.FallFlyingStartBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.AutoJumpBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.AutoJumpInverseSqrtBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.FlightActivationJumpBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.FlightSneakInputBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.KeyboardDiagonalInputBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.PassengerCrouchBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.RideableJumpBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.ShallowWaterSprintBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SneakInputSlowdownBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SprintCollisionBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SprintFallFlyingGateBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SprintStartBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SprintTickBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SprintTriggerBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SprintWindowBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SuffocationProbeBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.VehicleSprintBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.WaterDescentBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.WaterSneakBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.WaterSprintGateBehavior;
import me.wolfii.legacyparkourcompat.mixin.client.LocalPlayerAccessor;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.player.KeyboardInput;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.minecraft.world.entity.player.Input;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec2;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LocalPlayer.class)
abstract class LocalPlayerMixin {

    @WrapOperation(
        method = "aiStep",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/player/Player;tryToStartFallFlying()Z"
        )
    )
    private boolean legacyparkourcompat$fallFlyingJumpStart(Player player, Operation<Boolean> vanilla) {
        return MovementRuntime.find(FallFlyingStartBehavior.class, player)
            .map(behavior -> behavior.tryStartFallFlying(player, () -> vanilla.call(player)))
            .orElseGet(() -> vanilla.call(player));
    }

    @Redirect(
        method = "modifyInput(Lnet/minecraft/world/phys/Vec2;)Lnet/minecraft/world/phys/Vec2;",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isMovingSlowly()Z")
    )
    private boolean legacyparkourcompat$sneakSlowdownGate(LocalPlayer player) {
        boolean vanilla = player.isMovingSlowly();
        return MovementRuntime.find(SneakInputSlowdownBehavior.class, player)
            .map(behavior -> behavior.shouldSlowDown(player))
            .orElse(vanilla);
    }

    @WrapOperation(
        method = "aiStep",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/player/LocalPlayer;jumpFromGround()V"
        )
    )
    private void legacyparkourcompat$flightActivationJump(LocalPlayer player, Operation<Void> vanilla) {
        MovementRuntime.find(FlightActivationJumpBehavior.class, player)
            .ifPresentOrElse(
                behavior -> behavior.onFlightActivated(player, () -> vanilla.call(player)),
                () -> vanilla.call(player)
            );
    }

    @ModifyExpressionValue(
        method = "aiStep",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/player/LocalPlayer;isPassenger()Z",
            ordinal = 0
        )
    )
    private boolean legacyparkourcompat$passengerBlocksCrouching(boolean vanilla) {
        LocalPlayer player = (LocalPlayer)(Object)this;
        return MovementRuntime.find(PassengerCrouchBehavior.class, player)
            .map(behavior -> behavior.blocksCrouching(player, vanilla))
            .orElse(vanilla);
    }

    @Inject(method = "aiStep", at = @At("TAIL"))
    private void legacyparkourcompat$tickSprintDuration(CallbackInfo ci) {
        LocalPlayer player = (LocalPlayer) (Object) this;
        MovementRuntime.find(SprintTickBehavior.class, player)
            .ifPresent(behavior -> behavior.tick(player));
    }

    @ModifyVariable(method = "modifyInput", at = @At("HEAD"), argsOnly = true)
    private Vec2 legacyparkourcompat$modifyInput(Vec2 input) {
        LocalPlayer player = (LocalPlayer) (Object) this;
        return MovementRuntime.find(FlightSneakInputBehavior.class, player).map(behavior -> behavior.modify(
                player,
                input,
                MovementRuntime.find(SneakInputSlowdownBehavior.class, player)
                    .map(slowdown -> slowdown.shouldSlowDown(player)).orElse(player.isMovingSlowly())
            )).orElse(input);
    }

    @Redirect(
        method = "aiStep",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/PlayerRideableJumping;onPlayerJump(I)V")
    )
    private void legacyparkourcompat$applyRideableJumpCharge(PlayerRideableJumping mount, int strength) {
        LocalPlayer player = (LocalPlayer) (Object) this;
        MovementRuntime.find(RideableJumpBehavior.class, player)
            .ifPresentOrElse(
                behavior -> behavior.onPlayerJump(player, mount, strength, () -> mount.onPlayerJump(strength)),
                () -> mount.onPlayerJump(strength)
            );
    }

    @ModifyArg(
        method = "canStartSprinting",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isSprintingPossible(Z)Z"),
        index = 0
    )
    private boolean legacyparkourcompat$allowWaterSprintStart(boolean vanilla) {
        Player player = (Player)(Object)this;
        return MovementRuntime.find(WaterSprintGateBehavior.class, player)
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
        return MovementRuntime.find(WaterSprintGateBehavior.class, player)
            .map(behavior -> behavior.allowShallowWaterSprint(player, vanilla))
            .orElse(vanilla);
    }

    @WrapOperation(
        method = "aiStep",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;goDownInWater()V")
    )
    private void legacyparkourcompat$waterSneakDescent(LocalPlayer player, Operation<Void> vanilla) {
        boolean apply = MovementRuntime.find(WaterSneakBehavior.class, player)
            .map(behavior -> behavior.shouldApplyDownwardImpulse(player, true))
            .orElse(true);
        if (apply) {
            vanilla.call(player);
        }
    }

    @Redirect(
        method = "updateAutoJump(FF)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;invSqrt(F)F", ordinal = 0)
    )
    private float legacyparkourcompat$autoJumpInverseSqrt(float value) {
        float vanilla = org.joml.Math.invsqrt(value);
        Player player = (Player) (Object) this;
        return MovementRuntime.find(AutoJumpInverseSqrtBehavior.class, player)
            .map(behavior -> behavior.inverseSqrt(player, value, vanilla))
            .orElse(vanilla);
    }

    @Redirect(
        method = "canStartSprinting()Z",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isFallFlying()Z", ordinal = 0)
    )
    private boolean legacyparkourcompat$fallFlyingSprintGate(LocalPlayer localPlayer) {
        boolean vanilla = localPlayer.isFallFlying();
        Player player = localPlayer;
        return MovementRuntime.find(SprintFallFlyingGateBehavior.class, player)
            .map(behavior -> behavior.fallFlyingForSprintGate(player, MovementRuntime.profile(player).target(), vanilla))
            .orElse(vanilla);
    }

    @Inject(method = "canStartSprinting()Z", at = @At("RETURN"), cancellable = true)
    private void legacyparkourcompat$historicalFallFlyingSprintGate(CallbackInfoReturnable<Boolean> cir) {
        Player player = (Player) (Object) this;
        MovementRuntime.find(SprintStartBehavior.class, player).ifPresent(behavior ->
            cir.setReturnValue(behavior.canStartSprinting(player, cir.getReturnValue()))
        );
    }

    @Inject(method = "vehicleCanSprint(Lnet/minecraft/world/entity/Entity;)Z", at = @At("RETURN"), cancellable = true)
    private void legacyparkourcompat$historicalVehicleSprint(Entity vehicle, CallbackInfoReturnable<Boolean> cir) {
        Player player = (Player) (Object) this;
        MovementRuntime.find(VehicleSprintBehavior.class, player).ifPresent(behavior ->
            cir.setReturnValue(behavior.vehicleCanSprint(player, vehicle, MovementRuntime.profile(player).target(), cir.getReturnValue()))
        );
    }

    @Inject(method = "shouldStopRunSprinting()Z", at = @At("RETURN"), cancellable = true)
    private void legacyparkourcompat$restoreSprintStopOnCollision(CallbackInfoReturnable<Boolean> cir) {
        LocalPlayer player = (LocalPlayer) (Object) this;
        MovementRuntime.find(SprintCollisionBehavior.class, player)
            .ifPresent(behavior -> cir.setReturnValue(
                behavior.shouldStopRunSprinting(player, cir.getReturnValue())
            ));
    }

    @Inject(
        method = "modifyInput(Lnet/minecraft/world/phys/Vec2;)Lnet/minecraft/world/phys/Vec2;",
        at = @At("RETURN"),
        cancellable = true
    )
    private void legacyparkourcompat$diagonalInputPrecision(Vec2 raw, CallbackInfoReturnable<Vec2> callback) {
        LocalPlayer player = (LocalPlayer) (Object) this;
        Input keys = player.input.keyPresses;
        float left = keys.left() == keys.right() ? 0.0F : keys.left() ? 1.0F : -1.0F;
        float forward = keys.forward() == keys.backward() ? 0.0F : keys.forward() ? 1.0F : -1.0F;
        Vec2 rawImpulses = new Vec2(left, forward);
        boolean keyboard = player.input instanceof KeyboardInput;
        boolean diagonal = left != 0.0F && forward != 0.0F;
        boolean usingItem = player.isUsingItem() && !player.isPassenger();
        Vec2 vanilla = callback.getReturnValue();
        MovementRuntime.find(KeyboardDiagonalInputBehavior.class, player)
            .ifPresent(behavior -> callback.setReturnValue(behavior.diagonalInput(
                (Player) player,
                rawImpulses,
                vanilla,
                keyboard,
                diagonal,
                player.isMovingSlowly(),
                usingItem
            )));
    }

    @Inject(method = "isAutoJumpEnabled", at = @At("RETURN"), cancellable = true)
    private void legacyparkourcompat$applyAutoJumpBehavior(CallbackInfoReturnable<Boolean> cir) {
        LocalPlayer player = (LocalPlayer)(Object)this;
        MovementRuntime.find(AutoJumpBehavior.class, player).ifPresent(behavior ->
            cir.setReturnValue(behavior.isAutoJumpEnabled(player, cir.getReturnValue()))
        );
    }

    @Shadow
    private boolean isSlowDueToUsingItem() {
        throw new AssertionError();
    }

    @Redirect(
        method = "aiStep",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/client/player/LocalPlayer;sprintTriggerTime:I",
            opcode = Opcodes.PUTFIELD,
            ordinal = 1
        )
    )
    private void legacyparkourcompat$sprintTriggerReset(LocalPlayer player, int value) {
        LocalPlayerAccessor accessor = (LocalPlayerAccessor) player;
        // The pre-1.16 path still cleared the timer for item use. The newer
        // crouch and backward-input cancellations belong to this assignment,
        // so only preserve the item-use cause when emulating the older path.
        boolean anotherResetCause = this.isSlowDueToUsingItem() && !player.isPassenger();
        boolean shouldReset = MovementRuntime.find(SprintTriggerBehavior.class, player)
            .map(behavior -> behavior.shouldReset(player, anotherResetCause))
            .orElse(true);
        if (shouldReset) {
            accessor.legacyparkourcompat$setSprintTriggerTime(value);
        }
    }

    @Redirect(
        method = "aiStep",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isAffectedByFluids()Z")
    )
    private boolean legacyparkourcompat$waterDescentGate(LocalPlayer player) {
        boolean vanilla = player.isAffectedByFluids();
        return MovementRuntime.find(WaterDescentBehavior.class, player)
            .map(behavior -> behavior.mayDescend(player, vanilla))
            .orElse(vanilla);
    }

    @Inject(method = "suffocatesAt", at = @At("RETURN"), cancellable = true)
    private void legacyparkourcompat$suffocationQuery(BlockPos pos, CallbackInfoReturnable<Boolean> callback) {
        LocalPlayer player = (LocalPlayer) (Object) this;
        MovementRuntime.find(SuffocationProbeBehavior.class, player).ifPresent(behavior ->
            callback.setReturnValue(behavior.suffocatesAt(
                player,
                MovementRuntime.profile(player).target(),
                pos,
                callback::getReturnValue
            ))
        );
    }

    @Redirect(
        method = "aiStep()V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/OptionInstance;get()Ljava/lang/Object;"),
        slice = @Slice(from = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/Options;sprintWindow()Lnet/minecraft/client/OptionInstance;"
        ))
    )
    private Object legacyparkourcompat$doubleTapSprintWindow(OptionInstance<Integer> option) {
        LocalPlayer player = (LocalPlayer) (Object) this;
        int vanilla = option.get();
        return MovementRuntime.find(SprintWindowBehavior.class, player)
            .map(behavior -> behavior.doubleTapWindow((Player) player, vanilla))
            .orElse(vanilla);
    }

    @Redirect(
        method = "isSprintingPossible(Z)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/player/LocalPlayer;isInShallowWater()Z"
        )
    )
    private boolean legacyparkourcompat$shallowWaterSprintEligibility(LocalPlayer entity) {
        boolean vanilla = entity.isInShallowWater();
        LocalPlayer player = entity;
        boolean sprintKeyDown = player.input.keyPresses.sprint();
        return MovementRuntime.find(ShallowWaterSprintBehavior.class, player)
            .map(behavior -> behavior.isInShallowWaterForSprintEligibility(
                player,
                sprintKeyDown,
                player.isSprinting(),
                vanilla
            ))
            .orElse(vanilla);
    }
}
