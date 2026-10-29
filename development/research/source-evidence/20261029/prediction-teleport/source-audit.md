# Independent audit: prediction teleport boundary

## Input binding

Audited only `.native-source/20261028/frontier-c/12111-2612-responses/source-review.md`, whose SHA-256 is `0e331a2b76d631f9b8d5f2ff2c01c3d21a7fefb0ed879d4ca5cc01e7a558146d`, against the read-only canonical trees at `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.21.11\mojmap` and `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\26.1.2\unobfuscated`.

The provenance and ready sidecars agree on `versionId` and namespace: `1.21.11` / `mojmap` and `26.1.2` / `unobfuscated`; both ready statuses are `ready`. The 1.21.11 provenance records `mappingOption: mojmap` and batch releases `1.21.8`, `1.21.10`, `1.21.11`; the 26.1.2 provenance records `mappingOption: unobfuscated` and batch release `26.1.2`. Independently recomputed SHA-256 values of the source manifests match the ready markers: `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555` and `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0`. Artifact manifest hashes also match their ready markers (`c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c` and `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92`). The checked artifact indexes record 1.21.11 `client.jar` `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd`, `client-mojmap.jar` `7055b6a734a8f9f0d80229c2438ba12d10952a57c3795f32b8611020b34eb89a`, and `version.json` `9b55607ecf8a37c9cd5b7d17ffb5c283d7c8ca4333b34ef8bb20dcd24419b3a8`; the 26.1.2 index records published `client.jar` `b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0` and `version.json` `739c0e0520086c1a6d3dcd3ceec4f9c209848146e8a6012503241a3ba8117775`. This verifies the local sidecar identity chain; it does not independently establish the historical origin of cached artifacts beyond their recorded hashes.

All six cited Java file hashes match the actual files and their `*.sources.sha256` entries:

| Release / namespace | Source | SHA-256 |
|---|---|---|
| 1.21.11 / Mojmap | `ClientPacketListener.java` | `f1ebdb54d717266c894c87381c98bad101980d252de5bae6ca55c37aece1732e` |
| 1.21.11 / Mojmap | `ClientLevel.java` | `e76d09de5acad450062d5acb98e76ff032ecb179fef0f7af434e4148f84183a2` |
| 1.21.11 / Mojmap | `prediction/BlockStatePredictionHandler.java` | `39914e30a1c4cc8a5a6e53a7032e58153c9bd7000bb7def126229ab4cf854728` |
| 26.1.2 / unobfuscated | `ClientPacketListener.java` | `eb70d05e4f8429341fc41823eae2cc662560efa1e44c8b729db5477501718d82` |
| 26.1.2 / unobfuscated | `ClientLevel.java` | `6e46db964229bf53bb88c33140cfa9acb5423dbed11a7c721b5b8bf9a7424de7` |
| 26.1.2 / unobfuscated | `prediction/BlockStatePredictionHandler.java` | `3436c1a040b55036fc1b1c35bb99fe811e799d013503d65143f3edb77cd83a21` |

## Per-claim verdicts

### Position-correction ordering and boundary recording — corroborated

In 1.21.11 `ClientPacketListener.handleMovePlayer` (lines 776–785), the handler applies `setValuesFromPositionPacket` when the player is not a passenger, sends teleport acceptance and then the current position/rotation packet, and ends without updating block prediction state. In 26.1.2 (lines 796–807), the same passenger-gated position application and two sends are followed by `getBlockStatePredictionHandler().onTeleport()`. That call is outside the passenger conditional and occurs after both sends. `BlockStatePredictionHandler.onTeleport` (lines 66–68) assigns `lastTeleportSequence = currentSequenceNr`.

### Prediction reconciliation and exact comparison — corroborated; comparison is strict

In both releases, `endPredictionsUpTo` processes and removes entries with `serverVerifiedState.sequence <= sequence`, where `sequence` is the acknowledgment sequence passed from `ClientboundBlockChangedAckPacket.sequence()` through `ClientPacketListener.handleBlockChangedAck` and `ClientLevel.handleBlockChangedAck`.

In 1.21.11 it always passes the stored `playerPos` to `syncBlockState`. In 26.1.2 the argument is `playerPos` only for `lastTeleportSequence < sequence`; otherwise it is `null`. Therefore equality is on the suppression side: acknowledgment sequence equal to or below the recorded teleport sequence yields `null`; only an acknowledgment sequence strictly greater than that boundary passes the saved position.

### Collision/state snap gates — corroborated

In 1.21.11 `ClientLevel.syncBlockState` first checks reference inequality between current and authoritative block state, applies the authoritative state, then snaps the current player when `this == player.level()` and `player.isColliding(pos, state)`. It uses the supplied saved vector unconditionally under those gates. In 26.1.2 the state comparison, state write, current-level test, collision test and `absSnapTo` call remain; the new leading condition `playerPos != null` is required before the player/level and collision checks. Consequently a null position still permits block-state reconciliation but cannot snap the player.

### Conditional movement consequence — corroborated as source behavior, not runtime evidence

For a reconciled entry whose block state changed and collides with the local player in that same level, 1.21.11 can snap to the entry’s stored prediction-time position. In 26.1.2 that snap is suppressed when the acknowledgment sequence is at or below the teleport boundary. When the acknowledgment sequence is greater, the stored position remains eligible, subject to the unchanged state/current-level/collision gates. A saved position can remain the original position when an existing per-block entry has its sequence updated, so eligibility does not itself guarantee that the vector is temporally fresh. No trajectory or runtime behavior was tested.

## Bounded spot-check of reported no-difference packet responses

The `handleSetEntityMotion` body matches in both releases: same-thread dispatch, entity lookup, non-null guard, and `lerpMotion` with the packet movement. The explosion handler keeps the same optional player-knockback application through `addDeltaMovement`, with the same surrounding effects ordering; the random accessor changes from `level.random` to `level.getRandom()` for sound pitch. These spot checks support those specific claims only. The broader listed handler, codec, inventory, respawn and vehicle no-difference inventory was not re-audited here.

## Limits

This audit covers the assigned report’s identity and prediction/teleport claim plus two bounded packet-response spot checks. It does not inspect server packet producers, intermediate releases, product code, runtime trajectories, or pair-wide coverage/freeze. No source tree or product files were modified.
