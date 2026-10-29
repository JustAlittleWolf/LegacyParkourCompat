# Source finding: legacy red-sandstone stairs (ID 180)

Scope: exact identity and block-provider inputs for the 1.8.9 red-sandstone stair. This closes the ID 180 identity row only. It does not establish DDA traversal parity, a complete historical provider adapter, or runtime movement parity.

## Identity and state

In canonical 1.8.9 `net/minecraft/block/Block.java` (around lines 1212–1219), numeric ID 180 is registered as `red_sandstone_stairs` using the shared `StairsBlock` constructor. Its base state is `block13.defaultState()` with `RedSandstoneBlock.TYPE = SMOOTH`; `block13` is the red sandstone block registered as ID 179. `RedSandstoneBlock.Type.SMOOTH` is metadata value 2 and carries the `smooth_red_sandstone` identity. ID 180 therefore has an old smooth-red-sandstone base state. The stair's movement geometry is supplied by `StairsBlock`, not a distinct subclass.

The 26.2 `EntityBlockStateFix` numeric registry table maps `minecraft:red_sandstone_stairs` to 180. `BlockItemIds.RED_SANDSTONE_STAIRS` and `Blocks.RED_SANDSTONE_STAIRS` independently use the same current key. The separate current `smooth_red_sandstone_stairs` key is a different block; do not alias ID 180 to it based on its old base state. Current `Blocks.RED_SANDSTONE_STAIRS` uses the shared `registerLegacyStair` helper with `RED_SANDSTONE`, whose constructed base state is `RED_SANDSTONE.defaultBlockState()`, not the old smooth base state. The source evidence establishes identity and constructor inputs, not a claim that a data-fixer converts every runtime state identically.

The old shared stair metadata decoder stores half from bit 2 (`TOP` when set) and facing from `Direction.byId(5 - (metadata & 3))`; upper bits do not affect those two properties. Its encoder writes only half bit 2 and the inverse facing value. Current stair states expose `FACING`, `HALF`, `SHAPE`, and `WATERLOGGED`; `BlockStateData` explicitly lists red-sandstone-stair states for four horizontal facings, both halves, and straight/inner/outer shape variants. That palette inventory is not evidence that old metadata alone carries current `SHAPE` or `WATERLOGGED`.

## Historical movement and ray providers

The exact old shared `StairsBlock.addCollisions` override is the movement collision provider for this block. It mutates the block instance's collision bounds, adds the half-height base, computes the inner-stair condition from neighboring world states, adds the second half when applicable, conditionally adds a quarter-step from the neighboring stair arrangement, then resets bounds to a full cube. Neighbor classification uses `instanceof StairsBlock`, matching half/facing checks, and corner probes. These details apply to ID 180 because its constructor instantiates this class; they do not depend on its material/base identity.

The old `rayTrace` override is a distinct provider. It derives horizontal facing and half from the block state, marks the instance as corner-mode, then considers six of eight sub-boxes: the two indices listed by `ROTATIONS[facing + topOffset]` are excluded. Each included box is passed through `super.rayTrace`; among non-null hits, the result with the greatest squared distance from the segment end is returned. This documents the block-local ray provider only, not the outer world DDA order or all providers queried by an old ray operation.

In 26.2, the current `StairBlock` computes a voxel shape from the stored `FACING`, `HALF`, and `SHAPE`; its neighbor-derived shape logic recognizes any current `StairBlock` and stores shape state. This is not proven equivalent to old query-time neighbor evaluation: modern-only stair families may enter the current neighbor logic, while old logic recognizes only old stair instances. The base material difference is also explicit above. No equivalence claim is made for current outline/ray shapes, collision shapes, or state migration.

## Applicability and limits

This finding establishes the exact old ID 180 class, base-state and metadata inputs, the shared old collision/ray provider semantics, and the current registry key/state-shape surface. A source-backed historical adapter still needs the corresponding neighbor block/state classification and mutable-bound lifecycle for each provider it intends to reproduce, plus separately closed outer DDA visitation/first-hit semantics. Unsupported or unknown neighbor/provider inputs cannot be treated as non-stairs from this finding. No product code was inspected.

## Sources

Exact source file SHA-256 values are recorded in `sources.sha256`. The canonical old source is the 1.8.9 Ornithe Feather checkout; the canonical current source is the 26.2 unobfuscated checkout. The report is bounded to source-backed facts from those files.
