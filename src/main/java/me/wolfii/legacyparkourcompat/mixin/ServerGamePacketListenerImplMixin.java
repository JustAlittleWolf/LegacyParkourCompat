package me.wolfii.legacyparkourcompat.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * When historical movement is active, the client is the authority for legal
 * player motion. Vanilla still re-simulates the packet delta with
 * {@code Entity#move} and rubberbands, kicks for "flying", or rejects
 * "moved wrongly" / "moved too quickly" when that disagrees.
 */
@Mixin(ServerGamePacketListenerImpl.class)
public abstract class ServerGamePacketListenerImplMixin {
    @Shadow
    public ServerPlayer player;

    @Shadow
    private boolean clientIsFloating;

    @Shadow
    private boolean clientVehicleIsFloating;

    @ModifyReturnValue(method = "shouldCheckPlayerMovement", at = @At("RETURN"))
    private boolean lpc$skipSpeedCheck(boolean vanilla) {
        return MovementRuntime.appliesTo(this.player) ? false : vanilla;
    }

    @ModifyExpressionValue(
        method = "handleMovePlayer",
        at = @At(value = "CONSTANT", args = "doubleValue=0.0625")
    )
    private double lpc$skipMovedWrongly(double original) {
        return MovementRuntime.appliesTo(this.player) ? Double.POSITIVE_INFINITY : original;
    }

    @ModifyExpressionValue(
        method = "handleMoveVehicle",
        at = @At(value = "CONSTANT", args = "doubleValue=0.0625")
    )
    private double lpc$skipVehicleMovedWrongly(double original) {
        return MovementRuntime.appliesTo(this.player) ? Double.POSITIVE_INFINITY : original;
    }

    @ModifyExpressionValue(
        method = "handleMoveVehicle",
        at = @At(value = "CONSTANT", args = "doubleValue=100.0")
    )
    private double lpc$skipVehicleMovedTooQuickly(double original) {
        return MovementRuntime.appliesTo(this.player) ? Double.POSITIVE_INFINITY : original;
    }

    @Inject(method = "isEntityCollidingWithAnythingNew", at = @At("HEAD"), cancellable = true)
    private void lpc$skipNewBlockCollisionReject(
        LevelReader level,
        Entity entity,
        AABB oldAABB,
        double newX,
        double newY,
        double newZ,
        CallbackInfoReturnable<Boolean> cir
    ) {
        if (MovementRuntime.appliesTo(this.player)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "tickPlayer", at = @At("HEAD"))
    private void lpc$skipFloatingKick(CallbackInfo ci) {
        if (!MovementRuntime.appliesTo(this.player)) {
            return;
        }
        this.clientIsFloating = false;
        this.clientVehicleIsFloating = false;
    }
}
