package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.TrapdoorClimbingHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Redirects the 26.2 onClimbable trapdoor predicate before that branch writes
 * lastClimbablePos. The owner and descriptor match the private call in
 * LivingEntity#onClimbable()Z.
 */
@Mixin(LivingEntity.class)
public abstract class LivingEntityClimbingMixin {
    @Shadow
    private abstract boolean trapdoorUsableAsLadder(BlockPos pos, BlockState state);

    @Redirect(
        method = "onClimbable()Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/LivingEntity;trapdoorUsableAsLadder(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z"
        )
    )
    private boolean legacyparkourcompat$trapdoorClimbing(BlockPos pos, BlockState state) {
        LivingEntity entity = (LivingEntity)(Object)this;
        boolean vanillaResult = this.trapdoorUsableAsLadder(pos, state);
        return MovementRuntime.find(TrapdoorClimbingHook.class, entity)
            .map(hook -> hook.canClimbTrapdoor(vanillaResult))
            .orElse(vanillaResult);
    }
}
