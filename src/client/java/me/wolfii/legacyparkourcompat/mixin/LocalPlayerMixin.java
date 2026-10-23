package me.wolfii.legacyparkourcompat.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.clientload.ClientLoadGateHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.FallFlyingStartHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.FallFlyingClimbableGateHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.FallFlyingFlightToggleGateHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.AutoJumpCollisionOrderHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.AutoJumpBorderCandidateHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.AutoJumpInverseSqrtHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.FlightActivationJumpHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.FlightSneakInputHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.SimultaneousFlightInputHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.FlightToggleVehicleHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.SpectatorEntryVelocityResetHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.KeyboardDiagonalInputHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.vehicle.PassengerCrouchHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.network.PositionPacketThresholdHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.PushAwayVelocityHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.vehicle.RideableJumpHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.ShallowWaterSprintHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.InputSlowdownHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.StoredKeyboardInputHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.SneakSpeedScalarHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.SlowInputPredicateHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.PoseFitHook;
import me.wolfii.legacyparkourcompat.mechanic.input.KeyboardInputSupport;
import me.wolfii.legacyparkourcompat.mechanic.hook.sprint.ForwardSprintInputHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.SprintCollisionHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.SprintFallFlyingGateHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.DoubleTapSprintStartHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.ShallowWaterSprintStartHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.SprintKeyStartHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.SprintStartHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.SlowInputSprintStartHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.FlightShallowWaterSprintHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.SprintTickHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.SprintTriggerHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.SprintWindowHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.SuffocationProbeHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.vehicle.VehicleSprintHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.WaterDescentHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.WaterSneakHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.WaterSprintGateHook;
import me.wolfii.legacyparkourcompat.mixin.client.LocalPlayerAccessor;
import me.wolfii.legacyparkourcompat.mixin.client.ClientInputAccessor;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.player.KeyboardInput;
import net.minecraft.client.player.ClientInput;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.minecraft.world.entity.player.Input;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
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
    // Mojmap 1.21.4–1.21.10 Player initializes this per instance. Both historical
    // and published 26.2 ClientPacketListener#handleRespawn create a new player,
    // including same-dimension respawns; the timer must not carry across players.
    @Unique
    private int legacyparkourcompat$clientLoadTimeoutTicks = 60;

    @ModifyExpressionValue(method = "tick()V", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/client/multiplayer/ClientPacketListener;hasClientLoaded()Z"))
    private boolean legacyparkourcompat$clientLoadGate(boolean clientLoaded) {
        LocalPlayer player = (LocalPlayer) (Object) this;
        var hook = MovementRuntime.find(ClientLoadGateHook.class, player);
        if (hook.isEmpty()) {
            return clientLoaded;
        }
        // Player#tickClientLoadTimeout decrements while not explicitly ready,
        // even after expiry. Preserve Java int arithmetic and decision order.
        if (!clientLoaded) {
            this.legacyparkourcompat$clientLoadTimeoutTicks--;
        }
        return hook.get().shouldRunTick(clientLoaded, this.legacyparkourcompat$clientLoadTimeoutTicks);
    }

    @ModifyExpressionValue(method = {"canStartSprinting()Z", "shouldStopRunSprinting()Z"}, at = @At(value = "INVOKE",
        target = "Lnet/minecraft/client/player/LocalPlayer;isSprintingPossible(Z)Z"))
    private boolean legacyparkourcompat$flightShallowWaterSprint(boolean vanilla) {
        Player player = (Player) (Object) this;
        return MovementRuntime.find(FlightShallowWaterSprintHook.class, player)
            .map(hook -> hook.isSprintingPossible(player, MovementRuntime.profile(player).target(), vanilla))
            .orElse(vanilla);
    }

    @ModifyExpressionValue(method = "canStartSprinting()Z", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/client/player/LocalPlayer;isMovingSlowly()Z"))
    private boolean legacyparkourcompat$slowInputSprintGate(boolean movingSlowly) {
        LocalPlayer player = (LocalPlayer) (Object) this;
        return MovementRuntime.find(SlowInputSprintStartHook.class, player)
            .map(hook -> !hook.allowsSprintingWhileMovingSlowly() && movingSlowly)
            .orElse(movingSlowly);
    }

    @WrapOperation(method = "onGameModeChanged", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/client/player/LocalPlayer;setDeltaMovement(Lnet/minecraft/world/phys/Vec3;)V"))
    private void legacyparkourcompat$spectatorEntryVelocity(LocalPlayer player, Vec3 movement, Operation<Void> original) {
        boolean reset = MovementRuntime.find(SpectatorEntryVelocityResetHook.class, player)
            .map(hook -> hook.resetVelocity(MovementRuntime.profile(player).target()))
            .orElse(true);
        if (reset) {
            original.call(player, movement);
        }
    }

    @Unique
    private InputSlowdownHook legacyparkourcompat$inputSlowdown;
    @Unique
    private StoredKeyboardInputHook legacyparkourcompat$storedKeyboardInput;
    @Unique
    private Vec2 legacyparkourcompat$keyboardInput;

    @WrapOperation(
        method = {"aiStep()V", "canStartSprinting()Z"},
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/ClientInput;hasForwardImpulse()Z")
    )
    private boolean legacyparkourcompat$storedForwardSprintInput(ClientInput input, Operation<Boolean> vanilla) {
        // Apply only while an exact historical stored-input producer is active.
        // Other profiles retain native eligibility until their producer is bound.
        LocalPlayer player = (LocalPlayer)(Object)this;
        boolean active = this.legacyparkourcompat$inputSlowdown != null
            && this.legacyparkourcompat$inputSlowdown.supports(player, MovementRuntime.profile(player).target())
            || this.legacyparkourcompat$storedKeyboardInput != null
                && this.legacyparkourcompat$storedKeyboardInput.supports(player, MovementRuntime.profile(player).target());
        if (!active || !(input instanceof KeyboardInput)) {
            return vanilla.call(input);
        }
        return MovementRuntime.find(ForwardSprintInputHook.class, player)
            .map(change -> change.hasEnoughForwardInput(input.getMoveVector().y, player.isUnderWater()))
            .orElseGet(() -> vanilla.call(input));
    }

    @ModifyVariable(method = "aiStep()V", at = @At("STORE"), name = "hasForwardImpulse")
    private boolean legacyparkourcompat$doubleTapSupportBeforeArming(boolean previousForward) {
        LocalPlayer player = (LocalPlayer)(Object)this;
        // The prior-forward local gates both timer arming and double-tap start;
        // the separate held sprint-key branch does not read it.
        return MovementRuntime.find(DoubleTapSprintStartHook.class, player)
            .map(change -> !change.allowDoubleTapTransition(player, player.input.keyPresses.shift(), previousForward))
            .orElse(previousForward);
    }

    @WrapOperation(
        method = "aiStep()V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/ClientInput;tick()V")
    )
    private void legacyparkourcompat$sampleKeyboardInput(ClientInput input, Operation<Void> vanilla) {
        LocalPlayer player = (LocalPlayer)(Object)this;
        this.legacyparkourcompat$inputSlowdown = MovementRuntime.find(InputSlowdownHook.class, player)
            .filter(change -> input instanceof KeyboardInput && change.supports(player, MovementRuntime.profile(player).target()))
            .orElse(null);
        this.legacyparkourcompat$storedKeyboardInput = MovementRuntime.find(StoredKeyboardInputHook.class, player)
            .filter(change -> input instanceof KeyboardInput && change.supports(player, MovementRuntime.profile(player).target()))
            .orElse(null);
        this.legacyparkourcompat$keyboardInput = null;
        boolean sampledPose = this.legacyparkourcompat$inputSlowdown != null
            && this.legacyparkourcompat$inputSlowdown.samplePoseSlowdown(player);
        if (this.legacyparkourcompat$storedKeyboardInput != null) {
            PoseFitHook fit = MovementRuntime.find(PoseFitHook.class, player)
                .orElseThrow(() -> new IllegalStateException("Missing historical keyboard pose-fit provider"));
            SlowInputPredicateHook predicate = MovementRuntime.find(SlowInputPredicateHook.class, player)
                .orElseThrow(() -> new IllegalStateException("Missing historical keyboard slowdown predicate"));
            sampledPose = predicate.shouldSlow(player.getAbilities().flying, player.isSwimming(), player.isPassenger(),
                player.isShiftKeyDown(), player.isSleeping(),
                () -> fit.canFit(player, net.minecraft.world.entity.Pose.CROUCHING),
                () -> fit.canFit(player, net.minecraft.world.entity.Pose.STANDING),
                () -> player.getPose() == net.minecraft.world.entity.Pose.SWIMMING && !player.isInWater());
        }
        float sampledSneakSpeed = 0.3F;
        if (this.legacyparkourcompat$storedKeyboardInput != null) {
            SneakSpeedScalarHook scalar = MovementRuntime.find(SneakSpeedScalarHook.class, player)
                .orElseThrow(() -> new IllegalStateException("Missing historical sneak-speed scalar provider"));
            int swiftSneakLevel = KeyboardInputSupport.swiftSneakLevel(player, MovementRuntime.profile(player).target())
                .orElseThrow(() -> new IllegalStateException("Missing supported historical keyboard scalar"));
            sampledSneakSpeed = scalar.scalar(swiftSneakLevel,
                player.getAttributeValue(net.minecraft.world.entity.ai.attributes.Attributes.SNEAKING_SPEED));
        }
        vanilla.call(input);
        if (this.legacyparkourcompat$storedKeyboardInput != null) {
            this.legacyparkourcompat$keyboardInput = this.legacyparkourcompat$storedKeyboardInput.apply(
                input.keyPresses, sampledPose, sampledSneakSpeed,
                player.isUsingItem() && !player.isPassenger());
            ((ClientInputAccessor)input).legacyparkourcompat$setMoveVector(this.legacyparkourcompat$keyboardInput);
        } else if (this.legacyparkourcompat$inputSlowdown != null) {
            Input keys = input.keyPresses;
            float left = keys.left() == keys.right() ? 0.0F : keys.left() ? 1.0F : -1.0F;
            float forward = keys.forward() == keys.backward() ? 0.0F : keys.forward() ? 1.0F : -1.0F;
            this.legacyparkourcompat$keyboardInput = this.legacyparkourcompat$inputSlowdown
                .keyboardInput(player, sampledPose, left, forward);
            ((ClientInputAccessor)input).legacyparkourcompat$setMoveVector(this.legacyparkourcompat$keyboardInput);
        }
    }

    @Redirect(
        method = "modifyInput(Lnet/minecraft/world/phys/Vec2;)Lnet/minecraft/world/phys/Vec2;",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/LivingEntity;getAttributeValue(Lnet/minecraft/core/Holder;)D"
        )
    )
    private double legacyparkourcompat$historicalSneakInputFactor(
        LivingEntity entity, net.minecraft.core.Holder<?> attribute
    ) {
        double nativeFactor = entity.getAttributeValue(attribute);
        LocalPlayer player = (LocalPlayer) (Object) this;
        if (player.getPose() != Pose.CROUCHING || player.isSwimming()
            || player.getAbilities().flying || player.isPassenger()
            || !(player.input instanceof KeyboardInput)) {
            return nativeFactor;
        }
        // The selected KeyboardInput hook already applied its historical slowdown.
        return MovementRuntime.find(InputSlowdownHook.class, player)
            .filter(hook -> hook.supports(player, MovementRuntime.profile(player).target()))
            .map(hook -> (double) hook.modifyInputFactor((float) nativeFactor))
            .orElse(nativeFactor);
    }

    @WrapOperation(
        method = "aiStep()V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isControlledCamera()Z")
    )
    private boolean legacyparkourcompat$flightKeyboardInput(LocalPlayer player, Operation<Boolean> vanilla) {
        boolean controlled = vanilla.call(player);
        if (controlled && player.input.keyPresses.shift() && this.legacyparkourcompat$inputSlowdown != null) {
            this.legacyparkourcompat$keyboardInput = MovementRuntime.find(FlightSneakInputHook.class, player)
                .map(change -> change.compensate(player, MovementRuntime.profile(player).target(), this.legacyparkourcompat$keyboardInput))
                .orElse(this.legacyparkourcompat$keyboardInput);
            ((ClientInputAccessor)player.input).legacyparkourcompat$setMoveVector(this.legacyparkourcompat$keyboardInput);
        }
        if (controlled && player.input instanceof KeyboardInput) {
            MovementRuntime.find(SimultaneousFlightInputHook.class, player)
                .ifPresent(hook -> hook.apply(player, MovementRuntime.profile(player).target(), player.input.keyPresses));
        }
        return controlled;
    }

    @ModifyExpressionValue(
        method = "sendPosition()V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;square(D)D"),
        require = 1
    )
    private double legacyparkourcompat$positionPacketThreshold(double vanillaThreshold) {
        LocalPlayer player = (LocalPlayer)(Object)this;
        return MovementRuntime.find(PositionPacketThresholdHook.class, player)
            .map(behavior -> behavior.squaredDisplacementThreshold(vanillaThreshold))
            .orElse(vanillaThreshold);
    }

    @WrapOperation(
        method = "aiStep",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/player/LocalPlayer;tryToStartFallFlying()Z"
        )
    )
    private boolean legacyparkourcompat$fallFlyingJumpStart(LocalPlayer player, Operation<Boolean> vanilla) {
        return MovementRuntime.find(FallFlyingStartHook.class, player)
            .map(behavior -> behavior.tryToStartFallFlying(player, MovementRuntime.profile(player).target(), () -> vanilla.call(player)))
            .orElseGet(() -> vanilla.call(player));
    }

    @ModifyExpressionValue(
        method = "aiStep",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;onClimbable()Z")
    )
    private boolean legacyparkourcompat$fallFlyingClimbableGate(boolean vanilla) {
        Player player = (Player)(Object)this;
        return MovementRuntime.find(FallFlyingClimbableGateHook.class, player)
            .map(behavior -> behavior.isOnClimbableForFallFlyingStart(player, MovementRuntime.profile(player).target(), vanilla))
            .orElse(vanilla);
    }

    @ModifyVariable(method = "aiStep", at = @At("STORE"), name = "justToggledCreativeFlight")
    private boolean legacyparkourcompat$fallFlyingToggleGate(boolean vanilla) {
        Player player = (Player)(Object)this;
        return MovementRuntime.find(FallFlyingFlightToggleGateHook.class, player)
            .map(behavior -> behavior.justToggledCreativeFlightForStart(player, MovementRuntime.profile(player).target(), vanilla))
            .orElse(vanilla);
    }

    @WrapOperation(method = "aiStep()V", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/client/player/LocalPlayer;getVehicle()Lnet/minecraft/world/entity/Entity;"))
    private Entity legacyparkourcompat$flightToggleVehicle(LocalPlayer player, Operation<Entity> original) {
        boolean checksVehicle = MovementRuntime.find(FlightToggleVehicleHook.class, player)
            .map(hook -> hook.checksVehicle(MovementRuntime.profile(player).target()))
            .orElse(true);
        return checksVehicle ? original.call(player) : null;
    }

    @WrapOperation(
        method = "aiStep",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/player/LocalPlayer;jumpFromGround()V"
        )
    )
    private void legacyparkourcompat$flightActivationJump(LocalPlayer player, Operation<Void> vanilla) {
        MovementRuntime.find(FlightActivationJumpHook.class, player)
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
        return MovementRuntime.find(PassengerCrouchHook.class, player)
            .map(behavior -> behavior.blocksCrouching(player, vanilla))
            .orElse(vanilla);
    }

    @Inject(method = "aiStep", at = @At("HEAD"))
    private void legacyparkourcompat$tickSprintDuration(CallbackInfo ci) {
        LocalPlayer player = (LocalPlayer) (Object) this;
        MovementRuntime.find(SprintTickHook.class, player)
            .ifPresent(behavior -> behavior.tick(player));
    }

    @Redirect(
        method = "aiStep",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/PlayerRideableJumping;onPlayerJump(I)V")
    )
    private void legacyparkourcompat$applyRideableJumpCharge(PlayerRideableJumping mount, int strength) {
        LocalPlayer player = (LocalPlayer) (Object) this;
        MovementRuntime.find(RideableJumpHook.class, player)
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
        boolean allowed = MovementRuntime.find(WaterSprintGateHook.class, player)
            .map(behavior -> behavior.allowShallowWaterSprint(player, vanilla))
            .orElse(vanilla);
        return MovementRuntime.find(ShallowWaterSprintStartHook.class, player)
            .map(behavior -> behavior.allowShallowWaterStart(player, allowed))
            .orElse(allowed);
    }

    @WrapOperation(
        method = "aiStep",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/player/LocalPlayer;setSprinting(Z)V",
            ordinal = 0
        )
    )
    private void legacyparkourcompat$doubleTapSprintStart(LocalPlayer player, boolean sprinting, Operation<Void> vanilla) {
        boolean allowed = MovementRuntime.find(DoubleTapSprintStartHook.class, player)
            .map(behavior -> behavior.allowDoubleTapStart(player, sprinting))
            .orElse(sprinting);
        if (allowed) {
            vanilla.call(player, sprinting);
        }
    }

    @WrapOperation(
        method = "aiStep",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/player/LocalPlayer;setSprinting(Z)V",
            ordinal = 1
        )
    )
    private void legacyparkourcompat$sprintKeyStart(LocalPlayer player, boolean sprinting, Operation<Void> vanilla) {
        boolean allowed = MovementRuntime.find(SprintKeyStartHook.class, player)
            .map(behavior -> behavior.allowSprintKeyStart(player, sprinting))
            .orElse(sprinting);
        if (allowed) {
            vanilla.call(player, sprinting);
        }
    }

    @ModifyArg(
        method = "shouldStopRunSprinting",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isSprintingPossible(Z)Z"),
        index = 0
    )
    private boolean legacyparkourcompat$allowWaterSprintContinue(boolean vanilla) {
        Player player = (Player)(Object)this;
        return MovementRuntime.find(WaterSprintGateHook.class, player)
            .map(behavior -> behavior.allowShallowWaterSprint(player, vanilla))
            .orElse(vanilla);
    }

    @WrapOperation(
        method = "aiStep",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;goDownInWater()V")
    )
    private void legacyparkourcompat$waterSneakDescent(LocalPlayer player, Operation<Void> vanilla) {
        boolean apply = MovementRuntime.find(WaterSneakHook.class, player)
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
        return MovementRuntime.find(AutoJumpInverseSqrtHook.class, player)
            .map(behavior -> behavior.inverseSqrt(player, MovementRuntime.profile(player).target(), value, vanilla))
            .orElse(vanilla);
    }

    @WrapOperation(
        method = "updateAutoJump(FF)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/Level;getCollisions(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/AABB;)Ljava/lang/Iterable;"
        ),
        require = 1
    )
    private Iterable<VoxelShape> legacyparkourcompat$autoJumpCollisionOrder(
        Level level,
        Entity source,
        AABB query,
        Operation<Iterable<VoxelShape>> vanilla
    ) {
        Player player = (Player) (Object) this;
        Iterable<VoxelShape> candidates = MovementRuntime.find(AutoJumpCollisionOrderHook.class, player)
            .map(change -> change.autoJumpCollisions(player, level, query,
                MovementRuntime.profile(player).target(), () -> vanilla.call(level, source, query)))
            .orElseGet(() -> vanilla.call(level, source, query));
        return MovementRuntime.find(AutoJumpBorderCandidateHook.class, player)
            .flatMap(change -> change.borderCandidate(player, MovementRuntime.profile(player).target()))
            .<Iterable<VoxelShape>>map(border -> com.google.common.collect.Iterables.concat(
                java.util.List.of(border), candidates))
            .orElse(candidates);
    }

    @Redirect(
        method = "canStartSprinting()Z",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isFallFlying()Z", ordinal = 0)
    )
    private boolean legacyparkourcompat$fallFlyingSprintGate(LocalPlayer localPlayer) {
        boolean vanilla = localPlayer.isFallFlying();
        Player player = localPlayer;
        return MovementRuntime.find(SprintFallFlyingGateHook.class, player)
            .map(behavior -> behavior.fallFlyingForSprintGate(player, MovementRuntime.profile(player).target(), vanilla))
            .orElse(vanilla);
    }

    @Inject(method = "canStartSprinting()Z", at = @At("RETURN"), cancellable = true)
    private void legacyparkourcompat$historicalFallFlyingSprintGate(CallbackInfoReturnable<Boolean> cir) {
        Player player = (Player) (Object) this;
        MovementRuntime.find(SprintStartHook.class, player).ifPresent(behavior ->
            cir.setReturnValue(behavior.canStartSprinting(player, cir.getReturnValue()))
        );
    }

    @Inject(method = "vehicleCanSprint(Lnet/minecraft/world/entity/Entity;)Z", at = @At("RETURN"), cancellable = true)
    private void legacyparkourcompat$historicalVehicleSprint(Entity vehicle, CallbackInfoReturnable<Boolean> cir) {
        Player player = (Player) (Object) this;
        MovementRuntime.find(VehicleSprintHook.class, player).ifPresent(behavior ->
            cir.setReturnValue(behavior.vehicleCanSprint(player, vehicle, MovementRuntime.profile(player).target(), cir.getReturnValue()))
        );
    }

    @Inject(method = "shouldStopRunSprinting()Z", at = @At("RETURN"), cancellable = true)
    private void legacyparkourcompat$restoreSprintStopOnCollision(CallbackInfoReturnable<Boolean> cir) {
        LocalPlayer player = (LocalPlayer) (Object) this;
        MovementRuntime.find(SprintCollisionHook.class, player)
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
        if (this.legacyparkourcompat$storedKeyboardInput != null) {
            callback.setReturnValue(KeyboardInputSupport.travelInput(this.legacyparkourcompat$keyboardInput));
            return;
        }
        if (this.legacyparkourcompat$inputSlowdown != null) {
            callback.setReturnValue(this.legacyparkourcompat$inputSlowdown.travelInput(this.legacyparkourcompat$keyboardInput));
            return;
        }
        LocalPlayer player = (LocalPlayer) (Object) this;
        Input keys = player.input.keyPresses;
        float left = keys.left() == keys.right() ? 0.0F : keys.left() ? 1.0F : -1.0F;
        float forward = keys.forward() == keys.backward() ? 0.0F : keys.forward() ? 1.0F : -1.0F;
        Vec2 rawImpulses = new Vec2(left, forward);
        boolean keyboard = player.input instanceof KeyboardInput;
        boolean diagonal = left != 0.0F && forward != 0.0F;
        boolean usingItem = player.isUsingItem() && !player.isPassenger();
        Vec2 vanilla = callback.getReturnValue();
        MovementRuntime.find(KeyboardDiagonalInputHook.class, player)
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
    private void legacyparkourcompat$sprintTriggerReset(LocalPlayer player, int value,
        @Local(name = "wasShiftKeyDown") boolean priorShift) {
        LocalPlayerAccessor accessor = (LocalPlayerAccessor) player;
        // Before 1.16 only item use cleared the timer; through 1.21.4 the
        // independently resolved operation also admits saved shift input.
        boolean anotherResetCause = this.isSlowDueToUsingItem() && !player.isPassenger();
        boolean shouldReset = MovementRuntime.find(SprintTriggerHook.class, player)
            .map(behavior -> behavior.shouldReset(player, priorShift, anotherResetCause))
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
        return MovementRuntime.find(WaterDescentHook.class, player)
            .map(behavior -> behavior.mayDescend(player, vanilla))
            .orElse(vanilla);
    }

    @ModifyArg(
        method = "moveTowardsClosestSpace(DD)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/player/LocalPlayer;setDeltaMovement(DDD)V",
            ordinal = 0
        ),
        index = 0
    )
    private double legacyparkourcompat$pushAwayVelocityX(double vanilla) {
        return this.legacyparkourcompat$pushAwayVelocity(vanilla);
    }

    @ModifyArg(
        method = "moveTowardsClosestSpace(DD)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/player/LocalPlayer;setDeltaMovement(DDD)V",
            ordinal = 1
        ),
        index = 2
    )
    private double legacyparkourcompat$pushAwayVelocityZ(double vanilla) {
        return this.legacyparkourcompat$pushAwayVelocity(vanilla);
    }

    private double legacyparkourcompat$pushAwayVelocity(double vanilla) {
        LocalPlayer player = (LocalPlayer) (Object) this;
        return MovementRuntime.find(PushAwayVelocityHook.class, player)
            .map(behavior -> behavior.pushAwayVelocity(player, MovementRuntime.profile(player).target(), vanilla))
            .orElse(vanilla);
    }

    @Inject(method = "suffocatesAt", at = @At("RETURN"), cancellable = true)
    private void legacyparkourcompat$suffocationQuery(BlockPos pos, CallbackInfoReturnable<Boolean> callback) {
        LocalPlayer player = (LocalPlayer) (Object) this;
        MovementRuntime.find(SuffocationProbeHook.class, player).ifPresent(behavior ->
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
        return MovementRuntime.find(SprintWindowHook.class, player)
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
        return MovementRuntime.find(ShallowWaterSprintHook.class, player)
            .map(behavior -> behavior.isInShallowWaterForSprintEligibility(
                player,
                sprintKeyDown,
                player.isSprinting(),
                vanilla
            ))
            .orElse(vanilla);
    }
}
