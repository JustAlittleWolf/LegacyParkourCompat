package me.wolfii.legacyparkourcompat.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.TrapdoorClimbingHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * Redirects the 26.2 onClimbable trapdoor predicate before that branch writes
 * lastClimbablePos. The owner and descriptor match the private call in
 * LivingEntity#onClimbable()Z.
 */
@Mixin(LivingEntity.class)
public abstract class LivingEntityClimbingMixin {
    @WrapOperation(
        method = "onClimbable()Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/LivingEntity;trapdoorUsableAsLadder(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z"
        )
    )
    private boolean legacyparkourcompat$trapdoorClimbing(LivingEntity entity, BlockPos pos, BlockState state, Operation<Boolean> original) {
        boolean vanillaResult = original.call(entity, pos, state);
        return MovementRuntime.find(TrapdoorClimbingHook.class, entity)
            .map(hook -> hook.canClimbTrapdoor(vanillaResult))
            .orElse(vanillaResult);
    }
}
