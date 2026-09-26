package me.wolfii.legacyparkourcompat.recording;

import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private ReusableRecordingMetadata.Mode recordingReusableMode;
    private JsonObject recordingSetup;
    private MovementRecording workerPreparedRecording;
    private int workerRegistrationTicks;

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
    private int firstDeviationIndex = -1;
    private String followupDetail;
    private TaskWorker worker;
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
        this.recordingReusableMode = null;
        clearPendingPlayback();
        if (this.worker == null) {
            String workerVersion = System.getProperty("legacyparkour.tas.workerVersion");
            if (workerVersion != null && !workerVersion.trim().isEmpty()) {
                this.worker = new TaskWorker(System.getProperty("legacyparkour.tas.controlHost", "127.0.0.1"), workerVersion.trim());
            }
        }
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
        this.recordingReusableMode = null;
        this.pendingName = name == null || name.trim().isEmpty() ? "recording" : name.trim();
        this.startX = this.minecraft.playerX();
        this.startY = this.minecraft.playerY();
        this.startZ = this.minecraft.playerZ();
        this.startYaw = this.minecraft.playerYaw();
        this.startPitch = this.minecraft.playerPitch();
        this.recordingSetup = RecordingSetup.defaults();
        boolean creative = this.minecraft.isCreativeMode();
        this.recordingSetup.addProperty("gameMode", creative ? "creative" : "survival");
        this.recordingSetup.addProperty("flying", creative && this.minecraft.isFlying());
        this.recording = true;
        this.minecraft.sendGameMessage("Recording started: " + this.pendingName);
    }

    public void startReusableRecording(String name, ReusableRecordingMetadata.Mode mode) {
        startRecording(name);
        this.recordingReusableMode = mode;
        this.minecraft.sendGameMessage("Reusable mode: " + mode.name().toLowerCase(Locale.ROOT)
            + " (stationary edge ticks will be trimmed)");
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
        MovementRecording movement = new MovementRecording(
            this.startX,
            this.startY,
            this.startZ,
            this.startYaw,
            this.startPitch,
            0.0, 0.0, 0.0, this.recordedTicks, this.recordingSetup
        );
        if (!this.recordedTicks.isEmpty()) {
            movement = ReusableRecordingPlacement.trimStationaryEnds(movement);
        }
        if (this.recordingReusableMode == ReusableRecordingMetadata.Mode.BLOCK) {
            JsonObject setup = movement.setup();
            setup.add("reusable", ReusableRecordingMetadata.block().toJson());
            movement = movement.withSetup(setup);
        } else if (this.recordingReusableMode == ReusableRecordingMetadata.Mode.POSITIONS) {
            JsonObject setup = movement.setup();
            setup.add("reusable", ReusableRecordingMetadata.positions(this.startX, this.startY, this.startZ).toJson());
            movement = movement.withSetup(setup);
        }
        this.recording = false;
        Path file = RecordingFiles.file(this.minecraft.gameDirectory(), name);
        try {
            RecordingFiles.write(file, movement);
        } catch (IOException exception) {
            this.minecraft.sendGameMessage("Failed to save recording " + file.getFileName() + ": " + exception.getMessage());
            throw new IllegalStateException("Failed to save recording " + file, exception);
        }
        int tickCount = movement.ticks().size();
        this.recordedTicks.clear();
        this.recordingReusableMode = null;
        this.minecraft.sendGameMessage("Recording finished: " + file.getFileName() + " (" + tickCount + " ticks)");
    }

    public void addReusablePosition(String recordingName, String positionName) {
        ensureAttached();
        if (this.recording) throw new IllegalStateException("Stop recording before adding positions");
        Path file = RecordingFiles.file(this.minecraft.gameDirectory(), recordingName);
        try {
            ReusableRecordingMetadata.read(file)
                .withPosition(positionName, this.minecraft.playerX(), this.minecraft.playerY(), this.minecraft.playerZ())
                .write(file);
        } catch (IOException exception) {
            throw new IllegalStateException("Could not update reusable positions for " + recordingName, exception);
        }
        this.minecraft.sendGameMessage("Saved reusable position '" + positionName + "' for " + file.getFileName());
    }

    private void setRecordingEquipment(String name, int level) {
        if (!this.recording) throw new IllegalStateException("Start recording before setting equipment metadata");
        if (!"swiftSneak".equals(name) && !"soulSpeed".equals(name) && !"depthStrider".equals(name))
            throw new IllegalArgumentException("Equipment must be swiftSneak, soulSpeed, or depthStrider");
        if (level < 0 || level > 3) throw new IllegalArgumentException("Equipment level must be 0..3");
        this.recordingSetup.getAsJsonObject("equipment").addProperty(name, level);
        this.minecraft.sendGameMessage("Recording setup: " + name + " " + level);
    }

    private void addRecordingEffect(String id, int amplifier, int durationTicks) {
        if (!this.recording) throw new IllegalStateException("Start recording before setting potion metadata");
        if (!id.matches("[a-z0-9_.-]+:[a-z0-9_./-]+") || amplifier < 0 || amplifier > 255 || durationTicks <= 0)
            throw new IllegalArgumentException("Use a namespaced effect, amplifier 0..255, and positive durationTicks");
        JsonObject effect = new JsonObject();
        effect.addProperty("id", id);
        effect.addProperty("amplifier", amplifier);
        effect.addProperty("durationTicks", durationTicks);
        this.recordingSetup.getAsJsonArray("potionEffects").add(effect);
        this.minecraft.sendGameMessage("Recording setup: potion " + id);
    }

    public void play(String name) {
        ensureAttached();
        String trimmed = name.trim();
        Path file;
        if (trimmed.toLowerCase(Locale.ROOT).endsWith(".json") || trimmed.toLowerCase(Locale.ROOT).endsWith(".lprc")) {
            Path given = Paths.get(trimmed);
            file = given.isAbsolute() || given.getParent() != null
                ? given : RecordingFiles.directory(this.minecraft.gameDirectory()).resolve(given);
        } else {
            file = RecordingFiles.file(this.minecraft.gameDirectory(), trimmed);
        }
        playFile(file, null, false);
    }

    /** Starts playback from an arbitrary path, used by the Gradle workflow. */
    public void playFile(Path file, Path output, boolean exitWhenFinished) {
        ensureAttached();
        MovementRecording loaded;
        try {
            loaded = file.getFileName().toString().toLowerCase(Locale.ROOT).endsWith(".json")
                ? JsonPlaybackFiles.read(file) : RecordingFiles.read(file);
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to load recording " + file, exception);
        }
        playLoaded(loaded, output, exitWhenFinished, file.getFileName().toString());
    }

    private void playLoaded(MovementRecording loaded, Path output, boolean exitWhenFinished, String label) {
        if (output != null && this.minecraft.isConnected()) {
            if (!this.minecraft.sendGymMessage(tasPosePayload(loaded))
                && !this.minecraft.sendServerCommand(tasPoseCommand(loaded))) {
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
        this.firstDeviationIndex = -1;
        this.followupDetail = null;
        this.capturedTicks.clear();
        this.minecraft.teleport(loaded.startX(), loaded.startY(), loaded.startZ(), loaded.startYaw(), loaded.startPitch());
        this.minecraft.applyVelocity(loaded.startVelocityX(), loaded.startVelocityY(), loaded.startVelocityZ());
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
            if (this.worker != null && this.minecraft.isConnected() && hasLocalPlayer()
                && this.workerRegistrationTicks++ % 40 == 0) {
                this.minecraft.sendGymMessage("worker " + this.worker.id());
                this.minecraft.sendServerCommand("lpcworker " + this.worker.id());
            }
            if (this.worker != null && (this.automation == null || !this.automation.enabled() || this.automationFinished)) {
                JsonObject command = this.worker.poll();
                if (command != null) acceptWorkerCommand(command);
            }
            drivePendingPlayback();
            if (this.automation.enabled() && this.automation.mute() && !this.automationMuted) {
                muteForAutomation();
            }
            if (!this.automation.enabled() || this.automationFinished) {
                if (this.worker == null || this.minecraft.isConnected() || this.minecraft.isConnecting()
                    || !this.minecraft.isReadyForAutoJoin()) return;
                if (this.automationJoinAttempts++ % 40 == 0) {
                    this.minecraft.connectToServer(this.automation.server());
                }
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
                if (!this.parkourVersionSelected && !"unknown".equals(this.automation.version())) {
                    boolean selected = this.minecraft.selectParkourVersion(this.automation.version());
                    if (this.automation.compare() && !selected) {
                        throw new IllegalStateException("Legacy Parkour mod is unavailable for comparison");
                    }
                    this.parkourVersionSelected = true;
                }
                Path output = this.automation.output();
                if (output == null) {
                    throw new IllegalStateException("Automated playback needs -D" + AutomationSettings.OUTPUT_PROPERTY);
                }
                if (this.workerPreparedRecording != null) {
                    MovementRecording prepared = this.workerPreparedRecording;
                    this.workerPreparedRecording = null;
                    playLoaded(prepared, output, this.automation.exit(), this.automation.recording().getFileName().toString());
                } else {
                    playFile(this.automation.recording(), output, this.automation.exit());
                }
                this.automationStarted = true;
            }
        } catch (RuntimeException exception) {
            this.automationFinished = true;
            if (this.worker != null && this.automation != null && this.automation.enabled()) {
                this.worker.event(this.automation.runId(), "error", safeMessage(exception), this.playbackIndex);
            }
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

    private void acceptWorkerCommand(JsonObject command) {
        String runId = command.get("runId").getAsString();
        try {
            Path source = Paths.get(command.get("recording").getAsString());
            Path output = Paths.get(command.get("output").getAsString());
            String profile = command.has("profile") ? command.get("profile").getAsString() : "unknown";
            boolean compare = command.has("compare") && command.get("compare").getAsBoolean();
            Long maxUlps = command.has("maxUlps") ? Long.valueOf(command.get("maxUlps").getAsLong()) : null;
            double tolerance = command.has("tolerance") ? command.get("tolerance").getAsDouble() : 0.0D;
            MovementRecording sourceRecording = source.getFileName().toString().toLowerCase(Locale.ROOT).endsWith(".json")
                ? JsonPlaybackFiles.read(source) : RecordingFiles.read(source);
            if (command.has("start")) {
                JsonObject start = command.getAsJsonObject("start");
                sourceRecording = ReusableRecordingPlacement.moveStartTo(sourceRecording,
                    start.get("x").getAsDouble(), start.get("y").getAsDouble(), start.get("z").getAsDouble());
            }
            if (command.has("setup")) sourceRecording = sourceRecording.withSetup(command.getAsJsonObject("setup"));
            this.workerPreparedRecording = sourceRecording;
            configureAutomation(new AutomationSettings(source, output, "localhost:25565", true, true, true, false,
                compare, runId, profile, "", tolerance, maxUlps));
            this.worker.event(runId, "started", "Run accepted", 0);
        } catch (Exception failure) {
            this.worker.event(runId, "error", safeMessage(failure), 0);
        }
    }

    private void drivePendingPlayback() {
        if (this.pendingPlayback == null) {
            return;
        }
        MovementRecording loaded = this.pendingPlayback;
        boolean atStart = Double.compare(this.minecraft.playerX(), loaded.startX()) == 0
            && Double.compare(this.minecraft.playerY(), loaded.startY()) == 0
            && Double.compare(this.minecraft.playerZ(), loaded.startZ()) == 0;
        if (atStart) {
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
        this.pendingPoseTicks++;
        // Retry until the authoritative Gym pose is observable on the client.
        if (!atStart && this.pendingPoseTicks % 20 == 0
            && !this.minecraft.sendGymMessage(tasPosePayload(loaded))
            && !this.minecraft.sendServerCommand(tasPoseCommand(loaded))) {
            throw new IllegalStateException("Cannot retry the server-authoritative TAS start pose");
        }
        if (this.pendingPoseTicks > 400) {
            clearPendingPlayback();
            throw new IllegalStateException("Server did not apply the TAS start pose");
        }
    }

    private static String tasPoseCommand(MovementRecording recording) {
        return "lpcpose " + tasPoseArguments(recording);
    }

    private static String tasPosePayload(MovementRecording recording) {
        return "pose " + tasPoseArguments(recording);
    }

    private static String tasPoseArguments(MovementRecording recording) {
        return String.format(Locale.ROOT, "%s %s %s %s %s",
            Double.toString(recording.startX()), Double.toString(recording.startY()), Double.toString(recording.startZ()),
            Float.toString(recording.startYaw()), Float.toString(recording.startPitch()));
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
            if (this.deviationReported && this.playback != null
                && this.playbackIndex >= Math.min(this.playback.ticks().size(), this.firstDeviationIndex + 21)) {
                TickFrame expected = this.playback.ticks().get(this.playbackIndex - 1);
                this.followupDetail = String.format(Locale.ROOT,
                    "discrepancy after %d further ticks at tick %d (expected %.17g %.17g %.17g, actual %.17g %.17g %.17g, ULP=%s %s %s)",
                    Integer.valueOf(this.playbackIndex - this.firstDeviationIndex - 1), Integer.valueOf(this.playbackIndex - 1),
                    expected.x(), expected.y(), expected.z(),
                    this.minecraft.playerX(), this.minecraft.playerY(), this.minecraft.playerZ(),
                    safeUlp(expected.x(), this.minecraft.playerX()),
                    safeUlp(expected.y(), this.minecraft.playerY()),
                    safeUlp(expected.z(), this.minecraft.playerZ()));
                this.minecraft.sendGameMessage(this.followupDetail);
                sendFailureSnapshotSignal(this.playbackIndex - 1);
                finishPlayback();
            } else if (this.playback != null && this.playbackIndex >= this.playback.ticks().size()) {
                finishPlayback();
            }
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
        Long maxUlps = this.automation.maxUlps();
        if (PositionComparison.matches(expected.x(), actualX, tolerance, maxUlps)
            && PositionComparison.matches(expected.y(), actualY, tolerance, maxUlps)
            && PositionComparison.matches(expected.z(), actualZ, tolerance, maxUlps)) {
            return;
        }
        this.deviationReported = true;
        this.firstDeviationIndex = this.playbackIndex;
        int tick = this.playbackIndex;
        String details = String.format(Locale.ROOT,
            "first position deviation at tick %d (expected %.17g %.17g %.17g, actual %.17g %.17g %.17g, delta %.17g %.17g %.17g)",
            Integer.valueOf(tick), expected.x(), expected.y(), expected.z(), actualX, actualY, actualZ, deltaX, deltaY, deltaZ);
        if (maxUlps != null && finite(expected.x()) && finite(expected.y()) && finite(expected.z())
            && finite(actualX) && finite(actualY) && finite(actualZ)) {
            details += String.format(Locale.ROOT, " ULP=(%s, %s, %s)",
                PositionComparison.ulpDistance(expected.x(), actualX),
                PositionComparison.ulpDistance(expected.y(), actualY),
                PositionComparison.ulpDistance(expected.z(), actualZ));
        }
        this.minecraft.sendGameMessage(details);
        if (this.worker != null) this.worker.event(this.automation.runId(), "failure", details, tick);
        if (maxUlps != null && finite(expected.x()) && finite(expected.y()) && finite(expected.z())
            && finite(actualX) && finite(actualY) && finite(actualZ)) {
            this.minecraft.sendGameMessage("ULP distance XYZ: "
                + PositionComparison.ulpDistance(expected.x(), actualX) + ", "
                + PositionComparison.ulpDistance(expected.y(), actualY) + ", "
                + PositionComparison.ulpDistance(expected.z(), actualZ));
        }
        sendFailureSnapshotSignal(tick);
    }

    private void sendFailureSnapshotSignal(int tick) {
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
        if (!this.minecraft.isConnected()
            || (!this.minecraft.sendGymMessage(signal) && !this.minecraft.sendChatMessage(signal))) {
            this.minecraft.sendGameMessage("Unable to send the deviation snapshot signal to the parkour gym");
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

    private static String safeUlp(double expected, double actual) {
        return finite(expected) && finite(actual)
            ? PositionComparison.ulpDistance(expected, actual).toString() : "nonfinite";
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
        if (this.deviationReported && this.followupDetail == null && completed != null && this.playbackIndex > 0) {
            TickFrame expected = completed.ticks().get(Math.min(this.playbackIndex, completed.ticks().size()) - 1);
            this.followupDetail = String.format(Locale.ROOT,
                "discrepancy after %d further ticks at tick %d (expected %.17g %.17g %.17g, actual %.17g %.17g %.17g, ULP=%s %s %s)",
                Integer.valueOf(this.playbackIndex - this.firstDeviationIndex - 1), Integer.valueOf(this.playbackIndex - 1),
                expected.x(), expected.y(), expected.z(), this.minecraft.playerX(), this.minecraft.playerY(), this.minecraft.playerZ(),
                safeUlp(expected.x(), this.minecraft.playerX()), safeUlp(expected.y(), this.minecraft.playerY()),
                safeUlp(expected.z(), this.minecraft.playerZ()));
            this.minecraft.sendGameMessage(this.followupDetail);
            sendFailureSnapshotSignal(this.playbackIndex - 1);
        }
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
                completed.startVelocityX(), completed.startVelocityY(), completed.startVelocityZ(),
                this.capturedTicks, completed.setup()
            );
            try {
                RecordingFiles.write(output, result);
                this.minecraft.sendGameMessage("Playback finished: captured " + tickCount + " ticks to " + output.getFileName());
                if (automated && this.worker != null) {
                    String type = this.deviationReported ? "after_failure" : "success";
                    this.worker.event(this.automation.runId(), type,
                        this.deviationReported ? this.followupDetail : "All ticks completed", this.playbackIndex);
                }
            } catch (IOException exception) {
                this.minecraft.sendGameMessage("Failed to save playback result " + output + ": " + exception.getMessage());
                if (automated) {
                    this.automationFinished = true;
                    if (this.worker != null) this.worker.event(this.automation.runId(), "error", exception.getMessage(), this.playbackIndex);
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

    private static String safeMessage(Exception exception) {
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
                if (trimmed.startsWith("recording start reusable block ")) {
                    startReusableRecording(trimmed.substring("recording start reusable block ".length()), ReusableRecordingMetadata.Mode.BLOCK);
                    return true;
                }
                if (trimmed.startsWith("recording start reusable positions ")) {
                    startReusableRecording(trimmed.substring("recording start reusable positions ".length()), ReusableRecordingMetadata.Mode.POSITIONS);
                    return true;
                }
                startRecording(trimmed.substring("recording start ".length()));
                return true;
            }
            if (trimmed.startsWith("recording position ")) {
                String[] parts = trimmed.substring("recording position ".length()).split("\\s+");
                if (parts.length != 2) throw new IllegalArgumentException("Use .recording position <recording> <name>");
                addReusablePosition(parts[0], parts[1]);
                return true;
            }
            if (trimmed.startsWith("recording equipment ")) {
                String[] parts = trimmed.substring("recording equipment ".length()).split("\\s+");
                if (parts.length != 2) throw new IllegalArgumentException("Use .recording equipment <swiftSneak|soulSpeed|depthStrider> <level>");
                setRecordingEquipment(parts[0], Integer.parseInt(parts[1]));
                return true;
            }
            if (trimmed.startsWith("recording effect ")) {
                String[] parts = trimmed.substring("recording effect ".length()).split("\\s+");
                if (parts.length != 3) throw new IllegalArgumentException("Use .recording effect <id> <amplifier> <durationTicks>");
                addRecordingEffect(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
                return true;
            }
            if (trimmed.equals("recording effects clear")) {
                if (!this.recording) throw new IllegalStateException("Start recording first");
                this.recordingSetup.add("potionEffects", new JsonArray());
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
