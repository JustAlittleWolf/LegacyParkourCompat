# Focused review: pane-family correction supplement

## Verdict

**REVISE one coordinate description; accept the correction's substantive withdrawal and WEST-only counterexample.** The supplement is correctly bound to the original report, its original source-hash JSON, and the independent REVISE review. It withdraws the overbroad claim that any identical nonempty connection mask has matching old/current geometry and leaves general equivalence unclaimed.

## Bound artifact identities

- Correction `.native-source/20261028/collision-next/dda-pane-family-102-160-correction/correction.md`: SHA-256 `2DA42460A1490F081FCFCF5E5D55748A727BF3E1D44AF9FEBED0CA890A5EE68D`.
- Its `source-hashes.json`: SHA-256 `6C0ADD459D5EF218B960ED21E2C3784ECC41738D5B80591B698E1EA7398F001F`.
- Original report `dda-pane-family-102-160/report.md`: SHA-256 `13B9095BAF5D7B011E4FA30B8BAAB901F3172557D35C8F0116CE9F2ACFCF91D8`.
- Original source manifest `dda-pane-family-102-160/source-hashes.json`: SHA-256 `5595B40655F69C035428A09CF28ADE6AA10BB811E789DB8FE2A4F25FB08D9D47`.
- Independent review `dda-pane-family-102-160-review/review.md`: SHA-256 `18AC38F6B24EE9841F02C76ACF0FBDACA9CCE5596E94628DEA1C98C35D9C9DEE`.

All three correction source entries were recomputed against the canonical trees and matched both their listed SHA-256 values and source-manifest entries. The original 16 source entries, ready-marker identities, and source-manifest hashes were also rechecked and matched the original JSON.

## Coordinate correction

The correction says the modern central post spans X `[7/16, 9/16]` “through Z `[0,1]`.” That is an axis-label/extent error. `CrossCollisionBlock.makeShapes` uses `Block.column(postWidth, 0, postHeight)` for the post (`CrossCollisionBlock.java:53–58`); `Block.column` centers the width on both horizontal axes (`Block.java:177–184`). For `IronBarsBlock`'s `postWidth=2/16` and `postHeight=16/16` (`IronBarsBlock.java:22–27`), the post is X `[7/16,9/16]`, Y `[0,1]`, Z `[7/16,9/16]`.

This coordinate correction does not change the counterexample. The old WEST-only box is X `[0,1/2]`, Y `[0,1]`, Z `[7/16,9/16]` (`1.8.9 PaneBlock.java:67–73`), while the current WEST-only union contains the post; therefore points with X in `(1/2,9/16]` and Z in `[7/16,9/16]` are still current-only. The substantive correction remains valid.

## Accepted scope

Accept the supplement's binding, WEST-only counterexample, withdrawal of the broad mask-equivalence claim, and preservation of the identity/color-alias/provider/ray-refresh facts. The central-post coordinates above should be corrected in any downstream record. The supplement's explicit refusal to assert any broader mask-equivalence subset is appropriate; this review does not establish one.

No DDA traversal, ray-hit, runtime, or product-parity claim is made. No build, tests, or runtime were run.
