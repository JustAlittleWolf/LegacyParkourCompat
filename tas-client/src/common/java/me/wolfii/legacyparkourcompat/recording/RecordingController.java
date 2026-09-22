package me.wolfii.legacyparkourcompat.recording;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Client-side recorder/player. Positions are stored but never written back on
 * playback; only start teleport plus per-tick keys and facing are applied.
 *
 * <p>The same controller also owns the optional launch-time automation used by
 * the Gradle reference workflow. Keeping it here means every supported client
 * version uses exactly the same recording format and tick ordering.</p>
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
    private final List<TickFrame> capturedTicks = new ArrayList<TickFrame>();
    private MovementRecording playback;
    private int playbackIndex;
    private String pendingName = "recording";

    private AutomationSettings automation;
    private boolean automationMuted;
    private boolean automationJoinRequested;
    private boolean automationStarted;
    private boolean parkourVersionSelected;
    private boolean automationFinished;
    private int automationJoinAttempts;
    private Path captureOutput;
    private boolean captureOnPlayback;
    private boolean deviationReported;
    private MovementRecording pendingPlayback;
    private Path pendingOutput;
    private boolean pendingExit;
    private String pendingLabel;
    private int pendingPoseTicks;
    private int pendingStableTicks;

    private RecordingController() {
    }

    public static RecordingController get() {
        INSTANCE.ensureAttached();
        return INSTANCE;
    }

    public void attach(MinecraftPlayback minecraft) {
        if (minecraft == null) {
            throw new IllegalArgumentException("Minecraft playback is missing");
        }
        this.minecraft = minecraft;
    }

    /**
     * Configures one process-level automated run. Calling this more than once
     * is safe and resets only the automation state, not manual recording state.
     */
    public void configureAutomation(AutomationSettings settings) {
        ensureAttached();
        this.automation = settings;
        this.automationMuted = false;
        this.automationJoinRequested = false;
        this.automationStarted = false;
        this.parkourVersionSelected = false;
        this.automationFinished = false;
        this.automationJoinAttempts = 0;
        this.captureOutput = null;
        this.captureOnPlayback = false;
        clearPendingPlayback();
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
        ensureAttached();
        this.playing = false;
        this.playback = null;
        this.captureOutput = null;
        this.captureOnPlayback = false;
        clearPendingPlayback();
        this.recordedTicks.clear();
        this.pendingName = name == null || name.trim().isEmpty() ? "recording" : name.trim();
        this.startX = this.minecraft.playerX();
        this.startY = this.minecraft.playerY();
        this.startZ = this.minecraft.playerZ();
        this.startYaw = this.minecraft.playerYaw();
        this.startPitch = this.minecraft.playerPitch();
        this.recording = true;
        this.minecraft.sendGameMessage("Recording started: " + this.pendingName);
    }

    public void stopRecording() {
        stopRecording(this.pendingName);
    }

    public void stopRecording(String name) {
        ensureAttached();
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
            this.minecraft.sendGameMessage("Failed to save recording " + file.getFileName() + ": " + exception.getMessage());
            throw new IllegalStateException("Failed to save recording " + file, exception);
        }
        int tickCount = this.recordedTicks.size();
        this.recordedTicks.clear();
        this.minecraft.sendGameMessage("Recording finished: " + file.getFileName() + " (" + tickCount + " ticks)");
    }

    public void play(String name) {
        ensureAttached();
        Path file = RecordingFiles.file(this.minecraft.gameDirectory(), name);
        playFile(file, null, false);
    }

    /** Starts playback from an arbitrary path, used by the Gradle workflow. */
    public void playFile(Path file, Path output, boolean exitWhenFinished) {
        ensureAttached();
        MovementRecording loaded;
        try {
            loaded = RecordingFiles.read(file);
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to load recording " + file, exception);
        }
        String label = file.getFileName().toString();
        if (output != null && this.minecraft.isConnected()) {
            String command = String.format(Locale.ROOT, "lpcpose %s %s %s %s %s",
                Double.toString(loaded.startX()), Double.toString(loaded.startY()), Double.toString(loaded.startZ()),
                Float.toString(loaded.startYaw()), Float.toString(loaded.startPitch()));
            if (!this.minecraft.sendServerCommand(command)) {
                throw new IllegalStateException("Cannot request the server-authoritative TAS start pose");
            }
            this.pendingPlayback = loaded;
            this.pendingOutput = output;
            this.pendingExit = exitWhenFinished;
            this.pendingLabel = label;
            this.pendingPoseTicks = 0;
            this.minecraft.sendGameMessage("Positioning for " + label);
        } else {
            beginPlayback(loaded, output, exitWhenFinished, label);
        }
    }

    private void beginPlayback(MovementRecording loaded, Path output, boolean exitWhenFinished, String label) {
        this.recording = false;
        this.playback = loaded;
        this.playbackIndex = 0;
        this.playing = true;
        this.captureOutput = output;
        this.captureOnPlayback = output != null;
        this.deviationReported = false;
        this.capturedTicks.clear();
        this.minecraft.teleport(loaded.startX(), loaded.startY(), loaded.startZ(), loaded.startYaw(), loaded.startPitch());
        this.minecraft.sendGameMessage("Playing " + label + (output == null ? "" : " (capturing positions)"));
    }

    public void stopPlaying() {
        ensureAttached();
        if (this.playback == null) {
            if (this.pendingPlayback != null) {
                clearPendingPlayback();
                this.minecraft.sendGameMessage("Playback stopped");
                return;
            }
            this.minecraft.sendGameMessage("No playback is running");
            return;
        }
        this.playback = null;
        this.playing = false;
        this.captureOutput = null;
        this.captureOnPlayback = false;
        this.capturedTicks.clear();
        this.minecraft.applyButtons(0);
        this.minecraft.sendGameMessage("Playback stopped");
    }

    /** Invoked from both the client tick and the local-player tick mixins. */
    public void clientTick() {
        if (this.automation == null) {
            try {
                drivePendingPlayback();
            } catch (RuntimeException exception) {
                this.minecraft.sendGameMessage("Playback failed: " + safeMessage(exception));
            }
            return;
        }
        try {
            drivePendingPlayback();
            if (this.automation.enabled() && this.automation.mute() && !this.automationMuted) {
                muteForAutomation();
            }
            if (!this.automation.enabled() || this.automationFinished) {
                return;
            }
            if (!this.minecraft.isReadyForAutoJoin()) {
                return;
            }
            if (this.automation.autojoin() && !this.minecraft.isConnected() && !this.minecraft.isConnecting()) {
                if (!this.automationJoinRequested || this.automationJoinAttempts % 40 == 0) {
                    this.automationJoinRequested = true;
                    this.automationJoinAttempts++;
                    if (!this.minecraft.connectToServer(this.automation.server())) {
                        if (this.automationJoinAttempts == 1) {
                            this.minecraft.sendGameMessage("Unable to start connection to " + this.automation.server());
                        }
                    } else if (this.automationJoinAttempts == 1) {
                        this.minecraft.sendGameMessage("Joining parkour gym at " + this.automation.server());
                    }
                } else {
                    this.automationJoinAttempts++;
                }
                return;
            }
            // A connection may exist before the local player has been created.
            // The player tick will call this again once teleport/capture is safe.
            if (!this.automationStarted && hasLocalPlayer()) {
                if (this.automation.compare() && !this.parkourVersionSelected) {
                    this.minecraft.selectParkourVersion(this.automation.version());
                    this.parkourVersionSelected = true;
                }
                Path output = this.automation.output();
                if (output == null) {
                    throw new IllegalStateException("Automated playback needs -D" + AutomationSettings.OUTPUT_PROPERTY);
                }
                playFile(this.automation.recording(), output, this.automation.exit());
                this.automationStarted = true;
            }
        } catch (RuntimeException exception) {
            this.automationFinished = true;
            System.err.println("[Legacy Parkour Recording] Automated playback failed: " + safeMessage(exception));
            this.minecraft.sendGameMessage("Automated playback failed: " + safeMessage(exception));
            if (this.automation.mute() && this.automationMuted) {
                try {
                    this.minecraft.restoreAudio();
                } catch (RuntimeException restoreException) {
                    this.minecraft.sendGameMessage("Unable to restore client audio: " + safeMessage(restoreException));
                }
            }
            if (this.automation.exit()) {
                this.minecraft.requestShutdown();
            }
        }
    }

    private void drivePendingPlayback() {
        if (this.pendingPlayback == null) {
            return;
        }
        MovementRecording loaded = this.pendingPlayback;
        if (Double.compare(this.minecraft.playerX(), loaded.startX()) == 0
            && Double.compare(this.minecraft.playerY(), loaded.startY()) == 0
            && Double.compare(this.minecraft.playerZ(), loaded.startZ()) == 0) {
            if (++this.pendingStableTicks >= 40) {
                Path output = this.pendingOutput;
                boolean exit = this.pendingExit;
                String label = this.pendingLabel;
                clearPendingPlayback();
                beginPlayback(loaded, output, exit, label);
                return;
            }
        } else {
            this.pendingStableTicks = 0;
        }
        if (++this.pendingPoseTicks > 400) {
            clearPendingPlayback();
            throw new IllegalStateException("Server did not apply the TAS start pose");
        }
    }

    private void clearPendingPlayback() {
        this.pendingPlayback = null;
        this.pendingOutput = null;
        this.pendingExit = false;
        this.pendingLabel = null;
        this.pendingPoseTicks = 0;
        this.pendingStableTicks = 0;
    }

    /** Called at the start of the local player tick, before movement. */
    public void beforePlayerTick() {
        clientTick();
        if (!this.playing || this.playback == null || this.minecraft == null) {
            return;
        }
        if (this.playbackIndex >= this.playback.ticks().size()) {
            finishPlayback();
            return;
        }
        TickFrame tick = this.playback.ticks().get(this.playbackIndex);
        this.minecraft.applyFacing(tick.yaw(), tick.pitch());
        this.minecraft.applyButtons(tick.buttons());
    }

    /** Called after movement for this tick has run. */
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
            if (this.playback != null && this.playbackIndex < this.playback.ticks().size()
                && (this.captureOnPlayback || (this.automation != null && this.automation.compare()))) {
                TickFrame source = this.playback.ticks().get(this.playbackIndex);
                try {
                    double actualX = this.minecraft.playerX();
                    double actualY = this.minecraft.playerY();
                    double actualZ = this.minecraft.playerZ();
                    if (this.captureOnPlayback) {
                        this.capturedTicks.add(new TickFrame(
                            source.buttons(),
                            source.yaw(),
                            source.pitch(),
                            actualX,
                            actualY,
                            actualZ
                        ));
                    }
                    if (this.automation != null && this.automation.compare()) {
                        reportFirstDeviation(source, actualX, actualY, actualZ);
                    }
                } catch (RuntimeException exception) {
                    this.minecraft.sendGameMessage("Failed to read playback position: " + safeMessage(exception));
                }
            }
            this.playbackIndex++;
        }
    }

    private void reportFirstDeviation(TickFrame expected, double actualX, double actualY, double actualZ) {
        if (this.deviationReported || this.automation == null || !this.automationStarted || !this.automation.compare()) {
            return;
        }
        double deltaX = actualX - expected.x();
        double deltaY = actualY - expected.y();
        double deltaZ = actualZ - expected.z();
        double tolerance = this.automation.tolerance();
        boolean exactMatch = finite(expected.x()) && finite(expected.y()) && finite(expected.z())
            && Double.compare(expected.x(), actualX) == 0
            && Double.compare(expected.y(), actualY) == 0
            && Double.compare(expected.z(), actualZ) == 0;
        boolean withinTolerance = tolerance > 0.0D
            && finite(expected.x()) && finite(expected.y()) && finite(expected.z())
            && finite(actualX) && finite(actualY) && finite(actualZ)
            && Math.abs(deltaX) <= tolerance
            && Math.abs(deltaY) <= tolerance
            && Math.abs(deltaZ) <= tolerance;
        if (exactMatch || withinTolerance) {
            return;
        }
        this.deviationReported = true;
        int tick = this.playbackIndex;
        String details = String.format(Locale.ROOT,
            "first position deviation at tick %d (expected %.17g %.17g %.17g, actual %.17g %.17g %.17g, delta %.17g %.17g %.17g)",
            Integer.valueOf(tick), expected.x(), expected.y(), expected.z(), actualX, actualY, actualZ, deltaX, deltaY, deltaZ);
        this.minecraft.sendGameMessage(details);
        String runId = compactToken(this.automation.runId(), "run");
        String version = compactToken(this.automation.version(), "unknown");
        String signal = String.format(Locale.ROOT, "!lpcf %s %s %d", runId, version, Integer.valueOf(tick));
        if (signal.length() > 64) {
            int fixedLength = "!lpcf  ".length() + 1 + Integer.toString(tick).length();
            int available = Math.max(1, 64 - fixedLength);
            int runLength = Math.min(runId.length(), Math.max(1, available / 2));
            runId = runId.substring(0, runLength);
            int versionLength = Math.min(version.length(), Math.max(1, available - runLength));
            version = version.substring(0, versionLength);
            signal = String.format(Locale.ROOT, "!lpcf %s %s %d", runId, version, Integer.valueOf(tick));
        }
        if (!this.minecraft.isConnected() || !this.minecraft.sendChatMessage(signal)) {
            this.minecraft.sendGameMessage("Unable to send the first-deviation snapshot signal to the parkour gym");
        }
    }

    private static String compactToken(String value, String fallback) {
        String token = value == null ? "" : value.replaceAll("[^A-Za-z0-9._:+/@-]", "_");
        if (token.isEmpty()) {
            token = fallback;
        }
        return token.length() > 36 ? token.substring(0, 36) : token;
    }

    private static boolean finite(double value) {
        return !Double.isNaN(value) && !Double.isInfinite(value);
    }

    private boolean hasLocalPlayer() {
        try {
            this.minecraft.playerX();
            return true;
        } catch (RuntimeException exception) {
            return false;
        }
    }

    private void finishPlayback() {
        MovementRecording completed = this.playback;
        Path output = this.captureOutput;
        boolean automated = this.automation != null && this.automationStarted;
        int tickCount = this.capturedTicks.size();
        this.playing = false;
        this.playback = null;
        this.captureOnPlayback = false;
        this.captureOutput = null;
        this.minecraft.applyButtons(0);
        if (output != null && completed != null) {
            MovementRecording result = new MovementRecording(
                completed.startX(),
                completed.startY(),
                completed.startZ(),
                completed.startYaw(),
                completed.startPitch(),
                this.capturedTicks
            );
            try {
                RecordingFiles.write(output, result);
                this.minecraft.sendGameMessage("Playback finished: captured " + tickCount + " ticks to " + output.getFileName());
            } catch (IOException exception) {
                this.minecraft.sendGameMessage("Failed to save playback result " + output + ": " + exception.getMessage());
                if (automated) {
                    this.automationFinished = true;
                }
            }
        } else {
            this.minecraft.sendGameMessage("Playback finished");
        }
        this.capturedTicks.clear();
        if (automated) {
            this.automationFinished = true;
            if (this.automation.mute()) {
                try {
                    this.minecraft.restoreAudio();
                } catch (RuntimeException exception) {
                    this.minecraft.sendGameMessage("Unable to restore client audio: " + safeMessage(exception));
                }
            }
            if (this.automation.exit()) {
                this.minecraft.requestShutdown();
            }
        }
    }

    private void muteForAutomation() {
        try {
            this.minecraft.muteAudio();
        } catch (RuntimeException exception) {
            System.err.println("[Legacy Parkour Recording] Unable to mute client audio: " + safeMessage(exception));
            this.minecraft.sendGameMessage("Unable to mute client audio: " + safeMessage(exception));
        } finally {
            this.automationMuted = true;
        }
    }

    private static String safeMessage(RuntimeException exception) {
        String message = exception.getMessage();
        return message == null || message.isEmpty() ? exception.getClass().getSimpleName() : message;
    }

    public boolean handleCommand(String raw) {
        if (raw == null) {
            return false;
        }
        String message = raw.startsWith(".") ? raw.substring(1) : raw;
        String trimmed = message.trim();
        try {
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
        } catch (RuntimeException exception) {
            ensureAttached();
            this.minecraft.sendGameMessage("Recording command failed: " + safeMessage(exception));
            return true;
        }
        return false;
    }
}
