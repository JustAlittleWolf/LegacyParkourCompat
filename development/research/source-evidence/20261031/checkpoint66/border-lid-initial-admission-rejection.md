# Independent product review: border-lid65

## Verdict

**BLOCK** product commit `61f0e9f4267e79c529d4e29cbbc9a6092f7c2f52` (base `393e847102e834084de7c3d427dbf02907f59023`). The static border calculation and shulker raw-shape construction match the accepted source contracts, but the accepted 1.16 world-border witness cannot reach the new shape hook because the preexisting admission hook evaluates the current native shape first. The static-shape path also applies to client-side remote `Player` entities, beyond the assigned LocalPlayer-only boundary.

## Blockers

### P1 — Static historical shape is not used by border admission

`EntityBorderCollisionMixin.legacyparkourcompat$borderContext` resolves `BorderCollisionAdmissionHook` before the wrapped `Entity.collide` body. For selected 1.16 profiles, the applicable `BorderCollisionAdmission` at `V1_17_1` runs its `includeBorder` method. That method tests `border.getCollisionShape()` directly against the deflated current box. The newly added `StaticWorldBorderCollisionShapeHook` is only consulted later, in the wrapper around `getCollisionShape()` inside `collectCollidersIgnoringWorldBorder`.

For the accepted equal-input witness (center X `59,999,984`, size `59,999,968`, absolute max `29,999,984`), current 26.2 `StaticBorderExtent` clamps both X endpoints to `29,999,984`. Its sorted interior shape is empty, so the native border collision shape is `Shapes.INFINITY`. The admission hook therefore finds an overlap and returns `false`; the collector skips its `getCollisionShape()` call, so the historical finite border shape from the new hook is never returned. In 1.16.5 the accepted old shape has a sorted interior X interval `[29,999,984, 30,000,000]`, does not overlap the witness player's deflated box, and admits the border collider. The implementation consequently misses the accepted response difference.

The static historical shape used for this profile/input must feed the admission calculation as well as the later collider retrieval, with the existing moving-border historical-collision precedence and native fallback preserved.

Evidence: product `EntityBorderCollisionMixin.java` lines 27–78; `BorderCollisionAdmission.java` lines 27–32; 26.2 unobfuscated `Entity.java` `collide` → `collideBoundingBox(Entity,...)` → `collectCollidersIgnoringWorldBorder(Entity,...)`; 26.2 `WorldBorder.StaticBorderExtent.updateBox` and `getCollisionShape`; accepted `.native-source/20261031/1165-1171-finish45/worldborder-review/review.md`.

### P2 — Static override is broader than LocalPlayer

The new branch checks only `MovementRuntime.appliesTo(source)`. That method accepts any `Player` on a client-side level when the selected profile is enabled; it does not require `LocalPlayer`. Thus a remote client-side player invoking the same entity collector can receive the historical static-border shape, although the accepted witness and assigned boundary are LocalPlayer-only. Guard this operation to the local player while retaining vanilla behavior for other entities.

Evidence: product `EntityBorderCollisionMixin.java` lines 69–79 and `MovementRuntime.java` lines 117–122; the accepted world-border review's fixture is a `LocalPlayer`.

## Checks that passed

- `StaticWorldBorderCollisionShape` preserves the 1.16 asymmetric `Math.max`/`Math.min` endpoint clamps, floors minima, ceils maxima, sorts endpoint pairs, and computes `INFINITY ONLY_FIRST`. The separate native marker at `V1_15_2` and old implementation at `V1_16_2` resolve correctly for both 1.16 profile groups under the current closest-applicable resolver.
- The shulker residual progress equality and WEST/DOWN/NORTH facing gates match the accepted finding. `getBoundingBox(state)` supplies the exact local raw endpoints. `RawArrayVoxelShape.oneCell` uses the full discrete cell from `Shapes.block()` and the `ArrayVoxelShape` array constructor, preserving the negative endpoint without `Shapes.create(AABB)` quantization. In 26.2 source the constructor descriptor is `(DiscreteVoxelShape,[D,[D,[D)V`; the invoker parameters and return target match. Its mixin is listed in the client-only mixin configuration.
- Shulker dispatch occurs through the client player collision-context path. `MovementRuntime.appliesTo` rejects logical-server entities, and this change does not alter block entities, block states, or their producers.
- The static-shape wrapper retains the existing `historicalCollision` branch first and only attempts the new operation when lerp time and speed are zero; moving extents retain the original shape path. The `@WrapOperation` targets the Entity-argument `collectCollidersIgnoringWorldBorder` overload whose 26.2 source is called from `Entity.collide`; the separate `CollisionContext` overload is not targeted.

No product files were edited. No build, tests, game launch, or staging was performed. This review establishes source and call-path findings only; it makes no runtime parity claim.
