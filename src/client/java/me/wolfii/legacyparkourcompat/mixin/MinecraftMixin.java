package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.itemuse.MiningUseGateHook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    // Mojang mappings 26.2 Minecraft#startUseItem consults MultiPlayerGameMode#isDestroying.
    // Keep this native gate unless the resolved historical operation specifically replaces it.
    @Redirect(
        method = "startUseItem",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;isDestroying()Z"
        )
    )
    private boolean legacyparkourcompat$applyMiningUseGate(MultiPlayerGameMode gameMode) {
        boolean isDestroying = gameMode.isDestroying();
        return MovementRuntime.find(MiningUseGateHook.class, Minecraft.getInstance().player)
            .map(change -> isDestroying && change.blocksItemUseWhileDestroying())
            .orElse(isDestroying);
    }
}
