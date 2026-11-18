# B12 edge support: 1.21.8 to 1.21.9

Status: COMPLETED (bounded source comparison; no eligible behavior difference found).

## Evidence identity

- Pair: exact Minecraft 1.21.8 → 1.21.9; Mojmap on both sides.
- 1.21.8 assignment identities: release-root ready/source manifest SHA-256 `8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008`; mapping identity SHA-256 `dce035fedcc047ce0306c69892176b5eb9036bcc2845c87198163f12bb1d67ca`.
- 1.21.9 assignment identities: mapping-root provenance SHA-256 `37d3049478585a43ffcace14fc8f2ee814b35ceedd8c9bad8b60adaed9b72c4f` (matches the local `.decompile-provenance.json`); mapping identity SHA-256 `f223e9775abb66b6712c505cb1a73b48d615efebaf43a9f81dd58df9a9b1c7b3`.
- Local source body SHA-256s:
  - 1.21.8 `Entity.java`: `c403e6176d27b5bfd6aaa0dea3735ffa4fcf80dbae58766661dd84453b7e9704`
  - 1.21.9 `Entity.java`: `bd6074b108212bde667877700af1a52649472630c906c57c2a36c96a6fcee74c`
  - 1.21.8 `Player.java`: `8dc5514fe44311f39268692f5188840b9f65cb71143228fa8b84242585ea7987`
  - 1.21.9 `Player.java`: `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82`
  - Both `BlockCollisions.java`: `e60f4d3cf7537a5e3b8b3ad099a2a7ec167f972460fa01045fd2b2141b6ddaa0`
- Caveat: the 1.21.8 Mojmap directory has no local `.decompile-provenance.json` or other root-level manifest file. Its supplied release-root readiness/source-manifest identity could not be independently rechecked within the authorized source roots. The source files and complete method bodies were readable and hashed directly. 1.21.9 manifest source hashes for the relevant files match the local files.

## Compared slices and result

- `net/minecraft/world/entity/Entity.java`, `Entity.move` (`1.21.8:663–700`; `1.21.9:670–705`): in both, after piston limiting and stuck-multiplier handling, dispatches `maybeBackOffFromEdge(request, moverType)`, passes the returned vector into `collide`, and uses the collision result to update position. The edge-adjusted vector is therefore the requested displacement entering collision resolution.
- `net/minecraft/world/entity/player/Player.java`, `isStayingOnGroundSurface` (`1.21.8:373–375`; `1.21.9:309–311`): both return `isShiftKeyDown()`. This gate is crouch-key state, rather than a `Pose.CROUCHING` check.
- `Player.maybeBackOffFromEdge` (`1.21.8:1038–1087`; `1.21.9:891–940`): bodies match. Both require not flying, `y <= 0`, mover type `SELF` or `PLAYER`, shift-key ground-surface intent, and `isAboveGround(maxUpStep)`. Both then trim X, trim Z, and finally trim diagonal X/Z using separate loops and `0.05` decrements; each loop uses the same zero/`abs <= 0.05` exit behavior and returns the resulting X/Z with the original Y.
- `Player.isAboveGround` and `Player.canFallAtLeast` (`1.21.8:1089–1100`; `1.21.9:942–953`): bodies match. `onGround()` immediately qualifies. Otherwise, qualification requires `fallDistance < maxUpStep` and no qualifying collision in a probe extending `maxUpStep - fallDistance` below the feet. The probe preserves the same `1.0E-7` horizontal inset and lower-Y extension, and calls `level().noCollision(this, box)`.
- `net/minecraft/world/level/CollisionGetter.java`, `noCollision(Entity, AABB)` and its default overload (`48–69` both releases): both delegate to the dry block-collision iterator (`getBlockCollisions`, default boolean false), reject any nonempty block shape or entity collision, then check world-border collision. The block-collision implementation `BlockCollisions.java` has identical body hash on both sides. This includes full-cube and partial-height shapes intersecting the tested probe; boundary contact remains governed by the same probe bounds and collision iteration. The 1.21.9 change to the fluid-collision context is in `getBlockAndLiquidCollisions`, which this dry query does not call.

No eligible difference was found for the assigned ordinary local edge-support operation. No permanent finding draft was written because the paired methods and their dry collision-provider path show no change.

## Boundaries and open evidence

- Covered: shift-key crouch-intent gate; allowed mover types and nonpositive-Y/flying gates; grounded/support-range branch; `canFallAtLeast` bounds and dry provider; X/Z/diagonal decrement order; dispatch and net requested displacement; full and partial collision shapes with boundary-sensitive bounds.
- Not reviewed by assignment: adjacent-edge support; generic step candidate scoring; AutoJump; input/device plumbing; movement-segment lifecycle; fluid collisions/effects; wider 1.21.8→1.21.9 endpoint gate and complete-pair inventory.
- Remaining issue: ask the source preparation owner to confirm the supplied 1.21.8 readiness/source-manifest identity against the published manifest. No source-body damage or scoped behavioral difference was observed.
