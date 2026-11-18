# Independent source audit: negative-Y dry packet producer

## Verdict

**Reject the modern cleanup-loss claim as written; accept the bounded 1.13-family producer trace and exact-vector non-representability in the 26.2 motion codec.** The draft's 26.2 pack result is arithmetically incorrect: the actual `LpVec3.pack` expression rounds normalized Y to `16358`, not `16359`. Decoding at scale 2 gives `-50 / 16383` (approximately `-0.0030525`), whose magnitude is greater than `0.003`; the ordinary `LivingEntity` cleanup therefore retains this quantized Y. This packet path changes the exact value, but does not zero Y at that cleanup gate.

## 1.13 / 1.13.1 / 1.13.2

The trace is source-confirmed for all three releases. `EntityVelocityS2CPacket.read` reads signed shorts. The normal velocity handler resolves the packet entity ID, divides each component by `8000.0`, and calls `Entity.lerpVelocity`, which assigns the doubles directly. For shorts `(16000, -24, 0)`, X is `2.0`, Y has the same binary64 value as Java `-0.003`, and Z is `0.0`. `LivingEntity` clears Y only under strict `Math.abs(velocityY) < 0.003`; equality does not satisfy that predicate. No water-branch condition is involved in this packet handler/setter, so the received vector reaches the later ordinary travel path as state. The conclusion covers packet receipt and cleanup, not the downstream dry collision result or a server-side cause.

The concrete paths are `EntityVelocityS2CPacket.java` read at lines 54–60, `ClientPlayNetworkHandler.java` handler at 499–505 (1.13) / 507–513 (1.13.1–1.13.2), `Entity.java` setter at 1729–1733 (1.13) / 1730–1734 (1.13.1–1.13.2), and `LivingEntity.java` cleanup at 1859–1868 (1.13) / 1878–1887 (1.13.1–1.13.2), all under `ornithe-feather`.

## 26.2

`ClientboundSetEntityMotionPacket.STREAM_CODEC` uses `Vec3.LP_STREAM_CODEC`; the client applies its decoded movement by entity ID through `lerpMotion`, which directly assigns delta movement. `LpVec3.write` selects `scale = ceil(maxAbs)`, then packs each normalized component using `Math.round((value * 0.5 + 0.5) * 32766.0)`. For `(2.0, -0.003, 0.0)`, scale is 2; codes are X `32766`, Y `16358`, Z `16383`. `unpack(16358) * 2` is `-50/16383`, not `-48/16383`. This survives the exact `LivingEntity` strict Y threshold check at lines 3079–3083.

Exact `-0.003` is not representable by any Y code in this codec when X and Z decode to 2 and 0. The decoded component has form `S * (k/16383 - 1)` for integer scale `S` and integer unpacked code `k`. Since 16383 is odd, any such value that is a non-integral dyadic rational would retain an odd denominator; exact Java `-0.003` is a non-integral dyadic rational. Cancelling the odd denominator can only yield an integer, so it cannot equal `-0.003`. This conclusion is limited to `LpVec3`'s representation on this packet path; it does not claim that no other 26.2 movement source can produce the value.

Relevant exact source members: `ClientboundSetEntityMotionPacket.STREAM_CODEC` lines 11–18; `LpVec3.read/write/pack/unpack` lines 24–38, 41–63, 69–75; `ClientPacketListener.handleSetEntityMotion` lines 623–629; `Entity.lerpMotion` lines 2647–2649; and `LivingEntity` cleanup lines 3060–3083.

## Evidence identity

The submitted draft `negative-y-dry-producer.md` has raw SHA-256 `DE7EE28D2291EE41C1E6386E77B379EE30CBC858596DAFE7210642E9C08780DB`, matching the requested identity. For all cited relevant source files in 1.13, 1.13.1, 1.13.2 and 26.2, the computed SHA-256 matched the corresponding published `*.sources.sha256` manifest entries. Provenance identifies the old sources as Ornithe Feather and 26.2 as unobfuscated.

## Scope

This is an independent exact-source audit of the specified producer/codec path. It did not inspect product implementations, source history, wiki material or backlog, and performed no network/app operation, code edit, test, build, launch or decompilation.
