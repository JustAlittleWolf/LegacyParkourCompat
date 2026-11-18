# Independent verdict — 1.9 → 1.9.3

## Binding

- Authorized claim: `lane-falling-water-fence-19-193/comparison.md`
- Exact comparison SHA-256: `bba2e664adf8c6d855cd3afcfc2e05f8411d87ebbfdff426b94946d6c179a422`
- Blind roster SHA-256: `d4f08bca537ee95828fdeaee67aba7bf55d87cabd0a1dadd922cde803c7635ec`
- Pair assessed: exact local endpoints `1.9` → `1.9.3`, Feather-named directories only; no inference from 1.9.1/1.9.2.

## Verdict

**Not accepted as source-bound evidence in this checkout.** The comparison's assertion that both source roots contain `.decompile-provenance.json` is contradicted by the primary local tree: each `decompiled_minecraft/<version>/` contains only the `ornithe-feather` directory, and neither exact endpoint has a `.decompile-provenance.json`, `.ready.json`, `.sources.sha256`, or equivalent verification sidecar. The expected Feather artifact/build and publication identities therefore cannot be independently bound to the local sources. The per-file hashes below establish local bytes only.

**Content-level audit of the two bounded claims: both no-difference conclusions are supported by the inspected local source bytes.** This is conditional source-content agreement, not formal source acceptance while the provenance gate remains open.

### 1. Falling-liquid cue from ordinary oak fence

- In `LiquidBlock.getFlow`, both versions gate the wall cue on `LEVEL >= 8`, walk horizontal directions, short-circuit the same-level `isFaceSolid` check before the above-neighbor check, apply `normalize().add(0.0, -6.0, 0.0)`, break, then normalize the result. The old method reads the current state via `getLiquidDepth(world, pos)`; 1.9.3 receives that current `BlockState` and uses `getLiquidDepth(state)`. Neighbor reads and cue order are otherwise the same. Evidence: 1.9 `LiquidBlock.java:128–168`; 1.9.3 `LiquidBlock.java:131–171`.
- Both `LiquidBlock.isFaceSolid` implementations exclude same-material faces, return true for `UP`, exclude ICE for other faces, then delegate. Both `Block.isFaceSolid` methods read the neighboring material and call `Material.isSolid()`. `Material.isSolid()` returns true in both exact trees. Vanilla's plain oak fence registration uses `new FenceBlock(Material.WOOD, PlanksBlock.Variant.OAK.getColor())`; therefore an ordinary oak fence at either the horizontal same-height or horizontal-plus-one position satisfies this cue in both endpoint sources, despite the fence's `isSolidRender` override.
- Source refs: 1.9 `LiquidBlock.java:80–90,128–168`, `Block.java:305–306`, `Material.java:62`, `Block.java:874–880`; 1.9.3 `LiquidBlock.java:82–92,131–171`, `Block.java:327–328`, `Material.java:62`, `Block.java:919–925`.

### 2. Local-player water-current path, velocity, and loaded gates

- `Entity.tick` calls `checkWaterCollisions()` in both versions. Unless riding a boat, that method calls `World.applyLiquidDrag` with `getShape().grown(0.0, -0.4F, 0.0).contract(0.001)`. The world method floors/ceils the bounds, returns false when the full area is not loaded, and otherwise scans x, then y, then z, applying each matching water block's flow when the liquid-height bound qualifies. Its final impulse gate is `vec3d.length() > 0.0 && entity.hasLiquidCollision()`; it normalizes and adds each vector component times positive `0.014` to the corresponding velocity. These operations/order are the same in both endpoints.
- The checked area gate is unchanged: `maxY >= 0 && minY < 256`, then every covered chunk coordinate must satisfy `isChunkLoadedAt(..., allowEmpty)`; `applyLiquidDrag` passes `true`. The checked `LocalClientPlayerEntity` inherits this entity tick path. `PlayerEntity.hasLiquidCollision()` is `!abilities.flying` in both. `isLocallyControlled()` returns true on the local client player in both but does not participate in this current application gate. No sign inversion is present in the flow impulse.
- Source refs: 1.9 `Entity.java:358,845–860`, `World.java:1554–1595,210–231`, `PlayerEntity.java:1710–1712`, `LocalClientPlayerEntity.java:443–444`; 1.9.3 `Entity.java:361,849–864`, `World.java:1560–1601,216–237`, `PlayerEntity.java:1717–1719`, `LocalClientPlayerEntity.java:447–448`.

## Exact local dependency bytes inspected

The following are SHA-256 values of current local files. They agree with the comparison's per-file source hash table where it supplies those paths; that table cannot be checked against a missing manifest.

| Version | Source | Local SHA-256 |
|---|---|---|
| 1.9 | `net/minecraft/block/Block.java` | `91c053e11d6bedaac292b6be2f5670e5a5c4522f175c1e227163eeb5499780b2` |
| 1.9 | `net/minecraft/block/FenceBlock.java` | `e1874cad83f73cbe7749fc08439df6b90c67bac95bdfe27b48e62b2a9bcb4761` |
| 1.9 | `net/minecraft/block/LiquidBlock.java` | `7e76c5ebb1a59d36be3517d09e5df60a0eb206ff5ace5ce889eac9152b53c6e8` |
| 1.9 | `net/minecraft/block/material/Material.java` | `f198b08007c0acbe4e2737f7e484a9c85ec183220daef03b954cc90d0af95d19` |
| 1.9 | `net/minecraft/world/World.java` | `d1e23db2e20c86eb37b964a0df6838f1422ade7b0c2fff9c869ff8345979aa98` |
| 1.9 | `net/minecraft/entity/Entity.java` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` |
| 1.9 | `net/minecraft/entity/living/player/PlayerEntity.java` | `edbb46236d1e42630e600e4afcb434eb6e9adecbfc4ac7e15710a5dea50010b1` |
| 1.9 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `04d6196e7e17595c0aed59b28bc1730f8be5ea3eccdb1f971e9e2492dfadfe06` |
| 1.9.3 | `net/minecraft/block/Block.java` | `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` |
| 1.9.3 | `net/minecraft/block/FenceBlock.java` | `d9375bba3b41ae408bdf65d69b25aecad522a6d0b45b38d30f77d12b343a4909` |
| 1.9.3 | `net/minecraft/block/LiquidBlock.java` | `b988d19e379d01d291cb014cb05e1579311c589654ce9ff4faa7f43bbba0533e` |
| 1.9.3 | `net/minecraft/block/material/Material.java` | `f198b08007c0acbe4e2737f7e484a9c85ec183220daef03b954cc90d0af95d19` |
| 1.9.3 | `net/minecraft/world/World.java` | `2fe063e0ec224eed9fc7d01b8f788c32035eed5fee5a9d98ea5e82296236e05a` |
| 1.9.3 | `net/minecraft/entity/Entity.java` | `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0` |
| 1.9.3 | `net/minecraft/entity/living/player/PlayerEntity.java` | `ef96c8a00443f724eadad05a6a4dcc8a8a2e23cd4c3390b9e0f1f78c9ff4d1af` |
| 1.9.3 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d` |

Stop condition reached: verdict returned with the exact unresolved source-identity blocker; no product files, source trees, wiki records, builds, tests, or runtime paths were changed or invoked.