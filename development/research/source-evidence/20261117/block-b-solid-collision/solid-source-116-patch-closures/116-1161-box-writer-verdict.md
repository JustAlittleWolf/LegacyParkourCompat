# Independent source-only verdict: 1.16 → 1.16.1 solid player-box dependency

## Bounded scope and verdict

Pair: Minecraft 1.16 to 1.16.1, Mojmap source trees. Bounded operation: client player movement route through local-player movement dispatch, living travel/step checks, entity bounding-box writers and collision/support queries, then vanilla solid-block collision-shape lookup and shape math. Fluids and packet semantics are outside this review.

Verdict: **No source-confirmed difference** in the reviewed solid box operation. This independently agrees with the author-provided route summary: local Entity box writers, collision/query/step/support, and shape providers show no movement difference. The verdict covers the checked source pair and does not establish runtime parity or inspect packaged resource bytes.

## Exact source identity and census

Canonical files are at `decompiled_minecraft/1.16/` and `decompiled_minecraft/1.16.1/`.

| Sidecar | 1.16 actual SHA-256 | 1.16.1 actual SHA-256 |
|---|---|---|
| `mojmap.ready.json` | `5b6cb24f95e83b856c53f646d6477f2e46dae2276737dee56dd07c5bd745e4a2` | `c19d707f7d6622733b5189599ff77aa7999cc11a3d900f29d150f9fab203ffcb` |
| `mojmap.provenance.json` | `371d50521a183f4a3f42fac2d18021f228e0a4e6b2e8a9838c2805391709f2a9` | `a6ff065188a400ec891980cdf2e6c0c238bf5e05168a2f69b29c6cecc2214b98` |
| `mojmap.sources.sha256` | `e3c23e94ac985c25dec0c768aed17b443ba4823662cf1e152235c99756ae5d4a` | `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754` |
| `artifacts.sha256` | `b482d7aa44422dfafebad3a0b2a886d47d1f01135f99055aea5973faa110e8b2` | `a113f9d59fa4582effb2ebf548c4388a54c9c5654fbf57fae7974dce896d1f98` |

The actual source-manifest digests equal the values in each ready marker. Recomputing every listed source SHA-256 against its file gave 3,572/3,572 matches on each side; actual byte totals match the ready markers (19,220,152 for 1.16 and 19,220,344 for 1.16.1). The 1.16 ready marker's provenance digest matches the provenance file. The 1.16.1 ready marker has no provenance-digest field; its provenance sidecar itself is present and declares `versionId: 1.16.1`, `namespace: mojmap`, and the 1.16.1 exact run.

I compared the two source manifests directly: 3,572 paths each, zero path-only entries, exactly three hash changes:

| Relative Java source | 1.16 SHA-256 | 1.16.1 SHA-256 |
|---|---|---|
| `com/mojang/realmsclient/RealmsMainScreen.java` | `e948fdfa7f8791a524e4015e229ab0ba556e811d48e2a129fe95d00001681eee` | `cb1e00ed6734be3c3613d0fdd8f23e24bb863440aef2c9bee1bc681da686a236` |
| `com/mojang/realmsclient/gui/screens/RealmsUploadScreen.java` | `d119cecbb195c9b165d84fb4066be763cea95f6e23d9c31df9f1a869ec8d289c` | `b7b52ab28f02c89bb99da85e5c514309e1b916b5fef6effd1cbf9bfa0b9dcdf2` |
| `net/minecraft/DetectedVersion.java` | `3a5aafad289a9d38aaa24e291a223a9b06b14a08275102bc42c15c6c531da302` | `ac72abf7576418f0ab42cf89640c5bf452d3c245b291a8fa6a0dcaa0346a9ecb` |

None is part of the reviewed movement/collision route. The complete source-manifest equality census, not only the selected-file sample, bounds the finding.

## Player movement route traced

The following Mojmap source member locations were checked in both releases; the compared files are byte-identical:

1. `net/minecraft/client/player/LocalPlayer.java`: `tick()` line 183, `aiStep()` line 652, and local-player `move(MoverType, Vec3)` line 888. This includes the client-player movement dispatch and its direct collision adjustment path.
2. `net/minecraft/world/entity/LivingEntity.java`: `travel(Vec3)` line 1912, including step-up eligibility calls to `isFree(...)` at lines 1954 and 1974; `aiStep()` line 2366.
3. `net/minecraft/world/entity/Entity.java`: `setPos(...)` line 321 and its AABB construction at 325; collision-aware `move(...)` line 469; world-border collision shape at 656; swept block collision collection at 706; supporting block lookup `getBlockStateOn()` line 1032; dimensions-based box rewrite `refreshDimensions()` line 2298; and `setBoundingBox(...)` line 2360.
4. `net/minecraft/world/level/CollisionGetter.java`: block collision shape lookup at line 28 and `getBlockCollisions(...)` overloads at lines 58 and 62. `net/minecraft/world/level/block/state/BlockBehaviour.java`: collision-shape dispatch at lines 275 and 523–528.
5. Shape/math and state dependencies: `AABB`, `VoxelShape`, `Shapes`, `CollisionContext`, `BlockGetter`, `Level`, `Block`, `BlockBehaviour`, `StateHolder`, `Blocks`, and `WorldBorder`.
6. Representative solid shape providers in the checked closure: `WoolCarpetBlock`, `FenceBlock`, `IronBarsBlock`, `StainedGlassPaneBlock`, `WallBlock`, `StairBlock`, `FenceGateBlock`, `SlabBlock`, `TrapDoorBlock`, and `LeavesBlock`. `GlassPaneBlock` and `AbstractFenceBlock` are not source names in this pair; the pane implementation is `StainedGlassPaneBlock`/`IronBarsBlock`, and `FenceBlock` is concrete.

For every file in the SHA table below, the listed digest was computed from the 1.16 file and independently found identical for the paired 1.16.1 file. The source manifest audit above also validates the hash for each side.

| Relative source file | Equal SHA-256 |
|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b` |
| `net/minecraft/world/entity/LivingEntity.java` | `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26` |
| `net/minecraft/world/entity/Entity.java` | `27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576` |
| `net/minecraft/world/entity/player/Player.java` | `a849960d47bf00a2cddfb6636c78c518532a11967ba08829f19c5ebced08681e` |
| `net/minecraft/world/phys/AABB.java` | `514558cf4827679d84a4debd4f400d7d65bd4b8982b7ae8124a0d83a916ba878` |
| `net/minecraft/world/phys/shapes/VoxelShape.java` | `6747647a5b94e69747340d432932b77ce5ad701c8f06e141e15b3dd68f8c3bde` |
| `net/minecraft/world/phys/shapes/Shapes.java` | `ec218dca68cad3525364967aadfbb8c0bead0cc44aa3ee2beba31e00e55ae2d4` |
| `net/minecraft/world/level/CollisionGetter.java` | `fc7f175b10c2c6683913a903a366bd775facecf3c58f04cc6e9cfc95ddceed98` |
| `net/minecraft/world/level/BlockGetter.java` | `0f81fc1750218ddefabb33a03120cf1ac3447640c1c97ff59850fd45fab38f44` |
| `net/minecraft/world/level/Level.java` | `5f6be38c6b3e11e1ca00a9067f676151d1288f19d8cd817a3bbd1cc91b8eaa19` |
| `net/minecraft/world/level/block/Block.java` | `ca9a413bf8198402ec9f2febb65c2e89eef4386cb0f1f3d36650f4047d896655` |
| `net/minecraft/world/level/block/state/BlockBehaviour.java` | `1b524b5c45e3be4397dd680b9bd12c937497b9088e623428cf6387406018ef7f` |
| `net/minecraft/world/level/block/state/StateHolder.java` | `cd79ed66f0bf3f55f4d02f7422728004c9ba362fa974765242846720fd0621ec` |
| `net/minecraft/world/level/block/Blocks.java` | `61f18f903f7d96731bbc3cac544a8614f55fddac872dc0b0fee5a53511c313a9` |
| `net/minecraft/world/phys/shapes/CollisionContext.java` | `8d9e84cf4fdc62cc1a4fc437f828ddccfcc0a2cc9bddd1fd1f621e178beff59a` |
| `net/minecraft/world/level/border/WorldBorder.java` | `2925df8d985d0ec2d075d4f5c57538dcc628528b72a1d35e90af27fe4fbadb2a` |
| `net/minecraft/world/level/block/WoolCarpetBlock.java` | `e9a79c0409ac60ec475d3b96a1a3927f2a216a4710fc79f927f98dc9aaf53702` |
| `net/minecraft/world/level/block/FenceBlock.java` | `1337151f78048fcef2356d128599dec6e688a01b10e21e8a409f767796f93588` |
| `net/minecraft/world/level/block/IronBarsBlock.java` | `1e1c1e27005e4367152b46df5219aeed177e634bb03abbc1b04fd4e329c9cb69` |
| `net/minecraft/world/level/block/StainedGlassPaneBlock.java` | `f15bb87d0771fe78e469a17531f8744d3b735f24bcf9e7107d90ce84cf1ca715` |
| `net/minecraft/world/level/block/WallBlock.java` | `ce153c55791f44eb92abc5f2febfbf023a3e03e63252d94201e3ab843d18b5d4` |
| `net/minecraft/world/level/block/StairBlock.java` | `5dde626303aae20a25ab1d5a0524506404f116adad89e18fdc809ea2ef03b93a` |
| `net/minecraft/world/level/block/FenceGateBlock.java` | `ee6695b52f3299b30365dc9b40cafddb4d540300ccac47e1b23c5ecea9e01e3e` |
| `net/minecraft/world/level/block/SlabBlock.java` | `e759307a9a58d01ea9d6793ba121eb7fca3ac529bcbf2aa2287dbf8ab44e0dbb` |
| `net/minecraft/world/level/block/TrapDoorBlock.java` | `28a10845712869a1130bb4b68e838f2b38ff661cdda8d5e7b2b13f32d7d780aa` |
| `net/minecraft/world/level/block/LeavesBlock.java` | `aacba120c2e97664df04f2c207d91f07ce92cbe511de3807d32588d02aee6459` |

## Input artifact and resource availability

The artifact sidecar digests are present and match the ready-marker fields. Their manifests each list 38 artifacts. I checked the listed paths against their provenance-recorded roots: 38/38 artifacts are unavailable for 1.16 and 38/38 for 1.16.1. The local `.minecraft-decompile-cache/1.16` and `.minecraft-decompile-cache/1.16.1` roots are absent as well. Therefore the recorded SHA pins for the raw client JAR, mapped client JAR, Mojmap mapping file, version data, and libraries cannot be recomputed from the archived bytes in this checkout.

The published version roots contain the source trees and sidecars, not separate data/tag/resource payloads. The client JAR is listed in each artifact manifest but is unavailable at its recorded path; inspection of any packaged resource bytes or tags is explicitly unavailable. The reviewed code route resolves collision boxes through vanilla block state, shape providers, and voxel-shape math; its relevant Java dependencies are source-identical. This report does not claim that unavailable packaged resources were independently checked.

## Final bounded conclusion

The checked source manifests and exact paired files support a no-difference verdict for the assigned solid player-box dependency. This excludes fluid behavior, packet semantics, and inaccessible packaged-resource inspection. No product, wiki, backlog, Git history, build, runtime, or decompilation evidence was used.



