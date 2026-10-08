package me.wolfii.legacyparkourcompat.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.BoatPassengerYawRefreshBehavior;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.boat.Boat;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
abstract class ClientPacketListenerMixin {
    @Shadow @Final protected Minecraft minecraft;

    @Unique private Boat legacyparkourcompat$refreshVehicle;
    @Unique private boolean legacyparkourcompat$hadLocalPlayerPassenger;
    @Unique private boolean legacyparkourcompat$successfullyRemountedLocalPlayer;

    @Inject(method = "handleSetEntityPassengersPacket", at = @At("HEAD"))
    private void legacyparkourcompat$clearPassengerRefresh(
        ClientboundSetPassengersPacket packet,
        CallbackInfo ci
    ) {
        this.legacyparkourcompat$refreshVehicle = null;
        this.legacyparkourcompat$hadLocalPlayerPassenger = false;
        this.legacyparkourcompat$successfullyRemountedLocalPlayer = false;
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
        Operation<Void> original
    ) {
        LocalPlayer player = this.minecraft.player;
        this.legacyparkourcompat$refreshVehicle = null;
        this.legacyparkourcompat$hadLocalPlayerPassenger = false;
        this.legacyparkourcompat$successfullyRemountedLocalPlayer = false;
        if (vehicle instanceof Boat boat && player != null && vehicle.hasIndirectPassenger(player)) {
            this.legacyparkourcompat$refreshVehicle = boat;
            this.legacyparkourcompat$hadLocalPlayerPassenger = true;
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
        Operation<Boolean> original
    ) {
        boolean startedRiding = original.call(passenger, vehicle, force, sendEventAndTriggers);
        if (startedRiding
            && this.legacyparkourcompat$hadLocalPlayerPassenger
            && passenger == this.minecraft.player
            && vehicle == this.legacyparkourcompat$refreshVehicle) {
            this.legacyparkourcompat$successfullyRemountedLocalPlayer = true;
        }
        return startedRiding;
    }

    @Inject(method = "handleSetEntityPassengersPacket", at = @At("TAIL"))
    private void legacyparkourcompat$restorePassengerYaw(
        ClientboundSetPassengersPacket packet,
        CallbackInfo ci
    ) {
        Boat vehicle = this.legacyparkourcompat$refreshVehicle;
        boolean hadLocalPlayerPassenger = this.legacyparkourcompat$hadLocalPlayerPassenger;
        boolean successfullyRemountedLocalPlayer = this.legacyparkourcompat$successfullyRemountedLocalPlayer;
        this.legacyparkourcompat$refreshVehicle = null;
        this.legacyparkourcompat$hadLocalPlayerPassenger = false;
        this.legacyparkourcompat$successfullyRemountedLocalPlayer = false;

        LocalPlayer player = this.minecraft.player;
        if (hadLocalPlayerPassenger && successfullyRemountedLocalPlayer && vehicle != null && player != null) {
            MovementRuntime.find(BoatPassengerYawRefreshBehavior.class, player)
                .ifPresent(behavior -> behavior.onPassengerRefresh(
                    player,
                    vehicle,
                    MovementRuntime.profile(player).target()
                ));
        }
    }
}
