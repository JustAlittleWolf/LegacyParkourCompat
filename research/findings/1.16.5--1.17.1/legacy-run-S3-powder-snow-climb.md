# Powder snow adds a player climb assist

- **1.16.5**, mojmap: `1.16.5/mojmap/net/minecraft/world/entity/LivingEntity.java`; line 2067.
- **1.17.1**, mojmap: `1.17.1/mojmap/net/minecraft/world/entity/LivingEntity.java`; lines 2155–2165.

## Difference

1.17.1 broadens the climb-assist predicate to `(horizontalCollision || jumping) && (onClimbable || feetBlockIsPowderSnow && canEntityWalkOnPowderSnow(entity))`.

When true it returns a vector preserving X and Z while setting Y to `0.2`.

For a player, the powder-snow predicate can be satisfied by wearing leather boots in the feet slot. 1.16.5 has no powder-snow block or branch.

## Movement meaning

Player travel calls the shared living movement helper.

The precondition is that the player's feet block is powder snow, the player is wearing leather boots, and either horizontal collision or jumping is active.

The 1.17.1 helper checks entity type tags first, then recognizes leather boots for living entities.

The cited 1.17.1 client-jar tag excludes players from its entity-type list; the equipment path is sufficient for the stated player case.

The source-proven 1.17.1 result is a vertical velocity component of `0.2` from this helper under the stated conditions. 1.16.5 lacks the block/state entirely.

Powder snow was not a block/state an 1.16.5-era map could contain, so this is recorded as modern-only and does not imply historical behavior for 1.16.5 maps.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.16.5--1.17.1/findings/S3-powder-snow-climb.md).
