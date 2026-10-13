# F-SOUL-SAND-SPEED: Soul Sand applies a new horizontal velocity factor

- **1.14.4**, mojmap: `net/minecraft/world/entity/Entity.java`; lines 533-550.
- **1.15.2**, mojmap: `net/minecraft/world/entity/Entity.java`; lines 533-544.

## Difference

For a player moving normally on Soul Sand, 1.14.4 retains the post-collision horizontal delta movement. 1.15.2 multiplies X and Z velocity by 0.4 after the block-contact callback; Y is unchanged.

Soul Sand exists in both releases, so this changes an existing map block's player movement.

## Movement meaning

Player `LivingEntity.travel` calls `Entity.move(SELF, deltaMovement)` in ordinary ground travel. `Player#getBlockSpeedFactor()` in 1.15.2 returns the superclass factor when neither ability flying nor fall-flying, and returns 1 otherwise (`Player.java` lines 2027-2029 The movement-delta conclusion therefore applies to a grounded/non-flying player whose current/support lookup resolves to Soul Sand.

The source proves the factor is applied to stored horizontal velocity after movement/callback processing.

Exact client/server consequences depend on their respective movement ticks and block position lookup.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.14.4--1.15.2/findings/F-SOUL-SAND-SPEED.md).
