# Opaque roster review rebind 001

## Verdict: ACCEPT, superseding only the source-manifest omission

This rebind accepts the immutable dependency-closure supplement as resolving the sole issue recorded in the prior review. The original `FINDING.md`, roster CSV, original source ledger, and original REVISE review remain unchanged. This rebind changes no row classification and closes no current-state mapping or adapter-readiness gate.

## Bound artifacts

- Original finding SHA-256: `8AA7BDB0D06B3C306A831A0905215FADAA685E1FF2E816FB3672812755DE96D1`
- Roster CSV SHA-256: `3A10D2961F0A8F94649BF329EABA148A1D16BE01811490BF62B96255F6A05ADB`
- Original `source-hashes.json` SHA-256: `C907DC92D9C07D1A43EBE57D8A2DE5BF9ED169B5BBBC9935D7485630B33CBCF0`
- `dependency-closure-supplement.md` SHA-256: `9A3741D6584F86762F11B2F623DFDE1CBEB9544CEBF203C9288D47BB220CBBC6`
- `dependency-closure-source-hashes.json` SHA-256: `99671C39C3DBF395C0249A8979D28AD79AAEA974E6F30423B943B0930E89A7D0`
- Prior independent review SHA-256: `FF91B258B0AEE7EADB9A271E37DE26BDED992C53B57099B01DF74376737C27C4`
- Current canonical `.sources.sha256` SHA-256: `9E75F46DC0ED43B6A355BD65DB8A92C93A4DFEAECFA92284187C6FE9410D8004`

## Verification

The supplement sidecar binds the exact roster CSV and base source-ledger hashes and contains exactly the 17 omitted extant class source files identified in the prior review. I recomputed each byte count and SHA-256 from canonical 1.8.9 source; all match the sidecar and current `.sources.sha256`. Combining the base ledger and supplement yields 151 unique source entries and covers every existing `.java` file represented by the roster's registered-class and ancestry tokens. The two nested tokens `BannerBlock.Standing` and `BannerBlock.Wall` have no standalone source file and are contained in the already-hashed `BannerBlock.java`. `AbstractLeavesBlock.java` and `SlabBlock.java` were already present in the original ledger.

I reran the roster checks: IDs are exactly 0 through 197 once each and in order; all fields are nonblank; `constructed_opaqueCube` remains 72 true, 126 false, zero unresolved; no source digest mismatches were found across either ledger. The separate canonical source-manifest identity is recorded above.

The generic old opaque-ID-to-current-state mapping remains open. No product, parity, runtime, or adapter-readiness claim is made.
