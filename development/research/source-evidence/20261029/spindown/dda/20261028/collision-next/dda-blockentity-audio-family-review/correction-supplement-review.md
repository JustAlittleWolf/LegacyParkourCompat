# Correction-supplement rebind review: block-entity cube classification

**Verdict: ACCEPT.** This focused rebind closes the sole issue recorded in the original independent review. The original review remains immutable with its historical REVISE verdict.

## Exact bound inputs

- Correction supplement: `.native-source/20261028/collision-next/dda-blockentity-audio-family-25-84/correction-supplement.md`
  - SHA-256: `341B6EBDC13A1B64B4BCD3822890E17B97BAF7A4AD2C728F9D5024AB5647AE33`
- Finding: `dda-blockentity-audio-family-25-84/FINDING.md`
  - SHA-256: `42DA4BD7258AD6AA9653A4AC8A1DE13A6C7451C177780AFC95F324885F1475A8`
- Prior review, preserved unchanged: `dda-blockentity-audio-family-review/review.md`
  - SHA-256: `932B1C26523F18A97D81FA1093942ACECC08BF10B6F5F6194A0EA16E87F928FC`
- Source checked: 1.8.9 Ornithe Feather `net/minecraft/block/Block.java`
  - SHA-256: `EA10F05106A3CF7189AEC85236A7ECF9C7106A717F37BED583B5EA4ADEFFA528`

## Rebind result

Canonical source confirms `Block.isCube()` returns `true` unconditionally at lines 245–247. Lines 671–673 implement `hasSilkTouchDrops()` as `isCube() && !hasBlockEntity`; they do not implement cube classification. `BlockWithBlockEntity` setting the flag therefore affects this silk-touch predicate, not `isCube()`. The supplement correctly withdraws the mistaken full-cube statement and gives the supported replacement. It leaves the separate unit collision-box and inherited ray-box conclusions unchanged.

No broader review or DDA visitation/hit-parity claim is made.
