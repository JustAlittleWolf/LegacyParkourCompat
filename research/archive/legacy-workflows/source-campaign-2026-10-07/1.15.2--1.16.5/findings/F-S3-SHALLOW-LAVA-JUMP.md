# F-S3-SHALLOW-LAVA-JUMP: grounded shallow-lava jump selects ground jump

- Older version A: 1.15.2, Mojmap
- Newer version B: 1.16.5, Mojmap
- Mechanic / coverage slice IDs: LivingEntity jump input; S3-JUMP
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.15.2, 1.16.5]
- Runtime validation: not performed

## Paired evidence

- A manifest artifact reference: `../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap`; `net/minecraft/world/entity/LivingEntity.java`; `LivingEntity#aiStep()` jump block, lines 2265-2279; SHA-256 `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54`. When the outer water-height condition allows the block and `isInLava()` is true, A calls `jumpInLiquid(FluidTags.LAVA)` before checking its ordinary ground-jump branch.
- B manifest artifact reference: `../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap`; same method, lines 2427-2451; SHA-256 `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88`. B compares lava height `k` with threshold `m`; if onGround and not `(k > m)`, the nested lava guard falls through to ordinary `jumpFromGround()` when `noJumpDelay == 0`; otherwise it calls `jumpInLiquid(LAVA)`.
- B fluid-height writer and threshold are `Entity#updateFluidHeightAndDoFluidPushing`, lines 2660-2705, and `Entity#getFluidJumpThreshold`, lines 2712-2714; Entity SHA-256 `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666`.

## Source-level difference

For a grounded player in lava with positive fluid height at or below B's threshold, A selects `jumpInLiquid(LAVA)` while B selects `jumpFromGround()` when the jump delay is zero. In B, a grounded player above the threshold instead takes the lava liquid-jump branch. The branch choice therefore changes at the explicit fluid-height threshold.

## Reachability and dependencies

B's fluid scan writes lava height from the intersecting fluid surface and reports lava contact for positive height. `getFluidJumpThreshold()` returns a threshold based on eye height. A's branch uses its water-height gate before checking lava, so its concrete precondition includes entering that outer block; the report does not assume every pose meets it. For a reachable grounded state satisfying both liquid gates, the two helper calls are distinct source paths. `jumpFromGround` and `jumpInLiquid` implementations are not compared here; their call selection is the finding.

## Consequence and uncertainty

The source proves the selected jump helper differs under the stated state. The resulting launch velocity is outside this call-selection finding and should be compared in its own helper slice; no runtime trajectory is claimed.

## Handoff

Independent source delta: B selects the ground jump helper for grounded shallow-lava contact under its threshold test. Related finding IDs: F-S3-SHALLOW-LAVA-TRAVEL. Boundary within the endpoint interval remains unknown.
