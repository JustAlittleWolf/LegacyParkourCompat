# 1.8.9 → 1.9 explosion player-vector gate crosswalk

## Scope and bound inputs

This is a source-only crosswalk for the server-side player admission predicate that adds a player-to-explosion vector to `Explosion.damagedPlayers`. It does not assess explosion damage, movement integration, trajectory, or whether a particular map reaches an explosion. It uses the accepted exact-pair producer report and its independent audit only as the bounded starting point:

| Input | Exact identity |
| --- | --- |
| Pair | `1.8.9 → 1.9` |
| Source producer report | `build/source-189-19-20261117/tick-state/external-velocity-producers.md`, SHA-256 `33119428262A965543BD0A80EA00881D2F9C5B07A59A70F941BE5C70433C9C38` |
| Source producer audit | `build/source-189-19-20261117/tick-state/external-velocity-producers-audit/verdict.md`, SHA-256 `2C258197FEFB5EB46AD37A45C251DBFAB2B60971A51F321CCD9747D459E53D9F` (ACCEPT) |
| 1.8.9 source namespace | `decompiled_minecraft/1.8.9/ornithe-feather/`; `ornithe-feather.sources.sha256` SHA-256 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; `ornithe-feather.provenance.json` SHA-256 `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185` |
| 1.9 source namespace | `decompiled_minecraft/1.9/ornithe-feather/`; mapping-local `.decompile-provenance.json` SHA-256 `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a` |

All source references below are relative to those exact namespace roots. Hashes are SHA-256 of the named decompiled source files.

## Predicate and server-side state

| Release | Exact source predicate | Source identity |
| --- | --- | --- |
| 1.8.9 | A `PlayerEntity` is added when `!((PlayerEntity)entity).abilities.invulnerable` (`Explosion.java:138–140`). | `net/minecraft/world/explosion/Explosion.java`, SHA-256 `68D633251F2A6E604E53C2838693ED986692B87BF48E1BD390EE07C1E9C3390F` |
| 1.9 | A `PlayerEntity` is added when `!playerEntity.isSpectator() && (!playerEntity.isCreative() || !playerEntity.abilities.flying)` (`Explosion.java:144–148`). | `net/minecraft/world/explosion/Explosion.java`, SHA-256 `2DA5D23E51FE8ECF2AE1596FBEEEE4C00AB22390A23D374FD347AC97FD16414C` |

In 1.9 the mode predicates read the server interaction manager: `ServerPlayerEntity.isSpectator()` compares its game mode with `SPECTATOR` (`ServerPlayerEntity.java:897–900`), and `isCreative()` compares with `CREATIVE` (`:902–905`). The 1.8.9 explosion predicate instead reads the server player's `abilities.invulnerable` field directly.

Both releases' `WorldSettings.GameMode.apply` implement the same relevant assignments (`WorldSettings.java:102–120` in 1.8.9; `:104–122` in 1.9):

* `CREATIVE` sets `canFly=true`, `creativeMode=true`, and `invulnerable=true`; it does not assign `flying` in that branch.
* `SPECTATOR` sets `canFly=true`, `creativeMode=false`, `invulnerable=true`, and `flying=true`.
* Other modes set `canFly=false`, `creativeMode=false`, `invulnerable=false`, and `flying=false`.

The server interaction manager applies these flags when setting a mode: 1.8.9 `ServerPlayerInteractionManager.java:40–45`; 1.9 `:45–50`. `ServerPlayerEntity.setGameMode` also sends a game-mode event and synchronizes abilities in both versions (1.8.9 `ServerPlayerEntity.java:817–827`; 1.9 `:884–894`).

Relevant source identities:

| Source | SHA-256 |
| --- | --- |
| 1.8.9 `net/minecraft/world/WorldSettings.java` | `23703935A9FFDBF5854FFBEA06800E86D3C7481823682B6777311FBF35F81645` |
| 1.9 `net/minecraft/world/WorldSettings.java` | `31F980844878CA5D20F36C23736484622DE44B43F92754E7AACA5F4122DDD0D6` |
| 1.8.9 `net/minecraft/server/ServerPlayerInteractionManager.java` | `4B15005A73E2E2DF3B5E53C7427C727CBBFB37488FAF2C851EA5E4CB699BF9F5` |
| 1.9 `net/minecraft/server/ServerPlayerInteractionManager.java` | `E3885A1342264F7DF9B0287FB09A0865BAD65361199A2F7E2B72458B1F51C9CF` |
| 1.8.9 `net/minecraft/server/entity/living/player/ServerPlayerEntity.java` | `047158BDA6FA8FE2EA8A097972C098C5ABE492FFBBE280006AE7592CD97FBB81` |
| 1.9 `net/minecraft/server/entity/living/player/ServerPlayerEntity.java` | `DC6B4D3DFFF336AED5EFA03C9F97A3AE09531E0AE8CD80F8CB6DE949333FBE10` |
| 1.8.9 and 1.9 `net/minecraft/entity/living/player/PlayerAbilities.java` | `B0350CA7819F995E4EDE358A9D71270CC7A073A9D8817CE218D363CCA7EA0CB0` |

## State crosswalk

The table assumes the listed game mode has been applied by the server's interaction manager, so its game-mode predicates and ability bits correspond. “Admitted” means only that the player-vector map insertion predicate is true; it does not claim a nonzero vector or a movement outcome.

| Server game mode and `flying` state | Applied `invulnerable` | 1.8.9 vector entry | 1.9 vector entry | Result |
| --- | --- | --- | --- | --- |
| Survival or Adventure; either normal applied state (`flying=false`) | false | Yes | Yes | Same admission |
| Creative; `flying=false` | true | No | Yes | **Different admission** |
| Creative; `flying=true` | true | No | No | Same admission |
| Spectator; `flying=true` | true | No | No (`isSpectator()` is true) | Same admission |

Thus the exact changed state is an applied `CREATIVE` player with `abilities.flying == false`: 1.8.9 suppresses the player-to-explosion vector because `invulnerable` is true; 1.9 admits the vector because the player is not a spectator and the `!flying` disjunct makes its creative check true. The expression admits creative grounded/flying-off state, not only survival-like state.

## Local-player state path and limits

The state is reachable through ordinary source paths, subject to the unexamined event context:

1. In each release, server mode application gives Creative `canFly=true`, `creativeMode=true`, and `invulnerable=true` without overwriting `flying` (`WorldSettings.GameMode.apply` and `ServerPlayerInteractionManager.setGameMode` cited above). Therefore applied Creative with `flying=false` is not an invalid combination imposed by these methods.
2. The local client toggles flight only when `abilities.canFly` is true; a double-jump sequence toggles `abilities.flying` and calls `syncAbilities` (1.8.9 `LocalClientPlayerEntity.java:579–593`; 1.9 `:686–700`). A local player in a non-spectator mode who is on the ground has flying cleared and synchronized in both (`:632–636` and `:750–754`).
3. The client sends a `PlayerAbilitiesC2SPacket`; each server handler stores only `packet.isFlying() && player.abilities.canFly` (1.8.9 `ServerPlayNetworkHandler.java:921–924`; 1.9 `:1082–1085`). This confirms that the server-side `flying` bit used by the 1.9 gate is server-clamped to the mode's `canFly` permission.
4. Server `syncAbilities()` sends a `PlayerAbilitiesS2CPacket` on game-mode application; each client handler copies `flying`, `creativeMode`, `invulnerable`, and `canFly` from that packet (1.8.9 `ServerPlayerEntity.java:805–809`, `ClientPlayNetworkHandler.java:1271–1279`; 1.9 `ServerPlayerEntity.java:872–876`, `ClientPlayNetworkHandler.java:1319–1327`). The packet classes are byte-identical across these versions: S2C SHA-256 `808D7F80BFC07684566E6A1DCF53B14A68677DE599344F9DB50BD6AF84A85588`; C2S SHA-256 `E0BAB6BADB7937127365C8834EFAA92F61034FAE88717F95972E13BFB90BDAD1`.

Source identities for the synchronization path:

| Source | SHA-256 |
| --- | --- |
| 1.8.9 `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `1762B116E6B06D682B7DAAA0FC8CCE39B0FF455B3DB8CF79DAB03AC74F6C4053` |
| 1.9 `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `04D6196E7E17595C0AED59B28BC1730F8BE5EA3ECCDB1F971E9E2492DFADFE06` |
| 1.8.9 `net/minecraft/server/network/handler/ServerPlayNetworkHandler.java` | `47EF077A7FA0F74CF44E1C8BDA449D1B220BF56503B2FEF98FD8016E8092DF5B` |
| 1.9 `net/minecraft/server/network/handler/ServerPlayNetworkHandler.java` | `89609533320E02ED781C4AA87CAF9EB55417756BBA8A6B0EA53BB5F2E1836634` |
| 1.8.9 `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `7FA3D587325068EAA158526CB59E4850B704AC237938DF6592D1A09148DB40C3` |
| 1.9 `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `7F67BD2DAA792CFC4AAC1744D30F9CFDB28306EC55583D474DDC1A251561B1D6` |

This establishes an ordinary valid synchronized Creative-with-flight-off player state for the gate crosswalk. It does not establish that a particular eligible map or fixed movement profile reaches an explosion while in that state, or that the admitted vector changes a later tick's player movement. The external-velocity producer report's trajectory and interaction consequences remain outside this crosswalk; the source distinction is conditional on the explosion call and its already-unexamined prior ordinary reachability gates. The same server-side state is not inferred from a client-only mirror alone: the vector map is built in `Explosion.damageEntities` from the server player.

## Disposition

Source crosswalk: **conditional divergence established**. The server-side predicate admits a valid Creative, non-flying player vector in 1.9 that 1.8.9 excludes. No damage calculation, game/runtime trial, map-level reachability, or movement-trajectory claim is made. This bounded finding does not close or expand the full exact-pair coverage/freeze status.
