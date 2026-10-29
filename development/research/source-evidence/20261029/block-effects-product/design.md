# Neutral candidate: movement segment and callback lifecycle for 1.21.4–1.21.5

## Candidate contract

Implement the bounded source operation that supplies player block-effect callback segments and consumes them in source order. Keep segment capture, segment traversal, visited-position admission, and deferred effect collection as distinct inputs/steps. Do not transplant the full Entity movement loop or infer a trajectory from the source conditions.

The candidate describes only the exact 1.21.4 and 1.21.5 operation shapes. The 1.21.6 boundary begins a different record model and is outside this candidate. Earlier patch applicability is not established here; an integrator must resolve it with independently accepted boundaries and the closest-later resolver, never an exact-profile runtime gate.

## Version contract

| Source profile | Segment source | Drain behavior | Callback/effect behavior |
| --- | --- | --- | --- |
| 1.21.4 | Entity.move does not append accepted movement to the callback list. Each applyEffectsFromBlocks() call supplies one segment from oldPosition() to position(). | The two-position overload appends that segment to the list, snapshots it, clears live state before callbacks, then scans the snapshot. | checkInsideBlocks consumes a block position once when it is first admitted, before its block-shape test. A miss still consumes it for the pass. This method has no fluid callback test. |
| 1.21.5 | Every accepted physical Entity.move constructs one ordered batch of axis-aligned subsegments from the collision-resolved displacement, using Direction.axisStepOrder(resolvedDisplacement), and appends the batch before setPos. | The no-argument drain flattens batches and clears live state before callbacks. If the flattened list is empty, add (oldPosition(), position()). Otherwise append a residual last-endpoint-to-current-position segment only when squared distance is greater than 9.9999994E-11F. | One shared visited set is updated for non-air positions before shape and fluid tests. Shape and fluid tests are independent; the block or fluid callback runs only when its respective gate passes. A miss on both tests still consumes the position for the whole pass. The staged collector advances its step immediately before each passing callback and flushes queued effects on step changes. |

The 1.21.5 physical-move record gate is exactly:

    resolved.lengthSqr() > 1.0E-7
        || requested.lengthSqr() - resolved.lengthSqr() < 1.0E-7

The no-physics early return and a zero result from piston-motion limiting do not append a record. Preserve this distinction from the fallback segment at drain time.

## Minimal neutral data and operation

A minimal adapter needs:

- An ordered segment value with from and to positions. Coordinates are the exact source Vec3 values; do not round or normalize them.
- For 1.21.5 capture only: the resolved displacement used to form ordered axis subsegments, the requested displacement used by the record gate, whether the move took the no-physics early return, and whether piston limiting returned zero.
- A per-pass mutable ordered segment batch/list and a single per-pass visited-position set.
- For 1.21.5 callback staging only: the visitor step for each passing shape/fluid test and the existing InsideBlockEffectApplier operation order.

The scan consumes segments in list order and preserves the source traversal order for each segment. It clears the visited-position set only after the complete list has been processed. Do not retry a position in 1.21.4/.5 after a prior segment consumed it, even if all applicable tests missed.

For 1.21.5, before each passing block or fluid callback, advance the collector to that visitor step. When a step changes, flush each InsideBlockEffectType in enum order, with its runBefore consumers, coalesced apply effect, then runAfter consumers. After scanning, flush the final step, apply collected final effects in recorded order while the entity remains alive, clear them, and reset the collector step. Direct callback code remains synchronous; only operations submitted to the collector are deferred.

The client operation remains under the source gate: affected, non-removed, physics-enabled, locally controlled player. Ordinary local-player callbacks run after travel. Client piston handling has a version-specific call shape:

- In 1.21.4, the piston helper performs the move and then calls the no-argument apply method. That method supplies `(oldPosition(), position())`; the source does not capture a pre-push position.
- In 1.21.5, the piston helper captures the current position, performs the piston move, immediately scans the explicit pre/post positions, and removes that move's batch so the later ordinary drain does not replay it.

Do not replay server packet paths, non-player physics, or vehicle physics.

Source anchors for implementation comments: Mojmap 1.21.4 and 1.21.5 `Entity.move`, `Entity.applyEffectsFromBlocks`, and `Entity.checkInsideBlocks`; 1.21.5 `Entity.addMovementThisTick` and `InsideBlockEffectApplier.StepBasedCollector`; both releases `LivingEntity.tick`; and `PistonMovingBlockEntity.moveCollidedEntities`.

## Traversal dependency and limits

Use the accepted swept-AABB/voxel traversal contract as the only source for candidate enumeration, clipping, tie order, epsilon, and integer-boundary rules. This candidate does not supply a replacement traversal implementation or simplify it to raw AABB intersection.

The 1.21.6 record model (a capped deque, oldest-record merge, and an axis-independent flag) is a separate cutoff and is not described as a continuation of the 1.21.5 nested-batch format. No behavior for 1.21.6+ is implemented by this candidate.

## Accepted source inputs

- 1.21.3→1.21.4 callback caller report: .native-source/20261028/frontier-c/1213-1214-block-segment-callback-closure/report.md SHA-256 11c1e42f76911b4f527e3dc360e24834d81ea15374a12f6e41fc90a25a5b0c50; independent audit .../1213-1214-block-segment-callback-audit/audit.md SHA-256 66772dbc839f21fa0243b5f3e87f40be2726bd35916a53fadda62c64fcd0651f.
- 1.21.4–1.21.9 lifecycle report .native-source/20261028/frontier-c/1214-1219-segment-recording-lifecycle/report.md SHA-256 C4468295B043B2457E561C49D2C741D7C783F80A766EC303390CB6D889651DB6; audit .../1214-1219-segment-recording-lifecycle-audit/audit.md SHA-256 CC9DEA88864E9A62A552F9C04AD05F7F5C9C4EB6F1DDB7609861AFA293CBF400; accepted correction supplement SHA-256 54C53D4AF8C0602E7DB6B50232F34FD404ABAEE635F36BB24138A9C1E3A6133B; independent correction review SHA-256 14EB9BB2C079BC7D2AD141AD67DF006D8B99A50A79637C365E03D20BC203D5ED.
- 1.21.4–1.21.9 visited-block admission report .native-source/20261028/frontier-c/1214-1219-visited-block-admission/report.md SHA-256 C43EC8E8B47EDEAE326D5A68F5A9D006492781C12B091A562585778477EA4261; independent audit .../1214-1219-visited-block-admission-audit/audit.md SHA-256 8E9DE3F919E978B12F7A58DC05137C858A7591390A2E413794DA5F3A8A9AFD6A.
- 1.21.4–1.21.9 swept-AABB/voxel traversal report .native-source/20261028/frontier-c/1214-1219-swept-aabb-contract/report.md SHA-256 5B7ED6EE05DDA849182D8201EC90FAB8A6B23FEAB3A860BBDFB73ED504C3B25F; independent audit .../swept-aabb-contract-audit/audit.md SHA-256 4AB7F818896302A85F9E30940BB01821FE670FFBA79FF2089907EE4068C7B800; accepted correction supplement SHA-256 1A61B3770F0031380BE1DF36A01C820126A391C07E93DCD1FF6AA4340D0E91EF; independent supplement review SHA-256 0EA9A493DA0CF2F6DC245C7E45A0C4898359113B1BF4560F60B8A1CD33AFB864.

This candidate claims exact source-operation behavior within the cited releases only. It does not claim that every traversal produces a callback, that a particular player trajectory differs, complete pair coverage, or runtime parity.
