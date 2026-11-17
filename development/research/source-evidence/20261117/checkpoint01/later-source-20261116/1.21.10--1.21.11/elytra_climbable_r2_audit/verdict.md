# Independent source audit — ACCEPT

Candidate: `build/later-source-20261116/1.21.10--1.21.11/elytra_climbable_revision/fall-flying-through-climbable-vines-r2.md`
Candidate SHA-256: `F47F823A6CDD7AF5554F60AB51BC313D7D17C94A7A0302D6CD947B0F394BBD5F`
Source-check SHA-256: `542C31C0E3D8B5E7B2E459FEBCB6E19374C392CEBAE7FB66EE5868CFEE69D13C`

## Identity and source integrity

The ready markers identify 1.21.10 Mojmap and 1.21.11 Mojmap. The pinned source manifests have SHA-256 `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1` and `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`, respectively; these match the values in the ready markers. Provenance hashes are `9A1DB4C050C3CAA28A1313F89DAE9DA3B28FEAA5F8AFBA42CAB798B47D7AD77C` and `99C9FB741EB5E4AC8FB5780E2158DA324D7C8B6AFC179D494E5986F78FE29F72`. The artifact manifest hashes match across endpoints (`c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c`). All eight cited Java files' SHA-256 values in the source-check were independently recomputed and match.

## Claim audit

The revised seven-state intersection is correct for the compared vanilla providers. `VanillaBlockTagsProvider.java` at 1.21.10 lines 289–300 and 1.21.11 lines 289–300 both list LADDER, VINE, SCAFFOLDING, both weeping-vine forms, both twisting-vine forms, and both cave-vine forms in `CLIMBABLE`. Thus the prior audit's statement that twisting-vine forms and weeping-vine plant were excluded from both `CLIMBABLE` lists was incorrect. At 1.21.11 lines 320–324, `CAN_GLIDE_THROUGH` adds VINE, the two twisting forms, the two weeping forms and `CAVE_VINES`; lines 401–402 define `CAVE_VINES` as `CAVE_VINES_PLANT` and `CAVE_VINES`. This gives the exact overlap listed in r2: VINE, TWISTING_VINES, TWISTING_VINES_PLANT, WEEPING_VINES, WEEPING_VINES_PLANT, CAVE_VINES and CAVE_VINES_PLANT. LADDER and SCAFFOLDING are climbable-only. R2 does not claim an effect for glide-through-only types.

The gate and movement consequences are also supported. At 1.21.10, `LivingEntity.onClimbable()` checks spectator status and then the `CLIMBABLE` state (lines 1662–1678); in 1.21.11 it first returns false for `isFallFlying()` plus `CAN_GLIDE_THROUGH` (lines 1669–1687), before the same climbable-state and trapdoor checks. `travelFallFlying()` in 1.21.10 selects `travelInAir()` and then `stopFallFlying()` when `onClimbable()` is true (lines 2383–2397). The 1.21.11 form retains that branch but the new earlier `onClimbable()` gate makes it false on the seven overlapping states, so the existing gliding path updates fall-flying velocity and moves without stopping fall-flight.

The ordinary local-player route is reachable under the stated conditions. `LivingEntity.aiStep()` updates fall-flight state and invokes virtual `travel()` with the entity's input vector when `canSimulateMovement()` and `isEffectiveAi()` are true (1.21.10 lines 2833–2849; 1.21.11 lines 2959–2975). `Player.canSimulateMovement()` and `isEffectiveAi()` allow the local player on a logical client (1.21.10 lines 1186–1193; 1.21.11 lines 1251–1258). For a non-passenger player without abilities-flying, `Player.travel()` delegates to `LivingEntity.travel()` (1.21.10 lines 1295–1317; 1.21.11 lines 1360–1382). For the dry, already-fall-flying case, the corresponding `LivingEntity.travel()` proceeds to `travelFallFlying()` (1.21.10 lines 2275–2284; 1.21.11 lines 2309–2317). The excluded abilities-flying condition also avoids the distinct `Player.onClimbable()` override that returns false for abilities-flying players. The unchanged LocalPlayer jump-start gate cited in r2 is consistent with its source, but is not needed to establish the already-fall-flying route.

### Source anchors and hashes

- `decompiled_minecraft/1.21.10/mojmap/net/minecraft/data/tags/VanillaBlockTagsProvider.java` — `2393020635c9dbbe3cb2fa259870044d232ed215e45ed9acf048114b0904a980`
- `decompiled_minecraft/1.21.11/mojmap/net/minecraft/data/tags/VanillaBlockTagsProvider.java` — `8c17f2a6c6654721019e575377523edb0ac68c71b8325188a4e6412962c83474`
- `decompiled_minecraft/1.21.10/mojmap/net/minecraft/world/entity/LivingEntity.java` — `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66`
- `decompiled_minecraft/1.21.11/mojmap/net/minecraft/world/entity/LivingEntity.java` — `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8`
- `decompiled_minecraft/1.21.10/mojmap/net/minecraft/world/entity/player/Player.java` — `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82`
- `decompiled_minecraft/1.21.11/mojmap/net/minecraft/world/entity/player/Player.java` — `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81`
- `decompiled_minecraft/1.21.10/mojmap/net/minecraft/client/player/LocalPlayer.java` — `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1`
- `decompiled_minecraft/1.21.11/mojmap/net/minecraft/client/player/LocalPlayer.java` — `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477`

## Scope boundary

ACCEPT for the immutable r2 claim and the bounded 1.21.10→1.21.11 source difference. This verdict does not establish complete pair coverage, exact packaged/generated resource payloads or datapack overrides, intermediate patch boundaries, implementation applicability, or runtime parity.
