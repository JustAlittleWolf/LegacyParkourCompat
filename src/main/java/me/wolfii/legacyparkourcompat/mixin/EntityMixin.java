package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.BlockBounceBehavior;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
abstract class EntityMixin {
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
}
