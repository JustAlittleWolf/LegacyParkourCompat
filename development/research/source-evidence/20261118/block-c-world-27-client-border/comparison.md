# WORLD-27 source comparison: client world-limit and border collision fallback

## Bound scope

Source-only coverage projection: `WORLD-27: client world-limit and border collision fallback`, revision `fcd6d1894152abb27bb54789cd2ba68523b5df9345d2d0fb10a1f65dfaff8ced` (unexamined). Compared exact Ornithe Feather sources for 1.8.9, 1.9, and 1.9.4, limited to `World#getCollisions`, `World#isWithinBorder`, and the ordinary living-player movement call into `Entity#move`/`World#getCollisions`. The server loaded-chunk gate is excluded. This does not assess the independently assigned neighbor scan/piston geometry claim.

## Exact source identity and integrity

All source hashes below were generated with SHA-256 and checked against the release source manifests, or (for 1.9) the embedded `.decompile-provenance.json` per-path hashes. Every compared file matched its pin.

| Release | Mapping/source identity | Manifest/provenance | `World.java` SHA-256 |
|---|---|---|---|
| 1.8.9 | Ornithe Feather Gen2 build 2; `feather-gen2-1.8.9+build.2-mergedv2.jar`, `feather-gen2-1.8.9+build.2.tiny` | `ornithe-feather.ready.json` pins source-manifest SHA-256 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; all four selected source files matched `ornithe-feather.sources.sha256` | `3c04f5b874fbb6692039164c882a3c47fe28c7eca864e91c9ac57798153888ee` |
| 1.9 | Ornithe Feather Gen2 build 2; `feather-gen2-1.9+build.2-mergedv2.jar`, `feather-gen2-1.9+build.2.tiny` | No outer ready sidecar. Embedded `ornithe-feather/.decompile-provenance.json` pins each selected file; all four matched. `World.java` pin: `d1e23db2e20c86eb37b964a0df6838f1422ade7b0c2fff9c869ff8345979aa98` | `d1e23db2e20c86eb37b964a0df6838f1422ade7b0c2fff9c869ff8345979aa98` |
| 1.9.4 | Ornithe Feather Gen2 build 2; `feather-gen2-1.9.4+build.2-mergedv2.jar`, `feather-gen2-1.9.4+build.2.tiny` | `ornithe-feather.ready.json` pins source-manifest SHA-256 `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`; all four selected source files matched `ornithe-feather.sources.sha256` | `2fe063e0ec224eed9fc7d01b8f788c32035eed5fee5a9d98ea5e82296236e05a` |

## Compared slices

- `1.8.9/ornithe-feather/net/minecraft/world/World.java`: `getCollisions(Entity, Box)`, lines 891-945; `isWithinBorder(WorldBorder, Entity)`, lines 947-965.
- `1.9/ornithe-feather/net/minecraft/world/World.java`: `getCollisions(Entity, Box)`, lines 893-955; `isWithinBorder(WorldBorder, Entity)`, lines 957-975.
- `1.9.4/ornithe-feather/net/minecraft/world/World.java`: `getCollisions(@Nullable Entity, Box)`, lines 899-961; `isWithinBorder(WorldBorder, Entity)`, lines 963-981.
- Player context: `PlayerEntity` extends `LivingEntity` in all three releases (1.8.9 line 79, 1.9 line 91, 1.9.4 line 92). `LivingEntity` invokes `this.move(this.velocityX, this.velocityY, this.velocityZ)` in its normal movement paths (1.8.9 lines 1165, 1190, 1218; 1.9 lines 1328, 1378, 1407, 1435; 1.9.4 lines 1341, 1391, 1420, 1448). The inherited `Entity.move` collision probes pass the same player instance as the first argument and a swept/adjusted player box as the second argument to `this.world.getCollisions(this, ...)` (1.8.9 `Entity.java` lines 439, 469; 1.9 lines 506, 539; 1.9.4 lines 509, 542). Thus the local player call is non-null and takes the entity collision branch.

## Border gate and fallback order

All three versions first snapshot `bl = entity.isOutsideWorldBorder()` and `bl2 = isWithinBorder(worldBorder, entity)` for the collision query. `isWithinBorder` tests the entity center with strict `>`/`<` comparisons. If the prior outside flag is true, it contracts all four border bounds by 1 block; otherwise it expands all four bounds by 1 block. This is the same 1-block hysteresis in each release.

For each eligible scanned block cell, all three versions first reconcile the outside flag using those snapshots: clear it only when `(bl, bl2)` are both true; set it only when both are false. They then initialize the candidate state to `Blocks.STONE.defaultState()`, and replace it with the actual world block state only when `worldBorder.contains(candidatePosition) || !bl2`. Finally they ask that chosen state's block/collision provider to append collisions. Therefore the fallback order and initial-border-check behavior are unchanged for a non-null player. The flag can be updated during scanning, while the cached `bl2` used by the state-selection predicate remains the initial result; this ordering is also unchanged.

The only directly relevant signature guard change is that 1.9+ tolerates `entity == null` when making the initial snapshots and skips entity-state updates and entity-collision handling in that case. 1.8.9 dereferences `entity`; that difference is unreachable from the ordinary player movement calls above. The checked border predicate and fallback branches themselves match for every non-null player.

## Finding disposition

**No exact bounded direct player difference is source-supported for client world-limit/border fallback across 1.8.9 to 1.9 or 1.9 to 1.9.4.** The changed nullability guard does not apply to a player, and the initial border test, hysteresis, state fallback, and ordering are identical on the player path. No movement finding should be created from this slice. This disposition does not cover differences in which candidate cells are scanned or in block collision providers.
