# F019: Wall neighbor connection predicate

- Older version A: 1.13.2
- Newer version B: 1.14.4
- Mechanic / coverage slice IDs: wall collision-shape neighbor flags; S023
- Classification: changed behavior candidate
- Confidence: candidate (source predicates differ; outcome for common-era neighbor states remains to be enumerated)
- Applicability: A-era wall placement and neighbor updates; first changed release unknown within (1.13.2,1.14.4]
- Runtime validation: not performed

## Paired evidence

- A source root: `../../../build/movement-campaign-2026-10-07/ready/1.13.2/ornithe-feather/`; `WallBlock.java`::`shouldConnectTo`, `getPlacementState`, `updateShape`, and `getCollisionShape`, lines 37-38, 51-55, 69-115; SHA-256 `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf`. Neighbor `BlockState.getFaceShape`, lines 285-287, SHA-256 `0496fda381628e90b3ff8ae376a5baea24cbd662366f34d70991252fab2fe70`; base `Block.getFaceShape`, lines 383-385, SHA-256 `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4`.
- B source root: `../../../build/movement-campaign-2026-10-07/ready/1.14.4/ornithe-feather/`; `WallBlock.java`::`m_71853799`, `getPlacementState`, `updateShape`, and `getCollisionShape`, lines 39-40, 48-52, 55-102; SHA-256 `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7`. Neighbor `BlockState.m_87223014`, lines 326-328, SHA-256 `6c6703c7c2f7203a7507d8a621bc83e1dd613b32089394f5af4fbd8636b165f`; `Block.isFaceSolid`, lines 402-409, SHA-256 `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5`.

## Source-level difference

A computes each horizontal neighbor flag by passing the neighbor's `FaceShape` to `shouldConnectTo`. The predicate accepts `MIDDLE_POLE_THICK` unconditionally, accepts `MIDDLE_POLE` for a fence gate, or accepts `SOLID` when the neighbor is not an attachment exception. B passes a boolean face-solidity result and direction to `m_71853799`; it accepts a full collision face when the block is not an attachment exception, a block in `BlockTags.WALLS`, or a direction-compatible fence gate. `BlockState.m_87223014` may use a cached face-solidity value for cached states; otherwise `Block.isFaceSolid` excludes leaves and checks whether the collision shape fills that face.

Both placement and neighbor-update paths recompute the four horizontal flags and `UP`; `WallBlock.getCollisionShape` selects the collision-shape array from those flags. The predicates therefore have different source inputs and branches. Their equivalence for each common-era vanilla neighbor class depends on its A `FaceShape`, its B collision-face result/cache, attachment-exception membership, and B tag membership.

## Reachability and dependencies

Placement and neighbor updates are ordinary wall-state transitions, and the resulting state feeds the collision-shape path used by local player movement. `D-COLLISION-SHAPES` remains open: enumerate A-era `getFaceShape` providers and compare their outputs with B face-solid/cache and tag paths before claiming a common-world outcome. Modern-only block registrations are excluded. No general block-state emulation is proposed.

## Consequence and uncertainty

The connection predicate changed between endpoints and can select different wall collision-shape flags if a reachable common-era neighbor produces different predicate inputs. No specific common-era block outcome has yet been confirmed. Runtime validation is deferred; first changed release unknown within (1.13.2,1.14.4].

## Handoff

Independent source candidate. Continue the A-era face-shape provider inventory and tag/cache dependency analysis. No implementation or wiki feedback was used.