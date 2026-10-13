package me.wolfii.legacyparkourcompat.change.v1_18_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.InsideBlockContactHook;
import net.minecraft.world.entity.player.Player;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.18.2/mojmap/net/minecraft/world/entity/Entity.java:
 *   checkInsideBlocks.
 *   makeStuckInBlock.
 *   move.
 * 1.18.2/mojmap/net/minecraft/world/level/block/Blocks.java:
 *   class declaration / field initialization.
 * 1.18.2/mojmap/net/minecraft/world/level/block/WebBlock.java:
 *   entityInside.
 * 1.19.2/mojmap/net/minecraft/server/commands/TeleportCommand.java:
 *   teleportToPos.
 * 1.19.2/mojmap/net/minecraft/server/network/ServerGamePacketListenerImpl.java:
 *   teleport.
 * 1.19.2/mojmap/net/minecraft/util/Mth.java:
 *   floor.
 * 1.19.2/mojmap/net/minecraft/world/entity/Entity.java:
 *   move.
 *   tryCheckInsideBlocks.
 *   checkInsideBlocks.
 *   class declaration / field initialization.
 * 1.19.2/mojmap/net/minecraft/world/entity/EntityDimensions.java:
 *   makeBoundingBox.
 * 1.19.2/mojmap/net/minecraft/world/entity/EntityType.java:
 *   class declaration / field initialization.
 * 1.19.2/mojmap/net/minecraft/world/level/block/Blocks.java:
 *   class declaration / field initialization.
 * 1.19.2/mojmap/net/minecraft/world/level/block/HoneyBlock.java:
 *   entityInside.
 * 1.19.3/mojmap/net/minecraft/server/network/ServerGamePacketListenerImpl.java:
 *   teleport.
 * 1.19.3/mojmap/net/minecraft/world/entity/Entity.java:
 *   move.
 *   tryCheckInsideBlocks.
 *   class declaration / field initialization.
 * 1.19.3/mojmap/net/minecraft/world/entity/EntityDimensions.java:
 *   makeBoundingBox.
 * 1.19.3/mojmap/net/minecraft/world/entity/EntityType.java:
 *   class declaration / field initialization.
 * 1.19.3/mojmap/net/minecraft/world/level/block/Blocks.java:
 *   class declaration / field initialization.
 * 1.19.3/mojmap/net/minecraft/world/level/block/HoneyBlock.java:
 *   entityInside.
 * 1.19.4/mojmap/net/minecraft/world/entity/Entity.java:
 *   checkInsideBlocks.
 *   move.
 * 1.19.4/mojmap/net/minecraft/world/level/block/Blocks.java:
 *   class declaration / field initialization.
 * 1.19.4/mojmap/net/minecraft/world/level/block/WebBlock.java:
 *   entityInside.
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
 * 1.21.3/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   aiStep.
 * 1.21.3/mojmap/net/minecraft/world/level/BlockGetter.java:
 *   boxTraverseBlocks.
 * 1.21.3/mojmap/net/minecraft/world/level/block/Blocks.java:
 *   class declaration / field initialization.
 * 1.21.3/mojmap/net/minecraft/world/level/block/WebBlock.java:
 *   entityInside.
 * 1.21.3/mojmap/net/minecraft/world/level/block/state/BlockBehaviour.java:
 *   getEntityInsideCollisionShape.
 * 1.21.3/mojmap/net/minecraft/world/phys/shapes/Shapes.java:
 *   block.
 * Remaining source/applicability gates:
 *   Current V1_19 group cannot distinguish accepted1.19.2/1.19.3 behavior; earlier aliases and effects of new keys on each existing operation require a corrected decision table.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_18_2)
public final class InsideBlockContact implements InsideBlockContactHook {
    @Override
    public double inset(Player player, double vanilla) {
        return 0.001;
    }
}
