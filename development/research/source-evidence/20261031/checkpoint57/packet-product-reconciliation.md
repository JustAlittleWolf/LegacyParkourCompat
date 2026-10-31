# Product reconciliation: 1.19 packet movement findings

## Scope and immutable inputs

Read-only reconciliation against the product at HEAD `61bdfaed`. Accepted source drafts:

- `1182-1194-finish50/protocol_nonclientbound/vec-delta-position-rounding.md` — SHA-256 `95f07ebe0373988fbe62560bdbc0b8b914e4a2c352309b23a9cb3e4eebff3efa`.
- `1182-1194-finish50/protocol_nonclientbound/local-player-control-gate.md` — SHA-256 `ddfa7b209c53d9b125c9e66b2605147d4a48d18b83145e2f31726686f8d0f5ef`.
- `1182-1194-finish50/independent_full_pair_audit/audit.md` — SHA-256 `38ab3b0702ad6a47a5a1a38493d2a842f65caa7cadc0060d802ced9a6641d9bb`.

All three hashes were recomputed in the shared checkout. No product source was changed. No build, test, launch, or decompile was run.

## Existing operation coverage

| Existing operation | Product coverage | 1.19 finding disposition |
|---|---|---|
| Relative-move interpolation target | `ClientRelativeMoveTargetHook`; `RelativeMoveLongPacketTarget` at `change/v1_16`; dispatched by `ClientPacketListenerMovementMixin` after native `VecDeltaCodec.decode`. Captures packet deltas, then optionally queues a LocalPlayer target. | Covers the older 1.16 long-coordinate target only. The 1.19.2 floor-vs-round codec reconstruction is a pre-target decode operation and remains uncovered. |
| Teleport target / received interpolation | `ClientTeleportTargetHook`, `ClientReceivedInterpolationHook`, `TeleportNearCoordinateFallback`, `QueuedEntityPacketInterpolation`; current receiver wiring is in `ClientPacketListenerMovementMixin` and `LivingEntityPacketInterpolationMixin`. | Existing route covers the 1.16 historical queue only. It does not emulate the 1.19.3/1.19.4 control predicate or its tick-side queue/base reset. |
| Packet authority gates | Current redirects replace `isLocalInstanceAuthoritative()` for `handleMoveEntity` and `handleEntityPositionSync` only when the LocalPlayer has legacy target/interpolation hooks and `handlesLegacyPackets` is true. The 1.15 `NativeReceivedPacketTargets` barrier deliberately returns false for that policy; the 1.16 change returns true. | Correctly preserves the existing 1.15/1.16 split, but later profiles have no resolved target hook and therefore retain current authority. This leaves 1.17 through 1.19.3 without the accepted old LocalPlayer packet route. |
| Tick interpolation and rotations | `LivingEntityPacketInterpolationMixin` substitutes only the old queued-packet interpolation at `isInterpolating()`/`InterpolationHandler.interpolate()`; `EntityLegacyRotationMixin` exposes old direct rotation writes/modulo finish. Correction pitch/rotation/position have separate hooks. | Queue arithmetic and correction rotation hooks do not implement the changed LocalPlayer control gate. The accepted `LivingEntity.aiStep` branch that conditionally clears interpolation and synchronizes codec base has no corresponding hook. |
| Native teleport/correction packet helper | `handleTeleportEntity` redirect clears a stale old queue for LocalPlayer then invokes `setValuesFromPositionPacket`; `ClientPacketListenerMixin` scopes player-correction rotation/position behavior. | Keep distinct from the tracked-entity packet route. The prior accepted packet-target reconciliation maps the historical tracked-entity teleport response to current `handleEntityPositionSync`; do not alter new player-correction relative flags/velocity behavior as a side effect. |

## Minimal uncovered seams and composition

1. **Relative codec reconstruction (floor to round).** Add an independently resolved decoder/reconstruction operation at the `VecDeltaCodec.decode` call before the result is used to update the codec base and select the movement target. The existing decode redirect already captures `(xa, ya, za)` but currently returns the native decoded target; the existing `ClientRelativeMoveTargetHook` runs later and represents the separate 1.16 long-coordinate operation. Keep those contracts separate. The historical decoder must use the pre-decode codec base and return its reconstructed target so the normal subsequent base update follows that result. The v1.16 target override may continue to choose its separate legacy long-coordinate target; do not collapse the two coordinate state systems.

2. **LocalPlayer control predicate, packet response and tick reset.** Add a dedicated independently resolved control/eligibility operation, dispatched at the current equivalents of the old `isControlledByLocalInstance` uses: relative receive (`handleMoveEntity`), tracked absolute/teleport receive (`handleEntityPositionSync`), and the `LivingEntity.aiStep` branch that clears pending interpolation and synchronizes codec base. The accepted finding describes one changed predicate with multiple callers; preserve each caller's guards and only change the LocalPlayer result. In `aiStep`, do not clear or advance the separate 1.16 custom queue through the modern queue accidentally: its existing `ClientReceivedInterpolationHook` owns that state, while the new gate controls the vanilla/current interpolation and base-sync branch.

   Preserve the native barrier: selection through 1.15.1 must keep current authority; 1.15.2–1.16.1 must retain the existing legacy false authority/queue route; 1.16.2–1.19.3 must receive the accepted old false predicate; 1.19.4+ must retain current true behavior. Do not infer policy from the presence of a relative/teleport target hook alone. The old tracked-entity teleport source branch maps to current `handleEntityPositionSync`; current `handleTeleportEntity` remains the distinct player correction path described above.

## Version grouping and hook aliases

- `ParkourVersion.V1_19` already aliases 1.19, 1.19.1, and 1.19.2; the floor decoder behavior should be attached at the earliest applicable group (`emulates = V1_19`) if those earlier source bodies retain the audited floor semantics. The 1.18.2→1.19 codec-class introduction was audited equivalent to the old packet-coordinate arithmetic and must not be reintroduced as a separate change.
- `ParkourVersion.V1_19_3` is a single-release profile and is the natural source boundary for the old LocalPlayer control result. `V1_19_4` is already separate. No new profile enum alias is indicated.
- Existing `ClientRelativeMoveTargetHook`, `ClientTeleportTargetHook`, and `ClientReceivedInterpolationHook` stay independently resolved as 1.16 operations. A new codec-reconstruction hook and a new control/eligibility hook must not be registered as aliases of those target/queue operations; automatic discovery should register each operation's own `@MechanicType` interface. If the control operation supersedes old policy versions on resolution, give the 1.15 native barrier an explicit native variant and keep the 1.16 old result distinct.
- Existing correction-position/rotation and forced-rotation-pitch hooks are separate from packet target rotation. No additional rotation hook or release-group split is indicated by these two findings.

## Handoff disposition

Uncovered product work is limited to the decoder reconstruction seam and the LocalPlayer control result at the three packet/tick consumers, with explicit preservation of the existing 1.15 barrier and 1.16 queued target state. This is a reconciliation report only; implementation requires the sanitized independent candidate workflow.
