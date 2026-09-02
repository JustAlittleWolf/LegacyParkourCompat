package me.wolfii.legacyparkourcompat.physicstest;

import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.event.connection.PlayerConnectionValidateLoginEvent;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import live.minehub.polarpaper.Polar;
import live.minehub.polarpaper.PolarPaper;
import live.minehub.polarpaper.core.config.Config;
import live.minehub.polarpaper.core.generator.PolarGenerator;
import live.minehub.polarpaper.core.source.FilePolarSource;
import live.minehub.polarpaper.core.world.PolarWorld;
import live.minehub.polarpaper.core.world.PolarWriter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.Nullable;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

public final class PhysicsTestPlugin extends JavaPlugin implements Listener {
    public static final String WORLD_NAME = "physics_test";
    private static final double SPAWN_X = 8.5;
    private static final int PLATFORM_Y = 64;
    private static final double SPAWN_Y = PLATFORM_Y + 1.0;
    private static final double SPAWN_Z = 8.5;
    private static final byte MIN_SECTION = 0;
    private static final byte MAX_SECTION = 15;

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
    public void onLogin(PlayerConnectionValidateLoginEvent event) {
        InetSocketAddress socketAddress = event.getConnection().getClientAddress();
        InetAddress address = socketAddress.getAddress();
        if (!address.isLoopbackAddress()) {
            event.kickMessage(Component.text("This physics testing server only accepts localhost connections."));
        }
    }

    @EventHandler
    public void onSpawn(PlayerJoinEvent event) {
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
            onPolarWorldReady(polar);
            return;
        }
        Path polarFile = polarFile();
        if (Files.exists(polarFile)) {
            Polar.createWorld(new FilePolarSource(polarFile), WORLD_NAME, physicsConfig())
                .whenComplete((world, error) -> handleLoadResult(world, error, "Failed to load Polar physics world"));
            return;
        }
        createEmptyPolarWorld()
            .whenComplete((world, error) -> handleLoadResult(world, error, "Failed to create Polar physics world"));
    }

    private void handleLoadResult(@Nullable World world, @Nullable Throwable error, String failureMessage) {
        if (error != null) {
            getLogger().log(Level.SEVERE, failureMessage, error);
            return;
        }
        runOnGlobalRegion(() -> onPolarWorldReady(world));
    }

    private CompletableFuture<World> createEmptyPolarWorld() {
        PolarWorld polarWorld = new PolarWorld(MIN_SECTION, MAX_SECTION);
        try {
            Files.createDirectories(polarFile().getParent());
            PolarWriter.write(Polar.getDefaultFolderSource(WORLD_NAME), polarWorld);
        } catch (Exception exception) {
            return CompletableFuture.failedFuture(exception);
        }
        return Polar.createWorld(polarWorld, WORLD_NAME, physicsConfig());
    }

    private void onPolarWorldReady(@Nullable World world) {
        if (world == null) {
            getLogger().severe("Polar physics world did not load");
            return;
        }
        Location spawn = spawnLocation(world);
        Bukkit.getRegionScheduler().run(this, spawn, task -> {
            applyWorldSettings(world);
            ensureSpawnPlatform(world);
            getLogger().info("Physics testing world '" + WORLD_NAME + "' is ready (height "
                + world.getMinHeight() + " to " + world.getMaxHeight() + ")");
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getWorld() != world) {
                    player.teleportAsync(spawnLocation(world));
                }
            }
        });
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
            runOnGlobalRegion(() -> {
                if (error != null) {
                    getLogger().log(Level.SEVERE, "Failed to save Polar world", error);
                    sender.sendMessage(Component.text("Failed to save Polar world. Check the server log.", NamedTextColor.RED));
                    return;
                }
                sender.sendMessage(Component.text("Saved physics world as Polar.", NamedTextColor.AQUA));
            });
        });
    }

    private Config physicsConfig() {
        return Config.getDefaultConfig(PolarPaper.getPlugin().getConfig()).toBuilder()
            .autoSaveIntervalTicks(-1)
            .announceAutosave(false)
            .time(6000L)
            .saveOnStop(false)
            .loadOnStartup(true)
            .spawn(new Location(null, SPAWN_X, SPAWN_Y, SPAWN_Z, 0.0f, 0.0f))
            .difficulty(Difficulty.PEACEFUL)
            .async(false)
            .worldType(WorldType.FLAT)
            .environment(World.Environment.NORMAL)
            .gamerule("spawn_mobs", false)
            .gamerule("advance_time", false)
            .gamerule("advance_weather", false)
            .gamerule("random_tick_speed", 0)
            .gamerule("fire_spread_radius_around_player", 0)
            .gamerule("blockPhysics", true)
            .gamerule("blockGravity", false)
            .gamerule("liquidPhysics", false)
            .gamerule("blockFade", false)
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
        setNamedGameRule(world, "advance_time", false);
        setNamedGameRule(world, "spawn_mobs", false);
        setNamedGameRule(world, "advance_weather", false);
        setNamedGameRule(world, "random_tick_speed", 0);
        setNamedGameRule(world, "fire_spread_radius_around_player", 0);
    }

    private void ensureSpawnPlatform(World world) {
        Block center = world.getBlockAt(8, PLATFORM_Y, 8);
        if (center.getType() == Material.STONE) {
            return;
        }
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                world.getBlockAt(x, PLATFORM_Y, z).setType(Material.STONE, false);
            }
        }
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
        NamespacedKey namespaced = NamespacedKey.fromString(name.contains(":") ? name : "minecraft:" + name);
        if (namespaced == null) {
            return null;
        }
        return Registry.GAME_RULE.get(namespaced);
    }

    private void runOnGlobalRegion(Runnable action) {
        Bukkit.getGlobalRegionScheduler().run(this, task -> action.run());
    }

    private static Location spawnLocation(World world) {
        return new Location(world, SPAWN_X, SPAWN_Y, SPAWN_Z, 0.0f, 0.0f);
    }

    private static Path polarFile() {
        return PolarPaper.getPlugin().getDataPath().resolve("worlds").resolve(WORLD_NAME + ".polar");
    }
}
