# Independent sprint-jump impulse review

Review scope: exact 1.20.4 Mojmap to 1.20.5 Mojmap. Reviewed only the immutable draft revision below and exact source evidence for ordinary client-side sprint-jump reachability and horizontal-impulse arithmetic. No product/source/backlog edits, tests, builds, or launches were performed.

## Immutable input and source identity

- Draft: `.native-source/20261030/1204-1206-pool44/jump-pretravel/sprint-jump-horizontal-impulse-precision.md`
- Draft SHA-256: `66d73e188d7b2de1da6a82c220b8e7e66e8d5eb93255729beb70104ed209225e`
- Exact sources: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\{1.20.4,1.20.5}\mojmap`, Mojmap (official Mojang client mappings), source metadata identifies Vineflower/Java 25.
- 1.20.4 `mojmap.sources.sha256`: `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1`; provenance SHA-256: `10914d9b7b3fc29de066ca24f7d905e4d4f0a451a6c81729a2d1981addfc1385`.
- 1.20.5 `mojmap.sources.sha256`: `162dac6c4f1d539a1c2cea0255b7938a5281c207ab460bdc265358016d732484`; provenance SHA-256: `1d9b247fb839ad0e234054cba1668073245e60d86783eca40d44c2b7b23c6242`.

Every cited class was rehashed and matched exactly one entry in its version's source manifest:

| Class | 1.20.4 SHA-256 | 1.20.5 SHA-256 |
|---|---|---|
| `net/minecraft/client/multiplayer/ClientPacketListener.java` | `ff9c8222614551075b03454ee78712b0d39f0f51845e74bff76a81486f09b42f` | `e121e998ec25211aaecf91fffd0ea699cebd47eddea9134efd3f2ffbef8eb7cd` |
| `net/minecraft/client/player/KeyboardInput.java` | `a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0` | `a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0` |
| `net/minecraft/client/player/LocalPlayer.java` | `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` | `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa` |
| `net/minecraft/server/network/ServerGamePacketListenerImpl.java` | `7e2026bc5dc571031a52fa6be38a71cee20e7ccfbb2e77aec48ea4bc7af11527` | `be4268a7616ed94e2a03411a6fdf002b0e6dc3303ea959c9f41fef322867d533` |
| `net/minecraft/world/entity/player/Player.java` | `218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd` | `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2` |
| `net/minecraft/world/entity/LivingEntity.java` | `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d` | `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2` |
| `net/minecraft/world/entity/Entity.java` | `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9` | `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3` |
| `net/minecraft/util/Mth.java` | `f211066a32f5fd2ff3605d0db66f79d7811d7c90ea205caf6b5ce957c0652075` | `f1b2d62f18b4b20ffad5843f40d34d8b5f9c40eee3e106dfe275859a24cfb18d` |

## Independent verdict

**Needs correction.** The horizontal product-precision claim is source-supported, and an ordinary client-side sprint jump is reachable in both versions. However, the draft's cited `ServerGamePacketListenerImpl.handleMovePlayer` path is a server receiver and does not establish the client movement path. The client path can be closed directly from source and should be cited instead of leaving this as an open reachability issue. Also qualify the arithmetic consequence to jumps whose computed 1.20.5 power passes its `> 1.0E-5F` executor gate.

## Client-side ordinary reachability

The ordinary path in both versions is:

1. `ClientPacketListener` installs `new KeyboardInput(options)` on the local player and sets the local player as camera (`1.20.4` lines 410–414; `1.20.5` lines 423–427). `KeyboardInput.tick()` reads `options.keyJump.isDown()` into `jumping` (`1.20.4` lines 21–29; `1.20.5` lines 21–29). The same tick reads movement keys; a sprint key is accepted in `LocalPlayer.aiStep()` when the player can start sprinting (`1.20.4` lines 694–702; `1.20.5` lines 697–705).
2. `LocalPlayer.tick()` calls `super.tick()` (`1.20.4` lines 188–190; `1.20.5` lines 190–192). `LivingEntity.tick()` then invokes virtual `this.aiStep()` (`1.20.4` lines 2269–2310; `1.20.5` lines 2336–2376). `LocalPlayer.aiStep()` refreshes the input and calls `super.aiStep()` (`1.20.4` line 664 and line 800; `1.20.5` line 667 and line 807).
3. On the normal local-camera path, `LocalPlayer.isControlledCamera()` is true because the listener set the local player as camera. `LocalPlayer.serverAiStep()` then copies `input.jumping` into the inherited `jumping` field (`1.20.4` lines 610–624; `1.20.5` lines 612–626). The local player explicitly returns true from `isEffectiveAi()` (`1.20.4` lines 487–489; `1.20.5` lines 489–491), so `LivingEntity.aiStep()` runs the `serverAiStep()` dispatch even on the logical client (`1.20.4` lines 2555–2558; `1.20.5` lines 2635–2639).
4. In the jump stage, inherited `isAffectedByFluids()` returns true for the player (`1.20.4` lines 560–562; `1.20.5` lines 587–589). For a grounded, non-fluid ordinary jump with `noJumpDelay == 0`, `LivingEntity.aiStep()` calls virtual `this.jumpFromGround()` (`1.20.4` lines 2563–2578; `1.20.5` lines 2643–2658). The virtual call reaches `Player.jumpFromGround()`, which calls `super.jumpFromGround()` (`1.20.4` lines 1440–1448; `1.20.5` lines 1462–1470), landing on the reviewed `LivingEntity` executor.

Thus the server receiver callsite in the draft is unnecessary as reachability evidence. It may be omitted or clearly labeled server-side corroboration; it does not show historical physics runs on a server.

## Operation and same-input comparison

Under equal starting velocity, yaw, grounded state, sprint state, no Jump Boost, ordinary full-factor ground, and default jump strength (`0.42F`), both releases compute a positive vertical jump power above the 1.20.5 threshold. The player jump override delegates to the living executor. Both versions write the same vertical jump power first. If sprinting, both then compute the heading angle as `getYRot() * (float)(Math.PI / 180.0)`, preserving the same float angle-conversion order, and use `Mth.sin`/`Mth.cos`, which return `float` in both releases.

The horizontal component arithmetic differs exactly as drafted: 1.20.4 evaluates `-sin * 0.2F` and `cos * 0.2F` as float products, rounding to float before widening to the `double` components consumed by `Vec3`; 1.20.5 uses `0.2` double literals, so each float trig result is widened for double multiplication. `Entity.addDeltaMovement(Vec3)` in 1.20.5 delegates to `setDeltaMovement(getDeltaMovement().add(vector))`, which is the same vector accumulation used explicitly in 1.20.4. I found no other relevant difference in this sub-operation.

The draft should state that this isolated horizontal precision consequence applies when 1.20.5 computed power is strictly greater than `1.0E-5F`; at or below that value the 1.20.5 executor makes no vertical or sprint impulse write, so the simple equal-impulse comparison would not hold. Under the stated ordinary full-factor, default-power precondition, the threshold is passed and the described slight horizontal velocity difference is source-supported. Later position/tick effects remain inference, not runtime verification.

Paired movement math refs: 1.20.4 `LivingEntity.java` lines 2008–2017; `Player.java` lines 1440–1448; `Entity.java` lines 3174–3176; `Mth.java` lines 47–53. 1.20.5 `LivingEntity.java` lines 2069–2081; `Player.java` lines 1462–1470; `Entity.java` lines 3189–3191; `Mth.java` lines 48–54.

## Review boundary

This verdict binds only to the input draft SHA above and the exact source manifests identified here. It does not establish full-pair inventory completeness, dependency closure outside this finding, pair freeze, implementation, or runtime parity.
