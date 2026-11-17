# Wall rail and button face-projection census

## Scope and source identity

This closes only the assigned `rail`, `powered_rail`, `detector_rail`, `activator_rail`, `stone_button`, and six wooden-button keys from `wall_remaining_face_shape_census/report.md` (SHA-256 `f1a12c7564f14c91c652bea6603e8ada8350b98c2892df05c591c0161d4e041d`). Exact endpoints are Minecraft 1.13.2 and 1.14.4, Ornithe Feather Gen2 build 2. Source-manifest file hashes and ready-marker manifest digests were checked:

- 1.13.2 `ornithe-feather.sources.sha256`: `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.
- 1.14.4 `ornithe-feather.sources.sha256`: `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`.

The listed relevant source-file hashes below match the corresponding entries in those manifests. Geometry-provider claims are reused from `rail_shape_provider/report.md` (the rail provider) and `button_shape_provider/coverage.md` (the button provider), which address those exact endpoints and same provider/shape claims; no geometry claim is inferred from unrelated provider evidence.

## Common registration joins and state domains

Each row is a common registry key mapped to the same concrete class family at both endpoints. Registrations and `noCollision()` evidence are in old `Block.java` and target `Blocks.java` at the line ranges below.

| Key(s) | 1.13.2 → 1.14.4 class | Common state-definition domain | Registration evidence |
|---|---|---|---|
| `rail` | `RailBlock` → `RailBlock` | `SHAPE`: all 10 `RailShape` enum values | Old `Block.java:1006`; target `Blocks.java:261` |
| `powered_rail`, `activator_rail` | `PoweredRailBlock` → `PoweredRailBlock` | `SHAPE`: six values admitted by `RAIL_SHAPE_STRAIGHT`; `POWERED`: false/true (12 tuples per key) | Old `Block.java:917,1306`; target `Blocks.java:264,964` |
| `detector_rail` | `DetectorRailBlock` → `DetectorRailBlock` | `SHAPE`: same six straight values; `POWERED`: false/true (12 tuples) | Old `Block.java:918`; target `Blocks.java:452` |
| `stone_button` | `StoneButtonBlock` → `StoneButtonBlock` | inherited `FACING` (4 horizontal values) × `FACE` (floor/wall/ceiling) × `POWERED` (false/true): 24 state-definition tuples | Old `Block.java:1066`; target `Blocks.java:555–556` |
| `oak_button`, `spruce_button`, `birch_button`, `jungle_button`, `acacia_button`, `dark_oak_button` | `WoodenButtonBlock` → `WoodenButtonBlock` | same 24 inherited `ButtonBlock` tuples per key | Old `Block.java:1019–1054`; target `Blocks.java:869–885` |

`RailShape` contains NORTH_SOUTH, EAST_WEST, four ascending values, and four corner values. `RAIL_SHAPE_STRAIGHT` removes the four corner values; `RAIL_SHAPE` includes all ten. Button state-property declarations are unchanged in meaning, and all seven registrations retain `noCollision()` on both sides. The domains here are the complete state-manager products; actual placement/support reachability is not needed for the face result.

## Old directional FaceShape projection

In 1.13.2, `AbstractRailBlock.getFaceShape` returns `FaceShape.UNDEFINED` unconditionally (`AbstractRailBlock.java:45–47`); this applies to the four rail keys through the listed inheritance. `ButtonBlock.getFaceShape` also returns `UNDEFINED` unconditionally (`ButtonBlock.java:186–188`); `StoneButtonBlock` and `WoodenButtonBlock` inherit it. Consequently each common key has old result **false** for the wall generic `shape == FaceShape.SOLID` term on NORTH, EAST, SOUTH, and WEST, for every state in its domain. The keys are not fence gates and do not enter the old wall's pole special case (`WallBlock.java:47–50`). None is in the old `isExceptionForConnection` list (`WallBlock.java:56–65`); that exclusion does not alter the false result.

## Target directional collision-face projection

In 1.14.4, the wall generic arm passes `BlockState`'s directional face predicate as `bl` into its neighbor rule (`WallBlock.java:48–51, 67–70`). `BlockState.m_87223014` uses the cached directional value or delegates to `Block.isFaceSolid` (`BlockState.java:326–328`); `Block.isFaceSolid` applies `isFaceFull` to the collision shape (`Block.java:402–408`). Each listed rail and button registration sets `hasCollision=false` with `noCollision()`; base `Block.getCollisionShape` therefore yields `VoxelShapes.empty()` before consulting the rail/button outline provider (`Block.java:376–378, 853–855`). Its projection onto every horizontal direction is not a full face, so `bl=false` for every state.

The other target wall arm is also false for these keys: none of the six listed rail IDs or seven button IDs appears in the exact target `walls.json` payload (SHA-256 `f20174f51f66d27681fd9d469a77c86cae82012c0d9125e7d246c426b772391c`; 14 values), and none is a `FenceGateBlock` (`WallBlock.java:50–51`). The target attachment-exception predicate does not list these families (`Block.java:260–267`), but this cannot turn a false face result into a connection. Thus each directional wall bit remains **false → false** (N/E/S/W) for all 214 common state-definition tuples: 46 rail tuples and 168 button tuples.

## Provider evidence reused and bounded verdict

The rail provider report verifies the exact common rail classes, the inherited `AbstractRailBlock` geometry selection (flat versus ascending), and the four registrations' `noCollision()` gates; the provider geometry does not feed wall projection because target `isFaceSolid` sees empty collision shapes. The button provider report verifies the seven common keys/classes, state schema and outline selection, and their `noCollision()` gates; these outline shapes likewise do not feed the target wall predicate. These reused provider claims are cross-checked against the registrations and predicate above.

**Verdict:** no wall-face projection difference for the four common rail keys or seven common button keys. Old `UNDEFINED` yields false; target empty collision shape yields false in all four horizontal directions for every enumerated state. This closes the assigned rail/button rows only. It does not close other residual keys, establish global registration-census completeness, or freeze the source pair. No product files, tests, builds, game launches, network access, source generation, or shared decompiled sources were used.

## Relevant source hashes

| Endpoint | Source file | SHA-256 |
|---|---|---|
| 1.13.2 | `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` |
| 1.13.2 | `net/minecraft/block/WallBlock.java` | `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf` |
| 1.13.2 | `net/minecraft/block/AbstractRailBlock.java` | `ef89230726ce9bd5ebb009d9332da90969d074762fc7eca8d477640d020a0a8d` |
| 1.13.2 | `net/minecraft/block/RailBlock.java` | `1853b7f0359e7b5d3dd21c86e2a70803e9066ae321c7f4fbe6cd6bb68a184537` |
| 1.13.2 | `net/minecraft/block/PoweredRailBlock.java` | `960872830b7ef162b567c8b7dc901ee0d2af231a9f67de352814f3759c344aa1` |
| 1.13.2 | `net/minecraft/block/DetectorRailBlock.java` | `39c3cc54bd8ccc7c4d99669884e8dea9c3248d911815064ee049eda0872cae1b` |
| 1.13.2 | `net/minecraft/block/ButtonBlock.java` | `0cdd88bf4f0ee6b2d935a59867c1ba6df68c098d97701e800f499f83dc7a9578` |
| 1.13.2 | `net/minecraft/block/StoneButtonBlock.java` | `e9cef7b949924f49e7968d2f83e845a4fe818e32e3d25f013a9c326c4de406a4` |
| 1.13.2 | `net/minecraft/block/WoodenButtonBlock.java` | `96fe7edbd74a167c35266bbd59eeb8cd5d797ebf49a8e1a2cbe49eb4d633dcb7` |
| 1.14.4 | `net/minecraft/block/Block.java` | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` |
| 1.14.4 | `net/minecraft/block/Blocks.java` | `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8` |
| 1.14.4 | `net/minecraft/block/WallBlock.java` | `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7` |
| 1.14.4 | `net/minecraft/block/AbstractRailBlock.java` | `9f889ccea3e80c29fe4c5ac97c6713b5e40878eeaf5ffadd71918572a4fff70a` |
| 1.14.4 | `net/minecraft/block/DetectorRailBlock.java` | `0a8c54c7d650f7283281e49f04872b15069893c04deaf8466b6be799e300a3ad` |
| 1.14.4 | `net/minecraft/block/ButtonBlock.java` | `9153636ceafd3c93882d8b9603a7813ff99695b955d17f45507e87860408ba63` |
| 1.14.4 | `net/minecraft/block/WoodenButtonBlock.java` | `96fe7edbd74a167c35266bbd59eeb8cd5d797ebf49a8e1a2cbe49eb4d633dcb7` |
| 1.14.4 | `net/minecraft/block/state/BlockState.java` | `6c6703c7c2f7203a7507d8a621bc83e1dad613b32089394f5af4fbd8636b165f` |
