# F-005: landing emits HIT_GROUND on vibration-occluding blocks in 1.19.2

- **1.18.2**, mojmap: `1.18.2/mojmap/net/minecraft/world/entity/Entity.java`; lines 543-674.
- **1.19.2**, mojmap: `1.19.2/mojmap/net/minecraft/world/entity/Entity.java`; lines 547-679.

## Difference

For a grounded landing with positive accumulated fall distance on a state in `OCCLUDES_VIBRATION_SIGNALS`, 1.18.2 calls the block's `fallOn()` but does not dispatch `HIT_GROUND`; 1.19.2 calls `fallOn()` and dispatches the event with explicit entity, position and current-foot-block context.

On other landing states, 1.18.2 also dispatches the event, but through `gameEvent(GameEvent.HIT_GROUND)` without the explicit context arguments shown in 1.19.2.

The collision caller and `checkFallDamage()` branch preserve the same grounded/fall-distance guards and fall-distance reset order.

## Movement meaning

Client player movement -> `Entity.move()` resolves the post-collision support block and grounded flag -> `checkFallDamage()` -> version-specific `HIT_GROUND` dispatch.

F-003 is the separate `STEP` event difference in the same movement path.

Source-proven: 1.19.2 dispatches a `HIT_GROUND` event for grounded landings with positive fall distance even when the support state is tagged to occlude vibration signals; 1.18.2 suppresses that event for those states.

The dispatch itself does not write player position, velocity or pose. 1.18.2 later event listener could change world state, but no such response or movement consequence is claimed here.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.18.2--1.19.2/findings/F-005.md).
