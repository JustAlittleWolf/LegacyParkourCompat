package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.boat.BoatRiderOrientationHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.minecraft.world.entity.vehicle.boat.Boat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbstractBoat.class)
abstract class AbstractBoatPassengerOrientationMixin {
    @Redirect(
        method = "positionRider(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/entity/Entity$MoveFunction;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/Entity;is(Lnet/minecraft/tags/TagKey;)Z"
        )
    )
    private boolean legacyparkourcompat$preserveHistoricalRiderOrientation(
        Entity passenger,
        TagKey<EntityType<?>> tag
    ) {
        if (passenger instanceof LocalPlayer rider && (Object) this instanceof Boat boat
            && MovementRuntime.find(BoatRiderOrientationHook.class, rider)
                .map(hook -> hook.preserveRiderOrientation(boat, rider))
                .orElse(false)) {
            // AbstractBoat.positionRider adjusts yaw/head yaw and clamps only when this query is false.
            return true;
        }
        return passenger.is(tag);
    }
}
