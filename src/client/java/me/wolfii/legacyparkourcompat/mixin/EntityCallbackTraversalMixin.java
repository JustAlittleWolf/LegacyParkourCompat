package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockCallbackAxisOrderHook;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;

/** Thin runtime dispatch for the independently resolved callback axis operand. */
@Mixin(Entity.class)
abstract class EntityCallbackTraversalMixin {
    /**
     * 1.21.8 (Mojmap) calls Entity.axisStepOrder with to - from here. In
     * 1.21.9 and 1.21.10 (Mojmap), Entity.checkInsideBlocks instead passes the
     * movement's edge-adjusted requested vector. Replace only that ordering
     * operand; movement collision resolution and callback execution stay native.
     */
    @Redirect(
        method = "checkInsideBlocks(Ljava/util/List;Lnet/minecraft/world/entity/InsideBlockEffectApplier$StepBasedCollector;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/Entity$Movement;axisDependentOriginalMovement()Ljava/util/Optional;",
            ordinal = 1
        )
    )
    private Optional<Vec3> legacyParkourCompat$resolvedCallbackAxisOrder(@Coerce Object movement) {
        Optional<Vec3> recorded = ((EntityMovementAccessor)movement).axisDependentOriginalMovement();
        Entity entity = (Entity)(Object)this;
        if (recorded.isEmpty()) {
            return recorded;
        }
        EntityMovementAccessor access = (EntityMovementAccessor)movement;
        Vec3 resolved = access.legacyParkourCompat$to().subtract(access.legacyParkourCompat$from());
        return MovementRuntime.find(BlockCallbackAxisOrderHook.class, entity)
            .map(hook -> Optional.of(hook.callbackAxisOrder(recorded.get(), resolved)))
            .orElse(recorded);
    }
}
