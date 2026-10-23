package me.wolfii.legacyparkourcompat.change.v1_11_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.WallCollisionGeometry;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.WallCollisionHeightHook;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

/**
 * Ornithe Feather 1.11.1 {@code WallBlock#addCollisions} adds the outline from
 * {@code SHAPES[getShapeIndex(state)]} rather than the {@code COLLISION_SHAPES} table used
 * by {@code getCollisionShape}. In Feather 1.11.1, {@code WallBlock#SHAPES} entries 5 and 10
 * have maxY=0.875 and the other entries have maxY=1.0; {@code WallBlock#addCollisions} uses
 * the state-derived index. In 1.11.2, {@code WallBlock#addCollisions} selects
 * {@code COLLISION_SHAPES}, whose entries all have maxY=1.5.
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
    public double maxY(BlockState state) {
        int mask = WallCollisionGeometry.mask(state);
        return mask == 5 || mask == 10 ? 0.875 : 1.0;
    }
}
