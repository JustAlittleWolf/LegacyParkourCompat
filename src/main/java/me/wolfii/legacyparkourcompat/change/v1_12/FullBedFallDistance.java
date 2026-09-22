package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.BlockChanges;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeRegistry;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.hook.BlockFallBehavior;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

/** Before 1.12, beds used the ordinary block fall distance. */
@MovementChange(emulates = ParkourVersion.V1_11_2)
public final class FullBedFallDistance implements BlockFallBehavior {
    private final String blockId;

    public FullBedFallDistance(String blockId) {
        this.blockId = blockId;
    }

    public static void register(MovementChangeRegistry registry) {
        BlockChanges.registerEach(registry, block -> block == Blocks.BED.red(), FullBedFallDistance::new);
    }

    @Override
    public String blockId() {
        return this.blockId;
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, double fallDistance, VanillaCall vanilla) {
        entity.causeFallDamage(fallDistance, 1.0F, entity.damageSources().fall());
    }
}
