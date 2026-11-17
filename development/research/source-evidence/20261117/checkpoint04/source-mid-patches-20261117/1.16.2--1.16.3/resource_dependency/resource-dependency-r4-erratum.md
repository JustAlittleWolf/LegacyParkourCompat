# Resource-dependency packet citation erratum r4

This citation-only revision supersedes input-reference-r3.json SHA-256 E1E464006EF92896B9A380788E994DA5809A620ABEFBF04DD13900F9DA7C798B. The r3 independent citation audit is audit-r3/verdict.md SHA-256 37A151C4A18D29742F7791A77CF78EF920341F8A66911384D895421C7829B0B9.

The r2 and r3 citation handoffs carried malformed audit-digest strings. R4 replaces the operative fluidTagReuse[0].auditSha256 field with the SHA-256 computed from the exact referenced verdict file.

Referenced verdict path: build/source-mid-patches-20261117/1.16.1--1.16.2/fluid_travel_dependency/resource-gate/audit/verdict.md. Get-FileHash -Algorithm SHA256 returned: FEF9FA423063152AEE899B2A87505128B9D62CCDED805D38A2F7686B5B54E6D9. R4 stores this exact value in the operative JSON field.

Archive and previous audit references are retained unchanged. Source identities, tag keys, payload sizes/hashes and technical/resource claims are unchanged. No extraction, source review, or new technical audit was performed.
