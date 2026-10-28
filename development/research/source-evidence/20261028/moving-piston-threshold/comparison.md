# Moving piston `SHORT` threshold: source alias and boundary closure

## Finding

The source-head `SHORT` threshold is stable across the requested release aliases: 1.12, 1.12.1, and 1.12.2 all use `this.extending != 1.0F - this.progress < 0.25F`; 1.13, 1.13.1, and 1.13.2 all use `this.extending != 1.0F - this.progress < 4.0F`. In Java this parses as `extending != (1.0F - progress < threshold)`, because `<` binds before `!=`.

For an ordinarily constructed moving block entity, `progress` starts at the Java float default `0.0F`; each active tick adds `0.5F` and clamps only values at or above `1.0F` to `1.0F`. Thus the normal progression is 0, 0.5, 1.0. On that trajectory the 1.12 comparison is false at 0 and 0.5, true at 1.0; the 1.13 comparison is true throughout. The value stored in `SHORT` is the comparison result XORed with `extending`: in 1.13 normal live progress therefore yields `SHORT == !extending` throughout the source-head collision query. `PistonHeadBlock.getArmShape` consumes `SHORT` to select the corresponding short or full arm box/voxel shape.

This live-state conclusion does **not** extend to arbitrary NBT values. `readNbt` assigns `nbt.getFloat("progress")` directly and copies it to `lastProgress`; it performs no lower- or upper-bound normalization. The subsequent tick also has no lower clamp. Accordingly, a loaded 1.13 progress of `-3.0F` makes `1.0F - progress < 4.0F` false (and values below -3 remain false until enough ticks advance progress); loaded values outside normal progression can select different `SHORT` values. The source evidence closes the release alias, and closes the normal-live behavior, but does not justify a claim that the 1.13 comparison is true for every raw loaded state.

## Reachability boundary

In 1.12.x, `MovingBlock.getCollisionShape` obtains the moving entity's translated `getShape`, while its collision-box override delegates to `MovingBlockEntity.addCollisions`; the `SHORT` expression is in that latter path. The entity collision method skips its moving-shape branch only when progress is below 1 and the thread-local no-clip direction matches the move direction. The thread-local initializes to `null`; the branch is therefore reachable when no matching no-clip direction is active. `PistonHeadBlock` then reads `SHORT` in `getArmShape`.

In 1.13.x, `MovingBlock.getCollisionShape` delegates to `MovingBlockEntity.getCollisionShape`. The entity method has an analogous same-direction no-clip early return, then constructs the source-head state containing the threshold expression and asks the state for its collision shape. The ordinary provider can therefore reach this expression when that guard does not match. This is provider reachability only; this report does not claim an exhaustive player-query/traversal path or make a movement-parity conclusion.

## Per-release evidence and pins

The source roots below are the canonical read-only trees under `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\ornithe-feather`. For each release, the directory was directly enumerated with `Get-ChildItem -Force`; the listed provenance/ready/artifact records were read from that exact version directory. Actual SHA-256 values were recalculated from the three named Java files and match the recorded per-file source hashes/manifests.

| Release | `MovingBlockEntity.java` SHA-256 | `PistonHeadBlock.java` SHA-256 | `MovingBlock.java` SHA-256 | Exact threshold source |
|---|---|---|---|---|
| 1.12 | `a55ee14227fe6ac0932283cba0414e946ba8e2637f543b3a3fe13d4e45b7b16e` | `849757a70f525192fc01bac4883388326f9cbe55267ac336d78f378beddeac39` | `2bcea815334849934eaa3283eead3a7a27f7b77503a8789aae1479af725df5c4` | `1.0F - progress < 0.25F` |
| 1.12.1 | same as 1.12 | same as 1.12 | same as 1.12 | `1.0F - progress < 0.25F` |
| 1.12.2 | same as 1.12 | same as 1.12 | same as 1.12 | `1.0F - progress < 0.25F` |
| 1.13 | `c61aff4526dc75c16b7b3db2374261762c9c4bccae5e0bfd5d33400ada201a83` | `d0de8ab39c2d350054d7ee9ed66f0e1695f2b7d1d78324b9eabd955361b58e5f` | `19b8a41104da7b13eac58e2c01704043f6ff7ac1eea33457833bd41ea5810d61` | `1.0F - progress < 4.0F` |
| 1.13.1 | `6684ccc05adf8b29cac55f9796133405c6d4f6853051e651895303b657cd1c46` | same as 1.13 | same as 1.13 | `1.0F - progress < 4.0F` |
| 1.13.2 | same as 1.13.1 | same as 1.13 | same as 1.13 | `1.0F - progress < 4.0F` |

Pin evidence read directly from the version-directory provenance/cache records:

| Release | Raw client SHA-256 | Feather mapping artifact SHA-256 (merged jar) | Source-manifest SHA-256 |
|---|---|---|---|
| 1.12 | `59e84cf6acc3912e1705ab05c722485afb3ec33343eef4756d8c16edfe6411ca` | `73ce7d6d0a281db15fa030ae09e836387dae1172fdffcaf3e2d1a4840830b719` | embedded per-source hashes in `ornithe-feather/.decompile-provenance.json` |
| 1.12.1 | `19331fb29bf8b8d5ed764677c94cca9d66481e10104f660eb555894a4300cf22` | `f94febd2167d6091b4c157aef0a85c8791e6f8fb27b883e27a82a68c7b02b9c7` | embedded per-source hashes in `ornithe-feather/.decompile-provenance.json` |
| 1.12.2 | `8ada07da5ee77dad3527bd7278fbd05ee1fc8a597813b216a871a2d7d64cc64f` | `e48244030c53979793bdfbe48d7f1f3536f7e4f678ee5890a416198037cd46ca` | `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` |
| 1.13 | `d807a5ed9428b6c16b64888fd2b7fa76dcf5c55d06f96f7c0c47762c3fc3e26e` | `a7a61604c7b365db1f4250c4c0079172c3beff37f4e16f7a53a13d77f3976897` | `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a` |
| 1.13.1 | `f0b4cf6d1a87ae150f87a535651138ca7b82bdad8853f76e7ddbff1dd610ac87` | `6737db17e8cc724a018aad93d7fe5051a50e77136848d78d79b62310846fa65d` | `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba` |
| 1.13.2 | `3410887ba652f25792c7675bfaf9140e73b60e93cfbf113a803f8a98cb05c0f9` | `317384d4faccc2939c3b14252993d31745eb42aad59f2f0ea78893cbe4e7283b` | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` |

The two legacy releases store the source cache record inside each exact `ornithe-feather` directory as `.decompile-provenance.json`; their `inputs` entries pin the exact Mojang client and Feather merged mapping jar, and their `sources` entries pin each listed Java file. Releases 1.12.2 and 1.13.2 additionally carry verification records reporting zero source-file differences against their recorded verification source trees; their verification records note one regenerated intermediate mapped jar, while the published sources/raw inputs match. Those artifact caveats do not alter the per-file source hash comparisons above.

## Exact code references

All paths are relative to the version's `ornithe-feather` source root.

| Release(s) | Source and line range | Relevant operation |
|---|---|---|
| 1.12, 1.12.1, 1.12.2 | `net/minecraft/block/entity/MovingBlockEntity.java:273-304` | Tick `+0.5F`, upper clamp, next-tick completion, and raw NBT progress load. |
| same | `net/minecraft/block/entity/MovingBlockEntity.java:318-342` | Collision branch/gate and `SHORT` construction at line 331. |
| same | `net/minecraft/block/MovingBlock.java:127-140` | Ordinary collision provider hooks delegate shape and collision-box work to the block entity. |
| same | `net/minecraft/block/PistonHeadBlock.java:76-91` | Arm-shape selection reads `SHORT`. |
| 1.13 | `net/minecraft/block/entity/MovingBlockEntity.java:269-311` | Tick `+0.5F`, upper clamp, next-tick completion, and raw NBT progress load. |
| 1.13 | `net/minecraft/block/entity/MovingBlockEntity.java:320-352` | Collision-shape guard and source-head `SHORT` construction at line 338. |
| 1.13 | `net/minecraft/block/MovingBlock.java:106-116` | Collision-shape provider delegates to the moving block entity. |
| 1.13 | `net/minecraft/block/PistonHeadBlock.java:71-86` | Arm-shape selection reads `SHORT`. |
| 1.13.1, 1.13.2 | `net/minecraft/block/entity/MovingBlockEntity.java:271-311` | Tick `+0.5F`, upper clamp, next-tick completion, and raw NBT progress load. |
| same | `net/minecraft/block/entity/MovingBlockEntity.java:324-352` | Collision-shape guard and source-head `SHORT` construction at line 342. |
| same | `net/minecraft/block/MovingBlock.java:107-117` | Collision-shape provider delegates to the moving block entity. |
| same | `net/minecraft/block/PistonHeadBlock.java:71-86` | Arm-shape selection reads `SHORT`. |

No product implementation/history, wiki, backlog, runtime, build, or test evidence was consulted.
