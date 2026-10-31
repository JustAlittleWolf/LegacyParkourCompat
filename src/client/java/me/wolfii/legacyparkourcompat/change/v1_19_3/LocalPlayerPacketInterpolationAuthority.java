package me.wolfii.legacyparkourcompat.change.v1_19_3;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.network.ClientReceivedInterpolationHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.network.LocalPlayerPacketInterpolationAuthorityHook;
import me.wolfii.legacyparkourcompat.mechanic.network.ClientPacketPositionState;
import me.wolfii.legacyparkourcompat.mechanic.network.LegacyEntityRotationState;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

/**
 * 1.19.3 Mojmap Entity.isControlledByLocalInstance is false for the on-foot LocalPlayer, so
 * ClientPacketListener.handleMoveEntity and handleTeleportEntity accept its packet response;
 * LivingEntity.aiStep retains the three-step lerp (lines 2513-2527). 1.19.4 changes the predicate
 * fallback to isEffectiveAi, which is true for LocalPlayer. The 1.19–1.19.2 handler/predicate aliases
 * are covered by the accepted source review. The 26.2 unobfuscated tracked absolute route is
 * handleEntityPositionSync; handleTeleportEntity remains the native relative/velocity path. The
 * existing LivingEntityPacketInterpolationMixin advances this queued target in place of native
 * InterpolationHandler interpolation, avoiding two interpolators on one tick.
 */
@MovementChange(emulates = ParkourVersion.V1_19_3)
public final class LocalPlayerPacketInterpolationAuthority
    implements ClientReceivedInterpolationHook, LocalPlayerPacketInterpolationAuthorityHook {
    @Override
    public boolean handlesLegacyPackets(Entity entity) {
        return true;
    }

    @Override
    public boolean queue(Entity entity, Vec3 target, float yRot, float xRot, int steps) {
        var interpolation = ((LivingEntity)entity).getInterpolation();
        if (interpolation != null) {
            interpolation.cancel();
        }
        ((ClientPacketPositionState)entity).legacyQueue(target.x, target.y, target.z, yRot, xRot, steps);
        return true;
    }

    @Override
    public boolean pending(Entity entity) {
        return ((ClientPacketPositionState)entity).legacySteps() > 0;
    }

    @Override
    public void advance(Entity entity) {
        ClientPacketPositionState state = (ClientPacketPositionState)entity;
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
        yRot = yRot + (float)deltaYRot / steps;
        xRot = xRot + (float)(state.legacyXRot() - xRot) / steps;
        ((LegacyEntityRotationState)entity).legacySetRotations(yRot, xRot);
        state.legacyDecrementSteps();
        entity.setPos(x, y, z);
        ((LegacyEntityRotationState)entity).legacyFinishRotations(yRot, xRot);
    }

    @Override
    public boolean acceptsServerMovement(LocalPlayer player) {
        return true;
    }
}
