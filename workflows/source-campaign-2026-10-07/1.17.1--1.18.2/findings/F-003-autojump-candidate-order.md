# F-003: Auto-jump uses a different first collision candidate order

- Older version A: 1.17.1
- Newer version B: 1.18.2
- Mechanic / coverage slice IDs: T-AUTOJUMP-ORDER
- Classification: changed behavior
- Confidence: candidate; filters/geometry remain open
- Applicability: historical player behavior
- First changed release: unknown within (1.17.1, 1.18.2]
- Runtime validation: not performed

## Paired evidence

A LocalPlayer 956-965 and CollisionGetter 55-59: block before entity. B LocalPlayer 952-960 and CollisionGetter 65-75: entity before block. Class hashes in run.md.

## Source-level difference

Both loops stop at the first intersecting AABB and use maxY. If eligible entity and block shapes both intersect at different heights, order can change the candidate.

## Reachability and dependencies

LocalPlayer#aiStep -> updateAutoJump -> CollisionGetter -> first AABB; entity filters and providers open.

## Consequence and uncertainty

Candidate: order/early break proven; concrete overlap/filter parity not established, so jump timer change remains unproven.

## Handoff

Source discovery only; implementation deferred.
