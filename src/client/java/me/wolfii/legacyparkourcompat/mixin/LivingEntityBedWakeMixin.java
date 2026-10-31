package me.wolfii.legacyparkourcompat.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.wolfii.legacyparkourcompat.mechanic.BorderAdmissionContext;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.bed.BedWakePositionHook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

/**
 * Dispatches the local-player wake search. 1.16.1 Mojmap LivingEntity.stopSleeping
 * calls BedBlock.findStandUpPosition with counter zero; 1.16.2 Mojmap and current
 * 26.2 unobfuscated LivingEntity.stopSleeping pass the yaw ordered search inputs.
 * In 26.2 bytecode the call is inside lambda$stopSleeping$0(BlockPos), created by
 * the source-level Optional.ifPresent lambda, rather than stopSleeping itself.
 */
@Mixin(LivingEntity.class)
abstract class LivingEntityBedWakeMixin {
    @WrapOperation(
        method = "lambda$stopSleeping$0(Lnet/minecraft/core/BlockPos;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/BedBlock;findStandUpPosition(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/level/CollisionGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;F)Ljava/util/Optional;"
        )
    )
    private Optional<Vec3> legacyparkourcompat$findStandUpPosition(
        EntityType<?> entityType,
        CollisionGetter level,
        BlockPos bedPosition,
        Direction facing,
        float yaw,
        Operation<Optional<Vec3>> original
    ) {
        LivingEntity entity = (LivingEntity) (Object) this;
        return BorderAdmissionContext.withWakingPlayerResult(
            MovementRuntime.appliesTo(entity) ? entity : null,
            () -> legacyparkourcompat$findStandUpPositionInContext(
                entity, entityType, level, bedPosition, facing, yaw, original
            )
        );
    }

    private Optional<Vec3> legacyparkourcompat$findStandUpPositionInContext(
        LivingEntity entity,
        EntityType<?> entityType,
        CollisionGetter level,
        BlockPos bedPosition,
        Direction facing,
        float yaw,
        Operation<Optional<Vec3>> original
    ) {
        if (!(entity instanceof LocalPlayer) || Minecraft.getInstance().player != entity) {
            return original.call(entityType, level, bedPosition, facing, yaw);
        }
        VanillaFn<Optional<Vec3>> callNative = () -> original.call(entityType, level, bedPosition, facing, yaw);
        var hook = MovementRuntime.find(BedWakePositionHook.class, entity);
        if (hook.isEmpty()) {
            return callNative.get();
        }
        BedWakePositionHook.Result result = MovementRuntime.withEntityContext(entity, () ->
            MovementRuntime.supportsBedWakeSearch(entity, level, bedPosition)
                ? hook.get().findStandUpPosition(entity, entityType, level, bedPosition)
                : BedWakePositionHook.Result.unsupported()
        );
        return result.supported() ? result.position() : callNative.get();
    }
}
