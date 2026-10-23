package me.wolfii.legacyparkourcompat.mixin.collision;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Entity.class)
abstract class EntityMovementCollisionsMixin {
    @Redirect(method = "collectCollidersIgnoringWorldBorder(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/level/Level;Ljava/util/List;Lnet/minecraft/world/phys/AABB;)Ljava/util/List;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBlockCollisions(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/AABB;)Ljava/lang/Iterable;"))
    private static Iterable<VoxelShape> legacyparkourcompat$entityCandidates(Level level, Entity source, AABB query) {
        if (!MovementRuntime.appliesTo(source)) {
            return level.getBlockCollisions(source, query);
        }
        return level.getBlockCollisionsFromContext(MovementRuntime.movementCollisionContext(CollisionContext.of(source), query), query);
    }

    @Redirect(method = "collectCollidersIgnoringWorldBorder(Lnet/minecraft/world/phys/shapes/CollisionContext;Lnet/minecraft/world/level/Level;Ljava/util/List;Lnet/minecraft/world/phys/AABB;)Ljava/util/List;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBlockCollisionsFromContext(Lnet/minecraft/world/phys/shapes/CollisionContext;Lnet/minecraft/world/phys/AABB;)Ljava/lang/Iterable;"))
    private static Iterable<VoxelShape> legacyparkourcompat$contextCandidates(Level level, CollisionContext context, AABB query) {
        return level.getBlockCollisionsFromContext(MovementRuntime.movementCollisionContext(context, query), query);
    }
}
