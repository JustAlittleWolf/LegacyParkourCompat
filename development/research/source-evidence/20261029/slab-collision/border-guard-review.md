# Review: 26.2 border fallback guard for the slab collector

## Verdict

**ACCEPT, as a conservative fallback guard only.** Commit `4493b194fae59c9921f123155026a24c3ce7938b` replaces the nonexistent `Entity.isOutsideWorldBorder()` call with the existing 26.2 `WorldBorder.isWithinBounds(AABB)` API. The new check rejects an entity whose current box is not contained by the border. The adapter still separately rejects a swept query near the modern border and any source-resolved historical border collider. No old entity-border latch or state-transition equivalence is claimed.

## Evidence

Canonical read-only 26.2 source files:

- `net/minecraft/world/level/border/WorldBorder.java`, SHA-256 `D2D9AD6E9407779B486531602D26207DF9496A4486CA9642B76976A1A910C981`.
- `net/minecraft/world/entity/Entity.java`, SHA-256 `7AFB9C1294893FFE73E3B1ACFFCAD41C648F15DE8378BFF3DFFAFF869BB811D5`.

Product `src/client/java/me/wolfii/legacyparkourcompat/mechanic/MovementRuntime.java` at the reviewed root commit has SHA-256 `606529E8FDC96B3EE34B70224AA82B954065B6DF6A8D405DF835AAE8B7703B73`.

`WorldBorder.isWithinBounds(AABB)` exists at lines 60–61. It checks the minimum X/Z endpoints and the maximum endpoints shifted inward by `1.0E-5F`; it is a containment predicate, so negating it is a suitable conservative rejection test. `isInsideCloseToBorder(Entity,AABB)` at lines 114–116 uses entity-to-border distance and swept-box dimensions; retaining that existing gate avoids adding a modern border shape near the sweep. In 26.2 `Entity.collideBoundingBox` passes the expanded box through `collectCollidersIgnoringWorldBorder` before `collideWithShapes` (`Entity.java:1195–1206`); the separate border shape is added in collider collection when `isInsideCloseToBorder` succeeds (`Entity.java:1222–1228`).

The product provider additionally requires the complete historical integer scan envelope to be strictly inside the border: `border.minX < minX && maxX < border.maxX` and likewise for Z. Since the collector scans integer block positions in `[min,max)`, this excludes any candidate cell at or beyond a border plane. It is conservative at the boundary and does not use `isWithinBounds(AABB)` as proof that the historical collector would omit border shapes.

## Scope

This guard only selects native behavior when border geometry or prior outside-border state could matter. The API replacement is source-supported and compiles against the cited current API (root owns build verification). No claim is made about historical outside-border flags, border collision response, all movement routes, or runtime parity. No build, tests, or runtime were run by this review.
