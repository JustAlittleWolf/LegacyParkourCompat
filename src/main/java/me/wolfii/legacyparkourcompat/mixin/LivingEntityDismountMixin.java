package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.PortalDismountBehavior;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityDismountMixin {
    @Inject(
        method = "dismountVehicle(Lnet/minecraft/world/entity/Entity;)V",
        at = @At("HEAD"),
        cancellable = true
    )
    private void legacyparkourcompat$portalDismount(Entity vehicle, CallbackInfo ci) {
        LivingEntity passenger = (LivingEntity)(Object)this;
        if (passenger.isRemoved()
            || vehicle.isRemoved()
            || !passenger.level().getBlockState(vehicle.blockPosition()).is(BlockTags.PORTALS)) {
            return;
        }

        MovementRuntime.find(PortalDismountBehavior.class, passenger)
            .ifPresent(behavior -> {
                behavior.dismountFromPortal(passenger, vehicle);
                ci.cancel();
            });
    }
}
