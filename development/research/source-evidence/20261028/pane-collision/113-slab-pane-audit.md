# Independent audit: 1.13 slab/pane outcome witness

## Verdict

**REVISE — outcome claims accepted within the stated solver-input bounds; correct one targeted source hash in `../slab-pane-1130/WITNESS.md`.** The 1.13.0 two-slab result is the positive gap `4.9999999918171056e-8`; 1.13.1 and 1.13.2 return `0.0`. The pane case returns the supplied `0.003125` in all three versions. I found no source-derived outcome or actor/query-gate error in these claims.

The witness's `IronBarsBlock.java` SHA-256 omits `ed` in the digest: it records `df6d956bb2745241e59294056f31513151b976dbd9ce3f5478fd72b66c68a0`, while all three exact source exports and their manifests contain `df6d956bb2745241e59294056f31513151b976dbd9ceed3f5478fd72b66c68a0`. This does not change the pane geometry or outcome, but the hash table should be corrected before relying on it as a complete identity record.

## Slab calculation

The input is admissible to the stated collision query: the original box overlaps both bottom slabs in Y and Z; its max X is `1.99999995`, and the positive X sweep by `2.0`, padded by `1.0E-7`, reaches both slab positions. The original-box exclusion cannot remove either slab because neither overlaps the original box in X. The cases assume loaded interior chunks and a border far away; those conditions avoid the collector's chunk-edge and world-border branches.

`SlabBlock` in all three source trees defaults `HALF` to BOTTOM and defines the bottom half box `(0,0,0)-(1,0.5,1)`. The named `stone_slab` registration resolves to that class in each release. The same Java double inputs produce `2.0 - 1.99999995 == 4.9999999918171056e-8`.

In 1.13.0, `WorldView.getBlockCollisions` accepts the two partial shapes, joins them into the accumulating shape, optimizes the result, and returns one shape. With X-ascending scan, the adjacent slab occupancy optimizes to a continuous X span from 2 to 4 at the actor's Y/Z cross-section. The axis helper checks its `1.0E-7` threshold against the incoming `d=2.0`, then clips to the first face at X=2 and returns the positive gap. There is no second outer-shape iteration to zero that clipped result.

In 1.13.1/1.13.2, the collector emits each accepted non-full shape separately; `BlockPos.Mutable.iterateRegionMutable` advances X first, so the x=2 slab is consumed before x=3. `calculateMaxOffset` clips the first shape to the same positive gap. Before consuming the second stream element, its strict `abs(maxDist) < 1.0E-7` test returns zero. The supplied `dy=dz=0` leaves the ordinary movement axis order's X result unchanged. This proves the bounded direct solver result, not a natural player-input/tick trajectory.

## Pane calculation

The `IronBarsBlock` constructor calls `PaneBlock` with `f=g=1`, producing the center post `[7,9]` in local X/Z; all four connection properties default false, selecting the post-only shape. For the stated player dimensions, `width / 2.0F` is `0.30000001192092896`; the supplied center yields `maxX=0.43749001192092896` and `maxZ=0.43750006192092894`. The actor is short of the post in X by `9.98807907104382e-6`, yet overlaps in Z by `6.192092893986256e-8` and overlaps vertically.

The collision query is admissible: its swept X interval reaches the post, while the collector's original-box exclusion does not overlap it because of the positive X gap. The query's `1.0E-7` expansion raises the swept max Z to `0.43750016192092894`, a `1.6192092894e-7` positive overlap with the post, so the collector retains it. At clipping time, each source's X-axis routine indexes orthogonal Z using `box.maxZ - 1.0E-7 = 0.43749996192092894`, below post min Z `0.4375`; the only indexed Z cell is empty for the post. Thus the post supplies no X clip and the result remains `0.003125`, whether it is part of the 1.13.0 aggregate or its own 1.13.1/1.13.2 stream element.

The player actor itself is a valid standing-sized box for the supplied position. The claim remains an equal-input collision-query/solver comparison: the witness does not show that normal input processing creates this position and displacement on a gameplay tick.

## Entity route and source integrity

The entity-collision statement is correct for these normal `World` queries: 1.13.0's `WorldView.getCollisions` joins entity collision boxes for a non-null actor; 1.13.1/1.13.2's `World.getCollisions` concatenates `getEntityCollisions` to the block stream. The pane/slab cases posit no other entities in the query, so neither route supplies another shape or changes stream order. The border conditions above keep border colliders out of the candidate set.

I independently checked the 1.13, 1.13.1, and 1.13.2 ready records, provenance records, source-manifest hashes, and targeted Java-file hashes against the exact `ornithe-feather` source exports under `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`. Version IDs and mapping IDs match 1.13/1.13.1/1.13.2 and `ornithe-feather`; the source manifest and every targeted source hash I compared match. The witness's ready/provenance/source-manifest digest values match those records. The only targeted-file digest discrepancy found is the `IronBarsBlock.java` typo above. Artifact mapping binaries were not independently reopened, so mapping identity remains the ready/provenance metadata identity rather than a fresh artifact-byte verification.

No product, candidate, backlog, wiki, or runtime implementation was inspected. No source was generated or edited; no build, test, or runtime check was run.
