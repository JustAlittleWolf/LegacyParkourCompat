# Ordinary edge backoff from 1.21.1 through 1.21.4

Exact Mojmap 1.21.1 to 1.21.2, 1.21.2 to 1.21.3, and 1.21.3 to 1.21.4 preserve the bounded local crouch edge-backoff operation. A nonflying, non-riding player must have a nonpositive requested Y, a SELF or PLAYER move, shift held, and pass the above-ground gate. For a grounded state the gate passes directly. The airborne branch requires fall distance below step height and a support hit in the zero-offset probe. In both cases, X-only, then Z-only, then joint projections use the same signed 0.05 decrements and small-component zeroing. Requested Y is retained. Each support box has un-inset X/Z and Y bounds from feet minus float step height minus 1.0E-5F through feet.

The loaded, entity-clear, border-clear examples use an ordinary full cube or a dry bottom slab. The 1.21.2 noCollision overload/context refactor retains the same ordinary block query and context. Canonical full-cube providers take the unit-box overlap path. The bottom slab supplies the same half-height shape and uses nonempty AND intersection. This closes the named source operation and provider examples across the three edges. It leaves custom providers, unloaded behavior, subsequent collision and step resolution, and runtime parity open. The earlier 1.20.6 to 1.21.1 edge is outside this record.

The [separate position-write finding](../../../../findings/1.21.1--1.21.3/move-tiny-position-write.md) covers the downstream change after collide. Its integration is d305ddd14a1fa1ec9665da4adc73909c511b5154. It has no effect on the scope of this source closure.

## Provenance correction for 1.21.4

The second author report overstates full-file equality for BlockBehaviour.java. The verified 1.21.3 file SHA-256 is 4d92417129eb6def084a67d249d2ff8c7a30a4767fa46e63a045667ea70d5133. The verified 1.21.4 file SHA-256 is a549c5dcf7768f89f18519bdd2f985ef498d1bd4f6b329a30e0088e437819ddf. Both match their own source manifests. The relevant base and state getCollisionShape methods are byte-identical in the checked 1.21.2, 1.21.3 and 1.21.4 files.

- [1.21.1 to 1.21.2 author report](1211-1212-report.md), SHA-256 34abd73e9fcdf50e943f726e5bcdf0e3f9da3a111bd619e0ddaeda2feea900f7.
- [1.21.1 to 1.21.2 independent review](1211-1212-verdict.md), SHA-256 14ae177827dcdf9f237048e3a458309fda3ea7b8e6a74cc66742b8faa0362369.
- [1.21.2 through 1.21.4 author report](1212-1214-report.md), SHA-256 5c555dd4628f2678784834efcd6f09f70f4653cc5429ba630745dd77dc04b132.
- [1.21.2 through 1.21.4 independent review](1212-1214-verdict.md), SHA-256 f0b535d8fab9c499a89ad16f4944728fcdd5f64ab060a637c0fe8e11a8082727.
