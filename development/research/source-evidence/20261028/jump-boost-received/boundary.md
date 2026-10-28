# JUMP_BOOST boundary closure: 1.20.2–1.20.5

## Scope

Exact-source comparison of the clientbound mob-effect amplifier path and the ordinary `LivingEntity.jumpFromGround()` response in Mojmap 1.20.2, 1.20.3, 1.20.4, and 1.20.5. The claim is about decoded source inputs and their client-side representation; it does not establish how vanilla gameplay produces a high amplifier. Packet codec, `MobEffectInstance` normalization/storage, jump-power arithmetic, and jump admission are separate operations below.

Canonical read-only sources: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\mojmap\`.

## Source identity

For 1.20.2, `mojmap.ready.json` identifies version 1.20.2 / Mojmap / Vineflower Java 25, 4,904 source files, and source-manifest SHA-256 `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887`. Its hidden `mojmap.provenance.json` records the exact 1.20.1–1.20.6 Mojmap batch run and tool versions.

For 1.20.3, the source folder has no root `mojmap.ready.json` or `mojmap.sources.sha256`. Its hidden `mojmap\.decompile-provenance.json` records the source hash map and input metadata/client JAR hashes; the metadata URL ends in `/1.20.3.json`. The four relevant hashes in that map match both the available 1.20.2 manifest and the 1.20.4 manifest. This is the source identity evidence available for 1.20.3; it is not represented as a ready-marker-verified source set.

For 1.20.4, `mojmap.ready.json` identifies version 1.20.4 / Mojmap / Vineflower Java 25, 5,048 source files, and source-manifest SHA-256 `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1`. Its hidden `mojmap.provenance.json` records the exact 1.20.1–1.20.6 Mojmap batch.

For 1.20.5, `mojmap.ready.json` identifies version 1.20.5 / Mojmap / Vineflower Java 25, 5,328 source files, and source-manifest SHA-256 `162dac6c4f1d539a1c2cea0255b7938a5281c207ab460bdc265358016d732484`; its `mojmap.provenance.json` records a successful exact 1.20.5 Mojmap run.

Selected relevant source SHA-256 values (1.20.3 values are from its hidden per-source map; other values are from their source manifests):

| Release | Packet | Client listener | Effect instance | Living entity |
|---|---|---|---|---|
| 1.20.2 | `88a2692d0a0fc83753ee1b6ba558030c7836d12d25b4752457fb86ac353a3f3a` | `0208f6942035adaf2e787546851ca296f0e7ec2179d3610f694911eda5283264` | `e1aac04c3f5714c5c47f84375f1d930204bd177218c05d8c0b590d70381aa249` | `5c481da1ffc8684c4b30171c92e61fad751e6a3a708d5bc6486ac9f96ff69828` |
| 1.20.3 | `88a2692d0a0fc83753ee1b6ba558030c7836d12d25b4752457fb86ac353a3f3a` | `ff9c8222614551075b03454ee78712b0d39f0f51845e74bff76a81486f09b42f` | `e1aac04c3f5714c5c47f84375f1d930204bd177218c05d8c0b590d70381aa249` | `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d` |
| 1.20.4 | `88a2692d0a0fc83753ee1b6ba558030c7836d12d25b4752457fb86ac353a3f3a` | `ff9c8222614551075b03454ee78712b0d39f0f51845e74bff76a81486f09b42f` | `e1aac04c3f5714c5c47f84375f1d930204bd177218c05d8c0b590d70381aa249` | `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d` |
| 1.20.5 | `268e9368525964192b0fb8f15f4a6418a8efcbcb657035b73752367b8ebc7f2e` | `e121e998ec25211aaecf91fffd0ea699cebd47eddea9134efd3f2ffbef8eb7cd` | `e14f5bb6bdccd3f300f092ebee3fffcaf875e01051987494a8d25ccf306edec1` | `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2` |

The selected packet and effect-instance sources are hash-identical across 1.20.2–1.20.4; the client-listener source is hash-identical between 1.20.3 and 1.20.4, while 1.20.2 has its own hash. 1.20.2's `LivingEntity` differs from the identical 1.20.3/1.20.4 file, but the jump methods below have the same relevant expressions and admission behavior.

## Packet codec

**1.20.2–1.20.4:** `ClientboundUpdateMobEffectPacket` stores `effectAmplifier` as a `byte`; its instance-taking constructor narrows `(instance.getAmplifier() & 0xFF)` to byte, its buffer constructor calls `readByte()`, its writer calls `writeByte()`, and its getter returns `byte` (1.20.2 packet lines 17, 23–27, 45–50, 55–60, 76–78; identical packet source hashes in 1.20.3/1.20.4). On receipt, Java sign extension means byte payload `0x80` reads as `-128` and `0xFF` reads as `-1`.

**1.20.5:** the packet field and getter are `int`; the buffer constructor calls `readVarInt()` and the writer calls `writeVarInt()` (packet lines 21–25, 52–58, 60–65, 83–86). The packet constructor copies the instance amplifier to the int field without byte narrowing. Inputs 128 and 255 decode as positive 128 and 255.

## Instance construction and normalization

In 1.20.2, the listener passes the packet getter into `new MobEffectInstance(...)` and applies that instance with `forceAddEffect` (`ClientPacketListener.java:1452–1468`). The getter's signed byte widens to int; the main instance constructor assigns the argument directly to `this.amplifier` (`MobEffectInstance.java:61–72`). 1.20.3/1.20.4 use the same packet and instance sources; the listener site is at `ClientPacketListener.java:1501–1518`.

In 1.20.5, the listener passes the packet int into `MobEffectInstance` and then calls `forceAddEffect` (`ClientPacketListener.java:1534–1547`). Its constructor stores `Mth.clamp(amplifier, 0, 255)` (`MobEffectInstance.java:78–86`). Thus the 1.20.5 codec and instance normalization are two distinct stages: VarInt decoding supplies an int, then instance construction clamps it to `[0,255]`.

For the bounded wire values 128 and 255: 1.20.2–1.20.4 store the signed values `-128` and `-1`; 1.20.5 stores `128` and `255`. These observations say nothing about vanilla production or ordinary reachability of those inputs.

## Jump-power arithmetic

In 1.20.2, `LivingEntity.getJumpPower()` is `0.42F * getBlockJumpFactor() + getJumpBoostPower()`, and Jump Boost contributes `0.1F * (getAmplifier() + 1.0F)` when present (`LivingEntity.java:2003–2009`). 1.20.3/1.20.4 have the same expression at lines 2000–2006. Consequently, for the stated decoded values, the effect contribution is approximately `-12.7F` for stored `-128`, and `0.0F` for stored `-1`. On a full jump-factor block the corresponding total is `0.42F - 12.7F` (approximately `-12.28F`) or `0.42F`.

In 1.20.5, `getJumpPower()` delegates to `getJumpPower(1.0F)`, whose total is `(float)getAttributeValue(Attributes.JUMP_STRENGTH) * multiplier * getBlockJumpFactor() + getJumpBoostPower()`; the effect term remains `0.1F * (amplifier + 1.0F)` (`LivingEntity.java:2057–2067`). With normalized amplifiers 128 and 255, the effect contributions are approximately `+12.9F` (binary32 expression `12.900001F`) and `+25.6F`. These are only the effect terms; the attribute/block-factor product is a separate term and is not evaluated here.

## Jump-from-ground admission

In 1.20.2, `jumpFromGround()` reads current movement and unconditionally writes `getJumpPower()` to Y, then conditionally adds sprint impulse and sets `hasImpulse` (`LivingEntity.java:2011–2020`). There is no positive-power threshold. Exact 1.20.3 and 1.20.4 have the same behavior at lines 2008–2017. A negative computed power is not refused by this method before the Y write.

In 1.20.5, `jumpFromGround()` first computes total jump power and enters the movement write, sprint impulse, and `hasImpulse` update only under `!($$0 <= 1.0E-5F)` (`LivingEntity.java:2069–2081`). The guard is on total power, not on the Jump Boost term. For finite totals, its condition admits only values above the threshold; because the source tests `!($$0 <= 1.0E-5F)`, NaN also passes the predicate. The positive effect contribution therefore does not, by itself, prove admission; the other total-power factors remain relevant.

## Boundary conclusion

The 1.20.2–1.20.4 clientbound effect path uses a signed byte through packet decode and direct instance storage, and its Jump Boost formula uses that signed stored value. Those versions write computed ground-jump power without a positivity admission test. In first checked 1.20.5, the packet representation is VarInt/int, instance construction clamps to `[0,255]`, jump-power arithmetic includes the jump-strength attribute product, and `jumpFromGround()` uses `! (jumpPower <= 1.0E-5F)` as a total-power gate (for finite values, admitting only values above the threshold). Codec representation, instance normalization, arithmetic, and admission are separate changes. This bounded exact-source comparison makes no claim that vanilla ordinarily produces the high amplifier inputs used to illustrate the byte/VarInt boundary.

