package me.wolfii.legacyparkourcompat.network;

import java.util.Optional;
import java.util.UUID;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import net.fabricmc.loader.api.FabricLoader;

/**
 * Optional ViaVersion / ViaFabric / ViaFabricPlus lookup.
 */
public final class ViaVersionAccess {
    private ViaVersionAccess() {
    }

    public static boolean isViaOnServer() {
        FabricLoader loader = FabricLoader.getInstance();
        return loader.isModLoaded("viaversion") || loader.isModLoaded("viafabric");
    }

    public static boolean isViaOnClient() {
        FabricLoader loader = FabricLoader.getInstance();
        return loader.isModLoaded("viafabric")
                || loader.isModLoaded("viafabricplus")
                || loader.isModLoaded("viaversion");
    }

    /**
     * Protocol of a connecting player if Via on the server translated this connection.
     * Empty when Via is absent or this client is on the server's native protocol.
     */
    public static Optional<ParkourVersion> translatedClientVersion(UUID playerId) {
        if (!isViaOnServer()) {
            return Optional.empty();
        }
        return ViaVersionLookup.translatedClientVersion(playerId);
    }

    /**
     * Parkour version of the remote server when client-side ViaFabric/ViaFabricPlus is
     * actively translating. Empty when Via is absent or targeting native protocol.
     */
    public static Optional<ParkourVersion> clientTranslatedServerVersion() {
        if (!isViaOnClient()) {
            return Optional.empty();
        }
        return ViaVersionLookup.clientTranslatedServerVersion();
    }
}
