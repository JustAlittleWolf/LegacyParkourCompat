package me.wolfii.legacyparkourcompat.recording;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/** Placement choices stored in the LPRC setup, alongside game mode and effects. */
public final class ReusableRecordingMetadata {
    public enum Mode { BLOCK, POSITIONS }

    private final Mode mode;
    private final Map<String, Position> positions;

    private ReusableRecordingMetadata(Mode mode, Map<String, Position> positions) {
        this.mode = mode;
        this.positions = Collections.unmodifiableMap(new LinkedHashMap<String, Position>(positions));
    }

    public static ReusableRecordingMetadata block() {
        return new ReusableRecordingMetadata(Mode.BLOCK, Collections.<String, Position>emptyMap());
    }

    public static ReusableRecordingMetadata positions(double x, double y, double z) {
        Map<String, Position> anchors = new LinkedHashMap<String, Position>();
        anchors.put("origin", new Position(x, y, z));
        return new ReusableRecordingMetadata(Mode.POSITIONS, anchors);
    }

    public Mode mode() { return mode; }

    public Map<String, Position> positions() { return positions; }

    public Position position(String name) {
        Position position = positions.get(name);
        if (position == null) throw new IllegalArgumentException("Unknown reusable position '" + name + "'");
        return position;
    }

    public ReusableRecordingMetadata withPosition(String name, double x, double y, double z) {
        if (mode != Mode.POSITIONS) throw new IllegalArgumentException("This recording uses block placement");
        if (name == null || !name.matches("[A-Za-z0-9_-]{1,32}")) {
            throw new IllegalArgumentException("Position name must use 1-32 letters, digits, _ or -");
        }
        if (!Double.isFinite(x) || !Double.isFinite(y) || !Double.isFinite(z)) {
            throw new IllegalArgumentException("Reusable position must be finite");
        }
        Map<String, Position> updated = new LinkedHashMap<String, Position>(positions);
        updated.put(name, new Position(x, y, z));
        return new ReusableRecordingMetadata(mode, updated);
    }

    public JsonObject toJson() {
        JsonObject root = new JsonObject();
        root.addProperty("mode", mode == Mode.BLOCK ? "block" : "positions");
        JsonObject anchors = new JsonObject();
        for (Map.Entry<String, Position> entry : positions.entrySet()) {
            JsonObject value = new JsonObject();
            value.addProperty("x", entry.getValue().x);
            value.addProperty("y", entry.getValue().y);
            value.addProperty("z", entry.getValue().z);
            anchors.add(entry.getKey(), value);
        }
        root.add("positions", anchors);
        return root;
    }

    public static ReusableRecordingMetadata fromSetup(JsonObject setup) {
        if (!setup.has("reusable")) throw new IllegalArgumentException("Recording is not tagged reusable");
        try {
            JsonObject root = setup.getAsJsonObject("reusable");
            String modeName = root.get("mode").getAsString();
            if ("block".equals(modeName)) return block();
            if (!"positions".equals(modeName)) throw new IllegalArgumentException("Unknown reusable mode");
            ReusableRecordingMetadata metadata = new ReusableRecordingMetadata(Mode.POSITIONS, Collections.<String, Position>emptyMap());
            JsonObject anchors = root.getAsJsonObject("positions");
            for (Map.Entry<String, JsonElement> entry : anchors.entrySet()) {
                JsonObject value = entry.getValue().getAsJsonObject();
                metadata = metadata.withPosition(entry.getKey(), value.get("x").getAsDouble(),
                    value.get("y").getAsDouble(), value.get("z").getAsDouble());
            }
            if (!metadata.positions.containsKey("origin")) throw new IllegalArgumentException("Missing origin position");
            return metadata;
        } catch (RuntimeException invalid) { throw new IllegalArgumentException("Invalid reusable metadata: " + invalid.getMessage(), invalid); }
    }

    public static ReusableRecordingMetadata read(Path recording) throws IOException {
        return fromSetup(RecordingFiles.read(recording).setup());
    }

    public void write(Path recording) throws IOException {
        MovementRecording source = RecordingFiles.read(recording);
        JsonObject setup = source.setup();
        setup.add("reusable", toJson());
        RecordingFiles.write(recording, source.withSetup(setup));
    }

    public static final class Position {
        public final double x, y, z;
        public Position(double x, double y, double z) { this.x = x; this.y = y; this.z = z; }
    }
}
