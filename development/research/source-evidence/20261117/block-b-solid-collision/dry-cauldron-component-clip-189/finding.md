# Cauldron component clipping observability

Pair: Minecraft 1.8.9 (Ornithe Feather) to Minecraft 26.2 (unobfuscated). Scope: source-only comparison of axis clipping with the same cauldron occupied set represented as (a) five old AABBs and (b) their Boolean-union VoxelShape. This does not compare the modern vanilla cauldron's actual basin geometry, which is different.

## Evidence and identity

The shared source trees are at `decompiled_minecraft/1.8.9/ornithe-feather/` and `decompiled_minecraft/26.2/unobfuscated/`. Neither tree contains `.decompile-provenance.json`; the shared `buildSrc/README.md` says legacy trees without a manifest have unverified provenance. I therefore identify the exact requested release/mapping directories and hash each relevant source file, but cannot make a verified source-publication/decompiler-input identity claim from these trees. No decompilation was run.

1.8.9 `net/minecraft/block/CauldronBlock.java`, `addCollisions`, lines 36 onward (SHA-256 `CBC1724EA6707CE7F22981867CB62D4931240499291D18BD5E45A81D7A33836A`): adds five boxes in order: base y 0..5/16, west x 0..1/8, north z 0..1/8, east x 7/8..1, south z 7/8..1; each side box extends y 0..1.

1.8.9 `net/minecraft/util/math/Box.java`, `intersectZ`, lines 133 onward (SHA-256 `8FC7BB57B1132AFC7AE9539C30FF9C2585F0E36C2B0E59C60946CB1E3CEE9EEA`): tests strict positive overlap on the orthogonal x/y intervals, then clips a positive z delta when the moving box's `maxZ <= obstacle.minZ`. `net/minecraft/entity/Entity.java`, line 456 (SHA-256 `D4C10932CB5BB1067A5A58BE4E1BB1B42BDDE3B6FC893D88178CC07435A1696B`), applies Z clipping after Y then X.

26.2 `net/minecraft/world/phys/shapes/VoxelShape.java`, `collide`/`collideX`, lines 252 onward (SHA-256 `419A46BBCC8B48E1C075127954EB243DCC60D10D4087BF256D895CF91A2FF854`): for orthogonal ranges it uses `findIndex(min + 1e-7)` and `findIndex(max - 1e-7)`, then scans occupied voxel cells. `findIndex` is at line 159. `net/minecraft/world/phys/shapes/Shapes.java`, lines 234 onward (SHA-256 `6ED9460D58727770BDEC59C4F3184F075644F3C411BE1EEAB2EAE17E8ECE1891`), feeds the clipped distance through each shape. `net/minecraft/world/entity/Entity.java`, lines 1244 onward (SHA-256 `7AFB9C1294893FFE73E3B1ACFFCAD41C648F15DE8378BFF3DFFAFF869BB811D5`), calls the shape collision path axis-by-axis.

26.2 `AbstractCauldronBlock.SHAPE` is a subtractive basin shape (`AbstractCauldronBlock.java`, lines 28-36), not the same occupied set as the 1.8.9 five boxes; it is deliberately excluded from the numerical comparison.

## Conditional numerical discriminator

Use the old occupied set / equal-union shape at block origin. Player AABB (ordinary 0.6-wide, 1.8-high dimensions): x `[0.99999995, 1.59999995]`, y `[0.5, 2.3]`, z `[0.2, 0.8]`; request `+0.2` Z. A player centered at x=1.29999995, y=1.4, z=0.5 has these bounds. The player is on a neighboring bottom slab top at y=0.5; the cauldron south wall occupies x `[0,1]`, y `[0,1]`, z `[0.875,1]`.

Old AABB test: x overlap is 5e-8 > 0 and y overlaps, so the south wall is an obstacle. Its positive-Z gap is `0.875 - 0.8 = 0.075`; `intersectZ` returns `0.075`, changing the Z translation flag.

Equal-union 26.2 VoxelShape test: for Z clipping, the x orthogonal lower bound is `0.99999995 + 1e-7 = 1.00000005`; `findIndex` returns the X grid size, so `bMin == bMax` after clamping and no X/Y occupied cell is scanned. The shape returns the full `0.2` Z request. This is a real algorithmic discrepancy for a box already overlapping a cauldron wall by a sub-1e-7 orthogonal sliver; it is not due to different basin geometry or to a component iteration order.

## Applicability gate

This establishes a strict translation discriminator only for an already-overlapped state with orthogonal penetration below `1e-7`. At exact contact both implementations allow no overlap and the discriminator disappears; for ordinary larger positive overlap both find the wall and produce the same 0.075 clip. I have not found a source-proven lawful player trajectory that places a supported, non-riding player in this sub-epsilon embedded state. In particular, source review alone does not establish that contact rounding after prior movement can create it. Treat this as a conditional residual, not an accepted movement difference until that reachability gate is closed. A lawful witness should also establish preceding support without teleport/command or relying on an invalid embedded initial state.

## Disposition

Mathematical set-level clipping is equivalent away from VoxelShape's `1e-7` orthogonal range inset. The representational difference is observable on a strict translation flag at the sub-epsilon embedded boundary above. Applicability to reachable fixed-profile player motion remains unresolved; no implementation recommendation or runtime parity claim follows.
