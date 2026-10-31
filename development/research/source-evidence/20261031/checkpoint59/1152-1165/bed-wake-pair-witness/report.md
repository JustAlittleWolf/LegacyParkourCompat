# Bed-wake paired position-response witness — Mojmap 1.15.2 → 1.16.5

## Verdict

**Concrete witness established for the complete paired bed-exit algorithm.** A reachable client wake from an ordinary bed over a level stone floor can select different horizontal positions and different Y coordinates at the two endpoints. The difference is in the complete `BedBlock` candidate search and floor calculation, not attributable to the newly added `BlockGetter` overload alone. The accepted route report and independent review establish that the packet-triggered `stopSleeping` path reaches the local player and writes the selected position; this audit supplies the paired concrete world-state witness.

This does not establish the exact intermediate patch boundary, tick trajectory after the direct write, pair freeze, or runtime parity.

## Exact sources and integrity

The source roots are `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.15.2\mojmap` and `...\1.16.5\mojmap`; both `mojmap.ready.json` files identify the exact release and Mojmap mapping. Recomputed SHA-256 values of `mojmap.sources.sha256` match the ready records: 1.15.2 `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7`; 1.16.5 `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`. Every row in the inherited `block-getter-spatial-audit/source-hashes.tsv` and every source file listed in this audit's `source-hashes.tsv` rehashed successfully. `input-hashes.tsv` pins the two route reports, inherited input table, ready records and source manifests used here.

## Concrete reachable precondition

Take a north-facing, non-bunk bed with the sleeping-position block at `(0,64,0)`, over stone at `y=63`. Keep air at `y=64` in the candidate cells except for the bed blocks; keep air above. Extend the stone floor across the search neighborhood (for example `x,z = -3..3`). This is an ordinary reachable world state: a player sleeps in a normally placed bed, then receives the server's wake animation. The route report and independent review trace that animation to `Player.stopSleepInBed(false,false)` and `LivingEntity.stopSleeping()` on the local client; the latter commits the returned point with `setPos` (the route report and review are hash-pinned inputs here).

The player type dimensions used for the placement checks are `0.6 × 1.8` (`1.16.5 EntityType.java:479–481`); the bed's collision shape stays inside its block footprint (`1.16.5 BedBlock.java:49–57,204–216`). The candidate boxes below are at least one block center away horizontally from either bed cell, so their 0.6-wide footprints do not touch the bed collision shape. Their bottoms are at the top of the stone support, so the full support block does not overlap the box. The exact 1.16.5 block collision walk checks intersections before returning a shape (`CollisionSpliterator.java:74–85`); hence these candidate boxes pass the collision test. The old `noCollision` query likewise sees only the bed and stone shapes, with no overlap at the stated candidate.

## Paired selection

For 1.15.2, `LivingEntity.stopSleeping()` calls `BedBlock.findStandUpPosition(..., 0)` and commits its result (`LivingEntity.java:2815–2828`). The search first scans the 3×3 square with `x` outer and `z` inner loops, beginning at sleeping-block offset `(-1,-1)` (`BedBlock.java:237–265`). At that cell, the current air collision shape passes the `max(Y) > 0.4375` rejection (`:267–271`). The downward loop finds stone one block below (`:273–287`), producing support height `64 + 2.0E-7` (`:288`); the 2-block vertical limit is satisfied (`:289–291`), and the 0.6×1.8 player AABB at center `(-0.5,64.0000002,-0.5)` is clear (`:293–297`). It is the first valid candidate and is returned.

For 1.16.5, the same stop-sleep path passes current yaw to `BedBlock.findStandUpPosition`, then writes its result and reapplies that position after standing-pose transition (`LivingEntity.java:3032–3051`). The non-bunk-bed path chooses the first candidate from its yaw-dependent ordered side offsets (`BedBlock.java:233–244,276–289,338–359`). With a north-facing bed, its first candidate is one of the east/west adjacent cells, based on yaw (`:234–243`). In that cell, the current shape is empty and the supplier finds full-block stone below. `BlockGetter.getBlockFloorHeight(shape,supplier)` returns `1 - 1 = 0` (`BlockGetter.java:75–82`); this is valid under the strict `<1` test, and the dangerous-block gates do not reject air or stone (`DismountHelper.java:82–94`). `DismountHelper` therefore tests and accepts the center at either `(-0.5,64,0.5)` (west) or `(1.5,64,0.5)` (east) (`:96–98`), which is horizontally different from the old `(-0.5,-0.5)` center and has Y `64.0`, rather than `64.0000002`. As that first candidate succeeds, later offsets and fallback are not reached.

The output divergence is therefore observable at the direct local-player position write: both endpoints accept a safe supported candidate, but the selected candidate cell differs by search order, and the accepted Y value differs due to the old epsilon versus the new floor-height rule.

## Scope and limitations

This is a bed-exit/player-position response difference. It does not concern bed-entry eligibility. Attribute it to the **paired** 1.15.2 and 1.16.5 bed candidate algorithms, including their candidate ordering, floor search/helper behavior and collision checks. The new floor-height overload is one contributor to the 1.16.5 branch; comparing that overload in isolation would miss the changed yaw-sensitive candidate order and surrounding gates. The route report notes that 1.16.5 also has bunk-bed handling; this witness explicitly excludes that branch. The route itself exists at both endpoints. No intermediate version or dynamic execution was inspected.
