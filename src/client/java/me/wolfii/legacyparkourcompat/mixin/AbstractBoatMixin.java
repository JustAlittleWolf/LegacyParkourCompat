package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.BoatSideAccelerationBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.BoatSneakAccelerationBehavior;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(AbstractBoat.class)
abstract class AbstractBoatMixin {
    @Shadow private boolean inputLeft;
    @Shadow private boolean inputRight;
    @Shadow private boolean inputUp;
    @Shadow private boolean inputDown;

    @ModifyConstant(method = "controlBoat", constant = @Constant(floatValue = 0.005F, ordinal = 0))
    private float legacyparkourcompat$sideOnlyAcceleration(float vanilla) {
        LocalPlayer rider = this.legacyparkourcompat$localRider();
        if (rider == null) {
            return vanilla;
        }
        return MovementRuntime.find(BoatSideAccelerationBehavior.class, rider)
            .map(behavior -> behavior.sideOnlyAcceleration(
                (AbstractBoat)(Object)this, MovementRuntime.profile(rider).target(), vanilla,
                this.inputLeft, this.inputRight, this.inputUp, this.inputDown
            ))
            .orElse(vanilla);
    }

    @ModifyConstant(method = "controlBoat", constant = @Constant(floatValue = 0.04F, ordinal = 0))
    private float legacyparkourcompat$forwardAcceleration(float vanilla) {
        return this.legacyparkourcompat$sneakingAcceleration(vanilla);
    }

    @ModifyConstant(method = "controlBoat", constant = @Constant(floatValue = 0.005F, ordinal = 1))
    private float legacyparkourcompat$backwardAcceleration(float vanilla) {
        return this.legacyparkourcompat$sneakingAcceleration(vanilla);
    }

    private float legacyparkourcompat$sneakingAcceleration(float vanilla) {
        LocalPlayer rider = this.legacyparkourcompat$localRider();
        if (rider == null) {
            return vanilla;
        }
        return MovementRuntime.find(BoatSneakAccelerationBehavior.class, rider)
            .map(behavior -> behavior.sneakingAcceleration((AbstractBoat)(Object)this,
                MovementRuntime.profile(rider).target(), vanilla, rider.isShiftKeyDown()))
            .orElse(vanilla);
    }

    private LocalPlayer legacyparkourcompat$localRider() {
        Entity passenger = ((AbstractBoat) (Object) this).getControllingPassenger();
        return passenger instanceof LocalPlayer localPlayer ? localPlayer : null;
    }
}
