package me.wolfii.legacyparkourcompat.recording;

import net.minecraftforge.fml.common.Mod;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("legacyparkourrecording")
public final class ForgeRecordingMod {
    public ForgeRecordingMod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);
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

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            RecordingController.get().clientTick();
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.player != Minecraft.getInstance().player) {
            return;
        }
        RecordingController controller = RecordingController.get();
        if (event.phase == TickEvent.Phase.START) {
            controller.beforePlayerTick();
        } else {
            controller.afterPlayerTick();
        }
    }

    @SubscribeEvent
    public void onClientChat(ClientChatEvent event) {
        if (RecordingController.get().handleCommand(event.getMessage())) {
            event.setCanceled(true);
        }
    }
}
