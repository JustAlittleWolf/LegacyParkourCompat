# Additive correction r1: stair inner-shape occupancy

This note corrects one set description in `slab-stair-comparison.md`. The original report is preserved byte-for-byte; its SHA-256 remains `4f95e15278e3a55363e851a73c232ec77e5d12be7820f6a701dc2fb1b0fa2dac`.

Exact source: `net/minecraft/world/level/block/StairBlock.java`, lines 37–45 in both 26.1.2 and 26.2. The files are identical, SHA-256 `2a8ad6eedc5a151af7a739ccd476f18cab4fe78e9df7c1ec601cb9687a77630b` on each side. The source defines:

- `SHAPE_OUTER = lower-full-half ∪ upper-quarter Q0`.
- `SHAPE_STRAIGHT = SHAPE_OUTER ∪ rotate90(SHAPE_OUTER)`, so its upper occupancy is `Q0 ∪ Q1` (two adjacent quarters).
- `SHAPE_INNER = SHAPE_STRAIGHT ∪ rotate90(SHAPE_STRAIGHT)`. The second rotated straight has upper occupancy `Q1 ∪ Q2`; `Q1` overlaps, so the total upper occupancy is `Q0 ∪ Q1 ∪ Q2`: three quarters, not four.

Accordingly, canonical bottom-half stair occupancy is: outer = lower full slab plus one upper quarter; straight = lower full slab plus two upper quarters; inner = lower full slab plus three upper quarters. The `INVERT_Y` top maps invert these consistently: outer = upper full slab plus one lower quarter; straight = upper full slab plus two lower quarters; inner = upper full slab plus three lower quarters. Horizontal facing maps rotate the quarter sets without changing their counts. All coordinates remain the same normalized 0, 1/2 and 1 boundaries. The source definitions and 26.1.2→26.2 no-difference verdict are unchanged; this correction changes only the prior inner-shape occupancy description.