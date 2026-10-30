# Immutable errata for 1.7.10–1.8 source work

These corrections preserve the original worker reports unchanged. Exact source roots and provenance are the pair recorded in the individual audit files: 1.7.10 manifest SHA-256 `11bada441ebe07661402d1c40e1c490b78b279ecaa084f9a4dc3ae2dd54caf6f`; 1.8 manifest SHA-256 `52c8312c34a1a491d4a200c84e90f5d8d95c7e5caa9571f864d8e70f9737db05`.

The old ordinary tick inventory has a transcription typo in the 1.8 Feather mergedv2 artifact hash: it says `3c020d1588b4aab403b5df077ca91b6e3776da2823d3b55202f4616f0d5ae5fe`, while the embedded manifest and source identity checks give `3c020d1588b4aab403f5df077ca91b6e3776da2823d3b55202f4616f0d5ae5fe`. The manifest SHA itself is correctly recorded; use the corrected artifact value above.

## Carpet query result

The initial `collision-step/audit.md` says the zero-height 1.7.10 carpet collision box is rejected by strict intersection. That statement is incorrect. `Box.intersects` checks strict interval separation but does not reject a zero-height box itself. A query whose vertical interval strictly straddles the carpet plane can therefore collect the degenerate old box. A fresh exact-provider review accepted the corrected candidate and its falling witness in `review-shapes/carpet-collision-height.md` (SHA-256 `9D00ADE8D5D806307480AC296C9FF4EF20FC1FA5BAFF8560B710AE25350B7769`); the canonical accepted finding is `canonical-findings/1.7.10--1.8/carpet-collision-height.md` (SHA-256 `3E8F09636ABFA964BD97E0349898CD0B75F1E370C1C962E0D0EFF8FEA68D64F7`). The blanket-rejection line in the earlier audit must not be used.

## Step candidate witness precision

The original airborne-sneak witness using a bottom slab and motion from Y=1.1 was superseded after independent review. The accepted witness is in `canonical-findings/1.7.10--1.8/airborne-sneak-step-eligibility.md` (SHA-256 `C2521B80881BF9A50ABE9122CBB67DD2EEEC8FE737AFC36A23C1CA706D8947B3`): two-layer snow, starting feet Y=1.08, and requested dy=-0.10 ensure post-clip support is established; use `stepHeight=0.5F` on both sides to isolate the gate. The exact float result after offset decay is approximately `0.080000006F`, which exceeds `0.05F`.

The step solver draft now explicitly places the player center at `(x,z)=(0.69,0.5)` so the snow and ceiling blocks at z=0 overlap the player box. Corrected canonical draft SHA-256: `0F76F2AFA2CED7775727B5BBCF0C3ACC38EF7BE1F711DE445A1F663134527C84`. Independent review: `review-steps/independent-review.md`, SHA-256 `F496083F76DF0B33B4821EA5C00101D690DBFA1597881B8DF3F1BD90DECF2E80`.

## Coverage boundary

`collision-step/audit.md` and `block-support/block-support-audit.md` are bounded lane reports, not full provider or pair-freeze claims. The former's old Carpet rejection is superseded above. The block-support report's residual list is superseded for this batch by `unpaired-providers/closure.md`, `entity-list-standing/entity-list-standing-audit.md`, and the accepted fresh candidate reviews. Full independent formula re-derivation across all 43 paired direct collision-provider classes was not completed in this batch; the prior pool42 inventory remains the supporting paired disposition source for those provider branches. No movement runtime parity is claimed.

## Sleep high-Y packet wording

The joined summary describes the old signed-byte Y as a “correction Y.” The signed-byte field is specifically `PlayerSleepS2CPacket.y` (`writeByte`/signed `readByte`) in 1.7.10. The preceding own-player `PlayerMoveS2CPacket` is a separate absolute correction; it is sent first and resets the old sneak offset. The accepted high-Y sleep finding is therefore a sleep-response packet-coordinate wrap, not a position-correction packet encoding difference. The source-backed placement and exact Y=128 result remain as written in `canonical-findings/1.7.10--1.8/sleep-response-high-bed-coordinate-wrap.md`; the distinction is explicit in `input-flight/sleep-correction-review.md`.

## Provider audit completion supersedes earlier boundary note

The initial `Coverage boundary` note above stated that all-43 provider formula re-derivation was incomplete. That statement was true before the fresh provider passes. It is superseded by `provider-formulas/a.md`, `missing-lever-lilypad-liquid.md`, `provider-formulas/b.md`, and `provider-formulas/c.md`, which collectively cover all 43 paired direct provider classes, plus `unpaired-providers/closure.md` for Carpet/Banner declaration membership. The blind full-pair reviewer is preparing a separate independent coverage verdict; until that verdict, this source worker does not self-certify the pair freeze.

## Blind review final hash supersedes prior handoff hash

The reviewer first delivered `blind-pair-review/fresh-review.md` at SHA-256 `7A627ED3FF5EE74842351E05DDCA0192A2BE72BA3B477EFA352BFA3332EB9186`. The reviewer then added the paired bodies and source hashes for the four common whole-tree `Entity.getCollisionShape()` declarations (base Entity, Boat, Minecart, Fireworks Spark), retaining the original phase-1 inventory hash inside the report. The final report bytes were mechanically rehashed as `25DC286F68A252558208655370F34D90F4528D97F391FD61A410437D3E829ABE`. Reviewer confirmed this exact final hash with verdict **ACCEPT WITH CORRECTIONS; source coverage freeze accepted**. The earlier `7A627...` value is superseded and must not identify the final report. Runtime parity remains unverified.

The earlier `Provider audit completion supersedes earlier boundary note` paragraph was written before the independent reviewer finished; its final sentence saying the reviewer was still preparing a verdict is superseded by the completed verdict recorded above.
