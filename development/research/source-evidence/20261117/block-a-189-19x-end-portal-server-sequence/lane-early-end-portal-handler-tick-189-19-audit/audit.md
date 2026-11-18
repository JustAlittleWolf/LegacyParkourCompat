# Independent source audit: End transfer handler and helper tick

Pair: Minecraft 1.8.9 → 1.9 stable; Ornithe Feather.

## Outcome

NOT ACCEPTED as drafted. The handler ordering analysis is materially useful, but the draft assigns the wrong helper sequence and PortalForcer behavior to the ordinary overworld-to-End route. Correct those claims and re-audit the revised draft before accepting it.

## Frozen source identity

- 1.8.9 `ornithe-feather.ready.json`: `fe0ec792349ae43c91d0a30f23660c94b2f5c8d31a29643f73ed0de367110b95`; ready marker identifies version 1.8.9, Ornithe Feather, 1,612 sources, source-manifest SHA `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`.
- 1.8.9 `ornithe-feather.verification.json`: `a4206eee9ffb45a6f54f93371c8275b7a6c37cf9ab1ca93d5fa0f5d889881620`; records 1,612 source files verified, zero source differences/missing files; only the derived remapped intermediate archive differs.
- 1.8.9 `ornithe-feather.sources.sha256`: `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; independently checked all 1,612 listed source SHA-256 entries, zero mismatches.
- 1.8.9 `artifacts.sha256`: `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446`.
- 1.9 hidden `.decompile-provenance.json`: `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a`; independently checked every source SHA-256 entry, zero mismatches. Provenance pins 1.9 client SHA `0273b5c79d6b96799c6480ae61366d6b1d042655970de87f77f1a3f1ebea9307`, Feather artifact SHA `a4e09a92eab0fbf6ef4668e295db1949e9d52c42f145d56b6dfbf3a10c2c7c2a`, and 1.9 version metadata SHA `8d6f1e7f3a99f1236914b61e64eeade955ba21e8150563457142d3981aeee688`.

## Findings against the frozen sources

1. **Incorrect ordinary End helper-call sequence (draft § Transfer helper ticks).** In both `PlayerManager.changeDimension(Entity,...)` implementations, a player entering dimension 1 is placed at `toWorld.getSpawnPoint()` and, if alive, calls `fromWorld.tickEntity(entity, false)`. The later `if (dimension != 1)` branch is false, so there is no destination-world `tickEntity(entity,false)` call on this route. The draft’s statement that the ordinary overworld-to-End route runs both source and destination helper ticks is false. See 1.8.9 `PlayerManager.java:435-458,460-513` and 1.9 `PlayerManager.java:488-512,515-568`.
2. **Incorrect End PortalForcer placement claim (draft § Transfer helper ticks).** `PortalForcer.onDimensionChanged` is inside the same `dimension != 1` placement branch. End entry instead selects the destination world spawn point; it does not lay out a PortalForcer platform, floor coordinates, or zero velocity there. The draft’s paragraph describing End placement via `PortalForcer.onDimensionChanged` is false for the ordinary overworld-to-End route.
3. **Handler order is mostly source-supported, with a version-specific detail to preserve.** In 1.8.9 `ServerPlayerEntity.changeDimension` sends an early handler teleport to the forced End spawn before calling `PlayerManager.changeDimension`; the specialized manager path then sends another teleport after its helper work (`ServerPlayerEntity.java:440-463`, `PlayerManager.java:435-458`). In 1.9 `changeDimension` sets `inTeleportationState`, no longer sends that early teleport, and the specialized manager sends the teleport after helper work (`ServerPlayerEntity.java:455-484`, `PlayerManager.java:488-512`). The cited 1.9 handler tick writes its pre-tick `firstGood` snapshot after `tickPlayer()` returns (`ServerPlayNetworkHandler.java:161-164`); the packet-handler ordinary move path also continues to the unconditional post-move inbound-position write at `:460`. These are call/order facts, not delivery claims. The old handler tick itself does not call `tickPlayer` (`1.8.9 ServerPlayNetworkHandler.java:128-153`); its packet path does.
4. **`World.tickEntity(false)` summary is substantially supported but its End-route applicability must be narrowed.** Both versions bypass the entity tick and tick counter when `requireLoaded=false`, snapshot current coordinates/angles, run finite checks, and update chunk bookkeeping. In 1.8.9 an unloaded changed destination sets `inChunk=false`; in 1.9 `resetTeleported()` conditionally bypasses the unloaded-chunk branch and adds to the destination chunk. These facts apply to each actual false-call site, but draft claims about a destination-side helper for ordinary End entry must be removed. See 1.8.9 `World.java:1330-1396` and 1.9 `World.java:1391-1455`.

## Exact relied-on source SHA-256

| Release | Source | SHA-256 |
|---|---|---|
| 1.8.9 | `net/minecraft/server/network/handler/ServerPlayNetworkHandler.java` | `47ef077a7fa0f74cf44e1c8bda449d1b220bf56503b2fef98fd8016e8092df5b` |
| 1.9 | `net/minecraft/server/network/handler/ServerPlayNetworkHandler.java` | `89609533320e02ed781c4aa87caf9eb55417756bba8a6b0ea53bb5f2e1836634` |
| 1.8.9 | `net/minecraft/server/PlayerManager.java` | `0498b44f26782daa8b82db0c0823cef615c6bd7e7234a43c32456e15647d9a5d` |
| 1.9 | `net/minecraft/server/PlayerManager.java` | `ec75fbd8978b92d18f16b649c65ed596ddce6a437fd22bb18fc0b86d2c937e86` |
| 1.8.9 | `net/minecraft/server/entity/living/player/ServerPlayerEntity.java` | `047158bda6fa8fe2ea8a097972c098c5abe492ffbbe280006ae7592cd97fbb81` |
| 1.9 | `net/minecraft/server/entity/living/player/ServerPlayerEntity.java` | `dc6b4d3dfff336aed5efa03c9f97a3ae09531e0ae8cd80f8cb6de949333fbe10` |
| 1.8.9 | `net/minecraft/world/World.java` | `3c04f5b874fbb6692039164c882a3c47fe28c7eca864e91c9ac57798153888ee` |
| 1.9 | `net/minecraft/world/World.java` | `d1e23db2e20c86eb37b964a0df6838f1422ade7b0c2fff9c869ff8345979aa98` |
| 1.8.9 | `net/minecraft/server/world/PortalForcer.java` | `5fe634ce1537e1fbe6c615a47c23059b66b52f24b9c21f99d52b300f9c00ee44` |
| 1.9 | `net/minecraft/server/world/PortalForcer.java` | `13488be38f1b0426b40388e30fbbfec8ddfef885382b3354a9e7180254b4c947` |

## Audited author input

Candidate: `lane-early-end-portal-packets-189-19/post-transfer-handler-tick.md`
SHA-256: `317d5b7ee8bbefdc5ea24decf391af22e2d47b6e5f766b53a2017fb6ca06a435` (matches the assigned immutable input).

This audit is source-only. No primary source, product file, wiki, backlog, test, build, runtime, network, Git, or source-preparation writes/actions were performed.