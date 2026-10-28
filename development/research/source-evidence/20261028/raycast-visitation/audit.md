# Rebind note: integer-origin ray traversal slice

## Verdict

**ACCEPT, bounded.** The supplied traversal-order follow-up correctly shows a native 26.2 provider query for cell `(9,64,32)` before cell `(10,64,32)` for the exact ray `(10.0,64.25,32.5) -> (12.5,64.25,32.5)`. The 1.8.9 path starts directly at X cell 10 and never queries cell 9 for those endpoints. For canonical 26.2 Anvil/Chest states in cell 9, the shape query misses and does not stop traversal.

The source-proven effect is the extra state/fluid reads and state outline-shape provider query. Native Anvil/Chest `getShape` returns static shapes selected from queried state, so this query itself does not mutate old-style shared bounds. A compatibility observer that deliberately applies an old `updateShape`-equivalent cache write on that modern provider query could write for cell 9 before the native clip miss; that conditional adapter consequence is **not established by these sources**. It does not require an early hit. This note does not modify or weaken the prior accepted 1.8.9 stale-bound movement audit.

## Independent math and provider check

- The supplied numbers follow canonical 26.2 `Mth.lerp(alpha,p0,p1) = p0 + alpha * (p1-p0)`: `fromX = 10 + (-1e-7)*(12.5-10) = 9.99999975`, and `toX = 12.5 + (-1e-7)*(10-12.5) = 12.50000025`. Their floors are 9 and 12. The shifted start fraction is `0.99999975`, so with positive X motion the first boundary step is into 10. With constant interior Y/Z, the resulting X visitation is 9, 10, 11, 12. The old `World.rayTrace` starts at `floor(10.0)=10` and then steps to 11 and 12. No axis tie occurs. The stated Y/Z interior coordinates bound this comparison; coordinates on other integer boundaries could also be affected by their own epsilon shifts.
- 26.2 `BlockGetter.clip` invokes the consumer for each visited position. It reads block and fluid state, obtains `context.getBlockShape`, clips it, then handles fluid shape; `ClipContext.Block.OUTLINE` maps to `BlockStateBase.getShape` and `Fluid.NONE` rejects all fluid states. Traversal returns immediately only when the consumer returns non-null. Thus cell 9 is a native provider query even though its Anvil/Chest clip returns null and traversal continues to cell 10.
- Canonical Anvil shape selection uses the queried state's facing and static shape map; canonical Chest shape selection uses queried `TYPE` and static shape maps. Their shapes end at or before local X=1 (world X=10 for the cell 9 query). `VoxelShape.clip` tests the original ray with the `from + 0.001*(to-from)` probe and falls back to AABB clipping here. The positive-X AABB clip tests the minimum-X plane; for these boxes that plane is behind the ray origin, and `AABB.clipPoint` requires a strictly positive scale. No hit is returned. These are shape-provider facts for these two concrete providers, not a universal claim about all blocks.
- No canonical modern call in this native clip path dispatches to old `Block.rayTrace -> updateShape`. The old `Block.rayTrace` writer and old Anvil/Chest overrides remain as recorded in the prior audit. Any observer that adds an old-style write to modern shape resolution is hypothetical at this source-only boundary.

## Source identity check

The ready, provenance, source-manifest, and artifact-manifest SHA-256 values in `traversal-order-followup.md` were recomputed from the canonical roots and match. Each selected source hash listed there was also recomputed and matches. Relevant identities include 1.8.9 `ornithe-feather`/Feather build.2 and 26.2 `unobfuscated`; the 26.2 provenance has null mapping artifacts. The canonical roots and file list are in the supplied follow-up note.

**Limits:** This accepts only the stated ray and the concrete canonical Anvil/Chest shapes. It establishes no compatibility adapter behavior, all-provider side-effect inventory, gameplay frequency, trajectory, or release boundary. No product/integration source or runtime behavior was inspected.
