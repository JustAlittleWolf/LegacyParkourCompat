# Independent source audit: ResetPos state-domain expansion

**Verdict: ACCEPT, bounded to the registered finite state-domain inventory.**

Audited inputs:

- Coverage report SHA-256: `fad40d431698e7f7ef2e90646313ce61bcfb5fe3478c69295c9939280ef2dddc`
- State table SHA-256: `f12118dc09be593fdd58b268469b985fef95cfe644f3f5916aac4525977cb382`
- Consulted-source table SHA-256: `a5a14938cf0b1ba5737e2f74a6774cf14ae17ec8e1c3520c69670443d4781d7a`
- Input exact-key matrix SHA-256: `b7294302cd98bc923f543608ee9f5935a24a02033ac6033828c339dc702a2974`

The endpoint manifests match their ready records: 1.12.2 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`. The input matrix has 184 exact common keys. The state table has exactly two rows per key, one per endpoint (368 total), and its registered classes match the corresponding old/new class columns in the matrix. All rows are marked resolved with no notes. The listed Cartesian products recompute from the stated finite domains on all 368 rows. The report's 57 cardinality-difference keys and 17 registered-class-change keys match independent comparison of the state table.

All 247 consulted-source rows were independently hashed from their listed canonical files; each byte hash matches both the report table and its endpoint source-manifest entry. Representative domain checks included chest's old/new property products, flower-pot's 22 contents × 16 legacy-data values, snow's singleton old class versus eight target layers, the stone-slab and nested single purpur-slab variants, and acacia fence's added waterlogged state.

This acceptance covers source-declared state properties and cardinalities only. It does not establish geometric equivalence, provider behavior, state reachability in maps, alias applicability, shape intersection with ResetPos boxes, ResetPos query equivalence, or pair completion. No correction addendum was needed.
