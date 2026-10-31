package me.wolfii.legacyparkourcompat.change.v1_18_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.network.ClientReceivedInterpolationHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.network.LocalPlayerPacketInterpolationAuthorityHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

/**
 * Preserves the native target and authority policy before the 1.19.3 Mojmap
 * Entity.isControlledByLocalInstance result and its 1.19.4 predicate change.
 * The latter is consumed by ClientPacketListener.handleMoveEntity/handleTeleportEntity
 * and LivingEntity.aiStep; the current target handlers remain native before this boundary.
 */
@MovementChange(emulates = ParkourVersion.V1_18_2)
public final class LocalPlayerPacketInterpolationBarrier
    implements ClientReceivedInterpolationHook, LocalPlayerPacketInterpolationAuthorityHook {
    @Override
    public boolean handlesLegacyPackets(Entity entity) {
        return false;
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

    @Override
    public boolean acceptsServerMovement(LocalPlayer player) {
        return false;
    }
}
