# Turtle egg wall-neighbor predicate: 1.13.2 → 1.14.4

## Scope and exact source identities

This packet covers only the common registered key `minecraft:turtle_egg` and the horizontal neighbor-connection bit queried by `WallBlock`. It does not repeat the separate turtle-egg shape-provider review, claim a player trajectory, or freeze the pair.

Both endpoints are Ornithe Feather Gen2 build 2. The 1.13.2 ready marker binds source-manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; 1.14.4 binds `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`. In each case the marker digest equals the on-disk root `ornithe-feather.sources.sha256` hash. Each Java hash listed below was checked against both canonical bytes and that sidecar.

| Source | 1.13.2 SHA-256 | 1.14.4 SHA-256 |
|---|---|---|
| `net/minecraft/block/TurtleEggBlock.java` | `555e7263053f773dfce7d13c94c6dc1d043e26eea929a5b7cafddc6aa3189cf8` | `164fae7dc599eaf0ca63217178a755d15006e2ed6def947a28cd495868f3cb66` |
| `net/minecraft/block/WallBlock.java` | `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf` | `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7` |
| `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` |
| `net/minecraft/block/Blocks.java` | `3e128c9a4f6b53e1037fb85269c4d00bc85112ae52fe48f9534232e13ee2a303` | `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8` |
| `net/minecraft/block/state/BlockState.java` | `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70` | `6c6703c7c2f7203a7507d8a621bc83e1dad613b32089394f5af4fbd8636b165f` |
| `net/minecraft/state/property/Properties.java` | `3882c1b832ac8b2b16f5a83978da441ab03cdfe66f053a873342758045827f4c` | `9b524af5065100919c60667778e38986380c9df954ef70a9d0ac0a739132ddca` |

The exact row is present in `unexamined-wall-projection-keys.tsv` (SHA-256 `8a79ad3e851470a59976b9120d085ab2ec556d3cd61885205113dbd167712366`) as old and target `TurtleEggBlock`; its state counts were unresolved there and are established below. The broader r2 crosswalk retains the previously recorded limitation that its r1 TSV was overwritten, so the unchanged rows could not all be byte-diffed against r1. This note's claim is independently derived from the pinned endpoint sources and the exact key row; it does not upgrade that crosswalk limitation or pair coverage.

## Registered states and old predicate

Both `TurtleEggBlock.buildStateDefinition` methods add `HATCH` and `EGGS` (1.13.2 lines 163–165; 1.14.4 lines 143–145). The endpoint `Properties` declarations define `HATCH` as 0–2 and `EGGS` as 1–4, so the registered Cartesian domain is 12 states. Both registrations create one `TurtleEggBlock` under the key `turtle_egg` (old `Block.java` line 1756; target `Blocks.java` lines 1653–1655).

In 1.13.2, `TurtleEggBlock.getFaceShape` explicitly returns `FaceShape.UNDEFINED` for every face (lines 157–160). `WallBlock.shouldConnectTo` accepts a solid face when it is not a connection exception, or its separate thick-middle-pole / fence-gate branch (lines 51–61); the wall query passes the neighbor's opposite horizontal face to that method (lines 81–84 and 104–115). Turtle egg's explicit undefined face therefore yields no connection in every horizontal direction; no pole branch can apply because this registered class extends `Block`, not `FenceGateBlock`.

## Target predicate and result

In 1.14.4, `WallBlock` passes `BlockState.m_87223014(world, pos, direction)` into its connection predicate (lines 67–70). `BlockState.m_87223014` delegates to `Block.isFaceSolid` (BlockState lines 326–327); `Block.isFaceSolid` uses the state's collision shape and requires a full face (Block lines 402–412). `TurtleEggBlock.getShape` selects its one-egg shape for `EGGS=1` and its multiple-egg shape for `EGGS=2..4`, independent of `HATCH` and collision context (TurtleEggBlock lines 28–31, 138–145). `Block.block` divides the coordinates by 16 (Block lines 154–155), giving bounds `[3/16, 0, 3/16]–[12/16, 7/16, 12/16]` or `[1/16, 0, 1/16]–[15/16, 7/16, 15/16]`. Both footprints are inset from the unit-cell x/z faces, so their north/east/south/west collision face slices are empty and cannot be full.

The target registration does not call `.noCollision()`; `Block.Properties.hasCollision` defaults true (Block line 811). The target wall's alternate arms are also closed for this key: it is not in the exact 1.14.4 `WALLS` tag payload and it is not a `FenceGateBlock`. The pinned resource audit confirms `data/minecraft/tags/blocks/walls.json` payload SHA-256 `f20174f51f66d27681fd9d469a77c86cae82012c0d9125e7d246c426b772391c`, with values consisting of the fourteen wall block keys; the accepted resource verdict is `wall_resource_membership_audit/verdict-r2.md` SHA-256 `6402c6af692b3f198c853d3558b91d4de7c215db793e8847e66e45f08f4d8898`. Thus the target collision-face branch is false and neither tag nor gate arm adds a connection.

| Endpoint | HATCH × EGGS | Horizontal connection result |
|---|---:|---|
| 1.13.2 | 3 × 4 = 12 | `false` for all four horizontal directions and every state |
| 1.14.4 | 3 × 4 = 12 | `false` for all four horizontal directions and every state |

**Bounded result:** no difference in the turtle-egg wall-neighbor bit for this exact pair. No movement consequence is asserted. Other unexamined wall keys, state/provider dependencies and the independent full-pair audit remain open.
