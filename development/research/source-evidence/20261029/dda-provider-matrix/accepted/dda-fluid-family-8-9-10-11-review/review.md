# Independent review: legacy fluid provider family

**Verdict: ACCEPT** for the bounded registry, level-state, collision-provider, ray-bounds, aliases, and current-provider claims in the reviewed finding. This review does not establish DDA visitation, hit ordering, first-hit, movement-reachability, or end-to-end parity.

## Input identity and source verification

- Finding SHA-256: `6524894CD8B013D99B469D167FE3F116A182F3117D93546A2F63D2FA055F58D9`
- Source manifest SHA-256: `D6274F3A2601771878AFD1A886676F9D84093EFAC77EEE32FE3BE04C901FFD29`
- The ready markers' `sourceManifestSha256` values match the actual 1.8.9 and 26.2 source manifest hashes.
- Independently recomputed all 12 listed source-file hashes and compared each to both the finding manifest and its canonical source manifest entry: 12/12 match.

## Claims checked

- 1.8.9 `Block.java` registers IDs 8/9 as distinct flowing/source water classes and 10/11 as distinct flowing/source lava classes; `LiquidBlock.LEVEL` is 0–15 and metadata maps directly to/from it.
- `LiquidBlock.getCollisionShape` returns null. `Block.addCollisions` only appends non-null intersecting boxes, so these states add no legacy movement AABB through that provider.
- Liquid ray eligibility is exactly `allowLiquids && LEVEL == 0`. The base constructor initializes full-cube bounds; inherited `Block.rayTrace` calls the empty `Block.updateShape` before clipping. Neither liquid subclass adds a ray/update/collision override.
- 26.2 `DataFixers` renames `flowing_water`/`flowing_lava` to `water`/`lava`; `Blocks` registers one liquid block per fluid with `.noCollision()`. Modern liquid collision is context-dependent in the override, and its outline shape is empty. `CollisionContext.of(entity)` passes `alwaysCollideWithFluid=false`; the finding correctly leaves actual modern player reachability open.
- The report does not overclaim serialized state migration, traversal/visitation parity, or player-callsite flag behavior.

No product code, tests, builds, or runtime were inspected or changed.
