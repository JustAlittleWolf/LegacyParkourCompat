# ShulkerBox dynamic collision shape and LocalPlayer response

Pair: 1.16.5 → 1.17 → 1.17.1. Exact read-only Mojmap sources only. This report compares the block collision shape returned by `ShulkerBoxBlock.getShape` and its `Shapes.create(AABB)` path under equal block position, block state, BE animation state/progress and player movement-query inputs. It does not test or claim runtime trajectories.

## Verdict

**No changed ShulkerBox collision provider or LocalPlayer axis response is found for vanilla-eligible equal BE animation states.** The 1.16.5 and 1.17/1.17.1 `getBoundingBox` expressions yield the same AABB for the same `getProgress(1.0F)` value. For the states produced by the source's animation update, `Shapes.create` returns the same exact collision volume in both implementations: when closed, the unit cube singleton; at any positive reachable opening/closing progress, an AABB whose directed extent is at least about `0.05`, forcing the same continuous-array fallback. With equal query AABBs and equal vanilla state, the provider shape and resulting `CollisionSpliterator` admission/axis clip therefore agree.

The changed movement-expanded player query encoding remains a separate route; the accepted moving-piston witness covers that route. This result closes only the ShulkerBox provider-shape contribution and its equal-query consequences.

## Exact pair comparison

`ShulkerBoxBlock.getShape` has the same provider dispatch in the three releases: obtain the block entity and, if it is a `ShulkerBoxBlockEntity`, return `Shapes.create(blockEntity.getBoundingBox(blockState))`; otherwise return `Shapes.block()`. `BlockBehaviour.getCollisionShape` routes the collision query through this shape method. `CollisionSpliterator.collisionCheck` then moves the returned shape by the block position and sends a non-singleton shape through `Shapes.joinIsNotEmpty(providerShape, entityShape, AND)` before emitting it to the axis clipper.

The AABB operations are equivalent for equal progress. In 1.16.5, `ShulkerBoxBlockEntity.getBoundingBox` starts from `Shapes.block().bounds()` (the unit AABB) and calls `expandTowards(0.5F * progress * facingStep)`. In 1.17 and 1.17.1 it calls `Shulker.getProgressAabb(facing, 0.5F * getProgress(1.0F))`, whose body creates `new AABB(BlockPos.ZERO)`, expands it towards `facingStep * max(-1.0F, d)`, then contracts by `-facingStep * (1.0 + min(-1.0F, d))`; because the lower endpoint argument is exactly `-1.0F`, the contraction vector is zero. Both forms return the unit cube expanded along exactly the facing axis by the same float product, with the same endpoint and operation order. `Mth.lerp(float,float,float)` used by `getProgress(1.0F)` has the same `start + amount * (end - start)` body in all three sources.

The paired differences elsewhere in `ShulkerBoxBlockEntity` (tick signature and entity-push caller refactor) do not change this `getBoundingBox` result for equal current `progress` and `progressOld`. The provider call reads `getProgress(1.0F)`, so it does not use an interpolated render partial tick.

## Vanilla progress domain and Shapes.create consequence

`progress` starts at `0.0F`. `updateAnimation` sets it to zero in `CLOSED`, advances it by `0.1F` in `OPENING`, caps it at `1.0F`, subtracts `0.1F` in `CLOSING`, and resets it to zero when the subtraction is non-positive; `OPENED` holds it at one. The first positive value is therefore `0.1F`; subsequent positive values stay well above zero. `progress` and `progressOld` are private transient animation fields: the ShulkerBox save/load methods serialize container contents, not these fields; the client block event changes `openCount`/animation status and does not assign an arbitrary progress float. Thus a positive vanilla-produced progress value makes the directed AABB extension at least `0.5F * 0.1F`, approximately `0.05`.

For `progress == 0`, the shape bounds are exactly `[0,1]` on every axis. Both `Shapes.create(AABB)` versions return the shared `Shapes.block()` shape. For any positive vanilla-produced progress, the bounds extend at least `0.05` past zero or one along the facing axis. Both versions fail the same `findBits` closed-unit guard on that axis (`min < -1.0E-7` for a negative facing, or `max > 1.0000001` for a positive facing), and both return an `ArrayVoxelShape` backed by the exact same AABB endpoints. The 1.17 empty-extent check does not apply because every dimension is at least one. This proof is independent of player pose and world/block coordinates; shape construction is performed before `CollisionSpliterator` translates the provider by its block position.

The source does not admit an arbitrary sub-`2.0E-7` positive `progress` through the vanilla update or network/save paths. Such a reflectively injected or otherwise externally modified private field could exercise the changed negative-face `findBits` boundary, but it is outside the vanilla-eligible equal-state domain in this comparison. No conclusion is made for modded state writers.

## Caller boundary and exclusions

The LocalPlayer block-collision route is `Entity.collide` → multi-axis `collideBoundingBoxHeuristically` → `CollisionGetter.getBlockCollisions` → `CollisionSpliterator`; the latter calls each block's `getCollisionShape` and applies the provider/query AND filter. For this report, the local query AABB is held equal across releases. If it is a movement-expanded swimming query whose own `Shapes.create` conversion changes, that is the separate CollisionSpliterator query candidate and is not attributed to ShulkerBox's provider output.

The ShulkerBox BE's separate `moveCollidedEntities` action is not the `Shapes.create` provider path audited here. It directly moves entities during its animation and has its own source/caller scope; this report neither closes nor reclassifies that operation.

## Source identity

`source-hashes.tsv` rehashes the 12 paired source files needed for the shape expression, animation producer, shape conversion and collision caller; every row matches the declared exact source manifest. `publication-identities.tsv` binds the 1.16.5 and 1.17.1 parent sidecars and the 1.17 embedded `.decompile-provenance.json` source map. The parent-sidecar provenance for 1.16.5/1.17.1 is present and used; no embedded-provenance absence is treated as a blocker.

No product, wiki, backlog, runtime, build, test, launch, or source-generation data was used. No pair-wide coverage verdict is claimed.
