# 1.13.2 east-floor aggregate audit

## Verdict

For the bounded stationary east-border AutoJump fixture with physical STONE support at `(9,63,0)` and physical AIR in every other queried world cell, the 1.13.2 aggregate collision scan still does not arm the one-Step AutoJump timer. Including the support in the same `BitSetVoxelSet` changes the greedy AABB decomposition and the first-hit sample from the synthetic-only fixture: the first emitted AABB is the floor merged horizontally with the bottom border cell, but it is below both raised rays. The first AABB that qualifies a ray is `[10,11] × [63,67] × [-1,0]`. Its center floors to `(10,65,-1)`, the sole `t=1` clearance sample is `(10,66,-1)`, and AIR there leaves `s=67`; therefore `u=67-64=3 > 1.2`, so `ticksToNextAutojump` remains zero.

This is a distinct, floor-inclusive fixture audit. It does not overturn the prior accepted synthetic-only audit, whose SHA-256 is `dc5d6d7de8e3dcb265655fe66ac9f297f7422b90c003f0dd6db58e72c442f1bf`; that artifact's first intersecting box and sample describe its own stated voxel occupancy. It does correct the author clearance addendum's floor-inclusive aggregate account: that document lists support at `(9,63,0)` but still derives a synthetic-only first box `[10,11] × [64,66] × [-2,2]` and sample `(10,66,0)`. The actual shared-set result has a first ray-qualified box reaching maxY 67 and samples `(10,66,-1)`.

## Exact source and provenance

Read-only canonical source: `decompiled_minecraft/1.13.2/ornithe-feather/`, Minecraft `1.13.2`, Ornithe Feather / Feather Gen2 build.2, decompiled with Vineflower 1.12.0. `ornithe-feather.provenance.json` records the exact 1.13.2 Feather run and mapping artifact; `ornithe-feather.verification.json` records `verified-identical-source-and-input-artifacts`, 2,711 source files verified, and no source-file differences. The published source manifest SHA-256 is `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.

Examined source file SHA-256 values:

- `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`: `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf`
- `net/minecraft/world/WorldView.java`: `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f`
- `net/minecraft/block/shape/VoxelSet.java`: `fe39ea60ac27e595280627a4d15ea93a69292da5870b5ef34a1d7d7283a267c6`
- `net/minecraft/block/shape/BitSetVoxelSet.java`: `2048daa3a95ddd567a70476862e9b2dde135b91f49b61d22a4ae3aeed98322aa`
- `net/minecraft/block/shape/IntVoxelShape.java`: `e563f5530907a5cd0ecc07bd79473d378c8c1aeeaba1dba4ac6f703de7d7b3ca`
- `net/minecraft/block/shape/VoxelShape.java`: `d95b0607647da11595e24e1e26e9615cb7da7705ea7885908544362df62275c1`
- `net/minecraft/util/math/Box.java`: `0f573d00cc10386f94cf51671c62815979973053c9e67a806e9fee98e55a4d8d`

The prior accepted audit file's actual SHA-256 was checked against its supplied identity. The author clearance addendum `source-1122-1132-autojump-border-applicability/east-fixture-clearance-addendum.md` was also checked; its actual SHA-256 is `14e5b9566c6bd0a868441830053ebe67f83ee99ef451d1956a692249b49033f6`.

## Fixture and complete voxel occupancy

Use the bounded values recorded in the clearance: stationary border centered `(0,0)`, size 20 (`maxX=10`); player feet `(9.7,64,0)`, width `0.6`, height `1.8`, grounded, not sneaking/riding, AutoJump enabled, timer zero, zero actual horizontal delta, forward input east, yaw `-90°`, speed `0.1`, and no jump boost. The fallback points east. The query box is approximately x `[9.1,10.998775]`, y `[64,65.8]`, z `[-0.600000024,0.600000024]`; `WorldView.getBlockCollisions` computes scan indices `i=8,j=12,k=63,l=67,m=-2,n=2`, covering x=8..11, y=63..66, z=-2..1. The border gate is enabled for the stated inside flag and east-side query.

The finite cells admitted into the one `BitSetVoxelSet` are exactly:

| World x | Occupied `(y,z)` cells |
|---|---|
| 9 | `(63,0)` — physical Stone support |
| 10 | `(63,-1..0)`, `(64,-2..1)`, `(65,-2..1)`, `(66,-1..0)` — synthetic border full cells |
| 11 | `(64,-1..0)`, `(65,-1..0)` — synthetic border full cells |

The boundary predicate admits cells with at most one scan-edge flag. That is why x=10 at y=63/66 admits z=-1/0 but excludes z=-2/1; x=11 admits only its y/z interior cells. `BitSetVoxelSet.set(..., true, true)` marks both the actual full Stone and each synthetic full cell in that same occupancy set.

`VoxelSet.forEachBox` scans local x, then y, then contiguous z runs, clearing each emitted run before extending it. It first emits the support plus adjacent bottom border cell as `[9,11] × [63,64] × [0,1]`. This does not intersect either ray, whose y is `64.51`. It next emits the x=10, z=-1 column as `[10,11] × [63,67] × [-1,0]`; the missing x=11,y=63,z=-1 cell prevents expansion across x, while the x=10 column remains contiguous through y=66. This is the first emitted box that intersects the ray at z≈-0.3. The other ray at z≈+0.3 does not intersect this box; later boxes are irrelevant because AutoJump breaks on the first qualifying AABB.

For that first hit, `Box.center()` is `(10.5,65,-0.5)` and `BlockPos(Vec3d)` floors it to `(10,65,-1)`. With the stated AIR query/world cells, the single loop iteration `t=1` finds no nonempty collision shape at `(10,66,-1)`. The box maximum remains `s=67`; `u=(float)(s-getShape().minY)=3.0F`, which is above the no-jump cap `o=1.2F`, so the timer assignment gate is false.

## Source anchors

- `LocalClientPlayerEntity.java` lines 871–875 call AutoJump after movement; lines 882–944 form the direction, query and first-ray collision; lines 941–948 sample the center and clearance; lines 969–971 gate timer assignment.
- `WorldView.java` lines 112–151 computes the finite query scan, edge filter, synthetic full cells, and final `IntVoxelShape`; lines 170–177 select border handling from the player flag.
- `BitSetVoxelSet.java` lines 55–65 set the occupancy bit and update bounds for the supplied booleans.
- `VoxelSet.java` lines 200–249 define the greedy run, extension and emitted-box order; `IntVoxelShape.java` offsets voxel coordinates; `VoxelShape.java` lines 94–112 expose `getBoundingBoxes()` through that merger; `Box.java` lines 196–205 implement strict segment intersection and lines 335–337 compute the center.

This is exact-source reasoning for one bounded fixture. It makes no implementation, full-pair, runtime-parity, or broader applicability claim.
