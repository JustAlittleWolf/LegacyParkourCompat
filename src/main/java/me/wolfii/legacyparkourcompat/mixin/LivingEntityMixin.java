package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.MovementChunkLookupBehavior;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntity.class)
abstract class LivingEntityMixin {
    @Redirect(
        method = "travelInAir",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/Level;hasChunkAt(Lnet/minecraft/core/BlockPos;)Z"
        )
    )
    private boolean legacyParkourCompat$hasMovementChunk(Level level, BlockPos position) {
        LivingEntity entity = (LivingEntity) (Object) this;
        var behavior = MovementRuntime.find(MovementChunkLookupBehavior.class, entity);
        if (behavior.isEmpty() || !(entity instanceof net.minecraft.world.entity.player.Player)) {
            return level.hasChunkAt(position);
        }
        return behavior.orElseThrow().hasChunkAt(entity, level, position, () -> level.hasChunkAt(position));
    }
}
