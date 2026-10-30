# Final reconciliation supplement: six independent movement operations

This new note is bound to the existing ordinary-core source candidates and blind audit. Do not edit the bound files; each operation below is a distinct temporary source finding/claim for reconciliation, not a permanent finding or acceptance verdict.

## Bound claim and candidate hashes

- `report.md`: SHA-256 `84ED37D35E8A1932868516081AF999D88C9ADB15834E90A4B006E90B20281E6C`.
- `supplement.md`: SHA-256 `58B28BCFB820A88B49B1C4D65A7DC818DF21C0A5DDA6419EE5E316507DFFADBB`.
- `audit-followup/audit-followup.md`: SHA-256 `739E8152CDA3769D63368F1433FB2CE57548CC4E39B6CA031C601BB5E5F2705F`.
- `audit-followup/sprint-water-input.md`: SHA-256 `6C4C4762E74A674F952EF2B3316E1A94398C1B757403C67DFB86D1AB8726D9A6`.
- `audit-followup/water-contact-travel.md`: SHA-256 `0EB6BC55631EB94EBBFCB5E53E7ED7549FAF8F5ACB83F51A22B4528FEFF842FF`.
- Ordinary blind audit: SHA-256 `92d425d0ee158f9e4d690dcdfdac7dba4114127b4eae752dbae9c52ba8b5e1c5`.

## Pair evidence identity and hashes

The exact pair is 1.12.2 -> 1.13.2, namespace `ornithe-feather`, Feather mapping. Exact release markers, mapping artifacts and source file hashes are recorded in the bound report; relevant checked source SHA-256 values:

| Release | Relative source file | SHA-256 |
|---|---|---|
| 1.12.2 | `net/minecraft/entity/Entity.java` | `80F091BF32166C88CF8BBD31CAF72D84FA16224410733C7D2A0F00563F294A0A` |
| 1.13.2 | same | `1D6EC8B80F74635401745C2C027BF36555C85348CA5693764F2668363B17D269` |
| 1.12.2 | `net/minecraft/entity/living/LivingEntity.java` | `190E9AC551538E015D9E4D6C42856E5BA32B593131CF6D93895E7B29533F1EE6` |
| 1.13.2 | same | `BB691358C9A43C9F46E85575BF4D0A4AD671D0EB912502ACC3A6A3F625E42F1C` |
| 1.12.2 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `01A58E94D8C6FF98A8E3794227CDC76A5FCBDBAD795C70C9CF28854AFF9823CC` |
| 1.13.2 | same | `2583495F3A02B4791AA036E6A8D354D7596C4984761969A0F29B29D8D9BF42BF` |
| 1.12.2 | `net/minecraft/entity/living/player/PlayerEntity.java` | `E4E0FDBE07A7D0A0AE4A70CBB6739A287C9D045A4A12B409895C220B5D91FE1E` |
| 1.13.2 | same | `4ED22F6C5A3C55D67EED782070AC722201DF4D624ADBC90779F1FD29C2876633` |
| 1.12.2 | `net/minecraft/world/World.java` | `E9FA9B8D6D31AD57A5B876F5F63A5E3C554A23437DECF1845A493DAF48233594` |
| 1.12.2 | `net/minecraft/util/math/Box.java` | `F788B8146B14F299CCB58EA0854609F845C298A963295D503DE2E30E15D66F3A` |

## Temporary source operation A: water-jump selection gate

**1.12.2 evidence:** `LivingEntity.mobTick()` lines 1827-1839 routes a held jump to `jumpInWater()` whenever `isInWater()` is true, otherwise lava impulse, otherwise normal jump only on ground with `jumpingCooldown == 0`. `jumpInWater()` lines 1413-1415 adds `0.04F` to vertical velocity. The in-water state comes from `Entity.checkWaterCollisions()` during base tick (Entity.java:948-963).

**1.13.2 evidence:** `LivingEntity.mobTick()` lines 1903-1917 selects ordinary jump only when `(onGround || (0 < f_85121000 && f_85121000 <= 0.4)) && jumpingCooldown == 0`, subject to its enclosing zero/shallow-height gate. Positive fluid height above that shallow range routes the held jump to `m_74407200(WATER)`; that method adds `0.04F` (LivingEntity.java:1470-1472). An airborne player with positive water height therefore receives the upward impulse instead of ordinary jump. The height is refreshed by Entity.checkWaterState calling the fluid scan and storing `f_85121000` (Entity.java:961-981, 2509-2570).

**Difference and movement meaning:** the jump selection now depends on computed fluid height, on-ground status and cooldown. The impulse itself remains +0.04F; do not claim it was newly introduced. Grounded shallow water can select normal jump when cooldown is zero; positive deeper/airborne water routes to fluid impulse. Exact shared gate conditions and fluid-height producer are paired in the cited bodies.

## Temporary source operation B: existing-water vertical gravity update

**1.12.2 evidence:** `LivingEntity.moveRelative()` water branch lines 1555-1584 computes depth-strider input scale, calls movement, applies `velocityY *= 0.8F`, then subtracts `0.02` if gravity is enabled (line 1579).

**1.13.2 evidence:** corresponding water branch lines 1617-1650 retains depth-strider interpolation and vertical drag, but applies gravity only under `!isNoGravity() && !isSprinting()`. It normally subtracts `d / 16.0` after the move and vertical drag, with `d=0.08` (0.005); when falling Slow Falling selects `d=0.01` (0.000625). A near-zero case snaps to `-0.003` when the stated comparisons match (lines 1644-1649).

**Difference and movement meaning:** for the existing nonsprinting, gravity-enabled water path in ordinary conditions, post-move vertical velocity changes by -0.005 rather than -0.02. The new snap condition changes near-zero cases; sprinting suppresses gravity. Slow Falling's altered `d` is a new effect interaction and must remain separate from this default existing-water operation.

## Temporary source operation C: local sprint eligibility and stopping in non-submerged water

**1.12.2 evidence:** `LocalClientPlayerEntity.mobTick()` lines 694-745: direct sprint-key start at 734-741 tests forward input, food/flight, item use, blindness and current sprint state but does not test water. A player otherwise eligible can start sprinting while `isInWater()` is true and eye-submersion is false. Stop logic at 743-745 checks forward input, horizontal collision and food/flight only.

**1.13.2 evidence:** `LocalClientPlayerEntity.mobTick()` lines 698-758: direct-key start at 738-746 requires `!isInWater() || isSubmergedInWater()`, so shallow non-submerged water blocks that start. Stop logic computes `bl7 = bl6 || collidingHorizontally || (isInWater() && !isSubmergedInWater())`; however, this water stop is applied only when `isSwimming()` is false (748-758). The swimming branch instead checks its own airborne/non-sneaking plus low-forward/food condition or loss of water; it does not directly stop solely on non-submersion. The separate 1.13.2 double-tap path (723-736) adds `onGround || isSubmergedInWater()` and is a swimming-era addition; do not combine that new feature with this pre-existing direct-key shallow-water gate claim.

**Difference and movement meaning:** initiation by direct sprint key becomes disallowed in non-submerged water and nonswimming sprint gets a shallow-water stop. Already-swimming players are governed by a distinct condition and may retain sprint through a shallow transition. Do not state that every sprinting player unconditionally stops in shallow water.

## Temporary source operation D: water-contact predicate and height producer

**1.12.2 evidence:** `Entity.checkWaterCollisions()` lines 948-963 sets the `inWater` state after special casing boats. Its contact query passes `getShape().grown(0.0, -0.4F, 0.0).contract(0.001)` to `World.applyLiquidDrag`. `Box.grown(dx,dy,dz)` computes `minY - dy` and `maxY + dy` (Box.java:132-140); therefore `dy=-0.4F` contracts the box by 0.4 at each vertical face, followed by the 0.001 contract. The separate `Entity.checkWaterState()` line 945 uses `grown(0,-20,0)`; that is a distinct tall query, not the -0.4 contact geometry. `World.applyLiquidDrag()` scans cells and material/surface intersection (World.java:1579-1620). The 1.12.2 contact routine stores only the boolean, no fluid depth value.

**1.13.2 evidence:** `Entity.checkWaterState()` lines 961-977 delegates water contact to `m_69693160(WATER)`. That method contracts the entity box by 0.001, scans tagged FluidState cells, tests surface height against `box.minY`, records maximum depth, and returns the contact boolean (Entity.java:2509-2570). The same call consequently produces `inWater` and `f_85121000`, which the jump selection reads.

**Difference and movement meaning:** contact changes from a materially selected liquid query over a box narrowed vertically at both ends to a tagged-fluid scan over nearly the full box with an explicit fluid-surface-at/above-box-bottom test. The new depth value also feeds water-jump selection. These predicate/height semantics are a distinct operation from the current-force calculation below.

## Temporary source operation E: fluid-current force (collision/fluid lane dependency)

**1.12.2 evidence:** within the same `World.applyLiquidDrag()` producer, each matching block contributes via `Block.applyMaterialDrag`; if the aggregate vector is nonzero and `entity.hasLiquidCollision()`, the vector is normalized and `0.014` times the unit vector is added to velocity (World.java:1594-1618).

**1.13.2 evidence:** `Entity.m_69693160()` reads `FluidState.getFlow()`, scales sampled vectors when maximum surface depth is below 0.4, averages them by sampled cell count, normalizes only for non-player entities, and adds 0.014 times the resulting vector when nonzero (Entity.java:2532-2567). This operation is called by `checkWaterState()` before it stores the depth.

**Difference and movement meaning:** old normalized block-drag aggregation and new depth-scaled, averaged fluid-flow aggregation can produce different velocity impulses, particularly for a PlayerEntity whose new vector is not normalized. This is a candidate operation only. Its exact contact eligibility (`hasLiquidCollision`), fluid-flow provider behavior and block/fluid-state dependencies belong to the collision/fluid lane. Cross-reference that lane's current-force/contact evidence and reconcile as one operation if the same claim is already owned there; do not publish a duplicate claim or infer provider equivalence here.

## Temporary source operation F: ground acceleration precision

**1.12.2 evidence:** `LivingEntity.moveRelative()` grounded branch line 1485 uses `0.16277136F / (friction * friction * friction)` before multiplying by `getSpeed()` (see method around lines 1475-1493).

**1.13.2 evidence:** corresponding grounded branch line 1545 uses `0.16277137F / (friction * friction * friction)` with the same grounded `getSpeed() * u` use (lines 1538-1553). The exact 1.13 and 1.13.1 sources were also checked: both contain `0.16277137F`; the finding exists by 1.13 and remains through 1.13.1/1.13.2. This does not prove the exact patch within 1.12.2→1.13.

**Difference and movement meaning:** the float literal changes by one representable step and therefore perturbs grounded acceleration scaling. Keep this independent of water-specific travel.

## Excluded 1.13-only additions

For this 1.12.2 -> 1.13.2 reconciliation, treat these as new 1.13-era operations rather than changes to pre-existing 1.12.2 swimming: swimming pitch-steering before inherited movement (`PlayerEntity.moveRelative`, 1.13.2:1442-1465); swimming/spin-attack pose dimensions (`PlayerEntity.updatePlayerPose`, 334-360); new swim-state refresh and related submerged sprint feature; Dolphin's Grace drag; and Slow Falling effect behavior. They can be separately owned by another bounded source topic if assigned. They are not included in the six pre-existing movement operations above, except where the input gate for direct sprint in shallow water itself alters the pre-existing sprint-key operation.

## Remaining dependencies and freeze status

All claims above are source-bounded endpoint comparisons. Operation E remains explicitly dependent on exact collision/fluid-lane review for `hasLiquidCollision`, flow providers and fluid/block contacts; prevent duplicate ownership by cross-lane reconciliation. Operation D uses these contact providers too, so query-provider semantics remain a dependency even though the caller's geometry and acceptance gate are exact. The report set is not a full-pair freeze, and no intermediate release inspection establishes an exact patch introduction other than the already-present-by-1.13 checks recorded for operation F and the earlier water jump/travel candidates. No implementation, tests, build or runtime was performed.
