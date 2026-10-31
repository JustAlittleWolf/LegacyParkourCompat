# Final independent review: border-lid candidate 06e0765d

## Verdict

**ACCEPT the corrected candidate contract for the evidenced 1.16.0–1.16.5 old-side range.** Commit `06e0765d0fc25ce49d8d597f9e1ecb9d277211d6` moves both old-operation classes to `change/v1_16_2/` and annotates them `emulates = ParkourVersion.V1_16_2`. Under the exported resolver this makes the old implementations eligible for both `V1_16` (1.16/1.16.1) and `V1_16_2` (1.16.2–1.16.5), while the `V1_15_2` native markers remain the closer fallback for earlier selections. The accepted alias audit verifies the old-side operations across all six 1.16 releases.

Candidate commit reviewed: `06e0765d0fc25ce49d8d597f9e1ecb9d277211d6` (branch `fix/border-lid-geometry-20261031`). The prior ShulkerBox endpoint-expansion defect remains corrected. This is a review of the isolated candidate and assigned evidence only; no product implementation/history was inspected, and no build, tests, staging, or runtime launch was performed.

## Range and resolver verification

The exported `ParkourVersion` maps `V1_16` to 1.16 and 1.16.1, and `V1_16_2` to 1.16.2 through 1.16.5. `ChangeResolver.resolve` filters to changes whose emulated version is newer than or equal to the selected profile, then picks the closest such change. For both 1.16 profile groups, the moved `V1_16_2` class is eligible and is the closest candidate among the old-side implementation and the `V1_15_2` native marker. For pre-1.16 selections, `V1_15_2` remains the closer eligible fallback. No exact-profile gates were added.

This range now matches the alias evidence: the 1.16-side WorldBorder `StaticBorderExtent.updateBox` and ShulkerBox provider/encoding methods are member-identical across 1.16, 1.16.1, and 1.16.2–1.16.5; the new-side differences begin at 1.17 and remain in 1.17.1. The evidence provided does not extend beyond 1.17.1.

## ShulkerBox contract and gates

`ShulkerBoxLidCollisionShape.historicalBounds` still returns the supplied `vanillaBounds` unchanged only when the float progress equals `1.4901161193847656E-8F` and facing is WEST, DOWN, or NORTH. It returns empty for all other progress values and facings. These are the accepted reachable residual and negative-facing cases. The revision changes only the package and emulated-version annotation; the exact bounds contract and gates remain unchanged.

The hook Javadoc correctly requires the adapter to construct the old full one-cell discrete `ArrayVoxelShape` with the returned raw endpoints. It explicitly forbids passing the bounds through current `Shapes.create(AABB)`, which would quantize the negative residual endpoint away. That construction matches the 1.16 `Shapes.create` fallback for the provider AABB. The candidate intentionally leaves this adapter construction and its thin client-side call site to integration; those are integration requirements, not defects in this candidate contract.

## Static WorldBorder contract

The border operation body is unchanged apart from its package and annotation. It repeats the 1.16 static extent's asymmetric `Math.max`/`Math.min` endpoint clamps, floors minima, ceils maxima, sorts the coordinate pairs through AABB construction, then computes `INFINITY ONLY_FIRST`. The V1_16_2 version annotation is resolver-eligible throughout both 1.16 profile groups, and the lower V1_15_2 class retains native behavior for earlier profiles.

## Evidence

- Candidate commit: `06e0765d0fc25ce49d8d597f9e1ecb9d277211d6`.
- Neutral resolver contract: exported `ParkourVersion` and `ChangeResolver.resolve`.
- Source findings: `.native-source/20261031/1165-1171-finish45/worldborder-review/review.md`; `.native-source/20261031/1165-1171-finish45/shulkerbox-progress-independent-review/verdict.md`; `.native-source/20261031/1165-1171-finish45/shulkerbox-dynamic-collision-pair/report.erratum-1.md`; `.native-source/20261031/1165-1171-finish45/final-blind-audit/audit.erratum-1.md`; alias review `.native-source/20261031/border-lid-alias62/review.md`.
- Exact source members: 1.16.5 Mojmap `WorldBorder.StaticBorderExtent.updateBox`, `ShulkerBoxBlock.getShape`, `ShulkerBoxBlockEntity.getBoundingBox`, `Shapes.create/findBits`; 1.17.1 Mojmap `WorldBorder.StaticBorderExtent.updateBox`, `ShulkerBoxBlock.getShape`, `ShulkerBoxBlockEntity.getBoundingBox`, `Shulker.getProgressAabb`, `Shapes.create/findBits`.

No runtime parity claim is made. Integration must preserve client/profile guards and construct the ShulkerBox raw-coordinate shape as specified by the hook contract.
