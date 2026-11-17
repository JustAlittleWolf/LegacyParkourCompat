# Slime, soul sand, and magma wall-neighbor projection: 1.13.2 → 1.14.4

## Exact source identity and scope

Both endpoints are Ornithe Feather Gen2 build 2 from the ready source trees. The 1.13.2 manifest is `decompiled_minecraft/1.13.2/ornithe-feather.sources.sha256`, SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; the 1.14.4 manifest is `decompiled_minecraft/1.14.4/ornithe-feather.sources.sha256`, SHA-256 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`. Ready markers identify these same release, mapping and manifest identities.

This source-only slice compares only the unexamined common registry keys `minecraft:slime_block`, `minecraft:soul_sand`, and `minecraft:magma_block` listed in `build/source-residual-20261116/1.13.2--1.14.4/wall_full_registry_owner_crosswalk/unexamined-wall-projection-keys.tsv` (input SHA-256 `8a79ad3e851470a59976b9120d085ab2ec556d3cd61885205113dbd167712366`). It computes the four horizontal WallBlock neighbor bits from the exact old and target predicates. It does not analyze entity collision callbacks, damage, other slime responses, wall placement/update reachability, or player trajectories, and does not freeze the pair.

## Predicate and provider routes

At 1.13.2, `BlockState.getFaceShape` delegates to the block's `getFaceShape` (`net/minecraft/block/state/BlockState.java:285–286`). The base `Block.getFaceShape` returns `FaceShape.SOLID` (`net/minecraft/block/Block.java:383–384`). Old `WallBlock.shouldConnectTo` accepts a non-exception SOLID face (`net/minecraft/block/WallBlock.java:51–64`); it separately accepts thick poles and matching fence gates. None of the three assigned blocks is in the explicit exception list or is a `FenceGateBlock`. All three therefore produce a true generic wall bit on every horizontal neighbor face.

At 1.14.4, `WallBlock` calls `BlockState.m_87223014` for each horizontal neighbor and passes the result to its wall-bit predicate (`net/minecraft/block/WallBlock.java:48–52, 67–70`). `BlockState.m_87223014` delegates to `Block.isFaceSolid` (`net/minecraft/block/state/BlockState.java:326–327`); `Block.isFaceSolid` requires a full face in the state's collision shape (`net/minecraft/block/Block.java:402–408`). The wall predicate has independent true branches for membership in `BlockTags.WALLS` and for matching fence gates, but none of these keys is in the exact `WALLS` resource or is a gate. None is in `Block.isExceptionForAttachment` either.

`SlimeBlock` inherits shape and collision behavior through `TransparentBlock` and `Block`; the transparent superclass only changes same-block render-face culling. `MagmaBlock` also inherits base shape and collision behavior. Their registrations do not disable collision, so their default block collision shape is a full cube at both endpoints. `SoulSandBlock` overrides its collision shape at both endpoints, returning `Block.block(0, 0, 0, 16, 14, 16)` (`net/minecraft/block/SoulSandBlock.java:14, 20–22` in 1.13.2 and `:16, 22–24` in 1.14.4). Its horizontal face is only 14/16 high and is not full.

## Registered keys and results

The exact old registrations are in 1.13.2 `net/minecraft/block/Block.java:1079, 1380, 1609`; the target registrations are in 1.14.4 `net/minecraft/block/Blocks.java:580–582, 1070, 1445–1446`. Each registration has no block-state properties, so each key has one registered state.

| Registry key | State count | 1.13.2 old FaceShape → wall bit | 1.14.4 collision face → wall bit | N/E/S/W result |
|---|---:|---|---|---|
| `minecraft:slime_block` | 1 | inherited `SOLID`, not an exception → true | inherited full-cube collision face, not a tag/exception → true | true → true |
| `minecraft:soul_sand` | 1 | inherited `SOLID`, not an exception → true | explicit 16×14×16 collision shape; horizontal face not full → false | **true → false** |
| `minecraft:magma_block` | 1 | inherited `SOLID`, not an exception → true | inherited full-cube collision face, not a tag/exception → true | true → true |

The Soul Sand result is a source-level difference in the neighbor connection bit. It can feed a different WallBlock state/shape when this block is the queried neighbor. This packet does not establish which complete wall placements or subsequent movement trajectories realize the difference.

## Hash bindings

Each listed Java source SHA-256 was recomputed from canonical bytes and matched the same path entry in its endpoint's source manifest.

| Java path | 1.13.2 SHA-256 | 1.14.4 SHA-256 |
|---|---|---|
| `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` |
| `net/minecraft/block/WallBlock.java` | `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf` | `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7` |
| `net/minecraft/block/state/BlockState.java` | `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70` | `6c6703c7c2f7203a7507d8a621bc83e1dad613b32089394f5af4fbd8636b165f` |
| `net/minecraft/block/SlimeBlock.java` | `8a97ba1ed4b65141b6623c7a5b13162767a54bc5666a4d99e8518ba0113cfae4` | `9c5cf3602653fd5699064364906cd1f08ae445cba74674a1c9d8026aeafd24ca` |
| `net/minecraft/block/TransparentBlock.java` | `392cbb712f2e3cc70b23f40b917ed837958c37173a72f3a6aedb3403769c07f6` | `392cbb712f2e3cc70b23f40b917ed837958c37173a72f3a6aedb3403769c07f6` |
| `net/minecraft/block/MagmaBlock.java` | `1ed06ac125a3c421312b0c00705c894358b27e7a77f0e43aad9ec3f9263bc9d9` | `0cbb0ee522d22301fbe88fcfdb35e5b39dc17da0cf1351513d51969edfc3ce73` |
| `net/minecraft/block/SoulSandBlock.java` | `259645f4b6dbc7b71481756c8fcd804d7bd3dadb679d09f06284ffb9d145b5be` | `fd8afe4294317c439f7a8c90143aea1a59491c746d9fe1645db6094e7c53f21f` |
| `net/minecraft/block/Blocks.java` | not present in this older source layout; registry is in `Block.java` | `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8` |

The 1.14.4 `data/minecraft/tags/blocks/walls.json` payload is at `build/source-residual-20261116/1.13.2--1.14.4/wall_resource_membership/payloads/data/minecraft/tags/blocks/walls.json`, SHA-256 `f20174f51f66d27681fd9d469a77c86cae82012c0d9125e7d246c426b772391c`. It lists 14 wall block keys; none of these three keys appears. Its pinned-artifact extraction identity and independent resource audit are retained in `wall_resource_membership/extraction-record.md` and `wall_resource_membership_audit/verdict-r2.md` (verdict SHA-256 `6402c6af692b3f198c853d3558b91d4de7c215db793e8847e66e45f08f4d8898`).

## Bounded verdict and remaining gates

The exact source comparison establishes no wall-bit change for slime block or magma block and one wall-bit change for soul sand (`true→false`) for the singleton states in all four horizontal directions. This closes only these three registry-key predicate rows. Player collision consequences require a separately bounded source review of WallBlock state update/placement and its resulting collision shape under concrete neighbor configurations; no such trajectory claim is made here. The rest of the pair's registered key/provider inventory, tick and external-response coverage, and independent full-pair audit remain open.
