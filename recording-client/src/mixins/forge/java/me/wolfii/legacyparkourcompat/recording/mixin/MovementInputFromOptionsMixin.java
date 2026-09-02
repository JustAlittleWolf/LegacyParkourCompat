package me.wolfii.legacyparkourcompat.recording.mixin;

import me.wolfii.legacyparkourcompat.recording.RecordingController;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.util.MovementInputFromOptions")
public abstract class MovementInputFromOptionsMixin {
    @Inject(method = "updatePlayerMoveState", at = @At("HEAD"), cancellable = true, require = 0)
    private void legacyparkourcompat$skipVanillaWhilePlaying(CallbackInfo callback) {
        if (RecordingController.get().isPlaying()) {
            callback.cancel();
        }
    }
}
