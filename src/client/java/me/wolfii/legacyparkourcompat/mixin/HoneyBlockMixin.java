package me.wolfii.legacyparkourcompat.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.HoneySlideDeltaYHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.HoneySlideVelocityHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.HoneyBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

/**
 * Mojmap 26.2 HoneyBlock.isSlidingDown(BlockPos, Entity) and
 * doSlideMovement(Entity) route their delta-Y gates and vertical write through
 * getOldDeltaY(double) and getNewDeltaY(double); wrap those calls while leaving
 * entityInside and the rest of the callback native.
 */
@Mixin(HoneyBlock.class)
abstract class HoneyBlockMixin {
    @WrapOperation(
        method = "isSlidingDown(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/Entity;)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/HoneyBlock;getOldDeltaY(D)D"
        )
    )
    private double legacyparkourcompat$historicalSlideGate(
        double actualDeltaY,
        Operation<Double> vanilla,
        BlockPos pos,
        Entity entity
    ) {
        return legacyparkourcompat$oldDeltaY(entity, actualDeltaY, vanilla);
    }

    @WrapOperation(
        method = "doSlideMovement(Lnet/minecraft/world/entity/Entity;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/HoneyBlock;getOldDeltaY(D)D"
        )
    )
    private double legacyparkourcompat$historicalSlideThrottle(
        double actualDeltaY,
        Operation<Double> vanilla,
        Entity entity
    ) {
        return legacyparkourcompat$oldDeltaY(entity, actualDeltaY, vanilla);
    }

    @WrapOperation(
        method = "doSlideMovement(Lnet/minecraft/world/entity/Entity;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/HoneyBlock;getNewDeltaY(D)D"
        )
    )
    private double legacyparkourcompat$historicalSlideVelocity(
        double deltaY,
        Operation<Double> vanilla,
        Entity entity
    ) {
        if (entity instanceof Player player
            && MovementRuntime.appliesTo(player)
            && !MovementRuntime.profile(player).target().olderThan(ParkourVersion.V1_15)) {
            return MovementRuntime.find(HoneySlideVelocityHook.class, player)
                .map(HoneySlideVelocityHook::slideVerticalVelocity)
                .orElseGet(() -> vanilla.call(deltaY));
        }
        return vanilla.call(deltaY);
    }

    @Unique
    private double legacyparkourcompat$oldDeltaY(Entity entity, double actualDeltaY, Operation<Double> vanilla) {
        if (entity instanceof Player player
            && MovementRuntime.appliesTo(player)
            && !MovementRuntime.profile(player).target().olderThan(ParkourVersion.V1_15)) {
            return MovementRuntime.find(HoneySlideDeltaYHook.class, player)
                .map(hook -> hook.oldDeltaY(actualDeltaY))
                .orElseGet(() -> vanilla.call(actualDeltaY));
        }
        return vanilla.call(actualDeltaY);
    }
}
