package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Historical {@code Block#fallOn} behaviour for one block (slime bounce, honey, beds).
 */
@MechanicType("block.fall")
public interface BlockFallBehavior extends VersionedMechanic {
    String blockId();

    @Override
    default String variant() {
        return this.blockId();
    }

    void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, double fallDistance, VanillaCall vanilla);
}
