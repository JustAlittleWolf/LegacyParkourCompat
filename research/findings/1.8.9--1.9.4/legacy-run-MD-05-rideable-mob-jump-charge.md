# MD-05: Rideable-mob jump charge updates local mount movement

- **1.8.9**, ornithe-feather: `1.8.9/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`; lines 606-617.
- **1.9.4**, ornithe-feather: `1.9.4/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`; lines 727-740.

## Difference

On a rideable jump mount, 1.9.4 sets the local mount's jump strength from the charge before sending the jump action. 1.8.9 sends the charge to the server but leaves the local mount's jump-strength field untouched at that point.

The mount's own tick consumes this field when calculating its launch velocity, so the locally predicted horse movement can differ until server state is applied.

## Movement meaning

The gate requires riding an entity that implements `JumpingMount` and reports `canJump()`, and a jump-button falling edge.

The held-input duration determines `horseJumpSize`; this is not a generic player jump change. 1.8.9 and 1.9.4 both send a charge in the riding-jump action, and this finding concerns the additional local setter call in 1.9.4.

It does not assert a difference in server-authoritative jump handling.

Source confirms that 1.9.4 updates the local mount's strength before its next movement tick and that this field scales the horse's launch velocity. 1.8.9 locally predicted mount jump can therefore use a different charge-derived impulse.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.8.9--1.9.4/findings/MD-05-rideable-mob-jump-charge.md).
