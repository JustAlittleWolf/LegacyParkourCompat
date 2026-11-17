# Final rebind: water sprint drag applicability

## Verdict

**Accepted for the bounded claim in the corrected comparison.** The wrapper chronology is now accurate: Feather 1.13–1.14 `PlayerEntity.moveRelative` performs the swimming pre-step on Y and delegates to `super.moveRelative`; the base `LivingEntity.moveRelative` still supplies the water factor. The earlier verdict remains byte-for-byte unchanged.

For the assigned releases, a non-flying local player already in the eligible water branch, with sprinting true, Depth Strider 0, and no Dolphin’s Grace, selects `f = 0.9F`. The factor is selected from `isSprinting()`, then Depth Strider is processed, then Dolphin’s Grace may overwrite `f`, then movement runs, and X/Z are multiplied by `f` after the move. Under the bounded no-modifier witness, that remains `0.9F` throughout the checked interval. No source-confirmed horizontal drag change is present.

## Scoped wrapper verification

In exact Feather sources, `PlayerEntity.moveRelative` is at 1.13 `:1435–1460`, 1.13.1/.2 `:1442–1467`, and 1.14 `:1363–1390`. The `isSwimming() && !isRiding()` pre-step assigns only the vertical velocity component using the look-angle value and vertical velocity. The non-flying witness then reaches `super.moveRelative(...)`; the wrapper does not assign X or Z. The inherited LivingEntity water operation still selects the sprint factor at 1.13 `LivingEntity.java:1600–1624`, 1.13.1/.2 `:1619–1643`, and 1.14 `:1877–1904`.

The corrected comparison’s post-1.14 wrapper references also match the inspected source: `PlayerEntity.travel` in 1.14.1/.2 `:1362–1390` and 1.14.3 `:1364–1391`, then Mojmap `Player.travel`. Those swimming pre-steps likewise adjust Y before delegating. No local-player-specific wrapper changes the horizontal factor.

## Enclosing gates and applicability

The base movement routine’s outer control gate is `isLocallyControlled() || isLogicalSideForUpdatingMovement()` in 1.13–1.14; `canMoveVoluntarily() || isLogicalSideForUpdatingMovement()` in 1.14.1–1.14.3; and `isEffectiveAi() || isControlledByLocalInstance()` in 1.14.4–1.16.1. The non-water branch is gated by `!isInWater() || (Player && abilities.flying)` in 1.13–1.14 and 1.14.4–1.15.2; 1.14.1–1.14.3 use `!isInsideWater() || (PlayerEntity && abilities.flying)`. In 1.16–1.16.1 the water branch is explicitly gated by `isInWater() && isAffectedByFluids() && !canStandOnFluid(fluidState.getType())`; for the stated ordinary player, the fluid-affect predicate is true and the inherited stand-on-fluid predicate is false. These gates bound which water states enter the operation; they do not alter its sprint multiplier once entered.

The assigned later interval retains the same conclusion: the 1.14.4 base-water getter rename returns `0.8F` but does not affect the sprint selector, and the 1.16 falling adjustment preserves X/Z (for sprinting it returns the original vector). The verdict makes no claim about all shallow-water, unloaded-chunk, or mode-transition states, and it does not establish runtime parity.

## Evidence identity

- Corrected comparison: `lane-sprint-water-drag-continuity/comparison.corrected-r1.md`, SHA-256 `9113cfd333bd13a457780157ed7b0122cb34a4a9daa22e0d98c890581d670ffe` (verified before and after review).
- Frozen exact-source roster: 56 scoped `LivingEntity`, `Entity`, player-wrapper, and client-player files; sorted relative-path-plus-file-hash digest `ac85569c6afbe29aed4b582f187263fda0d1337a4a2c2b215505a8cfaee2a912` (unchanged). The operation and player-wrapper file hashes match the exact-version source records.
- Prior verdict preserved unchanged: `verdict.md`, SHA-256 `f7ef31b95dab4452a02f875c27b9352c4aecb8cd9af43135e44b51330b332de0`.

No source files were modified or regenerated. No product files, tests, builds, game/runtime artifacts, or network sources were used.