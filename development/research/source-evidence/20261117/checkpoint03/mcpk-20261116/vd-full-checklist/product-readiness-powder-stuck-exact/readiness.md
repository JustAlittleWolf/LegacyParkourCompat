# Product correspondence: Powder Snow stuck-piston player response

Disposition: **covered at the requested 1.21.9 / 1.21.10 stable boundary; no product gap identified.** This is a product-readiness check only, not source acceptance renewal, implementation, or runtime parity.

## Bound evidence

- Accepted source audit `powder-snow-postintro-audit/audit.md`, SHA-256 `a9c68903a2d451c34ed81954801451e05bdcbfe03798a5419db9e293b39ecdcd` (rehashed in product checkout).
- Corrected boundary recheck `powder-stuck-1219-recheck/recheck.md`, SHA-256 `6ba8b0f1fc17cd68d75d82d868177b94bd2d82c10f0f4652a06d3b56ee544b5e` (rehashed).
- Accepted cutover: 1.21.9 multiplies piston-limited player movement under the nonzero stored-multiplier gate; starting 1.21.10, piston movement skips the multiplication. 1.21.11 and 26.1/26.2 retain the new behavior. Runtime parity remains unverified.

## Product mapping

- `ParkourVersion` has distinct ordered `V1_21_9` and `V1_21_10` values (`src/main/java/me/wolfii/legacyparkourcompat/api/ParkourVersion.java:101-102`); neither is an alias that could collapse this boundary.
- The operation hook is `PistonStuckSpeedHook` (`src/client/java/me/wolfii/legacyparkourcompat/mechanic/hook/entity/PistonStuckSpeedHook.java:14-16`). Client auto-discovery registers annotated changes by implemented mechanic interface.
- `@MovementChange(V1_21_9)` implementation multiplies `limitedMovement` by the stored multiplier (`src/client/java/me/wolfii/legacyparkourcompat/change/v1_21_9/PistonStuckSpeed.java:17-24`). The resolver selects the closest registered change at or newer than the selected profile (`src/main/java/me/wolfii/legacyparkourcompat/impl/ChangeResolver.java:21-32`), so selected V1_21_9 resolves this class.
- `EntityMixin` only captures the multiplier for player piston moves when `lengthSqr() > 1.0E-7` and restores it only on the piston player path (`src/client/java/me/wolfii/legacyparkourcompat/mixin/EntityMixin.java:889-923`). The fallback when no operation hook resolves is the unmodified movement argument.
- There is no `PistonStuckSpeedHook` implementation annotated V1_21_10 or later. For selected V1_21_10, V1_21_11, or 26.1 on the current 26.2 client, the resolver therefore has no hook for this operation and retains native behavior. The accepted source boundary says this native-era piston path skips the multiplier, matching the target's native behavior. The mixin's no-hook fallback returns the already supplied native movement unchanged. `CURRENT` likewise resolves no changes.
- The older `V1_13_1` implementation returns movement unchanged; it does not conflict at V1_21_9 because resolver preference chooses the newer V1_21_9 implementation. It also does not need to supply the post-boundary behavior while selected V1_21_10+ uses native fallback.

## Result

For the exact requested profiles on the current native target: V1_21_9 gets the historical multiplier through its versioned hook; V1_21_10+ gets the no-multiplier behavior through native fallback. The previous generic-hook-only readiness note is superseded by this exact mapping. No edits, tests, builds, or runtime launches were performed.