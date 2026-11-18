# Block-contact block-cell traversal

1.21.3, Mojmap: `net/minecraft/world/level/BlockGetter.java`, `boxTraverseBlocks` and `addCollisionsAlongTravel` (lines 172–end).
1.21.4, Mojmap: `net/minecraft/world/level/BlockGetter.java`, `boxTraverseBlocks` and `addCollisionsAlongTravel` (lines 172–end).

Difference: For movement whose squared displacement reaches the existing long-path threshold, 1.21.3 computes `epsilon = normalize(to - from) * 1.0E-7`, offsets the AABB minimum by epsilon for one sweep endpoint, and offsets the inverse-displacement endpoint by negative epsilon before calling `addCollisionsAlongTravel`. 1.21.4 uses the unadjusted AABB minimum and its subtraction by `to - from` as the two endpoints. The short-path threshold, ordered set, endpoint box enumeration, and entire `addCollisionsAlongTravel` body are unchanged; the helper body was compared byte-for-byte after its declaration. The paired `AABB.java` source hashes are identical.

Movement meaning: The set/order of candidate block cells produced for a swept entity box can differ at the path endpoints because the tiny endpoint bias is absent in 1.21.4. `Entity.checkInsideBlocks` consumes this iterable for each stored movement segment before applying its callback gates. This finding concerns candidate-cell traversal only; it does not assert a solid-geometry or trajectory outcome.