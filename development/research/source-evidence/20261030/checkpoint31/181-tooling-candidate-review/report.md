# Independent tooling review: 1.8.1 enum-helper repair candidate

## Scope and identity

- Candidate repository: `.native-candidates/181-enum-preparation-20261030`
- Baseline: `69a81b4`
- Candidate: `8029d8ad65f4bdf66da57ab13eef358bb9bf91a7`
- Accepted source/bytecode audit: `.native-source/20261030/181-enum-helper-allowlist-audit/report.md`
- Accepted audit SHA-256 verified locally: `6f95ff161384fd8c8e41b0219d0e5b656dd78615175fa76876dd18448bdee342`
- Review is static and read-only. No build, test, decompile, launch, download, or candidate edit was performed.

## Decision

**Do not accept the candidate for generation yet.** The remap and comparator-metadata design follows the accepted contract in several important respects: it pins the raw client, Tiny file, and Feather artifact; overlays the 41 helper names before the post-remap metadata pass; uses canonical `$<suffix>` identities; adds null-name `InnerClasses` entries with `0x1008`; adds helper `EnclosingMethod(owner, null)`; compares the remapped archive against an ordinary remap; and keeps the strict decompiler/provenance/publication gates in the existing engine path.

Two required fail-closed checks from the audit are missing before transformation:

1. **No canonical-destination collision gate.** `verifyIntermediaryJar` reads each helper and owner but never checks that `pair.canonical + ".class"` is absent from the input archive. A pre-existing target class can therefore reach TinyRemapper without the collision rejection required by the audit. Add an explicit absence check for every canonical destination before remapping.
2. **No diagnostic-to-helper method binding gate.** The audit binds all 59 Vineflower diagnostics to their exact owner method and assigned helper; it verifies each failing method has `GETSTATIC` references to that helper's `[I` field(s), with the per-method owner set containing only that helper. The candidate only scans the owner class for *any* `GETSTATIC` of the helper array. It does not validate the 59 methods, their descriptors/field sets, or exclusivity of the helper-owner set, so changed or mismatched input structure could pass the current gate. Add a verifier for the audited method/field binding data, or otherwise make the same exact binding checks executable before the remap.

The metadata and structural preservation verifier is substantial, but these pre-remap checks are explicit acceptance conditions in the source audit and cannot be inferred from the fixed hashes alone.

## Static checks completed

- Compared both changed Java files against the requested baseline and read the full existing engine reuse, provenance, remapping, decompilation, source-completeness, and publication path.
- Confirmed the allowed mapping namespace remains `ornithe-feather` for mapping lookup and diagnostics; the repaired output uses the distinct folder `ornithe-feather-enum-helper-v1`, with a matching unique mapped-jar cache name. This is a sensible way to avoid reusing the old published tree and old remapped jar. Before integration, document that versioned output-folder exception because normal source consumers expect `<version>/ornithe-feather`.
- The special pipeline identifier is added to per-mapping provenance. Existing generated output is checked and returned before remap tasks are run; a forced or missing-output generation executes the remap tasks, and `remap` deletes each target before writing. I found no path that reuses a stale special mapped jar for a fresh generation.
- The metadata pass requests the corrected comparator shape: canonical binary name, null `inner_name`, static+synthetic flags, owner and helper records, and helper-only null-method `EnclosingMethod`.
- The archive verifier checks exact helper path moves, reverse-normalized class structure, non-class entry byte preservation, class-entry structural preservation, and absence of lingering flat helper symbols through ASM's class remapper visitor.
- Existing ERROR counting, failed-method marker scan, required movement-source checks, provenance comparison, reproducibility check, and atomic publication path remain in place. No heavy dependency was introduced.
- ASM and TinyRemapper API usage appears plausible from the project’s existing compile-time dependency surface, but this is not compile-verified.

## Limits and generation requirements

This review cannot establish that TinyRemapper produces the expected archive, that Vineflower emits zero errors, or that a generation passes the unchanged strict gates. The designated generation owner must close both fail-closed checks, then run the authorized candidate-generation flow with the exact audited `1.8.1` `ornithe-feather` inputs and verify zero ERROR reports, no failed-method marker, required movement sources, provenance, and publication. Do not treat static review or compilation as movement parity evidence.
