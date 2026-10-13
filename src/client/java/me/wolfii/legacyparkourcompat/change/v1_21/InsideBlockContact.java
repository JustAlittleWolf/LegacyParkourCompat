package me.wolfii.legacyparkourcompat.change.v1_21;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.InsideBlockContactHook;
import net.minecraft.world.entity.player.Player;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.21.1/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   tick.
 * 1.21.1/mojmap/net/minecraft/world/entity/Entity.java:
 *   move.
 * 1.21.1/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   aiStep.
 * 1.21.1/mojmap/net/minecraft/world/entity/player/Player.java:
 *   aiStep.
 * 1.21.1/mojmap/net/minecraft/world/level/block/Blocks.java:
 *   class declaration / field initialization.
 * 1.21.1/mojmap/net/minecraft/world/level/block/WebBlock.java:
 *   entityInside.
 * 1.21.3/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   isEffectiveAi.
 * 1.21.3/mojmap/net/minecraft/world/entity/Entity.java:
 *   isControlledByLocalInstance.
 *   applyEffectsFromBlocks.
 *   applyEffectsFromBlocks.
 *   applyEffectsFromBlocks.
 *   setAsInsidePortal.
 * 1.21.3/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   aiStep.
 *   aiStep.
 * 1.21.3/mojmap/net/minecraft/world/level/BlockGetter.java:
 *   boxTraverseBlocks.
 * 1.21.3/mojmap/net/minecraft/world/level/block/Blocks.java:
 *   class declaration / field initialization.
 * 1.21.3/mojmap/net/minecraft/world/level/block/EndPortalBlock.java:
 *   class declaration / field initialization.
 * 1.21.3/mojmap/net/minecraft/world/level/block/WebBlock.java:
 *   entityInside.
 * 1.21.3/mojmap/net/minecraft/world/level/block/state/BlockBehaviour.java:
 *   getEntityInsideCollisionShape.
 * 1.21.3/mojmap/net/minecraft/world/phys/shapes/Shapes.java:
 *   block.
 * 1.21.4/mojmap/net/minecraft/world/entity/Entity.java:
 *   applyEffectsFromBlocks.
 *   move.
 *   applyEffectsFromBlocks.
 *   checkInsideBlocks.
 * 1.21.4/mojmap/net/minecraft/world/level/block/state/BlockBehaviour.java:
 *   getEntityInsideCollisionShape.
 * 1.21.4/mojmap/net/minecraft/world/phys/shapes/Shapes.java:
 *   block.
 * 1.21.5/mojmap/net/minecraft/world/entity/Entity.java:
 *   move.
 *   applyEffectsFromBlocks.
 *   checkInsideBlocks.
 * 1.21.5/mojmap/net/minecraft/world/level/block/HoneyBlock.java:
 *   entityInside.
 * 1.21.5/mojmap/net/minecraft/world/level/block/PowderSnowBlock.java:
 *   entityInside.
 * No exact independently accepted source binding is established for this operation.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_21)
public final class InsideBlockContact implements InsideBlockContactHook {
    @Override
    public double inset(Player player, double vanilla) {
        return 1.0E-7;
    }
}
