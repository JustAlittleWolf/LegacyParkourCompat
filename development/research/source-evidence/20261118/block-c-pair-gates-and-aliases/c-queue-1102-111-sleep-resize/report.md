# Source coverage report — 1.10.2 to 1.11 sleeping pose resize, Feather

Terminal: **COMPLETED**

## Exact source identity

Compared exact releases `1.10.2` and `1.11`, both `ornithe-feather`, in the canonical read-only trees `decompiled_minecraft/1.10.2/ornithe-feather/` and `decompiled_minecraft/1.11/ornithe-feather/`. The source-only lead report was supplied at `c-queue-1102-111-tick-modifiers/report.md` with SHA-256 `f64a5c3839732cef92345e4488e84263b66379a81dc72dd11aa4553caaa34eb8`. Its exact readiness, provenance and source-manifest identities and relevant source hashes are recorded there; this worker did not generate sources.

Examined source hashes (also matching the lead report):

| Relative source | 1.10.2 SHA-256 | 1.11 SHA-256 |
|---|---|---|
| `net/minecraft/entity/living/player/PlayerEntity.java` | `a055b84b98d98e828e177cad9bba47a1334ee9bf3ecd4331792979106235a302` | `a0d787681de5caecda03fea1536dac0c07e6ca5cfbfe3b5fbb700cbe77c86fb6` |
| `net/minecraft/entity/Entity.java` | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` | `3209feaacd1e798b521a193eeb081d92c96881a70a18254ef24a58e911a4fd97` |
| `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `af3efba0a1d5a0824c29c4af4ac7f8e4484d1787a82208e4d72bcc65f957e656` | `cef5d643eca0b32119571eff5201705cc8c99125f8266f763172202d89e3a40b` |

## Bounded trace and result

**The width-shrink implementation differs, but the received sleeping transition does not retain the changed AABB. No player movement difference is established for this path.**

- `PlayerEntity.updatePlayerPose()` (1.10.2 lines 296–319; 1.11 lines 295–318) selects `0.2F × 0.2F` while `isSleeping()` is true, tests the candidate box against world collisions, then invokes `setSize`. Its selection, collision gate and call order match. This is the candidate pose-resize site identified by the lead report.
- `Entity.setSize()` (1.10.2 lines 264–277; 1.11 lines 267–287) differs on width reduction: 1.10.2 rebuilds from the existing box minimum X/Z; 1.11 recenters using entity `x`/`z` and the new half-width, then returns. At this site that difference could matter if the sleeping size change were left as-is.
- The received sleep state arrives through `ClientPlayNetworkHandler.handlePlayerSleep()` (1.10.2 lines 749–752; 1.11 lines 752–755), which runs on the packet thread via `PacketUtils.ensureOnSameThread` and calls `packet.getPlayer(world).trySleep(packet.getPos())`. The corresponding wake event is handled by `handleEntityAnimation()` action 2 and calls `wakeUp(false, false, false)` in both versions. These client receiver paths match in behavior/order.
- `PlayerEntity.trySleep()` (1.10.2 lines 1180–1248; 1.11 lines 1192–1246) skips server-only sleep validation on a logical client, calls `setSize(0.2F, 0.2F)`, then sets the sleeping location with `setPosition(...)` before setting `sleeping = true` and zeroing all three velocity components. `setPosition` in both `Entity` versions rebuilds the box around the supplied entity X/Z and current dimensions. Therefore it overwrites the just-created shrink box before `trySleep` returns. The intervening operations only read chunk/block-facing state and set sleeping camera offsets; no box consumer or movement callback intervenes.
- `updatePlayerPose()` is reached from `PlayerEntity.tick()` (1.10.2 line 250; 1.11 line 249). The successful `trySleep` has already set dimensions to 0.2 and position to the sleeping location, so this tick's sleeping-pose selection sees matching dimensions and does not call the differing shrink branch. NBT load is not an alternate client producer: when loaded sleeping, `readCustomDataFromNbt` calls `wakeUp(true, true, false)` in both versions. Wake-up first restores ordinary dimensions and then, when a bed is present, calls `setPosition`; its width increase follows the common expansion path, with no shrink-width special branch.
- The inbound packet is the equivalent producer of the client sleeping state in the multiplayer path: `PlayerSleepS2CPacket` calls the same `trySleep` entry point. Server-side bed eligibility and orientation behavior are outside this assignment and not used to support the conclusion.

**Disposition:** closed negative for the separately received sleeping-pose width-shrink path. There is a real `Entity.setSize` shrink math change, but this received transition immediately rewrites the box through `setPosition`; normal later pose evaluation does not repeat the shrink. No lasting box offset or movement consequence follows from this path. Bed-entry eligibility, camera/orientation details, world collision shape/provider math and server implementation were excluded.

## Coverage limits

This is only the bounded sleeping state producer/receiver and resize trace, not full-pair or tick-graph coverage. Other sources of sleeping state, arbitrary direct `setSize` callers, server-side behavior, collision provider details, and downstream non-pose uses of the box were not audited. The source read was static only; no product code, wiki inventory, Git history, tests, game/runtime or source generation was accessed.
