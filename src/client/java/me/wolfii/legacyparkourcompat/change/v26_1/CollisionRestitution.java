package me.wolfii.legacyparkourcompat.change.v26_1;

import java.util.Optional;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockBounceHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockLandingHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.CollisionRestitutionHook;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 26.1.2/unobfuscated/net/minecraft/client/player/AbstractClientPlayer.java:
 *   class declaration / field initialization.
 * 26.1.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java:
 *   isShiftKeyDown.
 *   aiStep.
 *   class declaration / field initialization.
 *   aiStep.
 * 26.1.2/unobfuscated/net/minecraft/util/Mth.java:
 *   lerp.
 * 26.1.2/unobfuscated/net/minecraft/world/entity/Avatar.java:
 *   class declaration / field initialization.
 * 26.1.2/unobfuscated/net/minecraft/world/entity/Entity.java:
 *   move.
 *   isSuppressingBounce.
 *   collideWithShapes.
 *   move.
 * 26.1.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java:
 *   aiStep.
 *   travelInAir.
 *   handleRelativeFrictionAndCalculateMovement.
 *   shouldDiscardFriction.
 *   aiStep.
 * 26.1.2/unobfuscated/net/minecraft/world/entity/player/Player.java:
 *   canSimulateMovement.
 *   travel.
 *   travel.
 *   class declaration / field initialization.
 *   canSimulateMovement.
 * 26.1.2/unobfuscated/net/minecraft/world/level/block/BedBlock.java:
 *   updateEntityMovementAfterFallOn.
 *   class declaration / field initialization.
 *   updateEntityMovementAfterFallOn.
 * 26.1.2/unobfuscated/net/minecraft/world/level/block/Block.java:
 *   updateEntityMovementAfterFallOn.
 *   updateEntityMovementAfterFallOn.
 * 26.1.2/unobfuscated/net/minecraft/world/level/block/Blocks.java:
 *   class declaration / field initialization.
 *   registerBed.
 *   class declaration / field initialization.
 * 26.1.2/unobfuscated/net/minecraft/world/level/block/SlimeBlock.java:
 *   updateEntityMovementAfterFallOn.
 *   updateEntityMovementAfterFallOn.
 * 26.1.2/unobfuscated/net/minecraft/world/phys/shapes/Shapes.java:
 *   collide.
 * 26.2/unobfuscated/net/minecraft/client/player/AbstractClientPlayer.java:
 *   class declaration / field initialization.
 * 26.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java:
 *   isShiftKeyDown.
 *   aiStep.
 *   class declaration / field initialization.
 *   aiStep.
 * 26.2/unobfuscated/net/minecraft/tags/BlockTags.java:
 *   class declaration / field initialization.
 * 26.2/unobfuscated/net/minecraft/util/Mth.java:
 *   lerp.
 * 26.2/unobfuscated/net/minecraft/world/entity/Avatar.java:
 *   class declaration / field initialization.
 * 26.2/unobfuscated/net/minecraft/world/entity/Entity.java:
 *   move.
 *   restituteMovementAfterCollisions.
 *   isSuppressingBounce.
 *   collideWithShapes.
 *   move.
 * 26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java:
 *   createLivingAttributes.
 *   getEntityBounciness.
 *   aiStep.
 *   travel.
 *   shouldDiscardFriction.
 *   getEntityBounciness.
 *   aiStep.
 * 26.2/unobfuscated/net/minecraft/world/entity/ai/attributes/Attributes.java:
 *   class declaration / field initialization.
 *   class declaration / field initialization.
 * 26.2/unobfuscated/net/minecraft/world/entity/player/Player.java:
 *   canSimulateMovement.
 *   travel.
 *   class declaration / field initialization.
 *   canSimulateMovement.
 * 26.2/unobfuscated/net/minecraft/world/level/block/BedBlock.java:
 *   class declaration / field initialization.
 * 26.2/unobfuscated/net/minecraft/world/level/block/Block.java:
 *   getBounceRestitution.
 * 26.2/unobfuscated/net/minecraft/world/level/block/Blocks.java:
 *   class declaration / field initialization.
 *   class declaration / field initialization.
 *   class declaration / field initialization.
 * 26.2/unobfuscated/net/minecraft/world/phys/shapes/Shapes.java:
 *   collide.
 *   ACCEPT bounded fresh-entity first-travel witness; exact standalone binding may remain open.
 * Remaining source/applicability gates:
 *   Archived-to-canonical source identity for these checked secondary citations: 26.1.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java, 26.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java, 26.1.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java. Archived source identity correspondence remains unresolved for them.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V26_1)
public final class CollisionRestitution implements CollisionRestitutionHook {
    @Override
    public void restituteAfterCollisions(
        Entity entity,
        BlockState effectState,
        boolean xCollision,
        boolean zCollision,
        boolean yCollision,
        Vec3 movement,
        Optional<BlockLandingHook> blockLanding,
        Optional<BlockBounceHook> bounce,
        VanillaCall vanilla
    ) {
        Vec3 velocity = entity.getDeltaMovement();
        double x = xCollision ? 0.0 : velocity.x;
        double z = zCollision ? 0.0 : velocity.z;
        entity.setDeltaMovement(x, velocity.y, z);

        if (yCollision) {
            VanillaCall landing = () -> entity.setDeltaMovement(entity.getDeltaMovement().multiply(1.0, 0.0, 1.0));
            blockLanding.ifPresentOrElse(
                behavior -> behavior.updateMovementAfterFallOn(entity.level(), entity, bounce, landing),
                landing::run
            );
        }
    }
}
