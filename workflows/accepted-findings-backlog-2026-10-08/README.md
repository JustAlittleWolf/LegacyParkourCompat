# Accepted movement finding backlog

Implementation-side ledger for bounded findings accepted from immutable source or wiki snapshots. This stays separate from the 26-pair discovery campaign: finding acceptance does not complete a pair and does not imply implementation, code review, integration, build, or runtime validation.

- accepted-findings.jsonl: accepted immutable snapshots, one JSON object per line, with reviewer identities and known implementation status.
- pending-and-rejected.jsonl: rejected/superseded items, unresolved identities, and current reviews. Rejected findings stay separate from the accepted list.

Unknown full identities are marked as abbreviated or unresolved with a next lookup. Runtime remains unverified unless an entry says otherwise. Pair state belongs to the source-campaign ledger.

Task began from main 64895d266fcee5da774c3236295ba949045200c7. The focused correction branch is merged with current main d4c4f154a0c2487dd6dd7d20d92eb57b3d8ab1ae via merge commits 47532cedee393b53ae189c22040f404265cc447f and e5acb412ffc8011b3a6df2bff82422c2eb8938fd. Task branch: feat/accepted-finding-backlog-2026-10-08. No tests, builds, clients, TAS, servers, or Docker were run for this documentation change. The current main sampler artifact and standalone Gradle log were independently rehashed from their absolute integration-worktree paths (see audit record); the WORLD03 E42DB JAR was overwritten by later builds. All 26 source pairs remain partial; coordinator correction for 1.21.11--26.1.2 is recorded in pending-and-rejected.jsonl without editing source reports.

- passenger-crouch-source-provenance-erratum-2026-10-08.md: corrects the accepted snapshot digest and labels the shortened copy as non-binding.
- integrated-code-batches.jsonl: exact code/review identities for integrated implementation batches already on the baseline, kept distinct from finding-source acceptance.
