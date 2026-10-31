# Dynamic partial-block provider witness

This addendum strengthens the expanded-query candidate in `report.md`: an existing vanilla dynamic collision provider can lie inside the representation-only boundary strip and cause a direct axis response difference. This is a static source fixture under equal endpoint inputs; it makes no claim about a produced runtime trajectory or server state.

## Inputs

- LocalPlayer is in the `SWIMMING` pose (`0.6F × 0.6F`) with X/Z center `0.42500009192092897` and Y bottom `0.12500008`. Its box is `[0.12500008,0.7250001038418579]` on X/Z and `[0.12500008,0.7250001038418579]` on Y.
- `Entity.collide(Vec3)` receives the same raw SELF displacement `(0.02499997615814209,0.02499997615814209,0.02499997615814209)` at both versions. Thus the query in the heuristic multi-axis branch is `[0.12500008,0.75000008]` on all axes.
- At block `(0,0,0)`, the equal logical block and block-entity inputs describe a moving piston carrying an oak bottom slab upward, extending, with `progress = 0.6250000596046448F` and the normal collision-query thread-local direction. Oak slab is an ordinary pushable material: `Material.Builder` defaults to `PushReaction.NORMAL`; `Material.WOOD` does not override it; `Blocks.OAK_SLAB` uses `Material.WOOD`.

## Source consequence

`PistonMovingBlockEntity.getCollisionShape` selects `movedState` for an extending non-source piston and moves its collision shape by `directionStep * getExtendedProgress(progress)`. For UP and extending, `getExtendedProgress` returns `progress - 1.0F = -0.3749999403953552F`. The bottom slab's local collision shape is `[0,0.5]` in Y. Moving it by that float gives a top boundary `0.12500005960464478` (within the interval `[0.125,0.12500008]`), and full X/Z coverage.

The old expanded query shape retains minimum Y `0.12500008`; the moving slab top is below it by `2.0395355224609375e-8`. The exact common volume is empty, so `CollisionSpliterator`'s `joinIsNotEmpty(..., AND)` rejects this provider. The join precheck's `1.0e-7` gap tolerance only prevents an early separation return; the merged interval occupancy still has no overlap.

The new query shape has been quantized to minimum Y `0.125`. It overlaps the same slab provider by `5.9604644775390625e-8`, so the Boolean AND query admits it. X/Z overlap spans the player box in both versions.

`Entity.collideBoundingBoxLegacy` consumes the resulting rewindable block-collision stream with Y clipped first. In `VoxelShape.collideX` (axis-cycled Y for this call), the slab's top minus the current player's min Y is `-2.0395355224609375e-8`, which satisfies the source gate `g >= -1.0E-7` and clamps positive requested Y displacement to that tiny negative value. The outer `Shapes.collide` returns zero when the result magnitude is below `1.0E-7`. The old block stream leaves the requested Y displacement unchanged. With the new admitted shape, the single-shape clip returns the tiny negative gap (`-2.0395355224609375e-8`); the outer stream clipper has no next-shape iteration at which to zero it. This is still a direct Y response difference of about `0.025` under the equal raw input. The later axis clips cannot restore Y. The raw query reaches `CollisionSpliterator` on both endpoint paths, as recorded in the main report.

## Scope and limits

This is a concrete conditional operation-level response witness using an era-existing, ordinary vanilla slab shape and vanilla moving-piston collision provider. It assumes the specified equal block-entity state and equal raw SELF movement vector; it does not establish how such a state/vector is produced in ordinary gameplay. The movement operation is an ordinary player collision consumer under those inputs. Independent pair audit is still required before treating the finding as accepted or closing the pair-wide collision gate.

Exact endpoint source hashes and manifest comparisons are in `source-hashes.tsv` and `manifest-check.tsv` adjacent to the main report.
