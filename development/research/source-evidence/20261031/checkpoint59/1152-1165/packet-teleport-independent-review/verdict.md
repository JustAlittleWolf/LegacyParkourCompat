# Independent source review — packet receiver residual

## Binding and evidence

- Reviewed report: `.native-source/20261030/1152-1165-finish43/client-packet-player-receivers/report.md`
- Bound report SHA-256: `957A85DBB8915B049139816ADCF467878F9F11975D3BB06734FD935085869354`
- Exact sources: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.15.2\mojmap` and `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.16.5\mojmap`.
- Every one of the 18 source-file entries in the report's `source-hashes.tsv` matched the canonical source file SHA-256.
- Key source hashes (also present in that table):
  - 1.15.2 `ClientPacketListener.java`: `d806d286af4b93d7c61c3c884f344a5870ce1871fb0a811506031adf78fafc30`; `Entity.java`: `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e`; `ClientboundMoveEntityPacket.java`: `7bf671557d36296789f53e8b0b1d576bf946543b0d3388926bebffa2174099ec`; `ClientboundTeleportEntityPacket.java`: `42f636ee7392058e5e785690b208fb5fa071694abf1b6cf80a6d9816fdd76891`.
  - 1.16.5 `ClientPacketListener.java`: `a1c3ea65f40a527d92b1e4d637cad4a8293fdddd1a9d530d778bf39cc2d8670e`; `Entity.java`: `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666`; `ClientboundMoveEntityPacket.java`: `c787f142b1621edfa211b7293f851171562ca79d32299e76718593052a354db7`; `ClientboundTeleportEntityPacket.java`: `f550e22e746e41a3de3323b7f8c1084a3d4ce19751ea54286bc52ee91d98f2ee`.
- Additional reachability refs: 1.15.2 `ClientPacketListener.java:367-375`, `ClientLevel.java:282-294,327-328`; 1.16.5 `ClientPacketListener.java:390-398`, `ClientLevel.java:273-285,318-319`.

## Verdict

**Supported, with the report's stated packet-input boundary.** The teleport receiver delta is source-confirmed and is a direct client-side player position response, which is in project movement scope. On both releases, `handleLogin` adds the local player to the client level under the login ID; `ClientLevel.getEntity` reads that same ID map. `handleTeleportEntity` looks up the packet ID in that level and enters the `!entity.isControlledByLocalInstance()` branch for a local player: `LocalPlayer.isLocalPlayer()` returns true, but `getControllingPassenger()` on the player entity returns null, so `Entity.isControlledByLocalInstance()` falls through to `!level.isClientSide`, which is false on the client. The branch is therefore not inherently remote-only.

The receiver bodies confirm the stated behavior. In 1.15.2, after updating cached packet coordinates, the branch uses the current position when each finite coordinate delta satisfies its corresponding strict threshold (`0.03125`, `0.015625`, `0.03125`); otherwise it uses the packet coordinates. In 1.16.5 it always passes the packet coordinates to `lerpTo`. `Entity.lerpTo` calls `setPos` and `setRot` directly in both versions, so the selected coordinates synchronously affect the player position. `ClientboundTeleportEntityPacket.read` reads the same ID, three doubles, two rotation bytes and ground boolean in both releases; the wire fields/getters are unchanged. The constructor-only `onGround` expression change is not part of decoded receiver input.

**Conditional zero-delta move candidate is also supported as bounded.** Login registration and `getEntity` permit the local-player receiver path. Both `handleMoveEntity` implementations enter the same control predicate. 1.15.2 adds each short delta to `Entity`'s `long xp/yp/zp`, converts the full cached triplet through `packetToEntity`, then calls `lerpTo`. 1.16.5's per-axis update returns an unchanged coordinate when that axis delta is zero, while nonzero axes are floored to 1/4096 units before adding the short; the vector is stored and passed to `lerpTo`. Thus a fractional cached coordinate can be retained on a zero-delta axis in 1.16.5 but quantized by the 1.15.2 whole-vector path. The move-packet subclasses read the same short deltas, rotation bytes and on-ground flag in both versions. The report correctly keeps this conditional on a fractional packet-coordinate state and a packet naming the registered local player.

The local receiver operation is in scope even though this review does not establish which server-side production path emits such entity packets for the owning player. As requested, server packet production is outside this verdict. The report also correctly leaves next-tick arrival/interleaving and runtime parity unproven.

## Precision note

The report calls 1.15.2 `xp/yp/zp` “integer” accumulators. Their exact Java type is `long`; use “long accumulators” if that sentence is reused in a permanent finding. The old teleport gate is written as negated `>=` checks (`!(Math.abs(delta) >= threshold)`), rather than literal `<` checks; these are equivalent for finite coordinates. This wording note does not change the candidate for the stated finite-coordinate case.
