package me.wolfii.legacyparkourcompat.change.v1_16;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.network.ClientPacketPositionState;
import me.wolfii.legacyparkourcompat.mechanic.hook.network.ClientReceivedInterpolationHook;
import me.wolfii.legacyparkourcompat.mechanic.network.LegacyEntityRotationState;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

/**
 * 1.15.2 Mojmap and 1.16.1 Mojmap LivingEntity.lerpTo store lerpX/Y/Z, lerp rotations and lerpSteps;
 * LivingEntity.aiStep advances one fraction before the rest of its tick. ClientPacketListener.handleMoveEntity
 * and handleTeleportEntity use the non-controlled-player route for LocalPlayer. In 26.2,
 * ClientPacketListener routes through Entity.moveOrInterpolateTo and LivingEntity.aiStep's
 * InterpolationHandler; this hook retains the same per-player target and step state. The absolute
 * packet angle's old byte conversion is shared with the later tracked-position route by LegacyPacketRotation.
 * LocalPlayer.getInterpolation currently returns a non-null handler; the nullable guard remains
 * defensive for subclasses/runtime changes, and does not replace the old queue state.
 */
@MovementChange(emulates = ParkourVersion.V1_16)
public final class QueuedEntityPacketInterpolation implements ClientReceivedInterpolationHook {
    @Override
    public boolean handlesLegacyPackets(Entity entity) {
        return true;
    }

    @Override
    public boolean queue(Entity entity, Vec3 target, float yRot, float xRot, int steps) {
        var interpolation = ((LivingEntity) entity).getInterpolation();
        if (interpolation != null) {
            interpolation.cancel();
        }
        ((ClientPacketPositionState) entity).legacyQueue(target.x, target.y, target.z, yRot, xRot, steps);
        return true;
    }

    @Override
    public boolean pending(Entity entity) {
        return ((ClientPacketPositionState) entity).legacySteps() > 0;
    }

    @Override
    public void advance(Entity entity) {
        ClientPacketPositionState state = (ClientPacketPositionState) entity;
        int steps = state.legacySteps();
        if (steps <= 0) {
            return;
        }

        double x = entity.getX() + (state.legacyX() - entity.getX()) / steps;
        double y = entity.getY() + (state.legacyY() - entity.getY()) / steps;
        double z = entity.getZ() + (state.legacyZ() - entity.getZ()) / steps;
        float yRot = entity.getYRot();
        float xRot = entity.getXRot();
        double deltaYRot = Mth.wrapDegrees(state.legacyYRot() - yRot);
        yRot = (float) (yRot + deltaYRot / steps);
        xRot = (float) (xRot + (state.legacyXRot() - xRot) / steps);
        ((LegacyEntityRotationState) entity).legacySetRotations(yRot, xRot);
        state.legacyDecrementSteps();
        entity.setPos(x, y, z);
        ((LegacyEntityRotationState) entity).legacyFinishRotations(yRot, xRot);
    }
}
