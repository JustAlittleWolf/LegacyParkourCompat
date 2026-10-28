# Independent review: 1.13 slab collector contracts

Verdict: **ACCEPT, source claims only, within the stated gates.**

## Bound inputs

The review checked these exact report revisions:

- `slab-1130-adapter-source-closure/report.md` SHA-256 `C605795686D61FBF3E6522DEE7C1E65F0741C4EAE4B010E828F0059360CDCEC7`; source manifest `sources.sha256` SHA-256 `FBC3F9E66D50846C7FC0EC70CB60D4420FC9FA039F0B41A69C18428D1E77F1B1`.
- `slab-113-query-adapter-gates/report.md` SHA-256 `26DDD2F5F9B4F4E8507291A4DB46E0341D6F855281664C5694173F8AB5B69608`; source manifest `source-hashes.sha256` SHA-256 `75E26E864040B755373A120DEA39EC226A68171FE5C14323040E3C2D9DEF8131`.
- `slab-113-stream-general-equivalence/report.md` SHA-256 `469C59BE178D594E2435A475C83358E16353A46781C33509B4EA0F0DA0DD465A`; source manifest `canonical-source-hashes.sha256` SHA-256 `CB711FDDF5FF6E458CC303D7009C9E289277640A35FE2C114BA728BE95BFC6D1`.
- Supporting accepted evidence: collector contract `report.md` SHA-256 `1CEA806788ECE1C7371087D1B160743772FC5FC382452ACF70FDE727D6194E40`, correction supplement SHA-256 `945D71DACEF1B4DF18580A57CC7F1784F7FFE80AC76B72DFB76624EDE2ECC3D5`, union/math audit SHA-256 `3766DF4C488615A8A6608F2AC23A3B34E05806D656387DD7A7E4803B5B6AF6A9`, slab alias closure SHA-256 `58CF22BD0E2EFEADD62EBBB678B7F9EAC8F957BAE1D3B087B68EA17E6D3BC30C`, and independent alias review SHA-256 `B39C4C76A211E9389F30403776F5F31959AE94C6535E3E45FE30C6EA09691935`.

## Source verification

All 13, 18, and 27 entries respectively in the three new canonical source manifests were recomputed from the read-only roots and matched. Their ready markers identify 1.13 / 1.13.1 / 1.13.2 as `ornithe-feather` and 26.2 as `unobfuscated`. The two 1.13.1/.2 `WorldAccess.java` files are byte-identical (SHA-256 `FC75177D629EF8D0D491821FE557E21606E4CB381164F1CA41B2763073F854BE`); each extends `WorldView` and has no `getCollisions` override. This verifies the inheritance supplement's binding.

## Claims verified

- In 1.13.0, the ordinary movement call builds start, shifted exclusion, and epsilon-expanded sweep shapes from the actual box and adjusted movement vector. It invokes the block collector with `onlyFirst=false`. The collector scans X outer, Z middle, Y inner; tests loadedness once per X/Z at Y=64; skips the described perimeter positions; and branches on canonical full-shape identity before the partial-shape overlap tests. Consequently, canonical full slabs enter the `BitSetVoxelSet` without either exclusion or sweep-overlap filtering, while partial shapes are translated, exclusion-tested, sweep-tested, and OR-joined by Y, then Z, then X. The partial union is optimized before the final OR with the full-cell integer shape.
- In 1.13.1/.2, the inherited block provider instead emits ordered partial shapes and appends one filtered integer-shape tail for canonical full cells. The stream collector checks the starting-box exclusion before its full-cell identity branch. The concrete `World` composition is blocks then entities; the correction accurately limits the API inheritance claim through `WorldAccess`.
- In 26.2, `BlockCollisions` yields only translated shapes, with no position/state/raw-full identity in the consumer's `Iterable<VoxelShape>`. It applies the modern AABB-intersection filtering and emits full cells at cursor position. `Cursor3D` order and boundary scan are not the old collector's order/range. Thus the plain modern list cannot reconstruct the old full-cell halo, provenance or grouping. The proposed direct provider scan needs original query context and must reapply the old envelope, exclusion, overlap, loading and grouping rules.
- `Shapes.collide` in 26.2 applies the strict `abs(distance) < 1e-7` outer gate before requesting each next shape. The old per-shape axis operation has the corresponding strict gate for matching voxel coordinates. The accepted slab closure justifies geometry/state mapping only for the 19 old slab keys and their six property combinations. The old/new scan or stream representation difference is verified; a general different coordinate result is not inferred. The existing numerical witness remains bounded to its reviewed arrangement.

## Applicability limits

These reports establish a source-supported adapter domain for arbitrary arrangements of the 19 mapped slabs, subject to explicit producer checks. They do not establish that an existing product hook captures the query or all positions. Exact old/current query-shape conversion and loaded-column/current chunk-availability equivalence remain implementation gates. The product must use native behavior when those gates, the block-only/no-border/no-step-up route, or the mapped nonempty-state restriction cannot be proven. The 1.13.1/.2 partial-order statement is conditional on matching query and exclusion filtering; it does not assert that the unmodified 26.2 collector itself has those old filters. No runtime applicability or movement-parity claim is accepted here.

No product code, build, tests or runtime were used in this review.
