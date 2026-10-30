# Entity-query order dependency follow-up: 1.8.1 → 1.8.2

This bounded source-only follow-up checks how the `TypeInstanceMultiMap` implementation change reaches ordinary local-player collision movement. It is not full pair closure.

## Exact source identity and hashes

The canonical read-only roots are the Ornithe Feather Gen2 `1.8.1+build.2` and `1.8.2+build.2` trees. I recomputed and compared each file below against its own `.decompile-provenance.json` `sources` entry; all matched.

| Source | 1.8.1 SHA-256 | 1.8.2 SHA-256 |
|---|---|---|
| `net/minecraft/util/TypeInstanceMultiMap.java` | `3ca1aa252d4ecb4eb9727bb3ddd30032925247a81f9f74136b4cb65eb902582a` | `4f3f923f19f15e84b96a65ca77d71fac7f3f2ca4abb64c061ce8736eb14ead08` |
| `net/minecraft/world/chunk/WorldChunk.java` | `549fe685b5405708b7f8059535783adbe28808daff883747961caa42fa41c43a` | `a658562b5df4a209e802d8bf25033c8399599a4d4652ddb012e32937e6cd4ca2` |
| `net/minecraft/world/World.java` | `f0873207c5c7a793189597470392fa0184513b3c5a682a6f8795e1c9ffde9f71` | `3c04f5b874fbb6692039164c882a3c47fe28c7eca864e91c9ac57798153888ee` |
| `net/minecraft/entity/Entity.java` | `aa359621cf9348e5ce4bcb9e28b67f8a5c75d7e5a3d554c8d93471c79540a55c` | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` |
| `net/minecraft/entity/living/LivingEntity.java` | `fbfb0f0821a2d6b9d1d665387b2c5423fc0c1d8f5f36f7b5e1258b26d90c4c4f` | `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e` |

`Box.java` was also hash-verified in the paired collision slice report.

## Source difference and route

- `TypeInstanceMultiMap` 1.8.1 stores base-type members in Guava `HashMultimap` (`iterator()` at lines 76–78). In 1.8.2 it stores them in an `ArrayList` (`iterator()` at lines 107–112). This changes member enumeration order; the source directly shows the former unordered set-backed collection and latter insertion-ordered list.
- `WorldChunk.getEntities(Entity, Box, List, Predicate)` traverses its section's `TypeInstanceMultiMap` (1.8.1 lines 700–725; 1.8.2 lines 710–737). `World.getCollisions(Entity, Box)` obtains the nearby candidates through `World.getEntities` and appends each candidate's non-null collision shape and entity-specific collision-against shape (1.8.1 lines 873–927; 1.8.2 lines 891–945). Thus entity iteration order can reach the ordered collision list used by `Entity.move`.
- `Entity.move` visits collision boxes in sequence and feeds each to `Box.intersectY`, `intersectX`, and `intersectZ`. The paired Box bodies (lines 93–144) update a positive delta only when a smaller positive separation is found and a negative delta only when a larger negative separation is found. For ordinary finite AABBs this reduces the same independent constraints by minimum/maximum; no per-box movement state is written. The inspection therefore found no displacement/position change from permuting these entity boxes.
- The added `WorldChunk` `isEmpty()` guard (1.8.2 line 717) skips the iterator when the exact collection has size zero; `TypeInstanceMultiMap.size()` reads the base-member list size on each version. On an empty section the old loop also has zero body executions.
- The other directly identified sequential consumer is `LivingEntity.pushAwayCollidingEntities`, which iterates `World.getEntities` and calls `pushAway`. Its caller in `LivingEntity.mobTick` is inside `if (!this.world.isClient)` in both releases. That path is server-side and excluded from historical client movement under the project invariant. Other entity-query call sites for vehicles/non-player behavior remain out of this player-only follow-up.

## Disposition

The storage/iteration implementation is a real source change and changes entity candidate order. For the checked local-player world-collision path, the movement solver consumes finite collision boxes as independent axis constraints, and the empty-section guard only elides an empty iteration; neither produced a movement difference in this source trace. The sequential living-entity push path is client-gated out. This closes only the identified order dependency for the stated player collision route; it does not close unrelated `getEntities` call sites or complete the independent pair audit.
