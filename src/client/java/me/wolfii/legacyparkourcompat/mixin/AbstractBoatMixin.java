package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.BoatRiderInputBehavior;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(AbstractBoat.class)
abstract class AbstractBoatMixin {
    @Shadow private boolean inputLeft;
    @Shadow private boolean inputRight;
    @Shadow private boolean inputUp;
    @Shadow private boolean inputDown;

    @ModifyConstant(method = "controlBoat", constant = @Constant(floatValue = 0.005F, ordinal = 0))
    private float legacyParkourCompat$sideOnlyAcceleration(float vanilla) {
        LocalPlayer rider = this.legacyParkourCompat$localRider();
        if (rider == null) {
            return vanilla;
        }
        return MovementRuntime.find(BoatRiderInputBehavior.class, rider)
            .map(behavior -> behavior.sideOnlyAcceleration(vanilla, this.inputLeft, this.inputRight, this.inputUp, this.inputDown))
            .orElse(vanilla);
    }

    @ModifyConstant(method = "controlBoat", constant = @Constant(floatValue = 0.04F, ordinal = 0))
    private float legacyParkourCompat$forwardAcceleration(float vanilla) {
        return this.legacyParkourCompat$sneakingAcceleration(vanilla);
    }

    @ModifyConstant(method = "controlBoat", constant = @Constant(floatValue = 0.005F, ordinal = 1))
    private float legacyParkourCompat$backwardAcceleration(float vanilla) {
        return this.legacyParkourCompat$sneakingAcceleration(vanilla);
    }

    private float legacyParkourCompat$sneakingAcceleration(float vanilla) {
        LocalPlayer rider = this.legacyParkourCompat$localRider();
        if (rider == null) {
            return vanilla;
        }
        return MovementRuntime.find(BoatRiderInputBehavior.class, rider)
            .map(behavior -> behavior.sneakingAcceleration(vanilla, rider.isShiftKeyDown()))
            .orElse(vanilla);
    }

    private LocalPlayer legacyParkourCompat$localRider() {
        Entity passenger = ((AbstractBoat) (Object) this).getControllingPassenger();
        return passenger instanceof LocalPlayer localPlayer ? localPlayer : null;
    }
}
