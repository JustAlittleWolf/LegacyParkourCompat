# Accepted movement finding backlog

Implementation-side ledger for bounded findings accepted from immutable source or wiki snapshots. This stays separate from the 26-pair discovery campaign: finding acceptance does not complete a pair and does not imply implementation, code review, integration, build, or runtime validation.

- accepted-findings.jsonl: accepted immutable snapshots, one JSON object per line, with reviewer identities and known implementation status.
- pending-and-rejected.jsonl: rejected/superseded items, unresolved identities, and current reviews. Rejected findings stay separate from the accepted list.

Unknown full identities are marked as abbreviated or unresolved with a next lookup. Runtime remains unverified unless an entry says otherwise. Pair state belongs to the source-campaign ledger.

Task began from main 64895d266fcee5da774c3236295ba949045200c7. The focused correction branch is merged with current main de8eaa594677588f4a70e260561cfd2e872f69d3 via merge commits 47532cedee393b53ae189c22040f404265cc447f and latest documentation merge. Task branch: feat/accepted-finding-backlog-2026-10-08. No tests, builds, clients, TAS, servers, or Docker were run for this documentation change. Latest integration build evidence was audited from existing reports and the artifact owner; the retained latest E6ACA JAR was confirmed at the integration worktree, while the earlier WORLD03 E42DB JAR was overwritten. All 26 source pairs remain partial.

- integrated-code-batches.jsonl: exact code/review identities for integrated implementation batches already on the baseline, kept distinct from finding-source acceptance.

