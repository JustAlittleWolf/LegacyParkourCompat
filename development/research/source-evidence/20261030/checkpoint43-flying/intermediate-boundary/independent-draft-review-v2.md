# Independent review v2: flying shift ladder descent finding

**Verdict: ACCEPT**

## Immutable inputs

- Draft `.native-source/20261030/1152-1165-finish43/canonical-findings/1.15.2--1.16/sneaking-flight-ladder-descent.md`, SHA-256 `AA8BA10592924E5FA36292F5E2C62593A7F181E9FD5ACAF82C9CE2F540BE2BD6`.
- Boundary report `.native-source/20261030/1152-1165-finish43/intermediate-boundary/report.md`, SHA-256 `D74AF8B7F76D81556331524916DDDCA0959C7A9B49C86179485A74589DBE690B`.

## Review

The corrected references match the exact paired Mojmap sources. In 1.15.2, the inherited predicate and helper are `LivingEntity.java:2686–2688` and `:1989–2003`; `onLadder` and its explicit fallback are `:1226–1248`. The helper is installed immediately before the land `SELF` move at `:1890–1891`. Shift descent input is applied in `LocalPlayer.java:735–748`, and the flying branch in `Player.travel` is cited at `:1448–1470`.

In 1.16.0, the LocalPlayer override is `LocalPlayer.java:347–349`, the full flying shift input application is `:770–782`, `onClimbable` plus its usable trapdoor fallback is `LivingEntity.java:1361–1393`, and `handleOnClimbable` is `:2087–2103`. The adjusted movement is set and moved at `LivingEntity.java:2062–2063`; the flying `Player.travel` branch is correctly cited at `:1380–1402`. The inherited predicate continues to be shift-only. The boundary report independently verifies the override through 1.16.5.

The claim preserves the important numeric and order facts: vertical Y is raised to a minimum of `-0.15` via `Math.max` before the conditional zero; that conditional requires negative clamped Y, feet not scaffolding, a true suppression predicate, and a Player instance; adjusted movement is installed before `MoverType.SELF`. The explicit common witness is a nonspectator player at an open trapdoor above a ladder with matching facings. It is accepted by both exact predicates without assuming `BlockTags.CLIMBABLE` contents. With shift held and flying, old suppression returns true and zeroes negative Y; new suppression returns false, allowing the clamped negative Y through the move. The stated dry ordinary land, non-fall-flying, non-passenger, unclipped limits correctly bound the predicted position change. No map-history or runtime-parity claim is made.

**Minor wording precision:** `Player.travel` saves `getDeltaMovement().y` before calling `super.travel`, then writes that saved pre-super Y velocity times `0.6` after the move. The draft calls it a saved “input value”; for strict source precision, “saved pre-super Y velocity” is preferable. This does not change the finding or its movement meaning.

Tag JSON/resource contents remain unavailable in the inspected source roots; the tag-independent fallback witness is sufficient for this conditional claim. The verdict is source-only and does not establish runtime parity.
