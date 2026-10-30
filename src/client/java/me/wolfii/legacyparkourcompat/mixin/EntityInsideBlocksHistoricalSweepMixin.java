package me.wolfii.legacyparkourcompat.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.HistoricalBlockCallbackSweepHook;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Entity.Movement;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;

/** Dispatches the list-level historical scan only for its bounded local-client profile. */
@Mixin(Entity.class)
abstract class EntityInsideBlocksHistoricalSweepMixin {
    @WrapMethod(method = "applyEffectsFromBlocks(Ljava/util/List;)V")
    private void legacyparkourcompat$historicalInsideBlockSweep(List<Movement> movements, Operation<Void> original) {
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
