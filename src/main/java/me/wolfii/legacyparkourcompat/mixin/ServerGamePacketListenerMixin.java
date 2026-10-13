package me.wolfii.legacyparkourcompat.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.wolfii.legacyparkourcompat.network.ServerMovementPolicy;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** Accepts client movement without replaying historical physics on the server. */
@Mixin(ServerGamePacketListenerImpl.class)
abstract class ServerGamePacketListenerMixin {
    @Unique
    private boolean legacyparkourcompat$acceptHistoricalMovement() {
        return ServerMovementPolicy.relaxPlayerMovement((ServerGamePacketListenerImpl) (Object) this);
    }

    @Inject(method = "shouldCheckPlayerMovement(Z)Z", at = @At("HEAD"), cancellable = true)
    private void legacyparkourcompat$acceptHistoricalSpeed(boolean fallFlying, CallbackInfoReturnable<Boolean> cir) {
        if (this.legacyparkourcompat$acceptHistoricalMovement()) {
            cir.setReturnValue(false);
        }
    }

    @ModifyConstant(method = "handleMovePlayer", constant = @Constant(doubleValue = 0.0625))
    private double legacyparkourcompat$acceptHistoricalCollisionResidual(double vanilla) {
        return this.legacyparkourcompat$acceptHistoricalMovement() ? Double.POSITIVE_INFINITY : vanilla;
    }

    @ModifyExpressionValue(
        method = "handleMovePlayer",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerGamePacketListenerImpl;isEntityCollidingWithAnythingNew(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/AABB;DDD)Z")
    )
    private boolean legacyparkourcompat$acceptHistoricalCollisionShape(boolean vanilla) {
        return !this.legacyparkourcompat$acceptHistoricalMovement() && vanilla;
    }

    @ModifyExpressionValue(
        method = "handleMovePlayer",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerGamePacketListenerImpl;noBlocksAround(Lnet/minecraft/world/entity/Entity;)Z")
    )
    private boolean legacyparkourcompat$acceptHistoricalFloating(boolean vanilla) {
        return !this.legacyparkourcompat$acceptHistoricalMovement() && vanilla;
    }
}
