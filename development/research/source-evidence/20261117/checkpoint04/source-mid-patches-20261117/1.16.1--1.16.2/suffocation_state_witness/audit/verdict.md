# Independent audit — 1.16.1 → 1.16.2 suffocation state witness

Verdict: **ACCEPT** the bounded static vanilla-state witness in `suffocation-escape-witness.md` (SHA-256 `7DE358A9A00D94485900903310BED28B40B0EE3FACB08BBD47A0167AB436D09A`). This acceptance is supplemental to the previously accepted source-code path claim and applies only to this concrete state witness. It does not freeze the pair or close the rest of the dependency inventory.

## Identity and binding

The witness packet itself hashes to the declared value. Canonical roots are `decompiled_minecraft/1.16.1/mojmap/` and `decompiled_minecraft/1.16.2/mojmap/`. On disk, the source-manifest SHA-256 values are respectively `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754` and `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7`; provenance SHA-256 values are `a6ff065188a400ec891980cdf2e6c0c238bf5e05168a2f69b29c6cecc2214b98` and `a9b166406064e877751542fe7df27acc32be3843f6fdc2280dac32ce0b54207c`. Ready records confirm `versionId` and Mojmap namespace at each endpoint and bind the same source-manifest digests.

I independently recomputed every Java SHA listed in the witness table and matched each to the exact endpoint manifest row (19 source-file references total). No cited hash mismatch was found. The referenced files and members were read at the claimed source roots.

## Witness checks

- `Player.STANDING_DIMENSIONS` is 0.6×1.8 in both releases. `EntityDimensions.makeBoundingBox` places entity y at AABB minY; at the stated center/position the box occupies approximately x/z `[0.2,0.8]`, y `[4.8,6.6]`. The four `aiStep` probe coordinates floor to the farmland column.
- In 1.16.1, `FARMLAND` is built from `Material.DIRT` and only overrides `isViewBlocking`. The default `isSuffocating` predicate requires motion-blocking material plus a full collision cube. `FarmBlock` supplies a 16×15×16 shape, so the default predicate is false. `checkInBlock` calls `blocked`; its vertical integer-slice checks therefore find no suffocating farmland and make no velocity write.
- In 1.16.2, the same farmland registration adds `.isSuffocating(Blocks::always)`. `suffocatesAt` queries the deflated block-column box across the player’s y interval; `CollisionGetter.noBlockCollision` and `CollisionSpliterator` apply the supplied suffocation predicate to collision shapes. The query intersects the 15/16-high farmland collision shape by about 0.1375 blocks, so the current-column checks are suffocating while the four adjacent columns are air.
- Recomputed probe geometry confirms the strict ordered distance comparisons choose west for probes one and two, then east for probes three and four. The final call writes x velocity `+0.1`; with initial horizontal velocity `(0,0)`, the old output remains `(0,0)` and the new output is `(0.1,0)`.
- The map-state witness is source-reachable by placing farmland at `(0,4,0)` with air above and teleporting an ordinary standing player to `(0.5,4.8,0.5)`. `FarmBlock.canSurvive` permits air above. The cited command, server teleport, and client position handlers support the stated vanilla command-mediated setup. This establishes a legal static state, not self-propelled entry into the collision shape.

## Citation note and limits

The report's `Block.java` row identifies `box` as “near 131”; the canonical 1.16.1 source places `Block.box` at line 119. The path, file hash, and `Block.box` shape-scaling content are correct, and the 15/16 geometry is independently explicit in `FarmBlock.SHAPE`. This line-location discrepancy is non-substantive and does not change this bounded acceptance; use line 119 in any later citation binding.

The verdict does not establish world-generation frequency, self-propelled arrival, behavior for other blocks, datapack overrides, runtime parity, complete pair coverage, or pair freeze. The source report and prior source audit remain bound to their original immutable hashes.