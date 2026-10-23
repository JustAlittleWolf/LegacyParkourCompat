package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.LavaMaterialScanUpperBoundHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.LavaPresenceHook;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Dispatches the player query to its historical hook and otherwise leaves
 * vanilla unchanged. The immediate query was replaced by a collision-pass
 * cache in 1.14.0 (Ornithe Feather Gen2 build 2, {@code Entity.move} and
 * {@code Entity.isInLava}), 1.14.1–1.14.3 (Yarn builds 10, 7 and 13,
 * {@code Entity.move}, {@code Entity.setInLava} and {@code Entity.isInLava}),
 * and 1.14.4 (Ornithe Feather Gen2 build 2, the same members). Profiles from
 * those releases therefore receive no old query hook and keep the cached native
 * behavior. MovementRuntime also keeps logical servers and non-player entities
 * on the native path.
 */
@Mixin(Entity.class)
abstract class EntityLavaPresenceMixin {
    @Inject(method = "isInLava", at = @At("HEAD"), cancellable = true)
    private void legacyparkourcompat$useHistoricalLavaPresence(CallbackInfoReturnable<Boolean> cir) {
        Entity entity = (Entity) (Object) this;
        var query = MovementRuntime.find(LavaPresenceHook.class, entity);
        if (query.isEmpty()) {
            return;
        }
        LavaMaterialScanUpperBoundHook scanUpperBound = MovementRuntime.find(
            LavaMaterialScanUpperBoundHook.class, entity
        ).orElseThrow(() -> new IllegalStateException("Historical lava scan bound is missing"));
        cir.setReturnValue(query.get().isInLava(entity, scanUpperBound));
    }
}
