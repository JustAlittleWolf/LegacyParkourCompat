# F-002 corrected artifact identity review — 2026-10-09

**Verdict: ACCEPT — identity-only correction verified against the exact corrected snapshot.** This verdict binds only the corrected artifact identity and byte lineage below. The prior behavioral acceptance remains limited to the unchanged F-002 claim for endpoints 1.17.1 and 1.18.2; this review does not redo the vanilla movement review or infer an intermediate release boundary.

## Immutable identities

| Record | Full commit | Path | Git blob | Raw file SHA-256 |
|---|---|---|---|---|
| Original accepted F-002 snapshot | `e531086eecc778432b40b2b9ef39499b8a7f0dba` | `workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-002-elytra-cosine-precision.md` | `75b6206ca3434ffd96d84caa1a6ff2fc6b5c4ab5` | `bb72676167340fa5e001062b3f9cb69ce36d0160b37fe834212693a4feb0b546` |
| Corrected identity snapshot reviewed here | `2f3425601753d8b153e795a973a147e6ce0cb0b5` | `workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-002-elytra-cosine-precision-artifact-correction-2026-10-09.md` | `dbce0be4ade1fdd84acd9867be201f9f630609cc` | `ccc39601c087a9df5d60e9506e7eaa1831adb7336b89c346bbdeecfcddb71f01` |
| Author binding memo | `959751e85c053f1f1d84dd7b6ed741f92d36620f` | `workflows/source-boundary-reviews/F002-artifact-correction-binding-2026-10-09.md` | `6a80bf497d48502d92e68b18f90853a6370e8e58` | `3da3d1afb502bd4b201f77417a7241873d813116f2d4905a37409b54c27b9507` |

The prior independent source review is `2fd121e116d7185165c76aea1c3708273afc7ea1`, `workflows/source-boundary-reviews/2026-10-08-mc1171-1182-snapshot-review.md`; it accepts F-002 for fall-flying cosine precision only and explicitly leaves the first changed release unknown within `(1.17.1, 1.18.2]`. Its verdict is bound to the original snapshot. The author memo correctly marks the corrected copy as pending independent review and does not claim acceptance.

## Byte-lineage verification

I rehashed the original finding bytes (`bb7267…b546`; 4,178 bytes) and corrected file (`ccc396…1f01`; 4,178 bytes). The original has one occurrence of the old B mapped-jar digest and the corrected copy has one occurrence of the replacement and none of the old digest:

- Old: `60a2017dd217b23df8a5ddbebac96fccb58ff747e961974696e0018f6ef12dba`
- Corrected: `60a2016dd217b23df8a5ddbebac96fccb58ff747e961974696e0018f6ef12dba`

Constructing the expected corrected byte stream by replacing only that single old digest in the original produced a byte-for-byte match with the corrected file (zero differing bytes from expected; unchanged length). The original finding path is unchanged between the author snapshot and the author branch tip. The correction commit adds the corrected snapshot as a new path, preserving the original immutable finding and its review history.

## Canonical artifact identity

The exact 1.18.2 Mojmap readiness marker at `D:/Javastuff/LegacyParkourCompat/build/movement-campaign-2026-10-07/ready/1.18.2/mojmap.ready.json` hashes to `d8057d47468c37880de38d658e95070ec3b750305b7856189997604822480946`. Its source-manifest SHA-256 is `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a`; direct rehash of the manifest matches. The cited B `LivingEntity.java` remains `db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782`.

The artifact-manifest SHA-256 is `a1507e4875faee892aca4c59686bbd68933db21a2274559e81eea8a32b021036`, matching the readiness marker and direct manifest rehash. Its `1.18.2/client-mojmap.jar` row gives `60a2016dd217b23df8a5ddbebac96fccb58ff747e961974696e0018f6ef12dba`. Direct SHA-256 of the published mapped jar at `D:/Javastuff/LegacyParkourCompat/build/movement-campaign-2026-10-07/artifacts/1.18.2/client-mojmap.jar` gives the same value. The corrected snapshot therefore names the artifact identified by both the canonical manifest and the present published jar bytes.

## Scope, merge, and follow-up

- **Accepted:** the exact correction changes only the erroneous mapped-jar digest; the corrected identity matches the canonical artifact-manifest row and actual mapped jar.
- **Still open:** the first changed release remains unknown within `(1.17.1, 1.18.2]`; this identity review does not establish 1.18 or 1.18.1 behavior, implementation coverage, or runtime parity.
- **Main integration:** current `main` `e1dd0a65474a56e24ceea36ba300ea54c135d6a2` was merged into the reviewer branch by fast-forward. Incoming report and campaign-ledger updates were inspected. They record the pair audit and endpoint review while keeping the 1.18/1.18.1 cutover and this F-002 identity correction open. No semantic conflict with this separate corrected-byte identity review was found.
- No author snapshot, shared source, readiness marker, artifact manifest, mapped jar, original review, or production code was edited. No build, tests, runtime, wiki lookup, source-owner message, or push was performed.

Reviewer: independent F-002 artifact-identity review. Date: 2026-10-09.
