# Lily pad and dead bush wall-predicate projection: 1.13.2 → 1.14.4

## Exact source identity and owned slice

Both endpoints are Ornithe Feather Gen2 build 2. The ready markers are `decompiled_minecraft/1.13.2/ornithe-feather.ready.json` (source-manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`) and `decompiled_minecraft/1.14.4/ornithe-feather.ready.json` (source-manifest SHA-256 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc`). Each cited Java file was hashed from the canonical read-only source directory and its digest matches the corresponding root `ornithe-feather.sources.sha256` entry.

This packet is limited to the exact `minecraft:lily_pad` and `minecraft:dead_bush` rows in `wall_full_registry_owner_crosswalk/unexamined-wall-projection-keys.tsv` (SHA-256 `8a79ad3e851470a59976b9120d085ab2ec556d3cd61885205113dbd167712366`). The rows were still classified `UNEXAMINED_FOR_WALL_PREDICATE`. I checked overlap against the accepted `wall_plant_fluid_projection` report (SHA-256 `41c1ec692df824c1aea57c186a4ff5968cc72fd257d359ccfb0cf88cb8ea20ee`) and its correction-bound audit (verdict SHA-256 `fd9bf69b11aaf9cc64ac884bf78fcc401186cfd084a4501f3713df6c25fa367b`): its eleven-key table contains neither key. The source-residual tree contains no prior wall-predicate packet for either key; `potted_dead_bush` in the separate flower-pot packet is a different registry key and is excluded. No crop-predicate packet covers either key.

## Compared source facts

In 1.13.2, both registered classes extend `PlantBlock`; its `getFaceShape` returns `FaceShape.UNDEFINED` for every face (`PlantBlock.java:43–45`). `BlockState.getFaceShape` delegates to the block override (`BlockState.java:285–286`). The old `WallBlock.shouldConnectTo` accepts the generic arm only for `FaceShape.SOLID` and its pole arm only for middle-pole face shapes / a fence gate (`WallBlock.java:51–55`). Thus both keys yield a false horizontal wall-connection bit. Neither concrete class is a fence gate.

In 1.14.4, `WallBlock.m_71853799` uses `(!isExceptionForAttachment(block) && isFaceSolid) || (block.is(BlockTags.WALLS) || direction-valid fence gate)` (`WallBlock.java:48–52`). The raw pinned `walls.json` payload has SHA-256 `f20174f51f66d27681fd9d469a77c86cae82012c0d9125e7d246c426b772391c` and contains neither key; the raw `leaves.json` payload has SHA-256 `c09bb9af30197720b232b9c84079039d4f344bfc2970fa0d02a1b43bc091b991` and contains neither key. Their extraction identity and resource hashes are in `wall_resource_membership/extraction-record.md` (SHA-256 `f26f84c7221f667cd84ff4e048d06b0fd3c3aec52779f3fbd0a8f4e2afe64fea`), independently checked in `wall_resource_membership_audit/verdict-r2.md` (SHA-256 `6402c6af692b3f198c853d3558b91d4de7c215db793e8847e66e45f08f4d8898`). Neither block is in `Block.isExceptionForAttachment`'s literal exceptions (`Block.java:260–267`), and neither is a fence gate.

The registered domains are singleton states at both endpoints: neither class overrides the empty base `Block.buildStateDefinition` (`Block.java:713` in 1.13.2; `Block.java:736` in 1.14.4), and the exact crosswalk rows independently record cardinality 1. In target collision-face evaluation, `BlockState.m_87223014` calls `Block.isFaceSolid` when its cache is absent (`BlockState.java:326–327`); `Block.isFaceSolid` rejects the leaves tag and tests whether the collision-shape face is full (`Block.java:402–408`).

* `dead_bush`: 1.14.4 registers `DeadBushBlock` with `Block.Properties.noCollision()` (`Blocks.java:274–275`). `noCollision()` sets `hasCollision=false` (`Block.java:853`), so the inherited collision provider returns an empty shape (`Block.java:376–378`); `DeadBushBlock.getShape` is not used for collision in that registration. The collision face is therefore not full in any horizontal direction.
* `lily_pad`: 1.14.4 registers `LilyPadBlock` with collision enabled by the base default and without `.noCollision()` (`Blocks.java:741`). Its fixed shape is `Block.block(1.0, 0.0, 1.0, 15.0, 1.5, 15.0)` (`LilyPadBlock.java:14,30–31`), inset to 14/16 of a block across and only 1.5/16 high. Each north/east/south/west collision-face slice is short of a full unit face, so `isFaceSolid` is false in every horizontal direction.

Both target keys therefore produce false connection bits in all four queried horizontal directions. The projection is **false → false** for each key and its singleton state. This checks only the registered vanilla state wall-neighbor predicate; no movement trajectory or pair-completion claim is made.

## Source hashes

SHA-256 values below were recomputed from canonical Java bytes and matched to the ready-manifest entries.

| Java source | 1.13.2 | 1.14.4 |
|---|---|---|
| `net/minecraft/block/WallBlock.java` | `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf` | `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7` |
| `net/minecraft/block/PlantBlock.java` | `a1234ccec309bd8805fbe3bad48bce076e17c131e048d2a57d18cdb75f0f286e` | `6ede310fcb1e0163865dd4161b291cc02fe223bb56dfa1f89aa64b1cf72a1f75` |
| `net/minecraft/block/LilyPadBlock.java` | `741784cff408619ec5f262366b481c92bd5892d329fc2f77393e3faeae6397eb` | `044fb0551c40490da3507e21ebcc2af81b82f6f5477df324681b07635f9875e5` |
| `net/minecraft/block/DeadBushBlock.java` | `101b68c9595addc9bcedb6c49cd9908eb6bf3d9a093abc6f4584f7b94a5aa4f9` | `0615386766594e7d7fcf9a929cf1deff3cc3eb90f9a6395ab5d3febef0063732` |
| `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` |
| `net/minecraft/block/Blocks.java` | `3e128c9a4f6b53e1037fb85269c4d00bc85112ae52fe48f9534232e13ee2a303` | `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8` |
| `net/minecraft/block/state/BlockState.java` | `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70` | `6c6703c7c2f7203a7507d8a621bc83e1dad613b32089394f5af4fbd8636b165f` |
