package me.wolfii.legacyparkourcompat.mixin.collision;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(Entity.class)
abstract class EntityMovementCollisionsMixin {
    @Redirect(method = "collectCollidersIgnoringWorldBorder(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/level/Level;Ljava/util/List;Lnet/minecraft/world/phys/AABB;)Ljava/util/List;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBlockCollisions(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/AABB;)Ljava/lang/Iterable;"))
    private static Iterable<VoxelShape> legacyparkourcompat$entityCandidates(Level level, Entity source, AABB query,
        @Local(argsOnly = true, index = 2) List<VoxelShape> entityCandidates) {
        if (!MovementRuntime.appliesTo(source)) {
            return level.getBlockCollisions(source, query);
        }
        CollisionContext context = MovementRuntime.movementCollisionContext(CollisionContext.of(source), query);
        return MovementRuntime.collectHistoricalMovementBlocks(source, level, context, query, entityCandidates,
            () -> level.getBlockCollisionsFromContext(context, query));
    }

    @Redirect(method = "collectCollidersIgnoringWorldBorder(Lnet/minecraft/world/phys/shapes/CollisionContext;Lnet/minecraft/world/level/Level;Ljava/util/List;Lnet/minecraft/world/phys/AABB;)Ljava/util/List;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBlockCollisionsFromContext(Lnet/minecraft/world/phys/shapes/CollisionContext;Lnet/minecraft/world/phys/AABB;)Ljava/lang/Iterable;"))
    private static Iterable<VoxelShape> legacyparkourcompat$contextCandidates(Level level, CollisionContext context, AABB query,
        @Local(argsOnly = true, index = 2) List<VoxelShape> entityCandidates) {
        CollisionContext movementContext = MovementRuntime.movementCollisionContext(context, query);
        Entity player = MovementRuntime.playerFrom(context);
        return MovementRuntime.collectHistoricalMovementBlocks(player, level, movementContext, query, entityCandidates,
            () -> level.getBlockCollisionsFromContext(movementContext, query));
    }
}
