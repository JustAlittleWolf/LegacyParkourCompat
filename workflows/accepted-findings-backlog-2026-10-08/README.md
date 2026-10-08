# Accepted movement finding backlog

Implementation-side ledger for bounded findings accepted from immutable vanilla-source or Wiki snapshots. Finding acceptance does not complete a pair and does not imply implementation, code review, integration, build, or runtime validation. Pair state belongs to the source-campaign ledger.

- `accepted-findings.jsonl`: accepted immutable snapshots and implementation status.
- `integrated-code-batches.jsonl`: exact code, review, integration, and artifact bindings, distinct from source-finding acceptance.
- `pending-and-rejected.jsonl`: rejected/superseded items, unresolved identities, active reviews, source checkpoints, and provenance caveats.
- `passenger-crouch-source-provenance-erratum-2026-10-08.md`: corrects the accepted source digest and marks the shortened copy non-binding.

This branch includes local `main` `dc1b65b29e3c2561175bd54276ae83e37cd1c045`. The architecture batch is integrated on main after independent review and a successful repair build: 18 actionable tasks, every Gradle Test task disabled and `-x test` supplied. The first compile failure, accepted two-modifier repair, repair log, packaged/source JAR hashes, and prior artifacts are all preserved in the integration record. Runtime remains unverified; no push was made.

All 14 1.17.1–1.18.2 finding snapshots now have bounded ACCEPTs, including the corrected F-013 identity-only snapshot. The original REQUEST CHANGES remains bound to the original immutable bytes. The separately accepted full-pair coverage audit does not complete the pair; pair state remains PARTIAL.

The 1.21.4–1.21.5 owner disclosed implementation-diff exposure during a prior merge. That evidence remains recorded; a fresh source-only discovery/coverage pass and blind review are required before full-pair freeze. The 1.21.11–26.1.2 pair is partial with F-1 snapshot-only ACCEPT, F-2/F-3 pending, and seven inventories open. Other terminal source checkpoints, Wiki/MCPK gaps, and resumable queues are recorded in `pending-and-rejected.jsonl` and the coordinator resume note.

All 26 source intervals remain partial/open. Tests, game/runtime validation, Docker, and pushes were not performed for this checkpoint.
