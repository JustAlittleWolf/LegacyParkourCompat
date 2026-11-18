# Independent audit verdict — 26.1.2 → 26.2 slab/stair providers

## Bound inputs

- Exact author payload: slab-stair-comparison.md, SHA-256 4f95e15278e3a55363e851a73c232ec77e5d12be7820f6a701dc2fb1b0fa2dac. The local raw file digest matches the authorized value.
- Pre-claim independent source roster: source-roster.md, SHA-256 0a5e9ca4233749b24b9d9262e19db3db9ee5ee68b481406851a398f11418673b.
- Roster digest correction: source-roster-erratum.md, SHA-256 06f4859b3c8188b3b4eba4e986aaef755796aa1c8de70a3f591ab4b5807d3d42.
- Pre-claim independent matrix: source-matrix.md, SHA-256 326e0f0556802fc8647162d40f1b71ea21166f5964f793ae9810ce6794b91651.
- Exact source manifest SHA-256 values: 26.1.2 54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0; 26.2 a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894. Ready and provenance markers are also hash-bound in the frozen roster.

The pre-claim roster contains one copied digest error for 26.1.2 SlabBlock; it is preserved as frozen and corrected by the separate erratum. Rechecking all other listed Java member digests against exact source bytes and manifest entries found no further errors.

## Family verdicts

| Family/slice | Verdict | Basis and caveat |
|---|---|---|
| SlabBlock dry TYPE→shape | Accept bounded no-difference | Both providers select the same TOP, BOTTOM and DOUBLE shapes. Exact getShape body is the same at both endpoints. TYPE is the sole selector; waterlogging, level, position and context are not read. No positive movement-shape delta. |
| StairBlock dry FACING×HALF×SHAPE→shape | Accept bounded no-difference; correct one geometry statement | Both StairBlock.java files are byte-identical. All 4 facings × 2 halves × 5 shape values select the same cached shape maps and facing key. However, the author's canonical INNER description says the union covers all four upper quarters. That is incorrect: SHAPE_INNER = SHAPE_STRAIGHT ∪ rotate90(SHAPE_STRAIGHT) covers three upper quarters, because the two two-quarter straight sets overlap in one quarter. Its lower half remains full. This correction does not change the endpoint no-difference verdict. |
| Registered built-in variants | Accept inheritance coverage | Exact source scan finds only WeatheringCopperSlabBlock extends SlabBlock and WeatheringCopperStairBlock extends StairBlock; the pair files are byte-identical and neither overrides getShape. Blocks.java registers these concrete subclasses for copper families at both endpoints; the 26.2 factory refactor still creates the same subclasses. No additional subclass of either provider exists in either source root. |
| Collision-shape cache/context dependency | Accept bounded independence | BlockBehaviour caches only non-dynamic shapes; cached collision shape is built with empty getter, zero position and empty context. Slab/stair getShape reads only state properties, so those omitted inputs cannot change these provider outputs. Stair adjacency is represented in stored SHAPE, computed during state placement/update; shape retrieval does not query neighbors. |

## Author claim disposition

The bounded no-difference conclusion is supported for both families. The report is not accepted exactly as written because its inner-stair occupied-set description is materially wrong. Revise that sentence to three upper quarters (not four). The report's source table hashes for SlabBlock and Block.java match the exact source manifests; the copied SlabBlock hash error is confined to the independent roster erratum above.

This audit closes only the specified stationary slab/stair provider families for this exact endpoint pair. It does not freeze full-pair discovery, verify intermediate release boundaries, or establish runtime parity. No product, wiki, backlog, build, test, runtime, or Git work was performed.

Encoding: UTF-8 without BOM, LF line endings.