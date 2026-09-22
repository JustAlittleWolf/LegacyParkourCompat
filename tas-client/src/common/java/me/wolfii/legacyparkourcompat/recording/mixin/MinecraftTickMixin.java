package me.wolfii.legacyparkourcompat.recording.mixin;

import me.wolfii.legacyparkourcompat.recording.RecordingController;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Drives launch-time automation before a local player exists.  A
 * LocalPlayer/EntityPlayerSP tick alone cannot auto-connect from the title
 * screen, so this small, version-tolerant hook owns only the controller tick.
 */
@Mixin(targets = "net.minecraft.client.Minecraft")
public abstract class MinecraftTickMixin {
    @Inject(method = {"tick", "runTick"}, at = @At("HEAD"), require = 0)
    private void legacyparkourcompat$automationTick(CallbackInfo callback) {
        RecordingController.get().clientTick();
    }
}
