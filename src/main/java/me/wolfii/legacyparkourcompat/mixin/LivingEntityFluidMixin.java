package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.FluidJumpBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.LavaTravelBehavior;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntity.class)
abstract class LivingEntityFluidMixin {
    @Redirect(
        method = "aiStep",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isAffectedByFluids()Z")
    )
    private boolean legacyparkourcompat$fluidJumpEligibility(LivingEntity entity) {
        boolean vanilla = entity.isAffectedByFluids();
        if (entity instanceof Player player) {
            return MovementRuntime.find(FluidJumpBehavior.class, player)
                .map(behavior -> behavior.mayJumpInFluid(player, vanilla))
                .orElse(vanilla);
        }
        return vanilla;
    }

    @Redirect(
        method = "aiStep",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isInShallowFluid(Lnet/minecraft/tags/TagKey;)Z")
    )
    private boolean legacyparkourcompat$shallowLavaJump(
        LivingEntity entity,
        TagKey<Fluid> fluid
    ) {
        boolean vanilla = entity.getFluidHeight(fluid) <= entity.getFluidJumpThreshold();
        if (fluid.equals(FluidTags.LAVA) && entity instanceof Player player) {
            return MovementRuntime.find(FluidJumpBehavior.class, player)
                .map(behavior -> behavior.isShallowLavaForGroundJump(player, vanilla))
                .orElse(vanilla);
        }
        return vanilla;
    }

    @Redirect(
        method = "travelInLava",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isInShallowFluid(Lnet/minecraft/tags/TagKey;)Z")
    )
    private boolean legacyparkourcompat$shallowLavaTravel(
        LivingEntity entity,
        TagKey<Fluid> fluid
    ) {
        boolean vanilla = entity.getFluidHeight(fluid) <= entity.getFluidJumpThreshold();
        if (entity instanceof Player player) {
            return MovementRuntime.find(LavaTravelBehavior.class, player)
                .map(behavior -> behavior.isShallowForTravel(player, vanilla))
                .orElse(vanilla);
        }
        return vanilla;
    }
}
