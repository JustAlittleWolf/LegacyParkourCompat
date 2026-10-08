# Accepted movement finding backlog

Implementation-side ledger for bounded findings accepted from immutable source or wiki snapshots. This stays separate from the 26-pair discovery campaign: finding acceptance does not complete a pair and does not imply implementation, code review, integration, build, or runtime validation.

- accepted-findings.jsonl: accepted immutable snapshots, one JSON object per line, with reviewer identities and known implementation status.
- pending-and-rejected.jsonl: rejected/superseded items, unresolved identities, and current reviews. Rejected findings stay separate from the accepted list.

Unknown full identities are marked as abbreviated or unresolved with a next lookup. Runtime remains unverified unless an entry says otherwise. Pair state belongs to the source-campaign ledger.

Task branch `feat/accepted-finding-backlog-2026-10-08` began from the operations reconciliation checkpoint and now includes current main `7e7b7bb7068ec7b68f3a8f09cbb42bc498a04bf4`. The latest main integration includes the V1_19_4 sampler extension, passenger-crouch no-code proof, shallow-water implementation, and MC01 no-code integration. Exact source, code-review, integration, and artifact bindings are in the ledgers. This task updated backlog documentation only; no tests, builds, clients, TAS, servers, or Docker were run. All 26 source pairs remain partial.

- passenger-crouch-source-provenance-erratum-2026-10-08.md: corrects the accepted source digest and labels the shortened copy as non-binding.
- integrated-code-batches.jsonl: exact code, review, integration, and artifact bindings, distinct from source-finding acceptance.
