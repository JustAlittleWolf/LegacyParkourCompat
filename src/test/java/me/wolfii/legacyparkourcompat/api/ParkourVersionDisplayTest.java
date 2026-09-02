package me.wolfii.legacyparkourcompat.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParkourVersionDisplayTest {
    @Test
    void singlePatchIsTheVersionId() {
        assertEquals("1.10", ParkourVersion.V1_10.displayLabel());
        assertEquals("1.11.1", ParkourVersion.V1_11_1.displayLabel());
    }

    @Test
    void twoPatchesUseSlashAndLastComponent() {
        assertEquals("1.20.5/6", ParkourVersion.V1_20_5.displayLabel());
        assertEquals("1.10.1/2", ParkourVersion.V1_10_1.displayLabel());
        assertEquals("1.16/1", ParkourVersion.V1_16.displayLabel());
        assertEquals("1.17/1", ParkourVersion.V1_17.displayLabel());
        assertEquals("1.21.2/3", ParkourVersion.V1_21_2.displayLabel());
    }

    @Test
    void threeOrMorePatchesUseFirstToLastRange() {
        assertEquals("1.8-1.8.9", ParkourVersion.V1_8.displayLabel());
        assertEquals("1.9-1.9.4", ParkourVersion.V1_9.displayLabel());
        assertEquals("1.21.5-1.21.10", ParkourVersion.V1_21_5.displayLabel());
        assertEquals("26.1-26.1.2", ParkourVersion.V26_1.displayLabel());
    }
}
