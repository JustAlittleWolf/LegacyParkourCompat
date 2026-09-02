package me.wolfii.legacyparkourcompat.recording.mixin;

import me.wolfii.legacyparkourcompat.recording.RecordingController;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.client.entity.EntityPlayerSP")
public abstract class EntityPlayerSPMixin {
    @Inject(method = "onUpdate", at = @At("HEAD"), require = 0)
    private void legacyparkourcompat$beforeTick(CallbackInfo callback) {
        RecordingController.get().beforePlayerTick();
    }

    @Inject(method = "onUpdate", at = @At("RETURN"), require = 0)
    private void legacyparkourcompat$afterTick(CallbackInfo callback) {
        RecordingController.get().afterPlayerTick();
    }

    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true, require = 0)
    private void legacyparkourcompat$clientCommand(String message, CallbackInfo callback) {
        if (RecordingController.get().handleCommand(message)) {
            callback.cancel();
        }
    }
}
