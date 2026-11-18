# 26.1.2 → 26.2: local player swim pitch vertical correction

## Result

No source difference was found in the bounded operation: the ordinary nonpassenger `Player.travel` swim pitch correction, its look-Y calculation, above-fluid lookup and branch order, or the inherited water-travel movement call. No numeric witness applies because the compared operation is equivalent in these two releases. This does not establish when the code was introduced or its behavior in intervening releases.

## Exact source identity

Both trees are `unobfuscated` Vineflower output. The 26.1.2 provenance identifies version `26.1.2`, Java `25.0.3+9-LTS`, Vineflower `1.12.0`; its ready marker is `decompiled_minecraft/26.1.2/unobfuscated.ready.json`, with source-manifest SHA-256 `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0`. The 26.2 provenance identifies version `26.2` with the same Java/Vineflower versions; its ready marker is `decompiled_minecraft/26.2/unobfuscated.ready.json`, source-manifest SHA-256 `a7ad74fc712567eb87136afc1eaFCFD602b4088e9869f3596331fd34faefe894`.

For each side, the ready marker's source-manifest digest matches the actual manifest file. For all six source files listed below, the SHA-256 in that manifest matches the actual source bytes. Original source/manifests were read only.

| Source | 26.1.2 SHA-256 | 26.2 SHA-256 |
|---|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe` | `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6` |
| `net/minecraft/world/entity/player/Player.java` | `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` | `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531` |
| `net/minecraft/world/entity/LivingEntity.java` | `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` | `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a` |
| `net/minecraft/world/entity/Entity.java` | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` | `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5` |
| `net/minecraft/util/Mth.java` | `19d00e9745368c34692d0dfd7eddec07797790ac7f6e0bc579208b7a4b682d33` | `30455c684f2401c79290847822bca82e77162c4a2bcf8618e85cac9898a2dc17` |
| `net/minecraft/core/BlockPos.java` | `881e56bdaa8e852eee71420d2148d7d2a585c7c746d19bf8f466ed06c46f7fd1` | `c86391b8b18c0ba47117a197376adef8d79c8781435cfddc91f2e5806691b899` |

## Trace and comparison

`LocalPlayer.aiStep` calls `super.aiStep()` in both releases (`LocalPlayer.java:914`). `LivingEntity.aiStep` reaches virtual `this.travel(input)` (`LivingEntity.java:3073` in 26.1.2; `3140` in 26.2), so a local player executes the inherited `Player.travel` override.

In `Player.travel` (26.1.2 lines 1381–1404; 26.2 lines 1402–1425), a passenger immediately delegates to `super.travel(input)`. Otherwise, when `isSwimming()` is true, it first reads `double lookAngleY = this.getLookAngle().y`, chooses `0.085` only for `lookAngleY < -0.2` and `0.06` otherwise, then applies the vertical correction when `lookAngleY <= 0.0 || jumping || above-fluid-state-is-nonempty`. The third predicate evaluates `BlockPos.containing(this.getX(), this.getY() + 1.0 - 0.1, this.getZ())`, queries that position's fluid state, and tests `!isEmpty()`. The addition/subtraction grouping and lookup position are identical. Only after this correction does the nonpassenger branch delegate to `super.travel(input)`; the separate flying branch saves current Y, delegates, then scales the resulting Y by `0.6`.

The look-Y dependency is unchanged: `Entity.getLookAngle()` calls `calculateViewVector(getXRot(), getYRot())`. Both `calculateViewVector` bodies compute float `realXRot = xRot * (float)(Math.PI / 180.0)`, float `realYRot = -yRot * (float)(Math.PI / 180.0)`, then float `yCos`, `ySin`, `xCos`, `xSin` via `Mth.cos/sin`, returning `new Vec3(ySin * xCos, -xSin, yCos * xCos)` (`Entity.java:1879–1887` vs `1966–1974`). The Y component thus begins as float `-xSin` and is widened into `Vec3.y`/`double lookAngleY`.

The LUT path is also identical. `Mth.sin(double)` indexes `SIN[(int)((long)(i * 10430.378350470453) & 65535L)]`; `Mth.cos(double)` adds `16384.0` before the same long cast/mask. The float sine table is 65,536 entries populated by `(float)Math.sin(i / 10430.378350470453)`. Therefore this call widens the float radians to double at the method boundary, multiplies in double, truncates to long, masks to 16 bits, and returns the float table value; no input cast, LUT expression, width, or rounding difference was found.

For the third correction gate, `BlockPos.containing(double,double,double)` is unchanged and calls `Mth.floor` independently for x/y/z. `Mth.floor(double)` remains `(int)Math.floor(v)`. The Y argument is evaluated left-to-right as double `((getY() + 1.0) - 0.1)` before floor/conversion to int; this is not a cast-to-int truncation of the player's Y.

The inherited water route matches: `LivingEntity.travel` dispatches fluid travel when `shouldTravelInFluid(...)` is true, and `travelInWater` executes `moveRelative(speed,input)`, then `move(MoverType.SELF,getDeltaMovement())`, before subsequent water slowdown/falling adjustment (`LivingEntity.java:2392–2400, 2467–2493` vs `2430–2438, 2516–2543`). `Entity.moveRelative` and `Entity.move` are inherited by the player; no change to that call sequence affects this correction comparison. The caller/predicate values are treated as environmental inputs; no additional behavior is inferred from them.
