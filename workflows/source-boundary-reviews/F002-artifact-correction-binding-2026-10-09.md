# F-002 mapped-jar identity correction binding

This memo binds an identity-only corrected copy of F-002. It is not a source review and does not accept the corrected snapshot. Obtain a fresh independent review against the exact corrected bytes before relying on it. The original finding and its prior review history remain unchanged.

## Immutable snapshot lineage

| Record | Full commit | Path | Git blob | Raw file SHA-256 |
|---|---|---|---|---|
| Original accepted snapshot | `e531086eecc778432b40b2b9ef39499b8a7f0dba` | `workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-002-elytra-cosine-precision.md` | `75b6206ca3434ffd96d84caa1a6ff2fc6b5c4ab5` | `bb72676167340fa5e001062b3f9cb69ce36d0160b37fe834212693a4feb0b546` |
| Corrected identity snapshot | `2f3425601753d8b153e795a973a147e6ce0cb0b5` | `workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-002-elytra-cosine-precision-artifact-correction-2026-10-09.md` | `dbce0be4ade1fdd84acd9867be201f9f630609cc` | `ccc39601c087a9df5d60e9506e7eaa1831adb7336b89c346bbdeecfcddb71f01` |

The corrected file was generated from the original commit's blob bytes. A byte comparison confirmed exactly one replacement, with unchanged byte length: the 1.18.2 mapped-jar hash in the artifact identity paragraph changes from `60a2017dd217b23df8a5ddbebac96fccb58ff747e961974696e0018f6ef12dba` to `60a2016dd217b23df8a5ddbebac96fccb58ff747e961974696e0018f6ef12dba`. No source ranges, math, reachability, dependencies, confidence, boundary, claims, exclusions or runtime status were edited.

The original F-002 was accepted by the independent source review recorded at commit `2fd121e116d7185165c76aea1c3708273afc7ea1`, in `workflows/source-boundary-reviews/2026-10-08-mc1171-1182-snapshot-review.md`. That historical decision remains bound to the original snapshot. The corrected copy has no independent verdict yet; its status is **pending independent identity review**. Do not represent this memo as new acceptance or changed source evidence.

## Canonical artifact verification

The exact 1.18.2 readiness publication is Mojmap, version metadata ID `1.18.2`, rooted at `D:/Javastuff/LegacyParkourCompat/build/movement-campaign-2026-10-07/ready/1.18.2/mojmap`. Its marker is `D:/Javastuff/LegacyParkourCompat/build/movement-campaign-2026-10-07/ready/1.18.2/mojmap.ready.json`; direct marker SHA-256 is `d8057d47468c37880de38d658e95070ec3b750305b7856189997604822480946`.

- Source manifest `mojmap.sources.sha256`: SHA-256 `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a`, matching the marker. The cited B `LivingEntity.java` SHA-256 remains `db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782`.
- Artifact manifest `artifacts.sha256`: SHA-256 `a1507e4875faee892aca4c59686bbd68933db21a2274559e81eea8a32b021036`, matching the marker. Its `1.18.2/client-mojmap.jar` row is `60a2016dd217b23df8a5ddbebac96fccb58ff747e961974696e0018f6ef12dba`.
- The actual mapped jar at `D:/Javastuff/LegacyParkourCompat/build/movement-campaign-2026-10-07/artifacts/1.18.2/client-mojmap.jar` was hashed directly; SHA-256 is `60a2016dd217b23df8a5ddbebac96fccb58ff747e961974696e0018f6ef12dba`, matching the manifest row and corrected snapshot.

These checks establish the corrected jar identity against the published manifest and present bytes. They do not prove new source behavior or change the finding's release-boundary uncertainty. No source tree, readiness marker, artifact manifest, mapped jar, original finding or source review was modified.

## Scope and follow-up

- Identity correction only; no production code changes.
- No build, tests, runtime validation, wiki lookup, shared-source write or source-owner message was performed.
- The corrected snapshot is ready for a new independent identity/source review; no acceptance is asserted here.
- Default branch was `e7f89348a619cc9feb2d47aab7a87fbefbe7649d` when this task began. Merged updated `main` at `8ad316aa84a8704f99d042c2a649bfa23aa4365e` in merge commit `741cab9a72aa7fe1e1e5164001b6895307d2e3ed`; the incoming readiness inventory, coordinator queue and integration note do not conflict with this identity-only correction. The readiness inventory independently lists the same 1.18.2 source and artifact manifest hashes.
