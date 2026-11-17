# Independent audit verdict — bubble contact corrected revision 1

## Immutable inputs

All three claim artifact hashes match the identities supplied for review:

- `comparison.corrected-r1.md` — `6cb17853cf44491e8754efc83b50b60d8499c74ca53f3e2aa05e3554a4db5dfc`
- `development/research/findings/1.21.4--1.21.5/bubble-column-above-contact-classification.corrected-r1.md` — `253d31727e5217955d79404882b598f6bcbdddafe0f4036b092feeefcb9d7a7d`
- `development/research/findings/1.21.9--1.21.10/bubble-column-short-sweep-contact-gate.corrected-r1.md` — `ddbf927a90089e65813d31a0982d8fa1b409235de1a5e4e1b3506d4f9256be5c`

The source roster was frozen before these files were requested/read: [source-roster.md](source-roster.md), SHA-256 `866F862E9491C8F26A991417961415389BF448F3550A1542B82560C92FF9D8CF`. Its 90 source entries match their per-release source manifests. Witness-only source references were subsequently checked and recorded in [source-roster-supplement.md](source-roster-supplement.md), SHA-256 `231258A64B0BF3E8304E7DE911ACF2C5E6F694B61D570DC50B48334B3628C94F`.

## Individual verdicts

### Comparison and corrected short-exit account — ACCEPT, with a witness-scope note

The release boundaries and operation descriptions are supported. Through 1.21.8, `BlockGetter` falls back to final-box candidates below `Mth.square(0.99999F)`; 1.21.9 lowers that trivial-motion cutoff to `Mth.square(1.0E-5F)` and admits the start-side swept candidates. In 1.21.10, `Entity.checkInsideBlocks` still invokes the block callback for an admitted full-sentinel candidate, while passing `isPrecise = movedFar || deflatedTargetBox.intersects(blockPos)`. The `double` threshold squares to `0.9999800001005053`. `BubbleColumnBlock.entityInside` then gates both direct responses on that flag. The cited 1.21.11 and 26.2 bodies retain that gate.

The tick witness arithmetic and order are correct under its ordinary-state assumptions. `LivingEntity.travelInFluid` moves first, applies the water vertical multiplier and fluid gravity adjustment, then `LivingEntity.aiStep` calls `applyEffectsFromBlocks`; thus the initial stationary pass changes `-0.005` to `0.095`, and the next travel carries `0.095` to feet y `1.075` and post-travel velocity about `0.0710000011`. The deflated target box misses y=0, but the translated-back start box begins at about `0.97999`, so the 1.21.9 iterator can visit the bubble cell. The displacement square is `0.009025`, below the precise-distance threshold. 1.21.9 applies the open-above `+0.1` response after travel; 1.21.10 invokes `entityInside` with `isPrecise=false` and skips it.

Scope note: `Player.onAboveBubbleColumn` and `onInsideBubbleColumn` call their superclass only while `abilities.flying` is false. The witness should state `abilities.flying=false` explicitly (and treat its “ordinary water travel” as non-sprinting, without Dolphin's Grace or nondefault water-movement efficiency) so the numerical trace binds unambiguously to a reachable LocalPlayer state. This does not undermine the stated non-flying witness path or the source-level gate finding.

### 1.21.4→1.21.5 above-contact classification — ACCEPT

The 1.21.4 predicate requires `stateAbove.isAir()`. In 1.21.5 it requires an empty collision shape and empty fluid state, using the bubble cell's position for the shape query. The cited dry `SHORT_GRASS` is non-air, has no collision, and has no fluid, so it selects the inside response in 1.21.4 and the above response in 1.21.5 when the callback is reached. The callback-entry arithmetic is correct: at y `0.65`, those results are `0.70` (plus fall-distance reset) and `0.75`. The direct response formulas are not new in this boundary.

### 1.21.9→1.21.10 short-sweep contact gate — ACCEPT, with the same witness-scope note

The short-sweep candidate description, short-circuit, strict final-box intersection, `isPrecise` computation, and BubbleColumn callback gate match the reviewed sources. Bubble columns inherit `BlockBehaviour`'s entity-inside collision shape `Shapes.block()` while exposing an empty ordinary collision shape. Therefore the identity side of `shape == Shapes.block() || collidedWithShapeMovingFrom(...)` admits the bubble contact without evaluating the AABB shape-sweep alternative.

The short vertical trace is consistent with the post-travel block-effect phase in both releases. State `Player.abilities.flying == false` to ensure the player override delegates to `Entity`'s vertical response; the source-scoped facts and branch-suppression verdict otherwise stand.

## Audit limits

Read-only exact-source/static audit only. No product files, source trees, Git, tests, builds, game/runtime, decompilation, or network were used or modified.