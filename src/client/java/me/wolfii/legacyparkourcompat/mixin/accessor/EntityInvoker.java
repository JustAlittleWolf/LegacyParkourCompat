package me.wolfii.legacyparkourcompat.mixin.accessor;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.CrashReportCategory;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Entity.class)
public interface EntityInvoker {
    @Invoker("getBlockPosBelowThatAffectsMyMovement")
    BlockPos legacyparkourcompat$invokeGetBlockPosBelowThatAffectsMyMovement();

    @Invoker("getBlockSpeedFactor")
    float legacyparkourcompat$invokeGetBlockSpeedFactor();

    @Invoker("setSharedFlag")
    void legacyparkourcompat$setSharedFlag(int flag, boolean value);

    @Invoker("onInsideBlock")
    void legacyparkourcompat$invokeOnInsideBlock(BlockState state);

    @Invoker("makeBoundingBox")
    AABB legacyparkourcompat$invokeMakeBoundingBox(Vec3 position);

    @Invoker("getFireImmuneTicks")
    int legacyparkourcompat$invokeGetFireImmuneTicks();

    @Invoker("playEntityOnFireExtinguishedSound")
    void legacyparkourcompat$invokePlayEntityOnFireExtinguishedSound();

    @Invoker("isInRain")
    boolean legacyparkourcompat$invokeIsInRain();

    @Invoker("fillCrashReportCategory")
    void legacyparkourcompat$invokeFillCrashReportCategory(CrashReportCategory category);

    @Invoker("restituteMovementAfterCollisions")
    void legacyparkourcompat$invokeRestituteMovementAfterCollisions(
        BlockState effectState,
        boolean xCollision,
        boolean zCollision,
        Vec3 movement
    );

    @Invoker("checkInsideBlocks")
    int legacyparkourcompat$invokeCheckInsideBlocks(
        Vec3 from,
        Vec3 to,
        InsideBlockEffectApplier.StepBasedCollector collector,
        LongSet visited,
        int remainingBudget
    );
}
