# Independent source audit verdict: wall rail/button face projection

**Verdict: ACCEPT WITH CITATION CORRECTIONS.** The report's bounded conclusion is source-supported: the four rail keys and seven button keys project a false wall connection bit in all four horizontal directions in both endpoints, for all 214 stated state-definition tuples. The corrections below repair registration line references; they do not change the class/key joins or the face result.

## Bound evidence

- Reviewed report: `build/source-residual-20261116/1.13.2--1.14.4/wall_rail_button_face_projection/report.md`
- Exact report SHA-256: `b1e29b7c575b6244e46c53d6966a14e0e5e383f4feea7c991058a2fa478596bf`
- Referenced scope parent `wall_remaining_face_shape_census/report.md` SHA-256: `f1a12c7564f14c91c652bea6603e8ada8350b98c2892df05c591c0161d4e041d` (verified).
- 1.13.2 Ornithe Feather Gen2 source-manifest SHA-256: `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`
- 1.14.4 Ornithe Feather Gen2 source-manifest SHA-256: `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`
- Both manifest digests match their ready markers. All 17 source-file hashes listed in the report match both the exact source-manifest entries and the local decompiled source bytes. The referenced `walls.json` payload hashes to `f20174f51f66d27681fd9d469a77c86cae82012c0d9125e7d246c426b772391c`; its 14 IDs contain none of the assigned rail/button keys.

## Citation corrections

- Report table row `rail`: target `Blocks.java:261` identifies `powered_rail`; the `rail` registration is at `Blocks.java:452`.
- Report table row `powered_rail`, `activator_rail`: target `Blocks.java:264` identifies `detector_rail`; `powered_rail` is at `Blocks.java:261`. `activator_rail` at `Blocks.java:964` is correct.
- Report table row `detector_rail`: target `Blocks.java:452` identifies `rail`; `detector_rail` is at `Blocks.java:264`.
- Report table row for the six wooden buttons: old `Block.java:1019–1054` identifies the six wooden pressure plates, not the buttons. The six old `WoodenButtonBlock` registrations are at `Block.java:1266–1271`.

The corrected source joins are: `rail` → `RailBlock`; `powered_rail` and `activator_rail` → `PoweredRailBlock`; `detector_rail` → `DetectorRailBlock`; `stone_button` → `StoneButtonBlock`; the six wood-button IDs → `WoodenButtonBlock`, on each endpoint. All eleven registrations have `noCollision()` on both endpoints.

## Predicate and state-domain check

In 1.13.2, `AbstractRailBlock.getFaceShape` and `ButtonBlock.getFaceShape` return `FaceShape.UNDEFINED` unconditionally. The old wall's generic solid-face arm is therefore false for these classes in N/E/S/W; the pole/gate arm does not apply.

In 1.14.4, the wall generic arm consumes `BlockState.m_87223014`, which resolves to the cached or computed `Block.isFaceSolid` result. `noCollision()` sets `hasCollision=false`; `Block.getCollisionShape` then returns `VoxelShapes.empty()` without using the rail/button outline shape. An empty shape has no full directional face. The tag/gate arm is false as well: the exact 14-value wall-tag payload excludes all eleven IDs, and none of these registered classes is a fence gate.

The rail domain is 10 `RAIL_SHAPE` values plus three `RAIL_SHAPE_STRAIGHT × POWERED` domains of 6 × 2 each: 46 tuples. Each button domain is `HORIZONTAL_FACING` (4) × `AttachFace` (3) × `POWERED` (2): 24 × 7 = 168 tuples. Total: 214. The formulas and source declarations support the report's count.

This verdict accepts only the assigned rail/button projection result; it does not freeze the source pair or close other residual keys.

