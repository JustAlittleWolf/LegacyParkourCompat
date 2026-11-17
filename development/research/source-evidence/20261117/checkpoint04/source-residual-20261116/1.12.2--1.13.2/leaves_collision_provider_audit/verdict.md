# Independent audit: leaves collision provider, 1.12.2 → 1.13.2

## Verdict

**ACCEPT** for the report’s bounded registered leaves provider/state-domain claim. No corrections are required. This verdict is bound to current report SHA-256 `2dd4ba32a887fcf3a4cca6952a821d91f0f7048eba47585270695c8001d0d21e`.

## Identity and evidence checks

- Exact source manifests and ready markers agree with 1.12.2 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` and 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.
- Recomputed each of the report’s 16 cited source-file hashes. Every local Java file matched its stated SHA-256 and corresponding manifest entry, including the added `LivingEntity.java` and `PlayerEntity.java` bindings.
- Checked old registry IDs 18 (`leaves`) and 161 (`leaves2`) and their effective class inheritance. The old leaves state manager combines the class-filtered variant with `CHECK_DECAY` and `DECAYABLE`; metadata derives those values from the encoded variant and two flags. Both classes inherit `AbstractLeavesBlock` without a shape/collision override. `Block.getShape` returns the full unit box, `getCollisionShape` delegates to the state shape, and `addCollisions` submits that collision shape.
- Checked all six target registrations (`oak_leaves`, `spruce_leaves`, `birch_leaves`, `jungle_leaves`, `acacia_leaves`, `dark_oak_leaves`). Each constructs `LeavesBlock` without `noCollision`. Its complete declared state domain is `DISTANCE=1..7` × `PERSISTENT=false/true`; the class declares no shape or collision override. `Block.Properties.hasCollision` defaults to true, base `Block.getShape` supplies the full unit voxel shape, and `Block.getCollisionShape` uses it when collision is enabled.
- Verified `PlayerEntity` extends `LivingEntity`, and `LivingEntity` extends `Entity` in both releases. The cited `Entity.move` methods query the corresponding world block-collision route; the listed world/provider dispatch supports the report’s ordinary player reachability statement.

## Limits retained

The audit accepts only the bounded provider/state-domain result. It does not prove a complete old-to-target block-state migration path or runtime applicability, resource membership, wall-specific face predicates, or any provider outside the listed leaves classes. Pair-wide source coverage remains partial.
