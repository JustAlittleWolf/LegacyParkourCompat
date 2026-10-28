# Repeated voxel offsets: corrected two-stone witness

The primitive difference remains: 1.12.2 Ornithe Feather `net.minecraft.util.math.Box.intersectX` has no small-distance cutoff; 1.13.2 `net.minecraft.block.shape.VoxelShapes.calculateMaxOffset` tests `abs(maxDist) < 1e-7` before processing each next shape. This alone does not establish a player movement difference for arbitrary blocks.

The original two-STONE witness is disproved for the stated historical pair. With maxX about 1.99999995, requested positive X displacement 2, two full stone cubes at (2,64,0) and (3,64,0), strict Y/Z overlap, loaded blocks, and no other entity or border colliders, both 1.12.2 and 1.13.2 retain the approximately 5e-8 gap. In 1.13.2, `WorldView.getBlockCollisions` aggregates the full cubes into one final `IntVoxelShape`; there is no later shape to trigger the between-shapes cutoff. The original claim that each stone independently enters the stream was false.

Under the same bounded collision neighborhood, 26.2 emits each full cube separately through `BlockCollisions`; `Shapes.collide` therefore zeros the residual before the second cube. This establishes the supplied three-endpoint source consequence only. It does not establish the intermediate cutover or an ordinary input-only trajectory.

Non-full components can remain separate in the historical collector. A bottom-slab replacement witness has separate accepted endpoint evidence, with exact alias/collector applicability still being closed; it must not be inferred from this rejected stone witness.

Exact source references, hashes, independent audit and corrected cache-root qualification are retained under `development/research/source-evidence/20261028/voxel-repeat-correction/`. The original draft remains in Git history. This correction does not claim full collision representation coverage, implementation completion or runtime parity.
