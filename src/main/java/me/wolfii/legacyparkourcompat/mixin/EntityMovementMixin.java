package me.wolfii.legacyparkourcompat.mixin;


@Mixin(Entity.class)

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.PistonMovementBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SneakEdgeMoverBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SoulSandSpeedBehavior;
import me.wolfii.legacyparkourcompat.mixin.accessor.EntityInvoker;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

abstract class EntityMovementMixin {
    @ModifyArg(
        method = "move(Lnet/minecraft/world/entity/MoverType;Lnet/minecraft/world/phys/Vec3;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/Entity;maybeBackOffFromEdge(Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/entity/MoverType;)Lnet/minecraft/world/phys/Vec3;"
        ),
        index = 1
    )
    private MoverType legacyparkourcompat$edgeGuardMoverType(MoverType moverType) {
        Entity entity = (Entity)(Object)this;
        if (entity instanceof Player player) {
            return MovementRuntime.find(SneakEdgeMoverBehavior.class, player)
                .map(behavior -> behavior.edgeMoverType(player, moverType))
                .orElse(moverType);
        }
        return moverType;
    }

    @Inject(
        method = "limitPistonMovement(Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;",
        at = @At("HEAD"),
        cancellable = true
    )
    private void legacyparkourcompat$limitPistonMovement(Vec3 movement, CallbackInfoReturnable<Vec3> cir) {
        Entity entity = (Entity)(Object)this;
        if (entity instanceof Player player) {
            MovementRuntime.find(PistonMovementBehavior.class, player)
                .map(behavior -> behavior.limitPistonMovement(player, movement))
                .ifPresent(cir::setReturnValue);
        }
    }


    @Inject(
        method = "move(Lnet/minecraft/world/entity/MoverType;Lnet/minecraft/world/phys/Vec3;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/Entity;checkInsideBlocks(Ljava/util/List;Lnet/minecraft/world/entity/InsideBlockEffectApplier$StepBasedCollector;)V",
            shift = At.Shift.AFTER
        )
    )
    private void legacyparkourcompat$applyHistoricalBlockCallbacks(MoverType moverType, Vec3 movement, CallbackInfo ci) {
        Entity entity = (Entity)(Object)this;
        MovementRuntime.find(SoulSandSpeedBehavior.class, entity)
            .ifPresent(behavior -> behavior.afterCollision(entity));
    }

    @Redirect(
        method = "move(Lnet/minecraft/world/entity/MoverType;Lnet/minecraft/world/phys/Vec3;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/Entity;getBlockSpeedFactor()F"
        )
    )
    private float legacyparkourcompat$movementSpeedFactor(Entity entity) {
        float vanilla = ((EntityInvoker)entity).legacyparkourcompat$invokeGetBlockSpeedFactor();
        return MovementRuntime.find(SoulSandSpeedBehavior.class, entity)
            .map(behavior -> behavior.movementSpeedFactor(entity, vanilla))
            .orElse(vanilla);
    }

}
