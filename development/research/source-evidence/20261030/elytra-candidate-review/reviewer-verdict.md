# Independent review: elytra glide vertical acceleration candidate

Verdict: **Accept the two arithmetic bodies as an isolated vertical-acceleration contract; reject this candidate as a complete, correctly fenced integration.** No tests or builds were run. Review was limited to the candidate commit, its finding, the supplied source-closure verdict, and the canonical decompiled sources.

## Immutable audit identity

- Candidate commit: `1e90a5288b460408649632bd471ecf36a6b72df3`
- Candidate finding SHA-256: `12C0CE776E3E60EB5A52212AB5191104331B3D63D2FD36C2C996C1A2448E98C3`
- Closure finding SHA-256 bound by supplied verdict: `ED7AC76568ADF100F48D92B27D42785AE703BFD0B7631E32D1850D4E50A1A44E`
- Candidate source SHA-256:
  - `change/v1_12/GlideVerticalAcceleration.java`: `17C542F3A0C9746E6ECECE746373CDA99D91F671C83645731EED326431C1930E`
  - `change/v1_13_1/GlideVerticalAcceleration.java`: `1B755B19BDCE13BCE87FF80668A5788BFDFB5A6CCBF10AFEB8182E1F6E1E1D98`
  - `mechanic/hook/player/GlideVerticalAccelerationHook.java`: `663ABF41805F398754093BDC11F5A6F0E25325A74D737C3D45CEE175B9FE893E`

## Accepted scope

The operation is independently meaningful: the source's narrowed float lift factor feeds a vertical acceleration whose Java double operation order differs across the 1.12.2 and 1.13.0 cutover. A provider for that term is warranted when a historical profile needs the old grouping; returning to the native operation when no historical provider applies is the right integration shape.

The `float liftFactor` parameter preserves the already-narrowed float value. Both method bodies preserve the relevant expression order and literal types:

- 1.12.2: `-0.08 + liftFactor * 0.06` — float promotes to double for multiplication by the double literal; the addition is double.
- 1.13+: `gravityFactor * (-1.0 + liftFactor * 0.75)` — the float promotes in the inner double expression and the grouping remains intact.

This acceptance is limited to the arithmetic bodies under their expected ordinary-gravity caller contract. It does not establish runtime parity or accept the candidate's version fences/call-site behavior.

## Defects and integration requirements

1. **Wrong 1.13 fence.** The regrouping begins in stable 1.13.0, but the grouped class is annotated `V1_13_1`. The candidate's `ParkourVersion` has a distinct `V1_13`; 1.13 profiles therefore resolve the old 1.12 expression. Move the grouped change to `V1_13` (and its matching `change/v1_13` package). The 1.13.1/1.13.2 sources confirm the same grouped expression.

2. **No pre-1.9 feature guard.** Elytra/fall-flying begins in 1.9, while the resolver selects the nearest change at or after a selected profile. Consequently, `V1_12` applies to selected 1.8 profiles if this hook is called. Since 1.8 has no historical glide operation and modern-only features must retain native behavior, the eventual caller must leave this operation native for selections earlier than 1.9. Adding an older formula class alone cannot solve this because the resolver's direction still chooses a later change for 1.8. The candidate has no consumer/call site, so this required guard is currently absent and unverified.

3. **Slow Falling leaks through the gravity parameter.** In 1.13+, vanilla changes `d` to `0.01` for nonpositive vertical velocity with Slow Falling, then uses that `d` in the grouped acceleration. Slow Falling is outside the historical target. Passing the caller's unrestricted `gravityFactor` to this hook would historicalize a modern-only branch; the 1.12 implementation also ignores the parameter entirely. The eventual consumer must retain the native acceleration for the Slow Falling branch (including for 1.12-selected profiles), or the contract must expose a way to request native fallback. The interface alone cannot signal fallback, and the candidate supplies no call site to prove the gate.

4. **Candidate has no runtime integration or native fallback path.** The commit adds the hook and two provider classes only. There is no injection/dispatch that obtains the narrowed float, applies the resolver-selected provider, and otherwise executes vanilla unchanged. This is acceptable only as an isolated provider fragment; it is not a functional implementation of the finding. Preserve the local-player/client-only boundary at the eventual call site.

## Source basis

Primary evidence is `LivingEntity.moveRelative` in the read-only canonical Ornithe Feather sources: 1.12.2, 1.13, 1.13.1, and 1.13.2. The supplied source-closure verdict records the source identities and SHA-256s, verifies that 1.13.0 is the stable cutover, and establishes that the two default-gravity expressions can differ by one ULP for a finite reachable input. Direct source inspection confirms the old float narrowing and old expression in 1.12.2, and the grouped expression with float lift intermediate and gravity factor in 1.13 through 1.13.2.
