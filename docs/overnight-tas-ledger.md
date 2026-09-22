# Overnight TAS compatibility ledger

Started 2026-09-22 from `a94fafb` on `fix/overnight-tas-ulp-compat`.
ULP infrastructure checkpoint: `315981a`.
The temporary acceptance threshold is **at most 10 ULP on each finite X, Y,
and Z coordinate of every tick, with exact tick count**. This is not bit-exact
compatibility or a declaration that a version is complete. Default comparison
remains exact. Native capture differences are evidence, not emulation failures.

## Major-release rough-pass sequence

One exact TAS representative per major release, oldest first; compare adjacent
representatives only. Derived from `ParkourVersion`, pinned Forge releases, and
the Fabric runner ranges:

`1.8.9 → 1.9.4 → 1.10.2 → 1.11.2 → 1.12.2 → 1.13.2 → 1.14.4 → 1.15.2 → 1.16.5 → 1.17.1 → 1.18.2 → 1.19.4 → 1.20.6 → 1.21.11 → 26.2 (current)`

`1.13.2` is pinned but currently fails TAS client compilation; runner repair
is being investigated. `26.1` versions are selectable but the TAS runner
currently launches only the exact 26.2 current release. Neither is counted as
validated. After the rough pass reaches current, revisit minor/patch
boundaries in chronological order.

## Evidence and integration order

| Pair / run | Source review and Change coverage | TAS evidence | Gap |
| --- | --- | --- | --- |
| 1.8.9 → 1.9.4 math | `LivingEntity.tickMovement/travel`, `PlayerEntity.jump`, `ClientPlayerEntity.tickMovement`, `KeyboardInput.tick`, and `Entity.updateVelocity` in both decompiles. 0.005→0.003 negligible-speed boundary covered by `NegligibleSpeed`. Restored float ground/air acceleration through 1.13, one-ULP 1.13 constant split, and float sprint-jump impulse. Split 1.8 versus 1.9 float yaw conversion order in `Entity.updateVelocity`. 1.8.9 600-tick sprint timeout and flying-sneak input restoration remain candidates. | Initial 200-tick current-vs-native run `compare-1.8.9-459ac84954` failed tick 4 (X=5,580,975; Y=0; Z=1,365,931), snapshot `parkourgym-server/run/logs/latest.log:156` (flat stone). After the float math and diagonal-operand fix, `compare-1.8.9-44f6acd005` **passed 200 ticks, maximum 5 ULP**. Following the later source corrections and dedicated sprint rule, the original manual native reference passed 200 ticks, observed maximum **7 ULP** (`compare-1.8.9-0088751962`). Native original-vs-replay has 1-ULP Z differences from tick 6. | Passing sample is not version completeness; validate targeted terrain. |
| 1.8.9 → 1.9.4 collision | `Entity.move`, player dimensions, client entity push in both decompiles. Core Y→X→Z, step and sneak-edge logic match. Standing pose and client push already covered. | No isolated collision recording yet. | Validate dynamic dimension transition and headroom. |
| 1.8.9 → 1.9.4 blocks | Ladder, lily pad, piston head, cocoa, anvil, chest, panes in both decompiles; existing V1_8 Changes cover found shapes. | No isolated block recording yet. | Targeted audit only; test facings and states. |
| 1.9.4 → 1.10.2 math | `LivingEntity.travel`, `ClientPlayerEntity.tickMovement`, `Entity.updateVelocity` in both decompiles. Ordinary math unchanged; 1.10 no-gravity flag and auto-jump added. Auto-jump suppression for earlier profile covered by `NoAutoJump`. | Native 1.9.4 and 1.10.2 200-tick captures succeeded with identical positions. Current 1.9.4 profile run `compare-1.9.4-dcd57626aa` first failed tick 21 on Z: expected 14.544677589088678, actual 14.542255886630212; snapshot `parkourgym-server/run/logs/latest.log:292`. Trace `logs/trace-194.log:243-244` proved no tick-20 collision clip; it revealed Z velocity below `0.003` that old per-axis cancellation clears while modern's vector threshold keeps it. Per-axis cutoff through 1.21.4 moved first failure to tick 78; restoring pre-1.18 strict sprint cancellation after wall contact moved it to tick 93. Old double `-0.15` downward ladder clamp corrected that to tick 132, where box-based position accumulation mattered. A versioned pre-1.17 bounding-box position update produced **200/200 exact XYZ positions, observed maximum 0 ULP**, runId `compare-1.9.4-3094d7d355`; the dedicated sprint-rule refactor also passed 200/200 at 0 ULP, runId `compare-1.9.4-a1287120d7`. Current profile against native current reference likewise passed 200/200 at 0 ULP, runId `compare-current-b53f65d3da`. Static `short=false` piston geometry and 1.9 ladder geometry match modern. | This sample does not prove full version coverage. No-gravity rare state lacks Change. |
| 1.9.4 → 1.10.2 blocks | `FarmlandBlock` in both decompiles: World collision becomes 15/16 instead of full cube; existing `FullFarmland` covers old shape. | No farmland-specific recording. | Validate collision and version gating. |

The 1.8.9→1.9.4 pair passed its basic recording gate. The 1.10.2 selected
profile also matched its native 200-tick capture exactly (observed maximum 0
ULP, `compare-1.10.2-6d774940f6`). These source reviews remain rough passes
and leave each historical profile partial.

Minor/patch source pass: 1.8→1.8.9, 1.9→1.9.4, 1.10→1.10.2,
1.11→1.11.2, 1.12→1.12.2, and 1.13→1.13.2 yielded no concrete
movement-math, general collision, pose, or older-block shape/effect deltas in
the compared methods. The 1.8 sprint timeout remains 600 ticks in 1.8.9;
1.9 and 1.9.4 both omit it. `TimedSprint` now restores that 600-tick timeout
only for 1.8 profiles. The original 1.8.9 native capture still passes 200
ticks after this change (maximum 7 ULP, `compare-1.8.9-391dbb4678`). The 1.9 mapped-source travel guard is not yet
proven equivalent to 1.9.4's renamed guard, although their arithmetic matches.

Later rough-pass source findings awaiting chronological integration: 1.11.2
cocoa age-2 collision is restored by existing `BuggedCocoaCollision`; 1.11.2
bed fall damage before the 1.12 bounce is restored by `FullBedFallDistance`
for player landings on beds;
1.13.2 introduces water sprint/drag/gravity differences; `LegacyWaterTravel`
now restores the 1.12.2 water branch for player water travel while leaving
lava on vanilla. This is source-verified, but a water-specific native TAS
capture, including Depth Strider levels, is still needed. 1.13.2→1.14.4
bed/cauldron underside shape changes are now represented by `SolidBedCollision`
and `WholeBaseCauldronCollision`, with the older 5/16 cauldron shape
preserved through 1.12.2. Shape-specific TAS coverage is pending. The
1.13.2 blocked-resize pose handling is now restored by
`KeepPoseWhenResizeBlocked`; the basic 1.9.4 capture still matches all 200
ticks exactly (`compare-1.9.4-2c79a8c804`). A blocked-clearance TAS case is
still needed;
1.21.4→1.21.5 adds square input preparation;
26.1→26.2 adds the direct-speed branch at friction <=0.6. These are evidence
leads, not integrated fixes.

Further rough-pass leads: the 1.14 fixed jump base and 1.15–1.16 block
jump-factor multiplier, float Jump Boost arithmetic, direct Y assignment, and
float sprint impulse are now restored by `FixedJumpPower` and
`BlockFactorFloatJump`. The 1.17–1.19 double Jump Boost addition and direct
Y assignment are restored by `DoubleBoostJump`; 1.20.1–1.20.4 use float jump
power with Boost included (`FloatJumpThrough1204`); 1.20.5–1.21.1 use the
jump-strength attribute but still assign Y directly (`DirectJumpThrough1211`).
Decompiled 1.21.2 starts retaining a higher current Y, matching modern. These
later jump rules have source and build verification but no targeted native TAS
captures;
1.18.2→1.19.4 adds an upward-motion guard to sneak-edge backoff;
1.19.4→1.20.6 replaces the full-box sneak support probe with a foot slice;
1.20.6→1.21.11 changes step-up from two max-height alternatives to sorted
candidate heights. Collision and pose otherwise remained structurally stable
through 26.2 in the examined source methods. These are leads for ordered
implementation, not claims of complete coverage.

Early jump source correction: through 1.13, the double Y velocity is assigned
`0.42F` and then gains the float Jump Boost product; modern 26.2 computes a
float jump power and retains a larger existing Y velocity. `LegacySprintJump`
now restores the full earlier jump assignment and boost order. The original
1.8.9 recording remains within 7 ULP (`compare-1.8.9-82c7637a62`) and 1.9.4
matches exactly (`compare-1.9.4-59a7229070`), each for 200 ticks. The default
current profile also matches its native 200-tick capture exactly after the
collision and pose additions (`compare-current-a4d472c7bb`).
