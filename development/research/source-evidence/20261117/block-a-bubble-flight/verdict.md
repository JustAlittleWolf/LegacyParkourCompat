# Independent source verdict: bubble-column callbacks while flying

## Verdict scope

**Accepted, bounded to the 1.21.1 → 1.21.2 source comparison.** The claim that a locally controlled player with active `abilities.flying` skips both bubble-column velocity responses in 1.21.2 is supported. For the inside-column callback, that same guard also skips the inherited fall-distance reset. The callback formulas and `BubbleColumnBlock` dispatch are unchanged across this adjacent pair. This verdict does not claim when the change first appeared before 1.21.2 or establish runtime parity.

Earlier releases were checked only as samples for player-specific callback eligibility. They show no player-class override in the sampled source files; they do not locate an introduction boundary. 26.2 is a later control sample, not evidence about the 1.21.2 boundary.

## Per-claim findings

| Claim | Verdict | Source result |
| --- | --- | --- |
| 1.21.1 LocalPlayer reaches Entity bubble callbacks without a player override | Supported | `LocalPlayer extends AbstractClientPlayer`, which extends `Player`; the 1.21.1 `Player` source has no callback override. Inherited `Entity.onAboveBubbleCol` sets Y to `max(-0.9, y - 0.03)` when dragging down, otherwise `min(1.8, y + 0.1)`. `Entity.onInsideBubbleColumn` sets Y to `max(-0.3, y - 0.03)` or `min(0.7, y + 0.06)`, then resets fall distance. |
| 1.21.2 active-flight guard suppresses both callbacks | Supported | `Player.onAboveBubbleCol` and `Player.onInsideBubbleColumn` each test `!this.getAbilities().flying` before calling `super`. With active flying, neither inherited Y write executes; the inside callback's inherited `resetFallDistance()` is also skipped. With flying false, both callbacks delegate to the same Entity formulas. |
| Above-versus-inside block dispatch changed | Not supported; source shows unchanged | In both releases, `BubbleColumnBlock.entityInside` checks whether the block above is air, then directly calls `onAboveBubbleCol` or `onInsideBubbleColumn`, passing `DRAG_DOWN`. |
| The callback is reachable on the local client | Supported | `LivingEntity` applies block effects when the world is server-side or the entity is controlled by the local instance. `Entity.applyEffectsFromBlocks` calls `checkInsideBlocks`; that traversal calls `state.entityInside` for the entity-inside shape when it is `Shapes.block()` or intersects the traversed movement. `BlockBehaviour` returns `Shapes.block()` by default, and BubbleColumnBlock does not override the shape. The direct callback path does not use `Player.isPushedByFluid()`; that separate predicate returns `!abilities.flying`. |
| Later native behavior remains available outside active flight | Supported narrowly | The 1.21.2 Player overrides delegate to `super` whenever the active flying bit is false; Entity's Y formulas and inside fall-distance reset remain in place. This says nothing about mod profile integration or full tick parity. |
| Sampled pre-1.21.2 Player classes introduce a flight guard | Not supported by these samples | The sampled 1.13–1.13.2 `PlayerEntity` and 1.14.4–1.21.1 `Player` files have no bubble-callback overrides. The sample set cannot establish an exact historical cutover. |
| 26.2 control matches 1.21.2 dispatch exactly | Not supported | 26.2 keeps active-flight guards in Player, but the above callback adds a `BlockPos` argument and BubbleColumnBlock checks `isPrecise`, then tests the block-above collision shape and fluid before dispatch. It is only a control. |

## Frozen source roster

Source paths below are relative to `decompiled_minecraft/<release>/<mapping>/`. Each listed source digest was rechecked against that version's `*.sources.sha256` entry (except 1.21.2, whose in-directory provenance manifest contains the matching source digest). These files were frozen before the final immutable claim inputs were requested.

| Release / mapping | Entity.java SHA-256 | BubbleColumnBlock.java SHA-256 | Player class SHA-256 |
| --- | --- | --- | --- |
| 1.13 / Ornithe Feather | `09514488d9055449c47e2e4bcd23b5773203ca634b7ecba2323a99d0ae016642` | `0320e1b70b215dfff209f8e5003b97cac3a247fd3d642597f2db8ab46d251b2e` | `net/minecraft/entity/living/player/PlayerEntity.java` `4a57ce65405436aeb211d7a04a9a9f6ae1a661d033fd3ba2424909bfb42b8acd` |
| 1.13.1 / Ornithe Feather | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` | `0320e1b70b215dfff209f8e5003b97cac3a247fd3d642597f2db8ab46d251b2e` | `net/minecraft/entity/living/player/PlayerEntity.java` `4c1b14b4727116071b1ba9e3eeae84b8077f3d767e10e49b6a68a2556da32272` |
| 1.13.2 / Ornithe Feather | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` | `0320e1b70b215dfff209f8e5003b97cac3a247fd3d642597f2db8ab46d251b2e` | `net/minecraft/entity/living/player/PlayerEntity.java` `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633` |
| 1.14.4 / Mojmap | `31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7` | `2cab08014222e6e3173986078b3fcb64348384b978c868e583cd2caa30c93be2` | `net/minecraft/world/entity/player/Player.java` `e9ce5eb18c5581ffe2b610b273ffd85786587a00ba853e5dba0ac96593622b12` |
| 1.15.2 / Mojmap | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` | `0f4b28ef76195b31cec9e45e3e3dd94fca26d1e851ce3c0a72748ea607a05879` | `net/minecraft/world/entity/player/Player.java` `1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793` |
| 1.16.5 / Mojmap | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` | `49e6ddc09f2d99056b0940ff0178c02bd0fe62d7758936eef751a3b9500f2ba5` | `net/minecraft/world/entity/player/Player.java` `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960` |
| 1.19.4 / Mojmap | `3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b` | `34f7c5400f1b8db997ac18333b7e57497b1667274779d4d410f4177b922cb452` | `net/minecraft/world/entity/player/Player.java` `5e4436afccb361156f8184e7a5cfd91d5b12dcfe8f5937b4ac23dd3edda737a2` |
| 1.20.6 / Mojmap | `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3` | `39c4da51460f4c2c06927f15de65a9ee2ae2f8bee0a2c9289cea375ad5d53a7c` | `net/minecraft/world/entity/player/Player.java` `785d93ccc94e1f912e545b2b0c355edeeb352b44ee8e83a69364daec35266dbe` |
| 1.21.1 / Mojmap | `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850` | `39c4da51460f4c2c06927f15de65a9ee2ae2f8bee0a2c9289cea375ad5d53a7c` | `net/minecraft/world/entity/player/Player.java` `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71` |
| 1.21.2 / Mojmap | `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9` | `9427b3e617753f5b3adc7a724fd80c87d4aa0e2f5d0eb38cd2ca34092e449b47` | `net/minecraft/world/entity/player/Player.java` `a803203e92aa4729d5f5c9b16085b6a43ce51d9907d309eb96736e9c7c1340de` |
| 26.2 / unobfuscated | `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5` | `ece83c008e6f8391b468dafe445c288630215fdff490defee50cb7766771e039` | `net/minecraft/world/entity/player/Player.java` `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531` |

Adjacent-pair route files: 1.21.1 `LocalPlayer.java` `c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583`, `AbstractClientPlayer.java` `759f8f03b89dab1f6b65c73e1e25a68ae6a6083cb49b5d2719136edda7edf696`, `LivingEntity.java` `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8`, `BlockBehaviour.java` `35420ee4fca120ccf1d1542a6b1746fcdd1e9dafd125cf0b8193f9b8b406b292`. 1.21.2: `LocalPlayer.java` `fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0`, `AbstractClientPlayer.java` `fa15cd69238b46b410bc709c8ae0a629d5f3f817a6bc454e70a1d60bf7aa785c`, `LivingEntity.java` `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52`, `BlockBehaviour.java` `4d92417129eb6def084a67d249d2ff8c7a30a4767fa46e63a045667ea70d5133`. 26.2 `BlockBehaviour.java`: `9c7a103492d0714c90397da88eb696912ff6a9ca1c005d984c4746d52637fd1e`.

## Exact source publication identities

For each release, the source provenance, ready marker, and source-file manifest are individually SHA-256 pinned below. The 1.21.2 tree instead has its provenance manifest inside the mapping directory; its `sources` map contains the checked file digests. All exact release IDs and mapping namespaces in these records match the selected directories.

| Release / mapping | Provenance SHA-256 | Ready SHA-256 | Source-manifest SHA-256 |
| --- | --- | --- | --- |
| 1.13 / ornithe-feather | `34652aeef6914b5cbff6619ae60a855aa676b2ae1f607eccdaa623ad09dc5e8d` | `82a4a8f6a03fbd99a7b8577b1ae5980d95af07c7ded0da284106d16eb1e01a99` | `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a` |
| 1.13.1 / ornithe-feather | `c28ed59f2c507b6133f7677742d316d38e9993eaf1fbb6c2054bd68d1085e904` | `f098b3a08b37276cc1a465a8a122df8de76d68786c854b92bac1ca51a79670de` | `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba` |
| 1.13.2 / ornithe-feather | `e25950b7855fc4f2aee1a662f0be6b0af9f28788d9e27293075cfc5a3aa5d383` | `1d1c644c1deb05c02d11dc575ad834f4f72489124701d9b2b90357f43dc37b38` | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` |
| 1.14.4 / mojmap | `9d1ad405e1e787d2fca5ed1897e6797b1f3cd79527da50d713232f46e2f30d97` | `ec7b6a6d9ba72f8a19908527977c970a8e2b3007b0e0b4323a725fb5943d3043` | `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b` |
| 1.15.2 / mojmap | `d4802d35ee2927a44d753871f184c3255c060eb94457a5c65a8bc121a087954e` | `64a0e40b784d525ce50937c63e465fc4f564efb1f7af17f2bac3c77a36907be0` | `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` |
| 1.16.5 / mojmap | `b71df61eb775134119881915198f87c4337bae409e1f0de8d8d6c007e062902d` | `e9dd4397c6ade5f68d27f50baf285086dc367b56d2119b03a116cf3cf3b23fee` | `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b` |
| 1.19.4 / mojmap | `0efcfc6075852711cff5d62c3177258cba273c206c8a2d2642bdf3487c676b34` | `59f59ab516d41eb97e09d63256ee26a68c15102052bf7acafdbd5440b72547d0` | `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3` |
| 1.20.6 / mojmap | `ee53e67e7ab51069a0eaae5a0ce13d49a9f972bdc13e3c31e041afa77f4d26e8` | `1ec1dc773ea2a8a3ef48a9fc868893bd2f83d6b13b057d75b151b711788c3d41` | `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311` |
| 1.21.1 / mojmap | `6f84a596ea944a1d40c79b53ead9d2cdd2b3c726d710a977758d15b5578ce7a9` | `988b38ac59491f9430d5bd1968db0fdaacb777a63fef745ca3e20342237a0310` | `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48` |
| 1.21.2 / mojmap | `00f399407dc447737ea83e3e46562418f8c712f66e3f51cb85d46afa2ecac575` (in-map `.decompile-provenance.json`) | — | source entries are in that manifest |
| 26.2 / unobfuscated | `a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1` | `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f` | `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` |

## Immutable claim inputs and independently checked source

The explicitly authorized prior-lane files were rehashed before review and matched the requested SHA-256 values:

- `bubble-column-flying-player-response.md`: `f00ecea5bc0642a63edcfbd4ee43439a7819711f4b5f9f3e143c3476b455d2b5`
- `comparison.md`: `cbaf179cee7fb11aa82c0d25b180bfc93493ddca8e73f1eda0ed1fdf6af971fe`

Their central eligibility claim is independently confirmed from `Player.java` and `Entity.java`; I did not rely on their prose as source evidence. The earlier comparison's evidence table has two source-digest transcription errors: 1.14.4 `BubbleColumnBlock.java` is `2cab08014222e6e3173986078b3fcb64348384b978c868e583cd2caa30c93be2`; 1.15.2 `Entity.java` is `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e`. The hashes above are the observed files and match their source manifests. No product, Git, backlog, or wiki files were opened or changed. No build, tests, launch, network access, or decompilation was performed.

## Exact member references and operation order

- **1.21.1:** `LocalPlayer.java:92,191-193` establishes the LocalPlayer class and its `super.tick()` call; `AbstractClientPlayer.java:18`, `Player.java:114`, and `LivingEntity.java:2319-2320` establish the hierarchy and superclass tick chain. `Entity.java:705` calls `tryCheckInsideBlocks()` during the native tick; `Entity.java:758-765,1001-1020` traverses the bounding-box blocks and dispatches `entityInside`. `BubbleColumnBlock.java:49-53,72-76` chooses the callback. `Entity.java:2357-2380` contains both inherited formulas and the inside fall-distance reset. `Player.java` contains no bubble callback override.
- **1.21.2:** `LocalPlayer.java:92,189-192`, `AbstractClientPlayer.java:17`, and `Player.java:118` establish the hierarchy and native `super.tick()` route. `LivingEntity.java:2774-2776` gates block effects to server-side entities or the locally controlled client; `Entity.java:736-750,1038-1072` traverses and invokes block callbacks. `BlockBehaviour.java:353-355` supplies the default `Shapes.block()` shape. `BubbleColumnBlock.java:50-53,74-77` dispatches above versus inside. The new guards are `Player.java:355-366`; inherited operations are `Entity.java:2455-2478`. The separate fluid predicate is `Player.java:1822-1824`.
- **1.21.2 arithmetic/precision:** the guards execute before `super`, with no new arithmetic, casts, or reordered operations in the inherited callback. Both inherited velocity updates read `Vec3.y` as a `double`, evaluate the subtraction/addition and `Math.max`/`Math.min` bounds in the shown order, then write only Y through `setDeltaMovement(x, y, z)`. Inside, the same inherited method performs `resetFallDistance()` after the velocity write; the reset assigns the float field `fallDistance = 0.0F`. Thus active flying skips the entire inherited operation; it does not merely clamp its result.
- **26.2 control:** `Player.java:310-321` keeps the flying guards. `BubbleColumnBlock.java:61-69` additionally uses `isPrecise` and checks collision shape plus fluid state above before its callback; the above callback signature carries a block position.

The supplied earlier comparison's sampled callback method locations are also confirmed: 1.13–1.13.2 `Entity.java:1871-1888` and `BubbleColumnBlock.java:41,71`; 1.14.4 `Entity.java:1923-1946` and `BubbleColumnBlock.java:39,69`; 1.15.2 `Entity.java:1906-1929` and `BubbleColumnBlock.java:38,68`; 1.16.5 `Entity.java:1932-1955` and `BubbleColumnBlock.java:39,69`; 1.19.4 `Entity.java:2138-2161` and `BubbleColumnBlock.java:43,65`; 1.20.6 `Entity.java:2291-2314` and `BubbleColumnBlock.java:52,74`. In those sampled Player files, the callbacks remain inherited.