package me.wolfii.legacyparkourcompat.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.water.WaterContext;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.AfterCollisionHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.NoPhysicsCollisionResetHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.MovementPositionWriteHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.HorizontalCollisionComparisonHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockBounceHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockLandingHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockSpeedFactorHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.SupportingBlockPositionHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.EntityBlockContactTraversalHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.GroundedBlockStepCallbackHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.PistonStuckSpeedHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.soulspeed.SoulSpeedBlockHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.soulspeed.SoulSpeedFrictionHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.StepOnCallbackHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.vehicle.BoatPassengerFluidPushHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.CollisionRestitutionHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.GravityHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.InputAccelerationHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.movement.HeadingVelocityHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.InputVectorHook;
import me.wolfii.legacyparkourcompat.mechanic.OrdinaryPlayerContext;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.InsideBlockContactHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.PlayerFallDistanceResetHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.movement.FallDistanceResetTraceHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.SneakEdgeMoverHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.ShallowLavaCurrentCutoffHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.ShallowWaterCurrentCutoffHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.RetainedWaterDepthHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.push.PlayerPushNormalizationHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.swimming.SwimmingEntryFeetWaterHook;
import me.wolfii.legacyparkourcompat.mixin.accessor.EntityInvoker;
import me.wolfii.legacyparkourcompat.movement.access.WaterJumpDepthAccess;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityFluidInteraction;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.client.player.KeyboardInput;
import net.minecraft.world.entity.player.Input;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(Entity.class)
abstract class EntityMixin implements WaterJumpDepthAccess {
    @Unique
    private Vec3 legacyparkourcompat$pistonStuckSpeedMultiplier;

    @ModifyArgs(
        method = "push(Lnet/minecraft/world/entity/Entity;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/Entity;push(DDD)V",
            ordinal = 0
        )
    )
    private void legacyparkourcompat$normalizeFirstPushRecipient(Args arguments, Entity other) {
        this.legacyparkourcompat$normalizePushRecipient((Entity) (Object) this, other, arguments);
    }

    @ModifyArgs(
        method = "push(Lnet/minecraft/world/entity/Entity;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/Entity;push(DDD)V",
            ordinal = 1
        )
    )
    private void legacyparkourcompat$normalizeSecondPushRecipient(Args arguments, Entity other) {
        this.legacyparkourcompat$normalizePushRecipient(other, (Entity) (Object) this, arguments);
    }

    @Unique
    private void legacyparkourcompat$normalizePushRecipient(Entity receiver, Entity other, Args arguments) {
        if (!(receiver instanceof Player player)) {
            return;
        }
        MovementRuntime.find(PlayerPushNormalizationHook.class, player).ifPresent(hook -> {
            Vec3 incoming = hook.normalize(receiver, other);
            arguments.set(0, incoming.x);
            arguments.set(2, incoming.z);
        });
    }

    @WrapOperation(method = "applyEffectsFromBlocks(Ljava/util/List;)V", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/world/level/block/Block;stepOn(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/entity/Entity;)V"))
    private void legacyparkourcompat$stepOnGate(Block block, Level level, BlockPos pos, BlockState state,
                                             Entity entity, Operation<Void> original) {
        boolean historicalTiming = entity instanceof Player player
            && MovementRuntime.find(GroundedBlockStepCallbackHook.class, player)
                .map(hook -> hook.suppressPostTravel(entity))
                .orElse(false);
        boolean historicalVariant = entity instanceof Player player
            && MovementRuntime.find(StepOnCallbackHook.class, block, player).isPresent();
        if (!historicalTiming && !historicalVariant) {
            original.call(block, level, pos, state, entity);
        }
    }

    @WrapOperation(method = "applyEffectsFromBlocks(Ljava/util/List;)V", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/world/entity/Entity;checkInsideBlocks(Ljava/util/List;Lnet/minecraft/world/entity/InsideBlockEffectApplier$StepBasedCollector;)V"))
    private void legacyparkourcompat$suppressPostTravelContactTraversal(
        Entity entity,
        java.util.List<?> movements,
        InsideBlockEffectApplier.StepBasedCollector effectCollector,
        Operation<Void> original
    ) {
        boolean historicalTraversal = entity instanceof Player player
            && MovementRuntime.find(EntityBlockContactTraversalHook.class, player)
                .map(hook -> hook.suppressPostTravel(entity))
                .orElse(false);
        if (!historicalTraversal) {
            original.call(entity, movements, effectCollector);
        }
    }

    @WrapMethod(method = "moveRelative")
    private void legacyparkourcompat$inputAcceleration(float speed, Vec3 input, Operation<Void> original) {
        Entity entity = (Entity)(Object)this;
        if (entity instanceof Player player) {
            if (MovementRuntime.find(HeadingVelocityHook.class, player)
                .map(hook -> hook.apply(player, speed, input))
                .orElse(false)) {
                return;
            }
            if (MovementRuntime.find(InputAccelerationHook.class, player)
                .map(hook -> hook.apply(player, MovementRuntime.profile(player).target(), speed, input))
                .orElse(false)) {
                return;
            }
        }
        original.call(speed, input);
    }

    @WrapOperation(method = "moveRelative(FLnet/minecraft/world/phys/Vec3;)V", at = @At(
        value = "INVOKE",
        target = "Lnet/minecraft/world/entity/Entity;getInputVector(Lnet/minecraft/world/phys/Vec3;FF)Lnet/minecraft/world/phys/Vec3;"
    ))
    private Vec3 legacyparkourcompat$inputVector(Vec3 input, float speed, float yaw, Operation<Vec3> original) {
        Entity self = (Entity) (Object) this;
        if (!(self instanceof LocalPlayer player)
            || !MovementRuntime.appliesTo(player)
            || !(player.input instanceof KeyboardInput)
            || !OrdinaryPlayerContext.matches(player)) {
            return original.call(input, speed, yaw);
        }
        Input keys = player.input.keyPresses;
        if (!(keys.forward() ^ keys.backward()) || !(keys.left() ^ keys.right())) {
            return original.call(input, speed, yaw);
        }
        return MovementRuntime.find(InputVectorHook.class, self)
            .map(hook -> hook.inputVector(input, speed, yaw, () -> original.call(input, speed, yaw)))
            .orElseGet(() -> original.call(input, speed, yaw));
    }

    @WrapOperation(method = "move", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/world/entity/Entity;collide(Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;"))
    private Vec3 legacyparkourcompat$captureCollisionMovement(Entity entity, Vec3 movement, Operation<Vec3> original,
        @Share("requestedMovement") LocalRef<Vec3> requested,
        @Share("resolvedMovement") LocalRef<Vec3> resolved,
        @Share("resolvedCollisionBox") LocalRef<AABB> resolvedBox) {
        requested.set(movement);
        Vec3 result = CollisionAxisOrderContext.withEntity(entity, () -> {
            Vec3 clipped = original.call(entity, movement);
            resolvedBox.set(CollisionAxisOrderContext.resolvedBox());
            return clipped;
        });
        resolved.set(result);
        return result;
    }

    @Inject(method = "move", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/world/entity/Entity;canSimulateMovement()Z"))
    private void legacyparkourcompat$horizontalVelocityBeforeRestitution(CallbackInfo ci,
        @Share("requestedMovement") LocalRef<Vec3> requested,
        @Share("resolvedMovement") LocalRef<Vec3> resolved,
        @Share("historicalHorizontalVelocity") LocalBooleanRef handled) {
        Entity entity = (Entity) (Object) this;
        if (MovementRuntime.appliesTo(entity)) {
            if (requested.get() == null || resolved.get() == null) {
                throw new IllegalStateException("Collision velocity dispatch has no movement vectors");
            }
            handled.set(MovementRuntime.clearHorizontalCollisionVelocity(entity, requested.get(), resolved.get()));
        }
    }

    @WrapOperation(method = "move", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;equal(DD)Z"))
    private boolean legacyparkourcompat$horizontalCollisionComparison(double requested, double resolved, Operation<Boolean> original) {
        Entity entity = (Entity)(Object)this;
        return MovementRuntime.find(HorizontalCollisionComparisonHook.class, entity)
            .map(hook -> hook.equal(requested, resolved))
            .orElseGet(() -> original.call(requested, resolved));
    }

    // The first Vec3.add builds the fall-reset trace endpoint; the second is the
    // actual endpoint shared by the movement record and the position write.
    @ModifyExpressionValue(method = "move", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/world/phys/Vec3;add(Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;", ordinal = 1))
    private Vec3 legacyparkourcompat$movementContactEnd(Vec3 position, @Local(name = "movement") Vec3 movement,
        @Share("resolvedCollisionBox") LocalRef<AABB> resolvedBox) {
        return MovementRuntime.movementEndpoint((Entity)(Object)this, movement, position, resolvedBox.get());
    }

    @WrapOperation(method = "move", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/world/entity/Entity;setPos(DDD)V"))
    private void legacyparkourcompat$noPhysicsMovementPosition(Entity entity, double x, double y, double z,
        Operation<Void> original, @Local(argsOnly = true) Vec3 movement) {
        MovementRuntime.writeMovementPosition(entity, movement, () -> original.call(entity, x, y, z));
    }

    // In Entity.move, the first write to each field belongs exclusively to the
    // noPhysics branch. Later writes in the colliding branch remain untouched.
    @Redirect(method = "move(Lnet/minecraft/world/entity/MoverType;Lnet/minecraft/world/phys/Vec3;)V",
        at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/Entity;horizontalCollision:Z",
            opcode = Opcodes.PUTFIELD, ordinal = 0))
    private void legacyparkourcompat$resetHorizontalCollision(Entity entity, boolean value) {
        if (legacyparkourcompat$clearCollisionFlags(entity)) {
            entity.horizontalCollision = value;
        }
    }

    @Redirect(method = "move(Lnet/minecraft/world/entity/MoverType;Lnet/minecraft/world/phys/Vec3;)V",
        at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/Entity;verticalCollision:Z",
            opcode = Opcodes.PUTFIELD, ordinal = 0))
    private void legacyparkourcompat$resetVerticalCollision(Entity entity, boolean value) {
        if (legacyparkourcompat$clearCollisionFlags(entity)) {
            entity.verticalCollision = value;
        }
    }

    @Redirect(method = "move(Lnet/minecraft/world/entity/MoverType;Lnet/minecraft/world/phys/Vec3;)V",
        at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/Entity;verticalCollisionBelow:Z",
            opcode = Opcodes.PUTFIELD, ordinal = 0))
    private void legacyparkourcompat$resetVerticalCollisionBelow(Entity entity, boolean value) {
        if (legacyparkourcompat$clearCollisionFlags(entity)) {
            entity.verticalCollisionBelow = value;
        }
    }

    @Redirect(method = "move(Lnet/minecraft/world/entity/MoverType;Lnet/minecraft/world/phys/Vec3;)V",
        at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/Entity;minorHorizontalCollision:Z",
            opcode = Opcodes.PUTFIELD, ordinal = 0))
    private void legacyparkourcompat$resetMinorHorizontalCollision(Entity entity, boolean value) {
        if (legacyparkourcompat$clearCollisionFlags(entity)) {
            entity.minorHorizontalCollision = value;
        }
    }

    @Unique
    private static boolean legacyparkourcompat$clearCollisionFlags(Entity entity) {
        return MovementRuntime.find(NoPhysicsCollisionResetHook.class, entity)
            .map(NoPhysicsCollisionResetHook::clearCollisionFlags)
            .orElse(true);
    }

    @Unique
    private boolean legacyparkourcompat$writeMovementPosition(double movementLength) {
        Entity entity = (Entity)(Object)this;
        return MovementRuntime.find(MovementPositionWriteHook.class, entity)
            .map(hook -> hook.shouldWrite(MovementRuntime.profile(entity).target(), movementLength))
            .orElse(true);
    }

    // This local is only used by the outer write gate and the >= 1.0 fall-ray
    // gate. Raise only tiny values above the former threshold, preserving the
    // latter gate and every actual movement component.
    @ModifyExpressionValue(method = "move", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/world/phys/Vec3;lengthSqr()D", ordinal = 1))
    private double legacyparkourcompat$movementWriteAdmission(double length) {
        boolean alwaysWrite = MovementRuntime.find(MovementPositionWriteHook.class, (Entity)(Object)this)
            .map(MovementPositionWriteHook::alwaysEnterWriteBranch).orElse(false);
        return alwaysWrite && length <= 1.0E-7 ? Math.nextUp(1.0E-7) : length;
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
        @Local(name = "movement") Vec3 movement,
        @Share("resolvedCollisionBox") LocalRef<AABB> resolvedBox) {
        if (this.legacyparkourcompat$writeMovementPosition(movementLength)) {
            MovementRuntime.writeMovementPosition(entity, movement, resolvedBox.get(), () -> original.call(entity, position));
        }
    }

    @WrapOperation(method = "refreshDimensions", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/world/entity/Entity;reapplyPosition()V"))
    private void legacyparkourcompat$poseBox(Entity entity, Operation<Void> original,
        @Local(ordinal = 0) EntityDimensions previous, @Local(ordinal = 1) EntityDimensions next) {
        MovementRuntime.refreshPoseBox(entity, previous, next, () -> original.call(entity));
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

    @WrapOperation(method = "updateSwimming()V", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/world/level/material/FluidState;is(Lnet/minecraft/tags/TagKey;)Z"))
    private boolean legacyparkourcompat$swimmingEntryFeetWater(FluidState fluid, TagKey<Fluid> tag,
                                                              Operation<Boolean> original) {
        Entity entity = (Entity) (Object) this;
        return MovementRuntime.find(SwimmingEntryFeetWaterHook.class, entity)
            .filter(hook -> hook.supports(MovementRuntime.profile(entity).target()))
            .map(hook -> hook.test(() -> original.call(fluid, tag)))
            .orElseGet(() -> original.call(fluid, tag));
    }

    @ModifyArg(
        method = "move(Lnet/minecraft/world/entity/MoverType;Lnet/minecraft/world/phys/Vec3;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/phys/Vec3;add(Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;",
            ordinal = 0
        ),
        index = 0
    )
    private Vec3 legacyparkourcompat$fallResetTraceDisplacement(
        Vec3 nativeTraceDisplacement,
        @Local(name = "movement") Vec3 resolvedDisplacement
    ) {
        // 26.2 Entity.move: the first Vec3.add builds checkTo, before the position write.
        // Capture the collide result (movement), not the requested move argument (delta).
        Entity entity = (Entity)(Object)this;
        return MovementRuntime.find(FallDistanceResetTraceHook.class, entity)
            .map(hook -> hook.fallResetTraceDisplacement(resolvedDisplacement, nativeTraceDisplacement))
            .orElse(nativeTraceDisplacement);
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
        Vec3 requestedMovement,
        @Share("historicalHorizontalVelocity") LocalBooleanRef handled
    ) {
        boolean restituteX = !handled.get() && xCollision;
        boolean restituteZ = !handled.get() && zCollision;
        VanillaCall vanilla = () -> ((EntityInvoker)(Object)entity)
            .legacyparkourcompat$invokeRestituteMovementAfterCollisions(effectState, restituteX, restituteZ, movement);
        var blockLanding = MovementRuntime.find(BlockLandingHook.class, effectState.getBlock(), entity);
        MovementRuntime.find(CollisionRestitutionHook.class, entity)
            .ifPresentOrElse(
                behavior -> behavior.restituteAfterCollisions(
                    entity, effectState, restituteX, restituteZ, requestedMovement.y != movement.y, movement, blockLanding,
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
        var historicalWater = ((WaterContext)entity).legacyparkourcompat$waterScan();
        if (fluid.equals(FluidTags.WATER) && historicalWater != null && historicalWater.replacesCurrent()) {
            entity.setDeltaMovement(entity.getDeltaMovement().add(historicalWater.impulse()));
            return;
        }
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

    @Redirect(
        method = "move(Lnet/minecraft/world/entity/MoverType;Lnet/minecraft/world/phys/Vec3;)V",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/world/entity/MoverType;PISTON:Lnet/minecraft/world/entity/MoverType;",
            opcode = Opcodes.GETSTATIC,
            ordinal = 0
        )
    )
    private @org.jspecify.annotations.Nullable MoverType legacyparkourcompat$pistonLimiterType() {
        // Disable only the limiter and its zero-result return; later PISTON gates
        // still see the caller's unchanged mover type.
        return MovementRuntime.limitPistonMovement((Entity) (Object) this) ? MoverType.PISTON : null;
    }

    @WrapOperation(
        method = "move(Lnet/minecraft/world/entity/MoverType;Lnet/minecraft/world/phys/Vec3;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/phys/Vec3;lengthSqr()D",
            ordinal = 0
        )
    )
    private double legacyparkourcompat$capturePistonStuckSpeedMultiplier(
        Vec3 multiplier,
        Operation<Double> original,
        @Local(argsOnly = true) MoverType moverType
    ) {
        double lengthSquared = original.call(multiplier);
        Entity entity = (Entity) (Object) this;
        this.legacyparkourcompat$pistonStuckSpeedMultiplier = null;
        if (moverType == MoverType.PISTON && entity instanceof Player && lengthSquared > 1.0E-7) {
            this.legacyparkourcompat$pistonStuckSpeedMultiplier = multiplier;
        }
        return lengthSquared;
    }

    @ModifyArg(
        method = "move(Lnet/minecraft/world/entity/MoverType;Lnet/minecraft/world/phys/Vec3;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/Entity;maybeBackOffFromEdge(Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/entity/MoverType;)Lnet/minecraft/world/phys/Vec3;"
        ),
        index = 0
    )
    private Vec3 legacyparkourcompat$restorePistonStuckSpeedMultiplier(
        Vec3 movement,
        @Local(argsOnly = true) MoverType moverType
    ) {
        Vec3 multiplier = this.legacyparkourcompat$pistonStuckSpeedMultiplier;
        this.legacyparkourcompat$pistonStuckSpeedMultiplier = null;
        Entity entity = (Entity) (Object) this;
        if (multiplier == null || moverType != MoverType.PISTON || !(entity instanceof Player player)) {
            return movement;
        }
        return MovementRuntime.find(PistonStuckSpeedHook.class, player)
            .map(hook -> hook.transformPistonMovement(movement, multiplier))
            .orElse(movement);
    }

    @Inject(
        method = "move(Lnet/minecraft/world/entity/MoverType;Lnet/minecraft/world/phys/Vec3;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/Entity;getMovementEmission()Lnet/minecraft/world/entity/Entity$MovementEmission;"
        )
    )
    private void legacyparkourcompat$historicalGroundedBlockStep(
        MoverType moverType,
        Vec3 movement,
        CallbackInfo ci,
        @Local(name = "effectPos") BlockPos effectPos,
        @Local(name = "effectState") BlockState effectState
    ) {
        Entity entity = (Entity)(Object)this;
        if (entity instanceof LocalPlayer player) {
            MovementRuntime.find(GroundedBlockStepCallbackHook.class, player)
                .ifPresent(hook -> hook.afterGroundState(entity, effectPos, effectState));
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
            MovementRuntime.find(EntityBlockContactTraversalHook.class, player)
                .ifPresent(hook -> hook.afterMove(
                    entity,
                    state -> ((EntityInvoker)(Object)entity).legacyparkourcompat$invokeOnInsideBlock(state)
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
        float factor = MovementRuntime.find(BlockSpeedFactorHook.class, entity)
            .map(behavior -> behavior.movementSpeedFactor(entity, vanilla))
            .orElse(vanilla);
        if (entity instanceof LivingEntity livingEntity) {
            var friction = MovementRuntime.find(SoulSpeedFrictionHook.class, livingEntity);
            if (friction.isPresent()) {
                if (MovementRuntime.profile(livingEntity).target().olderThan(ParkourVersion.V1_16)) {
                    return factor;
                }
                if (livingEntity instanceof Player player
                    && (player.getAbilities().flying || player.isFallFlying())) {
                    // Mojmap Player#getBlockSpeedFactor returns 1.0F before its superclass path in 1.20.6 and 1.21.1.
                    return 1.0F;
                }

                BlockPos supportPosition = ((EntityInvoker)livingEntity)
                    .legacyparkourcompat$invokeGetBlockPosBelowThatAffectsMyMovement();
                SoulSpeedBlockHook selector = MovementRuntime.find(SoulSpeedBlockHook.class, livingEntity)
                    .orElseThrow(() -> new IllegalStateException(
                        "Soul Speed support-block selector is missing from the active profile"
                    ));
                boolean onSoulSpeedBlock = selector.onSoulSpeedBlock(livingEntity, supportPosition);
                int soulSpeedLevel = onSoulSpeedBlock
                    ? EnchantmentHelper.getEnchantmentLevel(
                        livingEntity.registryAccess().lookupOrThrow(Registries.ENCHANTMENT)
                            .getOrThrow(Enchantments.SOUL_SPEED),
                        livingEntity
                    )
                    : 0;
                // Entity#getBlockSpeedFactor has its own support-cell selector, separate from Soul Speed's tag selector.
                VanillaFn<BlockPos> baseFactorSupportPosition = () -> MovementRuntime
                    .find(SupportingBlockPositionHook.class, livingEntity)
                    .map(hook -> hook.getPosition(
                        livingEntity,
                        () -> ((EntityInvoker)livingEntity)
                            .legacyparkourcompat$invokeGetBlockPosBelowThatAffectsMyMovement()
                    ))
                    .orElseGet(() -> ((EntityInvoker)livingEntity)
                        .legacyparkourcompat$invokeGetBlockPosBelowThatAffectsMyMovement());
                return friction.get().blockSpeedFactor(
                    livingEntity, onSoulSpeedBlock, soulSpeedLevel, baseFactorSupportPosition
                );
            }
        }
        return factor;
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
