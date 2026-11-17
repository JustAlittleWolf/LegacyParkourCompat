# Independent source verdict

Verdict: ACCEPT the exact draft revision bound below.

Bound claim: `wall-inner-stair-face-choice.md`, SHA-256 `acc67ce705fbc5b4b23eb3e4712179ca6778efacb93694c975791fa9c879f24b` (verified against the file bytes before review).

## Source verification

- Exact canonical decompilation identities are 1.13.2, 1.14 and 1.14.4 in the `ornithe-feather` namespace; provenance names Feather Gen2 build.2 inputs. The 1.14 and 1.14.4 `StairsBlock.java` files have identical SHA-256 `b49cbd43a9be5e848623f25769dede5795f50435ba0e82bbdbbf571527396b9f`.
- In 1.13.2, `StairsBlock.getFaceShape` reports horizontal faces STRAIGHT `{F}`, INNER_LEFT `{F,CW}`, INNER_RIGHT `{F,CCW}`, and outer shapes `{}`. `WallBlock` placement and updates query the stair at its position using the face opposite the wall-to-neighbor direction and accept SOLID, subject to its exception/pole logic. Stairs are not among the exception lists or a middle-pole case.
- In 1.14 and 1.14.4, the wall uses `Block.isFaceSolid` on the neighbor stair's collision shape. `BlockState.m_87223014` delegates to that predicate or to a cache populated by the same predicate; the predicate checks whether the collision-shape face is full. The stair collision shape delegates to the shared stair shape selection.
- Applying the 1.14.4 `SHAPE_INDICES` and quadrant masks to NORTH (horizontal direction id 2) gives STRAIGHT mask 3 (NORTH), INNER_LEFT mask 7 (NORTH+WEST), INNER_RIGHT mask 11 (NORTH+EAST), and outer masks 1/4 (no full horizontal face). TOP/BOTTOM select vertically mirrored base/quadrant shapes and produce the same horizontal full-face sets. The formula rotates to all horizontal facings.
- The draft's concrete coordinate example is correct. With stair at `(0,y,0)` and wall at `(1,y,0)`, the wall's west neighbor is the stair, so the wall queries stair EAST and stores the WEST property. For NORTH/INNER_LEFT, old `{NORTH,EAST}` makes it true; new `{NORTH,WEST}` makes it false. With wall at `(-1,y,0)`, the wall queries stair WEST and stores the EAST property; the old result is false and the new result true. These are the correct physical wall arms.
- The cited source references for the face matrices, wall queries/properties/shapes, and common stair registrations are supported by the exact source files. The 1.14 control corroborates the shared 1.14 release-line stair implementation and collision-face path; the endpoints do not identify the exact introduction patch within that line.

## Acceptance limits

Acceptance is bound only to the exact draft hash above and the source-controlled behavior described there. The endpoint comparison does not establish the exact patch version within 1.14 at which the wall/stair behavior changed. This is source acceptance and does not establish runtime trajectory parity.

No product, Git, index, or test state was inspected or changed.