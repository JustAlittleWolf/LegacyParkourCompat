# Independent review — bed-wake paired position witness

## Verdict

**REVISE** the bounded witness report before reuse. Its paired candidate ordering, floor calculation, gates, and collision acceptance establish a direct position divergence, but one stated 1.16.5 candidate coordinate is wrong for the west-yaw branch. The divergence conclusion remains supported. This does not establish an intermediate patch boundary, pair freeze, runtime parity, or behavior after the direct position write.

## Independent checks

- Rehashed every entry in the witness `source-hashes.tsv` against the exact read-only `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft` files. All matched. Rehashed every entry in the witness `input-hashes.tsv`; all matched, including the pinned paired route report and the earlier independent bed-wake route review.
- Rehashed both `mojmap.sources.sha256` manifests. Their SHA-256 identities match the report (`1.15.2 cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7`; `1.16.5 9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`) and each ready record's source-manifest field. Both ready records identify Mojmap. Exact source methods agree with the cited ranges.
- 1.15.2 `BedBlock.findStandUpPosition` scans x then z over the 3×3 area beginning at `(bedX-1, bedZ-1)`. For north-facing bed at `(0,64,0)`, that is `(-1,64,-1)`. Its air shape passes `maxY > .4375`; the downward scan finds stone at y=63; support y is `63 + 1 + 2.0E-7`; the 2-block bound passes; and `noCollision` accepts the box centered at `(-.5,64.0000002,-.5)`.
- 1.16.5 `findStandUpPosition` chooses the non-bunk branch (the block below is stone), computes the side direction from `direction.getClockWise()` and yaw, and uses it as the first offset. For north-facing, the first offset is either east or west for any yaw, so the first candidate center is `(1.5,64,.5)` for east or `(-.5,64,.5)` for west. Both differ from the old `(-.5,64.0000002,-.5)` center.
- For the 1.16.5 candidate cell, current non-climbable shape is empty and the lazy below-shape supplier returns full stone. `getBlockFloorHeight` gives `1 - 1 = 0`; this passes finite and `<1` checks. With bed danger gates enabled, air and stone do not trigger the dangerous-block predicate. The 0.6×1.8 box at bottom y=64 is clear: its x footprint is separated by 0.2 from the bed block(s), and its bottom only touches the y=63 stone top. The collision spliterator uses strict shape/AABB intersection before emitting colliding shapes. In 1.15.2 the old AABB is similarly clear.
- The report has a **coordinate arithmetic error** for the west yaw branch: it states the accepted 1.16.5 center is `(±1.5,64,.5)`, but `Vec3.upFromBottomCenterOf` adds the block center to offset `(-1,0)` as x `-0.5`, not `-1.5`. This does not alter the claimed existence or divergence of the witness, but the report must be corrected before reuse.
- The cited route report and prior independent review establish the conditional packet route to `Player.stopSleepInBed(false,false)`, inherited `LivingEntity.stopSleeping`, and the local entity position write. The route exists at both endpoints, and the claim is correctly scoped to bed exit.

## Required correction

Replace `(±1.5,64,0.5)` in the 1.16.5 coordinate claim with `(-0.5,64,0.5)` for west and `(1.5,64,0.5)` for east. Keep the conclusion that either branch differs horizontally from old center `(-0.5,-0.5)`; the west case differs in z, and east differs in both x and z. The report's collision-clearance conclusion remains valid after this correction.

No product, wiki, backlog, build, test, runtime, or decompile action was performed.
