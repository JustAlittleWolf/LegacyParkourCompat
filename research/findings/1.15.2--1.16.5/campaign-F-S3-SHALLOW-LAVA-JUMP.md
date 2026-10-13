# F-S3-SHALLOW-LAVA-JUMP: grounded shallow-lava jump selects ground jump

- **1.15.2**, mojmap: `net/minecraft/world/entity/LivingEntity.java`; lines 2265-2279.
- **1.16.5**, mojmap: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.15.2--1.16.5/findings/F-S3-SHALLOW-LAVA-JUMP.md); lines 2427-2451.

## Difference

For a grounded player in lava with positive fluid height at or below 1.16.5's threshold, 1.15.2 selects `jumpInLiquid(LAVA)` while 1.16.5 selects `jumpFromGround()` when the jump delay is zero.

In 1.16.5, a grounded player above the threshold instead takes the lava liquid-jump branch.

The branch choice therefore changes at the explicit fluid-height threshold.

## Movement meaning

1.16.5's fluid scan writes lava height from the intersecting fluid surface and reports lava contact for positive height. `getFluidJumpThreshold()` returns a threshold based on eye height. 1.15.2's branch uses its water-height gate before checking lava, so its concrete precondition includes entering that outer block; the report does not assume every pose meets it.

For a reachable grounded state satisfying both liquid gates, the two helper calls are distinct source paths. `jumpFromGround` and `jumpInLiquid` implementations are not compared here; their call selection is the finding.

The source proves the selected jump helper differs under the stated state.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.15.2--1.16.5/findings/F-S3-SHALLOW-LAVA-JUMP.md).
