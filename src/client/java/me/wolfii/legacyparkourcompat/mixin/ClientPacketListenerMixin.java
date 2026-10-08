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
        if (vehicle instanceof Boat boat && player != null && vehicle.hasIndirectPassenger(player)) {
            this.legacyparkourcompat$refreshVehicle = boat;
            this.legacyparkourcompat$hadLocalPlayerPassenger = true;
        }
        original.call(vehicle);
    }

    @Inject(method = "handleSetEntityPassengersPacket", at = @At("TAIL"))
    private void legacyparkourcompat$restorePassengerYaw(
        ClientboundSetPassengersPacket packet,
        CallbackInfo ci
    ) {
        Boat vehicle = this.legacyparkourcompat$refreshVehicle;
        boolean hadLocalPlayerPassenger = this.legacyparkourcompat$hadLocalPlayerPassenger;
        this.legacyparkourcompat$refreshVehicle = null;
        this.legacyparkourcompat$hadLocalPlayerPassenger = false;

        LocalPlayer player = this.minecraft.player;
        if (hadLocalPlayerPassenger && vehicle != null && player != null && vehicle.hasIndirectPassenger(player)) {
            MovementRuntime.find(BoatPassengerYawRefreshBehavior.class, player)
                .ifPresent(behavior -> behavior.onPassengerRefresh(
                    player,
                    vehicle,
                    MovementRuntime.profile(player).target()
                ));
        }
    }
}
