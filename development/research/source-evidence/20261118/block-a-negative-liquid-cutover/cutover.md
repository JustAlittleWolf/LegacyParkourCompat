# Negative-boundary `containsLiquid` cutover check

## Scope and verdict

This closes the missing earliest-stable-release gate for the accepted conditional `World.containsLiquid(Box)` negative-minimum probe. Exact sources establish that the old lower-bound decrement is present in 1.7.10 and absent in the first 1.8 stable release; the same newer scan remains in 1.8.9. The implementable stable profile boundary is therefore `1.7.10` versus `1.8` (the precise development commit inside that interval is not established). No 1.8.1–1.8.8 source is required for this stable-endpoint claim: the earliest 1.8 release already has the newer behavior and the paired endpoint retains it. This does not make a claim about every 1.8 patch or runtime reproduction.

## Exact source identity and integrity

All paths below are relative to `decompiled_minecraft/<version>/ornithe-feather/` in the primary checkout. Sources were read-only.

- **1.7.10, Ornithe Feather `1.7.10+build.2`:** `.decompile-provenance.json` maps 1,803 source files; all 1,803 file hashes matched. It identifies the exact Feather build.2 merged mapping artifact. Relevant hashes: `world/World.java` `2d648edfe7fdc953825a1df94709e889ea21354494d85762bca49b4a109d01f9`; `entity/Entity.java` `af4ef7b11bc709d96bd3eea1260a61d38a1a04996bdf87f2a77c459ddf070a69`; `entity/living/LivingEntity.java` `5c1c5cf82d98c2f57a65b857fe02975b749c0562e4c1437ab5f072e202ca941c`; `entity/living/player/PlayerEntity.java` `350901699e449537372e1c4af6a79ae256de39638cdad049dfc7800b6607791c`.
- **1.8, Ornithe Feather `1.8+build.2`:** `.decompile-provenance.json` maps 2,497 source files; all 2,497 file hashes matched. It identifies Feather build.2 inputs. Relevant hashes: `world/World.java` `afbc701ef8b66020bdbb2831fd80683705d20237c68fbf23a4001c020876fc6d`; `entity/Entity.java` `2be081abe1720f89d7ab90f782b8411e71bc5b8a6b53966b49a2380e3c47ffd9`; `entity/living/LivingEntity.java` `645624913e265cad50293f659812a35c3c4303d1e8bdafdcca4eaf2e8ed5c69d`; `entity/living/player/PlayerEntity.java` `13fa1367b71035efbfccc593de57838268b10a8ceebb0aa76ad3d819b44cb0ca`.
- **1.8.9, Ornithe Feather `1.8.9+build.2`:** ready marker and provenance identify both exact Feather build.2 mapping artifacts. The ready marker's source-manifest SHA-256 (`9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`) matched the manifest file; all 1,612 listed source hashes matched. Relevant hashes: `world/World.java` `3c04f5b874fbb6692039164c882a3c47fe28c7eca864e91c9ac57798153888ee`; `entity/Entity.java` `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b`; `entity/living/LivingEntity.java` `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e`; `entity/living/player/PlayerEntity.java` `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88`.

## Scan evidence

- 1.7.10 `world/World.java:1517–1540`: `containsLiquid(Box)` floors the lower X coordinate and decrements that scan start when `minX < 0`; the fixture's `minX < 0` includes the extra negative-side cell.
- 1.8 `world/World.java:1417–1437`: the method scans from `floor(minX)` through `floor(maxX)` inclusive and has no negative-minimum decrement. This is already the newer scan in the first stable 1.8 release.
- 1.8.9 `world/World.java:1438–1459`: the same inclusive floored X bounds remain.

## Player call-path continuity in 1.8

`client/entity/living/player/LocalClientPlayerEntity.java:389` returns true from `isLocallyControlled`; it extends `ClientPlayerEntity`, which extends `PlayerEntity`, which extends `LivingEntity`. `PlayerEntity.tick` invokes `super.tick` (class starts at line 81; override at 185; call at 230). `LivingEntity.tick` invokes `moveRelative` at line 1446. In `LivingEntity.moveRelative`, the water branch is reached when `isInWater` (lines 1115–1123), and after horizontal collision it calls `canMove` (line 1219). `Entity.baseTick` refreshes water state through `checkWaterCollisions`/`World.applyLiquidDrag` (`Entity.java:244, 746–758`); `Entity.canMove` delegates to `doesNotCollide`, whose final predicate calls `World.containsLiquid` (`Entity.java:361–367`). Thus the same local-player operation remains reachable in the first 1.8 stable release across the changed world predicate.

The accepted 1.7.10–1.8.9 fixture supplies the other endpoint's call-path and probe arithmetic. This check adds only the missing 1.8 endpoint and its canonical source integrity. It does not assert a continuous ordinary trajectory for the stipulated world snapshot.

