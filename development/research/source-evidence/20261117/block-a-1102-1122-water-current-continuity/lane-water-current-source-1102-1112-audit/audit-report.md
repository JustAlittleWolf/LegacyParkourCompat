# Independent blind audit: water-current sampling, 1.10.2 -> 1.11.2

## Verdict

**Accepted, bounded to the assigned operation:** the comparison correctly reports no source difference in the non-flying local player's water-current query, current-vector calculation, aggregate normalization, direct velocity addition, or sampling schedule. Its cited support-probe change is outside this audit's scope and is not given a verdict here.

## Immutable input identities

- Comparison: `lane-water-current-source-1102-1112/comparison.md`; SHA-256 `0d459dea0f6af14168bf6fc52d9252560e126b551609791c7e7ad3cb0e9f97fa` (verified before reading).
- Frozen exact-source roster: `source-roster.txt`; SHA-256 `dde9111b07804b8e933fd820b15cbb73435dd1ae4be008629be67df8c4fc3239` (written before comparison access).
- Pair/mappings: Minecraft 1.10.2 and 1.11.2, each `ornithe-feather`. All listed source files were checked against the per-version source manifest; no missing or extra source files were found. Ready-marker manifest digests matched the on-disk manifests. Exact source counts: 1,845 and 1,921.

## Retraced source path

- `Entity.checkWaterCollisions` uses the same boat short-circuit and the same `getShape().grown(0.0, -0.4F, 0.0).contract(0.001)` query in both versions (1.10.2 `Entity.java:862-878`; 1.11.2 `Entity.java:927-943`). It calls `World.applyLiquidDrag` before updating `inWater`; on success it sets `inWater = true`, and on failure it sets it false. The splash/first-tick gate is unchanged. Fire handling differs in these surrounding lines but is outside the assigned movement-current verdict.
- `World.applyLiquidDrag` is unchanged in the examined logic (1.10.2 `World.java:1570-1611`; 1.11.2 `World.java:1571-1612`): floor minima and ceil maxima; whole-query early return when `isAreaLoaded(i, k, m, j, l, n, true)` is false; exclusive X/Y/Z loops; exact material match; and contact when `l >= p + 1 - LiquidBlock.getHeightLoss(LEVEL)`. It sets the contact result separately from the accumulated vector. The same `getHeightLoss` (`LiquidBlock.java:49-55` / `:50-56`) is `(state + 1) / 9.0F`, with states >= 8 first mapped to zero.
- The per-cell `LiquidBlock.getFlow` and `applyMaterialDrag` bodies match (both `LiquidBlock.java:131-177`). Horizontal-neighbor order, same-material depth lookup, open-side/below fallback, offset-times-depth-difference additions, level-8 falling-fluid face test, intermediate and final normalizations, and per-cell vector addition are unchanged. The supporting `getLiquidState`, `getLiquidDepth`, and `isFaceSolid` implementations also match (`LiquidBlock.java:49-90` / `:50-91`).
- The aggregate current is normalized only when its length is `> 0.0` and `entity.hasLiquidCollision()` is true; X, Y, then Z velocity are each incremented by the normalized component times the double literal `0.014`. Gate, operation order, literal, and writes match in both `World.applyLiquidDrag` bodies. `PlayerEntity.hasLiquidCollision()` returns `!abilities.flying` in both versions (both `PlayerEntity.java:1735-1738`), so the assigned non-flying player receives this impulse when the aggregate is nonzero.

## Local-player timing

`LocalClientPlayerEntity.tick` has the same loaded-chunk gate and calls `super.tick()` only inside it (1.10.2 `LocalClientPlayerEntity.java:152-165`; 1.11.2 `:159-172`). The inherited tick path reaches `Entity.baseTick`, whose `tickSprintingEffect(); checkWaterCollisions();` order is unchanged (1.10.2 `Entity.java:304-367`; 1.11.2 `:317-380`). Thus the normal per-tick query and direct impulse occur before that tick's later player movement; an unloaded local-player tick skips this path in both versions.

After a move, `Entity.move` invokes `checkFallDamage` in both releases (1.10.2 `Entity.java:641-650`; 1.11.2 `:704-713`). `LivingEntity.checkFallDamage` first calls `checkWaterCollisions()` when `!isInWater()` (1.10.2 `LivingEntity.java:186-200`; 1.11.2 `:193-207`); this guard is not conditional on falling or landing. Therefore, when a previously-not-in-water on-foot player moves into water, the post-move recheck can add the current impulse immediately and then set `inWater`. When the state was already true at movement time, the post-move recheck is skipped; the next base tick samples and adds the current before movement. These gates and ordering match across the pair.

## Scope boundary

The comparison also reports a changed sneak-edge support probe reached through a self-move. That solid-support collision observation is expressly outside this assignment and remains unaudited here. This verdict does not cover other water/fluid predicates, falling behavior, other entities or versions, or runtime parity.
