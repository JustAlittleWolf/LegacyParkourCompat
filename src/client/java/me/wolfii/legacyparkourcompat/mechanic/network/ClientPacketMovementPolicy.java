package me.wolfii.legacyparkourcompat.mechanic.network;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.network.ClientReceivedInterpolationHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.network.LocalPlayerPacketInterpolationAuthorityHook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;

/** Shared client packet gate for resolved local-player packet operations. */
public final class ClientPacketMovementPolicy {
    private ClientPacketMovementPolicy() {
    }

    public static boolean isCurrentLocalPlayer(Entity entity) {
        return entity instanceof LocalPlayer player && Minecraft.getInstance().player == player;
    }

    public static boolean handlesLegacyPackets(Entity entity) {
        if (!isCurrentLocalPlayer(entity)) {
            return false;
        }
        return MovementRuntime.find(ClientReceivedInterpolationHook.class, entity)
            .map(hook -> hook.handlesLegacyPackets(entity))
            .orElse(false);
    }

    public static boolean acceptsServerMovement(Entity entity) {
        if (!(entity instanceof LocalPlayer player) || !isCurrentLocalPlayer(entity)) {
            return false;
        }
        return MovementRuntime.find(LocalPlayerPacketInterpolationAuthorityHook.class, player)
            .map(hook -> hook.acceptsServerMovement(player))
            .orElse(false);
    }
}
