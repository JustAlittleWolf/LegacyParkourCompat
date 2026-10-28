# Received Jump Boost amplifier interpretation and ground-jump admission

1.20.4, Mojmap: `ClientboundUpdateMobEffectPacket` constructor, read/write, and `getEffectAmplifier`; `ClientPacketListener.handleUpdateMobEffect`; `MobEffectInstance` constructor; `LivingEntity.getJumpBoostPower` and `jumpFromGround`.

1.20.5 and 1.20.6, Mojmap: the same classes and members. The exact 1.20.2 and 1.20.3 source closure establishes the earlier aliases of the bounded implemented interval.

The older packet stores an eight-bit amplifier, decodes with `readByte`, and sign-extends it when constructing the active effect. The instance stores that value directly. The newer packet uses VarInt and the instance clamps to 0..255. At the unchanged float expression `0.1F * (amplifier + 1.0F)`, received values 128..254 therefore contribute negative boost in the older client. Value 255 becomes -1 and contributes zero.

The older ground-jump body unconditionally writes its computed power and performs the sprint/impulse response. From 1.20.5, the body is admitted only when the computed float power is not <= 1.0E-5F. Amplifier interpretation and power admission are separate operations: changing the read alone would let the newer guard suppress the older negative-power response.

The local-player input reaches the inherited jump stage through the controlled-camera input copy, fluid branch, grounded state and zero jump-delay gates. The ordinary default-attribute response with equivalent received effect state is source-supported. This finding does not establish natural effect production, an input-only trajectory, or runtime parity. Modern jump-strength attribute production and unrelated sprint/velocity operations are separate from these two changes.

Full immutable source reports, exact source identities, arithmetic and independent reviews are retained in `development/research/source-evidence/20261028/jump-boost-received/`.
