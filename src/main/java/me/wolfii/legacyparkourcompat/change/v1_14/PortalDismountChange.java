package me.wolfii.legacyparkourcompat.change.v1_14;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.PortalDismountBehavior;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import net.minecraft.world.entity.vehicle.boat.Boat;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

@MovementChange(emulates = ParkourVersion.V1_14)
public final class PortalDismountChange implements PortalDismountBehavior {
    private static final int[][] DISMOUNT_OFFSETS = {
        {0, 1}, {0, -1}, {-1, 1}, {-1, -1}, {1, 1}, {1, -1}, {-1, 0}, {1, 0}, {0, 1}
    };

    @Override
    public boolean dismountFromPortal(LivingEntity passenger, Entity vehicle, ParkourVersion selected) {
        if (!me.wolfii.legacyparkourcompat.change.common.HistoricalRideables.contains(vehicle, selected)) {
            return false;
        }
        reposition(passenger, vehicle);
        return true;
    }

    private static void reposition(LivingEntity passenger, Entity vehicle) {
        if (!(vehicle instanceof Boat) && !(vehicle instanceof AbstractHorse)) {
            double x = vehicle.getX();
            double y = vehicle.getBoundingBox().minY + vehicle.getBbHeight();
            double z = vehicle.getZ();
            Direction direction = vehicle.getMotionDirection();
            if (direction != null) {
                Direction perpendicular = direction.getClockWise();
                double centerX = Math.floor(passenger.getX()) + 0.5;
                double centerZ = Math.floor(passenger.getZ()) + 0.5;
                double width = passenger.getBoundingBox().maxX - passenger.getBoundingBox().minX;
                double depth = passenger.getBoundingBox().maxZ - passenger.getBoundingBox().minZ;
                AABB searchBox = new AABB(
                    centerX - width / 2.0,
                    vehicle.getBoundingBox().minY,
                    centerZ - depth / 2.0,
                    centerX + width / 2.0,
                    Math.floor(vehicle.getBoundingBox().minY) + passenger.getBbHeight(),
                    centerZ + depth / 2.0
                );

                for (int[] offset : DISMOUNT_OFFSETS) {
                    double dx = direction.getStepX() * offset[0] + perpendicular.getStepX() * offset[1];
                    double dz = direction.getStepZ() * offset[0] + perpendicular.getStepZ() * offset[1];
                    double candidateX = centerX + dx;
                    double candidateZ = centerZ + dz;
                    AABB candidateBox = searchBox.move(dx, 0.0, dz);
                    if (passenger.level().noCollision(passenger, candidateBox)) {
                        BlockPos blockPos = BlockPos.containing(candidateX, passenger.getY(), candidateZ);
                        BlockState blockState = passenger.level().getBlockState(blockPos);
                        if (blockState.entityCanStandOn(passenger.level(), blockPos, passenger)) {
                            passenger.teleportTo(candidateX, passenger.getY() + 1.0, candidateZ);
                            return;
                        }

                        BlockPos below = BlockPos.containing(candidateX, passenger.getY() - 1.0, candidateZ);
                        if (passenger.level().getBlockState(below).entityCanStandOn(passenger.level(), below, passenger)
                            || passenger.level().getFluidState(below).is(net.minecraft.tags.FluidTags.WATER)) {
                            x = candidateX;
                            y = passenger.getY() + 1.0;
                            z = candidateZ;
                        }
                    } else {
                        BlockPos above = BlockPos.containing(candidateX, passenger.getY() + 1.0, candidateZ);
                        if (passenger.level().noCollision(passenger, candidateBox.move(0.0, 1.0, 0.0))
                            && passenger.level().getBlockState(above).entityCanStandOn(passenger.level(), above, passenger)) {
                            x = candidateX;
                            y = passenger.getY() + 2.0;
                            z = candidateZ;
                        }
                    }
                }
            }

            passenger.teleportTo(x, y, z);
            return;
        }

        double distance = passenger.getBbWidth() / 2.0F + vehicle.getBbWidth() / 2.0F + 0.4;
        float armOffset;
        if (vehicle instanceof Boat) {
            armOffset = 0.0F;
        } else {
            armOffset = (float)(Math.PI / 2) * (passenger.getMainArm() == HumanoidArm.RIGHT ? -1 : 1);
        }

        float sin = -me.wolfii.legacyparkourcompat.change.common.LegacyTrig.sin(-passenger.getYRot() * (float)(Math.PI / 180.0) - (float)Math.PI + armOffset);
        float cos = -me.wolfii.legacyparkourcompat.change.common.LegacyTrig.cos(-passenger.getYRot() * (float)(Math.PI / 180.0) - (float)Math.PI + armOffset);
        double scale = Math.abs(sin) > Math.abs(cos) ? distance / Math.abs(sin) : distance / Math.abs(cos);
        double x = passenger.getX() + sin * scale;
        double z = passenger.getZ() + cos * scale;
        passenger.setPos(x, vehicle.getY() + vehicle.getBbHeight() + 0.001, z);
        if (!passenger.level().noCollision(passenger, passenger.getBoundingBox().minmax(vehicle.getBoundingBox()))) {
            passenger.setPos(x, vehicle.getY() + vehicle.getBbHeight() + 1.001, z);
            if (!passenger.level().noCollision(passenger, passenger.getBoundingBox().minmax(vehicle.getBoundingBox()))) {
                passenger.setPos(vehicle.getX(), vehicle.getY() + passenger.getBbHeight() + 0.001, vehicle.getZ());
            }
        }
    }
}
