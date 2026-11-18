# Blind source roster — 1.9 → 1.9.3

Pair fixed: exact local source directories `decompiled_minecraft/1.9/ornithe-feather` and `decompiled_minecraft/1.9.3/ornithe-feather`; no 1.9.1 or 1.9.2 inference. Roster frozen before opening the authorized comparison.

## Inventory

1. **Dry ordinary oak fence adjacent-face response.** Trace the local-player corner probes in `LocalClientPlayerEntity.mobTick` to `Entity.pushAwayFrom`, `World.isFullCube`, and `FenceBlock`’s resolved properties, shapes, and collision contributors. The bounded cue is a horizontal neighbor at player probe height and a same-x/z neighbor one block above; include exact face/shape tests that decide whether the probe treats the fence neighbor as full. Review corresponding `BlockState`/collision-shape dispatch as dependency if it decides those results.
2. **Water-current response to local player.** Trace the local player tick through `Entity.tickMovement`/movement application, `World.applyLiquidDrag` area-loaded and water-height gates, `LiquidBlock.applyMaterialDrag`/`getFlow`, and velocity writes, plus the movement path that consumes those velocities. Include adjacent horizontal and upper-face solid checks and their collision/face predicate dependencies. Exclude non-player trajectory conclusions.

These are the only two owned operations; no additional differences are admitted by this roster.

## Locally observed source-file identities

The canonical directories contain source files but no sidecar `*.ready.json`, `*.provenance.json`, `*.sources.sha256`, or publication verification files at either exact endpoint. These file hashes bind local bytes only and do not prove source publication or mapping-build identity.

- `1.9/.../block/FenceBlock.java`: `e1874cad83f73cbe7749fc08439df6b90c67bac95bdfe27b48e62b2a9bcb4761`
- `1.9.3/.../block/FenceBlock.java`: `d9375bba3b41ae408bdf65d69b25aecad522a6d0b45b38d30f77d12b343a4909`
- `1.9/.../entity/Entity.java`: `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a`
- `1.9.3/.../entity/Entity.java`: `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0`
- `1.9/.../world/World.java`: `d1e23db2e20c86eb37b964a0df6838f1422ade7b0c2fff9c869ff8345979aa98`
- `1.9.3/.../world/World.java`: `2fe063e0ec224eed9fc7d01b8f788c32035eed5fee5a9d98ea5e82296236e05a`
- `1.9/.../block/LiquidBlock.java`: `7e76c5ebb1a59d36be3517d09e5df60a0eb206ff5ace5ce889eac9152b53c6e8`
- `1.9.3/.../block/LiquidBlock.java`: `b988d19e379d01d291cb014cb05e1579311c589654ce9ff4faa7f43bbba0533e`
- `1.9/.../client/entity/living/player/LocalClientPlayerEntity.java`: `04d6196e7e17595c0aed59b28bc1730f8be5ea3eccdb1f971e9e2492dfadfe06`
- `1.9.3/.../client/entity/living/player/LocalClientPlayerEntity.java`: `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d`

Open evidence gate: exact source publication identity and mapping artifact/build identity are not recoverable from the supplied local tree as found. Do not treat this roster or its local hashes as a source-acceptance verdict.