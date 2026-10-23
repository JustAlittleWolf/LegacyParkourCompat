package me.wolfii.legacyparkourcompat.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.OnPositionHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.SupportingBlockPositionHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Entity.class)
abstract class EntitySupportingBlockPositionMixin {
    @WrapMethod(method = "getBlockPosBelowThatAffectsMyMovement()Lnet/minecraft/core/BlockPos;")
    private BlockPos legacyparkourcompat$useHistoricalSupportingBlockPosition(Operation<BlockPos> original) {
        Entity entity = (Entity)(Object)this;
        if (MovementRuntime.profile(entity).target().olderThan(ParkourVersion.V1_15)) {
            return original.call();
        }
        return MovementRuntime.find(SupportingBlockPositionHook.class, entity)
            .map(hook -> hook.getPosition(entity, () -> original.call()))
            .orElseGet(() -> original.call());
    }

    @WrapMethod(method = "getOnPos(F)Lnet/minecraft/core/BlockPos;")
    private BlockPos legacyparkourcompat$onPosition(float offset, Operation<BlockPos> original) {
        Entity entity = (Entity)(Object)this;
        return MovementRuntime.find(OnPositionHook.class, entity)
            .map(hook -> hook.onPosition(entity, offset, () -> original.call(offset)))
            .orElseGet(() -> original.call(offset));
    }
}
