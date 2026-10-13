# F-S1-OPEN-SHULKER-ESCAPE: open shulker boxes stop triggering player escape

1.15.2, mojmap: `1.15.2/mojmap/net/minecraft/client/player/LocalPlayer.java`, `checkInBlock`, lines 386–440.
1.16.5, mojmap: `1.16.5/mojmap/net/minecraft/client/player/LocalPlayer.java`, `moveTowardsClosestSpace`, lines 414–447.

Difference: For an open shulker box with its block entity present, 1.15.2's `ShulkerBoxBlock.isSuffocating` still returns true. When a sampled player column includes that block, `blocked` therefore treats the column as obstructed, regardless of the shulker's open state or the player's overlap with its current shape. 1.16.5's shulker registration defines suffocation as `blockEntity.isClosed()` (or true when the block entity is absent). An open shulker is filtered out before 1.16.5 checks collision-shape intersection, so that same sample does not count as suffocating.

Movement meaning: Shulker boxes exist in both endpoints.

Original evidence: [archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.15.2--1.16.5/findings/F-S1-OPEN-SHULKER-ESCAPE.md).
