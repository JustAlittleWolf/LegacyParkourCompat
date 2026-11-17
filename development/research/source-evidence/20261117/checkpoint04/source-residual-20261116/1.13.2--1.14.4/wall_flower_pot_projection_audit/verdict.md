# Independent audit verdict: wall flower-pot projection

Verdict: **ACCEPT** for the bounded flower-pot family and wall-face projection claim.

Reviewed report SHA-256: `b03896935a0fb0f526b9d6c13534aec537dc2fcc43c987b81e2b8455d91606ad`.

## Audit checks

- Recomputed the report SHA-256 and both exact Feather Gen2 source-manifest SHA-256 values. They match the assigned identities: 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; 1.14.4 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`.
- Recomputed all nine cited Java source-file hashes against the bytes and their entries in the endpoint manifests. Every hash agrees. Also recomputed the cited wall-face census report hash; it matches `f1a12c7564f14c91c652bea6603e8ada8350b98c2892df05c591c0161d4e041d`.
- Independently checked registrations: the common set is base `flower_pot` plus the 21 potted keys listed in the report. The target-only keys are `potted_cornflower`, `potted_lily_of_the_valley`, `potted_wither_rose`, and `potted_bamboo`.
- Confirmed the 1.13.2 `Blocks.POTTED_CORNFLOWER` field is assigned from `getOrThrow("spruce_sapling")`; it is a field alias to that registry entry, not a registration of a `potted_cornflower` key. In 1.14.4 `Blocks.ALL` is the block registered as `spruce_sapling`, and the common `potted_spruce_sapling` registration uses it as content. This distinction and the target-only cornflower key are reported correctly.
- Checked the registrations' content arguments and provider implementations. Each registered pot is a `FlowerPotBlock`; the content is stored separately and does not affect its shape. Neither version adds state properties in `FlowerPotBlock`, so each registered pot has only its default state. Both providers return the same cuboid, with X/Z `[5,11]/16` and Y `[0,6]/16`; default collision is enabled and the registrations do not disable it.
- Checked both wall predicates and all four horizontal neighbor-face calls. In 1.13.2 every pot returns `FaceShape.UNDEFINED`; the generic wall branch requires `SOLID`, and no pot passes the pole exception. In 1.14.4 the collision-shape projection for each horizontal face is not a full-block face, so `Block.isFaceSolid` is false for all four directions; pots are not fence gates. The exact 1.14.4 raw-resource `walls.json` payload independently confirms no potted key is in `BlockTags.WALLS` (payload SHA-256 `f20174f51f66d27681fd9d469a77c86cae82012c0d9125e7d246c426b772391c`; extraction record pins the official client artifact and the Feather source identity).

## Corrections

None. The report supports no wall-bit or wall-collision-shape difference for this bounded set. Its stated limits remain appropriate: this verdict does not freeze the overall pair or close other wall registrations, state spaces, or dependencies.
