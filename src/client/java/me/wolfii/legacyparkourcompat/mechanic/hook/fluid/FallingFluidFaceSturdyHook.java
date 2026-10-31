package me.wolfii.legacyparkourcompat.mechanic.hook.fluid;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;

import java.util.List;

/** FULL-face support predicate used by a local player's falling-fluid current query. */
@MechanicType("fluid.falling_face_sturdy")
public interface FallingFluidFaceSturdyHook extends VersionedMechanic {
    /** Stair keys present in the 1.13 source domain, shared by its keyed boundaries. */
    List<String> V1_13_STAIR_IDS = List.of(
        "minecraft:oak_stairs", "minecraft:cobblestone_stairs", "minecraft:brick_stairs",
        "minecraft:stone_brick_stairs", "minecraft:nether_brick_stairs", "minecraft:sandstone_stairs",
        "minecraft:spruce_stairs", "minecraft:birch_stairs", "minecraft:jungle_stairs",
        "minecraft:quartz_stairs", "minecraft:acacia_stairs", "minecraft:dark_oak_stairs",
        "minecraft:prismarine_stairs", "minecraft:prismarine_brick_stairs", "minecraft:dark_prismarine_stairs",
        "minecraft:red_sandstone_stairs", "minecraft:purpur_stairs"
    );

    boolean isFaceSturdy(FlowingFluid fluid, Player player, BlockState state, BlockGetter level,
                         BlockPos pos, Direction direction, VanillaFn<Boolean> vanilla);
}
