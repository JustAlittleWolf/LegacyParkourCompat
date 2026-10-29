# Product wiring review: 1.21.2/.3 partial-shape sweep

## Binding and verdict

**Verdict: ACCEPT for the bounded partial-shape predicate integration at product commit `cc515023f8baaf3a24be965d82b90cfcad69002a`** (`fix/partial-sweep-1212-product-20261029`). The named product worktree is at that exact `HEAD` and had no reported working-tree changes during review.

This review accepts only the End Portal partial entity-inside-shape predicate delta for 1.21.2/.3. It does not close 1.21.2/.3 traversal, query-box, nudge, movement snapshot, piston-origin, or full callback traversal differences, and does not establish runtime parity.

## Evidence identities

- Accepted alias-closure report: SHA-256 `554fca52d724e8133ec090f8d1b28f3daea443c926682df21b62e133d0816ab4`.
- Independent alias-closure audit: SHA-256 `601e1ec94848d2651ceecb8249f08fe3b138c476e2103c5a7fc5410ca72aa34e`.
- Accepted neutral-helper review: SHA-256 `622e86851048dabbb6d33af726aec4ab07047c0ade3b16e5c50099eaeeae8501`.
- Product seam plan: SHA-256 `4b1f48ee0bd88d622d66ac88a57ba5033ed016d9e98f2e137c33c34cfd3b1607`.
- Exact 26.2 unobfuscated `Entity.java`: SHA-256 `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`.
- Exact 26.2 unobfuscated `EndPortalBlock.java`: SHA-256 `75addd79af8670d89880c248d8bcbfaad15208b5d43c4dd2aa27487ef84bcfeb`.
- Exact 26.2 unobfuscated `AABB.java`: SHA-256 `1ffdd0f6d26a4dd1e26407ea9a386eab7903e43462e60c9ae5fdc50a0468f10b`.
- 26.2 ready marker: SHA-256 `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f`; source manifest SHA-256 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`.

## Review

- `EntityPartialShapeSweepMixin` wraps only `Entity.collidedWithShapeMovingFrom(Vec3, Vec3, List<AABB>)` inside `lambda$checkInsideBlocks$0`, with the full descriptor present in the 26.2 cache's `javap -p -c -l` output. The bytecode contains one such invocation in that lambda. Its `LocalVariableTable` names slot 12 `state` with descriptor `BlockState`, matching `@Local(name = "state") BlockState state`.
- In the same bytecode, the separate `collidedWithFluid(...)` call follows the shape check; fluid's own later `collidedWithShapeMovingFrom` call is outside this wrapper target. The mixin also returns the native result for every state except `Blocks.END_PORTAL`. In 26.2, `EndPortalBlock` supplies its state shape (`Block.column(16.0, 6.0, 12.0)`) from `getEntityInsideCollisionShape`; the wrapper therefore selects the intended block callback without changing other block or fluid state callbacks. The base full-block sentinel bypasses this invocation in vanilla.
- `MovementRuntime.find(PartialShapeSweepHook.class, entity)` retains the existing client-side player and enabled-profile gates. The `@V1_21` empty provider fences 1.21/1.21.1; the `@V1_21_2` provider supplies the historical reverse displacement for 1.21.2/.3. `ChangeResolver` selects the closest provider at or after the selected version, so 1.21.4 and later profiles have no provider for this new hook and fall through to the original call. `CURRENT` resolves no changes. Existing `SweptShapeIntersectionHook` registrations and fallback remain separate.
- The historical change recenters the final entity box, moves it to `to`, and returns `from - recenteredBox.bottomCenter()`, matching the accepted 1.21.2/.3 operation. The subsequent helper uses the final box and preserves the historical predicate order: compute center and `to`, inflate each translated shape part by the full half-extents, check `contains(to)` then `contains(from)`, then `clip`. Exact 26.2 `AABB.collidedAlongVector` subtracts `1.0E-7` from each half-extent; the explicit historical helper avoids inheriting that current inset. The helper extraction leaves the existing .4+ full-half-extents `SweptShapeIntersection` math identical to its pre-commit body.

No issue requiring revision was found within this bounded integration. No build, test, game, TAS, Gym, or server command was run.
