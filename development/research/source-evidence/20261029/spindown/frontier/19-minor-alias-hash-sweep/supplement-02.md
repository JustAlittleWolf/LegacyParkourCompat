# Supplement 02: state-property and entity-less collision paths

This is an immutable continuation of `coverage.md` and `supplement.md`. It responds to the independent supplement audit. The exact source pair is Minecraft `1.9.2` → `1.9.3`, each from its own `ornithe-feather` root. Both map to Feather Gen2 `+build.2`; source roots and exact mapping/client/release identities are recorded in the mapping-root `.decompile-provenance.json` files. Manifest SHA-256 values: 1.9.2 `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510`; 1.9.3 `43325cdd53ca34b88ee9af8464782b323a948b98c8a556c4761f1e5737b84c3a`. Every source file cited below matches its corresponding manifest hash (verified in the prior supplement for all 1,819 files per version). This binds the observations to those local manifests; the artifact bytes were not fetched or separately authenticated.

## `StateDefinition` property get/set

`net/minecraft/block/state/StateDefinition.java` differs at state-property access:

- `State.get(Property)` (1.9.2 line 147; 1.9.3 line 150): 1.9.2 checks `values.containsKey(property)`, then `property.getType().cast(values.get(property))`. 1.9.3 reads `values.get(property)` into `Comparable<?>`, treats null as a missing property, then applies the same type cast. For a valid constructed block state, each declared property has a non-null value, so ordinary valid reads return the same typed value. A malformed state map containing a key with null value would now throw `IllegalArgumentException` rather than return null; that is an invalid-state error path, not a movement result established for vanilla states.
- `State.set(Property, value)` (1.9.2 line 156; 1.9.3 line 160): 1.9.2 checks property membership, validates `property.values().contains(value)`, then returns `this` for the identical current value or reads `neighbors.get(property,value)`. 1.9.3 reads the current value, errors on null, returns `this` immediately when `current == value`, otherwise reads the neighbor table and throws the same “not an allowed value” `IllegalArgumentException` when it is absent. For valid states and values from the property's value domain, both return the same state in the same cases. The potential difference is confined to invalid/malformed property values or a missing neighbor-table entry (where 1.9.3 throws instead of returning null). No player movement path passing an invalid value or observing a different valid state was found in this bounded review; no movement finding is established.

The compared `BlockState` providers use these methods to read shape/virtual properties and resolve valid states. The changed code does not alter the stored property ordering or the state neighbor table construction in a way traced here to a different valid player collision shape.

## `World` entity-less collision queries and callers

`World.isChunkLoaded(BlockPos, allowEmpty)` changes from `!contains(pos) ? false : isChunkLoadedAt(x>>4,z>>4,allowEmpty)` in 1.9.2 (line 180) to the direct chunk-provider lookup in 1.9.3 (line 186). With the scan position fixed to y=64, the relevant removed guard is the horizontal `x/z` range `[-30,000,000, 30,000,000)`.

- `World.getCollisions(Box)` (1.9.2 line 1012; 1.9.3 line 1018) scans the same x/z/y bounds and uses `isChunkLoaded` to decide whether to inspect a column. In 1.9.3, once a loaded chunk admits a scanned coordinate outside the horizontal range, the method returns `true` before reading the block state (lines 1030-1033); 1.9.2 skips the whole out-of-range column because its `isChunkLoaded` returns false. If the out-of-range chunk is not loaded, both paths skip it. This is a concrete entity-less collision-predicate delta at extreme coordinates.
- One direct player caller exists: `PlayerEntity.updatePlayerPose()` (1.9.2 lines 284-305; 1.9.3 lines 285-306) is invoked from `PlayerEntity.tick()` each tick (1.9.2 line 244; 1.9.3 line 245). When fall-flying, sleeping, or sneaking changes the target dimensions, it builds a candidate box and calls `world.getCollisions(box)`; only a false result applies the new width/height with `setSize`. Thus, if that candidate box crosses the horizontal world coordinate bound and the relevant outside chunk is loaded, 1.9.3 can reject the resize where 1.9.2 would allow it. This is a direct player dimension/collision-state consequence in the source, but its reachable precondition is not established here.
- `WorldBorder` is identical in both sources (same SHA-256 below). Its `maxSize` is `29,999,984`, 16 blocks inside the world horizontal limit. The ordinary entity-aware player movement collision path uses the border gate and stone fallback; the entity-less pose query does not consult the border. Normal in-border movement therefore cannot reach the out-of-world coordinate scan by merely walking to the default maximum border. The candidate requires an exceptional coordinate state (for example a position supplied externally) and a loaded out-of-range chunk; exact client chunk delivery/teleport reachability and operation ordering are left open. This is not a parity claim or accepted finding.
- Other callers of `getCollisions(Box)` in the exact roots are `WalkNodeEvaluator` mob pathfinding (excluded non-player behavior) and `LivingEntity.dismountRider`. The latter is called from `LivingEntity.stopRiding()` only when `!world.isClient` (1.9.2 line 1751; 1.9.3 line 1760), so the dismount candidate-position check is server-only and cannot be a historical client movement path under the project invariant. Its special Boat/Horse branch is also unchanged.
- `World.getBlockCollisions(Box)` (1.9.2 line 977; 1.9.3 line 983) uses the same chunk gate. Outside horizontal bounds, its body substitutes `BEDROCK` when an out-of-range chunk is loaded. Its only source caller is `Entity.pushAwayFrom()` (1.9.2 line 1689; 1.9.3 line 1699), and that method is called only by `ExperienceOrbEntity` and `ItemEntity` in the reviewed source trees. This is non-player entity physics and excluded from the assigned movement scope.

Consequently the added query path is a direct player-movement-adjacent candidate only through `PlayerEntity.updatePlayerPose`; dismount is server-only, and `pushAwayFrom` is non-player. The pose candidate has a concrete output difference but no ordinary reachable player path demonstrated, so it remains unresolved rather than a finding.

## `FireBlock`

`net/minecraft/block/FireBlock.java` is hash-different only for nullability metadata and callback parameter order in the reviewed hunks. `getCollisionShape` (1.9.2 line 96; 1.9.3 line 98) still returns `EMPTY_BLOCK_SHAPE`; `neighborChanged` (1.9.2 line 288; 1.9.3 line 290) has the same support/flammable-neighbor check and block-removal write. In 1.9.3, `World` dispatches through `BlockState.neighborChanged`, which forwards the same block state, world, position, and neighbor to this method. No player shape or movement-response difference is established.

## Bound evidence hashes

SHA-256 of source files in the exact mapped roots:

| Relative path | 1.9.2 | 1.9.3 |
|---|---|---|
| `net/minecraft/block/state/StateDefinition.java` | `6b7e70156f9f834dd3129d02f0a24ce78a91a8bf926ac0cb2aae06955799c0e3` | `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493` |
| `net/minecraft/world/World.java` | `cdda70c97f0e4d6f6c454c8f6d3c0d47f0e0ae612e75062d8fe93ddd3963e6b0` | `2fe063e0ec224eed9fc7d01b8f788c32035eed5fee5a9d98ea5e82296236e05a` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `1fc5107b9b7f5711105d521a28829f2466fe9f297e832f4d209e6ec422111aac` | `ef96c8a00443f724eadad05a6a4dcc8a8a2e23cd4c3390b9e0f1f78c9ff4d1af` |
| `net/minecraft/entity/living/LivingEntity.java` | `d8824f670037a88753c428c27213989fc5173b1d7c638f498f73395c145b6fd9` | `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5` |
| `net/minecraft/entity/Entity.java` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` | `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0` |
| `net/minecraft/entity/ai/pathing/WalkNodeEvaluator.java` | `d8d7b25ab07688cdd93f6178c3206e42d5de9fa8a568b0bf80357c2ac0aa718f` | `893108191844cee1eceb32fbef9c245a70cad969c02c8895b17e0475436b4c85c` |
| `net/minecraft/block/FireBlock.java` | `343857750c93cf56044cde85be8829e5d971a7a1369c9b2a94c78b937024c23a` | `48b734bf3665b9c743c05c176bb1208992e357f376fecfb1b6d337dade5a2c29` |
| `net/minecraft/world/border/WorldBorder.java` | `1f0052bc10592684ea845aa36b99b4dc78d1f4368df21625349fdd887b561f95` | `1f0052bc10592684ea845aa36b99b4dc78d1f4368df21625349fdd887b561f95` |

## Status

These additions classify the audit's omitted state-property, entity-less collision, and FireBlock paths. The pose-collision boundary case remains an unresolved candidate pending the assigned lane's reachability/inventory review. The exact entity-aware `World.getCollisions(Entity,Box)` movement scan remains a separate unchanged path; the bounds difference does not justify inferring an ordinary movement divergence. No tests/builds/launches/source generation/canonical writes were performed. Full provider/tick inventory and independent pair audit remain open; no pair freeze or movement parity claim is made.
