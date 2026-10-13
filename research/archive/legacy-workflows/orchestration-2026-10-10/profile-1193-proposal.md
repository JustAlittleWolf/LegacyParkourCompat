# Proposal: represent the 1.19.3 Honey contact-scan boundary

## Decision requested

Represent the accepted 1.19.2 → 1.19.3 stable-release change with the repository's existing version-group and closest-change patterns. The smallest profile structure that preserves the two sampled releases is:

```java
V1_19("1.19", "1.19.1"),
V1_19_2("1.19.2"),
V1_19_3("1.19.3"),
V1_19_4("1.19.4"),
```

Register the existing `InsideBlockContactBehavior` operation with an independent 1.19.2 change returning `0.001`, and a 1.19.3 change returning `1.0E-7`. This is a normal enum/profile split and two ordinary registrations. It does not require a `ChangeResolver` algorithm change, a new hook, a new mixin, a provider, or a change to the static-catalog model.

The proposal covers the source-confirmed sampled releases 1.19.2 and 1.19.3. Because 1.19 and 1.19.1 would continue to select the closest later 1.19.2 registration, exact source for those releases is needed before claiming their contact-scan behavior is also `0.001`. If that source differs, the same profile-group pattern can add the required boundary; no core refactor follows from that possibility.

## Immutable basis and limits

- Finding snapshot: `b66cde90c5f34970aa94553f838ec8c65e764ea6`, `workflows/source-campaign-2026-10-07/1.19.2--1.19.3/findings/F-010-honey-contact-scan-boundary.md`, blob `46614ff4d6498f729e7dba61abdaed0e011b6bc3`, SHA-256 `c37c6544856bd4dde35e36801383fe9249044ad4c02341984a8ae8205e217039`.
- Independent R3 acceptance: `dded99fb6d78d4d253ab7f367edc029e981b6b91`, `workflows/source-campaign-2026-10-07/1.19.2--1.19.3/reviews/F-010-honey-contact-scan-boundary-independent-r3-2026-10-10.md`, blob `deec2af60b2e8c6b6ea2dd67043971c8d3c126b7`, SHA-256 `7f54077b4570180d26f0f9a4028bae7ffee1f0dd5582a82c7330a5828608ba3d`.
- Admission report: `2d58da8ccbd49eff97f5f7b9a8a681f67f4f7766`, `workflows/orchestration-2026-10-10/ordinary-movement-admission-wide.md`.
- Accepted stable boundary: `Entity.checkInsideBlocks()` insets all AABB bounds by `0.001` in 1.19.2 and `1.0E-7` in 1.19.3. The canonical stable manifest puts 1.19.2 directly before 1.19.3. The finding supplies a source-produced `/tp` → local gravity → Honey callback witness for the extra cell visit. The Honey callback and shape are unchanged.
- The accepted scope does not prove an ordinary walking approach, runtime behavior, or the first development snapshot/pre-release containing the change. The latter remains unknown. The 1.19.2–1.19.3 pair remains partial.

## Why one new key is insufficient

`ChangeResolver` filters changes to registrations whose `emulates()` version is at or after the selected profile, then chooses the earliest such registration independently for each hook. With only a new `V1_19_3` contact registration, selection `V1_19` would resolve that later registration and use `1.0E-7`; a `V1_19` old-inset registration would lose because `V1_19_3` is the closer applicable entry. That reproduces the accepted 1.19.3 sample but regresses the accepted 1.19.2 sample, which is still mapped to `V1_19`.

The two-key split provides the necessary nearer old-side candidate and the new-side transition:

| Selected Minecraft release | `ParkourVersion` after split | Closest applicable contact registration | Inset |
|---|---|---|---:|
| 1.19.2 | `V1_19_2` | `V1_19_2` | `0.001` |
| 1.19.3 | `V1_19_3` | `V1_19_3` | `1.0E-7` |
| 1.19 / 1.19.1 | `V1_19` | `V1_19_2` unless a nearer earlier profile is evidenced and added | `0.001` under this proposal; historical confirmation pending |
| 1.19.4 | `V1_19_4` | `V1_21` (no 1.19.4 contact registration) | `1.0E-7` |

The 1.19.4 row describes current resolver routing, not a new source claim about the 1.19.4 contact scan. If an exact 1.19.4 source comparison is needed for that operation, record it separately.

## Concrete implementation surface

### Enum, aliases, and selection plumbing

- In `api/ParkourVersion.java`, change `V1_19` aliases from `1.19`, `1.19.1`, `1.19.2`, `1.19.3` to `1.19`, `1.19.1`; insert `V1_19_2("1.19.2")` and `V1_19_3("1.19.3")` before `V1_19_4` to preserve chronological enum ordering.
- `BY_PATCH`, `of`, `id`, `patches`, `displayLabel`, `selectable`, and `next()` derive from enum constants and alias lists. Their algorithms need no change; the generated 1.19 group label and `next()` relation naturally change as a consequence of the new constants.
- Client UI options are created from `ParkourVersion.selectable()` and `displayLabel()`. `MovementVersions` resolves inputs from enum IDs/patch aliases and stores the canonical `id()` after list selection. Server `movementVersion` parsing and the force-version payload also use `ParkourVersion.of`. No hand-maintained UI or config alias table was found; those components should need no semantic edit. Existing `1.19.2` and `1.19.3` strings will become distinct valid selections automatically.
- All profile groups are presently partial by default; both added keys should retain that constructor behavior. Do not mark either complete.

### Contact hook and static catalog

- Keep `InsideBlockContactBehavior`, its existing mixin dispatch, and native fallback unchanged.
- Add independent change classes under `change/v1_19_2/` and `change/v1_19_3/`, each implementing `InsideBlockContactBehavior` and annotated with its matching `ParkourVersion` key. The former returns the accepted 1.19.2 inset `0.001`; the latter returns the accepted 1.19.3 inset `1.0E-7`.
- In `MovementChangeCatalog`, call `registerV1_19_2` and `registerV1_19_3` in chronological order and register `InsideBlockContactBehavior` once in each matching method. Keep the existing `V1_18_2` and `V1_21` contact entries; the resolver needs all boundary candidates to choose the closest applicable one.

### Existing 1.19 registrations and semantic audit

The existing `registerV1_19` block has four registrations that will now resolve for the newly distinct 1.19.2 and 1.19.3 selections unless superseded by a nearer registration for the same hook:

- `BoatPassengerFluidPush` → `BoatPassengerFluidPushBehavior`.
- `SprintFallFlyingGate` → `SprintFallFlyingGateBehavior`.
- `SprintStart` → `SprintStartBehavior`.
- `VehicleSprint` → `VehicleSprintBehavior`.

Do not move or duplicate these registrations as part of the contact-scan patch. Keep `registerV1_19_4` unchanged; its `SprintStart` registration remains the closest applicable sprint-start entry for 1.19.3 and later. `HistoricalRideables` also uses `V1_19` as the first profile for mangrove-era rideables; adding later enum keys preserves `selected.newerThanOrEqual(V1_19)` for 1.19.2/1.19.3.

The profile split changes which selected enum value reaches these existing behaviors. The implementation owner should bind their 1.19.x operation boundaries against exact source before claiming new 1.19/1.19.1 coverage, especially the `HistoricalRideables` era gate and any release-sensitive predicates. Available 1.19.3 → 1.19.4 findings are samples at those endpoints; they do not establish behavior for 1.19 or 1.19.1. Do not infer those missing source boundaries from the enum order or resolver.

## Evidence still needed before broad applicability claims

1. Inspect exact stable 1.19 and 1.19.1 sources for `Entity.checkInsideBlocks()` and compare the inset, scan bounds, and call ordering with accepted 1.19.2/1.19.3 evidence. This decides whether `V1_19` can safely inherit the 1.19.2 contact registration or requires another group/registration.
2. For each existing `V1_19` hook above, verify exact 1.19 and 1.19.1 source against the existing 1.19.2 and 1.19.3 witness sources where the handler's behavior depends on selected-version gates or release availability. Update only the affected group boundary if a real transition is found.
3. Verify the 1.19.3-to-1.19.4 source boundary for `SprintStart` remains the one already represented by `V1_19_4`; do not use that separate boundary as evidence for the Honey contact scan.
4. Keep development pre-release onset unknown unless those source publications are separately inspected. No runtime trajectory is established by the finding.

These are bounded source follow-ups. They do not imply a `ChangeResolver` rewrite. A core version-resolution refactor is neither required nor proposed; if future evidence shows the existing closest-applicable model cannot encode a specific operation's behavior, stop and request approval before proposing that refactor under the repository's AGENTS guidance.

## Scope of this proposal

This is a reviewable implementation shape only. It does not alter Java, claim pair completion, or claim runtime parity. The ordinary split adds profile values and applies the existing per-hook nearest-registration rule; the accepted F-010 witness remains the evidence for the 1.19.2/1.19.3 contact inset.
