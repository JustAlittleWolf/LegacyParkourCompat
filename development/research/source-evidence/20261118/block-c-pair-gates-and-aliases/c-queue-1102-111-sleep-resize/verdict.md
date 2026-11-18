# Independent blind audit — sleeping pose resize, 1.10.2 → 1.11 Feather

Terminal: **COMPLETED**

## Bound inputs

- Coverage report: `c-queue-1102-111-sleep-resize/report.md`, SHA-256 `e8e24e69bdf4a780084f6bbc10652290489b672e3330e02cbe8db35f27e1e899`.
- Finding draft: `c-queue-1102-111-sleep-resize/finding-draft.md`, SHA-256 `62d3bb8a6f25b64c23374adfc79b670cb7f85041bba824c96adfe777eed0f6e1`.
- Compared canonical read-only source trees: `decompiled_minecraft/1.10.2/ornithe-feather/` and `decompiled_minecraft/1.11/ornithe-feather/`. Both are Feather; the local provenance manifests identify exact release and mapping inputs. Source generation was not performed.

Verified hashes of the inspected files:

| Source | 1.10.2 SHA-256 | 1.11 SHA-256 |
|---|---|---|
| `net/minecraft/entity/living/player/PlayerEntity.java` | `a055b84b98d98e828e177cad9bba47a1334ee9bf3ecd4331792979106235a302` | `a0d787681de5caecda03fea1536dac0c07e6ca5cfbfe3b5fbb700cbe77c86fb6` |
| `net/minecraft/entity/Entity.java` | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` | `3209feaacd1e798b521a193eeb081d92c96881a70a18254ef24a58e911a4fd97` |
| `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `af3efba0a1d5a0824c29c4af4ac7f8e4484d1787a82208e4d72bcc65f957e656` | `cef5d643eca0b32119571eff5201705cc8c99125f8266f763172202d89e3a40b` |

## Verdict

**Accepted as a closed negative for the bounded received sleeping-pose shrink path.** The draft correctly identifies a real `Entity.setSize` implementation difference but does not establish a lasting movement delta from this producer:

- `Entity.setSize` in 1.10.2 width shrink keeps the old shape minimum X/Z and derives the maximum edges from the reduced width. In 1.11 it immediately rebuilds a centered shape around entity `x`/`z` using the new half-width and returns. This establishes the local method difference.
- In both `PlayerEntity.trySleep` implementations, `setSize(0.2F, 0.2F)` is followed only by chunk/block-facing reads and sleeping-position calculation before `setPosition`. The sleep position write rebuilds the shape centered around the player position with the new dimensions. The changed intermediate AABB is therefore overwritten synchronously before `trySleep` returns; no read or movement callback between the resize and position write was found in the inspected method.
- In the network receiver, `handlePlayerSleep` calls `trySleep` after `PacketUtils.ensureOnSameThread` in both versions. The direct client state path therefore uses that same synchronous sequence. It does not expose the intermediate box to a later tick.
- Later `updatePlayerPose` selects the same sleeping dimensions. Since `trySleep` already set those dimensions, its conditional size-update branch does not call `setSize` for this sleeping transition. This rules out persistence by a repeated pose shrink on that path.
- Client-side `trySleep` bypasses the server-only eligibility block in both versions; the trace does not rely on server bed-entry validation. The corresponding wake receiver uses `wakeUp`, which first expands the dimensions; expansion does not enter the width-shrink branch under review.

Accordingly, the draft's conclusion that the received sleeping-pose width-shrink candidate is not an accepted movement finding is supported. The method-level `setSize` change should not be generalized as a closed no-difference result for other callers.

## Coverage limits

This audit covers the immutable report/draft claims and their bounded client sleep receiver → `trySleep` → box resize/position write → next pose evaluation path, plus the cited wake route. It is not a full 1.10.2→1.11 movement inventory or pair freeze. Other direct `setSize` callers, arbitrary pose changes, other producers of sleeping state, server behavior, collision providers/world shapes, and non-pose box consumers remain unaudited. Bed-entry eligibility and camera/UI offsets are excluded. Static source audit only; no product implementation, wiki, Git history, tests, game/runtime or source generation was used.
