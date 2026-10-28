# Independent source-only audit: high-amplifier jump response

## Inputs and identity

Audited report: `.native-source/20261028/ordinary-closure/jump-boost-high-amplifier/report.md`  
SHA-256: `9f5283facefe9cfb492656413be818cb261e7ef087edba5605a7f8e0bad61cef` (matches the expected digest).

Canonical source roots inspected were `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.20.4\mojmap`, `...\1.20.5\mojmap`, `...\1.20.6\mojmap`, and for the scope extension `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\26.2\unobfuscated`. The 1.20.4/5/6 ready markers identify the exact corresponding versions and Mojmap, Vineflower, Java 25; the 26.2 marker identifies version 26.2, unobfuscated, Vineflower, Java 25. Actual source- and artifact-manifest digests match each ready marker. Source manifests: 1.20.4 `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1`, 1.20.5 `162dac6c4f1d539a1c2cea0255b7938a5281c207ab460bdc265358016d732484`, 1.20.6 `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311`, 26.2 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`.

For the six selected 1.20.x classes in the report, recomputed file hashes match its table. 1.20.5 and 1.20.6 files are byte-identical for all six. For the added 26.2 closure, `LivingEntity.java` SHA-256 is `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`; the manifest entry agrees.

## Findings by operation

### Amplifier interpretation and storage

**Supported.** In 1.20.4 `ClientboundUpdateMobEffectPacket.java:17,23–27,46–50,54–61,76–78` stores a byte, masks and casts the constructor input, decodes with `readByte`, encodes with `writeByte`, and returns byte. `ClientPacketListener.handleUpdateMobEffect` constructs the effect using that getter and force-adds it (`ClientPacketListener.java:1501–1518`). `MobEffectInstance` stores the constructor amplifier directly (`MobEffectInstance.java:61–70`); `LivingEntity.forceAddEffect` stores that instance (`LivingEntity.java:975–983`) and `getEffect` retrieves it (`936–938`). Thus byte values 128–255 are sign-extended on read: 128 is -128; 255 is -1.

**Supported.** In 1.20.5/1.20.6, packet amplifier is `int`; codec uses VarInt and getter returns int (`ClientboundUpdateMobEffectPacket.java:21–31,52–66,85–87`). The client handler passes it into `MobEffectInstance` (`ClientPacketListener.java:1534–1547`), whose constructor clamps to `[0,255]` (`MobEffectInstance.java:78–85`). Force-add stores that instance; replacing one copies blend state but leaves its amplifier unchanged (`LivingEntity.java:999–1008`). The matching class hashes establish the same result in .6.

### Historical boost arithmetic and ground-jump assignment

**Supported.** In 1.20.4, `LivingEntity.getJumpBoostPower()` is `0.1F * (amplifier + 1.0F)` when JUMP exists, otherwise zero (`LivingEntity.java:2004–2006`). `getJumpPower()` adds this to `0.42F * getBlockJumpFactor()` (`2000–2002`). `jumpFromGround()` writes the computed value to Y unconditionally while preserving X/Z, then applies sprint X/Z impulse and marks `hasImpulse` (`2008–2017`). There is no positive-power guard.

For vanilla-reachable byte values used as movement examples: source/wire amplifier 128 is read as -128 and contributes `-12.7F`; 255 is read as -1 and contributes `0.0F`. At 128 the final Y is exactly `0.42F * blockJumpFactor - 12.7F`, including a negative result if that is the arithmetic result. The source constructor's `& 0xFF` means a source-side 256 is encoded as byte zero; this is a codec statement only here, not a movement-reachability claim.

**Supported.** In 1.20.5/.6, no-arg `getJumpPower()` calls `getJumpPower(1.0F)`, whose operation order is `(float)getAttributeValue(JUMP_STRENGTH) * multiplier * getBlockJumpFactor() + getJumpBoostPower()` (`LivingEntity.java:2057–2067`). The boost is `0.1F * (amplifier + 1.0F)`. A received 128 contributes `+12.9F`; 255 contributes `+25.6F` after clamping. `jumpFromGround()` computes power once and enters the velocity write/sprint impulse/impulse-flag block only when power is greater than `1.0E-5F` (`2069–2081`). The exact total still depends on the runtime jump-strength attribute and block factor; their production is outside this audit.

### 26.2 target-side response

`26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java:2376–2385` has the same modern arithmetic shape: no-arg `getJumpPower()` calls the multiplier overload with `1.0F`; that overload multiplies jump-strength attribute, multiplier, and block factor in that order, then adds `getJumpBoostPower()`. The effect registry name is now `JUMP_BOOST`; its power remains `0.1F * (amplifier + 1.0F)` (`2384–2385`).

At `2389–2401`, `jumpFromGround()` computes power once and has the same `!(jumpPower <= 1.0E-5F)` admission test as .5/.6. Within the admitted block it assigns Y using `Math.max(jumpPower, movement.y)`, then applies sprint X/Z impulse and sets `needsSync`. This adds a second target-side response to preserve: high positive jump power does not lower an already greater Y velocity. If a legacy signed amplifier is supplied to the modern boost formula, amplifier -128 yields -12.7F boost; with an ordinary ~0.42F base the total is nonpositive and the target early return prevents any Y write. 1.20.4 instead unconditionally writes the negative computed Y. For 255 -> -1, the zero boost leaves the ordinary positive base admitted. These arithmetic examples are conditional on that amplifier being what the target effect getter supplies; this bounded review does not inspect the target's effect construction/producer path or attribute production.

## Ordinary local jump route

**Supported for the inspected route.** LocalPlayer copies `input.jumping` into `jumping` only for the controlled camera (`LocalPlayer.java:609–620` in 1.20.4; `611–622` in .5/.6). Its `aiStep()` calls `Player.aiStep()`, which calls `LivingEntity.aiStep()` (1.20.4 `LocalPlayer.java:800`, `Player.java:506–524`; .5/.6 `LocalPlayer.java:807`, `Player.java:515–533`). LivingEntity clears jumping when immobile; otherwise effective AI invokes `serverAiStep`, after which the jump stage requires `jumping && isAffectedByFluids()`. The dry ordinary ground branch invokes `jumpFromGround()` only with `onGround()` and `noJumpDelay == 0`, then sets delay 10. Water/lava have separate branches (`LivingEntity.java:2551–2587` in 1.20.4; `2631–2667` in .5/.6). Dynamic dispatch reaches `Player.jumpFromGround()`, which calls super in all three versions (`Player.java:1439–1448` in 1.20.4; `1461–1470` in .5/.6).

## Claim disposition and implementation implication

The audited report's claims about packet representation, client effect construction/storage, version-specific boost arithmetic, ordinary jump route/gates, and no source difference from 1.20.5 to 1.20.6 are supported. No examined claim is refuted. The only scope refinement is to keep source-amplifier 256 as a codec-wrap observation, not a movement case; the report's formula claims are supported for 128/255.

The extended comparison confirms these are separable operations: amplifier interpretation/storage, boost-power calculation, and jump admission/velocity assignment. Matching signed-amplifier interpretation alone cannot reproduce 1.20.4 high-negative response on 26.2: its nonpositive-power guard skips the assignment, and its admitted path uses `Math.max` against prior Y. A compatibility implementation needs source-version behavior at the relevant calculation and jump-write/admission sites as well as the amplifier read if parity requires those responses. Treating the whole path as one undifferentiated jump-boost operation would hide these independently observable differences.

Remaining caveat: this is decompiled source evidence only. It does not prove runtime delivery, that a particular effect producer can create every internal value, player attribute/block-factor values, or integrated/server behavior. No tests, game launches, or source generation were run.
