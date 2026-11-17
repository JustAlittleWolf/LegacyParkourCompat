# Independent source audit: mushroom and plant wall predicate

## Verdict

**ACCEPT** the bounded predicate packet `wall_mushroom_plant_projection/report.md`, whose SHA-256 is `3fecc90f81ed60cea1118fb5fd371f78a488b69ae42ebf9136a7a2396a58cfd7`.

## Identity and scope checks

- 1.13.2 ready marker/source sidecar digest: `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.
- 1.14.4 ready marker/source sidecar digest: `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`.
- Independently recalculated SHA-256 for all 12 source paths listed in the report at both endpoints. Every canonical source byte hash matches its sidecar entry and the report.
- The input residual TSV SHA-256 is `8a79ad3e851470a59976b9120d085ab2ec556d3cd61885205113dbd167712366`. The assigned eight keys appear exactly once in the TSV: brown_mushroom, red_mushroom, brown_mushroom_block, red_mushroom_block, mushroom_stem, fern, grass, potatoes. The report explicitly distinguishes them from `wall_plant_fluid_projection`.
- Rechecked the cited raw tag payload hashes and values: `WALLS` (`f20174f51f66d27681fd9d469a77c86cae82012c0d9125e7d246c426b772391c`) lists 14 wall keys and none of these eight; `LEAVES` (`c09bb9af30197720b232b9c84079039d4f344bfc2970fa0d02a1b43bc091b991`) lists six leaf keys and none of these eight. The resource verdict cited by the author is `6402c6af692b3f198c853d3558b91d4de7c215db793e8847e66e45f08f4d8898`.

## Predicate verification

The 1.13.2 `BlockState.getFaceShape` delegates to the registered block's `getFaceShape`; `WallBlock` uses that face with its exception and gate tests. Base `Block.getFaceShape` is `SOLID`; `PlantBlock.getFaceShape` is `UNDEFINED`. The eight keys are not in the cited old exception list and none is a fence gate.

For target 1.14.4, `WallBlock` passes `BlockState.m_87223014` into its wall predicate. `BlockState.m_87223014` delegates to `Block.isFaceSolid`; that method rejects `LEAVES` and otherwise checks a full face of the collision shape. The separate WALLS-tag and fence-gate arms are false for this set.

The three MushroomBlock registrations each expose six independent Boolean properties (UP, DOWN, NORTH, EAST, SOUTH, WEST), hence 64 legal states per key. Neither endpoint MushroomBlock overrides the shape provider; target default Block shape is a unit cube and default `hasCollision` is true. Registrations omit `noCollision`. This establishes SOLID/full collision face and true wall bits for all 192 key-state combinations.

The five remaining keys are registered with `noCollision` at both endpoints. Old mushroom plants, fern, grass, and potatoes inherit the old PlantBlock UNDEFINED face. Target noCollision causes base `Block.getCollisionShape` to return empty; target `Block.isFaceSolid` therefore returns false. Potatoes inherits `AGE_7` through WheatBlock, with values 0–7, so its eight states are covered. Mushroom plants, fern, and grass have singleton state domains. The reported totals are consistent: 204 key-state combinations and 816 direction checks.

**Result:** all 816 reported wall-bit cases are supported: false→false for the five plant keys and true→true for the three mushroom-block keys. This is acceptance of the named wall provider/predicate slice only. It does not assert player-movement impact, close other dependencies, or freeze either pair. The historical crosswalk limitation (r1 TSV overwritten, so unchanged rows could not be byte-diffed) remains attached to the crosswalk and is not repaired by this audit.
