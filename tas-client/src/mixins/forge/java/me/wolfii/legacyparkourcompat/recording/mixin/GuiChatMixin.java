package me.wolfii.legacyparkourcompat.recording.mixin;

import me.wolfii.legacyparkourcompat.recording.RecordingController;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.client.gui.GuiChat")
public abstract class GuiChatMixin {
    @Inject(
        method = {
            "sendChatMessage(Ljava/lang/String;)V",
            "func_175275_f(Ljava/lang/String;)V"
        },
        at = @At("HEAD"),
        cancellable = true,
        require = 0
    )
    private void legacyparkourcompat$clientCommand(String message, CallbackInfo callback) {
        if (RecordingController.get().handleCommand(message)) {
            callback.cancel();
        }
    }
}
