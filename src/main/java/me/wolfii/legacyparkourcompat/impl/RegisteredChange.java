package me.wolfii.legacyparkourcompat.impl;

import me.wolfii.legacyparkourcompat.api.MinecraftVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicKey;

import java.util.Objects;

record RegisteredChange(MechanicKey key, MinecraftVersion vanillaChangedIn, Object implementation, String emulates) {
    RegisteredChange {
        Objects.requireNonNull(key, "key");
        Objects.requireNonNull(vanillaChangedIn, "vanillaChangedIn");
        Objects.requireNonNull(implementation, "implementation");
        emulates = emulates == null ? "" : emulates;
    }
}
