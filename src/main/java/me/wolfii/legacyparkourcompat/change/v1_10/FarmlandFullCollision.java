package me.wolfii.legacyparkourcompat.change.v1_10;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockCollisionShapeHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

/**
 * Farmland used a full-block collision shape through Minecraft 1.10.
 *
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.10.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java:
 *   public Box getShape(BlockState state, WorldView world, BlockPos pos) {; lines 32-34.
 *   public void onFallenOn(World world, BlockPos pos, Entity entity, float fallDistance) {; lines 61-71.
 *   Source SHA-256: 63d9048ebed65b890c370a1da6e79733904f67d953aff9fd8c0316f97f9d4e7f.
 * 1.9.4/ornithe-feather/net/minecraft/block/FarmlandBlock.java:
 *   public Box getShape(BlockState state, WorldView world, BlockPos pos) {; lines 32-34.
 *   public Box getCollisionShape(BlockState state, World world, BlockPos pos) {; lines 38-40.
 *   public void onFallenOn(World world, BlockPos pos, Entity entity, float fallDistance) {; lines 67-77.
 *   public Box getShape(BlockState state, WorldView world, BlockPos pos) {; lines 32-34.
 *   public Box getCollisionShape(BlockState state, World world, BlockPos pos) {; lines 38-40.
 *   public void onFallenOn(World world, BlockPos pos, Entity entity, float fallDistance) {; lines 67-77.
 *   Source SHA-256: 9df445485ba7e1603c20b5dede847fe412558148acefe1eeddc9bb3b62b14c25.
 * Bounded source decisions:
 *   Review 804375e6929fd6549a25aab756cea429f329c1d3; accepted, bounded.
 *   Reviewed snapshot c1799749fdef26e706aea1aef8150d5b72458ecf; SHA-256 8a8b68a4e70c44d666a55caaeff6a077fece5b7382f77d5d082cf1cca10c07c6.
 *   Review 804375e6929fd6549a25aab756cea429f329c1d3; accepted, bounded.
 *   Reviewed snapshot c1799749fdef26e706aea1aef8150d5b72458ecf; SHA-256 8a8b68a4e70c44d666a55caaeff6a077fece5b7382f77d5d082cf1cca10c07c6.
 *   Decision 4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/archive/legacy-workflows/coverage-review-2026-10-07/early.md; [{&#x27;section&#x27;: &#x27;Fresh verification of Feather derived-artifact revision&#x27;, &#x27;verdict&#x27;: [&#x27;**Decision: ACCEPTED, bounded to this source finding.** I independently confirmed the exact frozen owner record and the already reviewed source semantics. The immutable finding is `workflows/source-campaign-2026-10-07/1.13.2--1.14.4/findings/F002-pose-selection-and-collision-aware-resize.md`, commit `448934e826fb41266dc79a313ef1187899d76382`, SHA-256 `baa5c6b30167eaa8024d34b39186440be944115061c8f39fdf5e17555b8ec67e`, Git blob `0c2b00b276604d6c20d4536722cc8304fb293148`. Its exact snapshot event is `F002-2026-10-07-01` in `workflows/source-campaign-2026-10-07/1.13.2--1.14.4/run.md`, owner snapshot commit `f567f09f2279fc0b79234cb6706abf729db89f9b`, SHA-256 `ea63d376b8d2d85e00e2190171a41f072ddd84c8b6db35450a00951379d03926`.&#x27;, &quot;**Decision: PENDING; source delta verified, finding disposition needs a corrected immutable snapshot.** The F001 file is `workflows/source-campaign-2026-10-07/1.9.4--1.10.2/findings/F001-farmland-collision-height.md`, SHA-256 `f2f3af8b4d5efcf9c1a208fc84b57a4cd39938a3ae97f6235598f26431115a78`, Git blob `79055253498e6481efd2e6e67e006abc63145c7c`. The r2 event (`c4ca5a7c3a6ae275eac8c5c7adaffa428c9a3b93`) was superseded before review due to an F002 fallback clarification. F001&#x27;s bytes are unchanged in r3 commit `163841261802b34cbb2b6eb9c607e1baef6bb468` and are submitted in the current owner report on `feat/source-discovery-movement-source-1-9-4-1-10-2` at `d2db57ca31a121dca9cc285f19d241d01e7c330c`.&quot;, &#x27;**Decision: ACCEPTED, bounded to the paired farmland collision-height finding.** The exact finding is `workflows/source-campaign-2026-10-07/1.9.4--1.10.2/findings/F001-farmland-collision-height.md`, immutable finding commit `c1799749fdef26e706aea1aef8150d5b72458ecf`, SHA-256 `8a8b68a4e70c44d666a55caaeff6a077fece5b7382f77d5d082cf1cca10c07c6`, Git blob `8253669ab73f56c0e225f70b0207f5eebcc54c05`. Exact snapshot event `FS-1.9.4-1.10.2-2026-10-07-r4` is in `workflows/source-campaign-2026-10-07/1.9.4--1.10.2/run.md`, ledger commit `dcd8b8e69f96ececae8b724d4a0ac53dfe2f2feb`, run-file SHA-256 `3ec3d5c1bc992fe06bc317cb8acea716ea15722c39ec99277ab845cb266a4598`.&#x27;], &#x27;snapshot_hash_matches&#x27;: True}].
 * Remaining source/applicability gates:
 *   Later applicability gate: exact introduction of campaign-F001-farmland-collision-height is unknown within (1.9.4, 1.10.2]
 *   Exact independent source verdict/snapshot identity for F003-farmland-collision
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_10)
public final class FarmlandFullCollision implements BlockCollisionShapeHook {
    @Override
    public String blockId() {
        return "minecraft:farmland";
    }

    @Override
    public Optional<VoxelShape> collisionShape(
        BlockState state,
        BlockGetter level,
        BlockPos pos,
        CollisionContext context
    ) {
        return Optional.of(Shapes.block());
    }
}
