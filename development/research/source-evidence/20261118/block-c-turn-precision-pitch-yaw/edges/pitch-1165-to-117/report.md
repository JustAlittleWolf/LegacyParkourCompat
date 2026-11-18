# `Entity.turn` pitch precision → Elytra move request

## Finding

In the exact 1.16.5→1.17 Mojmap sources, ordinary in-range pitch turning changes from double-intermediate arithmetic to float-intermediate arithmetic. The difference propagates through the next dry, active Elytra `LivingEntity.travel` branch and changes the `Vec3` passed to the local player's `move(MoverType.SELF, ...)`. This is an independently observable player-movement delta, not merely a rotation-field representation difference.

## Exact source evidence

- 1.16.5 Mojmap: `net/minecraft/world/entity/Entity.java`, `turn`, lines 340–352. It calculates `e * 0.15` as `double`, then assigns `(float)(xRot + f)` and clamps. `net/minecraft/world/entity/LivingEntity.java`, `travel`, lines 1914–2010, reads current `xRot` in the active Elytra path and issues `move(SELF, getDeltaMovement())` at line 2008. The angle lookup path is `Entity.getLookAngle`/`calculateViewVector`, lines 1243–1251 and 1700; sine/cosine table indexing is `net/minecraft/util/Mth.java`, lines 12–30.
- 1.17 Mojmap: `net/minecraft/world/entity/Entity.java`, `turn`, lines 377–390. It calculates `(float)e * 0.15F`, adds in float, calls the rotation accessors/setters, and clamps. `net/minecraft/world/entity/LivingEntity.java`, `travel`, lines 1999–2093, reads the current pitch through `getXRot()` and issues the same `move(SELF, getDeltaMovement())` call at line 2093. `Entity.getLookAngle`/`calculateViewVector` are at lines 1337–1345 and 1829; `Mth.sin/cos` and its 65,536-entry sine table are at lines 26–48.
- Callback path: in both `MouseHandler`, cursor movement accumulates `accumulatedDY` (1.16.5 line 202; 1.17 line 198) and `turnPlayer` calls `minecraft.player.turn(...)` (lines 212–242; 208–244). `LocalPlayer.aiStep` calls `super.aiStep` (1.16.5 line 788; 1.17 line 812); `LivingEntity.aiStep` calls `travel` (1.16.5 line 2459; 1.17 line 2542). `LocalPlayer.isEffectiveAi()` is true in both versions (1.16.5 line 494; 1.17 line 501), satisfying the travel gate for a local player. Its `move` override passes the requested vector to `super.move` (1.16.5 lines 863–868; 1.17 lines 895–900).

## Reproducible callback and movement witness

Use the same setup on each side: a live local player, not riding or a passenger; mouse grabbed and window active; cursor position already initialized with first-move suppression cleared; move the cursor from Y=100 to Y=104; smooth camera off; invert-Y off; sensitivity `0.5F`; not scoping; current pitch initially the exact float `1.4599311351776123F` (`0x3fbadf06`); yaw `0`; delta movement `(0.0, -0.2, 0.3)`; dry air, no relevant effects, no horizontal collision, and Elytra flight already active and retained by `updateFallFlying`. This pitch and resulting pitch are finite and well inside the clamp. At sensitivity `0.5F`, both ordinary mouse paths produce multiplier `1.0`; the callback therefore lawfully supplies `turn(0.0, 4.0)`. (The 1.17 scoped-camera special branch is excluded by the stated non-scoping condition.)

Exact pitch operation sequence:

1. 1.16.5 computes `f = 4.0 * 0.15` in double (`0.6`), adds the exact initial float promoted to double, rounds once to float, and clamps: `xRot = 2.0599310398101807F` (`0x4003d5e9`).
2. 1.17 rounds `e` to float, multiplies by `0.15F`, giving `0.6000000238418579F` (`0x3f19999a`); float addition lands exactly at the midpoint between adjacent float results and ties to even: `xRot = 2.0599312782287598F` (`0x4003d5ea`).
3. On the next local AI step, `LivingEntity.travel` passes its AI/control gate; the dry water/lava checks fall through to `isFallFlying()`. For this state the glide code calculates `j = pitch * (float)(PI/180)`: 1.16.5 `0.035952579230070114F` (`0x3d134303`), 1.17 `0.03595258295536041F` (`0x3d134304`). `Mth.cos(j)` indexes `SIN[16758]` versus `SIN[16759]`, changing the gliding coefficient `n` to `0.9987148642539978F` versus `0.9987079501152039F`.
4. Following the source's `getLookAngle`, glide-velocity updates, gravity/steering terms and drag in order, the actual vector argument at `LocalPlayer.move(MoverType.SELF, ...)` is the following. Here `vec36.y < 0` and `k > 0` are true, so vertical lift compensation and horizontal alignment execute; `j < 0` is false, so the pitch-up term is skipped:

   | Release | requested `(x, y, z)` |
   |---|---|
   | 1.16.5 | `(0.0, -0.1941357266216167, 0.316583670275491)` |
   | 1.17 | `(0.0, -0.1941362416914499, 0.31658357161226786)` |

   These are source-formula calculations using the releases' float sine table and binary32 rounding. The `y` request changes by about `-5.15e-7`; `z` changes by about `-9.87e-8`. This is the requested `move` delta before collision resolution, which is the next operation. Collision-resolved position and any later outgoing packet are deliberately not claimed.

## Separately changing mechanisms and bounds

- The Elytra horizontal-speed expression changes spelling: 1.16.5 calls `sqrt(Entity.getHorizontalDistanceSqr(v))` (`Entity.java:709`, `LivingEntity.java:1988`); 1.17 calls `Vec3.horizontalDistance()` (`Vec3.java:131`, `LivingEntity.java:2073`). Both source bodies evaluate `sqrt(x*x + z*z)` in that order. The witness holds `(x,z)=(0,0.3)`, so this API change does not create the reported pitch-dependent difference.
- The 1.17 mouse handler adds a scoped-camera sensitivity branch; the callback witness excludes it. Cursor hardware values and smoothing are not needed to reproduce the specified single callback.
- The result establishes propagation into the local player's movement-call argument under the stated state. It does not establish a collision-resolved trajectory, server packet contents, a cutover inside the 1.16.5→1.17 interval, or runtime parity.

## Native source identity and pins

All source paths below are relative to `decompiled_minecraft/<release>/mojmap/`; the SHA-256 values are the checked local source bytes.

| Source | SHA-256 |
|---|---|
| 1.16.5 `net/minecraft/world/entity/Entity.java` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` |
| 1.16.5 `net/minecraft/world/entity/LivingEntity.java` | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` |
| 1.16.5 `net/minecraft/client/MouseHandler.java` | `2611dceb1078a63a33d621bf02f466ae70f6d015c902813935b9836f39415949` |
| 1.16.5 `net/minecraft/client/player/LocalPlayer.java` | `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b` |
| 1.16.5 `net/minecraft/util/Mth.java` | `0e4424ffa12923314c309ae13e012768271dfcace4b5a84727f74e8170afe290` |
| 1.16.5 `net/minecraft/world/phys/Vec3.java` | `b889010c321c45b497e71ff782d108a718ed23a5f605d6162904045e1e01fd48` |
| 1.17 `net/minecraft/world/entity/Entity.java` | `81231ad8ac1b9a7a9f7ee186352c7e73c0c5211a3034c3a35c04badc6914c8ef` |
| 1.17 `net/minecraft/world/entity/LivingEntity.java` | `8e81e5f1317d154fa41b676721ff581badd46296f77bcebcadb3c63448e528ed` |
| 1.17 `net/minecraft/client/MouseHandler.java` | `6ff70f8634171709ba08a42158aea7c8d4d3974d73f89b43bfe08a5ca6db5fb2` |
| 1.17 `net/minecraft/client/player/LocalPlayer.java` | `08dbb8615cc6c7776f35ce269223699d39f758d15786babfe11e497421c5f4d4` |
| 1.17 `net/minecraft/util/Mth.java` | `24515c4549e01e985017227dccf7159166a675b232be9135d5f896022a9cb113` |
| 1.17 `net/minecraft/world/phys/Vec3.java` | `eb52a20224767daeb5ddc500552c673df2daf9cd6868d7e74356411e94ef402e` |

The 1.16.5 source list entries were checked against `mojmap.sources.sha256`; its ready marker pins that manifest at `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`, release ID `1.16.5`, namespace `mojmap`, and raw client SHA-256 `00b5ebbc33e95ea88c1ab80601599c9827e9aa861d93ccc2cfcbbfd996e86263`. The sibling `mojmap.provenance.json` identifies the exact Mojmap run and has SHA-256 `b71df61eb775134119881915198f87c4337bae409e1f0de8d8d6c007e062902d`. For 1.17, the embedded source entries in `.decompile-provenance.json` match the listed file hashes; that exact file has SHA-256 `afc9dc30cd8f6406d8ea306e64cc0b3601cc8d7c7bcd314f737573d830a79357`. Its input pins include the 1.17 client SHA-256 `14122287d4e4256c2d65ec8c0ccfa3b56448495fa3ccabc87d0f0eeb4fa1c30f` and Mojang mapping text SHA-256 `191b957aaa40b294402feef29651ae7a9f1263ec22c5382f3b8fa0a4918b0471`. No online mapping lookup or source regeneration was used.
