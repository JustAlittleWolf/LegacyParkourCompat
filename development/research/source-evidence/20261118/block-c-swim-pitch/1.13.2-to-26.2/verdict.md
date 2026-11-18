# Independent blind source verdict — swim pitch vertical adjustment

## Freeze and scope

This independent source reconstruction was frozen before opening `c-swim-pitch-vertical-1132-262-destination-witness/report.md`. It compares only the voluntary swimming-pitch direct vertical-velocity adjustment and its immediate local-player movement path in exact 1.13.2 Ornithe Feather and 26.2 unobfuscated decompiled sources. The environment is used only to supply the truth value of the above-position fluid predicate; no fluid simulation, block-response result, gameplay, test, build, or runtime claim is made.

Host/execution environment: Windows host, PowerShell, primary checkout `D:\Javastuff\LegacyParkourCompat`. Canonical decompiled sources were read only.

## Exact evidence identity

The 1.13.2 source is `decompiled_minecraft/1.13.2/ornithe-feather`, namespace Ornithe Feather, exact release `1.13.2`; provenance identifies Feather Gen2 build 2 (`feather-gen2-1.13.2+build.2-mergedv2.jar` and `feather-gen2-1.13.2+build.2.tiny`), Vineflower 1.12.0, Java 25. Its ready marker binds source manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; that manifest's SHA-256 is the same.

The 26.2 source is `decompiled_minecraft/26.2/unobfuscated`, exact release `26.2`, published unobfuscated namespace, Vineflower 1.12.0, Java 25. Its ready marker binds source manifest SHA-256 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`; that manifest's SHA-256 is the same.

Source-file SHA-256 values below were recomputed from file bytes and match the corresponding entry in each bound `.sources.sha256` manifest.

| 1.13.2 Ornithe Feather source | SHA-256 |
| --- | --- |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633` |
| `net/minecraft/entity/living/LivingEntity.java` | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` |
| `net/minecraft/entity/Entity.java` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |
| `net/minecraft/util/math/MathHelper.java` | `2211b0cb3da7d8789a4a4df96509b95e6b1be8b222be2f530e7bd0695573bd69` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf` |

| 26.2 unobfuscated source | SHA-256 |
| --- | --- |
| `net/minecraft/world/entity/player/Player.java` | `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531` |
| `net/minecraft/world/entity/LivingEntity.java` | `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a` |
| `net/minecraft/world/entity/Entity.java` | `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5` |
| `net/minecraft/util/Mth.java` | `30455c684f2401c79290847822bca82e77162c4a2bcf8618e85cac9898a2dc17` |
| `net/minecraft/client/player/LocalPlayer.java` | `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6` |

## Independently reconstructed operation and path

In 1.13.2, `PlayerEntity.moveRelative(float,float,float)` at `PlayerEntity.java:1442` first saves position, then tests `isSwimming() && !isRiding()`. It obtains `double g = this.getLookVector().y`, selects `double h = g < -0.2 ? 0.085 : 0.06`, and only when `g <= 0.0 || this.jumping || aboveBlockFluidIsNotEmpty` writes `velocityY = velocityY + (g - velocityY) * h`. The block predicate is `getBlockState(new BlockPos(x, y + 1.0 - 0.1, z)).getFluidState().isEmpty()` negated. It then dispatches to `super.moveRelative(...)` (or the flying branch, excluded by this witness). `LivingEntity.tick`'s travel section calls virtual `moveRelative` at `LivingEntity.java:1920-1926`; on a local player, `LocalClientPlayerEntity.isLocallyControlled()` enables the inherited movement implementation. That inherited movement path reaches its ordinary `move(MoverType.SELF, velocityX, velocityY, velocityZ)` call (`LivingEntity.java:1478 onward; ordinary movement call at line 1640`), after the player's pitch adjustment.

The old look-Y comes from `Entity.getLookVector()` → `getRotationVector(this.pitch,this.yaw)` (`Entity.java:1691, 1251-1258`). The pitch is a float; radians are formed as `pitch * (float)(Math.PI / 180.0)`, then the float `MathHelper.sin(float)` table result is negated for Y. `MathHelper.sin` indexes its 65,536-float LUT with `(int)(x * 10430.378F) & 65535` (`MathHelper.java:24-25`). The returned float Y is promoted into the `double g` local; the threshold constants and velocity update are double arithmetic.

In 26.2, `Player.travel(Vec3)` at `Player.java:1402` skips the adjustment for passengers; otherwise it checks `isSwimming()`, gets `double lookAngleY = this.getLookAngle().y`, chooses `double multiplier = lookAngleY < -0.2 ? 0.085 : 0.06`, and applies `setDeltaMovement(movement.add(0.0, (lookAngleY - movement.y) * multiplier, 0.0))` only when `lookAngleY <= 0.0 || this.jumping || aboveFluidStateIsNotEmpty`. Its predicate uses `level().getFluidState(BlockPos.containing(x, y + 1.0 - 0.1, z)).isEmpty()` negated. It then invokes inherited `LivingEntity.travel(input)`. For a water swimmer that path reaches `travelInWater`; that method invokes `moveRelative(speed,input)` and then the real `move(MoverType.SELF, this.getDeltaMovement())` before water drag and fluid-falling adjustment (`LivingEntity.java:2430, 2504-2536`). The adjustment therefore changes movement submitted to the normal inherited move; it is not a position write.

Modern look-Y is `Entity.getLookAngle()` → `calculateViewVector(getXRot(),getYRot())` (`Entity.java:2579-2580, 1966-1973`). The degree-to-radian multiplications and trig outputs remain float; the vector component is stored as double. However, `Mth.sin(double)` indexes its 65,536-float LUT with `(int)((long)(i * 10430.378350470453) & 65535L)` (`Mth.java:37-51`), and its table initialization is `sin(i / 10430.378350470453)`. This is not the old float multiply/truncation/table construction, even though the sample below lands on the same index and table float.

Player swimming guards also exclude flying and spectators on both sides (`PlayerEntity.java:1819-1820`; `Player.java:1685-1686`). For the selected witness, a local, alive, non-spectator, non-flying, non-riding player is swimming in water and has `jumping == false`. The probe position is in the interior of a fluid cell and its fluid state is non-empty, so both above-fluid predicates are true without depending on boundary conversion. Thus the OR gate is active solely through the above-fluid term. A normal local movement tick is eligible: the old local player reports local control and enters virtual `moveRelative`; the modern local player's normal tick/aiStep supplies the ordinary input and the inherited travel branch applies it. No jump impulse is included in the witness.

## Frozen numeric witness and verdict

Use current pitch `-30.0F`, arbitrary yaw (Y is yaw-independent), initial vertical velocity `+0.0D`, and the preceding local swimming state/gates just described. In each source the float radians are `-0.5235987901687622F`. Old float multiplication produces `-5461.33349609375F`; modern double multiplication after float radians produces `-5461.333485308779D`. Both truncate toward zero, mask to LUT index `60075`, and return the same measured look-Y float `+0.49997231364250183F`, promoted to double. Thus the `g < -0.2` / `lookAngleY < -0.2` threshold is false and the selected coefficient is `0.06D`.

The direct vertical adjustment, before inherited movement, is in both cases `0.0D + (0.49997231364250183D - 0.0D) * 0.06D`, yielding `+0.029998338818550112D` (the exact binary double result represented by this decimal). For this concrete witness the two methods agree on direct adjusted vertical velocity. The inherited movement and its later environment-dependent drag/gravity are not included in that value.

**Conclusion:** the operation is behaviorally aligned for this witness, including the active gate, coefficient, look-Y float value, and direct double result. It is **not exact source equivalence**: the old write is in player `moveRelative` before inherited relative movement, while the modern write is in player `travel` before inherited travel; their lookup arithmetic/table construction and predicate call sites also differ. The shared witness result cannot establish equal LUT outputs or equal movement for every pitch/state. No broader parity or runtime conclusion follows.

## Frozen source body references

- 1.13.2 player adjustment and swimming override: `decompiled_minecraft/1.13.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java:1442-1470,1819-1820`.
- 1.13.2 rotation and LUT: `decompiled_minecraft/1.13.2/ornithe-feather/net/minecraft/entity/Entity.java:1251-1258,1691-1693`; `.../net/minecraft/util/math/MathHelper.java:11-29`.
- 1.13.2 local / inherited tick and movement: `decompiled_minecraft/1.13.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:181 onward,489`; `.../net/minecraft/entity/living/LivingEntity.java:1478 onward,1890-1928,1523-1640`.
- 26.2 player adjustment, swimming override and travel: `decompiled_minecraft/26.2/unobfuscated/net/minecraft/world/entity/player/Player.java:1402-1428,1685-1686`.
- 26.2 look vector and LUT: `decompiled_minecraft/26.2/unobfuscated/net/minecraft/world/entity/Entity.java:1966-1973,2579-2580`; `.../net/minecraft/util/Mth.java:15-55`.
- 26.2 inherited fluid travel / actual move: `decompiled_minecraft/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java:2430-2536`; local player input/tick path: `decompiled_minecraft/26.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java:227 onward,760-850`.

No destination witness/report content was consulted in forming or freezing this verdict.

## Post-freeze comparison addendum

After freezing the source verdict above (pre-report verdict SHA-256 `DE1C1DBAEE53A496DDEC3CC350501D4387B2356F6EF396E8B35133AA4FD716A7`), I verified and opened the destination report. Its bytes match the assigned report SHA-256 `676D98D08A99735C2CCA1C5E5642945D0D8806F1DEDC39AF741D5E4C00DED76D`.

The report describes a reachable submerged sprinting local player and a water-filled sample cell, and its generic source claim says the look-Y helper bodies are source-equivalent. The first is a viable gate witness. The latter is contradicted by the exact trig-LUT expressions: same helper-level float radians and `-sin` component do not imply same table index or table element for every legal pitch. This is not merely an abstract difference in implementation.

For a second ordinary legal look pitch, `-38.594970703125F`, the float radians are `-0.6736093163490295F`. 1.13.2's float multiplication gives `-7025.99951171875F`, truncates to `-7025`, and masks to index `58511`; 26.2's widened-float/double multiplication gives `-7026.00002992212D`, truncates to `-7026`, and masks to index `58510`. The respective LUT look-Y results are `+0.6237360835075378F` and `+0.6238110065460205F`. With the same non-jump, non-riding local-swim state, a nonempty above-position fluid predicate, and initial `velocityY = 0.0D`, both pass the gate and use `0.06D`; the direct writes are `+0.037424165010452266D` and `+0.03742866039276123D`, a modern-minus-old difference of `+0.000004495382308962459D` before inherited movement. This calculation uses the exact float/double operations and table construction expressions read from the cited sources.

Therefore, the destination report's **broad look-helper/source-equivalence claim is incorrect**. Its stated concrete setup does not give a numeric pitch, so the report alone does not establish whether its one unspecified-pitch sample lands on an equal or unequal lookup result. My `-30.0F` frozen witness does land on equal indices and outputs; that equality is witness-specific. Exact source equivalence and general source parity are rejected. The ordinary movement ordering remains aligned at the bounded pre-move stage: each direct correction precedes the inherited input acceleration and real move in this branch. The counterexample refines, rather than replaces, the frozen source finding.
