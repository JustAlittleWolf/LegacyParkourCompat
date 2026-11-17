# Honey callback cadence and continuity

Scope: canonical exact Mojmap sources for 1.19.2, 1.19.3, 1.19.4, 1.20, 1.20.1, 1.20.2, 1.20.3, 1.20.4, 1.20.5, 1.20.6, 1.21, 1.21.1 and 1.21.2. This pass follows only the caller and block-intersection conditions needed to establish when HoneyBlock's `entityInside` callback is reached. It does not reassess HoneyBlock's velocity formula.

## Result

| Exact release(s) | Callback cadence and gate |
| --- | --- |
| 1.19.2 | `Entity.move` calls `tryCheckInsideBlocks()` once at the end of each ordinary move. The scan uses bounding-box corners inset by `0.001` and runs only when `hasChunksAt(min,max)` succeeds. |
| 1.19.3 | Same per-move placement and loaded-area gate. The inset changes from `0.001` to `1.0E-7`. |
| 1.19.4, 1.20, 1.20.1, 1.20.2, 1.20.3, 1.20.4, 1.20.5, 1.20.6, 1.21, 1.21.1 | Same per-move placement and `hasChunksAt` gate; `BlockPos.containing` receives the `1.0E-7`-inset bounds. |
| 1.21.2 | No `tryCheckInsideBlocks` call remains in `Entity.move`. `LivingEntity.tick` applies block effects after `travel` returns, on the server or for the locally controlled instance. It appends movement segments, traverses them with `boxTraverseBlocks`, and deduplicates visited block positions for that batch. Its bounding box is deflated by `1.0E-5F`; there is no matching `hasChunksAt(min,max)` callback gate. |

Thus the located exact placement change is between 1.21.1 and 1.21.2. The inspected endpoints and every assigned intermediate patch are explicit above; the source evidence does not establish which unpublished commit introduced the 1.21.2 behavior.

## 1.21.1 to 1.21.2 details

In 1.21.1 `Entity.move` updates position and collision state, calls `tryCheckInsideBlocks()`, then applies the block speed factor to horizontal velocity. `checkInsideBlocks` scans the inset box only when the whole bounds pass `level.hasChunksAt`; for every selected block it calls `BlockState.entityInside` and then `onInsideBlock`.

In 1.21.2, `LivingEntity.tick` calls `travel`, then calls `applyEffectsFromBlocks()` when `!level.isClientSide() || isControlledByLocalInstance()`. A normal dry, non-fall-flying locally controlled player takes `LivingEntity.travel`'s `travelInAir` branch. That branch performs movement-vector calculation, gravity selection, and friction/drag writes before returning; block callbacks therefore run after those travel calculations. Fluid and fall-flying paths are selected ahead of `travelInAir`, so they do not share that ordinary-air ordering. Spectator `noPhysics`, removed entities, and other block-disabled cases are rejected through `isAffectedByBlocks()` (`!isRemoved() && !noPhysics`).

`applyEffectsFromBlocks` optionally runs `stepOn` first when grounded, adds the supplied movement segment, and then checks the collected movement list. For each traversed block position, `checkInsideBlocks` first rejects dead entities, gets the state, and uses a per-batch visited-position set. It skips air; for non-air it asks `getEntityInsideCollisionShape` and skips non-full-block shapes unless the movement intersects that shape. It then invokes `entityInside`, invokes `onInsideBlock`, and only after both returns adds that state to `blocksInside`. The caller uses that collector for its fire/lava check, then clears the movement list and collected states. This establishes path and state-collector ordering; it does not change HoneyBlock's own callback formula.

The pre-1.21.2 call site is move-local, so a callback is interleaved with the movement operation and precedes subsequent travel velocity handling. The 1.21.2 ordinary-air callback is post-travel. The new visited set also makes a block position eligible at most once within the collected movement batch, even if multiple recorded segments traverse it. For ordinary local client movement the segment added here is old position to current position; server packet movement handling can add accepted movement segments through `recordMovementThroughBlocks`.

## Per-block applicability boundary

The inset change is a separate source difference from callback cadence. In 1.19.2 the scan corners use `+/- 0.001`; in 1.19.3 they use `+/- 1.0E-7`, with the same change retained through 1.21.1. At an entity-box boundary near an integer block coordinate, this can select a different inclusive block range and therefore change whether that block's `entityInside` callback is reached. In 1.21.2 the range model changes again to movement-path traversal with a `1.0E-5F` deflated entity box and collision-shape intersection gating. These applicability facts are recorded separately in `findings/honey-block-inset.md` and `findings/honey-callback-cadence.md`.

## Source identity and integrity

All 13 exact `mojmap` trees were present. For each release, SHA-256 of `Entity.java`, `LivingEntity.java`, `Player.java`, `HoneyBlock.java`, `ServerGamePacketListenerImpl.java`, and `BlockGetter.java` matched the corresponding published source digest in the version-root `mojmap.sources.sha256` sidecar, or, where that sidecar is absent, the mapping-local `.decompile-provenance.json` `sources` entry. All 78 checked files matched. The hashes below identify the two primary caller files for every inspected release; hashes are of the exact UTF-8/LF source bytes.

| Release | `Entity.java` SHA-256 | `LivingEntity.java` SHA-256 |
| --- | --- | --- |
| 1.19.2 | 759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6 | fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77 |
| 1.19.3 | 8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32 | 2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db |
| 1.19.4 | 3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b | c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165 |
| 1.20 | 94b9c3656715de2d61fa9a02ccef164261e50eb13d6090a6f07e58fe9c5b0759 | decf8cd70d194098ad51d0c82e5a0087e0687881e0a2dc66456f2ba6ea76676f |
| 1.20.1 | 94b9c3656715de2d61fa9a02ccef164261e50eb13d6090a6f07e58fe9c5b0759 | decf8cd70d194098ad51d0c82e5a0087e0687881e0a2dc66456f2ba6ea76676f |
| 1.20.2 | d7ee49aaea5e862b92508e767562cabc8f10515605e8fb67565c8c8d5c23b01b | 5c481da1ffc8684c4b30171c92e61fad751e6a3a708d5bc6486ac9f96ff69828 |
| 1.20.3 | 07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9 | f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d |
| 1.20.4 | 07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9 | f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d |
| 1.20.5 | 71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3 | c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2 |
| 1.20.6 | 71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3 | c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2 |
| 1.21 | b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850 | 324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8 |
| 1.21.1 | b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850 | 324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8 |
| 1.21.2 | a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9 | 087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52 |

Relevant exact source locations:

- `net/minecraft/world/entity/Entity.java`: 1.19.2 `move` 547-679 and `checkInsideBlocks` 886-913; 1.19.3 `move` 547-679 and `checkInsideBlocks` 894-921; 1.21.1 `move` 598-727 and `checkInsideBlocks` 1001-1028; 1.21.2 `applyEffectsFromBlocks` 736-767, `recordMovementThroughBlocks` 1034-1036, and `checkInsideBlocks` 1038-1072.
- `net/minecraft/world/entity/LivingEntity.java`: 1.21.2 `travel`/`travelInAir` 2178-2221 and tick callback 2775.
- `net/minecraft/world/entity/player/Player.java`: 1.21.2 `travel` 1445-1466 delegates ordinary travel to `super.travel`.
- The other assigned intermediate versions were checked for the same `Entity.move` call, inset and loaded-area gate; their hashes are listed above.