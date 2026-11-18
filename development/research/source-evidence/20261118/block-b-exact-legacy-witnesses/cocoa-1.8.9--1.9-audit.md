# Cocoa 1.8.9→1.9 cutover independent audit

## Immutable bindings

- Handoff: `b-source-cocoa-19-cutover/COMPLETED.md`; supplied SHA-256 `eb862212207a10db53a07effe470d2d6340c9b02a1d13dad3457d4597e5113cd`; observed SHA-256 matched.
- Finding draft: `development/research/findings/1.8.9--1.9/cocoa-age-two-collision-shape.md`; supplied SHA-256 `24db4482fd500bfe0b484da463ca2791a14dca6c45b6dbc072569c372a3a66fd`; observed SHA-256 matched.

## Per-claim verdicts

1. **1.8.9 EAST age-two geometry — ACCEPT.** `CocoaBlock.updateShape` sets `j=4+2*age=8`, `k=5+2*age=9`, and `f=j/2.0F=4.0F`; the EAST branch gives x `[0.4375,0.9375]`, y `[(12-9)/16,0.75]=[0.1875,0.75]`, z `[(8-8)/16,(8+8)/16]=[0.25,0.75]`.
2. **1.9 EAST age-two geometry — ACCEPT.** `EAST_SHAPE[1]` and `[2]` are both `Box(0.5625,0.3125,0.3125,0.9375,0.75,0.6875)`; `getShape` indexes directly by AGE and the EAST facing branch selects that array.
3. **Legal support and state — ACCEPT.** `isSupported` offsets position by `FACING`, then checks jungle-log state. Cocoa `(0,0,0)`, EAST, with jungle log `(1,0,0)` satisfies the required condition in the reviewed source. No invented or unsupported state is needed for the shape lookup.
4. **1.9.1/.2/.3/.4 continuity — ACCEPT.** The EAST age-one/age-two entries and facing-indexed selection are present identically in all four exact sources. The supplied provenance and endpoint manifest hashes were observed in the handoff and bind the specified source snapshots; this audit did not regenerate sources.
5. **Grounded ordinary +X clip — ACCEPT, bounded to the stated witness.** Both Entity.move implementations enter collision handling when `noClip` is false, collect collision boxes over the swept player box, clip requested axes, and evaluate the grounded step attempt when horizontal motion was clipped. In 1.8.9 the swept maxX is `0.5`; the cocoa minX `0.4375` clips dx to `0.0375`. A 0.6 rise leaves the player vertically overlapping cocoa through y=0.75; after upward clipping the player bottom can rise only to 0.75, while its x span still overlaps cocoa, so the step candidate cannot achieve more horizontal travel. In 1.9+ cocoa minX is `0.5625`, beyond swept maxX `0.5`, so it contributes no clip and the full `0.1` is available. Existing grounded floor contact is consistent with the initial box bottom at y=0.
6. **Cutover scope — ACCEPT with interval caveat.** The change is evidenced between exact 1.8.9 and 1.9. The reviewed 1.9 patch family through 1.9.4 preserves the smaller box. This does not assign an exact introduction point inside unreviewed 1.8.x patches or establish full-pair movement coverage/runtime parity.

## Evidence retraced

Read only the prescribed project/buildSrc README and discovery/source workflow plus the pinned canonical source trees under `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\{1.8.9,1.9,1.9.1,1.9.2,1.9.3,1.9.4}\ornithe-feather`. Retraced CocoaBlock support and collision-shape access, Entity.move collision and step gates, living-entity step height/travel dispatch, and World collision-shape collection as cited in the bound draft. No code, tests, builds, runtime, decompilation, Git, wiki, product, or shared research state was accessed or changed.

## Remaining gates

Independent review accepts the bounded Cocoa claims above. Pair-wide inventory/coverage freeze, reconciliation with other lanes, integration, and runtime parity remain outside this audit.
