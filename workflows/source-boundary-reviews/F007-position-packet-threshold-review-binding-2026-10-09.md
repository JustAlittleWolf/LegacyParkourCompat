# F-007 boundary review: identity and provenance binding

**Review date:** 2026-10-09  
**Decision preserved:** ACCEPT — source-only first-release boundary evidence  
**Scope:** Metadata and immutable-artifact binding only. This record does not repeat or extend the accepted source comparison.

## Verified immutable identities

The independent review authored at `80e37d3be7033c3fe103d6c76d97b831d4382be0` is bound as follows:

- Review path: `workflows/source-boundary-reviews/2026-10-09-F007-position-packet-threshold-boundary-review.md`
- Review Git blob: `94b1c616498d3c2e9f05b74a0cc3a5bbbd894a1b`
- Review raw SHA-256: `4d2eddf2002ac01b968b49c2587f56c883bdaad2ca3c27609bb0faac4885a0f9`
- Decision in the bound report: `ACCEPT — source-only first-release boundary evidence`

Its immutable target resolves exactly to the memo:

- Memo commit: `918217999a8fc6922ffc5a7b1e82ea6291d7f542`
- Memo path: `workflows/source-boundary-reviews/F007-position-packet-threshold-boundary-2026-10-09.md`
- Memo Git blob: `20b94e42eec772864d88b29236c94ae2ee624000`
- Memo raw SHA-256: `2c998711e3ad1525100f5271ef03baa08181646acd1f6597086c971648ca486d`

The memo blob at the cited commit matches the bound Git blob. The report's memo path, commit, blob, and raw SHA-256 agree with that memo identity. Its four source-publication provenance rows agree with the memo's ready-marker, source-manifest, artifact-manifest, and `LocalPlayer.java` identities for 1.17.1, 1.18, 1.18.1, and 1.18.2. No provenance-binding discrepancy was found.

## Disposition

Preserve the existing ACCEPT for the first changed release among those four exact publications. The accepted report explicitly leaves the 1.17.1–1.18.2 pair partial and does not claim a pair freeze, implementation result, client trajectory result, later correction outcome, or runtime validation. No source-behavior re-review was performed for this metadata-only check.
