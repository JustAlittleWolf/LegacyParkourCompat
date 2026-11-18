# Additive independent source audit r1

## Bound evidence

- Author comparison: `comparison.md`, SHA-256 `3c5204b3f24a6f5ef09d03897b57b7606b61f4bb230643c2321b44adadc26634`.
- Original blind roster: `roster.txt`, SHA-256 `086021619a9edaca426eed0e28786b5a03c56d699ad0c8708f5ff95f38227a71`.
- Original verdict: `verdict.json`, SHA-256 `43ef0d7f11ac861386b8844f9c34e41f8bb2b07586b8d754c0033234233221e9`.
- Supplement roster frozen before reading its method bodies: `roster-r1.txt`, SHA-256 `cb2c01bb61da16a2702e752c08069fc208e95084384616a36cff3c64f0e07151`.
- Exact roots: 1.9.4 and 1.10.2, Ornithe Feather. Ready/provenance/source-manifest identities are bound in both rosters. Every file hash in `roster-r1.txt` was checked against its release's `ornithe-feather.sources.sha256` entry and the raw source bytes.

## Per-slice verdicts

### Client chunk admission — PASS

`World.applyLiquidDrag` passes `isAreaLoaded(i, k, m, j, l, n, true)` in both releases (1.9.4 `World.java:1567`; 1.10.2 `:1577`). The paired `World.isAreaLoaded(int...)` bodies match: they check vertical range, shift X/Z block bounds into chunk coordinates, and test the min-to-max chunk rectangle inclusively via `isChunkLoadedAt` (1.9.4 `World.java:216–237`; 1.10.2 `:216–237`). The base method is abstract in both.

The concrete `ClientWorld.isChunkLoadedAt` bodies also match (1.9.4 `ClientWorld.java:104–106`; 1.10.2 `:105–107`): `allowEmpty || !this.getChunkSource().getChunk(chunkX, chunkZ).isEmpty()`. Since the query passes `true`, Java short-circuit evaluation admits the chunk and does not evaluate the `getChunk(...).isEmpty()` operand. Thus empty client chunks are admitted for this liquid query, as the comparison states. No pair difference was found in the gate or client override.

### Liquid-collision predicate — PASS

The base `Entity.hasLiquidCollision()` returns `true` in both versions (1.9.4 `Entity.java:1967–1969`; 1.10.2 `:2009–2011`). `PlayerEntity` overrides it with `!this.abilities.flying` in both (1.9.4 `PlayerEntity.java:1716–1719`; 1.10.2 `:1735–1738`). For the assigned local on-foot, non-flying player case, the aggregate current update's existing `entity.hasLiquidCollision()` gate therefore passes in both; flying players fail it in both. No pair difference was found.

### Local-client tick entry and direct caller order — PASS

The client main tick invokes `world.tickEntities()` under the same unpaused-world gate in both (1.9.4 `Minecraft.java:1443–1450`; 1.10.2 `:1432–1439`). `World.tickEntities()` iterates regular entities and calls the same `tickEntity(entity)` path (1.9.4 `World.java:1266–1287`; 1.10.2 `:1270–1291`). In both, `tickEntity(entity, true)` updates previous position/rotation state, then for an in-chunk, non-riding entity increments ticks and invokes virtual `entity.tick()` (1.9.4 `World.java:1393–1414`; 1.10.2 `:1403–1424`). This is the on-foot direct dispatch path for the local-player tick override.

The local-player override has the same order in both: require the player's X/Z chunk to be loaded at Y=0, call `super.tick()`, then report movement to the server (1.9.4 `LocalClientPlayerEntity.java:143–157`; 1.10.2 `:152–166`). The unmounted branch uses `sendMovementToServer()` after the superclass call. `PlayerEntity.tick()` also calls `super.tick()` in both (1.9.4 `PlayerEntity.java:170–200`; 1.10.2 `:175–205`). No pair difference was found in entry, direct virtual dispatch, or superclass/reporting order.

## Disposition

The supplement closes exactly the three dependencies marked outside the original frozen roster: client chunk admission, player liquid-collision eligibility, and local-client tick entry/caller order. All three pass with no difference affecting the assigned ordinary nonfalling water-current operation or its local on-foot/non-flying applicability. Together with the original bounded verdict, the author comparison is independently accepted for this scoped operation. This does not claim full movement-pair coverage, nor does it reopen unrelated provenance-sidecar checks or excluded falling, body/eye, or swimming behavior.
