# C blind source report: 1.12.1 → 1.12.2

## Source identity and integrity

Both sides are canonical `ornithe-feather` decompilations. The 1.12.1 source root is `decompiled_minecraft/1.12.1/ornithe-feather/`; its in-root `.decompile-provenance.json` pins Mojang 1.12.1 client SHA-256 `19331fb29bf8b8d5ed764677c94cca9d66481e10104f660eb555894a4300cf22`, Feather `1.12.1+build.2` merged mapping SHA-256 `f94febd2167d6091b4c157aef0a85c8791e6f8fb27b883e27a82a68c7b02b9c7`, and the Feather `1.12.1` metadata SHA-256 `1893deb3a1a74c6f2054876e2b8512817f744188aa8a9d8aafdba8525c0e896b`. The mapping namespace is `ornithe-feather`.

The 1.12.2 source root is `decompiled_minecraft/1.12.2/ornithe-feather/`. Its provenance is adjacent to the source directory in `decompiled_minecraft/1.12.2/ornithe-feather.provenance.json`, with the adjacent `ornithe-feather.ready.json`, `ornithe-feather.sources.sha256`, and `artifacts.sha256`. Those identify exact Minecraft 1.12.2 client SHA-256 `8ada07da5ee77dad3527bd7278fbd05ee1fc8a597813b216a871a2d7d64cc64f` and Feather `1.12.2+build.2` mapping inputs (merged jar SHA-256 `e48244030c53979793bdfbe48d7f1f3536f7e4f678ee5890a416198037cd46ca`, Tiny SHA-256 `a3aa1c8e73e81bd09432ba1f4b2e88aaacbedb2d8fa3cbf797536d2bdf0d4e58`); the source manifest hash in the ready marker is `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`. The verification record reports all 2,050 published source files matched a second decompilation and all raw source inputs matched; its one artifact difference is a regenerated derived TinyRemapper jar, explicitly not a published source difference. For 1.12.1, the hidden sibling `.ornithe-feather.previous-*` directory was not used.

Current source bytes match the source manifests for the examined files. Across versions, `Entity.java` is identical (SHA-256 `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a`) and `LivingEntity.java` is identical (`190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6`). The examined player and local-player methods also match; their containing files have unrelated differences elsewhere.

## 1) Active Elytra first-movement look-vector yaw read

- 1.12.1, Feather: `net/minecraft/entity/living/LivingEntity.java`, `moveRelative`, lines 1425–1464; `net/minecraft/entity/Entity.java`, `getRotationVector(float,float)` lines 1239–1244 and `getLookVector()` lines 1690–1692; `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `isLocallyControlled()` lines 484–486.
- 1.12.2, Feather: same files and members at the same lines.

Difference: none in the examined operation. On the first call where `isFallFlying()` is already true, `moveRelative` first sets `fallDistance` to `1.0F` only when `velocityY > -0.5`, then reads `getLookVector()` once. That method calls `getRotationVector(this.pitch, this.yaw)` directly. The vector math uses current pitch and current body `yaw` with the same float-degree/trigonometric operation order in both releases. It does not read `headYaw`, `lastYaw`, `lastHeadYaw`, or `tickDelta`; the interpolated `getRotationVector(float tickDelta)` overload is not used by this call. `LocalClientPlayerEntity.isLocallyControlled()` returns true, satisfying the local-player side of `moveRelative`'s outer gate. The gliding branch uses the vector's X/Z and length immediately in its existing velocity updates; no trajectory is inferred here.

## 2) Ordinary mount dismount position selection

- 1.12.1, Feather: `net/minecraft/entity/living/player/PlayerEntity.java`, `rideTick` lines 374–379 and `stopRiding` lines 948–951; `net/minecraft/entity/living/LivingEntity.java`, `stopRiding` lines 1907–1914 and `dismountRider(Entity)` lines 1321–1387; `net/minecraft/entity/Entity.java`, `stopRiding` lines 1648–1654, `setPositionAndAngles` lines 1122–1135, `setPosition` lines 298–305, and `teleport` lines 2181–2185.
- 1.12.2, Feather: same files and members at the same lines; examined bodies match.

Difference: none in the examined operation. Eligibility is ordered in `PlayerEntity.rideTick`: only `!world.isClient && isSneaking() && isRiding()` enters the dismount path; it calls `stopRiding()` before clearing sneaking. `PlayerEntity.stopRiding()` calls the superclass and then clears `boardingCooldown`. `LivingEntity.stopRiding()` saves the mount, detaches through `Entity.stopRiding()`, then calls `dismountRider(savedMount)` only when detachment occurred and `!world.isClient`. Thus this direct position-selection callback is server-side; the local client does not run it or select a dismount position. It is outside this project's historical client-movement scope.

Llamas follow the excluded `HorseBaseEntity` branch. For a mount that is neither `BoatEntity` nor `HorseBaseEntity`, the default branch initializes a fallback at mount X/Z and mount shape-min-Y plus mount height. If mount movement direction is non-null, it visits the fixed nine offset pairs in source order. Each candidate tests the rider-sized translated box with `world.getCollisions`; a collision-free base candidate can early-return through `teleport(u, this.y + 1.0, v)` when the block at `(u,this.y,v)` is full. Otherwise full-block-or-water support below updates the selected candidate to Y `this.y + 1.0`; when the base box collides, a collision-free box one block higher plus a full block at `(u,this.y + 1.0,v)` selects Y `this.y + 2.0`. After the loop, `teleport(l,m,n)` writes the last selected candidate or the initial fallback. `teleport` sets its flag, calls `setPositionAndAngles`, then `world.tickEntity`. `setPositionAndAngles` writes X/Y/Z, copies them into `lastX/Y/Z` and `prevX/Y/Z`, sets yaw/pitch, and calls `setPosition` to rebuild the box. Collision feasibility remains dependent on the exact `world.getCollisions`/shape and block-state predicates and is not resolved as geometry by this report.

No accepted difference was found in either assigned slice. No player-tick inventory outside these two bounded operations was examined; this report does not freeze the pair.




