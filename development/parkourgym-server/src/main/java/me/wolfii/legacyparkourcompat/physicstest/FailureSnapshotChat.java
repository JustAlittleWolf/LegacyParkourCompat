package me.wolfii.legacyparkourcompat.physicstest;

import io.papermc.paper.event.player.AbstractChatEvent;
import io.papermc.paper.event.player.AsyncChatEvent;
import io.papermc.paper.event.player.ChatEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import java.util.regex.Pattern;

/**
 * Captures the authoritative server state at the first failed movement tick.
 *
 * <p>The accuracy-test protocol deliberately uses a reserved, short chat
 * message instead of a command. The message is cancelled before it can be
 * broadcast and a single JSON object is written to the server log.</p>
 */
final class FailureSnapshotChat implements Listener {
    static final String PREFIX = "!lpcf";
    static final String LOG_MARKER = "[LPC_FAILURE_SNAPSHOT]";
    static final int SNAPSHOT_RADIUS = 1;

    private static final int MAX_MESSAGE_LENGTH = 64;
    private static final int MAX_TOKEN_LENGTH = 36;
    private static final int MAX_TICK = 9_999_999;
    private static final Pattern SAFE_TOKEN = Pattern.compile(
        "[A-Za-z0-9][A-Za-z0-9._:+/@-]{0," + (MAX_TOKEN_LENGTH - 1) + "}"
    );

    private final PhysicsTestPlugin plugin;

    FailureSnapshotChat(PhysicsTestPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onAsyncChat(AsyncChatEvent event) {
        handle(event);
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onChat(ChatEvent event) {
        handle(event);
    }

    private void handle(AbstractChatEvent event) {
        String message = PlainTextComponentSerializer.plainText().serialize(event.message());
        if (!message.startsWith(PREFIX)) {
            return;
        }

        // Reserve the prefix even for malformed messages, so a failed test
        // can never leak its protocol payload to other players.
        event.setCancelled(true);
        FailureRequest request = parse(message);
        if (request == null) {
            return;
        }

        Player player = event.getPlayer();
        if (event.isAsynchronous()) {
            // AsyncChatEvent cannot safely read world/entity state. Paper's
            // entity scheduler runs the capture on the owning Folia region.
            player.getScheduler().execute(
                plugin,
                () -> logSnapshot(player, request),
                () -> plugin.getLogger().warning(
                    LOG_MARKER + " capture dropped because player left before scheduling"
                ),
                1L
            );
        } else {
            logSnapshot(player, request);
        }
    }

    private void logSnapshot(Player player, FailureRequest request) {
        plugin.getLogger().info(LOG_MARKER + " " + snapshotJson(player, request));
    }

    void handlePluginMessage(Player player, String message) {
        FailureRequest request = parse(message);
        if (request == null) {
            return;
        }
        player.getScheduler().execute(
            plugin,
            () -> logSnapshot(player, request),
            () -> plugin.getLogger().warning(
                LOG_MARKER + " capture dropped because player left before scheduling"
            ),
            1L
        );
    }

    private static FailureRequest parse(String message) {
        if (message.length() > MAX_MESSAGE_LENGTH || !isAscii(message)) {
            return null;
        }
        String[] parts = message.split(" ", -1);
        if (parts.length != 4 || !PREFIX.equals(parts[0])) {
            return null;
        }
        String runId = parts[1];
        String version = parts[2];
        if (!validToken(runId) || !validToken(version) || parts[3].isEmpty()
            || parts[3].length() > 7) {
            return null;
        }
        int tick;
        try {
            tick = Integer.parseInt(parts[3]);
        } catch (NumberFormatException ignored) {
            return null;
        }
        if (tick < 0 || tick > MAX_TICK) {
            return null;
        }
        return new FailureRequest(runId, version, tick);
    }

    private static boolean validToken(String value) {
        return value.length() <= MAX_TOKEN_LENGTH && SAFE_TOKEN.matcher(value).matches();
    }

    private static boolean isAscii(String value) {
        for (int i = 0; i < value.length(); i++) {
            if (value.charAt(i) > 0x7f) {
                return false;
            }
        }
        return true;
    }

    private static String snapshotJson(Player player, FailureRequest request) {
        Location location = player.getLocation();
        Vector velocity = player.getVelocity();
        BoundingBox box = player.getBoundingBox();
        World world = player.getWorld();
        int playerX = floor(location.getX());
        int playerY = floor(location.getY());
        int playerZ = floor(location.getZ());
        int minX = playerX - SNAPSHOT_RADIUS;
        int maxX = playerX + SNAPSHOT_RADIUS;
        int minY = Math.max(world.getMinHeight(), playerY - 1);
        int maxY = Math.min(world.getMaxHeight() - 1, playerY + 2);
        int minZ = playerZ - SNAPSHOT_RADIUS;
        int maxZ = playerZ + SNAPSHOT_RADIUS;

        StringBuilder json = new StringBuilder(16_384);
        json.append('{');
        appendStringField(json, "event", "failure_snapshot", false);
        appendNumberField(json, "schemaVersion", 1, true);
        appendStringField(json, "runId", request.runId, true);
        appendStringField(json, "version", request.version, true);
        appendNumberField(json, "tick", request.tick, true);

        json.append(",\"player\":{");
        appendStringField(json, "uuid", player.getUniqueId().toString(), false);
        appendStringField(json, "name", player.getName(), true);
        json.append('}');

        json.append(",\"world\":{");
        appendStringField(json, "key", world.getKey().toString(), false);
        appendStringField(json, "name", world.getName(), true);
        json.append('}');

        json.append(",\"position\":{");
        appendDoubleField(json, "x", location.getX(), false);
        appendDoubleField(json, "y", location.getY(), true);
        appendDoubleField(json, "z", location.getZ(), true);
        appendDoubleField(json, "yaw", location.getYaw(), true);
        appendDoubleField(json, "pitch", location.getPitch(), true);
        json.append('}');

        json.append(",\"velocity\":{");
        appendDoubleField(json, "x", velocity.getX(), false);
        appendDoubleField(json, "y", velocity.getY(), true);
        appendDoubleField(json, "z", velocity.getZ(), true);
        json.append('}');
        json.append(",\"onGround\":").append(player.isOnGround());

        json.append(",\"boundingBox\":{");
        appendDoubleField(json, "minX", box.getMinX(), false);
        appendDoubleField(json, "minY", box.getMinY(), true);
        appendDoubleField(json, "minZ", box.getMinZ(), true);
        appendDoubleField(json, "maxX", box.getMaxX(), true);
        appendDoubleField(json, "maxY", box.getMaxY(), true);
        appendDoubleField(json, "maxZ", box.getMaxZ(), true);
        json.append('}');

        json.append(",\"blockRadius\":").append(SNAPSHOT_RADIUS);
        json.append(",\"blockHeight\":4");
        json.append(",\"blockBounds\":{");
        appendNumberField(json, "minX", minX, false);
        appendNumberField(json, "minY", minY, true);
        appendNumberField(json, "minZ", minZ, true);
        appendNumberField(json, "maxX", maxX, true);
        appendNumberField(json, "maxY", maxY, true);
        appendNumberField(json, "maxZ", maxZ, true);
        json.append('}');

        json.append(",\"blocks\":[");
        boolean firstBlock = true;
        int blockCount = 0;
        for (int y = minY; y <= maxY; y++) {
            for (int z = minZ; z <= maxZ; z++) {
                for (int x = minX; x <= maxX; x++) {
                    Block block = world.getBlockAt(x, y, z);
                    if (block.getType().isAir()) {
                        continue;
                    }
                    if (!firstBlock) {
                        json.append(',');
                    }
                    firstBlock = false;
                    appendBlock(json, block);
                    blockCount++;
                }
            }
        }
        json.append(']');
        json.append(",\"blockCount\":").append(blockCount);
        json.append('}');
        return json.toString();
    }

    private static int floor(double value) {
        return (int) Math.floor(value);
    }

    private static void appendBlock(StringBuilder json, Block block) {
        json.append('{');
        appendNumberField(json, "x", block.getX(), false);
        appendNumberField(json, "y", block.getY(), true);
        appendNumberField(json, "z", block.getZ(), true);
        appendStringField(json, "material", block.getType().getKey().toString(), true);
        appendStringField(json, "blockData", block.getBlockData().getAsString(), true);
        json.append('}');
    }

    private static void appendStringField(
        StringBuilder json,
        String name,
        String value,
        boolean commaBefore
    ) {
        if (commaBefore) {
            json.append(',');
        }
        appendJsonString(json, name);
        json.append(':');
        appendJsonString(json, value);
    }

    private static void appendNumberField(
        StringBuilder json,
        String name,
        int value,
        boolean commaBefore
    ) {
        if (commaBefore) {
            json.append(',');
        }
        appendJsonString(json, name);
        json.append(':').append(value);
    }

    private static void appendDoubleField(
        StringBuilder json,
        String name,
        double value,
        boolean commaBefore
    ) {
        if (commaBefore) {
            json.append(',');
        }
        appendJsonString(json, name);
        json.append(':');
        if (Double.isFinite(value)) {
            json.append(Double.toString(value));
        } else {
            json.append("null");
        }
    }

    private static void appendJsonString(StringBuilder json, String value) {
        json.append('"');
        for (int i = 0; i < value.length(); i++) {
            char character = value.charAt(i);
            switch (character) {
                case '"' -> json.append("\\\"");
                case '\\' -> json.append("\\\\");
                case '\b' -> json.append("\\b");
                case '\f' -> json.append("\\f");
                case '\n' -> json.append("\\n");
                case '\r' -> json.append("\\r");
                case '\t' -> json.append("\\t");
                default -> {
                    if (character < 0x20) {
                        json.append("\\u");
                        String hex = Integer.toHexString(character);
                        json.append("0000", 0, 4 - hex.length()).append(hex);
                    } else {
                        json.append(character);
                    }
                }
            }
        }
        json.append('"');
    }

    private record FailureRequest(String runId, String version, int tick) {
    }
}
