# 1.13 / 1.13.1 aggregate-border caller audit, corrected

## Scope and evidence

This independently retraces the bounded exact-Feather sample: standing player, feet y=64, actual post-move `(8.99,64,0.5)` from x=8.89, dx=0.10, Speed II, Jump Boost II (amplifier 1; `o=2.7F`), stationary east border maxX=10, loaded queried chunks, no other colliding entities, physical Stone support at `(8,63,0)` and `(9,63,0)`. Exact pair inputs remain Feather Gen2 build.2 `1.13` and `1.13.1`, prepared together in run `prep-1.13-1.13.1-feather-2026-10-09-r1`. Reviewed sources are the published `decompiled_minecraft/<version>/ornithe-feather` trees; relevant LocalClientPlayerEntity and WorldView file hashes remain those recorded in the prior pair evidence.

The supplied independent verdict was verified at SHA-256 `998d62735a18c5d7624d2bc3ae0d012ab58e5ef80c4dcd4ca8b03a0abd69559b`. Its central outcome is confirmed: both versions select the x=11 center after including both real support Stones. Adding raw Stone at `(11,66,0)` therefore causes the same Jump Boost II early return in both; AIR there allows the same timer arm in both.

## Exact old-source mask audit

For the stated query, the padded cell scan is x `[7,12)`, y `[63,67)`, z `[-2,3)`. In 1.13, the source loops X then Z. Let `xEdge=(x==7 || x==11)` and `zEdge=(z==-2 || z==2)`. Its admission clauses are:

- the outer clause `(!xEdge || !zEdge)` excludes simultaneous X/Z scan corners;
- the inner clause `(!xEdge && !zEdge || y!=66)` includes **all y cells when both X and Z are interior**, and otherwise skips only high row y=66;
- outside-border positions with computed border collision set the full-cube voxel bit; the two physical floor Stones also set full-cube bits.

The complete occupied scan rows for this fixed region are:

| Release / y | x=8 | x=9 | x=10 | x=11 |
|---|---|---|---|---|
| 1.13 / 63 | `{0}` physical Stone | `{0}` physical Stone | `{-2,-1,0,1,2}` synthetic | `{-1,0,1}` synthetic |
| 1.13 / 64 | `{}` | `{}` | `{-2,-1,0,1,2}` synthetic | `{-1,0,1}` synthetic |
| 1.13 / 65 | `{}` | `{}` | `{-2,-1,0,1,2}` synthetic | `{-1,0,1}` synthetic |
| 1.13 / 66 | `{}` | `{}` | `{-1,0,1}` synthetic | `{}` |
| 1.13.1 / 63 | `{0}` physical Stone | `{0}` physical Stone | `{-1,0,1}` synthetic | `{}` |
| 1.13.1 / 64 | `{}` | `{}` | `{-2,-1,0,1,2}` synthetic | `{-1,0,1}` synthetic |
| 1.13.1 / 65 | `{}` | `{}` | `{-2,-1,0,1,2}` synthetic | `{-1,0,1}` synthetic |
| 1.13.1 / 66 | `{}` | `{}` | `{-1,0,1}` synthetic | `{}` |

The old-source high-row cells x=10, y=66, z=-1/0/1 follow directly from both X/Z coordinates being interior, making `!xEdge && !zEdge` true. Thus the exact old-source scan contains 29 occupied cells (2 Stone + 27 synthetic) under these stated bounds. The supplied verdict's old-row table lists x=10,y=66 as empty and its old total as 26; those two details do not follow from the displayed 1.13 condition. This occupancy discrepancy does **not** alter its central outcome or first raised-ray AABB, but should be resolved before relying on its complete-mask count.

The newer guard admits positions with fewer than two scan-edge coordinates. It yields 24 occupied cells: 2 Stone + 22 synthetic. In particular, x=11/y=63 and x=11/y=66 are excluded by X+Y edge status; x=10/y=66,z=-1/0/1 are admitted as single-Y-edge cells.

## Greedy boxes and caller outcome

`VoxelSet.forEachBox` in both sources clones the occupancy, scans X then Y then Z, takes a contiguous Z run, clears it, extends X and then Y, and emits a box. The two real floor cells and adjacent synthetic cells at y=63,z=0 are in the same mask, so the first floor box is `[8,12] × [63,64] × [0,1]` in 1.13 and `[8,11] × [63,64] × [0,1]` in 1.13.1. It touches the candidate box's lower face and misses the two rays raised to y=64.51.

For both exact masks, side boxes that precede the central run either end at z=0 or begin at z=1 and miss both ray z coordinates (about 0.2 and 0.8). The first raised-ray box in **both** versions is `[10,12] × [64,66] × [0,1]`. Its center is `(11,65,0.5)`, which `new BlockPos(center)` floors to `(11,65,0)`. The first-hit maxY is 66, so initial `s=66`, and without a blocking cap sample `u=66-64=2`.

The caller difference remains real: 1.13 iterates one returned aggregate `VoxelShape`'s boxes, while 1.13.1 flattens boxes from an ordered `Stream<VoxelShape>`. Their collected masks and floor boxes differ. But for this filled-floor fixture, the first ray-hit box center and maxY agree.

- With AIR at `(11,66,0)`, both versions' t=1 center-column state read is AIR; t=2 also reads AIR at `(11,67,0)`, and the player-column t>1 clearance reads `(8,67,0)`. If the initial player head cells `(8,65,0)` and `(8,66,0)` are AIR as required, both finish with `u=2` and satisfy `0.5F<u<=2.7F`, writing `ticksToNextAutojump=1`.
- With a stored full Stone at `(11,66,0)`, both versions read the raw physical state in the direct AutoJump loop, set `s=66+1=67`, then see `67-64=3>2.7F` and return before timer assignment. Thus both suppress the timer.

The physical upper Stone remains possible outside a stationary border: 1.13 `WorldBorder.setSize` updates border values/listeners without deleting block data; `World.setBlockState`/`getBlockState` delegate to the chunk without a border gate. The collision provider separately substitutes a synthetic full cube outside the border and skips reading raw state there. The new raw Stone therefore does not perturb either aggregate mask; it matters only to the direct center-column `getBlockState` call, which is at x=11 in both versions.

## Conclusion and limits

The earlier claimed 1.13-x10 / 1.13.1-x11 physical-state split was caused by omitting both real support Stones from the shared full-cube mask trace. For the exact filled-floor witness, both use x=11 and both arm on AIR or early-return on Stone at `(11,66,0)`. Do not use the prior `sample-state-witness.md` as evidence for a cross-version outcome difference; preserve it as an historical draft but supersede its conclusion with this correction.

This is a source-derived bounded sample, not runtime parity, a fixed-input guarantee, a general aggregation equivalence, or a full pair-coverage audit. No product source, wiki inventory, backlog, Git history, build/test/runtime/game launch, or decompiler task was used or changed.
