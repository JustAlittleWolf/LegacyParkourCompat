package me.wolfii.legacyparkourcompat.recording;

import net.fabricmc.api.ClientModInitializer;

public final class RecordingClientMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        RecordingController.get().attach(ReflectivePlayback.INSTANCE);
    }
}
