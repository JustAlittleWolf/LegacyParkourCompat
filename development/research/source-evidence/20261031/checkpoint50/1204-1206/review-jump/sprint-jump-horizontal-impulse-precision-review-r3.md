# Independent sprint-jump horizontal impulse review r3

Review scope: exact 1.20.4 Mojmap to 1.20.5 Mojmap. This review binds only to the r3 draft hash below; r1 and r2 reports remain separate and unchanged.

## Immutable input and source identity

- Draft: `.native-source/20261030/1204-1206-pool44/jump-pretravel/sprint-jump-horizontal-impulse-precision.md`
- Draft SHA-256: `c47d05ac4d7d1c781536e1731d9f761062cc7781c8db71149720da6583d3a8b2`
- Exact source trees: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\{1.20.4,1.20.5}\mojmap`; mapping Mojmap (official Mojang client mappings), source metadata identifies Vineflower/Java 25.
- 1.20.4 source manifest SHA-256: `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1`.
- 1.20.5 source manifest SHA-256: `162dac6c4f1d539a1c2cea0255b7938a5281c207ab460bdc265358016d732484`.

All cited classes were rehashed and matched exactly one entry in the corresponding source manifest:

| Class | 1.20.4 SHA-256 | 1.20.5 SHA-256 |
|---|---|---|
| `net/minecraft/client/multiplayer/ClientPacketListener.java` | `ff9c8222614551075b03454ee78712b0d39f0f51845e74bff76a81486f09b42f` | `e121e998ec25211aaecf91fffd0ea699cebd47eddea9134efd3f2ffbef8eb7cd` |
| `net/minecraft/client/player/KeyboardInput.java` | `a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0` | `a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0` |
| `net/minecraft/client/player/LocalPlayer.java` | `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` | `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa` |
| `net/minecraft/world/entity/player/Player.java` | `218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd` | `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2` |
| `net/minecraft/world/entity/LivingEntity.java` | `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d` | `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2` |
| `net/minecraft/world/entity/Entity.java` | `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9` | `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3` |
| `net/minecraft/util/Mth.java` | `f211066a32f5fd2ff3605d0db66f79d7811d7c90ea205caf6b5ce957c0652075` | `f1b2d62f18b4b20ffad5843f40d34d8b5f9c40eee3e106dfe275859a24cfb18d` |

## Independent verdict

**Accept.** The r3 claim is now scoped to horizontal impulse precision and to computed 1.20.5 jump power strictly greater than `1.0E-5F`. The client path, citations, and numerical operation order match the exact sources. It makes no unsupported claim that vertical jump power is unchanged.

## Client path and preconditions

The cited path is ordinary client-side movement. `ClientPacketListener.handleLogin()` assigns `KeyboardInput` to LocalPlayer and makes it the camera (1.20.4 lines 412–414; 1.20.5 lines 425–427). `KeyboardInput.tick()` reads the configured jump key (both lines 21–29). LocalPlayer's `tick()` calls `super.tick()`; `LivingEntity.tick()` invokes virtual `aiStep()`. LocalPlayer's `aiStep()` refreshes input, applies its sprint-start branch, then calls the superclass AI step. LocalPlayer returns true from `isEffectiveAi()`, so the living AI step dispatches `serverAiStep()`; when it is the controlled camera, LocalPlayer copies `input.jumping` into the inherited field. The grounded, dry, `noJumpDelay == 0` branch then invokes virtual `Player.jumpFromGround()`, which delegates to `LivingEntity.jumpFromGround()`.

These call/order facts are evidenced by the cited paired ranges: LocalPlayer tick/AI/input/sprint/isControlledCamera/isEffectiveAi (`1.20.4` lines 188–190, 610–624, 646–665, 694–702, 800, 487–489; `1.20.5` lines 190–192, 612–626, 648–668, 697–705, 807, 489–491); LivingEntity tick and jump stage (`1.20.4` lines 2269–2310, 2555–2578; `1.20.5` lines 2336–2376, 2635–2658); `isAffectedByFluids()` (`1.20.4` lines 560–562; `1.20.5` lines 587–589); and Player/LivingEntity jump methods (`1.20.4` `Player.java` lines 1440–1448 / `LivingEntity.java` lines 2008–2017; 1.20.5 `Player.java` lines 1462–1470 / `LivingEntity.java` lines 2069–2081).

## Arithmetic

For equal yaw, sprint state, and pre-add horizontal velocity, the stated 1.20.5 power gate ensures the new executor reaches its jump-write block. Both releases compute the angle from yaw with the same `(float)(Math.PI / 180.0)` conversion and multiply by that float angle; `Mth.sin`/`cos` return `float` in both releases. In 1.20.4 the horizontal terms multiply each float result by `0.2F`, so multiplication rounds as float before widening to the `double` components passed to `Vec3`. In 1.20.5 multiplication by double literal `0.2` produces double components. The vector addition is equivalent: 1.20.5 `Entity.addDeltaMovement(Vec3)` delegates to `setDeltaMovement(getDeltaMovement().add(vector))`, matching 1.20.4's explicit sequence (`Entity.java` lines 3174–3176 and 3189–3191). Thus the changed product precision can slightly change resulting horizontal velocity without asserting any vertical-power equality. At or below the 1.20.5 threshold, its executor suppresses the horizontal sprint-impulse write, so the isolated comparison does not apply.

## Review boundary

This acceptance binds only to the r3 draft hash and exact source manifests above. It does not assert full-pair inventory completeness, pair freeze, implementation, or runtime parity.
