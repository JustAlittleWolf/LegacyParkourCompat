# Moving-piston wall-face projection

## Scope and immutable inputs

Closes only the `minecraft:moving_piston` row in `wall_remaining_face_shape_census/report.md` (input SHA-256 `f1a12c7564f14c91c652bea6603e8ada8350b98c2892df05c591c0161d4e041d`). Endpoints are Minecraft 1.13.2 and 1.14.4, Ornithe Feather Gen2 build 2. The source-manifest SHA-256 values are `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` and `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc`; the endpoint ready markers agree on release, mapping build and manifest digest.

The accepted `context_shapes/report.md` (revision SHA-256 `52c002332843b77063805c85d414e9522de937fd5e350e7a46b319ec3460d140`) and `context_shapes_audit/verdict.md` (SHA-256 `87b6e19907586393d08f2d4bd4e5d8b9eeee4c39c9b61049df8383b567500705`) are reused only for the identical `MovingBlock.getCollisionShape` delegation, missing-entity empty fallback, and `MovingBlockEntity.getCollisionShape` shape/progress procedure. This report adds the target face projection; it does not re-audit that provider math.

Relevant paired source hashes: old/new `MovingBlock.java` `19b8a41104da7b13eac58e2c01704043f6ff7ac1eea33457833bd41ea5810d61` / `6e3ec2d4c9712f8012bb2bcad5474125dacd1acf3d4b02bd8a5d8ea0c4bf4e6e`; `MovingBlockEntity.java` `6684ccc05adf8b29cac55f9796133405c6d4f6853051e651895303b657cd1c46` / `e03c4123c06309c04dbc039c6f276f1bf988018a4f13cc329bd6d07acc6bc54a`. Target projection sources: `Block.java` `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5`, `WallBlock.java` `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7`, `PistonHeadBlock.java` `348885e6949b4aa754496af1e641ba07de9dcc1402ce0b88b4cde8f57febfd5b`, `PistonBaseBlock.java` `37409c9f2390642103155dcec3e922e0d387e9d0bbca069fb334277d5d84a058`, and `VoxelShape.java` `8efb68bf9d5efdb0ec48e401d1852e90458c7094f8fa0c8e9ae418f268c40fdd`.

## Old result

1.13.2 `MovingBlock.getFaceShape` (`block/MovingBlock.java:140–142`) returns `FaceShape.UNDEFINED` for every queried direction and state. The old wall generic neighbor arm requires `FaceShape.SOLID` (`WallBlock.java:113–116`); therefore the wall bit is false for each of north/east/south/west, regardless of moving-entity presence, moved state, facing, source flag, or progress. The face-shape method does not consult the entity's dynamic collision shape.

## Target result and complete parametric rule

1.14.4 `WallBlock`'s generic arm is `!isExceptionForAttachment(block) && isFaceSolid` (`block/WallBlock.java:48–51`). `MovingBlock` is not an attachment exception; its block collision provider gets the `MovingBlockEntity` and delegates, or returns empty if it is absent/wrong type (`block/MovingBlock.java:104–107`). `Block.isFaceSolid` is `!state.is(BlockTags.LEAVES) && isFaceFull(state.getCollisionShape(world,pos), face)` (`block/Block.java:402–408`); `moving_piston` is not a leaves state. `VoxelShape.getFaceShape` slices the requested boundary plane (`shape/VoxelShape.java:189–203`), and the predicate is true exactly when that slice is a full block face.

The moving-block blockstate itself has `FACING × TYPE` (`MovingBlock.java:26–27,135–137` in 1.13.2; target `:30–31,131–132`). The collision provider does not read either property: it uses the block entity's `facing`, `extending`, `source`, `progress`, and `movedState`. Therefore both type values and every registered facing value on the moving-block state produce the same result for an identical entity; mismatching entity/blockstate facing does not rotate its shape. A missing or wrong-type block entity yields the empty target shape, hence false in all directions.

Consequently, for any legal non-source `movedState` collision shape `S`, move direction `d`, and current progress `p`, the exact target bit is the full-face predicate on `S` translated by `d * (p - 1)` when extending, or `d * (1 - p)` when retracting. This is state-dependent: a full-cube moved state yields (for horizontal `d`) no horizontal full face at `p=0`, the face opposite `d` at extending `p=0.5` or the face toward `d` at retracting `p=0.5`, and all four horizontal faces at `p=1`; partial/state-dependent moved shapes are evaluated by the same exact slice rule. No single target bit applies to all movable states.

For source piston movements, the full-face outcomes can be evaluated from the exact head/base geometry. Extension creates a source entity with a piston-head `movedState`; retraction creates a source entity with a normal/sticky piston-base `movedState` (`PistonBaseBlock.java:310–326` target). Tick progress is `0`, `0.5`, then `1` (`MovingBlockEntity.tick`, both endpoints); collision uses `this.progress` directly, not render interpolation. The head plate is 4/16 thick and spans the other two axes; its arm is narrower than a full face. The extended base is a 12/16 slab. Type does not change those shapes. Target outputs below are ordered north/east/south/west:

| Source movement variant | `p=0` | `p=0.5` | `p=1` |
|---|---|---|---|
| Extending source head, horizontal facing `d` | none | none | `d` only |
| Extending source head, vertical facing | none | none | none |
| Retracting source, horizontal facing `d` (extended base plus translated head) | `opposite(d)` | `opposite(d)` | all four |
| Retracting source, vertical facing (base plus translated head) | none | none | all four |

These results hold for both piston types and for the generated short/long head variants: `SHORT` changes only the narrow arm, not the full-width 4/16 head plate; `TYPE` does not enter collision-shape selection. At retracting `p=1`, the base's 12/16 slab and head plate meet to form complete horizontal slices; before that endpoint, their gap prevents perpendicular full faces, while the base's opposite-facing end remains full. For a horizontal retracting source, the head-facing plane becomes full only at the terminal progress value among the reachable tick values.

All four horizontal directions are covered: rotate the horizontal-facing table with `d=NORTH/EAST/SOUTH/WEST`; vertical `d=UP/DOWN` uses its separate row. The target therefore differs concretely from old `false` for every retracting-source progress (opposite-facing bit) and at terminal retracting source progress (all bits); it also differs for extending source heads at terminal progress when horizontally facing. The non-source parametric predicate supplies exact target behavior for every legal moved-state shape without asserting a uniform output across distinct blocks/states.

## Wall state producer and closure

The direct consumer is the target wall placement/update calculation, which samples neighboring block state and calls `Block.isFaceSolid` for each horizontal direction (`WallBlock.java:55–104`); the resulting directional booleans select the wall shape. That is the relevant reachability route for this predicate. The source projection closes the requested `moving_piston` row as a real, state/progress-dependent change: old wall bits are uniformly false, while target bits can be true under the exact cases and predicate above. It does not claim closure of any other census row, the overall wall crosswalk, or the pair.
