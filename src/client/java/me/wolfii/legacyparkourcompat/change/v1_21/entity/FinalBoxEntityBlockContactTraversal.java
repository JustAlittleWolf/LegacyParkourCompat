package me.wolfii.legacyparkourcompat.change.v1_21;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.EntityBlockContactTraversalHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.InsideBlockEffectType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;

import java.util.function.Consumer;

/**
 * Replays the 1.21.1 final-box contact scan at the end of each local-player move.
 * In Mojmap 1.21.1, Entity.move calls Entity.checkInsideBlocks after movement;
 * checkInsideBlocks deflates the final box by 1.0E-7 and calls BlockState.entityInside
 * then Entity.onInsideBlock for every cell in X/Y/Z order, including air.
 * In 26.2 unobfuscated Entity.applyEffectsFromBlocks, checkInsideBlocks passes an
 * InsideBlockEffectApplier.StepBasedCollector. Its applyAndClear delays operations,
 * while flushStep orders them by InsideBlockEffectType. The immediate applier here
 * executes apply/runBefore/runAfter at each cell, keeping per-cell and per-position
 * writes synchronous as in the old callback. BaseFireBlock, LayeredCauldronBlock,
 * LavaCauldronBlock and PowderSnowBlock use those applier operations. The precise
 * flag is true because 1.21.1 calls every block in the bounded scan; this preserves
 * BubbleColumnBlock's otherwise gated onInside callback.
 *
 * Sources (paths relative to decompiled_minecraft/):
 * 1.21.1/mojmap/net/minecraft/world/entity/Entity.java: move, checkInsideBlocks.
 * 1.21.1/mojmap/net/minecraft/world/level/block/state/BlockBehaviour.java: entityInside.
 * 26.2/unobfuscated/net/minecraft/world/entity/Entity.java: move, applyEffectsFromBlocks, checkInsideBlocks.
 * 26.2/unobfuscated/net/minecraft/world/entity/InsideBlockEffectApplier.java: StepBasedCollector.
 * 26.2/unobfuscated/net/minecraft/world/level/block/BaseFireBlock.java: entityInside.
 * 26.2/unobfuscated/net/minecraft/world/level/block/LayeredCauldronBlock.java: entityInside.
 * 26.2/unobfuscated/net/minecraft/world/level/block/LavaCauldronBlock.java: entityInside.
 * 26.2/unobfuscated/net/minecraft/world/level/block/PowderSnowBlock.java: entityInside.
 * 26.2/unobfuscated/net/minecraft/world/level/block/BubbleColumnBlock.java: entityInside.
 */
@MovementChange(emulates = ParkourVersion.V1_21)
public final class FinalBoxEntityBlockContactTraversal implements EntityBlockContactTraversalHook {
    @Override
    public void afterMove(Entity entity, Consumer<BlockState> onInsideBlock) {
        if (!this.applies(entity)) {
            return;
        }

        AABB box = entity.getBoundingBox();
        BlockPos min = BlockPos.containing(box.minX + 1.0E-7, box.minY + 1.0E-7, box.minZ + 1.0E-7);
        BlockPos max = BlockPos.containing(box.maxX - 1.0E-7, box.maxY - 1.0E-7, box.maxZ - 1.0E-7);
        if (!entity.level().hasChunksAt(min, max)) {
            return;
        }

        InsideBlockEffectApplier immediate = new InsideBlockEffectApplier() {
            @Override
            public void apply(InsideBlockEffectType type) {
                type.effect().accept(entity);
            }

            @Override
            public void runBefore(InsideBlockEffectType type, Consumer<Entity> effect) {
                effect.accept(entity);
            }

            @Override
            public void runAfter(InsideBlockEffectType type, Consumer<Entity> effect) {
                effect.accept(entity);
            }
        };

        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        for (int x = min.getX(); x <= max.getX(); x++) {
            for (int y = min.getY(); y <= max.getY(); y++) {
                for (int z = min.getZ(); z <= max.getZ(); z++) {
                    if (!entity.isAlive()) {
                        return;
                    }
                    pos.set(x, y, z);
                    BlockState state = entity.level().getBlockState(pos);
                    state.entityInside(entity.level(), pos, entity, immediate, true);
                    onInsideBlock.accept(state);
                }
            }
        }
    }

    @Override
    public boolean suppressPostTravel(Entity entity) {
        return this.applies(entity);
    }

    private boolean applies(Entity entity) {
        return entity instanceof LocalPlayer && entity.level().isClientSide() && !entity.isRemoved() && !entity.noPhysics;
    }
}
