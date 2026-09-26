package me.wolfii.legacyparkourcompat.recording;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import com.google.gson.JsonObject;

import static org.junit.jupiter.api.Assertions.*;

class ReusableRecordingPlacementTest {
    @Test
    void trimsOnlyStationaryEdgesAndTranslatesEveryExpectedPosition() {
        MovementRecording source = new MovementRecording(8.5, 65.0, 8.5, 90.0f, 0.0f, Arrays.asList(
            frame(1, 8.5), frame(2, 8.5), frame(3, 9.0), frame(4, 9.0),
            frame(5, 10.0), frame(6, 10.0), frame(7, 10.0)
        ));
        MovementRecording trimmed = ReusableRecordingPlacement.trimStationaryEnds(source);
        assertEquals(3, trimmed.ticks().size());
        assertEquals(3, trimmed.ticks().get(0).buttons());
        assertEquals(5, trimmed.ticks().get(2).buttons());

        MovementRecording moved = ReusableRecordingPlacement.moveStartTo(trimmed, 5007.0, 64.5, 5011.0);
        assertEquals(5007.0, moved.startX());
        assertEquals(64.5, moved.startY());
        assertEquals(5007.5, moved.ticks().get(0).x());
        assertEquals(64.5, moved.ticks().get(0).y());
        assertEquals(5008.5, moved.ticks().get(2).x());
        assertEquals(3, moved.ticks().get(0).buttons());
        assertEquals(90.0f, moved.ticks().get(0).yaw());
    }

    @Test
    void storesOnlyExplicitNamedPositions() throws Exception {
        Path recording = Files.createTempDirectory("lpc-reusable-").resolve("jump.lprc");
        try {
            RecordingFiles.write(recording, new MovementRecording(8.5, 65.0, 8.5, 0, 0, Arrays.asList(frame(1, 9.0))));
            ReusableRecordingMetadata metadata = ReusableRecordingMetadata.positions(8.5, 65.0, 8.5)
                .withPosition("left_edge", 27.25, 65.0, -4.5);
            metadata.write(recording);
            ReusableRecordingMetadata read = ReusableRecordingMetadata.read(recording);
            assertEquals(ReusableRecordingMetadata.Mode.POSITIONS, read.mode());
            assertEquals(27.25, read.position("left_edge").x);
            assertThrows(IllegalArgumentException.class, () -> read.position("unlisted"));
        } finally {
            Files.deleteIfExists(recording);
            Files.deleteIfExists(recording.getParent());
        }
    }

    @Test
    void embedsPlayerSetupAndPreservesItWhenMovingTheRecording() throws Exception {
        Path recording = Files.createTempFile("lpc-setup-", ".lprc");
        try {
            JsonObject setup = RecordingSetup.defaults();
            setup.addProperty("gameMode", "creative");
            setup.addProperty("flying", true);
            setup.getAsJsonObject("equipment").addProperty("swiftSneak", 2);
            JsonObject effect = new JsonObject();
            effect.addProperty("id", "minecraft:speed");
            effect.addProperty("amplifier", 1);
            effect.addProperty("durationTicks", 100);
            setup.getAsJsonArray("potionEffects").add(effect);
            setup.add("reusable", ReusableRecordingMetadata.block().toJson());
            MovementRecording input = new MovementRecording(8.5, 65, 8.5, 0, 0, 0, 0, 0,
                Arrays.asList(frame(1, 9.0)), setup);
            RecordingFiles.write(recording, input);
            MovementRecording output = ReusableRecordingPlacement.moveStartTo(RecordingFiles.read(recording), 5008, 65, 5008);
            assertEquals("creative", output.setup().get("gameMode").getAsString());
            assertTrue(output.setup().get("flying").getAsBoolean());
            assertEquals(2, output.setup().getAsJsonObject("equipment").get("swiftSneak").getAsInt());
            assertEquals("minecraft:speed", output.setup().getAsJsonArray("potionEffects").get(0)
                .getAsJsonObject().get("id").getAsString());
            assertEquals(ReusableRecordingMetadata.Mode.BLOCK,
                ReusableRecordingMetadata.fromSetup(output.setup()).mode());
        } finally { Files.deleteIfExists(recording); }
    }

    @Test
    void readsOldInputOnlyRecordingsWithSafeDefaults() throws Exception {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(bytes);
        out.writeInt(0x4C505243);
        out.writeShort(1);
        out.writeShort(0);
        out.writeDouble(8.5); out.writeDouble(65); out.writeDouble(8.5);
        out.writeFloat(0); out.writeFloat(0);
        out.writeInt(1);
        out.writeShort(1); out.writeFloat(0); out.writeFloat(0);
        out.writeDouble(9); out.writeDouble(65); out.writeDouble(8.5);
        MovementRecording read = RecordingFiles.read(new ByteArrayInputStream(bytes.toByteArray()));
        assertEquals(1, read.ticks().size());
        assertEquals("survival", read.setup().get("gameMode").getAsString());
        assertFalse(read.setup().get("flying").getAsBoolean());
        assertTrue(read.setup().getAsJsonArray("potionEffects").isEmpty());
    }

    private static TickFrame frame(int buttons, double x) {
        return new TickFrame(buttons, 90.0f, 0.0f, x, 65.0, 8.5);
    }
}
