# 1.21.10–1.21.11 local dry velocity threshold comparison

## Evidence identity

Canonical read-only sources: `decompiled_minecraft/{1.21.10,1.21.11}/mojmap`. Both `mojmap.ready.json` markers report `status=ready`, exact `versionId` values matching their directory, and mapping `mojmap`. Their provenance records identify Vineflower 1.12.0 and the same exact preparation batch, `1.21.8,1.21.10,1.21.11`, using Mojmap.

- 1.21.10: client SHA-256 `e65ca028bc58da12bf8413066c90ecd4d48c94fb351d9bbd22ecdff5c87f9482`; mapped client artifact `client-mojmap.jar` SHA-256 `0885181c5e4c2f21dd2f95591dd095fa0176807edbe239ef77218e861f0751da`; `client_mappings.txt` SHA-256 `2a6f53f4c1fd048e8fa956e3a3fbbb0afc02d5bbc23e52a16b328aed61b9bf39`.
- 1.21.11: client SHA-256 `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd`; mapped client artifact `client-mojmap.jar` SHA-256 `7055b6a734a8f9f0d80229c2438ba12d10952a57c3795f32b8611020b34eb89a`; `client_mappings.txt` SHA-256 `517799a8485e107e932dc1bd27c002b2d0b9207eb2396b685bcfe6c3321a9fbd`.
- The 1.21.10 and 1.21.11 artifact manifests both hash to `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c`. Their source-manifest hashes match the ready markers: 1.21.10 `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1`; 1.21.11 `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`.
- Every used source file hash below was recomputed from the canonical file and matched its corresponding `mojmap.sources.sha256` entry.

## Paired operation and result

In both releases, `Player` constructs its superclass with `EntityType.PLAYER` (`world/entity/player/Player.java`: 1.21.10 line 183; 1.21.11 line 181). The local player's inherited tick reaches `LivingEntity.tick`, which calls `aiStep` while the entity remains live (1.21.10 `world/entity/LivingEntity.java` lines 2542–2583; 1.21.11 lines 2610–2655). `LocalPlayer.aiStep` calls `super.aiStep` in both versions (1.21.10 `client/player/LocalPlayer.java` lines 834–835; 1.21.11 lines 875–876); `Player.aiStep` likewise calls `super.aiStep` (1.21.10 `world/entity/player/Player.java` lines 455–466; 1.21.11 lines 452–463).

At the beginning of `LivingEntity.aiStep`, after equipment ticking and before input/travel, both versions read the current velocity into `Vec3`, copy its x/y/z doubles, and apply the same gates (`LivingEntity.java`: 1.21.10 lines 2767–2791; 1.21.11 lines 2893–2917):

- If `getType().equals(EntityType.PLAYER)`, `horizontalDistanceSqr() < 9.0E-6` sets both x and z to `0.0`. The strict comparison uses the combined horizontal squared magnitude; x and z are not independently compared to `0.003` on the player path.
- For non-player types, x and z instead each use strict `Math.abs(component) < 0.003`. A local player does not enter that branch because its `Player` constructor sets `EntityType.PLAYER`.
- Independently for every type, strict `Math.abs(y) < 0.003` sets y to `0.0`.
- The method writes the resulting x/y/z with `setDeltaMovement` before proceeding into AI, jump handling and `travel` later in the same method. This is pre-travel snapping in both releases.

Under the bounded same-state witness—live local `Player`, client-side, non-riding, ordinary dry air travel, movement simulation enabled, no fall-flying/climb/swim/crawl/flight branch, and no collision or external velocity response—the threshold gate and subsequent ordinary travel writer are unchanged. `LivingEntity.aiStep` is byte-for-byte identical as decompiled text in the two sources. The dry `travelInAir` method is also identical: it obtains relative-friction movement, calculates the vertical component, then writes movement through the same `shouldDiscardFriction` gate and the same x/y/z expressions (1.21.10 lines 2306–2329; 1.21.11 lines 2343–2366). No source-confirmed change in this assigned velocity-threshold operation is present, so no positive finding is warranted.

`LivingEntity.travel(Vec3)` has one dispatcher refactor: 1.21.10 inlines `(isInWater() || isInLava()) && isAffectedByFluids() && !canStandOnFluid(fluidState)` (lines 2275–2284); 1.21.11 calls `shouldTravelInFluid(fluidState)` (lines 2309–2321), whose body is that same expression. Therefore this dispatcher change does not alter the bounded dry witness. Fluid-specific behavior remains outside this lane.

After `travel`, the identical `LivingEntity.aiStep` continues through block-effect application, animation and tick-tail work; the enclosing `LivingEntity.tick` body is identical between versions. Block effect responses and collision response/math are not attributed to this result. The dry branch's gravity/friction formulas were only checked as downstream ordering context, not reclassified as a finding for the separately owned gravity/drag behavior.

## Used member body hashes

SHA-256 below is over each exact UTF-8 member substring from its signature through the closing brace, excluding any following newline.

| Member | 1.21.10 lines / SHA-256 | 1.21.11 lines / SHA-256 |
|---|---|---|
| `LivingEntity.tick()` | 2542–2654 / `4e5df33f3ac66e78c226d27335a2efba28b6e2bea4cf31a5826958d399172025` | 2610–2722 / `4e5df33f3ac66e78c226d27335a2efba28b6e2bea4cf31a5826958d399172025` |
| `LivingEntity.aiStep()` | 2751–2886 / `98b6dad3e3a4bedfbd4284978b63d54e5135ac5265f49cb018d5bc11d060448a` | 2877–3012 / `98b6dad3e3a4bedfbd4284978b63d54e5135ac5265f49cb018d5bc11d060448a` |
| `LivingEntity.travel(Vec3)` | 2275–2284 / `8e8cf4f39e0a730b138e216b5f86579c7f9a8ce6fad1e729f3739d7392b6ec36` | 2309–2317 / `5f62626deac10a94af8fcb505e0a2636dc7d0d4c14c0afdc5e73be4d04471f09` |
| `LivingEntity.travelInAir(Vec3)` | 2306–2329 / `48878ab9ef31b80110621f2485b0eb53c8a361f71cbcc9f6f5bca68cbbcde333` | 2343–2366 / `48878ab9ef31b80110621f2485b0eb53c8a361f71cbcc9f6f5bca68cbbcde333` |
| `Player.aiStep()` | 455–495 / `94db8672f1b6f37300ac1d7fc2245ed3db9c17f0da522655ef1f2c2bc810bd3f` | 452–492 / `94db8672f1b6f37300ac1d7fc2245ed3db9c17f0da522655ef1f2c2bc810bd3f` |

Relevant source-file SHA-256 values: `client/player/LocalPlayer.java` 1.21.10 `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1`, 1.21.11 `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477`; `world/entity/LivingEntity.java` 1.21.10 `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66`, 1.21.11 `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8`; `world/entity/player/Player.java` 1.21.10 `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82`, 1.21.11 `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81`.

## Residual boundary

This comparison closes only the assigned ordinary direct local dry non-riding velocity snap and its same-state pre/post-travel placement. It does not establish parity for fluids, riding, fall-flying, climbing, swimming/crawling, flight, sprint/sneak, timers, collision response, block/external velocity writers, or other tick inventory. LocalPlayer tick/input differences unrelated to this velocity snap were not adjudicated here. Intermediate release boundaries are unknown.