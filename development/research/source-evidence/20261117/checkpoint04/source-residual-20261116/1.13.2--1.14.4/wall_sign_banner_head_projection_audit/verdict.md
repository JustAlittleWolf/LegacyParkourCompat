# Independent source audit verdict

**Verdict: ACCEPT for the bounded family result, with one non-blocking predicate-description correction.**

Audited report SHA-256: `62b236ebe603ff7ebc06d5baec792b871fd975e2f66d152e89a11d22ff0d63ce`.

The exact Feather Gen2 source-manifest SHA-256 values match the assignment: 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` and 1.14.4 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`. The local ready records identify those exact versions and mapping. Every source-file hash listed in the report was checked against both its source-manifest entry and the corresponding file bytes; all match. The report's own bytes match its declared audit input SHA.

The registrations support the key projection: old `sign` and `wall_sign` map semantically to target `oak_sign` and `oak_wall_sign`; the other target wood sign IDs are target-only. All 32 banner IDs and all 12 floor/wall head IDs are common across endpoints. The listed orientation properties and finite domains match the block state definitions; floor/wall skull type is selected by registered block instance. Each old family base (`SignBlock`, `BannerBlock`, `SkullBlock`) returns `FaceShape.UNDEFINED`, so these members satisfy neither the old generic SOLID gate nor its pole/fence-gate special case. No roster member is in the old connection-exception family.

On 1.14.4, all sign and banner registrations in scope use `.noCollision()`, making their collision shapes empty for this predicate. The floor and wall skull collision shapes are the fixed registered class shapes; their horizontal face projections are respectively inset/shorter than a full face and at most 8×8. They therefore cannot pass `Block.isFaceSolid` in any listed state or horizontal direction. Head block-entity data and collision context do not change these shape results.

**Correction to carry if the report is revised:** the 1.14.4 wall connection helper has an additional `BlockTags.WALLS` / directional-fence-gate branch ORed with the generic face-solid result. I checked `BlockTags.WALLS` against the exact pinned 1.14.4 client jar (SHA-256 `b3b2a798e2d67b566008fe4a03767ae2c7ff3f8c7ba6751e7b71fc7299672d0a`); its built-in values contain none of the audited family. None of these registered instances is a fence gate. This omitted branch does not change the bounded no-difference conclusion, but the report should state the branch and exclusion to describe the full predicate exactly.

This verdict accepts only the report's bounded wall-bit no-difference result for signs, banners, and heads/skulls across 1.13.2–1.14.4. It does not close other neighbor families, dependencies, full-pair coverage, or pair freeze.
