# F-004: Auto-jump collision candidates omit the old world-border shape

- Older version A: 1.17.1
- Newer version B: 1.18.2
- Mechanic / coverage slice IDs: T-AUTOJUMP-BORDER
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.17.1, 1.18.2]
- Runtime validation: not performed

## Paired evidence

A LocalPlayer 956-959 and CollisionSpliterator#worldBorderCheck 103-116 emits nearby border shape. B LocalPlayer 952-953 uses CollisionGetter 67-80 without borderCollision; hashes in run.md.

## Source-level difference

A auto-jump query may include border shape; B query omits it, with border collision handled elsewhere.

## Reachability and dependencies

LocalPlayer#aiStep -> updateAutoJump -> getCollisions; general solver border path open.

## Consequence and uncertainty

Candidate set change proven; timer effect requires probe intersection and height threshold.

## Handoff

Source discovery only; implementation deferred.
