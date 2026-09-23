package me.wolfii.legacyparkourcompat.recording;

import java.nio.file.Path;

/**
 * Version-specific hooks for simulation pose and inputs.
 * Implementations must read/write the values the movement code uses, not the camera.
 */
public interface MinecraftPlayback {
    Path gameDirectory();

    double playerX();

    double playerY();

    double playerZ();

    float playerYaw();

    float playerPitch();

    void teleport(double x, double y, double z, float yaw, float pitch);

    /** Applies the velocity saved by an external playback source after teleporting. */
    default void applyVelocity(double x, double y, double z) {
        if (x != 0.0 || y != 0.0 || z != 0.0) {
            throw new IllegalStateException("This client cannot apply a TAS starting velocity");
        }
    }

    int currentButtons();

    void applyButtons(int buttons);

    void applyFacing(float yaw, float pitch);

    void sendGameMessage(String message);

    /** Sends a normal chat message to the connected gym, when supported. */
    default boolean sendChatMessage(String message) {
        return false;
    }

    /** Sends a server command without a leading slash. */
    default boolean sendServerCommand(String command) {
        return false;
    }

    /** Sends a Gym control payload when the selected client protocol supports it. */
    default boolean sendGymMessage(String message) {
        return false;
    }

    /**
     * Returns whether the local client has a multiplayer connection.  Older
     * clients do not expose the same connection accessor, so the default is
     * deliberately conservative for recording-only implementations.
     */
    default boolean isConnected() {
        return true;
    }

    /** Returns whether the client is already showing a connection screen. */
    default boolean isConnecting() {
        return false;
    }

    /** Initial resource reloads must finish before a world is joined. */
    default boolean isReadyForAutoJoin() {
        return true;
    }

    /**
     * Starts a connection to an address such as {@code localhost:25565}.
     * Implementations may return {@code false} when the version-specific
     * client API cannot be resolved.
     */
    default boolean connectToServer(String address) {
        return false;
    }

    /** Selects a movement profile when the current Legacy Parkour mod is present. */
    default boolean selectParkourVersion(String version) {
        return false;
    }

    /** Mutes client audio for headless/reference runs. */
    default void muteAudio() {
    }

    /** Restores the in-memory audio level changed by {@link #muteAudio()}. */
    default void restoreAudio() {
    }

    /** Requests a clean client shutdown after an automated run. */
    default void requestShutdown() {
    }
}
