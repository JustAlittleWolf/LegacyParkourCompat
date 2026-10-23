package me.wolfii.legacyparkourcompat.mixin.prediction;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.prediction.BlockPredictionRollbackHook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.prediction.BlockStatePredictionHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BlockStatePredictionHandler.class)
public abstract class BlockStatePredictionHandlerMixin {
    @Definition(id = "lastTeleportSequence", field = "Lnet/minecraft/client/multiplayer/prediction/BlockStatePredictionHandler;lastTeleportSequence:I")
    @Definition(id = "sequence", local = @Local(type = int.class, argsOnly = true, ordinal = 0))
    @Expression("this.lastTeleportSequence < sequence")
    @ModifyExpressionValue(
        method = "endPredictionsUpTo(ILnet/minecraft/client/multiplayer/ClientLevel;)V",
        at = @At("MIXINEXTRAS:EXPRESSION")
    )
    private boolean legacyparkourcompat$allowSavedPlayerPosition(boolean vanilla) {
        var player = Minecraft.getInstance().player;
        return vanilla || MovementRuntime.find(BlockPredictionRollbackHook.class, player)
            .map(BlockPredictionRollbackHook::restoresSavedPlayerPositionAfterTeleport)
            .orElse(false);
    }
}
