package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.LavaMaterialScanUpperBoundHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.LavaPresenceHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.util.Optional;

/**
 * Dispatches historical local-player lava presence when a provider has a
 * resolved result, otherwise preserving the selected profile's native value.
 * MovementRuntime keeps logical servers and non-player entities native.
 */
@Mixin(Entity.class)
abstract class EntityLavaPresenceMixin {
    @Inject(method = "isInLava", at = @At("HEAD"), cancellable = true)
    private void legacyparkourcompat$useHistoricalLavaPresence(CallbackInfoReturnable<Boolean> cir) {
        Entity entity = (Entity) (Object) this;
        if (!(entity instanceof LocalPlayer)) {
            return;
        }
        MovementRuntime.find(LavaPresenceHook.class, entity).ifPresent(hook -> {
            Optional<LavaMaterialScanUpperBoundHook> scanUpperBound = MovementRuntime.find(
                LavaMaterialScanUpperBoundHook.class, entity
            );
            hook.isInLava(entity, scanUpperBound).ifPresent(cir::setReturnValue);
        });
    }
}
