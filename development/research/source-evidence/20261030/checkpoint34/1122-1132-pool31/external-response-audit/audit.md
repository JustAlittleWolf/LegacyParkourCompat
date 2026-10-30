# Independent bounded audit: external player responses, 1.12.2 → 1.13.2

## Binding and verdict

This audit is bound to these immutable drafts:

- `external-responses/coverage.md` — SHA-256 `56ceb5e478775097bbf3c237ca155bde28819333ff1895eda9da5a7c5dde5dec`
- `external-responses/findings/slow-falling-gravity-response.md` — SHA-256 `13dee8f61162abada491bca132d4d872602f016c0e2900cefc287369846bc10a`
- `external-responses/findings/riptide-self-launch.md` — SHA-256 `3c3ed2dd75fc222061c2128775c3a3f8e68d00fdd36e753a32d0c51912d5ab44`

**Verdict:** accept the bounded source-path descriptions below with the Slow Falling qualification in this audit. The coverage is not a full pair inventory or pair freeze. Convert both newer-only draft findings to scope dispositions; neither represents a behavior to backport into a 1.12.2 profile.

## Source identity and integrity

Read-only canonical source roots used:

- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.12.2\ornithe-feather`
- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.13.2\ornithe-feather`

The release markers identify exact versions `1.12.2` and `1.13.2`, namespace `ornithe-feather`, and Feather Gen2 build 2 inputs (`feather-gen2-1.12.2+build.2-mergedv2.jar` plus its `.tiny`, and the corresponding 1.13.2 build 2 pair). The source-manifest hashes recomputed from the canonical files match both `ready.json` markers. I checked every manifest entry against its current file and checked for unlisted source files: 1.12.2 has 2,050/2,050 matching files, with no missing, differing, or unlisted files; 1.13.2 has 2,711/2,711 matching files, with no missing, differing, or unlisted files.

| Version | Source manifest SHA-256 | Artifact manifest SHA-256 | Marker status |
|---|---|---|---|
| 1.12.2 | `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` | `8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c` | `verified-identical-source-and-input-artifacts` |
| 1.13.2 | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` | `fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e` | `verified-identical-source-and-input-artifacts` |

The verification markers report zero source differences and no raw input artifact differences. Each reports one difference for its regenerated derived `client-ornithe-feather.jar`; the marker explains that the published source tree and raw inputs match and that the intermediate remap JAR was regenerated. This audit relies on the source tree and manifests, not on claiming byte identity for that derived archive.

## Bounded path audit

### Client packet response and server correction

- `ClientPlayNetworkHandler.handlePlayerMove(PlayerMoveS2CPacket)` is equivalent in 1.12.2 lines 578–619 and 1.13.2 lines 621–662. Both resolve relative position axes against the current player coordinates, clear velocity only on absolute axes, resolve relative pitch then yaw, update position/angles, send the teleport acknowledgement, then send the position/angle response. No client response delta was found.
- `handleExplosion` is equivalent at 1.12.2 lines 928–937 and 1.13.2 lines 968–977: apply `damageBlocks(true)`, then add packet velocity components to the local player's velocity in X/Y/Z order.
- `handleEntityVelocity` is equivalent at 1.12.2 lines 464–470 and 1.13.2 lines 507–513: resolve the entity, then call `lerpVelocity` with each integer component divided by `8000.0`.
- `ServerPlayNetworkHandler.teleport(..., relativeArgs)` changes its representation but not its resulting requested absolute target. In 1.12.2 lines 538–559 the server adds current relative coordinates/angles to form the target and sends the original tuple. In 1.13.2 lines 801–814 it stores/applies the absolute target and subtracts current relative coordinates/angles to form the packet tuple. The unchanged client resolver reconstructs the same target. No local-client response delta found.

### Login, respawn, dimension transfer, and spectate

- `ClientPlayNetworkHandler.handleLogin` (1.12.2 lines 286–305; 1.13.2 lines 316–340) preserves interaction-manager replacement, world construction/assignment, player dimension and network ID assignment, screen, game mode, and settings sync order. The brand packet spelling/format and removed max-player-count assignment are unrelated to player movement response.
- `handlePlayerRespawn` (1.12.2 lines 905–925; 1.13.2 lines 944–965) preserves the dimension-change gate, scoreboard carry-over, world replacement, player dimension update, `Minecraft.respawnPlayer`, and game-mode update. `Minecraft.respawnPlayer` preserves removal/recreation, synced-data copy, `resetPos`, entity add, interaction initialization, network ID restoration, and adjustment (1.12.2 lines 1965–1998; 1.13.2 lines 1454–1487). Recipe-book type differs, not the selected position/velocity response.
- `PlayerManager.changeDimension(ServerPlayerEntity, ...)` preserves respawn packet → transfer → correction teleport → interaction world update → abilities/world/player info/effect packets order (1.12.2 lines 487–512; 1.13.2 lines 494–519). The entity transfer method preserves scale factors, world-border clamps, End/forced-spawn selection, angle reset, integer coordinate clamp, portal-forcer, and final world assignment (1.12.2 lines 514–567; 1.13.2 lines 521–574); dimension IDs become `DimensionType` comparisons.
- The cross-world spectator path is equivalent in its player transform response. The 1.12.2 handler explicitly sends respawn, sets the target transform, changes world, and teleports/corrects (lines 672–719). In 1.13.2 `ServerPlayNetworkHandler.handlePlayerSpectate` delegates to `ServerPlayerEntity.teleport(ServerWorld, ...)` (lines 926–940; implementation lines 1228–1258), which preserves respawn → set transform/world → correction ordering. Same-world spectator teleport remains a position-only teleport in each release.

### Status/effect response and Slow Falling qualification

- For both releases, `ServerPlayerEntity.onStatusEffectApplied` sends `EntityStatusEffectS2CPacket` (1.12.2 lines 968–978; 1.13.2 lines 988–998). The client add handler resolves the entity/effect, creates the instance, and applies it to a `LivingEntity` (1.12.2 lines 1281–1294; 1.13.2 lines 1396–1409). The remove handler resolves the entity and calls `removeEffect` in both (1.12.2 lines 1362–1368; 1.13.2 lines 1488–1494). The 1.13 constructor's extra icon argument does not change this player movement path.
- 1.13.2 registers `slow_falling` at `StatusEffect.java:286` and exposes `StatusEffects.SLOW_FALLING` at `StatusEffects.java:36,82`. The 1.12.2 effect registry has no `slow_falling` registration/identifier or `SLOW_FALLING` field. The 1.13.2 `LivingEntity.moveRelative` branch at lines 1478–1484 is reachable for a locally controlled player: when `velocityY <= 0.0` and the effect is present, it selects `d = 0.01` and clears `fallDistance`; otherwise `d` starts at `0.08`. This is a 1.13 feature addition and should be a scope disposition, not a historical finding for 1.12.2.
- **Draft qualification:** do not characterize the Slow Falling operand as affecting only the later ordinary gravity subtraction. The same `d` also feeds the fall-flying vertical formula at 1.13.2 line 1500, and the liquid/ladder branch's later `d / 4.0` gravity at lines 1602–1611; in ordinary non-liquid, non-fall-flying movement it reaches `velocityY -= d` at lines 1584–1595, gated by `!isNoGravity()`. If retaining this draft for evidence, describe its uses accurately. Also, comparison of the surrounding `moveRelative` method exposes separate pre-existing movement differences, including `0.16277136F` (1.12.2 line 1485) versus `0.16277137F` (1.13.2 line 1545) and altered fall-flying arithmetic (1.12.2 lines 1434–1444 versus 1.13.2 lines 1493–1503). Those are outside this external-response inventory and remain unreviewed as independent movement findings; the Slow Falling disposition does not close them.

### Item-use / Riptide

- The 1.13.2 local release path is direct: `ClientPlayerInteractionManager.stopUsingHand` sends `RELEASE_USE_ITEM` then calls `player.stopUsingItem()` (lines 408–412); `LivingEntity.stopUsingItem` dispatches the active stack's `stopUsing` before clearing it (lines 2288–2294); `ItemStack.stopUsing` delegates to the item (lines 379–381). The server also handles the release action by invoking `player.stopUsingItem()` (`ServerPlayNetworkHandler` lines 844–846), so the server-side use completion is present as well.
- 1.13.2 `TridentItem.stopUsing` gates on player entity, at least 10 use ticks, and Riptide's water/rain condition, then adds look-directed velocity; on ground it additionally invokes `move(MoverType.SELF, 0.0, 1.1999999F, 0.0)` (lines 56–109). `startUsing` also gates durability and Riptide water/rain eligibility (lines 114–127). `TridentItem.java` is absent in the 1.12.2 tree and manifest. This is a new item and operation in 1.13.2; dispose the draft as newer-only behavior that remains native in the newer profile, rather than a 1.12.2 historical finding.

### Piston response

- The inspected `MovingBlockEntity` calls and `Entity.move(MoverType.PISTON, ...)` accumulator/clamp/epsilon return branch preserve the same player push calculation and operation order across the pair (1.12.2 `MovingBlockEntity` lines 143–164, 231–243; `Entity.move` lines 467–503; 1.13.2 `MovingBlockEntity` lines 127–148, 219–231; `Entity.move` lines 478–514).
- The secondary volume spelling change is equivalent: 1.12.2 `Block.FULL_BLOCK_SHAPE` is `Box(0,0,0,1,1,1)` (`Block.java:52`); 1.13.2 `VoxelShapes.block()` returns a voxel set covering the full 1×1×1 cube (`block/shape/VoxelShapes.java:16–22,30–32`), whose bounds are the same unit box. No piston player-response delta found.

## Scope dispositions and limits

README scope says features introduced after the selected profile retain modern behavior. Therefore, the Slow Falling and Riptide drafts describe real 1.13.2 player movement operations but do not establish changes that a 1.12.2 profile must emulate. Record them as exclusions/dispositions under that scope rule, not accepted historical findings. A broader search could still find changes to old features in the same methods or operations; this bounded audit does not rule those out.

The selected packet, login/respawn, dimension/spectate, status, item-use, and piston paths were retraced. This audit does not claim full player-tick coverage, all collision/block shapes, all event callbacks, or pair completeness. README scope exclusions remain rendering, input-device plumbing, non-player/vehicle physics, hunger production, healing, damage calculation, and bed-entry eligibility; direct player responses and predicates that read vanilla state remain eligible. No pair freeze or complete-pair acceptance is claimed.

## File hash cross-checks

Each following source-file SHA-256 was recomputed from the canonical file and matched its version's `.sources.sha256` entry. The complete manifests were also checked as described above.

| Release | Source file | SHA-256 |
|---|---|---|
| 1.12.2 | `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `fce21d9902e555fd46545fb04bdb6c4a912eef6398790776ad1a122e09960c7c` |
| 1.13.2 | `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `4f65502fbee6476cef0838563f03a66f07a1a9ed8c63e580f5338ba5fe274f11` |
| 1.12.2 | `net/minecraft/server/network/handler/ServerPlayNetworkHandler.java` | `77bf65b2c48ff952713942e183af1cd5fb243ad4f8fd2e53aa1b97272e7acd7c` |
| 1.13.2 | `net/minecraft/server/network/handler/ServerPlayNetworkHandler.java` | `ef4aa5d7b3039eb4b11aa47afd5056773a53e1dcd4c51cc1df472737c2ec7755` |
| 1.12.2 | `net/minecraft/server/PlayerManager.java` | `882937cb6794bc34366dd67a0b43f2c8122b54608ed822b5bd67880af503d53d` |
| 1.13.2 | `net/minecraft/server/PlayerManager.java` | `98cd7541d22bb655bd0b18fd5004e17e2e464f341330038680946c3d4b8a58b3` |
| 1.12.2 | `net/minecraft/client/Minecraft.java` | `8af833a5d03a6f6516a7eff0ab463a96bfec64aac31fc80656f37d193b1c1310` |
| 1.13.2 | `net/minecraft/client/Minecraft.java` | `b1d35473cd12bdf38e87f9888b4ed6f620614cbe914cf7e7a40c601d4e284875` |
| 1.12.2 | `net/minecraft/server/entity/living/player/ServerPlayerEntity.java` | `ad4effc65edd98098d86af8ba725f6d6c2c94068f996739cf2734d34ecf804a9` |
| 1.13.2 | `net/minecraft/server/entity/living/player/ServerPlayerEntity.java` | `82b02af5dcf3ad9ff6ab2db89fa6a48a7723913cbdb306f14e17a9b384d17728` |
| 1.12.2 | `net/minecraft/entity/living/LivingEntity.java` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` |
| 1.13.2 | `net/minecraft/entity/living/LivingEntity.java` | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` |
| 1.13.2 | `net/minecraft/entity/living/effect/StatusEffect.java` | `60cc53e7233d28413d3c93292fd216babe8ea9edeb70774141b68101c515c639` |
| 1.13.2 | `net/minecraft/entity/living/effect/StatusEffects.java` | `d982a7e051da6014286e07dcbfd36fd62dd0faa4ae5218a1c2d89bff02a78d9e` |
| 1.12.2 | `net/minecraft/client/ClientPlayerInteractionManager.java` | `6de11b1d7c5c05ae930a966bb6aaf1ae512100b7700501ebc4d15f00356d3406` |
| 1.13.2 | `net/minecraft/client/ClientPlayerInteractionManager.java` | `892bdb99e894b538daeeb7fd9ae6f9a0970b854f38f51b4b39c446e905692359` |
| 1.13.2 | `net/minecraft/item/ItemStack.java` | `ab31c259964fdc7e8c4a6fdfd57371fca2146d8823eb6db6eae783e3eec89d66` |
| 1.13.2 | `net/minecraft/item/TridentItem.java` | `97e5b7c3a84ba25c0dd229b78f571c936d7370006585c531896d5c49c76437f3` |
| 1.12.2 | `net/minecraft/block/entity/MovingBlockEntity.java` | `a55ee14227fe6ac0932283cba0414e946ba8e2637f543b3a3fe13d4e45b7b16e` |
| 1.13.2 | `net/minecraft/block/entity/MovingBlockEntity.java` | `6684ccc05adf8b29cac55f9796133405c6d4f6853051e651895303b657cd1c46` |
| 1.12.2 | `net/minecraft/entity/Entity.java` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` |
| 1.13.2 | `net/minecraft/entity/Entity.java` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |
| 1.12.2 | `net/minecraft/block/Block.java` | `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1` |
| 1.13.2 | `net/minecraft/block/shape/VoxelShapes.java` | `313968d4e5855b5ec380272b6ababcc5849d478ab771926355912a8cb6aa9c95` |
