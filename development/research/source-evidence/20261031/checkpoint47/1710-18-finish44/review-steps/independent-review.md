# Independent review: 1.7.10 to 1.8 step candidates

Scope: source-only review of the three assigned drafts. Verdicts bind to these exact draft contents and source roots; they do not establish runtime parity or pair coverage.

## Exact evidence identity

- 1.7.10 Ornithe Feather provenance SHA-256: `11bada441ebe07661402d1c40e1c490b78b279ecaa084f9a4dc3ae2dd54caf6f` (matches assigned identity).
- 1.8 Ornithe Feather provenance SHA-256: `52c8312c34a1a491d4a200c84e90f5d8d95c7e5caa9571f864d8e70f9737db05` (matches assigned identity).

Direct SHA-256 checks match the provenance `sources` digests for each file below.
Exact source roots and Feather mapping input identities:

- 1.7.10 root `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.7.10\ornithe-feather`; Feather Gen2 `1.7.10+build.2`, mergedv2 SHA-256 `1a22781aa3bac248e25c619842ec2ec5c126d015436d528d411a9e85a6f491a7`.
- 1.8 root `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.8\ornithe-feather`; Feather Gen2 `1.8+build.2`, mergedv2 SHA-256 `3c020d1588b4aab403f5df077ca91b6e3776da2823d3b55202f4616f0d5ae5fe`.

| Source file (relative to exact source root) | 1.7.10 SHA-256 | 1.8 SHA-256 |
|---|---|---|
| `net/minecraft/entity/Entity.java` | `af4ef7b11bc709d96bd3eea1260a61d38a1a04996bdf87f2a77c459ddf070a69` | `2be081abe1720f89d7ab90f782b8411e71bc5b8a6b53966b49a2380e3c47ffd9` |
| `net/minecraft/entity/living/LivingEntity.java` | `5c1c5cf82d98c2f57a65b857fe02975b749c0562e4c1437ab5f072e202ca941c` | `645624913e265cad50293f659812a35c3c4303d1e8bdafdcca4eaf2e8ed5c69d` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `350901699e449537372e1c4af6a79ae256de39638cdad049dfc7800b6607791c` | `13fa1367b71035efbfccc593de57838268b10a8ceebb0aa76ad3d819b44cb0ca` |
| `net/minecraft/block/BedBlock.java` | `90def62a050c87c5172b6a925d542e731b533a8c9393bb36a2e5ca77c82dc315` | `f11cdfc42b9a3b22ea1f400693e473f2de96a96eae4b2ec79fd1322d21a3ad0c` |
| `net/minecraft/block/SnowLayerBlock.java` | `ea7497ccdeb306339315149c20c299b4bd95850dfc5f511604b668bb60c3dabc` | `e68b477b30dfc38924f49859cd9e25722c5bc6ca6d5ee09d681285939ac415b4` |
| `net/minecraft/util/math/Box.java` | `0ea7fb1600f9e5d7566593310830d509fec44aab792db6d2d7cfa4cebefc6f0a` | `2d270470a11082e1cf9f772c94ecff9ecbf5430be78947dd152e2e72c20bbc52` |

Additional gate evidence: 1.7.10 `net/minecraft/client/entity/living/player/InputClientPlayerEntity.java` SHA-256 `1e37eeaebb22b759bfcae11c8c08f24b37dff7ebf5f5b17531f92e6cfbb48794`.

## Verdicts

### `step-height.md` — ACCEPT

`LivingEntity` constructor assigns `stepHeight = 0.5F` in 1.7.10 (line 127) and `0.6F` in 1.8 (line 131). `PlayerEntity` calls `super(world)` and does not assign the field; a source search found only the LivingEntity default and non-player Enderman/horse overrides. Player width/height are set to `0.6F, 1.8F` in both trees (1.7.10 PlayerEntity line 398; 1.8 line 411). The ordinary grounded, non-sneaking player satisfies the step gate without relying on the 1.7.10 sneak-offset predicate.

Bed collision shape is 9/16 high in each source (`BedBlock.setDefaultShape`: 1.7.10 lines 171–173; 1.8 lines 128–130). With a clear overhead and a horizontal request otherwise clipped by the bed, a 0.5 lift leaves 1/16 vertical overlap, while a 0.6 lift clears the top. The ordinary movement branch then can move over it. This consequence depends on the constructor default and step-height threshold; it does not depend on whether 1.8 uses one or two candidates. The draft correctly keeps it independent from the solver-restructuring claim.

### `step-eligibility-sneak-landing.md` — ACCEPT WITH WITNESS DETAIL

The old step gate is `stepHeight > 0 && var39 && (var20 || eyeHeightSneakOffset < 0.05F) && horizontalClipping` (1.7.10 Entity line 494). Here `var20` was captured before movement as `onGround && isSneaking() && this instanceof PlayerEntity` (line 409); `var39` is computed after vertical clipping as `onGround || (requestedY != clippedY && requestedY < 0)` (line 470). The new gate at 1.8 line 459 retains positive step height, `var57` support, and horizontal clipping, and drops the offset condition. The 1.7.10 local input raises the offset to `0.2F` while sneaking (InputClientPlayerEntity lines 151–153), then `Entity.move` multiplies it by `0.4F` (line 391) before the gate. In Java float arithmetic, that is approximately `0.080000006F` (often described as 0.08); it is strictly greater than `0.05F`, so the old offset comparison is false. An airborne sneaking player has `var20 == false` at call entry, even if it lands during this move.

Concrete geometry isolating this gate: ordinary player box width 0.6, center `(x,z)=(0.69,0.5)`, feet at `y=1.08`, onGround false, sneaking true; full floor at y=0 and a two-layer snow collision box at `(1,1,0)` with top `1.125`; request `(0.10,-0.10,0)` (so downward motion is actually clipped at the floor and `var39` is true). Let `stepHeight=0.5F` in both comparisons so this witness does not depend on the default-height change. The downward motion clips at the floor to feet y=1, making `var39` true; normal horizontal motion clips from 0.10 to 0.01 against snow. `var20` is false and the old scaled offset fails `<0.05F`, so 1.7.10 keeps the clipped box (center x=0.70, feet y=1). The 1.8 gate enters step resolution; with no overhead obstacle both candidates have 0.10 horizontal progress, descend onto the snow top, and finish center x=0.79, feet y=1.125. The resulting boxes differ. Snow's two-layer collision height is 0.125 in both exact sources (1.7.10 SnowLayerBlock lines 31–35 and metadata shape lines 58–61; 1.8 lines 40–45 for LAYERS=2). This is a source-derived geometric witness, not a runtime result.

Recommended precision correction in the draft: write `approximately 0.080000006F (i.e. > 0.05F)`, because the operation is float arithmetic rather than an exact decimal 0.08. The supplied text has the gates right but should add the concrete obstacle/motion/result above to meet the review assignment.

### `step-solver-swept-clearance-candidate.md` — ACCEPT

The candidate distinction is verified in the exact code. In 1.7.10, the solver builds the collision list from `shape.expanded(dx,dy,dz)` (Entity line 503), but applies upward `intersectY` against the unexpanded current shape (line 506). It then clips X/Z at the raised shape and descends. In 1.8, candidate A first expands the start box horizontally by `(dx,0,dz)` and clips upward clearance against that swept box (lines 469–477); candidate B clips upward against the unexpanded start box (lines 492–499). Squared horizontal distances select A only on strict `>` (lines 514–524), so ties choose B; final step output restores ordinary movement on `>=` (lines 533–538).

The submitted snow/ceiling witness is numerically consistent, provided the unspecified Z center is `0.5` (so the player overlaps blocks in z). Use feet y=1, center `(x,z)=(0.69,0.5)`, player width 0.6 and height 1.8, full floor at y=0, two-layer snow at `(1,1,0)` (collision top 1.125), full overhead block at `(1,3,0)`, and grounded non-sneaking motion `(0.10,-0.08,0)`. Ordinary movement clips X to 0.01. In 1.7.10, the collision list includes the ceiling because the expanded sweep reaches x=1, but upward clearance checks the original box ending at x=0.99, so the ceiling does not limit its 0.5 lift. The raised box overlaps the ceiling vertically; X therefore clips back to 0.01, and the final box is restored to ordinary movement. In 1.8 candidate A's swept vertical-clearance box overlaps the ceiling and limits rise to `3 - (1 + 1.8F)`, approximately 0.20000005. This makes the raised player top exactly touch (not penetrate) the ceiling underside. `Box.intersectX` uses strict vertical-overlap tests (`box.maxY <= obstacle.minY` exits), so the exact face does not block horizontal movement. Candidate A moves the full 0.10 and descends onto snow at y=1.125; candidate B gets 0.5 lift, clips X to 0.01 under the ceiling, and loses. A's squared progress is 0.01 versus B's 0.0001, so A wins; final center x=0.79. In the old solver, the 0.5 rise does not permit the horizontal sweep and ordinary movement is restored, leaving center x=0.70, feet y=1. The witness therefore distinguishes the solver despite the 0.5F default lift also being enough to clear the 0.125 snow layer.

Required edit to the draft: explicitly specify player center `z=0.5`; otherwise the stated blocks at z=0 need not overlap the player box, and the numeric witness is under-specified. The strict-face explanation above should be retained; under the exact comparisons, touching the ceiling face is allowed. The candidate's stated numeric result is correct once z is specified.

## Operation separation

- The constructor step-height delta changes the reachable lift ceiling and is independently witnessed by the 9/16 bed.
- The sneak-offset predicate removal changes whether the solver is entered at all for the specified airborne sneak landing; the gate witness holds both stepHeight values fixed at 0.5F.
- The solver restructuring changes the upward-clearance reference box and candidate selection; its witness uses grounded, non-sneaking movement, so the old sneak-offset gate is satisfied independently.
