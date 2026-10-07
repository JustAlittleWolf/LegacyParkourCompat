# Incremental pose-dimensions review

Reviewed implementation tip `f45f7a57424f824871e19daacc250b58b955a00a` on `feat/movement-impl-1-8-9-1-9-4`, specifically commits `4a95574`, `efc1ee0`, and `f45f7a5`, against the accepted physical pose findings. This tip is not yet the integrated `feat/movement-completeness-integration` branch. The review is static; no build, tests, game, server, or runtime check was run.

## Summary

No blocker was found for steady-state historical pose/profile correctness in this range. A separate live profile switch can expand the player AABB into a low ceiling; because historical versions had no in-game profile switching, transition safety is an undefined policy and remains a documented limitation. Ordinary pose transitions still use the native fit path, subject to the accepted collision-query-context limitation. Resolver ranges and native fallbacks otherwise compose as intended. Eye-height-dependent movement consumers remain an unvalidated follow-up; camera/rendering parity is excluded.

## Resolver and hook composition

`PlayerDimensionsBehavior` is the single `player.dimensions` key. `ChangeResolver` groups registrations by `MechanicKey`, excludes registrations older than the selected version, and picks the earliest applicable registration. Only one of the four dimension implementations runs for a profile. `SwimmingBehavior` is a separate `player.swim` key.

| Selected profile | Resolved dimensions behavior | Swimming state / fallback |
|---|---|---|
| V1_8 | `v1_8.SneakingDimensions`; CROUCHING and non-swimming SWIMMING return standing width/height | V1_12 `SwimmingState` still applies and returns false; modern swimming pose is not requested |
| V1_9–V1_12 | `v1_12.SwimmingDimensions`; CROUCHING is 0.6×1.65; SWIMMING fallback is 1.65 while shift is held, otherwise 1.8 | V1_12 `SwimmingState` returns false, so this SWIMMING size handles the native selector's forced fallback |
| V1_13 | `v1_13.SneakingDimensions`; CROUCHING is 0.6×1.65; non-swimming SWIMMING uses 1.65 while shift is held, otherwise 1.8 | V1_12 swimming change no longer applies; native `isSwimming()` preserves actual in-water swimming dimensions, while the custom branch only handles `!player.isSwimming()` |
| V1_14 and later historical selections | `v1_14.NativePoseDimensions` returns the native dimensions, superseding older registrations for this key | Native swimming and pose behavior |
| CURRENT or disabled / non-player | No historical hook resolves; original dimensions and state are returned | Native behavior |

`MovementRuntime.find` checks per-entity enablement and `profileFor(entity)`, so per-player overrides resolve the same table independently of the global selection. `LivingEntityMixin` only applies the dimension hook to `Player`; `PlayerMixin` routes the swimming hook on the `Player.isSwimming()` return. No extra implementation of either hook exists in the reviewed source tree.

The earlier V1_8 `SneakingDimensions` registration continues to win at V1_8. It handles the non-swimming SWIMMING pose itself by returning standing width/height; it does not fall through to native 0.6×0.6 or the V1_12 dimensions hook. The exact source and forced-crawl trace for this composition is in `integrated-review-d7b56bf.md`.

## Ordinary pose-fit path and accepted limit

The code does not replace `Player.updatePlayerPose()` or its fit query. Exact 26.2 source first requires the SWIMMING pose to fit, then tries the desired pose, then CROUCHING, then the SWIMMING fallback. Its candidate check calls `level.noCollision(this, candidateBox.deflate(1.0E-7))`. Exact 1.13.2 source instead derives target width/height from fall-flying, sleep, swimming/spin, sneak, or standing state; it applies the size only when `world.hasNoCollisions(null, box)` succeeds. The 1.13.2 source finding accepted this query-context difference and conditional tight-space outcomes, with no arbitrary-world or trajectory parity claim. The new physical dimension values leave the native 26.2 pose selector and query context in place; that is the bounded limitation, not evidence of general collision parity.

## Confirmed limitation: live profile growth skips collision fit

**Confidence: confirmed. Severity: important, limited to a live profile switch while crouched in tight clearance.** In `src/main/java/me/wolfii/legacyparkourcompat/mixin/LivingEntityMixin.java:68-88`, the epoch hook resolves the new dimensions for the current pose and calls `player.refreshDimensions()` whenever width, height, or eye height differs. Exact 26.2 `Entity.refreshDimensions()` (`Entity.java:3393-3408`) assigns the new dimensions/eye height and reapplies position without a collision check; its position-fudge path is guarded to exclude `Player`.

Concrete trigger: a player is already in native V1_14/CURRENT CROUCHING dimensions (0.6×1.5) in a 1.5-block clearance, then global or per-player selection changes to V1_13. The epoch hook resolves CROUCHING to 0.6×1.65 and refreshes immediately, so the AABB intersects the ceiling. Later in the same player tick, native `Player.updatePlayerPose()` begins by checking whether SWIMMING fits; for a non-swimming, shift-key-down V1_13 player that candidate is also 1.65, so the check can fail and the selector leaves the pose unchanged. This means the native fit query does not protect the live epoch-driven resize.

Disposition: this is not a blocker to integrating steady-state historical pose corrections, but it limits any claim that live profile changes are collision-safe in arbitrary tight spaces. No minimal native-consistent correction is established: deferring the requested profile conflicts with the next-tick effective-change promise, relocation invents transition behavior, and immediate application can overlap. Historical source defines no in-game transition policy. Do not silently skip a requested profile or relocate the player; keep the limitation explicit pending a policy decision. This applies to the earlier epoch-refresh change (`52a1620`) composed with the new 1.13 dimensions, not to ordinary pose selection alone.

## Eye-height scope caveat

The V1_12 and V1_13 CROUCHING implementations call `.withEyeHeight(vanilla.eyeHeight())` (`SwimmingDimensions.java:14-16`; `SneakingDimensions.java:15-20`). Current 26.2 native CROUCHING eye height is 1.27 (`Avatar.java:31-34`); exact older 1.9.4 and 1.12.2 sources use 1.54 while sneaking, and 1.13.2 uses 1.54 while sneaking or at height 1.65. These commits correct physical box height while retaining modern eye height. Camera/rendering parity is excluded from this review. Eye-height-dependent physical and fluid consumers remain an unvalidated movement follow-up, not automatically out of scope; this report makes no parity claim for those consumers.

## Six review angles

1. Requirement: the new historical physical heights and 1.14 native boundary are wired to the expected nearest registrations; live profile growth is unsafe in the stated tight-space trigger.
2. Correctness: the live refresh finding above is confirmed; no additional resolver/fallback error found.
3. Missing paths: global and per-player profile changes both advance the epoch and resolve per entity; the epoch resize path lacks a collision-fit gate for growth.
4. Conventions: dimension behavior remains one implementation per mechanic key with explicit version registrations; no duplicated movement loop was added.
5. Visibility: player collision dimensions change as intended for historical profiles; no permission or protocol visibility changes in these commits.
6. Security: no new input or trust boundary is introduced by these dimension changes.

## Not verified

- No runtime pose transition, live profile switch, collision witness, build, or test was executed.
- Eye-height-dependent physical and fluid consumers remain unvalidated movement follow-up; camera/rendering parity is excluded. No parity conclusion is made for those consumers.
- The current selection and collision context do not establish arbitrary-world parity with the historical `hasNoCollisions(null, box)` path.
