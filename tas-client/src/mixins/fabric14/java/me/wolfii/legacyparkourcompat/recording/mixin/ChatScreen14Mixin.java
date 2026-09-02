package me.wolfii.legacyparkourcompat.recording.mixin;

import me.wolfii.legacyparkourcompat.recording.RecordingController;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.client.gui.screen.ChatScreen")
public abstract class ChatScreen14Mixin {
    @Inject(
        method = {
            "sendMessage(Ljava/lang/String;Z)V",
            "sendMessage(Ljava/lang/String;)V"
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

    @Inject(
        method = "handleChatInput(Ljava/lang/String;Z)Z",
        at = @At("HEAD"),
        cancellable = true,
        require = 0
    )
    private void legacyparkourcompat$clientCommandReturn(String message, boolean addToHistory, CallbackInfoReturnable<Boolean> callback) {
        if (RecordingController.get().handleCommand(message)) {
            callback.setReturnValue(Boolean.TRUE);
        }
    }
}
