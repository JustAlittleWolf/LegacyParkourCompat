package me.wolfii.legacyparkourcompat.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.vehicle.BoatPassengerYawRefreshHook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.ClientCommonPacketListenerImpl;
import net.minecraft.client.multiplayer.CommonListenerCookie;
import net.minecraft.network.Connection;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.boat.Boat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
abstract class ClientPacketListenerMixin extends ClientCommonPacketListenerImpl {
    protected ClientPacketListenerMixin(Minecraft minecraft, Connection connection, CommonListenerCookie cookie) {
        super(minecraft, connection, cookie);
    }

    @WrapOperation(
        method = "handleSetEntityPassengersPacket",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/Entity;ejectPassengers()V"
        )
    )
    private void legacyparkourcompat$capturePassengerRefresh(
        Entity vehicle,
        Operation<Void> original,
        @Share("refreshVehicle") LocalRef<Boat> refreshVehicle
    ) {
        LocalPlayer player = this.minecraft.player;
        if (vehicle instanceof Boat boat && player != null && vehicle.hasIndirectPassenger(player)) {
            refreshVehicle.set(boat);
        }
        original.call(vehicle);
    }

    @WrapOperation(
        method = "handleSetEntityPassengersPacket",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/Entity;startRiding(Lnet/minecraft/world/entity/Entity;ZZ)Z"
        )
    )
    private boolean legacyparkourcompat$captureSuccessfulDirectRemount(
        Entity passenger,
        Entity vehicle,
        boolean force,
        boolean sendEventAndTriggers,
        Operation<Boolean> original,
        @Share("refreshVehicle") LocalRef<Boat> refreshVehicle,
        @Share("remountedLocalPlayer") LocalBooleanRef remountedLocalPlayer
    ) {
        boolean startedRiding = original.call(passenger, vehicle, force, sendEventAndTriggers);
        if (startedRiding
            && passenger == this.minecraft.player
            && vehicle == refreshVehicle.get()) {
            remountedLocalPlayer.set(true);
        }
        return startedRiding;
    }

    @Inject(method = "handleSetEntityPassengersPacket", at = @At("TAIL"))
    private void legacyparkourcompat$restorePassengerYaw(
        ClientboundSetPassengersPacket packet,
        CallbackInfo ci,
        @Share("refreshVehicle") LocalRef<Boat> refreshVehicle,
        @Share("remountedLocalPlayer") LocalBooleanRef remountedLocalPlayer
    ) {
        Boat vehicle = refreshVehicle.get();
        LocalPlayer player = this.minecraft.player;
        if (remountedLocalPlayer.get() && vehicle != null && player != null) {
            MovementRuntime.find(BoatPassengerYawRefreshHook.class, player)
                .ifPresent(behavior -> behavior.onPassengerRefresh(
                    player,
                    vehicle,
                    MovementRuntime.profile(player).target()
                ));
        }
    }
}
