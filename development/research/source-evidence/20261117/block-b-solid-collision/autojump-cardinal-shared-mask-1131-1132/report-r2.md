# 1.13.1/1.13.2 cardinal shared-border mask proof — r2

## Verdict and source identity

The relevant AutoJump, border collector, and voxel decomposition procedures are byte-identical in exact 1.13.1 and 1.13.2 Ornithe Feather sources. This geometric fixture establishes no 1.13.1→1.13.2 movement change.

1.13.1 is Feather Gen2 `1.13.1+build.2`, provenance run `prep-1.13-1.13.1-feather-2026-10-09-r1`; provenance SHA-256 `c28ed59f2c507b6133f7677742d316d38e9993eaf1fbb6c2054bd68d1085e904`, source-manifest SHA-256 `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba`. 1.13.2 is Feather Gen2 `1.13.2+build.2`; provenance SHA-256 `e25950b7855fc4f2aee1a662f0be6b0af9f28788d9e27293075cfc5a3aa5d383`, source-manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`. The 1.13.2 verification record reports all 2,711 source files identical to its verified tree.

Direct manifest/hash checks agree for `LocalClientPlayerEntity.java` (`2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf`), `WorldView.java` (`9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f`), `WorldBorder.java` (`7593d7a8df153f2a039a5b38cca684e1e15529b80ffde743151419e125590718`), `WorldAccess.java` (`fc75177d629ef8d0d491821fe557e21606e4cb381164f1ca41b2763073f854be`), `BlockPos.java` (`ee71cfdedcaa27458f48168ab761b435cf9c878b04f21e971ddbafb6254aa3fd`), `Box.java` (`0f573d00cc10386f94cf51671c62815979973053c9e67a806e9fee98e55a4d8d`), `VoxelSet.java` (`fe39ea60ac27e595280627a4d15ea93a69292da5870b5ef34a1d7d7283a267c6`), `BitSetVoxelSet.java` (`2048daa3a95ddd567a70476862e9b2dde135b91f49b61d22a4ae3aeed98322aa`), `VoxelShape.java` (`d95b0607647da11595e24e1e26e9615cb7da7705ea7885908544362df62275c1`), and `IntVoxelShape.java` (`e563f5530907a5cd0ecc07bd79473d378c8c1aeeaba1dba4ac6f703de7d7b3ca`). The entire `World.java` files do differ; the used `getCollisions` forwarding method at line 1755 is text-identical. Relevant code is at `LocalClientPlayerEntity.move`/`autoJump` (871/882), `WorldView.getBlockCollisions` (112), `WorldView.getCollisions` (166–177), `WorldBorder.contains(BlockPos)` (22), `VoxelSet.forEachBox` (200), `VoxelShape.getBoundingBoxes` (109), `IntVoxelShape.getCoordinates` (19), `BitSetVoxelSet` indexing/set (52/57), and `Box.intersects(Vec3d, Vec3d)` (196).

## Fixture and source procedure

Both world-border bounds are exactly X/Z `[-10,10]`. Tangent coordinate is `+0.5`; feet Y is 64; player width/height are 0.6/1.8. Pre-move normal coordinates are `±8.89`; post-move coordinates are `±8.99`. Side definitions follow the given world axes: E is +X, W is −X, N is −Z, S is +Z. Full Stone support cells are E `(8,63,0),(9,63,0)`; W `(-10,63,0),(-9,63,0)`; N `(0,63,-10),(0,63,-9)`; S `(0,63,8),(0,63,9)`. Every other physical cell is AIR; all scanned chunks are loaded. No corners, partial shapes, clouds, or entities are present.

For each direction, actual horizontal displacement is ±0.10, queried speed is `f=0.14F`, and `p=max(f*7,1/l)=0.98` (`l` is the source's float `fastInvSqrt` of the squared horizontal delta). The raised rays run from the post-move center at Y=64.51 to `current + delta + normalizedDirection*p`. The query is the swept player path grown by 0.6 horizontally, with Y `[64,65.8]`. Integer scan ranges are `[floor(query.min)-1, ceil(query.max)+1)`. The collector skips cells on two or more scan-box edges, adds every admitted outside-border full cube and physical full Stone cube into the same `BitSetVoxelSet`, and appends the aggregate `IntVoxelShape` after building it. The literal greedy routine scans X, then Y, then Z; it consumes a contiguous Z run, extends X, then extends Y. The tables below list every occupied cell and every emitted aggregate box in source order, not a rotated proxy. Rays intersect boxes with strict `Box.intersects` comparisons.

## Complete occupancy and greedy output

Each occupancy row lists world X cells and their occupied Z cells at that Y. All cells not shown are unoccupied in the scan volume.

### E (+X), post center `(8.99,64,0.5)`

Query X `[8.39,10.67]`, Y `[64,65.8]`, Z `[-0.1,1.1]`; scan X `[7,12)`, Y `[63,67)`, Z `[-2,3)`.

| Y | Occupied X → Z cells |
|---:|---|
| 63 | `8→{0}`, `9→{0}` (Stone); `10→{-1,0,1}` (border) |
| 64 | `10→{-2,-1,0,1,2}`, `11→{-1,0,1}` (border) |
| 65 | `10→{-2,-1,0,1,2}`, `11→{-1,0,1}` (border) |
| 66 | `10→{-1,0,1}` (border) |

Ordered boxes: (1) `[8,11]×[63,64]×[0,1]`; (2) `[10,11]×[63,67]×[-1,0]`; (3) `[10,11]×[63,67]×[1,2]`; (4) `[10,11]×[64,66]×[-2,-1]`; (5) `[10,12]×[64,66]×[0,1]`; (6) `[10,11]×[64,66]×[2,3]`; (7) `[10,11]×[66,67]×[0,1]`; (8) `[11,12]×[64,66]×[-1,0]`; (9) `[11,12]×[64,66]×[1,2]`.

Raised rays are Z `.2`, then `.8`; both first hit box 5, center `(11,65,.5)`, which floors to `(11,65,0)`.

### W (−X), post center `(-8.99,64,0.5)`

Query X `[-10.67,-8.39]`, Y `[64,65.8]`, Z `[-0.1,1.1]`; scan X `[-12,-7)`, Y `[63,67)`, Z `[-2,3)`.

| Y | Occupied X → Z cells |
|---:|---|
| 63 | `-11→{-1,0,1}` (border); `-10→{0}`, `-9→{0}` (Stone) |
| 64 | `-12→{-1,0,1}`, `-11→{-2,-1,0,1,2}` (border) |
| 65 | `-12→{-1,0,1}`, `-11→{-2,-1,0,1,2}` (border) |
| 66 | `-11→{-1,0,1}` (border) |

Ordered boxes: (1) `[-12,-10]×[64,66]×[-1,2]`; (2) `[-11,-10]×[63,64]×[-1,2]`; (3) `[-11,-10]×[64,66]×[-2,-1]`; (4) `[-11,-10]×[64,66]×[2,3]`; (5) `[-11,-10]×[66,67]×[-1,2]`; (6) `[-10,-8]×[63,64]×[0,1]`.

Raised rays are Z `.8`, then `.2`; both first hit box 1, center `(-11,65,.5)`, which floors to `(-11,65,0)`.

### N (−Z), post center `(0.5,64,-8.99)`

Query X `[-0.1,1.1]`, Y `[64,65.8]`, Z `[-10.67,-8.39]`; scan X `[-2,3)`, Y `[63,67)`, Z `[-12,-7)`.

| Y | Occupied X → Z cells |
|---:|---|
| 63 | `-1→{-11}`, `0→{-11,-10,-9}`, `1→{-11}` (border at -11; Stone at -10/-9) |
| 64 | `-2→{-11}`, `-1→{-12,-11}`, `0→{-12,-11}`, `1→{-12,-11}`, `2→{-11}` (border) |
| 65 | `-2→{-11}`, `-1→{-12,-11}`, `0→{-12,-11}`, `1→{-12,-11}`, `2→{-11}` (border) |
| 66 | `-1→{-11}`, `0→{-11}`, `1→{-11}` (border) |

Ordered boxes: (1) `[-2,3]×[64,66]×[-11,-10]`; (2) `[-1,2]×[63,64]×[-11,-10]`; (3) `[-1,2]×[64,66]×[-12,-11]`; (4) `[-1,2]×[66,67]×[-11,-10]`; (5) `[0,1]×[63,64]×[-10,-8]`.

Raised rays are X `.2`, then `.8`; both first hit box 1, center `(.5,65,-10.5)`, which floors to `(0,65,-11)`.

### S (+Z), post center `(0.5,64,8.99)`

Query X `[-0.1,1.1]`, Y `[64,65.8]`, Z `[8.39,10.67]`; scan X `[-2,3)`, Y `[63,67)`, Z `[7,12)`.

| Y | Occupied X → Z cells |
|---:|---|
| 63 | `-1→{10}`, `0→{8,9,10}` (Stone at 8/9; border at 10), `1→{10}` |
| 64 | `-2→{10}`, `-1→{10,11}`, `0→{10,11}`, `1→{10,11}`, `2→{10}` (border) |
| 65 | `-2→{10}`, `-1→{10,11}`, `0→{10,11}`, `1→{10,11}`, `2→{10}` (border) |
| 66 | `-1→{10}`, `0→{10}`, `1→{10}` (border) |

Ordered boxes: (1) `[-2,3]×[64,66]×[10,11]`; (2) `[-1,2]×[63,64]×[10,11]`; (3) `[-1,2]×[64,66]×[11,12]`; (4) `[-1,2]×[66,67]×[10,11]`; (5) `[0,1]×[63,64]×[8,10]`.

Raised rays are X `.8`, then `.2`; both first hit box 1, center `(.5,65,10.5)`, which floors to `(0,65,10)`. The first box is admitted from the one-edge cell at X `-2`, Y `64`, Z `10`; X-first greedy expansion carries that single Z cell across X before extending Y. This is the source-order hit; a later box is not the first hit.

## Raw AIR reads and Jump Boost reachability

In E/W/N/S order, the two direct player-head checks are `(8,65,0),(8,66,0)`; `(-9,65,0),(-9,66,0)`; `(0,65,-9),(0,66,-9)`; `(0,65,8),(0,66,8)`. These are physical `getBlockState(...).getCollisionShape(...).isEmpty()` reads and must remain AIR. The extra player-column clearance check reached at loop t=2 is the same side's column at Y=67; t=3 additionally checks Y=68.

Floored first-hit centers in E/W/N/S order are `(11,65,0)`, `(-11,65,0)`, `(0,65,-11)`, `(0,65,10)`. The cap loop samples center `up(t)`: for Jump Boost II, respectively `(11,66,0),(11,67,0)`; `(-11,66,0),(-11,67,0)`; `(0,66,-11),(0,67,-11)`; `(0,66,10),(0,67,10)`. Jump Boost III adds the matching Y=68 cell on each side. Those direct samples also require physical AIR; synthetic border collision does not alter raw block states.

The source cap is `o=1.2F`, with `(amplifier+1)*0.75F` added when Jump Boost is present. Default gives 1.2 and Jump Boost I gives 1.95: each only checks t=1 and rejects rise `u=2` at the final `u>o` gate. Jump Boost II gives 2.7, checks t=1 and 2, and arms `ticksToNextAutojump=1` when the listed cap and head-clearance cells are AIR. Jump Boost III gives 3.45, checks t=1,2,3 and also arms; it is unnecessary when JBII clearances are satisfied. All four first-hit boxes have `maxY=66`, so `s=66` and `u=s-feetY=2`.

This is a bounded exact-source derivation, not runtime parity evidence or a patch-boundary claim. No product source, primary research/backlog, or Git history was consulted or changed.
