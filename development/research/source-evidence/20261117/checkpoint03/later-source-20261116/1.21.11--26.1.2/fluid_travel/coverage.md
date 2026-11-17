# Fluid-current response threshold

## Endpoint identity

- Older: 1.21.11, Mojmap. Canonical source root: `decompiled_minecraft/1.21.11/mojmap`. `mojmap.ready.json` SHA-256 `0ad98d0ebd654650492c97eb58bc324d33864787f0ce10d99faec3ae2f5b804b`; it binds source manifest `mojmap.sources.sha256` SHA-256 `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555` and artifact manifest `artifacts.sha256` SHA-256 `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c`. Mapping-local provenance SHA-256 `99c9fb741eb5e4ac8fb5780e2158da324d7c8b6afc179d494e5986f78fe29f72` identifies exact run version `1.21.11`, mapping `mojmap`.
- Newer: 26.1.2, published unobfuscated. Canonical source root: `decompiled_minecraft/26.1.2/unobfuscated`. `unobfuscated.ready.json` SHA-256 `f24af0f4d38543c2bd9c19b50f347d4e89d6027050e4b21ae070734843946bb7`; it binds source manifest `unobfuscated.sources.sha256` SHA-256 `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` and artifact manifest `artifacts.sha256` SHA-256 `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92`. Mapping-local provenance SHA-256 `8d727f880cc91f4a297e148c5b75976303d051457334bc689268fbdf631a012a` identifies exact run version `26.1.2`, mapping `unobfuscated`.

## Difference

1.21.11 `Entity.updateInWaterStateAndDoFluidPushing` delegates to `updateFluidHeightAndDoFluidPushing` for water and lava during `Entity.baseTick` (`net/minecraft/world/entity/Entity.java:1501–1522`). That helper scans the deflated entity AABB, accumulates each matching fluid cell's flow (scaled by current maximum fluid height when below `0.4`), and for a nonzero sum averages by sample count. It keeps the average for `Player`, applies the water/lava scale, boosts a sufficiently small impulse to length `0.0045` when both prior horizontal velocity components are below `0.003`, then adds the impulse to `deltaMovement` (`Entity.java:3509–3573`). Its only current-vector entry gate is `sum.length() > 0.0`.

26.1.2 `Entity.updateFluidInteraction` invokes a per-tag `EntityFluidInteraction` during the same `baseTick` slot (`net/minecraft/world/entity/Entity.java:1558–1582`). For a non-passenger, `getFluidInteractionBox` still returns the entity AABB deflated by `0.001` (`Entity.java:4015–4023`). The helper accumulates the same kind of flow vector into a tracker and applies it to the entity (`net/minecraft/world/entity/EntityFluidInteraction.java:32–89, 166–189`). Its player path still averages by sample count, scales the impulse, uses the same low-velocity `0.0045` minimum, then calls `addDeltaMovement`. However, it first suppresses any current whose accumulated-vector squared length is below `1.0E-5F` (`EntityFluidInteraction.java:171–188`); the old helper accepted every nonzero sum.

The height origin also changes: old height is `fluidTop - deflatedBox.minY` (`Entity.java:3514–3538`); the new tracker uses `fluidTop - entity.getBoundingBox().minY` although its overlap test uses the deflated interaction box (`EntityFluidInteraction.java:34–43, 59–79`). Thus for the same single shallow overlap the new tracked height is `0.001` larger. Water/lava travel branch formulas at the endpoints retain the same order and constants (`LivingEntity.java:2309–2425` versus `2392–2519`); the difference described here is the tick-time flow impulse before travel.

## Bounded movement witness

For a fully loaded, non-passenger, non-flying `LocalPlayer` at rest, suppose one fluid cell intersecting the deflated box has a nonzero unit horizontal `getFlow` vector and its top is at `y=1.0`; set the player AABB `minY` to `0.997`. The deflated box begins at `0.998`, so both endpoints recognize a positive overlap. With no other intersected fluid cells, old penetration/current length is `0.002`; new tracker height/current length is `0.003`, and squared length is `9.0E-6 < 1.0E-5F`. The old nonzero gate therefore reaches its minimum-impulse clause: after water scaling `0.014`, the impulse is below `0.0045`, and zero prior horizontal velocity causes an added impulse of length `0.0045` in the flow direction. The new threshold suppresses the current before that clause and adds no fluid-current impulse. `FlowingFluid.getFlow` normalizes a nonzero horizontal gradient to a unit vector in both endpoint sources.

The player route is direct: `LocalPlayer.tick` calls `super.tick` (`net/minecraft/client/player/LocalPlayer.java:211–215` older; `226–231` newer); inherited `Entity.baseTick` performs the endpoint-specific fluid update before the later local player simulation/travel tick. `Player.isPushedByFluid` is `!abilities.flying` at both endpoints (`net/minecraft/world/entity/player/Player.java:1679–1681` older; `1673–1675` newer), so the stated non-flying player receives the current. `EntityFluidInteraction` then uses `addDeltaMovement`, a direct velocity write.

## Exact source hashes

Hashes below match both the exact files and their ready-marker-bound source manifests.

- 1.21.11 `net/minecraft/world/entity/Entity.java` — `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3`
- 1.21.11 `net/minecraft/world/entity/LivingEntity.java` — `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8`
- 1.21.11 `net/minecraft/world/entity/player/Player.java` — `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81`
- 1.21.11 `net/minecraft/client/player/LocalPlayer.java` — `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477`
- 1.21.11 `net/minecraft/world/level/material/FlowingFluid.java` — `905e5a02ab10b23b5f6e64a0f732ee552ae8895801e1944d9850a41cc8bce91a`
- 1.21.11 `net/minecraft/world/phys/AABB.java` — `b8ace04fa09628266e3e12ec5423dbceb7ecef000041824c00510a5ddca6e9a1`
- 26.1.2 `net/minecraft/world/entity/Entity.java` — `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`
- 26.1.2 `net/minecraft/world/entity/EntityFluidInteraction.java` — `5264ff4f1fddebc3fa9d63ad2edbe2eaf617392ff946a867a6817a78b478ee62`
- 26.1.2 `net/minecraft/world/entity/LivingEntity.java` — `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd`
- 26.1.2 `net/minecraft/world/entity/player/Player.java` — `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d`
- 26.1.2 `net/minecraft/client/player/LocalPlayer.java` — `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe`
- 26.1.2 `net/minecraft/world/level/material/FlowingFluid.java` — `f8c0c4430e81efdf39b221968272ed8e3a4d030fa5192e04e04d34c9fbd37b28`
- 26.1.2 `net/minecraft/world/phys/AABB.java` — `22f23018fd588c2c4893d872f730381127e3d3ddd1a8c960901ecb8dae626392`

## Open gates

- The source outputs are Java roots; this review did not establish the packaged resource membership of `FluidTags.WATER`/`LAVA` or any applicable fluid-tag overrides. The witness is conditional on the intersecting fluid type matching the water tag used by the tracker.
- The exact release introducing the `1.0E-5F` cutoff is unknown within 1.21.11→26.1.2; no intermediate versions were compared.
- Lava fast-flow environment parameters, overlapping multiple-fluid aggregation/cancellation, unloaded-chunk edge behavior, vehicle/passenger bounds, and runtime tick parity were not closed.
- This is one bounded source difference, not complete pair/tick coverage and not an implementation/runtime handoff.