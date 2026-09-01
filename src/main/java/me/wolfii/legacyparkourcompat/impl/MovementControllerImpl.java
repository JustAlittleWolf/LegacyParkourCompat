package me.wolfii.legacyparkourcompat.impl;

import me.wolfii.legacyparkourcompat.LegacyParkourCompat;
import me.wolfii.legacyparkourcompat.api.ActiveMovementProfile;
import me.wolfii.legacyparkourcompat.api.MinecraftVersion;
import me.wolfii.legacyparkourcompat.api.MovementController;
import me.wolfii.legacyparkourcompat.api.MovementVersionListener;
import me.wolfii.legacyparkourcompat.api.ParkourVersions;
import me.wolfii.legacyparkourcompat.mechanic.MechanicKey;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public final class MovementControllerImpl implements MovementController {
    private static final MovementControllerImpl INSTANCE = new MovementControllerImpl();

    private final MinecraftVersion nativeVersion = ParkourVersions.nativeVersion();
    private final MovementChangeRegistryImpl registry = new MovementChangeRegistryImpl(this::rebuild);
    private final CopyOnWriteArrayList<MovementVersionListener> listeners = new CopyOnWriteArrayList<>();
    private final ConcurrentHashMap<UUID, MinecraftVersion> perPlayer = new ConcurrentHashMap<>();

    private volatile MinecraftVersion selected;
    private volatile ActiveMovementProfile globalProfile;

    private MovementControllerImpl() {
        this.selected = this.nativeVersion;
        this.globalProfile = new ActiveMovementProfile(this.nativeVersion, Map.of());
    }

    public static MovementControllerImpl get() {
        return INSTANCE;
    }

    public void initialize() {
        this.rebuild();
        LegacyParkourCompat.LOGGER.info(
                "Movement controller ready (native {}, selected {})",
                this.nativeVersion,
                this.globalProfile.describe()
        );
    }

    @Override
    public MinecraftVersion nativeVersion() {
        return this.nativeVersion;
    }

    @Override
    public MinecraftVersion selectedVersion() {
        return this.selected;
    }

    @Override
    public MinecraftVersion versionFor(@Nullable Entity entity) {
        if (entity instanceof Player player) {
            MinecraftVersion override = this.perPlayer.get(player.getUUID());
            if (override != null) {
                return override;
            }
        }
        return this.selected;
    }

    @Override
    public void select(MinecraftVersion version) {
        Objects.requireNonNull(version, "version");
        MinecraftVersion previous = this.selected;
        if (previous.equals(version)) {
            return;
        }
        this.selected = version;
        this.rebuild();
        this.invalidateShapeCache();
        LegacyParkourCompat.LOGGER.info("Selected movement version {}", this.globalProfile.describe());
        for (MovementVersionListener listener : this.listeners) {
            listener.onMovementVersionChanged(previous, version);
        }
    }

    @Override
    public void selectFor(UUID playerId, @Nullable MinecraftVersion version) {
        Objects.requireNonNull(playerId, "playerId");
        if (version == null) {
            this.perPlayer.remove(playerId);
        } else {
            this.perPlayer.put(playerId, version);
        }
    }

    @Override
    public List<MinecraftVersion> suggestedVersions() {
        return ParkourVersions.SUGGESTED;
    }

    @Override
    public ActiveMovementProfile profile() {
        return this.globalProfile;
    }

    @Override
    public ActiveMovementProfile profileFor(@Nullable Entity entity) {
        MinecraftVersion version = this.versionFor(entity);
        if (version.equals(this.selected)) {
            return this.globalProfile;
        }
        return this.profileOf(version);
    }

    @Override
    public MovementChangeRegistry registry() {
        return this.registry;
    }

    @Override
    public void addListener(MovementVersionListener listener) {
        this.listeners.add(Objects.requireNonNull(listener, "listener"));
    }

    @Override
    public void removeListener(MovementVersionListener listener) {
        this.listeners.remove(listener);
    }

    private void rebuild() {
        this.globalProfile = this.profileOf(this.selected);
    }

    private ActiveMovementProfile profileOf(MinecraftVersion version) {
        Map<MechanicKey, Object> implementations = new HashMap<>();
        for (var entry : ChangeResolver.resolve(this.registry.snapshot(), version).entrySet()) {
            implementations.put(entry.getKey(), entry.getValue().implementation());
        }
        return new ActiveMovementProfile(version, implementations);
    }

    private void invalidateShapeCache() {
        try {
            for (Block block : BuiltInRegistries.BLOCK) {
                for (BlockState state : block.getStateDefinition().getPossibleStates()) {
                    state.initCache();
                }
            }
        } catch (Throwable t) {
            LegacyParkourCompat.LOGGER.debug("Could not rebuild block shape cache yet", t);
        }
    }
}
