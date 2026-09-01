package me.wolfii.legacyparkourcompat.api;

import net.fabricmc.loader.api.FabricLoader;
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
 * 1.8 behaviour; vanilla replaced it in {@link #next()}, which is {@link #V1_9}.
 *
 * <p>{@link #VANILLA} is latest / disabled: mixins must not alter Minecraft.
 */
public enum ParkourVersion {
    V1_8("1.8", "1.8.1", "1.8.2", "1.8.3", "1.8.4", "1.8.5", "1.8.6", "1.8.7", "1.8.8", "1.8.9"),
    V1_9("1.9", "1.9.1", "1.9.2", "1.9.3", "1.9.4"),
    V1_10("1.10", "1.10.1", "1.10.2"),
    V1_11("1.11", "1.11.1", "1.11.2"),
    V1_12("1.12", "1.12.1", "1.12.2"),
    V1_13("1.13", "1.13.1", "1.13.2"),
    V1_14("1.14", "1.14.1", "1.14.2", "1.14.3", "1.14.4"),
    V1_15("1.15", "1.15.1", "1.15.2"),
    V1_16("1.16", "1.16.1", "1.16.2", "1.16.3", "1.16.4", "1.16.5"),
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
    V26_1("26.1"),
    VANILLA;

    private static final Map<String, ParkourVersion> BY_PATCH = Arrays.stream(values())
            .filter(version -> !version.isVanilla())
            .flatMap(version -> version.patches.stream().map(patch -> Map.entry(patch, version)))
            .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a));

    private final List<String> patches;

    ParkourVersion(String... patches) {
        this.patches = List.of(patches);
    }

    /**
     * Canonical id for the UI ({@code 1.9}, {@code 1.14}, {@code vanilla}, …).
     */
    public String id() {
        return this.isVanilla() ? "vanilla" : this.patches.getFirst();
    }

    /**
     * Minecraft release ids that share this version's parkour mechanics.
     */
    public List<String> patches() {
        return this.patches;
    }

    public boolean isVanilla() {
        return this == VANILLA;
    }

    /**
     * Next selectable version. Vanilla's {@code next} is itself. A change that
     * {@code emulates} this version was replaced in vanilla by {@code next()}.
     */
    public ParkourVersion next() {
        ParkourVersion[] values = values();
        int index = this.ordinal() + 1;
        return index < values.length ? values[index] : VANILLA;
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

    /**
     * Parkour version of the running game. {@link #VANILLA} when the native
     * release is not in a historical group (the current latest).
     */
    public static ParkourVersion running() {
        return FabricLoader.getInstance()
                .getModContainer("minecraft")
                .map(container -> byPatch(container.getMetadata().getVersion().getFriendlyString()))
                .orElse(VANILLA);
    }

    /**
     * Historical versions older than the running game, plus {@link #VANILLA}.
     */
    public static List<ParkourVersion> selectable() {
        ParkourVersion nativeVersion = running();
        return Arrays.stream(values())
                .filter(version -> version.isVanilla() || version.olderThan(nativeVersion))
                .toList();
    }

    /**
     * Maps a Minecraft id or alias onto a selectable version.
     * {@code 1.9.2} becomes {@link #V1_9}; {@code latest}/{@code disabled} is {@link #VANILLA}.
     */
    public static ParkourVersion of(String id) {
        String key = id.trim();
        if (key.isEmpty()) {
            throw new IllegalArgumentException("Minecraft version id is empty");
        }
        if (isVanillaAlias(key)) {
            return VANILLA;
        }
        ParkourVersion exact = BY_PATCH.get(key);
        if (exact != null) {
            return exact;
        }
        return VANILLA;
    }

    public static @Nullable ParkourVersion tryOf(String id) {
        try {
            return of(id);
        } catch (RuntimeException e) {
            return null;
        }
    }

    private static ParkourVersion byPatch(String id) {
        ParkourVersion match = BY_PATCH.get(id.trim());
        return match == null ? VANILLA : match;
    }

    public static boolean isVanillaAlias(String id) {
        String key = id.trim().toLowerCase(Locale.ROOT);
        return key.equals("vanilla")
                || key.equals("disabled")
                || key.equals("disable")
                || key.equals("off")
                || key.equals("latest")
                || key.equals("native")
                || key.equals("current");
    }

    @Override
    public String toString() {
        return this.id();
    }
}
