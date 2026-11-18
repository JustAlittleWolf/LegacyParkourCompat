# COMPLETED — AutoJump geometry audit, 1.21.5 → 1.21.6

## Bounded verdict

**No source difference found** in ordinary dry local-player AutoJump geometry, candidate selection, clearance checks, or timer behavior. The complete `LocalPlayer.updateAutoJump(float,float)` method is identical in the compared source slices. This verdict applies only to this assigned AutoJump operation and does not close pair coverage or runtime parity.

## Evidence identity and integrity

- Older side: Minecraft `1.21.5`, Mojmap. Read-only source root: `decompiled_minecraft/1.21.5/mojmap`. `mojmap.ready.json` identifies version `1.21.5`, mapping `mojmap`, and source manifest SHA-256 `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9`; recomputed manifest digest matches. `net/minecraft/client/player/LocalPlayer.java` SHA-256 matches its manifest entry: `f1fcfed4a938732361e7ad951f93e9b73b02320ee56f0b219e2b3c7acdbfa2ef`.
- Newer side: Minecraft `1.21.6`, Mojmap. Read-only source root: `decompiled_minecraft/1.21.6/mojmap`. Embedded `.decompile-provenance.json` SHA-256 `56b036c61094d1d936882bdf5b2b59044512189cb33adbb075880c727da9b19e` matches the assignment. Its pinned version package URL identifies `1.21.6`; mapping namespace is established by the `mojmap` source root. `net/minecraft/client/player/LocalPlayer.java` SHA-256 matches its source entry: `53f2a71a886b9c71853afe36f2df857f80a9bf1cd6ec4ca8604ccd7e238d89ee`.

## Compared slices and findings

Both versions: `net/minecraft/client/player/LocalPlayer.java`:

- `move(MoverType, Vec3)`, lines 916–923: saves X/Z before `super.move`, casts actual horizontal displacement to floats, passes those deltas to AutoJump, then accumulates walk distance. Identical.
- `updateAutoJump(float,float)`, lines 935–1031: identical method body.
- `canAutoJump()`, lines 1051–1059, and `isMoving()`, lines 1061–1063: identical gates and input-vector movement predicate.
- Local-player tick timer decrement and jump request, lines 705–709: identical.

Geometry/control flow in both versions:

1. `canAutoJump()` requires AutoJump enabled, `autoJumpTime <= 0`, on-ground, not staying on a ground surface, not a passenger, nonzero input move vector, and block jump factor at least `1.0`.
2. The method starts from actual post-move horizontal displacement. If its float squared length is `<= 0.001F`, it reconstructs horizontal direction from `getSpeed() * input.getMoveVector()` rotated by yaw using the shown sine/cosine expressions. It returns if the reconstructed squared length is also `<= 0.001F`. It normalizes with `Mth.invSqrt`, then rejects only when the dot product with `getForward()` is `< -0.15F`.
3. Before scanning, it requires empty collision shapes at the block containing `(player X, bounding-box maxY, player Z)` and the block above. It sets base jump height to `1.2F`, adding `(jump-boost amplifier + 1) * 0.75F` when present. The scan distance is `max(speed * 7.0F, 1.0F / invSqrt(lengthSquared))`. The broadphase box runs from the pre-move position to the extended post-move endpoint plus player height, inflated horizontally by player width.
4. It forms two side segments by offsetting the vertical-shifted (`0.51F`) endpoints by `±(normalized direction × up) * width/2`. The collision query is `level.getCollisions(player, broadphaseBox)`, with shapes flattened to AABBs in iteration order. An AABB is a candidate when either side segment intersects it. The first such AABB sets the candidate height to its `maxY`; its center is floored by `BlockPos.containing` to choose the secondary-check block. The loop breaks after this first candidate, so later AABBs are not considered if it passes clearance.
5. Secondary checks iterate integer float offsets while `offset < jumpHeight`. At each block above the candidate center, a nonempty shape replaces candidate height with its absolute top; a rise above `jumpHeight` returns. For offsets `> 1`, the method also walks upward from the original player-height block and returns on a nonempty shape. The normal no-boost `1.2F` loop runs only at offset `1`. If there is no side-segment hit, the `Float.MIN_VALUE` sentinel remains and no jump timer is armed. A candidate arms `autoJumpTime = 1` only when rise is `> 0.5F` and `<= jumpHeight`.
6. The tick path decrements positive `autoJumpTime` by one and calls `input.makeJump()`. Both releases use the same timer assignment and consumption.

## Requested path controls

- **Ordinary dry slab/stair geometry:** For a forward or non-backward post-move path that satisfies the gates and has the required empty head blocks, the same broadphase and side-segment query runs in each version. A half-slab or stair collision AABB intersected by the first side segment supplies its top and center; the block above that center is then checked exactly as described. No AutoJump geometry/candidate/height/timer difference was found between releases. This is a source-path assessment; no gameplay was run.
- **No-hit control:** With no returned collision AABB intersecting either side segment, the sentinel is unchanged, so `autoJumpTime` is not set. This is identical in both releases.
- **Degenerate/backward/blocked controls:** Both return on zero fallback movement, reject direction below the `-0.15F` forward-dot boundary, require empty initial head blocks, and stop on excessive secondary clearance using the same expressions and order.

## Limits

Static source comparison only. No implementation changes, tests, gameplay, or server behavior were examined. General input processing, effects beyond the AutoJump jump-height adjustment, step/support mechanics, runtime integration, and other release pairs are outside this assignment.