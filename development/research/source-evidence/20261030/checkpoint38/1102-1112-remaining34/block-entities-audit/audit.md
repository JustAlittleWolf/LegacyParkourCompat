# Independent source audit: block entities and piston head

## Verdict

**Revise the report's piston witness; the endpoint collision change is supported.** The stated horizontal example depends on `MovingBlockEntity.progress == 0.8F`. That value satisfies the short-head predicate and the collision arithmetic as written, but it is not reached by the ordinary local piston movement progression: the field starts at zero, advances by `0.5F`, and clamps at `1.0F` (`1.11.2` `block/entity/MovingBlockEntity.java:273–288`). The collision collector reads `progress` directly, not the render interpolation (`:318–345`; interpolation is in `:73–78`). Thus the report has not established its claimed reachable horizontal response.

The same source change does have a source-reachable local-player collision witness at the naturally reached terminal `progress == 1.0F`; the witness uses vertical clipping and the old/new arm extents below. This preserves the candidate as a movement-relevant piston-head collision delta, but replaces the report's `progress == 0.8F` witness and its claimed base-limited horizontal clipping.

## Retraced source route

For an ordinary player collision query, `World.getCollisions(entity, box)` visits block states and invokes their collision collectors (`1.11.2` `world/World.java:960–1015`). The moving-block state dispatches to its `MovingBlockEntity` (`block/MovingBlock.java:135–152`), whose `addCollisions` adds the extended piston base for `!extending && source`, constructs a facing head, sets `SHORT` from `extending != 1.0F - progress < 0.25F`, and translates the resulting collision boxes by the retraction amount (`block/entity/MovingBlockEntity.java:318–345`). There is no client-world exclusion in this read/collection route. `Entity.move` obtains this list and applies per-axis `Box.intersectY/X/Z` clipping (`entity/Entity.java:564–585`); the ordinary world path is shared by player movement.

At progress 1.0 in a retracting source piston, the predicate sets `SHORT=true` and the retraction offset is zero. The old 1.10.2 `PistonHeadBlock.getArmShape` ignores `SHORT` and always returns the full east arm `[-0.25,0.75] × [0.375,0.625] × [0.375,0.625]` (`block/PistonHeadBlock.java:35–38, 75–87`). The 1.11.2 method selects the short east arm `[0,0.75] × [0.375,0.625] × [0.375,0.625]` when short (`:41, 76–92`). The moving base is added in both cases; the extended east base occupies `[0,0.75] × [0,1] × [0,1]` (`block/PistonBaseBlock.java:29, 52–82`).

Concrete collision response at block position `(0,0,0)`: take a player box `[-0.70,0.80,0.40 → -0.10,2.60,1.00]` and request `dy=-0.30`. It overlaps the old full arm in X/Z but is wholly west of the base and new short arm (`maxX=-0.10 < 0`). The old arm's top is `y=0.625`, so the downward solver clips to `dy=-0.175`; the new short arm and base do not intersect this player's X interval, so this collision contributes no vertical clip. This is a statically derived collision response, not a full-tick trajectory claim.

This terminal state is source-reachable: `MovingBlockEntity.tick` advances `0 → 0.5 → 1.0` and retains the block entity until its next tick (`:273–288`). `World.tickEntities` ticks ordinary players/entities before block entities (`world/World.java:1260–1315`), so after the block-entity tick first sets progress to 1.0, the following entity tick can query the terminal geometry before the block entity removes itself. No fractional render progress is needed.

## Block-entity closure audit

I checked the movement-facing changed paths identified in the 17 row dispositions against both endpoint sources. I found no additional changed local-player position, velocity, bounding-box, pose, or movement-predicate consumer in those rows.

- **Beacon:** `spreadEffects` is identical at the relevant endpoints and is gated by `!world.isClient` (`1.10.2` and `1.11.2` `BeaconBlockEntity.java:69–94`). Although effects such as speed or jump can matter to movement once applied, this row's changes are inventory/API changes, not a client-side effect application delta.
- **End gateway:** the changed cooldown and beam interpolation affect timing/render data. Entity teleport remains guarded by `!world.isClient` in both endpoint `EndGatewayBlockEntity.java` files (`tick`, `:65–82`; `teleport`, `:128–145` old and `:132–149` new). It is not a local client movement-physics response delta.
- **Hopper and chest:** hopper transfer-tick/cooldown changes are under the server gate (`HopperBlockEntity.java:164–184` old; `:102–123` new), and chest open/neighbor signaling changes update redstone/container state. Neither changed body moves or resizes the local player or changes a client movement predicate.
- **Base `BlockEntity`:** the changed position setter still stores the same coordinates; `BlockPos.immutable()` returns immutable positions unchanged and copies mutable positions in both endpoint `BlockPos.java` implementations. The representation change therefore does not alter the block-entity coordinates consumed by the piston route.
- **Remaining listed rows:** the changed methods described in the report are NBT/identifier/API refactors, inventory/menu/item transfer, presentation/render state, or structure data. Inspection of the listed source bodies found no changed player movement/pose writer or changed client-side direct movement consumer. In particular, item/inventory conversion does not itself enter the entity collision route.

## Correction and remaining limit

Keep the piston-head delta attached to the existing piston collision operation. Replace the progress-0.8 horizontal example with the progress-1.0 vertical witness above. The evidence establishes source-reachable endpoint collision clipping only; intermediate release boundary and runtime parity remain outside this audit.

### Row-by-row closure cross-check

The following names and changed-code domains were checked against both endpoint sources; none contributes a newly changed client movement/pose consumer:

- `BannerBlockEntity`: banner pattern/name/color data.
- `BeaconBlockEntity`: inventory changes; unchanged server-gated effect application.
- `BlockEntity`: registry/NBT and immutable position representation, coordinate semantics preserved.
- `BlockEntityProvider`: nullability annotation only.
- `BrewingStandBlockEntity`: inventory and brewing operations.
- `ChestBlockEntity`: inventory/menu handling and trap-chest neighbor signaling.
- `DispenserBlockEntity`: inventory and item selection/dispense operations.
- `DropperBlockEntity`: data-fixer registration.
- `EndGatewayBlockEntity`: server-gated teleport path; cooldown/render interpolation changes.
- `EndPortalBlockEntity`: render-face query.
- `FlowerPotBlockEntity`: item/metadata representation.
- `FurnaceBlockEntity`: inventory, fuel, and cooking operations.
- `HopperBlockEntity`: server-gated item transfer and cooldown timing.
- `InventoryBlockEntity`: menu-provider interface hierarchy.
- `LootInventoryBlockEntity`: shared loot inventory/menu operations.
- `MobSpawnerBlockEntity`: identifier/data-fixer registration and server entity production.
- `StructureBlockEntity`: default-zero seed and structure editing data.
