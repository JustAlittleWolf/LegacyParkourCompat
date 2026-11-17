# Independent source audit — ResetPos changed-class query reachability

## Verdict

**ACCEPT**, limited to the bounded claims in `resetpos_changed_classes_query/report.md` at SHA-256 `9335E6457177E3D46275FE337B6820584D9F572A0D432D5FF1EAF248F6126C21`. This accepts the exact pair identity, 17 changed-class matrix/state-domain joins, the stated geometric query-cell reachability, and bounded evidence reuse dispositions. It does not accept shape parity, complete ResetPos query equivalence, player displacement, or pair closure.

## Identity and inputs

The endpoints are Ornithe Feather Gen2 build 2: 1.12.2 source-manifest SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` and 1.13.2 source-manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`. Both values match the canonical `ornithe-feather.sources.sha256` bytes and the ready records.

The audited report's listed dependency hashes match current bytes: key/provider matrix `b7294302cd98bc923f543608ee9f5935a24a02033ac6033828c339dc702a2974`; state-domain coverage `fad40d431698e7f7ef2e90646313ce61bcfb5fe3478c69295c9939280ef2dddc`; legal-state table `f12118dc09be593fdd58b268469b985fef95cfe644f3f5916aac4525977cb382`; block-region note `8183cc6d247d0e236287d2b1f5bd9cddfa477fd373cb99d6ced9bc6cccaafe95`; base-dispatch coverage `2199f933da3c36f38bdffe942dac095e66c5948b3af9654adc06bbf1af9fef04`; and provider ledger `f5133f3574a8a4bc9c2aae01a357f9a2b4f9507c37b5cd7ebc669c4a129b44b2`.

The underlying matrix and finite-domain inventory have independent ACCEPT verdicts: `audit_resetpos_block_state_matrix/verdict.md` SHA-256 `26404a7ea05a5b43b13e92ef2b1189d3c6a301f3f5af2f45ba54a6e1ad23bc73` and `audit_resetpos_state_domain_expansion/verdict.md` SHA-256 `66d1d0f5fc3dcbbafbdb30a565e5514d475edcf44e470030b3abf6ba96775364`.

## Checks

- The matrix contains exactly 17 rows whose registered class differs between endpoints. The report contains exactly those 17 keys, once each; each old/new registered class matches the matrix, and each old/new count matches its corresponding resolved row in the accepted state table. No row or count mismatch was found.
- The state-source index binds every old/new class used by these 17 rows. I recomputed all 34 referenced source-file SHA-256 values; every value matches both the index and the endpoint's canonical source manifest.
- All rows are marked query-reachable. The accepted `resetpos_block_region` note and its independent audit establish the padded candidate interval, integer-Y retries, normal player-box dimensions, and the distinct shell filters. For any exact common key, the claim is only that its block may occupy an interior candidate cell with positive box overlap at some reachable world position. This is consistent with that bounded enumeration; it makes no map/state/context or resulting-collision claim.
- The reused default unit-cube packet is ACCEPTed for its bounded state-independent provider slice: coverage SHA-256 `3d2ecb822cde9aa8fa6592aca23687c01840479913409b6c32b83452b252381e`, verdict SHA-256 `6309ee8ddca94643c97914af7ed57819e0e3641b20c1dea4a333d4303c02741d`. Its changed-class intersection is exactly the eight reported keys: dirt, sandstone, obsidian, netherrack, quartz block, prismarine, red sandstone, and bone block. The report leaves query integration open for every one.
- The snow r3 packet and ACCEPT verdict hashes match the supplied identities (`124ccdf3667e28f9c241109cb3b1af60fb7bc88be84ae9a091413d2856e2f904` / `57b79925ce42119d2444a3c79d5729fe9d0018e0577fa730790856045202dbf2`). They support only a conditional layer-1 old candidate/clip-helper path. They explicitly leave ordinary-player reachability and displacement unproven; the report correctly keeps layers 2–8, ordinary-player/query applicability, and all-state closure open.

## Limits retained

Provider/state/context coverage remains open for grass, both liquid keys, both pane keys, both slab key/state crosswalks and raw world-generation insertion, and trapped chest. Snow remains conditional as above. ResetPos collision-query integration and consumer/displacement effects remain open for every key; the default-provider verdict does not close them. This audit does not extend to the other 167 common keys, recompute geometry, or make a pair-freeze claim.


