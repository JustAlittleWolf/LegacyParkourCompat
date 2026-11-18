# Additive r1 verdict — corrected lava-presence source claims

**Scope:** Narrow rebind of the corrected 1.16.1 → 1.16.2 Mojmap author drafts against the already frozen source roster and prior audit. The original roster and verdicts remain unchanged. No new source, wiki, product, runtime, intermediate-version, or other-lane evidence was opened.

## Bound-file digest checks

Every bound input below was hashed programmatically during this rebind; the expected and computed values matched. The verifier threw on any mismatch before producing this verdict.

| Input | Absolute path | Expected SHA-256 | Computed SHA-256 | Result |
|---|---|---|---|---|
| corrected producer | C:\Users\Wolfi\.codex\visualizations\2026\11\17\01a1eb7b-ac27-7712-9259-3041950bc340\lane-lava-presence-source-1161-1162\lava-presence-producer.corrected-r1.md | fd01ed4bdca5f90b50181e120a8614c14cfdd738f53a701ffef6a67310bdb21d | fd01ed4bdca5f90b50181e120a8614c14cfdd738f53a701ffef6a67310bdb21d | PASS |
| corrected travel/jump | C:\Users\Wolfi\.codex\visualizations\2026\11\17\01a1eb7b-ac27-7712-9259-3041950bc340\lane-lava-presence-source-1161-1162\lava-travel-held-jump.corrected-r1.md | a6ec6cbb2fcbc0c53cb320038a45dc9adb43c0ae1408ee2eaf2a75a96c109b14 | a6ec6cbb2fcbc0c53cb320038a45dc9adb43c0ae1408ee2eaf2a75a96c109b14 | PASS |
| frozen roster | C:\Users\Wolfi\.codex\visualizations\2026\11\17\01a1eb7b-ac27-7712-9259-3041950bc340\lane-lava-presence-source-1161-1162-audit\source-roster.md | c1f91b937dee568f6eda8d42714f850092dc249f90f07e15ed5503bea6454e5e | c1f91b937dee568f6eda8d42714f850092dc249f90f07e15ed5503bea6454e5e | PASS |
| prior producer verdict | C:\Users\Wolfi\.codex\visualizations\2026\11\17\01a1eb7b-ac27-7712-9259-3041950bc340\lane-lava-presence-source-1161-1162-audit\lava-presence-producer.verdict.md | 74bf21b09c07daa3752ec209544bc4a2efbd2a152667883238b14546af3d17a3 | 74bf21b09c07daa3752ec209544bc4a2efbd2a152667883238b14546af3d17a3 | PASS |
| prior consumer verdict | C:\Users\Wolfi\.codex\visualizations\2026\11\17\01a1eb7b-ac27-7712-9259-3041950bc340\lane-lava-presence-source-1161-1162-audit\lava-travel-held-jump.verdict.md | 69c0584673a4b81ef008385e158676e4089db2b8c1ce37ca703238235a3ec202 | 69c0584673a4b81ef008385e158676e4089db2b8c1ce37ca703238235a3ec202 | PASS |

## Claim verdicts

### Producer/cache — accept corrected r1

The corrected draft's account matches the frozen source review: 1.16.1 resets `isTouchingLava` immediately before `checkInsideBlocks`, and the lava `LiquidBlock.entityInside` callback can set it; its `isInLava()` reads that flag. The separate 1.16.1 fluid cache clears first and returns after water, which can leave lava depth zero while the callback flag remains true. In 1.16.2, the lava flag/setter is absent, base tick always scans lava after water, and the predicate is `!firstTick && cachedLavaHeight > 0.0`. The sampler's `0.001` deflation, chunk gate, loop bounds and height comparison are covered by the frozen roster.

The corrected first-tick timing is accurate for the ordinary local-player path: `Entity.baseTick` sets `firstTick` false before `LivingEntity.tick` reaches `aiStep`; later same-tick held-jump/travel does not see the new first-tick guard. The `f - 0.0005` old-box / `f + 0.0005` deflated-box example is a valid source-level separating state when the lava cell is loaded, included by the old block scan, and is a member of `FluidTags.LAVA`. Old callback presence can persist into the next base tick while the deflated sampler stores zero, so a later lava-only consumer can distinguish the predicates. This establishes a concrete source precondition; it does not establish a normal-play trajectory or runtime parity.

### Travel/held jump — accept corrected r1

The prior independent comparison programmatically found the `LivingEntity.travel`, held-jump block and `jumpInLiquid` bodies identical after LF normalization; the corrected draft accurately keeps the behavior delta on the producer/predicate input. Its ordinary local-player first-tick ordering and water-contact qualification are correct. `jumpInLiquid(WATER)` and `jumpInLiquid(LAVA)` have the same `+0.04F` Y body, so tag selection alone in overlapping water/lava does not prove a movement change. The corrected lava-only airborne held-jump witness avoids that ambiguity: the old flag selects the lava impulse with zero sampled depth; the new zero-depth predicate selects no fluid jump. The new path also skips the lava travel branch under the stated local-control/fluid-effect/no-water conditions.

## Remaining scope

Effective membership of raw runtime `FluidTags.LAVA` data was not independently verified in the decompiled-Java source publication; applicability remains conditional on that membership. The near-surface state was not tested in-game, and no intermediate patch boundary is inferred. This narrow r1 review does not close full-pair coverage or runtime parity.