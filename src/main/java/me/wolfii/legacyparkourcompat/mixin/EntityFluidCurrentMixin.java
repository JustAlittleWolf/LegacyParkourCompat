package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.FluidCurrentMinimumBehavior;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "net.minecraft.world.entity.EntityFluidInteraction$Tracker")
abstract class EntityFluidCurrentMixin {
    @Redirect(
        method = "applyCurrentTo",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/Vec3;lengthSqr()D")
    )
    private double legacyparkourcompat$shallowWaterCurrentCutoff(Vec3 current, Entity entity, double scale) {
        return ShallowWaterCurrentContext.isActiveFor(entity)
            ? Double.POSITIVE_INFINITY
            : current.lengthSqr();
    }

    @Redirect(
        method = "applyCurrentTo",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/Vec3;length()D")
    )
    private double legacyparkourcompat$minimumCurrentLength(Vec3 impulse, Entity entity, double scale) {
        return MovementRuntime.find(FluidCurrentMinimumBehavior.class, entity)
            .filter(behavior -> !behavior.applyMinimum(entity))
            .map(behavior -> Double.POSITIVE_INFINITY)
            .orElseGet(impulse::length);
    }
}
