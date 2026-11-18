# Independent source audit: cauldron component clipping observability

**Verdict: accept the narrowly bounded horizontal clip difference, conditional on the command-created embedded setup and a ceiling that blocks the old step-up route.** Finding revision reviewed: `source-cauldron-component-clip-observability/finding-r2.md`, SHA-256 `29075e1d51ae4885ac53420c481c1b0ef002b96e8883cd426065c4f2e1e519e2`.

## Evidence identity

The version-root ready/provenance manifests identify 1.8.9 Ornithe Feather and 26.2 unobfuscated sources; their source-manifest SHA-256 values are `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` and `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`. The reviewed file's identity matches the requested digest.

## Independent trace

In 1.8.9 `CauldronBlock.addCollisions` appends five boxes in order: floor `[0,1]×[0,5/16]×[0,1]`, west, north, east, south walls, each 1/8 thick and height 1. `Entity.move` clips Y, X, then Z over the list; `Box.intersectZ` requires strict overlap on X and Y. With feet at 0.5, the floor has no Y overlap. For the east wall at x `[0.875,1]` and the south wall at z `[0.875,1]`, the stipulated x-min `0.99999995` overlaps each wall by `5e-8`, so the east wall does not block +Z (its Z range already overlaps) and the later south wall clips +0.2Z to +0.075.

With exactly that five-box occupied union represented as one modern `VoxelShape`, `VoxelShape.collide` samples orthogonal X cells using `minX + 1e-7` and `maxX - 1e-7`. The first value is `1.00000005`; the cauldron shape has no occupied X interval to the right of 1. The orthogonal cell range is empty, so +Z remains +0.2. This establishes the requested *Z-axis clip-kernel* difference; it does not compare against 26.2's native cauldron shape.

## Full-move gate and corrected setup details

The original unobstructed setup does not establish an end-of-move Z difference. In 1.8.9 `LivingEntity` sets `stepHeight=0.6F`. Since the entity starts grounded and Z clips, `Entity.move` enters its step alternative. Raising 0.6 from feet 0.5 clears the wall top at y=1; the second downward leg can settle on that wall. The old final move can therefore be `(0, +0.5, +0.2)`, while the equal-union modern move is `(0, 0, +0.2)`: here the Z coordinate is equal and only Y differs. Do not describe the direct +0.075 clip as the final move in this unroofed case.

To preserve a full-move Z distinction, add a top slab at block `(1,2,0)`, whose collision begins at y=2.5. It leaves the initial body top at 2.3 clear and limits upward movement to 0.2, below the 0.5 rise needed to clear the cauldron wall from feet 0.5. In the old step alternative both horizontal legs still clip to +0.075Z; their squared horizontal gains tie the normal leg, and the source's `>=` fallback restores the normal clipped move. The modern shape still clips no Z, has no horizontal collision and does not enter the step alternative. With the ceiling, the resulting full-move distinction is Z: old +0.075, modern +0.2; both have zero net Y.

The provided AABB endpoints are slightly inconsistent with player dimensions. Both 1.8.9 `Entity.setPosition` and 26.2 `EntityDimensions.makeBoundingBox` use `float width=0.6F` and `float halfWidth=width/2.0F` (`0.30000001192092896`). A command-set center x of `1.299999961920929` yields minX approximately `0.99999995` and maxX approximately `1.5999999738418579`, not `1.59999995`. This tiny correction preserves the 5e-8 overlap under audit. A center z near `0.500000011920929` analogously centers the actual float width on z≈0.5.

## Lawful placement and push-out/tick gates

The command-created initial state is supported: a bottom slab at `(1,0,0)` has top y=0.5, and the player centered near `(1.3, 0.5, 0.5)` stands on it. Then authorized `/setblock 0 0 0 cauldron` creates only the 5e-8 side-wall overlap. In 1.8.9 `SetBlockCommand` permission level 2 calls `World.setBlockState` without entity-collision validation. In 26.2 the gamemaster-gated default `setblock` path uses `BlockInput.place` and `Level.setBlock`; these validate world/debug bounds and write the block, with no entity-collision rejection.

I found no 1.8.9 entity-tick push-out path. 26.2 `LocalPlayer.aiStep` does call `moveTowardsClosestSpace` when `!noPhysics`, but its four sample X positions for this player are approximately 1.09 and 1.51, both in the neighboring block column x=1; they do not sample the cauldron at x=0. In addition, the actual modern cauldron is non-suffocating under the default full-collision-shape predicate. Thus the documented supported stationary placement has no source-identified push-out that removes this overlap before the immediate request. Avoid inserting an unrelated player-motion tick between placement and request; the claim does not establish command-free natural reachability.

## Scope

Accept only the equal-occupied-union clip algorithm distinction and its command-enabled, supported, stationary applicability. The finding's mapping descriptions and main source reasoning are consistent. Carry the ceiling condition and corrected float-width endpoints whenever claiming an end-of-move horizontal difference. This audit does not establish runtime parity or natural locomotion reachability.

