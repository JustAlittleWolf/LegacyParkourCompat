package me.wolfii.legacyparkourcompat.recording;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Immutable launch-time settings for a reference TAS run.
 *
 * <p>The settings are intentionally read from system properties instead of a
 * Minecraft config file.  A Gradle workflow can then give every isolated
 * client process a different recording, output file, and server address
 * without changing the user's normal game configuration.</p>
 */
public final class AutomationSettings {
    public static final String RECORDING_PROPERTY = "legacyparkour.tas.recording";
    public static final String OUTPUT_PROPERTY = "legacyparkour.tas.output";
    public static final String SERVER_PROPERTY = "legacyparkour.tas.server";
    public static final String AUTOPLAY_PROPERTY = "legacyparkour.tas.autoplay";
    public static final String AUTOJOIN_PROPERTY = "legacyparkour.tas.autojoin";
    public static final String MUTE_PROPERTY = "legacyparkour.tas.mute";
    public static final String EXIT_PROPERTY = "legacyparkour.tas.exit";
    public static final String COMPARE_PROPERTY = "legacyparkour.tas.compare";
    public static final String RUN_PROPERTY = "legacyparkour.tas.run";
    public static final String VERSION_PROPERTY = "legacyparkour.tas.version";
    public static final String DEVIATION_COMMAND_PROPERTY = "legacyparkour.tas.deviationCommand";
    public static final String TOLERANCE_PROPERTY = "legacyparkour.tas.tolerance";

    private final Path recording;
    private final Path output;
    private final String server;
    private final boolean autoplay;
    private final boolean autojoin;
    private final boolean mute;
    private final boolean exit;
    private final boolean compare;
    private final String runId;
    private final String version;
    private final String deviationCommand;
    private final double tolerance;

    public AutomationSettings(
        Path recording,
        Path output,
        String server,
        boolean autoplay,
        boolean autojoin,
        boolean mute,
        boolean exit
    ) {
        this(recording, output, server, autoplay, autojoin, mute, exit,
            booleanProperty(COMPARE_PROPERTY, false),
            System.getProperty(RUN_PROPERTY), System.getProperty(VERSION_PROPERTY),
            System.getProperty(DEVIATION_COMMAND_PROPERTY, "lpcfail"),
            doubleProperty(TOLERANCE_PROPERTY, 0.0D));
    }

    public AutomationSettings(
        Path recording,
        Path output,
        String server,
        boolean autoplay,
        boolean autojoin,
        boolean mute,
        boolean exit,
        boolean compare,
        String runId,
        String version,
        String deviationCommand,
        double tolerance
    ) {
        this.recording = recording;
        this.output = output;
        this.server = server == null || server.trim().isEmpty() ? "localhost:25565" : server.trim();
        this.autoplay = autoplay;
        this.autojoin = autojoin;
        this.mute = mute;
        this.exit = exit;
        this.compare = compare;
        this.runId = runId == null || runId.trim().isEmpty() ? "tas-run" : runId.trim();
        this.version = version == null || version.trim().isEmpty() ? "unknown" : version.trim();
        this.deviationCommand = deviationCommand == null ? "" : deviationCommand.trim();
        if (Double.isNaN(tolerance) || Double.isInfinite(tolerance) || tolerance < 0.0D) {
            throw new IllegalArgumentException("TAS tolerance must be a finite non-negative number");
        }
        this.tolerance = tolerance;
    }

    /** Reads the documented {@code -Dlegacyparkour.tas.*} launch properties. */
    public static AutomationSettings fromSystemProperties() {
        Path recording = pathProperty(RECORDING_PROPERTY);
        Path output = pathProperty(OUTPUT_PROPERTY);
        boolean autoplay = booleanProperty(AUTOPLAY_PROPERTY, recording != null);
        boolean autojoin = booleanProperty(AUTOJOIN_PROPERTY, recording != null);
        boolean mute = booleanProperty(MUTE_PROPERTY, true);
        boolean exit = booleanProperty(EXIT_PROPERTY, recording != null);
        boolean compare = booleanProperty(COMPARE_PROPERTY, false);
        String runId = System.getProperty(RUN_PROPERTY, "tas-run");
        String version = System.getProperty(VERSION_PROPERTY, "unknown");
        String deviationCommand = System.getProperty(DEVIATION_COMMAND_PROPERTY, "lpcfail");
        double tolerance = doubleProperty(TOLERANCE_PROPERTY, 0.0D);
        return new AutomationSettings(recording, output, System.getProperty(SERVER_PROPERTY), autoplay, autojoin, mute, exit, compare,
            runId, version, deviationCommand, tolerance);
    }

    public Path recording() {
        return this.recording;
    }

    public Path output() {
        return this.output;
    }

    public String server() {
        return this.server;
    }

    public boolean autoplay() {
        return this.autoplay;
    }

    public boolean autojoin() {
        return this.autojoin;
    }

    public boolean mute() {
        return this.mute;
    }

    public boolean exit() {
        return this.exit;
    }

    public boolean compare() {
        return this.compare;
    }

    public String runId() {
        return this.runId;
    }

    public String version() {
        return this.version;
    }

    public String deviationCommand() {
        return this.deviationCommand;
    }

    public double tolerance() {
        return this.tolerance;
    }

    public boolean enabled() {
        return this.autoplay && this.recording != null;
    }

    private static Path pathProperty(String name) {
        String value = System.getProperty(name);
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return Paths.get(value.trim()).toAbsolutePath().normalize();
        } catch (InvalidPathException exception) {
            throw new IllegalArgumentException("Invalid path in -D" + name + ": " + value, exception);
        }
    }

    private static boolean booleanProperty(String name, boolean defaultValue) {
        String value = System.getProperty(name);
        if (value == null || value.trim().isEmpty()) {
            return defaultValue;
        }
        if ("true".equalsIgnoreCase(value.trim()) || "1".equals(value.trim()) || "yes".equalsIgnoreCase(value.trim())) {
            return true;
        }
        if ("false".equalsIgnoreCase(value.trim()) || "0".equals(value.trim()) || "no".equalsIgnoreCase(value.trim())) {
            return false;
        }
        throw new IllegalArgumentException("Expected true/false for -D" + name + ", got '" + value + "'");
    }

    private static double doubleProperty(String name, double defaultValue) {
        String value = System.getProperty(name);
        if (value == null || value.trim().isEmpty()) {
            return defaultValue;
        }
        try {
            double parsed = Double.parseDouble(value.trim());
            if (Double.isNaN(parsed) || Double.isInfinite(parsed) || parsed < 0.0D) {
                throw new NumberFormatException("not finite and non-negative");
            }
            return parsed;
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Expected a finite non-negative number for -D" + name + ", got '" + value + "'", exception);
        }
    }
}
