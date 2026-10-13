package me.wolfii.legacyparkourcompat.change.v1_10_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.FarmlandEntityPushHook;
import net.minecraft.world.entity.player.Player;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.10.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java:
 *   public Box getShape(BlockState state, WorldView world, BlockPos pos) {; lines 32-34.
 *   public void onFallenOn(World world, BlockPos pos, Entity entity, float fallDistance) {; lines 61-71.
 *   Source SHA-256: 63d9048ebed65b890c370a1da6e79733904f67d953aff9fd8c0316f97f9d4e7f.
 * 1.11.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java:
 *   public Box getShape(BlockState state, WorldView world, BlockPos pos) {; lines 30-32.
 *   private void setDirt(World world, BlockPos pos) {; lines 71-79.
 *   public void onFallenOn(World world, BlockPos pos, Entity entity, float fallDistance) {; lines 59-69.
 *   Source SHA-256: 09e260528fd78b4bbdbc302726c7ae937fbc49f17b674c4a7ad802e0f7b6f5e8.
 * 1.12.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java:
 *   public Box getShape(BlockState state, WorldView world, BlockPos pos) {; lines 31-33.
 *   setDirt(world, pos);; lines 52-52.
 *   setDirt(world, pos);; lines 66-66.
 *   protected static void setDirt(World world, BlockPos pos) {; lines 72-80.
 *   setDirt(world, pos);; lines 101-101.
 *   setDirt(world, pos);; lines 109-109.
 *   public void onFallenOn(World world, BlockPos pos, Entity entity, float fallDistance) {; lines 60-70.
 *   Source SHA-256: d84c75dae139181b5d58cfa34f3cb77c5b2d4bda63d6ad9c3b52a47350bd0b98.
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
