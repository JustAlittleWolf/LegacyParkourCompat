# Bubble Column movement persists after its support is removed in B

- Older version A: `1.16.5`
- Newer version B: `1.17.1`
- Mechanic / coverage slice IDs: `S3-BUBBLE-COLUMN-PROPAGATION`, `S3-04-TRAVEL-BRANCH-DEPENDENCIES`
- Classification: support-loss recovery timing changes bubble-column vertical movement
- Confidence: source-confirmed
- Applicability: existing Bubble Column with a player inside when its supporting block changes to a block that cannot support the column
- First changed release: unknown within (1.16.5, 1.17.1]
- Runtime validation: not performed

## Paired evidence

- A `world/level/block/BubbleColumnBlock.java`, SHA-256 `49E6DDC09F2D99056B0940FF0178C02BD0FE62D7758936EEF751A3B9500F2BA5`, handles a neighbor update in `updateShape(...)`. If `blockState.canSurvive(...)` is false, it immediately returns `Blocks.WATER.defaultBlockState()` before scheduling a tick. The neighbor update applies that returned replacement. Its `canSurvive(...)` accepts only another Bubble Column, Magma Block or Soul Sand below.
- B `BubbleColumnBlock.java`, SHA-256 `70AF7C91F54EAB13B32B75BA87C72374E03B51572A175440E8CD166CEFF97394`, schedules a block tick after five ticks when `!blockState.canSurvive(...)`, then returns the ordinary `updateShape(...)` result without replacing the Bubble Column immediately. At the scheduled tick, B's `updateColumn(...)` uses the current state and support below; for an unsupported column, `getColumnState(...)` selects water and the routine propagates that state upward through the existing column/source-water run.
- Both versions' `BubbleColumnBlock.entityInside(...)` call the same `Entity.onInsideBubbleColumn(...)` or `onAboveBubbleCol(...)` method based on whether the block above is air. Both `Entity.java` sources implement the same vertical velocity adjustment; inside a column this also resets `fallDistance`. Their source hashes are recorded in the run manifest.

## Source-level difference

When the support changes to an unsupported block, A removes the Bubble Column state during the neighbor update. B keeps the column state until its scheduled five-tick repair, then converts the unsupported column and its connected upper run to water. If a player overlaps the column during that interval, B continues applying the bubble-column vertical velocity behavior while A has already stopped doing so.

## Consequence and uncertainty

The different state and callback window is source-confirmed and can affect player vertical motion. No particular trajectory, block-removal mechanism or observed gameplay result is asserted. Runtime validation was not performed. The finding covers support-loss recovery only; stable column generation, direction changes and other propagation cases remain open.

## Handoff

Keep the broader `S3-BUBBLE-COLUMN-PROPAGATION` and `S3-04` travel-path audits open. This is a bounded historical movement difference in a block present in both versions.
