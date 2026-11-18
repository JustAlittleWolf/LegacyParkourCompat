# B10 AutoJump source coverage: 1.21.5 to 1.21.6

Status: COMPLETED. No eligible difference found in the assigned slice; no finding draft.

## Exact inputs and integrity

- 1.21.5 Mojmap source root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.21.5/mojmap`; ready manifest names version `1.21.5`, 5,921 source files, and source-manifest SHA-256 `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9`, matching the actual manifest. Assigned official mappings SHA-256: `3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5`.
- 1.21.6 Mojmap source root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.21.6/mojmap`; provenance manifest SHA-256 `56b036c61094d1d936882bdf5b2b59044512189cb33adbb075880c727da9b19e`, matching the actual manifest; manifest identifies `LocalPlayer.java` SHA-256 `53f2a71a886b9c71853afe36f2df857f80a9bf1cd6ec4ca8604ccd7e238d89ee`, matching the file. Assigned official mappings SHA-256: `2ff82b34ce5f849aaafdff51e23b9448c4263881238b780a441fe1061a6fc261`.
- 1.21.5 `LocalPlayer.java` SHA-256: `f1fcfed4a938732361e7ad951f93e9b73b02320ee56f0b219e2b3c7acdbfa2ef`, matching its source manifest.

## Compared slice and witnesses

In both versions `net/minecraft/client/player/LocalPlayer.java`, `move` (around lines 914–923), `updateAutoJump` (935–1026), `canAutoJump` (1051–1059), and `isMoving` (1061–1063) are intact and identical line-for-line. The motion vector is the post-move horizontal displacement. Only when its float squared length is `<= 0.001F` does the routine build a substitute from movement vector scaled by speed and rotated with yaw; that fallback is also rejected at `<= 0.001F`. The normalized vector is compared with `getForward()` and rejected only when dot product `< -0.15F`.

The dry block-space gates are unchanged: the collision shape at the block containing `(x, bbox.maxY, z)` and then the block above must both be empty. Broad phase constructs an AABB from start to projected endpoint, adds player height, inflates X/Z by player width, and projects two side segments at a `0.51F` Y offset using the normalized vector cross up scaled by half width. `getCollisions` supplies shapes; their constituent AABBs are consumed in iterator order. The first candidate intersecting either side segment wins; scanning breaks after this candidate. It initializes candidate top to `Float.MIN_VALUE`; no intersecting candidate leaves that sentinel and cannot arm the timer.

For the candidate, the routine samples collision shapes above its center in the existing `for (int i=1; i<1.2F; i++)` loop, updates candidate top from nonempty shape max-Y plus block Y, and returns if clearance exceeds `1.2F`. Its secondary head/block clearance checks and early returns are unchanged. Finally, it sets `autoJumpTime = 1` only when candidate top minus player Y is `> 0.5F` and `<= jump threshold` (1.2F, plus the existing Jump Boost increment). Timer countdown (`tick`, lines 705–706), movement call site, and the `canAutoJump` gates are identical.

Representative dry controls: bottom slab shape is `[0,0.5]`, top slab `[0.5,1]`, double slab full-block, as unchanged `SlabBlock.getShape` (lines 35–64); unchanged stair shapes include upper-half collision sections, selected by half/facing/shape in `StairBlock.getShape` (lines 38–88). Thus slab candidates whose highest intersecting top is at most 0.5 fail the final threshold; a stair candidate above 0.5 can reach the unchanged timer condition if the unchanged clearance checks pass. These are source-path witnesses, not simulated trajectories. No-hit control follows the unchanged sentinel path above.

## Dependency closure and bounds

Checked paired dependencies: `AABB.intersects(Vec3, Vec3)` and its strict AABB intersection inequalities are identical (the .6 AABB file only adds other methods); `CollisionContext.of(Entity)` is identical and `EntityCollisionContext.java` hashes match; `BlockState.java`, `BlockBehaviour.java`, `SlabBlock.java`, `StairBlock.java`, `LevelReader.java`, `VoxelShape.java`, and `BlockCollisions.java` source hashes match. `CollisionGetter` differs elsewhere, but its `getCollisions` concatenation and `getBlockCollisions` query methods at lines 83–93 are unchanged. First-candidate iteration and dry block-shape behavior therefore have no observed delta in this bounded path.

Excluded and unexamined by assignment: movement input production; wet/fluid/environment contact; support and step logic; other AutoJump edges or trigger timing; runtime/gameplay. No tests, launch, product/wiki/Git/history, or source generation used.
