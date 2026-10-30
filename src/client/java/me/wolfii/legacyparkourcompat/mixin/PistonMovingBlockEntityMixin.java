package me.wolfii.legacyparkourcompat.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.piston.PistonMovingBlockEntity;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec3;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.blockcallback.PistonCallbackScanOriginHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.piston.PistonStairPushHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

/** Adapts the explicit piston callback scan origin and historical stair response distance. */
@Mixin(PistonMovingBlockEntity.class)
abstract class PistonMovingBlockEntityMixin {
    @Unique private static final ThreadLocal<ResponseContext> legacyparkourcompat$pistonResponseContext = new ThreadLocal<>();

    @Unique
    private record ResponseContext(PistonMovingBlockEntity mover, BlockPos pos, float newProgress, Entity recipient) {
        private ResponseContext withRecipient(Entity recipient) {
            return new ResponseContext(this.mover, this.pos, this.newProgress, recipient);
        }
    }

    @WrapMethod(method = "moveCollidedEntities")
    private static void legacyparkourcompat$scopePistonResponse(Level level, BlockPos pos, float newProgress,
                                                                 PistonMovingBlockEntity mover, Operation<Void> original) {
        ResponseContext previous = legacyparkourcompat$pistonResponseContext.get();
        legacyparkourcompat$pistonResponseContext.set(new ResponseContext(mover, pos, newProgress, null));
        try {
            original.call(level, pos, newProgress, mover);
        } finally {
            if (previous == null) {
                legacyparkourcompat$pistonResponseContext.remove();
            } else {
                legacyparkourcompat$pistonResponseContext.set(previous);
            }
        }
    }

    @Redirect(
        method = "moveCollidedEntities",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getPistonPushReaction()Lnet/minecraft/world/level/material/PushReaction;")
    )
    private static PushReaction legacyparkourcompat$capturePistonRecipient(Entity entity) {
        PushReaction reaction = entity.getPistonPushReaction();
        ResponseContext context = legacyparkourcompat$pistonResponseContext.get();
        if (context != null) {
            boolean eligible = reaction != PushReaction.IGNORE
                && entity == Minecraft.getInstance().player
                && MovementRuntime.appliesTo(entity);
            legacyparkourcompat$pistonResponseContext.set(context.withRecipient(eligible ? entity : null));
        }
        return reaction;
    }

    @WrapOperation(
        method = "moveCollidedEntities",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/piston/PistonMovingBlockEntity;moveEntityByPiston(Lnet/minecraft/core/Direction;Lnet/minecraft/world/entity/Entity;DLnet/minecraft/core/Direction;)V")
    )
    private static void legacyparkourcompat$historicalStairPush(
        Direction pushDirection, Entity entity, double distance, Direction movementDirection,
        Operation<Void> original
    ) {
        ResponseContext context = legacyparkourcompat$pistonResponseContext.get();
        if (context != null && context.recipient() == entity) {
            PistonStairPushHook hook = MovementRuntime.find(
                PistonStairPushHook.class, context.mover().getMovedState().getBlock(), entity
            ).orElse(null);
            if (hook != null) {
                distance = hook.requestedDistance(
                    context.mover(), context.pos(), context.newProgress(), entity, movementDirection
                ).orElse(distance);
            }
        }
        original.call(pushDirection, entity, distance, movementDirection);
    }

    @ModifyArg(
        method = "moveEntityByPiston",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;applyEffectsFromBlocks(Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;)V"),
        index = 0
    )
    private static Vec3 legacyparkourcompat$historicalCallbackScanOrigin(
        Vec3 currentOrigin,
        @Local(argsOnly = true) Entity entity
    ) {
        return MovementRuntime.find(PistonCallbackScanOriginHook.class, entity)
            .map(hook -> hook.scanOrigin(entity, currentOrigin))
            .orElse(currentOrigin);
    }
}
