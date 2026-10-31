package me.wolfii.legacyparkourcompat.change.v1_15;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.hook.network.ClientReceivedInterpolationHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.network.ClientRelativeMoveTargetHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.network.ClientTeleportTargetHook;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

/** Native preservation barrier below the 1.15.2-to-1.16 received-packet change. */
@MovementChange(emulates = ParkourVersion.V1_15)
public final class NativeReceivedPacketTargets
    implements ClientTeleportTargetHook, ClientRelativeMoveTargetHook, ClientReceivedInterpolationHook {
    @Override
    public boolean handlesLegacyPackets(Entity entity) {
        return false;
    }

    @Override
    public Vec3 target(Entity entity, Vec3 packetTarget) {
        return packetTarget;
    }

    @Override
    public Vec3 target(Entity entity, long xa, long ya, long za, Vec3 nativeTarget) {
        return nativeTarget;
    }

    @Override
    public boolean queue(Entity entity, Vec3 target, float yRot, float xRot, int steps) {
        return false;
    }

    @Override
    public boolean pending(Entity entity) {
        return false;
    }

    @Override
    public void advance(Entity entity) {
    }
}
