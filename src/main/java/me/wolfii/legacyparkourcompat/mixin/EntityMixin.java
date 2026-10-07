package me.wolfii.legacyparkourcompat.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.hook.AfterCollisionBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.BlockBounceBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.BlockLandingBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.BlockSpeedFactorBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.BoatPassengerFluidPushBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.CollisionRestitutionBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.GravityBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.InsideBlockContactBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.PistonMovementBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SneakEdgeMoverBehavior;
import me.wolfii.legacyparkourcompat.mixin.accessor.EntityInvoker;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityFluidInteraction;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
abstract class EntityMixin {

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
        var blockLanding = MovementRuntime.find(BlockLandingBehavior.class, effectState.getBlock(), entity);
        MovementRuntime.find(CollisionRestitutionBehavior.class, entity)
            .ifPresentOrElse(
                behavior -> behavior.restituteAfterCollisions(
                    entity, effectState, xCollision, zCollision, requestedMovement.y != movement.y, movement, blockLanding,
                    MovementRuntime.find(BlockBounceBehavior.class, effectState.getBlock(), entity), vanilla
                ),
                vanilla::run
            );
    }

    @ModifyReturnValue(method = "getGravity", at = @At("RETURN"))
    private double legacyparkourcompat$historicalPlayerGravity(double vanilla) {
        Entity entity = (Entity)(Object)this;
        return MovementRuntime.find(GravityBehavior.class, entity)
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
        MovementRuntime.find(BlockBounceBehavior.class, block, entity)
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
            return MovementRuntime.find(InsideBlockContactBehavior.class, player)
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
            && MovementRuntime.find(BoatPassengerFluidPushBehavior.class, player)
                .map(behavior -> behavior.skipWaterCurrent(player, player.getVehicle(), MovementRuntime.profile(player).target()))
                .orElse(false)) {
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
            target = "Lnet/minecraft/world/entity/Entity;getBlockSpeedFactor()F",
            shift = At.Shift.BEFORE
        )
    )
    private void legacyparkourcompat$applyHistoricalBlockCallbacks(MoverType moverType, Vec3 movement, CallbackInfo ci) {
        Entity entity = (Entity)(Object)this;
        MovementRuntime.find(AfterCollisionBehavior.class, entity)
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
        return MovementRuntime.find(BlockSpeedFactorBehavior.class, entity)
            .map(behavior -> behavior.movementSpeedFactor(entity, vanilla))
            .orElse(vanilla);
    }
}
