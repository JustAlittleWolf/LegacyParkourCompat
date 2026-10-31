# Erratum 1 — reachable ShulkerBox progress residue and collision response

This immutable erratum supersedes the vanilla-progress conclusion in `report.md`; the original report remains unchanged. Its statement that every positive vanilla progress is at least `0.1F` overlooked reversal during the animation. The exact binary32 tick transition can leave a positive residual below the changed `Shapes.findBits` tolerance.

## Reachable equal-source input

Starting from `progress = progressOld = 0`, three OPENING ticks add `0.1F` three times and reach `0.30000001192092896F`. The same animation can switch to CLOSING and subtract `0.1F` on three ticks, reaching `0.20000001788139343F`, `0.10000001639127731F`, then `1.4901161193847656E-8F`. On the third closing tick the provider reads `getProgress(1.0F)`, whose `Mth.lerp` returns that same residual. The block entity update path allows open/close status changes between ticks. Neither source version serializes progress in the ShulkerBox BE tag; the witness uses the same client-side animation value in both releases and assumes identical event/state inputs. It does not compare server event production.

## Shape change at the reachable residual

With facing WEST, the equal progress gives `e = 0.5F * p = 7.450580596923828E-9` and local collision bounds `x=[-e,1]`, `y/z=[0,1]` in 1.16.5, 1.17 and 1.17.1 before conversion. The 1.16.5 floor-based `findBits` rejects `-e` at every scale because `floor(-e * scale)=-1`; `Shapes.create(AABB)` preserves the small outward X strip in an `ArrayVoxelShape`. In 1.17 and 1.17.1, nearest rounding accepts `-e` as zero at scale zero; all axes select zero bits and `Shapes.create(AABB)` returns `Shapes.block()`, dropping the strip. EAST/UP/SOUTH do not expose this specific boundary: their endpoint is `1+e`, which both versions encode as the unit cube. WEST/DOWN/NORTH are the affected faces.

## Correct LocalPlayer collision call path and direct response

The collision response is real on the one-axis path, but it does **not** use `CollisionSpliterator` or its AND admission filter. `Entity.collide(Vec3)` passes a one-component request to `collideBoundingBoxHeuristically`; because exactly one component is nonzero, that method takes `Entity.collideBoundingBox`. Its `Shapes.collide(Axis.X, currentAABB, LevelReader, delta, CollisionContext, stream)` scans the relevant block coordinates directly with `LevelReader.getBlockState`, calls `BlockState.getCollisionShape`, and clips against the returned `VoxelShape`. The requested movement is not expanded into a `CollisionSpliterator` query in this branch.

Use a standing LocalPlayer at position `(-0.35000001192092896, 0.2, 0.5)` with the equal residual progress ShulkerBox facing WEST at `(0,0,0)`, and an otherwise empty client level. `Player`'s `0.6F × 1.8F` dimensions produce current AABB `x=[-0.650000023841858,-0.04999999999999999]`, `y=[0.2,1.9999999523162842]`, and `z=[0.19999998807907104,0.800000011920929]`. Hold all of those raw inputs and the SELF X request `0.049999999` equal at both endpoints. The moving box approaches the outward lid face from X<0; the request ends at about X=`-1.0E-9`, short of the ordinary block face at zero.

- In 1.16.5, the box face is `-e`. Its gap from the player maxX is `0.0499999925494194`, so X clips to that value.
- In 1.17 and 1.17.1, the box face is `0`. Its gap is `0.04999999999999999`, larger than the requested `0.049999999`, so X remains `0.049999999`.

The resolved X movement differs by about `6.45E-9`. Both outputs have squared length about `0.0025`, above the 1.17 `Entity.move` position-writer threshold; this is a source-level LocalPlayer position response difference under equal raw state and movement inputs. The block's dynamic-shape setting leaves it eligible at the edge of the direct `Shapes.collide` block scan. No runtime trajectory proof is claimed.

The independent source review is [the reviewer verdict](../shulkerbox-progress-independent-review/verdict.md), SHA-256 `F46DC7C15381E480F46626BB8C10A07ACB7E4E6CF26F2D8074BD4129F0F2B4E0`, mechanically rechecked. Its exact residual and `Shapes.create` calculations are accepted here; the LocalPlayer player-box coordinates and one-axis block-scan consequence are bound to this erratum's 17-file source ledger and were re-traced directly. Do not carry forward the review's proposed `CollisionSpliterator` explanation for this one-axis fixture.

This closes the previously open dynamic ShulkerBox `Shapes.create(AABB)` candidate with a conditional movement response, beginning at 1.17 and persisting unchanged in 1.17.1. It does not establish pair-wide source coverage or runtime parity.
