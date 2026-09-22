package me.wolfii.legacyparkourcompat.recording.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * TAS clients launch without a Microsoft session. From 1.16 onward the title
 * screen then greys out Multiplayer even though the parkour gym is offline.
 */
@Mixin(targets = "net.minecraft.client.Minecraft")
public abstract class MinecraftMultiplayerMixin {
    @Inject(
        method = {
            "allowsMultiplayer",
            "isMultiplayerEnabled",
            "allowsRealms",
            "isRealmsEnabled",
            "allowsChat",
            "isChatEnabled"
        },
        at = @At("HEAD"),
        cancellable = true,
        require = 0
    )
    private void legacyparkourcompat$unlockOfflineMultiplayer(CallbackInfoReturnable<Boolean> callback) {
        callback.setReturnValue(Boolean.TRUE);
    }
}
