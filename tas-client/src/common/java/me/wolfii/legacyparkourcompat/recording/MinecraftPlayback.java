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

    int currentButtons();

    void applyButtons(int buttons);

    void applyFacing(float yaw, float pitch);

    void sendGameMessage(String message);
}
