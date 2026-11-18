# Piston response source comparison: 1.9.1 to 1.9.2

## Exact evidence identity

- Older source: `decompiled_minecraft/1.9.1/ornithe-feather`, Feather-gen2 `1.9.1+build.2` merged-v2 input SHA-256 `6d5768353bf2ff2756d38537312548a8ac91d570f130d23c23e9412d2edd97b3`; Feather metadata input SHA-256 `da56c9e7efe5c929b2ec8d99a8e2bbc6825847f2852a0e717fcd6c674db73dc4`; Mojang 1.9.1 client input SHA-256 `56ad7e53f6f4ad393844e274aa2190692b61d2ad9f6b3d12b33a8b7332fd51f9`.
- Newer source: `decompiled_minecraft/1.9.2/ornithe-feather`, Feather-gen2 `1.9.2+build.2` merged-v2 input SHA-256 `e2b98ececbff8fa0bd0c89283bbb32b7f9f9dcd4b89fa3f137666fa3ac0b6859`; Feather metadata input SHA-256 `5bad5e67d0870fec69bbab8fcae8d0525a274668bb472eb09d1552359d08edd1`; Mojang 1.9.2 client input SHA-256 `ef87eb71250df1b871deab8d3ef10aac3290b9e78667ad548fde4d0e14af0d65`.
- Paired file: `net/minecraft/block/entity/MovingBlockEntity.java`, lines 90–182. Both provenance manifests record source SHA-256 `7ad3426bb74c9e69b4f521d20e045b1dc1eafa9b725daf0e2c0b9b718bc2683c`; direct file hashing confirms it on each side.

## Bounded comparison

`MovingBlockEntity.tick()` copies `progress` to `lastProgress`; when the copied value is at least `1.0F`, it calls `moveEntities()` before removing the block entity and replacing the moving block when applicable. Otherwise it adds `0.5F`, clamps at `1.0F`, then calls `moveEntities()`. This order is identical in both releases (lines 164–182).

`moveEntities()` obtains entities for the current moving shape box, chooses `facing` while extending and the opposite direction while retracting, skips entities whose piston move behavior is `IGNORE`, and for slime sets the velocity component on the movement axis to that direction's offset before requesting the axis displacement plus `0.01` through `entity.move(...)`. The selection, gates, axis assignment, displacement calculation/order and move request are byte-identical in both releases (lines 90–147).

Difference: none in the assigned direct ordinary-player moving-piston callback, ticking/admission, entity selection, slime axis velocity, displacement request, or extension/retraction/progress timing. This is a bounded source equivalence only; moved-state geometry, query-overlap semantics, world availability, collision providers/support and clipping were held out of review as assigned. No final movement/parity inference is made.