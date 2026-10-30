# Product review correction supplement 001

This supplement preserves and corrects the verdict in `review.md`; it does not modify or replace that original report.

## Immutable input and reviewed commit

- Original product-review report `review.md` SHA-256: `8455380D670C000E82DF8584A63A07D07B5AD22817C7DBE87E7E307F17774DB3`.
- Correction commit reviewed: `1e9b0c9ec713a0fde9471fcabf98e26a41d728de` (`Filter moving fences at the verified shape dispatch seam`). HEAD matched this commit; the product worktree was clean at review start.
- The correction commit changes only `BlockCollisionsMixin.java` and `MovingFenceEdgeScanHook.java`; no tests, build, game, or runtime checks were run.
- Canonical source root remains `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`, read-only.

## Corrected verdict

**The prior descriptor blocker and exact-integer-boundary applicability caveat are closed by this correction.** The implementation now uses the existing `CollisionContext.getCollisionShape` `@WrapOperation`. Its invocation descriptor and handler arguments match the 26.2 bytecode. The hook runs on all admitted moving-piston candidates immediately before native shape evaluation, so the old inclusive floor/ceil face test can reject old-envelope two-face cells whether the current cursor classifies them as type 0, 1, or 2.

The verdict is accepted for the bounded resident moving-piston/fence candidate omission, with one operation-order limitation below: the old collector skips these cells before chunk and state access, whereas this adapter runs after the current state lookup and uses the already cached getter to inspect the block entity. The reviewed evidence supports non-loading, read-only client lookup and collision-result equivalence for the stated domain; it does not establish identical lookup count/order.

## Descriptor and argument audit

The corrected wrapper is at product `BlockCollisionsMixin.java:124–150`. Its `@At` target is:

```text
Lnet/minecraft/world/phys/shapes/CollisionContext;getCollisionShape(
  Lnet/minecraft/world/level/block/state/BlockState;
  Lnet/minecraft/world/level/CollisionGetter;
  Lnet/minecraft/core/BlockPos;
)Lnet/minecraft/world/phys/shapes/VoxelShape;
```

In one `computeNext` call site, `javap -p -c` reports the matching `invokeinterface CollisionContext.getCollisionShape` descriptor exactly. `javap -p -s net.minecraft.world.phys.shapes.CollisionContext` reports the same abstract method descriptor. The wrapper handler arguments are also in invocation order: receiver `CollisionContext`, then `BlockState`, `CollisionGetter`, `BlockPos`, then `Operation<VoxelShape>`. The hook does not use an `@Local` capture; `pos` is the actual invocation argument loaded from the `BlockCollisions.pos` field at bytecode offsets 163–167. Removing the `pos` shadow therefore avoids relying on mutable-field state or an LVT capture. The unrelated result-provider wrapper's existing `@Local BlockState state` is unchanged.

The 26.2 merged development jar used for static inspection has SHA-256 `0468742D98FE5EC68508493EF2155C6E5209F991055BC0C17DDF26872EB0D110`. Its `BlockCollisions.computeNext` bytecode has one `CollisionContext.getCollisionShape` invocation at offset 167. The earlier `BlockState.is(Object)Z` call remains a distinct native cursor type-2 gate and is no longer an injection target.

## Admission and old-face coverage

`BlockCollisions.computeNext` forms its current cursor bounds as:

```text
min = floor(box.min - 1.0E-7) - 1
max = floor(box.max + 1.0E-7) + 1
```

The historical envelope checked by `MovingFenceEdgeScan` is inclusive:

```text
min = floor(box.min) - 1
max = ceil(box.max)
```

On each axis the current cursor bounds contain the historical envelope: at an exact integer endpoint the current bound gains one cell on that side; otherwise the corresponding bounds are equal. An old two-face cell therefore cannot be lost merely because cursor bounds differ. It can become a current interior, face, or edge cell, but not a current corner unless it was also an old three-face corner. The wrapper is reached for old two-face moving-piston cells:

- type 0 passes the cursor-type gates;
- type 1 passes because the moving-piston block is `dynamicShape`, which leaves `BlockStateBase.cache` null, and `hasLargeCollisionShape()` returns true when that cache is null;
- type 2 passes the native `MOVING_PISTON` state predicate.

The canonical `MovingPistonBlock.java` SHA-256 is `9a8d9b03be6866b1e5a2d237f8cb635b89c08e63a241db2d32338448240102d1`; it configures `.dynamicShape()`. Canonical `BlockBehaviour.java` SHA-256 is `9c7a103492d0714c90397da88eb696912ff6a9ca1c005d984c4746d52637fd1e`; `hasLargeCollisionShape()` returns true when `cache == null`. Thus the corrected seam covers the exact-integer-boundary case that the original type-2-only wrapper missed.

The shape wrapper first requires `state.is(Blocks.MOVING_PISTON)`, then retains the existing suffocation, placement, always-collide-with-fluid, local-player, enabled client-profile, and cached-getter gates. It obtains the BE at the invocation's `pos`; a missing BE or absent hook follows native shape evaluation. If the hook confirms the supported non-source moving-piston/fence domain and at least two old-envelope faces, it returns `Shapes.empty()` before evaluating the current collision shape. The seven legacy fence keys, resolver split (`V1_13` no-op; `V1_13_1` omission), and native fallback were not changed.

## Remaining operation-order limit

Correction 001 to the source closure establishes that 1.13.1 rejects every two-face cell before `isChunkLoaded`, block-state, or collision-shape access. The product adapter still reaches `getChunkForCollisions` and `getBlockState` before this wrapper. It does not force-load chunks and does not call the shape provider for an omitted candidate, but it does not reproduce the historical pre-state-access short circuit. Retain the bounded claim as omission from collision-shape candidates/results for matching resident carried fences; do not claim identical collector access order or a general reimplementation of the whole old collector.

## File and source identities

Product file hashes at the reviewed correction commit:

| File | SHA-256 |
|---|---|
| `src/client/java/me/wolfii/legacyparkourcompat/mixin/BlockCollisionsMixin.java` | `9f9194a00e62fe3802743cb8a9a0be827b6662d519a91821f89fa8e68b5079ea` |
| `src/client/java/me/wolfii/legacyparkourcompat/mechanic/hook/collision/MovingFenceEdgeScanHook.java` | `867c4882d70dfedd5d86e0c3645486aa754f7260129782f8c9d017f71b900891` |
| `src/client/java/me/wolfii/legacyparkourcompat/change/v1_13_1/MovingFenceEdgeScan.java` (unchanged) | `445009b2679ec6eb2553d2ce12330eb15e1d93def8248a1ba212920f6d0f228b` |
| `src/client/java/me/wolfii/legacyparkourcompat/change/v1_13/NativeMovingFenceEdgeScan.java` (unchanged) | `a95ac3b683b58eb2f59c65b6372a4b220acb2453a14dfb72af82d6dfbff6fa15` |

| Canonical source | SHA-256 |
|---|---|
| 26.2 `BlockCollisions.java` | `eb8a8f6f07b1d6384f17987818f056d41adfd6b2ad120d26774bfa550f4fc51e` |
| 26.2 `Cursor3D.java` | `9a1fdc92bb95013c7e70da467a0b5e5b1c9787daafcb0e7e3a8c950dd0e7287f` |
| 26.2 `CollisionContext.java` | `0af02ec998df9e5b093a1b1887255619dd9b9a08604de40b452c11922fbf3bca` |
| 26.2 `MovingPistonBlock.java` | `9a8d9b03be6866b1e5a2d237f8cb635b89c08e63a241db2d32338448240102d1` |
| 26.2 `BlockBehaviour.java` | `9c7a103492d0714c90397da88eb696912ff6a9ca1c005d984c4746d52637fd1e` |
| 26.2 `Blocks.java` | `f3f2faeed23e9697407069a1d523107491590b8710175523ea05294d5bd00435` |
