# Independent static review: 1.8.1 enum-helper ASM overlay

## Decision

**ACCEPT for static mapping/overlay design and preservation gates at the reviewed revision**, subject to the coordinator’s already-noted compile correction at `if (referencesHelper || ...)` to index the boolean array (`referencesHelper[0]`). This review does not assert the exact checked-in candidate compiles, that the overlay was generated successfully, or that Vineflower reaches zero errors.

- Candidate: `.native-candidates/181-enum-preparation-20261030`
- Exact reviewed commit: `16f80e8cacf7f795ae0e0b79ee5c9f98aec6b28a`
- Reviewed delta: `80f8339..16f80e8cacf7f795ae0e0b79ee5c9f98aec6b28a`
- Files: `buildSrc/src/main/java/legacyparkourcompat/minecraft/EnumSwitchHelperRepair.java`, `buildSrc/src/main/java/legacyparkourcompat/minecraft/MinecraftDecompileEngine.java`, `buildSrc/README.md`
- Static review only. No build, tests, decompile, transformation, game launch, cache/source mutation, or download was run.

## Checks and findings

- Read the full `buildSrc/README.md` and `development/workflows/implementation/WORKFLOW.md`.
- The 41 pairs remain a fixed exact allowlist. The overlay is reachable only through the existing exact `1.8.1` + `ornithe-feather` branch; the mapping artifact/raw client/Tiny hashes and ordinary mapped archive content pin remain enforced. `verifyAuditMappings` uses the existing mapping provider and still requires all 41 audited rows plus the mapping artifact gate. The prior accepted allowlist audit records row-by-row consistency across raw, Tiny, mapped and 1.8.2 comparator identities.
- The 59 diagnostic bindings and their verifier are unchanged. The ordinary archive content and diagnostic checks run before overlay. The prior independent binding/allowlist audits verify the 59 log bindings against the 41 helpers.
- The overlay walks the ordinary baseline archive. It moves only each allowlisted flat helper entry to its canonical `$` path. `ClassRemapper` maps the helper’s ASM class-name references (class identity, descriptors/signatures, annotations, handles/constant-dynamic and instruction type/member operands covered by ASM remapping); ordinary string constants are not mapped. No arbitrary string rewriting is introduced. `mapInnerClassName` leaves unrelated names untouched and returns `null` for an allowlisted helper, matching the accepted 1.8.2 comparator’s null `inner_name_index` shape.
- For each allowlisted helper and owner, `metadata` adds exactly the canonical helper/owner `InnerClasses` relation with null inner name and `ACC_STATIC | ACC_SYNTHETIC`. For the helper it adds `EnclosingMethod(owner, null, null)`, rejects conflicting existing nesting, requires the helper’s synthetic flag, and restores its `SourceFile` from the ordinary baseline. This matches the accepted comparator contract. Custom ASM attributes fail closed here; prior immutable raw-attribute inventory for the pinned ordinary archive found no unknown attributes.
- `verifyArchiveDelta` enforces exactly the 41 flat-path removals and canonical-path additions; compares normalized helper and all overlapping class structure after reversing canonical identities and removing only the approved nesting attributes; verifies each helper/owner metadata record; rejects lingering flat symbolic class references; and byte-compares every non-class entry. ASM tree visitation preserves field/method list order, access flags, instructions and operands other than mapped type/member references; the normalized structural comparison covers class identity, fields, methods, code, and attributes. `SourceFile` is explicitly restored to avoid the prior second-remap debug-name delta. The overlay does not rename ordinary string constants.
- Engine changes replace the second TinyRemapper invocation with a verification-only mapping load and the class overlay. The normal ordinary remap still uses `renameInvalidLocals(true)`. The 59 ERROR counter/abort, failed-method scan, required-source checks, provenance/reproducibility gate, staging cleanup and atomic publication are unchanged in this delta. README accurately describes the overlay and its preservation intent.

## Limitation

The candidate source at this exact commit has the coordinator-identified Java compile typo: `referencesHelper` is a `boolean[]` and the condition uses it directly. The coordinator stated it will correct this during application; this is recorded separately from the static source-fidelity decision above. The overlay’s output and zero-ERROR Vineflower result remain unverified and must only be established by the designated authorized proof owner through the existing strict pipeline.
