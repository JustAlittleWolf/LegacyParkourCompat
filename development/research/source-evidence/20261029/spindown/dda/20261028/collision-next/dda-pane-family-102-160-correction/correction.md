# Immutable correction supplement: pane-family geometry claim

## Binding

This supplement corrects, but does not replace, `.native-source/20261028/collision-next/dda-pane-family-102-160/report.md` (SHA-256 `13b9095baf5d7b011e4fa30b8baab901f3172557d35c8f0116ce9f2acfcf91d8`). It responds to the independent review (SHA-256 `18AC38F6B24EE9841F02C76ACF0FBDACA9CCE5596E94628DEA1C98C35D9C9DEE`). The source identities needed to verify this correction are listed in `source-hashes.json`.

## Accepted counterexample and withdrawn claim

The original report's conditional statement that old and current geometry match whenever a nonempty connection mask is identical is withdrawn. Identical direction bits do not guarantee an identical geometric union.

Concrete WEST-only case: the legacy neighbor predicate returns true only for WEST. In `PaneBlock.addCollisions`, north/south/east are false, so lines 67–73 set bounds to x `[0, 0.5]`, y `[0, 1]`, z `[7/16, 9/16]`; the second-axis branch emits no extra strip for the matched WEST-only input (lines 80–90). In 26.2, `IronBarsBlock` constructs panes with a 2/16 post (`IronBarsBlock.java:31`), and `CrossCollisionBlock.makeShapes` begins with that post and unions an arm for each true direction bit (`CrossCollisionBlock.java:56-67`). The central post itself spans x `[7/16, 9/16]` through z `[0,1]`, so the current WEST-only union contains points with x in `(0.5, 9/16]` that the old WEST strip does not. The geometry therefore differs even when the WEST bit agrees.

No broader exact geometry-equivalence subset is asserted by this supplement. The source snippets do establish the specific old WEST strip and modern central-post contribution above. Any narrower equivalence claim needs a separate proof covering the exact directional masks and component union.

## Findings preserved

The correction is limited to the geometry comparison. The following findings from the bound original report remain supported by its source hashes: old registry IDs 102 and 160; the full 16-color state alias mapping including old `silver` to current `light_gray`; historical `addCollisions` reading current neighboring blocks and changing mutable pane bounds; and historical `Block.rayTrace` invoking the pane's `updateShape` before using bounds. The existing non-claims about DDA traversal, hit selection, universal neighbor-rule equivalence, and runtime reachability remain in force.

## Updated bounded conclusion

Current pane geometry is selected from stored directional flags and always includes a central post. Historical collision strips are computed from live neighbor identities and can omit portions of that post, as WEST-only demonstrates. The pane family is source-closed for identity, aliases, and provider side effects, but not geometrically equivalent as a general legacy-to-current provider. No DDA traversal or runtime parity follows from this source closure.
