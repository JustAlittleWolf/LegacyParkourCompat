# Review supplement: candidate `7eed19e9571c5008d34dcd344ccaf28ed2853cd8`

## Exact reviewed identity

- Commit: `7eed19e9571c5008d34dcd344ccaf28ed2853cd8`
- `EnumSwitchHelperRepair.java` SHA-256: `c5c47d157227ba0653f6b30948f06c82c43ef5e3a1f38062d8672af0ac19346a`
- `MinecraftDecompileEngine.java` SHA-256: `5608f9b4f1241e6e66f1d1429675c5301edb43d03eaef34fae6568fd49a3d261`
- Accepted allowlist audit report SHA-256: `6f95ff161384fd8c8e41b0219d0e5b656dd78615175fa76876dd18448bdee342`.
- Separate root report: test-disabled compile harness succeeded. This review ran no build, test, decompile, download, launch, or candidate edit.

## Re-review result

**Ready for the designated root proof generation.** The prior clean-cache blocker is fixed: the pre-remap gate now hashes only the pinned raw client, Tiny mappings, and Feather artifact; it verifies raw helper/owner shape before remapping. The ordinary mapped jar is produced into the baseline path, then its audited hash and all 59 diagnostic bindings are checked before the special remap proceeds. The old mapped cache file is no longer required.

I also rechecked the prior and original gates:

- The 41 helper rows have full owner paths. Pre-remap checks reject canonical destination collisions. The mapped baseline is hash-pinned, and the 59 diagnostic rows each require one matching owner method/helper binding and exclusive switch-helper ownership.
- Source output uses the canonical `1.8.1/ornithe-feather` namespace. Legacy output without a manifest is refused for this repair unless forced; pipeline identity remains part of provenance. Existing output reuse and forced generation follow the normal provenance and strict publication paths.
- Fresh and forced generation remaps both baseline and repaired jars after deleting old outputs. I found no stale special-remapped-jar reuse path.
- Comparator metadata matches the corrected contract: canonical `$<suffix>` class name, null `inner_name`, static+synthetic `InnerClasses` entries on helper and owner, and helper `EnclosingMethod(owner, null)`.
- The post-remap archive verifier enforces the 41 helper path moves, no other archive path-set changes, non-class entry byte equality, class-structure equality after normalizing only helper-name/metadata changes, and no lingering flat helper symbols.
- Existing Vineflower ERROR counting/abort, failed-method marker detection, required-source checks, provenance comparison/reproducibility, and publication/rollback gates remain unchanged. No heavyweight dependency was added.

The method descriptors are omitted from the hard-coded diagnostic table, but I accept that as a bounded static limitation here: the ordinary mapped archive is checked against the exact audited SHA-256, and each row must resolve to exactly one method that references its assigned helper, with no matching-binding ambiguity. I found no evidence in the accepted audit that this leaves a mismatch for the exact pinned input.

## Remaining structural caveat

The new custom-attribute guard checks `ClassNode`/field/method `attrs` after `ClassReader.accept(node, 0)`. ASM's default reader does not expose arbitrary unknown attribute payloads without prototypes; those attributes may therefore be skipped before this guard can inspect them. The verifier's `structuralBytes` normalization has the same limitation. This remains a proof limit for arbitrary custom attributes. It does not block the exact-input proof generation, but a successful generation should not be described as proving preservation of unknown attributes unless their absence on the pinned modified classes has separately been established or the verifier is made raw-attribute-aware.

The requested exact pinned inputs make diagnostic binding identity and normal mapped output reproducible; they do not remove this custom-attribute limitation. The accepted scope for this step is preparation proof, not movement parity.

## Generation handoff

The root owner may run the authorized exact `1.8.1` `ornithe-feather` candidate generation using the read-only pinned cache inputs and must retain the normal strict gates: zero Vineflower ERROR reports, no failed-method output, all required movement sources, provenance match, reproducibility check where applicable, and successful publication. A compile or generation result alone does not establish movement parity.
