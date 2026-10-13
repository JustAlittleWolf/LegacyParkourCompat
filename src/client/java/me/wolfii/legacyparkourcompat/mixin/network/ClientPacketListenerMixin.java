package me.wolfii.legacyparkourcompat.mixin.network;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.network.PlayerCorrectionPositionHook;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PositionMoveRotation;
import net.minecraft.world.entity.Relative;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Set;

@Mixin(ClientPacketListener.class)
public abstract class ClientPacketListenerMixin {
    @Unique
    private static final ThreadLocal<LocalPlayer> legacyParkourCompat$correctionPlayer = new ThreadLocal<>();

    // 26.2 unobfuscated: ClientPacketListener.handleMovePlayer invokes this helper
    // only for a non-passenger player, with interpolate=false. Other callers remain native.
    @WrapOperation(
        method = "handleMovePlayer",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientPacketListener;setValuesFromPositionPacket(Lnet/minecraft/world/entity/PositionMoveRotation;Ljava/util/Set;Lnet/minecraft/world/entity/Entity;Z)Z")
    )
    private boolean legacyParkourCompat$scopePlayerCorrection(
        PositionMoveRotation change, Set<Relative> relatives, Entity entity, boolean interpolate,
        Operation<Boolean> original
    ) {
        LocalPlayer previous = legacyParkourCompat$correctionPlayer.get();
        if (!interpolate && entity instanceof LocalPlayer player && MovementRuntime.appliesTo(player)) {
            legacyParkourCompat$correctionPlayer.set(player);
        } else {
            legacyParkourCompat$correctionPlayer.remove();
        }
        try {
            return original.call(change, relatives, entity, interpolate);
        } finally {
            if (previous == null) {
                legacyParkourCompat$correctionPlayer.remove();
            } else {
                legacyParkourCompat$correctionPlayer.set(previous);
            }
        }
    }

    // 26.2 unobfuscated: setValuesFromPositionPacket calls Entity.setPos(Vec3)
    // after PositionMoveRotation.calculateAbsolute; all subsequent writes stay native.
    @WrapOperation(
        method = "setValuesFromPositionPacket",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;setPos(Lnet/minecraft/world/phys/Vec3;)V")
    )
    private static void legacyParkourCompat$correctPlayerPosition(
        Entity entity, Vec3 position, Operation<Void> original
    ) {
        LocalPlayer player = legacyParkourCompat$correctionPlayer.get();
        if (player != null && entity == player) {
            Vec3 nativePosition = position;
            position = MovementRuntime.find(PlayerCorrectionPositionHook.class, player)
                .map(hook -> hook.correctionPosition(nativePosition, MovementRuntime.profile(player).target()))
                .orElse(position);
        }
        original.call(entity, position);
    }
}
