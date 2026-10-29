# Focused re-review: pane correction 002

## Verdict

**ACCEPT.** Correction 002 accurately fixes the axis description in correction 001 while retaining the valid WEST-only geometry counterexample. It leaves the broad identical-mask parity claim withdrawn and asserts no broader exact-equivalence subset.

## Bound artifact identities

- Correction 002 `.native-source/20261028/collision-next/dda-pane-family-102-160-correction-002/correction-002.md`: SHA-256 `0A97BDEA5EA3F8E826EB6AFC93C4CA3E072C5CAF0877B4D60CA17D453A577683`.
- Correction 002 `source-hashes.json`: SHA-256 `E2D4E2B9FAF98C141EEB16906607D6D7D664BED427D9BA27E07F488E9E88EFBE`.
- Original report: SHA-256 `13B9095BAF5D7B011E4FA30B8BAAB901F3172557D35C8F0116CE9F2ACFCF91D8`.
- Original review: SHA-256 `18AC38F6B24EE9841F02C76ACF0FBDACA9CCE5596E94628DEA1C98C35D9C9DEE`.
- Correction 001: SHA-256 `2DA42460A1490F081FCFCF5E5D55748A727BF3E1D44AF9FEBED0CA890A5EE68D`.
- Focused rebind 001: SHA-256 `43A66C18F32C43C30649C5C48B97B4D56DC28CE757775CCF4C98DCF603898C49`.

The four source files listed in correction 002's manifest were rehashed against canonical source files and matched their exact source-manifest entries.

## Source check

`CrossCollisionBlock.makeShapes` starts from `Block.column(postWidth, 0.0, postHeight)` and unions the selected arms (`CrossCollisionBlock.java:53–68`). `IronBarsBlock` passes post width 2 and post height 16 (`IronBarsBlock.java:22–27`). `Block.column(sizeXZ, minY, maxY)` applies equal X/Z dimensions and centers both axes; `Block.box` divides coordinates by 16 (`Block.java:160–184`). Thus the normalized post is X `[7/16,9/16]`, Y `[0,1]`, Z `[7/16,9/16]`.

For WEST-only, the old `PaneBlock.addCollisions` emits the west strip X `[0,1/2]`, Y `[0,1]`, Z `[7/16,9/16]`, and emits no second strip (`PaneBlock.java:67–90`). The current west-arm state includes the post, so the current union contains X `(1/2,9/16]` at the central Z strip; those points are absent from the old box. The counterexample is valid after the coordinate correction.

No DDA traversal, camera reachability, product integration, or runtime-parity claim is made. No build, tests, or runtime were run.
