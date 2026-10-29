# Partial-shape sweep candidate review

## Verdict: ACCEPT — neutral helper scope only

This accepts the sanitized neutral helper for source math, API shape, version boundary, and fallback behavior. It is not a runnable product patch; call-site, mixin, and runtime integration review belongs to the later product-wiring phase.

### Inputs verified

- Candidate `.native-candidates/partial-sweep-1212-20261029`: exact `HEAD` is `3235831862f95ec3357f2e7b626d23a0e594672c`.
- Accepted report `.native-source/20261029/partial-sweep-1212-alias-closure/report.md`: SHA-256 `554fca52d724e8133ec090f8d1b28f3daea443c926682df21b62e133d0816ab4` (matches requested).
- Independent audit `.native-source/20261029/partial-sweep-1212-alias-audit/audit.md`: SHA-256 `601e1ec94848d2651ceecb8249f08fe3b138c476e2103c5a7fc5410ca72aa34e` (matches requested); bounded source closure is accepted.
- Assigned finding `.native-candidates/partial-sweep-1212-20261029/findings/movement-partial-shape-sweep.md`: SHA-256 `2d8828000d6ce8570dd378e243d6d7fdc4457cc13471eab8f93a6fe3fa6849b3`.

### Review

The arithmetic in `change/v1_21_2/PartialShapeSweep.java` matches the accepted 1.21.2/1.21.3 Mojmap calculation: recenter the final box by its own center, place it at `to`, then return `from - recenteredBox.bottomCenter`. The geometry does not add traversal or shape-intersection behavior.

The hook signature `(AABB finalEntityBox, Vec3 from, Vec3 to) -> Optional<Vec3>` carries the inputs needed for the historical vector and gives the caller an explicit native-fallback result. The narrow hook appropriately delegates candidate enumeration, the existing partial-shape gate, and `AABB.collidedAlongVector` to its caller. The empty result in `change/v1_21/NativePartialShapeSweep.java` preserves native behavior for selections through 1.21.1. `@MovementChange(V1_21_2)` covers 1.21.2/1.21.3, which the accepted report establishes as byte-identical for this operation. 1.21.4 remains on its native/current forward-sweep behavior, consistent with the accepted source boundary. `MovementRuntime.find` is client-player gated; the pure historical change class itself adds no extra gates.

The candidate intentionally contains no product call site or mixin dispatch. Its empty client mixin list is consistent with the sanitized neutral-export phase and is not a defect in this helper-only review. Call-site, mixin, and runtime integration correctness remain for the separate product-wiring review.

The historical source comment accurately names Mojmap and `Entity.collidedWithShapeMovingFrom` for 1.21.2/1.21.3. Product wiring should document the corresponding 1.21.4 source boundary when that phase adds the caller integration.

### Scope

Reviewed only the sanitized candidate at the verified commit and the three assigned evidence files. Did not inspect product implementation or history. No build, test, or runtime command was run. The candidate was not edited; this report is the requested output.
