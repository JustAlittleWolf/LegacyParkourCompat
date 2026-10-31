package me.wolfii.legacyparkourcompat.change.v1_16;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.network.ClientPacketPositionState;
import me.wolfii.legacyparkourcompat.mechanic.hook.network.ClientRelativeMoveTargetHook;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

/**
 * 1.15.2 Mojmap and 1.16.1 Mojmap ClientPacketListener.handleMoveEntity add each packet short to Entity.xp/yp/zp
 * and passes ClientboundMoveEntityPacket.packetToEntity(long,long,long) to LivingEntity.lerpTo.
 * In 1.16.2, ClientboundMoveEntityPacket.updateEntityPosition(Vec3) preserves a zero-delta axis
 * as a raw double and floors only nonzero axes. 26.2 unobfuscated
 * ClientPacketListener.handleMoveEntity uses VecDeltaCodec; legacy accumulators remain separate.
 * 26.2 now treats the local player as authoritative and updates only that codec, so its
 * handleMoveEntity authority decision is narrowed to the historical non-authoritative route.
 */
@MovementChange(emulates = ParkourVersion.V1_16)
public final class RelativeMoveLongPacketTarget implements ClientRelativeMoveTargetHook {
    private static final double PACKET_SCALE = 2.4414062E-4F;

    @Override
    public Vec3 target(Entity entity, long xa, long ya, long za, Vec3 nativeTarget) {
        ClientPacketPositionState state = (ClientPacketPositionState) entity;
        long x = state.legacyPacketX() + xa;
        long y = state.legacyPacketY() + ya;
        long z = state.legacyPacketZ() + za;
        state.legacyPacketCoordinates(x, y, z);
        return new Vec3(x, y, z).scale(PACKET_SCALE);
    }
}
