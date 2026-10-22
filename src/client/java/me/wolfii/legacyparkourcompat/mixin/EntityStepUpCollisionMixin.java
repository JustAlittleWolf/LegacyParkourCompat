package me.wolfii.legacyparkourcompat.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.wolfii.legacyparkourcompat.mechanic.BorderAdmissionContext;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.border.BorderCollisionAdmissionHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.StepUpCollisionHook;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;

@Mixin(Entity.class)
abstract class EntityStepUpCollisionMixin {
    @WrapMethod(method = "collide(Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;")
    private Vec3 legacyparkourcompat$stepUpCollision(Vec3 movement, Operation<Vec3> original) {
        Entity entity = (Entity) (Object) this;
        StepUpCollisionHook hook = MovementRuntime.find(StepUpCollisionHook.class, entity).orElse(null);
        if (hook == null || hook.usesNativeResolver()) {
            return original.call(movement);
        }

        AABB box = entity.getBoundingBox();
        var admission = MovementRuntime.find(BorderCollisionAdmissionHook.class, entity)
            .flatMap(borderHook -> borderHook.includeBorder(
                MovementRuntime.profile(entity).target(), entity.level().getWorldBorder(), box))
            .map(include -> new BorderAdmissionContext.CollisionAdmission(entity, include))
            .orElse(null);
        return BorderAdmissionContext.withCollision(admission, () -> {
            List<VoxelShape> entityCollisions = entity.level().getEntityCollisions(entity, box.expandTowards(movement));
            Vec3 ordinary = movement.lengthSqr() == 0.0
                ? movement
                : Entity.collideBoundingBox(entity, movement, box, entity.level(), entityCollisions);
            return hook.resolveStepUp(movement, ordinary, box, entity.maxUpStep(), entity.onGround(),
                (requested, queryBox) -> Entity.collideBoundingBox(
                    entity, requested, queryBox, entity.level(), entityCollisions));
        });
    }
}
