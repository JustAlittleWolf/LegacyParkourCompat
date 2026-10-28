package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.falldistance.FallDistanceAccumulatorHook;
import net.minecraft.world.entity.Entity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Entity.class)
public abstract class EntityFallDistanceMixin {
    @Redirect(
        method = "checkFallDamage(DZLnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;)V",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/world/entity/Entity;fallDistance:D",
            opcode = Opcodes.PUTFIELD
        )
    )
    private void legacyparkourcompat$updateFallDistance(Entity entity, double updatedDistance) {
        double resolvedDistance = MovementRuntime.find(FallDistanceAccumulatorHook.class, entity)
            .map(hook -> hook.updatedDistance(updatedDistance))
            .orElse(updatedDistance);
        entity.fallDistance = resolvedDistance;
    }
}
