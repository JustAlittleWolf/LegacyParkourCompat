package me.wolfii.legacyparkourcompat.config.version;

import me.wolfii.legacyparkourcompat.api.MovementController;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;

/**
 * Session-wide movement version selection for the UI.
 *
 * <p>The client keeps the typed value in memory only. Closing the game returns
 * to vanilla movement.
 *
 * <p>Valid ids are {@link ParkourVersion} patch strings only. Unknown Minecraft
 * releases are invalid, not unimplemented.
 */
public final class MovementVersions {
    private static boolean wanted;
    private static boolean serverForced;
    private static String input = "";

    private MovementVersions() {
    }

    public static boolean isWanted() {
        return wanted;
    }

    /**
     * {@code true} while connected to a server that has this mod. That server
     * owns the parkour version; the UI must not change it.
     */
    public static boolean isServerForced() {
        return serverForced;
    }

    public static void setServerForced(boolean serverForced) {
        MovementVersions.serverForced = serverForced;
        if (!serverForced) {
            apply();
        }
    }

    public static void setWanted(boolean wanted) {
        MovementVersions.wanted = wanted;
        apply();
    }

    /**
     * Updates the typed value without flipping the UI enable switch.
     * Empty-but-enabled stays vanilla until a historical version is entered.
     */
    public static void setTypedValue(@Nullable String value) {
        input = normalize(value);
        apply();
    }

    public static String getInput() {
        return input;
    }

    public static void setInput(@Nullable String value) {
        input = normalize(value);
        ParkourVersion version = parkourVersion(input);
        if (version != null && !version.isCurrent()) {
            wanted = true;
        } else if (input.isEmpty() || isNativeGameVersion(input)) {
            wanted = false;
        }
        apply();
    }

    /**
     * Historical version the movement framework should emulate.
     * Empty means vanilla / disabled.
     */
    public static Optional<String> selectedVersion() {
        ParkourVersion selected = MovementController.get().selectedVersion();
        if (selected.isCurrent()) {
            return Optional.empty();
        }
        return Optional.of(selected.id());
    }

    public static VersionStatus status(@Nullable String value) {
        String normalized = normalize(value);
        if (normalized.isEmpty() || isNativeGameVersion(normalized)) {
            return VersionStatus.VANILLA;
        }
        ParkourVersion parkour = historical(normalized);
        if (parkour == null) {
            return VersionStatus.INVALID;
        }
        if (parkour == MovementController.get().nativeVersion()) {
            return VersionStatus.VANILLA;
        }
        return VersionStatus.VALID;
    }

    /**
     * Historical versions older than the running game. {@link ParkourVersion#CURRENT}
     * is listed separately as the default UI option.
     */
    public static List<ParkourVersion> listedVersions() {
        return MovementController.get().selectableVersions().stream()
            .filter(version -> !version.isCurrent())
            .toList();
    }

    /**
     * Applies a list selection. {@link ParkourVersion#CURRENT} turns legacy
     * movement off.
     */
    public static void select(ParkourVersion version) {
        if (version == null || version.isCurrent()) {
            wanted = false;
            input = "";
        } else {
            wanted = true;
            input = version.id();
        }
        apply();
    }

    /**
     * Version the config screen should show as selected.
     */
    public static ParkourVersion selectedForUi() {
        if (serverForced) {
            return MovementController.get().selectedVersion();
        }
        if (!wanted) {
            return ParkourVersion.CURRENT;
        }
        ParkourVersion match = parkourVersion(input);
        return match == null ? ParkourVersion.CURRENT : match;
    }

    public static String nativeGameVersion() {
        return ParkourVersion.nativeGameVersion();
    }

    public static String normalize(@Nullable String value) {
        if (value == null) {
            return "";
        }
        return value.strip();
    }

    public static @Nullable ParkourVersion parkourVersion(String normalized) {
        if (normalized.isEmpty() || isNativeGameVersion(normalized)) {
            return ParkourVersion.CURRENT;
        }
        ParkourVersion match = historical(normalized);
        if (match == null) {
            return null;
        }
        if (match == MovementController.get().nativeVersion()) {
            return ParkourVersion.CURRENT;
        }
        return match;
    }

    private static boolean isNativeGameVersion(String normalized) {
        String nativeId = nativeGameVersion();
        return !nativeId.isEmpty() && nativeId.equals(normalized);
    }

    /**
     * {@link ParkourVersion} whose {@link ParkourVersion#patches()} contains
     * {@code id}. Does not treat unknown ids as {@link ParkourVersion#CURRENT}.
     */
    private static @Nullable ParkourVersion historical(String id) {
        for (ParkourVersion version : ParkourVersion.values()) {
            if (version.isCurrent()) {
                continue;
            }
            if (version.id().equals(id) || version.patches().contains(id)) {
                return version;
            }
        }
        return null;
    }

    private static void apply() {
        if (serverForced) {
            return;
        }
        MovementController controller = MovementController.get();
        if (!wanted) {
            controller.disable();
            return;
        }
        ParkourVersion version = parkourVersion(input);
        if (version == null || version.isCurrent()) {
            controller.disable();
            return;
        }
        controller.select(version);
    }
}
