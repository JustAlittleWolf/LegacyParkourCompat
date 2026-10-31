# Independent re-review — open Shulker local-player escape finding

## Verdict bound to new revision

**ACCEPT** the immutable draft `.native-source/20261030/1152-1165-finish43/canonical-findings/1.15.2--1.16.5/open-shulker-local-player-escape.md`, SHA-256 `9307423c3e10d9300082daf7b67512e3b28a7b79015f9c3a8321d628ea29a738` (recomputed and matching the assigned revision). This verdict supersedes the earlier **REVISE** only for this new hash; the earlier verdict remains bound to the prior draft SHA.

## Recheck

- All cited source ranges are accurate against the literal 1.15.2 and 1.16.5 Mojmap trees. In particular, the corrected `CollisionSpliterator` range starts at line 71, which contains the predicate test before line 74's collision-shape lookup; `CollisionGetter` lines 62–68 forwards the supplied predicate to that spliterator.
- The added endpoint shape and BE ranges establish that both Shulker providers construct their shape from the matching BE's facing/progress box. `OPENED` stabilizes at progress `1.0F` on both endpoints. The new registration's `isClosed` predicate rejects that state.
- The added standing-dimensions, position-to-box, and 1.16.5 `EntityDimensions.makeBoundingBox` citations support the concrete player coordinates. The stated four probe columns and strict old WEST/EAST/NORTH/SOUTH tie order reproduce the `-0.1, -0.1, +0.1, +0.1` X writes with final `+0.1`; the new filtered query returns non-suffocating and performs no velocity write.
- Both cited `super.aiStep()` continuation lines occur after the probes. The conclusion stays scoped to this source-level pre-travel local-player velocity response and does not claim a later trajectory or runtime result.

The accepted witness evidence identity remains `.native-source/20261030/1152-1165-finish43/suffocation-provider-witness-independent-audit/report.md`, SHA-256 `214e87591315d984782e035e51261666d7ca96f6e02648e14d18baaa5906547d`. The newly cited `EntityDimensions.java` source hash also matches its corrected direct manifest entry (`6a4ded79d936bb740232ae923372e47d24a297a35eaba22e9d18fbd637a7eedf`). No further correction is required for this draft revision.
