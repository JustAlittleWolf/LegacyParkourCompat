# Iron bars ID 101: immutable neighbor-admission supplement

## Bound artifacts and scope

This supplement extends the bounded ID 101 finding without changing it. It binds:

- `FINDING.md` (ID 101 local geometry): SHA-256 `C706322C5A9B90252BB6B2882052C227554C9F569866A26CDC14EDCDD634918C`.
- `source-hashes.json` (ID 101 source ledger): SHA-256 `DC20F215A815A7D02A4EA6BEC87641A9335F3C7C04163267148482F6A8A58FE2`.
- `.native-source/20261028/collision-next/dda-iron-bars-101-review/review.md`: SHA-256 `4ED98BB7824F0EB97F7A16FD83CB42F594F7A74E3F2B28D06206F524B6BDD7E8` (ACCEPT, limited to ID101 identity/default state and all-air local geometry).
- `.native-source/20261028/collision-next/dda-pane-family-102-160/report.md`: SHA-256 `13B9095BAF5D7B011E4FA30B8BAAB901F3172557D35C8F0116CE9F2ACFCF91D8`; its original review rejected a broad geometry claim but verified the pane admission predicate. The focused correction 002 review is ACCEPT (correction hash `0A97BDEA5EA3F8E826EB6AFC93C4CA3E072C5CAF0877B4D60CA17D453A577683`; review hash `18AC38F6B24EE9841F02C76ACF0FBDACA9CCE5596E94628DEA1C98C35D9C9DEE`). This is used only for the exact predicate and pane-family identity facts.
- `.native-source/20261028/collision-next/dda-cobblestone-wall-139-closure/report.md`: SHA-256 `3DC40166D0E3A017474FB5CDBD2A88BCE22675D89F67B1F64A4426E46939D459`; independent review accepts the bounded provider facts, not general conversion.

Exact canonical Java file hashes, readiness markers, and manifest hashes used for this supplement are in `neighbor-source-hashes.json`. Every source entry was verified against the relevant ready source tree's canonical `*.sources.sha256` manifest.

## Historical ID 101 admission rule

The exact 1.8.9 `PaneBlock.shouldConnectTo(Block)` rule is:

`neighbor.isOpaque() || neighbor == this || neighbor == Blocks.GLASS || neighbor == Blocks.STAINED_GLASS || neighbor == Blocks.STAINED_GLASS_PANE || neighbor instanceof PaneBlock`.

`Block.isOpaque()` returns its `opaqueCube` field. `Block` initializes that field from `isSolidRender()` when the block is constructed. Therefore the first clause is a legacy block-instance classification, not a call to `isCube()`, material solidity, face support, or current face-sturdiness. It does not inspect neighbor state properties. ID 101 itself is a `PaneBlock`, so the explicit same-instance test is subsumed by the final `instanceof PaneBlock` clause for this block, but both source clauses are retained here.

The explicit named exceptions are positive admissions: old ID 20 `glass`, ID 95 `stained_glass`, and ID 160 `stained_glass_pane`; the final class test admits every legacy `PaneBlock`, including IDs 101, 102, and 160 regardless of pane color. IDs 102 and 160 are separately registered pane families; source alias/color closure for those two appears in the bound pane-family report/correction. ID 101's current default alias/state remains as documented in the bound base finding.

The generic opaque branch is broader than those named pane keys. It admits any adjacent old block whose constructed instance has `opaqueCube=true`. The state serializer does not decide that predicate. A complete adapter still needs a source-backed old registry-ID roster for this generic set and a justified mapping of each admitted identity to current state/input; this supplement does not enumerate that set or substitute a modern sturdy-face check.

## Explicitly classified adjacent families

- **Panes and panes of any color:** old `instanceof PaneBlock` admits them. The source-closed old pane IDs relevant here are 101, 102, and 160. Current glass panes are `IronBarsBlock`; stained glass panes are `StainedGlassPaneBlock`, a subclass of `IronBarsBlock`. Current class admission is structurally similar for those mapped pane families, but modern `IronBarsBlock` subclasses may include modern-only blocks, so current class membership is not by itself a historical identity mapping.
- **Glass blocks:** old IDs 20 and 95 are explicit admissions even though pane admission does not use their opaque/full-cube classification. The current IronBarsBlock rule has no explicit glass or stained-glass exception. The exact same-name current aliases are supported by `EntityBlockStateFix` (IDs 20 and 95) and `Blocks` registrations. Their historical positive exception must still be represented explicitly by an adapter; do not assume modern sturdy-face logic preserves it.
- **Fences:** old IDs 85, 113, and 188–192 are `FenceBlock` instances. `FenceBlock.isSolidRender()` is false, and `PaneBlock` has no fence-specific alternative, so they fail the old admission predicate. Their ordinary fence class is not admitted by current `IronBarsBlock.attachsTo` as a class exception; it can pass only through `!isExceptionForConnection(state) && faceSolid`. This current state/face predicate is distinct and its behavior for each mapped legacy fence state is not closed here.
- **Fence gates:** old IDs 107 and 183–187 are `FenceGateBlock` instances; that class also returns false from `isSolidRender()`, and there is no old gate-specific pane exception. They fail old pane admission regardless of OPEN/FACING. Current IronBarsBlock has no dedicated gate rule; a gate neighbor can pass only through the non-exception sturdy-face branch. State/face outcomes remain unresolved for adapter use.
- **Walls:** old ID 139 is `WallBlock`; `WallBlock.isSolidRender()` is false and the pane rule has no wall-specific exception, so it fails old pane admission even though wall geometry is collidable. Current IronBarsBlock explicitly admits any state in `BlockTags.WALLS`, independent of the face-solid argument. Thus modern wall-tag admission cannot be used as a proxy for old ID 101's rule. This comparison is consistent with the accepted bounded ID 139 source closure, which separately records the wall-family provider differences.
- **Barrier and other modern connection exceptions:** old pane rule has no explicit barrier exclusion; whether an old neighbor reaches its generic branch is determined by that neighbor instance's `isOpaque()` value. Current `Block.isExceptionForConnection` excludes LeavesBlock, barrier, carved pumpkin, jack o'lantern, melon, pumpkin, and shulker boxes from the sturdy-face arm. Current `IronBarsBlock.attachsTo` still admits the IronBarsBlock class and wall-tag branches after that exclusion. This is not equivalent to the old one-predicate rule; each historical identity/state requiring an exception check needs its own mapping evidence.

## Current admission rule

26.2 `IronBarsBlock` placement and horizontal `updateShape` query each adjacent `BlockState` and its face toward the bars, then call `attachsTo(state, faceSolid)`. The exact boolean rule is `(!Block.isExceptionForConnection(state) && faceSolid) || state.getBlock() instanceof IronBarsBlock || state.is(BlockTags.WALLS)`. `faceSolid` is the queried neighbor face's `isFaceSturdy` result. `CrossCollisionBlock` then uses stored direction properties for its cached collision shape. This differs from old ID 101's live-neighbor predicate by using a state/face input, a modern exception blacklist, a broad wall-tag admission, and all current IronBarsBlock subclasses.

## Remaining adapter gate: explicit unresolved set

The source proves these individual historical family identities and old predicate results, but does not yet prove a usable full adjacency adapter:

1. The opaque-class registry remainder: every 1.8.9 registered neighbor ID whose block instance has `opaqueCube=true`, other than the explicitly named IDs above, still needs an enumerated ID-to-current-key/state projection and a reviewed current-side treatment. This set is unresolved as a whole; it is not silently equated to full cubes or sturdy faces.
2. The glass exception IDs 20 and 95 have exact same-name current aliases in `EntityBlockStateFix` and registered current blocks; the adapter still needs an explicit old-rule inclusion even when the modern queried face is not sturdy.
3. The pane IDs 101, 102, and 160 have exact identities, but modern `IronBarsBlock` subclass matching can also include modern-only identities. Only the mapped historical IDs are admitted by legacy `instanceof PaneBlock` evidence.
4. The legacy fence IDs 85, 113, 188–192; gate IDs 107, 183–187; and wall ID 139 are explicitly non-admitted by the old opaque clause, while current face-sturdy/wall-tag logic may admit them. Their current representations and per-direction state results must not be treated as equivalent without a separate adapter decision.
5. Current modern-only `IronBarsBlock` subclasses have no old ID; a concrete example is `WeatheringCopperBarsBlock`, registered in the copper-bars collection. Current wall-tag membership also exceeds the old ID 139 wall identity. Such inputs must preserve native behavior unless the operation explicitly limits its input domain.
6. The old barrier and other nonopaque, nonpane identities outside the listed cases are not an enumerated list in this supplement. Their answer is mechanically `isOpaque()` for the historical block instance, but their ID/current-state crosswalk remains open.

This closes the rule shape and the named family classifications, not broad adapter readiness. Preserve native behavior or a narrower proven domain for unresolved neighbor inputs. No product code, runtime reachability, traversal, or DDA parity claim is made.
