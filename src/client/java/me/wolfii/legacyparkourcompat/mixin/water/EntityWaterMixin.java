package me.wolfii.legacyparkourcompat.mixin.water;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.water.WaterBodyContactAreaAdmissionHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.water.WaterBodyContactHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.water.WaterBodyContactScanBoundHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.water.WaterCurrentHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.world.FlowScanAreaAdmissionHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.world.WaterFlowScanBoundHook;
import me.wolfii.legacyparkourcompat.mechanic.water.WaterContext;
import me.wolfii.legacyparkourcompat.mechanic.water.WaterScan;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityWaterMixin implements WaterContext {
    @Unique private WaterScan.@Nullable Result legacyparkourcompat$water;
    @Unique private int legacyparkourcompat$waterTick;
    @Unique private @Nullable Level legacyparkourcompat$waterLevel;

    @Override
    public WaterScan.@Nullable Result legacyparkourcompat$waterScan() {
        Entity entity = (Entity)(Object)this;
        return legacyparkourcompat$waterTick == entity.tickCount
            && legacyparkourcompat$waterLevel == entity.level() ? legacyparkourcompat$water : null;
    }

    @Inject(method = "updateFluidInteraction", at = @At("HEAD"))
    private void legacyparkourcompat$resetProbe(CallbackInfoReturnable<Boolean> cir) {
        Entity entity = (Entity)(Object)this;
        legacyparkourcompat$water = null;
        legacyparkourcompat$waterTick = entity.tickCount;
        legacyparkourcompat$waterLevel = entity.level();
    }

    @ModifyExpressionValue(method = "updateFluidInteraction", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/world/entity/EntityFluidInteraction;isInFluid(Lnet/minecraft/tags/TagKey;)Z", ordinal = 0))
    private boolean legacyparkourcompat$contact(boolean nativeContact) {
        Entity entity = (Entity)(Object)this;
        // The native tracker has finished updating eyes/depth/lava. Apply the
        // old flow impulse before native water contact effects, then retain the
        // independent contact/current decisions for later consumers.
        if (entity instanceof Player player) {
            legacyparkourcompat$water = WaterScan.scan(
                player,
                MovementRuntime.find(WaterBodyContactHook.class, player),
                MovementRuntime.find(WaterBodyContactScanBoundHook.class, player),
                MovementRuntime.find(WaterBodyContactAreaAdmissionHook.class, player),
                MovementRuntime.find(WaterCurrentHook.class, player),
                MovementRuntime.find(WaterFlowScanBoundHook.class, player),
                MovementRuntime.find(FlowScanAreaAdmissionHook.class, player)
            ).orElse(null);
            if (legacyparkourcompat$water != null && legacyparkourcompat$water.replacesCurrent()
                && !legacyparkourcompat$water.impulse().equals(net.minecraft.world.phys.Vec3.ZERO)) {
                entity.setDeltaMovement(entity.getDeltaMovement().add(legacyparkourcompat$water.impulse()));
            }
        }
        return legacyparkourcompat$water == null || !legacyparkourcompat$water.replacesContact()
            ? nativeContact : legacyparkourcompat$water.contact();
    }
}
