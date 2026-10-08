# Identity review: bed-landing reconciliation report (2026-10-08)

## Decision

**ACCEPT — proof-only reconciliation for the accepted ordinary fresh-entity bed-contact witness.** The report binds to the accepted source snapshot, accurately maps that bounded witness to the existing `V26_1` implementation, preserves the older bed-bounce override, and adds no Java/provider changes. It does not claim broader restitution parity, a completed source pair, a release boundary, or runtime validation.

This follow-up resolves the candidate-availability blocker recorded in review commit `d027b9db7ece15beb68c85df67adde2ea5674669`. That earlier `REQUEST CHANGES` report remains unchanged; its finding reflected that the author object was absent from the primary repository at that time.

## Exact bindings

- Author report commit: `d506bfcab40186edaeb21a13f41768652033d039`, branch `fix/bed-landing-restitution-2026-10-08`.
- Author report path: `workflows/fix-implementation/runs/26.1.2-bed-landing-restitution-reconciliation-2026-10-08.md`.
- Git blob: `13eda16e2d87c360ec819e994a6d35b256b614a7`; exact file length: 10,366 bytes; raw SHA-256: `9d6861de6caa820f160ee6a49404c0692387ba7863ba299cfc1fe418769f49c5`.
- The author branch was fetched read-only from the recovered local checkout into `refs/review/bed-landing-restitution-author-d506bfcab40186edaeb21a13f41768652033d039`. The report's path/blob and working-file SHA match the supplied identities.
- Accepted source snapshot: `d90a4c13d7ac63984a758aa3f3a6ce320cecac69:workflows/source-campaign-2026-10-07/26.1.2--26.2/findings/F-26.2-BED-LANDING-RESTITUTION.md`, blob `7762fc6328e89c66afe6c28c563c6a4bd7dd1d47`, raw SHA-256 `c776cc145aa9e6373d83b24c209fb1c025d914dc63c64041b4aaf7d515fc227d`.
- Source acceptance: `daab0b806cf4fb33185ac294bea18d4f4a108e7f:workflows/source-campaign-2026-10-07/independent-review-bed-ordinaryfall-witness-2026-10-08.md`, accepted only for the corrected fresh-entity witness.
- Reconciliation baseline: current local `main`, `d8f3956602da94bf0cf67753cc0a9f4665397729`. It is an ancestor of the author commit. Comparing the author commit to this baseline shows the reconciliation report as the sole changed path; no production-code change is part of this report.
- Prior implementation-path review: `d027b9db7ece15beb68c85df67adde2ea5674669:workflows/source-campaign-2026-10-07/independent-review-bed-landing-restitution-reconciliation-2026-10-08.md`. The author report's implementation correspondence agrees with that independent trace at the same baseline.

## Reconciliation disposition

The authored report correctly limits its conclusion to the accepted fresh-entity setup and exact bed-contact witness. At the `V26_1` target, the existing collision handler dispatches the registered bed landing behavior; shift suppression delegates to the zero-Y base continuation, and the unsuppressed negative-Y bed response uses `0.66F` with the living-player factor `1.0`, without the newer gravity cutoff. This matches the accepted A-side witness. With `CURRENT`, the native 26.2 response remains active and its low-speed threshold suppresses that witness's bounce.

The report also correctly traces the older `V1_11_2` `BedBounce` override: it is keyed to the historically available red bed, returns zero for players, and is honored by the landing behavior. Per-mechanic resolution selects it for targets through 1.11.2 and excludes it for 26.1.2. The report leaves the first 1.12 patch boundary unknown instead of extending the 1.12.2 evidence to an exact release claim.

The cited `V26_1` provider registration, dynamic `BedBlock` ID registration, and keyed behavior are present at the reviewed baseline. If a later central-catalog migration relocates these registrations, that migration still needs its own preservation check for the provider and every bed's `block.landing:<registry-id>` entry.

## Scope

This is a no-code report review, not a source-pair freeze. The author report preserves the accepted source scope and existing partial/open status. No Java/provider edits, tests, builds, decompilation, runtime/game/TAS/Gym/server/Docker operations, or pushes were performed.
