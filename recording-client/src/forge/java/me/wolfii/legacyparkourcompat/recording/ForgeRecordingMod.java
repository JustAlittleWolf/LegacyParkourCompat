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
        RecordingController.get().attach(ReflectivePlayback.INSTANCE);
    }
}
