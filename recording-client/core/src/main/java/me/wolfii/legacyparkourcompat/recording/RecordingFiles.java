package me.wolfii.legacyparkourcompat.recording;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Binary {@code .lprc} recordings. Layout is independent of Minecraft version:
 * magic, format version, start pose, then per-tick buttons/facing/position.
 */
public final class RecordingFiles {
    public static final String DIRECTORY_NAME = ".legacyparkourrecordings";
    public static final String EXTENSION = ".lprc";
    private static final int MAGIC = 0x4C505243; // 'LPRC'

    private RecordingFiles() {
    }

    public static Path directory(Path gameDir) {
        return gameDir.resolve(DIRECTORY_NAME);
    }

    public static Path file(Path gameDir, String name) {
        return directory(gameDir).resolve(sanitize(name) + EXTENSION);
    }

    public static String sanitize(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Recording name is missing");
        }
        String trimmed = name.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("Recording name is empty");
        }
        StringBuilder builder = new StringBuilder(trimmed.length());
        for (int index = 0; index < trimmed.length(); index++) {
            char character = trimmed.charAt(index);
            if (isSafeNameChar(character)) {
                builder.append(character);
            } else {
                builder.append('_');
            }
        }
        String sanitized = builder.toString();
        if (sanitized.equals(".") || sanitized.equals("..")) {
            throw new IllegalArgumentException("Recording name is invalid");
        }
        return sanitized.toLowerCase(Locale.ROOT);
    }

    private static boolean isSafeNameChar(char character) {
        return (character >= 'a' && character <= 'z')
            || (character >= 'A' && character <= 'Z')
            || (character >= '0' && character <= '9')
            || character == '.'
            || character == '_'
            || character == '-';
    }

    public static void write(Path path, MovementRecording recording) throws IOException {
        Path parent = path.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        OutputStream output = new BufferedOutputStream(Files.newOutputStream(path));
        try {
            write(output, recording);
        } finally {
            output.close();
        }
    }

    public static void write(OutputStream output, MovementRecording recording) throws IOException {
        DataOutputStream data = new DataOutputStream(output);
        data.writeInt(MAGIC);
        data.writeShort(MovementRecording.FORMAT_VERSION);
        data.writeShort(0);
        data.writeDouble(recording.startX());
        data.writeDouble(recording.startY());
        data.writeDouble(recording.startZ());
        data.writeFloat(recording.startYaw());
        data.writeFloat(recording.startPitch());
        List<TickFrame> ticks = recording.ticks();
        data.writeInt(ticks.size());
        for (int index = 0; index < ticks.size(); index++) {
            TickFrame tick = ticks.get(index);
            data.writeShort(tick.buttons() & 0xFFFF);
            data.writeFloat(tick.yaw());
            data.writeFloat(tick.pitch());
            data.writeDouble(tick.x());
            data.writeDouble(tick.y());
            data.writeDouble(tick.z());
        }
        data.flush();
    }

    public static MovementRecording read(Path path) throws IOException {
        InputStream input = new BufferedInputStream(Files.newInputStream(path));
        try {
            return read(input);
        } finally {
            input.close();
        }
    }

    public static MovementRecording read(InputStream input) throws IOException {
        DataInputStream data = new DataInputStream(input);
        int magic = data.readInt();
        if (magic != MAGIC) {
            throw new IOException("Not a Legacy Parkour recording (bad magic)");
        }
        int formatVersion = data.readUnsignedShort();
        if (formatVersion != MovementRecording.FORMAT_VERSION) {
            throw new IOException("Unsupported recording format " + formatVersion);
        }
        data.readUnsignedShort();
        double startX = data.readDouble();
        double startY = data.readDouble();
        double startZ = data.readDouble();
        float startYaw = data.readFloat();
        float startPitch = data.readFloat();
        int tickCount = data.readInt();
        if (tickCount < 0) {
            throw new IOException("Negative tick count");
        }
        List<TickFrame> ticks = new ArrayList<TickFrame>(tickCount);
        for (int index = 0; index < tickCount; index++) {
            int buttons = data.readUnsignedShort();
            float yaw = data.readFloat();
            float pitch = data.readFloat();
            double x = data.readDouble();
            double y = data.readDouble();
            double z = data.readDouble();
            ticks.add(new TickFrame(buttons, yaw, pitch, x, y, z));
        }
        return new MovementRecording(startX, startY, startZ, startYaw, startPitch, ticks);
    }
}
