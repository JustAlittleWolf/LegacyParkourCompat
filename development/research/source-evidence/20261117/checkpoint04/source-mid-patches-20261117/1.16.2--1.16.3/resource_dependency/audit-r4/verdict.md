# Independent audit r4 — citation binding only

Verdict: **ACCEPT** for the r3 citation/binding correction and the bounded resource-dependency candidate previously audited. This is a narrow follow-up; no source or resource-content review was repeated.

## Bound revisions

- Input contract: `build/source-mid-patches-20261117/1.16.2--1.16.3/resource_dependency/input-reference-r3.json`, SHA-256 `E1E464006EF92896B9A380788E994DA5809A620ABEFBF04DD13900F9DA7C798B`.
- Erratum: `build/source-mid-patches-20261117/1.16.2--1.16.3/resource_dependency/resource-dependency-r3-erratum.md`, SHA-256 `94AAA971EE0BA754487A4CD333427516AE29E3EE514419E5B9D25629C59FE154`.
- Superseded r2 citation audit: `build/source-mid-patches-20261117/1.16.2--1.16.3/resource_dependency/audit/verdict-r2.md`, SHA-256 `68D692ACA6B2629917C6A8513B966E2B5094AB220C47D614EA855F0BEC8BB782`.
- Superseded r1 input contract: `build/source-mid-patches-20261117/1.16.2--1.16.3/resource_dependency/input-reference.json`, SHA-256 `24473AFA12ECD5641066B4627C5E232BD2ECB748230C4EB55CF9CDAB02AA0E89`.

## Citation checks

- The exact cited 1.16.2 fluid verdict path `build/source-mid-patches-20261117/1.16.1--1.16.2/fluid_travel_dependency/resource-gate/audit/verdict.md` hashes to `FEF9FA423063152AEE899B2A87505128B9D62CCDED805D38A2F7686B5B54E6D9`. Both `input-reference-r3.json` at `fluidTagReuse[0].auditSha256` and `citationCorrection.correctFluidReuseAuditSha256` contain this exact value. The r3 erratum binds the same full digest and exact path.
- The erratum's archive membership contract citation resolves to `build/source-mid-patches-20261117/1.16.2--1.16.3/resource_dependency/pinned-archive-verification/membership-verification.json`, SHA-256 `506B5E7DA8280695CB23EAACFFCF103C569EA2C0C3B268FC59982D2239759DF7`. That contract's input hash correctly identifies the original r1 resource input, and its 14-key allowlist matches the unchanged r3 tag-key inventory.
- The archive proof citation resolves to `build/source-mid-patches-20261117/1.16.2--1.16.3/resource_dependency/audit/artifact-check/archive-membership.md`, SHA-256 `8BA889711001FCD99F97D1911D0D161C23E892EF7F59C56866818492567642BB`.
- The r3 erratum binds and supersedes the exact r2 input-contract SHA-256 `53894034238EF0CF374F3B8CD659A4945C4AF0A96D2936CE98926C09A9C9809A`; the erratum itself matches its declared SHA-256.

## Reuse boundary

Comparison with the prior contract confirms unchanged endpoint source identities and Java bindings, all 14 tag keys, every packaged-payload size/hash, and the reused water/lava payload hashes. The corrected citation closes the sole remaining issue from the prior audit. The accepted resource evidence remains bounded to these exact default packaged resources; this does not freeze the source pair or establish runtime, generated-world, or external datapack parity.
