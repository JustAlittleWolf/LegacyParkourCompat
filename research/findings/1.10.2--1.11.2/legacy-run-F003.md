# F003: Piston displacement is accumulated and capped per world tick

- **1.10.2**, ornithe-feather: `1.10.2/ornithe-feather/net/minecraft/entity/Entity.java`.
- **1.11.2**, ornithe-feather: `1.11.2/ornithe-feather/net/minecraft/entity/Entity.java`; lines 464–500.

## Difference

1.11.2 maintains per-entity piston displacement state for the current world tick and limits cumulative movement per axis. 1.10.2 has no corresponding mover-specific accumulation in its move entry point.

## Movement meaning

MovingBlockEntity -> piston-typed Entity.move calls.

Applies when multiple piston movement requests for the same entity and tick accumulate on an axis.

1.11.2 caps total piston displacement on that axis in the tick, potentially changing position compared with separate untyped moves in 1.10.2.

Exact interaction with piston collision callbacks was not simulated.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.10.2--1.11.2/findings/F003.md).
