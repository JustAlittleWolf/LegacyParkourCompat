# Blind coverage audit: 1.20.4 → 1.20.5 Nether portal process

Scope: ordinary, unmounted local player contact with a Nether Portal block; eligibility, pending contact, dwell, cooldown and the first movement tick after those gates. End portals/gateways, portal construction/destination geometry, rendering/UI, and non-player movement are outside this bounded audit.

## Evidence identity and integrity

Both source roots report version IDs `1.20.4` and `1.20.5`, mapping namespace `mojmap`, Vineflower decompilation, and ready status. The root `mojmap.sources.sha256` digest equals the `sourceManifestSha256` in its ready marker on each side; every source file cited below also matches its manifest entry. No embedded `.decompile-provenance.json` was present in either source tree.

| Side | Ready marker SHA-256 | Provenance SHA-256 | Source manifest SHA-256 | Client artifact SHA-256 | Mapping input pin |
|---|---|---|---|---|---|
| 1.20.4 | `6225b97979420cc743c8b7156a0ce512ef27d0dbf48bfffbc40d3e1c8cb044d1` | `10914d9b7b3fc29de066ca24f7d905e4d4f0a451a6c81729a2d1981addfc1385` | `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1` | `9221ab461a491bf9661cd8e773a5e662aaa43d600fa7970b8c12bbfb0431b838` | Provenance says `mappingOption: mojmap`; the allowed 1.20.4 metadata does not record an upstream official mapping file hash. Exact namespace is bound; raw mapping artifact identity is not independently pinned. |
| 1.20.5 | `4a587caa86e4ddd68ba63a5431417933dc4df4822e33aaf29ce77ebda53947c2` | `1d9b247fb839ad0e234054cba1668073245e60d86783eca40d44c2b7b23c6242` | `162dac6c4f1d539a1c2cea0255b7938a5281c207ab460bdc265358016d732484` | `49d5949adbb1021a7eb00c1b949f9e39934b10249cbf86d25004774d1b3ff510` | Provenance identifies “official Mojang client mappings”, SHA-256 `9afdb3b7fcc4c6acbec3bc6ccb46192bfc6460e12bf9e268e29bfee46bc7fbac`. |

Cited Java file SHA-256 values (each equals the corresponding `mojmap.sources.sha256` entry):

| Relative file | 1.20.4 | 1.20.5 |
|---|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` | `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa` |
| `net/minecraft/world/entity/Entity.java` | `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9` | `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3` |
| `net/minecraft/world/entity/LivingEntity.java` | `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d` | `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2` |
| `net/minecraft/world/entity/player/Player.java` | `218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd` | `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2` |
| `net/minecraft/server/level/ServerPlayer.java` | `ebb8c83e5e1095501409b09657fd1a668a0067a7653dac5e7c231b87ecf503c1` | `259e6abf2549771ab14a14ddfebd3d4967c953f20666cb8075c46f4faaffdba3` |
| `net/minecraft/world/level/block/NetherPortalBlock.java` | `d349c04fcd934a9dfd9e2ba9d6fb53d58944bddaf750d77628ce8e0970cbf580` | `ba6e3ce5cb9c24eb4e3dd30e3193e9e114db8acd36aa47246dc4793605f12023` |
| `net/minecraft/world/level/block/state/BlockBehaviour.java` | `6284a7d08a6d0d475cd4fe8aea62b35153ae8fbc0bce5f51788389f679233b54` | `5ccc577c6b257badf8bffab54181c45b6c57779c8545efe5c54a9766687e685b` |

The body hashes below are SHA-256 over each extracted Java method (UTF-8, CRLF normalized to LF), independently matching across releases. File hashes above bind those bodies to the manifest-verified decompilations.

| Operation body | 1.20.4 source / body SHA-256 | 1.20.5 source / body SHA-256 |
|---|---|---|
| portal block `entityInside` | `NetherPortalBlock.java:84`, `0ba2583d6ff61cfa38a011737044379c351b6b3dbeef1326684a824eab272cb8` | `NetherPortalBlock.java:84`, same |
| `Entity.handleInsidePortal` | `Entity.java:2027`, `181e3f027a290ed34c2ba64b9e4c0219f2408b296d5c145bf30a5991074a97f6` | `Entity.java:2049`, same |
| `Entity.processPortalCooldown` | `Entity.java:502`, `d730659bb7081fd85777fe21d0a2747ee3a34d26df0baf8c62b84f18162cfb88` | `Entity.java:504`, same |
| `Entity.handleNetherPortal` | `Entity.java:2039`, `d4170f5ed340deafe728bcd7a67c975d82518f4b305cfe11993521f6be0be0e9` | `Entity.java:2061`, same |
| `Entity.checkInsideBlocks` | `Entity.java:968`, `e61c30e3ee1c79345a6e980835d278ad995df1cd9fef07d13fdd3951bca607de` | `Entity.java:969`, same |
| `BlockBehaviour.State.entityInside` forwarding | `BlockBehaviour.java:740`, `3237444372b6085c1cc3a00ca4787d8d2e40bd80fdeeac75a4333557f422849b` | `BlockBehaviour.java:714`, same |
| `Entity.canChangeDimensions` | `Entity.java:2548`, `0d26314a646ade321044289d0ca1c2414a17c98e26b5f881017e510b560324f4` | `Entity.java:2557`, same |
| `Player.getPortalWaitTime` | `Player.java:408`, `a5eb62455aaaad3f6d74a6d10f5d96f1e13c71fb8ff754af34949b7b30496dc0` | `Player.java:428`, same |
| `Player.getDimensionChangingDelay` | `Player.java:433`, `fd8b069d82e13f9f679e7d2d32844e86e12b4db8e2e0714916bd0cbb0052381a` | `Player.java:453`, same |
| local-player `handleNetherPortalClient` | `LocalPlayer.java:815`, `fa39ac17dba8e5da031c44bc456d0d95820cdd2db7b00886c716aa63cdd2f7e2` | `LocalPlayer.java:822`, same |
| `ServerPlayer.doTick` authority entry | `ServerPlayer.java:485`, `fca303d341d356b416a9bcc9744a7ce69a31acb6068a091f478152a09e7bfa23` | `ServerPlayer.java:540`, same |

## Ordered reachable route

1. For a loaded local-player chunk, `LocalPlayer.tick` calls `super.tick` (`LocalPlayer.java:188–208` in 1.20.4; `:190–210` in 1.20.5). The player path reaches `LivingEntity.tick` (`LivingEntity.java:2269` / `:2336`), then `Entity.tick` and `Entity.baseTick` (`Entity.java:412–416` / `:414–418`). `Entity.baseTick` invokes `handleNetherPortal` before the entity's `aiStep` and movement (`Entity.java:430` / `:432`). That method's whole body is gated by `level() instanceof ServerLevel`, so the client-side local-player instance does not run dwell progression or dimension transfer there.
2. `LivingEntity.tick` calls virtual `aiStep` before its later tick bookkeeping. LocalPlayer's `aiStep` calls `handleNetherPortalClient` near its head, unless the screen is a `ReceivingLevelScreen` (`LocalPlayer.java:646–654` / `:648–656`). This happens before input handling and travel. The handler consumes a pending `isInsidePortal` bit, updates only spinning-effect fields/sound/UI, clears the bit and calls `processPortalCooldown`; it has no position or velocity writes. UI/sound effects are outside the movement verdict.
3. In the ordinary unmounted, non-spectator route, player travel reaches `LivingEntity.handleRelativeFrictionAndCalculateMovement` and `Entity.move` (`Entity.java:596` / `:597`). After movement/position and collision state updates, `Entity.move` calls `tryCheckInsideBlocks` (`:703` / `:704`). This call is after the current tick's LocalPlayer portal handler. `checkInsideBlocks` scans block coordinates overlapped by the entity AABB, inset by `1.0E-7`, only when the required chunks are present; each state receives `entityInside` and then `onInsideBlock` (`Entity.java:968–1000` / `:969–1001`). It is a bounding-box block-contact callback path; portal `VoxelShape` is not consulted here.
4. `BlockBehaviour.BlockStateBase.entityInside` forwards to the underlying block (`BlockBehaviour.java:740–741` / `:714–715`). `NetherPortalBlock.entityInside` checks `Entity.canChangeDimensions()` and then invokes `handleInsidePortal` (`NetherPortalBlock.java:84–87` on both). The gate is `!isPassenger() && !isVehicle()` (`Entity.java:2548–2550` / `:2557–2559`), satisfying the ordinary unmounted scope.
5. `Entity.handleInsidePortal` checks cooldown first. If cooldown is positive it resets it via `setPortalCooldown`; otherwise it records immutable portal entrance position only on a non-client level and sets `isInsidePortal = true` (`Entity.java:2027–2037` / `:2049–2059`). Thus the move that first enters/contact-overlaps the portal sets the pending bit after that tick's pre-travel portal handling. The local handler observes it on the next eligible LocalPlayer `aiStep`.
6. Authoritative dwell is in `Entity.handleNetherPortal` and executes only for a `ServerLevel`. When the pending bit is set it resolves the other Nether/Overworld `ServerLevel`; if it exists, Nether is enabled, the entity is not a passenger, and postfix `portalTime++ >= getPortalWaitTime()`, it sets portal time to the threshold, sets cooldown and calls `changeDimension`. It then clears the pending bit; when absent, positive dwell time decays by four and is clamped at zero. `processPortalCooldown` runs at method end (`Entity.java:2039–2068` / `:2061–2090`). `Player.getPortalWaitTime` returns `max(1, game-rule integer)` choosing the creative/default delay from `abilities.invulnerable` (`Player.java:408–417` / `:428–437`); player cooldown duration is 10 (`Player.java:433–435` / `:453–455`). The dimension change is decided on server authority before that server tick's movement.
7. The server player entry calls `super.tick()` from `ServerPlayer.doTick` when not suppressed by its spectator/unloaded-chunk condition (`ServerPlayer.java:485–490` / `:540–545`), then inherits Player/LivingEntity/Entity ticking. Consequently the `ServerLevel` branch, not LocalPlayer's visual handler, owns dwell and dimension transfer. No historical physics should be inferred on either logical server.

## Separate operation verdicts

| Operation | 1.20.4 → 1.20.5 verdict | Movement consequence / scope |
|---|---|---|
| AABB contact scan → block dispatch → NetherPortal eligibility | **No portal-specific behavior delta found.** `checkInsideBlocks`, state forwarding, block callback, and `canChangeDimensions` bodies are byte-identical after newline normalization. | Same ordinary unmounted contact sets the same pending bit. The `NetherPortalBlock.entityInside` override changes Java visibility (`public` to `protected`) in 1.20.5; its body is identical and the override remains dispatched. |
| Pending `isInsidePortal` write/read and client dwell presentation | **No behavior delta found.** `handleInsidePortal` and `LocalPlayer.handleNetherPortalClient` bodies match. | Same one-movement-step handoff: contact is marked after current movement; local handling consumes the bit before next movement. Spinning intensity is a presentation field and does not change position, velocity or the movement box. |
| Server dwell, wait gate and dimension transfer | **No behavior delta found.** `handleNetherPortal`, player wait-time selection and `ServerPlayer.doTick` entry bodies match. | Same server-only dwell gate/order and transfer threshold. Dwell and transfer are server authority, not historical client movement. |
| Portal cooldown | **No behavior delta found.** `processPortalCooldown` and player 10-tick delay override match. | A positive cooldown is refreshed on contact; otherwise it decrements once in the applicable tick path. The client visual handler decrements local cooldown; server cooldown processing remains inside server-gated `handleNetherPortal`. |
| First changed movement tick caused by these portal operations | **None observed in the compared portal-specific operations.** | Portal contact/dwell code itself writes no player position or delta movement. A server-authoritative dimension transfer can replace the player's dimension/position, but that is not a changed historical movement response in these sources. No separate portal-related position/velocity operation was found in the ordinary contact route. |

## Coverage boundary and open identity item

Examined both versions' LocalPlayer tick/aiStep/client handler; Player and LivingEntity tick/baseTick path; Entity tick/baseTick, movement contact dispatch, AABB block scan, contact eligibility, pending state, cooldown and server dwell; NetherPortalBlock callback; BlockBehaviour state dispatch; Player wait/delay; and ServerPlayer server tick entry. Terminal portal-specific operations have paired body hashes above. I did not trace portal frame validation/construction, destination search/relative exit placement, End/gateway handling, screen/rendering math, or unrelated movement/environment effects.

The 1.20.4 ready/provenance metadata pins Mojmap namespace and the exact client artifact, but its allowed provenance JSON lacks an upstream official mapping file checksum. This is an evidence identity limitation only; all cited mapped source files and their source-manifest integrity are verified. No pair-wide freeze is claimed.

