package me.wolfii.legacyparkourcompat.mixin;


@Mixin(LivingEntity.class)

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.DismountPositionBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.FallFlyingLookAngleBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.MovementChunkLookupBehavior;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

abstract class LivingEntityMixin {
    @Redirect(
        method = "travelInAir",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/Level;hasChunkAt(Lnet/minecraft/core/BlockPos;)Z"
        )
    )
    private boolean legacyParkourCompat$hasMovementChunk(Level level, BlockPos position) {
        LivingEntity entity = (LivingEntity) (Object) this;
        var behavior = MovementRuntime.find(MovementChunkLookupBehavior.class, entity);
        if (behavior.isEmpty() || !(entity instanceof net.minecraft.world.entity.player.Player)) {
            return level.hasChunkAt(position);
        }
        return behavior.orElseThrow().hasChunkAt(entity, level, position, () -> level.hasChunkAt(position));
    }


    @Inject(
        method = "dismountVehicle(Lnet/minecraft/world/entity/Entity;)V",
        at = @At("HEAD"),
        cancellable = true
    )
    private void legacyparkourcompat$historicalDismountPosition(Entity vehicle, CallbackInfo callback) {
        LivingEntity passenger = (LivingEntity)(Object)this;
        if (!(passenger instanceof Player)) {
            return;
        }
        MovementRuntime.find(DismountPositionBehavior.class, passenger)
            .ifPresent(behavior -> {
                if (behavior.dismount(passenger, vehicle)) {
                    callback.cancel();
                }
            });
    }

    @Redirect(
        method = "updateFallFlyingMovement(Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/LivingEntity;getLookAngle()Lnet/minecraft/world/phys/Vec3;"
        )
    )
    private static Vec3 legacyparkourcompat$fallFlyingLookAngle(LivingEntity entity) {
        if (!(entity instanceof Player)) {
            return entity.getLookAngle();
        }
        return MovementRuntime.find(FallFlyingLookAngleBehavior.class, entity)
            .map(behavior -> behavior.lookAngle(entity))
            .orElseGet(entity::getLookAngle);
    }

}
