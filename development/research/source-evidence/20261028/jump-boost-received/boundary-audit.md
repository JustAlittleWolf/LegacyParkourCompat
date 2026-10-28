# Independent audit — JUMP_BOOST 1.20.2–1.20.5 boundary

## Subject and method

Audited `.native-source/20261028/ordinary-closure/jumpboost-1202-1205-boundary/closure.md` at SHA-256 `BADC720409132F1E4BE4B34587B32FB02899EB102DE017BFB94BF85453C5BD33`. Read only that packet and the exact Mojmap source/provenance inputs it names under `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`. Recomputed source-file hashes; checked ready markers/manifests where present and the hidden per-source provenance map for 1.20.3.

## Result

**Pass.** I found no material discrepancy in the boundary, codec, instance normalization, formula, or jump-admission claims. The closure keeps the codec, storage, arithmetic, and admission stages distinct and does not claim ordinary vanilla gameplay produces the high amplifier values used as examples.

- **Identity/provenance:** 1.20.2, 1.20.4, and 1.20.5 ready markers match the cited version, Mojmap namespace, source counts, and manifest hashes. Their source-file SHA-256 values match the closure and the source manifests. The 1.20.2/1.20.4 provenance records the 1.20.1, 1.20.2, 1.20.4, 1.20.6 Mojmap batch; 1.20.5 records its exact-version run. For 1.20.3, I confirmed there is no ready marker or source manifest. Its hidden `.decompile-provenance.json` contains the cited four source hashes and records the 1.20.3 metadata URL/client input hashes. This supports the stated caveat: 1.20.3 is source-map identified, not ready-marker verified.
- **Packet boundary:** 1.20.2–1.20.4 use a byte field, `readByte`, `writeByte`, and a byte getter. Thus payloads `0x80` and `0xFF` read as signed `-128` and `-1`. The instance-taking packet constructor also masks then narrows, as stated. In 1.20.5 the field/getter are `int`, codec calls are `readVarInt`/`writeVarInt`, and its instance-taking constructor copies the int; values 128 and 255 stay positive through packet decoding.
- **Client storage:** The listener passes the packet getter into `MobEffectInstance` and applies it via `forceAddEffect` in every cited version. The 1.20.2–1.20.4 constructor assigns the amplifier argument directly. The 1.20.5 constructor applies `Mth.clamp(amplifier, 0, 255)`. Therefore the stated post-construction values for the bounded examples are correct: `-128`, `-1` versus `128`, `255`.
- **Jump arithmetic:** The checked 1.20.2–1.20.4 expression is `0.42F * getBlockJumpFactor() + getJumpBoostPower()`, with the effect term `0.1F * (amplifier + 1.0F)`. For the examples this gives approximately `-12.7F` and `0.0F`; on a factor-1 block the totals are approximately `-12.28F` and `0.42F`. In 1.20.5, `getJumpPower()` delegates to the overload using the jump-strength attribute product plus the same effect term. Amplifiers 128 and 255 yield `12.900001F` and `25.6F` as effect terms; the closure correctly does not conflate those with total jump power.
- **Admission:** 1.20.2–1.20.4 write computed jump power without a positive-power guard in `jumpFromGround()`. In 1.20.5 the mutation block is gated by `!($$0 <= 1.0E-5F)` on total jump power. For finite values this admits totals greater than the threshold; NaN passes this negated comparison, as the closure notes.
- **Scope/production caveat:** These packet and client-listener sources establish client representation and handling of supplied packet values. They do not establish ordinary server-side effect production or reachability. The closure expressly limits itself accordingly.

No correction is requested from the closure author.
