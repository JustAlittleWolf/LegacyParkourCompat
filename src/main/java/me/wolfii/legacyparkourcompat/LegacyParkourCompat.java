package me.wolfii.legacyparkourcompat;

import me.wolfii.legacyparkourcompat.api.MovementController;
import me.wolfii.legacyparkourcompat.impl.MovementControllerImpl;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeProvider;
import me.wolfii.legacyparkourcompat.network.ParkourNetworking;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LegacyParkourCompat implements ModInitializer {
    public static final String MOD_ID = "legacyparkourcompat";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        MovementControllerImpl controller = MovementControllerImpl.get();
        FabricLoader.getInstance()
                .getEntrypoints("legacyparkourcompat:movement-change", MovementChangeProvider.class)
                .forEach(provider -> provider.register(controller.registry()));
        controller.initialize();
        ParkourNetworking.register();
        LOGGER.info(
                "Legacy Parkour Compat ready. Native movement {}, selected {}.",
                MovementController.get().nativeVersion(),
                MovementController.get().selectedVersion()
        );
    }
}
