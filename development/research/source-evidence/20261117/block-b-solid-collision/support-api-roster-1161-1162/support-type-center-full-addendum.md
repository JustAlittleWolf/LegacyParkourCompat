# SupportType API coverage addendum

This is an immutable supplement to `support-api-roster-audit.md`, expanding that audit from the RIGID predicate to the adjacent CENTER and FULL API paths after cross-checking the independently frozen shape-provider roster. The original verifier remains unchanged.

## CENTER

1.16.1 `world/level/block/Block.java:67,219-225` first checks the `Direction.DOWN && UNSTABLE_BOTTOM_CENTER` exception, otherwise testing that the centered box `Block.box(7.0, 0.0, 7.0, 9.0, 10.0, 9.0)` is contained by the selected support face through `!Shapes.joinIsNotEmpty(..., ONLY_SECOND)`. 1.16.2 `Block.java:214-219` preserves the same tag/direction gate and delegates to `SupportType.CENTER`; `SupportType.java:18-26` declares the same box and same face-support containment expression. No predicate-order or Boolean result change is evident.

## FULL and its cache representation

1.16.1 `Block.isFaceSturdy(state, getter, pos, direction)` is `isFaceFull(state.getBlockSupportShape(getter, pos), direction)` (`Block.java:228-234`). 1.16.2 `SupportType.FULL.isSupporting` delegates to that same `Block.isFaceFull` expression (`SupportType.java:12-16`), and the one-argument `BlockState.isFaceSturdy` still selects FULL (`BlockBehaviour.java:703-705`). The cache's new type×direction layout therefore preserves the old FULL cached values while adding the RIGID/CENTER results. Both cache constructors use the same `EmptyBlockGetter.INSTANCE` and `BlockPos.ZERO` to compute their cached predicates, and both skip caching for `hasDynamicShape()` states (`BlockBehaviour.java:386-389` / `387-390`).

## Verdict

The support API refactor's three enum operations are all source-equivalent to the old inline operations: FULL delegates to the prior full-face helper; CENTER reproduces the prior center-box test and unstable-bottom gate; RIGID reproduces the prior ring test, with the old full-collision/full-face short circuit redundant because full support covers the ring. This adds no new movement difference to the main verifier's bounded verdict.
