package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.blockcallback.PistonCallbackScanOriginHook;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.piston.PistonMovingBlockEntity;
import net.minecraft.world.phys.Vec3;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

/** Adapts only the explicit piston callback scan origin. */
@Mixin(PistonMovingBlockEntity.class)
abstract class PistonMovingBlockEntityMixin {
    @ModifyArg(
        method = "moveEntityByPiston",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;applyEffectsFromBlocks(Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;)V"),
        index = 0
    )
    private Vec3 legacyparkourcompat$historicalCallbackScanOrigin(
        Vec3 currentOrigin,
        @Local(argsOnly = true) Entity entity
    ) {
        return MovementRuntime.find(PistonCallbackScanOriginHook.class, entity)
            .map(hook -> hook.scanOrigin(entity, currentOrigin))
            .orElse(currentOrigin);
    }
}
