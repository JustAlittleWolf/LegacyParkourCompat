# Independent audit: residual slab wall-face projection

**Verdict: ACCEPT, bounded predicate result only.** The audited author report is `build/source-residual-20261116/1.13.2--1.14.4/wall_remaining_slab_face_projection/report.md`, SHA-256 `8615edc5a7da500035535b7c7c759c8da48b39029b5ad52d2802ac5a7435adb1`.

## Identity, inputs, and residual keys

The endpoint ready source manifests match the report: 1.13.2 Feather Gen2 build 2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; 1.14.4 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`. The crosswalk input `wall_full_registry_owner_crosswalk/unexamined-wall-projection-keys.tsv` matches SHA-256 `5deaea77f1ab904cadbc2a3338e8c31a92ec577c3c291932688aedef96c227b4`. Filtering its registry key column for `_slab` yields exactly the report's 15 keys, each with old and target concrete owner `SlabBlock`. The previously accepted four keys (`stone_slab`, `brick_slab`, `quartz_slab`, `purpur_slab`) are not in this residual subset.

All referenced source-file digests match both the bytes and their endpoint source manifests: old `Block.java` `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4`, `SlabBlock.java` `2e7ff3fcfa5faa83aa4af7ea9b68f07af8d8fe7047e08ee3a9d7d37b78c7128f`, `WallBlock.java` `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf`; target `Blocks.java` `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8`, `Block.java` `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5`, `SlabBlock.java` `91f2ebc592e5cb07d5ce8a6faa123ec0d4b7bf10aa6323d6aceb343bc0f73a53`, `WallBlock.java` `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7`; both `SlabType.java` files `20ef3591cf5f5cf83886d7ab5034fd8402418f68e517180cbba2fe0c7535f697`.

## Independent predicate check

Both `SlabBlock` versions define `HALF` and `WATERLOGGED`; the exact `SlabType` enum has `TOP`, `BOTTOM`, `DOUBLE`. Thus each key has six legal property combinations, not four. The old `getFaceShape` returns `SOLID` for every face only when `HALF=DOUBLE`; TOP and BOTTOM contribute their one vertical full face, never a horizontal one. Old `WallBlock.shouldConnectTo` accepts SOLID subject to its exception gate. None of the 15 keys is one of the old attachment/connection exceptions.

The target `SlabBlock.getShape` is full block for DOUBLE and upper/lower half for TOP/BOTTOM, without consulting WATERLOGGED, world, position, or collision context. `Block.isFaceSolid` checks the selected collision-shape face for fullness and rejects leaves. A horizontal face of a double slab is full; the four side faces of either half slab are half-height and not full. Target `WallBlock` applies its attachment exception gate and otherwise accepts this predicate (or its separately checked WALLS/fence-gate cases); none of these slab keys is an attachment exception or in the WALLS resource. Therefore the old and target slab branch agree for all states and horizontal directions.

The exhaustive Cartesian count is 15 keys × 3 HALF values × 2 WATERLOGGED values × 4 horizontal directions = 360 cases. All yield true for DOUBLE and false for TOP/BOTTOM on both endpoints. Result: no slab wall-arm face-predicate delta in this bounded slice.

## Limits

This accepts only the stated residual common-slab horizontal-face predicate projection. It does not establish complete neighbor-provider coverage, other wall gates or directions, player movement effects, or pair completion/freeze.
