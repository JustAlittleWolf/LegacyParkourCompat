# Independent source audit: AutoJump 1.21.8–1.21.10

## Verdict

- **1.21.8 → 1.21.9: no AutoJump movement delta found** in the assigned ordinary, dry path.
- **1.21.9 → 1.21.10: no AutoJump movement delta found** in the assigned ordinary, dry path.
- The `LocalPlayer.updateAutoJump(float, float)` body has the same SHA-256 in all three versions: `ba6582def07320243b42431c8353a1c80154cf9246164914a29803e93d014601`.
- This source verdict does not assert runtime parity or a general pair freeze.

## Source identity and integrity

All compared sources are exact local `mojmap` decompiles. The source pins matched the assignment:

| Version | Pin | Computed SHA-256 | Pin check |
|---|---|---|---|
| 1.21.8 | parent `mojmap.sources.sha256` | `8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008` | match |
| 1.21.9 | `.decompile-provenance.json` | `37d3049478585a43ffcace14fc8f2ee814b35ceedd8c9bad8b60adaed9b72c4f` | match |
| 1.21.10 | parent `mojmap.sources.sha256` | `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1` | match |

The 1.21.8 and 1.21.10 ready markers identify `mojmap`, exact version IDs and their corresponding source-manifest digests. The 1.21.9 manifest pins the exact client jar and client mappings input. The SHA-256 for each audited `LocalPlayer.java`, `CollisionGetter.java`, `BlockCollisions.java`, `AABB.java`, `SlabBlock.java`, `StairBlock.java` and `Block.java` matched its source-manifest entry on every version. The named methods are present as intact source bodies.

## Traced behavior

`LocalPlayer.move` calls `updateAutoJump` after `super.move`, passing horizontal position deltas cast to `float`. If their squared length cast to `float` is `<= 0.001F`, the method reconstructs a horizontal vector from `getSpeed() * input.getMoveVector()` and yaw sine/cosine; it repeats the same threshold and returns if still `<= 0.001F`. Otherwise it normalizes with `Mth.invSqrt`, computes a float dot product with `getForward()`, and suppresses candidates only when that dot is `< -0.15F`.

Before probing, `canAutoJump` requires AutoJump enabled, timer `<= 0`, grounded, not staying on a ground surface, not a passenger, nonzero movement input (`lengthSquared() > 0.0F`), and block jump factor `>= 1.0`. The initial collision context checks the block containing `(x, boundingBox.maxY, z)` and the block immediately above; either nonempty collision shape prevents candidate search.

Search distance is `max(speed * 7.0F, 1.0F / inverseLength)`. The search AABB spans current position to the projected endpoint plus player height, inflated horizontally by player width. The two tested segments connect corresponding left/right side points at the start and end, with each endpoint raised by `0.51F`; the side offset is the normalized movement vector crossed with up, scaled by half the player width. A candidate AABB qualifies if it strictly overlaps the endpoint-bounds AABB of either segment: `AABB.intersects(Vec3, Vec3)` takes per-axis minima and maxima of the two endpoints, then applies strict AABB overlap comparisons. This is not a line-segment intersection test.

`getCollisions(player, box)` puts entity collision shapes before block collision shapes. In the assigned ordinary clear path there is no other entity candidate. Block shapes are enumerated by `BlockCollisions`' cursor traversal; each shape is translated to world position, filtered for overlap with the search box, and yielded. AutoJump flattens each yielded shape with `toAabbs()` and stops at the **first** AABB intersecting either side segment. It does not sort by height or compare later candidates.

For that first hit, initial candidate height is the AABB's `maxY`; the method samples collision shapes above the block containing the AABB center for integer offsets `i = 1; i < heightLimit; i++`. A nonempty sample replaces candidate height with that shape's maximum Y plus block Y. If that height exceeds the limit relative to player Y, the method returns immediately. For `i > 1`, it also advances the original headroom position and returns if that collision shape is nonempty. The first ray-hit AABB ends candidate enumeration after these checks, even if it does not ultimately set the timer.

The limit starts at `1.2F`; Jump Boost adds `(amplifier + 1) * 0.75F`. The integer loop uses strict `<`: at 1.2 or 1.95 it checks only offset 1; at 2.7 it checks offsets 1 and 2. Finally the rise is cast to `float`, and `autoJumpTime` is set to `1` only when rise is `> 0.5F` and `<= heightLimit`. The tick path decrements a positive timer before later movement; the timer gates candidate generation and is not itself a jump impulse.

## Requested block controls

Candidate selection uses actual vanilla collision shapes through `CollisionContext` and leaves block states untouched. A full cube contributes the optimized full-block shape. Slabs provide bottom-half (`0..8/16`) or top-half (`8..16/16`) shapes by slab type; stairs select collision shape by facing, half and straight/inner/outer state. Their component AABBs are the ray candidates, so a half slab, stair box or full cube is handled by the same first-intersection and candidate-height rules. This audit found no shape-query or enumeration delta among these three versions; it does not infer a trajectory for every orientation or neighboring arrangement.

## Evidence locations

- `net/minecraft/client/player/LocalPlayer.java`: 1.21.8 `move` 916, `updateAutoJump` 935, `canAutoJump` 1051, `isMoving` 1061; 1.21.9 and 1.21.10 `move` 906, `updateAutoJump` 925, `canAutoJump` 1041, `isMoving` 1051. Timer decrement is at 706 (1.21.8), 710 (1.21.9/1.21.10).
- `net/minecraft/world/level/CollisionGetter.java`: `getCollisions` 83 and block-collision adapter 95–104 in all versions.
- `net/minecraft/world/level/BlockCollisions.java`: traversal and shape filtering in `computeNext` 69–101 in all versions.
- `net/minecraft/world/phys/AABB.java`: `intersects(Vec3, Vec3)` at 243 in all versions.
- `net/minecraft/world/level/block/SlabBlock.java`: state shape at 59–63; `StairBlock.java`: state/half dependent shape at 77–91; `Block.java`: shape construction helpers. All are manifest-pinned on each version.

## Residual scope

No pose or locomotion input producers, step solver, effects or fluids beyond the AutoJump height-limit read, border behavior, other release families, product code, wiki evidence, Git history, tests or gameplay runs were examined. The `.8 → .9` `move` method also changes a walk-distance call outside this AutoJump path; it is not part of this verdict.

