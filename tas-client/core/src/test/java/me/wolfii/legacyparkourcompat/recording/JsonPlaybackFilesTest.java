package me.wolfii.legacyparkourcompat.recording;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonPlaybackFilesTest {
    @Test
    void readsKeysFacingAndStartingVelocity() throws Exception {
        String json = "{\"version\":1,\"start\":{\"pos\":[746.5,5,-1104.5],"
            + "\"vel\":[0,-0.0784000015258789,0],\"yaw\":74.27063,\"pitch\":40},"
            + "\"rows\":[{\"keys\":[\"W\",\"SPRINT\",\"JUMP\"],\"yaw\":-0.2273624},"
            + "{\"keys\":[\"W\",\"A\",\"SPRINT\"]},"
            + "{\"keys\":[\"SNEAK\"],\"pitch\":-26.242702}]}";
        MovementRecording recording = JsonPlaybackFiles.read(new StringReader(json));

        assertEquals(746.5, recording.startX());
        assertEquals(-0.0784000015258789, recording.startVelocityY());
        assertEquals(3, recording.ticks().size());
        assertTrue(recording.ticks().get(0).forward());
        assertTrue(recording.ticks().get(0).jump());
        assertTrue(recording.ticks().get(0).sprint());
        assertTrue(recording.ticks().get(1).left());
        assertEquals(recording.ticks().get(0).yaw(), recording.ticks().get(1).yaw());
        assertEquals(40f, recording.ticks().get(1).pitch());
        assertTrue(recording.ticks().get(2).sneak());
        assertEquals(-26.242702f, recording.ticks().get(2).pitch());
    }

    @Test
    void rejectsUnsupportedRowState() {
        String json = "{\"version\":1,\"start\":{\"pos\":[0,0,0],\"yaw\":0,\"pitch\":0},"
            + "\"rows\":[{\"keys\":[],\"teleport\":true}]}";
        assertThrows(IOException.class, () -> JsonPlaybackFiles.read(new StringReader(json)));
    }
}
