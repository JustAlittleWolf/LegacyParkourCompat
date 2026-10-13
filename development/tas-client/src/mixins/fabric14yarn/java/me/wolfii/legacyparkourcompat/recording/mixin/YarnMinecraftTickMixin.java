package me.wolfii.legacyparkourcompat.recording.mixin;

import me.wolfii.legacyparkourcompat.recording.RecordingController;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Unimined compiles against Yarn, while the 1.14 Fabric dev launch keeps the
// game in intermediary names. Keep the stable intermediary target/method here.
@Mixin(targets = "net.minecraft.class_310")
public abstract class YarnMinecraftTickMixin {
    @Inject(method = "method_1574()V", at = @At("HEAD"), require = 0)
    private void legacyparkourcompat$automationTick(CallbackInfo callback) {
        RecordingController.get().clientTick();
    }
}
