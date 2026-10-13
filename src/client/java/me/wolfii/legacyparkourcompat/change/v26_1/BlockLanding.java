package me.wolfii.legacyparkourcompat.change.v26_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockLandingHook;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlimeBlock;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

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
public final class BlockLanding implements BlockLandingHook {
    private final String blockId;
    private final boolean slime;

    @MovementChange.Factory
    public static List<BlockLanding> variants() {
        List<BlockLanding> variants = new ArrayList<>();
        for (Block block : BuiltInRegistries.BLOCK) {
            boolean slime = block instanceof SlimeBlock;
            if (slime || block instanceof BedBlock) {
                Identifier id = BuiltInRegistries.BLOCK.getKey(block);
                if (id == null) {
                    throw new IllegalStateException("Registered bounce block has no identifier: " + block);
                }
                variants.add(new BlockLanding(id.toString(), slime));
            }
        }
        return List.copyOf(variants);
    }

    public BlockLanding(String blockId, boolean slime) {
        this.blockId = blockId;
        this.slime = slime;
    }

    @Override
    public String blockId() {
        return this.blockId;
    }

    @Override
    public void updateMovementAfterFallOn(BlockGetter level, Entity entity, java.util.Optional<me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockBounceHook> bounce, VanillaCall vanilla) {
        if (entity.isSuppressingBounce()) {
            vanilla.run();
            return;
        }

        float coefficient = bounce.map(behavior -> behavior.bounceRestitution(this.slime ? 1.0F : 0.66F, entity))
            .orElse(this.slime ? 1.0F : 0.66F);
        if (coefficient == 0.0F) {
            vanilla.run();
            return;
        }
        Vec3 movement = entity.getDeltaMovement();
        if (movement.y < 0.0) {
            double factor = entity instanceof LivingEntity ? 1.0 : 0.8;
            double bouncedY = this.slime
                ? -movement.y * factor
                : -movement.y * coefficient * factor;
            entity.setDeltaMovement(movement.x, bouncedY, movement.z);
        }
    }
}
