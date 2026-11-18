# 1.16.5 → 26.2: direct player yaw turn and movement use

## Source identity

- Older source: Minecraft `1.16.5`, `mojmap`, canonical decompile at `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.16.5/mojmap/`. Ready marker identifies version `1.16.5`, mapping `mojmap`, source manifest SHA-256 `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`, client artifact SHA-256 `00b5ebbc33e95ea88c1ab80601599c9827e9aa861d93ccc2cfcbbfd996e86263`. Provenance records Vineflower 1.12.0 / Java 25, exact run batch `1.15.2,1.16.5,1.17.1,1.18.2`, mapping option `mojmap`, created 2026-10-07.
- Newer source: Minecraft `26.2`, `unobfuscated` source output using Mojang-published names at `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/26.2/unobfuscated/`; it is not a remapped `mojmap` tree. Ready marker identifies version `26.2`, source manifest SHA-256 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`. Provenance records Vineflower 1.12.0 / Java 25 and exact run version `26.2`, created 2026-10-07.
- Per-file SHA-256 values below match each tree's `.sources.sha256` manifest.

## Difference: finite `Entity.turn` yaw is rounded at a different point

`1.16.5` `net/minecraft/world/entity/Entity.java`, `turn(double d, double e)` (line 340): yaw delta is computed as `double g = d * 0.15`; current yaw is assigned `(float)(this.yRot + g)` (line 344), and previous yaw is assigned `(float)(this.yRotO + g)` (line 347). The double product and sum are rounded to binary32 only at the assignment. This direct path does not call a yaw setter. `setRot` at line 326 reduces yaw with `% 360.0F`, but `turn` itself does not normalize yaw.

`26.2` `net/minecraft/world/entity/Entity.java`, `turn(double xo, double yo)` (line 489): `float yDelta = (float)xo * 0.15F` (line 492), then `setYRot(getYRot() + yDelta)` (line 493), and `yRotO += yDelta` (line 496). The input is narrowed before multiplication, the multiplier is binary32, and the yaw sum is binary32. `turn` still does not normalize yaw. `setRot` at lines 462–463 applies `% 360.0F` through `setYRot`; direct `turn` does not call `setRot`.

A finite, single-call numerical witness isolates this stored-yaw difference before any trigonometry or mouse-event batching: start with `yRot = yRotO = 0.0F`, call `turn(0.003, 0.0)`. Under the source expressions and Java binary32 rounding, 1.16.5 stores `0.00044999999227002263F` (`0x39ebedfa`) in both yaw fields. 26.2 stores `0.0004500000213738531F` (`0x39ebedfb`) in both. They are adjacent float values. The witness compares the direct API's state immediately after one finite call; it does not claim a downstream velocity delta by itself.

26.2 `setYRot(float)` (line 3898) rejects non-finite inputs via `Float.isFinite`, logs/pauses in IDE, and leaves `yRot` unchanged (lines 3899–3903); 1.16.5 `turn` writes the field directly. For non-finite `xo`, 26.2's yaw setter may reject the current-yaw write while the later `yRotO += yDelta` still runs; old `turn` directly propagates the corresponding non-finite arithmetic. No source evidence here establishes that ordinary mouse input produces non-finite values, so this is setter behavior, not the ordinary-path witness.

`LivingEntity` copies current yaw into previous yaw during tick processing in both releases (1.16.5 line 395, 26.2 line 500). Its rotation interpolation adjustment then moves `yRotO` by 360-degree steps until current-minus-previous is in `[-180, 180)` (1.16.5 lines 2204–2209; 26.2 lines 2832–2837). These are separate snapshot/interpolation operations; they do not normalize the yaw written by `turn`.

## Reachable player movement consumer

The ordinary client mouse path reaches `Entity.turn`: 1.16.5 `MouseHandler.turnPlayer` calls `player.turn(j, k * n)` (line 242); 26.2 `MouseHandler.turnPlayer` calls `player.turn(...)` with its mouse-axis sign options (line 375). The witness fixes the direct arguments at one call, bypassing differences in accumulated mouse deltas and sensitivity preprocessing.

For ordinary non-fluid ground/air movement, 1.16.5 `LivingEntity.travel` selects ground friction using `onGround` (line 2025), then its `handleRelativeFrictionAndCalculateMovement` calls `moveRelative` (line 2063). 26.2 `LivingEntity.travelInAir` selects block friction based on `onGround()` (line 2470) and `handleRelativeFrictionAndCalculateMovement` calls `moveRelative` (line 2677). In both, `Entity.moveRelative` passes current yaw into `getInputVector` (1.16.5 lines 1075–1080; 26.2 lines 1746–1759), which rotates scaled horizontal input into X/Z velocity using sine and cosine of yaw. Thus the directly stored yaw is a live input to real player ground/air velocity, independently of pitch.

## Dependency / limit on a velocity claim

The direct-turn witness proves a yaw-storage difference without evaluating trig and without relying on how mouse deltas were batched. It does not isolate the resulting velocity delta: `Mth` itself changed. 1.16.5 has `sin(float)`/`cos(float)` using `f * 10430.378F` and int truncation into its lookup table (`Mth.java` lines 25–30); 26.2 has `sin(double)`/`cos(double)` using `i * 10430.378350470453`, long conversion, then masking (`Mth.java` lines 50–55). A velocity attribution must independently account for this trig-index behavior. The `MouseHandler` preprocessing also differs; that is bypassed by the direct-call witness, not resolved by it.

## Exact source hashes

| Release / namespace | Relative file | SHA-256 |
|---|---|---|
| 1.16.5 / mojmap | `net/minecraft/world/entity/Entity.java` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` |
| 26.2 / unobfuscated | `net/minecraft/world/entity/Entity.java` | `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5` |
| 1.16.5 / mojmap | `net/minecraft/world/entity/LivingEntity.java` | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` |
| 26.2 / unobfuscated | `net/minecraft/world/entity/LivingEntity.java` | `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a` |
| 1.16.5 / mojmap | `net/minecraft/client/MouseHandler.java` | `2611dceb1078a63a33d621bf02f466ae70f6d015c902813935b9836f39415949` |
| 26.2 / unobfuscated | `net/minecraft/client/MouseHandler.java` | `049c4f21b6e1b724e1f0ed8b6784e55eae11c50ccd88c2a483a7253429ef8c15` |
| 1.16.5 / mojmap | `net/minecraft/util/Mth.java` | `0e4424ffa12923314c309ae13e012768271dfcace4b5a84727f74e8170afe290` |
| 26.2 / unobfuscated | `net/minecraft/util/Mth.java` | `30455c684f2401c79290847822bca82e77162c4a2bcf8618e85cac9898a2dc17` |
