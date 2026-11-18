# Independent author review verdict

## Binding

Reviewed author comparison SHA-256: `eec89743cb57c69122cb6616da2fcbe2f51fb9bd692a001a0ba342d3ba7e614a` (recomputed; matches assignment).

Independent blind freeze SHA-256: `60a48ca6772a7843bbefd712bb8dc39e0c233ab58d7fee22a0d9a168d631960f` (recomputed; preserved).

## Verdict by claim

- **Pre-travel player threshold gates: ACCEPT.** The comparison has the correct live LocalPlayer → LivingEntity tick/aiStep path and the exact strict predicates. Player X/Z share `horizontalDistanceSqr() < 9.0E-6`; Y independently uses `Math.abs(y) < 0.003`. The write is after equipment tick and before input, jump and travel in each release. The non-player per-axis X/Z branch does not apply to Player. This supports equivalence when the velocity vector passed to the reset is finite.
- **Bounded ordinary dry travel witness: ACCEPT WITH BOUNDARY.** The stated same-state witness excludes collision, external velocity responses and the other named branches. Within it, the compared threshold and ordinary dry travel are equivalent. The comparison expressly does not establish the excluded paths.
- **Post-travel/reset-writer coverage: REJECT AS INCOMPLETE.** The author comparison explicitly sets collision response aside and gives no paired `Entity.java` evidence, so it does not close the requested reset-writer inventory through tick end. Exact source review finds the relevant paired `Entity.move` order equivalent: the stuck-multiplier branch can zero the whole vector; after collision clipping, a horizontal collision zeroes blocked X/Z components and preserves Y; the fall-on callback runs on vertical clipping; then the horizontal block-speed factor is applied. These are in/during travel, not the pre-travel threshold snap. After travel, block-effect and entity/attack-push writers are separate excluded paths; no second generic threshold snap occurs before `LivingEntity.tick` and `LocalPlayer.tick` finish. This source equivalence is confirmed by the independent freeze, but was not demonstrated in the submitted comparison and must remain a coverage residual for that artifact.
- **Non-finite setter: SEPARATE DELTA, NOT COVERED BY THE FINITE EQUIVALENCE.** In 1.21.10 `Entity.setDeltaMovement(Vec3)` assigns unconditionally (lines 3619–3621); in 1.21.11 it assigns only when `Vec3.isFinite()` (lines 3643–3647). A non-finite component therefore suppresses the entire vector write in 1.21.11, including a write that would otherwise zero a small finite component. No reachable non-finite witness is established for the bounded ordinary finite dry path. Do not claim unbounded equivalence or silently fold this setter change into the unchanged threshold predicate.

## Residuals

The author comparison is acceptable for the finite pre-travel gate and its bounded no-collision dry witness. It is not a complete report of post-travel reset writers and does not mention the non-finite setter change. The independent freeze has minor transcription artifacts (a stray parenthesis and doubled apostrophes); its evidence, verdict, and recorded SHA remain unchanged.
