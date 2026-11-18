# 1.14–1.15.2 infinite-border source comparison

## Exact source identities

| Release | Mapping | Manifest sidecar SHA-256 | Version metadata SHA-256 | Publication sidecars |
|---|---|---|---|---|
| 1.14 | Ornithe Feather | `68bc39e38bc501e7c9af315de4678263e1a64606b0b9780099187c7984ccbc98` | `3d05fc911c8559105767713365ea1c29bbbeccf57214f286c86a598ce51f22a6` | `ornithe-feather.provenance.json`, `ornithe-feather.ready.json` |
| 1.14.4 | Ornithe Feather | `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc` | `b3b2a798e2d67b566008fe4a03767ae2c7ff3f8c7ba6751e7b71fc7299672d0a` | `ornithe-feather.provenance.json`, `.ready.json`, `.verification.json` (3,250 source files verified; zero differences) |
| 1.15 | Mojmap | `251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340` | `d154eaf07885a7fd6dfc6014bb9caf08fecc19ccd9a0862b31b1c7d1639e7785` | `mojmap.provenance.json`, `mojmap.ready.json`; batch names 1.15 and 1.15.1 |
| 1.15.1 | Mojmap | `c8fdce2f9432b72fc13c19a48f9ab3be203cac41a617139e81bac3552245912d` | `dda0859bca7447ff5f071643006b35a51f54e2fbef851a6665b424cf5743c598` | `mojmap.provenance.json`, `mojmap.ready.json` |
| 1.15.2 | Mojmap | `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` | `4a73008a73f3824b7c711750a5a37556df8614f193c0a531e292dad159a73a7c` | `mojmap.provenance.json`, `mojmap.ready.json` |

Per-file hashes below are from each published `*.sources.sha256` sidecar. `1.15` and `1.15.1` have identical hashes for every listed file; `1.15.2` does too except `LocalPlayer.java`.

| Operation source | 1.14 SHA-256 | 1.14.4 SHA-256 | 1.15 / 1.15.1 SHA-256 | 1.15.2 SHA-256 |
|---|---|---|---|---|
| Border shape: `world/border/WorldBorder.java` / `world/level/border/WorldBorder.java` | `dc01d59f1d8ad15fdf0cc33dc4280f871ce7d62f68a1822fe7233e28f606345f` | `dc01d59f1d8ad15fdf0cc33dc4280f871ce7d62f68a1822fe7233e28f606345f` | `5030e47d299d2741339dae0274dab41e63ae1eb1468171ef14f181a71a03086f` | same |
| Collision admission: `world/WorldView.java` / `world/level/CollisionGetter.java` | `1a9d57363b8123e48334065c363c5610e83dd353fe66edc2f08fed3ec09ddcb0` | `fcdf8dc98f97781f4639c4b8cda8a238edf834f3963e401e6eeef6cb475c4909` | `63daec0623c816d53f5dbdde407a27222fa21e62680c1096b63cf9082b1c25fd` | same |
| Movement collision: `entity/Entity.java` / `world/entity/Entity.java` | `15e9d4cde45d857ca3117ccfe2d5d9071c08e7ee4a67b4053f7b006fe7a4bfa9` | `7315a496c195da767de9d4936d3adb6efc3c419dc0f0e95d6f32781b0da1ba55` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` | same |
| Local jump scan: `LocalClientPlayerEntity.java` / `LocalPlayer.java` | `2b1ad3b3416949a9da2607a3ec2251aa69b2d9eebd76638c92e20749d625338f` | `708af6a3880fb58b67bf4604a5509b351719a9c2a0c06a8ec261586435bf00ce` | `7227da5233e3c72ced008187b92a6a86842f84778eaa9f40fa1eb117286af823` | `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd` |
| Shape boxes: `block/shape/VoxelShape.java` / `world/phys/shapes/VoxelShape.java` | `45b5c03a644d4c80c1af221c4e458d1cffe68438229b727e4eaf724468f7dab7` | `8efb68bf9d5efdb0ec48e401d1852e90458c7094f8fa0c8e9ae418f268c40fdd` | `5faceea0e4ce9ad0f2a1a2c72a89d9a768ab877c8728ae2968fe34aa1e03d3ea` | same |
| Box center: `util/math/Box.java` / `world/phys/AABB.java` | `b68667118ba729fc18fefa3b9de9060184f9d71f53a4bf10a8b7215863ff8b6e` | same | `82324c0e6a3d69e80424656e6098b39ce41f0f17a5e6efbe6542fe8eaca24a18` | same |
| Center-to-block conversion: `BlockPos.java`, `Vec3i.java`, `MathHelper.java` / `BlockPos.java`, `Vec3i.java`, `Mth.java` | `83e69c47ac85b6df0a625123201b57c3a83bf00d407720a218e831382265e599`; `f1d2e85d690b69c546b2dc783ede2ddbbfac400ca72271828929a45db03729a9`; `e39d5dfc69c17a9032be086d7d4d14cc63525962a0ff83615b1befdcca599843` | `8aa3d3a9ad3b3b9f230efaf5ac06a45ed7af3f59636ef7cf9507ba9317c22fd8`; same Vec3i and MathHelper hashes | `c71cc6f248a5c929879dd22a818d270dc6c586900e3dce57301ba86aa3115950`; `dd4ad167ad9b4be90e9b13216e8b450b7bc401ca700732c2dd78970739942c7d`; `a842059e498147d368126c896f0036916a90bdd125a09b09f9fe6658f2aaf0a4` | same |

## Operation equality table

| Operation | 1.14.4 Feather ↔ 1.15.2 Mojmap | 1.15 aliases | Result |
|---|---|---|---|
| Infinite-Y border shape | `WorldBorder` moving and static extents both subtract a rectangle with X/Z floor/ceil bounds and Y `−∞..+∞` from the infinite shape (`ONLY_FIRST`). | 1.15, 1.15.1, 1.15.2 border source hashes are identical. | Equal construction and bounds. |
| Thin-shell gate before block candidates | `WorldView.getBlockCollisions` lazily tests the entity’s current shape contracted and expanded by `1.0E-7`; it emits the border only for `!overlap(contracted) && overlap(expanded)`, then returns before cursor/block candidates. | `CollisionGetter` source hash identical across all three Mojmap versions; same deflate/inflate gate, one-shot flag and early border emission. | Equal admission predicate/order. The player move path also supplies the same border shape before axis collision resolution. |
| Shape-to-box strip | Player scan flattens collision shapes with `getBoundingBoxes().stream()`; shape implementation enumerates merged boxes. | `toAabbs().stream()` enumerates the same `forAllBoxes` output; shape source hash identical through 1.15.2. | Equal strip operation under renamed methods. |
| First intersecting box and block sample | First intersecting box sets the candidate top, takes `center()`, then constructs `BlockPos`; `Box.center` uses `lerp(0.5,min,max)`. | Same sequence in `LocalPlayer`; `AABB.getCenter` uses `Mth.lerp(0.5,min,max)`. `Vec3i(double,…)` floors each coordinate; `floor(NaN)` yields 0. | Equal center/cast path. An unbounded west exterior AABB has non-finite center coordinates; the floor conversion aliases those coordinates to block coordinate 0, so the follow-up block sample can read the origin column. |
| Auto-jump timer | On a candidate with `0.5 < top-playerY <= stepHeight`, writes `ticksToNextAutojump = 1`. | Same condition and write as `autoJumpTime = 1`; 1.15.2 `LocalPlayer` differs as a whole file, but the inspected operation matches 1.15/.1. | Equal timer effect for this path. |

`1.14` Feather source has the same infinite-Y border construction and the player center/cast/timer sequence, but its `WorldView.getCollisions` and `Entity` collision wiring differ from 1.14.4; hashes above identify those changed files. This endpoint comparison does not establish a patch introduction. The 1.14.1–1.14.3 Yarn directories had no provenance/source-manifest sidecars, so they are not treated as canonical evidence and no intermediate boundary is inferred.

## Source locations

- 1.14 / 1.14.4 Feather: `net/minecraft/world/border/WorldBorder.java`; `net/minecraft/world/WorldView.java`; `net/minecraft/entity/Entity.java`; `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`; `net/minecraft/block/shape/VoxelShape.java`; `net/minecraft/util/math/{Box,BlockPos,Vec3i,MathHelper}.java`.
- 1.15 / 1.15.1 / 1.15.2 Mojmap: `net/minecraft/world/level/border/WorldBorder.java`; `net/minecraft/world/level/CollisionGetter.java`; `net/minecraft/world/entity/Entity.java`; `net/minecraft/client/player/LocalPlayer.java`; `net/minecraft/world/phys/{AABB,shapes/VoxelShape}.java`; `net/minecraft/core/{BlockPos,Vec3i}.java`; `net/minecraft/util/Mth.java`.
