# F-VOXEL-OFFSET-EPSILON — repeated voxel offsets stop below 1e-7

- Older version A: 1.12.2
- Newer version B: 1.13.2
- Mechanic / coverage slice IDs: ENTITY-MOVE-AXES, WORLD-COLLISIONS, PLAYER-CORRECTION, VELOCITY-PACKETS, BLOCK-REGISTRATIONS, SHAPE-PROVIDERS.
- Classification: changed behavior
- Confidence: source-confirmed for the explicit paired geometry and movement preconditions below; independent blind finding review is pending.
- Applicability: historical player behavior
- First changed release: unknown within (1.12.2, 1.13.2]
- Runtime validation: not performed

## Paired evidence

### A artifact identity

- Evidence artifact record ID: `EA-FEATHER-R1-A`
- Publication status: revised-derived
- Revision ID: `feather-r1-2026-10-07`
- Immutable evidence path: `revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.12.2/ornithe-feather/client-ornithe-feather.jar`
- Evidence artifact SHA-256: `fcc17537a14a423e2086f600047725ec1fcfd4c7fcf5c0d1a5bda491966c1b87`
- Evidence manifest path / SHA-256: `revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.12.2/ornithe-feather/artifact.sha256` / `162170b94be0fa6393a515ea3beed10210b2c86a006366cf9d351eba136bae2d`
- Original artifact-manifest path / SHA-256: `ready/1.12.2/artifacts.sha256` / `8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c`
- Original derived-artifact availability and expected hash: unavailable; expected SHA-256 `65a08f15d18c4ec2bd0f05b89dfc1ba7ea6b8280ba93ed136245f39e63ec8a2b`
- Source/raw-input hash relation and verification reference: cited files match `ready/1.12.2/ornithe-feather.sources.sha256` (`b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`); complete source tree and original raw inputs were independently rehashed as recorded in `run.md`.
- Revised-to-original derived-artifact equivalence and evidence reference: unverified; original mapped JAR is unavailable.
- Provenance limitations: revised immutable source/artifact publication was verified; do not claim original mapped-bytecode identity or metadata-only differences.
- Relative source evidence: `ready/1.12.2/ornithe-feather/net/minecraft/util/math/Box.java::intersectX(Box,D)`, lines 185-203, SHA-256 `f788b8146b14f299cc8b58ea0854609f845c298a963295d503de2e30e15d66f3a`; `World.java::getCollisions(Entity,Box)`, lines 964-1040, SHA-256 `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594`; `Block.java::addCollisions/getShape/getCollisionShape`, lines 372-389, and Stone registration line 748, SHA-256 `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1`; `StoneBlock.java`, SHA-256 `49358cc69e314af3952211266696dd45b48a98eb37dacf93e32c59fdff758d22`; `Entity.java::move(MoverType,DDD)`, lines 567-599, SHA-256 `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a`; `ClientPlayNetworkHandler.handlePlayerMove`, lines 578-620, SHA-256 `fce21d9902e555fd46545fb04bdb6c4a912eef6398790776ad1a122e09960c7c`; `LivingEntity.travel`, line 1574, SHA-256 `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6`.

### B artifact identity

- Evidence artifact record ID: `EA-FEATHER-R1-B`
- Publication status: revised-derived
- Revision ID: `feather-r1-2026-10-07`
- Immutable evidence path: `revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.13.2/ornithe-feather/client-ornithe-feather.jar`
- Evidence artifact SHA-256: `b28c33e023928045c8fd7ed7727a860a6820241e371ddb16108366c6192b718c`
- Evidence manifest path / SHA-256: `revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.13.2/ornithe-feather/artifact.sha256` / `4fa7f17f639f7cff0ff9db327b3c978b049cee0c96a6b40625601868692c7dd8`
- Original artifact-manifest path / SHA-256: `ready/1.13.2/artifacts.sha256` / `fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e`
- Original derived-artifact availability and expected hash: unavailable; expected SHA-256 `d118ff5b9eb93fed06680466cb26c41a30647cdb2fc8744638cf9b35cf7864e5`
- Source/raw-input hash relation and verification reference: cited files match `ready/1.13.2/ornithe-feather.sources.sha256` (`2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`); complete source tree and original raw inputs were independently rehashed as recorded in `run.md`.
- Revised-to-original derived-artifact equivalence and evidence reference: unverified; original mapped JAR is unavailable.
- Provenance limitations: revised immutable source/artifact publication was verified; do not claim original mapped-bytecode identity or metadata-only differences.
- Relative source evidence: `ready/1.13.2/ornithe-feather/net/minecraft/block/shape/VoxelShapes.java::calculateMaxOffset`, lines 194-206, SHA-256 `313968d4e5855b5ec380272b6ababcc5849d478ab771926355912a8cb6aa9c95`; `VoxelShape.java::calculateMaxDistance`, lines 184-238, SHA-256 `d95b0607647da11595e24e1e26e9615cb7da7705ea7885908544362df62275c1`; `BlockPos.java::iterateRegionMutable`, lines 212-240, SHA-256 `ee71cfdedcaa27458f48168ab761b435cf9c878b04f21e971ddbafb6254aa3fd`; `C_70593968.java`, lines 11-40, SHA-256 `6efb9fbc71bca9d0c49a8ed265721874a264fb51233c8ba5f547bb0e5e98d974`; `Block.java::getShape/getCollisionShape`, lines 388-395, Stone registration line 778 and default `Properties.hasCollision` line 1901, SHA-256 `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4`; `StoneBlock.java`, SHA-256 `4a85d515f1195600042d7e67de00767c77c91c2e74aa00ac7796ceb7ea78c906`; `WorldView.java` collision stream, lines 112-152, SHA-256 `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f`; `Entity.java::move(MoverType,DDD)`, lines 578-599, SHA-256 `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`; `ClientPlayNetworkHandler.handlePlayerMove`, lines 621-663, SHA-256 `4f65502fbee6476cef0838563f03a66f07a1a9ed8c63e580f5338ba5fe274f11`; `LivingEntity.travel`, line 1640, SHA-256 `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`.

## Source-level difference

- In A, `Box.intersectX` clips a positive requested displacement to the nearest gap with no small-distance cutoff. With a positive gap below `1.0E-7`, a farther collision box leaves that clipped gap unchanged.
- In B, `VoxelShapes.calculateMaxOffset` iterates the candidate shapes. It returns `0.0` at the start of an iteration whenever `Math.abs(maxDist) < 1.0E-7`. `VoxelShape.calculateMaxDistance` can first reduce a larger displacement to a positive gap below that threshold; processing a later shape then replaces that gap with zero.
- Concrete paired preconditions: the player is airborne, has no sneak-edge probe or step candidate, moves in +X with `dx = 2.0`, and has no other nearby entity or collision shape. Its X max is less than `1.0E-7` from the next block face, and two collision full cubes lie in the swept volume along the same Y/Z interval in a loaded, in-border area. Use corresponding Stone blocks at (2,64,0) and (3,64,0), with player `box.maxX` about `1.99999995`, `box.minY` above 64, and Z bounds inside (0,1). A and B Stone inherit equal unit-cube collision shapes from their base Block implementations. Both collectors include both blocks. A visits increasing X candidates and keeps the small positive gap after the farther block; B's X-fastest region iterator returns the nearer Stone before the farther one, and its stream cache retains that order. The first B shape clips to the gap; the second shape triggers the `1.0E-7` early return. The paired player-correction handler accepts absolute packet position coordinates, and the paired velocity handler writes packet velocity divided by 8000, so this precise position and a 2.0 displacement are representable through existing player movement inputs.

## Reachability and dependencies

- Both `LivingEntity.travel` bodies pass the player's velocity to `Entity.move(MoverType.SELF, ...)`; the local client player reaches `LivingEntity.travel` through its movement tick. The existing player-correction handler writes packet coordinates to the player, and the existing velocity-packet handler can supply nonzero motion; those bounded A/B paths are recorded in `run.md` as `PLAYER-CORRECTION` and `VELOCITY-PACKETS`.
- A Stone uses the base A `Block.getShape` full-cube Box and base `getCollisionShape`/`addCollisions` path. B Stone inherits `Block.getShape` -> `VoxelShapes.block()` and `getCollisionShape`; block properties default `hasCollision` to true. These blocks and shapes existed in A, so the example is not a modern-only block disposition.
- The source-derived consequence is a player position difference after X clipping under the stated starting geometry, velocity and two-block arrangement. This is not a measured trajectory. The scenario depends on precise coordinates and a sufficiently large incoming displacement; no claim is made that ordinary keyboard input commonly creates it.

## Consequence and uncertainty

The behavior boundary is confirmed by exact paired source and concrete old-block preconditions. Independent blind source review is still required before an implementation snapshot. Full shape-provider, caller and pair coverage remains open; the finding is limited to this collision-offset sequence and does not establish first changed release within the interval.

## Handoff

- Independent delta description: preserve A's sequential Box clipping for sub-`1.0E-7` residual clearance; B's repeated-shape epsilon cutoff can zero it under the documented two-Stone arrangement.
- Related finding IDs: F-COLLISION-REPRESENTATION.
- Applicability constraints: player move path; positive X movement; residual clearance in `(0,1.0E-7)`; a later collision shape in the swept candidate stream.
- Independent blind finding review: pending. No implementation handoff until the reviewer accepts this exact source snapshot.
