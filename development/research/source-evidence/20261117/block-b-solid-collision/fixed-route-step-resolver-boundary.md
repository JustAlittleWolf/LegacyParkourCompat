# Fixed-route step resolver range

The [independent review](fixed-route-source-review.md) verifies the same historical two-route solver in exact 1.19.4, all stable 1.20 releases through 1.20.6, and the compared scalar collision path for ordinary loaded, border-clear, entity-free movement using corresponding vanilla block shapes. The existing `V1_20_5` Change already implements the step gate, direct full-height route, rise-then-horizontal route, strict horizontal-distance comparisons, and final `-stepY + requestedY` settling query. Its adapter reuses cached entity candidates and the running game's swept block and border providers. Closed evidence covers the solver and the stated domain, with provider behavior outside that domain left open.

The exact 1.21 Mojmap `Entity.java` SHA-256 `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850` confirms the candidate-height path in `Entity.collide` and `collectCandidateStepUpHeights` (lines 890–906). It remains the native boundary. Relocating the earlier native marker to 1.19.3 lets the resolver select the existing fixed-route Change from 1.19.4 through 1.20.6 and preserves the earlier fallback.

One wording correction to the archived review: the scalar resolver checks strict `abs(X) < abs(Z)` and clips Z first when true. The larger-magnitude horizontal axis is clipped first, with X first on ties. 1.19.4 Mojmap `Entity.collideWithShapes` (lines 858–861), 1.20.6 (lines 931–934), and 26.2 unobfuscated `Direction.axisStepOrder` (lines 378–380, SHA-256 `30fb61592f1a154bb8fc239ab8df1cd2b209f63317e0c7dde4d5b9b3e260d754`) agree.

Backlog path relocation:

- Old `src/client/java/me/wolfii/legacyparkourcompat/change/v1_20_2/NativeStepUpCollision.java`
- New `src/client/java/me/wolfii/legacyparkourcompat/change/v1_19_3/NativeStepUpCollision.java`

Runtime parity remains unverified.
