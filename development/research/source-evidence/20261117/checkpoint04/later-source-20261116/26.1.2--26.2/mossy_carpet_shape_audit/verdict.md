# Independent audit: MossyCarpetBlock shape provider

## Verdict

**ACCEPT**, bounded to the exact provider operations and registration evidence in the report. No correction is required. This does not establish ordinary state reachability or pair-wide coverage.

## Immutable input

- Report: `build/later-source-20261116/26.1.2--26.2/mossy_carpet_shape/coverage.md`
- SHA-256: `13CD3AAE56458D4C8B5DDA2D569F344471D4CA81FB0336188E1FDBC5408E0018`

## Identity and hashes

Both endpoint ready markers, provenance files, source manifests, and artifact manifests match the report's recorded SHA-256 values. The ready markers identify versions 26.1.2 and 26.2, mapping `unobfuscated`, and bind the reported source and artifact manifest digests; provenance identifies the same versions and `unobfuscated` mapping option. Each of the five cited Java files at both endpoints hashes to the report value and corresponding `unobfuscated.sources.sha256` row: `MossyCarpetBlock.java`, `Blocks.java`, `Block.java`, `BlockBehaviour.java`, and `Shapes.java`.

## Reviewed claims

- Both `MossyCarpetBlock` implementations set the default `BASE=true` with all wall sides `NONE`. Their `makeShapes` state-map construction, outline lookup, and collision override are the same. In particular, collision returns the map shape for the default base-only state when `BASE` is true and `Shapes.empty()` otherwise; it does not use the queried state's wall-side outline pieces.
- Neither class overrides `getBlockSupportShape`; the inherited provider delegates to `getCollisionShape` with an empty context. `BlockBehaviour.BlockState` forwards the support-shape query to that provider in both sources.
- The cited `Block.boxZ` overloads and `Shapes.rotateHorizontal`/`rotateAll` helpers preserve the same operation bodies and order for this construction.
- `Blocks.PALE_MOSS_CARPET` selects `MossyCarpetBlock::new` at both endpoints and retains the cited shape-relevant property chain. The registration key representation change does not affect these provider operations.
- The only textual difference in `MossyCarpetBlock.java` is the placement of the `@Block.UpdateFlags` annotation in the unrelated `placeAt` parameter list; the scoped provider methods match.

The report correctly limits its conclusion: no ordinary placement/state reachability, data-driven population, collision-consumer trajectory, runtime behavior, or complete pair inventory is claimed.
