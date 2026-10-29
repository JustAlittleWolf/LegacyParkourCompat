package me.wolfii.legacyparkourcompat.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.collision.SweptShapeIntersectionMath;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.PartialShapeSweepHook;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

/**
 * Dispatches only the accepted End Portal block-shape sweep; fluid intersection stays on its native path.
 * The 26.2 Entity.checkInsideBlocks synthetic block visitor has a dedicated
 * collidedWithShapeMovingFrom invocation before its separate collidedWithFluid call.
 */
@Mixin(Entity.class)
abstract class EntityPartialShapeSweepMixin {
    @WrapOperation(
        method = "lambda$checkInsideBlocks$0(ILjava/util/concurrent/atomic/AtomicInteger;ZLnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;Lit/unimi/dsi/fastutil/longs/LongSet;ZLnet/minecraft/world/phys/AABB;Lnet/minecraft/world/entity/InsideBlockEffectApplier$StepBasedCollector;Lnet/minecraft/core/BlockPos;I)Z",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;collidedWithShapeMovingFrom(Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;Ljava/util/List;)Z")
    )
    private boolean legacyparkourcompat$partialShapeSweep(
        Entity entity, Vec3 from, Vec3 to, List<AABB> shapeParts, Operation<Boolean> original,
        @Local(name = "state") BlockState state
    ) {
        if (!state.is(Blocks.END_PORTAL)) {
            return original.call(entity, from, to, shapeParts);
        }

        return MovementRuntime.find(PartialShapeSweepHook.class, entity)
            .flatMap(hook -> hook.reverseDisplacement(entity.getBoundingBox(), from, to))
            .map(displacement -> SweptShapeIntersectionMath.collides(
                entity.getBoundingBox(), displacement, shapeParts))
            .orElseGet(() -> original.call(entity, from, to, shapeParts));
    }
}
