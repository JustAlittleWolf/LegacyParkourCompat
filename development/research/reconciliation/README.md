# Historical record reconciliation

All **340** migrated records are assigned: **223 canonical findings**, **45 consolidated duplicate/revision records**, **78 dispositions**, and **4 concrete unresolved records**. **17 grouped records** map to multiple independent operations, so record reduction is not the duplicate count. No new discovery, historical implementation, independent acceptance or runtime verification is claimed.

- [Record map](record-map.md): one traceable resolution per original ID.
- [Implementation correspondence](implementation-map.md): current Changes/Hooks, operation/range coverage and missing pieces.
- [Concrete evidence limits and later gates](remaining-evidence.md).
- [Baseline inventory](baseline-records.json): immutable pre-reconciliation paths, hashes and archive links at `29b6a521`.
- [Early](early.json), [middle](middle.json), [late](late.json): source, review and implementation evidence; [summary](summary.json).
- [Exact accepted-backlog identities](review-identities.json): 47 decision documents recovered; 44 archive finding snapshots match their hashes and three exact reviewed revisions are recovered separately. Further outside-backlog decisions are in the record ledgers.
- [Additional existing evidence](additional-evidence.json): exact followup/boundary documents recovered from Git.

`reviewed-snapshots/`, `review-decisions/` and `additional-evidence/` are byte-exact historical documents. Their internal links/instructions refer to historical locations; they are evidence, not current procedures. Do not edit them. The original 864-file archive and separate staging report remain unchanged.

A shortened assertion can carry its exact historical verdict when meaning is preserved. Substantive corrections retain the old decision and explicitly require review of the corrected claim before implementation reliance. Source-content checks do not prove unavailable mapped-JAR provenance, full-pair coverage or intermediate release boundaries. Compilation and runtime parity remain separate evidence.

Historical path resolution: baseline record IDs retain their original `research/...` names at `29b6a521`; their current files/evidence are under `development/research/...`. Current canonical/ledger navigation paths are updated. Historical `workflows/...` Git paths retain their original spelling; preserved files resolve under `development/research/archive/legacy-workflows/...`, or through the exact commit/blob identity. Archived links leaving their original tree remain historical references, not assertions that an old worktree path still exists.
