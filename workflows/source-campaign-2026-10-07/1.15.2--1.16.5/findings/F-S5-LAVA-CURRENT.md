# F-S5-LAVA-CURRENT: non-flying players receive lava-current force

- Older version A: 1.15.2, Mojmap
- Newer version B: 1.16.5, Mojmap
- Mechanic / coverage slice IDs: fluid current force; S5-FLUIDS, S7-PUSH
- Classification: added mechanic
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.15.2, 1.16.5]
- Runtime validation: not performed

## Paired evidence

- A manifest artifact reference: `../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap`; `net/minecraft/world/entity/Entity.java`; `Entity#baseTick()` calls `updateWaterState()` at lines 361-363; `updateWaterState()` at lines 943-947 updates water only. `Entity#checkAndHandleWater(Tag<Fluid>)`, lines 2593-2652, is the sole fluid-flow push path in the inspected Entity source and is passed `FluidTags.WATER`; SHA-256 `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e`.
- B manifest artifact reference: `../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap`; `net/minecraft/world/entity/Entity.java`; `Entity#baseTick()` calls `updateInWaterStateAndDoFluidPushing()` at line 380, and that helper clears fluid-height state then calls `updateFluidHeightAndDoFluidPushing(FluidTags.LAVA, d)` at lines 957-963; `d` is `0.007` in ultra-warm dimensions and `0.0023333333333333335` otherwise. The shared helper adds the resulting flow at lines 2694-2701; SHA-256 `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666`.
- A Player fluid-push gate: `Player#isPushedByWater()`, lines 1846-1849; SHA-256 `1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793`.
- B Player fluid-push gate: `Player#isPushedByFluid()`, lines 1788-1791; SHA-256 `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960`. Both return `!abilities.flying`.

## Source-level difference

A's entity base tick updates water and its current; the inspected path does not update or push from lava. B's entity base tick updates fluid heights and pushes from both water and lava. A non-flying Player satisfies B's `isPushedByFluid()` guard, so the lava flow scan can add a dimension-scaled current vector to its delta movement. Flying players remain excluded by the Player override.

## Reachability and dependencies

`Entity.baseTick()` is called through the player tick superclass path. The B lava call uses the same fluid scan and push operation as water, keyed to `FluidTags.LAVA`; the fluid-state scan contributes only when matching flow is present and intersects the deflated entity box. `Player.isPushedByFluid()` permits the current for a non-flying player. A has no corresponding lava-push call in the inspected `baseTick`/`updateWaterState` path; its lava movement state is used for lava handling, but not as a current producer.

## Consequence and uncertainty

The source proves B can add nonzero lava-flow delta velocity to a non-flying player in a matching flowing-lava state. Force magnitude also depends on flow vector, occupancy, dimension warmth and the low-speed minimum described by F-S5-WATER-CURRENT. Net motion and survival consequences are not claimed.

## Handoff

Independent source delta: B adds lava-current pushing for entities whose fluid-push predicate allows it, including non-flying players. Related finding: F-S5-WATER-CURRENT. Boundary within the endpoint interval remains unknown.
