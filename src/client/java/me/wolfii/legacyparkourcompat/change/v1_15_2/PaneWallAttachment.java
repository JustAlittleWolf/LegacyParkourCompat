package me.wolfii.legacyparkourcompat.change.v1_15_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.pane.PaneWallAttachmentHook;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.state.BlockState;

/** 1.15.2 IronBarsBlock#attachsTo does not accept wall-tagged neighbors; 1.16 adds them. */
@MovementChange(emulates = ParkourVersion.V1_15_2)
public final class PaneWallAttachment implements PaneWallAttachmentHook {
    public PaneWallAttachment() {
    }

    @Override
    public boolean suppressAttachment(BlockState pane, BlockState neighbor, ParkourVersion selected) {
        return selected == ParkourVersion.V1_15_2
            && WallConnectionBlocks.isPaneOrBars(pane) && WallConnectionBlocks.isWall(neighbor)
            && neighbor.is(BlockTags.WALLS);
    }
}
