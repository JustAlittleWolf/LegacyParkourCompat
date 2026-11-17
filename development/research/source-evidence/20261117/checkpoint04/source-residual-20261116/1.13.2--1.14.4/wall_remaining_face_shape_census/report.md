# Remaining wall face-shape census

## Scope and evidence identity

Source-only residual census for the open common-registration crosswalk in `wall_generic_neighbor_inventory/report.md` (SHA-256 `1dc175827e5b8c7cbaecb20fc13dc2f55a8cf3450be8c76106ff855d56efcdbc`). Exact endpoints are Minecraft 1.13.2 and 1.14.4, Ornithe Feather Gen2 build 2. Read-only endpoint manifests and ready markers agree:

- 1.13.2 `decompiled_minecraft/1.13.2/ornithe-feather.sources.sha256`: SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.
- 1.14.4 `decompiled_minecraft/1.14.4/ornithe-feather.sources.sha256`: SHA-256 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`.

A complete old `net/minecraft/block` scan finds **65 source files with direct `getFaceShape` declarations**, including `Block`'s default. This is a source declaration census, not a registered-key census: it includes the base method, abstract classes, classes with no common registration, subclasses inheriting an override, and state-/world-dependent methods. Existing bounded packets close only their stated rows. The complete registration + inheritance + state-domain + four-horizontal-face crosswalk remains open; this report explicitly preserves that limitation and does not assert pair freeze.

## Newly established common-key wall-bit change

| Key | Common registration / state domain | 1.13.2 | 1.14.4 | Result |
|---|---|---|---|---|
| `minecraft:beacon` | Both endpoints register `BeaconBlock` (`Block.java:1239`; `Blocks.java:795`). It declares no state properties: one registered state. | Old `WallBlock.shouldConnectTo` accepts a generic neighbor only if it is outside `isExceptionForConnection` and has `FaceShape.SOLID`. Old `Block.isExceptionForPlacement` expressly rejects `Blocks.BEACON` (`Block.java:241–252`), so all four horizontal wall bits are false despite inherited `Block.getFaceShape == SOLID`. The pole arm does not apply. | Target `WallBlock` generic arm requires `!isExceptionForAttachment(block) && faceSolid`. Target `Block.isExceptionForAttachment` (`Block.java:260–267`) does not list Beacon. `BeaconBlock` inherits the collision-enabled default and full cube collision shape (`Block.java:370–378`); its registration does not call `noCollision`. It is absent from the reused target `LEAVES` payload, so `Block.isFaceSolid` passes for north/east/south/west. | **false → true** for all four horizontal directions, for the sole Beacon state. |

Wall placement and horizontal neighbor updates consume this predicate and store the directional bits (old `WallBlock.java:69–116`; target `:55–104`); the bits select the wall collision shape. This is a source-derived state/shape difference, not a measured movement trajectory.

SHA-256 values below were recomputed from source bytes and agree with endpoint manifest entries:

| Source file | SHA-256 |
|---|---|
| 1.13.2 `net/minecraft/block/BeaconBlock.java` | `c8489ab810e21c2efd95e24acf8eb40e120e8d4f78248b886c65a87d901efdaf` |
| 1.14.4 `net/minecraft/block/BeaconBlock.java` | `7416e10a733c49b608758f5937ab6690cb12e5cd6ba385106c44f28fadef7d36` |
| 1.13.2 `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` |
| 1.14.4 `net/minecraft/block/Block.java` | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` |
| 1.13.2 `net/minecraft/block/WallBlock.java` | `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf` |
| 1.14.4 `net/minecraft/block/WallBlock.java` | `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7` |
| 1.14.4 `net/minecraft/block/Blocks.java` | `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8` |

## Reused accepted bounded rows

These are reused only for their stated inputs and findings:

- TNT and stained-glass blocks: `wall_generic_neighbor_inventory/report.md` plus `wall_generic_neighbor_inventory_audit/verdict.md`; pane producer behavior: `pane_neighbor_producer/report.md` plus its audit.
- Frosted ice: `wall_frosted_ice/report.md` and accepted audit.
- Pistons, sticky pistons and piston heads: `wall_piston_faces/report.md` and accepted audit.
- Trapdoors and registered shulker boxes: `wall_trapdoor_shulker_faces/report.md` and accepted audit.
- Glass, glowstone, ice, sea lantern, conduit, cauldron and the common old-wall overlap with `WALLS`: `wall_full_face_candidates/report.md` and accepted audit.
- Fence gates: `wall_neighbor_bits/coverage.md` and accepted audit.
- Exact target `LEAVES`/`WALLS` payloads: `wall_resource_membership/extraction-record.md`; no extraction was performed here.

## Remaining per-key unknowns

The old source scan is complete for direct declarations, but the following candidate common keys still lack a complete join of old effective `FaceShape`, target effective collision shape, state domain/inheritance, collision gates and all four horizontal directions. These are **unknown**, not no-difference claims. The list is not certified globally exhaustive because remaining registration aliases and inherited concrete subclasses have not all been crosswalked.

| Key(s) identified | Old face behavior | Target wall-bit dependency still open |
|---|---|---|
| `minecraft:moving_piston` | `MovingBlock.getFaceShape` is `UNDEFINED`; old collision shape delegates to moving-piston entity. | Target dynamic moving-entity geometry by facing, head/base, type and progress, projected through target full-face predicate in four directions. Existing provider evidence does not close this wall projection. |
| `minecraft:rail`, `powered_rail`, `detector_rail`, `activator_rail` | `AbstractRailBlock` / concrete rail hierarchy returns `UNDEFINED`. | Exact key/class join and each rail state/facing shape against target full-face test. |
| `minecraft:stone_button` and six wood-button keys | Button classes return `UNDEFINED`. | Inherited class mapping and facing, attachment-face and powered-state collision projections. |
| Common sign, banner and floor/wall-head keys (`sign`, `wall_sign`, color-independent banners and skull/head variants) | Old concrete subclasses inherit `UNDEFINED` face results. | Each common key's target inheritance, orientation/state and any block-entity/context dependency against four horizontal faces. |
| Common utility/plant/portal classes with old horizontal `UNDEFINED`: `end_portal_frame`, `end_gateway`, `end_portal`, `end_rod`, `flower_pot` and potted keys, `brewing_stand`, `bubble_column`, `kelp`, `kelp_plant`, `sugar_cane`, `cocoa`, `chorus_plant`, `chorus_flower`, `dragon_egg`, `tripwire`, `tripwire_hook`, `vine`, `torch`, `lever`, `ladder`, `redstone_wire`, `fire`, `water`, `lava`, `structure_void`, `cobweb`, `cake`, bed color keys, door keys, carpet color keys, `farmland`, `grass_path`, `daylight_detector`, `enchanting_table`, `ender_chest`, `chest`, `hopper`, anvil variants, `cactus`, `snow` | Old classes provide `UNDEFINED` horizontally, except reused rows above. | Target collision shape/state/resource crosswalk for all registered keys. Individual provider reports close geometry only where stated and do not alone establish the wall predicate. |
| Common slabs: `stone_slab`, `brick_slab`, `quartz_slab`, `purpur_slab` | Old `SlabBlock` returns horizontal `SOLID` only for `HALF=DOUBLE`, otherwise `UNDEFINED`. | Compare all three slab types × both `WATERLOGGED` values × four directions to target collision-face result. Existing matching-state geometry report does not perform this wall-specific join. |
| Common stairs: `oak_stairs`, `spruce_stairs`, `birch_stairs`, `jungle_stairs`, `brick_stairs`, `sandstone_stairs`, `quartz_stairs`, `acacia_stairs`, `prismarine_stairs`, `purpur_stairs` | Old `StairsBlock.getFaceShape` varies by `FACING × HALF × SHAPE`; horizontal `SOLID` occurs on selected straight/inner states and outer states are `UNDEFINED`. | Compare all 40 facing/half/shape combinations × four directions with target collision-face predicate. Accepted stair evidence establishes matching-state collision geometry and neighbor-state selection, not this FaceShape-to-full-face join. |

The above is **at least 47 specifically named residual key entries/families** (multi-key families are not fully expanded above); that count is a lower bound and not the complete common-key denominator. Excluded/reused keys are not added to it. Additional aliases and inherited classes may add residual keys; registration + state-space census completion is still required before declaring any global count.

## Closure status

Found one additional concrete delta: `minecraft:beacon` false → true on each horizontal wall bit. The old source's 65 direct `getFaceShape` declaration files were enumerated; accepted bounded packets were reused as listed. **The common registration/state-space census is incomplete**: residual unknowns above and un-crosswalked aliases remain. This report does not close the overall wall crosswalk, does not claim no-difference for unknowns, and does not freeze the pair. No wiki, product implementation, backlog, Git history, tests, build, game, network access, source generation or shared-source writes were used.
