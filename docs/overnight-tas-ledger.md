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
1.21.4→1.21.5 adds normalized/square-adjusted keyboard input preparation.
`PreSquareInput` restores raw-axis, item/sneak, then 0.98F scaling through
1.21.4. The native 1.8.9 and 1.9.4 captures still pass after this hook:
maximum 7 ULP (`compare-1.8.9-7a03ec4752`) and exact
(`compare-1.9.4-0f3e843889`) for 200 ticks. Non-keyboard input with no
key presses currently falls back to vanilla preparation;
26.1→26.2 adds a direct-speed branch when float friction is at most the double
literal `0.6` (`0.6F` itself is slightly greater when promoted to double).
`FrictionSpeedThrough261` restores the old unconditional
grounded formula through 26.1; the 26.2 native capture remains exact after
its hook (`compare-current-34f704f5a3`). The shape, water, and pose-specific
TAS cases noted above remain pending.

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
1.18.2→1.19.4 adds an upward-motion guard to sneak-edge backoff, now split
between `UpwardFullBoxSneakEdge` and `DownwardFullBoxSneakEdge`;
1.19.4→1.20.6 replaces the full-box sneak support probe with a foot slice, so
the historical full-box rules now run through 1.20.4. These changes need a
ledge-specific TAS case;
1.20.6→1.21 changes step-up from two max-height alternatives to sorted
candidate heights. `TwoRouteStepUp` restores the older collision path through
1.20.6. Exact 1.21 source confirms the new path already exists at that
release. After the change, 1.8.9 remains within 7 ULP
(`compare-1.8.9-05e53e8874`) and 1.9.4 remains exact
(`compare-1.9.4-418a48261c`), both for 200 ticks. A multi-height step and
corner-specific native recording is still needed. `OriginalFootSlice` restores
the 1.20.5–1.21.4 foot probe's uninset X/Z bounds and `1e-5F` vertical pad;
26.2 uses `1e-7` insets. The current profile remains exact after the new
probe mixin (`compare-current-ddb738fbf9`). Collision and pose otherwise
remained structurally stable through 26.2 in the examined source methods;
these checks do not prove complete coverage.

Block-effect source checks found two additional boundaries. Powder snow
inherited ordinary fall damage in 1.17 and stopped doing so in 1.18;
`PowderSnowFallDamage` restores damage only for the 1.17 profile. Beds bounced
with a `0.66F` factor through 1.21.11 and use `0.75F` in 26.2;
`LegacyBedBounce` restores the earlier factor. `LegacyBlockRestitution` now
restores direct vertical reflection for player bed/slime contacts through
1.21.11, with sneak suppression and no bed bounce through 1.11.2. Partial
contacts and landing-specific TAS comparisons remain pending.

Early jump source correction: through 1.13, the double Y velocity is assigned
`0.42F` and then gains the float Jump Boost product; modern 26.2 computes a
float jump power and retains a larger existing Y velocity. `LegacySprintJump`
now restores the full earlier jump assignment and boost order. The original
1.8.9 recording remains within 7 ULP (`compare-1.8.9-82c7637a62`) and 1.9.4
matches exactly (`compare-1.9.4-59a7229070`), each for 200 ticks. The default
current profile also matches its native 200-tick capture exactly after the
collision and pose additions (`compare-current-a4d472c7bb`).

Soul sand source check: 1.14.4 applies a double-precision `0.4` X/Z velocity
multiplier once for every soul-sand cell overlapping the player's final AABB
during `Entity#move`, after collision resolution. In 1.15.2 and 1.16.5,
Soul Sand and Honey Block supply float `0.4F` speed factors selected from the
player's cell or the block below, also applied inside `Entity#move`. The same
move-stage factor remains through 26.2. `OverlappingSoulSandSpeed` restores
the pre-1.15 per-cell callbacks at the movement-stage speed-factor call and
suppresses the native one-block Soul Sand factor for those profiles. Honey is
left native because it did not exist in pre-1.15 maps. An earlier committed
base-tick hook was based on a mistaken reading of method boundaries and has
been corrected. The initial `build build` passed and the ordinary 1.9.4
recording stayed exact for 200 ticks (`compare-1.9.4-315137b2f4`); validation
of the corrected hook passed the 1.9.4 ordinary recording exactly for 200
ticks (`compare-1.9.4-61502f9eba`). A targeted Soul Sand case remains pending.

Revisiting the early collision source exposed an uncovered sneak-edge probe.
1.8.9 and 1.9.4 run a whole-AABB X, Z, then diagonal probe exactly one block
below the feet during `Entity#move`, with 0.05 reductions and only an on-ground
plus sneaking gate. 1.11.2 retains the whole box but switches the probe depth
to player step height and restricts movement type to SELF/PLAYER; 1.16.5
retains that whole-box geometry. `OneBlockFullBoxSneakEdge` now restores the
first rule through 1.10.2, and `GroundOnlySneakEdge` uses the shared whole-box
probe through 1.16.1. The ordinary 1.9.4 recording remains exact for 200
ticks (`compare-1.9.4-dca1a57f0d`); a ledge-specific native case is pending.
The 1.8.9 recording passes 200 ticks with maximum 7 ULP
(`compare-1.8.9-f06a432e1b`), and the default current recording remains exact
for 200 ticks (`compare-current-244f37e676`) after both corrections.

The 1.8.9→1.9.4 source pair also shows a flying-and-sneaking horizontal
input division by double `0.3` added in 1.9 and retained through 1.14.4,
then absent by 1.15.2. It is observable with item-use slowdown or small
analog inputs. `EarlyKeyboardInput` keeps the 1.8 order of sneak, item-use,
then 0.98F scaling; `FlyingSneakInput` adds the historical division between
item-use and 0.98F for 1.9–1.14 profiles. After this change, 1.8.9 passes
200 ticks with maximum 7 ULP (`compare-1.8.9-f5af4c8cf0`), and 1.9.4 is
exact for 200 ticks (`compare-1.9.4-253b2a7786`). A creative-flight and
item-use recording is still needed. The 1.9.4→1.10.2 pair adds a no-gravity
entity flag and conditional gravity, but ordinary vanilla player movement has
no path to set that flag. It remains a narrowly scoped follow-up candidate.
The default current profile remained exact for 200 ticks after the input hook
(`compare-current-ce4c3501a8`), and `build build` passed.

The decompiled 26.1, 26.1.1, and 26.1.2 source files for Entity,
LivingEntity, Player, LocalPlayer, BedBlock, SlimeBlock, and SoulSandBlock are
byte-identical across both adjacent patch pairs. No movement split is needed
for those patches based on these files. The 1.20.1→1.20.4, 1.20.5→1.20.6,
1.21→1.21.1→1.21.2→1.21.4, and 1.21.5→1.21.8→1.21.11 geometry/pose
methods showed no further changes in the examined decompiles. The 1.21.5
pose priority order changed when multiple desired poses overlap; reachability
and movement effect still need investigation. `FallFlyingFirstPose` now
restores the through-1.21.4 order (fall flying, sleeping, swimming, spin,
crouch, standing) for selected historical profiles; 1.21.5+ keeps native
sleeping/swimming-first order. This hook is small and source-backed, while an
overlapping-state native capture remains pending.
The ordinary 1.9.4 capture stayed exact for all 200 ticks after this hook
(`compare-1.9.4-062d21372d`), and `build build` passed.
The default current profile also remained exact for 200 ticks after both
soul-sand and pose hooks (`compare-current-3192fe8d44`).
