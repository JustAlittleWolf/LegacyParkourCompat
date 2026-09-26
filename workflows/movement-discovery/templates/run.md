# Discovery: <A> to <B>

- Status: not started | active | partial | blocked | complete
- Scope: client player movement; older A = ...; newer B = ...
- Repository revision and start date:
- Selected common mapping family and alignment evidence:
- Source preparation command and log:
- Toolchain/decompiler/remapper versions and options:

## Artifact manifest

Repeat for A and B: exact release; source root; client jar hash; mapping coordinate/build/path/hash; bridge mapping path/hash if any; mapped jar hash; cited source relative paths/hashes; cited resource jar entry names/hashes; required external data and its provenance. Use SHA-256 and record any publisher-provided hashes separately.

## Correspondence and call order

Repeat per logical role: A class/member descriptor -> B class/member descriptor; source anchors; rename/split/replacement evidence; callers; direct dependencies; read/write state and execution order. No unresolved guessed names.

## Coverage ledger

Repeat for every navigation-stage slice:

- Slice ID / stage / behavior:
- Status: pending | in-progress | compared-no-difference | findings | not-applicable | blocked
- A and B evidence paths, members and lines (or checked absence path):
- Dependency closure / parent slice / related findings:
- Conclusion and rationale:

## Dependency queue and blockers

For each: ID; originating slice; precise missing member/resource/question; why it can affect movement; next retrieval action; resolution evidence or blocked reason.

## Finding index

Link each finding with its short behavioral title and confidence. Record discarded candidates and reasons so they are not rediscovered.

## Resume checkpoint

- Last completed slice:
- Next bounded slice and exact files/members to open:
- Outstanding dependencies:
- Current assumptions requiring verification:

## Source audit closure

- Coverage counts by status:
- Unresolved gaps and limits:
- Evidence/hash/correspondence audit:
- Runtime validation: not performed (separate workflow).
