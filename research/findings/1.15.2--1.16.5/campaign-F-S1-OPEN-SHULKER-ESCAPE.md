# F-S1-OPEN-SHULKER-ESCAPE: open shulker boxes stop triggering player escape

- **1.15.2**, mojmap: `net/minecraft/client/player/LocalPlayer.java`; lines 386-440.
- **1.16.5**, mojmap: `net/minecraft/client/player/LocalPlayer.java`; lines 414-447.

## Difference

For an open shulker box with its block entity present, 1.15.2's `ShulkerBoxBlock.isSuffocating` still returns true.

When a sampled player column includes that block, `blocked` therefore treats the column as obstructed, regardless of the shulker's open state or the player's overlap with its current shape. 1.16.5's shulker registration defines suffocation as `blockEntity.isClosed()` (or true when the block entity is absent).

An open shulker is filtered out before 1.16.5 checks collision-shape intersection, so that same sample does not count as suffocating.

## Movement meaning

Shulker boxes exist in both endpoints.

LocalPlayer calls the 1.15.2 check from four horizontal samples during `aiStep`; 1.16.5 makes the corresponding suffocation-space query.

When the open shulker is the obstructing sample and adjacent queried columns are clear, 1.15.2 may write a horizontal escape velocity of magnitude 0.1 while 1.16.5 does not take that escape branch.

The conclusion depends only on source predicates, LocalPlayer call sites and block-state reachability; the comparison does not simulate damage or non-player movement.

This is a source-level difference in the local player's collision-escape impulse for open shulker boxes.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.15.2--1.16.5/findings/F-S1-OPEN-SHULKER-ESCAPE.md).
