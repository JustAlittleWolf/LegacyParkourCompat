# Independent exact-source destination audit: swim pitch vertical writer, 1.14 → 26.2

## Verdict

**The destination witness is source-valid for the bounded ordinary local player operation.** The pitch-derived vertical look component is read through different float/double lookup-index calculations. At the witness below the selected sine-table slots differ by one, but both components are positive, so the same `0.06` coefficient and the same above-fluid gate admit the vertical-velocity write. In both endpoints the write occurs before the same tick's real self movement consumes delta movement. This establishes the operation-level distinction and its downstream reachability; it does not claim fluid/collision parity or runtime parity.

## Pair and canonical evidence

- Older exact source: Minecraft `1.14`, `ornithe-feather`, `decompiled_minecraft/1.14/ornithe-feather/`.
  - Readiness marker SHA-256: `e0ecb319a63d5d664b87ad7c00638e1cdb9dc302ee2e05bbc05a88ce96d1e60b`.
  - Provenance SHA-256: `bfb011801a64e9afaa4aca43e8a8ed40bf2a02f136ffc02e65b32e79315764fa`.
  - Source manifest SHA-256, also asserted by the ready marker: `68bc39e38bc501e7c9af315de4678263e1a64606b0b9780099187c7984ccbc98`.
  - Provenance pins Feather Gen2 `1.14+build.2` mergedv2 JAR SHA-256 `57f0e3227099d010c4a0c7f2fea03dbd665bc7e612ad204db80fa5b5728e65a3` and Tiny SHA-256 `d865d01921c115a1c20dfdc1b7cdca1c59ff1694cad54bd79e146d553417d31f`.
  - The separate `decompiled_minecraft/1.14/yarn/` tree has no current ready marker/sidecar manifest; it was not used as canonical evidence.
- Newer exact source: Minecraft `26.2`, `unobfuscated`, `decompiled_minecraft/26.2/unobfuscated/`.
  - Readiness marker SHA-256: `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f`.
  - Provenance SHA-256: `a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1`.
  - Source manifest SHA-256, also asserted by the ready marker: `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`.
  - The ready marker reports `ready`, version `26.2`, namespace `unobfuscated`, 7,055 files / 33,813,717 source bytes and movement-source inspection including `Entity.move`, `Entity.moveRelative`, `LivingEntity.travel`, and `Player.travel`.

Direct SHA-256 checks of all relied Java source files matched their listed source-manifest entries:

| Side | Relative source file | SHA-256 |
|---|---|---|
| 1.14 | `net/minecraft/entity/living/player/PlayerEntity.java` | `f73879bd42103fa45f39cfe178ac82c7574c6306f00555af7f7e795976275dd7` |
| 1.14 | `net/minecraft/entity/living/LivingEntity.java` | `1db4dd1aa95a06c48511e5567c0d3836f2a6e5a12a5cf03ab5c892e429198b79` |
| 1.14 | `net/minecraft/entity/Entity.java` | `15e9d4cde45d857ca3117ccfe2d5d9071c08e7ee4a67b4053f7b006fe7a4bfa9` |
| 1.14 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `2b1ad3b3416949a9da2607a3ec2251aa69b2d9eebd76638c92e20749d625338f` |
| 1.14 | `net/minecraft/util/math/MathHelper.java` | `e39d5dfc69c17a9032be086d7d4d14cc63525962a0ff83615b1befdcca599843` |
| 26.2 | `net/minecraft/world/entity/player/Player.java` | `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531` |
| 26.2 | `net/minecraft/world/entity/LivingEntity.java` | `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a` |
| 26.2 | `net/minecraft/world/entity/Entity.java` | `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5` |
| 26.2 | `net/minecraft/client/player/LocalPlayer.java` | `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6` |
| 26.2 | `net/minecraft/util/Mth.java` | `30455c684f2401c79290847822bca82e77162c4a2bcf8618e85cac9898a2dc17` |
| 26.2 | `net/minecraft/world/phys/Vec3.java` | `026c8461113b32116f0183ec42fa1cb4c7414cec38b685484d2309f9a0cb9a33` |

## Bound source trace

- **1.14** `PlayerEntity.moveRelative(Vec3d)`, `PlayerEntity.java:1362–1374`, runs the player-specific swim pitch writer before `super.moveRelative`. For `isSwimming() && !isRiding()`, it reads `getLookVector().y`, chooses `h = 0.085` only below `-0.2`, otherwise `h = 0.06`, then writes `velocity += (0, (g - velocity.y) * h, 0)` iff `g <= 0`, jumping, or the sampled block at `(x, y + 1.0 - 0.1, z)` has nonempty fluid.
- **1.14 lookup** `Entity.getLookVector()` → `getRotationVector(float,float)`, `Entity.java:1169–1188,1632–1633`, computes pitch radians in `float`, then `MathHelper.sin(float)` / `.cos(float)`; `MathHelper.java:24–29` indexes `SINE_TABLE[(int)(x * 10430.378F) & 65535]`. The y component is `-k` where `k` is the float lookup result widened into `Vec3d`.
- **1.14 real movement** `LivingEntity`'s travel phase constructs movement input and calls `moveRelative(...)` (`LivingEntity.java:2214–2221`). Its water branch then calls `move(MoverType.SELF, velocity)` before water drag (`LivingEntity.java:1875–1904`). The local player is on this client path; the superclass movement gate admits a locally controlled entity.
- **26.2** `Player.travel(Vec3)`, `Player.java:1402–1424`, skips the writer for passengers; otherwise it reads `getLookAngle().y`, chooses the same coefficient (`< -0.2 ? 0.085 : 0.06`), applies the same `g <= 0 || jumping || sampled fluid nonempty` gate at `BlockPos.containing(x, y + 1.0 - 0.1, z)`, and calls `setDeltaMovement(delta + (0, (g - delta.y) * multiplier, 0))` before `super.travel(input)`.
- **26.2 lookup** `Entity.getLookAngle()` → `calculateViewVector(float,float)`, `Entity.java:1966–1973,2579–2581`, computes pitch radians as `float`, then calls `Mth.sin(float)` (the argument widens to the only `sin(double)` overload). `Mth.java:50–52` indexes `SIN[(int)((long)(i * 10430.378350470453) & 65535L)]`; the returned float y component is widened into `Vec3`.
- **26.2 real movement** the local player reaches inherited `LivingEntity.aiStep` movement simulation (`LivingEntity.java:3137–3141`), which calls `Player.travel(input)`. The nonflying `Player.travel` then calls `super.travel`; `LivingEntity.travel` selects fluid travel, whose water route invokes `moveRelative`, then `move(MoverType.SELF, deltaMovement)` before post-move water adjustment (`LivingEntity.java:2430–2437,2516–2543`). This is the same-tick consumer of the writer.

## Legal distinguishing witness

Use one ordinary local player with current/held pitch `-22.74169921875F`, yaw `0`, active swimming, not a passenger, not flying, not jumping, initial vertical velocity `0`, and the fluid state at the sampled `(x, y + 0.9, z)` cell nonempty (water). Choose ordinary forward input; input does not participate in this vertical writer. This is a legal upward-looking pitch and satisfies the positive-look gate through the above-cell fluid clause.

For both versions the radian intermediate is the same `float`, approximately `-0.39691752195358276F`. In 1.14 the float product/index selects slot **61396**; in 26.2 the widened-float/double product with the double scale selects slot **61397**. Both slots are in the negative-sine half of the table, hence `g = -sin(slot)` is positive in both cases; the selected coefficient is therefore `0.06` in both, and the water above admits the writer. The table values are approximately `g14 = 0.38658` and `g26 = 0.38649`. From zero vertical velocity, the pre-move write yields approximately `0.0231946` versus `0.0231893` vertical velocity. Each version then feeds that written velocity to its same-tick `move(SELF, velocity)` call. The movement call is real and reachable; later fluid drag/gravity/collision behavior is outside this bounded audit.


## Blind audit of the immutable author report

After freezing the independent trace above at SHA-256 `F001BFF780F0C6BE2045B781D1CB2675974B66F6AA61D9CFB01F2B461AC3E39F`, I checked the lab report. Its bytes match the requested SHA-256 `b2cdec90e9aaf109dcfad42ce8703b5521da9e469ba714646552f1d2b980d2b7`. The report's bounded claim is **accepted** against the canonical pair evidence identified above.

I independently reproduced the author's `84.375F` witness from the source expressions: radians `1.4726215600967407F`; 1.14 float product `15359.9990234375F` → slot `15359`; 26.2 widened-float/double product `15360.000038869068` → slot `15360`; old/new Y approximately `-0.995175302028656` / `-0.9951847195625305`. Since both Y values are negative, both choose `0.085` and short-circuit the admission gate at `lookY <= 0`. The report correctly scopes the fluid cell as a separate input and does not rely on it for this witness.

I also independently checked the actual local player destination paths and source identities. The 1.14 `LocalClientPlayerEntity.move` (`.../LocalClientPlayerEntity.java:847–850`) delegates to `super.move`; 26.2 `LocalPlayer.move` (`.../LocalPlayer.java:986–989`) does likewise. The corresponding source hashes are the ones listed above. The report's block-coordinate dependency is supported: 1.14 `BlockPos(double,double,double)` delegates to `Vec3i(double,double,double)` and floors coordinates; 26.2 `BlockPos.containing` floors all three coordinates. Direct source SHA-256 values are `83e69c47ac85b6df0a625123201b57c3a83bf00d407720a218e831382265e599` (1.14 BlockPos), `f1d2e85d690b69c546b2dc783ede2ddbffac400ca72271828929a45db03729a9` (1.14 Vec3i), and `c86391b8b18c0ba47117a197376adef8d79c8781435cfddc91f2e5806691b899` (26.2 BlockPos); each matched its source manifest.

For a witness that exercises the positive-look branch and uses the above-cell input, my independent legal alternative is held pitch `-22.74169921875F`, yaw `0`, active swimming, local, nonpassenger, nonflying, not jumping, zero initial vertical velocity, forward input, and nonempty fluid at `(x, y+0.9, z)`. It selects slots `61396` and `61397`; both table values give positive look-Y, so both choose `0.06`, and the nonempty above-cell fluid admits the write. The report's witness and this alternate witness both reach the same pre-move vertical writer and real same-tick `move(SELF, deltaMovement)` call.

No source mismatch or destination defect was found in the assigned operation. This verdict accepts that operation-level source witness only; it closes neither full-pair source coverage nor runtime parity.
