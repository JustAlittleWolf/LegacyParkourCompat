# AutoJump brewing-stand witness (source only)

## Verdict

The order change produces a reachable AutoJump difference with the player on a bottom-slab floor. At feet Y=0.5, a brewing stand at block Y=1, and probe ray Y=1.01, both the old stem AABB and base-plate AABB intersect the ray; 1.12.2 accepts the stem first, while the 1.13 union decomposition accepts the plate first. Their different first candidates produce rises 1.375 and 0.625 respectively: the old version rejects `u > 1.2` and does not arm AutoJump; the new version passes `0.5 < u <= 1.2` and sets `ticksToNextAutojump = 1`. On a full-block floor, the ray is at local Y=0.51 and only the stem/upper-stem AABB qualifies, so both versions use rise 0.875 and arm the timer; the difference is reachable but floor-height gated.

## Exact source identity and integrity

- **1.12.2 / Ornithe Feather gen2 build.2**, exact run recorded in `decompiled_minecraft/1.12.2/ornithe-feather.provenance.json`; client SHA-256 `8ada07da5ee77dad3527bd7278fbd05ee1fc8a597813b216a871a2d7d64cc64f`; source manifest SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; artifacts manifest SHA-256 `8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c`.
- **1.13 / Ornithe Feather gen2 build.2**, exact run recorded in `decompiled_minecraft/1.13/ornithe-feather.provenance.json`; client SHA-256 `d807a5ed9428b6c16b64888fd2b7fa76dcf5c55d06f96f7c0c47762c3fc3e26e`; source manifest SHA-256 `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a`; artifacts manifest SHA-256 `a2c17075b601d9b83703f7ee188b04c6d9f68cac08d97b1a4f838fe8cd2e2c24`.
- **1.13.2 / Ornithe Feather gen2 build.2**, exact run recorded in `decompiled_minecraft/1.13.2/ornithe-feather.provenance.json`; client SHA-256 `3410887ba652f25792c7675bfaf9140e73b60e93cfbf113a803f8a98cb05c0f9`; source manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; artifacts manifest SHA-256 `fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e`.

All source-manifest and artifact-manifest hashes match each version's ready marker. SHA-256 of each source file cited below matches its entry in that source manifest. Toolchain in provenance: Java `25.0.3+9-LTS`, Gradle `9.7.1`, Vineflower `1.12.0`, Mapping-IO `0.9.1`, TinyRemapper `0.14.1`. The Feather version mapping artifacts are named `feather-gen2-<version>+build.2-mergedv2.jar` and `.tiny`.

## Compared source

All paths are relative to `decompiled_minecraft/<version>/ornithe-feather/`.

- `1.12.2`, `net/minecraft/block/BrewingStandBlock.java:36-37,70-72`: `SHAPE` is a full-footprint 0.125-high plate; `COLLISION_SHAPE` is centered at X/Z 0.4375..0.5625, Y 0..0.875. `addCollisions` appends `COLLISION_SHAPE` first, then `SHAPE`.
- `1.12.2`, `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:857-948`: AutoJump gates on enabled, timer `<=0`, on-ground, not sneaking/riding, nonzero input, view dot not `< -0.15`, and empty block at player `maxY` and one block above. It builds the player-width swept box, raises two lateral rays by `0.51F`, iterates collision AABBs in returned list order, accepts the first AABB intersecting either ray, scans overhead clearance, then sets timer to 1 only when computed rise is `>0.5F` and `<=1.2F`.
- `1.13` and `1.13.2`, `net/minecraft/block/BrewingStandBlock.java:33`: both define the shape as `VoxelShapes.or(plate, stem)`, with plate bounds `(1,0,1)..(15,2,15)` and stem `(7,0,7)..(9,14,9)` in sixteenths. The source file hash is identical in 1.13 and 1.13.2.
- `1.13.2`, `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:882-971`: the same AutoJump conditions, ray offset, first intersecting AABB selection, clearance scan, and timer thresholds apply. Collision stream shapes are flattened with `shape.getBoundingBoxes()` before the ordered loop.
- `1.13.2`, `net/minecraft/block/shape/VoxelSet.java:200-251` and `net/minecraft/block/shape/VoxelShape.java:109-112`: bounding boxes come from the voxel set's greedy `forEachBox` decomposition. For this union, it emits the plate first and the central upper stem box second: `[1/16,0,1/16]..[15/16,2/16,15/16]`, then `[7/16,2/16,7/16]..[9/16,14/16,9/16]`.
- `1.13.2`, `net/minecraft/world/WorldView.java:112-151`: block collision shapes are visited in the static collision query and translated to world coordinates; the AutoJump stream therefore sees the brewing stand's decomposed shape in the block-position stream order, and its per-shape box order is the above `forEachBox` order. The witness uses only one relevant obstacle, so block-position ordering is immaterial.
- Slab geometry: `1.12.2/net/minecraft/block/SlabBlock.java:20,39-43` and `1.13.2/net/minecraft/block/SlabBlock.java:27,51-60` put a bottom slab's top at local Y=0.5. Brewing-stand placement is unconstrained by a support predicate: 1.12.2 `Block.java:519-524` only checks replaceability; 1.13.2 `Block.java:562-563` defaults `canBePlaced` to true and the `BlockState.canSurvive` delegation returns it.
- Sprint speed: 1.12.2 `PlayerEntity.java:157,1407-1409` initializes base movement speed to 0.1 and reads the attribute; `LivingEntity.java:87,1295-1304` adds the 0.3 sprint modifier. This gives `f=0.13` for the fallback probe.

1.13's brewing-stand shape is byte-identical to 1.13.2; its AutoJump code also has the union flattening/first-hit logic and timer gate. The first 1.13 source therefore confirms the behavior already exists by that exact release; it does not identify the precise introduction patch inside the 1.12.2-to-1.13 interval.

## Reachable witness

Use a local client, an ordinary loaded area safely inside the world border, no other entities, and only a small bottom-slab floor. Place the brewing stand at `(0,1,0)` atop a bottom slab at `(0,0,0)`. Bottom slabs provide a lawful standing surface at feet Y=`0.5`; the stand occupies the next block cell. Keep adjacent slabs only where needed to support the player's width. BrewingStandBlock has no support restriction in either compared source; the 1.13 placement path calls `state.canSurvive`, whose default Block implementation returns true. Place the stand while the player is elsewhere, then approach.

Set player position before the attempted move to `(-0.3, 0.5, 0.8)`, standing on the slab floor. Set yaw `-90°`, pitch 0 (the source's rotation vector points +X), hold forward and sprint input, enable AutoJump, and do not sneak or ride. Sprinting applies the source's 0.3 additive movement-speed modifier to the 0.1 base, so `f=0.13` and AutoJump's `p=max(f*7,1/l)=0.91` extends the fallback ray from X=-0.3 through the stem's X=0.4375..0.5625 range. Choose an ordinary positive movement attempt toward +X. The old plate begins at X=0, so the player at center X=-0.3 touches it and is clipped at essentially zero displacement; the modern plate begins at X=1/16, so it clips at center X=-0.2375 after about 0.0625 movement. Both remain on ground. In the old case AutoJump's nonzero forward input fallback supplies the +X probe vector; in the modern case the clipped actual displacement points +X. The yaw makes the view dot +1 in both cases. The player-height block and block above its floored X/Y/Z remain empty.

The AutoJump probe starts at the post-move feet position plus Y=`0.51`, hence world Y=`1.01`, which is local Y=`0.01` inside the stand. Its lateral ray at Z=`0.5` crosses the plate and stem's central footprint; the opposite ray at Z=`1.1` is clear. The broad swept box includes the stand. The supporting slabs have top Y=`0.5`, below the probe ray, and are not qualifying candidates.

- 1.12.2 returned collision list order: stand stem first, plate second. The +X ray at `(y=1.01,z=0.5)` intersects both the old stem AABB (world maxY `1.875`) and plate AABB (world maxY `1.125`); the old first qualifying candidate is the stem. Its center floors to stand block `(0,1,0)`, overhead block `(0,2,0)` is empty, and rise from feet is `1.875-0.5 = 1.375`, so the final `u <= 1.2` requirement rejects the timer.

- 1.13/1.13.2 returned union-box order: plate first, then the central upper stem. At ray Y=1.01 the upper stem box starts at world Y=1.125 and does not intersect. The plate is the first qualifying candidate, with world maxY `1.125` and rise `1.125-0.5 = 0.625`. Its center also floors to `(0,1,0)`; overhead cell `(0,2,0)` is empty. Since `0.5 < 0.625 <= 1.2`, AutoJump sets `ticksToNextAutojump = 1`.

## Scratch integrity

This is a bounded source-only witness, not a complete pair-coverage or independent-audit report. No product files, wiki inventories, backlog, Git history, builds, tests or game/runtime were read or changed. Scratch location: `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-9d43-7363-8cf5-16eb00d97900/early-autojump-brewing-stand-witness/`.




