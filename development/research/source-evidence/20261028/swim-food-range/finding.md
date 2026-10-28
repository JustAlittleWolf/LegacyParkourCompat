# Swimming sprint food-stop mask across pinned source profiles

## Finding

In the selected exact decompiled client profiles from 1.13 through 1.21.4, a swimming player who is already sprinting loses sprint when the food predicate fails only if the player is also airborne and not holding sneak. The branch has the form `(!forward || !food) && !onGround && !sneak || !inWater`; Java precedence makes the water exit unconditional. This structure is present at every profile listed in `comparison.tsv`, including all three stable 1.13 profiles (1.13, 1.13.1, and 1.13.2) and each other release profile listed in the comparison. The first observed difference is 1.21.5: the food failure is an independent stop disjunct in `shouldStopSwimSprinting`, so ground and sneak no longer mask it.

For an unmounted survival player, the compared food predicate remains `foodLevel > 6.0F || mayFly` (older Feather/Yarn names differ). The 1.19.3+ helper also treats passengers as food-sufficient. The sprint-retention branch itself is client-local and reads existing hunger/ability state; this evidence does not concern producing hunger or calculating exhaustion.

The comparison covers the pre-feature 1.12.2 fence and all 40 release profiles listed from 1.13 through 1.21.5. Its 82 client and Entity source hashes were checked against the corresponding source manifest or hidden provenance source map. The coverage claim applies to those pinned profiles and source records.

## Cached swimming state and source iteration

1.12.2 is the pre-feature fence: its selected `Entity.java` contains no `updateSwimming()` method/call or `isSwimming()` getter, and its client player source has no swimming sprint-retention branch. Starting with 1.13, the selected `Entity` sources call `updateSwimming()` during the entity base/tick update; `updateSwimming()` reads the swimming state and updates the shared flag, while `isSwimming()` reads flag bit 4 (`getFlag(4)` through 1.14.3, `getSharedFlag(4)` beginning in 1.14.4). The local-player sprint-retention code later reads `isSwimming()` in its player tick/AI step. The update and getter line references for every pinned profile are in `comparison.tsv`; the client branch line references are there as well. This is the cached entity swimming flag, not a fresh water test substituted for `isSwimming()`.

## Separate forward-input change

The food-mask finding is independent of the forward test. Across 1.13, 1.13.1, and 1.13.2, the combined stop flag uses `movementForward < 0.8F || !food`; this old stop comparison uses a float literal. In 1.14.2/1.14.3, it becomes `!input.method_20622() || !food`; the Yarn `Input.method_20622()` predicate is `movementForward > 1.0E-5F`. Mojmap 1.14.4 expresses the same low-positive-forward predicate as `Input.hasForwardImpulse()` (`forwardImpulse > 1.0E-5F`). Subsequent listed profiles use that impulse predicate. The outside-water sprint-start threshold compares against the double literal `0.8` in 1.14.2 and 1.14.4. The low-forward swimming stop predicate remains the strict `> 1.0E-5F` float comparison; these predicates must not be conflated.

## Interpretation and evidence limits

This source comparison establishes the direct sprint-state write and its gates. Each listed client and `Entity` SHA-256 was checked against that profile's source manifest or hidden provenance source map; the provenance-file SHA-256 is pinned in `comparison.tsv`. It does not measure the resulting trajectory. Existing accepted source evidence for the 1.21.4 to 1.21.5 boundary is retained at `development/research/source-evidence/20261026/swim-retention/development/research/findings/1.21.4--1.21.5/swim-sprint-food-stop.md`; this file extends that evidence backward across the explicitly pinned profiles. No builds, tests, game launches, or generated sources were used.
