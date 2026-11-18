# Exact 1.13 cardinal border and shared-mask audit

## Source identity and boundary

This is a direct static read of the canonical `decompiled_minecraft/1.13/ornithe-feather/` tree. The ready marker identifies Minecraft `1.13`, Ornithe Feather Gen2 `1.13+build.2`, raw client SHA-256 `d807a5ed9428b6c16b64888fd2b7fa76dcf5c55d06f96f7c0c47762c3fc3e26e`, mapped client SHA-256 `4b91c330aaf6179f7384f70f91ebcdf72af2713cc3788fea1482c3529905a761`, Feather merged mapping SHA-256 `a7a61604c7b365db1f4250c4c0079172c3beff37f4e16f7a53a13d77f3976897`, and Tiny SHA-256 `7fa6b27aab7245c2d47ecbfe86ad9178272d7b1fd9ba69a482a41659387ffd84`. The verified source-manifest SHA-256 is `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a`; the artifact-manifest SHA-256 is `a2c17075b601d9b83703f7ee188b04c6d9f68cac08d97b1a4f838fe8cd2e2c24`. Ready marker reports 2,702 source files. Exact decompiler identity: Java 25.0.3+9-LTS, Vineflower 1.12.0, mapping-io 0.9.1, TinyRemapper 0.14.1.

Consumed source members: `world/WorldView.java`, `getBlockCollisions` (113–184) and `getCollisions(Entity, Box)` (199–201); `client/entity/living/player/LocalClientPlayerEntity.java`, `autoJump` (878–968); `block/shape/VoxelSet.java`, `forEachBox`; `block/shape/BitSetVoxelSet.java`, `set`/`forEachBox`; `block/shape/IntVoxelShape.java`, coordinate mapping; `block/shape/VoxelShapes.java`, `joinUnoptimized`; `world/border/WorldBorder.java`, `contains(BlockPos)`; `block/Block.java`, full-block collision shape; `block/StoneBlock.java`; `entity/living/LivingEntity.java`, Jump Boost addition; `client/Minecraft.java`, tick loop cap.

The later-read east audit `aggregate-autojump-stone-mask-recheck/exact-113-old-edge-repro.md` is used only as a concordance check after freezing the 1.13 source math. Its east scan, 29-cell mask, greedy boxes, first hit and timer result agree with this derivation. No 1.13.1 source behavior or square geometry is used here.

## Exact 1.13 collection rules applied

`WorldView.getCollisions(entity, box)` passes empty exclusion shapes and `bl=false` to `getBlockCollisions`. The static size-20, center-(0,0) border has half-open cell occupancy from `contains(BlockPos)`: integer X/Z cells `-10..9` are inside; cells `<= -11` or `>= 10` are outside. `collidesWithBorder(entity)` is true for these four eroded positions, so the collector enables border cells.

For a bounds shape, 1.13 computes each scan range as `[floor(min)-1, ceil(max)+1)`. The four AutoJump bounds, with player width 0.6 grown on X/Z, height 1.8, start feet at 64, and axial endpoint magnitude about 10.068286, produce:

| Direction | Scan X | Scan Y | Scan Z |
|---|---:|---:|---:|
| E | [7,12) | [63,67) | [-2,3) |
| W | [-12,-7) | [63,67) | [-2,3) |
| N | [-2,3) | [63,67) | [-12,-7) |
| S | [-2,3) | [63,67) | [7,12) |

In the original collector, X is the outer loop and Z the middle loop. Let `xEdge` mean X equals either scan endpoint cell and `zEdge` mean Z equals either scan endpoint cell. It rejects an entire X/Z corner column when both flags are true: `(!xEdge || !zEdge)`. In every remaining column it omits the final scanned Y cell (`y=66`) when either flag is true; the top Y cell is admitted only if both X and Z are interior. This is the original 1.13 gate. Each admitted X/Z sample is chunk-loaded at Y=64; all are loaded in the stated setup.

For the AutoJump caller's `bl=false`, every exact full-cube collision shape is written to the shared `BitSetVoxelSet` without testing overlap against either query bounds or exclusion shape. That includes the two y=63 Stone supports even though they only touch the query's bottom, plus border-generated full cubes. Only non-full shapes take the overlap checks; here all other physical states are AIR. The collector returns the mask as an `IntVoxelShape`, and its X/Y/Z greedy `forEachBox` decomposition is the sequence below.

## Complete accepted mask cells

Notation `x=a; y=b..c; z=d..e` (or the transposed notation for N/S) means every integer cell in the inclusive ranges. Each mask has 29 cells: the two listed physical Stone cells plus 27 synthetic outside-border full cubes.

| Direction | Physical Stone cells | Synthetic border cells admitted by the 1.13 gate |
|---|---|---|
| E | `(8,63,0)`, `(9,63,0)` | `x=10, y=63..65, z=-2..2`; `x=10, y=66, z=-1..1`; `x=11, y=63..65, z=-1..1` |
| W | `(-10,63,0)`, `(-9,63,0)` | `x=-11, y=63..65, z=-2..2`; `x=-11, y=66, z=-1..1`; `x=-12, y=63..65, z=-1..1` |
| N | `(0,63,-10)`, `(0,63,-9)` | `z=-11, y=63..65, x=-2..2`; `z=-11, y=66, x=-1..1`; `z=-12, y=63..65, x=-1..1` |
| S | `(0,63,8)`, `(0,63,9)` | `z=10, y=63..65, x=-2..2`; `z=10, y=66, x=-1..1`; `z=11, y=63..65, x=-1..1` |

The cell arithmetic is identical under reflection/axis swap: 18 cells on the scan-interior border column, 9 on its scan-edge neighbor, and 2 Stone cells. The old collector's asymmetric outer-loop and greedy ordering still make the ordered boxes differ by direction; they must not be rotated from E.

## Greedy shared-mask boxes and first ray hits

Boxes are half-open world-coordinate bounds, listed in the exact order returned by the original `VoxelSet.forEachBox` scan. `hit` is the first box whose AABB strictly overlaps either AutoJump ray-segment AABB at Y≈64.51 and tangent≈0.200525 or 0.799475. E uses the direct east source reference endpoint; the other endpoints are the equal-magnitude axial reflection/axis-swap for this cardinal setup.

### E

| Order | X | Y | Z | Ray overlap |
|---:|---|---|---|---|
| 1 | [8,12) | [63,64) | [0,1) | no |
| 2 | [10,11) | [63,66) | [-2,0) | no |
| 3 | [10,11) | [63,66) | [1,3) | no |
| 4 | [10,12) | [64,66) | [0,1) | **first hit** |
| 5 | [10,11) | [66,67) | [-1,2) | no |
| 6 | [11,12) | [63,66) | [-1,0) | no |
| 7 | [11,12) | [63,66) | [1,2) | no |

### W

| Order | X | Y | Z | Ray overlap |
|---:|---|---|---|---|
| 1 | [-12,-10) | [63,66) | [-1,2) | **first hit** |
| 2 | [-11,-10) | [63,66) | [-2,-1) | no |
| 3 | [-11,-10) | [63,66) | [2,3) | no |
| 4 | [-11,-10) | [66,67) | [-1,2) | no |
| 5 | [-10,-8) | [63,64) | [0,1) | no |

### N

| Order | X | Y | Z | Ray overlap |
|---:|---|---|---|---|
| 1 | [-2,3) | [63,66) | [-11,-10) | **first hit** |
| 2 | [-1,2) | [63,66) | [-12,-11) | no |
| 3 | [-1,2) | [66,67) | [-11,-10) | no |
| 4 | [0,1) | [63,64) | [-10,-8) | no |

### S

| Order | X | Y | Z | Ray overlap |
|---:|---|---|---|---|
| 1 | [-2,3) | [63,66) | [10,11) | **first hit** |
| 2 | [-1,2) | [63,66) | [11,12) | no |
| 3 | [-1,2) | [66,67) | [10,11) | no |
| 4 | [0,1) | [63,64) | [8,10) | no |

Each first hit has top Y=66, so the recorded `s=(float)box.maxY` is 66 and `u=(float)(s - player.shape.minY)` is 2.0. The floor box appears earlier in W/N/S order, but the two ray segments are above it at Y≈64.51; E's first three boxes fail because of their Y or tangent ranges.

## Exact sampled states and cap

At the four positions (E `(8.99,64,0.5)`, W `(-8.99,64,0.5)`, N `(0.5,64,-8.99)`, S `(0.5,64,8.99)`), the first two AutoJump clearance reads before scanning are the block at `floor(x, shape.maxY, z)` and that block's `.up()`:

| Direction | Pre-scan AIR samples |
|---|---|
| E | `(8,65,0)`, `(8,66,0)` |
| W | `(-9,65,0)`, `(-9,66,0)` |
| N | `(0,65,-9)`, `(0,66,-9)` |
| S | `(0,65,8)`, `(0,66,8)` |

For each first ray box, AutoJump takes its center and floors it to a block position. The two `t` loop reads and the extra direct read executed at `t=2` are:

| Direction | `box.center()` floored | `center.up(1)` AIR | `center.up(2)` AIR | extra direct AIR read |
|---|---|---|---|---|
| E | `(11,65,0)` | `(11,66,0)` | `(11,67,0)` | `(8,66,0)` |
| W | `(-11,64,0)` | `(-11,65,0)` | `(-11,66,0)` | `(-9,66,0)` |
| N | `(0,64,-11)` | `(0,65,-11)` | `(0,66,-11)` | `(0,66,-9)` |
| S | `(0,64,10)` | `(0,65,10)` | `(0,66,10)` | `(0,66,8)` |

All stated cells are AIR under the assigned state layout; the only physical Stone is y=63. The direct read at t=2 repeats the pre-scan `.up()` cell. There are therefore five source-level state reads per cardinal path, with four unique positions per path.

Jump Boost II is amplifier 1. The AutoJump cap is computed in float order as `o = 1.2F + (amplifier + 1) * 0.75F`, approximately `2.7F`; its integer loop visits `t=1,2`. Since `u=2.0F` is greater than 0.5F and not greater than this cap, each of the four source paths writes `ticksToNextAutojump = 1`. The ordinary client frame loop separately caps game ticks at `Math.min(10, ticksThisFrame)`; that cap is unrelated to the AutoJump rise cap/cooldown.

## Reproducibility and disposition

Reproduce the cell sets from each scan range using the exact original gate above, insert the listed y=63 Stone cells and synthetic cells into a single X/Y/Z occupancy grid, then apply `VoxelSet.forEachBox`'s source algorithm: enumerate X, then Y, collect each Z run, consume matching X runs, extend matching Y layers, and emit half-open boxes. Test each box against the two cardinal ray segment AABBs in order. No runtime, game, tests, decompilation, source generation, implementation, product, or wiki evidence was used.
