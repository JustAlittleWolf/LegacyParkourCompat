# F-FARMLAND-PLAYER-RELOCATION: farmland conversion relocates a landed player differently

- **1.11.2**, ornithe-feather: `1.11.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java`; `1.11.2/ornithe-feather/net/minecraft/entity/Entity.java`.
- **1.12.2**, ornithe-feather: `1.12.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java`; `1.12.2/ornithe-feather/net/minecraft/entity/Entity.java`.

## Difference

Both versions route a landed entity through `Entity.move` to `checkFallDamage`; with `landed=true` and positive `fallDistance`, the selected support block receives `onFallenOn`.

For farmland, a server-side callback with `fallDistance > 1.5F` passes the random condition for every `nextFloat()` result, and a standing player passes the remaining checks: it is a `LivingEntity`, is a `PlayerEntity`, and its `0.6 x 0.6 x 1.8` volume is `0.648`, greater than `0.512`.

The helper first converts the farmland block to dirt; that block-state transition remains vanilla.

In 1.11.2, `setDirt` queries the dirt block's full collision box and calls `entity.setPosition(entity.x, box.maxY, entity.z)` for returned entities.

Dirt inherits `Block.getShape`, whose `FULL_BLOCK_SHAPE` reaches `pos.y + 1.0`.

In 1.12.2, `setDirt` queries only `SHAPE_ABOVE`, the top `0.0625` of the former farmland block, and computes `d = min(box.maxY - box.minY, box.maxY - entity.getShape().minY)` before calling `entity.teleport(entity.x, entity.y + d + 0.001, entity.z)`.

For a non-spectating standing player whose feet are at the farmland surface (`pos.y + 0.9375`), both entity queries include the player's box. 1.11.2 writes player Y as `pos.y + 1.0`; 1.12.2 computes `d = 0.0625` and writes Y as `pos.y + 1.001`. 1.12.2 also enters the inherited teleport path: it sets `teleported`, resets current/previous/last position through `setPositionAndAngles`, and calls `World.tickEntity(this,false)`.

With `requireLoaded=false`, that call skips `entity.tick()` while running the remaining world/chunk update path. 1.11.2's `setPosition` changes current coordinates and bounding box without those teleport updates.

## Movement meaning

The player movement path is `PlayerEntity` (inheriting `Entity.move`) -> landing support `BlockState` selection -> `Entity.checkFallDamage` -> `FarmlandBlock.onFallenOn` -> `FarmlandBlock.setDirt` -> `World.getEntities(null,box)` -> player position update. `World.getEntities(Entity,Box)` defaults to `EntityFilter.NOT_SPECTATOR` and does not restrict the query to a non-player entity type; the stated player is non-spectating.

The eligible direct player movement consequence is the position/position-history write.

Other entities returned by the same query are outside this finding's player-only conclusion.

The call can also be reached by farmland random ticks and neighbor/on-added callbacks; this finding is bounded to the landing path above.

Source proves different server-side player position operations under the stated landing and callback guards, including a `0.001` Y offset for the concrete standing-player case and 1.12.2's teleport bookkeeping path.

It does not establish a measured trajectory or downstream client correction.

The farmland-to-dirt state change itself and non-player relocation are not movement behaviors claimed by this finding.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.11.2--1.12.2/findings/F-FARMLAND-PLAYER-RELOCATION.md).
