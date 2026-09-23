package me.wolfii.legacyparkourcompat.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.AutoJumpBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.ClientInputBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.ClientUnstuckBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.ElytraStartClimbBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.ItemUseMovementBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.PassengerCrouchBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SprintCollisionBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SprintDurationBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SprintingBehavior;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Input;
import net.minecraft.world.phys.Vec2;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin {
    @WrapOperation(
        method = "aiStep",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isPassenger()Z", ordinal = 0)
    )
    private boolean lpc$passengerCrouch(LocalPlayer self, Operation<Boolean> original) {
        boolean vanilla = original.call(self);
        if (!MovementRuntime.appliesTo(self)) {
            return vanilla;
        }
        return MovementRuntime.find(PassengerCrouchBehavior.class, self)
            .map(behavior -> behavior.blocksCrouching(self, vanilla))
            .orElse(vanilla);
    }

    @WrapOperation(
        method = "modifyInput",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;itemUseSpeedMultiplier()F")
    )
    private float lpc$itemUseSpeed(LocalPlayer self, Operation<Float> original) {
        float vanilla = original.call(self);
        if (!MovementRuntime.appliesTo(self)) {
            return vanilla;
        }
        return MovementRuntime.find(ItemUseMovementBehavior.class, self)
            .map(behavior -> behavior.speedMultiplier(self, vanilla))
            .orElse(vanilla);
    }

    @ModifyReturnValue(method = "isSlowDueToUsingItem", at = @At("RETURN"))
    private boolean lpc$itemUseSprint(boolean vanilla) {
        LocalPlayer self = (LocalPlayer) (Object) this;
        if (!MovementRuntime.appliesTo(self)) {
            return vanilla;
        }
        return MovementRuntime.find(ItemUseMovementBehavior.class, self)
            .map(behavior -> behavior.slowsSprinting(self, vanilla))
            .orElse(vanilla);
    }

    @WrapOperation(
        method = "aiStep",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;moveTowardsClosestSpace(DD)V")
    )
    private void lpc$unstuck(LocalPlayer self, double x, double z, Operation<Void> original) {
        if (!MovementRuntime.appliesTo(self)) {
            original.call(self, x, z);
            return;
        }
        MovementRuntime.find(ClientUnstuckBehavior.class, self)
            .ifPresentOrElse(
                behavior -> behavior.moveTowardsClosestSpace(self, x, z, () -> original.call(self, x, z)),
                () -> original.call(self, x, z)
            );
    }

    @WrapOperation(
        method = "aiStep",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;onClimbable()Z")
    )
    private boolean lpc$elytraStartOnLadder(LocalPlayer self, Operation<Boolean> original) {
        boolean vanilla = original.call(self);
        if (!MovementRuntime.appliesTo(self)) {
            return vanilla;
        }
        return MovementRuntime.find(ElytraStartClimbBehavior.class, self)
            .map(behavior -> behavior.onClimbable(self, vanilla))
            .orElse(vanilla);
    }

    @Shadow
    protected abstract boolean isControlledCamera();

    @ModifyReturnValue(method = "modifyInput", at = @At("RETURN"))
    private Vec2 lpc$inputBeforeSquareMovement(Vec2 vanilla) {
        LocalPlayer self = (LocalPlayer) (Object) this;
        if (!MovementRuntime.appliesTo(self)) {
            return vanilla;
        }
        Input keys = self.input.keyPresses;
        Vec2 raw = new Vec2(
            (keys.left() ? 1.0F : 0.0F) - (keys.right() ? 1.0F : 0.0F),
            (keys.forward() ? 1.0F : 0.0F) - (keys.backward() ? 1.0F : 0.0F)
        );
        return MovementRuntime.find(ClientInputBehavior.class, self)
            .map(behavior -> behavior.modify(
                raw,
                vanilla,
                self.isUsingItem() && !self.isPassenger(),
                self.isMovingSlowly(),
                (float) self.getAttributeValue(Attributes.SNEAKING_SPEED),
                self.getAbilities().flying && this.isControlledCamera() && keys.shift()
            ))
            .orElse(vanilla);
    }

    @Inject(method = "aiStep", at = @At("HEAD"))
    private void lpc$sprintTimeout(CallbackInfo ci) {
        LocalPlayer self = (LocalPlayer) (Object) this;
        if (!MovementRuntime.appliesTo(self)) {
            return;
        }
        MovementRuntime.find(SprintDurationBehavior.class, self)
            .ifPresent(behavior -> behavior.tick(self));
    }

    @ModifyReturnValue(method = "isAutoJumpEnabled", at = @At("RETURN"))
    private boolean lpc$autoJump(boolean vanilla) {
        LocalPlayer self = (LocalPlayer) (Object) this;
        if (!MovementRuntime.appliesTo(self)) {
            return vanilla;
        }
        return MovementRuntime.find(AutoJumpBehavior.class, self)
            .map(behavior -> behavior.isAutoJumpEnabled(self, vanilla))
            .orElse(vanilla);
    }

    @ModifyReturnValue(method = "canStartSprinting", at = @At("RETURN"))
    private boolean lpc$canStartSprinting(boolean vanilla) {
        LocalPlayer self = (LocalPlayer) (Object) this;
        if (!MovementRuntime.appliesTo(self)) {
            return vanilla;
        }
        return MovementRuntime.find(SprintingBehavior.class, self)
            .map(behavior -> behavior.canStartSprinting(self, vanilla))
            .orElse(vanilla);
    }

    @ModifyReturnValue(method = "shouldStopRunSprinting", at = @At("RETURN"))
    private boolean lpc$shouldStopRunSprinting(boolean vanilla) {
        LocalPlayer self = (LocalPlayer) (Object) this;
        if (!MovementRuntime.appliesTo(self)) {
            return vanilla;
        }
        boolean oldSprintRules = MovementRuntime.find(SprintingBehavior.class, self)
            .map(behavior -> behavior.shouldStopRunSprinting(self, vanilla))
            .orElse(vanilla);
        return MovementRuntime.find(SprintCollisionBehavior.class, self)
            .map(behavior -> behavior.shouldStopRunSprinting(self, oldSprintRules))
            .orElse(oldSprintRules);
    }
}
