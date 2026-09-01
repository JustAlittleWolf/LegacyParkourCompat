package me.wolfii.legacyparkourcompat.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.PlayerPoseBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SneakEdgeBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SneakEdgeDistanceBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SprintingBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SwimmingBehavior;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin {
    @Unique
    private boolean lpc$vanillaPose;

    @Unique
    private boolean lpc$vanillaSwim;

    @Unique
    private int lpc$appliedEpoch = Integer.MIN_VALUE;

    @Shadow
    protected abstract void updatePlayerPose();

    @Inject(method = "tick", at = @At("HEAD"))
    private void lpc$applyVersionSwitch(CallbackInfo ci) {
        int epoch = MovementRuntime.epoch();
        if (this.lpc$appliedEpoch == epoch) {
            return;
        }
        this.lpc$appliedEpoch = epoch;
        Player self = (Player) (Object) this;
        self.refreshDimensions();
        this.updatePlayerPose();
    }

    @WrapOperation(
        method = "maybeBackOffFromEdge",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/player/Player;maxUpStep()F"
        )
    )
    private float lpc$sneakEdgeFall(Player instance, Operation<Float> original) {
        float vanilla = original.call(instance);
        if (!MovementRuntime.appliesTo(instance)) {
            return vanilla;
        }
        return MovementRuntime.find(SneakEdgeDistanceBehavior.class, instance)
            .map(behavior -> behavior.edgeFallDistance(instance, vanilla))
            .orElse(vanilla);
    }

    @ModifyReturnValue(method = "maybeBackOffFromEdge", at = @At("RETURN"))
    private Vec3 lpc$sneakEdge(Vec3 vanilla, Vec3 delta, MoverType moverType) {
        Player self = (Player) (Object) this;
        if (!MovementRuntime.appliesTo(self)) {
            return vanilla;
        }
        return MovementRuntime.find(SneakEdgeBehavior.class, self)
            .map(behavior -> behavior.maybeBackOffFromEdge(self, delta, moverType, () -> vanilla))
            .orElse(vanilla);
    }

    @Inject(method = "updatePlayerPose", at = @At("HEAD"), cancellable = true)
    private void lpc$pose(CallbackInfo ci) {
        if (this.lpc$vanillaPose) {
            return;
        }
        Player self = (Player) (Object) this;
        if (!MovementRuntime.appliesTo(self)) {
            return;
        }
        MovementRuntime.find(PlayerPoseBehavior.class, self).ifPresent(behavior -> {
            behavior.updatePlayerPose(self, () -> {
                this.lpc$vanillaPose = true;
                try {
                    this.updatePlayerPose();
                } finally {
                    this.lpc$vanillaPose = false;
                }
            });
            ci.cancel();
        });
    }

    @Inject(method = "updateSwimming", at = @At("HEAD"), cancellable = true)
    private void lpc$swim(CallbackInfo ci) {
        if (this.lpc$vanillaSwim) {
            return;
        }
        Player self = (Player) (Object) this;
        if (!MovementRuntime.appliesTo(self)) {
            return;
        }
        MovementRuntime.find(SwimmingBehavior.class, self).ifPresent(behavior -> {
            behavior.updateSwimming(self, () -> {
                this.lpc$vanillaSwim = true;
                try {
                    self.updateSwimming();
                } finally {
                    this.lpc$vanillaSwim = false;
                }
            });
            ci.cancel();
        });
    }

    @ModifyReturnValue(method = "canSprint", at = @At("RETURN"))
    private boolean lpc$canSprint(boolean vanilla) {
        Player self = (Player) (Object) this;
        if (!MovementRuntime.appliesTo(self)) {
            return vanilla;
        }
        return MovementRuntime.find(SprintingBehavior.class, self)
            .map(behavior -> behavior.canSprint(self, vanilla))
            .orElse(vanilla);
    }
}
