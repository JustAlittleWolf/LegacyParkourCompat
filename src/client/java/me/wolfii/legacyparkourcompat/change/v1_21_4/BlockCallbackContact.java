package me.wolfii.legacyparkourcompat.change.v1_21_4;

import it.unimi.dsi.fastutil.longs.LongSet;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.blockcallback.BlockCallbackContactHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.InsideBlockEffectType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Mojmap 1.21.4 Entity.checkInsideBlocks admits each non-air position before
 * its block-shape test, invokes entityInside only for a passing shape, then
 * calls onInsideBlock. That version has no fluid callback and no staged
 * InsideBlockEffectApplier; operations must run synchronously at the cell.
 * Its no-entity shape query defaults to Shapes.block(); only EndPortalBlock and
 * TripWireBlock override it in 1.21.4, and both return the block state's shape.
 * Source: 1.21.4/mojmap/net/minecraft/world/entity/Entity.java, checkInsideBlocks;
 * 1.21.4 EndPortalBlock.java and TripWireBlock.java.
 */
@MovementChange(emulates = ParkourVersion.V1_21_4)
public final class BlockCallbackContact implements BlockCallbackContactHook {
    @Override
    public Optional<Boolean> visit(Entity entity, BlockPos pos, int step, boolean precise,
                                   InsideBlockEffectApplier.StepBasedCollector collector, LongSet visited,
                                   Consumer<BlockState> onInsideBlock, Predicate<List<AABB>> sweptIntersection) {
        if (!entity.isAlive()) return Optional.of(false);
        BlockState state = entity.level().getBlockState(pos);
        if (state.isAir()) return Optional.of(true);
        if (!visited.add(pos.asLong())) return Optional.of(true);

        var shape = state.is(Blocks.END_PORTAL) || state.is(Blocks.TRIPWIRE)
            ? state.getEntityInsideCollisionShape(entity.level(), pos, entity)
            : Shapes.block();
        if (shape != Shapes.block() && !sweptIntersection.test(shape.move(new Vec3(pos)).toAabbs())) {
            return Optional.of(true);
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
        state.entityInside(entity.level(), pos, entity, immediate, true);
        onInsideBlock.accept(state);
        return Optional.of(true);
    }
}
