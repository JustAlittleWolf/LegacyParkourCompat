# Cobweb collision-provider packet audit

## Input identity

- Audited `build/source-residual-20261116/1.12.2--1.13.2/cobweb_shape_provider/report.md`, SHA-256 `32587f443a71973a516ed850c06653aeb7d30581d6b228e3a1387051c6a65e00`; the file hash matches the assigned identity.
- Both ready markers identify Ornithe Feather Gen2 build 2. The actual `ornithe-feather.sources.sha256` files hash to the report's manifest identities: 1.12.2 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.
- Every source-file hash listed in the report was checked against both its canonical source bytes and the corresponding manifest entry; all match. The provider-roster report hash also matches `524e27cf0618ea8942a961f27835fabf7091248a0629b9f03f4bd4d39c05a979`.

## Audit verdict

**PASS for the bounded CobwebBlock collision-provider slice.** The comparison and cited dispatch paths are source-supported:

- In 1.12.2, `CobwebBlock.getCollisionShape` unconditionally returns `EMPTY_BLOCK_SHAPE`; `Block.EMPTY_BLOCK_SHAPE` is `null`. State dispatch reaches the block provider, and base `Block.addCollisions` adds no box for that sentinel.
- In 1.13.2, `CobwebBlock` declares no collision-shape override. `Block.java:920` registers the instance with `Material.COBWEB` and `.noCollision()`. That property clears `hasCollision`, which the constructor copies. The inherited `Block.getCollisionShape` returns `VoxelShapes.empty()` when the flag is false; it does not call the state shape fallback.
- Ordinary player movement reaches these providers: `PlayerEntity` extends `LivingEntity`, whose inherited `Entity.move` uses the world collision query. The old `World.getCollisions` scan dispatches candidate states to `addCollisions`; the 1.13.2 query delegates to `WorldAccess`/`WorldView.getBlockCollisions`, which gets each state collision shape and filters empty shapes.

Thus the provider result is empty on both sides for this registered cobweb along the ordinary movement collision path. No cobweb collision-geometry difference is supported by this packet; no finding is warranted.

## Gates retained

This verdict closes only the class-level CobwebBlock shape-provider comparison and its ordinary player movement reachability. It does not audit the cobweb callback/velocity response, other provider families, registry alias closure, full tick inventory, independent pair coverage or 1.12.2–1.13.2 pair freeze. The callback is explicitly outside this packet.