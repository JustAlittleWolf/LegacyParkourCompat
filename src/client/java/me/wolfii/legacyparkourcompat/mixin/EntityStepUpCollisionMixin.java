package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.CollisionAxisOrderContext;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.wolfii.legacyparkourcompat.mechanic.BorderAdmissionContext;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.StaticWorldBorderCollisionShape;
import me.wolfii.legacyparkourcompat.mechanic.hook.border.BorderCollisionAdmissionHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.StepAssistVerticalReturnHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.StepUpCollisionHook;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;
import java.util.function.DoubleUnaryOperator;

@Mixin(Entity.class)
abstract class EntityStepUpCollisionMixin {
    @WrapOperation(
        method = "collide(Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;",
        at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/entity/Entity;collideBoundingBox(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/AABB;Lnet/minecraft/world/level/Level;Ljava/util/List;)Lnet/minecraft/world/phys/Vec3;"))
    private Vec3 legacyparkourcompat$ordinaryCollision(Entity entity, Vec3 movement, AABB box, Level level,
                                                        List<VoxelShape> entityCollisions, Operation<Vec3> original) {
        Vec3 clipped = original.call(entity, movement, box, level, entityCollisions);
        return MovementRuntime.clipOrdinaryCollision(entity, movement, box, entityCollisions, clipped);
    }

    @WrapMethod(method = "collide(Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;")
    private Vec3 legacyparkourcompat$stepUpCollision(Vec3 movement, Operation<Vec3> original) {
        Entity entity = (Entity) (Object) this;
        StepUpCollisionHook hook = MovementRuntime.find(StepUpCollisionHook.class, entity).orElse(null);
        if (hook == null || hook.usesNativeResolver()) {
            return original.call(movement);
        }

        AABB box = entity.getBoundingBox();
        var border = entity.level().getWorldBorder();
        var historicalStaticShape = StaticWorldBorderCollisionShape.resolve(border, entity);
        var admission = MovementRuntime.find(BorderCollisionAdmissionHook.class, entity)
            .flatMap(borderHook -> borderHook.includeBorder(
                MovementRuntime.profile(entity).target(), border, box,
                historicalStaticShape.orElseGet(border::getCollisionShape)))
            .map(include -> new BorderAdmissionContext.CollisionAdmission(
                entity, include, historicalStaticShape.orElse(null)))
            .orElse(null);
        return BorderAdmissionContext.withCollision(admission, () -> {
            List<VoxelShape> entityCollisions = entity.level().getEntityCollisions(entity, box.expandTowards(movement));
            Vec3 ordinary = movement.lengthSqr() == 0.0
                ? movement
                : MovementRuntime.clipOrdinaryCollision(entity, movement, box, entityCollisions,
                    Entity.collideBoundingBox(entity, movement, box, entity.level(), entityCollisions));
            StepUpCollisionHook.Result result = hook.resolveStepUp(
                movement, ordinary, box, entity.maxUpStep(), entity.onGround(),
                new StepUpCollisionHook.CollisionCall() {
                    @Override
                    public Vec3 collide(Vec3 requested, AABB queryBox) {
                        return Entity.collideBoundingBox(entity, requested, queryBox, entity.level(), entityCollisions);
                    }

                    @Override
                    public double downwardReturn(double nativeDescent, float configuredStepHeight) {
                        return MovementRuntime.downwardStepReturn(nativeDescent, configuredStepHeight, entity);
                    }

                    @Override
                    public double stepAssistVerticalReturn(double achievedStepY, double requestedY,
                                                          DoubleUnaryOperator correctionClipper) {
                        return MovementRuntime.find(StepAssistVerticalReturnHook.class, entity)
                            .map(hook -> hook.verticalReturn(achievedStepY, requestedY,
                                correctionClipper::applyAsDouble))
                            .orElseThrow(() -> new IllegalStateException(
                                "Step-assist vertical-return hook is missing for the selected movement profile"));
                    }

                    @Override
                    public StepUpCollisionHook.AxisCollisionCall sweep(Vec3 requested, AABB queryBox) {
                        List<VoxelShape> shapes = CollisionAxisOrderContext.withBlockCollectionDisabled(
                            () -> Entity.collectAllColliders(
                                entity, entity.level(), queryBox.expandTowards(requested)));
                        return (axis, movingBox, distance) -> MovementRuntime.clipMovementAxis(
                            entity, axis, movingBox, shapes, distance,
                            () -> Shapes.collide(axis, movingBox, shapes, distance));
                    }
                });
            if (result.boundingBox() != null) {
                CollisionAxisOrderContext.resolvedBox(result.boundingBox());
            }
            return result.movement();
        });
    }
}
