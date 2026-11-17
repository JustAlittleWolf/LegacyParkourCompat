# 1.13.2–1.14.4 glass-pane collision provider crosswalk

Status: bounded source-only close of the glass-pane registration/class/effective collision-provider mapping. Exact endpoints only. This is not pair freeze, full block-provider coverage, a state-producer parity audit, or an intermediate patch boundary.

## Source identities

- 1.13.2 Ornithe Feather Gen2: source root `decompiled_minecraft/1.13.2/ornithe-feather`; ready source-manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.
- 1.14.4 Ornithe Feather Gen2: source root `decompiled_minecraft/1.14.4/ornithe-feather`; ready source-manifest SHA-256 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc`.
- Each ready marker's manifest digest was recomputed from the corresponding `ornithe-feather.sources.sha256`; both match. Every Java file cited below was SHA-256 checked against that endpoint source manifest.

## Registered key → runtime class → effective player collision provider

The old registrations are made by `Block` static registration (`block/Block.java:1183–1184, 1324–1377`); 1.14.4 registers the corresponding keys in `Blocks` (`block/Blocks.java:703–708, 1015–1064`).

| Registry key | 1.13.2 registered class | 1.14.4 registered class | Effective `getCollisionShape` implementation at both endpoints |
|---|---|---|---|
| `iron_bars` | `IronBarsBlock` | `IronBarsBlock` | `PaneBlock.getCollisionShape` |
| `glass_pane` | `GlassPaneBlock` | `IronBarsBlock` | `PaneBlock.getCollisionShape` |
| `white_stained_glass_pane` | `StainedGlassPaneBlock` | `StainedGlassPaneBlock` | `PaneBlock.getCollisionShape` |
| `orange_stained_glass_pane` | `StainedGlassPaneBlock` | `StainedGlassPaneBlock` | `PaneBlock.getCollisionShape` |
| `magenta_stained_glass_pane` | `StainedGlassPaneBlock` | `StainedGlassPaneBlock` | `PaneBlock.getCollisionShape` |
| `light_blue_stained_glass_pane` | `StainedGlassPaneBlock` | `StainedGlassPaneBlock` | `PaneBlock.getCollisionShape` |
| `yellow_stained_glass_pane` | `StainedGlassPaneBlock` | `StainedGlassPaneBlock` | `PaneBlock.getCollisionShape` |
| `lime_stained_glass_pane` | `StainedGlassPaneBlock` | `StainedGlassPaneBlock` | `PaneBlock.getCollisionShape` |
| `pink_stained_glass_pane` | `StainedGlassPaneBlock` | `StainedGlassPaneBlock` | `PaneBlock.getCollisionShape` |
| `gray_stained_glass_pane` | `StainedGlassPaneBlock` | `StainedGlassPaneBlock` | `PaneBlock.getCollisionShape` |
| `light_gray_stained_glass_pane` | `StainedGlassPaneBlock` | `StainedGlassPaneBlock` | `PaneBlock.getCollisionShape` |
| `cyan_stained_glass_pane` | `StainedGlassPaneBlock` | `StainedGlassPaneBlock` | `PaneBlock.getCollisionShape` |
| `purple_stained_glass_pane` | `StainedGlassPaneBlock` | `StainedGlassPaneBlock` | `PaneBlock.getCollisionShape` |
| `blue_stained_glass_pane` | `StainedGlassPaneBlock` | `StainedGlassPaneBlock` | `PaneBlock.getCollisionShape` |
| `brown_stained_glass_pane` | `StainedGlassPaneBlock` | `StainedGlassPaneBlock` | `PaneBlock.getCollisionShape` |
| `green_stained_glass_pane` | `StainedGlassPaneBlock` | `StainedGlassPaneBlock` | `PaneBlock.getCollisionShape` |
| `red_stained_glass_pane` | `StainedGlassPaneBlock` | `StainedGlassPaneBlock` | `PaneBlock.getCollisionShape` |
| `black_stained_glass_pane` | `StainedGlassPaneBlock` | `StainedGlassPaneBlock` | `PaneBlock.getCollisionShape` |

The color-key registration rows are grouped by cited source ranges rather than repeated individually: the old block has one `new StainedGlassPaneBlock(...)` registration for each listed key at the cited lines; the target does likewise. `light_gray_stained_glass_pane` uses `DyeColor.SILVER` at both endpoints. `GlassPaneBlock.java` exists only at 1.13.2.

## Effect of the class remap

The mapping change itself does **not** change the effective pane collision provider or query-time state inputs:

- Old `GlassPaneBlock` only forwards its constructor to `IronBarsBlock` and overrides item-drop selection; it does not override collision shape, state definition, placement, or neighbor updates (`1.13.2 block/GlassPaneBlock.java:8–18`). Old `StainedGlassPaneBlock` extends it and resets the four directional defaults plus `WATERLOGGED` (`1.13.2 block/StainedGlassPaneBlock.java:8–18`).
- At 1.14.4, uncolored glass panes directly instantiate `IronBarsBlock`; stained panes extend `IronBarsBlock` and set the same four directional defaults plus `WATERLOGGED` (`1.14.4 block/Blocks.java:706–708, block/StainedGlassPaneBlock.java:7–15`).
- `IronBarsBlock` at both endpoints invokes `PaneBlock` with the same pane dimensions and resets the same five defaults (`IronBarsBlock.java:15–19`). Both endpoint `PaneBlock` collision methods select the state-indexed shape array by the four directional connection properties; the 1.14.4 context argument is unused. This exact provider/context claim is reused from the accepted `context_shapes/report.md` (SHA-256 `52c002332843b77063805c85d414e9522de937fd5e350e7a46b319ec3460d140`), independently audited by `context_shapes_audit/verdict-r2.md` (SHA-256 `f960b1e22a37348de607dbc1e4639e7f3b63c6fbfd92875e23d8100a24e61b06`). The inherited provider is `PaneBlock` for every row in the crosswalk.
- Thus removal of `GlassPaneBlock` and the stained-pane superclass remap do not by themselves add a collision branch, change pane geometry, or add query-time world/neighbor/context reads. `WATERLOGGED` is a declared state bit but is not among the four directional bits used to select the collision shape.

There is an adjacent, separately bounded state-producer difference across these endpoints: `IronBarsBlock.getPlacementState` / horizontal `updateShape` use neighbor `BlockState.getFaceShape(...)` and `FaceShape` categories in 1.13.2 (`IronBarsBlock.java:22–53, 77–80`), while 1.14.4 uses the boolean face predicate `BlockState.m_87223014(...)` and `shouldConnectTo` rule `!isExceptionForAttachment(block) && faceSolid || block instanceof IronBarsBlock` (`IronBarsBlock.java:22–53, 72–75`). In 1.14.4 that predicate is backed by `Block.isFaceSolid`: not a leaves-tagged state and a full collision-shape face (`BlockState.java:326–328; Block.java:402–409`). This establishes that the *neighbor classifier producing stored directional bits* differs at the endpoint boundary, independently of deleting `GlassPaneBlock`. It does not establish which concrete neighbor arrangements yield different bits; full neighbor-provider truth tables and paired state-producer parity remain open. Once the bits exist, the collision query reads those stored bits and does not inspect neighbors directly.

## Reuse boundary and remaining gates

Reused evidence is limited to the already accepted Pane provider/context claim named above. This packet adds the missing registered key → class crosswalk, confirms the effective provider under the class remap, and records the distinct neighbor-state producer dependency. It does not repeat the contextual caller chain or claim complete glass-pane connection-state parity.

Remaining gates include a paired audit of neighbor face classification and state updates for relevant neighbor blocks/states, and the separate whole-pair inventory/independent coverage audit. The two endpoints do not establish the precise release in which the observed state-producer changes entered.

## Cited Java source SHA-256 ledger

Hashes are complete-file SHA-256 values checked against each endpoint's source manifest.

| Endpoint | Source path relative to source root | SHA-256 |
|---|---|---|
| 1.13.2 | `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` |
| 1.13.2 | `net/minecraft/block/PaneBlock.java` | `9422e3f2aa9843ecd041f5ced13d05a4c7472d697f7dfd838450e49969e5326c` |
| 1.13.2 | `net/minecraft/block/IronBarsBlock.java` | `df6d956bb2745241e59294056f31513151b976dbd9ceed3f5478fd72b66c68a0` |
| 1.13.2 | `net/minecraft/block/GlassPaneBlock.java` | `7abf507b21b920c76e63e34293e6f40bbdefc95df396eb4847406a6d41df96a0` |
| 1.13.2 | `net/minecraft/block/StainedGlassPaneBlock.java` | `494d69648753478aa813a5b20a62b4a8f75b33b38c1d0f70061b9cafafda257d` |
| 1.14.4 | `net/minecraft/block/Blocks.java` | `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8` |
| 1.14.4 | `net/minecraft/block/PaneBlock.java` | `7150e89268a84113453cd5416ffe25e1ca7d42a39f264f7ae989878a10ee2158` |
| 1.14.4 | `net/minecraft/block/IronBarsBlock.java` | `10ccd6a2135cb05e74cdd4c440da29992dbc60655beedcb5f648f73bbc150541` |
| 1.14.4 | `net/minecraft/block/StainedGlassPaneBlock.java` | `d955bb542a9e9f148ac9c6c299b466a3358fca77c9a3f28330edd614891974c7` |
| 1.14.4 | `net/minecraft/block/Block.java` | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` |
| 1.14.4 | `net/minecraft/block/state/BlockState.java` | `6c6703c7c2f7203a7507d8a621bc83e1dad613b32089394f5af4fbd8636b165f` |
