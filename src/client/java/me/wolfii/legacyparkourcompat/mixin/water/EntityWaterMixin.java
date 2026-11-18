package me.wolfii.legacyparkourcompat.mixin.water;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.FluidCurrentAggregationHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.water.BoatPassengerBodyWaterHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.water.FallingWaterCurrentHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.water.WaterBodyContactAreaAdmissionHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.water.WaterBodyContactHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.water.WaterBodyContactScanBoundHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.water.WaterCurrentHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.world.FlowScanAreaAdmissionHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.world.WaterFlowScanBoundHook;
import me.wolfii.legacyparkourcompat.mechanic.water.WaterContext;
import me.wolfii.legacyparkourcompat.mechanic.water.BodyWaterProjection;
import me.wolfii.legacyparkourcompat.mechanic.water.WaterScan;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityFluidInteraction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import org.jspecify.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityWaterMixin implements WaterContext {
    @Shadow @Final private EntityFluidInteraction fluidInteraction;

    @ModifyVariable(method = "updateFluidInteraction()Z", at = @At(value = "LOAD", ordinal = 0),
        index = 1, require = 1)
    private boolean legacyparkourcompat$publishBodyWaterContact(boolean nativeContact,
        @Share("waterContactForCurrent") LocalBooleanRef currentContact,
        @Share("skipBoatBodyWater") LocalBooleanRef skipBoatBodyWater) {
        // The first inWater load governs fall reset/splash, followed by the state write.
        currentContact.set(nativeContact);
        return !skipBoatBodyWater.get()
            && ((BodyWaterProjection) this.fluidInteraction).legacyparkourcompat$bodyWaterContact(nativeContact);
    }

    @Inject(method = "updateFluidInteraction()Z", at = @At(value = "FIELD", opcode = Opcodes.PUTFIELD,
        target = "Lnet/minecraft/world/entity/Entity;wasTouchingWater:Z", shift = At.Shift.AFTER),
        require = 1)
    private void legacyparkourcompat$restoreWaterCurrentContact(CallbackInfoReturnable<Boolean> callback,
        @Local(index = 1) LocalBooleanRef inWater,
        @Share("waterContactForCurrent") LocalBooleanRef currentContact) {
        // The current branch retains the original predicate and tracker.
        inWater.set(currentContact.get());
    }

    @ModifyReturnValue(method = "updateFluidInteraction()Z", at = @At("RETURN"))
    private boolean legacyparkourcompat$bodyWaterRefreshResult(boolean original,
        @Local(index = 1) boolean inWater, @Local(index = 2) boolean inLava,
        @Share("skipBoatBodyWater") LocalBooleanRef skipBoatBodyWater) {
        if (skipBoatBodyWater.get()) {
            return inLava;
        }
        boolean contact = ((BodyWaterProjection) this.fluidInteraction).legacyparkourcompat$bodyWaterContact(inWater);
        return contact == inWater ? original : contact || inLava;
    }

    @ModifyReturnValue(method = "getFluidHeight(Lnet/minecraft/tags/TagKey;)D", at = @At("RETURN"))
    private double legacyparkourcompat$bodyWaterDepth(double nativeDepth, TagKey<Fluid> fluid) {
        return fluid.equals(FluidTags.WATER)
            ? ((BodyWaterProjection) this.fluidInteraction).legacyparkourcompat$bodyWaterDepth(nativeDepth) : nativeDepth;
    }

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
    private boolean legacyparkourcompat$contact(boolean nativeContact,
        @Share("skipBoatBodyWater") LocalBooleanRef skipBoatBodyWater) {
        Entity entity = (Entity)(Object)this;
        if (entity instanceof LocalPlayer player
            && MovementRuntime.find(BoatPassengerBodyWaterHook.class, player)
                .map(hook -> hook.skipsBodyWater(player.getVehicle(), MovementRuntime.profile(player).target()))
                .orElse(false)) {
            // Skip the legacy query before it can eagerly apply flow. The
            // native current branch still uses its independent boat policy.
            skipBoatBodyWater.set(true);
            return nativeContact;
        }
        // The native tracker has finished updating eyes/depth/lava. Capture the
        // historical flow result here; current application remains at the
        // later native current call, after its loaded-area gate.
        if (entity instanceof Player player) {
            boolean aggregateHistoricalCurrent = !MovementRuntime.profile(player).target()
                .olderThan(ParkourVersion.V1_13)
                && MovementRuntime.find(FluidCurrentAggregationHook.class, player).isPresent();
            legacyparkourcompat$water = WaterScan.scan(
                player,
                MovementRuntime.find(WaterBodyContactHook.class, player),
                MovementRuntime.find(WaterBodyContactScanBoundHook.class, player),
                MovementRuntime.find(WaterBodyContactAreaAdmissionHook.class, player),
                MovementRuntime.find(WaterCurrentHook.class, player),
                MovementRuntime.find(FallingWaterCurrentHook.class, player),
                MovementRuntime.find(WaterFlowScanBoundHook.class, player),
                MovementRuntime.find(FlowScanAreaAdmissionHook.class, player),
                aggregateHistoricalCurrent
                    ? MovementRuntime.find(FluidCurrentAggregationHook.class, player)
                    : java.util.Optional.empty()
            ).orElse(null);
            if (legacyparkourcompat$water != null && legacyparkourcompat$water.replacesCurrent()
                && !aggregateHistoricalCurrent
                && !legacyparkourcompat$water.impulse().equals(net.minecraft.world.phys.Vec3.ZERO)) {
                entity.setDeltaMovement(entity.getDeltaMovement().add(legacyparkourcompat$water.impulse()));
            }
        }
        return legacyparkourcompat$water == null || !legacyparkourcompat$water.replacesContact()
            ? nativeContact : legacyparkourcompat$water.contact();
    }
}
