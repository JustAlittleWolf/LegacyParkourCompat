# F-003 additive snapshot event: dependency closure

- Event ID: `F003-1.17.1-1.18.2-2026-10-10-closure-01`
- Date: 2026-10-10
- Finding: F-003, `T-AUTOJUMP-ORDER`
- Event type: additive source evidence and revised snapshot submitted for blind re-review

## Preserved accepted record

- Previously accepted bounded source snapshot commit: `2fd121e116d7185165c76aea1c3708273afc7ea1`
- Previously accepted finding blob: `e801da616021d5cab6a1ec6d985210803824f618`
- Previously accepted finding raw SHA-256: `221aa9b6e306ded17642e6cf59afcf5c03d12f676d0bc7ded30e80cb670f77f1`
- Accepted full-pair audit commit: `e0e36eff36a92be82327bf2a8140f18141d7cc21`

These records and the original `findings/F-003-autojump-candidate-order.md` bytes remain unchanged. The added evidence closes the entity-filter, candidate-geometry, first-qualifying-candidate, timer/state-write and movement-consumer dependencies. It also establishes exact 1.18 as the first changed stable release. Details and verified manifests are in [the additive source memo](F-003-autojump-order-closure-2026-10-10.md).

## New immutable source snapshot

- Snapshot ID: `F003-1.17.1-1.18.2-closure-a5870a1`
- Finding snapshot: [F-003 additive snapshot](F-003-autojump-order-snapshot-2026-10-10.md)
- Snapshot commit: `a5870a155106dbdf2a45010b267ae146c7735a8b`
- Finding Git blob: `5a679c822289aaa8e33f8d5d47535369f9937377`
- Finding raw SHA-256: `24b974df8b039fe36dc4d9b438f5c00507b2379072c0d0e6adf5acc66193c942`
- Source artifacts: exact Mojmap 1.17.1 and 1.18.2 endpoints plus ready canonical 1.18 and 1.18.1 sources; source/artifact manifest identities are listed in the memo.
- Finding dependencies: closed for source reachability and applicability in the stated witness; runtime validation not performed.
- Blind review of this new snapshot: pending; no new acceptance is claimed.
- Pair state: accepted audit commit `e0e36eff36a92be82327bf2a8140f18141d7cc21` remains the preserved record. It descends from assigned base `e531086eecc778432b40b2b9ef39499b8a7f0dba` and was not merged into this follow-up branch before blind re-review, per assignment.
- Implementation status: not assessed by this source worker.

## Provenance and limits

The snapshot was prepared from immutable base `e531086eecc778432b40b2b9ef39499b8a7f0dba` on `feat/source-autojump-order-closure-2026-10-10`. Only source publications under the shared ready cache were read. No Java implementation, wiki lane, shared source cache, test, build or runtime was accessed or changed.
