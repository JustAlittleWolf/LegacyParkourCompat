# Independent source audit verdict: End Rod collision provider

**Verdict: ACCEPT**

Audited `build/source-residual-20261116/1.12.2--1.13.2/end_rod_collision_provider/report.md`; its bytes recompute to the assigned SHA-256 `6e9b6b77ce4ea0e7d9549fb636292c1370ac13002c84d6e1b186d9830fe53eeb`.

The exact Feather Gen2 source-manifest bytes recompute to the assigned identities: 1.12.2 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`, and 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`. Their ready records identify the matching release and `ornithe-feather` mapping. All 14 source-file digests listed in the report (7 per endpoint) match both current source bytes and their manifest rows.

I independently checked the bounded claims against the exact sources:

- Both `Block.java` registries bind the exact `end_rod` key to `EndRodBlock`; the old registration is numeric ID 198. The cited registry crosswalk’s own bytes match its stated SHA-256.
- The End Rod state definitions contain only `FACING`. The old no-filter `DirectionProperty.of("facing")` includes every `Direction` enum member; the newer shared `Properties.FACING` explicitly lists NORTH, EAST, SOUTH, WEST, UP and DOWN. Both releases therefore expose the same six directions, with UP as the default.
- The old shape constants are 4/16-thick axis-aligned boxes; the newer 0–16 voxel bounds normalize to the same boxes. Both `getShape` bodies switch on the same axis and return X, Z or Y geometry respectively. No world or position input affects those bodies.
- The old `Block.addCollisions` queries state collision shape, whose base block implementation delegates to state shape; state delegates to `EndRodBlock.getShape`. The newer state collision query delegates to base `Block.getCollisionShape`, which returns `state.getShape` when `hasCollision` is true and empty otherwise. The `end_rod` registration uses default properties without `.noCollision()`; `hasCollision` defaults true and is copied into the constructed block.
- Both End Rod face-shape overrides unconditionally return `FaceShape.UNDEFINED`; the newer state dispatch delegates to that override.

No correction is required. Acceptance is limited to this exact provider/state comparison: it does not establish parity of downstream movement collision-consumer math, neighboring-block predicates, or any other collision providers, and does not imply pair freeze or runtime parity.
