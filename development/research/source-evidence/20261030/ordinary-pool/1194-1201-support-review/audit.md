# Bounded source audit: 1.19.4 → 1.20.1 support query

## Verdict

The reviewed closure's claims in scope are supported by the canonical Mojmap sources. No false claim found in the changed support selection path, 1.20/1.20.1 equality, or FarmBlock predicate reachability. This is a bounded method audit, not full pair coverage.

## Findings

- **Support selection:** 1.19.4 has no `CollisionGetter.findSupportingBlock`; its entity support-affecting position is `BlockPos.containing(position.x, boundingBox.minY - 0.5000001, position.z)`. In 1.20, grounded state invokes `checkSupportingBlock`, which scans the feet-thin AABB through `findSupportingBlock`. That selector enumerates `BlockCollisions` candidates, minimizes squared distance to entity position, and applies a deterministic `BlockPos` tie-break. Candidate inclusion is based on the block's context collision shape intersecting the entity AABB (with the full-block fast path). No solidity, motion-blocking, face-sturdiness, or support-type predicate appears in this selector path.
- **Version equality:** all seven closure-listed files have identical SHA-256 in 1.20 and 1.20.1. The hashes also match the closure's listed hashes for 1.19.4 and 1.20.
- **FarmBlock:** the predicate changed from `!above.getMaterial().isSolid()` to `!above.isSolid()`, retaining fence-gate and moving-piston exceptions. `canSurvive` is called by placement, upward-neighbor update scheduling, and the block tick. It is not called by `Entity.checkSupportingBlock`, `CollisionGetter.findSupportingBlock`, `BlockCollisions.computeNext`, or the collision-shape path. With the received block states held equal, FarmBlock's `getShape` returns its unchanged `SHAPE`; the predicate replacement does not gate player collision/support enumeration. A survival-driven world-state transition can change which block is later queried, but that is outside this equal-state conclusion.

## Remaining gates

The closure itself correctly leaves other era-block collision/support shape providers, collision contexts, neighboring-state effects, fluids, registrations, and external callbacks open. Its conclusion should remain limited to selector mechanics and the FarmBlock candidate; it does not establish complete pair coverage or movement parity.

## Immutable inputs and hashes

Reviewed report SHA-256: `DCFEB38DEEE9F8884784607249909CBBC9F7884483511B24D0769F49BB5E62CF` (`support-query-closure.md`). Hashes below were read directly from the canonical source files; 1.20 and 1.20.1 values match row-by-row.

| Mojmap source path | 1.19.4 SHA-256 | 1.20 = 1.20.1 SHA-256 |
| --- | --- | --- |
| `net/minecraft/world/entity/Entity.java` | `3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b` | `94b9c3656715de2d61fa9a02ccef164261e50eb13d6090a6f07e58fe9c5b0759` |
| `net/minecraft/world/level/CollisionGetter.java` | `ed62e6b800ec057c1269d3cc4e2fb7c0cc2037370223fac99f41371e000c6b5c` | `8e4863afe40705497b36d0a58d8ee11e5103f4e72cb642aa63cc57f57649a130` |
| `net/minecraft/world/level/BlockCollisions.java` | `11fecadc012d0a544097bf0febfa1a83da5a534e6182ee7df7b2334b6ee80127` | `3313a38f9ebbd389f62e95dcbedab6a4f7f3cd3920b9ffa0ecf1a77dd869725b` |
| `net/minecraft/world/level/block/FarmBlock.java` | `8eeeb823ec6ddbd0aa5a96809399aab4a57da2d30eefde9e684883d1379c3b1a` | `4713ba2c1b536ff8e48390563e4af10c82ca42ec6d5ba79db5a9bf10f0d1c957` |
| `net/minecraft/world/level/block/Block.java` | `440b55e32f468572b4c078ab0ce5bb9b94f1c7abcaa03b03a9ce7faccb646857` | `0c2df3d958078929c4cc2985e0d6a6a766cbbbb02066e6c9ac471981aa858656` |
| `net/minecraft/world/level/block/Blocks.java` | `a4f2df87c2cae5a3827a818f39f7ad123ee5b18905ead9947efe654421c30ff5` | `1e7095d1fbfdc8198191f40037997feeca2ec993cc54dd53f2636375cae66b51` |
| `net/minecraft/world/level/block/state/BlockBehaviour.java` | `517aa4bd9875e9f7b3f5fba472a84e82afd1e11e35d3c83dc2677d8bbeb4997d` | `5f37987191165379c222c412949a0c80a904bf78a453035b1f9d86c6454510fa` |
