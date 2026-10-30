# Independent static review: 1.8.1 enum-helper consumer binding correction

## Decision

**Accept the correction for the specific pre-remap consumer-binding issue.** The candidate now locates `GETSTATIC` consumers across all class entries in the pinned archive, confirms every such field reference resolves to a declared helper `[I` field, and rejects an audited helper with no archive consumer. This handles the Banner case where the direct owner `BannerBlock` is not the actual consumer; the raw class is `asz`, and its mapped consumer `BannerBlock__Wall.updateShape` reads `BannerBlock__32351573.f_07650394:[I`.

## Candidate and root identities

- Candidate repository: `.native-candidates/181-enum-preparation-20261030`
- Candidate HEAD: `e629ff203a54f354c4b056263c8d3f2fd1a11494`
- Reviewed delta: `7eed19e..e629ff203a54f354c4b056263c8d3f2fd1a11494`
- Changed file: `buildSrc/src/main/java/legacyparkourcompat/minecraft/EnumSwitchHelperRepair.java`
- Product checkout HEAD: `6bc71443e990cd64f4917f0d442c00c1b75d3e44`; its implementation has the same all-archive consumer scan.
- Review is read-only: no candidate/product edits, build, test, decompile, source generation, launch, or download.

## Checks performed

- Read `buildSrc/README.md` and the full implementation workflow.
- Reviewed the complete candidate delta. The change replaces the direct-owner-only reference requirement with an archive-wide scan. The scan excludes the helper's own class, walks method instructions, selects `GETSTATIC` references to the selected helper, requires descriptor `[I` and a declared helper field name, and requires at least one consumer.
- Confirmed raw namespace selection: `verifyRawSourceBeforeRemap` maps each named flattened helper to its raw obfuscated helper name before calling `verifyIntermediaryJar`; the mapping/remap path supplies its intermediary or mapped source name. The scan uses the chosen class name consistently for archive path, field owner, and helper field lookup.
- Inspected the pinned raw 1.8.1 class `asz` with `javap -p -c`: it declares synthetic switch array `a:[I`, initializes it from enum `values()`/`ordinal()`, and catches `NoSuchFieldError`. Inspected mapped consumer bytecode for `BannerBlock__Wall.updateShape`, which reads `BannerBlock__32351573.f_07650394:[I`. This is the nested consumer the old direct-owner gate missed.
- Rechecked the immutable attribute inventory: the exact mapped client archive pin is `d2977e05623d692e2d73dacbd0d6328b9226f47d80f824711a6d5fe81df09001`; the raw attribute parser reports 2,500 classes, 70,588 attribute occurrences, and no unknown attribute names. This closes the earlier concern that the existing ASM structural comparison might discard custom attributes for these exact input bytes.
- The candidate diff leaves the 41-pair allowlist, 59 diagnostic table/binding verifier, pinned mapping namespace and hashes, canonical destination collision check, structural archive guards, and strict generation/publication checks unchanged. The previous allowlist audit recorded exact binding of all 59 diagnostic methods to their 41 helper classes. The new scan complements that method-level verifier by validating actual archive-wide helper field consumers.

## Residual limits

This approves the static tooling correction only. It does not establish that TinyRemapper emits the expected archive or that Vineflower generation clears all errors. The archive scan parses every non-helper class once per allowlist pair, which is extra work but does not appear to alter acceptance semantics. It should not count unrelated helper classes as a binding because it filters on the selected helper owner; a different `[I` field or descriptor owned by that helper fails closed. No caller is missed among ordinary bytecode field reads because the pass visits every method's instruction list in every `.class` entry. It does not validate method handles or non-`GETSTATIC` uses; the accepted binding contract is specifically switch-array `GETSTATIC` consumers.
