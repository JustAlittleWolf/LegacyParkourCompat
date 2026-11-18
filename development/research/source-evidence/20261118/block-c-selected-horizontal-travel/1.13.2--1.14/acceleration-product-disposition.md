# Product disposition: 1.13.2 → 1.14 airborne input-acceleration precision

## Conclusion

**The accepted finite witness is already covered at both exact profile endpoints in product baseline `aae9c86464638cb5ed7faa535c92569b89484906`; this finding does not expose an endpoint implementation gap.** The direct yaw updater is a separate no-difference result. With yaw fixed at `0.0F`, the accepted witness isolates the changed arithmetic in relative acceleration, and the baseline resolves the matching float and double-vector handlers for 1.13.2 and 1.14 respectively.

This is a source/code-path disposition only. It does not establish full-tick runtime parity, every unsupported-input fallback, or the exact behavior of every 1.14 patch release.

## Evidence identity

- Accepted exact-source report: `c-yaw-precision-1132-114-source/exact-report.md`, SHA-256 `da0399ca1fe6c9a621a25a96d0d1c7e5291389369815f0a60daa576c950ca5ba`.
- Signed independent audit: `c-yaw-precision-1132-114-audit/verdict.md`, SHA-256 `900fc90bc76b6f642d7a33e6b41bc93b556d6d4cb57c4f456a41cc866eb28011`.
- Product baseline inspected read-only: `aae9c86464638cb5ed7faa535c92569b89484906`.
- Exact source files and hashes in the checkout match the accepted report: `Entity.java` and `LivingEntity.java` for 1.13.2 and 1.14 (hashes listed in the accepted report).

## Source operation and witness

The accepted difference is in `Entity.updateVelocity`, reached from the ordinary dry ground/air branch in `LivingEntity.moveRelative`. The 1.13.2 path accumulates input length, thresholds, normalizes/scales, and rotates in floats before widening the horizontal increments into double velocity fields (`Entity.java:1057–1074`). The 1.14 path builds a `Vec3d` using double length/scaling and double component products, with float sine/cosine (`Entity.java:1010–1025`). Both releases use the same local-control/logical-movement gate and select finite `speedInAir = 0.02F` for the ordinary airborne branch (`LivingEntity.java:1478–1488, 1545–1553` and `1779–1790, 1833–1839, 1954–1956`). The effective input component ordering matches.

For the accepted witness—ordinary local player, first airborne tick, right-only input, no movement modifiers, yaw `0.0F`, and zero horizontal velocity—keyboard input gives sideways `-1.0F`, then tick damping gives `-0.98F`; vertical and forward inputs are zero. The input squared length is about `0.9604`, so it is above both zero cutoffs and remains unnormalized. At yaw zero the rotation is identity. The audit’s corrected source arithmetic is:

- 1.13.2 float product widened into velocity: `-0.019600000232458115`.
- 1.14 double product of widened float operands: `-0.01959999994337558`.

The different finite increment is consumed by the first movement call. It is a scalar/vector acceleration precision difference, not a yaw-value or trigonometric difference. The signed audit also separates this result from the unchanged ordinary yaw update. I use the audit’s corrected float result, not the shorthand `-0.0196F` in the source report.

## Baseline implementation, gates, and resolution

The baseline has one `InputAccelerationHook` (`src/client/java/me/wolfii/legacyparkourcompat/mechanic/hook/travel/InputAccelerationHook.java`). `EntityMixin.legacyparkourcompat$inputAcceleration` wraps `Entity.moveRelative`, checks player-only historical heading and input-acceleration hooks, and calls the native operation when neither handles it (`EntityMixin.java:345–360`). `MovementRuntime.find` returns no historical hook unless the entity is a client-side player under an enabled historical profile; otherwise it looks up that profile’s independently resolved hook (`MovementRuntime.java:386–391`, with the client-side gate in `appliesTo`). This avoids running the historical operation on logical servers.

- **1.13.2:** `ParkourVersion.V1_13_1` covers 1.13.1–1.13.2. `InputAcceleration` is annotated `emulates = V1_13_1`, so this profile resolves it. Its movement-feature gate admits the stated default-scale, non-swimming/non-using witness. It casts the widened input components back to float, evaluates the 1.13 float squared-length / `1.0E-4F` threshold / square root / scale and float yaw rotation, then adds the float results to double velocity (`change/v1_13/InputAcceleration.java`, `apply`). For the witness it returns handled and reproduces the accepted 1.13.2 increment.
- **1.14:** `PlanarInputAcceleration` is annotated `emulates = V1_14_2`; its ordinary planar gates require the local keyboard player, ordinary standing/crouching context, non-spectator, non-flying, dry, not using an item, zero vertical input, and supported movement features. The witness passes each gate and its squared length exceeds `1.0E-7`; the hook uses double vector scaling and float trig, adds the vector, and returns handled (`change/v1_14_2/PlanarInputAcceleration.java`, `apply`). `ChangeResolver.resolve` retains changes whose emulated version is at least the selected profile and chooses the earliest such version for each hook. Thus the available `V1_14_2` entry is selected for the `V1_14` profile, and this handler reproduces the accepted 1.14 operation for the witness (`ChangeResolver.java:23–36`).

## Boundary and remaining limits

For the exact endpoints in this accepted pair, the product mapping is coherent: `V1_13_1` selects the old float implementation for 1.13.2; `V1_14` selects the double-vector implementation via the closest later registration. The baseline’s `V1_14_2` registration also serves the 1.14 and 1.14.1 profile keys when no nearer hook exists. The accepted report/audit establish the exact 1.13.2 and 1.14 source comparison only; they do not independently prove intermediate 1.14.1/1.14.2–1.14.4 source continuity or a full-pair patch inventory. Keep that broader boundary claim separate. No implementation change is indicated for this finite endpoint witness. Runtime parity remains unverified.


