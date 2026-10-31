# EntityGetter movement-route closure — 1.15.2 → 1.16.5 Mojmap

## Verdict

**CLOSED for vanilla movement-reachable player consumers; no remaining direct LocalPlayer gap from `EntityGetter`.** The changed member that is actually on a LocalPlayer collision path, `getEntityCollisions`, is covered by the earlier exact candidate comparison and remains equivalent when LocalPlayer is the source entity. The remaining changed members (`isUnobstructed`, removed `getNearestPlayerIgnoreY`) are not changed local-player movement responses. A query performed with another entity as the source only chooses that mover's collision shapes; `Entity.move` does not write the target player's position or velocity. The eligible LocalPlayer push route uses `Level.getEntities` plus `EntitySelector.pushableBy` and `Entity.push`, not `EntityGetter.getEntityCollisions`. The distinct `Level` query row remains separately assigned.

This is a source-only caller disposition, not a claim that every `EntityGetter` implementation, arbitrary modded subclass, remote-entity trajectory, or non-player source route is equivalent.

## Exact changed member dispositions

### `getEntityCollisions` — LocalPlayer as source: equivalent

In 1.15.2, `EntityGetter.getEntityCollisions(entity, aabb, Set)` rejects a too-small query, inflates by `1.0E-7`, obtains candidates with `getEntities`, removes the supplied exclusion set and same-vehicle passengers, then emits intersecting `getCollideBox` and (when the mover is nonnull) `getCollideAgainstBox` boxes as shapes (`EntityGetter.java:53–67`). `Entity.move` supplies an empty set (`Entity.java:648`). In 1.16.5, the member takes a predicate, applies it together with candidate-bounding-box intersection and `source.canCollideWith(candidate)`, and emits candidate bounding boxes (`EntityGetter.java:59–75`); `Entity.move` supplies `entity -> true` (`Entity.java:675`).

For an ordinary LocalPlayer source, the legacy source-side `getCollideAgainstBox` is inherited from `Entity` and returns null. The exact 1.15.2 world-source scan finds only two target overrides of legacy `getCollideBox`: Boat (its bounding box) and Shulker (its bounding box iff alive). Their 1.16.5 eligibility matches: Boat's `canBeCollidedWith()` is true; Shulker's is `isAlive()`. Other vanilla targets inherit the old null collision box and the new false `canBeCollidedWith`. LocalPlayer inherits base `Entity.canCollideWith`, which excludes targets in the same vehicle. Old/new query inflation, the small-query guard, strict candidate intersection and Level/LevelChunk candidate routing match. This exact vanilla LocalPlayer source route is independently covered in `.native-source/20261030/1152-1165-pool33/entity-collide-player-audit/audit.md` and `.native-source/20261030/1152-1165-finish43/collision-collection-residual/report.md`; the latter separately retains only its canonical full-block block-collector fringe witness, not an entity-candidate difference.

The same predicate migration in LocalPlayer's `updateAutoJump` caller is explicitly closed for that caller by `.native-source/20261030/1152-1165-finish43/autojump-residual-audit/report.md`: the old empty set and new always-true predicate admit the same LocalPlayer target candidates, including Boat and live Shulker. Pose-fit/canEnterPose candidate behavior is bounded in `.native-source/20261030/1152-1165-pool33/closest-space-geometry/closure-report.md`. These conclusions are limited to the named LocalPlayer source operations and the exact vanilla endpoint classes.

### `isUnobstructed` — no changed movement response

The method changes from a stream/filter/`noneMatch` expression to an ordered loop (`EntityGetter.java`, old `:35–44`, new `:34–51`). Both use the same `getEntities(entity, shape.bounds())` list, then in the same order test `!removed`, `blocksBuilding`, the same-vehicle exclusion, and the same `Shapes.joinIsNotEmpty(shape, Shapes.create(entity.getBoundingBox()), AND)` collision predicate. For the Boolean result this is the same short-circuit test.

Exact callers are block-placement collision eligibility (`BlockItem`), entity-spawn obstruction checks (`Mob` and animal/monster overrides), and zombie reinforcements. They do not write LocalPlayer position/velocity. Block placement and entity spawn are state/entity producers, while the fixed-state player collision consumer is covered separately. The source bodies produce no changed LocalPlayer response under equal sampled state/input.

### Removed `getNearestPlayerIgnoreY` — server spawning only

The removed old member is called by `NaturalSpawner` to select a player when considering natural spawn positions (`NaturalSpawner.java:68`); it has no LocalPlayer movement caller. The call is server-side spawn-distance logic and is excluded with entity production/non-player behavior.

## Remote-source collision query is not a player writer

`getEntityCollisions` is queried from the source entity's own `Entity.move` collision solver. Its output shapes are consumed to clip that same source entity's requested displacement; that method writes the mover's bounding box, collision flags and velocity. When the source is a mob, boat, minecart or other remote entity and the LocalPlayer merely appears among its candidate targets, the changed query does not invoke `LocalPlayer.push`, mutate LocalPlayer velocity, or position the LocalPlayer. That route is non-player/vehicle collision production, outside this LocalPlayer movement operation.

The direct local push routine is a separate call path: `LivingEntity.pushEntities` collects `this.level.getEntities(this, this.getBoundingBox(), EntitySelector.pushableBy(this))`, then calls `this.doPush(candidate)`; `doPush` invokes `candidate.push(this)` (`LivingEntity.java`, old `:2323–2345, 2370–2371`; new `:2502–2524, 2549–2550`). When `this` is LocalPlayer, the entity list selector is based on the LocalPlayer source, and the player response occurs through `Entity.push`/`LivingEntity.push`. It does not call the changed `EntityGetter.getEntityCollisions`. Any changed `Level.getEntities` implementation is owned by the separate Level residual and is not attributed to this `EntityGetter` rewrite.

## Evidence identity

`source-hashes.tsv` contains 27 file/release checks for the exact endpoint source members and callers, with zero mismatches against each release's `mojmap.sources.sha256`. `input-hashes.tsv` binds the prior independent LocalPlayer query audit, the collision collection and auto-jump closures, the pose candidate closure, and the reconciled route ledger. The canonical 1.15.2 and 1.16.5 trees were read-only.

## Remaining limits

- The non-LocalPlayer-source `EntityGetter.getEntityCollisions` result is not claimed equivalent; it is excluded when it only affects a non-player/vehicle mover. Arbitrary modded entity subclasses are outside this vanilla-source audit.
- This does not resolve the separate `Level`, `LevelAccessor`, or `LevelReader` member inventory, nor the independently scoped Wall/IronBars state routes.
- No runtime trajectory, entity-production timing, packet cadence, build, test, product edit, wiki edit, or backlog write was performed.
