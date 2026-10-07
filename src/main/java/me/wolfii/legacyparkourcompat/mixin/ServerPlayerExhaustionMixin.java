package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.JumpExhaustionBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.MovementExhaustionBehavior;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ServerPlayer.class)
abstract class ServerPlayerExhaustionMixin {
    @ModifyConstant(method = "jumpFromGround()V", constant = @Constant(floatValue = 0.2F))
    private float legacyparkourcompat$sprintingJumpExhaustion(float vanilla) {
        ServerPlayer player = (ServerPlayer)(Object)this;
        return MovementRuntime.find(JumpExhaustionBehavior.class, player)
            .map(behavior -> behavior.jumpExhaustion(player, JumpExhaustionBehavior.JumpKind.SPRINTING, vanilla))
            .orElse(vanilla);
    }

    @ModifyConstant(method = "jumpFromGround()V", constant = @Constant(floatValue = 0.05F))
    private float legacyparkourcompat$normalJumpExhaustion(float vanilla) {
        ServerPlayer player = (ServerPlayer)(Object)this;
        return MovementRuntime.find(JumpExhaustionBehavior.class, player)
            .map(behavior -> behavior.jumpExhaustion(player, JumpExhaustionBehavior.JumpKind.NORMAL, vanilla))
            .orElse(vanilla);
    }

    @ModifyConstant(
        method = "checkMovementStatistics(DDD)V",
        constant = @Constant(floatValue = 0.01F, ordinal = 0)
    )
    private float legacyparkourcompat$swimmingExhaustion(float vanilla) {
        return this.legacyparkourcompat$movementFactor(MovementExhaustionBehavior.MovementKind.SWIMMING, vanilla);
    }

    @ModifyConstant(
        method = "checkMovementStatistics(DDD)V",
        constant = @Constant(floatValue = 0.01F, ordinal = 2)
    )
    private float legacyparkourcompat$underwaterWalkingExhaustion(float vanilla) {
        return this.legacyparkourcompat$movementFactor(MovementExhaustionBehavior.MovementKind.UNDERWATER_WALKING, vanilla);
    }

    @ModifyConstant(
        method = "checkMovementStatistics(DDD)V",
        constant = @Constant(floatValue = 0.01F, ordinal = 4)
    )
    private float legacyparkourcompat$waterWalkingExhaustion(float vanilla) {
        return this.legacyparkourcompat$movementFactor(MovementExhaustionBehavior.MovementKind.WATER_WALKING, vanilla);
    }

    @ModifyConstant(
        method = "checkMovementStatistics(DDD)V",
        constant = @Constant(floatValue = 0.1F)
    )
    private float legacyparkourcompat$sprintExhaustion(float vanilla) {
        return this.legacyparkourcompat$movementFactor(MovementExhaustionBehavior.MovementKind.SPRINTING, vanilla);
    }

    @ModifyConstant(
        method = "checkMovementStatistics(DDD)V",
        constant = @Constant(floatValue = 0.0F, ordinal = 0)
    )
    private float legacyparkourcompat$sneakExhaustion(float vanilla) {
        return this.legacyparkourcompat$movementFactor(MovementExhaustionBehavior.MovementKind.SNEAKING, vanilla);
    }

    @ModifyConstant(
        method = "checkMovementStatistics(DDD)V",
        constant = @Constant(floatValue = 0.0F, ordinal = 1)
    )
    private float legacyparkourcompat$walkingExhaustion(float vanilla) {
        return this.legacyparkourcompat$movementFactor(MovementExhaustionBehavior.MovementKind.WALKING, vanilla);
    }

    private float legacyparkourcompat$movementFactor(MovementExhaustionBehavior.MovementKind kind, float vanilla) {
        ServerPlayer player = (ServerPlayer)(Object)this;
        return MovementRuntime.find(MovementExhaustionBehavior.class, player)
            .map(behavior -> behavior.movementExhaustionFactor(player, kind, vanilla))
            .orElse(vanilla);
    }
}
