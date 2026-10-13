# F001: Sneak-edge support probe depth changes

- **1.10.2**, ornithe-feather: `1.10.2/ornithe-feather/net/minecraft/entity/Entity.java`; lines 468–512.
- **1.11.2**, ornithe-feather: `1.11.2/ornithe-feather/net/minecraft/entity/Entity.java`; lines 519–562.

## Difference

For an on-ground sneaking PlayerEntity moving horizontally, 1.10.2 searches for support one full block below; 1.11.2 searches only one stepHeight below.

The local living travel path invokes 1.11.2 with `MoverType.SELF`.

Default stepHeight is 0.6F in both versions.

## Movement meaning

Local player tick -> LivingEntity travel -> Entity.move; the entity collision shape is checked through world.getCollisions.

Snow layer collision height is unchanged: a two-layer snow block has top at 0.125 above its block base.

At feet height 1.0 over such a neighbor, the 1.10.2 -1.0 probe can see the snow while 1.11.2 -0.6 cannot.

This is an illustrative source-derived precondition, not a tested trajectory.

When this mismatch occurs, 1.11.2's sneak edge guard can trim horizontal displacement earlier.

Other surrounding movement branches were not exhaustively compared.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.10.2--1.11.2/findings/F001.md).
