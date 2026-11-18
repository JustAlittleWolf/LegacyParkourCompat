# Independent source B verdict: corrected cardinal mask report r2

## Verdict and binding

The corrected report is bound to `source-1131-1132-cardinal-shared-border-masks/report-r2.md`, SHA-256 `b3b989c98259d59516b297a0d76152117435fc601a9b08de0f1fc1a2d84686ea`; the file hash matches. I independently retraced the four masks and greedy outputs through the exact source. The occupancy rows, complete ordered `VoxelSet.forEachBox` outputs, and first ray hits in r2 all match. I also confirm the center-zero border applicability, movement/support geometry, direct headroom coordinates, and numeric rise gate below. The resulting geometric eligibility is the same in 1.13.1 and 1.13.2; it is not a pairwise movement change.

The `ticksToNextAutojump=1` conclusion is conditional on the AutoJump entry gates. The r2 fixture gives the supported path and movement but does not explicitly bind `isAutoJumpEnabled`, cooldown `<=0`, `!isSneaking`, `!isRiding`, nonzero input, or the view/movement dot gate `m >= -0.15F`. The geometric and cap results are confirmed; a timer-arm statement requires those entry conditions as well as all named physical-AIR clearances.

## Source and fixture audit

Exact sources are 1.13.1 and 1.13.2 Ornithe Feather Gen2 build 2. I rechecked the report's source-manifest hashes and the exact relevant source-file hashes. `LocalClientPlayerEntity`, `WorldView`, `WorldBorder`, `WorldAccess`, `BlockPos`, `Box`, `VoxelSet`, `BitSetVoxelSet`, `VoxelShape`, and `IntVoxelShape` match across the pair. Whole `World.java` hashes differ, while the forwarding method used by this query at line 1755 is text-identical. The reviewed methods are `LocalClientPlayerEntity.move`/`autoJump` (871/882), `Entity.move` (580 onward), `WorldView.getBlockCollisions` (112) and `getCollisions` (166–187), `WorldBorder.contains(BlockPos)` (22), `Box.intersects(Vec3d, Vec3d)` (196), and `VoxelSet.forEachBox` (200). These procedures are byte-identical where relevant.

For the centered border with bounds X/Z `[-10,10]`, `collidesWithBorder` uses strict entity-center bounds `(-11,11)` when the player is not outside. Each center `±8.99` passes. Each AutoJump query's padded scan bounds cross one border face, so the global `bl` “scan wholly inside border” condition is false; with `checkWorldBorder=true`, cells failing `WorldBorder.contains(BlockPos)` are synthesized as full cubes. BlockPos cells `+10` and `-11` are outside this border; `+9` and `-10` are inside it. This matches all four occupancy masks below.

The actual movement phase has post-move body boxes, displayed to 0.01 using width/height 0.6/1.8: E `[8.69,9.29]×[64,65.8]×[.2,.8]`; W `[-9.29,-8.69]×[64,65.8]×[.2,.8]`; N `[.2,.8]×[64,65.8]×[-9.29,-8.69]`; S `[.2,.8]×[64,65.8]×[8.69,9.29]`. The pre-move normal intervals are respectively `[8.59,9.19]`, `[-9.19,-8.59]`, `[-9.19,-8.59]`, and `[8.59,9.19]`. Each lies over the two supplied full Stone supports, whose individual physical AABBs are:

- E: `[8,9]×[63,64]×[0,1]` and `[9,10]×[63,64]×[0,1]`.
- W: `[-10,-9]×[63,64]×[0,1]` and `[-9,-8]×[63,64]×[0,1]`.
- N: `[0,1]×[63,64]×[-10,-9]` and `[0,1]×[63,64]×[-9,-8]`.
- S: `[0,1]×[63,64]×[8,9]` and `[0,1]×[63,64]×[9,10]`.

The movement collision query expands the swept body shell by `1.0E-7`; this allows the support tops at Y=64 into its collision collection. Their full physical boxes still end at Y=64, while all raised AutoJump rays are at Y=64.51, so no Stone support box intersects a raised ray. The AutoJump phase uses its separate wide box grown horizontally by player width; its exact rounded query/scan bounds are in the case table. Its aggregate mask combines physical support cells and synthetic border cells without changing raw block states.

## Per-case independent recomputation

The supplied centers are preceded by a signed `0.10` move from normal coordinate `±8.89`. With `f=0.14F`, the source computes float-cast `g` from the delta, `l=(float)fastInvSqrt(g)`, then `p=max(f*7,1/l)=0.98` at the reported precision. `vec3d7` is `current + delta + normalizedDirection*p`; the two ray lanes are offset by `direction.cross(up) * (width/2)`. The source scans integer cells from `floor(query.min)-1` to `ceil(query.max)+1` exclusive, rejects only candidates on at least two scan bounds, collects full physical and synthetic cells in the same bitset, then greedily scans X, Y, Z; it grows a Z run across X and then Y. I reconstructed every occupied row and emitted box directly from those rules.

| Side | Occupied rows in scan (only occupied world cells listed) | All aggregate boxes, source order | Ray order; first hit and floored center |
|---|---|---|---|
| E (+X) | y63: x8 `{0}`, x9 `{0}` Stone; x10 `{-1,0,1}` border. y64–65: x10 `{-2,-1,0,1,2}`, x11 `{-1,0,1}` border. y66: x10 `{-1,0,1}` border. | 1 `[8,11]×[63,64]×[0,1]`; 2 `[10,11]×[63,67]×[-1,0]`; 3 `[10,11]×[63,67]×[1,2]`; 4 `[10,11]×[64,66]×[-2,-1]`; 5 `[10,12]×[64,66]×[0,1]`; 6 `[10,11]×[64,66]×[2,3]`; 7 `[10,11]×[66,67]×[0,1]`; 8 `[11,12]×[64,66]×[-1,0]`; 9 `[11,12]×[64,66]×[1,2]`. | z `.2`, `.8`; both hit box 5; center `(11,65,.5)` → `(11,65,0)`. |
| W (−X) | y63: x−11 `{-1,0,1}` border; x−10 `{0}`, x−9 `{0}` Stone. y64–65: x−12 `{-1,0,1}`, x−11 `{-2,-1,0,1,2}` border. y66: x−11 `{-1,0,1}` border. | 1 `[-12,-10]×[64,66]×[-1,2]`; 2 `[-11,-10]×[63,64]×[-1,2]`; 3 `[-11,-10]×[64,66]×[-2,-1]`; 4 `[-11,-10]×[64,66]×[2,3]`; 5 `[-11,-10]×[66,67]×[-1,2]`; 6 `[-10,-8]×[63,64]×[0,1]`. | z `.8`, `.2`; both hit box 1; center `(-11,65,.5)` → `(-11,65,0)`. |
| N (−Z) | y63: x−1 `{-11}` border; x0 `{-11,-10,-9}` (border at −11; Stone at −10/−9); x1 `{-11}` border. y64–65: x−2 `{-11}`, x−1/0/1 `{-12,-11}`, x2 `{-11}` border. y66: x−1/0/1 `{-11}` border. | 1 `[-2,3]×[64,66]×[-11,-10]`; 2 `[-1,2]×[63,64]×[-11,-10]`; 3 `[-1,2]×[64,66]×[-12,-11]`; 4 `[-1,2]×[66,67]×[-11,-10]`; 5 `[0,1]×[63,64]×[-10,-8]`. | x `.2`, `.8`; both hit box 1; center `(.5,65,-10.5)` → `(0,65,-11)`. |
| S (+Z) | y63: x−1 `{10}` border; x0 `{8,9,10}` (Stone at 8/9; border at 10); x1 `{10}` border. y64–65: x−2 `{10}`, x−1/0/1 `{10,11}`, x2 `{10}` border. y66: x−1/0/1 `{10}` border. | 1 `[-2,3]×[64,66]×[10,11]`; 2 `[-1,2]×[63,64]×[10,11]`; 3 `[-1,2]×[64,66]×[11,12]`; 4 `[-1,2]×[66,67]×[10,11]`; 5 `[0,1]×[63,64]×[8,10]`. | x `.8`, `.2`; both hit box 1; center `(.5,65,10.5)` → `(0,65,10)`. |

Rounded AutoJump query bounds and scan ranges match r2: E X `[8.39,10.67]`, Y `[64,65.8]`, Z `[-.1,1.1]`, scan `[7,12)×[63,67)×[-2,3)`; W X `[-10.67,-8.39]`, same Y/Z, scan `[-12,-7)×[63,67)×[-2,3)`; N X `[-.1,1.1]`, Y `[64,65.8]`, Z `[-10.67,-8.39]`, scan `[-2,3)×[63,67)×[-12,-7)`; S X `[-.1,1.1]`, same Y, Z `[8.39,10.67]`, scan `[-2,3)×[63,67)×[7,12)`. Strict segment/AABB tests pass for both listed rays per case; each segment extends to `10.07` or `-10.07`, crossing the synthetic side face by `0.07`.

## Raw headroom and strict rise gate

`autoJump` first floors `BlockPos(this.x, this.getShape().maxY, this.z)` and directly checks collision shapes at that block and one above. The physical-AIR cells read at Y=65 and 66 are E `(8,65,0),(8,66,0)`; W `(-9,65,0),(-9,66,0)`; N `(0,65,-9),(0,66,-9)`; S `(0,65,8),(0,66,8)`. These reads are not substituted by synthetic border cells.

Every first hit has `maxY=66`; hence `s=66`, feet Y=64, and rise `u=2`. With no effect, cap `o=1.2F`; Jump Boost I (amplifier 0) gives `1.95F`. Both only reach loop `t=1`, read hit-center Y=66, and fail the strict final test `u>0.5F && u<=o`. Jump Boost II (amplifier 1) gives `2.7F`; it reads hit-center Y=66 and 67, and at `t=2` reads player-column Y=66. Jump Boost III (amplifier 2) gives `3.45F`; it also reads hit-center Y=68 and player-column Y=67. All required cells are physical AIR by fixture. Therefore JBII/JBIII pass the geometric cap and clearance gates; JBII is sufficient. For E/W/N/S, the hit-center columns are `(11,65,0)`, `(-11,65,0)`, `(0,65,-11)`, `(0,65,10)`; the cap-sample Y rows are 66–67 for JBII and 66–68 for JBIII.

This review confirms the corrected source-only geometry with these fixed inputs and listed AIR conditions. It does not prove that unspecified AutoJump entry gates are satisfied, nor does it establish runtime parity or an exact patch-introduction boundary. No product implementation, wiki inventory, backlog, or Git history was read or changed; no product or research source files were modified. No build, tests, runtime launch, or decompilation was performed.

