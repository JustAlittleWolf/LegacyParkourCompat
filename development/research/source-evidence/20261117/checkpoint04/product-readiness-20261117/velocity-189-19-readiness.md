# Product readiness: 1.8.9→1.9 velocity component cutoff

Status: COVERED_NO_CODE for the bounded accepted operation; no runtime-parity claim.

## Immutable source inputs

- Candidate: `build/source-189-19-20261117/input-travel/velocity-small-component-cutoff.md` — SHA-256 `2813369b5f70c5a2e1aa6be2122690c3df9c6223ae2342905624d1285170c0ce`.
- Independent ACCEPT: `build/source-189-19-20261117/audit-velocity/verdict.md` — SHA-256 `AAF353454B1899D8913D8B8335012D09686F10E4F4695D4F56320C1B4E826CB4`.
- Canonical finding remains `development/research/findings/1.8.9--1.9.4/velocity-zero-threshold.md`; it was not edited.

## Product correspondence

The only providers implementing `VelocityZeroThresholdHook` are:

- `src/client/java/me/wolfii/legacyparkourcompat/change/v1_8/VelocityZeroThreshold.java`, annotated `@MovementChange(emulates = V1_8_9)`, uses strict `abs(component) < 0.005`.
- `src/client/java/me/wolfii/legacyparkourcompat/change/v1_21_4/PlayerVelocityZeroThreshold.java`, annotated `@MovementChange(emulates = V1_21_4)`, uses strict `abs(component) < 0.003`.

`LivingEntityMixin` filters X, Y and Z independently at the `aiStep()V` `setDeltaMovement(DDD)V` arguments (ordinal 0, indexes 0, 1 and 2). Closest-applicable resolution gives selected V1_8_9 the 0.005 provider; selected V1_9 (aliases 1.9/.1/.2) and V1_9_3 (1.9.3/.4) resolve the later V1_21_4 provider with the accepted 1.9 threshold. No native fallback applies for those selections while a provider resolves.

No product files or Git state were changed for this check.