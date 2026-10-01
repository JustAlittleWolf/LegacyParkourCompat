package me.wolfii.legacyparkourcompat.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Block.class)
abstract class BlockPushEntitiesMixin {
    @Redirect(
        method = "pushEntitiesUp(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;teleportRelative(DDD)V")
    )
    private static void legacyparkourcompat$preserveFarmlandPlayerPosition(Entity entity, double x, double y, double z) {
        if (!FarmlandEntityPushContext.shouldSuppress(entity)) {
            entity.teleportRelative(x, y, z);
        }
    }
}
