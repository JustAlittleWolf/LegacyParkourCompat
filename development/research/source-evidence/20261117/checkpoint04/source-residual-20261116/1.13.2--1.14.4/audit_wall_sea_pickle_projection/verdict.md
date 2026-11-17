# Independent source audit — sea pickle wall predicate

**Verdict: ACCEPT**, bounded to the source claim in `build/source-residual-20261116/1.13.2--1.14.4/wall_sea_pickle_projection/report.md` (SHA-256 `689b1150669377e266216f844fb7f1316c44bde2469ec36a02cee99553df2370`).

## Checks

- The 1.13.2 and 1.14.4 Feather ready markers bind source-manifest SHA-256 values `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` and `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc`; each marker's digest matches the corresponding `ornithe-feather.sources.sha256` file. All eight source files listed in the report match their canonical bytes and source-manifest entries on both sides.
- The pinned unexamined-key TSV SHA-256 is `8a79ad3e851470a59976b9120d085ab2ec556d3cd61885205113dbd167712366`; its `minecraft:sea_pickle` row has old `SeaPickleBlock` inheriting `PlantBlock.getFaceShape` and target `SeaPickleBlock.getShape` through the base collision route.
- Both SeaPickleBlock state definitions add `PICKLES` and `WATERLOGGED`; both `Properties` files define PICKLES as 1–4 inclusive and WATERLOGGED as boolean. `StateDefinition` enumerates the Cartesian product of property values, so the registered domain is eight states per endpoint. The shape switch depends only on PICKLES.
- Both versions define identical four shape bounds: 6–10 × 0–6 × 6–10; 3–13 × 0–6 × 3–13; 2–14 × 0–6 × 2–14; and 2–14 × 0–7 × 2–14, in sixteenths. The shared `Block.block` helper divides coordinates by 16. The 1.14.4 registration does not call `.noCollision()` and `Block.Properties.hasCollision` defaults to true, so the state shape is used as its collision shape.
- In 1.13.2, `PlantBlock.getFaceShape` returns `UNDEFINED`; the old wall predicate connects only SOLID faces or its middle-pole cases, neither of which applies to sea pickle. In 1.14.4, the wall generic arm uses `BlockState.m_87223014`, which delegates to `Block.isFaceSolid` and tests the collision-shape face for a full unit face. Each pickle shape is narrower than a block and shorter than one block, so none of its horizontal slices is full. The `WALLS` payload (SHA-256 `f20174f51f66d27681fd9d469a77c86cae82012c0d9125e7d246c426b772391c`) excludes sea pickle; its extraction record SHA-256 `f26f84c7221f667cd84ff4e048d06b0fd3c3aec52779f3fbd0a8f4e2afe64fea` is bound by accepted resource audit `wall_resource_membership_audit/verdict-r2.md` (SHA-256 `6402c6af692b3f198c853d3558b91d4de7c215db793e8847e66e45f08f4d8898`). Sea pickle is not a fence gate. The old and target routes therefore both yield false for all eight states and four horizontal directions.

## Limits

Acceptance covers only the `minecraft:sea_pickle` wall-neighbor predicate. It does not establish a player movement effect, close other provider/state/resource dependencies, or freeze the pair. The source pair remains partial.
