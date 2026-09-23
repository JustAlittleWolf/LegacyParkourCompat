package me.wolfii.legacyparkourcompat.recording;

import net.fabricmc.api.ClientModInitializer;

public final class RecordingClientMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        RecordingController controller = RecordingController.get();
        controller.attach(ReflectivePlayback.INSTANCE);
        try {
            controller.configureAutomation(AutomationSettings.fromSystemProperties());
        } catch (IllegalArgumentException exception) {
            System.err.println("[Legacy Parkour Recording] Invalid automation setting: " + exception.getMessage());
        }
    }
}
