# Independent audit — jump-from-ground admission rev 2

**Verdict: accepted for the stated bounded claims.** Audited only `closure-rev2.md` (SHA-256 `CAC5E9878324218B993026C128C3CE68957FD3C3210C40B5110E63B2DBC00CD0`) against the named exact 1.20.4 Mojmap and 26.2 unobfuscated sources.

## Findings

- The codec and storage chain supports the bounded received values. In 1.20.4 the packet's `byte` field is masked when constructed, read as a signed byte and passed by the client listener into the effect instance, whose constructor assigns the amplifier directly. Thus 128 arrives at the formula as -128 and 255 as -1. In 26.2 the packet uses VarInt, the client listener passes its `int` into `MobEffectInstance`, and that constructor clamps it to 0–255. This confirms 128 and 255 remain those values. The correction properly avoids inferring ordinary production or reachability of other values from codec behavior.
- The arithmetic is consistent with binary32 operation order. In 1.20.4, `0.1F * (-128 + 1.0F)` rounds to the float represented by `-12.7F`; adding it to the full-factor `0.42F` yields the float represented by `-12.28F`. For amplifier 255, the decoded -1 makes the boost zero. In 26.2, `0.1F * (128 + 1.0F)` rounds to the float represented by `12.900001F` (about +12.9), while `0.1F * (255 + 1.0F)` is represented by `25.6F`. The revision's more precise 128 contribution is correct. The non-effect attribute/block-factor product remains outside this audit, as the closure states.
- The admission behavior matches the source. 1.20.4 unconditionally writes computed jump power into Y, then applies the sprint impulse and sets `hasImpulse`. 26.2 wraps the Y response, sprint impulse, and `needsSync` update in `!(jumpPower <= 1.0E-5F)`, and uses `Math.max(jumpPower, movement.y)` when admitted. The finite-value qualification correctly avoids implying that NaN is rejected by this predicate.
- The described ordinary dry-ground local-player route is supported: the input state is copied for the controlled local camera; the living-entity jump stage requires jumping and `isAffectedByFluids()`; and its dry-ground branch requires `onGround()` and zero jump delay before calling `jumpFromGround()` and setting delay 10. In 26.2 the separate power gate can then reject the response. The endpoint-only and no-intermediate-cutover qualifications are appropriate.

## Source evidence checked

Exact canonical source files read: `LivingEntity.java`, `MobEffectInstance.java`, `ClientboundUpdateMobEffectPacket.java`, `ClientPacketListener.java`, `LocalPlayer.java`, and `Player.java` from `1.20.4\mojmap` and `26.2\unobfuscated`, under `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`.

No source or product files were changed. No build, tests, game launch, or source generation was run.
