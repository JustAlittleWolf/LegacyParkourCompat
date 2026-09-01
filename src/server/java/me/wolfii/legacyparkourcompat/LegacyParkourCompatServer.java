package me.wolfii.legacyparkourcompat;

import me.wolfii.legacyparkourcompat.config.ServerMovementConfig;
import net.fabricmc.api.DedicatedServerModInitializer;

public class LegacyParkourCompatServer implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer() {
        ServerMovementConfig.load();
    }
}
