package me.wolfii.legacyparkourcompat.mechanic.hook.network;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.client.player.LocalPlayer;

/** Whether the historical client accepts received server movement for its local player. */
@MechanicType("client_packet.interpolation_authority")
public interface LocalPlayerPacketInterpolationAuthorityHook extends VersionedMechanic {
    boolean acceptsServerMovement(LocalPlayer player);
}
