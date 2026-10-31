# Final product review — entity-envelope admission guard

## Verdict

**Accept the source-level product integration at commit `91d50bb029e3577b2df98b9acc4fabbb84159fd6`**, subject to the designated build owner completing the pending compile. This verdict supersedes the entity-collision blocker in `entity-collision-addendum.md` for this exact commit; it does not claim runtime parity.

The new preflight closes the omitted `noCollision(AABB)` entity dependency for admitted queries. It requires a real `Level`, requires that level to be exactly `entity.level()`, and requires `getEntities(entity, envelope, other -> true)` to be empty before the historical search proceeds. The source argument excludes the waking local player from the returned list; the always-true predicate admits every other entity. A collision getter that is not that same world, or any other entity in the envelope, yields unsupported and the caller invokes the complete native helper after `withEntityContext` has restored its previous state.

## Geometric coverage proof

The old search visits two 3×3 grids. For cardinal bed facing and `m = 0..1`, each candidate X/Z coordinate lies within `bedX/Z ±2`. Only candidates at `bedY` are tested. The accepted preflight envelope spans X/Z `[bed-3, bed+4)` and Y `[bed-3, bed+3)`, so it contains the grid cells and has at least 0.7 blocks of horizontal clearance beyond any candidate's player AABB.

For the admitted block domain, an initial candidate shape can pass the `maxY <= 0.4375` gate only if it is air: stone has height 1, and all admitted bed orientations have the historical shape's full height `9/16 > 0.4375`. The support scan descends at most to candidate Y minus 3 before its final support-shape check. Its later `candidateY - positionY <= 2` acceptance test means the lowest accepted standing Y is candidate Y minus 2 plus the `2.0E-7` epsilon (stone support at Y−3); bed support there is too low and is rejected. The highest possible accepted standing Y is candidate Y plus epsilon (stone support at Y−1). With the fixed player type dimensions `0.6 × 1.8`, every accepted player AABB therefore lies within horizontal bed ±2.3, vertical `[bedY−2+epsilon, bedY+1.8+epsilon]`. The dangerous-block AABB expands only downward by `0.2`, so its vertical minimum is above bedY−2.2. Both player-box and danger-box extents lie strictly inside the entity preflight envelope.

Thus any other entity whose current collision shape could overlap an admitted player AABB would also be found by the query over the containing envelope. On the old side, the inspected 1.16.1 `Entity.getCollideBox` defaults to null; the only overrides are Boat and Shulker and both return their bounding boxes. The guard excludes all other entities regardless of current type or collider policy, so 1.16.1's entity collision stream and 26.2's entity collision stream are both empty for an admitted candidate. This closes the earlier concern about different entity selectors, collision-box accessors, and newer collidable types.

The guard is conservative: an entity elsewhere in the large envelope can cause native fallback even when it would not overlap the eventual candidate box. This is safe and does not mix old and current candidate ordering.

## Context, bounds, and scope

`supportsBedWakeSearch` first verifies `currentEntity() == entity` and movement applicability. The caller wraps only the exact current `LocalPlayer` route in `withEntityContext`; the result exits that try/finally scope before `callNative.get()` handles unsupported input. The new `Level` identity check ties the entity query to the same world that supplied the bed and block states. Existing height and world-border envelope checks remain before entity and block reads. The bed shape equality and no-applicable-shape-provider checks remain unchanged.

Reviewed the exact diff from `ca86c156ca168cd046d00dba560e76d6828a5d4d` to `91d50bb029e3577b2df98b9acc4fabbb84159fd6`, the final method and redirect/fallback context, and exact 1.16.1/26.2 `CollisionGetter` and `EntityGetter` behavior identified in the preceding addendum. No product files were changed. The pending build was not run by this reviewer; no tests or runtime launch were run.
