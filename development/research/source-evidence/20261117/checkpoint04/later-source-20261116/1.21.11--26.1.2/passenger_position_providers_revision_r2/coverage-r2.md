# Passenger-position provider crosswalk: 1.21.11 to 26.1.2

## Exact source identity

Older source: `decompiled_minecraft/1.21.11/mojmap/`, version `1.21.11`, namespace `mojmap`. Root sidecars: `decompiled_minecraft/1.21.11/mojmap.provenance.json` SHA-256 `99c9fb741eb5e4ac8fb5780e2158da324d7c8b6afc179d494e5986f78fe29f72`; `mojmap.ready.json` SHA `0ad98d0ebd654650492c97eb58bc324d33864787f0ce10d99faec3ae2f5b804b`; `mojmap.sources.sha256` SHA `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`. Ready identity: exact version/mapping, `ready`, 6,622 source files.

Newer source: `decompiled_minecraft/26.1.2/unobfuscated/`, version `26.1.2`, namespace `unobfuscated`. Root sidecars: `decompiled_minecraft/26.1.2/unobfuscated.provenance.json` SHA `8d727f880cc91f4a297e148c5b75976303d051457334bc689268fbdf631a012a`; `unobfuscated.ready.json` SHA `f24af0f4d38543c2bd9c19b50f347d4e89d6027050e4b21ae070734843946bb7`; `unobfuscated.sources.sha256` SHA `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0`. Ready identity: exact version/mapping, `ready`, 6,882 source files. Actual root-sidecar forms and ready-bound manifest identities were checked. No full source-tree rehash or artifact inspection was performed.

## Inventory and checked path

Recursive search across both exact Java trees found these declarations:

- `Entity.positionRider`, `Entity.getPassengerRidingPosition`, `Entity.getPassengerAttachmentPoint`, `Entity.getDefaultPassengerAttachmentPoint`.
- `LivingEntity.getPassengerRidingPosition`.
- `getPassengerAttachmentPoint` overrides in `AbstractBoat`, `AbstractMinecart`, `AbstractHorse`, `Llama`, `Camel`, `Slime`, `Strider`.
- `positionRider` overrides in `AbstractBoat`, `Minecart`, `AbstractHorse`, `Chicken`.
- `AbstractBoat.rideHeight` implementations in `Boat`, `ChestBoat`, `Raft`, `ChestRaft`.

The common entity registry declares these keys/classes at both endpoints: boats and chest boats for Acacia, Birch, Cherry, Dark Oak, Jungle, Mangrove, Oak, Pale Oak and Spruce; `CAMEL`, `CHICKEN`, `DONKEY`, `DOLPHIN`, `GOAT`, `HORSE`, `LLAMA`, `MINECART`, `MULE`, `SLIME`, `STRIDER`, `TURTLE`, and `ARMADILLO`. This crosswalk does not classify modern-only entity types.

The base positioning route agrees: `Entity.positionRider` checks `hasPassenger`, computes vehicle `getPassengerRidingPosition`, subtracts the passenger's `getVehicleAttachmentPoint`, then invokes the movement function with those XYZ offsets. `Entity.getPassengerRidingPosition` adds the provider result to vehicle position using its dimensions, passenger index and yaw; the default provider selects a clamped `PASSENGER` attachment. Same order, inputs and writes (`Entity.java`: old 2262–2290; new 2322–2350). `LivingEntity.getPassengerRidingPosition` likewise adds the vehicle position after calling the provider with current-pose dimensions and `getScale() * getAgeScale()` (old `LivingEntity.java:3814–3816`; new 3925–3927). Base `LivingEntity.getAgeScale()` and `getScale()` bodies match (old 535–543; new 531–539).

| Provider | Paired behavior and player relevance | Declaration ranges |
| --- | --- | --- |
| `AbstractBoat` | Attachment uses `rideHeight(dimensions)` plus passenger-count/index X offsets; a second seat gets opposite X offset and `Animal` passengers add `0.2F`. The `Animal` condition is false for a Player. `positionRider` first applies the common position then changes passenger yaw/head yaw by `deltaRotation` unless type tag `CAN_TURN_IN_BOATS`; extra full-seat body rotation is Animal-only. Code/order/constants agree. Tag key is declared in both; effective membership/resource data unexamined. | Old `AbstractBoat.java:136–151, 623–635`; new `:136–151, 627–639`. `getSinglePassengerXOffset()` is `0.0F` at old `:614–618` / new `:618–622`. |
| `Boat`, `ChestBoat` | `rideHeight = dimensions.height() / 3.0F`; same. | Old/new `Boat.java:15–17`; `ChestBoat.java:15–17`. |
| `Raft`, `ChestRaft` | `rideHeight = dimensions.height() * 0.8888889F`; same. | Old/new `Raft.java:15–17`; `ChestRaft.java:15–17`. |
| `AbstractMinecart` | Lower attachment is used only for `Villager` or `WanderingTrader`; Player takes generic attachment. Same predicate/result. | Old `AbstractMinecart.java:171–174`; new `:178–181`. |
| `Minecart` | After common positioning, client-only Player yaw correction is gated by `shouldRotateWithMinecart()` and experimental movement. It uses `rotLerp(0.5, playerRotationOffset, rotationOffset)`, subtracts the interpolated delta from yaw, then stores the new offset. Same gate/order/math; this affects yaw, not target position. | Old/new `Minecart.java:82–90`. |
| `AbstractHorse` | Calls common position then copies vehicle `yBodyRot` to a `LivingEntity` passenger. Its attachment adds the same rotated `(0, 0.15 * standAnimO * scale, -0.7 * standAnimO * scale)` to inherited point. Player is a `LivingEntity`; same code. | Old `AbstractHorse.java:919–923, 1025–1028`; new `:930–934, 1038–1041`. |
| `Llama` | Uses shared default passenger attachment based on its dimensions' `PASSENGER` attachments; same. | Old `Llama.java:448–450`; new `:449–451`. |
| `Camel` | Seat method matches: index/front-seat selection, multi-passenger rear offset, Animal-only `+0.2F`, body-anchor animation, scale and yaw rotation. Body-anchor arithmetic/order also matches. However, baby `getAgeScale()` changes from `0.45F` (1.21.11) to `0.6F` (26.1.2); adult remains `1.0F`. Inherited `LivingEntity.getPassengerRidingPosition` multiplies attachment scale by this value, so a passenger attached to a baby Camel receives a differently scaled point. Ordinary `Camel.mobInteract` calls `doPlayerRide` only when passenger count is below two and `!isBaby` (old `:365–385`; new `:374–394`); ordinary player mounting does not establish this baby case. Forced/synchronized attachment reachability remains open. | Old `Camel.java:480–501`; new `:489–510`; helper old `:503–535`; new `:512–544`. |
| `Goat` scale input to inherited provider | 1.21.11 `Goat` has no `getAgeScale` override and inherits base baby scale `0.5F`. 26.1.2 adds `Goat.getAgeScale()`, returning `0.55F` for a baby and `1.0F` otherwise (`Goat.java:157–159`). If a baby Goat is a passenger provider, inherited living-entity positioning passes a different scale. `GOAT` is registered on both sides; this report does not establish any ordinary Player-on-Goat route. | Old `Goat.java` has no override; new `:157–159`. |
| `Strider` | Client-only attachment adds vertical walk-animation bob `0.12F * cos(position * 1.5F) * 2.0F * min(0.25F, speed) * scale`; server path delegates directly. Same gates/order/constants. | Old `Strider.java:172–180`; new `:179–187`. |
| `Slime` | Returns the same point at `dimensions.height() - 0.015625 * size * scale`. No ordinary Player passenger route established. | Old/new `Slime.java:245–247`. |
| `Chicken` | Calls common position, then copies vehicle body rotation to a `LivingEntity` passenger; no offset override. Same. | Old `Chicken.java:242–247`; new `:274–279`. |

The custom `getAgeScale()` census also compared common `Turtle`, `Dolphin`, and `Armadillo`: their baby factors match at `0.3F`, `0.65F`, and `0.6F`. `Camel` and `Goat` are the common entity types with changed age-scale input to inherited living-entity passenger positioning. HappyGhast and `EntityType.HAPPY_GHAST` exist at both endpoints. `HappyGhast.getAgeScale()` returns `0.2375F` for babies and `1.0F` otherwise in both sources; it has no passenger-position override and inherits `LivingEntity` positioning. `canAddPassenger` allows fewer than four passengers in both versions. This unchanged route is included in the common-entity census; ordinary Player passenger reachability is not established here.

## Bounded verdict and open gates

The generic position-write route and listed provider bodies match, including boat/minecart/horse/strider/chicken conditional writes. Two conditional scale-input differences were found through inherited `LivingEntity.getPassengerRidingPosition`: baby Camel `0.45F → 0.6F`, and baby Goat from inherited `0.5F → 0.55F`. Source proves their effect on provider scale if that entity has a passenger; it does not establish an ordinary Player mounting route for either baby animal. This is a partial provider inventory, not a player gameplay finding, whole-pair coverage or movement parity verdict.

Open: ordinary Player passenger reachability for baby Camel/Goat; server attachment packet production/synchronization; effective `CAN_TURN_IN_BOATS` membership; vehicle `SCALE` attribute values and data-driven inputs; player-side `VEHICLE` attachment changes; remaining movement tick areas and intermediate stable boundaries. Vehicle physics and non-player trajectories are excluded.

## Cited Java hashes

All hashes below were independently recomputed from current source bytes and matched the corresponding ready-bound `*.sources.sha256` row.

| Relative path | 1.21.11 SHA-256 | 26.1.2 SHA-256 |
| --- | --- | --- |
| `net/minecraft/world/entity/Entity.java` | `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3` | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` |
| `net/minecraft/world/entity/LivingEntity.java` | `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8` | `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` |
| `net/minecraft/world/entity/EntityType.java` | `fc2abb3e905b9a63a27f0b027fad089d9d40e5d3d45608406b5da2a5ec47a73e` | `e5f37ee2dd639b7271197e58f7a6f3bdbedc0f0f1d618039d159a0e7de315229` |
| `net/minecraft/tags/EntityTypeTags.java` | `12dd847ea92c7bf5b581e62b1d5480219a7f261494678003cec7b4925d3d1e0a` | `c8b0514f6e3adc670f5093a705a183f0eaedbc954621e16260648af70722a51c` |
| `net/minecraft/world/entity/vehicle/boat/AbstractBoat.java` | `e7d22b0d7a7c204eea153d1dcac7f74b4e4738fe33bdd613aff9ebcc924aa3cf` | `8d681d6ac6e93a648640fd39cde921cc8ed9d4aec03a49a9e9e43b28cb7be840` |
| `net/minecraft/world/entity/vehicle/boat/Boat.java` | `476fb64c69c742495f3043608508f20499db109ab0a3a6cafe02c8e503e1af3b` | `b4ad2291573e1e352fc24ab0b337d252c67cf6158ca3d49acf97d75a94cf2840` |
| `net/minecraft/world/entity/vehicle/boat/ChestBoat.java` | `e1a1c195b7841309100c474d9530352a08078041581d41a623710f3945e39e7f` | `c839a12188a7fe593018d423a42070117695b837333da4474f59b247c170950a` |
| `net/minecraft/world/entity/vehicle/boat/Raft.java` | `531a582be1398677650b5e8e076fe836ab8758491582e816521fd14f9cb04b56` | `ea0fde13a7eb021b97a3d34f563fb67538436b6c175d8dca628eb849e94dbec2` |
| `net/minecraft/world/entity/vehicle/boat/ChestRaft.java` | `9e2bcbbd7a9e24a3d9c743db64dc2a308838775084c07d224bc3262b58b39123` | `26a4525c4dadb70259e573d404fe1ae66e501120cc3d5a91d9dddf2119651b4a` |
| `net/minecraft/world/entity/vehicle/minecart/AbstractMinecart.java` | `7c5257f58d71fc8c1648773198d88af5eccf9c942e39ae61dc46e5a08ebf82ed` | `01349d104be9ec0c598267ea5e9a333ef55a9a1beec6e037b21017f1fa2f12c7` |
| `net/minecraft/world/entity/vehicle/minecart/Minecart.java` | `5213cba5b6cc4969bd99dda1e8471c9f9492f7adadb4681efa5cd562c48d5c4a` | `5be9ae555d46ed4b600f438e8dffb666753eb812be8af2dd9d10a9c827775471` |
| `net/minecraft/world/entity/animal/equine/AbstractHorse.java` | `2224455679a16c3f64250c31b54b13b3f9b3adb96e250fdeb7de919d4504e8cc` | `bf9232ea2f4473c874043abcdab8955f2f0f90117d4ffeb269531dbb1b758b0c` |
| `net/minecraft/world/entity/animal/equine/Llama.java` | `8f3551733916683cf15bf14ce102f86f3a72a409180cefae275be774c0c42bc0` | `4d3a51648f3a825ccb5f8e2f5714d5194224e3f1dc47f5a0900b4bf6b9257fb5` |
| `net/minecraft/world/entity/animal/camel/Camel.java` | `fe30d80e2f91f4cf8098ef82811cf733cb487efadaaafc36d7034e35707bf1a1` | `9d693fc15dbed34caa6e880e8559a4e96bdecf731c508da0101dc943a1632c1f` |
| `net/minecraft/world/entity/animal/happyghast/HappyGhast.java` | `0c2a0b3a97d8c7bc2de50924565c55367247f5cd7d9b59ff4fde3692e25610ea` | `798c1eb723e4e9640dd342c645829460b98029360d88a1467dc8170cfa54606e` |
| `net/minecraft/world/entity/animal/goat/Goat.java` | `53ee17a50efc45d15ef6b3056a5d456341c26ae5e4f6b1a21a26c311fae95ab7` | `b1588897a9002c389978b38e65a1b5b207cabed27b2d786b890dfd4856f94ee5` |
| `net/minecraft/world/entity/animal/chicken/Chicken.java` | `f1134a0774ee4d023147482e84386370a242d7a37440a18c9e8891e69230e1ce` | `3f3267c86f0db80d9af53f018dd7718441c03a24afb8fa09945156212d9fb7b0` |
| `net/minecraft/world/entity/monster/Slime.java` | `92ef65b853a5e1bb88c99f0644d9ecaf002ceca8d87ab05ae8caa4bc76e7e9ab` | `d34054775bc77b08d03817134e8ddc89322273d5126275a713a9264af59861c2` |
| `net/minecraft/world/entity/monster/Strider.java` | `4faf3019fa255b8468687734164311a7ed003cb31e1f2752577576dccfc1394a` | `8561c9f060dfc0c4e93ea8d17f3a671a3c8fd61d86c2bb0f12c5f0c50a8d83dc` |
| `net/minecraft/world/entity/animal/turtle/Turtle.java` | `f57fa8b458d1fe5f4cb35c7955c4e8094eb1b4107cd99debbaefba252cde224d` | `b69e3979425c43b1484272534321969539aab64debe406af7ac45f5cae7f3b34` |
| `net/minecraft/world/entity/animal/dolphin/Dolphin.java` | `40f74c1b1a645ed6be1f3c13a96419e83a712624d1824e81200ebd2633f1c93b` | `892cc2ee6ca5a3eae200bf9136378f309beec5b4d89d14173a43e480f8cc7c5c` |
| `net/minecraft/world/entity/animal/armadillo/Armadillo.java` | `814d2879b31436d4509402dcb82fa2faa6fdc15a02bfaa570033ce258a2c8ba1` | `cdb188e706bef6425fcea3acf2beae5777bb469c39f05a91e4965160034f170b` |

No canonical finding, backlog or product file was changed. No build, test, runtime, generation or Git operation was performed.
