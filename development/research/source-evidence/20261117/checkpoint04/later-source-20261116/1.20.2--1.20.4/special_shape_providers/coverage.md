# 1.20.2 → 1.20.4 special shape-provider coverage

## Scope and bounded result

This source-only slice covers the collision/outline/support shape paths, direct state selectors, and `Blocks` registrations for the cauldron family, lily pad (`WaterlilyBlock` in Mojmap), hopper, and End Portal Frame. The named cauldron path necessarily includes its shared `AbstractCauldronBlock` provider and old powder-snow subtype. No movement-semantic difference was found in these assigned shape operations. This is not a full provider inventory or pair freeze, and it makes no ordinary-world reachability or runtime-parity claim.

Entity-inside callbacks, cauldron contents/damage, boat interaction, hopper inventory behavior, portal activation/transfer, resource membership, and other block providers were not examined.

## Exact source identities

Both publications are the exact `mojmap` source trees. Their version-root sidecars declare the matching release IDs and `namespace=mojmap`; each ready marker is `status=ready`, and its `sourceManifestSha256` matches the corresponding `mojmap.sources.sha256` file.

| Release | Ready sidecar SHA-256 | Provenance sidecar SHA-256 | Source manifest SHA-256 | Artifact manifest SHA-256 | Source count |
| --- | --- | --- | --- | --- | ---: |
| 1.20.2 | `5ff3322f287c250ee914c7ca3840d053bffc3c2c5f35e91b59314d948292483f` | `1dad9f725f6cb25b6568700fa0294f83b66ea9633b0d62ed354d7bd8b45d4f05` | `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887` | `ee67da9733953b30ae4077c587184e59772b745d780abd27a7b023780b459f54` | 4,904 |
| 1.20.4 | `6225b97979420cc743c8b7156a0ce512ef27d0dbf48bfffbc40d3e1c8cb044d1` | `10914d9b7b3fc29de066ca24f7d905e4d4f0a451a6c81729a2d1981addfc1385` | `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1` | `ee3efc771d264c0bc49d5472abb9763a646f8d221841d0a61201ecd991cb3948` | 5,048 |

For each source file listed below, the local SHA-256 was checked against its entry in that endpoint's source manifest. No full-tree rehash or artifact-membership claim was made.

## Paired shape providers

### Cauldron family

`AbstractCauldronBlock` builds the same static `SHAPE` at both endpoints: `Shapes.block()` minus the same union of four interior/leg boxes using `BooleanOp.ONLY_FIRST` (1.20.2 `AbstractCauldronBlock.java:33–38`; 1.20.4 `:32–37`). Its `getShape` returns that shape without consulting block state (1.20.2 `:61–64`; 1.20.4 `:64–66`). `CauldronBlock`, `LayeredCauldronBlock`, and `LavaCauldronBlock` inherit this provider. In 1.20.2, `PowderSnowCauldronBlock` also extends `LayeredCauldronBlock`; in 1.20.4, the `POWDER_SNOW_CAULDRON` registration directly constructs `LayeredCauldronBlock`. The newer unified constructor stores a precipitation type rather than the old subclass's fill predicate, but none of these classes overrides the inherited shape provider.

`Blocks.CAULDRON` has the same constructor/property chain at both endpoints. Water, lava, and powder-snow cauldrons use `Properties.copy(CAULDRON)` in 1.20.2 and `Properties.ofLegacyCopy(CAULDRON)` in 1.20.4. The corresponding copy methods assign the same legacy fields, including `hasCollision` and `canOcclude`; the cauldron base registration starts from the default collision-enabled properties. These registrations therefore select the same inherited cauldron shape and collision path for the scoped operation. Registration anchors: 1.20.2 `Blocks.java:2780–2790`; 1.20.4 `:2779–2791`.

### Lily pad / WaterlilyBlock

`WaterlilyBlock.AABB` is the same box from `(1, 0, 1)` to `(15, 1.5, 15)` in sixteenth-block units, and `getShape` returns it unchanged (1.20.2 `WaterlilyBlock.java:17, 31–34`; 1.20.4 `:19, 39–41`). `BushBlock` has no collision-shape override at either endpoint. `LILY_PAD` uses the same `WaterlilyBlock` constructor and the same property chain, including `.noOcclusion()` (1.20.2 `Blocks.java:2721–2725`; 1.20.4 `:2722–2726`).

### Hopper

The `HopperBlock` static box constants and boolean-shape construction are the same at both endpoints: a top at y=10..16, funnel at x/z=4..12 and y=4..10, `BASE = CONVEX_BASE - Hopper.INSIDE`, then direction-specific spout boxes. `Hopper.INSIDE` is byte-identical across the endpoints. `getShape` selects `DOWN_SHAPE`, `NORTH_SHAPE`, `SOUTH_SHAPE`, `WEST_SHAPE`, or `EAST_SHAPE` from the `FACING` state and falls back to `BASE`; the default state remains `FACING=DOWN, ENABLED=true` (1.20.2 `HopperBlock.java:39–52, 54–57, 60–77`; 1.20.4 `:41–54, 61–64, 67–84`). The `HOPPER` registration is the same `HopperBlock` with the same listed properties, including `.noOcclusion()` (1.20.2 `Blocks.java:3152–3156`; 1.20.4 `:3146–3150`).

### End Portal Frame

`EndPortalFrameBlock` uses the same base box, eye box and their union. `getShape` selects `BASE_SHAPE` when `HAS_EYE=false` and `FULL_SHAPE` when true; defaults remain `FACING=NORTH, HAS_EYE=false`. The facing property does not alter this shape method (1.20.2 `EndPortalFrameBlock.java:27–45`; 1.20.4 `:29–52`). `Blocks.END_PORTAL_FRAME` retains the same provider and listed properties at both endpoints; neither registration calls `noCollission()` (1.20.2 `Blocks.java:2803–2814`; 1.20.4 `:2804–2815`).

### Collision and support dispatch

None of the assigned providers overrides `getCollisionShape` or `getBlockSupportShape`. `BlockBehaviour.getBlockSupportShape` delegates to the empty-context collision shape; its default collision method returns `state.getShape(...)` when `hasCollision` is true and otherwise returns empty (1.20.2 `BlockBehaviour.java:245–246, 290–292`; 1.20.4 `:289–290, 334–336`). `hasCollision` defaults true at both endpoints. `.noOcclusion()` only clears `canOcclude`; `.noCollission()` is the separate setter that clears `hasCollision` (1.20.2 `:993–1002`; 1.20.4 `:1060–1069`). The selected cauldron, lily-pad, and hopper registrations use `.noOcclusion()` without `.noCollission()`; End Portal Frame does not disable collision. The same selected provider shapes therefore supply collision and support through this default path.

Shape construction helpers were also checked for this scope: `Block.box` still divides all six coordinates by 16 before calling `Shapes.box` (1.20.2 `Block.java:139–141`; 1.20.4 `:146–148`), and `Shapes.java` is byte-identical between endpoints. These helper facts bind the dimensions/shape expressions above without extending the report to unrelated collision code.

## Scope limits

This comparison establishes equality only for the named provider paths and their directly used registrations/default collision dispatch. It does not establish availability or ordinary placement of any state, shape behavior for other blocks, portal or cauldron contact responses, data/resource membership, or whole-pair movement equivalence.

## Exact cited Java source SHA-256

| Relative source path | 1.20.2 | 1.20.4 |
| --- | --- | --- |
| `net/minecraft/world/level/block/AbstractCauldronBlock.java` | `abb98a9fbee4e0198afbb1a5966f3e36fb4062e067aa318c6d33226ae8707282` | `9571ff0ec7f94c7891bf98d5fc6a4fc949261201e43f2f2cf42d4813a921436d` |
| `net/minecraft/world/level/block/CauldronBlock.java` | `c1d62d8d48e09142a112d1949e563df554e2caef942c363232bf5575978cc28f` | `4b980a1eb151609f4965ac89c5b0e50dd57f31ef8a86e40f4e8ee48a6bb0b27f` |
| `net/minecraft/world/level/block/LayeredCauldronBlock.java` | `f6d511c4ab7640e536c53bddceeb76bcc6a9c4ac8498d989534ce6d744544117` | `e0daf77e52269d616a3c2d57bc8da05b324531fe9eba8f731a0c328434be20fe` |
| `net/minecraft/world/level/block/LavaCauldronBlock.java` | `0cd612086fa0560395ae6907fcee70f171475b6ad1d326a259081b4d1002faa2` | `ced2a94183fbfae7ef2fcd7bf47f3b9e489eb16c4371ae8b9e3811751a2ddc41` |
| `net/minecraft/world/level/block/PowderSnowCauldronBlock.java` | `c92ad9bf23cf34c6fee5aadb541e3abd6a2371e6da221c9bf2150c060a6917ba` | absent; `Blocks.POWDER_SNOW_CAULDRON` constructs `LayeredCauldronBlock` |
| `net/minecraft/world/level/block/WaterlilyBlock.java` | `a26c348a5cd671823c818a5349e695d622c1f1579428c23c46c32542301b4266` | `0f5e6639f50f5ef79548f7df200830717eb6291f840ffcfe9f6384f022da85cb` |
| `net/minecraft/world/level/block/BushBlock.java` | `7a77eeb4d23d06ba917cfdff66278aca067ec9242a5f1b18def4b3f3c0094e61` | `a07ae6bb4ceeceada4d15a0a3dab1674fb2cdbcbe5fb8abb27d3ab05697032cb` |
| `net/minecraft/world/level/block/HopperBlock.java` | `29edf180ce4050cc693d321bfbb51e0193b7a62c51b3c90e3675eab19c739aa9` | `b082e9261faa708dc02561f00443b5de5d0435da429883968c2a27a6b1b865ed` |
| `net/minecraft/world/level/block/BaseEntityBlock.java` | `a2cc5c41e53c1b12742b7994365153e1f58900a9aa19424c614497df71c68e50` | `5f5e0acbd4cb6e00815858b8d3596cbc105386a14e8940b853469e5fd55fdf43` |
| `net/minecraft/world/level/block/entity/Hopper.java` | `d641ed4bfd257cbc8598e0b629529504d5c6399ca165c8157eab0c9d9bf96915` | `d641ed4bfd257cbc8598e0b629529504d5c6399ca165c8157eab0c9d9bf96915` |
| `net/minecraft/world/level/block/EndPortalFrameBlock.java` | `158881f4f36d58019f5d12c32f919a92d895a6b6ec7bcba49666820117e96be2` | `17dab1a2dd073e3f53b8655c55984101b046e42ecdbe44f4f5aad90125726ed9` |
| `net/minecraft/world/level/block/Blocks.java` | `75e8e77adab4a07b02ef95619be83a004f5f79810cf3f07157a713d714881253` | `5392c4349317e55c2989865be38c17b94e6e0543e7017e6a2a64d79217e83643` |
| `net/minecraft/world/level/block/state/BlockBehaviour.java` | `a6d88726ce4642fc0a61f46a01347bb0b74887b61ef55af7e7c76723789a0b57` | `6284a7d08a6d0d475cd4fe8aea62b35153ae8fbc0bce5f51788389f679233b54` |
| `net/minecraft/world/level/block/Block.java` | `0c2df3d958078929c4cc2985e0d6a6a766cbbbb02066e6c9ac471981aa858656` | `9a777d44c9c6b55677132fb5a56d46c9aa322e42faf956749ef80443817416c4` |
| `net/minecraft/world/phys/shapes/Shapes.java` | `ec207e73b8e9de4b1234132f81126973f0cb2bb37646b070d273259f2e91311a` | `ec207e73b8e9de4b1234132f81126973f0cb2bb37646b070d273259f2e91311a` |
