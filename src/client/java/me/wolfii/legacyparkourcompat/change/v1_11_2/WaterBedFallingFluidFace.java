package me.wolfii.legacyparkourcompat.change.v1_11_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.FallingFluidFaceSturdyHook;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;

/**
 * Ornithe Feather Gen2 1.11.2 LiquidBlock.isFaceSolid delegates to Block.isFaceSolid, which
 * reads Material.isSolid; BedBlock uses Material.WOOL. In 1.12.2, LiquidBlock.isFaceSolid reads
 * the neighbor face shape and BedBlock.getFaceShape returns UNDEFINED. The existing
 * FlowingFluid.isSolidFace hook changes only this local-player water face answer; native falling
 * flow traversal and vector arithmetic remain intact.
 */
@MovementChange(emulates = ParkourVersion.V1_11_2)
public final class WaterBedFallingFluidFace implements FallingFluidFaceSturdyHook {
    @Override
    public String variant() {
        return "minecraft:red_bed";
    }

    @Override
    public boolean isFaceSturdy(FlowingFluid fluid, Player player, BlockState state, BlockGetter level,
                                BlockPos pos, Direction direction, VanillaFn<Boolean> vanilla) {
        if (fluid.is(FluidTags.WATER)
            && !player.getAbilities().flying
            && !(player.getVehicle() instanceof AbstractBoat)
            && state.is(Blocks.BED.red())) {
            return true;
        }
        return vanilla.get();
    }
}
