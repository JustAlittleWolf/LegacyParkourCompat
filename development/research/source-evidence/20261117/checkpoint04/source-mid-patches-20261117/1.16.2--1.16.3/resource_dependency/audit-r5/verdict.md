# Independent audit r5 — citation binding only

Verdict: **ACCEPT** for the r4 citation correction and the previously checked bounded resource/archive evidence. This follow-up verifies the exact bindings only; it does not repeat source or resource-content review.

## Bound revisions

- Contract: `build/source-mid-patches-20261117/1.16.2--1.16.3/resource_dependency/input-reference-r4.json`, SHA-256 `5DEA2DBEE7E4653DDB0D67AFE67A0F1D8147D2155C49F6C648A0C6C047153B0A`.
- Erratum: `build/source-mid-patches-20261117/1.16.2--1.16.3/resource_dependency/resource-dependency-r4-erratum.md`, SHA-256 `CC38FFF79D9B72C76091001289352FBA1087F34304E49006B48696B1589E36E4`.
- Superseded citation audit: `build/source-mid-patches-20261117/1.16.2--1.16.3/resource_dependency/audit-r3/verdict.md`, SHA-256 `37A151C4A18D29742F7791A77CF78EF920341F8A66911384D895421C7829B0B9`.

## Binding checks

- The exact referenced verdict file at `build/source-mid-patches-20261117/1.16.1--1.16.2/fluid_travel_dependency/resource-gate/audit/verdict.md` hashes to `FEF9FA423063152AEE899B2A87505128B9D62CCDED805D38A2F7686B5B54E6D9`. The r4 operative `fluidTagReuse[0].auditSha256`, `citationCorrection.correctFluidReuseAuditSha256`, and r4 erratum all carry this exact value. The malformed r2 digest is absent from the operative field and current erratum.
- The archive membership contract citation resolves to `pinned-archive-verification/membership-verification.json`, SHA-256 `506B5E7DA8280695CB23EAACFFCF103C569EA2C0C3B268FC59982D2239759DF7`. The archive proof citation resolves to `audit/artifact-check/archive-membership.md`, SHA-256 `8BA889711001FCD99F97D1911D0D161C23E892EF7F59C56866818492567642BB`. Both immutable bytes match the r4 citations; the membership contract's allowlist matches the unchanged r4 14-key inventory.
- The erratum correctly binds the superseded r3 contract SHA-256 `E1E464006EF92896B9A380788E994DA5809A620ABEFBF04DD13900F9DA7C798B` and the prior r3 audit SHA above.

## Reuse boundary

Compared with r3, the source identities and Java bindings, 14 tag keys, all block-tag payload sizes/hashes, and reused water/lava payload hashes are unchanged. This closes the citation-only issue from the previous request-changes verdict. The prior bounded archive/resource checks are reused without reopening their contents. The pair remains partial and unfrozen; this does not claim runtime, generated-world, or external datapack parity.
