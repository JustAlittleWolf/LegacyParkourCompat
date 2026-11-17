# Local-player death velocity: bounded source trace

**Terminal:** COMPLETED — bounded source trace only. This does not establish a runtime parity result or an exact first-release boundary.

## Result

In 1.8.9 and 1.9, the received entity event 3 is routed to the entity's virtual event handler. A player override handles unrelated event IDs and delegates event 3 to `LivingEntity`; that handler plays the death sound, sets health to zero, and calls `this.die(GENERIC)`. Dynamic dispatch reaches `PlayerEntity.die`, which writes local-player velocity: Y becomes `0.1F`; because the event supplies a non-null generic source, X/Z become the yaw and damage-direction knockback components, each scaled by `0.1F`. This is a direct client-side movement response to the received event.

The 1.14.4 sample still has that event-3-to-`die` path. By the inspected 1.17.1 sample, `LivingEntity.handleEntityEvent(3)` plays the sound but only sets health and calls `die` when `this` is **not** a `Player`; the `Player` override delegates unhandled IDs to that method. The same exclusion is present in 1.20.6, 1.21.11, and 26.2. Thus the path change is bounded only to **after inspected 1.14.4 and by inspected 1.17.1**. No exact introduction release is established because the intervening releases were not inspected.

In 26.2, the corresponding death-screen message is handled by `ClientPacketListener.handlePlayerCombatKill(ClientboundPlayerCombatKillPacket)`: it resolves the packet player ID and proceeds only when that entity is `minecraft.player`. If `shouldShowDeathScreen()` is true, it opens `DeathScreen`; otherwise it calls local `respawn()`. The flag is server-fed from the login packet (`showDeathScreen`) and preserved across respawn player replacement. The packet handler does not call `die` and contains no velocity write. Separately, the generic received event-3 handler still plays death sound, but explicitly skips setting health/calling `die` for `Player`. `Player.die` itself still writes its death velocity if called; these received-message paths do not reach it. This is a negative closure for a direct local velocity write in the inspected 26.2 death-message handlers, not a claim that no other network or tick path can write velocity.

## Exact source references

Paths are relative to `decompiled_minecraft/`.

- **1.8.9, Ornithe Feather:** `1.8.9/ornithe-feather/net/minecraft/client/network/handler/ClientPlayNetworkHandler.java:771` resolves entity and calls `entity.doEvent(packet.getEvent())`; `.../entity/living/player/PlayerEntity.java:371–380` delegates event 3; `.../entity/living/LivingEntity.java:955–976` handles event 3, sets health, and calls `this.die(DamageSource.GENERIC)`; `.../entity/living/player/PlayerEntity.java:513–533` writes size/position and death velocity.
- **1.9, Ornithe Feather:** `1.9/ornithe-feather/net/minecraft/client/network/handler/ClientPlayNetworkHandler.java:827–836` routes the event; `.../entity/living/player/PlayerEntity.java:341–350` delegates; `.../entity/living/LivingEntity.java:1058–1088` invokes virtual `die`; `.../entity/living/player/PlayerEntity.java:483–503` writes death velocity. Its combat-kill screen path is separately at `.../client/network/handler/ClientPlayNetworkHandler.java:1212–1218` and requires the resolved entity to equal the local player.
- **1.14.4, Ornithe Feather:** `1.14.4/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java:417–426` delegates unhandled event IDs; `.../entity/living/LivingEntity.java:1419–1464` includes event 3, health-zero, and `die(GENERIC)`; player death override starts at `.../entity/living/player/PlayerEntity.java:581`.
- **1.17.1, Mojmap:** `1.17.1/mojmap/net/minecraft/world/entity/player/Player.java:438–449` delegates unhandled events; `.../world/entity/LivingEntity.java:1643–1700` has event 3 and the `!(this instanceof Player)` guard; `.../client/multiplayer/ClientPacketListener.java:1434–1444` handles combat-kill, local-player identity, and `shouldShowDeathScreen()`/screen-vs-respawn branch; login death-screen flag is set at `.../ClientPacketListener.java:381`.
- **1.20.6, Mojmap:** `1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java:1803–1817` retains the player exclusion; `.../client/multiplayer/ClientPacketListener.java:1567–1578` retains combat-kill local-player and death-screen gating; `.../world/entity/player/Player.java:624–640` shows the `die` velocity writer when directly called.
- **1.21.11, Mojmap:** `1.21.11/mojmap/net/minecraft/world/entity/LivingEntity.java:1955–1973` retains the player exclusion; `.../client/multiplayer/ClientPacketListener.java:1765–1776` retains the screen gate.
- **26.2, published unobfuscated names:** `26.2/unobfuscated/net/minecraft/client/multiplayer/ClientPacketListener.java:1202–1223` dispatches received entity events; `.../world/entity/LivingEntity.java:2067–2083` skips death mutation for `Player`; `.../client/multiplayer/ClientPacketListener.java:1845–1855` handles `ClientboundPlayerCombatKillPacket`, checks local-player identity and chooses screen or `respawn()`; `.../world/entity/player/Player.java:526–541` contains the `die` velocity writer, which the two received-message paths above do not invoke. `LocalPlayer.java:505–524` has no death-event override and delegates unhandled IDs.

## Source identity and limits

Exact release directories and mapping namespaces inspected: 1.8.9/1.9/1.14.4 `ornithe-feather`; 1.17.1/1.20.6/1.21.11 `mojmap`; 26.2 `unobfuscated`. 1.8.9 and 1.14.4 provenance and ready/source manifests are present; 1.17.1, 1.20.6, 1.21.11 and 26.2 ready markers identify those exact client versions and mapping namespaces. 1.9 has a source-local `.decompile-provenance.json`; there is no sibling `.ready.json` or `.sources.sha256` in that mapping directory, so its provenance is less independently cross-checkable from the usual ready record.

SHA-256 of inspected source files (local source-byte identity):

- 1.8.9 `LivingEntity.java`: `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e`; `PlayerEntity.java`: `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88`.
- 1.9 `LivingEntity.java`: `5b5936656aacc64a4d75db7b370985f9456ac96076331da047bd246f76a705fb`; `PlayerEntity.java`: `edbb46236d1e42630e600e4afcb434eb6e9adecbfc4ac7e15710a5dea50010b1`.
- 1.14.4 `LivingEntity.java`: `2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61`.
- 1.17.1 `LivingEntity.java`: `33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f`; 1.20.6: `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2`; 1.21.11: `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8`.
- 26.2 `LivingEntity.java`: `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`; `Player.java`: `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`; `ClientPacketListener.java`: `9cb0cc8afeae9e4f4e42f428a52719c645817d03893dc371e6711c7bd16eac1b6`.

Scope limits: this is a narrow received-death-event/combat-message trace, not a complete client tick inventory; it excludes damage calculation, server physics, respawn eligibility, runtime behavior, and exact intermediate release attribution.
