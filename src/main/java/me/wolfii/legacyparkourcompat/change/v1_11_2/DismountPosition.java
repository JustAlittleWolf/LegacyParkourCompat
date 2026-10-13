package me.wolfii.legacyparkourcompat.change.v1_11_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.vehicle.DismountPositionHook;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.pig.Pig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.minecart.AbstractMinecart;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.11.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java:
 *   public void stopRiding() {; lines 1864-1870.
 *   Source SHA-256: bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f.
 * 1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java:
 *   public void stopRiding() {; lines 1908-1914.
 *   Source SHA-256: 190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6.
 * No exact independently accepted source binding is established for this operation.
 * Remaining source/applicability gates:
 *   Later applicability gate: exact introduction of campaign-F-PLAYER-DISMOUNT-CLEARANCE-BOX is unknown within (1.11.2, 1.12.2]
 *   Exact independent source verdict/snapshot identity for F-PLAYER-DISMOUNT-CLEARANCE-BOX
 *   Exact independent source verdict/snapshot identity for MOVE-003-server-dismount-position
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_11_2)
public final class DismountPosition implements DismountPositionHook {
    private static final int[][] CANDIDATE_OFFSETS = {
        {0, 1}, {0, -1}, {-1, 1}, {-1, -1}, {1, 1}, {1, -1}, {-1, 0}, {1, 0}, {0, 1}
    };

    @Override
    public boolean dismount(LivingEntity passenger, Entity vehicle) {
        if (!(passenger instanceof Player)
            || passenger.level().isClientSide()
            || !(vehicle instanceof Pig || vehicle instanceof AbstractMinecart)
        ) {
            return false;
        }

        double exitX = vehicle.getX();
        double exitY = vehicle.getBoundingBox().minY + vehicle.getBbHeight();
        double exitZ = vehicle.getZ();
        Direction direction = vehicle instanceof AbstractMinecart
            ? vehicle.getMotionDirection()
            : Direction.from2DDataValue(Mth.floor(vehicle.getYRot() * 4.0F / 360.0F + 0.5) & 3);

        if (direction != null) {
            Direction perpendicular = direction.getClockWise();
            double centerX = Math.floor(passenger.getX()) + 0.5;
            double centerZ = Math.floor(passenger.getZ()) + 0.5;
            AABB passengerBox = passenger.getBoundingBox();
            double widthX = passengerBox.maxX - passengerBox.minX;
            double widthZ = passengerBox.maxZ - passengerBox.minZ;
            double vehicleHeight = vehicle.getBbHeight();
            AABB collisionBox = new AABB(
                centerX - widthX / 2.0,
                passengerBox.minY - vehicleHeight,
                centerZ - widthZ / 2.0,
                centerX + widthX / 2.0,
                passengerBox.maxY - vehicleHeight,
                centerZ + widthZ / 2.0
            );
            Level level = passenger.level();

            for (int[] offset : CANDIDATE_OFFSETS) {
                double dx = direction.getStepX() * offset[0] + perpendicular.getStepX() * offset[1];
                double dz = direction.getStepZ() * offset[0] + perpendicular.getStepZ() * offset[1];
                double x = centerX + dx;
                double z = centerZ + dz;
                AABB query = collisionBox.move(dx, 1.0, dz);
                if (!hasCollisions(level, query)) {
                    BlockPos atPlayerY = BlockPos.containing(x, passenger.getY(), z);
                    if (isFullBlock(level, atPlayerY)) {
                        passenger.dismountTo(x, passenger.getY() + 1.0, z);
                        return true;
                    }

                    BlockPos belowPlayer = BlockPos.containing(x, passenger.getY() - 1.0, z);
                    BlockState belowState = level.getBlockState(belowPlayer);
                    if (belowState.isCollisionShapeFullBlock(level, belowPlayer)
                        || belowState.getFluidState().is(FluidTags.WATER)
                    ) {
                        exitX = x;
                        exitY = passenger.getY() + 1.0;
                        exitZ = z;
                    }
                } else if (!hasCollisions(level, query.move(0.0, 1.0, 0.0))) {
                    BlockPos abovePlayer = BlockPos.containing(x, passenger.getY() + 1.0, z);
                    if (isFullBlock(level, abovePlayer)) {
                        exitX = x;
                        exitY = passenger.getY() + 2.0;
                        exitZ = z;
                    }
                }
            }
        }

        passenger.dismountTo(exitX, exitY, exitZ);
        return true;
    }

    private static boolean hasCollisions(Level level, AABB box) {
        return !level.noBlockCollision(null, box) || !level.getWorldBorder().isWithinBounds(box);
    }

    private static boolean isFullBlock(Level level, BlockPos pos) {
        return level.getBlockState(pos).isCollisionShapeFullBlock(level, pos);
    }
}
