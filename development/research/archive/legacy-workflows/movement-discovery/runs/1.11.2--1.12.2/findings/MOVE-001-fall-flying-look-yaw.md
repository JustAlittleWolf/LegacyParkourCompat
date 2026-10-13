# MOVE-001: Fall-flying direction uses body yaw instead of head yaw

- Older version A: 1.11.2
- Newer version B: 1.12.2
- Mechanic / coverage slice IDs: 3.2 / glide branch of `LivingEntity.moveRelative`
- Classification: changed behavior
- Confidence: source-confirmed; confirm A's remapper warning disposition before catalog closure
- Applicability: historical player behavior
- First changed release: unknown within (1.11.2, 1.12.2]
- Runtime validation: not performed

## Paired evidence

- A manifest artifact A; `decompiled_minecraft/1.11.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`; `net.minecraft.entity.living.LivingEntity#getLookVector()Lnet/minecraft/util/math/Vec3d;`; lines 1915-1928; SHA-256 `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f`. The method returns `this.getRotationVector(1.0F)`. The same class overrides `getRotationVector(float)` at lines 1921-1928; at `tickDelta == 1.0F`, it calls `getRotationVector(this.pitch, this.headYaw)`.
- A glide consumer: same file, `LivingEntity#moveRelative(float,float)`, lines 1388-1400; SHA-256 as above. Under `isFallFlying()`, it reads `this.getLookVector()` at line 1397.
- B manifest artifact B; `decompiled_minecraft/1.12.2/ornithe-feather/net/minecraft/entity/Entity.java`; `net.minecraft.entity.Entity#getLookVector()Lnet/minecraft/util/math/Vec3d;`; lines 1690-1692; SHA-256 `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a`. The method directly returns `this.getRotationVector(this.pitch, this.yaw)`.
- B glide consumer and living rotation override: `decompiled_minecraft/1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`; `net.minecraft.entity.living.LivingEntity#moveRelative(float,float,float)` lines 1425-1438, including the glide branch's `getLookVector()` call at 1434; and `getRotationVector(float)` lines 1960-1968, which still uses `headYaw` at the endpoint. SHA-256 `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6`.
- Bytecode cross-checks: A `javap -classpath <A client-ornithe-feather.jar> -c -p net.minecraft.entity.living.LivingEntity` showed `getLookVector()` loads `1.0F` and invokes `getRotationVector(F)` virtually; the endpoint branch loads `pitch` and `headYaw` and invokes `getRotationVector(FF)`. B `javap -classpath <B client-ornithe-feather.jar> -c -p net.minecraft.entity.Entity` showed `getLookVector()` loads `pitch` and `yaw` and invokes `getRotationVector(FF)` directly. B `LivingEntity.moveRelative` bytecode invokes `getLookVector()` in the fall-flying branch. A/B mapped jar hashes are recorded in `run.md`. Both javap invocations emitted an internal AccessDenied diagnostic after printing the relevant method bytecode; the bounded outputs were present, but missing raw decompiler logs still prevent auditing unrelated remapper warnings.

## Source-level difference

For 1.11.2, `LivingEntity.getLookVector()` calls the virtual `getRotationVector(1.0F)`, so the `LivingEntity` override supplies pitch and head yaw at the tick endpoint. In 1.12.2 the `LivingEntity.getLookVector()` override is absent; the inherited `Entity` implementation calls the final two-float rotation-vector helper directly with pitch and body yaw. Although the 1.12.2 `LivingEntity.getRotationVector(float)` still uses head yaw for its own callers, `Entity.getLookVector()` bypasses that override.

## Reachability and dependencies

The client player's inherited `LivingEntity.moveRelative` selects its fall-flying branch while `isFallFlying()` is true and uses `getLookVector()` in the glide acceleration calculations. A concrete differing precondition is active player fall-flying while `headYaw != yaw`. The exact A/B player path reaches this shared living-entity implementation; other aspects of glide selection/equipment were not needed to establish this direction change. Source proves the vector input changes; trajectory consequences are inferred from the unchanged downstream glide expressions consuming its X/Y/Z components.

## Consequence and uncertainty

Source-proven: the glide algorithm receives a look vector built from head yaw in A and body yaw in B. Predicted consequence: when those yaw values differ, subsequent fall-flying velocity updates can differ. No trajectory was measured. The adjacent owner is checking A remapper invalid-access warning targets; the raw log is unavailable, so the exact warning-to-class relation remains an audit caveat.

## Handoff

Player fall-flying direction basis changed from head yaw to body yaw between the inspected endpoints. Do not infer a first affected patch from these two versions alone. Related to stage 3 glide movement; runtime validation and implementation decisions are deferred.
