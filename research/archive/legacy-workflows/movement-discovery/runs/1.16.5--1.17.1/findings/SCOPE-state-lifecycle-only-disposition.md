# Scope disposition: Bubble Column and Turtle Egg lifecycle differences

- Older version A: `1.16.5`
- Newer version B: `1.17.1`
- Related source records: [Bubble Column support-loss timing](S3-bubble-column-support-loss-delay.md), [Turtle Egg callback state](S5-turtle-egg-stale-callback-state.md)
- Disposition: world/block-state lifecycle differences only; excluded from player-movement emulation
- Runtime validation: not performed

## Equivalent-state comparison

- Bubble Columns in both versions have an empty collision shape. Their reviewed `entityInside(...)` callbacks pass the `DRAG_DOWN` state to the same `Entity.onInsideBubbleColumn(...)` / `onAboveBubbleCol(...)` behavior. The corresponding A and B entity methods use the same vertical velocity math. For equivalent Bubble Column states and the same player contact, no movement callback or collision-shape difference was found.
- Turtle Egg `getShape(...)` selects the one-egg or multi-egg shape from `EGGS` in both versions using the same rule. For an equivalent egg-count state, no shape-provider difference was found.

## Lifecycle differences retained as source evidence

- Bubble Column neighbor updates and scheduled ticks produce a different period/state after the support is lost; see the paired source evidence in the Bubble Column record.
- Turtle Egg landing callbacks can leave a different egg-count state because B reuses a state snapshot; see the paired callback evidence in the Turtle Egg record.

Both differences change which vanilla block state exists and therefore may change downstream contact/shape outcomes. That consequence does not establish a movement delta for equivalent vanilla block states. Under the project scope, retain the lifecycle observations for audit context and exclude them as implementation candidates.

## Superseding note

This scope disposition supersedes the applicability and consequence classification in the two related source records. Their committed source snapshots remain immutable; the run catalog should treat this document as the current scope decision.
