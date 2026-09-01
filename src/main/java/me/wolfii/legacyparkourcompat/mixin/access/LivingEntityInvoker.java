package me.wolfii.legacyparkourcompat.mixin.access;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LivingEntity.class)
public interface LivingEntityInvoker {
    @Invoker("handleOnClimbable")
    Vec3 lpc$handleOnClimbable(Vec3 delta);

    @Invoker("handleRelativeFrictionAndCalculateMovement")
    Vec3 lpc$handleRelativeFrictionAndCalculateMovement(Vec3 input, float friction);

    @Invoker("travelInAir")
    void lpc$travelInAir(Vec3 input);

    @Invoker("travelInFluid")
    void lpc$travelInFluid(Vec3 input);

    @Invoker("travelFallFlying")
    void lpc$travelFallFlying(Vec3 input);

    @Invoker("getJumpPower")
    float lpc$getJumpPower();
}
