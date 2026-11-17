# Berry bush swept-contact witness — corrected r1

## Verdict

The 1.21.1 to 1.21.2 source change is supported. A swept intermediate sweet-berry-bush callback can store slowdown for a non-flying `LocalPlayer` whose final AABB has cleared the bush. The corrected route uses a side graze: the player box stays 0.0125 blocks outside the age-1 bush's physical collision shape while still extending into its voxel cell, which the newer inside-block scan enumerates.

The concrete path is conditional on the player entering ordinary non-fall-flying travel with displacement `(2.1, 0.2, 0)`. Vanilla explosion knockback can add enough horizontal velocity to provide that input, but the inspected sources do not establish that four explosion packets are applied before the same travel tick. This is a source-derived conditional movement witness, not a deterministic tick-by-tick runtime reproduction.

## Exact source identity

Sources are the canonical Mojmap trees at `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/<version>/mojmap/`.

- **1.21.1:** the ready marker names version `1.21.1`, Mojmap, client SHA-256 `499f6897d1837516680f3114072d8106e11c9adcd933fe5cf051b551089b0c99`, and source manifest SHA-256 `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48`.
- **1.21.2:** embedded `.decompile-provenance.json` SHA-256 `00F399407DC447737EA83E3E46562418F8C712F66E3F51CB85D46AFA2ECAC575`; pinned version metadata SHA-256 `793e3fd0451354a26aaa00253dcb6600add11f9d2a90f7bdef75ddf5b38c2b45`; client SHA-256 `613091da0f62426ae8dcf9afb30eb639485f493b81cda3a2b96b1fb8a6b9976c`; Mojmap client mappings input SHA-256 `e37560e4889168916a73dbc4922c563002eff70fbecfe1cf5c50a7b222f451b2`.
- **1.21.3:** ready marker names version `1.21.3`, Mojmap, client SHA-256 `25190c556bc56d11070638ccab3b3c6efd76bb9684c92a2680a4de01c3c7138b`, and source manifest SHA-256 `d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce`.

The seven relevant 1.21.2 files `Entity.java`, `LivingEntity.java`, `LocalPlayer.java`, `Player.java`, `BlockGetter.java`, `BlockBehaviour.java`, and `SweetBerryBushBlock.java` are byte-identical to their 1.21.3 counterparts. Hashes are recorded below.

## Provider and consumer difference

**1.21.1 provider:** `world/entity/Entity.java`, `move(MoverType, Vec3)` line 705 calls `tryCheckInsideBlocks()` after movement. `checkInsideBlocks()` at lines 1001–1034 derives its cell bounds only from the current AABB (`min + 1.0E-7`, `max - 1.0E-7`) and calls `entityInside` only in those final-box cells. For the path below the final box begins at x=2.3, so it excludes the bush at x=1. `world/level/block/SweetBerryBushBlock.java`, `entityInside` lines 80–91, applies `makeStuckInBlock` to living entities other than foxes and bees.

**1.21.2 and 1.21.3 consumer:** `LivingEntity.tick` travels, then invokes `applyEffectsFromBlocks()` when server-side or controlled by the local instance (1.21.2 `LivingEntity.java:2771–2775`). `LocalPlayer.tick()` calls `super.tick()` (`LocalPlayer.java:189–191`); `LocalPlayer.isEffectiveAi()` returns true (`LocalPlayer.java:478–480`), satisfying `Entity.isControlledByLocalInstance()` (`Entity.java:3149–3150`). For a non-flying, non-fall-flying player outside fluids, `Player.travel` delegates to `LivingEntity.travel` (`Player.java:1445–1465`), whose `travelInAir` calls `move(MoverType.SELF, getDeltaMovement())` before in-air damping (`LivingEntity.java:2178–2213, 2351–2361`).

`Entity.applyEffectsFromBlocks` records the old-to-current position segment and passes it to `checkInsideBlocks` (`Entity.java:736–767, 1038–1078`). `BlockGetter.boxTraverseBlocks` first checks `delta.lengthSqr() < Mth.square(0.99999F)`; below that strict threshold it returns only final-box cells (`BlockGetter.java:172–177`). Otherwise its DDA traces the travel path and expands each hit cell by the deflated box dimensions (`BlockGetter.java:192–245`). The sample segment has squared length 4.45, so it takes the sweep branch.

The scanner asks `getEntityInsideCollisionShape`. `BlockBehaviour` defaults this method to `Shapes.block()` (`BlockBehaviour.java:353–355`). The berry bush does not override it; for that sentinel the scanner skips `collidedWithShapeMovingFrom` and calls `entityInside` (`Entity.java:1052–1061`). The callback stores `(0.8F, 0.75, 0.8)` without an age gate; age only gates the later server damage clause (`SweetBerryBushBlock.java:79–92`). `Player.makeStuckInBlock` forwards the multiplier only when `abilities.flying` is false (`Player.java:1582–1586`). At the next `Entity.move`, the request is multiplied by the stored vector, then the vector resets and delta movement is zeroed (`Entity.java:633–637`). The slowdown is therefore stored after this travel and consumed by the next move.

## Corrected map and geometry

Use a loaded flat dirt floor at y=0 and an age-1 sweet-berry bush at block `(1,1,0)`. `BushBlock.mayPlaceOn` accepts dirt (`BushBlock.java:23–25`). There are no other obstacles. Use a standing player with non-flying abilities and no fall-flying pose. Set the movement witness positions to:

- From `(0.5, 1.1, 1.25)`
- To `(2.6, 1.3, 1.25)`

The standing dimensions are width `0.6`, height `1.8` (`Player.java:136`). Thus the final AABB is `[2.3,2.9] × [1.3,3.1] × [0.95,1.55]`. The bush voxel is x `[1,2]`, y `[1,2]`, z `[0,1]`; the final AABB is past its x extent, so the 1.21.1 final-cell scan does not call it.

Age 1 uses `MID_GROWTH_SHAPE = Block.box(1,0,1,15,16,15)` and returns it from `getShape` (`SweetBerryBushBlock.java:36–38, 54–60`). In world coordinates the bush collision shape's z maximum is `0.9375`; the player's swept z minimum is `0.95`, leaving a `0.0125` clearance. `BlockBehaviour.getCollisionShape` delegates to the block's shape when collision is enabled (`BlockBehaviour.java:312–313`). The player therefore moves alongside the bush without being clipped by its age-1 collision shape.

For the new scan, `delta=(2.1,0.2,0)` and `lengthSqr=4.45`. Deflating the final player box by `1.0E-5F` gives approximately minimum `(2.30001,1.30001,0.95001)` and size `(0.59998,1.79998,0.59998)`. The helper reconstructs the swept start near `(0.20001,1.10001,0.95001)`. Its DDA crosses x=1 at about y=1.176 and z=0.95. For that hit, x expansion reaches `floor(1 + 0.59998)=1`, y expansion covers rows 1–2, and z expansion reaches `floor(0.95 + 0.59998)=1`; the returned candidates include `(1,1,0)`. The final box itself contains only x-cell 2, so this candidate is intermediate-only for the callback scan, while physical collision remains clear because of the z gap.

## Reachability and inference boundary

`ServerExplosion.hurtEntities` computes an outward impulse and adds it to existing velocity (`ServerExplosion.java:170–213`). The default explosion damage calculator has knockback multiplier 1 (`ExplosionDamageCalculator.java:23–25`). Server code includes the result in `ClientboundExplodePacket` (`ServerLevel.java:1102–1110`); the client handler adds that vector directly to the local player's delta movement (`ClientPacketListener.java:1225–1242`). Thus aligned explosions can create a local-player velocity above the scan threshold without flight, and normal air travel sends that velocity into `Entity.move`.

For example, four unobstructed power-4 explosions centered 3.1 blocks behind a standing player at eye height and the same z each contribute roughly `1 - 2.8/8 = 0.65` horizontal impulse with default resistance, for roughly 2.6 total. A vector `(2.1,0.2,0)` at travel entry realizes the map path: the player rises 0.2, remains vertically within the bush's y range, and translates past it in x. This path does not require landings, flying, fall-flying or an absent block collision.

The inspected source proves the impulse accumulation and packet handler, but does not prove that all four packets arrive before one specific local travel tick. Treat the incoming velocity/tick alignment as an explicit precondition of this source witness. No exact runtime frame, natural occurrence frequency, or client/server packet schedule is claimed.

## Source hashes

SHA-256 values read from the canonical source files:

- 1.21.1 `world/entity/Entity.java`: `B81905C7879E2CC5C5063A41D865C4164AD919F156306705BE791D1017B99850`
- 1.21.1 `world/level/block/SweetBerryBushBlock.java`: `615B36436B64E20F6F33235AA2E24AA84A3D0E9B54FF27AA7D97821D8FFB6D3C`
- 1.21.2 and 1.21.3 `world/entity/Entity.java`: `A93719C302A0381A972AF75EA360465E2E3551708DD07C34D4D40B7E5173C2B9`
- 1.21.2 and 1.21.3 `world/entity/LivingEntity.java`: `087390495F4FDFD14B9E12230E7AEA4FDC50913BB4D1D91119E72892881CFC52`
- 1.21.2 and 1.21.3 `client/player/LocalPlayer.java`: `FBD40F1F47ADFA66DDA9B15188E5DCE82AF3E8E8D7C3DD0543E602A354AD3FE0`
- 1.21.2 and 1.21.3 `world/entity/player/Player.java`: `A803203E92AA4729D5F5C9B16085B6A43CE51D9907D309EB96736E9C7C1340DE`
- 1.21.2 and 1.21.3 `world/level/BlockGetter.java`: `5D6FDE6FEF7928603FF97F4A4015EFBB48442F9E583F1D0CFE368C5168E01C39`
- 1.21.2 and 1.21.3 `world/level/block/SweetBerryBushBlock.java`: `529BB21DF59F802B83A589440DC01D9A3B8814499B58B0624C83E4B24A85D2FF`
- 1.21.2 and 1.21.3 `world/level/block/state/BlockBehaviour.java`: `4D92417129EB6DEF084A67D249D2FF8C7A30A4767FA46E63A045667EA70D5133`
- 1.21.2 `world/level/ServerExplosion.java`: `C6B558532C1D0503FC6219D29B71AD3BD6F4DE28C73847D0E2C533BD73F96DDE`
- 1.21.2 `server/level/ServerLevel.java`: `C664C8FF5807EED64B5F627AFD6908F5BACCF4ACAB504EF4DAB6E42AD2FEF00D`
- 1.21.2 `client/multiplayer/ClientPacketListener.java`: `169E1EDAF666EBEB4AD736323565E28935A247748FECB67333DD5DB53F24FB8A`

No runtime, test, build, decompilation or network validation was performed. The first observed release containing the swept path is 1.21.2; no exact source cutover within an uninspected interval is inferred.