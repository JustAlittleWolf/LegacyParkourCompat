# Independent review verdict: original 1.13 cardinal border masks

**Verdict: ACCEPT.** The source author's report is accurate for the assigned fixed setup. I independently retraced the exact 1.13 source gates, reconstructed the finite occupancy mask for each cardinal, and replayed `VoxelSet.forEachBox` in source order. No discrepancy found.

## Evidence identity

The report under review has SHA-256 `eb1ba1cd85131bf783970e7acfd490a39ba023cfc1d8b437374502c818aa57f1`, matching the assigned identity. Exact source is `decompiled_minecraft/1.13/ornithe-feather` (Minecraft 1.13, Ornithe Feather Gen2 `1.13+build.2`); provenance SHA-256 `34652aeef6914b5cbff6619ae60a855aa676b2ae1f607eccdaa623ad09dc5e8d`, source-manifest SHA-256 `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a`. Relevant source file hashes checked against that manifest all matched, including `WorldView.java`, `LocalClientPlayerEntity.java`, `VoxelSet.java`, `BitSetVoxelSet.java`, `IntVoxelShape.java`, `VoxelShapes.java`, `WorldBorder.java`, `Box.java`, `Entity.java`, `LivingEntity.java`, `PlayerEntity.java`, `StatusEffect.java`, `Minecraft.java`, `Block.java`, and `StoneBlock.java`.

## Independent mask and decomposition check

With border bounds `[-10,10]` on X/Z, `WorldBorder.contains(BlockPos)` includes integer cells -10 through 9; `collidesWithBorder` is true at the assigned positions. `WorldView.getBlockCollisions` uses scan intervals `[floor(min)-1, ceil(max)+1)`, rejects columns only when both scan-edge flags are true, and omits the final Y scan cell whenever either edge flag is true. The exact query extents yield the report's four ranges. Under the `bl=false` AutoJump caller, full-cube shapes are inserted directly into the finite `BitSetVoxelSet` without query-overlap filtering, so the y=63 Stone support cells are retained along with border-generated cubes.

Each cardinal mask has exactly 29 occupied cells: its two Stone cells plus 27 synthetic border cubes. The synthetic cells independently reproduce as:

- E: `x=10, y=63..65, z=-2..2`; `x=10, y=66, z=-1..1`; `x=11, y=63..65, z=-1..1`.
- W: `x=-11, y=63..65, z=-2..2`; `x=-11, y=66, z=-1..1`; `x=-12, y=63..65, z=-1..1`.
- N: `z=-11, y=63..65, x=-2..2`; `z=-11, y=66, x=-1..1`; `z=-12, y=63..65, x=-1..1`.
- S: `z=10, y=63..65, x=-2..2`; `z=10, y=66, x=-1..1`; `z=11, y=63..65, x=-1..1`.

Applying the exact source greedy routine (X, then Y, then Z runs; consume Z runs, grow X, then Y, emit half-open bounds) reproduces the report's direction-specific ordered boxes and counts: E 7, W 5, N 4, S 4. In particular, the decompositions are not interchangeable by simply rotating E's emitted list.

The first intersecting box for the two lateral ray segments is E box 4 `[10,12)×[64,66)×[0,1)`, W box 1 `[-12,-10)×[63,66)×[-1,2)`, N box 1 `[-2,3)×[63,66)×[-11,-10)`, and S box 1 `[-2,3)×[63,66)×[10,11)`. Their reported centers floor to the stated block positions. The pre-scan top and top-plus-one positions, each first-hit `center.up(1/2)` pair, and the repeated direct `up()` read are AIR under the assigned state layout.

## App gates and outcome

`LocalClientPlayerEntity.move` invokes `autoJump` after `super.move`. The applicable 1.13 gates are: Auto Jump enabled; cooldown `<= 0`; on ground; not sneaking or riding; nonzero movement input; moved-horizontal squared magnitude above `0.001` (so the yaw/input fallback is bypassed); movement-facing dot product not below `-0.15`; and empty collision shapes at the current head cell and the cell above. The player is normal-sized (width `0.6F`, height `1.8F`); Speed II supplies the stated movement speed for the edge extension, and Jump Boost II has amplifier 1. With the exact `o = 1.2F + (amplifier + 1) * 0.75F` rise cap, `o` is about `2.7F`. Every first hit has `maxY=66`, yielding `u=2.0F`; the loop reads `t=1,2`, all relevant raw block samples are AIR, and the source writes `ticksToNextAutojump=1` on all four paths.

The reported ordinary frame tick cap is a separate client-loop behavior and does not affect this AutoJump result. This verdict is limited to the named 1.13 fixed setup and does not establish a patch boundary or behavior in another release.
