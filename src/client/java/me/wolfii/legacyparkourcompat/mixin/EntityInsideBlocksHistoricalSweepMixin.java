package me.wolfii.legacyparkourcompat.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.wolfii.legacyparkourcompat.mechanic.EntityHistoricalCallbackState;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.HistoricalBlockCallbackSweepHook;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

/** Dispatches the list-level historical scan only for its bounded local-client profile. */
@Mixin(Entity.class)
abstract class EntityInsideBlocksHistoricalSweepMixin implements EntityHistoricalCallbackState {
    @Unique
    private boolean legacyparkourcompat$wasOnFireBeforeMove;

    /**
     * 1.21.2/1.21.3 Mojmap Entity.move samples its wasOnFire field at the start of
     * the non-noPhysics branch, before piston limiting and collision work.
     * 26.2 has only an applyEffectsFromBlocks local, so retain the old sample here.
     */
    @Inject(method = "move(Lnet/minecraft/world/entity/MoverType;Lnet/minecraft/world/phys/Vec3;)V", at = @At("HEAD"))
    private void legacyparkourcompat$captureHistoricalFireState(MoverType type, Vec3 movement, CallbackInfo ci) {
        Entity entity = (Entity)(Object)this;
        if (entity instanceof LocalPlayer && entity.level().isClientSide() && !entity.noPhysics
            && MovementRuntime.find(HistoricalBlockCallbackSweepHook.class, entity)
                .filter(HistoricalBlockCallbackSweepHook::usesHistoricalSweep).isPresent()) {
            legacyparkourcompat$wasOnFireBeforeMove = entity.isOnFire();
        }
    }

    @Override
    public boolean legacyparkourcompat$wasOnFireBeforeMove() {
        return legacyparkourcompat$wasOnFireBeforeMove;
    }

    @WrapMethod(method = "applyEffectsFromBlocks(Ljava/util/List;)V")
    private void legacyparkourcompat$historicalInsideBlockSweep(List<?> movements, Operation<Void> original) {
        Entity entity = (Entity)(Object)this;
        if (!(entity instanceof LocalPlayer) || !entity.level().isClientSide()) {
            original.call(movements);
            return;
        }

        var sweep = MovementRuntime.find(HistoricalBlockCallbackSweepHook.class, entity)
            .filter(HistoricalBlockCallbackSweepHook::usesHistoricalSweep);
        if (sweep.isPresent() && sweep.get().supportsBatch(entity, movements)) {
            sweep.get().apply(entity, movements);
        } else {
            original.call(movements);
        }
    }
}
