# Independent follow-up audit — 1.16.2 → 1.16.3 coverage binding r2

Verdict: **ACCEPT** for the corrected exact-pair source-coverage candidate r2. This is a citation-only follow-up to the prior REQUEST CHANGES verdict; the earlier technical and scope review is reused unchanged. Acceptance is limited to source coverage for the exact 1.16.2 → 1.16.3 pair and does not establish runtime parity.

## Bound candidate and prior verdict

- Candidate: `build/source-mid-patches-20261117/1.16.2--1.16.3/coverage_consolidation/coverage-candidate-r2.md`
- Candidate SHA-256: `78E164D0CE1B33EC150BBD15C53E032FB4FDC5DDE61B12A676C28DCF30752F42`
- Prior REQUEST CHANGES verdict: `coverage_consolidation/audit/verdict.md`, SHA-256 `BAF7F5136F646857DBF0FCA087A4B17D849E406905EDFC1BDA08E7A44B5BBFCF`

The prior verdict found only a resource binding error: the candidate cited obsolete input-reference r3 (whose audit was REQUEST CHANGES) and an audit-r4 path that did not exist. It found no additional unexamined source operation or resource dependency.

## Corrected binding verification

The candidate r2 now cites:

- Resource report `resource_dependency/resource-dependency-report.md`, SHA-256 `B984B03F6C7158FE4E4EFA609552781B03BEA984B9B3EC64C13D499A494E9BD2`.
- Corrected input reference `resource_dependency/input-reference-r4.json`, SHA-256 `5DEA2DBEE7E4653DDB0D67AFE67A0F1D8147D2155C49F6C648A0C6C047153B0A`.
- Accepted binding verdict `resource_dependency/audit-r5/verdict.md`, SHA-256 `42177C349023006DBFC725D4E553C4E43C2A3493A51D7433C0CAA3587D65691F`.
- Independent archive membership proof `resource_dependency/audit/artifact-check/archive-membership.md`, SHA-256 `8BA889711001FCD99F97D1911D0D161C23E892EF7F59C56866818492567642BB`, and membership contract `resource_dependency/pinned-archive-verification/membership-verification.json`, SHA-256 `506B5E7DA8280695CB23EAACFFCF103C569EA2C0C3B268FC59982D2239759DF7`.

I recomputed each of these cited current file hashes; all match. The candidate no longer cites the superseded r3 input digest or the nonexistent audit-r4 path. The audited resource verdict r5 confirms the corrected reference and archive citations while reusing the previously checked exact resource contents and source identities.

The base ordinary-player inventory/audit and direct-player packet response r3/audit citations are unchanged from the prior full coverage review and remain bound to the exact hashes recorded there. No source/resource comparison or full-tree rehash was repeated for this metadata-only correction.

## Disposition

The candidate now correctly binds the accepted base Java inventory, complete movement-resource packet evidence, and direct-player response supplement. I accept the bounded exact-pair source-coverage candidate. The coordinator may record the pair's source-coverage freeze under the normal workflow. This does not establish runtime parity, external datapack behavior, generated-world occurrence, or patch onset outside this exact edge.
