# Independent bounded verdict — revision 1

Disposition: ACCEPTED_BOUNDED_SOURCE_DIFFERENCE

## Accepted source difference

For the frozen piston-to-entity environmental-contact operation, the author comparison's main source claim is independently confirmed. In 1.21.3, `PistonMovingBlockEntity.moveEntityByPiston` calls `Entity.move(MoverType.PISTON, ...)`, then the no-argument `applyEffectsFromBlocks()` (`PistonMovingBlockEntity.java:182-186`). That no-argument method supplies `oldPosition()` and current `position` (`Entity.java:736-738`); the explicit implementation appends this span to `movementThisTick`, scans it, and clears `movementThisTick` and `blocksInside` (`Entity.java:740-752`).

In 26.2, `PistonMovingBlockEntity.moveEntityByPiston` snapshots `previousPos = entity.position()` immediately before `move`, applies block effects with `(previousPos, entity.position())`, and then calls `removeLatestMovementRecording()` (`PistonMovingBlockEntity.java:192-199`). The explicit overload wraps that pair in a one-element list (`Entity.java:945-947`). The separate no-argument overload copies `movementThisTick` into `finalMovementsThisTick` and clears the live queue (`Entity.java:903-914`); the piston caller does not use that overload.

This confirms the source-level change in callback input: old-position-to-post-move in 1.21.3 versus pre-piston-snapshot-to-post-move in 26.2. The span values are not necessarily different on every invocation. The difference is realized when the accessor's stored `xOld/yOld/zOld` position differs from the current position sampled before piston movement. Accordingly, accept the distinct source expressions and their conditional state witness; do not state that the spans always differ or call the old accessor a proven tick-start coordinate based on this bounded roster alone.

## Movement gates and record removal qualification

Both versions' `Entity.move` has a `noPhysics` branch that sets position without entering the ordinary piston limiter path. When `noPhysics` is false, piston movement is limited; an exact `Vec3.ZERO` result returns from `move` before position mutation (`1.21.3 Entity.java:619-629`; `26.2 Entity.java:711-724`). The piston helper proceeds after that return. The block-effects implementation is gated by `isAffectedByBlocks()`, which in both versions is `!isRemoved() && !noPhysics` (`1.21.3 Entity.java:769-771`; `26.2 Entity.java:977-979`).

In 26.2, movement records are added only in the non-noPhysics position-update path and under its movement acceptance condition (`Entity.java:711-755`). The Piston caller invokes tail removal unconditionally after the explicit callback. `removeLatestMovementRecording()` removes the tail only if the deque is nonempty (`Entity.java:931-935`). Thus, when a piston move added a record, it removes that piston record. If the move added no record and a prior record is queued, it removes that prior tail instead. The latter is conditional on the queue's state; it is not a universal effect. The noPhysics case additionally suppresses block-effect processing at the gate while still reaching the caller's tail-removal statement.

## Qualifications and scope

This verdict accepts only the bounded source difference above. The author comparison's broader assertions about `LivingEntity.aiStep` call sites and the complete `recordMovementThroughBlocks` caller inventory were outside the frozen roster and were not independently audited here. No claim is made about actual tick scheduling, whether the queue is nonempty at a particular piston invocation, block-specific results, full-pair coverage/freeze, implementation, or runtime parity.

## Immutable evidence binding

- Author comparison raw-byte SHA-256: `c3069fcd2e4091a418d1e790c9f91580f23e011abd7216a03c30247a2c1c5492` (7,758 bytes; matches the corrected authorized digest).
- Frozen roster SHA-256: `0b6a373ec80f846ffce6370ea19cf3bb1245b8ea2f48c63e2ed2e0ef2e1614c1`.
- Preserved initial viability verdict SHA-256: `d78c3833368d08c63fc0d99f6d49c405e77ec31c141f98e032315bc16af94b3e`.
- Source-root ready/provenance/source-manifest identities are recorded in the preserved initial verdict; both roots were ready and all manifest entries were checked against source-file SHA-256 before source inspection.

Frozen-roster source-file hashes:
- 1.21.3 mojmap `net/minecraft/world/level/block/piston/PistonMovingBlockEntity.java`: `426442149f89a28fc5973a52b8156aec7c18d76faf8b62eecae9f120b2fdba16` (matches verified source manifest).
- 1.21.3 mojmap `net/minecraft/world/entity/Entity.java`: `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9` (matches verified source manifest).
- 1.21.3 mojmap `net/minecraft/client/player/LocalPlayer.java`: `fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0` (matches verified source manifest).
- 26.2 unobfuscated `net/minecraft/world/level/block/piston/PistonMovingBlockEntity.java`: `706bab1a13ba99bbd334aad853b99f72df58fce5b6b67d30d41f902b63dd8e06` (matches verified source manifest).
- 26.2 unobfuscated `net/minecraft/world/entity/Entity.java`: `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5` (matches verified source manifest).
- 26.2 unobfuscated `net/minecraft/client/player/LocalPlayer.java`: `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6` (matches verified source manifest).
