package me.wolfii.legacyparkourcompat.mixin.network;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.PlayerCorrectionContext;
import me.wolfii.legacyparkourcompat.mechanic.hook.network.PlayerCorrectionPitchHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.PositionMoveRotation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PositionMoveRotation.class)
abstract class PositionMoveRotationMixin {
    @WrapOperation(method = "calculateAbsolute", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/util/Mth;clamp(FFF)F"))
    private static float legacyparkourcompat$correctionPitch(float pitch, float min, float max, Operation<Float> original) {
        float vanilla = original.call(pitch, min, max);
        LocalPlayer player = PlayerCorrectionContext.player();
        return MovementRuntime.find(PlayerCorrectionPitchHook.class, player)
            .map(hook -> hook.pitch(MovementRuntime.profile(player).target(), pitch, vanilla))
            .orElse(vanilla);
    }
}
