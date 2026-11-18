# Independent audit verdict: 1.16.5 → 1.17 turn pitch and Elytra witness

## Verdict

**Accepted for this bounded claim:** exact Mojmap source exposes a reachable finite mouse-input witness where `LocalPlayer`'s inherited `Entity.turn` produces different in-range pitch values, and the next dry, active, nonpassenger Elytra travel produces different velocity and unobstructed displacement. The witness isolates the `turn` arithmetic: both clients start from the same legal state and use the same exact mouse caller values. This does not claim full pair coverage or pair freeze.

## Exact source identity

Both sides are the canonical read-only decompiler outputs at `decompiled_minecraft/<version>/mojmap/`.

- **1.16.5 Mojmap:** `mojmap.provenance.json` identifies release `1.16.5`, namespace `mojmap`, Vineflower 1.12.0 / Java 25; SHA-256 `b71df61eb775134119881915198f87c4337bae409e1f0de8d8d6c007e062902d`. `mojmap.ready.json` SHA-256 `e9dd4397c6ade5f68d27f50baf285086dc367b56d2119b03a116cf3cf3b23fee` records the 3,523-source manifest SHA-256 `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`. In `artifacts.sha256`, the exact client JAR is `00b5ebbc33e95ea88c1ab80601599c9827e9aa861d93ccc2cfcbbfd996e86263` and official Mojang `client_mappings.txt` is `7931ed6d723eceb1d621d05a76e10ddf643bf468c6ecf1c4ecf377bd72cf8b8c`. The audited source files below match the source manifest entries.
- **1.17 Mojmap:** `.decompile-provenance.json` SHA-256 `afc9dc30cd8f6406d8ea306e64cc0b3601cc8d7c7bcd314f737573d830a79357`; its exact 1.17 metadata input is `1.17.json` SHA-256 `537c6fa7e6ab13caa76ce2e84e4748882d36fcd5a076fa6903f894880e1df0f6`, client JAR SHA-256 `14122287d4e4256c2d65ec8c0ccfa3b56448495fa3ccabc87d0f0eeb4fa1c30f`, and official Mojang `client.txt` SHA-256 `191b957aaa40b294402feef29651ae7a9f1263ec22c5382f3b8fa0a4918b0471`. The audited source files below match the per-file hashes recorded in this provenance manifest.

Per-file SHA-256 (all hashes were checked against their side's published source manifest):

| Mojmap source | 1.16.5 | 1.17 |
|---|---|---|
| `net/minecraft/client/MouseHandler.java` | `2611dceb1078a63a33d621bf02f466ae70f6d015c902813935b9836f39415949` | `6ff70f8634171709ba08a42158aea7c8d4d3974d73f89b43bfe08a5ca6db5fb2` |
| `net/minecraft/world/entity/Entity.java` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` | `81231ad8ac1b9a7a9f7ee186352c7e73c0c5211a3034c3a35c04badc6914c8ef` |
| `net/minecraft/world/entity/LivingEntity.java` | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` | `8e81e5f1317d154fa41b676721ff581badd46296f77bcebcadb3c63448e528ed` |
| `net/minecraft/util/Mth.java` | `0e4424ffa12923314c309ae13e012768271dfcace4b5a84727f74e8170afe290` | `24515c4549e01e985017227dccf7159166a675b232be9135d5f896022a9cb113` |
| `net/minecraft/world/phys/Vec3.java` | `b889010c321c45b497e71ff782d108a718ed23a5f605d6162904045e1e01fd48` | `eb52a20224767daeb5ddc500552c673df2daf9cd6868d7e74356411e94ef402e` |
| `net/minecraft/world/entity/player/Player.java` | `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960` | `9ad4c439814caf5f51cf6da9a86c5d22906b0a28b5b606bdeb1b770617d90516` |
| `net/minecraft/client/player/LocalPlayer.java` | `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b` | `08dbb8615cc6c7776f35ce269223699d39f758d15786babfe11e497421c5f4d4` |
| `net/minecraft/client/Options.java` | `431ed10af0ee5d083a007a18208c6b117541c4cd4e4d37ad7ddd1a591de61190` | `30b91d23563e5b6ffd903e1f6e0dccc39352eb13dbe5e1aaa6d3a0e36118706e` |
| `net/minecraft/client/Option.java` | `bbe06b4facdae62dbd17628b4a7deed40903b46ffbc1cff67061bf88994404bf` | `c02cdb296175617a8762f72874ca30a532424d9a182f665a021cf6f9a7744aa7` |
| `net/minecraft/client/Minecraft.java` | `3fc48eb93d2dc001496c11b66071db939cdaa3f1ff7c4e5b2b391e25a715facc` | `ba5c7d5cfe7ffc6253a98df049035abfa113ff6860137d4e914427672f2f943d` |

## Caller and legal state

`Minecraft.player` is `LocalPlayer` (1.16.5 `Minecraft.java:307`; 1.17 `:332`). `MouseHandler.turnPlayer` calls `minecraft.player.turn(...)` (1.16.5 `MouseHandler.java:212–242`; 1.17 `:208–244`). `Options.sensitivity` is a `double` (1.16.5 `Options.java:49`; 1.17 `:64`); `Option.SENSITIVITY` bounds it inclusively to `[0.0, 1.0]` (1.16.5 `Option.java:234–241`; 1.17 `:239–246`).

Use this caller state on both versions:

- Sensitivity `1.0`; mouse grabbed and window active; smooth camera off; invert-Y off; camera not in the first-person-scoping path; accumulated cursor Y displacement `+1.0` (e.g. prior Y `0.0`, event Y `1.0`); accumulated X displacement `0.0`.
- The mouse event accumulation in `MouseHandler` makes `accumulatedDY = 1.0`; the option expression uses `double` arithmetic with float literals: `f = 1.0 * 0.6F + 0.2F = 0.8000000268220901`, then the non-smoothed mouse scale is `f*f*f*8.0 = 4.096000411987318`. In 1.17, `g=f*f*f; h=g*8.0` yields the same double for this non-scoping path. Thus `Entity.turn` receives `d=0.0`, `e=4.096000411987318` in both.
- Pre-turn current pitch is `xRot = -4.0696001052856445F`, a finite in-range angle; prior `xRotO` can equal it. The resulting pitches also lie strictly within `[-90F, 90F]`, so neither clamp changes them.

The paired operation differs at 1.16.5 `Entity.turn` lines 340–352 versus 1.17 lines 377–389. In 1.16.5, `e*0.15` is computed as `double`, added to float `xRot` after promotion, and the sum is cast to float. In 1.17, `e` is first cast to float, multiplied by `0.15F`, then added as float through `setXRot`. For the state above:

| Intermediate/result | 1.16.5 | 1.17 |
|---|---:|---:|
| pitch delta passed to addition | double `0.6144000617980977` | float `0.6144000291824341` |
| current pitch after `turn` | `-3.455199956893921F` | `-3.4552001953125F` |

This is a source-derived mouse caller, not an arbitrary out-of-range or infinite direct method argument.

## Next dry Elytra travel

For the next travel call, use `onGround=false`, `isFallFlying()=true`, `isPassenger()=false`, no water/lava, `abilities.flying=false`, no collision, yaw `0F`, current delta movement `(0.1, -0.1, 0.0)`, and no movement keys (`travel(Vec3.ZERO)`). An open, dry location such as `(0,100,0)` keeps the requested movement unobstructed. `LocalPlayer.isEffectiveAi()` returns `true` on both sides (1.16.5 `LocalPlayer.java:494–496`; 1.17 `:501–503`), so `LivingEntity.travel` executes. `LivingEntity.aiStep` calls `this.travel(new Vec3(xxa,yya,zza))` (1.16.5 `LivingEntity.java:2456–2459`; 1.17 `:2539–2542`). `Player.travel` delegates to `super.travel` when abilities flight is off (1.16.5 `Player.java:1405–1414`; 1.17 `:1436–1446`). The dry `isFallFlying()` branch is 1.16.5 `LivingEntity.java:1985–2010` and 1.17 `:2064–2096`.

`Entity.calculateViewVector` is the same float-radian and `Mth` lookup path in both versions (1.16.5 `Entity.java:1243–1250`; 1.17 `:1337–1344`). `Mth` builds a 65,536-entry float sine table and uses `(int)(f * 10430.378F) & 65535` for sine and the corresponding `+16384.0F` for cosine (1.16.5 `Mth.java:12–30`; 1.17 `:27–49`). For the witnessed post-turn pitch, `h=j=pitch*(float)(PI/180)` is `-0.06030461564660072` (1.16.5) and `-0.06030461937189102` (1.17). `Mth.sin` multiplies by `10430.378F`: the float products are `-628.9999389648438` and `-629.0`, truncating to indices **64908** and **64907** respectively. The sine table values are `-0.06017237529158592` and `-0.06026807427406311`; `Entity.calculateViewVector` negates them for look Y, giving `+0.06017237529158592` and `+0.06026807427406311`. The cosine lookup adds `16384.0F` and selects index **15755** on both sides, giving look Z `0.9981822371482849`. The travel `j` sine lookup crosses the same index boundary. Its `k` is `0.9981822371482849`, horizontal speed `l` is `0.1` on both sides, and `n` is `0.9963677525520325` on both sides. The calculated friction-adjusted delta movement is:

| Next Elytra travel result | 1.16.5 | 1.17 |
|---|---:|---:|
| x | `0.08910000085830688` | `0.08910000085830688` |
| y | `-0.10532021111523943` | `-0.10531901066717987` |
| z | `0.020358057324752363` | `0.020357716253575527` |

With no collision, `move(SELF, getDeltaMovement())` requests that same displacement on the next movement. Thus the `turn` pitch rounding difference survives the historical trig lookup and changes the next movement by `(+0.0, +1.2004480595545486e-6, -3.4107117683618293e-7)` in 1.17 relative to 1.16.5.

## Coverage limit

This validates the requested direct turn → next dry active Elytra movement witness only. It does not establish complete 1.16.5→1.17 movement inventory, patch cutover, runtime parity, or full-pair audit. The 1.17 `Vec3.horizontalDistance()` helper is `sqrt(x*x+z*z)` (`Vec3.java:131–132`), matching 1.16.5 `Entity.getHorizontalDistanceSqr()` (`Entity.java:709–711`) followed by `Math.sqrt`; with the selected `(0.1,0.0)` horizontal state both sides produce `l=0.1`. No dependency remains open for this numeric witness.

## Phase-two comparison (frozen source report)

The frozen source report was rehashed before review: SHA-256 `f94d39bf82443713e9ae0fdbb6f9b835aea0a38d536f0a19012d5eadb6546525`, matching the authorized identity. I did not edit it. Its shared exact source-file hashes agree with the manifest-verified source identities above. The precise side is Mojang's canonical `1.16.5/mojmap` and `1.17/mojmap`: the 1.16.5 artifact pin names `client_mappings.txt`; 1.17's embedded provenance pins Mojang `client.txt` and the 1.17 client metadata. These are concrete release IDs, not a `latest` or patch alias.

The frozen report's *claimed mouse callback* does not supply its stated `turn(0, 4)` for sensitivity `0.5`. Both `Options.sensitivity` fields are `double` (1.16.5 `Options.java:49`; 1.17 `:64`), so the source's `0.6F` and `0.2F` operands are widened to double in `sensitivity * 0.6F + 0.2F`. At the report's sensitivity `0.5` and `accumulatedDY=4`, source arithmetic gives `f=0.5000000149011612`, multiplier `f*f*f*8=1.0000000894069698`, and the actual pitch argument `e=4.000000357627879`, not `4.0`. Re-evaluating its stated initial pitch `1.4599311351776123F` with this exact caller value gives **the same current `xRot` on both sides: `2.0599312782287598F`**. The current pitch used by `getLookAngle` therefore has no 1.16.5/1.17 difference for that callback witness; its claimed next-move delta is not established by the stated ordinary mouse path. The reported direct-method calculation at `e=4.0` may describe an isolated `Entity.turn` call, but it does not match the mouse state and caller specified in that report.

My independent witness above uses a legal sensitivity endpoint `1.0` (the `Option.SENSITIVITY` interval is inclusive `[0.0,1.0]`), `accumulatedDY=1.0`, smooth camera off, invert-Y off, and the non-scoping 1.17 path. With the exact double mouse calculation it actually calls `turn(0.0, 4.096000411987318)`. From the same finite in-range starting pitch `-4.0696001052856445F`, the source-derived current pitches are `-3.455199956893921F` and `-3.4552001953125F`; their actual float sine products truncate to indices `64908` and `64907`. In the next travel call, additionally set `isSwimming=false` (along with the dry, active, nonpassenger, non-ability-flight, no-collision state already specified above) so `Player.travel`'s pre-super swimming adjustment is bypassed. The resulting requested motion vectors in the original verdict are isolated to this pitch/trig divergence: the relevant flight equations match for these inputs, and the 1.17 `Vec3.horizontalDistance()` body is the same `sqrt(x*x+z*z)` arithmetic as 1.16.5's helper followed by `Math.sqrt`. In open space those requested vectors are also the displacement; no collision-resolved trajectory is claimed.
The mouse-coordinate witness assumes the cursor baseline is already initialized (`ignoreFirstMove=false`): both `MouseHandler.onMove` bodies seed `xpos/ypos` during the first-move event before calculating the accumulated delta, so that event itself contributes zero. Use the later `+1.0` Y event for the stated accumulated value. This closes that caller-state prerequisite in addition to the explicit `isSwimming=false` movement gate above.