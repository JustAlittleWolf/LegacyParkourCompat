# 1.9 → 1.9.1 ability-flight / fall-flying overlap

## Result

**No movement difference found for the bounded overlap.** Both exact Feather trees have the same `PlayerEntity.moveRelative` state order: with `abilities.flying` true and not riding, it saves the current vertical velocity and air speed, sets fly air speed, calls `LivingEntity.moveRelative`, then restores vertical velocity as saved velocity × `0.6`, restores air speed, clears `fallDistance`, and writes flag 7 false. Because the superclass call happens before those writes, a pre-existing true fall-flying flag selects and completes the elytra branch's movement (including `move`) in that call; the ability-flight override's direct flag-7 clear is after that movement. It is not a pre-move cancellation of the glide branch.

The local ability-flight input update precedes the superclass movement path. A player with `canFly` toggles `abilities.flying` on the ordinary jump double-tap path and calls `syncAbilities`; when that ability is true and this is the camera player, sneak/jump input adjusts vertical velocity before `super.mobTick()`. The conditions for starting fall-flying are separate: local jump input, airborne, negative vertical velocity, no current fall-flying flag, no ability flight, and a flyable elytra. The server packet path accepts the start action only under its own airborne/descending/not-already-flying/not-in-water and flyable-elytra checks, then sets flag 7. The server accepts an ability-flight request only when `packet.isFlying()` and its authoritative `canFly` are both true. Thus the overlap being reviewed is a player whose ability-flight state is authorized and whose fall-flying flag was already accepted/synchronized; no timing or latency assumption is needed for the local method-order finding.

`fallDistance` is a separate write in the ability-flight wrapper. The glide branch can set it to `1.0F` before moving when vertical velocity is greater than `-0.5`; after the superclass movement returns, the ability-flight wrapper resets it to `0.0F`. That reset is not the flag-7 write and does not change which branch already moved.

The `1.9.1` local player also plays an Elytra sound when sending the start action; this is an audio side effect, not a movement delta. The checked movement branch, local flight-toggle/input ordering, glide-branch movement math and direct flag-7 clear are unchanged from `1.9`.

## Exact source evidence and ranges

All paths below are relative to `decompiled_minecraft/<version>/ornithe-feather/`; line numbers are inclusive.

| Evidence | 1.9 | 1.9.1 |
| --- | --- | --- |
| Local ability toggle, synced ability state, elytra-start input gate, and flying vertical-input adjustment | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:686-720` | same path, `:687-722` |
| Local `mobTick` calls `super.mobTick()` before grounded ability-flight shutdown | same path, `:750-754` | same path, `:752-756` |
| Ability-flight wrapper, call ordering, post-call vertical velocity/air-speed/fall-distance/flag writes | `net/minecraft/entity/living/player/PlayerEntity.java:1365-1383` | same path and range |
| `isFallFlying` glide branch, motion calculations, `move`, and grounded server-side clear | `net/minecraft/entity/living/LivingEntity.java:1289-1341` | same path, `:1293-1345` |
| Server acceptance of `START_FALL_FLYING` | `net/minecraft/server/network/handler/ServerPlayNetworkHandler.java:846-855` | same path/range |
| Accepted start action writes tracked flag 7 true; `isFallFlying` reads flag 7 | `net/minecraft/server/entity/living/player/ServerPlayerEntity.java:1040-1047`; `net/minecraft/entity/living/LivingEntity.java:2038-2040` | same paths/ranges |
| Server acceptance of ability-flight bit only when `canFly` | same path, `:1082-1085` | same path/range |
| Client application of server-synced ability bits | `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java:1319-1328` | same path/range |
| Client `syncAbilities` sends the ability packet | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:296-299` | same path/range |

`PlayerEntity.moveRelative` in both releases uses the same branch and operation order. In `1.9.1`, the local start-action block adds `play(new ElytraOnPlayerSoundInstance(this))` immediately after sending the packet; this does not alter the branch above. The superclass grounded clear is guarded by `!world.isClient`, whereas the player ability-flight wrapper's `setFlag(7, false)` is unconditional after its superclass call. The latter is the relevant local direct write in this overlap.

## Source identity and integrity

Canonical source location: `D:\\Javastuff\\LegacyParkourCompat\\decompiled_minecraft\\<version>\\ornithe-feather\\`. Both trees were read in place; no decompilation or generation was run. Each provenance manifest pins the exact source and mapping metadata inputs. Its `sources` digests were checked against actual file bytes for every cited movement/authority source below.

| Release | Feather artifact (pinned SHA-256) | Feather version metadata (pinned SHA-256) | Mojang client (pinned SHA-256) | Provenance manifest actual SHA-256 |
| --- | --- | --- | --- | --- |
| 1.9 | `1.9+build.2-mergedv2.jar` — `a4e09a92eab0fbf6ef4668e295db1949e9d52c42f145d56b6dfbf3a10c2c7c2a` | `ca32cbff5f91d52f6563a2d50a20e3f0fb1e1f76dbb6e41e53c847d2d6977371` | `0273b5c79d6b96799c6480ae61366d6b1d042655970de87f77f1a3f1ebea9307` | `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a` |
| 1.9.1 | `1.9.1+build.2-mergedv2.jar` — `6d5768353bf2ff2756d38537312548a8ac91d570f130d23c23e9412d2edd97b3` | `da56c9e7efe5c929b2ec8d99a8e2bbc6825847f2852a0e717fcd6c674db73dc4` | `56ad7e53f6f4ad393844e274aa2190692b61d2ad9f6b3d12b33a8b7332fd51f9` | `e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a` |

The exact pinned Feather metadata URLs are `https://meta.ornithemc.net/v3/versions/feather/1.9` and `.../1.9.1`; their pinned SHA-256 values are listed above. Provenance records the matching release-specific Feather build.2 artifact URLs. The decompiler tool hash is `vineflower-1.12.0.jar` = `1dfcfe974395734fa467ce620661c7623d05ba83670de0529b1fbd63ff548b9d`; recorded runtime/pipeline on both sides: Eclipse Adoptium 25.0.3+9-LTS, Windows 11 amd64; `lpc-decompile-v1`, single-threaded.

| Relative source file | 1.9 actual SHA-256 = manifest source SHA-256 | 1.9.1 actual SHA-256 = manifest source SHA-256 |
| --- | --- | --- |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `04d6196e7e17595c0aed59b28bc1730f8be5ea3eccdb1f971e9e2492dfadfe06` | `de9ad48bcfb2705ef7a9a63e9401c1742aead183e1e9c2d13f8fe40cca0139eb` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `edbb46236d1e42630e600e4afcb434eb6e9adecbfc4ac7e15710a5dea50010b1` | `d7035f08f03b01823dff70ca504f7244f9b0753542cc0f6dd7301e66f8866b71` |
| `net/minecraft/entity/living/LivingEntity.java` | `5b5936656aacc64a4d75db7b370985f9456ac96076331da047bd246f76a705fb` | `d8824f670037a88753c428c27213989fc5173b1d7c638f498f73395c145b6fd9` |
| `net/minecraft/server/network/handler/ServerPlayNetworkHandler.java` | `89609533320e02ed781c4aa87caf9eb55417756bba8a6b0ea53bb5f2e1836634` | `89609533320e02ed781c4aa87caf9eb55417756bba8a6b0ea53bb5f2e1836634` |
| `net/minecraft/server/entity/living/player/ServerPlayerEntity.java` | `dc6b4d3dfff336aed5efa03c9f97a3ae09531e0ae8cd80f8cb6de949333fbe10` | `dc6b4d3dfff336aed5efa03c9f97a3ae09531e0ae8cd80f8cb6de949333fbe10` |
| `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | manifest and actual SHA-256 `7f67bd2daa792cfc4aac1744d30f9cfdb28306ec55583d474ddc1a251561b1d6` | manifest and actual SHA-256 `7f67bd2daa792cfc4aac1744d30f9cfdb28306ec55583d474ddc1a251561b1d6` |
| `net/minecraft/network/packet/c2s/play/PlayerAbilitiesC2SPacket.java` | `e0bab6badb7937127365c8834efaa92f61034fae88717f95972e13bfb90bdad1` | `e0bab6badb7937127365c8834efaa92f61034fae88717f95972e13bfb90bdad1` |
| `net/minecraft/network/packet/s2c/play/PlayerAbilitiesS2CPacket.java` | `808d7f80bfc07684566e6a1dcf53b14a68677de599344f9db50bd6af84a85588` | `808d7f80bfc07684566e6a1dcf53b14a68677de599344f9db50bd6af84a85588` |

## Bounds

This is a bounded adjacent-source comparison of ordinary local player input and the overlap where authorized ability flight and an already accepted/synchronized fall-flying flag are both true at the `PlayerEntity.moveRelative` call. It does not claim the exact patch where any behavior first appeared beyond this 1.9 → 1.9.1 pair. It does not establish end-to-end tick parity or cover other flight states, server movement enforcement, non-player flight, sound/rendering, or unrelated movement. No A/B run, tests, build, launch, server interaction, or source generation was performed.



