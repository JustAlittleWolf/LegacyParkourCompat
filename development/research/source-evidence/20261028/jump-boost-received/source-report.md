# Ordinary jump response to high-amplifier JUMP_BOOST

## Scope and source identity

This report covers only the client-received JUMP_BOOST instance and its ordinary local-player ground-jump consumption in 1.20.4, 1.20.5, and 1.20.6. Exact decompiled sources were read from `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\mojmap`.

All three `mojmap.ready.json` markers say `status: ready`, identify the matching game version and `mapping: mojmap`, and name Vineflower with Java runtime 25. The source manifests and artifact manifests match the SHA-256 values declared in each ready marker. Per the adjacent provenance records, 1.20.4 and 1.20.6 were produced by the same recorded Mojmap batch run; 1.20.5 was produced by its own exact-version Mojmap run, with the provenance record identifying official Mojang client mappings. The matching version metadata IDs are 1.20.4, 1.20.5, and 1.20.6. The ready markers report 5,048, 5,328, and 5,329 Java sources respectively. The 1.20.5 ready marker also records the source-pair review as not performed by the preparation owner; this report is the bounded independent source review for this slice.

Selected source-file SHA-256 values below were recomputed from the listed source trees and matched the corresponding entries in each `mojmap.sources.sha256` manifest. The 1.20.5 and 1.20.6 hashes are identical for every selected class.

| Selected class | 1.20.4 | 1.20.5 and 1.20.6 |
|---|---|---|
| `ClientboundUpdateMobEffectPacket.java` | `88a2692d0a0fc83753ee1b6ba558030c7836d12d25b4752457fb86ac353a3f3a` | `268e9368525964192b0fb8f15f4a6418a8efcbcb657035b73752367b8ebc7f2e` |
| `ClientPacketListener.java` | `ff9c8222614551075b03454ee78712b0d39f0f51845e74bff76a81486f09b42f` | `e121e998ec25211aaecf91fffd0ea699cebd47eddea9134efd3f2ffbef8eb7cd` |
| `MobEffectInstance.java` | `e1aac04c3f5714c5c47f84375f1d930204bd177218c05d8c0b590d70381aa249` | `e14f5bb6bdccd3f300f092ebee3fffcaf875e01051987494a8d25ccf306edec1` |
| `LivingEntity.java` | `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d` | `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2` |
| `LocalPlayer.java` | `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` | `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa` |
| `Player.java` | `218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd` | `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2` |

Source-manifest SHA-256 values: 1.20.4 `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1`; 1.20.5 `162dac6c4f1d539a1c2cea0255b7938a5281c207ab460bdc265358016d732484`; 1.20.6 `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311`. Artifact-manifest SHA-256 values: 1.20.4 `ee3efc771d264c0bc49d5472abb9763a646f8d221841d0a61201ecd991cb3948`; 1.20.5 `f2dbd7a2e6c3fb452b0c28c93c9815e43ca24c179815f3d4fca34e484d465bb6`; 1.20.6 `e5882622bcf22b3e3c2c96c73843a1308e11e3096f4feefd945ea5808933ce31`.

## Packet to active effect

**1.20.4.** `ClientboundUpdateMobEffectPacket` stores the amplifier in a `byte`; constructing the packet applies `(byte)(amplifier & 0xFF)` (lines 15–27), decoding uses `readByte` (45–50), encoding uses `writeByte` (54–61), and `getEffectAmplifier()` returns `byte` (76–78). Thus a payload whose source amplifier is 128–255 is sign-extended when the client consumes the getter: e.g. 128 becomes -128, and 255 becomes -1.

`ClientPacketListener.handleUpdateMobEffect` runs the packet on the client thread, looks up the entity, requires `LivingEntity`, obtains the effect, constructs `MobEffectInstance` with the packet duration and amplifier, and passes that instance to `forceAddEffect` (1.20.4 `ClientPacketListener.java:1501–1518`). The constructor assigns `this.amplifier = $$2` directly, without clamping (`MobEffectInstance.java:61–70`). `forceAddEffect` puts this instance into the active-effect map keyed by effect (`LivingEntity.java:975–983`). `getEffect` returns the map entry (`LivingEntity.java:936–938`), so the jump formula reads that stored instance's signed amplifier.

**1.20.5 and 1.20.6.** The packet amplifier is an `int`; encode and decode both use VarInt, and `getEffectAmplifier()` returns `int` (`ClientboundUpdateMobEffectPacket.java:21–31, 52–66, 85–87`; identical file hash in 1.20.5 and 1.20.6). The handler constructs a `MobEffectInstance` from that integer and force-adds it (`ClientPacketListener.java:1534–1547`, same lines and file hash in both releases). Its constructor clamps the received value to `[0, 255]` (`MobEffectInstance.java:78–85`, same lines and file hash in both releases). `forceAddEffect` stores the instance directly; when replacing an existing instance, the new instance receives blend state from the prior one, but its amplifier is not changed (`LivingEntity.java:999–1008`, same in both releases). `getEffect` then retrieves that instance (`LivingEntity.java:960–962`).

## Jump calculation and ordinary local-player gates

In all three releases, `LivingEntity.getJumpBoostPower()` returns zero when JUMP is absent; when present it computes `0.1F * (getAmplifier() + 1.0F)` (1.20.4 `LivingEntity.java:2004–2006`; 1.20.5 and 1.20.6 `LivingEntity.java:2065–2067`).

In 1.20.4, `getJumpPower()` computes `0.42F * getBlockJumpFactor() + getJumpBoostPower()` (`LivingEntity.java:2000–2002`). `jumpFromGround()` writes this value as the Y component while preserving X and Z, then applies the sprint impulse and sets `hasImpulse`; it has no positive-power guard (`LivingEntity.java:2008–2017`). Therefore, for a received JUMP amplifier `a` in 128–254, the stored amplifier is `a - 256` and the boost contribution is `0.1F * (a - 255)`, a negative value. For example, wire/source amplifier 128 yields boost `-12.7F`; amplifier 255 wraps to -1 and yields `0.0F`. The resulting Y assignment is exactly `0.42F * blockJumpFactor + boost`, including negative results. Amplifier 256 wraps to zero.

In 1.20.5 and 1.20.6, no-argument `getJumpPower()` delegates to `getJumpPower(1.0F)`, which computes `(float)getAttributeValue(Attributes.JUMP_STRENGTH) * 1.0F * getBlockJumpFactor() + getJumpBoostPower()` (`LivingEntity.java:2057–2067`, same in both releases). `jumpFromGround()` evaluates this once and only applies its Y assignment, sprint impulse, and `hasImpulse` update when the value is greater than `1.0E-5F` (`LivingEntity.java:2069–2081`, same in both releases). For amplifier 128, the constructor retains 128 and the boost contribution is `+12.9F`; for any received amplifier at or above 255, it is clamped to 255 and the contribution is `+25.6F`. The exact total Y value depends on the runtime jump-strength attribute and block jump factor; this bounded review does not inspect attribute production.

The ordinary local-player route is consistent across the three releases. `LocalPlayer.serverAiStep()` copies `input.jumping` into the inherited `jumping` field only while the local player is the controlled camera (1.20.4 `LocalPlayer.java:609–620`; 1.20.5 and 1.20.6 `LocalPlayer.java:611–622`). `LocalPlayer.aiStep()` delegates to `Player.aiStep()` (`LocalPlayer.java:800` in 1.20.4; line 807 in 1.20.5/1.20.6); `Player.aiStep()` delegates to `LivingEntity.aiStep()` (1.20.4 `Player.java:506–524`; 1.20.5/1.20.6 `Player.java:515–533`). In `LivingEntity.aiStep()`, immobility clears `jumping`; otherwise effective AI invokes `serverAiStep`. The jump stage then requires `jumping && isAffectedByFluids()`. For an ordinary dry ground jump, the non-water/non-lava branches reach `onGround() && noJumpDelay == 0`, invoke `jumpFromGround()`, and set `noJumpDelay = 10` (1.20.4 `LivingEntity.java:2551–2587`; 1.20.5/1.20.6 `LivingEntity.java:2631–2667`). Dynamic dispatch reaches `Player.jumpFromGround()`, which delegates to `super.jumpFromGround()` in each version (1.20.4 `Player.java:1439–1448`; 1.20.5/1.20.6 `Player.java:1461–1470`).

## Coverage result

* **1.20.4:** packet byte representation, listener construction/storage, amplifier read, jump formula, jump write, and local-player caller/gates reviewed. High amplifiers 128–254 become negative amplifier values; 255 becomes -1; 256 wraps to zero.
* **1.20.4 → 1.20.5:** movement-relevant differences are packet representation and normalization (signed byte/raw constructor versus VarInt/clamped constructor), the jump-power base change from fixed `0.42F` to the jump-strength attribute, and the new `<= 1.0E-5F` guard in `jumpFromGround()`. The local input-to-jump route and ordinary dry-ground/liquid/delay gates are structurally the same across this boundary; source line offsets differ.
* **1.20.5 → 1.20.6:** no difference in the six lane-relevant classes listed in the hash table; each class has identical source-file SHA-256. The exact target source was reviewed for all requested operations and gates in both version folders.

This is source evidence for the stated decompiled artifacts. It does not evaluate effect creation, attribute production, health, levitation, or server behavior.

