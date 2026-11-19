package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.FeetBlockStateHook;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
abstract class FeetBlockStateMixin {
    @Inject(method = "getInBlockState", at = @At("HEAD"), cancellable = true)
    private void legacyparkourcompat$getFeetBlockState(CallbackInfoReturnable<BlockState> callback) {
        Entity entity = (Entity) (Object) this;
        MovementRuntime.find(FeetBlockStateHook.class, entity)
            .flatMap(hook -> hook.getFeetBlockState(entity))
            .ifPresent(callback::setReturnValue);
    }
}
