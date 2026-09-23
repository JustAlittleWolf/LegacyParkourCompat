package me.wolfii.legacyparkourcompat.recording;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(
    modid = "legacyparkourrecording",
    name = "Legacy Parkour Recording",
    version = "1.0.0",
    clientSideOnly = true,
    acceptedMinecraftVersions = "*"
)
public final class ForgeRecordingMod {
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        RecordingController controller = RecordingController.get();
        controller.attach(ReflectivePlayback.INSTANCE);
        try {
            controller.configureAutomation(AutomationSettings.fromSystemProperties());
        } catch (IllegalArgumentException exception) {
            System.err.println("[Legacy Parkour Recording] Invalid automation setting: " + exception.getMessage());
        }
    }
}
