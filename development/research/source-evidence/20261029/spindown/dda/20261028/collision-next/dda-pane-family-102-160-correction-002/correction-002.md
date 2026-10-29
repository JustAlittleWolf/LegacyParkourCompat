# Immutable correction 002: pane WEST-only post coordinates

## Binding

This supplement corrects the coordinate description in correction 001; it does not replace any earlier report or supplement. Bound evidence:

- Original pane-family report: SHA-256 `13b9095baf5d7b011e4fa30b8baab901f3172557d35c8f0116ce9f2acfcf91d8`.
- Correction 001: SHA-256 `2da42460a1490f081fcfcf5e5d55748a727bf3e1d44af9febed0ca890a5ee68d`.
- Original independent review: SHA-256 `18AC38F6B24EE9841F02C76ACF0FBDACA9CCE5596E94628DEA1C98C35D9C9DEE`.
- Focused correction rebind: SHA-256 `43A66C18F32C43C30649C5C48B97B4D56DC28CE757775CCF4C98DCF603898C49`.

Exact canonical source hashes for the geometric statements are in `source-hashes.json`.

## Coordinate correction

The WEST-only counterexample and the conclusion that the geometries differ are preserved. Correction 001 described the modern central post with the wrong axes. The correct current `Block.column` geometry is X and Z `[7/16, 9/16]`, Y `[0, 1]`: `CrossCollisionBlock.makeShapes` calls `Block.column(postWidth, 0.0, postHeight)` (`CrossCollisionBlock.java:56`); `IronBarsBlock` supplies `postWidth=2` and `postHeight=16` (`IronBarsBlock.java:31`); `Block.column(sizeXZ,minY,maxY)` uses equal X/Z widths, and its overloaded implementation centers both horizontally (`Block.java:177-185`). The `Block.box` helper scales coordinates by 1/16 (`Block.java:160-161`), yielding the stated normalized bounds.

For the WEST-only legacy case, `PaneBlock.addCollisions` emits X `[0, 1/2]`, Y `[0, 1]`, Z `[7/16, 9/16]` (`PaneBlock.java:67-73`; its second branch emits no other segment for this mask, `:80-90`). The modern west arm is unioned with the central post (`CrossCollisionBlock.java:58-67`). The post adds points with X `(1/2, 9/16]`, Z `[7/16,9/16]`, and Y `[0,1]`; those points are absent from the old west strip. Therefore the geometric mismatch remains proven, but correction 001's statement that the modern post extends through all Z values is withdrawn.

The general identical-nonempty-mask parity claim remains withdrawn. No broader exact equivalence subset is asserted. All other conclusions from correction 001 remain as written, including the source-supported alias/provider findings and the DDA/runtime non-claims.
