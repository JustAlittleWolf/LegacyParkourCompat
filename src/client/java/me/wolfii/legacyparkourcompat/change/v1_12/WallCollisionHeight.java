package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.WallCollisionHeightHook;

import java.util.List;

/**
 * Ornithe Feather 1.12.2 {@code WallBlock.COLLISION_SHAPES} gives every horizontal
 * connection mask a box from Y=0 to Y=1.5. This explicit resolved height also supplies
 * the old ceiling when the modern UP=false pane collision shape is empty.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
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
    public double maxY() {
        return 1.5;
    }
}
