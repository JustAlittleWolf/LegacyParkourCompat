package me.wolfii.legacyparkourcompat.mixin;


@Mixin(Entity.class)

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.BlockBounceBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.BoatPassengerFluidPushBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.InsideBlockContactBehavior;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityFluidInteraction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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


    @Redirect(
        method = "checkInsideBlocks(Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/entity/InsideBlockEffectApplier$StepBasedCollector;Lit/unimi/dsi/fastutil/longs/LongSet;I)I",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/AABB;deflate(D)Lnet/minecraft/world/phys/AABB;")
    )
    private AABB legacyparkourcompat$insideBlockInset(AABB box, double vanilla) {
        if ((Object) this instanceof Player player) {
            return MovementRuntime.find(InsideBlockContactBehavior.class, player)
                .map(behavior -> box.deflate(behavior.inset(player, vanilla)))
                .orElseGet(() -> box.deflate(vanilla));
        }
        return box.deflate(vanilla);
    }

    @Redirect(
        method = "updateFluidInteraction()Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/EntityFluidInteraction;applyCurrentTo(Lnet/minecraft/tags/TagKey;Lnet/minecraft/world/entity/Entity;D)V",
            ordinal = 0
        )
    )
    private void legacyparkourcompat$boatPassengerWaterCurrent(
        EntityFluidInteraction interaction,
        TagKey<Fluid> fluid,
        Entity entity,
        double scale
    ) {
        if (entity instanceof Player player
            && MovementRuntime.find(BoatPassengerFluidPushBehavior.class, player)
                .map(behavior -> behavior.skipWaterCurrent(player, player.getVehicle()))
                .orElse(false)) {
            return;
        }
        interaction.applyCurrentTo(fluid, entity, scale);
    }

}
