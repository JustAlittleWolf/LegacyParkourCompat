# F-003: Auto-jump uses a different first collision candidate order

- **1.17.1**, mojmap: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-003-autojump-candidate-order.md).
- **1.18.2**, mojmap: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-003-autojump-candidate-order.md).

## Difference

Both loops stop at the first intersecting AABB and use maxY.

If eligible entity and block shapes both intersect at different heights, order can change the candidate.

## Movement meaning

LocalPlayer#aiStep -> updateAutoJump -> CollisionGetter -> first AABB; entity filters and providers open.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-003-autojump-candidate-order.md).
