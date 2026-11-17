# 1.15.1 → 1.15.2 movement-effect restoration applicability

Status: **bounded source-reachable player-state witness; pair remains PARTIAL.** This supplements the accepted exact-pair source report for the hidden-effect stacking candidate. It does not freeze the pair, establish runtime parity, or claim the state occurs in generated worlds.

## Bound inputs

Exact canonical Mojmap trees:

- 1.15.1: `decompiled_minecraft/1.15.1/mojmap/`; complete source manifest SHA-256 `c8fdce2f9432b72fc13c19a48f9ab3be203cac41a617139e81bac3552245912d`; root provenance SHA-256 `6a7af1f5d65d7af165c2ab9a90c1fae20169b38e70c8e92dbc5f59d94efe9461`; official client mappings SHA-256 `f46186d0ed9d17e073e16ae2c98675dbbc70b4a29495700837aa5de9c1b42c34`.
- 1.15.2: `decompiled_minecraft/1.15.2/mojmap/`; complete source manifest SHA-256 `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7`; root provenance SHA-256 `d4802d35ee2927a44d753871f184c3255c060eb94457a5c65a8bc121a087954e`; official client mappings SHA-256 `65ad295b6cf63821f5d8f961c128128475e6d39386358d22eb238a3ce6e31777`.

This witness is bounded to the accepted source packet `build/source-mid-patches-20261117/1.15.1--1.15.2/ordinary_player/source-report-r2.md` (SHA-256 `322E867BEA50AF506E4438F4809D3C893B5E2897B656F02C0855D41A43274D2B`), whose citation-only correction was independently accepted in `build/source-mid-patches-20261117/1.15.1--1.15.2/ordinary_player/audit-r2/verdict-r2.md` (SHA-256 `710103010D2473E0F9C9D3ECBD0051222F6CAD00D1382A90E91AC5B74104F0E3`).

## Legal effect setup and paired result

A command-capable vanilla map can apply two Speed effects to a player in order: Speed I (amplifier 0) for 20 seconds, then Speed II (amplifier 1) for 5 seconds. `EffectCommands.register` accepts a target entity collection, effect, integer seconds and amplifier; the give path converts a non-instant duration to seconds × 20 ticks and calls `LivingEntity.addEffect`. The command requires permission level 2, so the state is explicitly a command-enabled map/admin setup. The packet makes no claim about generated-world occurrence or survival acquisition.

After the first effect, the player's active `MobEffectInstance` is Speed I with duration 400. Adding Speed II sets a higher amplifier and shorter 100-tick duration. The exact paired behavior is:

- **1.15.1:** `MobEffectInstance.update` replaces the active amplifier/duration but has no hidden-effect field. When the 100-tick Speed II expires, `LivingEntity.tickEffects` sees `tick` return false on the server, removes the active effect and invokes `onEffectRemoved`. The prior 400-tick Speed I is not restored.
- **1.15.2:** on a higher-amplifier, shorter-duration replacement, `update` saves the previous Speed I instance as `hiddenEffect`. `tickDownDuration` decrements the hidden duration along with the active effect. When Speed II reaches zero, `tick` copies the hidden instance's details back to the active instance, drops that hidden node and runs the update callback. After the same 100 ticks, the restored Speed I has 300 ticks remaining; `LivingEntity.tickEffects` keeps the effect active and invokes `onEffectUpdated(instance, true)` through the callback.

`MobEffects.MOVEMENT_SPEED` declares a `MOVEMENT_SPEED` modifier of `+0.2`, `MULTIPLY_TOTAL`; `MobEffect.getAttributeModifierValue` multiplies the amount by amplifier+1. Thus the active Speed II modifier is `+0.4`, and the restored Speed I modifier is `+0.2`. At the 100-tick boundary, 1.15.1 removes the effect modifier while 1.15.2 replaces it with the lower-amplifier modifier. No trajectory or exact displacement is asserted here.

This reaches the movement attribute consumed by the local player. `SharedMonsterAttributes.MOVEMENT_SPEED` is syncable. On the server, effect add/update/remove applies or removes the modifier; `ServerPlayer.onEffectUpdated` sends the updated effect state. `ServerEntity.sendDirtyEntityData` emits dirty syncable attribute snapshots via `broadcastAndSend`, which also sends directly to the affected `ServerPlayer` connection. `ClientPacketListener.handleUpdateAttributes` installs the base and modifiers on the client entity. `Player.aiStep` reads the movement-speed attribute into `speed`; `LocalPlayer.aiStep` uses `getSpeed()` for its local input movement. These methods are source-level state/data flow evidence only, not a runtime timing measurement.

## Exact source references and hashes

All listed Java hashes were checked against disk and the endpoint's exact `mojmap.sources.sha256` entry.

| Source and relevant member | 1.15.1 SHA-256 | 1.15.2 SHA-256 |
|---|---|---|
| `net/minecraft/world/effect/MobEffectInstance.java` — `update`, `tick`, `tickDownDuration` | `80cc2a91b77d4687a9b36bcb38e3107e3b2b8f859c69893e4eec0e98b4941b5e` | `4999d12cd468e402506f067dbd8694b1c8125dd9131e4f8a2fcc1848f616f180` |
| `net/minecraft/world/entity/LivingEntity.java` — `tickEffects`, `addEffect`, `onEffectAdded/Updated/Removed` | `139c3866ad2c6a1ad73496f22ff1a4b5259998442092ffbafb8664428e28bf79` | `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54` |
| `net/minecraft/world/effect/MobEffects.java` — Speed/Slowness declarations | `472b25ede6bed3ad3ed1090db3310cea9708b637f111981bcf083f492c030412` | `472b25ede6bed3ad3ed1090db3310cea9708b637f111981bcf083f492c030412` |
| `net/minecraft/world/effect/MobEffect.java` — modifier add/remove and amplifier scaling | `f218a040ff9f77272395245c4ffecc3f2266335600b79be0a5b2823c46ad7dd0` | `f218a040ff9f77272395245c4ffecc3f2266335600b79be0a5b2823c46ad7dd0` |
| `net/minecraft/world/entity/monster/SharedMonsterAttributes.java` — syncable movement-speed attribute | `f76db9a5159192af7548be4b521581038fcbc68879ba41d5441da91f543737ab` | `f76db9a5159192af7548be4b521581038fcbc68879ba41d5441da91f543737ab` |
| `net/minecraft/world/entity/player/Player.java` — `aiStep` movement-speed read | `6985e11ad9be553e9a2f25e5decc20a9da5c3b59b68f4e83a5b9f448cd2e48a0` | `1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793` |
| `net/minecraft/client/player/LocalPlayer.java` — `aiStep` reads `getSpeed()` for input movement | `7227da5233e3c72ced008187b92a6a86842f84778eaa9f40fa1eb117286af823` | `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd` |
| `net/minecraft/server/commands/EffectCommands.java` — give command target/duration/amplifier dispatch | `97864d37c315ac1040bed12d49dd2dba355954548fce4a3e2d3d2b1d3b93086e` | `97864d37c315ac1040bed12d49dd2dba355954548fce4a3e2d3d2b1d3b93086e` |
| `net/minecraft/server/level/ServerPlayer.java` — effect update/removal packet callbacks | `719416651f82ff78e9990a77e5bea0998938bf8070898865c37ae2df6e3b22da` | `719416651f82ff78e9990a77e5bea0998938bf8070898865c37ae2df6e3b22da` |
| `net/minecraft/server/level/ServerEntity.java` — dirty attributes and self-send | `dc5892e3b2d7c6a141f235053c414284a6c77876981db3ba1ba8419af08d5527` | `dc5892e3b2d7c6a141f235053c414284a6c77876981db3ba1ba8419af08d5527` |
| `net/minecraft/client/multiplayer/ClientPacketListener.java` — `handleUpdateAttributes` | `d806d286af4b93d7c61c3c884f344a5870ce1871fb0a811506031adf78fafc30` | `d806d286af4b93d7c61c3c884f344a5870ce1871fb0a811506031adf78fafc30` |

The source identities and hashes above establish this bounded state/applicability route. This is not a fresh full-pair coverage audit: unrelated tick paths, any other pair dependencies, implementation association and runtime parity are not addressed.