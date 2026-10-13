# F002: Piston movement bypasses the newer sneak-edge guard

- **1.10.2**, ornithe-feather: `1.10.2/ornithe-feather/net/minecraft/entity/Entity.java`; lines 468–512.
- **1.11.2**, ornithe-feather: `1.11.2/ornithe-feather/net/minecraft/entity/Entity.java`; lines 519–562.

## Difference

1.10.2's sneak-edge collision probe is applied to player movement regardless of caller. 1.11.2 skips it for piston-type movement.

## Movement meaning

Piston movement entity query -> MovingBlockEntity.moveEntities -> Entity.move.

The precondition is a grounded, sneaking player moved by a piston near an edge.

1.11.2 can apply piston displacement without the sneak-edge horizontal trimming performed by 1.10.2.

Exact position outcome depends on collision geometry and was not simulated.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.10.2--1.11.2/findings/F002.md).
