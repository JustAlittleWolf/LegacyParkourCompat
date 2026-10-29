# Independent audit: 26.1–26.1.2 block-prediction alias closure

## Verdict

The bounded closure is supported. The stale-position suppression is present in 26.1, and the examined handler, level, packet listener, ACK packet/interface, prediction caller, and position writer sources are byte-identical in 26.1, 26.1.1, and 26.1.2. This verifies the bounded claim only; it does not extend or repeat the earlier 1.21.11-to-26.1.2 comparison.

## Identity and hash checks

I read the canonical read-only trees at `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\unobfuscated`. Each ready marker and provenance record identifies the matching release and `unobfuscated` namespace. The three source-manifest hashes match the ready markers:

- 26.1: `b77289c135ac6e12aa35e75177cea52d968f49d49e1ec745e510b1a786c9aeba`
- 26.1.1: `88afdfb8264842713f8cce86c0bf88e95c2c991ee706349d6ed8b6c22d52fcb3`
- 26.1.2: `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0`

For all three releases, I independently checked each named Java file against its manifest entry. All 21 checks passed. The seven file hashes also match across all three releases:

| Source file | SHA-256 |
|---|---|
| `net/minecraft/client/multiplayer/prediction/BlockStatePredictionHandler.java` | `3436c1a040b55036fc1b1c35bb99fe811e799d013503d65143f3edb77cd83a21` |
| `net/minecraft/client/multiplayer/ClientLevel.java` | `6e46db964229bf53bb88c33140cfa9acb5423dbed11a7c721b5b8bf9a7424de7` |
| `net/minecraft/client/multiplayer/ClientPacketListener.java` | `eb70d05e4f8429341fc41823eae2cc662560efa1e44c8b729db5477501718d82` |
| `net/minecraft/client/multiplayer/MultiPlayerGameMode.java` | `674fe62b7debec3fa582735ae1b86247a25211ec03ec19c9f331dd3c44502e37` |
| `net/minecraft/network/protocol/game/ClientboundBlockChangedAckPacket.java` | `72a20f1c4aec399b95b559a0855cdaa47bd9ae9ea1b061b2581a5cdb0f198b95` |
| `net/minecraft/network/protocol/game/ClientGamePacketListener.java` | `9857bd02f8fbae5e97110551ee227c60e2fd86b2b22d122e1900dfe9d2813258` |
| `net/minecraft/world/entity/Entity.java` | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` |

The closure's recorded input hash for `12111-2612-responses/source-review.md` also matches the file. The 26.1.2 provenance omits metadata hash and client SHA-1; the closure correctly does not infer them.

## Source retrace

In `BlockStatePredictionHandler`, `currentSequenceNr` defaults to zero and `lastTeleportSequence` initializes to `-1` (`:14-16`). `startPredicting` increments the sequence (`:52-56`); retained states capture the local player's position (`:18-25`), and repeated pending positions update their sequence (`:22-24`, `:85-88`).

`MultiPlayerGameMode.startPrediction` takes the incremented sequence, runs its predictive action, and sends the packet (`:293-299`). Its six call sites (`:163,184,240,277,330,389`) cover the described block destruction and item-use paths. `ClientLevel.setBlock` retains the prior state and player position when a client prediction succeeds (`:203-209`). Server block updates route through `setServerVerifiedBlockState` from both section and single-block packet handlers (`ClientPacketListener.java:849,941`; `ClientLevel.java:181-185`).

`ClientPacketListener.handleMovePlayer` changes position only in the non-passenger branch, sends teleport acceptance and the current position, then unconditionally records `onTeleport()` (`:796-807`). `onTeleport` stores the current sequence (`BlockStatePredictionHandler.java:66-68`). ACK dispatch is `ClientboundBlockChangedAckPacket.handle` → `ClientGamePacketListener.handleBlockChangedAck` → `ClientPacketListener.handleBlockChangedAck` → `ClientLevel.handleBlockChangedAck` → `endPredictionsUpTo` (`ClientboundBlockChangedAckPacket.java:26-28`; `ClientGamePacketListener.java:211`; `ClientPacketListener.java:2449-2451`; `ClientLevel.java:161-166`).

`endPredictionsUpTo` removes pending entries whose sequence is at most the ACK sequence, and passes the saved player position only when `lastTeleportSequence < sequence` (`BlockStatePredictionHandler.java:38-48`). `syncBlockState` still applies a differing authoritative state, while the positional snap additionally requires non-null saved position, the same player level, and a collision with the new block state (`ClientLevel.java:187-195`). The three-argument `Entity.absSnapTo` clamps X/Z, updates old coordinates, then sets the position (`Entity.java:1695-1702`). In the examined client sources, `syncBlockState` is called only from `endPredictionsUpTo`, and `onTeleport` only from `handleMovePlayer`.

## Boundary and limit

The boundary is confirmed present in 26.1 and byte-identical in the two named bugfix releases. Thus 26.1 is the earliest confirmed release within this three-release sample. Pre-26.1 introduction remains unexamined; this audit does not determine the historical introduction point.

This was a source-only audit. No product source, product history, wiki material, generation, builds, tests, or launches were used; no Git mutation was performed.
