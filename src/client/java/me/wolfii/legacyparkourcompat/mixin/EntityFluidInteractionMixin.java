package me.wolfii.legacyparkourcompat.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.OrdinaryPlayerContext;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.BodyWaterScanAdmissionHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.BodyWaterSurfaceHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.FluidInteractionBoxHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.FluidInteractionFluidHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.LavaCurrentHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.FluidScanLoadedChunksHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.EyeFluidSurfaceBoundaryHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.PlayerEyeFluidSampleHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.PlayerEyeSubmersionHook;
import me.wolfii.legacyparkourcompat.mechanic.water.BodyWaterProjection;
import net.minecraft.util.Mth;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.core.BlockPos;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityFluidInteraction;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.minecraft.world.entity.vehicle.boat.Boat;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityFluidInteraction.class)
abstract class EntityFluidInteractionMixin implements BodyWaterProjection {
    @Unique private boolean legacyparkourcompat$bodyWaterObserved;
    @Unique private boolean legacyparkourcompat$bodyWaterUnsupported;
    @Unique private boolean legacyparkourcompat$bodyWaterContact;
    @Unique private double legacyparkourcompat$bodyWaterDepth;

    @Override
    public boolean legacyparkourcompat$bodyWaterContact(boolean nativeContact) {
        return legacyparkourcompat$bodyWaterObserved && !legacyparkourcompat$bodyWaterUnsupported
            ? legacyparkourcompat$bodyWaterContact : nativeContact;
    }

    @Override
    public double legacyparkourcompat$bodyWaterDepth(double nativeDepth) {
        return legacyparkourcompat$bodyWaterObserved && !legacyparkourcompat$bodyWaterUnsupported
            ? legacyparkourcompat$bodyWaterDepth : nativeDepth;
    }

    @Inject(method = "update(Lnet/minecraft/world/entity/Entity;Z)V", at = @At("HEAD"))
    private void legacyparkourcompat$resetBodyWater(Entity entity, boolean ignoreCurrent, CallbackInfo callback,
        @Share("bodyWaterSurface") LocalRef<BodyWaterSurfaceHook> surface) {
        legacyparkourcompat$bodyWaterObserved = false;
        legacyparkourcompat$bodyWaterUnsupported = false;
        legacyparkourcompat$bodyWaterContact = false;
        legacyparkourcompat$bodyWaterDepth = 0.0;
        if (entity instanceof LocalPlayer && !entity.isPassenger()) {
            surface.set(MovementRuntime.find(BodyWaterSurfaceHook.class, entity).orElse(null));
        }
    }

    @Inject(method = "update(Lnet/minecraft/world/entity/Entity;Z)V", at = @At(value = "INVOKE", target =
        "Lnet/minecraft/world/level/material/FluidState;getHeight(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)F"),
        require = 1)
    private void legacyparkourcompat$observeBodyWater(Entity entity, boolean ignoreCurrent, CallbackInfo callback,
        @Local FluidState fluidState, @Local BlockPos.MutableBlockPos pos, @Local AABB box,
        @Share("bodyWaterSurface") LocalRef<BodyWaterSurfaceHook> surface) {
        legacyparkourcompat$recordBodyWater(surface.get(), entity, fluidState, pos.getY(), box.minY);
    }

    @Unique
    private void legacyparkourcompat$recordBodyWater(@Nullable BodyWaterSurfaceHook hook, Entity entity,
        FluidState fluidState, int cellY, double boxMinY) {
        if (hook == null || legacyparkourcompat$bodyWaterUnsupported || !fluidState.is(FluidTags.WATER)) {
            return;
        }

        var ownHeight = hook.historicalHeight(entity, fluidState);
        if (ownHeight.isEmpty()) {
            legacyparkourcompat$bodyWaterUnsupported = true;
            return;
        }

        // Observe the cell before the native surface check. The 1.13 float addition
        // can round differently, and an exact lower-face match still counts as contact.
        legacyparkourcompat$bodyWaterObserved = true;
        double top = cellY + ownHeight.get();
        if (top >= boxMinY) {
            legacyparkourcompat$bodyWaterContact = true;
            legacyparkourcompat$bodyWaterDepth = Math.max(legacyparkourcompat$bodyWaterDepth, top - boxMinY);
        }
    }

    @WrapOperation(method = "update", at = @At(value = "INVOKE", target =
        "Lnet/minecraft/world/level/material/FluidState;getFlow(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/phys/Vec3;"))
    private Vec3 legacyparkourcompat$localPlayerFluidCurrent(FluidState state, BlockGetter level, BlockPos pos,
                                                             Operation<Vec3> original, Entity entity,
                                                             boolean ignoreCurrent) {
        return MovementRuntime.withLocalPlayerFluidCurrent(entity,
            () -> original.call(state, level, pos));
    }

    @Redirect(
        method = "update(Lnet/minecraft/world/entity/Entity;Z)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getEyeY()D")
    )
    private double legacyparkourcompat$eyeFluidSampleY(Entity entity) {
        double eyeY = entity.getEyeY();
        return MovementRuntime.find(PlayerEyeFluidSampleHook.class, entity)
            .map(hook -> hook.sampleY(entity, eyeY))
            .orElse(eyeY);
    }

    @Definition(id = "eyeY", local = @Local(type = double.class, ordinal = 1))
    @Definition(id = "fluidTop", local = @Local(type = double.class, ordinal = 3))
    @Expression("eyeY <= fluidTop")
    @ModifyExpressionValue(
        method = "update(Lnet/minecraft/world/entity/Entity;Z)V",
        at = @At("MIXINEXTRAS:EXPRESSION"),
        require = 1
    )
    private boolean legacyparkourcompat$eyeFluidUpperBoundary(boolean vanilla,
        @Local(argsOnly = true) Entity entity,
        @Local(ordinal = 1) double eyeY,
        @Local(ordinal = 3) double fluidTop,
        @Local FluidState fluidState,
        @Local BlockPos.MutableBlockPos mutablePos) {
        if (entity instanceof Player
            // Chest boats and rafts are modern-only; preserve their native eye-fluid path.
            && !(entity.getVehicle() instanceof AbstractBoat && !(entity.getVehicle() instanceof Boat))
            && mutablePos.getX() == entity.getBlockX()
            && mutablePos.getY() == Mth.floor(eyeY)
            && mutablePos.getZ() == entity.getBlockZ()) {
            // This expression executes inside the scanner's existing tracker/tag and loaded-scan gates.
            // Restrict the historical predicate to the actual sampled eye block, not body fluid cells.
            var historical = MovementRuntime.find(PlayerEyeSubmersionHook.class, entity)
                .flatMap(hook -> hook.isSubmerged(
                    fluidState,
                    true,
                    entity.getVehicle() instanceof Boat,
                    eyeY,
                    mutablePos.getY()
                ));
            if (historical.isPresent()) {
                return historical.get();
            }
        }
        int comparison = Double.compare(eyeY, fluidTop);
        if (entity instanceof Player) {
            return MovementRuntime.find(EyeFluidSurfaceBoundaryHook.class, entity)
                .map(hook -> hook.upperBoundaryComparison(eyeY, fluidTop, comparison))
                .orElse(comparison) <= 0;
        }
        return vanilla;
    }

    @WrapOperation(method = "update", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/world/entity/EntityFluidInteraction;hasFluidAndLoaded(Lnet/minecraft/world/level/Level;IIIIII)Z"))
    private boolean legacyparkourcompat$fluidLoadedChunks(Level level, int x0, int y0, int z0, int x1, int y1, int z1,
        Operation<Boolean> original, @Local(argsOnly = true) Entity entity, @Local AABB box,
        @Share("bodyWaterSurface") LocalRef<BodyWaterSurfaceHook> surface) {
        if (entity instanceof Player player) {
            var historical = MovementRuntime.find(FluidScanLoadedChunksHook.class, player)
                .flatMap(hook -> hook.scanAllowed(player, MovementRuntime.profile(player).target()));
            if (historical.isPresent()) {
                return historical.get();
            }
        }

        boolean nativeAdmission = original.call(level, x0, y0, z0, x1, y1, z1);
        BodyWaterSurfaceHook bodySurface = surface.get();
        if (!nativeAdmission && bodySurface != null && x0 + 1 == x1 - 1 && z0 + 1 == z1 - 1
            && entity instanceof LocalPlayer player && OrdinaryPlayerContext.matches(player)
            && player.getPose() == Pose.STANDING && !player.getAbilities().flying
            && player.getBbWidth() == 0.6F && player.getBbHeight() == 1.8F
            && MovementRuntime.find(BodyWaterScanAdmissionHook.class, player)
                .map(hook -> hook.admitsColumn(y0, y1)).orElse(false)
            && original.call(level, x0 + 1, y0, z0 + 1, x1 - 1, y1, z1 - 1)) {
            // The narrower preflight confirms a loaded fluid-bearing column.
            // Keep the rejected padded result for native eye and current handling.
            BlockPos.MutableBlockPos cell = new BlockPos.MutableBlockPos();
            for (int y = y0; y <= y1; y++) {
                cell.set(x0 + 1, y, z0 + 1);
                legacyparkourcompat$recordBodyWater(bodySurface, player, level.getFluidState(cell), y, box.minY);
            }
        }
        return nativeAdmission;
    }

    @Redirect(
        method = "update(Lnet/minecraft/world/entity/Entity;Z)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/BlockGetter;getFluidState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/material/FluidState;"
        )
    )
    private FluidState legacyparkourcompat$fluidInteractionState(BlockGetter level, BlockPos pos, Entity entity) {
        FluidState vanilla = level.getFluidState(pos);
        var behavior = MovementRuntime.find(FluidInteractionFluidHook.class, entity);
        return behavior.isPresent() ? behavior.get().fluidState(entity, vanilla) : vanilla;
    }

    @Redirect(
        method = "update(Lnet/minecraft/world/entity/Entity;Z)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/Entity;getFluidInteractionBox()Lnet/minecraft/world/phys/AABB;"
        )
    )
    private @Nullable AABB legacyparkourcompat$fluidInteractionBox(Entity entity) {
        AABB vanilla = entity.getFluidInteractionBox();
        var behavior = MovementRuntime.find(FluidInteractionBoxHook.class, entity);
        return behavior.isPresent() ? behavior.get().interactionBox(entity, vanilla) : vanilla;
    }

    @Inject(method = "applyCurrentTo", at = @At("HEAD"), cancellable = true)
    private void legacyparkourcompat$applyLavaCurrent(
        TagKey<Fluid> fluid,
        Entity entity,
        double scale,
        CallbackInfo callback
    ) {
        if (!fluid.equals(FluidTags.LAVA)) {
            return;
        }
        MovementRuntime.find(LavaCurrentHook.class, entity).ifPresent(behavior -> {
            if (!behavior.allowLavaCurrent(entity, true)) {
                callback.cancel();
            }
        });
    }
}
