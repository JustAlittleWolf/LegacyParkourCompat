# Independent sprint-jump horizontal impulse review r2

Review scope: exact 1.20.4 Mojmap to 1.20.5 Mojmap. This report binds only to the revised draft hash below. The r1 review report remains unchanged. No source/product/draft edits, tests, builds, or launches were performed.

## Immutable input and exact source identity

- Draft: `.native-source/20261030/1204-1206-pool44/jump-pretravel/sprint-jump-horizontal-impulse-precision.md`
- Draft SHA-256: `125f27587d1d1a1dc18dd1516356940a435d29fdad29749cf68802eb0886cc5f`
- Compared exact trees: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\{1.20.4,1.20.5}\mojmap`; mapping Mojmap (official Mojang client mappings), with Vineflower/Java 25 in the source metadata.
- 1.20.4 source-manifest SHA-256: `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1`; provenance SHA-256: `10914d9b7b3fc29de066ca24f7d905e4d4f0a451a6c81729a2d1981addfc1385`.
- 1.20.5 source-manifest SHA-256: `162dac6c4f1d539a1c2cea0255b7938a5281c207ab460bdc265358016d732484`; provenance SHA-256: `1d9b247fb839ad0e234054cba1668073245e60d86783eca40d44c2b7b23c6242`.

All cited classes below were SHA-256 checked against the matching version's `mojmap.sources.sha256` entry; each class had one exact match.

| Class | 1.20.4 SHA-256 | 1.20.5 SHA-256 |
|---|---|---|
| `net/minecraft/client/multiplayer/ClientPacketListener.java` | `ff9c8222614551075b03454ee78712b0d39f0f51845e74bff76a81486f09b42f` | `e121e998ec25211aaecf91fffd0ea699cebd47eddea9134efd3f2ffbef8eb7cd` |
| `net/minecraft/client/player/KeyboardInput.java` | `a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0` | `a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0` |
| `net/minecraft/client/player/LocalPlayer.java` | `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` | `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa` |
| `net/minecraft/world/entity/player/Player.java` | `218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd` | `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2` |
| `net/minecraft/world/entity/LivingEntity.java` | `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d` | `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2` |
| `net/minecraft/world/entity/Entity.java` | `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9` | `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3` |
| `net/minecraft/util/Mth.java` | `f211066a32f5fd2ff3605d0db66f79d7811d7c90ea205caf6b5ce957c0652075` | `f1b2d62f18b4b20ffad5843f40d34d8b5f9c40eee3e106dfe275859a24cfb18d` |
| `net/minecraft/world/entity/ai/attributes/Attributes.java` (related power-premise check) | `287dcd477ccca2aebd24ad28db7b0e7a1e9dc9bbeb272d330cd15504b8669ff7` | `7e30d87c7a58b14d0052d2f9f7319d997b49ae7d025579cd762d28e845b2e82e` |

## Independent verdict

**Needs correction.** The revised client reachability chain and the `> 1.0E-5F` premise are correct. The float-versus-double product difference and subsequent vector accumulation are also correct. The remaining scope issue is the statement that vertical jump is unchanged: the power gate alone does not establish equal computed jump power across releases. 1.20.5 reads the new generic jump-strength attribute, while 1.20.4 uses hard-coded `0.42F`. The draft should restrict “vertical jump unchanged” to equal computed power (for example, unmodified default strength with the same block factor and effect state) and cite the getter/attribute evidence, or remove that statement from this isolated precision finding.

## Client-side path confirmation

The paired citations now establish ordinary local reachability, rather than relying on the server movement-packet receiver:

1. `ClientPacketListener.handleLogin()` installs `KeyboardInput` on the local player and sets that player as camera (`1.20.4` lines 412–414; `1.20.5` lines 425–427). `KeyboardInput.tick()` reads the configured jump key into `jumping` (`1.20.4` and `1.20.5` lines 21–29).
2. `LocalPlayer.tick()` calls `super.tick()` (`1.20.4` lines 188–190; `1.20.5` lines 190–192). `LivingEntity.tick()` invokes virtual `this.aiStep()` (`1.20.4` lines 2269–2310; `1.20.5` lines 2336–2376). `LocalPlayer.aiStep()` refreshes input before calling `super.aiStep()` (`1.20.4` lines 664 and 800; `1.20.5` lines 667 and 807).
3. `LocalPlayer.isEffectiveAi()` returns true (`1.20.4` lines 487–489; `1.20.5` lines 489–491), so the inherited living AI step dispatches `serverAiStep()` in this client entity (`1.20.4` lines 2555–2558; `1.20.5` lines 2635–2639). On the controlled-camera path established by the login setup, `LocalPlayer.serverAiStep()` copies `input.jumping` into the inherited jump field (`1.20.4` lines 610–624; `1.20.5` lines 612–626).
4. `isAffectedByFluids()` returns true (`1.20.4` lines 560–562; `1.20.5` lines 587–589). For the stated ordinary dry grounded jump with `noJumpDelay == 0`, `LivingEntity.aiStep()` calls virtual `this.jumpFromGround()` (`1.20.4` lines 2563–2578; `1.20.5` lines 2643–2658). That reaches `Player.jumpFromGround()` and then `LivingEntity.jumpFromGround()` (`1.20.4` `Player.java` lines 1440–1448 and `LivingEntity.java` lines 2008–2017; 1.20.5 `Player.java` lines 1462–1470 and `LivingEntity.java` lines 2069–2081). The LocalPlayer sprint-start branch can set `isSprinting()` from a held sprint key when the start conditions hold (`1.20.4` lines 694–702; `1.20.5` lines 697–705).

The cited ranges are accurate and close the prior client-reachability gap. The branch changes no logical-server behavior; the method name `serverAiStep` is an inherited AI hook invoked for LocalPlayer because its override of `isEffectiveAi()` returns true.

## Arithmetic and preconditions

For equal yaw, sprint state, pre-add velocity, and computed jump power greater than `1.0E-5F`, both jump executors first write the computed vertical power. When sprinting, 1.20.4 multiplies each `float` returned by `Mth.sin`/`Mth.cos` by `0.2F`; Java therefore rounds each product to `float` before widening to the `double` `Vec3` component. 1.20.5 multiplies the same `float` results by double literal `0.2`, producing double components. The angle calculation `(float)(Math.PI / 180.0)` and yaw multiplication are the same in both versions. `Entity.addDeltaMovement(Vec3)` in 1.20.5 delegates to `setDeltaMovement(getDeltaMovement().add(vector))`, matching the accumulation explicit in 1.20.4 (`Entity.java` lines 3174–3176 and 3189–3191). The differing product precision can therefore change the resulting horizontal velocity slightly; it is not a changed nominal coefficient.

The r2 draft is right that a jump at or below the 1.20.5 gate does not execute either the vertical or sprint horizontal write. It should additionally state that equal vertical jump power is assumed for the “vertical jump unchanged” part. `LivingEntity.getJumpPower()` is hard-coded as `0.42F * blockJumpFactor + jumpBoostPower` in 1.20.4 (lines 2000–2002); 1.20.5 delegates to `getJumpPower(1.0F)` and multiplies the generic `JUMP_STRENGTH` attribute before the block factor (lines 2057–2063), whose default is `0.42F` (`Attributes.java` lines 45–47). For a Player, that attribute is supplied through living attributes (`Player.java` lines 221–230; `LivingEntity.java` lines 292–305). If the scope is limited to that vanilla unmodified default and equal remaining inputs, cite those lines and state that assumption. Without it, vertical power may differ independently of the horizontal product precision.

## Review boundary

This verdict binds only to the draft hash above and the exact sources/manifests identified here. It does not assert full-pair inventory completeness, dependency closure outside this finding, pair freeze, implementation, or runtime parity.
