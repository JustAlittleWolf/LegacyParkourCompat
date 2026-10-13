package me.wolfii.legacyparkourcompat.change.v1_10_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.FarmlandEntityPushHook;
import net.minecraft.world.entity.player.Player;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.10.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java:
 *   public Box getShape(BlockState state, WorldView world, BlockPos pos).
 *   public void onFallenOn(World world, BlockPos pos, Entity entity, float fallDistance).
 * 1.11.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java:
 *   public Box getShape(BlockState state, WorldView world, BlockPos pos).
 *   private void setDirt(World world, BlockPos pos).
 *   public void onFallenOn(World world, BlockPos pos, Entity entity, float fallDistance).
 * 1.12.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java:
 *   public Box getShape(BlockState state, WorldView world, BlockPos pos).
 *   setDirt(world, pos);.
 *   setDirt(world, pos);.
 *   protected static void setDirt(World world, BlockPos pos).
 *   setDirt(world, pos);.
 *   setDirt(world, pos);.
 *   public void onFallenOn(World world, BlockPos pos, Entity entity, float fallDistance).
 * No exact independently accepted source binding is established for this operation.
 * Remaining source/applicability gates:
 *   Later applicability gate: exact introduction of campaign-F-08-farmland-landing-player-position is unknown within (1.10.2, 1.11.2]
 *   Exact independent source verdict/snapshot identity for F-08-farmland-landing-player-position
 *   Exact independent source verdict/snapshot identity for F004
 *   Later applicability gate: exact introduction of campaign-F-FARMLAND-PLAYER-RELOCATION is unknown within (1.11.2, 1.12.2]
 *   Exact independent source verdict/snapshot identity for F-FARMLAND-PLAYER-RELOCATION
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_10_1)
public final class FarmlandConversion implements FarmlandEntityPushHook {
    @Override
    public boolean suppressPlayerPushUp(Player player) {
        return true;
    }
}
