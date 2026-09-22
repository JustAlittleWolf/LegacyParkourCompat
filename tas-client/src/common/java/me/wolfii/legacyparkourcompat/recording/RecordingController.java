package me.wolfii.legacyparkourcompat.recording;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Client-side recorder/player. Positions are stored but never written back on
 * playback; only start teleport plus per-tick keys and facing are applied.
 */
public final class RecordingController {
    private static final RecordingController INSTANCE = new RecordingController();

    private MinecraftPlayback minecraft;
    private boolean recording;
    private boolean playing;
    private double startX;
    private double startY;
    private double startZ;
    private float startYaw;
    private float startPitch;
    private final List<TickFrame> recordedTicks = new ArrayList<TickFrame>();
    private MovementRecording playback;
    private int playbackIndex;
    private String pendingName = "recording";

    private RecordingController() {
    }

    public static RecordingController get() {
        INSTANCE.ensureAttached();
        return INSTANCE;
    }

    public void attach(MinecraftPlayback minecraft) {
        this.minecraft = minecraft;
    }

    private void ensureAttached() {
        if (this.minecraft == null) {
            this.minecraft = ReflectivePlayback.INSTANCE;
        }
    }

    public boolean isRecording() {
        return this.recording;
    }

    public boolean isPlaying() {
        return this.playing;
    }

    public void startRecording(String name) {
        if (this.minecraft == null) {
            throw new IllegalStateException("Minecraft playback is not attached");
        }
        this.playing = false;
        this.playback = null;
        this.recordedTicks.clear();
        this.pendingName = name == null || name.trim().isEmpty() ? "recording" : name.trim();
        this.startX = this.minecraft.playerX();
        this.startY = this.minecraft.playerY();
        this.startZ = this.minecraft.playerZ();
        this.startYaw = this.minecraft.playerYaw();
        this.startPitch = this.minecraft.playerPitch();
        this.recording = true;
        this.minecraft.sendGameMessage("Recording started");
    }

    public void stopRecording() {
        stopRecording(this.pendingName);
    }

    public void stopRecording(String name) {
        if (this.minecraft == null) {
            throw new IllegalStateException("Minecraft playback is not attached");
        }
        if (!this.recording) {
            this.minecraft.sendGameMessage("No recording is running");
            return;
        }
        this.recording = false;
        MovementRecording movement = new MovementRecording(
            this.startX,
            this.startY,
            this.startZ,
            this.startYaw,
            this.startPitch,
            this.recordedTicks
        );
        Path file = RecordingFiles.file(this.minecraft.gameDirectory(), name);
        try {
            RecordingFiles.write(file, movement);
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to save recording " + file, exception);
        }
        this.recordedTicks.clear();
        this.minecraft.sendGameMessage("Saved recording to " + file.getFileName());
    }

    public void play(String name) {
        if (this.minecraft == null) {
            throw new IllegalStateException("Minecraft playback is not attached");
        }
        Path file = RecordingFiles.file(this.minecraft.gameDirectory(), name);
        MovementRecording loaded;
        try {
            loaded = RecordingFiles.read(file);
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to load recording " + file, exception);
        }
        this.recording = false;
        this.playback = loaded;
        this.playbackIndex = 0;
        this.playing = true;
        this.minecraft.teleport(loaded.startX(), loaded.startY(), loaded.startZ(), loaded.startYaw(), loaded.startPitch());
        this.minecraft.sendGameMessage("Playing " + file.getFileName());
    }

    public void stopPlaying() {
        if (this.playback == null) {
            this.minecraft.sendGameMessage("No playback is running");
            return;
        }
        this.playback = null;
        this.playing = false;
        this.minecraft.sendGameMessage("Playback stopped");
    }

    /**
     * Called at the start of the local player tick, before movement.
     */
    public void beforePlayerTick() {
        if (!this.playing || this.playback == null || this.minecraft == null) {
            return;
        }
        if (this.playbackIndex >= this.playback.ticks().size()) {
            this.playing = false;
            this.playback = null;
            this.minecraft.applyButtons(0);
            this.minecraft.sendGameMessage("Playback finished");
            return;
        }
        TickFrame tick = this.playback.ticks().get(this.playbackIndex);
        this.minecraft.applyFacing(tick.yaw(), tick.pitch());
        this.minecraft.applyButtons(tick.buttons());
    }

    /**
     * Called after movement for this tick has run.
     */
    public void afterPlayerTick() {
        if (this.minecraft == null) {
            return;
        }
        if (this.recording) {
            this.recordedTicks.add(new TickFrame(
                this.minecraft.currentButtons(),
                this.minecraft.playerYaw(),
                this.minecraft.playerPitch(),
                this.minecraft.playerX(),
                this.minecraft.playerY(),
                this.minecraft.playerZ()
            ));
        }
        if (this.playing) {
            this.playbackIndex++;
        }
    }

    public boolean handleCommand(String raw) {
        if (raw == null) {
            return false;
        }
        String message = raw.startsWith(".") ? raw.substring(1) : raw;
        String trimmed = message.trim();
        if (trimmed.equals("recording start")) {
            startRecording("recording");
            return true;
        }
        if (trimmed.startsWith("recording start ")) {
            startRecording(trimmed.substring("recording start ".length()));
            return true;
        }
        if (trimmed.equals("recording stop")) {
            stopRecording();
            return true;
        }
        if (trimmed.startsWith("recording stop ")) {
            stopRecording(trimmed.substring("recording stop ".length()));
            return true;
        }
        if (trimmed.equals("playback stop")) {
            stopPlaying();
            return true;
        }
        if (trimmed.startsWith("playback ")) {
            play(trimmed.substring("playback ".length()));
            return true;
        }
        return false;
    }
}
