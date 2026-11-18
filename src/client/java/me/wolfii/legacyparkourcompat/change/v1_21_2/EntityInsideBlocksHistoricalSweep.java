package me.wolfii.legacyparkourcompat.change.v1_21_2;

import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.ReferenceArraySet;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.collision.SweptShapeIntersectionMath;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockCallbackAxisOrderHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.callback.SweptBlockResponseHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.EntityInsideBlocksScanHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.HistoricalBlockCallbackSweepHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.blockcallback.BlockCallbackTraversalHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.PartialShapeSweepHook;
import me.wolfii.legacyparkourcompat.mixin.EntityMovementAccessor;
import me.wolfii.legacyparkourcompat.mechanic.EntityHistoricalCallbackState;
import me.wolfii.legacyparkourcompat.mixin.accessor.EntityInvoker;
import me.wolfii.legacyparkourcompat.mixin.accessor.EntityVisitedBlocksAccessor;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BubbleColumnBlock;
import net.minecraft.world.level.block.EyeblossomBlock;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.LavaCauldronBlock;
import net.minecraft.world.level.block.PowderSnowBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Restores the 1.21.2/1.21.3 list-level visitor, callback ordering and fire summary.
 * Sources: 1.21.2 and 1.21.3 Mojmap Entity.applyEffectsFromBlocks,
 * Entity.checkInsideBlocks and Entity.collidedWithShapeMovingFrom; BlockGetter.boxTraverseBlocks;
 * 26.2 unobfuscated Entity.applyEffectsFromBlocks(List) and checkInsideBlocks. The old paired
 * source files are byte-identical for these operations. The accepted list-scan candidate supplies
 * only the DDA candidates; this class supplies old position admission and ordered dispatch.
 * In 1.21.2 and 1.21.3 Mojmap, LivingEntity's post-travel call and
 * PistonMovingBlockEntity.moveEntityByPiston use Entity.applyEffectsFromBlocks()
 * to scan oldPosition() through position(). LocalPlayer has no other record producer.
 * In 26.2 unobfuscated, the post-travel records and the piston's explicit
 * Vec3 singleton both reach Entity.applyEffectsFromBlocks(List); the last-movement
 * replay caller is ItemEntity. The list wrapper invokes this provider only for LocalPlayer.
 * Check the original native candidates, including the axis split, and the
 * projected historical candidates before the historical callbacks run.
 */
@MovementChange(emulates = ParkourVersion.V1_21_2)
public final class EntityInsideBlocksHistoricalSweep implements HistoricalBlockCallbackSweepHook {
    @Override
    public boolean supportsBatch(Entity entity, List<?> movements) {
        if (entity.isRemoved() || entity.noPhysics) {
            return true;
        }

        EntityInsideBlocksScanHook scan = MovementRuntime.find(EntityInsideBlocksScanHook.class, entity)
            .filter(EntityInsideBlocksScanHook::usesHistoricalScan)
            .orElseThrow(() -> new IllegalStateException("Historical callback sweep has no selected scan provider"));
        AABB queryBox = entity.getBoundingBox().deflate(1.0E-5F);
        EntityInvoker invoker = (EntityInvoker)entity;
        for (Object movement : movements) {
            EntityMovementAccessor access = (EntityMovementAccessor)(Object)movement;
            Vec3 from = access.legacyParkourCompat$from();
            Vec3 to = access.legacyParkourCompat$to();
            if (containsHistoricalEyeblossomCandidate(entity, scan, from, to, queryBox)) {
                return false;
            }

            Vec3 delta = to.subtract(from);
            var axisDependentMovement = access.axisDependentOriginalMovement();
            if (axisDependentMovement.isPresent() && delta.lengthSqr() > 0.0) {
                Vec3 axisOrder = MovementRuntime.find(BlockCallbackAxisOrderHook.class, entity)
                    .filter(hook -> hook.appliesTo(entity))
                    .map(hook -> hook.callbackAxisOrder(axisDependentMovement.get(), delta))
                    .orElse(axisDependentMovement.get());
                Vec3 segmentFrom = from;
                for (Direction.Axis axis : Direction.axisStepOrder(axisOrder)) {
                    double segmentDelta = delta.get(axis);
                    if (segmentDelta == 0.0) {
                        continue;
                    }

                    Vec3 segmentTo = segmentFrom.with(axis, segmentFrom.get(axis) + segmentDelta);
                    AABB segmentQueryBox = invoker.legacyparkourcompat$invokeMakeBoundingBox(segmentTo)
                        .deflate(1.0E-5F);
                    if (containsModernEyeblossomCandidate(entity, segmentFrom, segmentTo, segmentQueryBox)) {
                        return false;
                    }
                    segmentFrom = segmentTo;
                }
            } else {
                AABB segmentQueryBox = invoker.legacyparkourcompat$invokeMakeBoundingBox(to).deflate(1.0E-5F);
                if (containsModernEyeblossomCandidate(entity, from, to, segmentQueryBox)) {
                    return false;
                }
            }
        }
        // Eyeblossom has no historical callback. Keep the original batch if the
        // projected span introduces one, before stepOn or finalization runs.
        return !containsHistoricalEyeblossomCandidate(entity, scan, entity.oldPosition(), entity.position(), queryBox);
    }

    private static boolean containsHistoricalEyeblossomCandidate(Entity entity, EntityInsideBlocksScanHook scan,
                                                                 Vec3 from, Vec3 to, AABB queryBox) {
        for (BlockPos pos : scan.candidateCells(from, to, queryBox)) {
            if (isEyeblossom(entity, pos)) {
                return true;
            }
        }
        return false;
    }

    private static boolean containsModernEyeblossomCandidate(Entity entity, Vec3 from, Vec3 to, AABB queryBox) {
        boolean[] found = {false};
        BlockGetter.BlockStepVisitor inspect = (pos, step) -> {
            if (isEyeblossom(entity, pos)) {
                found[0] = true;
                return false;
            }
            return true;
        };

        // This is the exact native 26.2 block candidate enumerator. Some selected
        // profile hooks also replace its segment visitor; include those candidates
        // too because the original fallback retains the same composed mixins.
        BlockGetter.forEachBlockIntersectedBetween(from, to, queryBox, inspect);
        if (found[0]) {
            return true;
        }
        MovementRuntime.find(BlockCallbackTraversalHook.class, entity)
            .filter(hook -> hook.appliesTo(entity))
            .ifPresent(hook -> hook.visitSegment(from, to, queryBox, inspect));
        return found[0];
    }

    private static boolean isEyeblossom(Entity entity, BlockPos pos) {
        return entity.level().getBlockState(pos).getBlock() instanceof EyeblossomBlock;
    }

    private record CallbackSpan(Vec3 from, Vec3 to) {}

    @Override
    public void apply(Entity entity, List<?> nativeMovements) {
        if (entity.isRemoved() || entity.noPhysics) {
            return;
        }

        CallbackSpan span = new CallbackSpan(entity.oldPosition(), entity.position());
        if (entity.onGround()) {
            BlockPos stepPosition = entity.getOnPosLegacy();
            BlockState stepState = entity.level().getBlockState(stepPosition);
            stepState.getBlock().stepOn(entity.level(), stepPosition, stepState, entity);
        }

        Set<BlockState> blocksInside = new ReferenceArraySet<>();
        LongSet visitedBlocks = ((EntityVisitedBlocksAccessor)entity).legacyparkourcompat$visitedBlocks();
        EntityInsideBlocksScanHook scan = MovementRuntime.find(EntityInsideBlocksScanHook.class, entity)
            .filter(EntityInsideBlocksScanHook::usesHistoricalScan)
            .orElseThrow(() -> new IllegalStateException("Historical callback sweep has no selected scan provider"));
        boolean[] stoppedAliveLoop = {false};

        // The historical scanner clears its input on the normal path. Supply a
        // separate singleton so the native callback list and live queue stay intact.
        scan.scanMovements(entity.getBoundingBox(), new ArrayList<>(List.of(span)), (movement, queryBox) -> {
            if (stoppedAliveLoop[0]) {
                return;
            }

            Vec3 from = movement.from();
            Vec3 to = movement.to();
            for (BlockPos pos : scan.candidateCells(from, to, queryBox)) {
                if (!entity.isAlive()) {
                    stoppedAliveLoop[0] = true;
                    return;
                }

                BlockState state = entity.level().getBlockState(pos);
                if (state.isAir() || !visitedBlocks.add(pos.asLong())) {
                    continue;
                }

                try {
                    VoxelShape insideShape = legacyInsideShape(state, entity.level(), pos, entity);
                    if (insideShape != Shapes.block()
                        && !passesLegacyPartialShapeGate(entity, from, to, pos, insideShape)) {
                        continue;
                    }

                    // 1.21.2/1.21.3 Mojmap BubbleColumnBlock.entityInside has no precision operand
                    // and runs for every admitted cell; 26.2 unobfuscated gates its response on it.
                    // Bypass only BubbleColumnBlock's modern precision gate; other modern-only
                    // callback fallbacks retain the native moved-far/query-box signal.
                    boolean precise = state.getBlock() instanceof BubbleColumnBlock
                        || from.distanceToSqr(to) > 9.9999994E-11F
                        || queryBox.intersects(pos);
                    VanillaCall nativeCallback = () -> state.entityInside(
                        entity.level(),
                        pos,
                        entity,
                        InsideBlockEffectApplier.NOOP,
                        precise
                    );
                    boolean replaced = MovementRuntime.find(SweptBlockResponseHook.class, entity)
                        .map(response -> response.apply(
                            state,
                            entity.level(),
                            pos,
                            entity,
                            nativeCallback
                        ))
                        .orElse(false);
                    if (!replaced) {
                        nativeCallback.run();
                    }
                    ((EntityInvoker)entity).legacyparkourcompat$invokeOnInsideBlock(state);
                } catch (Throwable exception) {
                    CrashReport report = CrashReport.forThrowable(exception, "Colliding entity with block");
                    CrashReportCategory blockCategory = report.addCategory("Block being collided with");
                    CrashReportCategory.populateBlockDetails(blockCategory, entity.level(), pos, state);
                    CrashReportCategory entityCategory = report.addCategory("Entity being checked for collision");
                    ((EntityInvoker)entity).legacyparkourcompat$invokeFillCrashReportCategory(entityCategory);
                    throw new ReportedException(report);
                }

                blocksInside.add(state);
            }
        });

        visitedBlocks.clear();
        boolean touchingFireOrLava = blocksInside.stream()
            .anyMatch(state -> state.is(BlockTags.FIRE) || state.is(Blocks.LAVA));
        if (!touchingFireOrLava && entity.isAlive()) {
            EntityInvoker invoker = (EntityInvoker)entity;
            if (entity.getRemainingFireTicks() <= 0) {
                entity.setRemainingFireTicks(-invoker.legacyparkourcompat$invokeGetFireImmuneTicks());
            }

            if (((EntityHistoricalCallbackState)entity).legacyparkourcompat$wasOnFireBeforeMove()
                && (entity.isInPowderSnow || isInWaterRainOrBubble(entity, invoker))) {
                invoker.legacyparkourcompat$invokePlayEntityOnFireExtinguishedSound();
            }
        }

        if (entity.isOnFire() && (entity.isInPowderSnow || isInWaterRainOrBubble(entity, (EntityInvoker)entity))) {
            entity.setRemainingFireTicks(-((EntityInvoker)entity).legacyparkourcompat$invokeGetFireImmuneTicks());
        }
    }

    private static VoxelShape legacyInsideShape(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (state.getBlock() instanceof PowderSnowBlock
            || state.getBlock() instanceof LayeredCauldronBlock
            || state.getBlock() instanceof LavaCauldronBlock) {
            return Shapes.block();
        }
        return state.getEntityInsideCollisionShape(level, pos, entity);
    }

    private static boolean passesLegacyPartialShapeGate(Entity entity, Vec3 from, Vec3 to, BlockPos pos, VoxelShape shape) {
        Vec3 reverseDisplacement = MovementRuntime.find(PartialShapeSweepHook.class, entity)
            .flatMap(hook -> hook.reverseDisplacement(entity.getBoundingBox(), from, to))
            .orElseThrow(() -> new IllegalStateException("Historical partial-shape sweep provider is missing"));
        return SweptShapeIntersectionMath.collides(
            entity.getBoundingBox(),
            reverseDisplacement,
            shape.move(new Vec3(pos)).toAabbs()
        );
    }

    private static boolean isInWaterRainOrBubble(Entity entity, EntityInvoker invoker) {
        return entity.isInWater()
            || invoker.legacyparkourcompat$invokeIsInRain()
            || entity.getInBlockState().is(Blocks.BUBBLE_COLUMN);
    }
}
