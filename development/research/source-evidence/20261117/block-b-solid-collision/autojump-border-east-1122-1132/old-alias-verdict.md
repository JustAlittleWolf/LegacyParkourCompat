# Independent old-alias corroboration: AutoJump border cells

## Verdict

The 1.12 / 1.12.1 / 1.12.2 source bodies for `LocalClientPlayerEntity.autoJump` are byte-identical. Their world-border collision collector is also byte-identical and uses a per-cell `List<Box>` path: when a scanned block position is outside `WorldBorder.contains` while the entity is in the border shell, `World.getCollisions` substitutes `Blocks.STONE.defaultState()`, then `Block.addCollisions` moves that state's collision shape to that block position and appends the intersecting box. This supports the old-side “distinct cell boxes” portion of the stated per-cell-versus-merged-shape contrast. This bounded review does not independently audit the later shape-merging side.

For the stated 1.12.2 witness (stationary center x=9.7,z=0; east border maxX=10; width 0.6; feet y=64; height 1.8; yaw -90 degrees; forward input; no jump boost), the candidate at y=64 has `maxY=65`. One correction is required to the supplied independent verdict: with z center 0, the negative-side ray is at z=-0.3, and the collector loops z cells in ascending order. The first intersecting synthetic border cube is therefore `(10,64,-1)`, whose center floors to `(10,64,-1)` and whose sole above-sample is `(10,65,-1)`. Emptiness at `(10,65,0)` alone does not establish the timer outcome. If the raw state at `(10,65,-1)` has no collision shape, then `s` stays 65, `u=(float)(65-64)=1`, and the timer assignment passes `u>0.5F && u<=1.2F`. The bounded draft should add this actual sample to its witness conditions.

## Trace from the old aliases

- `Input.getMovement()` returns `(movementSideways, movementForward)`. With forward 1, sideways 0, and negligible callback displacement, AutoJump's fallback computes an eastward vector at yaw -90 degrees. `Vec3d.fromRotation` gives the same eastward heading, so the dot product gate `m < -0.15F` does not reject it.
- With speed 0.1 and the fallback direction normalized, `p = max(0.1F * 7.0F, 1.0F / l) = 0.7F`. The query box starts at `(9.7,64,0)`, extends to x=10.4, then grows by the full width 0.6: x `[9.1,11.0]`, y `[64,65.8]`, z `[-0.6,0.6]`. The two tested horizontal segments are raised by `0.51F`; their z coordinates are -0.3 and +0.3.
- For border maxX=10 and `outsideWorldBorder=false`, `isWithinBorder` expands the entity's accepted bound to maxX+1, so x=9.7 is inside (`bl2=true`). The stored flag is false (`bl=false`), so `bl == bl2` is false and this collector call does not toggle the flag. At block x=10, `WorldBorder.contains(BlockPos)` is false because it requires `pos.getX() < maxX`; while `bl2` is true, the collector substitutes stone for that block state.
- Collector bounds are x `[8,12)`, y `[63,67)`, z `[-2,2)`, from `floor(min)-1` and `ceil(max)+1`. It visits x, then z, then y. The z=-2 cell does not intersect the query; at x=10,z=-1,y=64 the stone cube intersects the negative-side raised ray first. The cube is `[10,11] × [64,65] × [-1,0]`; its center is `(10.5,64.5,-0.5)`, and `BlockPos(Vec3d)` floors the coordinates.
- With no jump boost, `o=1.2F`; `for (t=1; t<o; t++)` runs exactly once. It reads the actual world state at `(10,65,-1)`, not a synthetic border state. If that state has no collision shape, `s` stays at the cube's `maxY` 65. The timer condition accepts `u=1` and assigns `ticksToNextAutojump=1`, subject to the method's entry gates (enabled, timer <=0, grounded, not sneaking/riding, nonzero input).

## Exact source identities and selected hashes

Canonical mapping: Ornithe Feather / Feather Gen2 build.2 throughout. For 1.12 and 1.12.1, selected current source-file hashes match the leaf `.decompile-provenance.json` hashes. The hidden cache pins for each raw client, version metadata and Feather metadata match the local artifact hashes. The exact Feather mergedv2 JAR is present and its adjacent pin matches both its bytes and the provenance input SHA. The associated `.tiny` is present without an adjacent pin; no claim is made that every cache item or whole source root was audited.

- 1.12: raw client `59e84cf6acc3912e1705ab05c722485afb3ec33343eef4756d8c16edfe6411ca`; Feather build.2 JAR `73ce7d6d0a281db15fa030ae09e836387dae1172fdffcaf3e2d1a4840830b719`; `LocalClientPlayerEntity.java` `d7237b8b9a68525bbfec6af1c234d2aafb1e4aa7d52059331d1d243313d6d7d1`; `World.java` `f7ab7a34d456e720e7ee4f34fbd0eb10f7f96151596c8c615ab2edef05a884d8`.
- 1.12.1: raw client `19331fb29bf8b8d5ed764677c94cca9d66481e10104f660eb555894a4300cf22`; Feather build.2 JAR `f94febd2167d6091b4c157aef0a85c8791e6f8fb27b883e27a82a68c7b02b9c7`; `LocalClientPlayerEntity.java` `d7237b8b9a68525bbfec6af1c234d2aafb1e4aa7d52059331d1d243313d6d7d1`; `World.java` `d182122a9b052f57803672c21b0941cb7e7d41ed756cd0a4ea8c0858684c8259`.
- 1.12.2: ready-root marker identifies client `8ada07da5ee77dad3527bd7278fbd05ee1fc8a597813b216a871a2d7d64cc64f` and source-manifest SHA `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; current `LocalClientPlayerEntity.java` and `World.java` hashes match its manifest (`01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc` and `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594`). The root ready/verification sidecars report all 2,050 source files equal and no raw-input artifact differences; the sole reported artifact difference is a regenerated derived remapped jar, with its old/new hashes recorded in the verification sidecar. The actual source- and artifact-manifest hashes match their ready marker. The artifact manifest records client SHA `8ada07da5ee77dad3527bd7278fbd05ee1fc8a597813b216a871a2d7d64cc64f`, Feather mergedv2 JAR SHA `e48244030c53979793bdfbe48d7f1f3536f7e4f678ee5890a416198037cd46ca`, and Tiny SHA `a3aa1c8e73e81bd09432ba1f4b2e88aaacbedb2d8fa3cbf797536d2bdf0d4e58`.

Extracted method-body SHA-256 is identical in all three aliases for each consumed operation:

| Operation | SHA-256 |
|---|---|
| `LocalClientPlayerEntity.autoJump(float,float)` | `5b124378adc6f3e0116153987d752d813a48d5c4916d07731993d10e537b4aa3` |
| `World.getCollisions(Entity,Box,boolean,List)` | `dfdec2ce08f74e710a1004a9edd4690965c092cc46a400655244417a4230917e` |
| `World.getCollisions(Entity,Box)` | `227b1be34ca8602bdbf15283fdd5b212d004409e41524d5ae2c0a838d0b3b9a5` |
| `World.isWithinBorder(Entity)` | `00835e8ad15c08c985436982212677d7a69cc93da9c8c0257948bc59693f4578` |
| `WorldBorder.contains(BlockPos)` | `116f2f8ce25105f2287218e7d44907c985fdc310d72072b366c1733c3d552cc2` |
| `Block.addCollisions(...)` | `c16478ff2b1b76e9dc3ba001abf5282998538c0bc00d9540aed37c91954a8264` |
| Input, heading, lateral-ray, box-center/intersection and floor-conversion helpers | `Input.getMovement`: `e95b842f42559fd6fa0fb6a20a28984a9a1958d6bd5297703750be57155562a5`; `Vec3d.fromRotation`: `abb723618c1fbd2387ee7bc3541e5793d78ba63d3659eefb5c3d65a8b397707c`; `Vec3d.cross`: `91c5a8cb44818a44cc552b818c63562846d8b47a0801e81db22dac68a2e0e017`; `Box.intersects(Vec3d,Vec3d)`: `3c8dc0d0b474f905717fcc9b5ab794d66866c6e30b3e5440ca5cc5491b43b68b`; `Box.center`: `61623d1d5345b53b1d6dbc9e0510a57487c5b33c7605fb69ce60dfdba071d185`; `Vec3i(double,double,double)`: `49a5543cffc6a0f3c0792d7ab98889f73a1d2f56fac8b59364897f4342ec59d9` |

## Source anchors

All anchors exist with the same lines in 1.12 and 1.12.1, except AutoJump begins at 852 rather than 857 in 1.12.2:

- `LocalClientPlayerEntity.java`: `autoJump` starts at 852 in 1.12 / 1.12.1 and 857 in 1.12.2; timer assignment at 943 / 948, respectively; query construction, raised rays and collision-list call at 896–910 in 1.12.2; clearance state read at 924–926.
- `World.java`: collector bounds/scan and synthetic-stone selection 964–1008; entity list call 1017; border margin predicate 1042–1058.
- `WorldBorder.java`: integer-cell `contains(BlockPos)` 24–26.
- `Block.java`: per-cell collision append starts at 372.
- `Input.java`: movement vector fields and getter 5–19; `Vec3d.java`: `cross` 46 and `fromRotation` 188 onward; `Box.java`: segment-box intersection 253 and center 374; `BlockPos.java` 38 and `Vec3i.java` 19: vector coordinate flooring.

This is bounded source corroboration only. It does not establish full-pair coverage, exact patch introduction, runtime parity, or implementation status.
