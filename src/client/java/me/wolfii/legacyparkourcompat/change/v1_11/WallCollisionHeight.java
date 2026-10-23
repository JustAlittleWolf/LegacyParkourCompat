package me.wolfii.legacyparkourcompat.change.v1_11;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.WallCollisionHeightHook;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

/**
 * Ornithe Feather 1.8.9 {@code WallBlock#getCollisionShape} sets {@code maxY=1.5};
 * 1.10.2 and 1.11 {@code WallBlock#getCollisionShape} select collision boxes whose
 * entries use {@code withMaxY(1.5)}. The 1.11.1 collision-collection exception is
 * represented by the next versioned change.
 */
@MovementChange(emulates = ParkourVersion.V1_11)
public final class WallCollisionHeight implements WallCollisionHeightHook {
    private final String blockId;

    private WallCollisionHeight(String blockId) {
        this.blockId = blockId;
    }

    @MovementChange.Factory
    public static List<WallCollisionHeight> variants() {
        return List.of(new WallCollisionHeight("minecraft:cobblestone_wall"),
            new WallCollisionHeight("minecraft:mossy_cobblestone_wall"));
    }

    @Override
    public String blockId() {
        return this.blockId;
    }

    @Override
    public double maxY(BlockState state) {
        return 1.5;
    }
}
