package me.wolfii.legacyparkourcompat.recording;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/** Reads input rows from the Legacy Parkour/Combat TAS JSON format. */
public final class JsonPlaybackFiles {
    private JsonPlaybackFiles() {
    }

    public static MovementRecording read(Path path) throws IOException {
        try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            return read(reader);
        }
    }

    public static MovementRecording read(Reader reader) throws IOException {
        try {
            JsonObject root = object(new JsonParser().parse(reader), "recording");
            if (integer(root, "version", "recording") != 1) {
                throw new IOException("Unsupported Legacy Parkour/Combat JSON version");
            }
            JsonObject start = object(required(root, "start", "recording"), "start");
            JsonArray position = array(required(start, "pos", "start"), "start.pos", 3);
            JsonArray velocity = start.has("vel") ? array(start.get("vel"), "start.vel", 3) : null;
            double x = number(position.get(0), "start.pos[0]");
            double y = number(position.get(1), "start.pos[1]");
            double z = number(position.get(2), "start.pos[2]");
            float startYaw = angle(required(start, "yaw", "start"), "start.yaw");
            float startPitch = angle(required(start, "pitch", "start"), "start.pitch");
            double vx = velocity == null ? 0.0 : number(velocity.get(0), "start.vel[0]");
            double vy = velocity == null ? 0.0 : number(velocity.get(1), "start.vel[1]");
            double vz = velocity == null ? 0.0 : number(velocity.get(2), "start.vel[2]");
            JsonArray rows = array(required(root, "rows", "recording"), "rows", -1);
            List<TickFrame> ticks = new ArrayList<TickFrame>(rows.size());
            float yaw = startYaw;
            float pitch = startPitch;
            for (int index = 0; index < rows.size(); index++) {
                String location = "rows[" + index + "]";
                JsonObject row = object(rows.get(index), location);
                if (row.has("yaw") && !row.get("yaw").isJsonNull()) {
                    // JSON row yaw is a turn from the previous facing, not an absolute angle.
                    yaw += angle(row.get("yaw"), location + ".yaw");
                    if (!Float.isFinite(yaw)) throw new IOException(location + ".yaw exceeds the float range");
                }
                if (row.has("pitch") && !row.get("pitch").isJsonNull()) {
                    pitch = angle(row.get("pitch"), location + ".pitch");
                }
                if (enabled(row, "teleport", location)
                    || integerOrZero(row, "speedAmplifier", location) != 0
                    || integerOrZero(row, "jumpBoostAmplifier", location) != 0
                    || integerOrZero(row, "hotbarSlot", location) != 0) {
                    throw new IOException(location + " uses teleport, potion, or hotbar state that TAS playback cannot apply");
                }
                JsonArray keys = array(required(row, "keys", location), location + ".keys", -1);
                int buttons = 0;
                for (JsonElement keyElement : keys) {
                    String key = keyElement.getAsString();
                    switch (key) {
                        case "W": buttons |= TickButtons.FORWARD; break;
                        case "S": buttons |= TickButtons.BACK; break;
                        case "A": buttons |= TickButtons.LEFT; break;
                        case "D": buttons |= TickButtons.RIGHT; break;
                        case "JUMP": buttons |= TickButtons.JUMP; break;
                        case "SNEAK": buttons |= TickButtons.SNEAK; break;
                        case "SPRINT": buttons |= TickButtons.SPRINT; break;
                        default: throw new IOException(location + " has unsupported key " + key);
                    }
                }
                ticks.add(new TickFrame(buttons, yaw, pitch, Double.NaN, Double.NaN, Double.NaN));
            }
            return new MovementRecording(x, y, z, startYaw, startPitch, vx, vy, vz, ticks);
        } catch (IllegalStateException | NumberFormatException | UnsupportedOperationException exception) {
            throw new IOException("Invalid Legacy Parkour/Combat TAS JSON: " + exception.getMessage(), exception);
        }
    }

    private static JsonElement required(JsonObject object, String key, String location) throws IOException {
        JsonElement value = object.get(key);
        if (value == null || value.isJsonNull()) {
            throw new IOException("Missing " + location + "." + key);
        }
        return value;
    }

    private static JsonObject object(JsonElement element, String location) throws IOException {
        if (element == null || !element.isJsonObject()) throw new IOException(location + " must be an object");
        return element.getAsJsonObject();
    }

    private static JsonArray array(JsonElement element, String location, int length) throws IOException {
        if (element == null || !element.isJsonArray()) throw new IOException(location + " must be an array");
        JsonArray array = element.getAsJsonArray();
        if (length >= 0 && array.size() != length) throw new IOException(location + " must contain " + length + " values");
        return array;
    }

    private static double number(JsonElement element, String location) throws IOException {
        try {
            double value = element.getAsDouble();
            if (Double.isFinite(value)) return value;
        } catch (RuntimeException exception) {
            throw new IOException(location + " must be a finite number", exception);
        }
        throw new IOException(location + " must be a finite number");
    }

    private static float angle(JsonElement element, String location) throws IOException {
        float value = (float) number(element, location);
        if (!Float.isFinite(value)) throw new IOException(location + " is outside the float range");
        return value;
    }

    private static int integer(JsonObject object, String key, String location) throws IOException {
        JsonElement element = required(object, key, location);
        double value = number(element, location + "." + key);
        if (value != Math.rint(value) || value < Integer.MIN_VALUE || value > Integer.MAX_VALUE) {
            throw new IOException(location + "." + key + " must be an integer");
        }
        return (int) value;
    }

    private static int integerOrZero(JsonObject object, String key, String location) throws IOException {
        return object.has(key) && !object.get(key).isJsonNull() ? integer(object, key, location) : 0;
    }

    private static boolean enabled(JsonObject object, String key, String location) throws IOException {
        if (!object.has(key) || object.get(key).isJsonNull()) return false;
        JsonElement value = object.get(key);
        if (!value.isJsonPrimitive() || !value.getAsJsonPrimitive().isBoolean()) {
            throw new IOException(location + "." + key + " must be boolean");
        }
        return value.getAsBoolean();
    }
}
