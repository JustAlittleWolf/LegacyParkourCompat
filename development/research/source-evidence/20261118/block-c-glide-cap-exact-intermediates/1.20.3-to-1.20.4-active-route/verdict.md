# Independent source audit verdict: 1.20.3 → 1.20.4 active Elytra route

## Verdict

**Behavior claim accepted for the assigned bounded slice:** no difference was found in the already-active, dry travel route, glide damping/move order, active-flag retention/cancellation, or climbable cleanup bypass. This is source acceptance for this slice only; it does not establish pair-wide coverage, intermediate patch boundaries, or runtime parity.

**Evidence qualification:** the checked 1.20.3 local provenance artifact does not directly record a client-mappings artifact/hash. Its `inputs` record the exact 1.20.3 version metadata JSON (SHA-256 `05e727aac55594b6014e9714ad38fa16af09f075daf5efa0f5743f2453fa8fc2`) and client JAR (`bef21938224902dd925e2d89dcfe1d2540656d4cf7a5abd2b93e0402d135c655`), but no mappings entry. Thus the report’s `ad03...` mapping hash is not independently confirmed as the 1.20.3 mapping identity by the local 1.20.3 provenance file. The 1.20.4 marker names the source manifest `mojmap.sources.sha256` (SHA-256 `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1`); the report calls it `source-manifest.sha256`, a filename discrepancy. The 1.20.4 artifact manifest does confirm `1.20.4/client_mappings.txt` at SHA-256 `ad03c803d866062909dd378ed5f1611687dc0ed0a76cc7fd57e968a99d641e5f`.

## Independent source checks

Both canonical `LivingEntity.java` files have SHA-256 `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d`, and both `LocalPlayer.java` files have SHA-256 `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5`. Identical whole-file hashes establish the compared method bodies and call site are byte-identical across these local trees.

- `decompiled_minecraft/1.20.3/mojmap/net/minecraft/world/entity/LivingEntity.java`: `travel(Vec3)` lines 2035–2166; `aiStep()` call to `updateFallFlying()` line 2593; `updateFallFlying()` lines 2641–2666; `handleRelativeFrictionAndCalculateMovement(Vec3,float)` lines 2202–2213; `handleOnClimbable(Vec3)` lines 2230–2244; `isFallFlying()` lines 3086–3088.
- `decompiled_minecraft/1.20.4/mojmap/net/minecraft/world/entity/LivingEntity.java`: same members and lines.
- Paired local provenance: `decompiled_minecraft/1.20.3/mojmap/.decompile-provenance.json`, SHA-256 `c3d605e55b287e800a86af8567a75edcb009d1a9a1744cfcbb4e6f47667dcee0f`; `decompiled_minecraft/1.20.4/mojmap.provenance.json`, SHA-256 `10914d9b7b3fc29de066ca24f7d905e4d4f0a451a6c81729a2d1981addfc1385`; and `decompiled_minecraft/1.20.4/mojmap.ready.json`.

The source checks support the report’s stated behavior:

1. `updateFallFlying()` starts from shared flag 7 and retains it only if it was already set and the entity is airborne, not a passenger, not levitating, and has an enabled chest Elytra. Otherwise its local flag becomes false. Only the logical server writes the shared flag back. This validates the described active-state cancel/retain gate; initial flight eligibility is outside this verdict.
2. `travel()` tests water and lava before `isFallFlying()`. Under the assigned dry-state condition, an already-active flyer enters the glide branch before the ordinary friction route. The branch applies lift/dive/pitch and horizontal-speed convergence in the same order, then calls `setDeltaMovement($$13.multiply(0.99F, 0.98F, 0.99F))` immediately before `move(MoverType.SELF, this.getDeltaMovement())`. `Vec3.multiply` accepts doubles, so the float literals widen to double; there is no separate velocity clamp in this branch before that move.
3. Ordinary friction calls `handleOnClimbable()` before its move. The glide branch does not call ordinary friction or climbable cleanup, independent of whether feet are ordinary, ladder, or vine. Cleanup retains the same fall-distance reset, X/Z `Mth.clamp(..., -0.15F, 0.15F)`, Y `Math.max(..., -0.15F)`, and player/feet-block/sneak suppression predicate in both releases.

## Immutable inputs

- Frozen report: `c-glide-1203-1204-cap-route/report.md`, SHA-256 `def8833e5528cb630ff1368d625734e7d406ba7f75b3bba8bf84d57e8278f9ae`.
- Verdict body SHA-256 (normalized UTF-8 of all preceding text, terminal CR/LF trimmed): `41e01910025c8937d524c7d5275c7c4bf5013f7380080e9c103aa35fa054510b`.
- Full-file verdict SHA-256: supplied with the audit result (a self-referential hash cannot be embedded in the same file).