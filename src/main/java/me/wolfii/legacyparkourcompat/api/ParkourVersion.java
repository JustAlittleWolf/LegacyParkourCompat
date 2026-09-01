package me.wolfii.legacyparkourcompat.api;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.SharedConstants;
import net.minecraft.WorldVersion;
import org.jspecify.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Selectable parkour versions. Patch releases that share the same parkour
 * mechanics belong to one constant (for example {@link #V1_9} covers
 * {@code 1.9} through {@code 1.9.4}).
 *
 * <p>Order is chronological. A {@code @MovementChange(emulates = V1_8)} is the
 * 1.8 behaviour; Minecraft replaced it in {@link #next()}, which is {@link #V1_9}.
 *
 * <p>{@link #CURRENT} is the running game / disabled: mixins must not alter Minecraft.
 * Its {@link #id()} is that game's Minecraft version (for example {@code 26.2}), never
 * an alias such as {@code current}. Historical constants declare
 * {@link #isFullyImplemented()} / {@link #isPartiallyImplemented()};
 * they are partial until marked complete in the enum constructor.
 *
 * <p>Minor-version splits (Minecraft Wiki + MCPK, 1.8+):
 * <ul>
 *   <li>{@link #V1_10_1} – farmland collision 1.0 → 0.9375</li>
 *   <li>{@link #V1_11_1} – walls 1b high (MC-111645); restored in {@link #V1_11_2}</li>
 *   <li>{@link #V1_16_2} – sneak-edge fall while stepping down (MC-2404)</li>
 *   <li>{@link #V1_19_4} – supporting-block / {@code getOnPos} movement lookup</li>
 *   <li>{@link #V1_20_5} – {@code generic.step_height}</li>
 *   <li>{@link #V1_21_2} – slime sneak fall-damage (MC-54532)</li>
 *   <li>{@link #V1_21_4} – sprint-while-sneaking when {@code sneaking_speed} ≥ 0.8</li>
 *   <li>{@link #V1_21_5} – per-axis low-speed momentum cancel (MC-241951) and related travel fixes</li>
 *   <li>{@link #V1_21_11} – elytra through cave vines / later 1.21 movement</li>
 * </ul>
 * 26.2 is native {@link #CURRENT} (block bounce/restitution). 26.1.1 and 26.1.2
 * are grouped with {@link #V26_1} (no movement deltas). Pre-1.8 is out of scope.
 */
public enum ParkourVersion {
    V1_8("1.8", "1.8.1", "1.8.2", "1.8.3", "1.8.4", "1.8.5", "1.8.6", "1.8.7", "1.8.8", "1.8.9"),
    V1_9("1.9", "1.9.1", "1.9.2", "1.9.3", "1.9.4"),
    V1_10("1.10"),
    V1_10_1("1.10.1", "1.10.2"),
    V1_11("1.11"),
    V1_11_1("1.11.1"),
    V1_11_2("1.11.2"),
    V1_12("1.12", "1.12.1", "1.12.2"),
    V1_13("1.13", "1.13.1", "1.13.2"),
    V1_14("1.14", "1.14.1", "1.14.2", "1.14.3", "1.14.4"),
    V1_15("1.15", "1.15.1", "1.15.2"),
    V1_16("1.16", "1.16.1"),
    V1_16_2("1.16.2", "1.16.3", "1.16.4", "1.16.5"),
    V1_17("1.17", "1.17.1"),
    V1_18("1.18", "1.18.1", "1.18.2"),
    V1_19("1.19", "1.19.1", "1.19.2", "1.19.3"),
    V1_19_4("1.19.4"),
    V1_20("1.20", "1.20.1", "1.20.2", "1.20.3", "1.20.4"),
    V1_20_5("1.20.5", "1.20.6"),
    V1_21("1.21", "1.21.1"),
    V1_21_2("1.21.2", "1.21.3"),
    V1_21_4("1.21.4"),
    V1_21_5("1.21.5", "1.21.6", "1.21.7", "1.21.8", "1.21.9", "1.21.10"),
    V1_21_11("1.21.11"),
    V26_1("26.1", "26.1.1", "26.1.2"),
    CURRENT;

    private static final Map<String, ParkourVersion> BY_PATCH = Arrays.stream(values())
        .filter(version -> !version.isCurrent())
        .flatMap(version -> version.patches.stream().map(patch -> Map.entry(patch, version)))
        .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a));

    private final List<String> patches;
    private final boolean fullyImplemented;

    ParkourVersion(String... patches) {
        this(false, patches);
    }

    /**
     * @param fullyImplemented {@code true} when this version's movement is complete.
     *                         Historical versions default to partial.
     */
    ParkourVersion(boolean fullyImplemented, String... patches) {
        this.fullyImplemented = fullyImplemented;
        this.patches = List.of(patches);
    }

    /**
     * Release id of the running Minecraft game (for example {@code 26.2}).
     * This is also {@link #CURRENT}'s {@link #id()}.
     */
    public static String nativeGameVersion() {
        String name = worldVersionName();
        if (name != null) {
            return name;
        }
        String fabricId = fabricMinecraftVersion();
        if (fabricId != null) {
            return fabricId;
        }
        throw new IllegalStateException("Minecraft version is unavailable");
    }

    /**
     * Parkour version of the running game. {@link #CURRENT} when the native
     * release is not in a historical group (the latest).
     */
    public static ParkourVersion running() {
        return byPatch(nativeGameVersion());
    }

    /**
     * Historical versions older than the running game, plus {@link #CURRENT}.
     */
    public static List<ParkourVersion> selectable() {
        ParkourVersion nativeVersion = running();
        return Arrays.stream(values())
            .filter(version -> version.isCurrent() || version.olderThan(nativeVersion))
            .toList();
    }

    /**
     * Maps a Minecraft id or alias onto a selectable version.
     * {@code 1.9.2} becomes {@link #V1_9}; the running game version
     * (and legacy aliases such as {@code current}) is {@link #CURRENT}.
     */
    public static ParkourVersion of(String id) {
        String key = id.trim();
        if (key.isEmpty()) {
            throw new IllegalArgumentException("Minecraft version id is empty");
        }
        if (isCurrentAlias(key) || key.equals(nativeGameVersion())) {
            return CURRENT;
        }
        ParkourVersion exact = BY_PATCH.get(key);
        if (exact != null) {
            return exact;
        }
        return CURRENT;
    }

    public static @Nullable ParkourVersion tryOf(String id) {
        if (id == null || id.trim().isEmpty()) {
            return null;
        }
        return of(id);
    }

    private static ParkourVersion byPatch(String id) {
        ParkourVersion match = BY_PATCH.get(id.trim());
        return match == null ? CURRENT : match;
    }

    private static @Nullable String worldVersionName() {
        WorldVersion version;
        try {
            version = SharedConstants.getCurrentVersion();
        } catch (IllegalStateException exception) {
            return null;
        }
        String name = version.name();
        if (name == null || name.isBlank()) {
            throw new IllegalStateException("Minecraft version name is missing");
        }
        return name;
    }

    private static @Nullable String fabricMinecraftVersion() {
        return FabricLoader.getInstance()
            .getModContainer("minecraft")
            .map(container -> container.getMetadata().getVersion().getFriendlyString())
            .filter(id -> !id.isBlank())
            .orElse(null);
    }

    public static boolean isCurrentAlias(String id) {
        String key = id.trim().toLowerCase(Locale.ROOT);
        return key.equals("current")
            || key.equals("disabled")
            || key.equals("disable")
            || key.equals("off")
            || key.equals("latest")
            || key.equals("native")
            || key.equals("vanilla");
    }

    /**
     * Canonical Minecraft version id ({@code 1.8}, {@code 1.14}, {@code 26.2}, …).
     * {@link #CURRENT} uses {@link #nativeGameVersion()}.
     */
    public String id() {
        return this.isCurrent() ? nativeGameVersion() : this.patches.getFirst();
    }

    /**
     * Minecraft release ids that share this version's parkour mechanics.
     */
    public List<String> patches() {
        return this.patches;
    }

    public boolean isCurrent() {
        return this == CURRENT;
    }

    /**
     * {@code true} when this historical version's movement is complete.
     * {@link #CURRENT} is never fully-implemented in this sense; native
     * Minecraft already is.
     */
    public boolean isFullyImplemented() {
        return !this.isCurrent() && this.fullyImplemented;
    }

    /**
     * {@code true} when this historical version can be selected but does not
     * yet reproduce every intended movement mechanic. All historical constants
     * are partial until marked {@code true} in the enum constructor.
     */
    public boolean isPartiallyImplemented() {
        return !this.isCurrent() && !this.fullyImplemented;
    }

    /**
     * Next selectable version. Current's {@code next} is itself. A change that
     * {@code emulates} this version was replaced in Minecraft by {@code next()}.
     */
    public ParkourVersion next() {
        ParkourVersion[] values = values();
        int index = this.ordinal() + 1;
        return index < values.length ? values[index] : CURRENT;
    }

    public boolean olderThan(ParkourVersion other) {
        return this.ordinal() < other.ordinal();
    }

    public boolean olderThanOrEqual(ParkourVersion other) {
        return this.ordinal() <= other.ordinal();
    }

    public boolean newerThan(ParkourVersion other) {
        return this.ordinal() > other.ordinal();
    }

    public boolean newerThanOrEqual(ParkourVersion other) {
        return this.ordinal() >= other.ordinal();
    }

    @Override
    public String toString() {
        return this.id();
    }
}
