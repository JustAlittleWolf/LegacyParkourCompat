# F-S5-WATER-CURRENT: weak water currents receive a minimum player push

- Older version A: 1.15.2, Mojmap
- Newer version B: 1.16.5, Mojmap
- Mechanic / coverage slice IDs: water current force; S5-FLUIDS, S7-PUSH
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.15.2, 1.16.5]
- Runtime validation: not performed

## Paired evidence

- A manifest artifact reference: `../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap`; `net/minecraft/world/entity/Entity.java`; `Entity#checkAndHandleWater(Tag<Fluid>)`, lines 2593-2652; SHA-256 `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e`. After averaging contributing flow vectors, A does not normalize for Player and adds `vec3.scale(0.014)` when the sum length is positive.
- A player gate: `net/minecraft/world/entity/player/Player.java`; `Player#isPushedByWater()`, lines 1846-1849; SHA-256 `1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793`; returns `!abilities.flying`.
- B manifest artifact reference: `../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap`; `net/minecraft/world/entity/Entity.java`; `Entity#updateFluidHeightAndDoFluidPushing(Tag<Fluid>,double)`, lines 2641-2705; SHA-256 `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666`. For water the caller passes `0.014`; after scaling, B raises the push magnitude to `0.0045000000000000005` when both pre-push horizontal velocity components are below `0.003` and the scaled push length is smaller than that threshold.
- B player gate: `net/minecraft/world/entity/player/Player.java`; `Player#isPushedByFluid()`, lines 1788-1791; SHA-256 `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960`; returns `!abilities.flying`.

## Source-level difference

The flow-vector scan, partial-height scaling, averaging, and Player normalization exclusion correspond. A always adds the computed water-current vector at scale 0.014. B applies the same scale and then enforces a minimum horizontal current-push magnitude under the stated low-speed guard. A player already moving slowly in x/z can therefore receive a larger push in B for a nonzero but weak current. Both versions suppress fluid pushing for flying players through the respective Player override.

## Reachability and dependencies

A `Entity.updateWaterState()` calls `updateInWaterState()`, which reaches `checkAndHandleWater(WATER)`; B `Entity.updateWaterState()` reaches `updateInWaterStateAndDoWaterCurrentPushing()`, which calls `updateFluidHeightAndDoFluidPushing(WATER, 0.014)`. Entity base ticking reaches these state-update paths, and LivingEntity also invokes the corresponding update on the movement/fall-damage path when not in water. For a Player, the override permits the push when `abilities.flying` is false. The changed lower bound reads the velocity present immediately before adding the current push.

## Consequence and uncertainty

The source proves a larger delta-velocity addition in B when the flow sum is nonzero, the scaled vector is below the minimum, and both current horizontal velocity components satisfy the low-speed guard. Net displacement depends on subsequent travel/collision and is not claimed. No runtime observation is included.

## Handoff

Independent source delta: B adds a minimum water-current push for sufficiently slow players. Related finding IDs: none. Boundary within the endpoint interval remains unknown.
