# Review supplement: corrected 1.8.1 enum-helper candidate

## Identity

- Candidate: `b116f0935740bc3bd2d111011d0be2fedeeedc96`
- `EnumSwitchHelperRepair.java` SHA-256: `fcad422025d8d092c25183dea1598c397f072f89b8ba04fe18bb3df464d46726`
- `MinecraftDecompileEngine.java` SHA-256: `c38c211d7ac61c784ac2221c0a5e0d9b3a507e6be173572e9e52e9408b404095`
- Accepted allowlist audit report SHA-256 remains `6f95ff161384fd8c8e41b0219d0e5b656dd78615175fa76876dd18448bdee342`.
- This supplement reviews the corrected commit statically. No build, test, decompile, download, launch, or candidate modification was performed.

## Changes that close the prior findings

- All 41 rows now carry full `net/minecraft/...` owner paths; helper/owner names align with the allowlist evidence.
- `verifyIntermediaryJar` rejects a pre-existing canonical helper archive path before transformation.
- The candidate now defines 59 diagnostic rows, validates each expected helper binding against the mapped owner methods, checks the referenced array fields belong to that helper, and rejects multiple matching overloads.
- The ordinary remap output is checked against the audited mapped-client hash before the repair remap. The standard `1.8.1/ornithe-feather` source directory is restored. Legacy source trees without a manifest are refused unless `--force` is used.
- The corrected canonical metadata remains as required by the audit: `$<suffix>` identity, null `inner_name`, `ACC_STATIC | ACC_SYNTHETIC`, helper and owner `InnerClasses` rows, and helper-only null-method `EnclosingMethod`.

## Remaining blocker: ordinary mapped jar is required before the remap that creates it

At `MinecraftDecompileEngine.java:222`, `verifyPinnedInputs` hashes `versionCache/client-ornithe-feather.jar` before the scheduled `remaps` execute. The ordinary `client-ornithe-feather.jar` is a remap output, not an independently downloaded input. In a clean version cache it does not yet exist, so `DecompileStore.hash` fails before the task can run the ordinary remap at lines 237-238 that already has the correct post-generation hash check. This also blocks reuse of an existing valid output tree when paired with an empty cache.

Keep the raw-client/Tiny/mapping artifact gates before transformation; verify the ordinary mapped jar after generating the ordinary remap (the existing `verifyOrdinaryRemap` call does this). Do not require an old cache product as a precondition to creating that product. Until corrected, the candidate is not ready for clean-cache generation.

## Remaining proof limitations

1. **Diagnostic method identity is name-based, not descriptor-exact.** `Diagnostic` stores only the owner and method name. `verifyDiagnosticBindings` allows multiple methods with that name when exactly one references the expected helper (`matches.size() == 1`), so an overload is not itself rejected. The accepted audit describes exact failing-method bindings. Store and check the expected JVM descriptor (and, if field-level proof is required, the exact referenced switch-field set) for each diagnostic row, or reject any owner with more than one same-name method. This is a fail-closed proof gap even though the pinned mapped-jar hash narrows the current input.
2. **ASM normalization cannot prove preservation of unknown attributes.** The metadata transformer and structural comparator use `ClassReader.accept(ClassNode, 0)` without attribute prototypes followed by `ClassWriter`. ASM does not materialize unrecognized custom attributes without prototypes, so this verifier compares normalized known structures and may not detect their loss. The accepted contract requires preservation of unrelated attributes. For this bounded exact input, establish that none of the modified classes contain unknown attributes or use a verifier/transformer that preserves and compares their raw payloads.

## Other original gates rechecked

- Static inspection finds no changes to the existing ERROR counter/abort, failed-method scan, required-source checks, provenance comparison, deterministic source comparison, or publication/rollback path. Per-mapping pipeline identity is included in provenance.
- The previous source-output namespace concern is resolved by restoring the canonical Feather output path; the explicit no-manifest guard prevents stale legacy sources from being silently reused.
- The versioned repair jar is distinct from the ordinary mapped jar, and fresh/forced generation remaps it. The post-remap archive verifier checks helper path moves, normalized class structure, lingering flattened symbolic references, non-class archive payloads, and expected complete path-set changes.
- No new dependency was introduced. ASM/TinyRemapper API plausibility was reviewed statically; root's separate compile remains the compilation evidence.

## Decision

**Static review remains blocked on clean-cache generation** until the pre-remap ordinary-jar hash requirement is moved after its generation. The two proof limitations above should be closed or explicitly bounded before claiming the audit's full fail-closed and structural-preservation contract. These are source-preparation gates only; a passing compile or candidate generation does not establish movement parity.
