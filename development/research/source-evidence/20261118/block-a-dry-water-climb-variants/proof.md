# Dry trapdoor variant equivalence for marginal water admission

**Result: COMPLETED — all six requested variants are equivalent for the accepted fixed-state witness in 1.13, 1.13.1 and 1.13.2.** The witness uses a dry, open, north-facing trapdoor in the player's feet cell and a north-facing ladder directly below. The six variants are spruce, birch, jungle, acacia, dark oak and iron. This closes only the provider-variant dependency for carrying the accepted oak-state water-admission fixture across those variants. It does not establish new water-scan, climbing, collision or trajectory behavior.

## Bounded inputs and immutable prior evidence

- Accepted fixture: repository commit `bec48f6c1d8371645699061895c2f753cc795087`, file `marginal-climb-water-admission/admission.md`; verified raw worktree SHA-256 `3837bc51f392464b9d1824f3f23f306efc05f07535fdb3c91266db0b8479f325`.
- Independent accepted verdict: repository commit `539091e2c7e2ff72b12e112765995c11bdd1addf`, file `marginal-climb-admission-audit/verdict.md`; verified raw worktree SHA-256 `1293ea7e4c87662896972a7ac58dbb67d88715f43c6c511b1a1c0adb795ea66a`.
- Both commit IDs resolve to the named commits in their respective scratch repositories. The two working files' raw hashes match the expected values above. They are the sole prior source claims used; the oak verdict itself was not repeated.
- Source root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/<release>/ornithe-feather/`, read-only. All three ready markers identify their exact release, `ornithe-feather`, and the corresponding Feather gen2 build.2 merged jar/Tiny mapping pair. Each ready marker's source-manifest SHA-256 matches the raw manifest file hash. Every source file listed below was hashed and matched its manifest entry.

| Release | Mapping identity | Source manifest SHA-256 | Ready marker SHA-256 | Provenance SHA-256 |
|---|---|---|---|---|
| 1.13 | `feather-gen2-1.13+build.2` | `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a` | `82a4a8f6a03fbd99a7b8577b1ae5980d95af07c7ded0da284106d16eb1e01a99` | `34652aeef6914b5cbff6619ae60a855aa676b2ae1f607eccdaa623ad09dc5e8d` |
| 1.13.1 | `feather-gen2-1.13.1+build.2` | `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba` | `f098b3a08b37276cc1a465a8a122df8de76d68786c854b92bac1ca51a79670de` | `c28ed59f2c507b6133f7677742d316d38e9993eaf1fbb6c2054bd68d1085e904` |
| 1.13.2 | `feather-gen2-1.13.2+build.2` | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` | `1d1c644c1deb05c02d11dc575ad834f4f72489124701d9b2b90357f43dc37b38` | The 1.13.2 ready marker has no provenance field; its companion provenance file SHA-256 is `e25950b7855fc4f2aee1a662f0be6b0af9f28788d9e27293075cfc5a3aa5d383`.

The 1.13.2 verification record reports `verified-identical-source-and-input-artifacts`, 2,711/2,711 source files checked, zero source differences and zero missing sources. Its regenerated intermediate archive difference is not a source-tree difference.

## Provider and predicate audit

`TrapdoorBlock.java` is byte-identical in all three releases (SHA-256 `26dd6911517029daef779cef046e943953356644a76372f1d47b1323054629da`). It defines the same state properties `OPEN`, `HALF`, `FACING` (inherited from `HorizontalFacingBlock`), `POWERED`, and `WATERLOGGED`. The default is north-facing, closed, bottom-half, unpowered and dry. `getShape` first checks `OPEN`; when open it returns the facing-specific slab without consulting `HALF`. The north slab is `Block.block(0, 0, 13, 16, 16, 16)` in 1/16-block units, occupying the north-edge band from Z=13/16 to 1. This leaves the accepted fixture's player maxZ near 0.6200 clear of the trapdoor. `WATERLOGGED` does not alter collision shape.

Registration slices in `Block.java` instantiate every requested wood variant with the same `TrapdoorBlock` class and `Material.WOOD`; only map color and shared wood strength/sound properties vary. Iron also uses `TrapdoorBlock`, with `Material.IRON` and iron strength/sound properties. `Blocks.java` binds each corresponding identifier to its registered provider. Thus all six blocks accept the exact same explicit property tuple `OPEN=true, FACING=NORTH, HALF=<either>, WATERLOGGED=false` and have identical collision geometry. `POWERED` does not enter `getShape` or `isClimbing` and can be either value in this bounded claim.

`LivingEntity.isClimbing()` has the same relevant body in all three releases: it samples the feet block; for a `TrapdoorBlock` it calls `canClimbTrapdoor`; that helper requires `OPEN`, reads the block directly below, and accepts `Blocks.LADDER` only when `LadderBlock.FACING` equals `TrapdoorBlock.FACING`. It does not inspect trapdoor variant, material, half or waterlogged state. `LadderBlock.java` is also byte-identical across the three releases (SHA-256 `c89dea1d7648bf5908e9d925b31b1ad6ead25bea3784838ae3d0fb505cfb9ab5`). The accepted fixture's matching north ladder therefore meets the same predicate for each variant.

The fixed dry/open input is attainable in vanilla state production. Placement reads fluid from the trapdoor's own cell to set `WATERLOGGED`, so a dry trapdoor cell remains dry even with water in the adjacent scan cell. A neighbor signal can set `OPEN=true` for all six variants. Wooden trapdoors can also be toggled by interaction; iron `use` returns false for iron material, so iron needs the redstone route. This is a state-production distinction only; the accepted witness supplies the fixed state. No state needs to be generated by the player's movement tick.

## Per-release and per-variant disposition

| Release | Spruce | Birch | Jungle | Acacia | Dark oak | Iron |
|---|---|---|---|---|---|---|
| 1.13 | Equivalent | Equivalent | Equivalent | Equivalent | Equivalent | Equivalent |
| 1.13.1 | Equivalent | Equivalent | Equivalent | Equivalent | Equivalent | Equivalent |
| 1.13.2 | Equivalent | Equivalent | Equivalent | Equivalent | Equivalent | Equivalent |

For every cell, the variant is registered as the shared `TrapdoorBlock`, supports the exact dry/open north-facing fixed state, resolves to the same north 3/16 slab, and passes the same open-trapdoor/matching-ladder facing predicate. No concrete delegate or exception exists within this scope.

## Exact source references

All paths are relative to `decompiled_minecraft/<release>/ornithe-feather/` and each cited file hash was checked against that release's source manifest.

| Release | Variant registrations in `net/minecraft/block/Block.java` | Shared geometry in `net/minecraft/block/TrapdoorBlock.java` | Predicate in `net/minecraft/entity/living/LivingEntity.java` |
|---|---|---|---|
| 1.13 | 1175–1180 (wood); 1401 (iron) | 25–60; default 38–40 | 1041–1068 |
| 1.13.1 | 1156–1161 (wood); 1382 (iron) | 25–60; default 38–40 | 1041–1068 |
| 1.13.2 | 1156–1161 (wood); 1382 (iron) | 25–60; default 38–40; fluid state 174–176 | 1041–1068 |

The inherited provider dispatch is confirmed in `net/minecraft/block/state/BlockState.java:205–210,313–314` in all releases. `Block.java` routes collision through `getCollisionShape` and fluid through its default empty provider at lines 409,695 in 1.13 and 393,679 in 1.13.1/1.13.2; the default collision flag is true at line 1910 in 1.13 and 1901 in 1.13.1/1.13.2. The state-provider fluid body is `TrapdoorBlock.java:174–176`. These files were also verified in every release manifest. `BlockState.java` is byte-identical in all three source trees, SHA-256 `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70`.\n\nManifest-verified relevant file SHA-256 values:

| Release | `Block.java` | `TrapdoorBlock.java` | `LadderBlock.java` | `LivingEntity.java` |
|---|---|---|---|---|
| 1.13 | `e5be72b77a28e89595009289964bb031bbb187d15d5698570615e6fc9a21a476` | `26dd6911517029daef779cef046e943953356644a76372f1d47b1323054629da` | `c89dea1d7648bf5908e9d925b31b1ad6ead25bea3784838ae3d0fb505cfb9ab5` | `c38e95c2c13e088b03d77fc2c6b98d4edd7e6ca5aef12b187c8e3f3ea782e864` |
| 1.13.1 | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` | `26dd6911517029daef779cef046e943953356644a76372f1d47b1323054629da` | `c89dea1d7648bf5908e9d925b31b1ad6ead25bea3784838ae3d0fb505cfb9ab5` | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` |
| 1.13.2 | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` | `26dd6911517029daef779cef046e943953356644a76372f1d47b1323054629da` | `c89dea1d7648bf5908e9d925b31b1ad6ead25bea3784838ae3d0fb505cfb9ab5` | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` |

## Scope limits

This proof only carries the accepted 1.13/1.13.1/1.13.2 adjacent-body-water admission witness from the oak provider to the six listed variants. It does not repeat the oak fixture's water scan or numerical collision derivation; does not reopen travel, climb impulse, swim/look/cache or optional clearance predicates; and does not compare activation or material behavior beyond noting the iron redstone route. No product files, wiki/backlog, tests, builds, runtime, generation or pushes were inspected or performed.
