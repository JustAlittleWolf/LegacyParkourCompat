# Correction supplement: ground-friction phase claim

**Original report SHA-256:** `ce710a4f05685bef29b908b0fae9f2462f5b12d313e9f963e09ec6fa94b55bca` (`source-review.md`). The original report bytes are preserved.

## Retraction

Retract candidate difference 3, which claimed that 1.13.2 samples ground slipperiness after movement while 1.14.2 samples it before movement. That claim is incorrect.

In 1.13.2 Feather, `LivingEntity.moveRelative()` samples the slipperiness at `(x, getShape().minY - 1, z)` at lines 1540–1543, computes acceleration and calls `updateVelocity` at lines 1545–1553, then resets the mutable position to `(x, getShape().minY - 1, z)` and samples again at lines 1554–1558. The `move(...)` call is later at line 1575. Both slipperiness samples therefore occur before that move; the second sample uses the still-unmoved `x/z` because no position-changing call precedes it.

In 1.14.2 Yarn, `LivingEntity.travel()` samples the block at `(x, boundingBox.minY - 1, z)` and derives `w` at lines 1824–1826, calls `updateVelocity` at line 1827, then calls `move(...)` at line 1829. Its sample also occurs before movement.

Exact on-disk Java hashes, each matching its declared source-map entry: 1.13.2 `net/minecraft/entity/living/LivingEntity.java` SHA-256 `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`; 1.14.2 `net/minecraft/entity/LivingEntity.java` SHA-256 `e8224e7df98ccf0986bb390f35e64293263d17b9ab1dbdfeb661c65d28b6ee78`.

## Still unproven

The ground-acceleration coefficient expressions differ syntactically and are not established equivalent by this review. In 1.13.2, the grounded path derives `t = slipperiness * 0.91F`, then uses `getSpeed() * (0.16277137F / (t * t * t))` (`LivingEntity.java:1538–1548`). In 1.14.2, it passes slipperiness `v` to `method_18802(v)` (`LivingEntity.java:1824–1827`), whose grounded expression is `getMovementSpeed() * (0.21600002F / (v * v * v))` (`LivingEntity.java:1944–1946`). Exact float operation order, the returned speed providers and algebraic equivalence remain unproven. The old/new friction-sampling phase is not a movement finding.

This supplement corrects only the retracted phase claim. Other candidates remain bounded to their stated source gates; collision outputs, provider equivalence and the reachability of collision-dependent branches remain open. No pair-coverage, acceptance or runtime-parity claim is made.
