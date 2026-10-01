package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.SupportingBlockBehavior;
import me.wolfii.legacyparkourcompat.mixin.accessor.EntityInvoker;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntity.class)
public abstract class LivingEntityTravelMixin {
    @Redirect(
        method = "travelInAir(Lnet/minecraft/world/phys/Vec3;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/Entity;getBlockPosBelowThatAffectsMyMovement()Lnet/minecraft/core/BlockPos;"
        )
    )
    private BlockPos legacyparkourcompat$groundFrictionBlockPosition(Entity entity) {
        VanillaFn<BlockPos> vanilla = () -> ((EntityInvoker)entity)
            .legacyparkourcompat$invokeGetBlockPosBelowThatAffectsMyMovement();
        return MovementRuntime.find(SupportingBlockBehavior.class, entity)
            .map(behavior -> behavior.getBlockPosBelowThatAffectsMyMovement(entity, vanilla))
            .orElseGet(vanilla::get);
    }
}
