# Accepted movement finding backlog

Implementation-side ledger for bounded findings accepted from immutable source or Wiki snapshots. Finding acceptance does not complete a pair and does not imply implementation, code review, integration, build, or runtime validation. Pair state belongs to the source-campaign ledger.

- `accepted-findings.jsonl`: accepted immutable snapshots and implementation status.
- `integrated-code-batches.jsonl`: exact code, review, integration, and artifact bindings, distinct from source-finding acceptance.
- `pending-and-rejected.jsonl`: rejected or superseded items, unresolved identities, and active reviews.
- `passenger-crouch-source-provenance-erratum-2026-10-08.md`: corrects the accepted source digest and marks the shortened copy non-binding.

This task branch includes current main `3a60fe735560e478bf0aa0d05f5e306c74800f6a`. Sleep and sprint implementations are integrated and have recorded builds with Gradle Test tasks disabled; slime landing restitution is an accepted no-code reconciliation. Runtime remains unverified. The vanilla `F-WATER-SPRINT` snapshot remains a candidate: its exact independent source-acceptance chain is unverified, separate from the accepted Wiki snapshot and code review.

The 1.20.4–1.20.6 source boundary is independently accepted for bounded claims. Its scope proof received REQUEST CHANGES for stale boundary wording despite per-claim ACCEPT decisions; the author published corrected r2, now under a fresh independent review. MC02 code review is independently ACCEPTED, with integration/build queued after source preparation. The 1.17.1–1.18.2 full-pair coverage audit is ACCEPTED after an immutable count erratum, but the pair ledger remains PARTIAL until the owner records the freeze; all 14 finding snapshots remain individually unaccepted.

Pane r2 received a second REQUEST CHANGES because a fallback shape uses a 7/16 and 9/16 subdivision outside the supported `findBits` subdivision; the owner is correcting it and continuing the full inventory. A new ordinary-fall bed witness is under blind review. MCPK r5 received bounded ACCEPT; the Wiki lane remains partial, with attribution correction and full-catalog continuation active. Piston source evidence is accepted, while a proof-only scope review remains active; first exact cutover and 1.15.1 coverage are open.

All 26 source pairs remain partial. This ledger update is documentation only; no tests, builds, clients, TAS, servers, Docker, or push were performed by this task.

## 2026-10-08 deadline checkpoint

Current main is `d8f3956602da94bf0cf67753cc0a9f4665397729`. MC02 sprint-jump precision is integrated and independently verified from its retained build evidence; the source pair remains PARTIAL and runtime parity remains unverified. The `F-WATER-SPRINT` candidate still lacks an exact independent source-acceptance chain.

The architecture handoff is due by 19:25 Europe/Vienna, with worker-ready checkpoints at 19:15 and a clean coordinator checkpoint by 19:40. Continue only already-assigned work: split `SprintInputStartBehavior` into three mechanics/interfaces and replace per-version movement providers with one static central catalog; implement an independent `V1_15_2` suffocation probe without extending `V1_16`; continue bed implementation with registration overlap coordinated. Edge-backoff simplification remains an unassigned investigation. The full deadline is 20:00; completion is not required, and work resumes tomorrow from the recorded state.

Stop new source discovery by 19:25 and checkpoint active work cleanly by 19:40. The 1.14.4–1.15.2 and 1.15.2–1.16.5 owners are terminal; 1.8.9–1.9.4 and 1.16.5–1.17.1 owners are still closing their current slices. Other active source owners, including 1.21.11–26.1.2 and 26.1.2–26.2, share that cutoff. Maintain the 20-role cap including coordinator and nested workers; no new worker or chat dispatch. The exclusive source-preparation owner has completed and hash-verified the 1.11, 1.11.1, and 1.16 preparations and is idle pending independently reviewed architecture integration.
