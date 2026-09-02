package me.wolfii.legacyparkourcompat.physicstest;

import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import live.minehub.polarpaper.Polar;
import live.minehub.polarpaper.PolarPaper;
import live.minehub.polarpaper.core.config.Config;
import live.minehub.polarpaper.core.generator.PolarGenerator;
import live.minehub.polarpaper.core.source.FilePolarSource;
import live.minehub.polarpaper.core.world.BlockSelector;
import live.minehub.polarpaper.core.world.PolarWorld;
import live.minehub.polarpaper.core.world.PolarWriter;
import live.minehub.polarpaper.nms.VersionUtil;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.CommandSender;
import org.jspecify.annotations.Nullable;

import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

public final class PhysicsTestPlugin extends JavaPlugin implements Listener {
    public static final String WORLD_NAME = "physics_test";
    private static final String VANILLA_WORLD_NAME = "world";
    private static final double SPAWN_X = 8.5;
    private static final double SPAWN_Y = -60.0;
    private static final double SPAWN_Z = 8.5;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            event.registrar().register(
                Commands.literal("save")
                    .requires(stack -> stack.getSender().hasPermission("physicstest.save") || stack.getSender().isOp())
                    .executes(ctx -> {
                        savePolarWorld(ctx.getSource().getSender());
                        return com.mojang.brigadier.Command.SINGLE_SUCCESS;
                    })
                    .build(),
                "Save the physics testing world in the Polar format"
            );
        });
        Bukkit.getGlobalRegionScheduler().run(this, task -> bootstrapWorld());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onLogin(PlayerLoginEvent event) {
        InetAddress address = event.getAddress();
        if (address == null || !address.isLoopbackAddress()) {
            event.disallow(
                PlayerLoginEvent.Result.KICK_OTHER,
                Component.text("This physics testing server only accepts localhost connections.")
            );
            return;
        }
        event.getPlayer().setOp(true);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.setOp(true);
        World polar = Bukkit.getWorld(WORLD_NAME);
        if (polar == null) {
            return;
        }
        if (player.getWorld() != polar) {
            player.teleportAsync(spawnLocation(polar));
        }
    }

    private void bootstrapWorld() {
        World polar = Bukkit.getWorld(WORLD_NAME);
        if (polar != null) {
            applyWorldSettings(polar);
            return;
        }
        Path polarFile = polarFile();
        if (Files.exists(polarFile)) {
            Polar.createWorld(new FilePolarSource(polarFile), WORLD_NAME, physicsConfig())
                .thenAccept(this::onPolarWorldReady)
                .exceptionally(error -> {
                    getLogger().log(Level.SEVERE, "Failed to load Polar physics world", error);
                    return null;
                });
            return;
        }
        World vanilla = Bukkit.getWorld(VANILLA_WORLD_NAME);
        if (vanilla == null) {
            getLogger().severe("Vanilla void world '" + VANILLA_WORLD_NAME + "' is missing");
            return;
        }
        applyWorldSettings(vanilla);
        PolarWorld.convert(
                vanilla,
                VersionUtil.getPolarFeaturesWorldAccess(),
                BlockSelector.square(0, 0, 4),
                true,
                true
            )
            .thenCompose(converted -> persistAndLoad(converted))
            .thenAccept(this::onPolarWorldReady)
            .exceptionally(error -> {
                getLogger().log(Level.SEVERE, "Failed to convert void world to Polar", error);
                return null;
            });
    }

    private CompletableFuture<@Nullable World> persistAndLoad(@Nullable PolarWorld converted) {
        if (converted == null) {
            return CompletableFuture.failedFuture(new IllegalStateException("Polar conversion returned no world"));
        }
        try {
            PolarWriter.write(Polar.getDefaultFolderSource(WORLD_NAME), converted);
        } catch (Exception exception) {
            return CompletableFuture.failedFuture(exception);
        }
        return Polar.createWorld(converted, WORLD_NAME, physicsConfig());
    }

    private void onPolarWorldReady(@Nullable World world) {
        if (world == null) {
            getLogger().severe("Polar physics world did not load");
            return;
        }
        applyWorldSettings(world);
        getLogger().info("Physics testing world '" + WORLD_NAME + "' is ready");
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getWorld() != world) {
                player.teleportAsync(spawnLocation(world));
            }
        }
    }

    private void savePolarWorld(CommandSender sender) {
        World world = Bukkit.getWorld(WORLD_NAME);
        if (world == null) {
            sender.sendMessage(Component.text("Physics world is not loaded.", NamedTextColor.RED));
            return;
        }
        if (PolarGenerator.fromWorld(world) == null) {
            sender.sendMessage(Component.text("Physics world is not a Polar world.", NamedTextColor.RED));
            return;
        }
        sender.sendMessage(Component.text("Saving Polar world...", NamedTextColor.GRAY));
        Polar.updateConfig(world, WORLD_NAME);
        Polar.saveWorld(world, Polar.getDefaultFolderSource(WORLD_NAME)).whenComplete((ignored, error) -> {
            if (error != null) {
                getLogger().log(Level.SEVERE, "Failed to save Polar world", error);
                sender.sendMessage(Component.text("Failed to save Polar world. Check the server log.", NamedTextColor.RED));
                return;
            }
            sender.sendMessage(Component.text("Saved physics world as Polar.", NamedTextColor.AQUA));
        });
    }

    private Config physicsConfig() {
        Config defaults = Config.getDefaultConfig(PolarPaper.getPlugin().getConfig());
        Map<String, Object> gamerules = new HashMap<>(defaults.gamerules());
        gamerules.put("spawn_mobs", false);
        gamerules.put("do_daylight_cycle", false);
        gamerules.put("random_tick_speed", 0);
        gamerules.put("blockPhysics", true);
        gamerules.put("blockGravity", false);
        gamerules.put("liquidPhysics", false);
        gamerules.put("blockFade", false);
        return defaults.toBuilder()
            .autoSaveIntervalTicks(-1)
            .announceAutosave(false)
            .time(6000L)
            .saveOnStop(false)
            .loadOnStartup(true)
            .spawn(new Location(null, SPAWN_X, SPAWN_Y, SPAWN_Z, 0.0f, 0.0f))
            .difficulty(Difficulty.PEACEFUL)
            .worldType(WorldType.FLAT)
            .environment(World.Environment.NORMAL)
            .gamerules(gamerules)
            .build();
    }

    private void applyWorldSettings(World world) {
        world.setDifficulty(Difficulty.PEACEFUL);
        world.setSpawnLocation(spawnLocation(world));
        world.setTime(6000L);
        world.setFullTime(6000L);
        world.setWeatherDuration(0);
        world.setStorm(false);
        world.setThundering(false);
        setNamedGameRule(world, "do_daylight_cycle", false);
        setNamedGameRule(world, "do_mob_spawning", false);
        setNamedGameRule(world, "do_weather_cycle", false);
        setNamedGameRule(world, "blockPhysics", true);
        setNamedGameRule(world, "blockGravity", false);
        setNamedGameRule(world, "liquidPhysics", false);
        setNamedGameRule(world, "blockFade", false);
    }

    private void setNamedGameRule(World world, String name, Object value) {
        GameRule<?> rule = resolveGameRule(name);
        if (rule == null) {
            getLogger().warning("Unknown gamerule '" + name + "'");
            return;
        }
        setUntypedGameRule(world, rule, value);
    }

    @SuppressWarnings("unchecked")
    private static <T> void setUntypedGameRule(World world, GameRule<T> rule, Object value) {
        world.setGameRule(rule, (T) value);
    }

    private static @Nullable GameRule<?> resolveGameRule(String name) {
        String keyName = name.contains(":") ? name.substring(name.indexOf(':') + 1) : name;
        GameRule<?> rule = lookupGameRule(name);
        if (rule != null) {
            return rule;
        }
        for (String namespace : new String[]{"minecraft", "polarpaper", "polar"}) {
            rule = lookupGameRule(namespace + ":" + keyName);
            if (rule != null) {
                return rule;
            }
        }
        return null;
    }

    private static @Nullable GameRule<?> lookupGameRule(String namespacedName) {
        NamespacedKey namespaced = NamespacedKey.fromString(namespacedName.contains(":") ? namespacedName : "minecraft:" + namespacedName);
        if (namespaced == null) {
            return null;
        }
        GameRule<?> fromRegistry = Registry.GAME_RULE.get(namespaced);
        if (fromRegistry != null) {
            return fromRegistry;
        }
        return Registry.GAME_RULE.get(Key.key(namespaced.getNamespace(), namespaced.getKey()));
    }

    private static Location spawnLocation(World world) {
        return new Location(world, SPAWN_X, SPAWN_Y, SPAWN_Z, 0.0f, 0.0f);
    }

    private static Path polarFile() {
        return PolarPaper.getPlugin().getDataPath().resolve("worlds").resolve(WORLD_NAME + ".polar");
    }
}
