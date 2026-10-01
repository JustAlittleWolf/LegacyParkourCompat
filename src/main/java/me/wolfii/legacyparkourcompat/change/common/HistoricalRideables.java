package me.wolfii.legacyparkourcompat.change.common;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
/** Era availability of the vanilla rideables used by the confirmed sprint/current deltas. */
public final class HistoricalRideables {
    private HistoricalRideables() {}
    public static boolean contains(Entity vehicle, ParkourVersion selected) {
        String id = BuiltInRegistries.ENTITY_TYPE.getKey(vehicle.getType()).getPath();
        ParkourVersion first = switch (id) {
            case "oak_boat", "boat", "minecart", "chest_minecart", "furnace_minecart", "tnt_minecart",
                "hopper_minecart", "spawner_minecart", "command_block_minecart", "pig", "horse", "donkey",
                "mule", "skeleton_horse", "zombie_horse" -> ParkourVersion.V1_8;
            case "spruce_boat", "birch_boat", "jungle_boat", "acacia_boat", "dark_oak_boat" -> ParkourVersion.V1_9;
            case "llama" -> ParkourVersion.V1_11;
            case "trader_llama" -> ParkourVersion.V1_14;
            case "strider" -> ParkourVersion.V1_16;
            case "mangrove_boat", "chest_boat", "oak_chest_boat", "spruce_chest_boat", "birch_chest_boat",
                "jungle_chest_boat", "acacia_chest_boat", "dark_oak_chest_boat", "mangrove_chest_boat" -> ParkourVersion.V1_19;
            default -> null;
        };
        return first != null && selected.newerThanOrEqual(first);
    }
}
