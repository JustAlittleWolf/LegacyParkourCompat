package me.wolfii.legacyparkourcompat.impl;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicKey;

import java.util.Objects;

record RegisteredChange(MechanicKey key, ParkourVersion emulates, Object implementation) {
    RegisteredChange {
        Objects.requireNonNull(key, "key");
        Objects.requireNonNull(emulates, "emulates");
        Objects.requireNonNull(implementation, "implementation");
        if (emulates.isCurrent()) {
            throw new IllegalArgumentException("A movement change cannot emulate CURRENT");
        }
    }
}
