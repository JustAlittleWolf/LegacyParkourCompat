package me.wolfii.legacyparkourcompat.mixin;

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
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;

/**
 * Dispatches the local-player wake search. 1.16.1 Mojmap LivingEntity.stopSleeping
 * calls BedBlock.findStandUpPosition with counter zero; 1.16.2 Mojmap and current
 * 26.2 unobfuscated LivingEntity.stopSleeping pass the yaw ordered search inputs.
 */
@Mixin(LivingEntity.class)
abstract class LivingEntityBedWakeMixin {
    @Redirect(
        method = "stopSleeping",
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
        float yaw
    ) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (!(entity instanceof LocalPlayer) || Minecraft.getInstance().player != entity) {
            return BedBlock.findStandUpPosition(entityType, level, bedPosition, facing, yaw);
        }
        VanillaFn<Optional<Vec3>> callNative = () -> BedBlock.findStandUpPosition(entityType, level, bedPosition, facing, yaw);
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
