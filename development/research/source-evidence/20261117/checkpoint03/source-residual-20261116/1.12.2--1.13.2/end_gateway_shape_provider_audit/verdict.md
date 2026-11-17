# Independent audit: End Gateway shape provider

**Audited claim revision:** `build/source-residual-20261116/1.12.2--1.13.2/end_gateway_shape_provider/end-gateway-shape-provider.md`, SHA-256 `573703e496c3100a7efa172e2c3a80a7a4157c5990bc9750745b3d1e343859a3`.

## Verdict

**Accepted for the bounded claim: the registered `minecraft:end_gateway` has no effective block collision for ordinary player movement in either 1.12.2 or 1.13.2.** The class-local provider changed form, but the registered collision result remained empty.

## Audit evidence

- Both ready markers identify the requested exact versions and `ornithe-feather` mapping. The marker's source-manifest digest equals the actual manifest digest: 1.12.2 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.
- All 13 canonical source files and hashes listed in the audited report match both the actual files and their exact-version source-manifest rows. The extra player reachability files checked for this audit (`PlayerEntity.java`, `LivingEntity.java` for both releases) also match their source-manifest rows.
- The three registry join inputs match the report's stated SHA-256 values: old registry inventory `29278a0c2b0899866f444296bae8575bc6e039c3bbe419d0c51051077c1624a2`, new inventory `d9bae34a1b4f02a5ab00f7ce7909f420663625c145c328fc5f332a9957a2b139`, and crosswalk `bbb1c099dc384489dbb219daa90f797649fc0c8094e5931a68a0c2997f32280a`. The inventories join the same `minecraft:end_gateway` key to `EndGatewayBlock`; crosswalk row 263 marks it `EXACT_KEY_ONLY` and identifies the effective target provider as `Block.getCollisionShape → Block.getShape`.
- In 1.12.2, `EndGatewayBlock.getCollisionShape` returns `Block.EMPTY_BLOCK_SHAPE`; `Block.java` defines that sentinel as `null`. `Block.addCollision` skips that sentinel before dereferencing or adding a box. The state implementation forwards to the block provider.
- In 1.13.2, `EndGatewayBlock` has no collision-shape override. Its registration constructs `EndGatewayBlock` with `Properties.noCollision()`, which sets `hasCollision=false`. The base `Block.getCollisionShape` returns `VoxelShapes.empty()` when that flag is false, before it can use the default full-unit `getShape`. The collision stream rejects empty shapes.
- Ordinary player movement reaches this query in both versions: `PlayerEntity` extends `LivingEntity`; `LivingEntity` calls `this.move(MoverType.SELF, ...)`; `Entity.move` requests world block collisions for its displacement. Thus the empty result applies on the normal player path, rather than only on a placement or inspection helper.

## Bounded gates

This accepts only the `minecraft:end_gateway` registration and ordinary block-collision route for the exact 1.12.2 / 1.13.2 sources above. It does not establish full pair coverage, an intermediate patch boundary, other End Gateway operations, or behavior of other provider families. No runtime parity or test claim is made.
