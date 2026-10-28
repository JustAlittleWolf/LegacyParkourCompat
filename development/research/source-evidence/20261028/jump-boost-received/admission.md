# JUMP_BOOST jump-from-ground admission closure

## Scope and source identity

Bounded comparison: exact 1.20.4 to exact 26.2, for a client-received JUMP_BOOST and the ordinary local-player jump-from-ground path. This establishes endpoint behavior only; it does not establish an intermediate implementation cutover.

Canonical read-only sources are under `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`. The 1.20.4 folder is `1.20.4\mojmap` (ready marker: version `1.20.4`, mapping `mojmap`, 5,048 files, Vineflower, Java 25); the 26.2 folder is `26.2\unobfuscated` (ready marker: version `26.2`, mapping `unobfuscated`, 7,055 files, Vineflower, Java 25). Both ready markers and their `exactRun` provenance records match those identities. The recorded exact runs use Gradle 9.7.1, Java 25.0.3+9-LTS, Vineflower 1.12.0, ASM 9.10.1, Mapping IO 0.9.1, Gson 2.14.0, and Tiny Remapper 0.14.1. The 1.20.4 Mojmap run is a recorded four-version batch; 26.2 is a separate one-version unobfuscated run. 26.2 provenance has `mappingArtifacts: null`, as expected for the recorded `unobfuscated` target.

The ready-marker source-manifest SHA-256 values were recomputed and match: 1.20.4 `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1`; 26.2 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`. Artifact-manifest SHA-256 values also match their ready markers: 1.20.4 `ee3efc771d264c0bc49d5472abb9763a646f8d221841d0a61201ecd991cb3948`; 26.2 `ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba`. Selected class hashes were recomputed against the source manifests; relevant entries include:

| Class | 1.20.4 Mojmap | 26.2 unobfuscated |
|---|---|---|
| `LivingEntity.java` | `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d` | `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a` |
| `MobEffectInstance.java` | `e1aac04c3f5714c5c47f84375f1d930204bd177218c05d8c0b590d70381aa249` | `a9bfa80bbfeb7e5fcb561fad90d464112da5f9e63cea581cda0072ae28eb5af7` |
| `ClientboundUpdateMobEffectPacket.java` | `88a2692d0a0fc83753ee1b6ba558030c7836d12d25b4752457fb86ac353a3f3a` | `d4dcc462739d3514b7dff6a0c757819bb84dc885d710700c4aef1c9e112389c0` |
| `ClientPacketListener.java` | `ff9c8222614551075b03454ee78712b0d39f0f51845e74bff76a81486f09b42f` | `9cb0cc8afebae9f4e42f428a52719c645817d03893dc371e6711c7bd16eac1b6` |
| `LocalPlayer.java` | `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` | `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6` |
| `Player.java` | `218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd` | `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531` |

## Effect amplifier decoding and storage

**1.20.4, Mojmap.** `ClientboundUpdateMobEffectPacket.java:23–27,45–51,55–61,76–78` stores a byte, constructs it as `(byte)(amplifier & 0xFF)`, reads it with `readByte`, writes it with `writeByte`, and returns it as `byte`. The client listener passes that signed byte to the `MobEffectInstance` constructor (`ClientPacketListener.java:1501–1518`); that constructor assigns the amplifier directly (`MobEffectInstance.java:61–70`). Thus the specified wire/source value 128 reaches the jump formula as -128, and 255 reaches it as -1.

**26.2, unobfuscated.** The packet stores/returns an `int`, reads and writes the amplifier with VarInt (`ClientboundUpdateMobEffectPacket.java:20–29,50–64,83–85`), and the listener passes it into `MobEffectInstance` (`ClientPacketListener.java:1794–1812`). That constructor stores `Mth.clamp(amplifier, 0, 255)` (`MobEffectInstance.java:71–87`). Therefore 128 remains 128 and 255 remains 255. Packet decoding and instance normalization are separate operations.

The amplifier claims here are limited to 128 and 255. A 256 input would become zero through the 1.20.4 byte mask, but this is codec behavior only; this review has no reachability proof for 256 and makes no vanilla-reachable claim for it. Nor does the byte codec by itself establish that every value from 128 through 255 is ordinarily produced.

## Jump-power arithmetic

**1.20.4.** `LivingEntity.java:2000–2006` computes `0.42F * getBlockJumpFactor() + getJumpBoostPower()`. With JUMP present, `getJumpBoostPower()` is `0.1F * (getAmplifier() + 1.0F)`. For amplifier 128, signed-byte decoding gives -128, so the boost term is `-12.7F`; on a full jump-factor block the resulting jump power is `0.42F - 12.7F` (approximately `-12.28F`). For amplifier 255, the stored value is -1, so its boost term is zero and the full-factor result is `0.42F`.

**26.2.** `LivingEntity.java:2376–2385` delegates to the multiplier overload and computes `(float)getAttributeValue(Attributes.JUMP_STRENGTH) * 1.0F * getBlockJumpFactor() + getJumpBoostPower()`. JUMP_BOOST contributes `0.1F * (clampedAmplifier + 1.0F)`: `+12.9F` for 128 and `+25.6F` for 255. The attribute and block-factor product remains part of the total; this closure does not audit its production.

## Jump-from-ground admission and ordinary gates

In 1.20.4, `LivingEntity.java:2008–2017` unconditionally writes the computed jump power into the Y component, then applies the sprint impulse if sprinting and sets `hasImpulse`. There is no positive-power test. Consequently, the specified received amplifier 128 can produce a negative Y assignment on a full-factor block; the method does not reject it as a failed jump. Amplifier 255 produces the normal positive base term on that block.

In 26.2, `LivingEntity.java:2388–2401` first computes jump power and enters the response only if `!(jumpPower <= 1.0E-5F)`. For finite values, nonpositive power is rejected before vertical movement, sprint impulse, or `needsSync` is written. If admitted, the Y component is `Math.max(jumpPower, movement.y)`, rather than an unconditional assignment. This admission check is distinct from the positive JUMP_BOOST term and clamp: if some other part of the total made power nonpositive, the guard would still reject it.

The ordinary local-player route reaches this method under these gates:

* In 1.20.4, `LocalPlayer.serverAiStep()` copies `input.jumping` to `jumping` for the controlled camera (`LocalPlayer.java:609–620`); `Player.aiStep()` delegates to `LivingEntity.aiStep()` (`Player.java:506–524`). The living-entity jump stage requires `jumping && isAffectedByFluids()`; for an ordinary dry ground jump it selects `onGround() && noJumpDelay == 0`, invokes `jumpFromGround()`, and sets `noJumpDelay = 10` (`LivingEntity.java:2551–2587`).
* In 26.2, `LocalPlayer.applyInput()` copies the jump key state for the controlled camera (`LocalPlayer.java:692–705`), and `LocalPlayer.aiStep()` delegates through `Player.aiStep()` (`LocalPlayer.java:767–914`, `Player.java:440–453`). `LivingEntity.aiStep()` calls `applyInput()` before its jump stage; its `jumping && isAffectedByFluids()` gate and ordinary dry-ground `onGround() && noJumpDelay == 0` branch still reach `jumpFromGround()` and set the delay to 10 (`LivingEntity.java:3083–3123`). Its `serverAiStep()` is guarded by `!level().isClientSide()` at `LivingEntity.java:3091–3095`; the local player's input route is the `applyInput()` override.

For the requested ordinary dry-ground route, both versions require an active jump input and an eligible fluid-affecting entity, ground contact, and zero jump delay before invoking `jumpFromGround()`. In 26.2, an additional independent power gate may then suppress the actual ground-jump response. The surrounding water/lava branches differ in detail but do not change this dry-ground route.

## Bounded conclusion

For the specified received amplifier 128, 1.20.4 decodes the byte as -128 and can write strongly negative Y on an ordinary full-factor ground block. Exact 26.2 preserves 128 through VarInt and the [0,255] instance clamp, yielding a positive `+12.900001F` effect contribution (approximately `+12.9F`); it also rejects any finite total jump power at or below `1.0E-5F` before applying ground-jump movement. For the specified amplifier 255, 1.20.4 decodes to -1 and adds zero boost, while 26.2 retains 255 and adds `+25.6F`. These are source facts for the endpoints and bounded ordinary path; they do not identify when a change first appeared between the versions.
