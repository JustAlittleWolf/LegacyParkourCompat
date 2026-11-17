# Sea-pickle wall-neighbor predicate: 1.13.2 → 1.14.4

## Scope and source identities

This packet closes only the `minecraft:sea_pickle` row in the unexamined wall-projection crosswalk. It compares the old and target horizontal wall-neighbor predicate over the registered state domain. It does not analyze plant support/survival, fluid behavior, growth, collision effects on player movement, or the remaining pair inventory.

Both endpoints are Ornithe Feather Gen2 build 2, checked against their exact ready markers and `ornithe-feather.sources.sha256` manifests:

- 1.13.2 source manifest SHA-256: `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.
- 1.14.4 source manifest SHA-256: `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`.

The source-only crosswalk input is `build/source-residual-20261116/1.13.2--1.14.4/wall_full_registry_owner_crosswalk/unexamined-wall-projection-keys.tsv`, SHA-256 `8a79ad3e851470a59976b9120d085ab2ec556d3cd61885205113dbd167712366`. Its exact row names `minecraft:sea_pickle`, old `SeaPickleBlock` with `PlantBlock.getFaceShape`, and target `SeaPickleBlock` through `Block.getCollisionShape` → `SeaPickleBlock.getShape`.

## Registered states

In both endpoints, `SeaPickleBlock.buildStateDefinition` adds `PICKLES` and `WATERLOGGED` (1.13.2 lines 137–138; 1.14.4 lines 108–109). `Properties.PICKLES` is the inclusive integer range 1–4; `Properties.WATERLOGGED` is boolean. The registered Cartesian state domain is therefore 4 × 2 = 8 states in each release. The source default is pickles=1 and waterlogged=true. Both endpoint `SeaPickleBlock.getShape` methods select one of four shapes by `PICKLES` only; `WATERLOGGED` does not affect the selected shape.

The shape bounds, in sixteenths of a block, are unchanged:

| PICKLES | X and Z bounds | Y bounds |
| --- | --- | --- |
| 1 | 6–10 | 0–6 |
| 2 | 3–13 | 0–6 |
| 3 | 2–14 | 0–6 |
| 4 | 2–14 | 0–7 |

The 1.13.2 block registration at `Block.java:1869` and target registration at `Blocks.java:1828–1830` both construct the block without `.noCollision()`. Target `Block.Properties.hasCollision` defaults to true (`Block.java:811`) and the target `Block.getCollisionShape` uses `state.getShape` when this flag is true (`Block.java:376–377`). The target state-specific shape is consequently the collision shape used for face solidity. The horizontal face slices never fill a unit face: every shape is narrower than 16/16 across X/Z and shorter than 16/16 across Y.

## Predicate comparison

For each old state and each of NORTH, EAST, SOUTH and WEST, `WallBlock` obtains `BlockState.getFaceShape` and passes it to `shouldConnectTo` (`WallBlock.java:51–54, 81–84`). `BlockState.getFaceShape` delegates to the block's `getFaceShape`; `PlantBlock.getFaceShape` returns `FaceShape.UNDEFINED` (`PlantBlock.java:43–45`). Therefore the old `shape == SOLID` branch is false, as is the middle-pole branch. Each of the 8 states has a false wall bit in all four horizontal directions.

For each target state and horizontal direction, `WallBlock` passes `BlockState.m_87223014` to its predicate (`WallBlock.java:48–51, 67–70`). `m_87223014` delegates to `Block.isFaceSolid` (`BlockState.java:326–328`); that method tests the collision-shape face for a full unit face (`Block.java:402–408`). Since each pickle collision shape has a horizontal face slice with height at most 7/16 and width at most 12/16, `isFaceFull` is false for all four horizontal directions and all four pickle counts. `PICKLES` chooses only these four shapes, and waterlogged state does not change them.

The other target wall-predicate alternatives are false: the 1.14.4 `WALLS` tag does not contain sea pickle; `SeaPickleBlock` is not a `FenceGateBlock`; and `Block.isExceptionForAttachment` does not name sea pickle. The exact raw target resource `data/minecraft/tags/blocks/walls.json` has SHA-256 `f20174f51f66d27681fd9d469a77c86cae82012c0d9125e7d246c426b772391c` and excludes `minecraft:sea_pickle`. Its extraction record is SHA-256 `f26f84c7221f667cd84ff4e048d06b0fd3c3aec52779f3fbd0a8f4e2afe64fea`, independently accepted by `wall_resource_membership_audit/verdict-r2.md` SHA-256 `6402c6af692b3f198c853d3558b91d4de7c215db793e8847e66e45f08f4d8898`; the audited payload is bound to the exact 1.14.4 client JAR SHA-256 `b3b2a798e2d67b566008fe4a03767ae2c7ff3f8c7ba6751e7b71fc7299672d0a`.

**Result:** for the bounded `minecraft:sea_pickle` wall-neighbor predicate, all 8 registered states × 4 horizontal directions are false→false. This is a no-difference predicate result only; it does not establish player movement impact, close any broader provider/state inventory, or freeze the pair.

## Canonical Java source hashes

All listed Java hashes were recomputed from the read-only canonical source files and match the respective manifest entries.

| Relative Java path | 1.13.2 SHA-256 | 1.14.4 SHA-256 |
| --- | --- | --- |
| `net/minecraft/block/SeaPickleBlock.java` | `1b232549de646d46b2f986d78802b0f7208978e103f00bd98fe64e4fe47d76a4` | `8adcb2c2bf60478e41ca2542ac9e661172e8c3d1bac9674fb293eca4d164e12f` |
| `net/minecraft/block/PlantBlock.java` | `a1234ccec309bd8805fbe3bad48bce076e17c131e048d2a57d18cdb75f0f286e` | `6ede310fcb1e0163865dd4161b291cc02fe223bb56dfa1f89aa64b1cf72a1f75` |
| `net/minecraft/block/WallBlock.java` | `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf` | `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7` |
| `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` |
| `net/minecraft/block/Blocks.java` | `3e128c9a4f6b53e1037fb85269c4d00bc85112ae52fe48f9534232e13ee2a303` | `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8` |
| `net/minecraft/block/state/BlockState.java` | `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70` | `6c6703c7c2f7203a7507d8a621bc83e1dad613b32089394f5af4fbd8636b165f` |
| `net/minecraft/state/property/Properties.java` | `3882c1b832ac8b2b16f5a83978da441ab03cdfe66f053a873342758045827f4c` | `9b524af5065100919c60667778e38986380c9df954ef70a9d0ac0a739132ddca` |
| `net/minecraft/state/StateDefinition.java` | `d7702c839e8d8181a49a24d9d81df69412f78253e9e66633bb5c6c03ef349365` | `d7702c839e8d8181a49a24d9d81df69412f78253e9e66633bb5c6c03ef349365` |

Independent source acceptance remains pending. The pair remains partial.
