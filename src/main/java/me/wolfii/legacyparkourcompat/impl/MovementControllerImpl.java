package me.wolfii.legacyparkourcompat.impl;

import me.wolfii.legacyparkourcompat.LegacyParkourCompat;
import me.wolfii.legacyparkourcompat.api.ActiveMovementProfile;
import me.wolfii.legacyparkourcompat.api.MinecraftVersion;
import me.wolfii.legacyparkourcompat.api.MovementController;
import me.wolfii.legacyparkourcompat.api.MovementVersionListener;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
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
import java.util.concurrent.atomic.AtomicInteger;

public final class MovementControllerImpl implements MovementController {
    private static final MovementControllerImpl INSTANCE = new MovementControllerImpl();

    private final MinecraftVersion nativeVersion = ParkourVersions.nativeVersion();
    private final MovementChangeRegistryImpl registry = new MovementChangeRegistryImpl(this::rebuild);
    private final CopyOnWriteArrayList<MovementVersionListener> listeners = new CopyOnWriteArrayList<>();
    private final ConcurrentHashMap<UUID, ParkourVersion> perPlayer = new ConcurrentHashMap<>();
    private final AtomicInteger epoch = new AtomicInteger();

    private volatile ParkourVersion selected;
    private volatile ActiveMovementProfile globalProfile;

    private MovementControllerImpl() {
        this.selected = ParkourVersions.vanilla();
        this.globalProfile = ActiveMovementProfile.vanilla(this.nativeVersion);
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
    public ParkourVersion selectedParkourVersion() {
        return this.selected;
    }

    @Override
    public MinecraftVersion selectedVersion() {
        return this.selected.isVanilla() ? this.nativeVersion : MinecraftVersion.parse(this.selected.id());
    }

    @Override
    public boolean isEnabled() {
        return !this.selected.isVanilla();
    }

    @Override
    public boolean isEnabled(@Nullable Entity entity) {
        return !this.parkourVersionFor(entity).isVanilla();
    }

    @Override
    public ParkourVersion parkourVersionFor(@Nullable Entity entity) {
        if (entity instanceof Player player) {
            ParkourVersion override = this.perPlayer.get(player.getUUID());
            if (override != null) {
                return override;
            }
        }
        return this.selected;
    }

    @Override
    public MinecraftVersion versionFor(@Nullable Entity entity) {
        ParkourVersion version = this.parkourVersionFor(entity);
        return version.isVanilla() ? this.nativeVersion : MinecraftVersion.parse(version.id());
    }

    @Override
    public void select(ParkourVersion version) {
        Objects.requireNonNull(version, "version");
        ParkourVersion canonical = version.isVanilla()
                ? ParkourVersions.vanilla()
                : ParkourVersions.of(MinecraftVersion.parse(version.id()));
        ParkourVersion previous = this.selected;
        if (previous.equals(canonical)) {
            return;
        }
        MinecraftVersion previousVersion = this.selectedVersion();
        this.selected = canonical;
        this.rebuild();
        this.invalidateCollisionCache();
        this.epoch.incrementAndGet();
        LegacyParkourCompat.LOGGER.info("Selected movement version {}", this.globalProfile.describe());
        for (MovementVersionListener listener : this.listeners) {
            listener.onMovementVersionChanged(previousVersion, this.selectedVersion());
        }
    }

    @Override
    public void selectFor(UUID playerId, @Nullable ParkourVersion version) {
        Objects.requireNonNull(playerId, "playerId");
        if (version == null) {
            this.perPlayer.remove(playerId);
        } else {
            ParkourVersion canonical = version.isVanilla()
                    ? ParkourVersions.vanilla()
                    : ParkourVersions.of(MinecraftVersion.parse(version.id()));
            this.perPlayer.put(playerId, canonical);
        }
        this.epoch.incrementAndGet();
    }

    @Override
    public List<ParkourVersion> selectableVersions() {
        return ParkourVersions.selectable();
    }

    @Override
    public ActiveMovementProfile profile() {
        return this.globalProfile;
    }

    @Override
    public ActiveMovementProfile profileFor(@Nullable Entity entity) {
        ParkourVersion version = this.parkourVersionFor(entity);
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
    public int epoch() {
        return this.epoch.get();
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
        this.epoch.incrementAndGet();
    }

    private ActiveMovementProfile profileOf(ParkourVersion version) {
        if (version.isVanilla()) {
            return ActiveMovementProfile.vanilla(this.nativeVersion);
        }
        Map<MechanicKey, Object> implementations = new HashMap<>();
        for (var entry : ChangeResolver.resolve(this.registry.snapshot(), version).entrySet()) {
            implementations.put(entry.getKey(), entry.getValue().implementation());
        }
        return new ActiveMovementProfile(MinecraftVersion.parse(version.id()), implementations);
    }

    /**
     * Rebuilds the empty-context collision cache used by some world queries.
     * Player movement collision uses {@code CollisionContext} and is looked up
     * live, so it updates without this. Outline/cosmetic shapes are not hooked.
     */
    private void invalidateCollisionCache() {
        try {
            for (Block block : BuiltInRegistries.BLOCK) {
                for (BlockState state : block.getStateDefinition().getPossibleStates()) {
                    state.initCache();
                }
            }
        } catch (Throwable t) {
            LegacyParkourCompat.LOGGER.debug("Could not rebuild block collision cache yet", t);
        }
    }
}
