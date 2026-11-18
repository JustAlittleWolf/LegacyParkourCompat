# Independent exact draft review

Verdict: ACCEPTED for the four scoped fluid operations and the separate Slow Falling fall-distance caveat, bound to the exact bytes below.

- Draft: `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-ac27-7712-9259-3041950bc340/fluid-java-1194-1204/1.19.4--1.20/source/comparison.md`
- Draft SHA-256: `bfa50de90ec6357ec7c35360c503b48aa27a699885ac73b7dc37ffb3be0460b9`
- Frozen roster: `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-ac27-7712-9259-3041950bc340/fluid-java-1194-1204/1.19.4--1.20/blind-audit/frozen.md`
- Frozen roster SHA-256: `a8a07a0abb70905af10be972a1ca67fb946ad696185e977d127b52e0a43cf8fa`

## Review basis

I independently retraced the four scoped operations in exact Mojmap Entity/LivingEntity sources and checked the cited source bodies, the `LocalPlayer` dispatch references, and the adjacent `getFluidFallingAdjustedMovement` method. The roster's four equality conclusions hold under the stated same-effective-tag, same-world-input precondition. The draft accurately keeps the removed Slow Falling `resetFallDistance()` write separate from the unchanged water/lava branch gravity and velocity math. Its source digests for Entity, LivingEntity, LocalPlayer, FluidState, and FluidTags match the 1.19.4 source manifest and 1.20 embedded provenance entries; the cited official client/mapping pins and 1.19.4 ready metadata are consistent.

The draft's scope and caveats are adequate: the review does not establish fluid/tag resource equivalence outside the stipulated input assumption, other entity/vehicle behavior, whole-pair coverage, implementation cutover, or runtime parity. The Slow Falling difference is accepted as a fall-distance bookkeeping caveat only; no verdict is made here about fall-damage evaluation, which is explicitly excluded.

No blocking claim or evidence defect found. This verdict binds only to the exact draft SHA above and the frozen roster SHA above. It does not alter either source artifact or the historical freeze.