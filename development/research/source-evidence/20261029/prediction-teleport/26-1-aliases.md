# 26.1–26.1.2 block-prediction teleport-sequence closure

## Scope and method

Bounded source-only follow-up to `.native-source/20261028/frontier-c/12111-2612-responses/source-review.md` (input SHA-256 recorded in `SHA256SUMS.txt`). Examined only the canonical `26.1`, `26.1.1`, and `26.1.2` `unobfuscated` source trees for block-prediction sequence state, the teleport marker, acknowledgment reconciliation, authoritative block-state updates, and the local-player position writer. No product code, product history, wiki material, source generation, builds, tests, or launches were examined. The current checkout top level was verified as `C:\Users\Wolfi\.codex\worktrees\56a8\LegacyParkourCompat`; no Git mutation was performed.

Hash-first check: for every examined Java file, its SHA-256 matched its entry in that version's `unobfuscated.sources.sha256`. Each source-manifest SHA-256 also matched the hash in the corresponding ready marker. The seven relevant Java files have identical content hashes in all three releases; hashes and sidecar identities are listed in `SHA256SUMS.txt`.

## Exact source identity

All three canonical trees are at `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\unobfuscated`, use the published `unobfuscated` namespace, and have ready markers naming their exact `versionId` with 6,882 source files.

| Release | Exact run / ready identity | Source-manifest SHA-256 | Client artifact identity in sidecars |
|---|---|---|---|
| 26.1 | `versionId=26.1`; resolved metadata ID `26.1`; metadata SHA-256 `68e8d6e01e06e6a9a740a7ad474061f9303b004ceedb8224b085a58616390e49`; status `ready` | `b77289c135ac6e12aa35e75177cea52d968f49d49e1ec745e510b1a786c9aeba` | client SHA-1 `191771837687b766537a8c4607cb6fad79c533a1`; client JAR SHA-256 `bc6194c61566b587f196090d730698f2191170f7580adb143e4ff839939d3840` |
| 26.1.1 | `versionId=26.1.1`; resolved metadata ID `26.1.1`; metadata SHA-256 `86b232149a2ae224b06443730fe32c46c306f035a1599e3477c5237df1c950ef`; status `ready` | `88afdfb8264842713f8cce86c0bf88e95c2c991ee706349d6ed8b6c22d52fcb3` | client SHA-1 `377031a9e733ba8ab4d355959a8f6fb8eb707556`; client JAR SHA-256 `8c109be6177f40d10d0afd820b06542f9609597874242b96e16c5b0dc9e1fef3` |
| 26.1.2 | `versionId=26.1.2`; provenance records a separate exact `--versions=26.1.2 --mappings=unobfuscated` run; ready marker says `versionMetadataId=26.1.2`, status `ready` | `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` | Client JAR SHA-256 `b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0` is recorded in the ready marker. The 26.1.2 provenance JSON does not contain client SHA-1 or `versionMetadataSha256`; no values are inferred. |

The exact release identities are distinct; equality below is limited to the examined source files and does not establish whole-release aliasing.

## Sequence and caller chain

The relevant source bodies and line numbers below are identical in 26.1, 26.1.1, and 26.1.2, as shown by their equal source hashes.

- `BlockStatePredictionHandler.java:14-16,18-25` initializes `currentSequenceNr` to zero and `lastTeleportSequence` to `-1`. `startPredicting` increments the sequence (`:52-56`). `retainKnownServerState` records the current local-player position with a newly retained server state (`:18-25`); if that block position is already pending, it advances that entry's sequence while retaining the entry (`ServerVerifiedState.setSequence`, `:80-86`).
- `MultiPlayerGameMode.startPrediction` (`:293-299`) opens prediction, takes the incremented sequence, runs the local predictive action with that sequence, and sends the resulting packet. Its call sites (`:163,184,240,277,330,389`) cover creative/survival block-break start and completion/continuation paths plus `useItemOn` and `useItem`. These are the direct local prediction producers in this source tree.
- `ClientLevel.setBlock` (`:203-209`) captures the prior block state and `minecraft.player` while prediction is active. The client packet listener supplies authoritative block states through `setServerVerifiedBlockState` for section updates (`ClientPacketListener.java:849`) and single-block updates (`:941`); `ClientLevel.java:181-185` updates a pending entry or writes directly when none is pending.
- A server position correction enters `ClientPacketListener.handleMovePlayer` (`:796-807`). It applies the received position only when the local player is not a passenger (`:798-801`), sends teleport acceptance and the current position (`:803-805`), then unconditionally calls `onTeleport()` (`:806`). Thus the marker is also advanced when this handler took the passenger branch.
- `onTeleport` records exactly `currentSequenceNr` in `lastTeleportSequence` (`BlockStatePredictionHandler.java:66-68`). Acknowledgments dispatch through `ClientboundBlockChangedAckPacket.handle` (`:26-28`), `ClientPacketListener.handleBlockChangedAck` (`:2449-2451`), and `ClientLevel.handleBlockChangedAck` (`:161-166`) to `endPredictionsUpTo`.
- `endPredictionsUpTo` removes each pending state with `serverVerifiedState.sequence <= sequence` and calls `syncBlockState`, passing its saved position only when `lastTeleportSequence < sequence` (`BlockStatePredictionHandler.java:38-48`). Therefore an ACK at or below the latest teleport marker passes `null`; an ACK strictly later than that marker can pass the saved position.
- `ClientLevel.syncBlockState` (`:187-195`) still writes a differing authoritative block state (`:188-190`) in either case. The positional response is gated by non-null `playerPos`, `this == player.level()`, and `player.isColliding(pos,state)` (`:191-194`). Only then does it call `player.absSnapTo` with the stored coordinates. The three-argument writer is `Entity.absSnapTo` (`Entity.java:1695-1702`): it clamps X/Z to ±`3.0E7`, stores the old-coordinate fields, and calls `setPos`. `ClientLevel.syncBlockState` is called only from `BlockStatePredictionHandler.endPredictionsUpTo` in the examined client sources; `onTeleport` is called only from `ClientPacketListener.handleMovePlayer`.

## Result and applicability gate

The stale-position suppression is already present in **26.1**, and the same bounded implementation is byte-identical in **26.1.1** and **26.1.2**. Thus 26.1 is the first confirmed applicable release in the requested three-release sample. The author report's 26.1.2 endpoint is not the first point within this sample where the sequence boundary exists.

Applicability is conditional: a position-correction packet records the current block-prediction sequence; when an acknowledgment reconciles one or more pending predicted block states at a sequence less than or equal to that marker, those states still reconcile normally, but cannot restore their saved local-player position. A later acknowledgment (sequence greater than the marker) can still supply the saved position, and `syncBlockState` can snap the player only if the block differs, the local player remains in that client level, and the new state collides with the player.

This comparison does not establish the historical introduction release before 26.1. The earliest verified release here is 26.1; narrowing the pre-26.1 boundary would require another exact source release outside this assignment. The source-only author report documents the older 1.21.11 versus 26.1.2 contrast, but this follow-up makes no claim about intermediate releases or the precise change point before 26.1.