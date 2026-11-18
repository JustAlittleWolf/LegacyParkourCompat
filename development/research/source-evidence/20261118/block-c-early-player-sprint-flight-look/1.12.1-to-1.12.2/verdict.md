# Independent audit verdict: 1.12.1 → 1.12.2 Elytra look and ordinary dismount

## Immutable input and source identity

Audited frozen report: `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-b9ff-7a20-8686-0295dde20f26/c-early-1121-1122-glide-look-dismount/report.md`, 6,082 bytes, SHA-256 `1aa7b1bddaff87bffc0f53331f0eb1ce22f0826b08e7f2ea8b44d11daea11c7c`.

The sources are the read-only local exact-release `ornithe-feather` decompilations:

- 1.12.1 `decompiled_minecraft/1.12.1/ornithe-feather/`. `.decompile-provenance.json` SHA-256 `0ff5246e8d29c04554cc9ec93ae9b4abef539652325b6c0ac822851d7186a9c8`. It records client SHA-256 `19331fb29bf8b8d5ed764677c94cca9d66481e10104f660eb555894a4300cf22`, Feather `1.12.1+build.2` merged mapping SHA-256 `f94febd2167d6091b4c157aef0a85c8791e6f8fb27b883e27a82a68c7b02b9c7`, and Feather metadata SHA-256 `1893deb3a1a74c6f2054876e2b8512817f744188aa8a9d8aafdba8525c0e896b`.
- 1.12.2 `decompiled_minecraft/1.12.2/ornithe-feather/`, with adjacent `.provenance.json` SHA-256 `747425a26da363d1abc8a2cecd83a74504aeb1f58be93aa53f48c690e35318ce` and `.ready.json` SHA-256 `b0aeec721e5c0af02b33d9e217cb8272889fa139ded1ecc2a31e42b930138f7a`. The ready marker records client SHA-256 `8ada07da5ee77dad3527bd7278fbd05ee1fc8a597813b216a871a2d7d64cc64f`; `artifacts.sha256` (SHA-256 `8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c`) records Feather `1.12.2+build.2` merged mapping SHA-256 `e48244030c53979793bdfbe48d7f1f3536f7e4f678ee5890a416198037cd46ca` and Tiny SHA-256 `a3aa1c8e73e81bd09432ba1f4b2e88aaacbedb2d8fa3cbf797536d2bdf0d4e58`. The source-manifest SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` equals both the ready marker value and the actual `ornithe-feather.sources.sha256` file hash. Its verification record reports 2,050 source files checked, zero source differences and no raw-input differences; the one artifact difference is identified there as a regenerated derived TinyRemapper JAR, not a source difference.

All actual source hashes below were checked against the 1.12.1 provenance `sources` map or the 1.12.2 source manifest. SHA-256 (1.12.1 / 1.12.2):

| Source file | 1.12.1 | 1.12.2 |
| --- | --- | --- |
| `net/minecraft/entity/Entity.java` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` | same |
| `net/minecraft/entity/living/LivingEntity.java` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` | same |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `4dab93ea3178a28c616947004ecdc514e6542edca8c62ef83f686ccf6b2ce640` | `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `d7237b8b9a68525bbfec6af1c234d2aafb1e4aa7d52059331d1d243313d6d7d1` | `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc` |
| `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `15e5e391df2e799ce04cac91af6feb7b9dc866a8963cfb03bc1bd04f375b986f` | `fce21d9902e555fd46545fb04bdb6c4a912eef6398790776ad1a122e09960c7c` |
| `net/minecraft/server/TrackedEntity.java` | `1ba9b26e88e9fc278c667b4311105bbfe45d1b58d6ffa015e02a56ad1c3e2cdc` | same |

## Claim verdicts

**Elytra look-vector operation — accepted, no difference in the bounded slice.** In both Feather trees `LivingEntity.moveRelative(float,float,float)` lines 1425–1464 takes the active gliding branch only after its local/logical-movement and fluid gates, sets `fallDistance` conditionally, then reads `getLookVector()`. `Entity.getLookVector()` lines 1690–1692 calls the final `getRotationVector(this.pitch, this.yaw)` implementation (lines 1239–1244). Therefore this call uses current pitch and current body `yaw`; it does not route through the interpolated `getRotationVector(tickDelta)` overload. The `LivingEntity` tick-delta overload’s `headYaw` path is not used here. `LocalClientPlayerEntity.isLocallyControlled()` returns true in both versions (1.12.1 lines 484–486; 1.12.2 lines 485–487), satisfying the local-client side of the outer movement gate. The checked `LivingEntity.java` and `Entity.java` files are byte-identical across the pair; the local-player method bodies also match despite unrelated changes in its file. This verdict is conditional on the Elytra/gliding state already being active at the `moveRelative` call; it does not establish the timing of activation or any trajectory result.

**Ordinary non-Boat/non-Horse dismount selection — accepted as server-only for the examined callback; no client-side selection difference established.** `PlayerEntity.rideTick()` lines 375–379 only starts the sneaking dismount branch under `!world.isClient`. `LivingEntity.stopRiding()` lines 1908–1914 detaches first and invokes `dismountRider(savedMount)` only when the mount changed and `!world.isClient`. `LivingEntity.dismountRider()` lines 1321–1387 then selects the default non-Boat/non-Horse destination and calls `Entity.teleport`; `Entity.teleport()` lines 2181–2185 writes position through `setPositionAndAngles` and ticks the entity. The compared callback/selection bodies are identical in both versions (even though `PlayerEntity.java` differs elsewhere).

The local client does receive passenger-state synchronization: `ClientPlayNetworkHandler.handleEntityPassengers()` lines 833–851 calls `yeetPassengers()` and reapplies the packet’s passenger IDs. This changes riding links. Its local `stopRiding()` chain cannot invoke `dismountRider` because of the `!world.isClient` guard. The dismount callback’s `EntityTeleportS2CPacket` is not a direct position writer to the owning client: `TrackedEntity.tick()` sends movement/teleport packets with `sendPacket(packet2)` (line 237), `sendPacket()` sends to its tracked-player set (lines 288–292), and `updatePlayer()` excludes `player == this.entity` (lines 316–318). The local client’s distinct self-position writer is `handlePlayerMove(PlayerMoveS2CPacket)` (lines 578–614), which applies packet coordinates; the examined dismount callback calls `Entity.teleport`, not the server network handler’s player-correction path. This establishes no direct self-position packet carrying the callback’s selected destination in the traced source path; it does not rule on later, independent server corrections. Geometry/collision feasibility remains conditional and unreviewed, as in the report.

## Scope and disposition

Both assigned source claims are supported as bounded no-difference findings. No change was found to accept. This is not full tick/inventory coverage, does not resolve dismount geometry, and does not freeze the 1.12.1–1.12.2 pair. No build, tests, game launch, source generation, product edit, or Git operation was performed.

