package me.wolfii.legacyparkourcompat.recording.mixin;

import me.wolfii.legacyparkourcompat.recording.RecordingController;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.class_743")
public abstract class YarnKeyboardInputMixin {
    @Inject(method = "method_3129(ZZ)V", at = @At("HEAD"), cancellable = true, require = 0)
    private void legacyparkourcompat$skipVanillaWhilePlaying(CallbackInfo callback) {
        if (RecordingController.get().isPlaying()) {
            callback.cancel();
        }
    }
}
