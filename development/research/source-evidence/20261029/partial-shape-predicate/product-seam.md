# Product seam plan: 1.21.2/.3 partial-shape sweep

## Scope and evidence

Product HEAD is `89e28202af20d44b5c52fe6ebff8693c37d579e4`, as requested. The accepted alias-closure report hash matches `554fca52d724e8133ec090f8d1b28f3daea443c926682df21b62e133d0816ab4`; its independent audit at `.native-source/20261029/partial-sweep-1212-alias-audit/audit.md` matches `601e1ec94848d2651ceecb8249f08fe3b138c476e2103c5a7fc5410ca72aa34e`.

The accepted closure establishes that the checked 1.21.2 and 1.21.3 operation sources and caller files are byte-identical. Their partial-shape sweep uses the final entity box, recenters it, moves that recentered box to `to`, forms the reverse displacement from `from` to the moved box's bottom center, then tests the final box against translated shape AABBs. This differs from 1.21.4's start box and forward displacement. The local-player callback reaches the operation through End Portal's partial inside shape; Tripwire's callback is server-only. Piston movement is another client-reachable callback entry.

## Current literal providers and selection

The relevant independently resolved hook today is `SweptShapeIntersectionHook` (`entity.swept_shape_intersection`):

| Selected profile | Resolved provider | Effect |
| --- | --- | --- |
| `V1_21` | `NativeSweptShapeIntersection@V1_21_2` | Empty result; native running-game sweep |
| `V1_21_2` | `NativeSweptShapeIntersection@V1_21_2` | Empty result; native running-game sweep |
| `V1_21_4` | `SweptShapeIntersection@V1_21_6` | Replaces the nested AABB sweep with full half-size inflation |
| `CURRENT` | none | Native fallback; current profile resolves no changes |

The input comes from literal providers `change/v1_21_2/NativeSweptShapeIntersection.java` and `change/v1_21_5/SweptShapeIntersection.java` (the latter is annotated `V1_21_6`). `ChangeResolver` independently selects the smallest provider version greater than or equal to the selected profile; package names do not affect it.

Related current callback providers are `NativeBlockCallbacks@V1_21_2` for `BlockCallbackTraversalHook`, `BlockCallbackBudgetHook`, and `BlockCallbackContactHook`; `BlockCallbackTraversal@V1_21_4` and `BlockCallbackContact@V1_21_4`; and the corresponding later callback providers at `V1_21_6`. Thus `V1_21`/`V1_21_2` resolve the native callback fence. `V1_21_4` resolves the 1.21.4 traversal/contact operations and the later `SweptShapeIntersection` provider. `CURRENT` resolves none.

## Product seam and recommended wiring

`EntitySweptShapeIntersectionMixin` wraps `AABB.collidedAlongVector` inside the current `Entity.collidedWithShapeMovingFrom(Vec3, Vec3, List<AABB>)` helper. Its hook sees only the current moving AABB, movement vector and AABB parts. That is the right seam for the 1.21.4+ nested AABB operation, but not a self-sufficient contract for the 1.21.2/.3 algorithm: the old operation depends on the entity's final box and both original endpoints, with a reversed/recentered construction.

The existing product callback callsite that has the full operation inputs is in `EntityBlockCallbackSegmentMixin`: it passes `from`, `to`, and the translated partial-shape AABBs to `entity.collidedWithShapeMovingFrom(...)` through `BlockCallbackContactHook`'s `sweptIntersection` predicate. The 1.21.4 and 1.21.6 contact providers invoke that predicate only for non-`Shapes.block()` shapes; the 1.21.2 native contact provider returns empty and leaves native contact sequencing active.

For implementation, add a distinct topic hook for the partial entity-inside-shape sweep with an explicit contract carrying the entity, `from`, `to`, and translated shape parts. Add one `@MovementChange(emulates = V1_21_2)` implementation reproducing the accepted 1.21.2/.3 box recentering, reverse-vector construction, translated shape AABB iteration and `AABB.collidedAlongVector` order. Wire it only at the partial block-shape predicate, so it cannot alter fluid AABB callbacks or the separate 1.21.4+ swept-operation hook. Keep the existing `SweptShapeIntersectionHook` providers and native empty-result fallback intact.

The key integration requirement is reachability: the current partial-shape predicate in `EntityBlockCallbackSegmentMixin` is nested under the resolved callback traversal provider, while `NativeBlockCallbacks@V1_21_2` deliberately returns empty. The integrator must expose the new hook at the actual partial-shape callback site for a `V1_21_2` profile without enabling the 1.21.4+ candidate enumeration, contact ordering, or callback lifecycle changes for that profile. A narrow wrapper at the predicate callsite or a separate client mixin at the partial-shape invocation is preferable to reusing the lower-level AABB wrapper. It must retain the existing `MovementRuntime` player/client/profile gates and invoke the original unchanged when the new hook is unresolved.

## Resolver expectations after wiring

The new hook should have only the `V1_21_2` provider. Under the current closest-applicable rule it will resolve for selected `V1_21` and `V1_21_2`, and will not resolve for `V1_21_4` or `CURRENT`. If the intended boundary is strictly 1.21.2/.3, the product's enum grouping makes `V1_21` share the resolver range; a separate pre-1.21.2 native fence provider for this new hook is required to keep 1.21/1.21.1 native. This is a material boundary gate to settle before implementation.

For selected `V1_21_4`, the existing `SweptShapeIntersection@V1_21_6` remains selected and the current callback contact path retains its 1.21.4 source semantics. For `CURRENT`, both new and existing historical hooks fall back to native behavior.

## Main conflict and fallback gates

- Do not make the new provider implement `SweptShapeIntersectionHook`: that hook intercepts the lower `AABB` operation and is also used by modern entity-inside/fluid sweeps. The accepted 1.21.2/.3 method has different inputs and is scoped here to partial block shapes.
- Do not call the 1.21.2 algorithm from the existing 1.21.4+ `BlockCallbackContact` solely by changing its predicate: the 1.21.2 contact route currently stays native and must not inherit later traversal/order/collector semantics as a side effect.
- Do not broaden the new operation to full-block sentinel or non-player/server callbacks. The provider and mixin must preserve native fallback for unresolved profiles, non-player entities, logical servers, removed/no-physics entities, and any path that is not the accepted partial-shape query.
- Preserve native shape objects and callback order; the accepted finding changes the intersection calculation, not block state or shape providers. End Portal establishes local-player reachability. Tripwire's server-only callback is not a client applicability reason.
- Keep fluid callbacks on their existing hook path. If implementation wiring cannot distinguish partial block shapes from fluid lists at the selected seam, choose a higher-level partial-block callsite with explicit shape origin rather than applying the legacy formula broadly.

## Handoff limit

This is a product-seam plan only. It does not implement the hook, establish broader version-boundary history, claim runtime parity, or prescribe callback candidate traversal changes. The integrator should confirm the `V1_21` alias gate and exact current partial-shape invocation descriptor against the build target before coding.
