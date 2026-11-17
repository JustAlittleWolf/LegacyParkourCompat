# Source audit verdict: base collision dispatch (1.12.2 → 1.13.2)

**Verdict: ACCEPTED, hash-bound to the reviewed coverage report.**

- Reviewed report SHA-256: `2199f933da3c36f38bdffe942dac095e66c5948b3af9654adc06bbf1af9fef04` (`build/source-residual-20261116/1.12.2--1.13.2/base_collision_dispatch/coverage.md`).
- Exact source identity verified from each ready marker and the corresponding manifest SHA-256: 1.12.2 Ornithe Feather Gen2 build 2, `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; 1.13.2 Ornithe Feather Gen2 build 2, `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.
- Every Java-file hash listed in the report matches both the local source manifest entry and the file bytes.

The bounded source claim is supported. In 1.12.2, `World.getCollisions` visits candidate blocks and dispatches through `BlockState.addCollisions`; base `Block.addCollisions` translates the base collision box and appends it when it intersects the query. In 1.13.2, ordinary `Entity.move` asks `World.getCollisions` for the current shape and requested deltas; the inherited living-player movement path reaches this method. The swept block query subtracts the current shape (with its epsilon adjustment), and `getBlockCollisions` drops a candidate whose local collision shape overlaps `excludeBlock`. Thus the candidate filters differ for an inherited base-shape block, without establishing a particular final displacement. The old base shape is the unit `Box`; the new base shape is `VoxelShapes.block()`. The new base collision dispatch gates that shape on `hasCollision`; the property defaults true, while a false value returns empty before consulting the state shape.

The report correctly leaves two dependencies open: the downstream clipping/candidate-consumption comparison needed to conclude displacement, and joining registered legacy block keys/state-property construction to the new false gate and old specialized collision providers. This verdict accepts only the stated bounded source findings and those explicit limits; it does not close full-pair coverage or runtime parity.
