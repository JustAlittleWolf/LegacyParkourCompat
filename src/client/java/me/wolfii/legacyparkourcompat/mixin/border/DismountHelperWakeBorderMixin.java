package me.wolfii.legacyparkourcompat.mixin.border;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.wolfii.legacyparkourcompat.mechanic.BorderAdmissionContext;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.border.WakeBorderAdmissionHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(DismountHelper.class)
public abstract class DismountHelperWakeBorderMixin {
    @WrapOperation(method = "findSafeDismountLocation", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/world/level/border/WorldBorder;isWithinBounds(Lnet/minecraft/world/phys/AABB;)Z"))
    private static boolean legacyparkourcompat$admitWakeCandidate(WorldBorder border, AABB box,
        Operation<Boolean> original, EntityType<?> type, CollisionGetter level,
        BlockPos pos, boolean checkDangerous) {
        var player = BorderAdmissionContext.wakingPlayer();
        if (type == EntityTypes.PLAYER && player != null && player.level() == level) {
            var hook = MovementRuntime.find(WakeBorderAdmissionHook.class, player);
            if (hook.isPresent()) {
                return hook.get().admitsCandidate();
            }
        }
        return original.call(border, box);
    }
}
