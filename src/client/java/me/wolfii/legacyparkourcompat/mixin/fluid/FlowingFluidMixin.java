package me.wolfii.legacyparkourcompat.mixin.fluid;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.FallingFluidFaceSturdyHook;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FlowingFluid.class)
abstract class FlowingFluidMixin {
    @WrapOperation(method = "isSolidFace", at = @At(value = "INVOKE", target =
        "Lnet/minecraft/world/level/block/state/BlockState;isFaceSturdy(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;)Z"))
    private boolean legacyparkourcompat$fallingCurrentFace(BlockState state, BlockGetter level, BlockPos pos,
                                                          Direction direction, Operation<Boolean> original) {
        var player = MovementRuntime.localPlayerFluidCurrent();
        if (player == null) {
            return original.call(state, level, pos, direction);
        }
        FlowingFluid fluid = (FlowingFluid)(Object)this;
        return MovementRuntime.find(FallingFluidFaceSturdyHook.class, state.getBlock(), player)
            .map(hook -> hook.isFaceSturdy(fluid, player, state, level, pos, direction,
                () -> original.call(state, level, pos, direction)))
            .orElseGet(() -> original.call(state, level, pos, direction));
    }
}
