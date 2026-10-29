# DDA first-hit adapter gate inventory

Snapshot: 2026-10-29. This source-only synthesis groups the accepted 1.8.9 camera-ray evidence and current 26.2 provider evidence into four separate adapter gates. It is not a whole-operation acceptance or movement-parity claim. No product source, runtime, tests, or build were used for this report.

## Decision

The evidence supports designing a transactional, fail-closed adapter contract, but it does not yet support enabling a useful legacy ray path in ordinary sessions. Historical registry identity coverage is now broad and explicit; current state/provider translation and lifecycle-wide cache validity remain open independently. A complete old-ID roster cannot substitute for either gate.

## A. Historical ID, metadata, and opaque classification

The three first-hit crosswalk partitions enumerate every registered old numeric ID 0–197 once. The 0–63 table records all 16 metadata values for each ID. The 64–131 table records all 16 metadata values for IDs 64–127; it lists IDs 128–131 from the higher-ID registration table without inventing metadata rows. The 132–197 partition is registration-level. Thus registration identity coverage is complete, but these artifacts do not constitute a full decoded metadata/state domain for IDs 128–197.

Separately, the old opaque-neighbor roster has one row for every ID 0–197 and resolves `opaqueCube` for all 198 constructed block singletons: 72 true, 126 false, none unresolved. That closes the historical `isOpaque()` classification used by the ID 101 pane admission rule. It does not turn “opaque” into a modern full-cube/sturdy-face classification or establish current-state aliases.

The row artifacts preserve exact old registration expressions and distinguish proven family closures from candidate current keys. They explicitly mark unsupported or unreviewed state projections OPEN. Do not treat all old states as translated merely because every ID has a row.

## B. Current-state, provider, neighbor, and block-entity translation

The accepted writer matrix is an index of 36 mutable-bounds provider classes plus the supplemental RedstoneWire row, with source reports independently accepted at family-specific scope. Its 147 referenced artifacts are hash-bound. The crosswalks reuse that matrix and pin exact current source files, but retain candidate keys where mapping is not proven. Even a closed alias/provider family does not automatically close its contextual inputs.

The remaining source-to-adapter requirement is a lossless conversion for every camera-eligible cell the supported operation may visit: exact old ID and decoded properties, current key/state projection, per-state `canRayTrace` eligibility, correct virtual/custom provider dispatch, constructor and query-time bounds, and any neighbor, paired-cell, block-entity, or chunk input read by that provider. The old opaque roster closes only ID 101’s old opaque predicate. Its current `IronBarsBlock` predicate remains different and must be handled through an explicit old-rule adapter or an appropriately narrower supported input domain.

The accepted matrix marks provider/state/cache-consumer distinctions per family. Accepted direct ray-writer-to-ordinary-movement-cache consumer evidence currently includes Anvil ID 145, Chest ID 54, and Trapped Chest ID 146. This is a lower bound on known consumers, not a statement that other ray writers cannot affect movement. Other families are either bounded exclusions for their documented path or remain open. In particular, ID 101 neighbor outcomes and ID 180 stair neighbor inputs are only bounded; neither closes global cell translation.

## C. Lifecycle-wide mutable-bounds writers and callers

The camera caller contract is closed for its flags and first-hit semantics: the old visitor applies its eligibility gate, invokes the selected provider in visitation order, retains provider writes even when the resulting clip misses, and stops at the first non-null hit. The generic provider is writer-then-clip with virtual `updateShape`; stairs have a custom internal sub-box loop, and rail, door, and trapdoor paths have repeated update dispatch. These facts require ordered provider execution, not a modern outline-shape substitution.

The writer matrix is not a lifecycle-complete census of every old caller that may mutate the shared registry-object bounds or every later player movement consumer. Known Anvil/Chest/Trapped-Chest consumers prove that ray-path writes can become movement-visible. The rest of the matrix distinguishes proven null/empty, refreshed, state-derived, or bypassed cases from open cache-consumer cases; those bounded exclusions cannot be generalized to unreviewed writers or callers. In particular, the out-of-camera caller inventory and whole-lifecycle write ordering are still gates. Do not assume old non-player ray callers are absent just because non-player physics is outside product scope: a non-player call may still write shared bounds subsequently read by player movement.

Accordingly, the current evidence does not establish that a cache mirror can be seeded once and remain valid through an ordinary session. A bounded adapter needs either complete relevant writer/caller observation or a source-defined invalidation policy that disables the mirror after any unobserved mutation. No claim of such lifecycle closure is made here.

## D. Transactional fallback, invalidation, and recovery

The safe operation contract from the accepted reconciliation is:

1. Seed historical bounds from exact constructor defaults before any relevant query, and mark the mirror valid only at that point.
2. Execute traversal and providers as one staged operation, preserving exact visited-cell order, old eligibility, state/metadata, provider-specific writes, misses, custom provider behavior, and first-hit stop.
3. Resolve every visited cell and every provider neighbor/paired/block-entity input through a closed mapping. Do not commit a prefix if a later cell is unsupported.
4. Commit staged cache writes and result only when the whole operation is supported. Otherwise discard the staged result, use native behavior, and invalidate the entire historical cache mirror because an unknown operation may have left shared bounds stale.
5. Revalidate only after a source-defined complete replay from known constructor defaults. A later successful query or world reload alone is not a reset when untouched old instances may retain prior bounds.

This is a sufficient fail-closed design contract, not evidence that the current product implements it. The inspected bounds branch only scopes a local-player no-fluid tick pick and records Anvil/Chest writers; it has no generic old visitor, no global cache-validity state, and no in-process replay/invalidation path. Permanently selecting native behavior after invalidation is safe but may make the historical domain empty unless a complete replay mechanism is separately closed and implemented.

## Readiness and next gate

No generally useful adapter domain is yet justified. A bounded domain could be proposed only after its entire operation envelope is named and source-closed across (1) every old state and provider input it may query, and (2) every lifecycle writer/caller capable of invalidating the cache it reads. Unsupported input must fall back atomically and invalidate; it cannot be skipped or treated as a negative result. If complete lifecycle coverage is impractical, the next source/design step is to identify a nonempty domain whose cache validity can be established and maintained by a complete replay/observation protocol, then state precisely which cells and calls remain native.

The previously accepted product-aware reconciliation found no such operational domain at product commit `993707517116faa21584be5bd101a53ebcf5cc66`. It remains a design gate, not a product patch authorization or parity acceptance.

## Evidence identities

The principal bound sources and hashes are:

| Artifact | SHA-256 |
|---|---|
| Accepted writer matrix report `dda-writer-coverage-matrix-audit/report.md` | `F9905E2F93B0C53C330EDCEA37742576BF370F00F4452CA1F0FDAC1CDD1507C2` |
| Accepted default-provider source rebind `dda-writer-coverage-matrix-audit/default-provider-rebind.md` | `06D73BF1830E0E0F3E1C25DCE2E0D1300A1DD24CF6E60CED68EDD71D9B7ED53D` |
| Product-aware adapter reconciliation `dda-adapter-domain-reconciliation/report.md` | `38F7D0BB1D41F55173E5E49EBCCDF22E52ED74203F45DE9C17CA66D167976BF9` |
| Crosswalk 0–63 `crosswalk.tsv` | `CFC84ADF363FCCB958D2189954BF844894A3FCE3F03042B1E5977741D9BF4167` |
| Crosswalk 64–131 `crosswalk.tsv` | `3D943C1D021B241088621B02C91C84E19E915E8AEBB3AFC87C7D97EA3FF36BC9` |
| Crosswalk 64–131 static validation | `7572F9D8636E5FF18659615B615D1A85BA4A250C8A543A5A24E4769A4BABEB40` |
| Crosswalk 132–197 `crosswalk.tsv` | `BC3F9D631324D904F779D2A505833FE71008A66396E9467016131C7710407BF5` |
| Crosswalk 132–197 validation | `536EB26CDD3238C32477D684572086E78E173E93DD27E80D51B164999DEBE28C` |
| Old opaque roster finding | `8AA7BDB0D06B3C306A831A0905215FADAA685E1FF2E816FB3672812755DE96D1` |
| Old opaque roster CSV | `3A10D2961F0A8F94649BF329EABA148A1D16BE01811490BF62B96255F6A05ADB` |

The 0–63 partition README (SHA-256 `34F77B88FC53A92D1189F4363621246EECCB31FABD413FC3662BA48BC4DD4A31`) specifies its 64-row and 16-metadata-per-ID static coverage. The 64–131 README is `22B17586CB8FFEAE7C120068E7B14F319D77B6D6B07DDB1A1D61E4DD00AFB2DF`; the 132–197 README is `E51C9A38ACA1369CB5FBFE82D806A31F19D4A2A3F91FC38A30DA9FA5CF5B93C4`. Their validation notes explicitly preserve candidate-key and open-projection limits.
