package me.wolfii.legacyparkourcompat.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.AutoJumpBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.ClientInputBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SprintCollisionBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SprintDurationBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SprintingBehavior;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Input;
import net.minecraft.world.phys.Vec2;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin {
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
                (float) self.getAttributeValue(Attributes.SNEAKING_SPEED)
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
