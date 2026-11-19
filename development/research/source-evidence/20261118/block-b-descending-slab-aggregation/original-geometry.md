# 1.13 slab aggregate: airborne negative-Y primary query

## Result

For the existing two-bottom-slab witness, adding an ordinary airborne negative-Y movement request of `-0.003` does **not** mask the primary horizontal collector difference. With `onGround=false`, `z=0`, and the unchanged box/request setup, the Y clip remains `-0.003`; Y resolves before X, but the slabs are outside the Y clip's orthogonal X-index range. The subsequent X clip therefore retains the finding's distinct result: 1.13 returns `4.9999999918171056e-8`, while 1.13.1/1.13.2 return zero through the ordered-shape epsilon gate.

The same call also cannot enter step-up: in 1.13/1.13.1 `bl = onGround || o != y && o < 0.0`; here `onGround` is false and `o == y == -0.003`. Even though X clips, `bl` is false. No second step-up collection masks or replaces the primary result.

This is a narrow source derivation for the already recorded witness and its same lateral request. It is not a new shape family, broad movement finding, runtime result, or general reachability claim.

## Producer and exact primary gate

In 1.13 `LivingEntity.java:1600-1634`, the ordinary dry non-flying branch updates horizontal velocity and calls `move(MoverType.SELF, velocityX, velocityY, velocityZ)` before friction and gravity. In the airborne, non-sprinting, non-no-gravity subcase, its post-move gravity correction can set `velocityY = -0.003` (`:1627`); that stored velocity supplies the negative-Y component to a subsequent ordinary `SELF` travel call. `LivingEntity.java` has the identical SHA-256 in 1.13.1 and 1.13.2. The source path supports the small negative-Y input without changing the requested primary query after `Entity.move`'s normal gates.

For this witness the entry conditions remain: ordinary `SELF` movement; `noClip=false`; no cobweb or piston adjustment; airborne and not sneaking; dry/non-flying travel; no entities or border shapes; slabs at `(2,64,0)` and `(3,64,0)` with every other scanned cell air; actor box `minY=64.1`, `maxY=65.9`, `maxX=1.99999995` and the prior witness's unchanged other coordinates; movement `(2,-0.003,0)`. The established start-shape exclusion is preserved: each slab is outside the starting box in X. The negative-Y sign makes `WorldView.getCollisions` shift the exclusion shape by `+1.0E-7` in Y; it does not alter the existing X exclusion. The expanded sweep still contains both slab shapes.

`Entity.move` calls the primary collector once for nonzero adjusted movement and clips Y, then X, then Z (`1.13 Entity.java:579-598`; `1.13.1/.2 Entity.java:579-600`). For Y clipping, the shape routine's orthogonal X search ends at `box.maxX - 1.0E-7 = 1.99999985`, below the first slab's X start at `2`. Neither slab can affect Y, so Y remains `-0.003`. For X clipping, the Y box is consequently unchanged and overlaps the slabs vertically. The first slab's positive X face gap is `2 - 1.99999995 = 4.9999999918171056e-8` in the source's double arithmetic.

1.13 clips against its one already-aggregated shape once, so this sub-`1.0E-7` positive offset remains the X result. 1.13.1 and 1.13.2 stream individual partial shapes in the collector's X-fast traversal; the slab at X=2 precedes the slab at X=3. `VoxelShapes.calculateMaxOffset` checks `Math.abs(maxDist) < 1.0E-7` before consuming each next shape. After the first slab reduces the positive X distance to `4.9999999918171056e-8`, that strict check returns zero before the second slab can be processed. Thus the negative Y component preserves, rather than hides, the existing primary horizontal difference.

The step-up predicate is evaluated after all three primary clips. Since `o` records the requested negative Y and the clipped `y` is identical, `o != y && o < 0.0` is false. The airborne `onGround` term is also false. Therefore `bl` is false despite X clipping (`n != x`), and the later step-up block and its second collector query are not reached.

## Exact sources and source identity

All are canonical read-only sources under `decompiled_minecraft/<version>/ornithe-feather`; ready markers identify Feather Gen2 `1.13+build.2`, `1.13.1+build.2`, and `1.13.2+build.2`.

- 1.13 `net/minecraft/entity/living/LivingEntity.java:1600-1634`: ordinary dry movement producer and stored `-0.003` correction.
- 1.13 `net/minecraft/entity/Entity.java:579-602`: primary Y/X/Z clip order and step-up gate.
- 1.13 `net/minecraft/world/WorldView.java:187-219, 223-247`: primary swept query, signed shifted-start exclusion, old aggregate.
- 1.13.1/1.13.2 `net/minecraft/world/WorldView.java:112-153`: partial stream order, start exclusion and full-cell tail.
- 1.13.1/1.13.2 `net/minecraft/entity/Entity.java:579-602`: same primary clip order and step-up gate.
- 1.13.1/1.13.2 `net/minecraft/block/shape/VoxelShapes.java:194-205`: per-next-shape strict epsilon gate.
- 1.13 `net/minecraft/block/shape/VoxelShapes.java:207-266`; 1.13.1/1.13.2 `net/minecraft/block/shape/VoxelShape.java:184-231`: source axis clipping and orthogonal index bounds.

The release paths and selected Java hashes were verified against each ready source manifest. Key hash identities: 1.13 `Entity.java` `09514488d9055449c47e2e4bcd23b5773203ca634b7ecba2323a99d0ae016642`; 1.13 `WorldView.java` `b0d1406fb04eacbc644a78548f858d5ff6c14ad781927832cb374e985f32a543`; 1.13 `LivingEntity.java` `c38e95c2c13e088b03d77fc2c6b98d4edd7e6ca5aef12b187c8e3f3ea782e864`; 1.13.1 `Entity.java` `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`; 1.13.1 `WorldView.java` `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f`; 1.13.1 `VoxelShapes.java` `313968d4e5855b5ec380272b6ababcc5849d478ab771926355912a8cb6aa9c95`. The corresponding 1.13.2 `Entity.java`, `WorldView.java`, and `VoxelShapes.java` hashes are identical to 1.13.1; the 1.13.1 and 1.13.2 `LivingEntity.java` hashes are both `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`.

## Reused evidence boundary

Reuses the canonical witness and slab-state identities in `development/research/findings/1.13--1.13.1/movement-collision-shape-aggregation.md`, plus the exact query/start-exclusion contract in `development/research/source-evidence/20261029/slab-collision/query-adapter-gates.md`, shape conversion gate in `query-shape-conversion.md`, and step-up gate in `stepup-gate.md`. Does not change their claims or prove additional block/provider behavior.

