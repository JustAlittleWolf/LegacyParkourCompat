package me.wolfii.legacyparkourcompat.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.AfterCollisionHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.MovementPositionWriteHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.MovementPositionReconstructionHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.HorizontalCollisionComparisonHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockBounceHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockLandingHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockSpeedFactorHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.StepOnCallbackHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.vehicle.BoatPassengerFluidPushHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.CollisionRestitutionHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.GravityHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.InputAccelerationHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.InsideBlockContactHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.PistonMovementHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.PlayerFallDistanceResetHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.SneakEdgeMoverHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.ShallowLavaCurrentCutoffHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.ShallowWaterCurrentCutoffHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.RetainedWaterDepthHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.SwimmingUpdateHook;
import me.wolfii.legacyparkourcompat.mixin.accessor.EntityInvoker;
import me.wolfii.legacyparkourcompat.mixin.accessor.WaterJumpDepthAccess;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityFluidInteraction;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
abstract class EntityMixin implements WaterJumpDepthAccess {
    @WrapOperation(method = "applyEffectsFromBlocks(Ljava/util/List;)V", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/world/level/block/Block;stepOn(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/entity/Entity;)V"))
    private void legacyparkourcompat$stepOnGate(Block block, Level level, BlockPos pos, BlockState state,
                                             Entity entity, Operation<Void> original) {
        if (!(entity instanceof Player player)
            || MovementRuntime.find(StepOnCallbackHook.class, block, player).isEmpty()) {
            original.call(block, level, pos, state, entity);
        }
    }

    @WrapMethod(method = "moveRelative")
    private void legacyparkourcompat$inputAcceleration(float speed, Vec3 input, Operation<Void> original) {
        Entity entity = (Entity)(Object)this;
        if (entity instanceof Player player
            && MovementRuntime.find(InputAccelerationHook.class, player)
                .map(hook -> hook.apply(player, MovementRuntime.profile(player).target(), speed, input))
                .orElse(false)) {
            return;
        }
        original.call(speed, input);
    }

    @WrapOperation(method = "move", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;equal(DD)Z"))
    private boolean legacyparkourcompat$horizontalCollisionComparison(double requested, double resolved, Operation<Boolean> original) {
        Entity entity = (Entity)(Object)this;
        return MovementRuntime.find(HorizontalCollisionComparisonHook.class, entity)
            .map(hook -> hook.equal(requested, resolved))
            .orElseGet(() -> original.call(requested, resolved));
    }

    @Unique
    private java.util.Optional<MovementPositionReconstructionHook.Position> legacyparkourcompat$movementPosition(Vec3 movement) {
        Entity entity = (Entity)(Object)this;
        if (!(entity instanceof Player player)) {
            return java.util.Optional.empty();
        }
        return MovementRuntime.find(MovementPositionReconstructionHook.class, player)
            .flatMap(hook -> hook.resolve(player, MovementRuntime.profile(player).target(), movement));
    }

    @Unique
    private boolean legacyparkourcompat$applyMovementPosition(Entity entity, Vec3 movement) {
        var historical = this.legacyparkourcompat$movementPosition(movement);
        if (historical.isEmpty()) {
            return false;
        }
        var resolved = historical.get();
        entity.setBoundingBox(resolved.box());
        entity.setPosRaw(resolved.position().x, resolved.position().y, resolved.position().z);
        return true;
    }

    @ModifyArg(method = "move", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/world/entity/Entity$Movement;<init>(Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;)V"), index = 1)
    private Vec3 legacyparkourcompat$movementContactEnd(Vec3 position, @Local(name = "movement") Vec3 movement) {
        return this.legacyparkourcompat$movementPosition(movement)
            .map(MovementPositionReconstructionHook.Position::position).orElse(position);
    }

    @WrapOperation(method = "move", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/world/entity/Entity;setPos(DDD)V"))
    private void legacyparkourcompat$noPhysicsMovementPosition(Entity entity, double x, double y, double z,
        Operation<Void> original, @Local(argsOnly = true) Vec3 movement) {
        if (!this.legacyparkourcompat$applyMovementPosition(entity, movement)) {
            original.call(entity, x, y, z);
        }
    }

    @Unique
    private boolean legacyparkourcompat$writeMovementPosition(double movementLength) {
        Entity entity = (Entity)(Object)this;
        return MovementRuntime.find(MovementPositionWriteHook.class, entity)
            .map(hook -> hook.shouldWrite(MovementRuntime.profile(entity).target(), movementLength))
            .orElse(true);
    }

    @WrapOperation(method = "move", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/world/entity/Entity;addMovementThisTick(Lnet/minecraft/world/entity/Entity$Movement;)V"))
    private void legacyparkourcompat$movementContactSegment(Entity entity, @Coerce Object movement,
        Operation<Void> original, @Local(name = "movementLength") double movementLength) {
        if (this.legacyparkourcompat$writeMovementPosition(movementLength)) {
            original.call(entity, movement);
        }
    }

    @WrapOperation(method = "move", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/world/entity/Entity;setPos(Lnet/minecraft/world/phys/Vec3;)V"))
    private void legacyparkourcompat$movementPosition(Entity entity, Vec3 position,
        Operation<Void> original, @Local(name = "movementLength") double movementLength,
        @Local(name = "movement") Vec3 movement) {
        if (this.legacyparkourcompat$writeMovementPosition(movementLength)
            && !this.legacyparkourcompat$applyMovementPosition(entity, movement)) {
            original.call(entity, position);
        }
    }

    @Unique
    private double legacyparkourcompat$retainedWaterDepth;

    @Override
    @Unique
    public double legacyparkourcompat$getRetainedWaterDepth() {
        return this.legacyparkourcompat$retainedWaterDepth;
    }

    @Override
    @Unique
    public void legacyparkourcompat$setRetainedWaterDepth(double depth) {
        this.legacyparkourcompat$retainedWaterDepth = depth;
    }

    @WrapMethod(method = "updateFluidInteraction()Z")
    private boolean legacyparkourcompat$retainWaterDepthAfterRefresh(Operation<Boolean> original) {
        Entity entity = (Entity)(Object)this;
        AABB rawBox = entity.getBoundingBox();
        boolean result = original.call();
        MovementRuntime.find(RetainedWaterDepthHook.class, entity).ifPresent(behavior ->
            this.legacyparkourcompat$setRetainedWaterDepth(behavior.afterWaterRefresh(
                MovementRuntime.profile(entity).target(), entity.isPassenger(), rawBox,
                this.legacyparkourcompat$getRetainedWaterDepth(), entity.getFluidHeight(FluidTags.WATER))));
        return result;
    }

    @Inject(method = "updateSwimming()V", at = @At("HEAD"), cancellable = true)
    private void legacyparkourcompat$historicalSwimmingUpdate(CallbackInfo callback) {
        Entity entity = (Entity) (Object) this;
        if (entity instanceof Player player) {
            MovementRuntime.find(SwimmingUpdateHook.class, player)
                .filter(behavior -> behavior.supports(MovementRuntime.profile(player).target()))
                .ifPresent(behavior -> {
                    player.setSwimming(behavior.isSwimmingAfterUpdate(player, MovementRuntime.profile(player).target()));
                    callback.cancel();
                });
        }
    }

    @Redirect(
        method = "move(Lnet/minecraft/world/entity/MoverType;Lnet/minecraft/world/phys/Vec3;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/Entity;restituteMovementAfterCollisions(Lnet/minecraft/world/level/block/state/BlockState;ZZLnet/minecraft/world/phys/Vec3;)V"
        ),
        require = 1
    )
    private void legacyparkourcompat$dispatchCollisionRestitution(
        Entity entity,
        BlockState effectState,
        boolean xCollision,
        boolean zCollision,
        Vec3 movement,
        MoverType moverType,
        Vec3 requestedMovement
    ) {
        VanillaCall vanilla = () -> ((EntityInvoker)(Object)entity)
            .legacyparkourcompat$invokeRestituteMovementAfterCollisions(effectState, xCollision, zCollision, movement);
        var blockLanding = MovementRuntime.find(BlockLandingHook.class, effectState.getBlock(), entity);
        MovementRuntime.find(CollisionRestitutionHook.class, entity)
            .ifPresentOrElse(
                behavior -> behavior.restituteAfterCollisions(
                    entity, effectState, xCollision, zCollision, requestedMovement.y != movement.y, movement, blockLanding,
                    MovementRuntime.find(BlockBounceHook.class, effectState.getBlock(), entity), vanilla
                ),
                vanilla::run
            );
    }

    @ModifyReturnValue(method = "getGravity", at = @At("RETURN"))
    private double legacyparkourcompat$historicalPlayerGravity(double vanilla) {
        Entity entity = (Entity)(Object)this;
        return MovementRuntime.find(GravityHook.class, entity)
            .map(behavior -> behavior.gravity(entity, vanilla))
            .orElse(vanilla);
    }

    @Inject(
        method = "getBlockBounciness(Lnet/minecraft/world/level/block/Block;)D",
        at = @At("RETURN"),
        cancellable = true
    )
    private void legacyparkourcompat$blockBounce(Block block, CallbackInfoReturnable<Double> callback) {
        Entity entity = (Entity)(Object)this;
        MovementRuntime.find(BlockBounceHook.class, block, entity)
            .ifPresent(behavior -> callback.setReturnValue(
                (double)behavior.bounceRestitution(callback.getReturnValue().floatValue(), entity)
            ));
    }

    @Redirect(
        method = "checkInsideBlocks(Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/entity/InsideBlockEffectApplier$StepBasedCollector;Lit/unimi/dsi/fastutil/longs/LongSet;I)I",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/AABB;deflate(D)Lnet/minecraft/world/phys/AABB;")
    )
    private AABB legacyparkourcompat$insideBlockInset(AABB box, double vanilla) {
        if ((Object) this instanceof Player player) {
            return MovementRuntime.find(InsideBlockContactHook.class, player)
                .map(behavior -> box.deflate(behavior.inset(player, vanilla)))
                .orElseGet(() -> box.deflate(vanilla));
        }
        return box.deflate(vanilla);
    }

    @Redirect(
        method = "updateFluidInteraction()Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/EntityFluidInteraction;applyCurrentTo(Lnet/minecraft/tags/TagKey;Lnet/minecraft/world/entity/Entity;D)V",
            ordinal = 0
        )
    )
    private void legacyparkourcompat$boatPassengerWaterCurrent(
        EntityFluidInteraction interaction,
        TagKey<Fluid> fluid,
        Entity entity,
        double scale
    ) {
        if (entity instanceof Player player
            && MovementRuntime.find(BoatPassengerFluidPushHook.class, player)
                .map(behavior -> behavior.skipWaterCurrent(player, player.getVehicle(), MovementRuntime.profile(player).target()))
                .orElse(false)) {
            return;
        }
        if (fluid.equals(FluidTags.WATER)
            && entity instanceof LocalPlayer player
            && !player.isPassenger()
            && player.isPushedByFluid()
            && MovementRuntime.find(ShallowWaterCurrentCutoffHook.class, player)
                .map(behavior -> behavior.bypassWeakCurrentCutoff(interaction.getFluidHeight(fluid)))
                .orElse(false)) {
            FluidCurrentCutoffContext.run(entity, () -> interaction.applyCurrentTo(fluid, entity, scale));
            return;
        }
        interaction.applyCurrentTo(fluid, entity, scale);
    }

    @Redirect(
        method = "updateFluidInteraction()Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/EntityFluidInteraction;applyCurrentTo(Lnet/minecraft/tags/TagKey;Lnet/minecraft/world/entity/Entity;D)V",
            ordinal = 1
        ),
        require = 1
    )
    private void legacyparkourcompat$shallowLavaCurrentCutoff(
        EntityFluidInteraction interaction,
        TagKey<Fluid> fluid,
        Entity entity,
        double scale
    ) {
        if (fluid.equals(FluidTags.LAVA) && PostMoveFluidRefreshContext.suppressesLavaCurrent(entity)) {
            return;
        }
        if (fluid.equals(FluidTags.LAVA)
            && entity instanceof Player player
            && !player.isPassenger()
            && MovementRuntime.find(ShallowLavaCurrentCutoffHook.class, player)
                .map(behavior -> behavior.bypassWeakCurrentCutoff(interaction.getFluidHeight(fluid)))
                .orElse(false)) {
            FluidCurrentCutoffContext.run(entity, () -> interaction.applyCurrentTo(fluid, entity, scale));
            return;
        }
        interaction.applyCurrentTo(fluid, entity, scale);
    }

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
            return MovementRuntime.find(SneakEdgeMoverHook.class, player)
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
            MovementRuntime.find(PistonMovementHook.class, player)
                .map(behavior -> behavior.limitPistonMovement(player, movement))
                .ifPresent(cir::setReturnValue);
        }
    }

    @Inject(
        method = "move(Lnet/minecraft/world/entity/MoverType;Lnet/minecraft/world/phys/Vec3;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/Entity;getBlockSpeedFactor()F",
            shift = At.Shift.BEFORE
        )
    )
    private void legacyparkourcompat$applyHistoricalBlockCallbacks(MoverType moverType, Vec3 movement, CallbackInfo ci) {
        Entity entity = (Entity)(Object)this;
        if (entity instanceof Player player && MovementRuntime.appliesTo(player)) {
            BlockPos onPos = new BlockPos(
                Mth.floor(entity.getX()),
                Mth.floor(entity.getY() - 0.2F),
                Mth.floor(entity.getZ())
            );
            BlockState onState = entity.level().getBlockState(onPos);
            if (onState.isAir()) {
                BlockPos below = onPos.below();
                BlockState belowState = entity.level().getBlockState(below);
                Block belowBlock = belowState.getBlock();
                if (belowState.is(BlockTags.FENCES) || belowState.is(BlockTags.WALLS)
                    || belowBlock instanceof FenceGateBlock) {
                    onPos = below;
                    onState = belowState;
                }
            }
            BlockPos stepOnPosition = onPos;
            BlockState stepOnState = onState;
            MovementRuntime.find(StepOnCallbackHook.class, stepOnState.getBlock(), player)
                .ifPresent(hook -> hook.invokeBeforeInsideBlockCallbacks(
                    player, entity.level(), stepOnPosition, stepOnState
                ));
        }
        MovementRuntime.find(AfterCollisionHook.class, entity)
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
        return MovementRuntime.find(BlockSpeedFactorHook.class, entity)
            .map(behavior -> behavior.movementSpeedFactor(entity, vanilla))
            .orElse(vanilla);
    }

    @Redirect(
        method = "move(Lnet/minecraft/world/entity/MoverType;Lnet/minecraft/world/phys/Vec3;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/Entity;resetFallDistance()V"
        ),
        require = 1
    )
    private void legacyparkourcompat$movementFallDistanceReset(Entity entity) {
        if (entity instanceof Player player
            && MovementRuntime.find(PlayerFallDistanceResetHook.class, player)
                .map(behavior -> behavior.suppressMovementFallDistanceReset(MovementRuntime.profile(player).target()))
                .orElse(false)) {
            return;
        }
        entity.resetFallDistance();
    }
}
