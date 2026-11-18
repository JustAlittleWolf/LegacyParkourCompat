# Blind source audit freeze: 1.9.1 to 1.9.2 moving-piston player response

## Bounded verdict

No source difference was found in the assigned moving-piston player-response path between Minecraft 1.9.1 and 1.9.2. This closes only the bounded callback-admission, slime-axis velocity, requested displacement, and tick-order comparison. It is not a full pair freeze, implementation acceptance, runtime-parity result, or claim about final collision geometry or trajectory.

## Exact evidence identity

Both sides are the canonical `decompiled_minecraft/<version>/ornithe-feather/` trees. Their embedded `.decompile-provenance.json` manifests were present; each selected source file's SHA-256 matched its manifest entry. Actual cache bytes matched the input hashes in each manifest.

| Evidence | 1.9.1 | 1.9.2 |
| --- | --- | --- |
| Manifest SHA-256 | `e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a` | `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510` |
| Mojang client SHA-256 | `56ad7e53f6f4ad393844e274aa2190692b61d2ad9f6b3d12b33a8b7332fd51f9` | `ef87eb71250df1b871deab8d3ef10aac3290b9e78667ad548fde4d0e14af0d65` |
| Feather Gen2 mapping artifact | `1.9.1+build.2`, SHA-256 `6d5768353bf2ff2756d38537312548a8ac91d570f130d23c23e9412d2edd97b3` | `1.9.2+build.2`, SHA-256 `e2b98ececbff8fa0bd0c89283bbb32b7f9f9dcd4b89fa3f137666fa3ac0b6859` |
| Embedded Tiny mapping SHA-256 | `387e55b125e5691d993b0a553ab712eb906d59fe8dd25a5c9e805265e78d1334` | `387e55b125e5691d993b0a553ab712eb906d59fe8dd25a5c9e805265e78d1334` |
| Ornithe release metadata SHA-256 | `da56c9e7efe5c929b2ec8d99a8e2bbc6825847f2852a0e717fcd6c674db73dc4` | `5bad5e67d0870fec69bbab8fcae8d0525a274668bb472eb09d1552359d08edd1` |

The manifest names the exact Feather artifact and client URLs above. The mapping family is Ornithe Feather (`ornithe-feather`); no online mapping lookup was used.

## Paired source coverage and operation trace

All paths below have identical 1.9.1 and 1.9.2 content hashes, and those hashes match both embedded provenance manifests:

| Paired source path | SHA-256 |
| --- | --- |
| `net/minecraft/block/entity/MovingBlockEntity.java` | `7ad3426bb74c9e69b4f521d20e045b1dc1eafa9b725daf0e2c0b9b718bc2683c` |
| `net/minecraft/block/PistonBaseBlock.java` | `58387062a4d8b07843e0086edcf80c7bf53f9e6c03afe52a72284e847a8c0bf1` |
| `net/minecraft/block/MovingBlock.java` | `a5b2584bc575dc888f1d32d9d4b683b91fc8b145ee079e66ff4722718fc8906f` |
| `net/minecraft/entity/Entity.java` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` |
| `net/minecraft/entity/EntityFilter.java` | `f45827ffebad59c151aa5efa834824ba4d63bf60a645d14909df5d41f4b6e440` |
| `net/minecraft/world/World.java` | `cdda70c97f0e4d6f6c454c8f6d3c0d47f0e0ae612e75062d8fe93ddd3963e6b0` |
| `net/minecraft/world/chunk/WorldChunk.java` | `b98bccf16277a5a62b09ac2e77db7d77e098a62d8029c03a44fbed62e01ff267` |
| `net/minecraft/util/math/Box.java` | `cb0f30c72b7925fa0c0b133dffd051cf8260f4321f7c4a093c64ef5a5f500e62` |

The paired `PistonBaseBlock` setup creates moving block entities for the piston head and moved blocks, supplying the facing and extending flag (`PistonBaseBlock.java`, 1.9.1 lines 173–181, 310–326). These setup calls are unchanged in the pair.

In `MovingBlockEntity.tick()` (lines 164–181), `lastProgress` is first assigned the prior `progress`. If that value is already at least `1.0F`, `moveEntities()` runs before the block entity is removed and the moved state is installed. Otherwise `progress` advances by `0.5F`, clamps to `1.0F`, then `moveEntities()` runs. Thus at a reachable initial progress of zero, the first tick selects and moves entities using the new progress `0.5F` and the saved old progress `0.0F`; the next tick takes the terminal branch and calls the response before replacing the moving block. The separate `finish()` path sets both progress values to `1.0F` and removes/replaces the block without calling `moveEntities()` (`MovingBlockEntity.java`, lines 151–160).

For each such callback, `moveEntities()` forms a box from the union returned by `getShape(world, pos)`, translates that box by the block position, and calls `world.getEntities(null, box)` (lines 90–94). `World.getEntities` applies `EntityFilter.NOT_SPECTATOR`; `WorldChunk.getEntities` admits an entity when its `getShape()` intersects that box. This selector does not filter riders. For the bounded ordinary local, non-spectator, non-riding player case, `Entity.getPistonMoveBehavior()` returns `NORMAL` (line 2246), so the callback proceeds past the sole `IGNORE` rejection (lines 97–99). The local player is therefore admitted when the world/chunk query's equal geometry inputs satisfy the intersection predicate; the witness does not assert a particular final box or clipping result.

The movement direction is `facing` while extending and `facing.getOpposite()` while retracting (line 94). When the moved block is slime, the callback writes only the velocity component for that direction's axis, assigning the axis offset (`±1`) for X, Y, or Z (lines 99–112). For every non-ignored moved block, including slime after that velocity write, it computes only the direction-axis displacement from the callback box and the entity shape, adds `0.01` on that axis, and calls `entity.move(d*offsetX, e*offsetY, f*offsetZ)` (lines 113–146). The same displacement request occurs for other moved blocks without the slime velocity assignment. This records the move request, not its eventual clipped displacement or player trajectory.

The callback's geometry and world/entity inputs remain dependency boundaries: `MovingBlockEntity.getShape` delegates to the moved state's shape and offsets it by progress and facing (lines 77–88); the world/chunk selector uses the resulting box and entity shapes. No claim is made about the final piston shape, overlap result for a concrete world, collision clipping, or downstream trajectory.

## Coverage limit

The pair-wide tick and movement inventory, remaining block and player operations, and independent complete-coverage audit were not assigned here. Discovery acceptance for this bounded no-difference coverage does not establish full-pair acceptance. No tests or game/TAS/Gym/server launches were performed.
