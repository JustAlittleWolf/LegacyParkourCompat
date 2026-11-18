# Independent audit: early direct player pitch applicability

Status: **COMPLETED — ACCEPTED WITH PROVENANCE CORRECTION**

## Bound identity

Audited the immutable candidate `c-external-early-pitch/early-player-pitch-applicability.md`, SHA-256 `53d70d381d0b8c270200fb83cbb7159038d197e6c0ffa2745dbc03ab1d64db0c`, against exact local decompiles for 1.7.10, 1.8.0–1.8.9, and 1.9.0. Reused the already accepted 1.8.9→1.9 direct-correction audit at `development/research/source-evidence/20261118/block-c-grouped-source-closures/early-player-packet-authority/verdict.md` (which binds its reviewed comparison hash as `2a072c27e8b4aa6da478b0c8c0a4891283438e9161f2725bf78102230976e43b`). No wiki, product implementation, Git history, tests, build, launch, or source generation was consulted or performed.

## Verdict

The direct finite player-correction pitch result is accepted: **1.8.0 through 1.8.9 apply relative PITCH by float addition to the current player pitch, pass the resolved float to `updatePositionAndAngles`, and the setter ultimately applies Java float remainder `pitch % 360.0F`; it does not clamp to ±90.** The correction response reads the resulting `player.pitch`. For a finite witness, current pitch `80.0F` plus finite relative delta `40.0F` yields `120.0F`, and the immediate position-and-angle response contains `120.0F`. This is a direct local correction application/result, not a claim that the server produces or admits the witness.

The accepted 1.9.0 evidence establishes the upper transition: its correction path still resolves relative pitch by addition, but `Entity.updatePositionAndAngles` first clamps the finite resolved pitch to `[-90.0F, 90.0F]`, then writes angle state and calls the same modulo wrapper. Thus `120.0F` becomes `90.0F` before the response. This agrees with the accepted 1.8.9→1.9 verdict.

The 1.7.10 sample also preserves finite absolute pitch `120.0F` through the modulo setter and response, but its correction packet has no relative-angle flag resolution. It establishes only that the no-clamp finite result exists in this sampled release; it cannot establish 1.7-relative semantics or the lower introduction boundary. No unsampled versions or snapshots are inferred.

## Exact source checks

All source paths below are relative to `decompiled_minecraft/<release>/ornithe-feather/`.

- 1.7.10: `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java`, `handlePlayerMove` lines 480–493; `net/minecraft/entity/Entity.java`, `setRotation` lines 210–213 and `updatePositionAndAngles` lines 858–876. Handler takes absolute packet pitch, calls the setter, then responds from packet pitch; setter writes pitch then calls `% 360.0F`.
- 1.8.0 and 1.8.1: handler `handlePlayerMove` begins at line 525 and resolves PITCH/YAW flags, calls `updatePositionAndAngles`, then sends `player.pitch`; entity `setRotation` and `updatePositionAndAngles` contain the same no-clamp/remainder order. Full handler/entity payloads differ between .0 and .1, but their targeted operation is unchanged.
- 1.8.2–1.8.9: handler `handlePlayerMove` begins at line 517; entity `updatePositionAndAngles` is at lines 876–893 in 1.8.9 and `setRotation` at 221–223. Both files are byte-identical throughout this sampled range: handler SHA-256 `7fa3d587325068eaa158526cb59e4850b704ac237938df6592d1a09148db40c3`; Entity SHA-256 `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b`.
- 1.9.0: `ClientPlayNetworkHandler.handlePlayerMove` begins at line 545; `Entity.updatePositionAndAngles` starts at line 985 and clamps pitch before its state writes and `% 360.0F` setter. The accepted 1.9.0–1.9.4 method review is in the bound audit above.

For 1.7.10 and 1.8.0–1.8.8, local `.decompile-provenance.json` manifests exist and their recorded handler and Entity source hashes match the files inspected. The candidate incorrectly states that the 1.7.10 tree and all 1.8 trees lack provenance sidecars. Specifically, 1.7.10 and 1.8.0–1.8.8 have matching nested manifests; 1.8.9 is the endpoint with no nested manifest (the accepted prior audit documents its outer sidecar provenance). This provenance wording correction does not change the behavioral verdict. The 1.9.0 handler/entity hashes also match their nested manifest.

## Scope limits

This is only finite pitch handling in the inbound direct local-player correction and its immediate angle response. Coordinates, yaw behavior, packet wire details/acknowledgment, server origin/admission, general look, other player responses, gameplay movement, runtime parity, unsampled releases, and snapshots are outside this verdict. No 26.2 comparison was needed to establish the bounded historical result.

