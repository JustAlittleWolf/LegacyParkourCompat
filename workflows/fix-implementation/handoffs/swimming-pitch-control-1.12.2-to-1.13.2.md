# Fix handoff: swimming pitch control

- Discovery finding: `wiki-swimming-pitch-1.12.2-1.13.2` from `workflows/wiki-audit-2026-10-07/swimming-pitch-control-1.12.2-to-1.13.2.md`.
- Accepted finding snapshot: commit `b20730f64ed07090b618f6101467f8bde07e0024`; exact file SHA-256 `dd0abd5a90ac1813197e4c09ab97ed0e0027c48e237a148bbf95bde2f64e6a89`.
- Independent Wiki-aware review: commit `c80b31ff94b4264194d2a08d168cca81c45d5f09`, `workflows/wiki-audit-2026-10-07/reviews/independent-review-2026-10-08.md`, SHA-256 `5e6804d6c1fc9d08e0d1fff33de4d2e755beb0ad1cff24e38e9eeb420d2454f5`; swim finding accepted.
- Pair status at handoff: active; the pair and exact first 1.13 patch that introduced the behavior remain unresolved. The endpoint evidence establishes only `(1.12.2, 1.13.2]`.
- Implementation status: implemented for the requested `V1_13` profile. Swimming did not exist in the 1.12.2 profile; no swimming behavior is added there.
- Source limitation: the 1.13.2 revised Feather source snapshot is `feather-r1-2026-10-07`; the original derived Feather JAR is unavailable, so equivalence to that original JAR remains unproven. The cited Java source hashes and published manifests were checked.

## Evidence and implementation

The accepted 1.13.2 `PlayerEntity.moveRelative(FFF)V` body (`ready/1.13.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`, lines 1442-1465; SHA-256 `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633`) reads `getLookVector().y` and applies the swimming correction. Its Y component is computed by `Entity.getRotationVector(float,float)` (`Entity.java`, SHA-256 `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`): `-MathHelper.sin(pitch * (float)(Math.PI / 180.0))`. `MathHelper.sin(float)` uses float multiplication by `10430.378F` to select the 65,536-entry table (`MathHelper.java`, SHA-256 `2211b0cb3da7d8789a4a4df96509b95e6b1be8b222be2f530e7bd0695573bd69`).

The configured current target is Minecraft 26.2 (`gradle.properties`). Its ready source set is `build/movement-campaign-2026-10-07/ready/26.2/unobfuscated`, exact version marker `ready/26.2/unobfuscated.ready.json`, source-manifest SHA-256 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`. Native `Player.travel(Vec3)V` (`Player.java`, lines 1402-1422; SHA-256 `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`) retains the same swimming guards, look-Y thresholds (`-0.2`, `0.0`), interpolation factors (`0.085`, `0.06`), fluid position, and `movement.y + (lookY - movement.y) * multiplier` operation order. `LivingEntity.travel` calls into the inherited `Entity.moveRelative(float,Vec3)V`; the player correction is still in the corresponding pre-travel position.

The one math difference is the binding from current `Entity.getLookAngle()` to `calculateViewVector`, which calls `Mth.sin(double)` (`Entity.java`, SHA-256 `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`; `Mth.java`, SHA-256 `30455c684f2401c79290847822bca82e77162c4a2bcf8618e85cac9898a2dc17`). `Mth.sin(double)` quantizes its table index after double multiplication. The old source's `MathHelper.sin(float)` quantizes after float multiplication. At pitch `-84.375F`, those operations select indices `50176` and `50177`, respectively. The selected table value becomes `lookY` in the velocity correction, so native math does not preserve the 1.13 result at every pitch.

`SwimmingPitchBehavior` is dispatched by `PlayerMixin` at the existing `Player.travel` `getLookAngle()` invocation. The V1_13 implementation uses `LegacyTrig.sin` with the historical float index calculation and computes `player.getXRot() * (float)(Math.PI / 180.0)` before negating the sine, matching the 1.13 source. It changes only the look Y value consumed by the native correction; the native branch, fallback, operation order, jump/fluid guards, and movement loop remain intact. `change.v1_13.MovementChanges` registers it. The existing `change.v1_12.SwimmingState` returns false and is selected only for V1_12 and older, so those profiles cannot enter native swimming pitch control.

## Code and validation identity

- Base: `main` at `ea812dc9171fdf41e02b3a2292db7d40b105c9c4`; branch `fix/swimming-pitch-control-1-13`.
- Changed code: `mechanic/hook/SwimmingPitchBehavior.java`, `mixin/PlayerMixin.java`, `change/v1_13/SwimmingPitch.java`, and `change/v1_13/MovementChanges.java`.
- No overlapping mechanic key existed. The existing water gravity/sprint changes are post-travel water behavior and remain independent.
- Static verification: source-file SHA-256 values above match their published manifests; `git diff --check` passed. No build, tests, game client, or runtime validation was run, per assignment.
- Runtime question: TAS comparison should verify the pitch boundary and fluid/jump combinations after the implementation owner authorizes runtime validation.
- No implementation-derived feedback was sent to source-only owners.
