# Water-current source comparison: 1.10.2 → 1.11.2

## Scope and result

Blind exact-source comparison of the assigned non-fall water-current path: player bounds and water admission; per-cell liquid flow and aggregate velocity; and the local player's water/no-flight movement path and entry/exit timing. The current sampling and numerical current calculation are unchanged. A movement-path dependency does differ: the self-move sneak-edge support probe changes from one block below the player to `-stepHeight` (0.6 by default) in 1.11.2. The water branch calls that self-move, so a grounded, sneaking, non-flying player in water can be constrained differently near an edge. No trajectory was inferred beyond that source-reachable condition.

## Evidence identity

The host is Windows PowerShell. Both canonical version-root sidecar sets identify exact versions and `ornithe-feather` mappings. Provenance records the pinned Feather build.2 mapping jar/Tiny names and Java 25/Vineflower 1.12.0 toolchain. The ready markers' source-manifest SHA-256 values match the manifests on disk. Verification sidecars report 1,845 and 1,921 verified source files respectively, zero source-file differences, and no missing published source files. Their sole artifact difference is the regenerated derived remapped client jar; raw-input artifact differences are empty, and the published source trees are verified identical to the verification outputs.

| Release | Feather mapping inputs (artifact manifest SHA-256) | Source manifest SHA-256 |
|---|---|---|
| 1.10.2 | `feather-gen2-1.10.2+build.2-mergedv2.jar` `18cffc56c2d8de89b50cb0328b174566236553c4aafb62afdc58a1e0ff0cadb0`; `feather-gen2-1.10.2+build.2.tiny` `7c4055aa9becb027462fe4f4a9af8822de4df9b70ecb80a84e38fd9e81e33af1` | `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71` |
| 1.11.2 | `feather-gen2-1.11.2+build.2-mergedv2.jar` `d14500101ac23c874b0fe394eae21a382c410ec4f3bbc2e58042e5234a236757`; `feather-gen2-1.11.2+build.2.tiny` `4fa160c09d83bf61ae21bb74ab1e33b6aabe9b8ec89904b266ad53cecc9c36e6` | `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0` |

The following actual source SHA-256 values were independently checked against each release's source manifest:

| Source | 1.10.2 | 1.11.2 |
|---|---|---|
| `net/minecraft/world/World.java` | `888ed0e9de765def87b05c4126ecdf0b10e9dd448b4543dd1cb98211e9951646` | `27cfaa5ff45c2d88c492fc5dae3ea4a2bb4536fd64bff8498a9a7d0cb82efb58` |
| `net/minecraft/block/LiquidBlock.java` | `b988d19e379d01d291cb014cb05e1579311c589654ce9ff4faa7f43bbba0533e` | `c91618c8445dd488dd61f4a01aed0799dae2faaf3585bc6f2d5164891e988244` |
| `net/minecraft/entity/Entity.java` | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` | `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440` |
| `net/minecraft/entity/living/LivingEntity.java` | `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82` | `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `a055b84b98d98e828e177cad9bba47a1334ee9bf3ecd4331792979106235a302` | `87fe94fa6cbf7aba18b9a5e3401664439eb8eba9958173da8fbcd05cc7ad948b` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443` | `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed` |

## Compared operations

### Water bounds, loaded admission, and liquid states

`1.10.2 Entity.checkWaterCollisions()` (`entity/Entity.java:862–877`) and `1.11.2` (`:927–942`) pass `getShape().grown(0.0, -0.4F, 0.0).contract(0.001)` to `World.applyLiquidDrag`. The boat gate, bounds transform, `!inWater && !firstTick` splash gate, water-state writes, and false-result exit all match.

`World.applyLiquidDrag` (`world/World.java:1570–1612` / `:1571–1613`) floors each minimum and ceils each maximum, rejects the whole query unless `isAreaLoaded(i, k, m, j, l, n, true)`, then loops `[min, ceil(max))` in X/Y/Z order. A cell contributes only when the fetched block state's material equals the requested material and `ceil(bounds.maxY) >= y + 1 - LiquidBlock.getHeightLoss(state LEVEL)`. That contact boolean is independent of whether the accumulated current is nonzero. The local mutable-position reads, property lookup, loaded admission, loop bounds and water-surface test match.

### Per-cell flow and aggregate velocity

`LiquidBlock.getFlow(WorldView, BlockPos, BlockState)` (`block/LiquidBlock.java:131–172`) is the same in both releases. It scans horizontal directions in enum order. For a liquid neighbor it adds the direction offsets times `neighborDepth - currentDepth`; for a non-liquid, non-movement-blocking neighbor it checks the block below and uses `belowDepth - (currentDepth - 8)`. For current-state `LEVEL >= 8`, the first side or upper-side solid face changes the vector to `normalize(vector) + (0,-6,0)` and stops that scan. The method then normalizes once more. `applyMaterialDrag` at `:175–177` adds this per-cell vector to the aggregate in both versions.

After the cell scan, `World.applyLiquidDrag` normalizes the aggregate only for `length() > 0.0 && entity.hasLiquidCollision()`, then adds `vec3d.{x,y,z} * 0.014` to the matching velocity components. The `0.014` operand is a double literal; the local `double e = 0.014` is not used by those three writes. Scan order, normalization count/order, gate and component-write order match.

### Local-player water/no-flight route and timing

`LocalClientPlayerEntity.tick()` (`client/entity/living/player/LocalClientPlayerEntity.java:152–166` / `:159–173`) only delegates to `super.tick()` while the chunk at `(x,0,z)` is loaded. Both versions retain the same gate and the same on-foot `sendMovementToServer()` versus riding packet path. In `mobTick()` (`:620–783` / `:632–795`), spectator flight enablement, double-jump flight toggle and the post-`super.mobTick()` on-ground flight reset have the same ordering and predicates. The flight-toggle and on-ground reset predicates/order remain unchanged.

`PlayerEntity.moveRelative()` (`entity/living/player/PlayerEntity.java:1391–1412` / `:1387–1408`) routes non-flying, non-riding player input to `LivingEntity.moveRelative()`; its flying-player speed/vertical-velocity wrapper is unchanged. The inherited `LivingEntity.moveRelative()` (`:1332–1506` / `:1388–1562`) retains the local-control/logical-side gate and water predicate: ordinary land movement runs when `!isInWater()` or when the entity is a flying player; otherwise the non-flying water branch applies input acceleration, moves, damps horizontal velocity by the base movement factor and vertical velocity by `0.8F`, applies `-0.02` gravity unless no-gravity, then may raise vertical velocity to `0.3F` on a horizontal collision. Its water/land arithmetic and branch gates are unchanged. The movement call is now `move(MoverType.SELF, ...)` in place of `move(...)`.

That new call reaches a changed player sneak-edge guard in `Entity.move`: 1.10.2 (`entity/Entity.java:446–510`) uses `onGround && isSneaking() && instanceof PlayerEntity` and probes collision boxes lowered by `-1.0`; 1.11.2 (`:459–557`) applies the guard to `SELF`/`PLAYER` movers and lowers probes by `-stepHeight` (the living-player default is `0.6F`). The water branch passes `SELF`, so grounded sneaking players in water reach the changed probe. This is a reachable movement delta adjacent to, but separate from, the unchanged fluid-current accumulation.

Timing gates: `Entity.baseTick()` calls `checkWaterCollisions()` each tick after `tickSprintingEffect` (`entity/Entity.java:312–366` / `:325–379`). `LivingEntity.checkFallDamage()` also calls it after the collision move only while `!isInWater()` (`entity/living/LivingEntity.java:187–188` / `:194–195`): a just-entered player can be marked in water and receive the current during that recheck; a player whose state was already true skips that mid-move exit refresh, which is picked up by the next base tick. The boat gate sets water false without sampling. `firstTick` suppresses only the splash effect, not the water state/current. These call sites and gates match.

## Coverage disposition

- Examined: water bounds transform and volume admission; water material and level/source-state checks; ordinary/downhill per-cell vector; high-level vertical-current case; aggregate normalization and `0.014` velocity addition; local player loaded-tick and on-foot/riding routing; no-flight water route; water entry/exit and splash gates; self-move sneak-edge support probe.
- No source difference in current bounds, fluid-flow arithmetic, aggregate normalization, `0.014` additions, water/no-flight branch gate, or in-water entry/exit timing.
- Difference established for grounded sneaking self-movement reachable from the non-flying water branch: the lower support probe is `-1.0` in 1.10.2 and `-stepHeight` in 1.11.2. Keep this distinct from a water-flow arithmetic finding.
- Not examined per assignment: fall-side cues, body-water eye checks or any other water/fluid operation, other releases, wiki evidence, product code, and runtime behavior.