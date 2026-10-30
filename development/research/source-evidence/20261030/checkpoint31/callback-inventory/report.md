# Block-inside callback inventory: 1.21.2/1.21.3 versus 26.2

## Scope and result

This bounded source-only inventory enumerates every old `Block.entityInside` override and the player-relevant `onInsideBlock` overrides at 1.21.2 Mojmap and 1.21.3 Mojmap, then compares the relevant client response bodies and typed/deferred callback API use with 26.2 unobfuscated. The 1.21.2 and 1.21.3 callback source files listed below are byte-identical. This is not pair-wide movement coverage or runtime-parity evidence.

The old local-player sweep reaches synchronous block callbacks after travel; its accepted dispatch contract is `BlockState.entityInside` → virtual `onInsideBlock` → add the state to the old `blocksInside` summary, with old candidate/shape eligibility and old finalizer boundaries preserved. It dispatches block callbacks only, not fluid callbacks. 26.2 adds an effect applier and deferred stage collector.

**Old reachable client callback families with movement-relevant work that require an explicit old-policy decision:**

- `BubbleColumnBlock`: the old callback always calls the movement response and selects above-vs-inside using `above.isAir()`. 26.2 calls movement response only when `isPrecise`, and its above test is empty collision shape plus empty fluid rather than air. Preserve the old callback gate and old air predicate; do not rely on the modern callback body under arbitrary sweep labels.
- `BaseFireBlock`: old local-client visits update the entity fire timer inline, may ignite it, and call hurt inline. 26.2 registers clear-freeze/fire-ignite/hurt through typed effects, with fire ignition coalesced by stage and hurt deferred. Preserve the old immediate fire-timer response and do not run collector registrations as old callback semantics. Damage itself remains excluded by the project invariant.
- `LavaCauldronBlock`: old callback applies the cauldron-content predicate then calls `lavaHurt()` inline. 26.2 registers clear-freeze/lava-ignite/hurt without that callback-body content gate. Preserve the old content gate and synchronous response; the old `lavaHurt` damage component remains excluded.
- `PowderSnowBlock`: old stuck response and powder-snow flag are immediate; 26.2 defers freeze/extinguish and block-destruction work. Retain the corrected old gate/order; do not substitute modern collector effects.
- `LayeredCauldronBlock`: old callback is a `ServerLevel` path that clears fire before interaction checking and immediately writes the cauldron state. 26.2 defers the server write and registers extinguishing outside that server-only branch. A client old-profile sweep has no corresponding old response; suppress the new client typed effect.

`HoneyBlock`, `WebBlock`, and the movement portion of `SweetBerryBushBlock` remain direct and body-compatible for the old client response under the historical dispatcher. `EndPortalBlock` and `NetherPortalBlock` also directly set portal-process state on a local client when `canUsePortal(false)` passes, but this is portal transition/UI state, not a player movement-physics response in this inventory. Other old callbacks are server-only, restricted to non-player entity types, or limited to excluded damage/status/world effects as itemized below. The 26.2 `WaterFluid`/`LavaFluid` swept callbacks have no old block-sweep equivalent and must not be introduced into the historical callback sequence.

## Complete old override inventory and endpoint comparison

Ranges are the enclosing `entityInside` method (or base method); source paths are relative to each release root. `Unused applier` means the 26.2 callback receives the argument but neither registers nor invokes it, apart from forwarding it to the empty base method where noted.

| Old callback | 1.21.2/1.21.3 range | 26.2 range / class | Player-relevant source result |
|---|---|---|---|
| `BaseFireBlock.entityInside` | `BaseFireBlock.java:130–146` | `BaseFireBlock.java:131–142` | Old timer update/ignite/hurt is inline. Modern registers `CLEAR_FREEZE`, `FIRE_IGNITE`, and per-callback `runAfter(FIRE_IGNITE, hurt)`; timing and coalescing differ. See accepted family closure and correction below. |
| `BasePressurePlateBlock.entityInside` | `BasePressurePlateBlock.java:74–81` | same class `:84–98` | Both gate on server side and only invoke pressed check when unpowered; no local-client player response. Modern applier unused. |
| `BigDripleafBlock.entityInside` | `BigDripleafBlock.java:188–194` | same class `:192–205` | Both gate tilt/scheduling to server side; world-state producer only. Modern applier unused. |
| `BubbleColumnBlock.entityInside` | `BubbleColumnBlock.java:50–77` | same class `:53–70` | Old immediately calls `onAboveBubbleCol` iff above state is air, else `onInsideBubbleColumn`. Modern movement calls are inside `isPrecise`; “nothing above” is empty collision shape **and** empty fluid. This is a direct velocity/fall-distance response and a gate difference. Applier unused. |
| `ButtonBlock.entityInside` | `ButtonBlock.java:179–183` | same class `:156–167` | Both are server-only arrow-activation paths for an unpowered eligible button. No local-client response; applier unused. |
| `CactusBlock.entityInside` | `CactusBlock.java:108–110` | same class `:126–135` | Both call cactus damage. Damage/health response is excluded; no separate movement write found. Applier unused. |
| `CampfireBlock.entityInside` | `CampfireBlock.java:107–113` | same class `:116–129` | Both damage a living entity when lit and forward to base. Damage excluded; base is empty. Applier unused. |
| `CropBlock.entityInside` | `CropBlock.java:166–172` | same class `:154–167` | Both destroy only on `ServerLevel` for a Ravager with mob griefing enabled; modern forwards to empty base. No local-player response. |
| `DetectorRailBlock.entityInside` | `DetectorRailBlock.java:50–56` | same class `:56–69` | Both are server-only unpowered-rail press checks. No local-client response; applier unused. |
| `EndGatewayBlock.entityInside` | `EndGatewayBlock.java:89–94` | same class `:89–104` | Both require portal eligibility, server side, a present gateway block entity, and no cooldown before portal handling/cooldown. Server-only; no local-player callback response. |
| `EndPortalBlock.entityInside` | `EndPortalBlock.java:58–66` | same class `:61–76` | Both use `canUsePortal(false)`; client path calls `setAsInsidePortal`, server End credits branch is separate. Local client portal-process state is direct but not movement physics. Applier unused. |
| `FrogspawnBlock.entityInside` | `FrogspawnBlock.java:83–87` | same class `:94–105` | Both destroy only when the entity type is `FALLING_BLOCK`; no player response. Applier unused. |
| `HoneyBlock.entityInside` | `HoneyBlock.java:62–70` | same class `:64–79` | Accepted closure: same sliding gates, velocity/fall-distance writes and slide effect gates; modern applier/precision are unused. Immediate client movement response is reusable at body level. |
| `HopperBlock.entityInside` | `HopperBlock.java:183–188` | same class `:163–174` | Both delegate only when a hopper block entity is present. The shared helper further requires a nonempty `ItemEntity` intersecting the hopper suck AABB; no player response. Applier unused. |
| `LavaCauldronBlock.entityInside` | `LavaCauldronBlock.java:34–38` | same class `:47–58` | Old content-height/bounding-box gate then synchronous `lavaHurt`; modern schedules typed lava effects/hurt without that body gate. Special handling required. |
| `LayeredCauldronBlock.entityInside` | `LayeredCauldronBlock.java:63–70` | same class `:85–103` | Old requires server, on-fire, and content gate; clears fire before permission test and immediately lowers cauldron if allowed. Modern queues a server consumer and always registers `EXTINGUISH`; no old client callback response. Special handling required to suppress new client effect. |
| `NetherPortalBlock.entityInside` | `NetherPortalBlock.java:104–108` | same class `:112–123` | Both call `setAsInsidePortal` when `canUsePortal(false)`. Direct client portal-process state, outside movement-physics response. Applier unused. |
| `PitcherCropBlock.entityInside` | `PitcherCropBlock.java:102–108` | same class `:120–126` | Both destroy only on server for a Ravager with mob griefing enabled, then forward to base. No local-player response. |
| `PowderSnowBlock.entityInside` | `PowderSnowBlock.java:60–88` | same class `:62–103` | Old stuck response and powder-snow flag are immediate; server destruction/fire-flag order is immediate. Modern keeps stuck/particles inline but unconditionally registers `runBefore(EXTINGUISH)` and later `FREEZE`/`EXTINGUISH`. Special handling required. |
| `SweetBerryBushBlock.entityInside` | `SweetBerryBushBlock.java:79–93` | same class `:82–103` | Both immediately apply the same stuck multiplier for LivingEntity except fox/bee. Berry damage branch is server-only. Modern applier/precision are unused; client movement response is reusable at body level. |
| `TripWireBlock.entityInside` | `TripWireBlock.java:142–148` | same class `:150–163` | Both callback bodies are server-only. 26.2 additionally skips if a tick is already scheduled, which only changes server redstone timing. No local-client movement response. |
| `WaterlilyBlock.entityInside` | `WaterlilyBlock.java:31–36` | renamed `LilyPadBlock.java:34–46` | Both forward to empty base and destroy only for `ServerLevel` plus `AbstractBoat`. No player response; this is a class-name mapping change. |
| `WebBlock.entityInside` | `WebBlock.java:26–33` | same class `:27–41` | Accepted closure: same weaving gate, stuck multipliers, reset-fall-distance and immediate multiplier write. Modern applier/precision unused. Reusable at callback-body level. |
| `WitherRoseBlock.entityInside` | `WitherRoseBlock.java:65–72` | same class `:67–81` | Both only add an effect on `ServerLevel` for eligible living entities outside Peaceful; modern effect source changes to the bee interaction effect. No local-client response; status-effect/damage domain is outside this client callback slice. |

In addition, 1.21.2/1.21.3 `BlockBehaviour.entityInside` is an empty base method (`BlockBehaviour.java:350–351`), and `BlockStateBase.entityInside` forwards directly to its block (`:736–737`). 26.2 keeps an empty base callback (`BlockBehaviour.java:377–385`) and forwards the applier/precision through the state delegate (`:780–783`). The old scan contained exactly the 24 override classes in the table; the separate Hopper static helper is not a `Block.entityInside` override.

## Player hook inventory and callbacks outside the player sweep

- `Entity.onInsideBlock` is empty in 1.21.2/1.21.3 (`Entity.java:1086–1088`) and 26.2 (`Entity.java:1406–1408`). Neither old `Player` nor old `LocalPlayer` overrides it, so the ordinary local-player sweep has no additional hook body after the block callback.
- `ServerPlayer.onInsideBlock` triggers `CriteriaTriggers.ENTER_BLOCK` in old and modern sources (`1.21.2/1.21.3 ServerPlayer.java:639–641`; `26.2 ServerPlayer.java:571–573`). This is not a logical-client LocalPlayer response.
- `ThrownEnderpearl.onInsideBlock` is a non-player entity callback that, for an `END_GATEWAY` state and a `ServerPlayer` owner, directly calls that server player's `onInsideBlock` (`1.21.2/1.21.3 ThrownEnderpearl.java:223–228`; 26.2 renamed package `.../throwableitemprojectile/ThrownEnderpearl.java:215–220`). This is the explicit callback-origin-outside-the-player-sweep route; it remains server-only advancement/criterion behavior, not local player movement. The modern and old hook bodies retain this condition and delegation.

`EndPortalBlock` and `NetherPortalBlock` are the old callback families with a direct LocalPlayer-side response: their client branch reaches `Entity.setAsInsidePortal`, which updates portal-process state (`1.21.2 Entity.java:2214–2225`; 26.2 `Entity.java:2605–2616`). LocalPlayer consumes the portal processor in its client tick (`1.21.2 LocalPlayer.java:815–844`; 26.2 `LocalPlayer.java:932–968`). The method path concerns portal timing/transition presentation and server transfer, not the movement integration, collision response or player box/velocity; this inventory records it but does not treat it as a historical movement callback delta.

For BubbleColumn movement writes, the old `Entity.onAboveBubbleCol` and `onInsideBubbleColumn` retain the same vertical caps/increments as the 26.2 `handleOnAboveBubbleColumn` and `handleOnInsideBubbleColumn` helpers: above uses `max(-0.9, y - 0.03)` when dragging down or `min(1.8, y + 0.1)` otherwise; inside uses `max(-0.3, y - 0.03)` or `min(0.7, y + 0.06)` and resets fall distance. The old above helper changes velocity only; modern above additionally emits its server particle effect. The player-physics difference in this closure is the callback admission/branch gate described above, not those shared velocity constants (`1.21.2 Entity.java:2455–2478`; 26.2 `Entity.java:2849–2896`).

## Typed effects and callback boundary

The complete 26.2 block/fluid scan found actual `InsideBlockEffectApplier` registrations only in these callback families:

| Callback | Typed registrations | Old counterpart / required old-policy treatment |
|---|---|---|
| `BaseFireBlock.entityInside` | `CLEAR_FREEZE`, `FIRE_IGNITE`, per-visit after-hurt | Old block callback updates fire ticks, ignites and hurts synchronously; use old inline response. |
| `LavaCauldronBlock.entityInside` | `CLEAR_FREEZE`, `LAVA_IGNITE`, per-visit `lavaHurt` after-action | Old callback has content gate and synchronous `lavaHurt`; preserve it. |
| `LayeredCauldronBlock.entityInside` | server `runBefore(EXTINGUISH, lower-cauldron consumer)` plus `EXTINGUISH` | Old callback is server-only and writes inline; do not apply the modern client extinguish registration. |
| `PowderSnowBlock.entityInside` | unconditional registration of `runBefore(EXTINGUISH, consumer)`, then `FREEZE`, `EXTINGUISH` | Old entity/world responses are inline with old server gate ordering. |
| `LavaFluid.entityInside` | `CLEAR_FREEZE`, `LAVA_IGNITE`, per-visit after-`lavaHurt` | No old swept-fluid callback. Omit from old traversal. |
| `WaterFluid.entityInside` | `EXTINGUISH` | No old `entityInside` override and no old swept-fluid callback. Omit from old traversal. |

All other modern block callback applier arguments are unused or only forwarded to the empty base callback. The collector's typed effects are not equivalent to synchronous old dispatch; accepted scheduler and family closures expressly reject replacing old behavior with a no-op or inline collector. `FREEZE`, `CLEAR_FREEZE`, `FIRE_IGNITE`, `LAVA_IGNITE`, and `EXTINGUISH` run through the after-sweep collector lifecycle in 26.2.

Modern-only callback domains in the scanned source are `EyeblossomBlock.entityInside` (a server Bee/effect branch) and swept `WaterFluid`/`LavaFluid` callbacks. There is no 1.21.2/1.21.3 block callback contract for Eyeblossom or those fluid visits. Do not infer historical behavior for them. The block-state eligibility closure separately covers changed 26.2 inside-shape providers for powder snow/cauldrons and the old end-portal/tripwire partial-shape cases; it is not re-audited here.

## Immutable inputs and canonical source identities

The assigned accepted six-family callback materials and supporting phase/shape evidence were read without product source or Git history:

| Input | SHA-256 |
|---|---|
| `.native-source/20261030/callback-eligibility-closure/report.md` | `d3d20d5a1a02d07fc509cc7b4af39348419f397b12652a908e0a053b9bb3450a` |
| `.native-source/20261030/callback-order-audit/report.md` | `1f8d091f1e52705a76447a8972cfeee13d4138516025c281f9bedb2c1beeb8d0` |
| `.native-source/20261030/dda-collector-flush/report.md` | `bb3d871fa1d65051faa3a9a36b1c3839f07406bf18384c60aeb52f8d274d261e` |
| `.native-source/20261030/dda-collector-flush-audit/audit.md` | `d48a3bca758d408303cd5acbe5291a72ca5bf250c6faf3c4f02532b00724e1ba` |
| `.native-source/20261030/ordinary-pool/typed-callbacks/closure.md` | `b961cd61ffcd45871839b0978b1dc2bc804226a5347c03bd0f9236f08d90665c` |
| `.native-source/20261030/ordinary-pool/typed-callbacks-review/audit.md` | `ef064cea3390cc5a8d56434539be903afc641341d5bd450ed722cec5b10c7625` |
| `.native-source/20261030/ordinary-pool/fire-lava-cauldron/closure.md` | `049f663455719a749717866ed00a4f3986298b71da4aa9e453b27be364d1157d` |
| `.native-source/20261030/ordinary-pool/fire-lava-cauldron/correction-1-clear-fire.md` | `1e368c539dd00328414f287c7ec161c6f1d373c0fcf3e6ff1a96732ca2b906ab` |
| `.native-source/20261030/ordinary-pool/fire-lava-cauldron-review/audit.md` | `ebd41ead57b9f7d3df58dd8e24390bcc52d4f8ecf8cf45739c73199824fddd86` |
| `.native-source/20261030/ordinary-pool/powder-snow-water/closure.md` | `c7b9be695a36824c5ffee9653785716d7e7cda372952494b8eaf5b2201bbdfb6` |
| `.native-source/20261030/ordinary-pool/powder-snow-water/correction-1-effect-gate-timing.md` | `baab0e17c1a3f0e5dcdc2af5cbe613cb6fe9ab4a09ad34a93abe5b31f1b7e14e` |
| `.native-source/20261030/ordinary-pool/powder-snow-water/correction-2-line-anchor.md` | `8a28e6fc7668b4f6964ced172b3d00e428900154cba5ac80fe48f8ff06bad6bf` |
| `.native-source/20261030/ordinary-pool/powder-snow-water-review/audit.md` | `850d2359962d994eb1fbecae467d4decc6dd8e4478200341f47fe3a7c0546e3b` |
| `.native-source/20261030/ordinary-pool/shared-callback-phase-order/phase-order.md` | `7cf4ff49b5aeb6ec996a38ccb10e0dbdfc7fe0aadb5f08105ff23fc87b7741de` |

Canonical read-only source root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`.

- 1.21.2 Mojmap provenance: `.decompile-provenance.json` SHA-256 `00f399407dc447737ea83e3e46562418f8c712f66e3f51cb85d46afa2ecac575`; selected source file hashes below were recomputed and match the source identities carried by the accepted evidence.
- 1.21.3 Mojmap provenance: `mojmap.provenance.json` SHA-256 `24093437c0cd41d807ab0829068ef97828b65bbb95b38dfdc4f706883a8483d7`; ready/source-manifest SHA-256 `d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce`. Every old callback file and helper in this inventory was recomputed and byte-identical to 1.21.2.
- 26.2 unobfuscated provenance: `unobfuscated.provenance.json` SHA-256 `a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1`; ready marker SHA-256 `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f`; source-manifest SHA-256 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`. Actual callback/helper file hashes were recomputed below.

Selected `Block.entityInside` source hashes (one old hash applies identically to both Mojmap endpoints):

| Relative file (old → modern) | 1.21.2 and 1.21.3 Mojmap SHA-256 | 26.2 unobfuscated SHA-256 |
|---|---|---|
| `block/BaseFireBlock.java` | `2d610c87ba438b7ef25df922d004ac868d5526002ba86a14911080d317f33bfc` | `ce9460bfb19bb89a3e5c77fcd905544a792d5a3c1f4fa4a186393acdf129a39a` |
| `block/BasePressurePlateBlock.java` | `b4d967d4f274c1f08268876c8a9c175804abc8ce48d031b53ae888d35ce9a53f` | `59a21aab756fa63b5fedde28d8a2b7c20a4e7f24672b55b5bec68c7f990b3c94` |
| `block/BigDripleafBlock.java` | `030b4f36bdcdbbf294d349cea7adce9d6e71cfbae83daaa51ef67bd25327c0b1` | `6442924185357e52fb41d110596766cc98140058e5484af7c1ea04610bed816d` |
| `block/BubbleColumnBlock.java` | `9427b3e617753f5b3adc7a724fd80c87d4aa0e2f5d0eb38cd2ca34092e449b47` | `ece83c008e6f8391b468dafe445c288630215fdff490defee50cb7766771e039` |
| `block/ButtonBlock.java` | `1259ffda7781b8df1b5fe653a3a441bc8e8eaca4a4c287de99d6869ae373d346` | `d15e114293a3c0b4aaee55fe93df62eb9cf63d2535c9926da2b752fbdd87fb9d` |
| `block/CactusBlock.java` | `41ce88254fab5545bf8e194cbf87d7c5b23da5df3b72b929734bf3cb284fe9e8` | `22a5ae876e12ce061b89c9ad8e83fd6004ee17a0e6e8e88fec5f3d53e22b6e76` |
| `block/CampfireBlock.java` | `1af4bd6264e2322d6771233441cda587e3355b27b9e9ce0288af1266ca9977e3` | `2fc873eb9a56f8ba7617faf73518961eb470d80137b780286ce4dc06d867e011` |
| `block/CropBlock.java` | `61d3222b67856962fa038f8cfda47fc7aa0a8da2ff274b50bcc13d9aa44b6d37` | `3e5d5cb67ba7f2d58b02bd775f71d9d9eb13902b6ef94a709b380f96ad0ef633` |
| `block/DetectorRailBlock.java` | `6f71cdafbc3fa74fe2df45581780b6fcbf600a0be9a672ee9ee2702bb2113e2c` | `1e3104a45cc5aa384870b4cb75fb1c41a45ca3c52f33b023d86f552238b27400` |
| `block/EndGatewayBlock.java` | `4e4d64efa8aac4d8874635c39613401eec0d3805af3df2b29687fd84f50ec8a0` | `7db53ce90448064775f77f094249d0ae9115b410d5f1396943920320ddf4e8e7` |
| `block/EndPortalBlock.java` | `725bbf4f36daa1f57f343d3f3123a6c527f13d7bf3c51fba1b27515b65c8c863` | `75addd79af8670d89880c248d8bcbfaad15208b5d43c4dd2aa27487ef84bcfeb` |
| `block/FrogspawnBlock.java` | `213dbf472d424ade53e8f6b866ba83e3ddacc99ffc1a1e4d9206a844cbf1494c` | `d26d1224315b3e10f15fc8f2f42d725e78131a111c73344e0ef4d0f23ec5d98c` |
| `block/HoneyBlock.java` | `40bf4e5d55f44f7aec9716a46b11cd6ad08ed861be4d63a85544a1bfb595a71d` | `ad1626f8b2c7ac39bdae01de7f8d5dbec0fb4193057dea33e39e0c7cc3c1b6aa` |
| `block/HopperBlock.java` | `d3d08da4bab9bea33a12ba11dedfe1060b2cb5504b7e3a8b7c5757a056795bd3` | `e6687c0f3a34a796e5faf7bb04004c9b3cadc700119f417087eae4eb6aeb298a` |
| `block/LavaCauldronBlock.java` | `146f402a93d685e116067634d100646c31c40ed387a2163f50d30e3bbd7ff82e` | `979d958a18b76671c53cadf2a2b669a2a268a53008345a5d6b8d736e0b3584c5` |
| `block/LayeredCauldronBlock.java` | `57329d1bef6c0b39d4532bc518a1f83fadb605899a2a3ebdb3a609d2cefe5c47` | `18be2624a0c45b6f807627a0f0febabc48a9a7092dc918411e94b49276976c2d` |
| `block/NetherPortalBlock.java` | `9caab804dfb5231e1077061316ec5e67e19b8c83bb6977886076e70d4e29cb02` | `749fa4f33bf71d9758404778c92e88782e510a9e0868c970c18aa81a00db70e3` |
| `block/PitcherCropBlock.java` | `a48940b9fba5f8249ce0bd5a209fdc21de03772d53279de986fba27ab98455b9` | `9f90c4a6f3338d4092a735c16e20426f432b02dfbe0eda800d9b9aa41d8d581c` |
| `block/PowderSnowBlock.java` | `a018411167f5d8b60b131c3f6387dcd256b736ef64126bdcf33fd4457151654` | `2b693be4a4d3cf2b1895096431c16d7ba3ae51b9cf4320773d384bdd146ba417` |
| `block/SweetBerryBushBlock.java` | `529bb21df59f802b83a589440dc01d9a3b8814499b58b0624c83e4b24a85d2ff` | `39883c99abc12e6f6b654f025a2340396f86fea4e9bea6d9f5283172ef61699b` |
| `block/TripWireBlock.java` | `8fc4647ed98d48434dc3ac10590507156b62fce924d2d7fd28ea40cf9f466012` | `3b379d732599e55b156ffde5ebd0f7043ab4d59e6556f128e830038c77c9249a` |
| `block/WaterlilyBlock.java` → `block/LilyPadBlock.java` | `ff0e9dc095117193a3f64c675e888a72212d28a2197784437236bd5b632b24a7` | `bcbbcc0364f65c9eaea1254bff4f91ab06fc5bb9914ac0c2705b06dc3e096035` |
| `block/WebBlock.java` | `ece9f8840481748d2d1542990b442cc9a97d670255d693b85f528fa618f48414` | `8afb61bea4d6fc8502971d0729c6a2f87f4f55d618ae6ed3251b95141f524af5` |
| `block/WitherRoseBlock.java` | `fb0af751035e4fac8c50fd3191032b755489d6eb07d0aaabc6b5155d33d2ec06` | `83b17c96565c5011c3a127250c653dee112a79e47013c4a2c8be854a97d08ddf` |
| `block/state/BlockBehaviour.java` | `4d92417129eb6def084a67d249d2ff8c7a30a4767fa46e63a045667ea70d5133` | `9c7a103492d0714c90397da88eb696912ff6a9ca1c005d984c4746d52637fd1e` |

Additional callback, hook, and collector identities (same old hash at 1.21.2 and 1.21.3):

| Source | Old SHA-256 | 26.2 SHA-256 |
|---|---|---|
| `world/entity/Entity.java` | `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9` | `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5` |
| `world/entity/player/Player.java` | `a803203e92aa4729d5f5c9b16085b6a43ce51d9907d309eb96736e9c7c1340de` | `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531` |
| `client/player/LocalPlayer.java` | `fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0` | `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6` |
| `server/level/ServerPlayer.java` | `f6c58a668916857a2aa28f27b4b20d711b5f703aa19fad24875c129ae9ca712c` | `37e617a5beccbf3558c8cf8f6624ba9d704593ccc5d6633bedf71cf4f792e82e` |
| `world/entity/projectile/ThrownEnderpearl.java` → `world/entity/projectile/throwableitemprojectile/ThrownEnderpearl.java` | `794445f6a0d4729baef98a42299e10161cb26172da0a8145e19b6ad0256054f7` | `ea834f2764c9897d81ca9eb5b5acfc53588891d59ed510f72c552d9291c8ea29` |
| `world/level/block/entity/HopperBlockEntity.java` | `2c3bea593d386a87fd43e86943b212302a6deb8461849bb964ed76d9cf5633ed` | `975d1921b89953ed29d774719de75c2cd860ddca97197cedd815f4f1c4d20aa7` |
| `world/entity/InsideBlockEffectApplier.java` | — | `3ed01ec7fec19d9c06c8d40eacc81bbb2e6e0276e5f8cf11d26b4a5706c8e132` |
| `world/entity/InsideBlockEffectType.java` | — | `2cac4f03664883d22759ff248f2a6b5a0eade39c57d06b7e112a5a88130e512b` |
| `world/level/material/WaterFluid.java` | `96acd37fbabb3a438fd022a4e7d16cb70f6e91f9068bf73a41a4d2e4f2618fbc` (no override) | `14cb3d49ef1db28fdab367cd6a4f196f20b7886b8768153c6e82906e9d25f0ad` |
| `world/level/material/LavaFluid.java` | `34bef6c8b50cbd7a7f6717d7162243b4cd9dccab36c171accba67b8cce9f03cc` (no override) | `423ff44a57c316f58c21a66903a4ef2c1b9c3d26316bdc4d301727575f93703f` |
| `world/level/block/EyeblossomBlock.java` | absent | `ac1b30e3ebe13ed2821df373e07070e4cdb1de1c30bf2a8a708f8201d6146362` |

## Boundary

This report closes the requested old block-override inventory and the player hook routes relevant to this callback slice. Callback-body equivalence for Honey/Web and the named fire/lava/cauldron/powder-snow families remains bound to their cited independently reviewed closures; the present report adds the wider override inventory, the bubble-column gate distinction, portal direct-state route, hook route, and modern-only callback classification. It does not establish a release introduction boundary between 1.21.3 and 26.2, callback behavior for modern-only blocks, server movement emulation, complete pair coverage, or runtime trajectory parity. No product files, source corpus, Git history, wiki, tests, builds, game launches, or generated sources were changed or used.
