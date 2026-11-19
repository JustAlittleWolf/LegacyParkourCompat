package me.wolfii.legacyparkourcompat.change.v1_15_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.wall.WallSoulSandConnectionHook;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

/** 1.15.2 Soul Sand's collision shape is not sturdy; 1.16 adds its full support shape for wall connections. */
@MovementChange(emulates = ParkourVersion.V1_15_2)
public final class WallSoulSandConnection implements WallSoulSandConnectionHook {
    public WallSoulSandConnection() {
    }

    @Override
    public boolean suppressConnection(BlockState wall, BlockState neighbor, ParkourVersion selected) {
        return selected == ParkourVersion.V1_15_2
            && WallConnectionBlocks.isWall(wall) && neighbor.is(Blocks.SOUL_SAND);
    }
}
