package me.wolfii.legacyparkourcompat.mixin.border;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import me.wolfii.legacyparkourcompat.mechanic.BorderAdmissionContext;
import me.wolfii.legacyparkourcompat.mechanic.CollisionBorderShapeContext;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.border.BorderCollisionAdmissionHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.border.WorldBorderCollisionHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.worldborder.StaticWorldBorderCollisionShapeHook;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Entity.class)
public abstract class EntityBorderCollisionMixin {
    @WrapMethod(method = "collide")
    private Vec3 legacyparkourcompat$borderContext(Vec3 movement, Operation<Vec3> original) {
        Entity entity = (Entity) (Object) this;
        var admission = MovementRuntime.find(BorderCollisionAdmissionHook.class, entity)
            .flatMap(hook -> hook.includeBorder(MovementRuntime.profile(entity).target(),
                entity.level().getWorldBorder(), entity.getBoundingBox()))
            .map(include -> new BorderAdmissionContext.CollisionAdmission(entity, include))
            .orElse(null);
        return BorderAdmissionContext.withCollision(admission, () -> original.call(movement));
    }

    @WrapOperation(
        method = "collectCollidersIgnoringWorldBorder(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/level/Level;Ljava/util/List;Lnet/minecraft/world/phys/AABB;)Ljava/util/List;",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/border/WorldBorder;isInsideCloseToBorder(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/AABB;)Z")
    )
    private static boolean legacyparkourcompat$admitBorder(WorldBorder border, Entity entity,
                                                         AABB sweptBox, Operation<Boolean> original) {
        var historicalCollision = MovementRuntime.find(WorldBorderCollisionHook.class, entity)
            .map(hook -> hook.collision(border, entity, sweptBox)).orElse(null);
        if (historicalCollision != null) {
            boolean nativeAdmission = original.call(border, entity, sweptBox);
            var frame = CollisionBorderShapeContext.current();
            if (frame != null) {
                frame.recordHistoricalCollision(entity, historicalCollision, nativeAdmission);
            }
            return historicalCollision.insideCloseToBorder();
        }

        var admission = BorderAdmissionContext.collision();
        if (admission != null && admission.entity() == entity && MovementRuntime.appliesTo(entity)) {
            var frame = CollisionBorderShapeContext.current();
            if (frame != null) {
                frame.recordAdmission(entity, admission.includeBorder(), original.call(border, entity, sweptBox));
            }
            return admission.includeBorder();
        }
        return original.call(border, entity, sweptBox);
    }

    @WrapOperation(
        method = "collectCollidersIgnoringWorldBorder(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/level/Level;Ljava/util/List;Lnet/minecraft/world/phys/AABB;)Ljava/util/List;",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/border/WorldBorder;getCollisionShape()Lnet/minecraft/world/phys/shapes/VoxelShape;")
    )
    private static VoxelShape legacyparkourcompat$captureAdmittedBorder(WorldBorder border,
        Operation<VoxelShape> original, @Local(argsOnly = true) Entity source) {
        var frame = CollisionBorderShapeContext.current();
        var historicalCollision = frame == null ? null : frame.historicalCollision(source);
        VoxelShape shape;
        if (historicalCollision != null) {
            shape = historicalCollision.shape();
        } else if (MovementRuntime.appliesTo(source)
            && border.getLerpTime() == 0L && border.getLerpSpeed() == 0.0) {
            shape = MovementRuntime.find(StaticWorldBorderCollisionShapeHook.class, source)
                .flatMap(hook -> hook.collisionShape(
                    border.getCenterX(), border.getCenterZ(), border.getSize(), border.getAbsoluteMaxSize()
                ))
                .orElseGet(() -> original.call(border));
        } else {
            shape = original.call(border);
        }
        if (frame != null) {
            frame.recordShape(source, shape);
        }
        return shape;
    }
}
