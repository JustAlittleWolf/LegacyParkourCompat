package me.wolfii.legacyparkourcompat.recording;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("legacyparkourrecording")
public final class ForgeRecordingMod {
    public ForgeRecordingMod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    }

    private void setup(FMLCommonSetupEvent event) {
        RecordingController controller = RecordingController.get();
        controller.attach(ReflectivePlayback.INSTANCE);
        try {
            controller.configureAutomation(AutomationSettings.fromSystemProperties());
        } catch (IllegalArgumentException exception) {
            System.err.println("[Legacy Parkour Recording] Invalid automation setting: " + exception.getMessage());
        }
    }
}
