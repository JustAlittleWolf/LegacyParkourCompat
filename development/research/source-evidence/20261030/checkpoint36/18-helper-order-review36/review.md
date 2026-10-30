# Static build tooling review: 1.8 Feather enum-helper order

## Decision

**ACCEPT — no functional findings.** Reviewed the three-file working diff against `33d1f18d`: `buildSrc/README.md`, `EnumSwitchHelperRepair.java`, and `MinecraftDecompileEngine.java`. This was a static review only; no source files, cache archives, decompile outputs, builds, or tests were changed or run by this reviewer.

## Review evidence

- The new 1.8 path is narrowly selected by exact version `1.8` and `ornithe-feather` mapping label (`MinecraftDecompileEngine.java:221-223,285-291`). It checks the raw client, Feather artifact, and Tiny file against distinct 1.8 SHA-256 constants and records a pipeline ID containing those pins plus the canonical mapped-content digest (`EnumSwitchHelperRepair.java:34-40,218-230`). The referenced 1.8 cache inputs independently match the pinned values.
- The cached ordinary remap remains at `client-ornithe-feather.jar`; the reordered jar has a distinct `client-ornithe-feather-18-enum-helper-order-v1.jar` name. The separate `pipeline:18-enum-helper-order` manifest input prevents 1.8/1.8.1 cache/provenance identity collision. Existing 1.8.1 names and its `PIPELINE_ID` remain intact.
- The order operation is deferred until after ordinary remapping and runs on the ordinary mapped jar, so it composes after either mapping branch without changing the mappings or classpath (`MinecraftDecompileEngine.java:234-291`). The pinned Feather Tiny header is `official / intermediary / named`; the selected 1.8 mapping follows the existing official-to-named branch. The existing 1.8.1 official and intermediary-bridge branches are unchanged and still call the original default-hash overload.
- The reorder validates the expected canonical digest before and after; it rejects duplicate entry paths, mismatched class identities, nested/enclosing-class metadata, absent helper/dependency graphs, undeclared helper-field reads, and cycles/incomplete topological output. It changes only which class entry occupies each class slot. A second path-set and uncompressed-byte comparison verifies content identity (`EnumSwitchHelperRepair.java:246-267,295-333,353-430,432-455`).
- The required strict stages remain after the reorder: Vineflower ERRORs fail decompilation, then failed-method markers and movement-source requirements are checked, source provenance is generated and compared, and publication is staged (`MinecraftDecompileEngine.java:322-348`, existing `decompileJar` strict error check). The legacy-source refusal was extended to 1.8 Feather alongside 1.8.1 (`:295-300`).
- Reviewed `.native-artifacts/18-mapped-attributes35.json`: 2,497 classes, 70,178 attributes, and no attributes beyond `Code`, `ConstantValue`, `LineNumberTable`, `LocalVariableTable`, `MethodParameters`, `SourceFile`, and `StackMapTable`. This supports the documented flat-class guard for the pinned archive.
- Read the existing `decompile-18-proof36.log` record: it ends in `BUILD SUCCESSFUL` / `Finished 1.8 using ornithe-feather`; it contains no Vineflower ERROR lines or failed-method marker lines. This is recorded evidence from the preparation run, not a run performed for this review.

## Limits

No independent archive transform, source-hash comparison, or runtime/parity check was performed. The reviewed proof log documents strict decompile completion, not movement parity. `git diff --check` reported CR-at-EOL whitespace on added `MinecraftDecompileEngine.java` lines; the working tree reports LF/CRLF normalization warnings, so this appears to be line-ending handling rather than trailing source spaces. No functional blocker was found.
