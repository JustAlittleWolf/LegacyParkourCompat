# 1.8.1 enum-helper SourceFile repair review

## Decision

**ACCEPT for the scoped SourceFile correction at product commit `73b4e35ec2e84400d8fc9b907ac4796b3eb7ceff`.** The change restores each renamed helper's `SourceFile` value from that helper's audited flat-class entry in the pinned baseline archive. It does not relax or normalize away the full archive verifier's attribute checks.

## Review

- Read `buildSrc/README.md` and the complete implementation workflow.
- Reviewed the full commit diff and the 41-entry `PAIRS` application path in `EnumSwitchHelperRepair.addComparatorMetadata` / `metadata`.
- `addComparatorMetadata` opens `baselineJar` and passes it to `metadata` for all selected canonical-helper and owner classes. For each canonical helper, `metadata` reads `pair.flat` from the baseline through `read`, then copies that class node's `sourceFile` onto the renamed helper. `read` fails closed if the baseline entry is missing or class identity does not match. This therefore applies uniformly to all 41 helper pairs. Owners do not enter this branch and their `SourceFile` value is not reassigned.
- TinyRemapper's generated helper SourceFile is the known source of the earlier Banner failure (`BannerBlock__32351573.java` became `BannerBlock.java`). Restoring from the baseline flat helper is the appropriate audited value, rather than synthesizing a filename from the new identity.
- The patch changes no input hashes, 41-pair allowlist, consumer-binding scan, 59 diagnostic binding checks, custom-attribute gate, archive path/content gate, or strict full normalized archive-delta verifier. That verifier still compares normalized ASM structures, including `SourceFile`; it is not weakened to ignore this or other attributes.
- Prior raw-attribute audit found only standard JVM attributes across the pinned mapped archive (2,500 classes; no unknown/custom names), which supports the existing ClassNode parse/write path for these fixed inputs.

## Remaining verification issue

The coordinator reports that the latest proof passed the helper structural check after this patch but then failed on unrelated `GlStateManager__PolygonOffset` normalized structure: TinyRemapper's `renameInvalidLocals(true)` altered constructor debug parameter names in `LocalVariableTable` / `MethodParameters` (`glStateManager__48070952` to `arg`). This is outside the SourceFile correction, but means the full archive proof is not currently green and the repaired artifact as a whole is not accepted. Keep the strict verifier intact and resolve that preservation issue before claiming end-to-end archive acceptance.

## Scope and limits

Static code review only. No build, test, source generation, decompilation, game launch, download, or cache mutation was performed. The SourceFile patch itself is accepted; overall 1.8.1 proof status remains blocked by the separate debug-attribute mismatch reported above.
