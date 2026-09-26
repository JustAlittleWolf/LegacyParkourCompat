package me.wolfii.legacyparkourcompat.physicstest;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.wolfii.legacyparkourcompat.recording.MovementRecording;
import me.wolfii.legacyparkourcompat.recording.RecordingSetup;
import me.wolfii.legacyparkourcompat.recording.ReusableRecordingMetadata;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/** Validates a run and installs its isolated terrain and player setup before dispatch. */
final class RunPreparation {
    private static final List<Material> BLOCKS = Arrays.stream(Material.values())
        .filter(Material::isBlock).filter(material -> !material.isAir() && !material.isLegacy())
        .sorted(Comparator.comparing(Material::name)).toList();

    static JsonArray blockCatalogue() {
        JsonArray catalogue = new JsonArray();
        for (int index = 0; index < BLOCKS.size(); index++) {
            JsonObject entry = new JsonObject();
            entry.addProperty("block", BLOCKS.get(index).getKey().toString());
            entry.addProperty("x", 5008 + (index % 32) * 16);
            entry.addProperty("z", 5008 + (index / 32) * 16);
            catalogue.add(entry);
        }
        return catalogue;
    }

    final JsonObject setup;
    final Material block;
    final double x, y, z;
    final double offsetX, offsetY, offsetZ;

    private RunPreparation(JsonObject setup, Material block, double x, double y, double z,
                           double offsetX, double offsetY, double offsetZ) {
        this.setup = setup; this.block = block; this.x = x; this.y = y; this.z = z;
        this.offsetX = offsetX; this.offsetY = offsetY; this.offsetZ = offsetZ;
    }

    static RunPreparation parse(MovementRecording recording, JsonObject request, String version) {
        JsonObject setup = recording.setup();
        if (request.has("gameMode") || request.has("flying"))
            throw new IllegalArgumentException("Game mode and flying are fixed by the recording");
        if (request.has("equipment")) setup.add("equipment", request.getAsJsonObject("equipment").deepCopy());
        if (request.has("potionEffects")) setup.add("potionEffects", request.getAsJsonArray("potionEffects").deepCopy());
        setup = RecordingSetup.normalize(setup);
        validateEquipment(setup.getAsJsonObject("equipment"), version);
        validateEffects(setup.getAsJsonArray("potionEffects"), version);
        if (!request.has("placement")) return new RunPreparation(setup, null,
            recording.startX(), recording.startY(), recording.startZ(), 0, 0, 0);
        JsonObject placement = request.getAsJsonObject("placement");
        ReusableRecordingMetadata reusable = ReusableRecordingMetadata.fromSetup(setup);
        String type = requiredString(placement, "type");
        double dx = number(placement, "offsetX"), dy = number(placement, "offsetY"), dz = number(placement, "offsetZ");
        if (Math.abs(dx) > 6.5 || Math.abs(dz) > 6.5 || Math.abs(dy) > 4.0)
            throw new IllegalArgumentException("Placement offset exceeds the pad bounds");
        if ("block".equals(type) && reusable.mode() == ReusableRecordingMetadata.Mode.BLOCK) {
            String blockId = requiredString(placement, "block");
            if (!blockId.startsWith("minecraft:")) throw new IllegalArgumentException("Block must use a minecraft: identifier");
            Material material = Material.matchMaterial(blockId.substring("minecraft:".length()));
            if (material == null || !BLOCKS.contains(material)) throw new IllegalArgumentException("Unknown block type");
            return new RunPreparation(setup, material, 0, 0, 0, dx, dy, dz);
        }
        if ("position".equals(type) && reusable.mode() == ReusableRecordingMetadata.Mode.POSITIONS) {
            ReusableRecordingMetadata.Position position = reusable.position(requiredString(placement, "name"));
            return new RunPreparation(setup, null, position.x + dx, position.y + dy, position.z + dz, dx, dy, dz);
        }
        throw new IllegalArgumentException("Placement type does not match the recording's reusable mode");
    }

    CompletableFuture<JsonObject> prepare(PhysicsTestPlugin plugin, Player player) {
        CompletableFuture<JsonObject> result = new CompletableFuture<>();
        World world = plugin.privateWorld(player.getUniqueId());
        if (world == null) {
            result.completeExceptionally(new IllegalStateException("Worker is still joining its private world"));
            return result;
        }
        if (block == null) {
            preparePlayer(plugin, player, x, y, z, result);
            return result;
        }
        int index = BLOCKS.indexOf(block);
        int originX = 5008 + (index % 32) * 16;
        int originZ = 5008 + (index / 32) * 16;
        world.getChunkAtAsync(originX >> 4, originZ >> 4).whenComplete((chunk, failure) -> {
            if (failure != null) { result.completeExceptionally(failure); return; }
            Bukkit.getRegionScheduler().run(plugin, new org.bukkit.Location(world, originX + 7, 65, originZ + 7), task -> {
                try {
                    for (int x = 0; x < 14; x++) for (int z = 0; z < 14; z++) {
                        world.getBlockAt(originX + x, 63, originZ + z).setType(Material.STONE, false);
                        world.getBlockAt(originX + x, 64, originZ + z).setType(block, false);
                        for (int y = 65; y <= 68; y++) world.getBlockAt(originX + x, y, originZ + z).setType(Material.AIR, false);
                    }
                    Block center = world.getBlockAt(originX + 7, 64, originZ + 7);
                    double surfaceY = center.getBoundingBox().getMaxY();
                    preparePlayer(plugin, player, originX + 7.5 + offsetX, surfaceY + offsetY,
                        originZ + 7.5 + offsetZ, result);
                } catch (RuntimeException invalid) { result.completeExceptionally(invalid); }
            });
        });
        return result;
    }

    private void preparePlayer(PhysicsTestPlugin plugin, Player player, double x, double y, double z,
                               CompletableFuture<JsonObject> result) {
        player.getScheduler().execute(plugin, () -> {
            try {
                for (PotionEffect effect : player.getActivePotionEffects()) player.removePotionEffect(effect.getType());
                player.getInventory().clear();
                JsonObject equipment = setup.getAsJsonObject("equipment");
                int swift = level(equipment, "swiftSneak", 3);
                int soul = level(equipment, "soulSpeed", 3);
                int depth = level(equipment, "depthStrider", 3);
                if (swift > 0) {
                    ItemStack leggings = new ItemStack(Material.DIAMOND_LEGGINGS);
                    leggings.addUnsafeEnchantment(enchantment("swift_sneak"), swift);
                    player.getInventory().setLeggings(leggings);
                }
                if (soul > 0 || depth > 0) {
                    ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);
                    if (soul > 0) boots.addUnsafeEnchantment(enchantment("soul_speed"), soul);
                    if (depth > 0) boots.addUnsafeEnchantment(enchantment("depth_strider"), depth);
                    player.getInventory().setBoots(boots);
                }
                for (JsonElement entry : setup.getAsJsonArray("potionEffects")) {
                    JsonObject effect = entry.getAsJsonObject();
                    NamespacedKey key = NamespacedKey.fromString(requiredString(effect, "id"));
                    PotionEffectType type = Registry.MOB_EFFECT.get(key);
                    player.addPotionEffect(new PotionEffect(type,
                        effect.has("durationTicks") ? effect.get("durationTicks").getAsInt() : 1200000,
                        effect.has("amplifier") ? effect.get("amplifier").getAsInt() : 0, true, false));
                }
                boolean creative = "creative".equals(setup.get("gameMode").getAsString());
                player.setGameMode(creative ? GameMode.CREATIVE : GameMode.SURVIVAL);
                player.setAllowFlight(creative);
                player.setFlying(creative && setup.get("flying").getAsBoolean());
                player.setVelocity(new org.bukkit.util.Vector(0, 0, 0));
                player.setFallDistance(0);
                JsonObject start = new JsonObject();
                start.addProperty("x", x); start.addProperty("y", y); start.addProperty("z", z);
                result.complete(start);
            } catch (RuntimeException invalid) { result.completeExceptionally(invalid); }
        }, () -> result.completeExceptionally(new IllegalStateException("Worker left the Gym")), 1L);
    }

    private static void validateEquipment(JsonObject equipment, String version) {
        for (String key : List.of("swiftSneak", "soulSpeed", "depthStrider")) level(equipment, key, 3);
        if (!"current".equals(version)) {
            int versionNumber = versionNumber(version);
            if (level(equipment, "swiftSneak", 3) > 0 && versionNumber < 11900)
                throw new IllegalArgumentException("Swift Sneak requires Minecraft 1.19+");
            if (level(equipment, "soulSpeed", 3) > 0 && versionNumber < 11600)
                throw new IllegalArgumentException("Soul Speed requires Minecraft 1.16+");
            if (level(equipment, "depthStrider", 3) > 0 && versionNumber < 10800)
                throw new IllegalArgumentException("Depth Strider requires Minecraft 1.8+");
        }
    }

    private static void validateEffects(JsonArray effects, String version) {
        for (JsonElement entry : effects) {
            JsonObject effect = entry.getAsJsonObject();
            NamespacedKey key = NamespacedKey.fromString(requiredString(effect, "id"));
            if (key == null || Registry.MOB_EFFECT.get(key) == null)
                throw new IllegalArgumentException("Unknown potion effect " + effect.get("id"));
            int amplifier = effect.has("amplifier") ? effect.get("amplifier").getAsInt() : 0;
            int duration = effect.has("durationTicks") ? effect.get("durationTicks").getAsInt() : 1200000;
            if (amplifier < 0 || amplifier > 255 || duration <= 0)
                throw new IllegalArgumentException("Invalid potion amplifier or durationTicks");
            if (!"current".equals(version) && versionNumber(version) < minimumEffectVersion(key.getKey()))
                throw new IllegalArgumentException("Potion effect is unavailable in " + version + ": " + key);
        }
    }

    private static int minimumEffectVersion(String id) {
        return switch (id) {
            case "glowing", "levitation", "luck", "unluck" -> 10900;
            case "slow_falling", "conduit_power", "dolphins_grace" -> 11300;
            case "bad_omen", "hero_of_the_village" -> 11400;
            case "darkness" -> 11900;
            case "trial_omen", "raid_omen", "wind_charged", "weaving", "oozing", "infested" -> 12100;
            case "breath_of_the_nautilus" -> Integer.MAX_VALUE;
            default -> 10800;
        };
    }

    private static Enchantment enchantment(String id) {
        Enchantment value = Registry.ENCHANTMENT.get(NamespacedKey.minecraft(id));
        if (value == null) throw new IllegalStateException("Gym does not support enchantment " + id);
        return value;
    }

    private static int level(JsonObject object, String key, int maximum) {
        int value = object.has(key) ? object.get(key).getAsInt() : 0;
        if (value < 0 || value > maximum) throw new IllegalArgumentException(key + " level must be 0.." + maximum);
        return value;
    }

    private static int versionNumber(String version) {
        String[] parts = version.replaceFirst("-(forge|fabric)$", "").split("\\.");
        if (parts.length < 2 || !"1".equals(parts[0])) throw new IllegalArgumentException("Unsupported client version " + version);
        return 10000 + Integer.parseInt(parts[1]) * 100 + (parts.length > 2 ? Integer.parseInt(parts[2]) : 0);
    }

    private static double number(JsonObject object, String key) {
        double value = object.has(key) ? object.get(key).getAsDouble() : 0;
        if (!Double.isFinite(value)) throw new IllegalArgumentException(key + " must be finite");
        return value;
    }

    private static String requiredString(JsonObject object, String key) {
        if (!object.has(key)) throw new IllegalArgumentException("Missing " + key);
        return object.get(key).getAsString();
    }
}
