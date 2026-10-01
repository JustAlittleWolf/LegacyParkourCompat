package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.hook.BlockLandingBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.CollisionRestitutionBehavior;
import me.wolfii.legacyparkourcompat.mixin.accessor.EntityInvoker;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Entity.class)
public abstract class EntityCollisionMixin {
    @Redirect(
        method = "move(Lnet/minecraft/world/entity/MoverType;Lnet/minecraft/world/phys/Vec3;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/Entity;restituteMovementAfterCollisions(Lnet/minecraft/world/level/block/state/BlockState;ZZLnet/minecraft/world/phys/Vec3;)V"
        ),
        require = 1
    )
    private void legacyparkourcompat$dispatchCollisionRestitution(
        Entity entity,
        BlockState effectState,
        boolean xCollision,
        boolean zCollision,
        Vec3 movement
    ) {
        VanillaCall vanilla = () -> ((EntityInvoker)(Object)entity)
            .legacyparkourcompat$invokeRestituteMovementAfterCollisions(effectState, xCollision, zCollision, movement);
        var blockLanding = MovementRuntime.find(BlockLandingBehavior.class, effectState.getBlock(), entity);
        MovementRuntime.find(CollisionRestitutionBehavior.class, entity)
            .ifPresentOrElse(
                behavior -> behavior.restituteAfterCollisions(
                    entity, effectState, xCollision, zCollision, movement, blockLanding, vanilla
                ),
                vanilla::run
            );
    }
}
