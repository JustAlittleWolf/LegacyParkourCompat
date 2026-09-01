package me.wolfii.legacyparkourcompat.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.AutoJumpBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SprintingBehavior;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin {
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
        return MovementRuntime.find(SprintingBehavior.class, self)
            .map(behavior -> behavior.shouldStopRunSprinting(self, vanilla))
            .orElse(vanilla);
    }
}
