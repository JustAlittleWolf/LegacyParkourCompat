# Independent source audit verdict

Verdict: **ACCEPT**

Audited claim: `build/mcpk-20261116/vd-full-checklist/correction-applicability-middle/source-facts.md`
Claim SHA-256 (exact file bytes): `14c2295d202a32d6465f742e6c19b42a011fd66697afa8b3fa045c774afcc058`.

## Identity checks

For each sampled release, the provenance JSON names the reported exact `versionId` and mapping namespace. SHA-256 values of each source file, its `*.sources.sha256` manifest, and its `*.provenance.json` match the report. In all five source manifests, the exact relative source path and recorded file digest match the source bytes. The 1.13.2 provenance names the two Feather artifacts stated in the report. The 1.20.5 and 1.20.6 Java files are byte-identical (109,512 bytes and SHA-256 `e121e998ec25211aaecf91fffd0ea699cebd47eddea9134efd3f2ffbef8eb7cd`); their provenance IDs, manifest hashes, and provenance hashes remain distinct as reported.

## Behavior and ordering checks

- **1.13.2**, `ClientPlayNetworkHandler.handlePlayerMove`, lines 621–662: main-thread scheduling precedes player lookup; each relative XYZ flag adds its packet value to the current coordinate and does not assign that velocity component; each absolute axis assigns `0.0` to its component. Rotation adjustments precede `updatePositionAndAngles`, then teleport acknowledgment and response sends. The called `Entity.updatePositionAndAngles` (lines 1096–1119) writes/clamps coordinates and rotations but does not write velocity. No vehicle predicate or dismount appears in the receiver.
- **1.16.5**, `ClientPacketListener.handleMovePlayer`, lines 671–739: each relative axis takes the captured old velocity component and offsets position; each absolute axis uses packet position and zero velocity. Old-position fields are updated while computing X/Y/Z, before the `tickCount > 0 && getVehicle() != null` removal gate. `setPosRaw`, `xo/yo/zo`, and delta movement are then written in that order, followed by relative rotations, `absMoveTo`, acknowledgment, and response. The reported detachment condition and ordering are exact.
- **1.19.4**, same receiver, lines 605–676: relative axes retain the captured component; absolute axes choose zero. Position-history/interpolation fields are updated per axis; `setPos` precedes `setDeltaMovement`, followed by rotations and acknowledgment/response. No receiver-level vehicle predicate or dismount is present.
- **1.20.5 and 1.20.6**, same receiver, lines 618–689: both bodies have the same per-axis retention/zeroing and state-write sequence as reported: per-axis history fields, `setPos`, `setDeltaMovement`, rotations, acknowledgment, response. No receiver-level vehicle predicate or dismount is present.

The report's conclusions stay bounded to these exact samples and do not assert continuity or an unsampled cutover. No material discrepancy found.

Audit was read-only against the report and local decompiled sources. No product implementation, Git history, network, decompilation, build, tests, or game execution was used.