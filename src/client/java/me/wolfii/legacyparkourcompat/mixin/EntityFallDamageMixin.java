package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.FallDistanceAccumulationHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.FallDamageCallbackHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;

/** Dispatches only the independently resolved Entity.move fall-damage callback decision. */
@Mixin(Entity.class)
public abstract class EntityFallDamageMixin {
    @Shadow
    protected abstract void checkFallDamage(double movementY, boolean onGround, BlockState onState, BlockPos pos);

    @Redirect(
        method = "move(Lnet/minecraft/world/entity/MoverType;Lnet/minecraft/world/phys/Vec3;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/Entity;checkFallDamage(DZLnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;)V"
        )
    )
    private void legacyparkourcompat$dispatchFallDamageCallback(
        Entity entity, double movementY, boolean onGround, BlockState onState, BlockPos pos
    ) {
        Optional<FallDamageCallbackHook> hook = MovementRuntime.find(FallDamageCallbackHook.class, entity);
        if (hook.isEmpty() || hook.get().shouldCall(entity)) {
            checkFallDamage(movementY, onGround, onState, pos);
        }
    }

    @Redirect(
        method = "checkFallDamage(DZLnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/Entity;isInWater()Z",
            ordinal = 0
        ),
        require = 1
    )
    private boolean legacyparkourcompat$fallDistanceAccumulationWaterGate(
        Entity entity, double yMovement, boolean onGround, BlockState onState, BlockPos pos
    ) {
        return MovementRuntime.find(FallDistanceAccumulationHook.class, entity)
            .flatMap(hook -> hook.shouldAccumulateFallDistance(entity, yMovement, onGround))
            .map(shouldAccumulate -> !shouldAccumulate)
            .orElseGet(entity::isInWater);
    }
}
