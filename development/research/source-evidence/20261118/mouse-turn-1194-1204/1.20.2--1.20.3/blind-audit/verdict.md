# Independent claim review: 1.20.2 to 1.20.3

## Bound inputs

- Author claim: `source/comparison.md`, raw SHA-256 `ab21e646ed3d68207013640569b61ef11d24d039a9ef4bb2e65547d6db08ae1f`.
- Blind source record: `blind-audit/frozen.md`, raw SHA-256 `4baf1caa137d135e36d64ea99f25df032d90f21f29efc2341685e7dce77cd64f`.
- Exact sources: canonical Mojmap roots `decompiled_minecraft/1.20.2/mojmap/` and `decompiled_minecraft/1.20.3/mojmap/`. The author hash matched the supplied SHA. The frozen file currently hashes to the actual 64-character value shown above; the dispatch value was 63 characters and therefore could not identify a SHA-256 artifact. The original frozen bytes were preserved.

## Source provenance and record limits

The 1.20.2 ready marker’s source-manifest and artifact-manifest hashes match their files. Its artifact manifest pins `client.jar`, `client_mappings.txt`, `client-mojmap.jar`, and `version.json`; those entries match the ready marker’s client pin and Mojmap mapping identity. The author’s 1.20.2 `mojmap.provenance.json` hash also matches `1dad9f725f6cb25b6568700fa0294f83b66ea9633b0d62ed354d7bd8b45d4f05`. However, that JSON records the batch/run and tool identities but has no per-input/source hashes, and the ready marker does not pin the provenance JSON digest. Source content identity is reproducible through the checked manifests; the connection to the run-provenance file is a local record gap.

The 1.20.3 embedded `.decompile-provenance.json` hash matches `c3d605e55b287e800a86af8567a75edcb009d1a9a1744cfcb4e6f47667dcee0f`; its local `inputs` include exact client, official `client.txt` mappings, and version-metadata hashes, and its `sources` map contains the compared file hashes. All checked consumed source hashes match both published manifests and computed file hashes. Input artifact bytes themselves were not separately rehashed from cache; this review verifies their recorded local pins and the decompiled-source hashes.

## Separate verdicts

### 1. Ordinary callback arithmetic and gates — ACCEPTED

`MouseHandler.onMove` only accumulates cursor deltas behind the grabbed-and-active window gate, calls `turnPlayer()` synchronously before updating stored cursor positions, and does so only for the matching window. `turnPlayer()` is called at this callback site; it is not a per-tick scheduler. Its own grabbed/active gate, smooth-camera branch, first-person-scoping exception, ordinary sensitivity branch, accumulator reset, Y inversion, and non-null player dispatch match across the pair. `MouseHandler.java` is byte-identical. This accepts the stated finite, captured, unscoped, smooth-camera-off callback path; callback registration, device details, and exact inter-tick callback timing remain outside scope.

Type note: `Options.sensitivity()` returns `OptionInstance<Double>` in each release. Thus the `0.6F` and `0.2F` literals are promoted to `double` in the sensitivity expression. The author’s formula/order is correct, but the frozen record’s extra sentence that this first expression “retain[s] float arithmetic” is inaccurate. At sensitivity `0.5`, the resulting factor is not exactly `1.0`; the frozen witness should be read only as a reachable finite callback path, not as an exact factor calculation. This does not change the pairwise equivalence verdict.

### 2. `Entity.turn` virtual pitch setter and clamp — ACCEPTED

The compared `Entity.turn` bodies match: both cast each input to `float`, multiply by `0.15F`, call pitch setter before yaw setter, clamp the current pitch after the first setter, then update and clamp previous rotations in the same order. The inherited `Entity.setXRot(float)` checks finiteness then assigns; LocalPlayer has no `turn` or `setXRot` override in either source tree. The float `Mth.clamp` body is identical. Dynamic dispatch and the clamp remain in the stated order.

### 3. First active elytra movement — ACCEPTED WITH WITNESS NARROWING

The `LivingEntity.travel` elytra branch and its direct helpers (`isFallFlying`, `checkSlowFallDistance`, `getLookAngle` / `calculateViewVector`, used `Mth` trig and `Vec3` arithmetic) match for the first self-move. The call route needs one additional wrapper: `LivingEntity.aiStep` calls virtual `travel`; an unmounted LocalPlayer reaches inherited `Player.travel`, whose pre-move path skips its swimming adjustment when not swimming and calls `super.travel` when `abilities.flying` is false. LocalPlayer’s `isEffectiveAi()` returns true, satisfying the local-control gate. On the next movement tick, `updateFallFlying` retains the flag for an airborne, non-passenger, non-levitating player with a valid enabled elytra, and the water/lava alternatives precede the elytra branch.

A sufficient witness is therefore an unmounted LocalPlayer with `isSwimming == false`, `abilities.flying == false`, airborne with a valid enabled elytra and fall-flying flag, not in water or lava, and a clear first movement path. Under those explicit conditions, the first `MoverType.SELF` displacement is produced by the matching `LivingEntity.travel` branch. The authored witness omitted the Player wrapper and some of these preconditions; its conclusion is supported after narrowing.

`Player.travel` has a surrounding difference after `super.travel`: 1.20.2 calls `checkMovementStatistics` after the move, while 1.20.3 does not. This is after the first displacement and affects statistics/exhaustion, outside the first-move claim and the project’s health/food-production scope. It remains a residual if the claim is widened to later tick state. Collision responses after the move and all non-clear-space trajectories are also outside this bounded verdict.

## Frozen-record correction and unresolved gates

Preserve the bound frozen bytes. Its no-difference conclusion is supported for the named slices, but the sensitivity type/witness sentence identified above is a factual correction for any future reuse. The 1.20.2 provenance JSON linkage gap remains a record limitation; the current source and artifact-manifest identities match. No full-pair inventory, runtime parity, broad collision audit, or source difference outside these claims is accepted here.