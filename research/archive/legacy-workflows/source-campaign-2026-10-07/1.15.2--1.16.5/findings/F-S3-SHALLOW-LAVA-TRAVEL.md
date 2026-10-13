# F-S3-SHALLOW-LAVA-TRAVEL: shallow lava uses a different vertical adjustment

- Older version A: 1.15.2, Mojmap
- Newer version B: 1.16.5, Mojmap
- Mechanic / coverage slice IDs: LivingEntity lava travel; S3-LAVA
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.15.2, 1.16.5]
- Runtime validation: not performed

## Paired evidence

- A manifest artifact reference: `../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap`; `net/minecraft/world/entity/LivingEntity.java`; `LivingEntity#travel(Vec3)`, lines 1913-1926; SHA-256 `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54`. In the lava branch A moves, scales all velocity components by `0.5`, then adds vertical gravity `-d / 4.0` when gravity applies.
- B manifest artifact reference: `../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap`; same class/member, lines 1959-1978; SHA-256 `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88`. B tests `getFluidHeight(FluidTags.LAVA) <= getFluidJumpThreshold()`. In that branch it multiplies `(x,y,z)` by `(0.5,0.8F,0.5)` and calls `getFluidFallingAdjustedMovement`; otherwise it scales all components by `0.5`. Gravity `-d / 4.0` remains afterward.
- B height source: `net/minecraft/world/entity/Entity.java`; `Entity#updateFluidHeightAndDoFluidPushing(Tag<Fluid>,double)`, lines 2660-2705, writes `fluidHeight.put(tag,e)`; `Entity#isInLava()`, lines 1071-1073, reads positive lava height outside the first tick. Entity SHA-256 `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666`.
- B threshold: `Entity#getFluidJumpThreshold()`, lines 2712-2714, returns `0.0` when eye height is below `0.4`, otherwise `0.4`; same Entity hash.

## Source-level difference

The branch before movement is shared in outline: add relative input and move once. When B's lava height is at or below the eye-height-derived threshold, it uses vertical multiplier `0.8F` instead of A's `0.5` and applies the falling-adjustment helper before common gravity. The helper subtracts `d / 16.0` from y unless gravity is disabled or sprinting; those latter cases return the input vector unchanged. At greater lava height B follows the A-style `scale(0.5)` path, preserving the branch distinction.

## Reachability and dependencies

For a non-flying Player in lava, B's `Player.isAffectedByFluids()` returns true and `LivingEntity.canStandOnFluid()` returns false. The fluid scan measures the intersecting height relative to the deflated bounding box and stores it under `FluidTags.LAVA`; `isInLava()` is true for positive height after first tick. A shallow positive fluid height no greater than the threshold reaches B's special branch. A's lava branch is selected by its lava-contact state and has no height split. This finding does not derive player pose height beyond the explicit threshold method.

## Consequence and uncertainty

The source proves different y-velocity arithmetic in the shallow-lava branch; the x/z multiplier and later gravity addition are identified separately. Net displacement depends on earlier velocity, sprint/gravity state, collision and the subsequent tick; no runtime trajectory is claimed.

## Handoff

Independent source delta: B uses a shallow-lava-specific vertical scale and falling adjustment. Related finding IDs: F-S3-SHALLOW-LAVA-JUMP. Boundary within the endpoint interval remains unknown.
