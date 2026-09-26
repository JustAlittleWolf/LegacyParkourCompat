# F-008: Water currents can push a passenger of an underwater boat

- Older version A: Minecraft 1.18.2
- Newer version B: Minecraft 1.19.4
- Mechanic / coverage slice IDs: stage 3 fluid current; stage 1 mounted movement state
- Classification: changed behavior
- Confidence: source-confirmed reachable velocity-write difference
- Applicability: player is riding a boat classified as underwater, is not flying, and intersects flowing water with a nonzero flow vector
- First changed release: unknown within (1.18.2, 1.19.4]
- Runtime validation: not performed

## Paired evidence

- A `decompiled_minecraft/1.18.2/mojmap/net/minecraft/world/entity/Entity.java`, `updateInWaterStateAndDoWaterCurrentPushing()`: SHA-256 `2228FDACA5793171CBD94038306D571A6ADA78CA96F5734EFB4CADA5B744C10A`. If `getVehicle() instanceof Boat`, the method sets `wasTouchingWater = false` and skips water-current scanning for the passenger.
- B `decompiled_minecraft/1.19.4/mojmap/net/minecraft/world/entity/Entity.java`, same method: SHA-256 `3667FEE610CBC5F58012E3A8FB8D6C4849F649FB7FE5300595158112D8B4B58B`. It skips only when the vehicle is a boat and `!boat.isUnderWater()`; otherwise it calls the same `updateFluidHeightAndDoFluidPushing(FluidTags.WATER, 0.014)` implementation.
- Both `Entity.baseTick()` methods invoke `updateInWaterStateAndDoFluidPushing()`, which invokes the method above. The paired `updateFluidHeightAndDoFluidPushing()` bodies are identical: they collect fluid-flow vectors intersecting the deflated entity bounds, scale and average them, apply the same minimum-current rule, then add the result to delta movement. Both `Boat.isUnderWater()` bodies classify `UNDER_WATER` and `UNDER_FLOWING_WATER` statuses as underwater.
- Both `Player.isPushedByFluid()` bodies return `!this.abilities.flying`; the source files are A `world/entity/player/Player.java` SHA-256 `BF639C1962FF90D69E4569B2B18F6FCF57AC46EF80B19686F0FBC1687FCA744A`, B `5E4436AFCCB361156F8184E7A5CFD91D5B12DCFE8F5937B4AC23DD3EDDA737A2`.

## Source-level difference

For a non-flying player on a submerged boat, A's boat check unconditionally bypasses water-current sampling. B continues through the same fluid scan as an unmounted entity, and the player's `isPushedByFluid()` result permits the flow vector to be added to the player's delta movement. This is distinct from boat steering or boat motion: the write is to the passenger player's velocity.

## Consequence and uncertainty

In B, flowing water can directly change the velocity of a non-flying player riding an underwater boat; A suppresses that passenger current update. The branch and velocity write are confirmed from paired source. No concrete world/trajectory was run, and the finding does not claim a difference for dry boats, boats outside water current, or flying players. Exact introduction release is unknown within `(1.18.2, 1.19.4]`.

## Handoff

This finding covers the underwater-boat exception in the player water-current prepass. The shared current-sampling arithmetic, other fluid interactions, boat steering, and local mounted-input packet paths remain separate coverage.
