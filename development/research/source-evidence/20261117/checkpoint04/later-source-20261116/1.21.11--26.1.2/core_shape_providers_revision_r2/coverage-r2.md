# 1.21.11 → 26.1.2 core shape-provider slice

## Scope and result

This source-only slice compares the registered `SnowLayerBlock`, `CarpetBlock`/`WoolCarpetBlock`, `ScaffoldingBlock`, `SlimeBlock`, and `HoneyBlock` shape providers and their directly relevant state selectors. It is **partial**. The examined shape formulas and collision dispatch are equivalent for the concrete source paths below. The snow-support tag identifiers changed, and their packaged membership was not examined; this report therefore does not claim complete snow survival/support equivalence or pair coverage. No player trajectory or runtime claim is made.

## Exact source identities

Java sources are in `decompiled_minecraft/1.21.11/mojmap/` and `decompiled_minecraft/26.1.2/unobfuscated/`. Sidecars are at each version root. `ready.versionId`/`ready.mapping` and provenance `versionId`/`namespace` agree; each ready marker's source/artifact manifest hashes match the sidecars on disk.

| Release | Mapping | Ready marker SHA-256 | Provenance SHA-256 | Source manifest SHA-256 | Artifact manifest SHA-256 |
| --- | --- | --- | --- | --- | --- |
| 1.21.11 | Mojmap | `0ad98d0ebd654650492c97eb58bc324d33864787f0ce10d99faec3ae2f5b804b` | `99c9fb741eb5e4ac8fb5780e2158da324d7c8b6afc179d494e5986f78fe29f72` | `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555` | `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c` |
| 26.1.2 | unobfuscated | `f24af0f4d38543c2bd9c19b50f347d4e89d6027050e4b21ae070734843946bb7` | `8d727f880cc91f4a297e148c5b75976303d051457334bc689268fbdf631a012a` | `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` | `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92` |

Every cited Java file SHA-256 below was checked against the corresponding source-manifest row and local file. Source SHA values are lower-case for readability.

## Paired shape methods and state selection

- **Layered snow:** Both `SnowLayerBlock` constructors default `LAYERS` to 1. `SHAPES` is populated for indices 0 through 8 with `Block.column(16, 0, height * 2)`. `getShape`, `getVisualShape`, and `getBlockSupportShape` select `SHAPES[LAYERS]`; `getCollisionShape` selects `SHAPES[LAYERS - 1]`. With `Block.boxes` using an inclusive 0..8 range and the paired `Block.column` helper using the same centered X/Z bounds, the outline/support heights are 2..16 units while collision heights are 0..14 units, for legal layer values 1..8. The index arithmetic, default state, placement increment capped at 8, and collision/support selectors match (1.21.11 `SnowLayerBlock.java:26–58, 114–121`; 26.1.2 `SnowLayerBlock.java:26–58, 124–131`; helpers `Block.java:166–186` and `168–188`).
- `SnowLayerBlock.canSurvive` keeps the same ordered decision shape: reject a below block in a negative tag; accept a below block in a positive override tag; otherwise require a full upward collision face or an 8-layer snow block below. The tag names changed from `SNOW_LAYER_CANNOT_SURVIVE_ON` / `SNOW_LAYER_CAN_SURVIVE_ON` to `CANNOT_SUPPORT_SNOW_LAYER` / `SUPPORT_OVERRIDE_SNOW_LAYER` (1.21.11 lines 77–84; 26.1.2 lines 77–86). The old and new tag keys have now been paired to exact, byte-identical entries in the pinned client archives, as documented in the supplemental resource evidence below; this closes only archive membership for these two gates.
- **Carpet:** `CarpetBlock` defines the same `Block.column(16, 0, 1)` shape, returns it from `getShape`, and survives exactly when the block below is non-empty (1.21.11 lines 17, 29–42; 26.1.2 lines 17, 29–51). `WoolCarpetBlock` extends `CarpetBlock` in both trees and adds no shape override. The paired `BlockBehaviour` default collision method returns `state.getShape(...)` when `hasCollision` is true; the selected carpet registrations do not call `noCollision`, and `hasCollision` defaults true. Therefore the scoped carpet collision path uses the same one-unit carpet outline/collision shape in both sources.
- **Scaffolding:** Stable, unstable, unstable-bottom, and below-block shape constants have the same box dimensions and union/rotation construction. Both providers choose unstable vs stable outline from `BOTTOM` unless the collision context holds the block item, in which case outline is full-block. Their collision override returns empty for placement context; returns stable scaffolding when the context is above a full block and not descending; returns the two-unit bottom shape only when `DISTANCE != 0`, `BOTTOM`, and the context is above the below-block probe; otherwise empty. Shape constants, selectors and condition order match (1.21.11 `ScaffoldingBlock.java:29–39, 61–63, 80–85, 128–139`; 26.1.2 lines 29–39, 61–63, 80–85, 135–147). Both `getDistance` bodies use the block below first (reuse scaffolding distance, else test its upward sturdy face), then the horizontal neighbor distances and `min(distance + 1)` in the same order (1.21.11 lines 152–169; 26.1.2 lines 161–178). The waterlogged placement predicate is written as `getType() == Fluids.WATER` in 1.21.11 and `FluidState.is(Fluids.WATER)` in 26.1.2. The latter is inherited `TypedInstance.is(rawType)`, which compares `typeHolder().value() == rawType`; `FluidState.typeHolder()` returns its type's built-in registry holder. This is the same fluid-identity test at the scoped selector (26.1.2 `FluidState.java:108–110`, `TypedInstance.java:8,22–23`, `Fluid.java:31,124–125`; compare 1.21.11 `FluidState.java:37–38,115–117`). The exact support-block set selected by `isFaceSturdy` is outside this provider slice.
- **Slime:** `SlimeBlock` declares no shape, collision, or support-shape override in either source and extends `HalfTransparentBlock`, which likewise has no such override. `BlockBehaviour` supplies the same full-block `getShape` and the same `hasCollision ? state.getShape : empty` collision dispatch. The `SLIME_BLOCK` registrations retain `SlimeBlock::new`, `.noOcclusion()`, and do not call `.noCollision()`; `noOcclusion` changes `canOcclude`, while `noCollision` is the operation that clears `hasCollision`. Thus this scoped inherited provider remains the default full-block collision shape. Slime bounce/contact callbacks are deliberately not evaluated here.
- **Honey:** Both `HoneyBlock` providers define the same `Block.column(14, 0, 15)` shape and return it from `getCollisionShape` without state/context branches (1.21.11 lines 30, 46–47; 26.1.2 lines 31, 47–48). `Block.column`'s paired helper produces the same centered 14-by-14 X/Z footprint and Y interval 0..15. Honey contact/fall callbacks and movement factors are outside this slice.

## Registration checks

In `Blocks.java`, the paired `SNOW` registration uses `SnowLayerBlock::new` and retains the same relevant collision/default property path, including the layer-8 view-blocking gate; the `SLIME_BLOCK` registration retains `SlimeBlock::new` and `.noOcclusion()` without `.noCollision()`; `WHITE_CARPET` constructs `WoolCarpetBlock`; `SCAFFOLDING` constructs `ScaffoldingBlock` with `.noCollision()` and `dynamicShape()` (its class override supplies the conditional collision geometry); and `HONEY_BLOCK` constructs `HoneyBlock`. The directly relevant registration forms and state/provider class identities match (1.21.11 `Blocks.java:2043–2069, 3207–3209, 3291–3295, 5427–5438, 5916–5920`; 26.1.2 `Blocks.java:2055–2081, 3221–3223, 3305–3309, 5439–5450, 5928–5932`). Properties unrelated to collision geometry, including honey movement factors and slime friction, are not compared here.

## Exact source-file SHA-256 values

| Relative source path | 1.21.11 Mojmap | 26.1.2 unobfuscated |
| --- | --- | --- |
| `net/minecraft/world/level/block/SnowLayerBlock.java` | `e62febb4017baa7935485e214ca114e1a6e7f3973a647a45d72eb427e247d5ab` | `312952c724788f30a2255bc34ef7d3242d4737067c23939f0bd0a76059b473cc` |
| `net/minecraft/world/level/block/CarpetBlock.java` | `2149a8fee8a57d693341826474c2a42a457fdfd9dd24d8e811d2f0622e6c14bc` | `ce669310bf7ab918e5bbdb07253b9030a516ae143cd4aa9e464bd7f2fdedffdc` |
| `net/minecraft/world/level/block/WoolCarpetBlock.java` | `16e0dd0cd4461c495990b612e0de709482299a5fa9bf4ce6067e40396a4e0d43` | `f99bd32dc81aac89584803bc9eb92ee229502cbb5719d726672195d879365b4e` |
| `net/minecraft/world/level/block/ScaffoldingBlock.java` | `9cf5e367ef3c237238ab8ff1f63cec907134af8328ad09f7ee2ce8956946d74b` | `a55b102f47b261f808ef31c0b74cda9efdbc6fa53ccc4bda8ce46230904b22fa` |
| `net/minecraft/world/level/block/SlimeBlock.java` | `86f4ced616a25bb8089f9ee2ff5a875f6e4f0ee0ab15b120863cd082cf35efd3` | `84d22cf526d6bf1b4ec4b0b642a76fc2c90380a71f05ab92a7de9935f0d1c38e` |
| `net/minecraft/world/level/block/HoneyBlock.java` | `d6f11b868160cba12b45b1818265ab870fcb96bbb6f28fdc13952013bdf2ab32` | `998aff733f7bb654fcd837680375ba740574d3371d39bd708dbdae352d4782ec` |
| `net/minecraft/world/level/block/HalfTransparentBlock.java` | `c8095554d514f2ad2ddbfdcf636ac8447ad6c5002272948434aa214ada450fde` | `2023e0ac7ca496c19037aaf34781a1dd5d1b6ceb52d7418b1483e5b0d3547712` |
| `net/minecraft/world/level/block/Blocks.java` | `cc6636f6ace603b32edd3db4c0cfcda60be08e2c6b5def783a698f105b8f7f23` | `ba8a258b33f73fe03f93e7b02f9c25d4f66cf3aaab04c4580d0863cc71bc866f` |
| `net/minecraft/world/level/block/Block.java` | `bd7f69ffe617fa1008c9311ad7c731b23f242acb5ff3bab8c1a81e41e7d15a73` | `1693cfb7b84190a2fe664470a56d59e78ed5bd7d722a2bd16888b036d4d8e977` |
| `net/minecraft/world/level/block/state/BlockBehaviour.java` | `cf7eff07efb53d95c8ffa633ff38c7d3f454e185f197de49eb4da279a8da9d17` | `9db85de84e502903e6fe497f043b58620b92089236ffb213f0b93db979428d13` |
| `net/minecraft/world/level/material/FluidState.java` | `146a5e6a19a93da631a6f4ed57020791bde31087fcedc6d52c68b1c582b71b0f` | `e35608d5775013c67653ea350309b5150a97f36aa65c27f1c6247ae2aa840f18` |
| `net/minecraft/world/level/material/Fluid.java` | `ebc977d8a089c2ee0da1222aa137457e26239e3f207b330be6f2b45d57bb2798` | `e7759a85dea543266b734086b2061332bc9f62ba3330be0f203f1f8baaa2c80d` |
| `net/minecraft/core/TypedInstance.java` | — | `caa4db2c73d7a18a59964ec2db7d3401a823da9f15a8e51aa266ba6f8a80e6df` |

## Supplemental accepted resource evidence: snow support tags

The independently audited input reference `build/source-resource-inputs-20261117/movement-gates/1.21.11--26.1.2_snow_support_tags/input-reference.json` has SHA-256 `18b9a5deb911956374f2d4086988e8daf471f7afea8d96a62bb79ec1c67baede`; its independent audit is `build/source-resource-inputs-20261117/movement-gates/1.21.11--26.1.2_snow_support_tags/audit/verdict.md`, SHA-256 `0ec3e96dccd16fd30966fb512a0f6f739785be50156caff8cde6601e70c725c7` (ACCEPT). It verifies these exact archive entries, sizes, payload hashes and parsed values:

| Release | Source constant / resource path | Bytes | SHA-256 | Values |
| --- | --- | ---: | --- | --- |
| 1.21.11 | `SNOW_LAYER_CANNOT_SURVIVE_ON`; `data/minecraft/tags/block/snow_layer_cannot_survive_on.json` | 94 | `282662c1139488dcc43e3bfe48a36a6c4a759e53cb6a4024454236b8f07384cb` | `minecraft:ice`, `minecraft:packed_ice`, `minecraft:barrier` |
| 26.1.2 | `CANNOT_SUPPORT_SNOW_LAYER`; `data/minecraft/tags/block/cannot_support_snow_layer.json` | 94 | `282662c1139488dcc43e3bfe48a36a6c4a759e53cb6a4024454236b8f07384cb` | `minecraft:ice`, `minecraft:packed_ice`, `minecraft:barrier` |
| 1.21.11 | `SNOW_LAYER_CAN_SURVIVE_ON`; `data/minecraft/tags/block/snow_layer_can_survive_on.json` | 97 | `c7491ae1bafae440421e90a1387ac87f46f7e003f37315f4acf5ea0d0ee8cd5a` | `minecraft:honey_block`, `minecraft:soul_sand`, `minecraft:mud` |
| 26.1.2 | `SUPPORT_OVERRIDE_SNOW_LAYER`; `data/minecraft/tags/block/support_override_snow_layer.json` | 97 | `c7491ae1bafae440421e90a1387ac87f46f7e003f37315f4acf5ea0d0ee8cd5a` | `minecraft:honey_block`, `minecraft:soul_sand`, `minecraft:mud` |

The input reference binds the archive keys through each endpoint's `BlockTags.java` default-namespace helper and confirms the `replace` field is absent in each of the four entries. The pairwise old/new payloads are byte-identical. The exact client JAR SHA-256 values match the repository's `artifacts.sha256` pins and their official version-metadata SHA-1 values were verified by the resource audit. The source provenance sidecars do not themselves record the client JAR digest or path, so this report makes no provenance-recorded-artifact claim.

This addition changes only the resource membership evidence and the associated unresolved-gate wording. All Java method, shape, registration, and source-identity observations above are retained unchanged from r1.
## Remaining gates

- Snow tag membership is compared for the exact pinned client archive entries listed below. Datapack overrides, reload state, world-effective membership, and player-world reachability of each tag member remain open.
- Scaffolding distance uses `isFaceSturdy` on the block below; the full set of provider states that satisfy that predicate and ordinary player-world generation/placement reachability are not checked.
- This slice does not cover shape families beyond the five assigned blocks, contact callbacks, friction/speed/jump-factor behavior, world-generation/data-pack overrides, intermediate stable versions, the remaining tick graph, whole-pair closure, or runtime parity.

No source generation, tests, builds, or game/runtime checks were performed.

