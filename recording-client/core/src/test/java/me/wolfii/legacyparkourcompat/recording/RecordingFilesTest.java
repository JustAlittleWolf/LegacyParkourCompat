package me.wolfii.legacyparkourcompat.recording;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RecordingFilesTest {
    @TempDir
    Path tempDir;

    @Test
    void roundTripPreservesBitsAndFloats() throws Exception {
        List<TickFrame> ticks = Arrays.asList(
            new TickFrame(TickButtons.pack(true, false, false, false, true, false, true, true, true), 12.5f, -4.25f, 1.0, 64.0, -8.5),
            new TickFrame(TickButtons.pack(false, true, true, false, false, true, false, false, true), -90f, 90f, 1.125, 64.5, -8.25)
        );
        MovementRecording recording = new MovementRecording(8.5, -60.0, 8.5, 45f, 10f, ticks);
        Path file = RecordingFiles.file(tempDir, "Jump Test");
        RecordingFiles.write(file, recording);
        MovementRecording loaded = RecordingFiles.read(file);

        assertEquals("jump_test.lprc", file.getFileName().toString());
        assertEquals(recording.startX(), loaded.startX());
        assertEquals(recording.startY(), loaded.startY());
        assertEquals(recording.startZ(), loaded.startZ());
        assertEquals(recording.startYaw(), loaded.startYaw());
        assertEquals(recording.startPitch(), loaded.startPitch());
        assertEquals(2, loaded.ticks().size());
        TickFrame first = loaded.ticks().get(0);
        assertTrue(first.forward());
        assertTrue(first.jump());
        assertTrue(first.sprint());
        assertTrue(first.useHold());
        assertTrue(first.useClick());
        assertEquals(12.5f, first.yaw());
        assertEquals(-4.25f, first.pitch());
        assertEquals(1.0, first.x());
        TickFrame second = loaded.ticks().get(1);
        assertTrue(second.back());
        assertTrue(second.left());
        assertTrue(second.sneak());
        assertFalse(second.useHold());
        assertTrue(second.useClick());
        assertEquals(-90f, second.yaw());
    }

    @Test
    void sanitizeRejectsEmptyNames() {
        assertThrows(IllegalArgumentException.class, () -> RecordingFiles.sanitize("  "));
    }
}
