# Independent review: flying shift ladder descent finding

**Verdict: REJECT**

## Immutable inputs

- Draft `.native-source/20261030/1152-1165-finish43/canonical-findings/1.15.2--1.16/sneaking-flight-ladder-descent.md`, SHA-256 `7D11DA624F943FD0D38DDA38B1DA5CDE53784C3B473AC0866ADC5E8E7D3FCDDB`.
- Boundary report `.native-source/20261030/1152-1165-finish43/intermediate-boundary/report.md`, SHA-256 `D74AF8B7F76D81556331524916DDDCA0959C7A9B49C86179485A74589DBE690B`.

## Findings

The behavioral claim is source-supported, but the draft needs citation corrections before acceptance:

1. The 1.16.0 `LocalPlayer.isSuppressingSlidingDownLadder()` override is at `LocalPlayer.java:347–349`, not `:348–350`. It returns `!this.abilities.flying && super.isSuppressingSlidingDownLadder()`.
2. The 1.16.0 flying shift input citation `LocalPlayer.java:770–779` stops before the `setDeltaMovement(... j * getFlyingSpeed() * 3.0F ...)` application at line 781. Cite `:770–782` to include the applied vertical input.
3. The 1.16.0 `LivingEntity.onClimbable()` and its explicit trapdoor helper span through line 1393; cite `:1361–1393` rather than ending at 1392.

The remaining checked evidence is correct: 1.15.2 has no LocalPlayer override and inherits the shift-key predicate; both `handleOnClimbable` methods clamp Y with `Math.max(y, -0.15F)` before the conditional zeroing and install the adjusted movement before `MoverType.SELF`; suppression requires negative clamped Y, feet not scaffolding, a true suppression predicate, and `this instanceof Player`. The nonspectator open-trapdoor-over-ladder witness with matching facings is accepted directly by both climbability predicates, so no `CLIMBABLE` tag membership assumption is needed. The flying branch in `Player.travel` calls `super.travel`, then restores velocity Y from the saved value times `0.6`; that later velocity assignment does not undo position already moved. These source facts support a conditional position difference for dry ordinary land travel, not a claim about map prevalence.

No tag-resource contents were available in the requested source roots; this does not weaken the explicit trapdoor witness. The result remains source-level and does not establish runtime parity.

## Required disposition

Correct the three source ranges above, then bind a renewed independent review to the revised immutable draft hash. I did not edit the draft.
