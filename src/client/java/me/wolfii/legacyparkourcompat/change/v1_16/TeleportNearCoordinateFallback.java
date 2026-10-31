package me.wolfii.legacyparkourcompat.change.v1_16;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.network.ClientPacketPositionState;
import me.wolfii.legacyparkourcompat.mechanic.hook.network.ClientTeleportTargetHook;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

/**
 * 1.15.2 Mojmap and 1.16.1 Mojmap ClientPacketListener.handleTeleportEntity store raw packet coordinates,
 * then queues the current position when all three negated threshold comparisons pass.
 * This changed in 1.16.2, where ClientPacketListener.handleTeleportEntity queues the packet
 * target unconditionally. The historical tracker teleport is received as
 * ClientboundEntityPositionSyncPacket in 26.2; ServerEntity.sendChanges emits this packet for
 * its tracked teleport. Its ClientPacketListener.handleEntityPositionSync currently chooses
 * moveOrInterpolateTo or snapTo, while 1.16.1 ClientPacketListener.handleTeleportEntity always
 * queues LivingEntity.lerpTo for a non-locally-controlled LocalPlayer.
 */
@MovementChange(emulates = ParkourVersion.V1_16)
public final class TeleportNearCoordinateFallback implements ClientTeleportTargetHook {
    @Override
    public Vec3 target(Entity entity, Vec3 packetTarget) {
        ClientPacketPositionState state = (ClientPacketPositionState) entity;
        state.legacyPacketCoordinates(
            Mth.lfloor(packetTarget.x * 4096.0),
            Mth.lfloor(packetTarget.y * 4096.0),
            Mth.lfloor(packetTarget.z * 4096.0)
        );
        if (!(Math.abs(entity.getX() - packetTarget.x) >= 0.03125)
            && !(Math.abs(entity.getY() - packetTarget.y) >= 0.015625)
            && !(Math.abs(entity.getZ() - packetTarget.z) >= 0.03125)) {
            return new Vec3(entity.getX(), entity.getY(), entity.getZ());
        }
        return packetTarget;
    }
}
