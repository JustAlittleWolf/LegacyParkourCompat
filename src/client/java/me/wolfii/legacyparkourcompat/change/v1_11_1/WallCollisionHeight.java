package me.wolfii.legacyparkourcompat.change.v1_11_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.WallCollisionHeightHook;

import java.util.List;

/**
 * Ornithe Feather 1.11.1 {@code WallBlock#addCollisions} adds the outline from
 * {@code SHAPES}, whose maximum Y is 1.0, even though {@code getCollisionShape}
 * still returns {@code COLLISION_SHAPES}. In 1.11.2, {@code addCollisions} uses
 * {@code COLLISION_SHAPES}, whose entries have maximum Y 1.5.
 */
@MovementChange(emulates = ParkourVersion.V1_11_1)
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
        return 1.0;
    }
}
