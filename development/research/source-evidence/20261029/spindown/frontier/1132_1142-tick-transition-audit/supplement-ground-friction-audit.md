# Independent audit supplement: ground-friction phase correction

**Audited supplement:** `.native-source/20261028/frontier-c/1132_1142-tick-transition/correction-supplement-ground-friction.md`
**Supplement SHA-256:** `3b55bbef191c687a90f71a31c182763bb0de8cfc9b60c6153c3634eca0f3e3e4`
**Original source report SHA-256:** `ce710a4f05685bef29b908b0fae9f2462f5b12d313e9f963e09ec6fa94b55bca`

## Verdict

**Accept the correction and retraction.** The 1.13.2 second support sample precedes its movement call, as does the 1.14.2 sample. The author correctly withdraws the claim that these releases sample slipperiness on opposite sides of movement. The correction also accurately leaves the distinct acceleration-coefficient equivalence unproven.

## Canonical source recheck

The two canonical `LivingEntity.java` files rehash to the source-map entries previously verified in the original independent audit:

- 1.13.2 Feather `net/minecraft/entity/living/LivingEntity.java`: `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`.
- 1.14.2 Yarn `net/minecraft/entity/LivingEntity.java`: `e8224e7df98ccf0986bb390f35e64293263d17b9ab1dbdfeb661c65d28b6ee78`.

I rechecked the exact code ranges. In 1.13.2, the first support sample at lines 1540–1543 feeds acceleration; `updateVelocity` occurs at 1553; then the mutable position is reset using the still-current `x`, shape minimum Y and `z`, and the second sample is read at 1555–1557. `move(...)` follows only at line 1575. No position-changing operation intervenes before that second sample. In 1.14.2, the support position and slipperiness are read at lines 1824–1825, `updateVelocity` and the pre-move velocity helper run at 1827–1828, and `move(...)` occurs at 1829. Thus both releases sample support before movement.

The supplement transcribes the coefficient expressions correctly: the older grounded path uses `getSpeed() * (0.16277137F / (t * t * t))` after forming `t` from slipperiness times `0.91F`; the newer `method_18802` uses `getMovementSpeed() * (0.21600002F / (f * f * f))`. This audit does not establish equality of their float results, operation order or speed providers. That distinction is properly retained as open work, rather than used to revive the retracted sample-phase claim.

This supplement only audits the correction. The other source claims and scope boundaries remain as recorded in the original audit; collision/provider reachability, wider pair coverage and runtime parity are not established here.
