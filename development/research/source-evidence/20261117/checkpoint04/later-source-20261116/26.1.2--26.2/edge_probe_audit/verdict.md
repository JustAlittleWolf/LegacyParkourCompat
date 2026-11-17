# Independent source audit: edge backoff and support query

Candidate: `edge_probe/REPORT.md`, SHA-256 `CE90AFEF442D8AF16DCD2FC6AEBA7A89D88DDF8FA969E34D66AA9B7DC873AD32`.

## Verdict

**Movement claims verified for the inspected slice; provenance is qualified by missing original output directories.** The report is accurate that its endpoint comparison is partial and does not establish pair freeze, implementation acceptance, or runtime parity. The requested edge-backoff, support-distance, support-selection, ordinary-move-gate, and LocalPlayer-forwarding statements match the exact source checked at both endpoints, with no difference in those operations.

## Evidence checks

- Both ready markers report `ready`, and their `versionId`/`mapping` match their provenance `versionId`/`namespace` (`26.1.2` / `unobfuscated`; `26.2` / `unobfuscated`). Ready-declared source and artifact manifest files exist under each `decompiled_minecraft/<version>/` root. Their actual SHA-256 values match the ready marker values and the candidate report.
- The source hashes in the report for `Entity.java`, `Player.java`, `LocalPlayer.java`, `CollisionGetter.java`, and `BlockCollisions.java` match both the on-disk files and corresponding source-manifest entries. I also checked the unlisted `LivingEntity.java` hashes against disk and each source manifest: 26.1.2 `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd`; 26.2 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.
- `Player.maybeBackOffFromEdge`, `isAboveGround`, and `canFallAtLeast` match across the endpoints: flying/upward/non-SELF-or-PLAYER/non-shift gates, 0.05 X then Z then diagonal trimming, unchanged Y, fall-distance short circuit, and the inset/downward support-query box are as reported. `LivingEntity.maxUpStep` reads the step-height attribute as `float` and applies the same Player-passenger `Math.max(..., 1.0F)` rule; `Entity.maxUpStep` returns `0.0F` at both endpoints.
- `Entity.move` takes the `noPhysics` path before edge backoff; otherwise piston limiting/zero return and stuck-speed handling precede the same `maybeBackOffFromEdge` call. The checked `LocalPlayer.move` overrides are identical: `super.move` followed by float X/Z deltas, auto-jump update, and walked-distance update.
- `Entity.checkSupportingBlock` has the same `1.0E-6` support slab, current query, conditional reverse-horizontal-movement query, and grounded/airborne support-state writes. `CollisionGetter.findSupportingBlock` has the same `BlockCollisions` query and squared center-distance / `BlockPos.compareTo` ordering. `CollisionGetter.noCollision(entity, box)` includes block, entity, and world-border checks. The identical `BlockCollisions.java` hash confirms its broad-phase one-block margin and exact-shape intersection filter for this query.

## Provenance qualification and open scope

Both ready markers' `outputPath` values point to `build/movement-campaign-2026-10-07/ready/<version>/unobfuscated`, which is absent in this checkout. The published source trees and their root-sidecar manifests are present and hash-consistent, so the inspected source claims are reproducible from those trees; the original declared output locations and downloaded artifacts cannot be re-opened here. The report correctly leaves block-shape/state/provider and neighboring-block dependency closure open. No broader upstream closure or pair freeze is supported by this audit.
