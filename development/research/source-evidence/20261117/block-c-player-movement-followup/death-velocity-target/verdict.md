# Independent source audit: local-player death velocity

## Verdict

**COMPLETED.** The bounded received-message claims are confirmed, with one correction to the comparison's boundary: the first stable inspected release with the event-3 player exclusion is **1.15**, not merely “after 1.14.4 and by 1.17.1.” Exact canonical sources are present and locally provenance-bound at every stable release in the requested interval. The source establishes the first stable release in this inspected interval, not an earlier preview or pre-1.14.4 origin.

## 26.2 received-message closure

`26.2/unobfuscated/net/minecraft/client/multiplayer/ClientPacketListener.java:1202-1224` resolves a `ClientboundEntityEventPacket` entity and, for the ordinary event route, calls `entity.handleEntityEvent(packet.getEventId())`. `LocalPlayer.handleEntityEvent` delegates unhandled event IDs (`LocalPlayer.java:505-524`); `Player.handleEntityEvent` likewise delegates (`Player.java:413-423`). `LivingEntity.handleEntityEvent` handles ID 3 by optionally playing the death sound, then sets health to zero and calls `die` only under `!(this instanceof Player)` (`LivingEntity.java:2067-2082`). Thus a received event 3 for the local player does not reach `die`, change health, or write velocity through this path.

The separate combat-kill route is also closed for a direct local movement write. `ClientPacketListener.handlePlayerCombatKill` resolves the packet player ID, acts only when it is the local player, then either opens `DeathScreen` or calls `LocalPlayer.respawn` (`ClientPacketListener.java:1845-1855`). `LocalPlayer.respawn` sends a `PERFORM_RESPAWN` client command and resets toggle keys; it does not alter player velocity or call `die` (`LocalPlayer.java:329-332`). The receive handler contains no direct velocity, health, or death-method write. `Player.die` still has a velocity write when called (`Player.java:526-541`); this audit does not claim that method is absent, only that these two received-message paths do not call it.

The screen-versus-respawn choice is server-fed: login handling applies `packet.showDeathScreen()` to the local player (`ClientPacketListener.java:492-542`), and the server-originated `IMMEDIATE_RESPAWN` game event can update the same flag (`ClientPacketListener.java:1593-1596`). On respawn player replacement, the listener copies `oldPlayer.shouldShowDeathScreen()` to the new player (`ClientPacketListener.java:1327-1335`). The received combat-kill packet therefore selects UI or requests server respawn according to this server-controlled setting; it does not itself recreate death physics locally.

## First stable exclusion cutoff

In 1.14.4, the canonical Ornithe Feather player override delegates unhandled events (`1.14.4/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java:417-428`); the living-entity event-3 case unconditionally sets health to zero and calls virtual `die(GENERIC)` (`.../LivingEntity.java:1457-1465`). The player `die` override writes the response velocity (`PlayerEntity.java:580-596`). These facts confirm that the path could reach the player velocity response in the older sample; no damage/death producer behavior was inspected.

The exclusion is present in the first stable release after that sample: `1.15/mojmap/net/minecraft/world/entity/LivingEntity.java:1475-1485` plays the sound, then guards health-zero and `die(GENERIC)` with `!(this instanceof Player)`. It remains present at the corresponding event-3 case in every checked stable release: 1.15.1, 1.15.2, 1.16, 1.16.1, 1.16.2, 1.16.3, 1.16.4, 1.16.5, 1.17, and 1.17.1. Accordingly, the first stable cutoff in the requested interval is **1.15**. This identifies the first stable sampled release carrying the change; it does not attribute the change to an uninspected preview or assert a deeper historical origin.

## Exact source identity and provenance

The immutable comparison input SHA-256 is `6773f9ef535883971bd5dbf4d2ba271df14e0c10cf84c2a4d32e6de9e8ca3b63`. All source reads were from canonical `decompiled_minecraft/<release>/<mapping>/` trees; the temporary `.yarn.staging-*` tree was excluded. Local provenance and source-hash manifests were checked for each release. Where a ready/source-manifest sidecar was not present (1.16.3, 1.16.4, 1.17), the mapping-local `.decompile-provenance.json` source entry matched the exact `LivingEntity.java` bytes.

`LivingEntity.java` SHA-256, checked versions and mappings:

- 1.14.4 / Ornithe Feather: `2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61`
- 1.15 / Mojmap: `139c3866ad2c6a1ad73496f22ff1a4b5259998442092ffbafb8664428e28bf79`
- 1.15.1 / Mojmap: `139c3866ad2c6a1ad73496f22ff1a4b5259998442092ffbafb8664428e28bf79`
- 1.15.2 / Mojmap: `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54`
- 1.16 / Mojmap: `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26`
- 1.16.1 / Mojmap: `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26`
- 1.16.2 / Mojmap: `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88`
- 1.16.3 / Mojmap: `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88`
- 1.16.4 / Mojmap: `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88`
- 1.16.5 / Mojmap: `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88`
- 1.17 / Mojmap: `8e81e5f1317d154fa41b676721ff581badd46296f77bcebcadb3c63448e528ed`
- 1.17.1 / Mojmap: `33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f`

For 26.2 / published unobfuscated names, relevant source SHA-256 values are: `LivingEntity.java` `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`; `Player.java` `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`; `ClientPacketListener.java` `9cb0cc8afeae9e4f4e42f428a52719c645817d03893dc371e6711c7bd16eac1b6`; `LocalPlayer.java` `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`. The first three also match the source hashes in the fixed comparison input.

Scope: received event/combat-message routes and the first stable event-3 exclusion in the requested interval only. No runtime claim, full tick inventory, server physics, respawn eligibility, or damage/death producer logic is covered.

TERMINAL: COMPLETED | input_sha256=6773f9ef535883971bd5dbf4d2ba271df14e0c10cf84c2a4d32e6de9e8ca3b63 | output_body_sha256=ffa508378094301fde3e24e6d50403b144f8ba594e4a8a512aeef199d44d5385 (all report bytes above, UTF-8 LF; terminal record excluded to avoid self-reference).
