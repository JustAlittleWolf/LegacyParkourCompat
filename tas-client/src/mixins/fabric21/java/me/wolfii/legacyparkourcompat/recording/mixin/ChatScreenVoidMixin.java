package me.wolfii.legacyparkourcompat.recording.mixin;

import me.wolfii.legacyparkourcompat.recording.RecordingController;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 1.20.5 changed {@code ChatScreen.handleChatInput} from boolean to void.
 * Mixin treats a same-args inject as a match even when the return type differs,
 * so this lives apart from the boolean injector.
 */
@Mixin(targets = "net.minecraft.client.gui.screens.ChatScreen")
public abstract class ChatScreenVoidMixin {
    @Inject(method = "handleChatInput(Ljava/lang/String;Z)V", at = @At("HEAD"), cancellable = true, require = 0)
    private void legacyparkourcompat$clientCommand(String message, boolean addToHistory, CallbackInfo callback) {
        if (RecordingController.get().handleCommand(message)) {
            callback.cancel();
        }
    }
}
