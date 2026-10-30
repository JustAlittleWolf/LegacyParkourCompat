# Independent audit: ordinary pose correction

## Verdict

**REVISE — neither movement candidate is accepted.** The correction supplement accurately records the candidate-specific 1.14.4 `updatePlayerPose()` gate and binds to the stated immutable revisions. Read together, the corrected claims are conditional and appropriately narrow at source level. The gate is not global to every pose write in the release: reset, death, and sleep lifecycle code also writes poses outside `updatePlayerPose()`. Those transitions are outside the two ordinary sneaking/fallback candidates. Collision/query and concrete reachability dependencies remain unresolved, so neither behavioral claim is accepted.

## Immutable revision binding

The supplied SHA-256 values match the four files reviewed:

- `ordinary-tick/source-review.md`: `f9daef67079078add9ad621c6ef279e8275ae687b00a11184873fc6e00c1d128`
- `ordinary-tick/player-sneaking-pose-height.draft.md`: `2774b0e7e45d042cc2d2a236df179807325c40bba0fb13082b580b14ecf43d16`
- `ordinary-tick/constrained-player-pose-fallback.draft.md`: `9e857b97768b5dba8b03851f878d8e0f4c99ad05761e390eeba88345ed90eaf0`
- `ordinary-tick/correction-supplement.md`: `679a5605972826dc3203717200f9c125e5834b83db8e9357177da0b8e1825481`

The source report's selected 1.13.2/1.14.4 `PlayerEntity.java` hashes and 1.14.4 `Entity.java` hash match the exact read-only source files and their source-manifest entries. They are `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633`, `2614deb3b50d11f6cdf54d9e308703bf49cb53b72646b05253857e9557d657df`, and `7315a496c195da767de9d4936d3adb6efc3c419dc0f0e95d6f32781b0da1ba55`, respectively.

## Source check and corrected claims

In 1.14.4, `PlayerEntity.tick()` calls `updatePlayerPose()` at line 281. Within that method, the outer `if (m_87715359(Pose.SWIMMING))` at lines 343–370 encloses candidate desired-pose selection, fit/fallback decisions, and `m_59065105(pose7)` application. `m_87715359` tests `world.hasNoCollisions(this, m_16672198(pose))` (`Entity.java:1628–1629`); the requested pose box is built from that pose's dimensions at the current position (`Entity.java:2300–2305`). Thus each sneaking or constrained-fallback pose update performed by this method first requires a passing 0.6 × 0.6 swimming-pose query. If it fails, that tick does not select or apply a candidate pose. The supplement states this candidate-specific condition correctly. In 1.13.2, `updatePlayerPose()` instead computes the target dimensions and tests that target box directly before `setSize` (`PlayerEntity.java:334–360`); its tick caller is line 254.

This is not a global gate on all 1.14.4 pose writes. `PlayerEntity.resetPos()` writes `Pose.STANDING` at line 478; `LivingEntity` writes `Pose.DYING` in death handling at line 1154 and writes `Pose.SLEEPING` / `Pose.STANDING` in `startSleeping()` / `stopSleeping()` at lines 2727 / 2754. The entity data callback invokes dimension application (`Entity.java:2238–2249`) when the pose data changes; that callback itself is not guarded by the swimming-fit predicate. These call sites do not create the sneaking or blocked-pose fallback claims under review, but any unqualified assertion that all pose changes/applications are behind the outer gate would be false.

1. **Sneaking height:** source dimensions are 0.6 × 1.65 in the old sneaking branch and 0.6 × 1.5 for 1.14.4 `Pose.SNEAKING`. The corrected movement claim is conditional: in the 1.14.4 ordinary pose-update path, the swimming-pose box must first fit, and the selected sneaking target must pass its fit/query; a 1.5–1.65-block clearance witness must also be admitted by the exact collision path. The supplement supplies the outer condition omitted from the standalone draft. The dimensions delta is confirmed; the tight-clearance movement result remains unaccepted.
2. **Constrained fallback:** source order is confirmed: after the outer swimming-pose gate, the desired pose is selected; for a non-spectator, non-riding player whose desired pose does not fit, sneaking is tested, then swimming is selected if sneaking does not fit. Swimming dimensions are 0.6 × 0.6. The corrected behavioral claim requires all those tests to resolve in that order for a concrete blocked-player state. It remains unaccepted pending that witness and query closure.

## Remaining gate

For both candidates, close `World.hasNoCollisions` and its entity/block shape providers for the exact current-position pose boxes, then establish a concrete reachable witness through the ordinary player tick and collision movement. For sneaking height, show that the swimming outer-gate box and 1.5-high sneaking box pass while the corresponding old 1.65-high box cannot. For fallback, show the swimming outer gate passes, desired and sneaking fits fail, the 0.6-high fallback applies at tick end, and the next travel tick can move through the obstruction. Until those checks close, these are provisional source candidates only, not accepted findings or pair freeze.

This audit did not inspect product code/history, wiki material, backlog, or mixed-lane data, and did not run a build, tests, launches, decompilation, or downloads.
