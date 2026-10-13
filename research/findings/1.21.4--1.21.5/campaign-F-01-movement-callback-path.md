# F-01: Block contact callbacks follow accepted axis movement segments

- **1.21.4**, mojmap: `net/minecraft/world/entity/Entity.java`; lines 627-712.
- **1.21.5**, mojmap: `net/minecraft/world/entity/Entity.java`; lines 636-733.

## Difference

For movement that changes more than one coordinate, 1.21.5 records the accepted displacement as sequential axis-aligned segments, using the collision result's axis order.

The end-of-travel block scan checks each segment, so the traversed contact path can differ from 1.21.4's caller endpoint movement.

Block callbacks still run during the scan in both versions, but 1.21.5 queues enumerated effects in movement-step order and applies them after scanning; 1.21.4 callback effects are immediate.

The new fluid callback path schedules water extinguishing and lava ignition, while lava hurt is also scheduled after ignition; damage outcomes are outside this comparison's movement scope.

The same player travel reaches `LivingEntity.aiStep`, which calls `applyEffectsFromBlocks` after travel on the authoritative client or server path (1.21.4 lines 2785-2787; 1.21.5 lines 2769-2771). `Entity.move` is called by player travel and supplies the 1.21.5 movement segments.

## Movement meaning

Local player tick -> `LivingEntity.aiStep` -> `travel` / `travelRidden` -> `Entity.move` -> recorded movement -> `applyEffectsFromBlocks` -> `checkInsideBlocks` -> block callback.

Directly movement-affecting callbacks include honey slide velocity adjustment and powder snow stuck movement multiplier. 1.21.5 additionally uses each block's entity-aware inside collision shape and the `BlockGetter.forEachBlockIntersectedBetween` ordering.

Source confirms the movement path representation, enumerated-effect ordering and added fluid-contact route.

It therefore changes which player path segments are tested and when queued callback effects are committed.

Direct callback code can still change movement state during the scan.

Which individual contacts differ depends on the collision result, axis order, block shapes and callback.

No specific changed trajectory has been measured.

Health, fire damage and other damage outcomes are excluded.

Server player corrections have separate movement recording callers and are not evidence for the local client path.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.4--1.21.5/findings/F-01-movement-callback-path.md).
