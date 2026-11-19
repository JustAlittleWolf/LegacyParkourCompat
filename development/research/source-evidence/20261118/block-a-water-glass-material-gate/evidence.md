# Glass material gate for ordinary water current

## Bounded result

The gate is continuous across all 24 exact stable releases from 1.8 through 1.12.2. In every release, full glass and stained glass are registered with `Material.GLASS`; that field is an ordinary `Material` instance, whose inherited `blocksMovement()` returns true. The horizontal-neighbor path in `LiquidBlock.getFlow` first checks for liquid at that neighbor; when it is dry, it samples the block below only if `!neighborMaterial.blocksMovement()`. Thus both full-glass identities suppress the below-neighbor water contribution throughout the checked interval. No individual discrepancy or cutover is indicated.

This is the horizontal-neighbor `blocksMovement()` predicate. It is distinct from the falling-liquid side-face `Material.isSolid()` cue. Glass also inherits `isSolid() == true`, but the branch under review calls `blocksMovement()`. The claim excludes glass panes, stained-glass panes, waterlogging, other glass-like blocks and rendering/opacity behavior.

## Exact comparison and release coverage

Every row uses `decompiled_minecraft/<version>/ornithe-feather/net/minecraft/`. The two `Block.java` lines register `glass` and `stained_glass`; the `Material.java` lines identify `GLASS` and the base `blocksMovement` method; `LiquidBlock.java` lists `getFlow`, the dry-side `blocksMovement` gate, its below-neighbor sample, and the weighted level formula. For 1.8.1, the decompiler names the formula local `var9` instead of `k`; it is still `j - (i - 8)`.

| Release | Block registrations | Material.GLASS / base predicate | getFlow / gate / below / weight | Exact source identity SHA-256 |
|---|---:|---:|---:|---|
| 1.8 | 808, 947 | 22, 63 | 129, 137, 138, 140 | `.decompile-provenance.json` `52c8312c34a1a491d4a200c84e90f5d8d95c7e5caa9571f864d8e70f9737db05` |
| 1.8.1 | 819, 991 | 22, 63 | 130, 138, 139, 141 | `.decompile-provenance.json` `f3a3baa039f0e0ade6e656dfa11a4529909a4b709ac489703c4250262cefb1ca` |
| 1.8.2 | 863, 1031 | 22, 68 | 129, 137, 138, 140 | `.decompile-provenance.json` `8ad11ea36f2dda39ae30d57daa53316a8c0889a2f3c0c84ee5d79b9b21c25472` |
| 1.8.3 | 863, 1031 | 22, 68 | 129, 137, 138, 140 | `.decompile-provenance.json` `7da5f772d7ed1433cb3da23861723ebaec7cb714ce49c4d1a5c30499fec3afed` |
| 1.8.4 | 863, 1031 | 22, 68 | 129, 137, 138, 140 | `.decompile-provenance.json` `b2207bf9ab2a7561bf73ac92a63cc67760ccc6fd9e2378ff1e10710ecbb03a00` |
| 1.8.5 | 863, 1031 | 22, 68 | 129, 137, 138, 140 | `.decompile-provenance.json` `986d25dfd11927abed51c92643e5beda48237da875fbcdf1a48a930456ea99ce` |
| 1.8.6 | 863, 1031 | 22, 68 | 129, 137, 138, 140 | `.decompile-provenance.json` `c96f635864596f6cbd6e34ba7ba184c22a8139b25f099cc8fbbff46a85286ea9` |
| 1.8.7 | 863, 1031 | 22, 68 | 129, 137, 138, 140 | `.decompile-provenance.json` `6c6a5b64243ae7931ea1fa72a6c33440d5eec4070461cba80cdf51bf95dd93ef` |
| 1.8.8 | 863, 1031 | 22, 68 | 129, 137, 138, 140 | `.decompile-provenance.json` `588f83dc64ce0bbd0f11880d2da8e59d4d097d43225025a2a74a2b26cf909ac1` |
| 1.8.9 | 863, 1031 | 22, 68 | 129, 137, 138, 140 | `ornithe-feather.sources.sha256` `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` |
| 1.9 | 710, 891 | 24, 70 | 128, 139, 140, 142 | `.decompile-provenance.json` `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a` |
| 1.9.1 | 710, 891 | 24, 70 | 128, 139, 140, 142 | `.decompile-provenance.json` `e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a` |
| 1.9.2 | 710, 891 | 24, 70 | 128, 139, 140, 142 | `.decompile-provenance.json` `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510` |
| 1.9.3 | 755, 936 | 24, 70 | 131, 142, 143, 145 | `.decompile-provenance.json` `43325cdd53ca34b88ee9af8464782b323a948b98c8a556c4761f1e5737b84c3a` |
| 1.9.4 | 755, 936 | 24, 70 | 131, 142, 143, 145 | `ornithe-feather.sources.sha256` `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` |
| 1.10 | 760, 941 | 24, 71 | 131, 142, 143, 145 | `.decompile-provenance.json` `df266e2c3009dd1a873c1665c30fe388078ed1c6227668e10cde87409a18c6da` |
| 1.10.1 | 760, 941 | 24, 71 | 131, 142, 143, 145 | `.decompile-provenance.json` `a9a50497930848bf0ddec732b6d424e947f643a8fa6290e01425ac2bbfce219f` |
| 1.10.2 | 760, 941 | 24, 71 | 131, 142, 143, 145 | `ornithe-feather.sources.sha256` `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71` |
| 1.11 | 765, 946 | 24, 71 | 131, 142, 143, 145 | `ornithe-feather.sources.sha256` `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca` |
| 1.11.1 | 765, 946 | 24, 71 | 131, 142, 143, 145 | `ornithe-feather.sources.sha256` `0d5f4697d4e4ce53b36d0dbfec89d44830eb76753a6643b49e42f0857753066d` |
| 1.11.2 | 765, 946 | 24, 71 | 131, 142, 143, 145 | `ornithe-feather.sources.sha256` `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0` |
| 1.12 | 784, 965 | 24, 71 | 139, 150, 151, 153 | `.decompile-provenance.json` `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467` |
| 1.12.1 | 784, 965 | 24, 71 | 139, 150, 151, 153 | `.decompile-provenance.json` `0ff5246e8d29c04554cc9ec93ae9b4abef539652325b6c0ac822851d7186a9c8` |
| 1.12.2 | 784, 965 | 24, 71 | 139, 150, 151, 153 | `ornithe-feather.sources.sha256` `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` |

For the 17 `.decompile-provenance.json` trees, the recorded source hashes for `Block.java`, `Material.java`, and `LiquidBlock.java` were checked against the local files. For the seven release-root sidecar sets, each `ready.json` reports `status: ready`; its SHA-256 references to `ornithe-feather.sources.sha256` and `artifacts.sha256` match those files, and the three relevant source-file hashes match the local source files. The sidecar provenance names the exact release and `ornithe-feather` mapping artifacts. In particular, 1.8.9 and 1.12.2 do have these release-root readiness/provenance sidecars; their absence is only inside the mapping directory under the older `.decompile-provenance.json` filename.

Across 1.8–1.8.9, `getFlow` accumulates a normalized immutable `Vec3d` contribution per direction; from 1.9 onward it accumulates direction components in three doubles before constructing the vector. The inspected horizontal gate, lower-cell read, liquid-depth admission, and `j - (i - 8)` weight are equivalent throughout. In 1.10+ the current block state is passed to `getFlow` and `getLiquidDepth`; this does not change the neighbor material test. These are the provider and consumer differences relevant to this one dependency; no full-pair inventory or unrelated water-current claims are implied.

## Reachable player consequence with valid world coordinates

Place flowing water at `(0, 64, 0)` with level `i = 1`. Place dry full glass at its east neighbor `(1, 64, 0)` and a water source with level `j = 0` below that at `(1, 63, 0)`. Other horizontal neighbors of the current cell are dry air, with dry lower cells. The local player stands unmounted at `(0.5, 64, 0.5)` on a full-glass floor block at `(0, 63, 0)` and is not flying.

The source basis for this geometry/path is 1.8.9 `PlayerEntity.java:417` and 1.9 `PlayerEntity.java:387` (`setSize(0.6F, 1.8F)`), `Entity.java:747` / `848` (`checkWaterCollisions`), `util/math/Box.java:59-66,176-183` (`grown` and `contract`), `World.java:1486-1526` / `1554-1595` (cell bounds, upper-height admission and `0.014` update), and `PlayerEntity.java:1617-1618` / `1710-1711` (`hasLiquidCollision`). The historical standing player box is 0.6 wide and 1.8 high: at that position its unmodified bounds are X/Z `[0.2, 0.8]`, Y `[64, 65.8]`. `checkWaterCollisions` applies `grown(0, -0.4, 0)` and then contracts each face by `0.001`, giving X/Z `[0.201, 0.799]`, Y `[64.401, 65.399]`. `World.applyLiquidDrag` therefore scans only X/Z cell 0 and Y cells 64 and 65. Cell `(0,64,0)` is admitted because level-1 water has liquid top `64 + 1 - (1 + 1)/9.0F` and the exclusive upper scan bound is 66; cell `(0,65,0)` is dry air and contributes no drag. All coordinates, including the lower sample at Y=63, are within historical world height `[0,256)`.

In `getFlow` for the admitted center cell, the east side has no liquid, but glass `blocksMovement() == true`, so the lower source is not read. Replacing only the east-side glass with air enables the lower read, which gives `k = 0 - (1 - 8) = 7`; the east weighted contribution is `(7,0,0)`. With that as the only nonzero contribution, `getFlow` returns positive X. `World.applyLiquidDrag` normalizes its accumulated flow and, because `PlayerEntity.hasLiquidCollision()` returns `!abilities.flying`, adds `+0.014` to X velocity for this nonflying player when the east block is air. With either full-glass identity at east, that lower-water contribution is absent. This isolates the material gate from the earlier water-cell admission boundary.

## State-projection boundary

Project this result only for the exact vanilla full-block identities `minecraft:glass` and `minecraft:stained_glass` (including stained-glass color state). Do not infer it from transparency, opacity, full-cube shape, or a generic glass label. Pane identities, waterlogged states, and other modern blocks' fluid properties do not establish the historical dry full-glass case.

## Prior evidence and checks

The assigned accepted evidence remains unchanged: `development/research/source-evidence/20261117/block-a-early-fluid-bounds/water-upper-bound-liquid-admission.md`, SHA-256 `a82ce04764f3d0c5f2155c0fda86b0cf29aa460d39be2ac3bd296baef32cd330`.

This is source-only evidence. No source generation, implementation, test, build, or runtime check was performed.

