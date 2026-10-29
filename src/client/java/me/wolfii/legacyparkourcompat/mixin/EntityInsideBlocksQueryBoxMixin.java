package me.wolfii.legacyparkourcompat.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.wolfii.legacyparkourcompat.mechanic.EntityInsideBlocksQueryBoxContext;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.EntityInsideBlocksQueryBoxHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;

/** Scopes the selected player callback query box around the native movement-list scan. */
@Mixin(Entity.class)
abstract class EntityInsideBlocksQueryBoxMixin {
    @WrapMethod(method = "checkInsideBlocks(Ljava/util/List;Lnet/minecraft/world/entity/InsideBlockEffectApplier$StepBasedCollector;)V")
    private void legacyparkourcompat$sharedHistoricalQueryBox(
        List<?> movements,
        InsideBlockEffectApplier.StepBasedCollector collector,
        Operation<Void> original
    ) {
        Entity entity = (Entity)(Object)this;
        if (!(entity instanceof LocalPlayer) || !entity.level().isClientSide()
            || entity.isRemoved() || entity.noPhysics
            || !MovementRuntime.find(EntityInsideBlocksQueryBoxHook.class, entity)
                .map(EntityInsideBlocksQueryBoxHook::shareFinalQueryBox)
                .orElse(false)) {
            original.call(movements, collector);
            return;
        }

        // 1.21.2/1.21.3 Entity.checkInsideBlocks shares one final-box query
        // across movement records. The wrapped native loop still owns traversal,
        // visitor iteration labels, contact order, and effect collection.
        AABB queryBox = entity.getBoundingBox().deflate(1.0E-5F);
        EntityInsideBlocksQueryBoxContext.Scope scope =
            EntityInsideBlocksQueryBoxContext.enter(entity, queryBox);
        try {
            original.call(movements, collector);
        } finally {
            scope.close();
        }
    }
}
