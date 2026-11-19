package me.wolfii.legacyparkourcompat.change.v1_15_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.wall.WallPaneAttachmentHook;
import net.minecraft.world.level.block.state.BlockState;

/** 1.15.2 WallBlock#connectsTo has no iron-bars branch; 1.16 adds one. */
@MovementChange(emulates = ParkourVersion.V1_15_2)
public final class WallPaneAttachment implements WallPaneAttachmentHook {
    public WallPaneAttachment() {
    }

    @Override
    public boolean suppressAttachment(BlockState wall, BlockState neighbor, ParkourVersion selected) {
        return selected == ParkourVersion.V1_15_2
            && WallConnectionBlocks.isWall(wall) && WallConnectionBlocks.isPaneOrBars(neighbor);
    }
}
