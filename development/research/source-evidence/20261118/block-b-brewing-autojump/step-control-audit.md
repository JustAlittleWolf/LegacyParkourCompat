# B01 independent source audit — brewing step control

## Status: COMPLETED

Audited report: `b01-brewing-step-control/report.md`

Report SHA-256: `89ffadc032d3bf41871d8dd8803cabc9280a29ddab56dbbe21b3f5a06c862cf75`

## Evidence identity and integrity

- The 1.11.2 source-list SHA-256 is `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`; the 1.12.2 source-list SHA-256 is `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`. Both match the assigned identities.
- All six cited Java source files per release (Entity, Box, LocalClientPlayerEntity, BrewingStandBlock, SlabBlock, LivingEntity) match their source-list member hashes. The method bodies inspected are present and intact.
- Provenance records Ornithe Feather and the Feather Gen2 build.2 mapping jar/Tiny names; `artifacts.sha256` contains their pins. The recorded campaign artifact cache is not present at its provenance path here, so raw mapping archive bytes could not be rehashed independently.

## Per-claim verdicts

1. **0.6 step retry against a bottom slab, exact-touch placement — SUPPORTED.** Both `LivingEntity` bodies set `stepHeight = 0.6F`. `Entity.move` retries after horizontal clipping, compares candidate horizontal squared distance against the flat result, and restores the original result on an equal distance. With feet Y=0.5, a 0.6 rise reaches 1.1; the slab top is 1.0, so the raised player still overlaps the stand plate whose bottom is Y=1.0 and top is Y=1.125. At exact horizontal contact, the plate clips +X on the retry, so equal displacement loses and the flat result is restored.

2. **Bottom-slab no-arm 1.375 first-hit rejection — SUPPORTED, with the exact-touch precondition.** Both brewing stand definitions append the stem box (local maxY 0.875) before the plate box (maxY 0.125). At block Y=1, the stem's world maxY is 1.875. After the flat movement is restored, `LocalClientPlayerEntity.move` calls AutoJump with actual horizontal displacement zero; the input/yaw fallback supplies +X. The first intersected stand collision is the stem, producing rise `1.875 - 0.5 = 1.375`, which exceeds the ordinary 1.2 bound and rejects arming.

3. **Full-floor +0.13 post-step arm — SUPPORTED as a post-step witness, with exact-touch placement and stated ordinary grounded/downward collision preconditions.** From feet Y=1.0, the 0.125-high plate is reachable by the 0.6 retry. The retained candidate advances +0.13, then its downward leg clips at plate top Y=1.125. `LocalClientPlayerEntity.move` passes actual post-move displacement to AutoJump; the stem first hit gives `1.875 - 1.125 = 0.75`, within the ordinary 1.2 bound. This does not validate a query-entry calculation at feet Y=1.0 as the result of that move; that unchanged-box control computes 0.875 only in isolation.

4. **Literal x=-0.3 fixture in the submitted report — NOT SUPPORTED as written.** In both exact `Entity.setPosition` bodies, `f` is `float width / 2.0F`; for width `0.6F`, it is approximately `0.3000000119`. Adding that float to the double literal `-0.3` makes the box's maxX slightly greater than zero, so the player begins just inside the stand plate's X boundary. `Box.intersectX` only clips a positive move when the moving box's maxX is `<=` the collider's minX. Thus that literal placement does not provide the collision gate assumed by either witness. Use center X `-(double)(0.6F / 2.0F)` for exact contact (or specify a valid approach ending at contact) before accepting the movement conclusions.

## Acceptance limits

This review accepts only the corrected exact-touch mechanics in the two exact controls, 1.11.2 and 1.12.2, subject to the stated movement and AutoJump conditions. It does not accept the report's literal x=-0.3 fixture, extend results to intermediate patches, complete pair discovery or coverage audit, renew any broader acceptance, authorize implementation, or establish runtime parity. No product files, tests, builds, game launches, or decompilation were run.
