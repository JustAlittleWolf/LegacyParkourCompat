package me.wolfii.legacyparkourcompat.mechanic.hook.block;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;

@MechanicType("block.shulker_box.opening_query")
public interface ShulkerBoxOpeningQueryHook extends VersionedMechanic {
    boolean supports(Block block, ParkourVersion target);

    AABB bounds(BlockPos pos, Direction facing, float previousProgress, float progress);
}
