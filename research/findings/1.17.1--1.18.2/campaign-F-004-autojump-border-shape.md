# F-004: Auto-jump collision candidates omit the old world-border shape

- **1.17.1**, mojmap: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-004-autojump-border-shape.md).
- **1.18.2**, mojmap: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-004-autojump-border-shape.md).

## Difference

1.17.1 auto-jump query may include border shape; 1.18.2 query omits it, with border collision handled elsewhere.

## Movement meaning

LocalPlayer#aiStep -> updateAutoJump -> getCollisions; general solver border path open.

Candidate set change proven; timer effect requires probe intersection and height threshold.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-004-autojump-border-shape.md).
