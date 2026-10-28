package me.wolfii.legacyparkourcompat.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.PlayerPassengerPlacementHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.PlayerPassengerPlacementHook.Placement;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.PlayerPassengerPlacementHook.RotationEffect;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Entity.class)
abstract class EntityPassengerPlacementMixin {
    @WrapOperation(
        method = "positionRider(Lnet/minecraft/world/entity/Entity;)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;positionRider(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/entity/Entity$MoveFunction;)V")
    )
    private void legacyparkourcompat$placeLocalPassenger(
        Entity vehicle,
        Entity passenger,
        Entity.MoveFunction moveFunction,
        Operation<Void> original
    ) {
        if (!(passenger instanceof LocalPlayer player) || !MovementRuntime.appliesTo(player)) {
            original.call(vehicle, passenger, moveFunction);
            return;
        }

        Placement historical = MovementRuntime.find(PlayerPassengerPlacementHook.class, player)
            .flatMap(hook -> hook.placement(player, vehicle))
            .orElse(null);
        if (historical == null) {
            original.call(vehicle, passenger, moveFunction);
            return;
        }

        float previousBodyYaw = player.getYBodyRot();
        original.call(vehicle, passenger, moveFunction);
        Vec3 position = historical.position();
        moveFunction.accept(passenger, position.x, position.y, position.z);

        if (historical.rotationEffect() == RotationEffect.SUPPRESS_NATIVE_BODY_YAW_COPY) {
            player.setYBodyRot(previousBodyYaw);
        } else if (historical.rotationEffect() == RotationEffect.CLAMP_TO_VEHICLE) {
            clampCamelPassenger(vehicle, player);
        }
    }

    private static void clampCamelPassenger(Entity vehicle, LocalPlayer passenger) {
        float vehicleYaw = vehicle.getYRot();
        passenger.setYBodyRot(vehicleYaw);

        float riderYaw = passenger.getYRot();
        float relativeYaw = Mth.wrapDegrees(riderYaw - vehicleYaw);
        float clampedYaw = Mth.clamp(relativeYaw, -160.0F, 160.0F);
        float correction = clampedYaw - relativeYaw;
        passenger.yRotO += correction;
        float adjustedYaw = riderYaw + correction;
        passenger.setYRot(adjustedYaw);
        passenger.setYHeadRot(adjustedYaw);
    }
}
