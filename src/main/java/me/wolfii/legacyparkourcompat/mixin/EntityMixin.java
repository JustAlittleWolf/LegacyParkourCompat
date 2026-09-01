package me.wolfii.legacyparkourcompat.mixin;

import com.google.common.collect.ImmutableList;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.BlockBounceBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.BlockFallBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.BlockStepBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.CollisionAlgorithm;
import me.wolfii.legacyparkourcompat.mechanic.hook.CollisionAxisOrder;
import me.wolfii.legacyparkourcompat.mechanic.hook.CollisionRestitutionBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.StepHeightBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SupportingBlockBehavior;
import me.wolfii.legacyparkourcompat.mixin.access.EntityInvoker;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Unique
    private boolean lpc$vanillaRestitution;

    @Inject(method = "move", at = @At("HEAD"))
    private void lpc$enterMove(net.minecraft.world.entity.MoverType moverType, Vec3 delta, CallbackInfo ci) {
        MovementRuntime.enter((Entity) (Object) this);
    }

    @Inject(method = "move", at = @At("RETURN"))
    private void lpc$exitMove(net.minecraft.world.entity.MoverType moverType, Vec3 delta, CallbackInfo ci) {
        MovementRuntime.exit();
    }

    @WrapOperation(
            method = "move",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Entity;collide(Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;"
            )
    )
    private Vec3 lpc$collide(Entity instance, Vec3 movement, Operation<Vec3> original) {
        return MovementRuntime.find(CollisionAlgorithm.class, instance)
                .map(algorithm -> algorithm.collide(instance, movement, () -> original.call(instance, movement)))
                .orElseGet(() -> original.call(instance, movement));
    }

    @WrapOperation(
            method = "collideWithShapes",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/core/Direction;axisStepOrder(Lnet/minecraft/world/phys/Vec3;)Lcom/google/common/collect/ImmutableList;"
            )
    )
    private static ImmutableList<Direction.Axis> lpc$axisOrder(
            Vec3 movement,
            Operation<ImmutableList<Direction.Axis>> original
    ) {
        ImmutableList<Direction.Axis> vanilla = original.call(movement);
        return MovementRuntime.find(CollisionAxisOrder.class, MovementRuntime.currentEntity())
                .map(order -> order.axisOrder(movement, vanilla))
                .orElse(vanilla);
    }

    @WrapOperation(
            method = "applyEffectsFromBlocks(Ljava/util/List;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/Block;stepOn(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/entity/Entity;)V"
            )
    )
    private void lpc$stepOn(Block block, Level level, BlockPos pos, BlockState state, Entity entity, Operation<Void> original) {
        MovementRuntime.find(BlockStepBehavior.class, block, entity)
                .ifPresentOrElse(
                        behavior -> behavior.stepOn(level, pos, state, entity, () -> original.call(block, level, pos, state, entity)),
                        () -> original.call(block, level, pos, state, entity)
                );
    }

    @WrapOperation(
            method = "checkFallDamage",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/Block;fallOn(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/Entity;D)V"
            )
    )
    private void lpc$fallOn(
            Block block,
            Level level,
            BlockState state,
            BlockPos pos,
            Entity entity,
            double fallDistance,
            Operation<Void> original
    ) {
        MovementRuntime.find(BlockFallBehavior.class, block, entity)
                .ifPresentOrElse(
                        behavior -> behavior.fallOn(level, state, pos, entity, fallDistance, () -> original.call(block, level, state, pos, entity, fallDistance)),
                        () -> original.call(block, level, state, pos, entity, fallDistance)
                );
    }

    @WrapOperation(
            method = "getBlockBounciness",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/Block;getBounceRestitution()F"
            )
    )
    private float lpc$bounceRestitution(Block block, Operation<Float> original) {
        Entity self = (Entity) (Object) this;
        float vanilla = original.call(block);
        return MovementRuntime.find(BlockBounceBehavior.class, block, self)
                .map(behavior -> behavior.bounceRestitution(vanilla))
                .orElse(vanilla);
    }

    @Inject(method = "restituteMovementAfterCollisions", at = @At("HEAD"), cancellable = true)
    private void lpc$restitute(
            BlockState effectState,
            boolean xCollision,
            boolean zCollision,
            Vec3 movement,
            CallbackInfo ci
    ) {
        if (this.lpc$vanillaRestitution) {
            return;
        }
        Entity self = (Entity) (Object) this;
        MovementRuntime.find(CollisionRestitutionBehavior.class, self).ifPresent(behavior -> {
            behavior.restituteAfterCollisions(self, effectState, xCollision, zCollision, movement, () -> {
                this.lpc$vanillaRestitution = true;
                try {
                    ((EntityInvoker) self).lpc$restituteMovementAfterCollisions(effectState, xCollision, zCollision, movement);
                } finally {
                    this.lpc$vanillaRestitution = false;
                }
            });
            ci.cancel();
        });
    }

    @ModifyReturnValue(method = "getOnPos(F)Lnet/minecraft/core/BlockPos;", at = @At("RETURN"))
    private BlockPos lpc$getOnPos(BlockPos vanilla, float offset) {
        Entity self = (Entity) (Object) this;
        return MovementRuntime.find(SupportingBlockBehavior.class, self)
                .map(behavior -> behavior.getOnPos(self, offset, () -> vanilla))
                .orElse(vanilla);
    }

    @ModifyReturnValue(method = "getBlockPosBelowThatAffectsMyMovement", at = @At("RETURN"))
    private BlockPos lpc$velocityAffectingPos(BlockPos vanilla) {
        Entity self = (Entity) (Object) this;
        return MovementRuntime.find(SupportingBlockBehavior.class, self)
                .map(behavior -> behavior.getBlockPosBelowThatAffectsMyMovement(self, () -> vanilla))
                .orElse(vanilla);
    }

    @ModifyReturnValue(method = "maxUpStep", at = @At("RETURN"))
    private float lpc$stepHeight(float vanilla) {
        Entity self = (Entity) (Object) this;
        return MovementRuntime.find(StepHeightBehavior.class, self)
                .map(behavior -> behavior.stepHeight(self, vanilla))
                .orElse(vanilla);
    }
}
