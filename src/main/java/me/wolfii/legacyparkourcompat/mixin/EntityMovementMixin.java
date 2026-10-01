package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.SoulSandSpeedBehavior;
import me.wolfii.legacyparkourcompat.mixin.accessor.EntityInvoker;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMovementMixin {
    @Inject(
        method = "move(Lnet/minecraft/world/entity/MoverType;Lnet/minecraft/world/phys/Vec3;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/Entity;checkInsideBlocks(Ljava/util/List;Lnet/minecraft/world/entity/InsideBlockEffectApplier$StepBasedCollector;)V",
            shift = At.Shift.AFTER
        )
    )
    private void legacyparkourcompat$applyHistoricalBlockCallbacks(MoverType moverType, Vec3 movement, CallbackInfo ci) {
        Entity entity = (Entity)(Object)this;
        MovementRuntime.find(SoulSandSpeedBehavior.class, entity)
            .ifPresent(behavior -> behavior.afterCollision(entity));
    }

    @Redirect(
        method = "move(Lnet/minecraft/world/entity/MoverType;Lnet/minecraft/world/phys/Vec3;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/Entity;getBlockSpeedFactor()F"
        )
    )
    private float legacyparkourcompat$movementSpeedFactor(Entity entity) {
        float vanilla = ((EntityInvoker)entity).legacyparkourcompat$invokeGetBlockSpeedFactor();
        return MovementRuntime.find(SoulSandSpeedBehavior.class, entity)
            .map(behavior -> behavior.movementSpeedFactor(entity, vanilla))
            .orElse(vanilla);
    }
}
