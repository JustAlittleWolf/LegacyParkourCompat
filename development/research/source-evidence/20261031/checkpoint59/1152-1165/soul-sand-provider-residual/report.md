# Soul Sand provider closure — Mojmap 1.15.2 → 1.16.5

## Verdict

**Fixed-state direct local-player movement provider closure: no delta.** With the same `Blocks.SOUL_SAND` state and equal surrounding state/tag inputs, the collision shape and registered speed factor are identical. The 1.16.5 support and visual providers are new but do not change the traced local-player collision path. One separate neighbor/update scheduling change remains a world-state timing dependency; it is not a fixed-state Soul Sand movement delta.

## Exact identity

- Pair: Minecraft 1.15.2 Mojmap → Minecraft 1.16.5 Mojmap.
- Exact source roots: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.15.2\mojmap` and `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.16.5\mojmap`.
- Canonical source-manifest pins: older `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7`; newer `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`.
- Provenance names the Mojmap namespace and the exact 1.15.2 / 1.16.5 versions. The paired exact-run command was `.\gradlew.bat decompileMinecraft --versions=1.15.2,1.16.5,1.17.1,1.18.2 --mappings=mojmap ...`; these sources were consumed read-only. Mapping files were not present as standalone artifacts (`mappingArtifacts: null`), so no mapping build number is inferred.
- Corrected ordinal path-status inventory pin: `835ba455d3dd4825f01a74f282eee21c42254cee3870a400ee7f96d9824fbcd5`.
- The exact removed/added class and all paired source dependencies checked here are listed in `source-hashes.tsv`.

## Member-level closure

1. **Registration and speed factor — equal.** `Blocks.SOUL_SAND` keeps the same registry key on both endpoints. 1.15.2 `Blocks.java` registers `new SoulsandBlock(...)` with `.speedFactor(0.4F)`; 1.16.5 registers `new SoulSandBlock(...)` with `.speedFactor(0.4F)`. Material/color and sound construction changed, but the movement speed factor did not. No tags select or alter this provider.
2. **Collision shape and movement dispatch — equal.** Both class bodies define `SHAPE = Block.box(0, 0, 0, 16, 14, 16)` and return it unconditionally from `getCollisionShape`. The 1.15.2 `BlockState.getCollisionShape` dispatches to `Block.getCollisionShape`, using its cached collision shape when available. The 1.16.5 `BlockBehaviour.BlockStateBase.getCollisionShape` retains the same cached-versus-provider dispatch to `BlockBehaviour.getCollisionShape`. Soul Sand is not marked dynamic; its cached result is the same constant shape. The 1.15.2 `CollisionGetter` and 1.16.5 `CollisionSpliterator` both request the block state's collision shape for candidate cells during `Entity.move` collision collection. Thus the same player sweep sees the same 14/16-high volume at the same position on both endpoints.
3. **Support shape — new non-collision predicate.** The 1.16.5 class adds `getBlockSupportShape`, returning `Shapes.block()` even though collision remains 14/16 high. `BlockBehaviour.BlockStateBase.isFaceSturdy` routes that provider through `SupportType`; this can change support predicates used by block/state behavior. The audited local-player movement collection and `Entity` response path use collision shapes, and the entity source scan has no `isFaceSturdy`/`getBlockSupportShape` consumer. Under equal block states this addition does not change player collision, clipping, or ground contact. Any downstream effect through neighbor placement/update is a world-state-evolution dependency.
4. **Visual shape — rendering/query-only.** The new `getVisualShape` returns a full block while collision remains 14/16. It is not requested by the traced player collision collector; it does not establish a movement delta.
5. **Remaining block methods.** Both endpoints' `tick` call `BubbleColumnBlock.growColumn` at the cell above with `false`; both `onPlace` paths schedule a 20-tick block tick; both retain `isPathfindable(...)=false`. The older class additionally overrides redstone conduction, spawn validity and view blocking; those are not local-player movement consumers in this route. No differing direct player contact/velocity method was found on the Soul Sand block itself.

## Distinct neighbor/update timing dependency

The older `SoulsandBlock.neighborChanged` schedules a 20-tick block tick after any neighbor change. In 1.16.5 that override is gone; `SoulSandBlock.updateShape` schedules the same 20-tick delay only when the changed neighbor is above (`Direction.UP`) and its state `is(Blocks.WATER)`. The per-side gate and callback differ, while the tick delay and tick consumer remain the same. The scheduled tick runs on `ServerLevel`, and `BubbleColumnBlock.growColumn` can replace source water above Soul Sand with a bubble-column state.

That gives a separate state-production/timing route: an older non-upward or non-water neighbor update can schedule a later server attempt that the newer Soul Sand callback does not schedule. If the source-water-above condition is present when the older scheduled tick runs, that attempt may create the bubble-column state. `BubbleColumnBlock.entityInside` then calls `Entity.onAboveBubbleCol` or `Entity.onInsideBubbleColumn`; both paired entity methods directly alter vertical velocity. This establishes a possible movement-reachable consequence only through changed server state production and subsequent state delivery/consumption. It is excluded from the equal-state fixed-provider verdict; packet/update ordering and actual state convergence at the player's movement sample are not closed here. No claim is made that every neighbor update yields a bubble column or that the endpoints diverge in a particular live sequence.

## Closure status

These two corrected ordinal rows are exact case-distinct paths: removed `SoulsandBlock.java` and added `SoulSandBlock.java`. Both ledger rows correctly remain **movement-reachable** because the path is a legacy movement provider and the neighbor/synchronization dependency has a possible direct-player endpoint. The named fixed-state provider and collision-dispatch slice is now closed as no-delta; the distinct server neighbor/update timing dependency remains open for movement impact through bubble-column state production and client delivery. This does not freeze the pair or close unrelated Soul Sand state-generation dependencies.

No implementation, wiki, product history, build, test, game launch, decompilation, or backlog edit was performed.
