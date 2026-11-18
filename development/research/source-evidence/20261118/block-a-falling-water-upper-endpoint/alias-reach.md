# 1.8.0–1.8.8 alias reach: falling-only old liquid scan

## Verdict

**ACCEPT for 1.8.0, 1.8.1, and 1.8.2–1.8.8**, narrowly extending the already accepted exact 1.8.9 → 1.9 call-boundary witness to each 1.8 patch source. For every alias the bounded old-only current-cell contribution is the same: the 1.8 scan visits the falling-water cell at the contracted player-box maximum integer Y=64, normalizes `LEVEL >= 8` to depth 0, obtains the WEST lower-water contribution, applies the dry ordinary oak-fence face cue, aggregates and normalizes the cell flow, then adds the aggregate times `0.014` for a nonflying player.

This extends only the accepted *call-boundary source witness*. It does not establish that the fractional lower-Y fixture naturally occurs during an ordinary player tick, a stable complete tick, trajectory change, sustained propagation, any other fence material, or a patch cutover within 1.8.x. It does not re-audit 1.8.9 or 1.9.

## Frozen inputs and provenance

Required project docs read: `README.md`, `buildSrc/README.md`, `development/workflows/discovery/WORKFLOW.md`, and `development/workflows/discovery/source/WORKFLOW.md`. Host/execution environment: Windows 11, PowerShell 7.6.9.

Assigned immutable source-only inputs (all raw SHA-256 values match the assignment):

- `legacy-fluid/falling-current-boundary/evidence.md`: `595a2a01ebe0e87320815fc2b7d23043d10b74ee1fa7971c44cda9bacf75e795`.
- `legacy-fluid/falling-current-boundary-audit/audit.md`: `5329f89c38e4d8ea9012574b43701d66414b1020e0297b0d0534c43179737108`.
- `development/research/source-evidence/20261117/block-a-oak-fence-pre112-verified-b/lane-falling-water-fence-prior/comparison.corrected-r1.md`: `0e9bebe9aba84ae1b6d4fc8f9e319b064092eb727e4e808cbaddc4229df67846`.

Each alias source root is `decompiled_minecraft/<release>/ornithe-feather/`, produced by the pinned `lpc-decompile-v1` pipeline with Ornithe Feather build.2. For 1.8.0–1.8.8, the exact `.decompile-provenance.json` manifests were parsed; their manifest SHA-256 and the raw-client and Feather mergedv2 SHA-256 are listed below. Actual hashes for the nine bounded source files were checked against each release's provenance `sources` entries: zero mismatches for every release. For the accepted 1.8.9 reference, exact ready/provenance/source-manifest identities are from the assigned accepted evidence: ready `fe0ec792349ae43c91d0a30f23660c94b2f5c8d31a29643f73ed0de367110b95`, provenance `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185`, source manifest `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`.

| Exact release | Alias verdict | Provenance SHA-256 | Raw client SHA-256 | Feather mergedv2 SHA-256 |
|---|---|---|---|---|
| 1.8 | ACCEPT | `52c8312c34a1a491d4a200c84e90f5d8d95c7e5caa9571f864d8e70f9737db05` | `ccccd0a92f76fbb24279691daad9b67f85d80471a37e59e0da76d7754982dfe8` | `3c020d1588b4aab403f5df077ca91b6e3776da2823d3b55202f4616f0d5ae5fe` |
| 1.8.1 | ACCEPT | `f3a3baa039f0e0ade6e656dfa11a4529909a4b709ac489703c4250262cefb1ca` | `66a749ad95adcb929439155c0341309e89e437444867e108dcb36fd16493b0bd` | `dac40cbd25c4d76d99d4263fd9754ab0195efd658756b6828fd06f0c4a8002f7` |
| 1.8.2 | ACCEPT | `8ad11ea36f2dda39ae30d57daa53316a8c0889a2f3c0c84ee5d79b9b21c25472` | `5cc5b24312f3d182bf06e7f1a4da3a05ce94be6c861d5c9995c86e6d8811fbe9` | `8235d85a8471d541f2bad73c2a60c6132a9432fe804b98369982fc8de847f37b` |
| 1.8.3 | ACCEPT | `7da5f772d7ed1433cb3da23861723ebaec7cb714ce49c4d1a5c30499fec3afed` | `4da43d06c8c1605a9a3ba1bf22893245cf1b108eb5c8fed859012252370c2c06` | `e0ad2917dcc3a91586fb6f9b1bde78e55f1ece908572672885395e0d045dd9a2` |
| 1.8.4 | ACCEPT | `b2207bf9ab2a7561bf73ac92a63cc67760ccc6fd9e2378ff1e10710ecbb03a00` | `d68a3aa5271abece4ce2b7048f6caf4f58071476344d9a52fe9ab1410c5d458b` | `8ee5582ac3f6581e85ac27de5c9df9427a4bf67d0652531cf2e94eac4dde9b8c` |
| 1.8.5 | ACCEPT | `986d25dfd11927abed51c92643e5beda48237da875fbcdf1a48a930456ea99ce` | `2d825073de0da4383c0525e81d181ec81f92d17daa2f0b81faff3850492e461d` | `18ec5e1a8f4cdfc3a886a7e24fe6a8594f04f51f6669bb0619d727f27cdd887d` |
| 1.8.6 | ACCEPT | `c96f635864596f6cbd6e34ba7ba184c22a8139b25f099cc8fbbff46a85286ea9` | `e597384cf964950b02650a39f73c2cbf5ce9e51d934cdfa1291530896e7cf100` | `d1507e3253a944d59dd0b924b6495deb7732ecadb5f8bd06f7139c241639ad81` |
| 1.8.7 | ACCEPT | `6c6a5b64243ae7931ea1fa72a6c33440d5eec4070461cba80cdf51bf95dd93ef` | `2e45066ec194c6daec5f7dbb27822ef0b5855d5829f62392906620145689a3e0` | `e0cd7842acda42ae23d2e9d031358c1faac1ac01a139bb29ac8034fbaa5fa27b` |
| 1.8.8 | ACCEPT | `588f83dc64ce0bbd0f11880d2da8e59d4d097d43225025a2a74a2b26cf909ac1` | `9481ed51d7fc4be54ec38c509f84124fbac5d9fea238bbde5b2e6c4f753b1ac8` | `934d6112c5c1fa2d1801dbe78aee37ffbcec7ec03e1ed3a62c73de3c7925437f` |

## Alias comparison evidence

For 1.8.2–1.8.8, each of these nine entire source files is byte-identical to the same 1.8.9 file (checked SHA-256 equality, after checking each file against its own provenance map): `Entity.java`, `World.java`, `LiquidBlock.java`, `Box.java`, `Vec3d.java`, `LocalClientPlayerEntity.java`, `PlayerEntity.java`, `Block.java`, and `Material.java`. Therefore these aliases reuse one unique normalized source comparison against the already accepted 1.8.9 witness; each was not independently re-read as a new movement implementation.

1.8.0 and 1.8.1 have distinct decompiled files, so their bounded relevant members were inspected directly. The exact source bindings are below; the checked behavior is the same as the accepted 1.8.9 trace. Their renamed decompiler locals are immaterial to the specified expressions and operation order.

### 1.8.0 and 1.8.1 bounded trace

- `Entity.checkWaterCollisions` passes `getShape().grown(0.0, -0.4F, 0.0).contract(0.001, 0.001, 0.001)` into `World.applyLiquidDrag`: 1.8 lines 745–759; 1.8.1 lines 750–764. `Box.contract(dx,dy,dz)` moves each maximum inward by that amount: both `Box.java` lines 176–184.
- `World.applyLiquidDrag` computes all six integer bounds with `floor(min)` and `floor(max + 1.0)`, checks `isAreaLoaded(minX,minY,minZ,maxX,maxY,maxZ,true)` before visiting cells, and loops in X, then Y, then Z order. It gets each block state, compares material, applies `l = y + 1 - getHeightLoss(LEVEL)` with `if (upperY >= l)`, sets contact true, and accumulates `applyMaterialDrag`. After the loops it normalizes the aggregate only if positive length and `entity.hasLiquidCollision()`, adding components times `0.014`. Bindings: 1.8 `World.java` 1462–1502; 1.8.1 1468–1509.
- For the fixture maximum Y exactly 64, `floor(64 + 1.0) == 65` and the exclusive loop visits Y=64. Its X/Z bounds and loaded-area request are the same as in the accepted witness. The lower level-0 source at Y=63 remains in both the old scan and its zero-flow water-admission gate.
- `LiquidBlock.getHeightLoss` maps state values `>= 8` to 0 before returning `(state + 1) / 9.0F`; `getLiquidDepth` maps `>= 8` to 0. Bindings: 1.8 `LiquidBlock.java` 41–47, 53–56; 1.8.1 42–48, 54–57.
- `getFlow` scans `Direction.Plane.HORIZONTAL` order. On a non-liquid side it checks `blocksMovement`; when false, reads `side.down()` and adds `(side - center) * (lowerDepth - (centerDepth - 8))`. It then checks the central raw `LEVEL >= 8`; each face query is short-circuit `isFaceSolid(side,direction) || isFaceSolid(side.up(),direction)`. On the selected WEST side, air fails the first face query and the dry oak fence above passes the second; the branch normalizes, adds `(0,-6,0)`, and normalizes again. Bindings: 1.8 `LiquidBlock.java` 129–161; 1.8.1 130–162.
- `LiquidBlock.isFaceSolid` delegates non-liquid horizontal faces to `Block.isFaceSolid`; the base predicate returns `material.isSolid()`. In both 1.8.0 and 1.8.1 oak fence registration constructs a `FenceBlock(Material.WOOD, ...)` (1.8 `Block.java` line 936; 1.8.1 lines 974–976), and `Material.isSolid()` returns true (both `Material.java` lines 55–57). The cue does not query fence shape or connection bits.
- `Vec3d.normalize` uses the same zero-length threshold and `MathHelper.sqrt` path as the accepted witness: 1.8 `Vec3d.java` 30–33; 1.8.1 lines 34–37. The existing accepted evidence supplies the composed numeric trace; this extension makes no new numeric claim.
- Player applicability is unchanged. `PlayerEntity.hasLiquidCollision()` returns `!abilities.flying`: 1.8 `PlayerEntity.java` 1612–1614; 1.8.1 lines 1615–1617. The local client player calls `super.tick()` only after `world.isChunkLoaded(new BlockPos(x,0.0,z))`: both 1.8.0 and 1.8.1 `LocalClientPlayerEntity.java` lines 111–120. The nine-file identity comparison above carries the checked client/player member continuity for 1.8.2–1.8.8 from 1.8.9.

## Scope limits and disposition

This is a source-continuity gate for one pre-established bounded fixture. It closes the 1.8.0–1.8.8 stable alias reach of that witness, including scan box/floor formula, Y membership and load-check order, `LEVEL >= 8` normalization and lower-neighbor flow, ordinary oak-fence material predicate, normalized aggregation and ordinary nonflying player applicability. It does not re-open accepted 1.8.9/1.9 claims; invent a new witness or mechanic; assert natural full-tick reachability; or infer any other material. No product source/history, wiki inventory, build, test, runtime, generation, backlog write or push was consulted or performed.

