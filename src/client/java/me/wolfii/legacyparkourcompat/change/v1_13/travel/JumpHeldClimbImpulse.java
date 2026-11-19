package me.wolfii.legacyparkourcompat.change.v1_13.travel;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.HistoricalTrapdoors;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.TrapdoorClimbingHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.ClimbImpulseHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

/**
 * Ornithe Feather 1.13.2 LivingEntity.moveRelative assigns Y=0.2 after move only
 * for horizontal collision while isClimbing; 1.14.4 adds jumping to that gate.
 * The 1.13.2 and 1.14.4 LivingEntity.isClimbing predicates both admit ladder,
 * vine, and an open trapdoor above a same-facing ladder, with a spectator gate.
 * In 26.2 unobfuscated LivingEntity.handleRelativeFrictionAndCalculateMovement,
 * the corresponding assignment follows Entity.move and modifies the returned
 * local vector without writing the entity's delta movement. Preserve that native
 * result outside historical climbing, including modern-only climbable states and
 * the powder-snow equipment response. Ornithe Feather Blocks registers oak and
 * iron trapdoors through 1.12.2, adding spruce, birch, jungle, acacia and dark oak in 1.13.
 */
@MovementChange(emulates = ParkourVersion.V1_13_1)
public final class JumpHeldClimbImpulse implements ClimbImpulseHook {
    @Override
    public Vec3 afterMove(LivingEntity entity, ParkourVersion selected, Vec3 nativeMovement,
                          @Nullable TrapdoorClimbingHook trapdoors) {
        if (!isHistoricalClimbing(entity, selected, trapdoors)) {
            return nativeMovement;
        }

        Vec3 movedVelocity = entity.getDeltaMovement();
        if (entity.horizontalCollision) {
            return new Vec3(movedVelocity.x, 0.2, movedVelocity.z);
        }
        return movedVelocity;
    }

    private static boolean isHistoricalClimbing(LivingEntity entity, ParkourVersion selected,
                                                @Nullable TrapdoorClimbingHook trapdoors) {
        if (entity.isSpectator()) {
            return false;
        }

        BlockPos pos = BlockPos.containing(entity.getX(), entity.getBoundingBox().minY, entity.getZ());
        BlockState state = entity.level().getBlockState(pos);
        if (state.is(Blocks.LADDER) || state.is(Blocks.VINE)) {
            return true;
        }
        boolean eraTrapdoor = selected.olderThan(ParkourVersion.V1_13)
            ? HistoricalTrapdoors.existedBefore1_13(state) : HistoricalTrapdoors.existedIn1_13(state);
        if (!eraTrapdoor || !state.getValue(TrapDoorBlock.OPEN)) {
            return false;
        }

        BlockState below = entity.level().getBlockState(pos.below());
        if (!below.is(Blocks.LADDER)
            || below.getValue(LadderBlock.FACING) != state.getValue(TrapDoorBlock.FACING)) {
            return false;
        }

        // The mixin resolved the trapdoor operation for the active profile.
        // Apply that result only after this historical open/matching-state gate.
        return trapdoors == null || !trapdoors.blocksClimbing(state);
    }
}
