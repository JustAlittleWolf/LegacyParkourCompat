# Incremental pose-dimensions review

Reviewed implementation tip `f45f7a57424f824871e19daacc250b58b955a00a` on `feat/movement-impl-1-8-9-1-9-4`, specifically commits `4a95574`, `efc1ee0`, and `f45f7a5`, against the accepted physical pose findings. This tip is not yet the integrated `feat/movement-completeness-integration` branch. The review is static; no build, tests, game, server, or runtime check was run.

## Summary

One confirmed important issue remains in live profile changes: an epoch-triggered dimension growth can install a larger player AABB inside a tight ceiling without checking collision. Ordinary pose transitions still use the native fit path, subject to the accepted collision-query-context limitation. The resolver ranges and native fallbacks otherwise compose as intended. The historical eye-height behavior is not covered by the accepted physical-size correction and is explicitly left without a parity claim below.

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

## Confirmed finding: live profile growth skips collision fit

**Confidence: confirmed. Severity: important, limited to a live profile switch while crouched in tight clearance.** In `src/main/java/me/wolfii/legacyparkourcompat/mixin/LivingEntityMixin.java:68-88`, the epoch hook resolves the new dimensions for the current pose and calls `player.refreshDimensions()` whenever width, height, or eye height differs. Exact 26.2 `Entity.refreshDimensions()` (`Entity.java:3393-3408`) assigns the new dimensions/eye height and reapplies position without a collision check; its position-fudge path is guarded to exclude `Player`.

Concrete trigger: a player is already in native V1_14/CURRENT CROUCHING dimensions (0.6×1.5) in a 1.5-block clearance, then global or per-player selection changes to V1_13. The epoch hook resolves CROUCHING to 0.6×1.65 and refreshes immediately, so the AABB intersects the ceiling. Later in the same player tick, native `Player.updatePlayerPose()` begins by checking whether SWIMMING fits; for a non-swimming, shift-key-down V1_13 player that candidate is also 1.65, so the check can fail and the selector leaves the pose unchanged. This means the native fit query does not protect the live epoch-driven resize.

Smallest correction: defer a dimension *growth* on the epoch path until the candidate box fits, keeping the refresh pending so it is retried on a later player tick; dimension shrink can continue to refresh immediately. The code was not changed in this review. This finding applies to the earlier epoch-refresh change (`52a1620`) composed with the new 1.13 dimensions, rather than to ordinary pose selection alone.

## Eye-height scope caveat

The V1_12 and V1_13 CROUCHING implementations call `.withEyeHeight(vanilla.eyeHeight())` (their source locations are `SwimmingDimensions.java:14-16` and `SneakingDimensions.java:15-20`). In exact current 26.2 `Avatar.POSES`, native CROUCHING eye height is 1.27 (`Avatar.java:31-34`). Exact older 1.9.4 and 1.12.2 `PlayerEntity.getEyeHeight()` returns 1.54 while sneaking; 1.13.2 also returns 1.54 while sneaking or at height 1.65. Therefore the commits correct the physical box height while retaining the modern crouching eye height. The accepted F002 is bounded to physical pose sizing/selection and lists eye-height/fluid checks as open dependencies; this report makes no eye-height, fluid-state, or complete pose-parity claim. If those behaviors are part of the requested completeness boundary, they need separate accepted evidence and review.

## Six review angles

1. Requirement: the new historical physical heights and 1.14 native boundary are wired to the expected nearest registrations; live profile growth is unsafe in the stated tight-space trigger.
2. Correctness: the live refresh finding above is confirmed; no additional resolver/fallback error found.
3. Missing paths: global and per-player profile changes both advance the epoch and resolve per entity; the epoch resize path lacks a collision-fit gate for growth.
4. Conventions: dimension behavior remains one implementation per mechanic key with explicit version registrations; no duplicated movement loop was added.
5. Visibility: player collision dimensions change as intended for historical profiles; no permission or protocol visibility changes in these commits.
6. Security: no new input or trust boundary is introduced by these dimension changes.

## Not verified

- No runtime pose transition, live profile switch, collision witness, build, or test was executed.
- Eye-height/fluid consequences are source-different but outside the accepted physical-sizing boundary; no broader parity decision is made.
- The current selection and collision context do not establish arbitrary-world parity with the historical `hasNoCollisions(null, box)` path.
