package me.wolfii.legacyparkourcompat.network;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.ProtocolInfo;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import me.wolfii.legacyparkourcompat.LegacyParkourCompat;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import net.fabricmc.loader.api.FabricLoader;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.UUID;

/**
 * Isolated so ViaVersion classes are only linked when Via is installed.
 */
final class ViaVersionLookup {
    private static final String[] VIAFABRICPLUS_TYPES = {
        "com.viaversion.viafabricplus.ViaFabricPlus",
        "com.viaversion.viafabricplus.api.ViaFabricPlus"
    };

    private ViaVersionLookup() {
    }

    static Optional<ParkourVersion> translatedClientVersion(UUID playerId) {
        try {
            if (!Via.getAPI().isInjected(playerId)) {
                return Optional.empty();
            }
            ProtocolVersion protocol = Via.getAPI().getPlayerProtocolVersion(playerId);
            if (protocol == null || !protocol.isKnown()) {
                return Optional.empty();
            }
            ProtocolVersion serverProtocol = Via.getAPI().getServerVersion().lowestSupportedProtocolVersion();
            if (serverProtocol != null && protocol.equals(serverProtocol)) {
                return Optional.empty();
            }
            return Optional.of(toParkourVersion(protocol));
        } catch (Throwable exception) {
            LegacyParkourCompat.LOGGER.debug("Failed to query ViaVersion protocol for {}", playerId, exception);
            return Optional.empty();
        }
    }

    static Optional<ParkourVersion> clientTranslatedServerVersion() {
        try {
            Optional<ProtocolVersion> plus = viaFabricPlusTarget();
            if (plus.isPresent()) {
                return inUse(plus.get());
            }
            return viaFabricClientTarget();
        } catch (Throwable exception) {
            LegacyParkourCompat.LOGGER.debug("Failed to query client Via target version", exception);
            return Optional.empty();
        }
    }

    private static Optional<ProtocolVersion> viaFabricPlusTarget() {
        for (String className : VIAFABRICPLUS_TYPES) {
            try {
                Class<?> type = Class.forName(className);
                Object impl = type.getMethod("getImpl").invoke(null);
                if (impl == null) {
                    continue;
                }
                Method getTarget = impl.getClass().getMethod("getTargetVersion");
                Object value = getTarget.invoke(impl);
                if (value instanceof ProtocolVersion protocol && protocol.isKnown()) {
                    return Optional.of(protocol);
                }
            } catch (ClassNotFoundException ignored) {
            } catch (ReflectiveOperationException exception) {
                LegacyParkourCompat.LOGGER.debug("ViaFabricPlus API {} is present but unreadable", className, exception);
            }
        }
        return Optional.empty();
    }

    private static Optional<ParkourVersion> viaFabricClientTarget() {
        try {
            for (UserConnection connection : Via.getManager().getConnectionManager().getConnections()) {
                ProtocolInfo info = connection.getProtocolInfo();
                if (info == null) {
                    continue;
                }
                ProtocolVersion server = info.serverProtocolVersion();
                ProtocolVersion client = info.protocolVersion();
                if (server == null || !server.isKnown()) {
                    continue;
                }
                if (client != null && server.equals(client)) {
                    continue;
                }
                return inUse(server);
            }
        } catch (Throwable exception) {
            LegacyParkourCompat.LOGGER.debug("Failed to read ViaFabric client connections", exception);
        }
        return Optional.empty();
    }

    private static Optional<ParkourVersion> inUse(ProtocolVersion target) {
        ProtocolVersion nativeProtocol = nativeProtocol();
        if (nativeProtocol != null && target.equals(nativeProtocol)) {
            return Optional.empty();
        }
        ParkourVersion parkour = toParkourVersion(target);
        if (parkour.isCurrent() && nativeProtocol != null && target.equals(nativeProtocol)) {
            return Optional.empty();
        }
        return Optional.of(parkour);
    }

    private static ProtocolVersion nativeProtocol() {
        String id = FabricLoader.getInstance()
            .getModContainer("minecraft")
            .map(container -> container.getMetadata().getVersion().getFriendlyString())
            .orElse("");
        if (id.isEmpty()) {
            return null;
        }
        ProtocolVersion closest = ProtocolVersion.getClosest(id);
        return closest != null && closest.isKnown() ? closest : null;
    }

    static ParkourVersion toParkourVersion(ProtocolVersion protocol) {
        for (ParkourVersion version : ParkourVersion.values()) {
            if (version.isCurrent()) {
                continue;
            }
            for (String patch : version.patches()) {
                if (protocol.getName().equals(patch) || protocol.getIncludedVersions().contains(patch)) {
                    return version;
                }
                ProtocolVersion closest = ProtocolVersion.getClosest(patch);
                if (closest != null && closest.equals(protocol)) {
                    return version;
                }
            }
        }
        return ParkourVersion.of(preferredName(protocol));
    }

    private static String preferredName(ProtocolVersion protocol) {
        for (String included : protocol.getIncludedVersions()) {
            if (!included.contains("/") && !included.endsWith(".x")) {
                return included;
            }
        }
        String name = protocol.getName();
        int slash = name.indexOf('/');
        if (slash >= 0) {
            return name.substring(slash + 1);
        }
        if (name.endsWith(".x") && name.length() > 2) {
            return name.substring(0, name.length() - 2);
        }
        return name;
    }
}
