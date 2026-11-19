# Independent raw-bound verdict

## Bound input

- Comparison: `c-input-earlier-math/comparison.md` SHA-256 `87092614af0666a3e9bd5405c215a1e685896e7c6a185c876079d633a818ddd8` (machine-checked against the assigned digest).
- Audit scope: exact canonical Feather Gen2 `Entity.java` and `LivingEntity.java` sources for 1.8.9, 1.9.4, 1.10.2, 1.11.2, 1.12.0, 1.12.1, and 1.12.2; dry on-foot helper and caller; matched yaw/float control points.

## Verdict: CONFIRMED, with a scope qualification

The ordinary dry on-foot input helper changes from two float components to three between exact 1.11.2 and 1.12.0. In 1.11.2, `LivingEntity.moveRelative(float sideways, float forwards)` passes `(sideways, forwards, u)` to `Entity.updateVelocity`, whose 2D body normalizes `sideways² + forwards²` and applies yaw to X/Z. In 1.12.0, `LivingEntity.tick` calls `moveRelative(sidewaysSpeed, verticalSpeed, forwardSpeed)`; the three-argument `moveRelative(forwards, sideways, upwards)` calls `updateVelocity(forwards, sideways, upwards, u)`. Thus the helper receives `(sidewaysSpeed, verticalSpeed, forwardSpeed)` as local X/Y/Z. Vanilla player input does not assign `verticalSpeed` (within the inspected source tree its only assignment is in `MobEntity`), so the ordinary player horizontal path supplies zero for Y.

Matched 1.11.2/1.12.0 helper control points: both use float arithmetic, the same strict `sum < 1.0E-4F` deadband, `MathHelper.sqrt`, minimum length `1.0F`, `scale / length` before component scaling, then `sin(yaw * (float)(Math.PI / 180.0))` before `cos` with identical yaw conversion expression. For the ordinary finite horizontal player witness, the added squared-zero term leaves the horizontal normalization value unchanged; the 1.12 helper adds the zero Y component to `velocityY` between X and Z updates. This establishes helper/call-contract applicability for horizontal input. It does not establish parity for nonzero Y callers or runtime trajectories.

The two-axis form is present in each inspected earlier control (1.8.9, 1.9.4, 1.10.2, 1.11.2); the three-axis form is already present in 1.12.0, 1.12.1, and 1.12.2. The helper/call path transition is therefore bounded to 1.11.2→1.12.0 among these inspected exact releases. `Entity.java` and `LivingEntity.java` SHA-256 digests independently match the assigned comparison table for all seven releases.

Qualification: 1.8.9 uses the distinct yaw expression `yaw * (float)Math.PI / 180.0F`; therefore do not characterize its yaw arithmetic as identical to 1.9.4–1.12.2. The transition comparison 1.11.2→1.12.0 does retain identical yaw expression and operation ordering for shared horizontal coordinates.

## Exact-source control references

- `decompiled_minecraft/1.11.2/ornithe-feather/net/minecraft/entity/Entity.java`: `updateVelocity`, lines 1028–1044; `LivingEntity.java`: dry branch call line 1456; ordinary tick call line 1803.
- `decompiled_minecraft/1.12/ornithe-feather/net/minecraft/entity/Entity.java`: `updateVelocity`, lines 1049–1067; `LivingEntity.java`: declaration line 1425, dry branch call line 1493, ordinary tick call line 1847; `MobEntity.java`: `verticalSpeed` assignment line 475.
- In 1.12.1 and 1.12.2, `Entity.java` and `LivingEntity.java` exact source digests equal the 1.12.0 digests; helper signatures and call sites above therefore match byte-identical relevant files.

## Machine-generated source digest checks

| Release | `Entity.java` SHA-256 | `LivingEntity.java` SHA-256 |
|---|---|---|
| 1.8.9 | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` | `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e` |
| 1.9.4 | `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0` | `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5` |
| 1.10.2 | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` | `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82` |
| 1.11.2 | `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440` | `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f` |
| 1.12 | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` |
| 1.12.1 | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` |
| 1.12.2 | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` |
